package rmi.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ServiceProvider {
    // 用于等待 SyncConnected 事件触发后继续执行当前线程
    private final CountDownLatch latch = new CountDownLatch(1);

    // 发布 RMI 服务并注册 RMI 地址到 ZooKeeper 中
    void publish(Remote remote, String host, int port) {
        String url = publishService(remote, host, port); // 发布 RMI 服务并返回 RMI 地址
        if (url != null) {
            ZooKeeper zk = connectServer(); // 连接 ZooKeeper 服务器并获取 ZooKeeper 对象
            if (zk != null) {
                createNode(zk, url); // 创建 ZNode 并将 RMI 地址放入 ZNode 上
            }
        }
    }

    // 发布 RMI 服务
    private String publishService(Remote remote, String host, int port) {
        String url = null;
        try {
            url = String.format("rmi://%s:%d/%s", host, port, remote.getClass().getName());
            LocateRegistry.createRegistry(port);
            Naming.rebind(url, remote);
            log.info("publish rmi service (url: {})", url);
        } catch (RemoteException | MalformedURLException e) {
            log.error("", e);
        }
        return url;
    }

    // 连接 ZooKeeper 服务器
    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(Constant.ZK_CONNECTION_STRING, Constant.ZK_SESSION_TIMEOUT, event -> {
                if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    latch.countDown(); // 唤醒当前正在执行的线程
                }
            });
            latch.await(); // 使当前线程处于等待状态
        } catch (IOException | InterruptedException e) {
            log.error("", e);
        }
        return zk;
    }

    // 创建 ZNode
    private void createNode(ZooKeeper zk, String url) {
        try {
            byte[] data = url.getBytes();
            String path = zk.create(Constant.ZK_PROVIDER_PATH, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);    // 创建一个临时性且有序的 ZNode
            log.info("create zookeeper node ({} => {})", path, url);
        } catch (KeeperException | InterruptedException e) {
            log.error("", e);
        }
    }


}
