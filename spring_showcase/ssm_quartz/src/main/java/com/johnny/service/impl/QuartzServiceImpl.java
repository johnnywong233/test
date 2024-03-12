package com.johnny.service.impl;

import com.johnny.controller.JobController;
import com.johnny.model.JobEntity;
import com.johnny.service.QuartzService;
import jakarta.servlet.http.HttpServletResponse;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    private Scheduler scheduler;// 获取调度器

    @Override
    public void addJob(JobEntity jobEntity, HttpServletResponse response, JobController jobController) {

        try {
            Class s = Class.forName(jobEntity.getClazz());
            //Class cls=Class.forName(jobEntity.getClazz());
            //System.out.println(cls.getName());
            // 创建一项作业
            JobDetail job = JobBuilder.newJob(s)
                    .withIdentity(jobEntity.getJobName(), jobEntity.getJobGroupName())
                    .build();

            //测试传参
            String msg = "枭雄";
            //方法一
            job.getJobDataMap().put("message", msg);
            //方法二
            scheduler.getContext().put("com.johnny.controller.JobController", jobController);

            // 创建一个触发器
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobEntity.getTriggerName(), jobEntity.getTriggerGroupName())
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobEntity.getCronExpr()))
                    .build();
            // 告诉调度器使用该触发器来安排作业
            scheduler.scheduleJob(job, trigger);

            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}