package test.testng;

import org.testng.IExecutionListener;

/**
 * Created by wajian on 2016/10/9.
 */
public class ExecutionListener2 implements IExecutionListener {
    @Override
    public void onExecutionStart() {
        System.out.println("Notify by mail that TestNG is going to start");
    }

    @Override
    public void onExecutionFinish() {
        System.out.println("Notify by mail, TestNG is finished");
    }
}
