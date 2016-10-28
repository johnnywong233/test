package thread;

public class ThreadTest {

	public static void main(String[] args) {
		for (int i = 0; i < 2; i++) {
			Thread t = new SimpleThread();
			t.start();
		}
	}

}
