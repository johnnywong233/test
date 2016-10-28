package concurrent;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@SuppressWarnings("all")
public class FutureTaskTest {
	/*
	 * http://uule.iteye.com/blog/1539084
	 */
	
	public static void main(String[] args) {
		//create a futureTask instance to hold Callable instance
        Callable pAccount = new PrivateAccount();  
        FutureTask futureTask = new FutureTask(pAccount);  
        // use futureTask to create a thread
        Thread pAccountThread = new Thread(futureTask);  
        System.out.println("futureTask thread starting,start time:" + System.nanoTime());  
        
        pAccountThread.start();  
        System.out.println("the main thread starting");  
        int totalMoney = new Random().nextInt(100000);  
        System.out.println("your total money" + totalMoney);  
        System.out.println("waiting for private account to finish computing...");  
        // is not finished, then waiting
        while (!futureTask.isDone()) {
            try {
                Thread.sleep(500);  
                System.out.println("waiting for private account to finish computing...");  
            } catch (InterruptedException e) {
                e.printStackTrace();  
            }  
        }  
        System.out.println("futureTask thread compute finish, and the time is " + System.nanoTime());  
        Integer privateAccountMoney = null;  
        try {
            privateAccountMoney = (Integer) futureTask.get();  
        } catch (InterruptedException e) {
            e.printStackTrace();  
        } catch (ExecutionException e) {
            e.printStackTrace();  
        }  
        System.out.println("your total money" + totalMoney + privateAccountMoney.intValue());  
    }  
}  
  
@SuppressWarnings("all")  
class PrivateAccount implements Callable {
    Integer totalMoney;  
  
    public Object call() throws Exception {
        Thread.sleep(5000);  
        totalMoney = new Integer(new Random().nextInt(10000));  
        System.out.println("you currently have" + totalMoney + "in your private account");  
        return totalMoney;  
    }

}
