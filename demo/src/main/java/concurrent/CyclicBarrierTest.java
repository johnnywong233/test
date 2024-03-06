package concurrent;

import lombok.AllArgsConstructor;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    public static void main(String[] args) {
        int num = 4;
        CyclicBarrier barrier = new CyclicBarrier(num, () -> System.out.println("current " + Thread.currentThread().getName()));

        for (int i = 0; i < num; i++) {
            new Writer(barrier).start();
        }
    }

    @AllArgsConstructor
    static class Writer extends Thread {
        private final CyclicBarrier cyclicBarrier;

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
