package concurrent;

import java.util.concurrent.CountDownLatch;

public class ThreadSafeTest1 {
    //http://blog.csdn.net/xiao__gui/article/details/8934832
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            test();
        }
    }

    public static void test() {
        Counter counter = new Counter();
        int threadCount = 1000;
        // 用来让主线程等待threadCount个子线程执行完毕
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new MyThread1(counter, countDownLatch));
            thread.start();
        }
        try {
            // 主线程等待所有子线程执行完成，再向下执行
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(counter.getCount());
    }
}

class MyThread1 implements Runnable {
    private final Counter counter;
    private final CountDownLatch countDownLatch;

    public MyThread1(Counter counter, CountDownLatch countDownLatch) {
        this.counter = counter;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        // 每个线程向Counter中进行10000次累加
        for (int i = 0; i < 10000; i++) {
            counter.addCount();
        }
        // 完成一个子线程
        countDownLatch.countDown();
    }
}

class Counter {
    private int count = 0;

    public int getCount() {
        return count;
    }

    // see the difference with/without synchronized
    public synchronized void addCount() {
        count++;
    }
}