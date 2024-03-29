package jp.co.hankyuhanshin.itec.hitmall.batch.online.goods.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.online.goods.listener.GoodsImageUpdateBatchJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.online.goods.tasklet.GoodsImageUpdateBatch;
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
public class GoodsImageUpdateBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public GoodsImageUpdateBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                              StepBuilderFactory stepBuilderFactory,
                                              Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public GoodsImageUpdateBatch goodsImageUpdateTasklet() {
        return new GoodsImageUpdateBatch(environment);
    }

    @Bean
    public Step goodsImageUpdateMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_GOODSIMAGE_UPDATE).tasklet(goodsImageUpdateTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_GOODSIMAGE_UPDATE)
    public Job importUserJob(GoodsImageUpdateBatchJobListener goodsImageUpdateBatchJobListener,
                             Step goodsImageUpdateMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_GOODSIMAGE_UPDATE)
                                .incrementer(new RunIdIncrementer())
                                .listener(goodsImageUpdateBatchJobListener)
                                .flow(goodsImageUpdateMainStep)
                                .end()
                                .build();
    }

}
