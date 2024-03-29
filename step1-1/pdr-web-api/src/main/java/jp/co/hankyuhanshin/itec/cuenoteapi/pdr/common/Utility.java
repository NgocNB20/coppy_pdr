/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.cuenoteapi.pdr.common;

import jp.co.hankyuhanshin.itec.cuenoteapi.pdr.response.Result;
import jp.co.hankyuhanshin.itec.cuenoteapi.pdr.response.ResultInfo;
import net.arnx.jsonic.JSON;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.core.Response;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 共通クラス<br/>
 *
 * @author k-katoh
 */
public final class Utility {

    /**
     * コンストラクタ
     */
    private Utility() {

    }

    /**
     * 返却Resposne情報を生成する<br/>
     *
     * @param log ロガー
     * @param obj Resultクラス
     * @param jsonParam JSON文字列
     * @param logLevel ログレベル
     * @return Responseオブジェクト
     */
    public static Response createResponse(Logger log, List<Object> obj, String jsonParam, Level logLevel,ApiInfo apiInfo) {

        // 正常終了でない場合は、パラメータをログに出力する
        if (Constant.STORED_OK_200 != apiInfo.getStoredCode() && Constant.STORED_OK_201 != apiInfo.getStoredCode()) {
            log.error(jsonParam);
        }

        // 処理結果をJSON形式に変換
        JSON json = new JSON();

        // NULLオブジェクトを出力しない
        json.setSuppressNull(true);
        String resultJSON = json.encode(obj);
        // 先頭と末尾の角カッコを取り除く
        resultJSON = resultJSON.substring(1, resultJSON.length() - 1);

        if (Level.INFO.equals(logLevel)) {
            log.info("[OUT]" + resultJSON);
        } else {
            log.debug("[OUT]" + resultJSON);
        }
        return Response.ok().entity(resultJSON).build();
    }

    /**
     * クローズ処理<br/>
     *
     * @param cs CallableStatement
     * @param conn Connection
     * @param info API情報
     */
    public static void close(CallableStatement cs, Connection conn, ApiInfo info) {
        close(cs, null, conn, info);
    }

    /**
     * クローズ処理<br/>
     *
     * @param cs CallableStatement
     * @param cs2 CallableStatement
     * @param conn Connection
     * @param info API情報
     */
    public static void close(CallableStatement cs, CallableStatement cs2, Connection conn, ApiInfo info) {
        // クローズ処理
        if (cs != null) {
            try {
                cs.close();
            } catch (SQLException e) {
                info.setException(e);
                Utility.errorLogAndMail(info);
            }
        }
        if (cs2 != null) {
            try {
                cs2.close();
            } catch (SQLException e) {
                info.setException(e);
                Utility.errorLogAndMail(info);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                info.setException(e);
                Utility.errorLogAndMail(info);
            }
        }
    }

    /**
     * エラー処理<br/>
     * 例外発生時のエラー処理<br/>
     * [処理概要]
     * ・エラーログ出力
     * ・エラーメール送信
     *
     * @param info API情報
     */
    public static void errorLogAndMail(ApiInfo info) {

        info.getLog().error("[メッセージ]" + info.getErrorMsg());
        info.getLog().error("[ WebAPI名 ]" + info.getWebApiName());
        if (info.getErrorKeyInfoLog() != null) {
            info.getLog().error("[エラーキー情報]" + info.getErrorKeyInfoLog());
        }

        if (info.getException() == null) {
            info.getLog().error("[ストアド名]" + info.getStoredName());
            info.getLog().error("[ステータス]" + info.getStoredCode());
        } else {
            info.getLog().error(info.getException().getMessage(), info.getException());
        }

        // エラーメールを送信する
        Utility.sendMail(info);
    }

    /**
     * メール送信処理<br/>
     *
     * @param info API情報
     */
    private static void sendMail(ApiInfo info) {

        String host = PropManeger.getConf(info.getContext(), "mail.smtp.host");
        final String user = PropManeger.getConf(info.getContext(), "mail.user");
        final String pwd = PropManeger.getConf(info.getContext(), "mail.password");
        String from = PropManeger.getConf(info.getContext(), "mail.from");
        String to = PropManeger.getConf(info.getContext(), "mail.to");
        String auth = PropManeger.getConf(info.getContext(), "mail.auth");
        String port = PropManeger.getConf(info.getContext(), "mail.port");
        String ssl = PropManeger.getConf(info.getContext(), "mail.ssl");

        String title = PropManeger.getMessage(info.getContext(), "mail.error.title");

        Properties props = new Properties();
        props.setProperty("mail.smtp.from", from);
        props.setProperty("mail.smtp.host", host);

        // 認証指定の場合
        Authenticator authenticator = null;
        if ("true".equals(auth)) {
            props.setProperty("mail.smtp.auth", auth);

            authenticator = new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pwd);
                }
            };
        }

        // ポート指定されている場合
        if (port != null && !port.equals("")) {
            props.setProperty("mail.smtp.port", port);
        }

        // SSL指定の場合
        if ("true".equals(ssl)) {
            props.setProperty("mail.smtp.starttls.enable", ssl);
        }

        Session session = null;
        if (authenticator != null) {

            session = Session.getInstance(props, authenticator);
        } else {

            session = Session.getInstance(props);
        }
        session.setDebug(true);

        try {

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));

            // 送信先を分割してから設定する
            String[] toList = to.split(",");
            InternetAddress[] address = new InternetAddress[toList.length];
            for (int i = 0; i < toList.length; i++) {
                address[i] = new InternetAddress(toList[i]);
            }
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(title);
            msg.setSentDate(new Date());

            String br = System.getProperty("line.separator");

            StringBuffer buf = new StringBuffer();
            buf.append(info.getErrorMsg() + br + br);

            buf.append("[ WebAPI名 ]" + info.getWebApiName() + br);
            if (info.getErrorKeyInfoMail() != null) {
                buf.append("[エラーキー情報]" + info.getErrorKeyInfoMail() + br);
            }

            if (info.getException() == null) {
                buf.append("[ストアド名]" + info.getStoredName() + br);
                buf.append("[ステータス]" + info.getStoredCode() + br);
            } else {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                info.getException().printStackTrace(pw);
                buf.append(sw.toString() + br);
            }

            msg.setText(buf.toString());

            Transport.send(msg);

        } catch (Exception e) {
            info.getLog().error(e);
        }
    }

    /**
     * ストアドから返却されたステータスを判定し<br/>
     * 返却モデルを作成する<br/>
     *
     * @param info API情報
     * @param result Resultクラス
     * @param resultInfo ResultInfoクラス
     * @param infoList 返却情報配列
     */
    public static void makeResult(ApiInfo info, Result result, ResultInfo resultInfo, List<Object> infoList) {
        // ストアドからの返却値が正常以外の場合
        if (Constant.STORED_OK_200 != info.getStoredCode() && Constant.STORED_OK_201 != info.getStoredCode()) {

            // ストアドからのエラーコードをそのまま返却
            resultInfo.setMessage(info.getErrorMsg());

            Utility.errorLogAndMail(info);

        } else {

//            // ストアドからの返却値が正常の場合は、情報リストを設定する
//            if (infoList != null && !infoList.isEmpty()) {
//                result.setInfo();
//            }
        }
        resultInfo.setStatus(info.getStoredCode());
        result.setResult(resultInfo);
    }

    /**
     * WEB-API内で例外が発生した場合の共通処理
     * [処理概要]
     * ・コネクションのロールバック
     * ・エラーログ出力
     * ・エラーメール送信
     * ・返却値作成
     *
     * @param info API情報
     * @param result Resultクラス
     * @param resultInfo ResultInfoクラス
     */
    public static void logicException(ApiInfo info, Result result, ResultInfo resultInfo) {

        Utility.errorLogAndMail(info);

        // CUENOTE-API内で例外が発生した場合
        // FIXME エラーコード一覧参照して修正すること
        resultInfo.setStatus(Constant.STATUS_ERROR_400);
        resultInfo.setMessage(info.getErrorMsg());
        result.setResult(resultInfo);
    }

    /**
     * WEB-APIエラー発生時のログとメールに出力するWEB-APIインプット情報のセット処理
     * [処理概要]
     * ・個人情報を含まない共通情報をセットする場合に使用
     * ・個人情報を含む場合は処理クラスで個別にセットする
     *
     * @param info API情報
     * @param errorKeyInfo APIのインプットJson情報
     */
    public static void setErrorKeyInfo(ApiInfo info, String errorKeyInfo) {

        // エラーキー情報をセット
        info.setErrorKeyInfoLog(errorKeyInfo);
        info.setErrorKeyInfoMail(errorKeyInfo);
    }
}
