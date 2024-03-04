package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by johnny on 2016/10/4.
 * simple job as print something
 */
public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("this is the job to execute!");
        //Throw	exception	for	testing
        throw new JobExecutionException("Testing Exception");
    }
}