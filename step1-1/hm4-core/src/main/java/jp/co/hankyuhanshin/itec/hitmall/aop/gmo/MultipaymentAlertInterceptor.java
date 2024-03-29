/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.aop.gmo;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hitmall.thymeleaf.MaskConverterViewUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.ComTransactionUtility;
import jp.co.hankyuhanshin.itec.hitmall.web.SessionParamsUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.mail.InstantMailSetting;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.BeanUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.NetworkUtility;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ペイジェント通信時のインタセプタ
 * <pre>
 * 通信前後のログ出力と、エラー時に管理者宛にメール送信を行う
 * </pre>
 *
 * @author y.kawai
 */
@Order(1)
@Aspect
@Component
public class MultipaymentAlertInterceptor {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MultipaymentAlertInterceptor.class);

    /**
     * 定数：アラート検証対象のPREFIX名
     */
    protected static final String TARGET_PREFIX = "paygent_";

    /**
     * 定数：受注ID不明
     */
    protected static final String UNKNOWN_ORDER_ID = "受注ID不明";

    // Paygent Customization from here

    /**
     * インタセプト対象のメソッドをインボークする。
     *
     * @param invocation ポイントカットした実行
     * @return インタセプトしたオブジェクトが返した戻り値
     * @throws Throwable インタセプトしたオブジェクトが投げた例外
     */
    @Around("execution(* jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.ComTransactionLogic.*(..))")
    public Object invoke(ProceedingJoinPoint invocation) throws Throwable {

        // リクエスト電文の情報を取得
        Object inputInfo = getInputInfo(invocation);

        // 通信処理実行
        Object outputInfo = null;
        Throwable communicateError = null;
        writeLog("START\t" + getTelegramKind(inputInfo), Level.INFO);
        try {
            outputInfo = invocation.proceed();
        } catch (Throwable th) {
            LOGGER.error("例外処理が発生しました", th);
            // 通信処理時のエラーは後で処理する
            communicateError = th;
        }

        // 決済IDを取得
        String orderId = getOrderId(inputInfo, outputInfo);
        // 通信APIが返却するエラー情報を取得
        String[] errorInfo = null;
        if (communicateError == null) {
            errorInfo = getErrorInfo(inputInfo, outputInfo);
        }
        // ログ出力
        writeLog(orderId, inputInfo, outputInfo, errorInfo, communicateError);
        // アラートメール送信
        sendAlertMail(orderId, inputInfo, outputInfo, errorInfo, communicateError);

        // 通信処理時のエラーが発生していればスローする
        if (communicateError != null) {
            throw communicateError;
        }
        return outputInfo;
    }

    /**
     * リクエスト電文の情報を取得
     *
     * @param invocation インタセプト対象のメソッド
     * @return リクエスト電文
     */
    protected Object getInputInfo(ProceedingJoinPoint invocation) {
        Object rtn = null;
        try {
            Object[] args = invocation.getArgs();
            rtn = (ComRequestDto) args[0];
            if (rtn == null) {
                // ありえないはずだがログ出力しておく
                writeLog("リクエスト電文の情報取得に失敗しました。", Level.ERROR);
            }
            return rtn;
        } catch (Throwable th) {
            // ありえないはずだがもみ消しておく
            writeLog("リクエスト電文の情報取得に失敗しました。", th, Level.ERROR);
            return null;
        }
    }

    /**
     * 実行する電文種別IDを取得
     *
     * @param inputInfo リクエスト電文の情報
     * @return 電文種別ID
     */
    protected String getTelegramKind(Object inputInfo) {

        String telegramKindTitle = "電文種別ID：";
        String telegramKindNotFound = "不明";
        if (inputInfo == null) {
            return telegramKindTitle + telegramKindNotFound;
        }
        Map<String, String> inputInfoMap = ((ComRequestDto) inputInfo).getRequestMap();
        if (inputInfoMap == null || !inputInfoMap.containsKey("telegram_kind")) {
            return telegramKindTitle + telegramKindNotFound;
        }
        return telegramKindTitle + (StringUtils.isEmpty(inputInfoMap.get("telegram_kind")) ?
                        telegramKindNotFound :
                        inputInfoMap.get("telegram_kind"));
    }

    /**
     * 決済IDを取得する
     *
     * @param inputInfo  リクエスト電文の情報
     * @param outputInfo レスポンス電文の情報
     * @return 決済ID
     */
    protected String getOrderId(Object inputInfo, Object outputInfo) {
        try {
            String orderId = null;
            // リクエスト電文の決済IDを取得
            if (inputInfo != null) {
                if (inputInfo instanceof ComRequestDto) {
                    Map<String, String> request =
                                    (Map<String, String>) getFieldValue((ComRequestDto) inputInfo, "requestMap");
                    if (request != null && request.containsKey("payment_id")) {
                        orderId = request.get("payment_id");
                    }
                }
            }

            // リクエスト電文に決済IDがなければ、レスポンス電文から取得する
            if (StringUtils.isEmpty(orderId)) {
                if (outputInfo != null) {
                    if (outputInfo instanceof ComResultDto) {
                        Map<String, String> resultMap =
                                        (Map<String, String>) getFieldValue((ComResultDto) outputInfo, "resultMap");
                        if (resultMap != null && resultMap.containsKey("payment_id")) {
                            orderId = resultMap.get("payment_id");
                        }
                    }
                }
            }

            return StringUtils.isEmpty(orderId) ? StringUtils.EMPTY : orderId;
        } catch (Throwable th) {
            // ありえないはずだがもみ消しておく
            writeLog("決済IDの取得に失敗しました。", th, Level.ERROR);
            return UNKNOWN_ORDER_ID;
        }
    }

    /**
     * 通信APIが返却するエラー情報を取得
     *
     * @param inputInfo  リクエスト電文の情報
     * @param outputInfo レスポンス電文の情報
     * @return 配列{エラーコード, エラーメッセージ}
     */
    protected String[] getErrorInfo(Object inputInfo, Object outputInfo) {
        try {

            // 通信関連Utility
            ComTransactionUtility comTransactionUtility =
                            ApplicationContextUtility.getBean(ComTransactionUtility.class);
            ComResultDto result = (ComResultDto) outputInfo;

            if (comTransactionUtility.isErrorOccurred(result)) {
                return new String[] {result.getResponseCode(), result.getResponseDetail()};
            }
        } catch (Throwable th) {
            // ありえないはずだがもみ消しておく
            writeLog("通信APIが返却するエラー情報の取得に失敗しました。", th, Level.ERROR);
        }
        return null;
    }

    /**
     * 通信内容をログに出力する
     *
     * @param orderId          決済ID
     * @param inputInfo        リクエスト電文の情報
     * @param outputInfo       レスポンス電文の情報
     * @param errorInfo        エラー情報
     * @param communicateError 通信エラー
     */
    protected void writeLog(String orderId,
                            Object inputInfo,
                            Object outputInfo,
                            String[] errorInfo,
                            Throwable communicateError) {
        String inputLog = createInputLog(inputInfo);
        if (communicateError != null) {
            writeLog(inputLog, Level.ERROR);
            writeLog("通信処理に失敗しました。", communicateError, Level.ERROR);
            return;
        }

        Level level;
        if (outputInfo == null || StringUtils.isEmpty(outputInfo.toString())) {
            // toString（resultXml）が取得できない場合は、通信障害や環境不備など、ペイジェントAPI内で通信が出来なかった場合
            level = Level.ERROR;
        } else if (errorInfo != null) {
            level = Level.WARN;
        } else {
            level = Level.INFO;
        }

        String outputLog = createOutputLog(outputInfo, errorInfo);
        writeLog(inputLog, level);
        writeLog(outputLog, level);
    }

    /**
     * ログ出力
     *
     * @param message メッセージ
     * @param level   ログ出力レベル
     */
    protected void writeLog(String message, Level level) {
        writeLog(message, null, level);
    }

    /**
     * ログ出力
     *
     * @param message メッセージ
     * @param th      例外
     * @param level   ログ出力レベル
     */
    protected void writeLog(String message, Throwable th, Level level) {
        String sessionId = getSessionId();
        String msg = sessionId + "\t" + message;

        if (Level.INFO == level) {
            LOGGER.info(msg, th);
        } else if (Level.WARN == level) {
            LOGGER.warn(msg, th);
        } else if (Level.ERROR == level) {
            LOGGER.error(msg, th);
        } else {
            LOGGER.debug(msg, th);
        }
    }

    /**
     * セッションIDを取得
     *
     * @return セッションID
     */
    protected String getSessionId() {
        HttpServletRequest request =
                        ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                                        .getRequest();
        return request.getSession().getId();
    }

    /**
     * リクエスト電文内容をログ出力用に加工
     * <pre>
     * ペイジェントのリクエスト用のDtoはMap型
     * タブ区切りにする
     * </pre>
     *
     * @param inputInfo リクエスト電文の情報
     * @return 生成したログ出力内容
     */
    protected String createInputLog(Object inputInfo) {
        StringBuilder result = new StringBuilder("INPUT\t");
        if (inputInfo == null) {
            result.append("[requestDto is null]");
        } else {
            if (inputInfo instanceof ComRequestDto) {
                Map<String, String> request = ((ComRequestDto) inputInfo).getRequestMap();
                if (request == null || request.size() == 0) {
                    result.append("[maskedLog is empty]");
                } else {
                    // マスクコンバータ
                    MaskConverterViewUtil util = new MaskConverterViewUtil();

                    for (String name : request.keySet()) {

                        String value = request.get(name);

                        // カード番号をマスクする
                        if ("card_number".equals(name)) {
                            value = util.convertForCreditCardNumber(value);
                        }
                        // セキュリティコード、有効期限をマスクする
                        else if ("card_conf_number".equals(name) || "card_valid_term".equals(name)) {
                            value = util.convert(value);
                        }
                        // 値が空の場合文字列nullを出力する
                        else if (StringUtils.isEmpty(value)) {
                            value = "<null>";
                        }

                        result.append("\t");
                        result.append(name);
                        result.append("\t");
                        result.append(value);
                    }
                }
            }
        }
        return result.toString();
    }

    /**
     * レスポンス電文内容をログ出力用に加工
     * <pre>
     * ペイジェントのレスポンス用のDtoはMap型
     * タブ区切りにする
     * </pre>
     *
     * @param outputInfo レスポンス電文の情報
     * @param errorInfo  エラー情報
     * @return 生成したログ出力内容
     */
    protected String createOutputLog(Object outputInfo, String[] errorInfo) {
        StringBuilder result = new StringBuilder("OUTPUT\t");
        if (outputInfo == null) {
            result.append("[responseDto is null]");
        } else {

            if (errorInfo != null) {
                String msg = createMessage(errorInfo, null);
                result.append("[" + msg + "]\t");
            }

            if (outputInfo instanceof ComResultDto) {
                Map<String, String> resultMap = ((ComResultDto) outputInfo).getResultMap();
                if (resultMap == null || resultMap.size() == 0) {
                    result.append("[maskedLog is empty]");
                } else {
                    // マスクコンバータ
                    MaskConverterViewUtil util = new MaskConverterViewUtil();

                    for (String name : resultMap.keySet()) {

                        String value = resultMap.get(name);

                        // ACS支払人認証要求Htmlはログ容量を圧迫する可能性があるため省略する
                        if ("out_acs_html".equals(name)) {
                            value = "<省略>";
                        }
                        // セキュリティコード、有効期限をマスクする
                        else if ("card_conf_number".equals(name) || "card_valid_term".equals(name)) {
                            value = util.convert(value);
                        }
                        // 値が空の場合文字列nullを出力する
                        else if (StringUtils.isEmpty(value)) {
                            value = "<null>";
                        }

                        result.append("\t");
                        result.append(name);
                        result.append("\t");
                        result.append(value);
                    }
                }
            }
        }
        return result.toString();
    }

    /**
     * メールに記載するメッセージを作成
     *
     * @param errorInfo        エラー情報
     * @param communicateError 通信エラー
     * @return メッセージ
     */
    protected String createMessage(String[] errorInfo, Throwable communicateError) {
        String result = null;
        if (errorInfo != null) {
            result = StringUtils.join(errorInfo, " - ");
        } else if (communicateError != null) {
            String exception = communicateError.getClass().getName();
            String message = communicateError.getMessage();
            result = "通信中に " + exception + " が発生しました。\n" + message;
        }
        return result;
    }

    /**
     * 指定オブジェクトの指定フィールドの値を取得する
     *
     * @param target    指定オブジェクト
     * @param fieldName 指定フィールド
     * @return 指定されたフィールドの値
     */
    protected Object getFieldValue(Object target, String fieldName) {
        Object result = null;
        if (target == null) {
            return result;
        }
        BeanUtility beanUtility = ApplicationContextUtility.getBean(BeanUtility.class);
        return beanUtility.getFieldValue(target, fieldName);
    }

    /**
     * 管理者へのアラートメールを送信する
     *
     * @param orderId          決済ID
     * @param inputInfo        リクエスト電文の情報
     * @param outputInfo       レスポンス電文の情報
     * @param errorInfo        エラー情報
     * @param communicateError 通信エラー
     */
    protected void sendAlertMail(String orderId,
                                 Object inputInfo,
                                 Object outputInfo,
                                 String[] errorInfo,
                                 Throwable communicateError) {
        try {
            // 何も問題が起きていない場合
            if (errorInfo == null && communicateError == null) {
                return;
            }

            // アラートメール送信設定がオフの場合はアラート処理を行わない
            if ("false".equalsIgnoreCase(PropertiesUtil.getSystemPropertiesValue(TARGET_PREFIX + "send_flg"))) {
                writeLog("アラートメール送信設定がオフのため管理者への通知は行われません。", Level.DEBUG);
                return;
            }

            // メールに記載するメッセージを作成
            String text = createMessage(errorInfo, communicateError);
            // メールに記載する電文種別IDを設定
            String api = getTelegramKind(inputInfo);
            // 送信処理
            sendMail(text, api, orderId);

        } catch (Throwable th) {
            // メール送信エラーはログ出力のみ行う
            writeLog("アラートメール送信に失敗しました。", th, Level.ERROR);
        }
    }
    // Paygent Customization to here

    /**
     * 管理者宛のメールを送信する
     *
     * @param text    エラーメッセージ
     * @param api     エラーの発生したAPI
     * @param orderId エラーの発生した OrderId
     * @throws Exception 発生した例外
     */
    protected void sendMail(String text, String api, String orderId) throws Exception {

        //
        // プレースホルダの準備
        //

        LOGGER.debug("プレースホルダを準備");

        Map<String, String> placeHolders = new LinkedHashMap<>();
        // ネットワークHelper取得
        NetworkUtility networkUtility = ApplicationContextUtility.getBean(NetworkUtility.class);

        placeHolders.put("SERVER", networkUtility.getLocalHostName());
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        placeHolders.put("TIME", dateUtility.format(dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));
        placeHolders.put("API", api);
        placeHolders.put("ERROR", text);
        placeHolders.put("ORDERID", orderId);

        /*
         * ユーザ情報をメールに追加 バッチ等、オンライン以外から呼ばれている想定を考慮しての徹底的に nullチェックと例外処理
         * 例外発生時は、なかったことにし、WARNログだけ出力する。
         */
        // null があるので、初期化する
        placeHolders.put("SESSIONID", "");
        placeHolders.put("AUI", "");
        placeHolders.put("MEMBERINFOSEQ", "");

        try {
            SessionParamsUtil sessionParamsUtil = new SessionParamsUtil();
            String aui = sessionParamsUtil.getAccessUid();
            String sessionId = sessionParamsUtil.getSessionId();
            String memberInfoSeq = sessionParamsUtil.getMemberInfoSeq();
            if (StringUtils.isNotEmpty(sessionId)) {
                placeHolders.put("SESSIONID", sessionId);
            }
            if (StringUtils.isNotEmpty(aui)) {
                placeHolders.put("AUI", aui);
            }
            if (StringUtils.isNotEmpty(memberInfoSeq)) {
                placeHolders.put("MEMBERINFOSEQ", memberInfoSeq);
            }

        } catch (Exception e) {
            LOGGER.warn("アラートメールでユーザ情報の取得に失敗しました。バッチからの呼び出しの場合は、ユーザー情報の取得は出来ません。", e);
        }

        // メールに記載するシステム名
        String mailSystem = PropertiesUtil.getSystemPropertiesValue(TARGET_PREFIX + "mail_system");
        placeHolders.put("SYSTEM", mailSystem);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("SERVER:" + placeHolders.get("SERVER"));
            LOGGER.debug("TIME:" + placeHolders.get("TIME"));
            LOGGER.debug("API:" + placeHolders.get("API"));
            LOGGER.debug("ERROR:" + placeHolders.get("ERROR"));
            LOGGER.debug("ORDERID:" + placeHolders.get("ORDERID"));

            LOGGER.debug("SESSIONID:" + placeHolders.get("SESSIONID"));
            LOGGER.debug("MEMBERINFOSEQ:" + placeHolders.get("MEMBERINFOSEQ"));
            LOGGER.debug("AUI:" + placeHolders.get("AUI"));
            LOGGER.debug("SYSTEM:" + placeHolders.get("SYSTEM"));
        }

        //
        // 簡易メール送信の準備
        //

        LOGGER.debug("簡易メールの設定を準備");

        InstantMailSetting setting = new InstantMailSetting();
        setting.setServer(PropertiesUtil.getSystemPropertiesValue(TARGET_PREFIX + "mail_server"));
        setting.setMailFrom(PropertiesUtil.getSystemPropertiesValue(TARGET_PREFIX + "mail_from"));
        setting.setMailResource(PropertiesUtil.getSystemPropertiesValue(TARGET_PREFIX + "template_file"));
        setting.setNotificationReceivers(PropertiesUtil.getSystemPropertiesValue(TARGET_PREFIX + "recipient")
                                                       .replaceAll("\"", "")
                                                       .split(" *, *"));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("mail_server:" + setting.getServer());
            LOGGER.debug("mail_from:" + setting.getMailFrom());
            LOGGER.debug("template_file:" + setting.getMailResource());
            LOGGER.debug("recipient:" + PropertiesUtil.getSystemPropertiesValue(TARGET_PREFIX + "recipient"));
        }

        //
        // メールを送信する
        //
        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);
            Map<String, Object> mailContentsMap = new HashMap<>();

            mailContentsMap.put("mailContentsMap", placeHolders);

            mailDto.setMailTemplateType(HTypeMailTemplateType.MULTI_PAYMENT_ALERT);
            mailDto.setFrom(setting.getMailFrom());
            mailDto.setToList(setting.getNotificationReceivers());
            mailDto.setSubject("【" + mailSystem + " 要確認】ペイジェント決済エラー報告");
            mailDto.setMailContentsMap(mailContentsMap);

            MailSendService mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
            mailSendService.execute(mailDto);

            LOGGER.info("アラートメールを送信しました。");
        } catch (Exception e) {
            LOGGER.warn("アラートメール送信に失敗しました。", e);
            throw e;
        }
    }
}
