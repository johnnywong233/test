package quartz.service;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import quartz.bean.ScheduleJob;

/**
 * Author: Johnny
 * Date: 2017/4/20
 * Time: 19:10
 */
@DisallowConcurrentExecution
public class QuartzJobFactory implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("任务成功运行");
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        System.out.println("任务名称 = [" + scheduleJob.getJobName() + "]");
    }
}
