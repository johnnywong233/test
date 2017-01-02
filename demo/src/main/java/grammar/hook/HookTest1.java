package grammar.hook;

import java.util.concurrent.TimeUnit;

/**
 * Author: Johnny
 * Date: 2016/12/27
 * Time: 1:25
 */
public class HookTest1 {
    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Execute Hook.....")));
    }

    public static void main(String[] args) {
        new HookTest1().start();
        System.out.println("The Application is doing something");
        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
