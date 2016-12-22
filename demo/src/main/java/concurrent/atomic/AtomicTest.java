package concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {
    private int value;

    public AtomicTest(int value) {
        this.value = value;
    }

    // try remove the keyword synchronized
    public synchronized int increase() {
        //in java ++ and -- is not thread-safe, need synchronized to ensure the correctness of the result, which means time-consuming
        //AtomicInteger is thread safe, and perform better than synchronized
        //the overall time consumed is: synchronized > AtomicInteger > without synchronized(do not ensure the result's right)
        return value++;
    }

    //http://blog.csdn.net/sunnydogzhou/article/details/6564396
    public static void main(String args[]) {
        long start = System.currentTimeMillis();
        AtomicTest test = new AtomicTest(0);
        for (int i = 0; i < 1000000; i++) {
            test.increase();
        }
        long end = System.currentTimeMillis();
        System.out.println("synchronized time elapse:" + (end - start));

        long start1 = System.currentTimeMillis();
        AtomicInteger atomic = new AtomicInteger(0);
        for (int i = 0; i < 1000000; i++) {
            atomic.incrementAndGet();
        }
        long end1 = System.currentTimeMillis();
        System.out.println("AtomicInteger time elapse:" + (end1 - start1));
    }
}
