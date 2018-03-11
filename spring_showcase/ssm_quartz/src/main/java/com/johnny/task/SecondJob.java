package com.johnny.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class SecondJob implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Second  Job--------->" + new Date().toLocaleString());
    }
}