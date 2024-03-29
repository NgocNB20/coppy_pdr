package jp.co.hankyuhanshin.itec.hitmall.batch.offline.memberinfoupdate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// PDR Migrate Customization from here
@SpringBootApplication
@ComponentScan(basePackages = {"jp.co.hankyuhanshin.itec.hitmall", "jp.co.hankyuhanshin.itec.hmbase"})
public class MemberInfoUpdateBatchApplication {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(MemberInfoUpdateBatchApplication.class, args)));
    }

}
// PDR Migrate Customization to here
