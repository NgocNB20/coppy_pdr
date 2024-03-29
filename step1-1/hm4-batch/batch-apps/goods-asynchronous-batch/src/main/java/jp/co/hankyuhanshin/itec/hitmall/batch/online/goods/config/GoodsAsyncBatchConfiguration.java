package jp.co.hankyuhanshin.itec.hitmall.batch.online.goods.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.online.goods.listener.GoodsAsyncJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.online.goods.tasklet.GoodsAsynchronousBatch;
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
public class GoodsAsyncBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public GoodsAsyncBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                        StepBuilderFactory stepBuilderFactory,
                                        Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public GoodsAsynchronousBatch goodsAsyncTasklet() {
        return new GoodsAsynchronousBatch(environment);
    }

    @Bean
    public Step goodsAsyncMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_GOODS_ASYNCHRONOUS).tasklet(goodsAsyncTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_GOODS_ASYNCHRONOUS)
    public Job importUserJob(GoodsAsyncJobListener goodsAsyncJobListener, Step goodsAsyncMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_GOODS_ASYNCHRONOUS)
                                .incrementer(new RunIdIncrementer())
                                .listener(goodsAsyncJobListener)
                                .flow(goodsAsyncMainStep)
                                .end()
                                .build();
    }

}
