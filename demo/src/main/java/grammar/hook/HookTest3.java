package grammar.hook;

import java.util.concurrent.TimeUnit;

/**
 * Author: Johnny
 * Date: 2016/12/27
 * Time: 1:28
 */
public class HookTest3 {
    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Execute Hook.....")));
    }

    public static void main(String[] args) {
        new HookTest3().start();
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("thread is running....");
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

}
