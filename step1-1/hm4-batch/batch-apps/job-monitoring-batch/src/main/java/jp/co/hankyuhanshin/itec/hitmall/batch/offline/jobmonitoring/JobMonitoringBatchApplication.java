// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.jobmonitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"jp.co.hankyuhanshin.itec.hitmall", "jp.co.hankyuhanshin.itec.hmbase"})
public class JobMonitoringBatchApplication {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(JobMonitoringBatchApplication.class, args)));
    }
}
// PDR Migrate Customization to here
