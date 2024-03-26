package zk;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Johnny on 2017/12/31.
 */
public class MyDistributedLockByZK {
    /**
     * 线程池
     **/
    private static ExecutorService executorService = null;
    private static final int THREAD_NUM = 5;
    private static int threadNo = 0;
    private static final CountDownLatch threadCompleteLatch = new CountDownLatch(THREAD_NUM);

    /**
     * ZK的相关配置常量
     **/
    private static final String CONNECTION_STRING = "192.0.0.1:2181";
    private static final int SESSION_TIMEOUT = 10000;
    // 此变量在LockWatcher中也有一个同名的静态变量，正式使用的时候，提取到常量类中共同维护即可。
    private static final String LOCK_ROOT_PATH = "/myDisLocks";

    public static void main(String[] args) {
        // 定义线程池
        executorService = Executors.newFixedThreadPool(THREAD_NUM, r -> {
            String name = String.format("第[%s]个测试线程", ++threadNo);
            Thread ret = new Thread(Thread.currentThread().getThreadGroup(), r, name, 0);
            ret.setDaemon(false);
            return ret;
        });

        // 启动线程
        if (executorService != null) {
            startProcess();
        }
    }

    /**
     *  模拟并发执行任务
     */
    private static void startProcess() {
        Runnable disposeBusinessRunnable = new Thread(() -> {
            String threadName = Thread.currentThread().getName();

            LockWatcher lock = new LockWatcher(threadCompleteLatch);
            try {
                /* 步骤1: 当前线程创建ZK连接 */
                lock.createConnection(CONNECTION_STRING, SESSION_TIMEOUT);

                /* 步骤2: 创建锁的根节点  */
                // 注意，此处创建根节点的方式其实完全可以在初始化的时候由主线程单独进行根节点的创建，没有必要在业务线程中创建。
                // 这里这样写只是一种思路而已，不必局限于此
                synchronized (MyDistributedLockByZK.class) {
                    lock.createPersistentPath(LOCK_ROOT_PATH, "该节点由" + threadName + "创建", true);
                }

                /* 步骤3: 开启锁竞争并执行任务 */
                lock.getLock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        for (int i = 0; i < THREAD_NUM; i++) {
            executorService.execute(disposeBusinessRunnable);
        }
        executorService.shutdown();

        try {
            threadCompleteLatch.await();
            System.out.println("所有线程运行结束!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}