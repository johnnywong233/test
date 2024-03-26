package thread;

import java.util.concurrent.atomic.AtomicIntegerArray;

/*
 * http://ifeve.com/concurrent-collections-9/
 * 即使只有一个线程在访问共享对象，它也要执行必要的获取锁和释放锁的代码。
 */
public class AtomicIntegerArrayTest {
    public static void main(String[] args) {
        final int threads = 100;
        AtomicIntegerArray vector = new AtomicIntegerArray(1000);

        Incrementer incrementer = new Incrementer(vector);
        Decrementer decrementer = new Decrementer(vector);

        Thread[] threadIncrementer = new Thread[threads];
        Thread[] threadDecrementer = new Thread[threads];

        for (int i = 0; i < threads; i++) {
            threadIncrementer[i] = new Thread(incrementer);
            threadDecrementer[i] = new Thread(decrementer);

            threadIncrementer[i].start();
            threadDecrementer[i].start();
        }
        for (int i = 0; i < 100; i++) {
            try {
                threadIncrementer[i].join();
                threadDecrementer[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < vector.length(); i++) {
            if (vector.get(i) != 0) {
                System.out.println("Vector[" + i + "] : " + vector.get(i));
            }
        }

        //if no read-write error occur, which means each element of array is 0, then just output this line
        System.out.println("Main: End of the example");
    }
}


class Incrementer implements Runnable {
    private final AtomicIntegerArray vector;

    public Incrementer(AtomicIntegerArray vector) {
        this.vector = vector;
    }

    @Override
    public void run() {
        for (int i = 0; i < vector.length(); i++) {
            vector.getAndIncrement(i);
        }
    }
}

class Decrementer implements Runnable {

    private final AtomicIntegerArray vector;

    public Decrementer(AtomicIntegerArray vector) {
        this.vector = vector;
    }

    @Override
    public void run() {
        for (int i = 0; i < vector.length(); i++) {
            vector.getAndDecrement(i);
        }
    }
}

