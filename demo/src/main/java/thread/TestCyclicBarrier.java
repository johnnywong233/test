package thread;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCyclicBarrier {

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Random random = new Random();

        final CyclicBarrier barrier = new CyclicBarrier(4, () -> System.out.println("everyone is here"));

        for (int i = 0; i < 4; i++) {
            exec.execute(() -> {
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "is here, where is the other");
                try {
                    barrier.await();//wait for others
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        exec.shutdown();
    }

}
