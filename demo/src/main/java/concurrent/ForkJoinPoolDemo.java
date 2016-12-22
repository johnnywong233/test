package concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * Created by wajian on 2016/8/18.
 */
public class ForkJoinPoolDemo {
    //http://ifeve.com/testing-concurrent-applications-5-2/
    public static void main(String[] args) throws Exception {

        ForkJoinPool pool = new ForkJoinPool();
        int array[] = new int[10];
        NewTask task1 = new NewTask(array, 0, array.length);

        // 11. 使用 execute() 方法 把任务发送到pool里执行。
        pool.execute(task1);
        // 12. 当任务还未结束执行，调用 showLog() 方法来把 ForkJoinPool 类的状态信息写入，然后让线程休眠一秒。
        while (!task1.isDone()) {
            showLog(pool);
            TimeUnit.SECONDS.sleep(1);
        }
        pool.shutdown();
        //use awaitTermination() method to wait for end of pool
        pool.awaitTermination(1, TimeUnit.DAYS);

        // 15. 调用 showLog() 方法写关于 ForkJoinPool 类状态的信息并写一条信息到操控台表明结束程序。
        showLog(pool);
        System.out.printf("Main: End of the program.\n");
    }

    // 16. 实现 showLog() 方法。它接收 ForkJoinPool 对象作为参数和写关于线程和任务的执行的状态的信息。
    private static void showLog(ForkJoinPool pool) {
        System.out.printf("**********************\n");
        System.out.printf("Main: Fork/Join Pool log\n");
        System.out.printf("Main: Fork/Join Pool: Parallelism:%d\n",
                pool.getParallelism());
        System.out.printf("Main: Fork/Join Pool: Pool Size:%d\n",
                pool.getPoolSize());
        System.out.printf("Main: Fork/Join Pool: Active Thread Count:%d\n",
                pool.getActiveThreadCount());
        System.out.printf("Main: Fork/Join Pool: Running Thread Count:%d\n",
                pool.getRunningThreadCount());
        System.out.printf("Main: Fork/Join Pool: Queued Submission:%d\n",
                pool.getQueuedSubmissionCount());
        System.out.printf("Main: Fork/Join Pool: Queued Tasks:%d\n",
                pool.getQueuedTaskCount());
        System.out.printf("Main: Fork/Join Pool: Queued Submissions:%s\n",
                pool.hasQueuedSubmissions());
        System.out.printf("Main: Fork/Join Pool: Steal Count:%d\n",
                pool.getStealCount());
        System.out.printf("Main: Fork/Join Pool: Terminated :%s\n",
                pool.isTerminated());
        System.out.printf("**********************\n");
    }
}

class NewTask extends RecursiveAction {

    private static final long serialVersionUID = 1L;

    // 2. 声明一个私有 int array 属性，名为 array，用来储存你要增加的 array 的元素。
    private int[] array;

    // 3. 声明2个私有 int 属性，名为 start 和 end，用来储存 此任务已经处理的元素块的头和尾的位置。
    private int start;
    private int end;

    // 4. 实现类的构造函数，初始化属性值。
    public NewTask(int array[], int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    // 5. 用任务的中心逻辑来实现 compute()
    // 方法。如果此任务已经处理了超过100任务，那么把元素集分成2部分，再创建2个任务分别来执行这些部分，使用 fork() 方法开始执行，并使用
    // join() 方法等待它的终结。
    protected void compute() {
        if (end - start > 100) {
            int mid = (start + end) / 2;
            NewTask task1 = new NewTask(array, start, mid);
            NewTask task2 = new NewTask(array, mid, end);

            task1.fork();
            task2.fork();

            task1.join();
            task2.join();

            // 6. 如果任务已经处理了100个元素或者更少，那么在每次操作之后让线程进入休眠5毫秒来增加元素。
        } else {
            for (int i = start; i < end; i++) {
                array[i]++;
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}