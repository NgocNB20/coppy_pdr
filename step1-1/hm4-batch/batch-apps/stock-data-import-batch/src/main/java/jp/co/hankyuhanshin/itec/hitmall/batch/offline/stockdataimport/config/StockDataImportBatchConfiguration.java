/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.stockdataimport.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.stockdataimport.listener.StockDataImportJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.stockdataimport.tasklet.StockDataImportBatch;
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
public class StockDataImportBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public StockDataImportBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                             StepBuilderFactory stepBuilderFactory,
                                             Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public StockDataImportBatch stockDataImportTasklet() {
        return new StockDataImportBatch(environment);
    }

    @Bean
    public Step stockDataImportMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_STOCK_DATA_IMPORT).tasklet(stockDataImportTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_STOCK_DATA_IMPORT)
    public Job importUserJob(StockDataImportJobListener stockDataImportJobListener, Step stockDataImportMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_STOCK_DATA_IMPORT)
                                .incrementer(new RunIdIncrementer())
                                .listener(stockDataImportJobListener)
                                .flow(stockDataImportMainStep)
                                .end()
                                .build();
    }

}
