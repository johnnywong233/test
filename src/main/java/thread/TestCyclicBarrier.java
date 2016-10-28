package thread;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCyclicBarrier {
	
	 public static void main(String[] args) {
	      
	        ExecutorService exec = Executors.newCachedThreadPool();       
	        final Random random=new Random();  
	          
	        final CyclicBarrier barrier=new CyclicBarrier(4,new Runnable(){
	            public void run() {
	                System.out.println("大家都到齐了，开始happy去");  
	            }});
	          
	        for(int i=0;i<4;i++){
	            exec.execute(new Runnable(){
	                public void run() {
	                    try {
	                        Thread.sleep(random.nextInt(1000));  
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();  
	                    }  
	                    System.out.println(Thread.currentThread().getName()+"到了，其他哥们呢");  
	                    try {
	                        barrier.await();//等待其他哥们  
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();  
	                    } catch (BrokenBarrierException e) {
	                        e.printStackTrace();  
	                    }  
	                }});  
	        }  
	        exec.shutdown();  
	    }  

}
