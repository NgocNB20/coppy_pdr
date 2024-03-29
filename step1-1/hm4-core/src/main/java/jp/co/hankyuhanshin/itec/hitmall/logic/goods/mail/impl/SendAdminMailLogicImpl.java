/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.mail.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailDeliveryStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.restock.ReStockDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiGetDeliveryResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockAddImportListDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.mail.SendAdminMailLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock.ReStockAddImportListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.mail.InstantMailSetting;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 入荷お知らせメールアドレス帳登録リスト取得ロジック
 *
 * @author st75001
 * @version $Revision: 1.3 $
 */
@Component
public class SendAdminMailLogicImpl extends AbstractShopLogic implements SendAdminMailLogic {

    /**
     * ロガー
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(SendAdminMailLogic.class);

    /**
     * dicon 設定： メール送信設定
     */
    protected final InstantMailSetting mailSetting;

    /**
     * メール送信サービス
     */
    protected final MailSendService mailSendService;

    @Autowired
    public SendAdminMailLogicImpl(Environment environment) {
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
        this.mailSetting = new InstantMailSetting(environment.getProperty("mail.setting.restock.sendmail.smtp.server"),
                environment.getProperty("mail.setting.restock.sendmail.mail.from"),
                null, null, Collections.singletonList(
                environment.getProperty("mail.setting.restock.sendmail.mail.receivers")));
    }

    /**
     *
     * @param detail 処理結果
     * @param processName 処理名
     * @param mailTemplateType メールテンプレートタイプ
     * @param errFlg エラーフラグ true：エラー、false：正常
     */
    @Override
    public void execute(String detail, String processName, HTypeMailTemplateType mailTemplateType, boolean errFlg) {

        try {

            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();
            // メール本文の設定
            Map<String, String> valueMap = new HashMap<String, String>();
            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            //ショップ名
            valueMap.put("SYSTEM", systemName);
            // 処理名
            valueMap.put("PROCESS_NAME", processName);
            // 処理結果
            valueMap.put("RESULT", detail);

            mailContentsMap.put("admin", valueMap);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            if (errFlg) {
                mailDto.setMailTemplateType(mailTemplateType);
                mailDto.setSubject("【" + systemName + "】" + "【" + processName + "】報告 [*]");
            } else {
                mailDto.setMailTemplateType(mailTemplateType);
                mailDto.setSubject("【" + systemName + "】" + "【" + processName + "】報告");
            }
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);
            LOGGER.info("管理者へ通知メールを送信しました。");

        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);
        }
    }

    /**
     * 配信予約メール処理結果成功内容生成
     */
    @Override
    public String createSuccessDeliveryReserveMailDetail(Integer sendMailRequestCount,Integer sendMailMemberCount,List<String> skipGoodsCodeList,List<Integer> skipCustomerNoList,List<String> errDetailsList) {

        StringBuilder resultDetail = new StringBuilder();
        resultDetail.append("処理結果：正常終了");
        resultDetail.append("\n\n配信予約件数：" + sendMailMemberCount + "件");
        resultDetail.append("\n配信予約成功件数：" + sendMailRequestCount + "件");
        Integer sendMailSkipCount = sendMailMemberCount - sendMailRequestCount;
        resultDetail.append("\n配信除外件数：" + sendMailSkipCount + "件");

        // 配信予約除外理由
        if(errDetailsList.size() > 0){
            resultDetail.append("\n\n\n▼配信除外理由");
            for (int i = 0; i < errDetailsList.size(); i++) {
                resultDetail.append("\n" + errDetailsList.get(i));
            }
        }
        // 処理結果メールメッセージに格納
        return resultDetail.toString();
    }

    /**
     * 処理結果異常終了内容生成
     *
     * @param exceptionName 発生したException
     * @param processName 処理名
     * @param recoveryMethod リカバリー方法
     */
    @Override
    public String createFailedMailDetail(String exceptionName, String processName, String recoveryMethod) {

        StringBuilder resultDetail = new StringBuilder();
        resultDetail.append(processName + "が終了しました。");
        resultDetail.append("\n処理結果：異常終了");
        resultDetail.append("\n\n予期せぬエラーが発生したため異常終了しています。");
        resultDetail.append("\n[" + exceptionName + "]");
        resultDetail.append("\n\n【リカバリ方法】");
        resultDetail.append("\n[" + recoveryMethod + "]");
        // 処理結果メールメッセージに格納
        return resultDetail.toString();
    }

    /**
     * 処理結果成功内容生成
     * @param cuenoteApiGetDeliveryResponseDto 配信情報取得APIレスポンスDTO
     * @param deliveryStatus 配信ステータス
     */
    @Override
    public String createSuccessDeliveryConfirmMailDetail(CuenoteApiGetDeliveryResponseDto cuenoteApiGetDeliveryResponseDto,
                                                         HTypeMailDeliveryStatus deliveryStatus) {

        StringBuilder resultDetail = new StringBuilder();

        if (cuenoteApiGetDeliveryResponseDto == null || deliveryStatus == null) {
            // 対象件数0のため0件でメール送信
            resultDetail.append("処理結果：正常終了");
            resultDetail.append("\n配信中のメールはありませんでした。");
            return resultDetail.toString();
        }

        Integer statSum = cuenoteApiGetDeliveryResponseDto.getStatSuccess()
                          + cuenoteApiGetDeliveryResponseDto.getStatDeferral()
                          + cuenoteApiGetDeliveryResponseDto.getStatFailure();
        resultDetail.append("処理結果：正常終了");
        resultDetail.append("\n配信状況：「" + deliveryStatus.getLabel() + "」");
        resultDetail.append("\n\n- 配信ID：" + cuenoteApiGetDeliveryResponseDto.getDeliveryId());
        resultDetail.append("\n配信確認件数：" + statSum + "件");
        resultDetail.append("\n-----");
        resultDetail.append("\n配信済み件数：" + cuenoteApiGetDeliveryResponseDto.getStatSuccess() + "件");
        resultDetail.append("\n\n配信一時失敗件数：" + cuenoteApiGetDeliveryResponseDto.getStatDeferral() + "件");
        resultDetail.append("\n\n配信失敗件数：" + cuenoteApiGetDeliveryResponseDto.getStatFailure() + "件");

        // 処理結果メールメッセージに格納
        return resultDetail.toString();
    }
}
