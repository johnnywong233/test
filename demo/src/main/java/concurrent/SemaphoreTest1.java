package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest1 {


	private static final int THREAD_COUNT = 15;

	private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);

	private static Semaphore s = new Semaphore(10);

	public static void main(String[] args) {
		for (int i = 0; i < THREAD_COUNT; i++) {
			threadPool.execute(new Runnable() {
				public void run() {
					try {
						s.acquire();
						System.out.println("save data");
						s.release();
					} catch (InterruptedException e) {
					}
				}
			});
		}
		threadPool.shutdown();
	}

}
