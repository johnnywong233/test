package com.johnny.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.Date;

public class SecondJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("Second  Job--------->" + new Date());
    }
}