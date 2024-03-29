package jp.co.hankyuhanshin.itec.hitmall.batch.offline.delvinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// PDR Migrate Customization from here
@SpringBootApplication
@ComponentScan(basePackages = {"jp.co.hankyuhanshin.itec.hitmall", "jp.co.hankyuhanshin.itec.hmbase"})
public class DelvInfoImportBatchApplication {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(DelvInfoImportBatchApplication.class, args)));
    }

}
// PDR Migrate Customization to here
