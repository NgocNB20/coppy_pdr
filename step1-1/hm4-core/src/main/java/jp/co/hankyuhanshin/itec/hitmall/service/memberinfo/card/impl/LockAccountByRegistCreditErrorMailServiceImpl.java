/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2023 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.card.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.LockAccountByRegistCreditErrorMailDto;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.card.LockAccountByRegistCreditErrorMailService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * アカウントロック通知（クレジット登録エラー）メール送信サービス
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Service
// 2023-renew No60 from here
public class LockAccountByRegistCreditErrorMailServiceImpl implements LockAccountByRegistCreditErrorMailService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LockAccountByRegistCreditErrorMailServiceImpl.class);

    /**
     * アカウントロック通知（クレジット登録エラー）メールを送信する
     *
     * @param lockAccountByRegistCreditErrorMailDto アカウントロック通知（クレジット登録エラー）メールDto
     */
    @Override
    public void execute(LockAccountByRegistCreditErrorMailDto lockAccountByRegistCreditErrorMailDto) {
        sendMail(lockAccountByRegistCreditErrorMailDto);
    }

    /**
     * 管理者メール送信
     *
     * @param lockAccountByRegistCreditErrorMailDto アカウントロック通知（クレジット登録エラー）メールDto
     */
    protected void sendMail(LockAccountByRegistCreditErrorMailDto lockAccountByRegistCreditErrorMailDto) {
        // プレースホルダの準備
        Map<String, String> placeHolders = createPlaceHolders(lockAccountByRegistCreditErrorMailDto);

        // 簡易メール送信の準備
        String mailFrom = PropertiesUtil.getSystemPropertiesValue("mail_from");
        List<String> notificationReceivers = Arrays.asList(PropertiesUtil.getSystemPropertiesValue("recipient")
                                                                         .replaceAll("\"", "")
                                                                         .split(" *, *"));
        String mailSystem = PropertiesUtil.getSystemPropertiesValue("mail_system");

        // メールを送信する
        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);
            Map<String, Object> mailContentsMap = new HashMap<>();

            mailContentsMap.put("mailContentsMap", placeHolders);

            mailDto.setMailTemplateType(HTypeMailTemplateType.LOCK_ACCOUNT_BY_REGIST_CREDIT_ERROR);
            mailDto.setFrom(mailFrom);
            mailDto.setToList(notificationReceivers);
            mailDto.setSubject("【" + mailSystem + " 要確認】アカウントロック通知");
            mailDto.setMailContentsMap(mailContentsMap);

            MailSendService mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
            mailSendService.execute(mailDto);

            LOGGER.info("アカウントロック通知（クレジット登録エラー）メールを送信しました。");
        } catch (Exception e) {
            LOGGER.warn("アカウントロック通知（クレジット登録エラー）メール送信に失敗しました。", e);
            throw e;
        }
    }

    /**
     * プレースホルダの作成
     *
     * @param lockAccountByRegistCreditErrorMailDto アカウントロック通知（クレジット登録エラー）メールDto
     * @return プレースホルダ
     */
    protected Map<String, String> createPlaceHolders(LockAccountByRegistCreditErrorMailDto lockAccountByRegistCreditErrorMailDto) {
        Map<String, String> placeHolders = new LinkedHashMap<>();

        placeHolders.put("CUSTOMER_NO", lockAccountByRegistCreditErrorMailDto.getCustomerNo().toString());
        placeHolders.put("OFFICE_NAME", lockAccountByRegistCreditErrorMailDto.getOfficeName());

        return placeHolders;
    }

}
// 2023-renew No60 to here
