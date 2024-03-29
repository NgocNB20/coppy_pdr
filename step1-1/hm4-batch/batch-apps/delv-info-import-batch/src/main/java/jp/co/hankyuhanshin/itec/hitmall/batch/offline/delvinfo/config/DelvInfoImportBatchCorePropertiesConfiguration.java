package jp.co.hankyuhanshin.itec.hitmall.batch.offline.delvinfo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

// PDR Migrate Customization from here
@Configuration
@PropertySource(value = {"classpath:config/hitmall/alert/multipaymentAlert.properties",
                "classpath:config/hitmall/alert/orderRegistAlert.properties",
                "classpath:config/hitmall/log/application_log.properties",
                "classpath:config/hitmall/other/executionShellFile.properties",
                "classpath:config/hitmall/coreSystem.properties", "classpath:config/hitmall/batchSystem.properties",
                "classpath:config/batchSpring-core.properties", "classpath:config/batchSpring-web.properties",
                "classpath:config/batchSpring-security.properties", "classpath:config/batchSpring-batch.properties",
                "classpath:config/batchThymeleaf.properties", "classpath:config/batchSpring-datasource.properties",
                "classpath:config/batchSpring-mail.properties", "classpath:config/batchOther.properties"},
                ignoreResourceNotFound = true, encoding = "UTF-8")
public class DelvInfoImportBatchCorePropertiesConfiguration {
}
// PDR Migrate Customization to here
