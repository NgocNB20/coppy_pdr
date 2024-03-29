/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.cuenotefcsalemail.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.cuenotefcsalemail.listener.CuenotefcSaleMailJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.cuenotefcsalemail.tasklet.CuenotefcSaleMailBatch;
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
public class CuenotefcSaleMailBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public CuenotefcSaleMailBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                               StepBuilderFactory stepBuilderFactory,
                                               Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public CuenotefcSaleMailBatch cuenotefcSaleMailTasklet() {
        return new CuenotefcSaleMailBatch(environment);
    }

    @Bean
    public Step cuenotefcSaleMailMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_CUENOTEFC_SALE_MAIL).tasklet(cuenotefcSaleMailTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_CUENOTEFC_SALE_MAIL)
    public Job importUserJob(CuenotefcSaleMailJobListener cuenotefcSaleMailJobListener,
                             Step cuenotefcSaleMailMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_CUENOTEFC_SALE_MAIL)
                                .incrementer(new RunIdIncrementer())
                                .listener(cuenotefcSaleMailJobListener)
                                .flow(cuenotefcSaleMailMainStep)
                                .end()
                                .build();
    }
}
// 2023-renew No41 to here
