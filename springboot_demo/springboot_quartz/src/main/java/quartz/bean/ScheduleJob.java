package quartz.bean;

import lombok.Data;

/**
 * Author: Johnny
 * Date: 2017/4/20
 * Time: 19:06
 */
@Data
public class ScheduleJob {
    private String jobName;
    private String jobGroup;
    private String desc;
    private String jobStatus;
    private String cronExpression;
}
