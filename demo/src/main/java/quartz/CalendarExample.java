package quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.quartz.DateBuilder.dateOf;
import static org.quartz.TriggerBuilder.newTrigger;


/**
 * Author: Johnny
 * Date: 2017/9/30
 * Time: 10:38
 * 排除特定日期，比如节假日的CronTrigger实例
 * 注意static method的使用
 */
public class CalendarExample {
    public void run() throws SchedulerException {

        // 获取任务调度器
        StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = stdSchedulerFactory.getScheduler();

        // 构建所有日期对象集合
        AnnualCalendar holidays = new AnnualCalendar();

        // 劳动节 5月1日
        Calendar firstOfMay = new GregorianCalendar(2016, 4, 1);
        holidays.setDayExcluded(firstOfMay, true);

        // 国庆节 10月1日
        Calendar nationalDay = new GregorianCalendar(2016, 9, 1);
        holidays.setDayExcluded(nationalDay, true);

        // tell the schedule about our holiday calendar
        scheduler.addCalendar("holidays", holidays, false, false);

        // 10月1日上午十点启动任务调度
        Date runDate = dateOf(0, 0, 10, 1, 10);

        // job1将会在每周一、三、五的每天晚上11:30得到执行
        JobDetail job = JobBuilder.newJob(SimpleJob.class).withIdentity("job1", "group1").build();
        CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 30 11pm ? * MON,WED,FRI")).modifiedByCalendar("holidays").startAt(runDate).build();

        // schedule the job and print the first run date
        Date firstRunTime = scheduler.scheduleJob(job, trigger);

        // print out the first execution date.
        scheduler.scheduleJob(job, trigger);

        // 启动任务调度器
        scheduler.start();

        // 等待300s展示结果
        try {
            Thread.sleep(300L * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 停止任务调度器
        scheduler.shutdown(true);
    }

    public static void main(String[] args) throws SchedulerException {
        CalendarExample example = new CalendarExample();
        example.run();
    }
}

class SimpleJob implements Job {

    public SimpleJob() {
    }

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        System.out.println("SimpleJob says: " + jobKey + " executing at " + new Date());
    }
}