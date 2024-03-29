/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.CommunicateResult;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.SettlememtMismatchCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 請求情報の不整合チェックLogic
 *
 * @author kk32102
 */
@Component
public class SettlememtMismatchCheckLogicImpl extends AbstractShopLogic implements SettlememtMismatchCheckLogic {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SettlememtMismatchCheckLogicImpl.class);

    @Override
    public void initOrderUpdate(String orderCode) {
        init(PROC_TYPE_ORDER_UPDATE, orderCode);
    }

    @Override
    public void initReAuth(String orderCode) {
        init(PROC_TYPE_RE_AUTH, orderCode);
    }

    @Override
    public void initOrderCancel(String orderCode) {
        init(PROC_TYPE_ORDER_CANCEL, orderCode);
    }

    @Override
    public void initSettlementMethodChange(String orderCode) {
        init(PROC_TYPE_SETTLEMENT_METHOD_CHANGE, orderCode);
    }

    @Override
    public void initShipmentRegist(String orderCode) {
        init(PROC_TYPE_SHIPMENT_REGIST, orderCode);
    }

    /**
     * 初期処理
     *
     * @param processType HIT-MALLの処理区分
     * @param orderCode   受注番号
     */
    protected void init(String processType, String orderCode) {
        CommunicateResult communicateResult = ApplicationContextUtility.getBean(CommunicateResult.class);
        communicateResult.init(processType, orderCode);
    }

    @Override
    public void execute() {
        try {
            CommunicateResult communicateResult = ApplicationContextUtility.getBean(CommunicateResult.class);
            if (communicateResult.hasCancelError()) {
                // 正常終了の場合、取消エラーが発生している場合は通知する
                sendMail(communicateResult, null);
            }
        } catch (Throwable th) {
            LOGGER.error("請求不整合報告処理中にシステムエラーが発生しました。", th);
        }
    }

    @Override
    public void execute(Throwable error) {
        try {
            CommunicateResult communicateResult = ApplicationContextUtility.getBean(CommunicateResult.class);
            if (communicateResult.isCommunicate()) {
                // 異常終了の場合、処理済みの処理がある通知する
                sendMail(communicateResult, error);
            }
        } catch (Throwable th) {
            LOGGER.error("請求不整合報告処理中にシステムエラーが発生しました。", th);
        }
    }

    /**
     * 請求不整合報告メールを送信する
     *
     * @param communicateResult 決済代行会社との通信結果
     * @param error             HIT-MALLの処理中に発生した例外
     * @throws Exception 当処理で発生した例外
     */
    protected void sendMail(CommunicateResult communicateResult, Throwable error) throws Exception {
        // プレースホルダの準備
        Map<String, String> placeHolders = createPlaceHolders(communicateResult, error);

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

            mailDto.setMailTemplateType(HTypeMailTemplateType.SETTLEMENT_MISMATCH);
            mailDto.setFrom(mailFrom);
            mailDto.setToList(notificationReceivers);
            mailDto.setSubject("【" + mailSystem + " 要確認】請求不整合報告");
            mailDto.setMailContentsMap(mailContentsMap);

            MailSendService mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
            mailSendService.execute(mailDto);

            LOGGER.info("請求不整合報告メールを送信しました。");
        } catch (Exception e) {
            LOGGER.warn("請求不整合報告メール送信に失敗しました。", e);
            throw e;
        }
    }

    /**
     * プレースホルダの作成
     *
     * @param communicateResult 決済代行会社との通信結果
     * @param error             HIT-MALLの処理中に発生した例外
     * @return プレースホルダ
     */
    protected Map<String, String> createPlaceHolders(CommunicateResult communicateResult, Throwable error) {
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        Map<String, String> placeHolders = new LinkedHashMap<>();
        placeHolders.put("TIME", dateUtility.format(dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));
        placeHolders.put("SERVER", PropertiesUtil.getSystemPropertiesValue("system.name"));
        placeHolders.put("ORDERCODE", communicateResult.getOrderCode());
        placeHolders.put("PROCESS", PropertiesUtil.getSystemPropertiesValue(createProcessKey(communicateResult)));
        placeHolders.put("MULPAY_PROCESS", communicateResult.writeTranInfo());
        String errorInfo;
        String recoveryKey;
        if (error != null) {
            errorInfo = createErrorInfo(error);
            recoveryKey = createRecovetyMsgKey(communicateResult, false);
        } else {
            errorInfo = communicateResult.writeCancelErrorInfo();
            recoveryKey = createRecovetyMsgKey(communicateResult, true);
        }
        placeHolders.put("ERROR", errorInfo);
        placeHolders.put("RECOVERY", PropertiesUtil.getSystemPropertiesValue(recoveryKey));

        // メールに記載するシステム名
        placeHolders.put("SYSTEM", PropertiesUtil.getSystemPropertiesValue("mail_system"));

        return placeHolders;
    }

    /**
     * メールに記載するエラー情報を返却
     *
     * @param error HIT-MALLの処理中に発生した例外
     * @return エラー情報
     */
    protected String createErrorInfo(Throwable error) {
        if (error instanceof AppLevelListException) {
            List<String> errorInfo = new ArrayList<>();
            for (AppLevelException ale : ((AppLevelListException) error).getErrorList()) {
                errorInfo.add(ale.getMessage());
            }
            return StringUtils.join(errorInfo.iterator(), "\r\n");
        } else {
            return error.toString();
        }
    }

    /**
     * メールに記載するリカバリー方法を取得する為のプロパティキーを返却
     *
     * @param communicateResult 決済代行会社との通信結果
     * @param isCancelError     取消エラーが発生した場合の正常終了か？
     * @return プロパティキー
     */
    protected String createRecovetyMsgKey(CommunicateResult communicateResult, boolean isCancelError) {
        StringBuilder result = new StringBuilder("recovery.");
        if (isCancelError) {
            result.append("cancel.");
        }
        result.append(communicateResult.getProcessType());
        result.append(createSubKey(communicateResult));
        return result.toString();
    }

    /**
     * メールに記載するHIT-MALLの処理名を取得する為のプロパティキーを返却
     *
     * @param communicateResult 決済代行会社との通信結果
     * @return プロパティキー
     */
    protected String createProcessKey(CommunicateResult communicateResult) {
        StringBuilder result = new StringBuilder("proc.");
        result.append(communicateResult.getProcessType());
        result.append(createSubKey(communicateResult));
        return result.toString();
    }

    /**
     * 受注修正の処理の場合に、決済方法変更か金額変更かを切り分ける為のプロパティキーを返却
     *
     * @param communicateResult 決済代行会社との通信結果
     * @return プロパティキー
     */
    protected String createSubKey(CommunicateResult communicateResult) {
        if (PROC_TYPE_ORDER_UPDATE.equals(communicateResult.getProcessType())) {
            if (communicateResult.hasChangeTran()) {
                return ".change";
            } else {
                return ".cancel";
            }
        } else {
            return "";
        }
    }

}
