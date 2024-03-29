package jp.co.hankyuhanshin.itec.hitmall.batch.offline.taskclean.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.taskclean.listener.TaskCleanJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.taskclean.tasklet.TaskCleanBatch;
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
public class TaskCleanBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public TaskCleanBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                       StepBuilderFactory stepBuilderFactory,
                                       Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public TaskCleanBatch taskCleanTasklet() {
        return new TaskCleanBatch(environment);
    }

    @Bean
    public Step taskCleanMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_TASK_CLEAN).tasklet(taskCleanTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_TASK_CLEAN)
    public Job importUserJob(TaskCleanJobListener taskCleanJobListener, Step taskCleanMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_TASK_CLEAN)
                                .incrementer(new RunIdIncrementer())
                                .listener(taskCleanJobListener)
                                .flow(taskCleanMainStep)
                                .end()
                                .build();
    }

}
