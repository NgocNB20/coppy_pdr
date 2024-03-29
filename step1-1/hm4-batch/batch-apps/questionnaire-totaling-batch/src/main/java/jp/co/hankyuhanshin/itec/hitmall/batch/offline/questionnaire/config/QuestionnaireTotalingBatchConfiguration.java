package jp.co.hankyuhanshin.itec.hitmall.batch.offline.questionnaire.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.questionnaire.listener.QuestionnaireTotalingJobListener;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.questionnaire.tasklet.QuestionnaireTotalingBatch;
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
public class QuestionnaireTotalingBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Environment environment;

    @Autowired
    public QuestionnaireTotalingBatchConfiguration(JobBuilderFactory jobBuilderFactory,
                                                   StepBuilderFactory stepBuilderFactory,
                                                   Environment environment) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.environment = environment;
    }

    @Bean
    @JobScope
    public QuestionnaireTotalingBatch questionnaireTotalingTasklet() {
        return new QuestionnaireTotalingBatch(environment);
    }

    @Bean
    public Step questionnaireTotalingMainStep() {
        return stepBuilderFactory.get(BatchName.BATCH_QUESTIONNAIRE_TOTALING)
                                 .tasklet(questionnaireTotalingTasklet())
                                 .build();
    }

    @Bean(name = BatchName.BATCH_QUESTIONNAIRE_TOTALING)
    public Job importUserJob(QuestionnaireTotalingJobListener questionnaireTotalingJobListener,
                             Step questionnaireTotalingMainStep) {
        return jobBuilderFactory.get(BatchName.BATCH_QUESTIONNAIRE_TOTALING)
                                .incrementer(new RunIdIncrementer())
                                .listener(questionnaireTotalingJobListener)
                                .flow(questionnaireTotalingMainStep)
                                .end()
                                .build();
    }

}
