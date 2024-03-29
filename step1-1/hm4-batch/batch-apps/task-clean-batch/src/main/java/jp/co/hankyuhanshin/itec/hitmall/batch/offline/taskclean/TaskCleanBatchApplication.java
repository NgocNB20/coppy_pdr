package jp.co.hankyuhanshin.itec.hitmall.batch.offline.taskclean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"jp.co.hankyuhanshin.itec.hitmall", "jp.co.hankyuhanshin.itec.hmbase"})
public class TaskCleanBatchApplication {

    public static void main(String[] args) throws Exception {
        System.exit(SpringApplication.exit(SpringApplication.run(TaskCleanBatchApplication.class, args)));
    }

}
