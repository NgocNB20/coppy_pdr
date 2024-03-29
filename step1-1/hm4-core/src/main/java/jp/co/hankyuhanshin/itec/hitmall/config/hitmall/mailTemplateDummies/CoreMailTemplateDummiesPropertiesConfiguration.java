/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.config.hitmall.mailTemplateDummies;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * HIT-MALLメールテンプレートダミープロパティ　読み込みクラス
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Configuration
@PropertySource(value = {"classpath:config/hitmall/mailTemplateDummies/FollowMailCartTransformHelper.properties",
                "classpath:config/hitmall/mailTemplateDummies/GoodsArrivalTransformHelper.properties",
                "classpath:config/hitmall/mailTemplateDummies/InquiryTransformHelper.properties",
                "classpath:config/hitmall/mailTemplateDummies/MemberInfoTransformHelper.properties",
                "classpath:config/hitmall/mailTemplateDummies/MemberRankNotificationTransformHelper.properties",
                "classpath:config/hitmall/mailTemplateDummies/OrderTransformHelper.properties",
                "classpath:config/hitmall/mailTemplateDummies/PointAddBirthTransformHelper.properties",
                "classpath:config/hitmall/mailTemplateDummies/PointNotificationTransformHelper.properties",
                "classpath:config/hitmall/mailTemplateDummies/StringTransformHelper.properties",
                "classpath:config/hitmall/mailTemplateDummies/VoidTransformHelper.properties"},
                ignoreResourceNotFound = true, encoding = "UTF-8")
public class CoreMailTemplateDummiesPropertiesConfiguration {

}
