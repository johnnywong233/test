package com.johnny.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.Date;

public class HelloWorldJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("First Job--------->" + new Date());
    }
}