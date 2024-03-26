package concurrent;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    //http://www.cnblogs.com/dolphin0520/p/3920397.html
    public static void main(String[] args) {
        //工人数
        int num = 8;
        //机器数
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < num; i++) {
            new Worker(i, semaphore).start();
        }
    }

    static class Worker extends Thread {
        private final int num;
        private final Semaphore semaphore;

        public Worker(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("工人" + this.num + "占用一个机器在生产...");
                Thread.sleep(2000);
                System.out.println("工人" + this.num + "释放出机器");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
