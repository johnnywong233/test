package concurrent;

import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch1 {
	private static final int N = 10;
	/*
	 * http://janeky.iteye.com/blog/769965  优化之后的代码
	 * 假设我们要打印1-100，最后再输出“Ok“。1-100的打印顺序不要求统一，只需保证“Ok“是在最后出现即可。
	 */
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch Signal = new CountDownLatch(N);
        System.out.println("begin------------");  
               
        for (int i = 1; i <= N; i++) {
            new Thread(new Worker(i, Signal)).start();//线程启动
        }
        
        Signal.await();//等待所有的线程执行完毕
        System.out.println("-------------Ok");
    }  
  
    static class Worker implements Runnable {
        private final CountDownLatch Signal;
        private int beginIndex;
  
        Worker(int beginIndex, CountDownLatch Signal) {
            this.beginIndex = beginIndex;
            this.Signal = Signal;
        }  
  
        public void run() {
            try {
                beginIndex = (beginIndex - 1) * 10 + 1;  
                for (int i = beginIndex; i <= beginIndex + 10; i++) {
                    System.out.println(i);  
                }  
            } finally {
            	Signal.countDown();
            }
        }  
    }

}
