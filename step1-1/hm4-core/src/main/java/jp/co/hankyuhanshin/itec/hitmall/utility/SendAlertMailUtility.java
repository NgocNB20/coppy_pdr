/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.utility;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hmbase.util.mail.InstantMailSetting;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * PDR#011 08_データ連携（顧客情報）
 *
 * <pre>
 * アラートメール送信Utilityクラス
 * </pre>
 *
 * @author Pham Quang Dieu
 */
@Component
public class SendAlertMailUtility {

    private static final String PREFIX = "/config/hitmall/alert/";

    /**
     * 設定ファイルを読み込み、
     * アラートメールを送信します。
     *
     * @param propFileNm プロパティファイル名
     * @param log        ログファイル
     * @param mailNm     メール名
     * @param valueMap   メール本文に設定する値
     */
    public void sendAlertMail(String propFileNm,
                              Logger log,
                              String mailNm,
                              Map<String, String> valueMap,
                              HTypeMailTemplateType mailTemplateType) {

        // メール送信情報の設定
        InstantMailSetting setting = new InstantMailSetting();
        Map<String, Object> props = getAlertMailProperties(propFileNm, log, mailNm);
        setting.setServer(props.get("mail_server").toString());
        setting.setMailFrom(props.get("mail_from").toString());
        setting.setMailResource(props.get("template_file").toString());
        setting.setNotificationReceivers((String[]) props.get("recipient"));

        try {

            // メール送信実施
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            mailDto.setMailTemplateType(mailTemplateType);
            mailDto.setFrom(setting.getMailFrom());
            mailDto.setToList(setting.getNotificationReceivers());
            mailDto.setSubject(mailNm);

            Map<String, Object> mailContentsMap = new HashMap<>();
            mailContentsMap.put("mailContentsMap", valueMap);

            mailDto.setMailContentsMap(mailContentsMap);

            MailSendService mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
            mailSendService.execute(mailDto);

            log.info("アラートメールを送信しました。 / " + mailNm);
        } catch (Exception e) {
            log.warn("アラートメールの送信に失敗しました。/ " + mailNm, e);
        }
    }

    /**
     * アラートメール送信用パラメータ取得
     *
     * @param propFileNm プロパティファイル名
     * @param log        ログファイル
     * @param mailNm     メール名
     * @return アラートメール送信用パラメータ保持MAP
     */
    private Map<String, Object> getAlertMailProperties(String propFileNm, Logger log, String mailNm) {

        Map<String, Object> mailProps = new HashMap<>();
        Properties props = new Properties();
        try (InputStream in = SendAlertMailUtility.class.getResourceAsStream(PREFIX + propFileNm)) {
            props.load(new InputStreamReader(in, Charset.forName("UTF8")));
        } catch (Exception e) {
            log.warn("アラートメール送信用プロパティの読込に失敗しました。 " + mailNm, e);
        }
        mailProps.put("mail_server", props.getProperty("mail_server"));
        mailProps.put("mail_from", props.getProperty("mail_from"));
        mailProps.put("template_file", props.getProperty("template_file"));
        mailProps.put("recipient", props.getProperty("recipient").replaceAll("\"", "").split(" *, *"));

        return mailProps;
    }
}
