/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

@SpringBootApplication
@ComponentScan(basePackages = {"jp.co.hankyuhanshin.itec.hitmall", "jp.co.hankyuhanshin.itec.hmbase",
                "jp.co.hankyuhanshin.itec.hitmall.batch"})
@EnableAsync
public class AdminApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws IOException {
        Properties props = System.getProperties();
        URL url = Thread.currentThread().getContextClassLoader().getResource("config/spring-log.properties");
        if (url != null) {
            InputStream inputStream = url.openStream();
            props.load(inputStream);
            System.setProperties(props);
            inputStream.close();
        }
        SpringApplication.run(AdminApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AdminApplication.class);
    }
}
