package com.johnny.model;

import lombok.Data;

import java.util.Date;

@Data
public class ScheduleJob {
    private Integer jobId;
    private Date createTime;
    private Date updateTime;
    private String jobName;//任务名称
    private String jobGroup;//任务分组
    private String jobStatus;//任务状态 是否启动任务
    private String cronExpression;// cron表达式
    private String description;//描述
    private String beanClass;//任务执行时调用哪个类的方法 包名+类名
    private String isConcurrent;//任务是否有状态
    private String springId;//spring bean
    private String methodName;//任务调用的方法名
}