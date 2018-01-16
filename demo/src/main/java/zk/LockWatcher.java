package zk;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Johnny on 2017/12/31.
 */
public class LockWatcher implements Watcher {
    /**
     * 成员变量
     **/
    private ZooKeeper zk = null;
    // 当前业务线程竞争锁的时候创建的节点路径
    private String selfPath = null;
    // 当前业务线程竞争锁的时候创建节点的前置节点路径
    private String waitPath = null;
    // 确保连接zk成功；只有当收到Watcher的监听事件之后，才执行后续的操作，否则请求阻塞在createConnection()创建ZK连接的方法中
    private CountDownLatch connectSuccessLatch = new CountDownLatch(1);
    // 标识线程是否执行完任务
    private CountDownLatch threadCompleteLatch = null;

    /**
     * ZK的相关配置常量
     **/
    private static final String LOCK_ROOT_PATH = "/myDisLocks";
    private static final String LOCK_SUB_PATH = LOCK_ROOT_PATH + "/thread";

    public LockWatcher(CountDownLatch latch) {
        this.threadCompleteLatch = latch;
    }

    @Override
    public void process(WatchedEvent event) {
        if (event == null) {
            return;
        }

        // 通知状态
        Event.KeeperState keeperState = event.getState();
        // 事件类型
        Event.EventType eventType = event.getType();

        // 根据通知状态分别处理
        if (Event.KeeperState.SyncConnected == keeperState) {
            if (Event.EventType.None == eventType) {
                System.out.println(Thread.currentThread().getName() + "成功连接上ZK服务器");
                // 此处代码的主要作用是用来辅助判断当前线程确实已经连接上ZK
                connectSuccessLatch.countDown();
            } else if (event.getType() == Event.EventType.NodeDeleted && event.getPath().equals(waitPath)) {
                System.out.println(Thread.currentThread().getName() + "收到情报，排我前面的家伙已挂，我准备再次确认我是不是最小的节点！？");
                try {
                    if (checkMinPath()) {
                        getLockSuccess();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (Event.KeeperState.Disconnected == keeperState) {
            System.out.println(Thread.currentThread().getName() + "与ZK服务器断开连接");
        } else if (Event.KeeperState.AuthFailed == keeperState) {
            System.out.println(Thread.currentThread().getName() + "权限检查失败");
        } else if (Event.KeeperState.Expired == keeperState) {
            System.out.println(Thread.currentThread().getName() + "会话失效");
        }
    }

    /**
     * 创建ZK连接
     *
     * @param connectString  ZK服务器地址列表
     * @param sessionTimeout Session超时时间
     */
    public void createConnection(String connectString, int sessionTimeout) throws IOException, InterruptedException {
        zk = new ZooKeeper(connectString, sessionTimeout, this);
        // connectSuccessLatch.await(1, TimeUnit.SECONDS) 正式实现的时候可以考虑此处是否采用超时阻塞
        connectSuccessLatch.await();
    }

    /**
     * 创建ZK节点
     *
     * @param path 节点path
     * @param data 初始数据内容
     */
    public boolean createPersistentPath(String path, String data, boolean needWatch) throws KeeperException, InterruptedException {
        if (zk.exists(path, needWatch) == null) {
            String result = zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(Thread.currentThread().getName() + "创建节点成功, path: " + result + ", content: " + data);
        }
        return true;
    }

    /**
     * 获取分布式锁
     */
    public void getLock() throws Exception {
        selfPath = zk.create(LOCK_SUB_PATH, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(Thread.currentThread().getName() + "创建锁路径:" + selfPath);
        if (checkMinPath()) {
            getLockSuccess();
        }
    }

    /**
     * 获取锁成功
     */
    private void getLockSuccess() throws KeeperException, InterruptedException {
        if (zk.exists(selfPath, false) == null) {
            System.err.println(Thread.currentThread().getName() + "本节点已不在了...");
            return;
        }
        System.out.println(Thread.currentThread().getName() + "获取锁成功，开始处理业务数据！");
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName() + "处理业务数据完成，删除本节点：" + selfPath);
        zk.delete(selfPath, -1);
        releaseConnection();
        threadCompleteLatch.countDown();
    }

    /**
     * 关闭ZK连接
     */
    private void releaseConnection() {
        if (zk != null) {
            try {
                zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "释放ZK连接");
    }

    /**
     * 检查自己是不是最小的节点
     */
    private boolean checkMinPath() throws Exception {
        List<String> subNodes = zk.getChildren(LOCK_ROOT_PATH, false);
        // 根据元素按字典序升序排序
        Collections.sort(subNodes);
        System.err.println(Thread.currentThread().getName() + "创建的临时节点名称:" + selfPath.substring(LOCK_ROOT_PATH.length() + 1));
        int index = subNodes.indexOf(selfPath.substring(LOCK_ROOT_PATH.length() + 1));
        System.err.println(Thread.currentThread().getName() + "创建的临时节点的index:" + index);
        switch (index) {
            case -1: {
                System.err.println(Thread.currentThread().getName() + "创建的节点已不在了..." + selfPath);
                return false;
            }
            case 0: {
                System.out.println(Thread.currentThread().getName() + "子节点中，我果然是老大" + selfPath);
                return true;
            }
            default: {
                // 获取比当前节点小的前置节点,此处只关注前置节点是否还在存在，避免惊群现象产生
                waitPath = LOCK_ROOT_PATH + "/" + subNodes.get(index - 1);
                System.out.println(Thread.currentThread().getName() + "获取子节点中，排在我前面的节点是:" + waitPath);
                try {
                    zk.getData(waitPath, true, new Stat());
                    return false;
                } catch (Exception e) {
                    if (zk.exists(waitPath, false) == null) {
                        System.out.println(Thread.currentThread().getName() + "子节点中，排在我前面的" + waitPath + "已失踪，该我了");
                        return checkMinPath();
                    } else {
                        throw e;
                    }
                }
            }

        }
    }
}