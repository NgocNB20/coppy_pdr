//  Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderRegistFailureAddressBookSendMailLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.SendAlertMailUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.mail.InstantMailSetting;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * PDR#011 08_データ連携（顧客情報）
 *
 * <pre>
 * 住所録登録失敗通知メール ロジック
 * </pre>
 *
 * @author satoh
 */
@Component
public class OrderRegistFailureAddressBookSendMailLogicImpl extends AbstractShopLogic
                implements OrderRegistFailureAddressBookSendMailLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRegistFailureAddressBookSendMailLogicImpl.class);

    /**
     * アラートメール送信Utility
     */
    private final SendAlertMailUtility sendAlertMailUtility;

    @Autowired
    public OrderRegistFailureAddressBookSendMailLogicImpl(SendAlertMailUtility sendAlertMailUtility) {
        this.sendAlertMailUtility = sendAlertMailUtility;
    }

    /**
     * 住所録の登録に失敗した場合のメールを送信します。
     *
     * <pre>
     * プロパティファイルでメールの送信有に設定されていた場合は
     * 管理者宛にメールの送信を行います。
     * </pre>
     *
     * @param customerNo 顧客番号
     */
    public void execute(Integer customerNo) {
        boolean isSendMail = FLG_ON.equals(PropertiesUtil.getSystemPropertiesValue(KEY_SEND_MAIL_FLG));

        // メール送信を行わない場合は処理終了
        if (!isSendMail) {
            LOGGER.info("住所録登録失敗通知メールの送信を行いませんでした。");
            return;
        }

        try {
            final Map<String, String> valueMap = new HashMap<>();
            valueMap.put("CUSTOMER_NO", customerNo.toString());

            if (LOGGER.isDebugEnabled()) {
                valueMap.put("DEBUG", "1");
            } else {
                valueMap.put("DEBUG", "0");
            }

            Map<String, Object> mailContentsMap = new HashMap<>();
            mailContentsMap.put("mailContentsMap", valueMap);

            InstantMailSetting setting = new InstantMailSetting();
            setting.setServer(PropertiesUtil.getSystemPropertiesValue("mail_server"));
            setting.setMailFrom(PropertiesUtil.getSystemPropertiesValue("mail_from"));
            setting.setMailResource(PropertiesUtil.getSystemPropertiesValue("template_file"));
            setting.setNotificationReceivers(
                            PropertiesUtil.getSystemPropertiesValue("recipient").replaceAll("\"", "").split(" *, *"));

            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            mailDto.setMailTemplateType(HTypeMailTemplateType.ORDER_REGIST_FAILURE_ADDRESS_BOOK_ALERT);
            mailDto.setFrom(setting.getMailFrom());
            mailDto.setToList(setting.getNotificationReceivers());
            mailDto.setSubject("【ピーディーアール　オンラインショップ】住所録登録失敗通知");
            mailDto.setMailContentsMap(mailContentsMap);

            sendAlertMailUtility.sendAlertMail(PROP_FILE_NAME, LOGGER, MAIL_NAME, valueMap,
                                               HTypeMailTemplateType.ORDER_REGIST_FAILURE_ADDRESS_BOOK_ALERT
                                              );
            LOGGER.info("管理者へ通知メールを送信しました。");

        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);
        }

    }
}
//  Customization to here
