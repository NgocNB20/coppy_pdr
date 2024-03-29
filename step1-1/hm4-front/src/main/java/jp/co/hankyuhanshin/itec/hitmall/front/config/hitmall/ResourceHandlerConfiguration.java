// 2023-renew No42 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.config.hitmall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 会員登録時に、確認書類（画像データ）を表示したい
 * （セキュリティのため、NGINXの経由でしない）
 *
 * @author doanthang
 */
@Configuration
public class ResourceHandlerConfiguration implements WebMvcConfigurer {

    // 環境定数
    private final Environment environment;

    @Autowired
    public ResourceHandlerConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String docsRealResourceLocations = "file:/" + environment.getProperty("digital.catalog.directory") + "/";
        registry.addResourceHandler("/catalog/**").addResourceLocations(docsRealResourceLocations);

        // 2023-renew No22 from here
        String filesTmpResourceLocations = "file:///" + environment.getProperty("real.tmp.path.conf.document") + "/";
        registry.addResourceHandler("/tmp-docs/**").addResourceLocations(filesTmpResourceLocations);

        String filesRealResourceLocations = "file:///" + environment.getProperty("real.path.conf.document") + "/";
        registry.addResourceHandler("/confirm-docs/**").addResourceLocations(filesRealResourceLocations);
        // 2023-renew No22 to here
    }

}
// 2023-renew No42 from here
