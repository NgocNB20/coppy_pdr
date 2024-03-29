/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.offline.cuenotefcsalemail.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

// 2023-renew No41 from here
@Configuration
@PropertySource(value = {"classpath:config/spring-web.properties", "classpath:config/spring-datasource.properties",
                "classpath:config/spring-mail.properties", "classpath:config/other.properties",
                "classpath:config/hitmall/coreSystem.properties", "classpath:config/hitmall/coreMessages.properties",
                "classpath:config/hitmall/validationMessages.properties",
                "classpath:config/hitmall/mail/mail-template.properties",
                "classpath:config/hitmall/mailTemplateDummies/FollowMailCartTransformHelper.properties",
                "classpath:config/hitmall/mailTemplateDummies/GoodsArrivalTransformHelper.properties",
                "classpath:config/hitmall/mailTemplateDummies/InquiryTransformHelper.properties",
                "classpath:config/hitmall/mailTemplateDummies/MemberInfoTransformHelper.properties",
                "classpath:config/hitmall/mailTemplateDummies/MemberRankNotificationTransformHelper.properties",
                "classpath:config/hitmall/mailTemplateDummies/OrderTransformHelper.properties",
                "classpath:config/hitmall/mailTemplateDummies/PointAddBirthTransformHelper.properties",
                "classpath:config/hitmall/mailTemplateDummies/PointNotificationTransformHelper.properties",
                "classpath:config/hitmall/mailTemplateDummies/StringTransformHelper.properties",
                "classpath:config/hitmall/mailTemplateDummies/VoidTransformHelper.properties",
                "classpath:config/hitmall/IPAddress/IPAddress.properties",
                "classpath:config/hitmall/htmlparse/definedTag.properties",
                "classpath:config/hitmall/htmlparse/dependencyTag.properties",
                "classpath:config/hitmall/htmlparse/exclusiveTag.properties",
                "classpath:config/hitmall/htmlparse/singleTag.properties",
                "classpath:config/hitmall/htmlparse/uniqueTag.properties",
                "classpath:config/hitmall/carrier/CarrierAgent.properties",
                "classpath:config/hitmall/carrier/CarrierEmail.properties",
                "classpath:config/hitmall/alert/messageIdMap.properties",
                "classpath:config/hitmall/alert/MultipaymentAlertMessages.properties"}, ignoreResourceNotFound = true,
                encoding = "UTF-8")
public class CuenotefcSaleMailHmCorePropertiesConfiguration {
}
// 2023-renew No41 to here
