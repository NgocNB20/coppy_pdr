// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.jobmonitoring.listener;

import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class JobMonitoringJobListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobMonitoringJobListener.class);
    private final JobExplorer jobExplorer;

    @Autowired
    public JobMonitoringJobListener(JobExplorer jobExplorer) {
        this.jobExplorer = jobExplorer;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // 終了メッセージ設定
        String exitMessage = (Objects.requireNonNull(
                        jobExecution.getExecutionContext().get(BatchExitMessageUtil.exitMsg))).toString();
        jobExecution.setExitStatus(new ExitStatus(jobExecution.getStatus().toString(), exitMessage));

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results -- afterJob");
        }

        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.info("!!! JOB FAILED! Time to verify the results -- afterJob");
        }
    }

}
// PDR Migrate Customization to here
