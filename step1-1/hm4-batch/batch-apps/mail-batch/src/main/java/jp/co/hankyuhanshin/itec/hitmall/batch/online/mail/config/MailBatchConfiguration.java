package jp.co.hankyuhanshin.itec.hitmall.batch.online.mail.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.online.mail.listener.MailJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.online.mail.tasklet.MailBatch;
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

@Configuration
@EnableBatchProcessing
public class MailBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    public MailBatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    @JobScope
    public MailBatch mailTasklet() {
        return new MailBatch();
    }

    @Bean
    public Step mailMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_MAIL).tasklet(mailTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_MAIL)
    public Job importUserJob(MailJobListener mailJobListener, Step mailMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_MAIL)
                                .incrementer(new RunIdIncrementer())
                                .listener(mailJobListener)
                                .flow(mailMainStep)
                                .end()
                                .build();
    }

}
