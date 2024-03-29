package jp.co.hankyuhanshin.itec.hitmall.batch.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * Spring-BatchのJobLauncherによる非同期バッチの起動のコンフィグレーション
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Configuration
@EnableBatchProcessing
public class BatchAsyncConfig {
    /**
     * ジョブリポジトリ
     **/
    private final JobRepository jobRepository;

    @Autowired
    public BatchAsyncConfig(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Bean(name = "jobLauncherAsync")
    public JobLauncher simpleJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

}
