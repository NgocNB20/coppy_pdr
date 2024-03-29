/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.saledataimport.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.saledataimport.listener.SaleDataImportJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.saledataimport.tasklet.SaleDataImportBatch;
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
public class SaleDataImportBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public SaleDataImportBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                             StepBuilderFactory stepBuilderFactory,
                                             Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public SaleDataImportBatch saleDataImportTasklet() {
        return new SaleDataImportBatch(environment);
    }

    @Bean
    public Step saleDataImportMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_SALE_DATA_IMPORT).tasklet(saleDataImportTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_SALE_DATA_IMPORT)
    public Job importUserJob(SaleDataImportJobListener saleDataImportJobListener, Step saleDataImportMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_SALE_DATA_IMPORT)
                                .incrementer(new RunIdIncrementer())
                                .listener(saleDataImportJobListener)
                                .flow(saleDataImportMainStep)
                                .end()
                                .build();
    }

}
