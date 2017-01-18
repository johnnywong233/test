package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Author: Johnny
 * Date: 2017/1/18
 * Time: 19:54
 */
public class HelloQuartz implements Job {
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println("Hello Quartz !");
    }
}