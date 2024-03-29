/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.cuenotefcsaleconfirm.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.cuenotefcsaleconfirm.listener.CuenoteFCSaleConfirmJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.cuenotefcsaleconfirm.tasklet.CuenoteFCSaleConfirmBatch;
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
public class CuenoteFCSaleConfirmBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public CuenoteFCSaleConfirmBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                                  StepBuilderFactory stepBuilderFactory,
                                                  Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public CuenoteFCSaleConfirmBatch cuenoteFCSaleConfirmTasklet() {
        return new CuenoteFCSaleConfirmBatch(environment);
    }

    @Bean
    public Step cuenoteFCSaleConfirmMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_CUENOTEFC_SALE_MAIL_CONFIRM)
                                 .tasklet(cuenoteFCSaleConfirmTasklet())
                                 .build();
    }

    @Bean(name = BatchName.BATCH_CUENOTEFC_SALE_MAIL_CONFIRM)
    public Job importUserJob(CuenoteFCSaleConfirmJobListener cuenoteFCSaleConfirmJobListener,
                             Step cuenoteFCSaleConfirmMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_CUENOTEFC_SALE_MAIL_CONFIRM)
                                .incrementer(new RunIdIncrementer())
                                .listener(cuenoteFCSaleConfirmJobListener)
                                .flow(cuenoteFCSaleConfirmMainStep)
                                .end()
                                .build();
    }
}
// 2023-renew No41 to here
