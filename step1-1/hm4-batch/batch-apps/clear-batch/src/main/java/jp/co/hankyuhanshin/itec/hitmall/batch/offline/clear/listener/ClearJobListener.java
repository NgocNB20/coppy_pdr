package jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ClearJobListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(ClearJobListener.class);
    private final JobExplorer jobExplorer;

    @Autowired
    public ClearJobListener(JobExplorer jobExplorer) {
        this.jobExplorer = jobExplorer;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // 並列処理禁止のため
        String jobName = jobExecution.getJobInstance().getJobName();
        Set<JobExecution> executions = jobExplorer.findRunningJobExecutions(jobName);
        if (executions.size() > 1) {
            jobExecution.stop();
            log.info("!!! JOB STOPPED due to concurrent running -- beforeJob");
        }
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results -- afterJob");
        }

        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.info("!!! JOB FAILED! Time to verify the results -- afterJob");
        }
    }

}
