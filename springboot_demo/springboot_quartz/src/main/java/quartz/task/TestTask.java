package quartz.task;

import lombok.extern.slf4j.Slf4j;

/**
 * Author: Johnny
 * Date: 2017/4/20
 * Time: 19:07
 */
@Slf4j
public class TestTask {

    public void run() {
        log.info("test task thread start running...");//OK
//        new ScheduleJobService().getScheduleJob();//Not OK
    }
}
