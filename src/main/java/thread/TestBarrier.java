package thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TestBarrier {
	/*
	 * http://www.tianshouzhi.com/api/tutorials/mutithread/113
	 * 
	 */
	public static void main(String[] args) {
        int N = 4;
        CyclicBarrier barrier  = new CyclicBarrier(N);
        for(int i=0;i<N;i++)
            new Writer(barrier).start();
        
    }
	
    static class Writer extends Thread{
        private CyclicBarrier cyclicBarrier;
        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }
 
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+" is writing data...");
            try {
                Thread.sleep(3000);//以睡眠来模拟写入数据操作
                System.out.println(Thread.currentThread().getName()+" writing data over,wating for other threads writing...");
                cyclicBarrier.await();
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }catch(BrokenBarrierException e){
                e.printStackTrace();
            }
            
            System.out.println("All threads writing over, continue main thread...");
            
        }
    }

}
