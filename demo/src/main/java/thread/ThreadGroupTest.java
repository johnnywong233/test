package thread;

import java.util.Random;

public class ThreadGroupTest extends ThreadGroup {
    public ThreadGroupTest(String name) {
        super(name);
    }

	/*
	 * http://ifeve.com/thread-management-12
	 * @see java.lang.ThreadGroup#uncaughtException(java.lang.Thread, java.lang.Throwable)
	 */

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.printf("The thread %s has thrown an Exception\n", t.getId());
        e.printStackTrace(System.out);
        System.out.printf("Terminating the rest of the Threads\n");
        interrupt();
    }

    public class Task implements Runnable {
        @Override
        public void run() {
            int result;
            Random random = new Random(Thread.currentThread().getId());
            while (true) {
                result = 1000 / ((int) (random.nextDouble() * 1000));
                System.out.printf("%s : f\n", Thread.currentThread().getId(), result);
                if (Thread.currentThread().isInterrupted()) {
                    System.out.printf("%d : Interrupted\n", Thread.currentThread().getId());
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadGroupTest test = new ThreadGroupTest("ThreadGroupTest");

//		Task task=new Task();
//		for (int i=0; i<2; i++){
//			Thread t=new Thread(threadGroup,task);
//			t.start();
//			}
    }


}
