package rmi.zookeeper.demo1;

/**
 * Author: Johnny
 * Date: 2016/12/4
 * Time: 22:46
 */
public class ZkTest {
    //http://www.jb51.net/article/49825.htm
    public static void main(String[] args) {
        Runnable task1 = () -> {
            DistributedLock lock = null;
            try {
                lock = new DistributedLock("127.0.0.1:2182","test1");
                //lock = new DistributedLock("127.0.0.1:2182","test2");
                lock.lock();
                Thread.sleep(3000);
                System.out.println("===Thread " + Thread.currentThread().getId() + " running");
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (lock != null) {
                    lock.unlock();
                }
            }
        };
        new Thread(task1).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        ConcurrentTest.ConcurrentTask[] tasks = new ConcurrentTest.ConcurrentTask[60];
        for(int i=0;i<tasks.length;i++){
            ConcurrentTest.ConcurrentTask task3 = () -> {
                DistributedLock lock = null;
                try {
                    lock = new DistributedLock("127.0.0.1:2183","test2");
                    lock.lock();
                    System.out.println("Thread " + Thread.currentThread().getId() + " running");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    if (lock != null) {
                        lock.unlock();
                    }
                }
            };
            tasks[i] = task3;
        }
        new ConcurrentTest(tasks);
    }

}
