package batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Johnny
 * Date: 2017/10/4
 * Time: 18:51
 */
@Service
@Slf4j
public class MyJobLauncher {
    private final JobLauncher jobLauncher;
    private final Job job;

    @Autowired
    public MyJobLauncher(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    public JobExecution handle() throws Exception {
        JobExecution execution = jobLauncher.run(job, new JobParameters());
        log.info("Execute method MyJobLauncher.handle:{}", execution);
        return execution;
    }
}
