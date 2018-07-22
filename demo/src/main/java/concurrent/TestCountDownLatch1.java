package concurrent;

import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch1 {
    private static final int N = 10;

    /**
     * http://janeky.iteye.com/blog/769965
     */
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch signal = new CountDownLatch(N);
        System.out.println("begin------------");

        for (int i = 1; i <= N; i++) {
            new Thread(new Worker(i, signal)).start();
        }

        signal.await();
        System.out.println("-------------Ok");
    }

    static class Worker implements Runnable {
        private final CountDownLatch signal;
        private int beginIndex;

        Worker(int beginIndex, CountDownLatch signal) {
            this.beginIndex = beginIndex;
            this.signal = signal;
        }

        @Override
        public void run() {
            try {
                beginIndex = (beginIndex - 1) * 10 + 1;
                for (int i = beginIndex; i <= beginIndex + 10; i++) {
                    System.out.println(i);
                }
            } finally {
                signal.countDown();
            }
        }
    }

}
