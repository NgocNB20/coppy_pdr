/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.aop.gmo;

import com.gmo_pg.g_pay.client.output.ErrHolder;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.mail.InstantMailSetting;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.NetworkUtility;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * GMO会員カード通信エラー時にメールを送信するインタセプタ
 */
@Order(3)
@Aspect
@Component
public class GmoMemberCardAlertInterceptor {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GmoMemberCardAlertInterceptor.class);

    /**
     * 定数：メッセージリソースパス
     */
    protected static final String RESOURCE = "MultipaymentAlertMessages.properties";

    /**
     * 定数：アラート検証対象のPREFIX名
     */
    protected static final String TARGET_PREFIX = "gmoMember_";

    /**
     * インタセプト対象のメソッドをインボークする。
     *
     * @param invocation ポイントカットした実行
     * @return インタセプトしたオブジェクトが返した戻り値
     * @throws Throwable インタセプトしたオブジェクトが投げた例外
     */
    @Around("execution(* com.gmo_pg.g_pay.client.PaymentClient.*(..))")
    public Object invoke(ProceedingJoinPoint invocation) throws Throwable {

        String methodName = invocation.getSignature().getName();

        Object returnValue = null;
        String memberId = null;
        String cardSeq = null;

        try {

            // PaymentClient のメソッドを実行する
            returnValue = invocation.proceed();

            // このインタセプタ処理内で発生した例外は、呼び出し元へ伝播させない
            try {

                List<ErrHolder> errHolderList = getErrHolderList(returnValue);
                memberId = (String) getValue("getMemberId", invocation.getArgs(), returnValue, true);
                Object cardSeqTemp = getValue("getCardSeq", invocation.getArgs(), returnValue, false);
                if (cardSeqTemp != null) {
                    cardSeq = cardSeqTemp.toString();
                }

                // エラーが１件でもある場合はエラーメール送信を行う
                if (!errHolderList.isEmpty()) {
                    alert(methodName, errHolderList, null, memberId, cardSeq);
                }

            } catch (Exception e) {
                LOGGER.warn("PaymentClient API の戻り値の取得に失敗しました。", e);
            }

        } catch (Throwable th) {
            LOGGER.error("例外処理が発生しました", th);
            // PaymentClient で例外が発生した場合はエラーメール送信を行う
            alert(methodName, null, th, memberId, cardSeq);
            throw th;
        }

        return returnValue;
    }

    /**
     * 戻り値に含まれるエラーリストを取得する
     *
     * @param returnValue 戻り値
     * @return エラーリスト
     * @throws java.lang.reflect.InvocationTargetException 発生していた例外
     * @throws IllegalAccessException                      発生していた例外
     */
    public List<ErrHolder> getErrHolderList(Object returnValue)
                    throws java.lang.reflect.InvocationTargetException, IllegalAccessException {

        List<ErrHolder> holderList = new ArrayList<>();

        if (returnValue == null) {
            return holderList;
        }

        java.lang.reflect.Method getErrList = null;
        java.lang.reflect.Method getEntryErrList = null;
        java.lang.reflect.Method getExecErrList = null;

        for (java.lang.reflect.Method method : returnValue.getClass().getMethods()) {

            // public メソッドでないのなら無視
            // 引数をとるメソッドであるのなら無視
            // 戻り値がリストでないのなら無視
            if (!java.lang.reflect.Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length != 0
                && !List.class.isAssignableFrom(method.getReturnType())) {
                continue;
            }

            if ("getErrList".equals(method.getName())) {
                LOGGER.debug("getErrList メソッドが見つかりました");
                getErrList = method;
                continue;
            }

            if ("getEntryErrList".equals(method.getName())) {
                LOGGER.debug("getEntryErrList メソッドが見つかりました");
                getEntryErrList = method;
                continue;
            }

            if ("getExecErrList".equals(method.getName())) {
                LOGGER.debug("getExecErrList メソッドが見つかりました");
                getExecErrList = method;
                continue;
            }
        }

        // getErrList() メソッドの内容を取得する
        if (getErrList != null) {
            List<?> list = (List<?>) getErrList.invoke(returnValue);

            // 内容が List<ErrHolder> のオブジェクトの場合のみ、返却リストへ追加する
            if (list != null && !list.isEmpty() && list.get(0).getClass().equals(ErrHolder.class)) {
                @SuppressWarnings("unchecked")
                List<ErrHolder> tmp = (List<ErrHolder>) list;
                holderList.addAll(tmp);
            }
        }

        // getEntryErrList() メソッドの内容を取得する
        if (getEntryErrList != null) {
            List<?> list = (List<?>) getEntryErrList.invoke(returnValue);

            // 内容が List<ErrHolder> のオブジェクトの場合のみ、返却リストへ追加する
            if (list != null && !list.isEmpty() && list.get(0).getClass().equals(ErrHolder.class)) {
                @SuppressWarnings("unchecked")
                List<ErrHolder> tmp = (List<ErrHolder>) list;
                holderList.addAll(tmp);
            }
        }

        // getExecErrList() メソッドの内容を取得する
        if (getExecErrList != null) {
            List<?> list = (List<?>) getExecErrList.invoke(returnValue);

            // 内容が List<ErrHolder> のオブジェクトの場合のみ、返却リストへ追加する
            if (list != null && !list.isEmpty() && list.get(0).getClass().equals(ErrHolder.class)) {
                @SuppressWarnings("unchecked")
                List<ErrHolder> tmp = (List<ErrHolder>) list;
                holderList.addAll(tmp);
            }
        }

        LOGGER.debug("返されたエラー一覧：" + holderList);

        return holderList;
    }

    /**
     * 管理者へのメール送信処理
     * このメソッドは、いかなる例外も返してはいけない。
     *
     * @param methodName    メソッド名
     * @param errHolderList エラーリスト
     * @param th            ポイントカットしたオブジェクトがスローした例外
     * @param memberId      会員ID
     * @param cardSeq       カード登録連番
     */
    public void alert(String methodName, List<ErrHolder> errHolderList, Throwable th, String memberId, String cardSeq) {
        try {
            // 何も問題が起きていない場合
            if ((errHolderList == null || errHolderList.isEmpty()) && th == null) {
                return;
            }

            // 対象外の API の場合はアラート処理を行わない
            if (!isTargetApi(methodName)) {
                LOGGER.debug("設定ファイルが存在していないか、処理対象外のAPIのため管理者への通知は行われません。");
                return;
            }

            // エラーホルダまたは例外からメッセージを作成する
            String errorText = holder2Message(errHolderList) + exception2Message(th);

            // 送信処理
            sendMail(errorText, methodName, memberId, cardSeq);

        } catch (Throwable secondThrowable) {
            // このメソッドの呼び出し元に例外/エラー等は一切伝播させてはいけないため、
            // 全てのスローされたオブジェクトをここで握りつぶす。
            LOGGER.warn("GmoMemberCardAlert操作でエラーが発生しましたが、管理者へはメール送信を行えませんでした。", secondThrowable);
        }
    }

    /**
     * 管理者宛のメールを送信する
     *
     * @param text     エラーメッセージ
     * @param api      エラーの発生したAPI
     * @param memberId 会員ID
     * @param cardSeq  カード登録連番
     * @throws Exception 発生した例外
     */
    protected void sendMail(String text, String api, String memberId, String cardSeq) throws Exception {

        //
        // プレースホルダの準備
        //

        LOGGER.debug("プレースホルダを準備");

        Map<String, String> placeHolders = new LinkedHashMap<>();
        // ネットワーク関連Utility
        NetworkUtility networkUtility = ApplicationContextUtility.getBean(NetworkUtility.class);
        placeHolders.put("SERVER", networkUtility.getLocalHostName());
        placeHolders.put("TIME", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        placeHolders.put("API", api);
        placeHolders.put("ERROR", text);
        placeHolders.put("MEMBERID", memberId);
        placeHolders.put("CARDSEQ", cardSeq);
        // メールに記載するシステム名
        String mailSystem = PropertiesUtil.getSystemPropertiesValue(TARGET_PREFIX + "mail_system");
        placeHolders.put("SYSTEM", mailSystem);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("SERVER:" + placeHolders.get("SERVER"));
            LOGGER.debug("TIME:" + placeHolders.get("TIME"));
            LOGGER.debug("API:" + placeHolders.get("API"));
            LOGGER.debug("ERROR:" + placeHolders.get("ERROR"));
            LOGGER.debug("MEMBERID:" + placeHolders.get("MEMBERID"));
            LOGGER.debug("CARDSEQ:" + placeHolders.get("CARDSEQ"));
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
        String[] recipients = PropertiesUtil.getSystemPropertiesValue(TARGET_PREFIX + "recipient").split(" *, *");
        for (String recipient : recipients) {
            setting.addNotificationReceiver(recipient);
        }

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

            mailDto.setMailTemplateType(HTypeMailTemplateType.GMO_MEMBER_CARD_ALERT);
            mailDto.setFrom(setting.getMailFrom());
            mailDto.setToList(setting.getNotificationReceivers());
            mailDto.setSubject("【" + mailSystem + " 要確認】GMO会員カードエラー報告");
            mailDto.setMailContentsMap(mailContentsMap);

            MailSendService mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
            mailSendService.execute(mailDto);

            LOGGER.info("アラートメールを送信しました。");
        } catch (Exception e) {
            LOGGER.warn("アラートメール送信に失敗しました。", e);
            throw e;
        }
    }

    /**
     * アラートメールを送信する対象の API かどうかを確認する
     *
     * @param methodName 検証対象のAPI
     * @return true - 検証するよ
     */
    protected boolean isTargetApi(String methodName) {

        // "検証対象API"をpropertiesから取得
        String val = PropertiesUtil.getSystemPropertiesValue(TARGET_PREFIX + methodName);
        if ("true".equalsIgnoreCase(val)) {
            LOGGER.debug(methodName + " はメール送信ターゲットである");
            return true;
        }

        LOGGER.debug(methodName + " はメール送信ターゲットでない");

        return false;

    }

    /**
     * エラーホルダからメッセージを作成する
     *
     * @param errHolderList エラーホルダ
     * @return メッセージ
     */
    protected String holder2Message(List<ErrHolder> errHolderList) {

        String errorText = "";

        // エラーメッセージを取得する
        if (errHolderList != null && !errHolderList.isEmpty()) {
            List<String> errorMessages = getMessages(errHolderList);

            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < errHolderList.size(); i++) {
                String errCode = errHolderList.get(i).getErrCode();
                String errInfo = errHolderList.get(i).getErrInfo();
                String errMsg = errorMessages.get(i);

                builder.append(errCode + ":" + errInfo + " - " + errMsg + "\n");
            }

            errorText = builder.toString();

        }

        return errorText;
    }

    /**
     * 例外からメッセージを作成する
     *
     * @param th 例外
     * @return メッセージ
     */
    protected String exception2Message(Throwable th) {

        String errorText = "";

        // 例外をメッセージにする
        if (th != null) {
            String exception = th.getClass().getName();
            String mes = th.getMessage();
            errorText += "GMO マルチペイメントと通信中に " + exception + " が発生しました。\n" + mes + "\n";
        }

        return errorText;
    }

    /**
     * エラーメッセージに対応するメッセージリソースを取得する
     *
     * @param errHolders GMO xxxTranOutput の ErrHolder
     * @return ErrHolder に対応したメッセージリスト
     */
    public List<String> getMessages(List<ErrHolder> errHolders) {

        List<String> returnList = new ArrayList<>();
        if (errHolders == null) {
            return returnList;
        }

        for (ErrHolder holder : errHolders) {

            String errInfo = holder.getErrInfo();
            String message = "";

            if (errInfo != null && !errInfo.isEmpty()) {
                message = StringUtils.trimToEmpty(PropertiesUtil.getSystemPropertiesValue(errInfo));
            }
            returnList.add(message);
        }

        return returnList;
    }

    /**
     * ポイントカットしたメソッドから value を取得する
     *
     * @param valueName   value名称
     * @param args        ポイントカットメソッドの引数
     * @param returnValue ポイントカットメソッドの戻り値
     * @param unknownFlag 不明フラグ
     * @return value
     */
    protected Object getValue(String valueName, Object[] args, Object returnValue, boolean unknownFlag) {
        Object value;

        // ポイントカットメソッドの引数から value 取得を試みる
        value = getValueFromArgument(valueName, args);
        if (value != null) {
            return value;
        }

        // ポイントカットメソッドの戻り値から value 取得を試みる
        value = getValueFromReturnValue(valueName, returnValue);
        if (value != null) {
            return value;
        }

        // Id を取得できなかった場合
        if (unknownFlag) {
            return valueName + "不明";
        } else {
            return null;
        }
    }

    /**
     * ポイントカットしたメソッドの引数より value 取得を試みる処理。<br />
     *
     * @param valueName value名称
     * @param args      ポイントカットメソッドの引数
     * @return value。取得できなかった場合は null
     */
    protected Object getValueFromArgument(String valueName, Object[] args) {
        if (args.length != 1) {
            return null;
        }

        try {
            return args[0].getClass().getMethod(valueName).invoke(args[0]);
        } catch (Exception e) {
            // ここでは例外発生時のハンドリング処理はログだけ吐いて行わない。
            // 例外が発生する場合は、そもそも valueName メソッドが対象の引数に存在していないケースであるため null
            // を返すことが妥当
            LOGGER.error("例外処理が発生しました", e);
            return null;
        }
    }

    /**
     * ポイントカットメソッドの戻り値より value 取得を試みる処理。<br />
     *
     * @param valueName   value名称
     * @param returnValue ポイントカットメソッドの戻り値
     * @return value。取得できなかった場合は null
     */
    protected Object getValueFromReturnValue(String valueName, Object returnValue) {
        if (returnValue == null) {
            return null;
        }

        try {
            return returnValue.getClass().getMethod(valueName).invoke(returnValue);
        } catch (Exception e) {
            // ここでは例外発生時のハンドリング処理はログだけ吐いて行わない。
            // 例外が発生する場合は、そもそも valueName メソッドが対象の引数に存在していないケースであるため null
            // を返すことが妥当
            LOGGER.error("例外処理が発生しました", e);
            return null;
        }
    }
}
