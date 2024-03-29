package jp.co.hankyuhanshin.itec.hitmall.batch.offline.stockdisplay.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.stockdisplay.listener.StockStatusJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.stockdisplay.tasklet.StockStatusDisplayUpdateBatch;
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
public class StockStatusBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public StockStatusBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                         StepBuilderFactory stepBuilderFactory,
                                         Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public StockStatusDisplayUpdateBatch stockStatusTasklet() {
        return new StockStatusDisplayUpdateBatch(environment);
    }

    @Bean
    public Step stockStatusMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_STOCKSTATUSDISPLAY_UPDATE).tasklet(stockStatusTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_STOCKSTATUSDISPLAY_UPDATE)
    public Job importUserJob(StockStatusJobListener stockStatusJobListener, Step stockStatusMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_STOCKSTATUSDISPLAY_UPDATE)
                                .incrementer(new RunIdIncrementer())
                                .listener(stockStatusJobListener)
                                .flow(stockStatusMainStep)
                                .end()
                                .build();
    }

}
