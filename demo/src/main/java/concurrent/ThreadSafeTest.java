package concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ThreadSafeTest {
    //http://blog.csdn.net/xiao__gui/article/details/8934832
    public static void main(String[] args) {
        // run test ten times
        for (int i = 0; i < 10; i++) {
            test();
        }
    }

    public static void test() {
        // List for test
//		List<Object> list = new ArrayList<>(); //non thread safe
        List<Object> list = new LinkedList<>();//non thread safe
//		List<Object> list = new Vector<>(); //thread safe
        int threadCount = 1000;
        // 用来让主线程等待threadCount个子线程执行完毕
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new MyThread(list, countDownLatch));
            thread.start();
        }
        try {
            // 主线程等待所有子线程执行完成，再向下执行
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // List的size
        System.out.println(list.size());
    }
}

class MyThread implements Runnable {
    private final List<Object> list;
    private final CountDownLatch countDownLatch;

    public MyThread(List<Object> list, CountDownLatch countDownLatch) {
        this.list = list;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        // 每个线程向List中添加100个元素
        for (int i = 0; i < 100; i++) {
            list.add(new Object());
        }
        // 完成一个子线程
        countDownLatch.countDown();
    }
}