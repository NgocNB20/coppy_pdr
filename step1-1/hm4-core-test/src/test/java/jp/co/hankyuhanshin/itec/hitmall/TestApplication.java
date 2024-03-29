package jp.co.hankyuhanshin.itec.hitmall;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"jp.co.hankyuhanshin.itec.hitmall", "jp.co.hankyuhanshin.itec.hmbase"})
@EnableAutoConfiguration
@PropertySource(value = {"classpath:config/hitmall/coreSystem.properties"

}, ignoreResourceNotFound = true, encoding = "UTF-8")
public class TestApplication {

}
