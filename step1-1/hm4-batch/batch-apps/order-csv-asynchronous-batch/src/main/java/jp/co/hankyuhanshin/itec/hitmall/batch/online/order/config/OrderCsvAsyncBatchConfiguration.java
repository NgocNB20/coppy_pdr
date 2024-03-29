package jp.co.hankyuhanshin.itec.hitmall.batch.online.order.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.online.order.listener.OrderCsvAsyncJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.online.order.tasklet.OrderCsvAsyncBatch;
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
public class OrderCsvAsyncBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public OrderCsvAsyncBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                           StepBuilderFactory stepBuilderFactory,
                                           Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public OrderCsvAsyncBatch orderCsvAsyncTasklet() {
        return new OrderCsvAsyncBatch(environment);
    }

    @Bean
    public Step orderCsvAsyncMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_ORDER_CSV_ASYNCHRONOUS).tasklet(orderCsvAsyncTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_ORDER_CSV_ASYNCHRONOUS)
    public Job importUserJob(OrderCsvAsyncJobListener orderCsvAsyncJobListener, Step orderCsvAsyncMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_ORDER_CSV_ASYNCHRONOUS)
                                .incrementer(new RunIdIncrementer())
                                .listener(orderCsvAsyncJobListener)
                                .flow(orderCsvAsyncMainStep)
                                .end()
                                .build();
    }

}
