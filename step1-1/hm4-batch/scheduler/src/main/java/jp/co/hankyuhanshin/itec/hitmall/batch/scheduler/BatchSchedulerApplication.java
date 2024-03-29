package jp.co.hankyuhanshin.itec.hitmall.batch.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * 機能概要：バッチスケジューラ
 * 作成日：2021/01/12
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
@SpringBootApplication
@ComponentScan(basePackages = {"jp.co.hankyuhanshin.itec.hitmall", "jp.co.hankyuhanshin.itec.hmbase",
                "jp.co.hankyuhanshin.itec.hitmall.batch"})
public class BatchSchedulerApplication {

    public static void main(String[] args) throws IOException {
        Properties props = System.getProperties();
        URL url = Thread.currentThread().getContextClassLoader().getResource("config/spring-log.properties");
        if (url != null) {
            InputStream inputStream = url.openStream();
            props.load(inputStream);
            System.setProperties(props);
            inputStream.close();
        }
        SpringApplication.run(BatchSchedulerApplication.class, args);
    }
}
