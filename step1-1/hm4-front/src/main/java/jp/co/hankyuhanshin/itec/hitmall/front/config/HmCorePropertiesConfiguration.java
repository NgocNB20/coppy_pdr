package jp.co.hankyuhanshin.itec.hitmall.front.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
                "classpath:config/hitmall/htmlparse/definedTag.properties",
                "classpath:config/hitmall/htmlparse/dependencyTag.properties",
                "classpath:config/hitmall/htmlparse/exclusiveTag.properties",
                "classpath:config/hitmall/htmlparse/singleTag.properties",
                "classpath:config/hitmall/htmlparse/uniqueTag.properties",
                "classpath:config/hitmall/carrier/CarrierAgent.properties",
                "classpath:config/hitmall/carrier/CarrierEmail.properties",
                "classpath:config/hitmall/alert/messageIdMap.properties",
                "classpath:config/hitmall/alert/MultipaymentAlertMessages.properties",
                "classpath:config/hitmall/device/smartPhone.properties"
                // PDR Migrate Customization from here
                , "classpath:config/hitmall/alert/OrderRegistFailureAddressBookAlert.properties",
                "classpath:config/hitmall/alert/memberUnregistInquiryAlert.properties",
                "classpath:config/hitmall/pageaccess/pageaccess.properties"
                // PDR Migrate Customization to here
                // Paygent Customization from here
                , "classpath:modenv.properties"
                // Paygent Customization to here
}, ignoreResourceNotFound = true, encoding = "UTF-8")
public class HmCorePropertiesConfiguration {
}
