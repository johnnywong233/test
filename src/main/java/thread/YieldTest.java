package thread;

public class YieldTest implements Runnable {
    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        for (int i = 0; i < 5; ++i) {
            System.out.println(Thread.currentThread().getName() + "运行" + i);
            if (i == 3) {
                System.out.println("线程的礼让");
                Thread.currentThread();
                Thread.yield();
//				Thread.currentThread().yield(); //上面两行的等价
            }
        }
    }

    /*
     * 在线程操作中，也可以使用yield（）方法，将一个线程的操作暂时交给其他线程执行。
     *
     */
    public static void main(String[] args) {
        Thread h1 = new Thread(new YieldTest(), "A");
        Thread h2 = new Thread(new YieldTest(), "B");
        h1.start();
        h2.start();

    }

}
