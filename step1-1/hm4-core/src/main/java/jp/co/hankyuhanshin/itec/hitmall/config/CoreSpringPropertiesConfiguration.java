/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Spring用プロパティ　読み込みクラス
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Configuration
@PropertySource(value = {"classpath:config/spring-web.properties", "classpath:config/spring-datasource.properties",
                "classpath:config/spring-mail.properties", "classpath:config/other.properties"},
                ignoreResourceNotFound = true, encoding = "UTF-8")
public class CoreSpringPropertiesConfiguration {

}
