package com.johnny.task;

import com.johnny.controller.JobController;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;

public class AlertJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {

        String msg = context.getJobDetail().getJobDataMap().getString("message");
        System.out.println("msg--------third-------->" + msg + "--------->");

        //测试scheduler.getCurrentlyExecutingJobs()
        /*for(long i=0;i<90000000000l;i++){
            System.out.println(i);
		}*/
        try {
            JobController jobController = (JobController) context.getScheduler().getContext().get("com.johnny.controller.JobController");
            jobController.test(msg);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}