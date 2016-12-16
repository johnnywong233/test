package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestSynchronized {
    /*
     * http://blog.csdn.net/liovey/article/details/7456096
     */
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch cdOrder = new CountDownLatch(1);
        final CountDownLatch cdAnswer = new CountDownLatch(3);

        final SynchonizedClass sc = new SynchonizedClass();
        for (int i = 0; i < 3; i++) {
            Runnable runnable = () -> {
                try {
                    cdOrder.await();
                    sc.start();
                    cdAnswer.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            service.execute(runnable);
        }
        try {
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println("thread" + Thread.currentThread().getName() + "has given execute order");
            cdOrder.countDown();
            long beginTime = System.currentTimeMillis();
            System.out.println("thread" + Thread.currentThread().getName() + "is waiting for finish");
            cdAnswer.await();
            System.out.println("thread" + Thread.currentThread().getName() + "has received all response. Time consumed:" + (System.currentTimeMillis() - beginTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();
    }
}


class SynchonizedClass {
    public void start() throws InterruptedException {
        Thread.sleep(100);//do something else
        synchronized (this) {
            System.out.println("sleep for 10 ms");
        }

        //compare difference of the synchronized
//		System.out.println("sleep for 10 ms");
    }
}
