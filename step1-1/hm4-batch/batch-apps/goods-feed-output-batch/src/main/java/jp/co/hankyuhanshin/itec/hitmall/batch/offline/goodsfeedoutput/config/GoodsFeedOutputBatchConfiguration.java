/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.goodsfeedoutput.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.goodsfeedoutput.listener.GoodsFeedOutputJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.goodsfeedoutput.tasklet.GoodsFeedOutputBatch;
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
public class GoodsFeedOutputBatchConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public GoodsFeedOutputBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                             StepBuilderFactory stepBuilderFactory,
                                             Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public GoodsFeedOutputBatch goodsFeedOutputTasklet() {
        return new GoodsFeedOutputBatch(environment);
    }

    @Bean
    public Step goodsFeedOutputMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_GOODS_FEED_OUTPUT).tasklet(goodsFeedOutputTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_GOODS_FEED_OUTPUT)
    public Job importUserJob(GoodsFeedOutputJobListener goodsFeedOutputJobListener, Step goodsFeedOutputMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_GOODS_FEED_OUTPUT)
                                .incrementer(new RunIdIncrementer())
                                .listener(goodsFeedOutputJobListener)
                                .flow(goodsFeedOutputMainStep)
                                .end()
                                .build();
    }
}
