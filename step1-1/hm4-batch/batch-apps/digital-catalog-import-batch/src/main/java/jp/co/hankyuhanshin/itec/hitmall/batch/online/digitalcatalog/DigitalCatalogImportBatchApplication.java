// 2023-renew No42 from here
package jp.co.hankyuhanshin.itec.hitmall.batch.online.digitalcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"jp.co.hankyuhanshin.itec.hitmall", "jp.co.hankyuhanshin.itec.hmbase"})
public class DigitalCatalogImportBatchApplication {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(DigitalCatalogImportBatchApplication.class, args)));
    }
}
// 2023-renew No42 to here