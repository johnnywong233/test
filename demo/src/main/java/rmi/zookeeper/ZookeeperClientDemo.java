package rmi.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnny on 2016/8/30.
 */
public class ZookeeperClientDemo implements Watcher {

    private ZooKeeper zk;

    //http://www.phpxs.com/code/1002103/
    public static void main(String[] args) {
        ZookeeperClientDemo zkc = new ZookeeperClientDemo();
        zkc.createZkClient();
        if (!zkc.exists("/windowcreate")) {
            zkc.createPersistentNode("/windowcreate", "windowcreate");
        }
        if (!zkc.exists("/windowcreate/value")) {
            System.out.println("not exists /windowcreate/value");
            zkc.createPersistentNode("/windowcreate/value", "A0431P001");
        }
        if (!zkc.exists("/windowcreate/valuetmp")) {
            System.out.println("not exists /windowcreate/valuetmp");
            zkc.createEphemeralNode("/windowcreate/valuetmp", "A0431P002");
        }
        System.out.println(zkc.getNodeData("/zookeeper"));
        System.out.println(zkc.getChildren("/windowcreate"));
        System.out.println(zkc.getNodeData("/windowcreate/value"));
        System.out.println(zkc.getNodeData("/windowcreate/valuetmp"));
        zkc.setNodeData("/windowcreate/value", "A0431P003");
        System.out.println(zkc.getNodeData("/windowcreate/value"));
        zkc.deleteNode("/windowcreate/value");
        System.out.println(zkc.exists("/windowcreate/value"));
        zkc.closeZk();
    }

    /**
     * 创建zookeeper客户端
     */
    private boolean createZkClient() {
        try {
            zk = new ZooKeeper(PropertiesDynLoading.CONNECT_STRING, PropertiesDynLoading.SESSION_TIMEOUT, this);
        } catch (IOException e) {
            this.log("{}", e);
            return false;
        }
        if (PropertiesDynLoading.AUTHENTICATION) {
            zk.addAuthInfo(PropertiesDynLoading.AUTH_SCHEME, PropertiesDynLoading.ACCESS_KEY.getBytes());
        }
        if (!isConnected()) {
            log(" ZooKeeper client state [{}]", zk.getState().toString());
        }
        try {
            if (zk.exists("/zookeeper", false) != null) {
                log("create ZooKeeper Client Success! connectString", PropertiesDynLoading.CONNECT_STRING);
                log(" ZooKeeper client state [{}]", zk.getState());
                return true;
            }
        } catch (Exception e) {
            this.log("create ZooKeeper Client Fail! connectString", PropertiesDynLoading.CONNECT_STRING);
        }
        return false;
    }

    /**
     * 新增持久化节点
     *
     * @param path 节点路径
     * @param data 节点数据
     */
    private boolean createPersistentNode(String path, String data) {
        if (isConnected()) {
            try {
                if (PropertiesDynLoading.AUTHENTICATION) {
                    zk.create(path, data.getBytes(), getAdminAcls(), CreateMode.PERSISTENT);
                } else {
                    zk.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
                return true;
            } catch (Exception e) {
                log("{}", e);
                return false;
            }
        }
        this.log("zookeeper state", zk.getState());
        return false;
    }

    /**
     * 创建瞬时节点
     *
     * @param path 节点路径
     * @param data 节点数据
     */
    private boolean createEphemeralNode(String path, String data) {
        if (isConnected()) {
            try {
                if (PropertiesDynLoading.AUTHENTICATION) {
                    zk.create(path, data.getBytes(), getAdminAcls(), CreateMode.PERSISTENT);
                } else {
                    zk.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                }
                return true;
            } catch (Exception e) {
                log("{}", e);
                return false;
            }
        }
        this.log("zookeeper state", zk.getState());
        return false;
    }

    /**
     * 修改数据
     *
     * @param path 节点路径
     * @param data 节点数据
     */
    private boolean setNodeData(String path, String data) {
        if (isConnected()) {
            try {
                zk.setData(path, data.getBytes(), -1);
                return true;
            } catch (Exception e) {
                this.log("{}", e);
                return false;
            }
        }
        this.log("zookeeper state = [{}]", zk.getState());
        return false;
    }

    /**
     * 删除节点
     *
     * @param path 节点路径
     */
    private boolean deleteNode(String path) {
        if (isConnected()) {
            try {
                zk.delete(path, -1);
                return true;
            } catch (Exception e) {
                this.log("{}", e);
                return false;
            }
        }
        this.log("zookeeper state = [{}]", zk.getState());
        return false;
    }

    /**
     * 获取节点值
     *
     * @param path 节点路径
     */
    private String getNodeData(String path) {
        if (isConnected()) {
            String data;
            try {
                byte[] byteData = zk.getData(path, true, null);
                data = new String(byteData, StandardCharsets.UTF_8);
                return data;
            } catch (Exception e) {
                this.log("{}", e);
                return null;
            }
        }
        this.log("zookeeper state = [{}]", zk.getState());
        return null;
    }

    /**
     * 获取path子节点名列表
     *
     * @param path 节点路径
     */
    private List<String> getChildren(String path) {
        if (isConnected()) {
            try {
                return zk.getChildren(path, false);
            } catch (Exception e) {
                this.log("{}", e);
                return null;
            }
        }
        this.log("zookeeper state = [{}]", zk.getState());
        return null;
    }

    public boolean startZkClient() {
        return createZkClient();
    }

    /**
     * zookeeper是否连接服务器
     */
    private boolean isConnected() {
        return zk.getState().isConnected();
    }

    /**
     * 是否存在path路径节点
     */
    public boolean exists(String path) {
        try {
            return zk.exists(path, false) != null;
        } catch (Exception e) {
            this.log("{}", e);
        }
        return false;
    }

    /**
     * 关闭zookeeper
     */
    private void closeZk() {
        if (isConnected()) {
            try {
                zk.close();
                this.log("close zookeeper [{}]", "success");
            } catch (InterruptedException e) {
                this.log("zookeeper state = [{}]", e);
            }
        } else {
            this.log("zookeeper state = [{}]", zk.getState());
        }
    }

    public List<ACL> getCreateNodeAcls() {
        List<ACL> listAcls = new ArrayList<>(3);
        try {
            Id id = new Id(PropertiesDynLoading.AUTH_SCHEME,
                    DigestAuthenticationProvider.generateDigest(PropertiesDynLoading.ACCESS_KEY));
            ACL acl = new ACL(Perms.CREATE, id);
            listAcls.add(acl);
        } catch (NoSuchAlgorithmException e) {
            return Ids.OPEN_ACL_UNSAFE;
        }
        return listAcls;
    }

    private List<ACL> getAdminAcls() {
        List<ACL> listAcls = new ArrayList<>(3);
        try {
            Id id = new Id(PropertiesDynLoading.AUTH_SCHEME,
                    DigestAuthenticationProvider.generateDigest(PropertiesDynLoading.ACCESS_KEY));
            ACL acl = new ACL(Perms.ALL, id);
            listAcls.add(acl);
        } catch (NoSuchAlgorithmException e) {
            return Ids.OPEN_ACL_UNSAFE;
        }
        return listAcls;
    }

    public void log(String format, Object args) {
        int index = format.indexOf("{");
        StringBuilder sb = new StringBuilder(format);
        sb.insert(index + 1, "%s");
        System.out.printf(sb + "%n", args);
    }

    @Override
    public void process(WatchedEvent event) {
    }

    private static class PropertiesDynLoading {
        static final String CONNECT_STRING = "localhost:9181";
        static final int SESSION_TIMEOUT = 3000;
        static final String AUTH_SCHEME = "digest";
        static final String ACCESS_KEY = "cache:svcctlg";
        static final boolean AUTHENTICATION = false;
    }
}
