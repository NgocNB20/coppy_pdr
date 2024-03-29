/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.config.hitmall.alert;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * HIT-MALLアラート用プロパティ　読み込みクラス
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Configuration
@PropertySource(value = {"classpath:config/hitmall/alert/gmoMemberCardAlert.properties",
                "classpath:config/hitmall/alert/multipaymentAlert.properties",
                "classpath:config/hitmall/alert/orderRegistAlert.properties"}, ignoreResourceNotFound = true,
                encoding = "UTF-8")
public class AdminAlertPropertiesConfiguration {

}
