package thread;

public class JoinTest {
	/*
	 * http://ifeve.com/talk-concurrency-countdownlatch/
	 */
	public static void main(String[] args) throws InterruptedException {
		//CountDownLatch in jdk1.5 or later has more function than join.
		Thread parser1 = new Thread(new Runnable() {
			public void run() {
				System.out.println("parser1 finish");
			}
		});

		Thread parser2 = new Thread(new Runnable() {
			public void run() {
				System.out.println("parser2 finish");
			}
		});

		parser1.start();
		parser2.start();
		parser1.join();//join用于让当前执行线程等待join线程执行结束
		parser2.join();
		System.out.println("all parser finish");
		
		/*
		 * join实现原理是不停检查join线程是否存活，如果join线程存活则让当前线程永远wait
		 * while (isAlive()) {
 			wait(0);//wait for ever
			}
		 * 直到join线程中止后，线程的this.notifyAll会被调用，调用notifyAll是在JVM里实现的，所以JDK里看不到，有兴趣的同学可以看看 JVM源码。
		 * JDK不推荐在线程实例上使用wait，notify和notifyAll方法。
		 * 
		 * 
		 */
	}
}