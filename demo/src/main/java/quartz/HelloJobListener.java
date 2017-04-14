package quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * Created by wajian on 2016/10/4.
 * JobListener
 */
public class HelloJobListener implements JobListener {

    private static final String LISTENER_NAME = "dummyJobListenerName";

    @Override
    public String getName() {
        return LISTENER_NAME;
    }

    //Run this if job is about to be executed.
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().toString();
        System.out.println("job To Be Executed");
        System.out.println("Job: " + jobName + " is	going to start...");
    }

    //http://stackoverflow.com/questions/16568315/when-jobexecutionvetoed-of-joblistener-will-be-execured-in-quartz-scheduler
    //http://www.cnblogs.com/daxin/archive/2013/05/29/3105830.html
    //这个方法正常情况下不执行,但是如果当TriggerListener中的vetoJobExecution方法返回true时,那么执行这个方法.
    //如果此方法执行,那么jobToBeExecuted, jobWasExecuted这俩个方法不会执行
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("job Execution Vetoed");
    }

    //Run this after job has been executed
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        System.out.println("job Was Executed");
        String jobName = context.getJobDetail().getKey().toString();
        System.out.println("Job: " + jobName + "is finished...");
        if (!jobException.getMessage().equals("")) {
            System.out.println("Exception thrown by: " + jobName + " Exception: " + jobException.getMessage());
        }
    }
}
