package simpletest;

import java.util.concurrent.PriorityBlockingQueue;

public class Main {
    //TODO
    public static void main(String[] args) {
//		PriorityBlockingQueue<Event> queue=new PriorityBlockingQueue<>();
        PriorityBlockingQueue<?> queue = new PriorityBlockingQueue<>();

        Thread taskThreads[] = new Thread[5];

        for (int i = 0; i < taskThreads.length; i++) {
            EventTest task = new EventTest(i, queue);

            taskThreads[i] = new Thread(task);
        }

        for (Thread taskThread1 : taskThreads) {
            taskThread1.start();
        }

        for (Thread taskThread : taskThreads) {
            try {
                taskThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("Main: Queue Size: %d\n", queue.size());
        for (int i = 0; i < taskThreads.length * 1000; i++) {
            EventTest event = queue.poll();
            System.out.printf("Thread %s: Priority %d\n", event.getThread(), event.getPriority());
        }
        System.out.printf("Main: Queue Size: %d\n", queue.size());
        System.out.printf("Main: End of the program\n");


    }


}
