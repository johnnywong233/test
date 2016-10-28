package thread;

public class SimpleThread extends Thread{
	public void run() {
		for (int i=0;i<10;i++) {
			System.out.println("thread " + this.getId() + " print out " + i);
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				;
			}
		}
	}

}
