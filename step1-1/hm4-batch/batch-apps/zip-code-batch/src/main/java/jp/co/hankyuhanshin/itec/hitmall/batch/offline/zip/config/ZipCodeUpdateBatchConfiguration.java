package jp.co.hankyuhanshin.itec.hitmall.batch.offline.zip.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.zip.listener.ZipCodeUpdateJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.zip.tasklet.ZipCodeUpdateBatch;
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
public class ZipCodeUpdateBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public ZipCodeUpdateBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                           StepBuilderFactory stepBuilderFactory,
                                           Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public ZipCodeUpdateBatch zipCodeUpdateTasklet() {
        return new ZipCodeUpdateBatch(environment);
    }

    @Bean
    public Step zipCodeUpdateMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_ZIP_CODE).tasklet(zipCodeUpdateTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_ZIP_CODE)
    public Job importUserJob(ZipCodeUpdateJobListener zipCodeUpdateJobListener, Step zipCodeUpdateMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_ZIP_CODE)
                                .incrementer(new RunIdIncrementer())
                                .listener(zipCodeUpdateJobListener)
                                .flow(zipCodeUpdateMainStep)
                                .end()
                                .build();
    }

}
