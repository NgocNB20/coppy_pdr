// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.goods.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.goods.listener.GoodsPriceUpdateJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.goods.tasklet.GoodsPriceUpdateBatch;
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
public class GoodsPriceUpdateBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public GoodsPriceUpdateBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                              StepBuilderFactory stepBuilderFactory,
                                              Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public GoodsPriceUpdateBatch goodsPriceUpdateTasklet() {
        return new GoodsPriceUpdateBatch(environment);
    }

    @Bean
    public Step goodsPriceUpdateMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_GOODS_PRICE_UPDATE).tasklet(goodsPriceUpdateTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_GOODS_PRICE_UPDATE)
    public Job importUserJob(GoodsPriceUpdateJobListener goodsPriceUpdateJobListener, Step goodsPriceUpdateMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_GOODS_PRICE_UPDATE)
                                .incrementer(new RunIdIncrementer())
                                .listener(goodsPriceUpdateJobListener)
                                .flow(goodsPriceUpdateMainStep)
                                .end()
                                .build();
    }

}
// PDR Migrate Customization to here
