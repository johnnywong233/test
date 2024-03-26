package concurrent;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerTest {
    private static final Exchanger<String> EXGR = new Exchanger<>();
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(() -> {
            try {
                String a = "bank pipeline A\n";//pipeline A get data of the bank
                EXGR.exchange(a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(() -> {
            try {
                String b = "bank pipeline B\n";//pipeline A get data of the bank
                String a = EXGR.exchange("B");
                System.out.println("the data of pipeline A and B equals?\n" + a.equals(b) + "\ndata from pipeline A:"
                        + a + "data from pipeline B:" + b);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadPool.shutdown();
    }
}
