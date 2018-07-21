package rmi.zookeeper.demo1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: Johnny
 * Date: 2016/12/4
 * Time: 22:45
 */
public class ConcurrentTest {
    private CountDownLatch startSignal = new CountDownLatch(1);
    private CountDownLatch doneSignal = null;
    private CopyOnWriteArrayList<Long> list = new CopyOnWriteArrayList<>();
    private AtomicInteger err = new AtomicInteger();//atomic increase
    private ConcurrentTask[] task = null;

    public ConcurrentTest(ConcurrentTask... task) {
        this.task = task;
        if (task == null) {
            System.out.println("task can not null");
            System.exit(1);
        }
        doneSignal = new CountDownLatch(task.length);
        start();
    }

    private void start() {
        //创建线程，并将所有线程等待在阀门处
        createThread();
        //打开阀门
        startSignal.countDown();//递减锁存器的计数，如果计数到达零，则释放所有等待的线程
        try {
            doneSignal.await();//等待所有线程都执行完毕
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //计算执行时间
        getExeTime();
    }

    /**
     * 初始化所有线程，并在阀门处等待
     */
    private void createThread() {
        long len = doneSignal.getCount();
        for (int i = 0; i < len; i++) {
            final int j = i;
            new Thread(() -> {
                try {
                    startSignal.await();//使当前线程在锁存器倒计数至零之前一直等待
                    long start = System.currentTimeMillis();
                    task[j].run();
                    long end = (System.currentTimeMillis() - start);
                    list.add(end);
                } catch (Exception e) {
                    err.getAndIncrement();//相当于err++
                }
                doneSignal.countDown();
            }).start();
        }
    }

    /**
     * 计算平均响应时间
     */
    private void getExeTime() {
        int size = list.size();
        List<Long> newList = new ArrayList<>(size);
        newList.addAll(list);
        Collections.sort(newList);
        long min = newList.get(0);
        long max = newList.get(size - 1);
        long sum = 0L;
        for (Long t : newList) {
            sum += t;
        }
        long avg = sum / size;
        System.out.println("min: " + min);
        System.out.println("max: " + max);
        System.out.println("avg: " + avg);
        System.out.println("err: " + err.get());
    }

    interface ConcurrentTask {
        void run();
    }
}
