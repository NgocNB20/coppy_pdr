// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoUnregistInquirySendMailLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.SendAlertMailUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * PDR#011 08_データ連携（顧客情報）
 *
 * <pre>
 * 未登録会員照会発生メール ロジック
 * </pre>
 *
 * @author satoh
 * @version $Revision:$
 */

@Component
public class MemberInfoUnregistInquirySendMailLogicImpl extends AbstractShopLogic
                implements MemberInfoUnregistInquirySendMailLogic {

    /**
     * ロガー
     */
    protected Logger log = LoggerFactory.getLogger(MemberInfoUnregistInquirySendMailLogic.class);

    /**
     * アラートメール送信Utility
     */
    private final SendAlertMailUtility sendAlertMailUtility;

    @Autowired
    public MemberInfoUnregistInquirySendMailLogicImpl(SendAlertMailUtility sendAlertMailUtility) {
        this.sendAlertMailUtility = sendAlertMailUtility;
    }

    /**
     * 未登録会員照会発生メールを送信します。
     *
     * <pre>
     * プロパティファイルでメールの送信有に設定されていた場合は
     * 管理者宛にメールの送信を行います。
     * </pre>
     *
     * @param customerNo    顧客番号
     * @param memberInfoTel 会員TEL
     */
    @Override
    public void execute(String customerNo, String memberInfoTel) {

        boolean isSendMail = FLG_ON.equals(PropertiesUtil.getSystemPropertiesValue(KEY_SEND_MAIL_FLG));

        // メール送信を行わない場合は処理終了
        if (!isSendMail) {
            log.info("未登録会員参照発生メールの送信を行いませんでした。");
            return;
        }

        // PDR Migrate Customization from here
        String mailFrom = PropertiesUtil.getSystemPropertiesValue("mail_from");

        String recipientMail = PropertiesUtil.getSystemPropertiesValue("recipient");
        // PDR Migrate Customization to here

        // メール本文の設定
        Map<String, String> valueMap = new HashMap<String, String>();
        // 顧客番号
        valueMap.put("CUSTOMER_NO", customerNo);
        // 会員電話番号
        valueMap.put("MEMBERINFO_TEL", memberInfoTel);

        Map<String, Object> mailContentsMap = new HashMap<>();
        mailContentsMap.put("mailContentsMap", valueMap);

        MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

        // PDR Migrate Customization from here
        mailDto.setSubject("【ピーディーアール　オンラインショップ】未登録会員の照会発生");
        // PDR Migrate Customization to here
        mailDto.setMailTemplateType(HTypeMailTemplateType.MEMBERINFO_UNREGIST_INQUIRY);
        mailDto.setFrom(mailFrom);
        mailDto.setToList(Collections.singletonList(recipientMail));
        mailDto.setMailContentsMap(mailContentsMap);

        // メール送信実施
        sendAlertMailUtility.sendAlertMail(
                        PROP_FILE_NAME, log, MAIL_NAME, valueMap, HTypeMailTemplateType.MEMBERINFO_UNREGIST_INQUIRY);

    }
}
// PDR Migrate Customization to here
