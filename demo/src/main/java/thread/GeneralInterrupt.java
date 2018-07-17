package thread;

public class GeneralInterrupt implements Runnable {
    /*
     * source code see http://outofmemory.cn/java/java.util.concurrent/multi-threading
	 *  use interrupt to stop thread
	 */
    @Override
    public void run() {
        try {
            System.out.println("in run() - get in work2()");
            work2();
            System.out.println("in run() - get away from work2()");
            System.out.println("in run() - get in work()");
            work();
            System.out.println("in run() - get away from work()");
        } catch (InterruptedException x) {
            System.out.println("in run() -  interrupted in work2()");
            return;
        }
        System.out.println("in run() - doing stuff after nap");
        System.out.println("in run() - leaving normally");
    }


    private void work2() throws InterruptedException {
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("C isInterrupted()=" + Thread.currentThread().isInterrupted());
                Thread.sleep(2000);
                System.out.println("D isInterrupted()=" + Thread.currentThread().isInterrupted());
            }
        }
    }

    public void work() throws InterruptedException {
        while (true) {
            for (int i = 0; i < 100000; i++) {
                i = i * 2;
            }
            System.out.println("A isInterrupted()=" + Thread.currentThread().isInterrupted());
            if (Thread.interrupted()) {
                System.out.println("B isInterrupted()=" + Thread.currentThread().isInterrupted());
                throw new InterruptedException();
            }
        }
    }


    public static void main(String[] args) {
        GeneralInterrupt si = new GeneralInterrupt();
        Thread t = new Thread(si);
        t.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException x) {
            x.printStackTrace();
        }
        System.out.println("in main() - interrupting other thread");
        t.interrupt();
        System.out.println("in main() - leaving");
    }

}
