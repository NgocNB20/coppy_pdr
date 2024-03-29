package jp.co.hankyuhanshin.itec.hitmall.config.mailtemplate;

import jp.co.hankyuhanshin.itec.hitmall.application.mailtemplate.MailTemplateTypeEntry;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MailTemplateTypeConfiguration {

    @Bean("mailTemplateTypeEntryList")
    public List<MailTemplateTypeEntry> mailTemplateTypeEntryList() {
        List<MailTemplateTypeEntry> mailTemplateTypeEntries = new ArrayList<>();

        MailTemplateTypeEntry orderConfirm = new MailTemplateTypeEntry();
        orderConfirm.setTemplateType(HTypeMailTemplateType.ORDER_CONFIRMATION);
        orderConfirm.setOrder(70);
        mailTemplateTypeEntries.add(orderConfirm);

        MailTemplateTypeEntry shipmentNotification = new MailTemplateTypeEntry();
        shipmentNotification.setTemplateType(HTypeMailTemplateType.SHIPMENT_NOTIFICATION);
        shipmentNotification.setOrder(80);
        mailTemplateTypeEntries.add(shipmentNotification);

        MailTemplateTypeEntry settlementReminder = new MailTemplateTypeEntry();
        settlementReminder.setTemplateType(HTypeMailTemplateType.SETTLEMENT_REMINDER);
        settlementReminder.setOrder(100);
        mailTemplateTypeEntries.add(settlementReminder);

        MailTemplateTypeEntry settlementExpirationNotification = new MailTemplateTypeEntry();
        settlementExpirationNotification.setTemplateType(HTypeMailTemplateType.SETTLEMENT_EXPIRATION_NOTIFICATION);
        settlementExpirationNotification.setOrder(110);
        mailTemplateTypeEntries.add(settlementExpirationNotification);

        // 2023-renew general-mail from here
        MailTemplateTypeEntry emailModificationComplete = new MailTemplateTypeEntry();
        emailModificationComplete.setTemplateType(HTypeMailTemplateType.EMAIL_MODIFICATION_COMPLETE);
        emailModificationComplete.setOrder(120);
        mailTemplateTypeEntries.add(emailModificationComplete);

        MailTemplateTypeEntry sendCustomerNoPassword = new MailTemplateTypeEntry();
        sendCustomerNoPassword.setTemplateType(HTypeMailTemplateType.SEND_CUSTOMERNO_PASSWORD);
        sendCustomerNoPassword.setOrder(130);
        mailTemplateTypeEntries.add(sendCustomerNoPassword);

        MailTemplateTypeEntry coreMemberInfoModificationComplete = new MailTemplateTypeEntry();
        coreMemberInfoModificationComplete.setTemplateType(HTypeMailTemplateType.CORE_MEMBERINFO_MODIFICATION_COMPLETE);
        coreMemberInfoModificationComplete.setOrder(140);
        mailTemplateTypeEntries.add(coreMemberInfoModificationComplete);

        MailTemplateTypeEntry generalMember = new MailTemplateTypeEntry();
        generalMember.setTemplateType(HTypeMailTemplateType.GENERAL_MEMBER);
        generalMember.setOrder(150);
        mailTemplateTypeEntries.add(generalMember);
        // 2023-renew general-mail to here

        return mailTemplateTypeEntries;
    }
}
