package thread;

public class SynchronizedTest1 implements Runnable{
	
	public void run() {
        for (int i = 0; i < 10; ++i) {
            sale();
        }
    }

	//同步方法
		public synchronized void sale() {
	        if (count > 0) {
	            try {
	                Thread.sleep(1000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            System.out.println(count--);
	        }
	    }
		
		public static void main(String[] args) {
			SynchronizedTest1 he = new SynchronizedTest1();
	        Thread h1 = new Thread(he);
	        Thread h2 = new Thread(he);
	        Thread h3 = new Thread(he);
	        h1.start();
	        h2.start();
	        h3.start();
	    }
	 
	    private int count = 5;

}
