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
        return	LISTENER_NAME;
    }

    //Run this if job is about to be executed.
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String	jobName	= context.getJobDetail().getKey().toString();
        System.out.println("job To Be Executed");
        System.out.println("Job: " + jobName + " is	going	to	start...");
    }

    //TODO: what does this mean?
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
        if	(!jobException.getMessage().equals(""))	{
            System.out.println("Exception thrown by: " + jobName + "	Exception: " + jobException.getMessage());
        }
    }
}
