package jp.co.hankyuhanshin.itec.ukapi.pdr.common;

import jp.co.hankyuhanshin.itec.ukapi.pdr.response.UkResponse;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.UkResponseHeaderInfo;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.feedgoods.GetUkUniSearchGoodsHeaderFallback;
import net.arnx.jsonic.JSON;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * UK-API連携 共通クラス<br/>
 * @author tt32117
 */
public final class Utility {

    /**
     * コンストラクタ
     */
    private Utility() {

    }

    /**
     * Request情報をログに出力する
     * @param log ロガー
     * @param obj Requestクラス
     * @param logLevel ログレベル
     */
    public static void createRequestLog(Logger log, Object obj, Level logLevel) {
        // 処理結果をJSON形式に変換
        JSON json = new JSON();

        // NULLオブジェクトを出力しない
        json.setSuppressNull(true);

        String resultJSON = json.format(obj);
        if (Level.INFO.equals(logLevel)) {
            log.info("[IN]" + resultJSON);
        } else {
            log.debug("[IN]" + resultJSON);
        }
    }

    /**
     * 返却Resposne情報を生成する<br/>
     *
     * @param log ロガー
     * @param result Resultクラス
     * @param jsonParam JSON文字列
     * @param logLevel ログレベル
     * @return Responseオブジェクト
     */
    public static Response createResponse(Logger log, UkResponse result, String jsonParam, Level logLevel) {
        // 正常終了でない場合は、パラメータをログに出力する
        if (Constant.STORED_OK != result.getResponseHeader().getStatus()) {
            log.error(jsonParam);
        }

        // 処理結果をJSON形式に変換
        JSON json = new JSON();

        // NULLオブジェクトを出力しない
        json.setSuppressNull(true);

        String resultJSON = json.format(result);

        if (Level.INFO.equals(logLevel)) {
            log.info("[OUT]" + resultJSON);
        } else {
            log.debug("[OUT]" + resultJSON);
        }
        return Response.ok().entity(resultJSON).build();
    }

    /**
     * 返却Resposne情報を生成する<br/>
     *
     * @param log ロガー
     * @param result Resultクラス
     * @param jsonParam JSON文字列
     * @param logLevel ログレベル
     * @return Responseオブジェクト
     */
    public static Response createResponseForJsonp(Logger log,
                                                  UkResponse result,
                                                  String jsonParam,
                                                  Level logLevel,
                                                  String cbk) {
        // 正常終了でない場合は、パラメータをログに出力する
        if (Constant.STORED_OK != result.getResponseHeader().getStatus()) {
            log.error(jsonParam);
        }

        // 処理結果をJSON形式に変換
        JSON json = new JSON();

        // NULLオブジェクトを出力しない
        json.setSuppressNull(true);

        String resultJSON = cbk + "(" + json.format(result) + ")";

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
                Utility.errorLog(info);
            }
        }
        if (cs2 != null) {
            try {
                cs2.close();
            } catch (SQLException e) {
                info.setException(e);
                Utility.errorLog(info);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                info.setException(e);
                Utility.errorLog(info);
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
    public static void errorLog(ApiInfo info) {
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

    }

    /**
     * ストアドから返却されたステータスを判定し<br/>
     * 返却モデルを作成する<br/>
     *
     * @param info API情報
     * @param response Resultクラス
     * @param header ResultInfoクラス
     */
    public static void makeResult(ApiInfo info, UkResponse response, UkResponseHeaderInfo header) {
        makeResult(info, response, header, null, null);
    }

    /**
     * ストアドから返却されたステータスを判定し<br/>
     * 返却モデルを作成する<br/>
     *
     * @param info API情報
     * @param response Resultクラス
     * @param header ResultInfoクラス
     * @param reqId リクエストID
     * @param fallback Fallbackクラス
     */
    public static void makeResult(ApiInfo info,
                                  UkResponse response,
                                  UkResponseHeaderInfo header,
                                  String reqId,
                                  GetUkUniSearchGoodsHeaderFallback fallback) {
        if (Constant.STORED_OK == info.getStoredCode()) {
            header.setQTime(info.getQTime());
            header.setReqId(reqId);
            if (fallback != null) {
                if (fallback.getKeyword() != null) {
                    fallback.setType("word_delete");
                }
            }
            header.setFallback(fallback);
        } else {
            header.setErrorMessage(info.getErrorMsg());
            Utility.errorLog(info);
        }
        header.setStatus(info.getStoredCode());
        response.setResponseHeader(header);
    }

    /**
     * UK-API内で例外が発生した場合の共通処理
     * [処理概要]
     * ・コネクションのロールバック
     * ・エラーログ出力
     * ・返却値作成
     *
     * @param info API情報
     * @param response UkResponseクラス
     * @param header UkResponseHeaderInfoクラス
     */
    public static void logicException(ApiInfo info, UkResponse response, UkResponseHeaderInfo header) {
        Utility.errorLog(info);

        // WEB-API内で例外が発生した場合
        // FIXME エラーコード一覧参照して修正すること
        header.setStatus(Constant.STATUS_ERROR);
        header.setErrorMessage(info.getErrorMsg());

        response.setResponseHeader(header);
    }

    /**
     * UK-APIエラー発生時のログに出力するUK-APIインプット情報のセット処理
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
    }
}
