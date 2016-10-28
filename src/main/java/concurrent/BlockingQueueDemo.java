package concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by wajian on 2016/8/16.
 */
public class BlockingQueueDemo {
	//http://tonl.iteye.com/blog/1936391
    public static void main(String[] args) {
    	//if the size of queue is set as 2, then at the same time, 
    	//队列的大小限定成了2，所以最多只有两个产品被加入到队列当中，而且消费者取到产品的顺序也是按照生产的先后顺序，
    	//原因就是LinkedBlockingQueue和ArrayBlockingQueue都是按照FIFO的顺序存取元素的。
//        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(2);
         BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
        //if not set, then default as Integer.MAX_VALUE

//         BlockingQueue<String> queue = new ArrayBlockingQueue<String>(2);

        Consumer consumer = new Consumer(queue);
        Producer producer = new Producer(queue);
        for (int i = 0; i < 5; i++) {
            new Thread(producer, "Producer" + (i + 1)).start();

            new Thread(consumer, "Consumer" + (i + 1)).start();
        }
    }
}

class Consumer implements Runnable{
    BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
        	//if the queue is empty, then the current thread will be blocked
            String temp = queue.take();
            System.out.println(temp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Producer implements Runnable{
    BlockingQueue<String> queue;

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            String temp = "A Product, the producer thread: " + Thread.currentThread().getName();
            System.out.println("I have made a product:" + Thread.currentThread().getName());
            //if the queue is full, then the current thread will be blocked
            queue.put(temp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
