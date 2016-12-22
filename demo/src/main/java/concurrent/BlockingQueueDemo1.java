package concurrent;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ExecutorService;

/**
 * Created by wajian on 2016/8/16.
 */
public class BlockingQueueDemo1 {
    //http://www.cnblogs.com/jackyuj/archive/2010/11/24/1886553.html
    public static void main(String[] args) throws InterruptedException {
        //size = 10
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10);

        MyProducer producer1 = new MyProducer(queue);
        MyProducer producer2 = new MyProducer(queue);
        MyProducer producer3 = new MyProducer(queue);
        MyConsumer consumer = new MyConsumer(queue);

        //with Executors to realize thread pool
        ExecutorService service = Executors.newCachedThreadPool();
        //start thread
        service.execute(producer1);
        service.execute(producer2);
        service.execute(producer3);
        service.execute(consumer);

        Thread.sleep(10 * 1000);
        producer1.stop();
        producer2.stop();
        producer3.stop();

        Thread.sleep(2000);
        //shutdown Executor
        service.shutdown();
    }
}

class MyConsumer implements Runnable {

    public MyConsumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        System.out.println("start consumer thread!");
        Random r = new Random();
        boolean isRunning = true;
        try {
            while (isRunning) {
                System.out.println("getting data from queue...");
                String data = queue.poll(2, TimeUnit.SECONDS);
                if (null != data) {
                    System.out.println("get data:" + data);
                    System.out.println("consuming data:" + data);
                    Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));
                } else {
                    // 超过2s还没数据，认为所有生产线程都已经退出，自动退出消费线程。
                    isRunning = false;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("quit consumer thread!");
        }
    }

    private BlockingQueue<String> queue;
    private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;
}

@SuppressWarnings("rawtypes")
class MyProducer implements Runnable {
    public MyProducer(BlockingQueue queue) {
        this.queue = queue;
    }

    @SuppressWarnings("unchecked")
	public void run() {
        String data;
        Random r = new Random();

        System.out.println("start producer thread!");
        try {
            while (isRunning) {
                System.out.println("producing data...");
                Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));

                data = "data:" + count.incrementAndGet();
                System.out.println("putting data:" + data + "into queue...");
                if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
                    System.out.println("putting data failure:" + data);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("quit producer thread!");
        }
    }

    public void stop() {
        isRunning = false;
    }

    private volatile boolean isRunning = true;
	private BlockingQueue queue;
    private static AtomicInteger count = new AtomicInteger();
    private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;

}