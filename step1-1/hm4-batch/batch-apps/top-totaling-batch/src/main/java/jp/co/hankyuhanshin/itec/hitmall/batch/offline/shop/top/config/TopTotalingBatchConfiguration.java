package jp.co.hankyuhanshin.itec.hitmall.batch.offline.shop.top.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.shop.top.listener.TopTotalingJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.shop.top.tasklet.TopTotalingBatch;
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

@Configuration
@EnableBatchProcessing
public class TopTotalingBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    public TopTotalingBatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    @JobScope
    public TopTotalingBatch topTotalingTasklet() {
        return new TopTotalingBatch();
    }

    @Bean
    public Step topTotalingMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_TOP_TOTALING).tasklet(topTotalingTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_TOP_TOTALING)
    public Job importUserJob(TopTotalingJobListener topTotalingJobListener, Step topTotalingMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_TOP_TOTALING)
                                .incrementer(new RunIdIncrementer())
                                .listener(topTotalingJobListener)
                                .flow(topTotalingMainStep)
                                .end()
                                .build();
    }

}
