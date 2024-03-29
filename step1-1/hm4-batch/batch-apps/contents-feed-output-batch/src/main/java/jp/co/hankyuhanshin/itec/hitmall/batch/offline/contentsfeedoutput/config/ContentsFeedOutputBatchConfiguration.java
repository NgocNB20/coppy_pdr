/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.contentsfeedoutput.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.contentsfeedoutput.listener.ContentsFeedOutputJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.contentsfeedoutput.tasklet.ContentsFeedOutputBatch;
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
public class ContentsFeedOutputBatchConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public ContentsFeedOutputBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                                StepBuilderFactory stepBuilderFactory,
                                                Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public ContentsFeedOutputBatch contentsFeedOutputTasklet() {
        return new ContentsFeedOutputBatch(environment);
    }

    @Bean
    public Step contentsFeedOutputMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_CONTENTS_FEED_OUTPUT)
                                 .tasklet(contentsFeedOutputTasklet())
                                 .build();
    }

    @Bean(name = BatchName.BATCH_CONTENTS_FEED_OUTPUT)
    public Job importUserJob(ContentsFeedOutputJobListener contentsFeedOutputJobListener,
                             Step contentsFeedOutputMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_CONTENTS_FEED_OUTPUT)
                                .incrementer(new RunIdIncrementer())
                                .listener(contentsFeedOutputJobListener)
                                .flow(contentsFeedOutputMainStep)
                                .end()
                                .build();
    }
}
