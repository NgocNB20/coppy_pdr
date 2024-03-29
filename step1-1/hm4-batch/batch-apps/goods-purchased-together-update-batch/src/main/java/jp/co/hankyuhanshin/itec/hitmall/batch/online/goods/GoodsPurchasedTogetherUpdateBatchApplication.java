// 2023-renew No21 from here
package jp.co.hankyuhanshin.itec.hitmall.batch.online.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"jp.co.hankyuhanshin.itec.hitmall", "jp.co.hankyuhanshin.itec.hmbase"})
public class GoodsPurchasedTogetherUpdateBatchApplication {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(GoodsPurchasedTogetherUpdateBatchApplication.class, args)));
    }
}
// 2023-renew No21 to here