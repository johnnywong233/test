package com.johnny.model;

import lombok.Data;
import org.quartz.JobDataMap;

import java.util.Date;

@Data
public class JobEntity {
    private int jobId;

    private String oldjobName;
    private String oldjobGroupName;
    private String oldtriggerName;
    private String oldtriggerGroup;

    private String clazz;
    private String jobType;
    private String jobGroupName;

    private String jobName;

    private String triggerName;

    private String triggerGroupName;

    private String cronExpr;

    private Date previousFireTime;

    private Date nextFireTime;

    private String jobStatus;

    private long runTimes;

    private long duration;

    private Date startTime;

    private Date endTime;

    private String jobMemo;

    private String jobClass;

    private String jobMethod;

    private String jobObject;

    private Long count;

    private JobDataMap jobDataMap;
}