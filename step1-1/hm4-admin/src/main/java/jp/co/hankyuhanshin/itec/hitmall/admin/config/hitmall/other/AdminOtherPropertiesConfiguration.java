/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.config.hitmall.other;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * HIT-MALLその他プロパティ　読み込みクラス
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Configuration
@PropertySource(value = {"classpath:config/hitmall/other/adminAuth.properties",
                "classpath:config/hitmall/other/settlementMismatch.properties"}, ignoreResourceNotFound = true,
                encoding = "UTF-8")
public class AdminOtherPropertiesConfiguration {

}
