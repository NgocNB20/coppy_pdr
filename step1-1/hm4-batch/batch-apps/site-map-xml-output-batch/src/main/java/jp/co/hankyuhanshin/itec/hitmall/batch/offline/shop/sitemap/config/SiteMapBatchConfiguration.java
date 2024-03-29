package jp.co.hankyuhanshin.itec.hitmall.batch.offline.shop.sitemap.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.shop.sitemap.listener.SiteMapJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.shop.sitemap.tasklet.SiteMapXmlOutputBatch;
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
public class SiteMapBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public SiteMapBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                     StepBuilderFactory stepBuilderFactory,
                                     Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public SiteMapXmlOutputBatch siteMapTasklet() {
        return new SiteMapXmlOutputBatch(environment);
    }

    @Bean
    public Step siteMapMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_SITEMAPXML_OUTPUT).tasklet(siteMapTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_SITEMAPXML_OUTPUT)
    public Job importUserJob(SiteMapJobListener siteMapJobListener, Step siteMapMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_SITEMAPXML_OUTPUT)
                                .incrementer(new RunIdIncrementer())
                                .listener(siteMapJobListener)
                                .flow(siteMapMainStep)
                                .end()
                                .build();
    }

}
