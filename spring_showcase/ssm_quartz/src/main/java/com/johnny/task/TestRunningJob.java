package com.johnny.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestRunningJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        for (long i = 0; i < 900000000000L; i++) {
            System.out.println(i);
        }
    }
}