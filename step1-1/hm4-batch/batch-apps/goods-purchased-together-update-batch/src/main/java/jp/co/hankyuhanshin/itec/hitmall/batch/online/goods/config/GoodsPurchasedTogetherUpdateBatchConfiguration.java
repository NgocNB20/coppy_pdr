// 2023-renew No21 from here
package jp.co.hankyuhanshin.itec.hitmall.batch.online.goods.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.online.goods.listener.GoodsPurchasedTogetherUpdateJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.online.goods.tasklet.GoodsPurchasedTogetherUpdateBatch;
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
public class GoodsPurchasedTogetherUpdateBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public GoodsPurchasedTogetherUpdateBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                                          StepBuilderFactory stepBuilderFactory,
                                                          Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public GoodsPurchasedTogetherUpdateBatch goodsPurchasedTogetherUpdateTasklet() {
        return new GoodsPurchasedTogetherUpdateBatch(environment);
    }

    @Bean
    public Step goodsPurchasedTogetherUpdateMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_GOODS_PURCHASED_TOGETHER_UPDATE)
                                 .tasklet(goodsPurchasedTogetherUpdateTasklet())
                                 .build();
    }

    @Bean(name = BatchName.BATCH_GOODS_PURCHASED_TOGETHER_UPDATE)
    public Job importUserJob(GoodsPurchasedTogetherUpdateJobListener goodsPurchasedTogetherUpdateJobListener,
                             Step goodsPurchasedTogetherUpdateMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_GOODS_PURCHASED_TOGETHER_UPDATE)
                                .incrementer(new RunIdIncrementer())
                                .listener(goodsPurchasedTogetherUpdateJobListener)
                                .flow(goodsPurchasedTogetherUpdateMainStep)
                                .end()
                                .build();
    }

}
// 2023-renew No21 to here