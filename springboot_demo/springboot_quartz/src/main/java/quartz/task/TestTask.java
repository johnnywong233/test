package quartz.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Johnny
 * Date: 2017/4/20
 * Time: 19:07
 */
public class TestTask {
    private static final Logger LOG = LoggerFactory.getLogger(TestTask.class);

    public void run() {
        if (LOG.isInfoEnabled()) {
            LOG.info("test task thread start running...");//OK

//            new ScheduleJobService().getScheduleJob();//Not OK
        }
    }
}
