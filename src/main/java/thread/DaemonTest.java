package thread;

public class DaemonTest implements Runnable{
	
	public void run() {
//        while (true) {
            System.out.println(Thread.currentThread().getName() + "is running");
//        }
    }
 
	/*
	 * though there is endless loop, it seems OK. Cause the thread in endless loop is set as Daemon
	 */
    public static void main(String[] args) {
//    	DaemonTest he = new DaemonTest();
//        Thread demo = new Thread(he, "thread");
//        demo.setDaemon(true);
//        demo.start();
    	System.out.println("wtf");
    }

}
