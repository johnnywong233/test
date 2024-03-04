package quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;

/**
 * Created by johnny on 2016/10/4.
 * example of CronTrigger, but add jobListener
 */
public class CronTriggerExample1 {
    public static void main(String[] args) throws Exception {
        JobKey jobKey = new JobKey("dummyJobName", "group1");
        JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity(jobKey).build();

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("dummyTriggerName", "group1")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("0/1 * * * * ?"))
                .build();
        //schedule	it
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();

        //Listener	attached to	jobKey
        scheduler.getListenerManager().addJobListener(
                new HelloJobListener(), KeyMatcher.keyEquals(jobKey)
        );

        //Listener attached	to	group named	"group	1"	only.
        scheduler.getListenerManager().addJobListener(new HelloJobListener(), GroupMatcher.jobGroupEquals("group1"));
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }
}
