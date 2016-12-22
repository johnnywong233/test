package concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    public static void main(String[] args) {
        int N = 4;
        CyclicBarrier barrier = new CyclicBarrier(N, () -> {
            System.out.println("current " + Thread.currentThread().getName());
            //当四个线程都到达barrier状态后，会从四个线程中选择一个线程去执行Runnable。
        });

        for (int i = 0; i < N; i++)
            new Writer(barrier).start();
    }

    static class Writer extends Thread {
        private CyclicBarrier cyclicBarrier;

        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " is writing data...");
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " writing data over,waiting for other threads writing...");
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("All threads writing over, continue main thread...");
        }
    }

}
