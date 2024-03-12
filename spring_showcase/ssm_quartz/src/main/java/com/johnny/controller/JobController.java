package com.johnny.controller;

import com.johnny.model.JobEntity;
import com.johnny.model.ScheduleJob;
import com.johnny.service.QuartzService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class JobController {

    @Autowired
    private QuartzService quartzService;

    @Autowired
    private Scheduler scheduler;

    /**
     * 添加
     *
     * @param job job
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addJob(JobEntity job, HttpServletResponse response) {
        quartzService.addJob(job, response, new JobController());
        return "forward:test1";
    }

    /**
     * Test1获取所有计划中的任务列表
     */
    @RequestMapping("/test1")
    public String getAllJobTest1(Model model) throws SchedulerException {
        List<JobEntity> jobInfos = new ArrayList<>();
        List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
        for (String triggerGroupName : triggerGroupNames) {
            Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(GroupMatcher
                    .triggerGroupEquals(triggerGroupName));
            for (TriggerKey triggerKey : triggerKeySet) {
                Trigger t = scheduler.getTrigger(triggerKey);
                if (t instanceof CronTrigger) {
                    CronTrigger trigger = (CronTrigger) t;
                    JobKey jobKey = trigger.getJobKey();
                    JobDetail jd = scheduler.getJobDetail(jobKey);
                    JobEntity jobInfo = new JobEntity();
                    jobInfo.setJobName(jobKey.getName());
                    jobInfo.setJobGroupName(jobKey.getGroup());
                    jobInfo.setTriggerName(triggerKey.getName());
                    jobInfo.setTriggerGroupName(triggerKey.getGroup());
                    jobInfo.setCronExpr(trigger.getCronExpression());
                    jobInfo.setNextFireTime(trigger.getNextFireTime());
                    jobInfo.setPreviousFireTime(trigger.getPreviousFireTime());
                    jobInfo.setStartTime(trigger.getStartTime());
                    jobInfo.setEndTime(trigger.getEndTime());
                    jobInfo.setJobClass(jd.getJobClass().getCanonicalName());
                    // jobInfo.setDuration(Long.parseLong(jd.getDescription()));
                    System.out.println(jd.getDescription());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    jobInfo.setJobStatus(triggerState.toString());// NONE无,
                    // NORMAL正常,
                    // PAUSED暂停,
                    // COMPLETE完成,
                    // ERROR错误,
                    // BLOCKED阻塞
                    jobInfos.add(jobInfo);
                }
            }
        }
        model.addAttribute("jobInfos", jobInfos);
        return "forward:quartzs/list1.jsp";
    }

    /**
     * Test2获取所有计划中的任务列表
     */
    @RequestMapping("/test2")
    public String getAllJobTest2(Model model) throws SchedulerException {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<ScheduleJob> jobList = new ArrayList<>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                ScheduleJob job = new ScheduleJob();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setDescription("触发器:" + trigger.getKey());
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
        model.addAttribute("jobList", jobList);
        return "forward:quartzs/list2.jsp";
    }

    /**
     * 所有正在运行的job
     */
    @RequestMapping("/runjob")
    public String runningJob(Model model) throws SchedulerException {
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<ScheduleJob> jobList = new ArrayList<>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            ScheduleJob job = new ScheduleJob();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setJobName(jobKey.getName());
            job.setJobGroup(jobKey.getGroup());
            job.setDescription("触发器:" + trigger.getKey());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCronExpression(cronExpression);
            }
            jobList.add(job);
        }
        model.addAttribute("jobList", jobList);
        return "forward:quartzs/runjob.jsp";
    }

    /**
     * 暂停任务
     */
    @RequestMapping("/pauseJob")
    public String pauseJob(@RequestParam("jobName") String jobName,
                           @RequestParam("jobGroupName") String jobGroupName, HttpServletRequest request) {
        String method = request.getMethod();
        try {
            if ("GET".equals(method)) {
                jobName = new String(jobName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                jobGroupName = new String(jobGroupName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            }
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "forward:test1";
    }

    /**
     * 启动任务
     */
    @RequestMapping("/resumeJob")
    public String resumeJob(@RequestParam("jobName") String jobName,
                            @RequestParam("jobGroupName") String jobGroupName, HttpServletRequest request) {
        String method = request.getMethod();

        try {
            if ("GET".equals(method)) {
                jobName = new String(jobName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                jobGroupName = new String(jobGroupName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            }
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "forward:test1";
    }

    /**
     * 删除任务
     */
    @RequestMapping("/deleteJob")
    public String deleteJob(@RequestParam("jobName") String jobName,
                            @RequestParam("jobGroupName") String jobGroupName,
                            @RequestParam("triggerName") String triggerName,
                            @RequestParam("triggerGroupName") String triggerGroupName, HttpServletRequest request) {
        String method = request.getMethod();
        try {
            if ("GET".equals(method)) {
                jobName = new String(jobName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                jobGroupName = new String(jobGroupName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                triggerName = new String(triggerName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                triggerGroupName = new String(triggerGroupName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            }
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "forward:test1";
    }

    /**
     * 跳转到编辑页面
     */
    @RequestMapping("/toEditJob")
    @SuppressWarnings("unchecked")
    public String toEditJob(@RequestParam("jobName") String jobName,
                            @RequestParam("jobGroupName") String jobGroupName, Model model, HttpServletRequest request) {
        String method = request.getMethod();
        JobEntity jobEntity = new JobEntity();
        try {
            if ("GET".equals(method)) {
                jobName = new String(jobName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                jobGroupName = new String(jobGroupName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            }
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            List<CronTrigger> triggers = (List<CronTrigger>) scheduler.getTriggersOfJob(jobKey);
            CronTrigger trigger = triggers.get(0);
            TriggerKey triggerKey = trigger.getKey();
            String cron = trigger.getCronExpression();
            jobEntity.setJobName(jobKey.getName());
            jobEntity.setJobGroupName(jobKey.getGroup());
            jobEntity.setTriggerName(triggerKey.getName());
            jobEntity.setTriggerGroupName(triggerKey.getGroup());
            jobEntity.setCronExpr(cron);
            jobEntity.setClazz(jobDetail.getJobClass().getCanonicalName());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        model.addAttribute("jobEntity", jobEntity);
        return "forward:quartzs/edit.jsp";
    }

    /**
     * 编辑
     */
    @RequestMapping("/editJob")
    public void editJob(JobEntity jobEntity, HttpServletResponse response) {
        TriggerKey oldTriggerKey = TriggerKey.triggerKey(jobEntity.getOldTriggerName(), jobEntity.getOldTriggerGroup());
        JobKey jobKey = JobKey.jobKey(jobEntity.getOldJobName(), jobEntity.getOldJobGroupName());
        response.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(oldTriggerKey);
            if (trigger != null) {
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                // 停止触发器
                scheduler.pauseTrigger(oldTriggerKey);
                // 移除触发器
                scheduler.unscheduleJob(oldTriggerKey);
                // 删除任务
                scheduler.deleteJob(jobKey);
                quartzService.addJob(jobEntity, response, new JobController());
                out.print("<script>alert('修改成功！');location.href='toEditJob?jobName=" + jobEntity.getJobName() + "&jobGroupName=" + jobEntity.getJobGroupName() + "'</script>");
            } else {
                out.print("<script>alert('修改失败！');location.href='toEditJob?jobName=" + jobEntity.getOldJobName() + "&jobGroupName=" + jobEntity.getOldJobGroupName() + "'</script>");
            }

        } catch (SchedulerException | IOException e) {
            e.printStackTrace();
        }
    }

    public void test(String msg) {
        System.out.println(msg + "---------------进入controller--------------------");
    }
}