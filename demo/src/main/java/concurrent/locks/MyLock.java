package concurrent.locks;

import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by johnny on 2016/8/18.
 */
public class MyLock extends ReentrantLock {
    private static final long serialVersionUID = -1302150672127716753L;

    //http://ifeve.com/testing-concurrent-applications-2/
    public static void main(String[] args) throws Exception {
        MyLock lock = new MyLock();

        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            Task task = new Task(lock);
            threads[i] = new Thread(task);
            threads[i].start();
        }

        for (int i = 0; i < 15; i++) {
            System.out.print("Main: Logging the Lock\n");
            System.out.printf("Lock Owner : %s\n", lock.getOwnerName());
            //print the number and name of QueuedThreads
            System.out.printf("Lock: Queued Threads: %s\n", lock.hasQueuedThreads());
            if (lock.hasQueuedThreads()) {
                System.out.printf("Lock: Queue Length: %d\n", lock.getQueueLength());
                System.out.print("Lock: Queued Threads: ");
                Collection<Thread> lockedThreads = lock.getThreads();
                for (Thread lockedThread : lockedThreads) {
                    System.out.printf("%s ", lockedThread.getName());
                }
                System.out.print("\n");
            }
            System.out.printf("Lock: Fairness: %s\n", lock.isFair());
            System.out.printf("Lock: Locked: %s\n", lock.isLocked());
            //合上类的循环。
            TimeUnit.SECONDS.sleep(1);
        }
    }

    //此方法使用Lock类的保护方法 getOwner()， 返回控制锁的线程（如果存在）的名字。
    public String getOwnerName() {
        if (this.getOwner() == null) {
            return "None";
        }
        return this.getOwner().getName();
    }

    //使用Lock类的保护方法 getQueuedThreads()，返回在锁里的线程的 queued list。
    public Collection<Thread> getThreads() {
        return this.getQueuedThreads();
    }
}

@AllArgsConstructor
class Task implements Runnable {
    private final Lock lock;

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            //get the lock with lock() method
            lock.lock();
            System.out.printf("%s: Get the Lock.\n", Thread.currentThread().getName());

            //release the lock with unlock()
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.printf("%s: Free the Lock.\n", Thread
                        .currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}

