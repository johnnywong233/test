package concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {
    //http://www.cnblogs.com/liuling/p/2013-8-20-01.html
    public static void main(String[] args) {
        final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep((long) (Math.random() * 1000));
                        System.out.println(Thread.currentThread().getName() + " is ready to store data!");
                        queue.put(1);
                        System.out.println(Thread.currentThread().getName() + "has got data, " +
                                "the queue now has " + queue.size() + " data");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        new Thread(() -> {
            while (true) {
                try {
                    //change the sleep time to see execute result
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "is ready to get data!");
                    queue.take();
                    System.out.println(Thread.currentThread().getName() + "has got data, " +
                            "the queue now has" + queue.size() + "data");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
