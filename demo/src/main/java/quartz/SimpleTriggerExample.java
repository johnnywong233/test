package quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by wajian on 2016/10/4.
 * SimpleTrigger example: allow to set start time, end time, and interval
 * notice: the API changed form 1.* to 2.*
 */
public class SimpleTriggerExample {
    public static void main(String[] args) throws Exception {
        //	Quartz	1.6.3
        //	JobDetail	job	=	new	JobDetail();
        //	job.setName("dummyJobName");
        //	job.setJobClass(HelloJob.class);
        JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("dummyJobName", "group1").build();
        //Quartz	1.6.3
        //	SimpleTrigger	trigger	=	new	SimpleTrigger();
        //	trigger.setStartTime(new Date(System.currentTimeMillis() + 1000))
        //	trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        //	trigger.setRepeatInterval(30000);
        //	Trigger	the	job	to	run	on	the	next round	minute
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("dummyTriggerName", "group1")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(1).repeatForever())
                .build();
        //schedule it
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        //调度类链接“工作”和“触发器”到一起，并执行它。
        scheduler.scheduleJob(job, trigger);
    }
}
