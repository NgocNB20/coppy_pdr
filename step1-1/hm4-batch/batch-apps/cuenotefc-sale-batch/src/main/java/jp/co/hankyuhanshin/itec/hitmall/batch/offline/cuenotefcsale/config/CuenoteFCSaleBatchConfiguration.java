/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.cuenotefcsale.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.cuenotefcsale.listener.CuenoteFCSaleJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.cuenotefcsale.tasklet.CuenoteFCSaleBatch;
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

// 2023-renew No41 from here
@Configuration
@EnableBatchProcessing
public class CuenoteFCSaleBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public CuenoteFCSaleBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                           StepBuilderFactory stepBuilderFactory,
                                           Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public CuenoteFCSaleBatch cuenoteFCSaleTasklet() {
        return new CuenoteFCSaleBatch(environment);
    }

    @Bean
    public Step cuenoteFCSaleMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_CUENOTEFC_SALE_MAIL).tasklet(cuenoteFCSaleTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_CUENOTEFC_SALE_MAIL)
    public Job importUserJob(CuenoteFCSaleJobListener cuenoteFCSaleJobListener, Step cuenoteFCSaleMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_CUENOTEFC_SALE_MAIL)
                                .incrementer(new RunIdIncrementer())
                                .listener(cuenoteFCSaleJobListener)
                                .flow(cuenoteFCSaleMainStep)
                                .end()
                                .build();
    }
}
// 2023-renew No41 to here
