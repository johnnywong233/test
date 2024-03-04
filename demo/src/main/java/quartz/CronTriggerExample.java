package quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by johnny on 2016/10/4.
 * example of CronTrigger: allow UNIX cron expression to assign a date and time to run job
 */
public class CronTriggerExample {
    public static void main(String[] args) throws Exception {
        //Quartz	1.6.3
        //JobDetail	job	=	new	JobDetail();
        //job.setName("dummyJobName");
        //job.setJobClass(HelloJob.class);
        JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("dummyJobName", "group1").build();
        //Quartz	1.6.3
        //CronTrigger	trigger	=	new	CronTrigger();
        //trigger.setName("dummyTriggerName");
        //trigger.setCronExpression("0/1	*	*	*	*	?");
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("dummyTriggerName", "group1")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("0/1 * * * * ?"))
                .build();
        //schedule	it
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }
}
