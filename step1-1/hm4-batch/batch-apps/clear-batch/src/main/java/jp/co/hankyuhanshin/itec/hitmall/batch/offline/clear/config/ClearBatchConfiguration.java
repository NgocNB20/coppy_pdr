package jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.listener.ClearJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.tasklet.ClearBatch;
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
public class ClearBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public ClearBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                   StepBuilderFactory stepBuilderFactory,
                                   Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public ClearBatch clearTasklet() {
        return new ClearBatch(environment);
    }

    @Bean
    public Step clearMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_CLEAR).tasklet(clearTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_CLEAR)
    public Job importUserJob(ClearJobListener clearJobListener, Step clearMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_CLEAR)
                                .incrementer(new RunIdIncrementer())
                                .listener(clearJobListener)
                                .flow(clearMainStep)
                                .end()
                                .build();
    }

}
