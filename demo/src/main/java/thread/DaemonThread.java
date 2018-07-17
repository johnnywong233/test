package thread;

public class DaemonThread implements Runnable {

    /*
     * demo of Daemon thread
     * source code see http://outofmemory.cn/java/java.util.concurrent/multi-threading
     */
    @Override
    public void run() {
        System.out.println("entering run()");
        try {
            System.out.println("in run(): currentThread() is " + Thread.currentThread());
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException x) {
                    x.printStackTrace();
                }
                System.out.println("in run(): woke up again");
            }
        } finally {
            System.out.println("leaving run()");
        }
    }

    public static void main(String[] args) {
        System.out.println("entering main()");
        Thread t = new Thread(new DaemonThread());
        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException x) {
            x.printStackTrace();
        }
        System.out.println("leaving main()");
    }

}
