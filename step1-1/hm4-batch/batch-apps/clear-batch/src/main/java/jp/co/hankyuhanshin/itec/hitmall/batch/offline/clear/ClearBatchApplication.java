package jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"jp.co.hankyuhanshin.itec.hitmall", "jp.co.hankyuhanshin.itec.hmbase"})
public class ClearBatchApplication {

    public static void main(String[] args) throws Exception {
        System.exit(SpringApplication.exit(SpringApplication.run(ClearBatchApplication.class, args)));
    }

}
