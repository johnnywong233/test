package grammar.hook;

import java.util.concurrent.TimeUnit;

/**
 * Author: Johnny
 * Date: 2016/12/27
 * Time: 1:26
 */
public class HookTest2 {
    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Execute Hook.....")));
    }

    //make sure java -x rags: -Xmx20M to produce OOM Exception
    public static void main(String[] args) {
        new HookTest2().start();
        System.out.println("The Application is doing something");
        byte[] b = new byte[500 * 1024 * 1024];
        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
