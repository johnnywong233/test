package concurrent;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;
import org.junit.Assert;

import java.util.concurrent.LinkedTransferQueue;

/**
 * Created by johnny on 2016/8/18.
 * <a href="http://www.cs.umd.edu/projects/PL/multithreadedtc/overview.html">...</a>
 */

public class ProducerConsumerTest extends MultithreadedTestCase {
    //http://ifeve.com/testing-concurrent-applications-10/
    public static void main(String[] args) throws Throwable {

        ProducerConsumer test = new ProducerConsumer();
        //do test with TestFramework.runOnce
        System.out.print("Main: Starting the test\n");
        TestFramework.runOnce(test);
        System.out.print("Main: The test has finished\n");
    }
}


class ProducerConsumer extends MultithreadedTestCase {
    private LinkedTransferQueue<String> queue;

    //initialize()不接收任何参数，也不返回任何值。它调用父类的 initialize() 方法，然后初始化 queue 属性。
    @Override
    public void initialize() {
        super.initialize();
        queue = new LinkedTransferQueue<>();
        System.out.print("Test: The test has been initialized\n");
    }

    //实现的逻辑是第一个consumer。调用 queue 的 take() 方法，然后把返回值写入操控台。
    public void thread1() throws InterruptedException {
        String ret = queue.take();
        System.out.printf("Thread 1: %s\n", ret);
    }

    //实现逻辑是第二个consumer。首先，使用 waitForTick() 方法，一直等到第一个线程在 take() 方法中进入休眠。然后，调用queue的 take() 方法，并把返回值写入操控台。
    public void thread2() throws InterruptedException {
        waitForTick(1);
        String ret = queue.take();
        System.out.printf("Thread 2: %s\n", ret);
    }

    //实现逻辑producer。 首先，使用 waitForTick() 两次一直等到2个consumers被阻塞。然后，调用 queue的 put() 方法插入2个String 到queue中。
    public void thread3() {
        waitForTick(1);
        waitForTick(2);
        queue.put("Event 1");
        queue.put("Event 2");
        System.out.print("Thread 3: Inserted two elements\n");
    }

    //写信息到操控台表明测试结束执行。使用assertEquals() 方法检查2个事件已经被consumed（queue的大小为0）。
    @Override
    public void finish() {
        super.finish();
        System.out.print("Test: End\n");
        Assert.assertEquals(0, queue.size());
        System.out.print("Test: Result: The queue is empty\n");
    }
}
