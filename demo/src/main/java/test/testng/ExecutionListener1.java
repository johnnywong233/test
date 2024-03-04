package test.testng;

import org.testng.IExecutionListener;

/**
 * Created by johnny on 2016/10/9.
 */
public class ExecutionListener1 implements IExecutionListener {
    private long startTime;

    @Override
    public void onExecutionStart() {
        startTime = System.currentTimeMillis();
        System.out.println("TestNG is going to start");

    }

    @Override
    public void onExecutionFinish() {
        System.out.println("TestNG has finished, took around " + (System.currentTimeMillis() - startTime) + "ms");
    }


}
