package thread;

import java.util.ArrayList;
import java.util.List;

public class TestRunnable implements Runnable {
    private String threadName;

    public TestRunnable(String threadName) {
        this.threadName = threadName;
    }


    @Override
    public void run() {
        System.out.println("[" + threadName + "] Running !");
    }

    public static void main(String[] args) throws InterruptedException {
        List<Thread> lists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new TestRunnable("子线程" + (i + 100)));
            lists.add(thread);
            thread.start();
        }
        System.out.println("主线程阻塞,等待所有子线程执行完成");
        for (Thread thread : lists) {
            //如果注释掉thread.join(),启动后 main线程 与 所有子线程 thread并发工作,并不会等待子线程完成后再执行
            thread.join();
        }
        System.out.println("所有线程执行完成!");
    }

}
