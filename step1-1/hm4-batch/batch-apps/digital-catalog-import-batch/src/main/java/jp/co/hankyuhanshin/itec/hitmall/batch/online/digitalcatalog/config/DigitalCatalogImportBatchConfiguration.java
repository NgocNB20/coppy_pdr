// 2023-renew No42 from here
package jp.co.hankyuhanshin.itec.hitmall.batch.online.digitalcatalog.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.online.digitalcatalog.listener.DigitalCatalogImportJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.online.digitalcatalog.tasklet.DigitalCatalogImportBatch;
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
public class DigitalCatalogImportBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public DigitalCatalogImportBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                                  StepBuilderFactory stepBuilderFactory,
                                                  Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public DigitalCatalogImportBatch digitalCatalogImportTasklet() {
        return new DigitalCatalogImportBatch(environment);
    }

    @Bean
    public Step digitalCatalogImportMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_DIGITALCATALOG_IMPORT)
                                 .tasklet(digitalCatalogImportTasklet())
                                 .build();
    }

    @Bean(name = BatchName.BATCH_DIGITALCATALOG_IMPORT)
    public Job importUserJob(DigitalCatalogImportJobListener digitalCatalogImportJobListener,
                             Step digitalCatalogImportMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_DIGITALCATALOG_IMPORT)
                                .incrementer(new RunIdIncrementer())
                                .listener(digitalCatalogImportJobListener)
                                .flow(digitalCatalogImportMainStep)
                                .end()
                                .build();
    }

}
// 2023-renew No42 from here