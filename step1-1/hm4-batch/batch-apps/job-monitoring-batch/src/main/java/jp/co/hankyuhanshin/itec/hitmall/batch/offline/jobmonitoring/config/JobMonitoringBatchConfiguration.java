// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.jobmonitoring.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.jobmonitoring.listener.JobMonitoringJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.jobmonitoring.tasklet.JobMonitoringBatch;
import jp.co.hankyuhanshin.itec.hitmall.constant.BatchName;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableBatchProcessing
public class JobMonitoringBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public JobMonitoringBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                           StepBuilderFactory stepBuilderFactory,
                                           Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public JobMonitoringBatch jobMonitoringTasklet() {
        return new JobMonitoringBatch(environment);
    }

    @Bean
    public Step jobMonitoringMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_JOB_MONITORING).tasklet(jobMonitoringTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_JOB_MONITORING)
    public Job jobMonitoringJob(JobMonitoringJobListener jobMonitoringJobListener, Step jobMonitoringMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_JOB_MONITORING)
                                .incrementer(new RunIdIncrementer())
                                .listener(jobMonitoringJobListener)
                                .flow(jobMonitoringMainStep)
                                .end()
                                .build();
    }

}
// PDR Migrate Customization to here
