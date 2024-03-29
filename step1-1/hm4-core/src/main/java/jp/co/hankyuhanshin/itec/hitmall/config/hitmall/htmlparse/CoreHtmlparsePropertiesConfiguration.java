/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.config.hitmall.htmlparse;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * HIT-MALLHTMLパース用プロパティ 読み込みクラス
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Configuration
@PropertySource(value = {"classpath:config/hitmall/htmlparse/definedTag.properties",
                "classpath:config/hitmall/htmlparse/dependencyTag.properties",
                "classpath:config/hitmall/htmlparse/exclusiveTag.properties",
                "classpath:config/hitmall/htmlparse/singleTag.properties",
                "classpath:config/hitmall/htmlparse/uniqueTag.properties"}, ignoreResourceNotFound = true,
                encoding = "UTF-8")
public class CoreHtmlparsePropertiesConfiguration {

}
