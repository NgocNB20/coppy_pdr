package jp.co.hankyuhanshin.itec.hitmall.batch.offline.zip.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.zip.listener.OfficeZipCodeJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.zip.tasklet.OfficeZipCodeUpdateBatch;
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
public class OfficeZipCodeBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public OfficeZipCodeBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                           StepBuilderFactory stepBuilderFactory,
                                           Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public OfficeZipCodeUpdateBatch officeZipCodeTasklet() {
        return new OfficeZipCodeUpdateBatch(environment);
    }

    @Bean
    public Step officeZipCodeMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_OFFICE_ZIP_CODE).tasklet(officeZipCodeTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_OFFICE_ZIP_CODE)
    public Job importUserJob(OfficeZipCodeJobListener officeZipCodeJobListener, Step officeZipCodeMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_OFFICE_ZIP_CODE)
                                .incrementer(new RunIdIncrementer())
                                .listener(officeZipCodeJobListener)
                                .flow(officeZipCodeMainStep)
                                .end()
                                .build();
    }

}
