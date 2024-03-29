package jp.co.hankyuhanshin.itec.hitmall.batch.offline.delvinfo.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.delvinfo.listener.DelvInfoImportJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.delvinfo.tasklet.DelvInfoImportBatch;
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
// PDR Migrate Customization from here
public class DelvInfoImportBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public DelvInfoImportBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                            StepBuilderFactory stepBuilderFactory,
                                            Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public DelvInfoImportBatch delvInfoImportTasklet() {
        return new DelvInfoImportBatch(environment);
    }

    @Bean
    public Step delvInfoImportMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_DELV_PRICE_UPDATE).tasklet(delvInfoImportTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_DELV_PRICE_UPDATE)
    public Job importUserJob(DelvInfoImportJobListener delvInfoImportJobListener, Step delvInfoImportMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_DELV_PRICE_UPDATE)
                                .incrementer(new RunIdIncrementer())
                                .listener(delvInfoImportJobListener)
                                .flow(delvInfoImportMainStep)
                                .end()
                                .build();
    }

}
// PDR Migrate Customization to here
