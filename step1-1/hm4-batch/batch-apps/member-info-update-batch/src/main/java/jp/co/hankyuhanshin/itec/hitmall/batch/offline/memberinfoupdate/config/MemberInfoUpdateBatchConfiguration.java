package jp.co.hankyuhanshin.itec.hitmall.batch.offline.memberinfoupdate.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.memberinfoupdate.listener.MemberInfoUpdateJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.memberinfoupdate.tasklet.MemberInfoUpdateBatch;
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

// PDR Migrate Customization from here
@Configuration
@EnableBatchProcessing
public class MemberInfoUpdateBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public MemberInfoUpdateBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                              StepBuilderFactory stepBuilderFactory,
                                              Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public MemberInfoUpdateBatch memberInfoUpdateTasklet() {
        return new MemberInfoUpdateBatch(environment);
    }

    @Bean
    public Step memberInfoUpdateMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_MEMBER_INFO_UPDATE).tasklet(memberInfoUpdateTasklet()).build();
    }

    @Bean(name = BatchName.BATCH_MEMBER_INFO_UPDATE)
    public Job importUserJob(MemberInfoUpdateJobListener memberInfoUpdateJobListener, Step memberInfoUpdateMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_MEMBER_INFO_UPDATE)
                                .incrementer(new RunIdIncrementer())
                                .listener(memberInfoUpdateJobListener)
                                .flow(memberInfoUpdateMainStep)
                                .end()
                                .build();
    }
}
// PDR Migrate Customization to here
