package quartz.service;

import org.quartz.CronTrigger;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import quartz.bean.ScheduleJob;
import quartz.util.MyApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Author: Johnny
 * Date: 2017/4/20
 * Time: 19:12
 */
public class ScheduleJobService {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleJobService.class);

    public void getScheduleJob() {
        try {
            SchedulerFactoryBean schedulerFactoryBean = MyApplicationContext.getBean("scheduler");
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            List<ScheduleJob> jobList = new ArrayList<>();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    ScheduleJob job = new ScheduleJob();
                    job.setJobName(jobKey.getName());
                    job.setJobGroup(jobKey.getGroup());
                    job.setDesc("trigger:" + trigger.getKey());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    job.setJobStatus(triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        job.setCronExpression(cronExpression);
                    }
                    jobList.add(job);
                }
            }
            for (ScheduleJob job : jobList) {
                LOG.info("schedule job list, name:{},group:{},desc:{},status:{}", job.getJobName(), job.getJobGroup(), job.getDesc(), job.getJobStatus());
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
