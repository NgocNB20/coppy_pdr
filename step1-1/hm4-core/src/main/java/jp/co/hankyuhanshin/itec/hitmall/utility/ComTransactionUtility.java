/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.utility;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hmbase.application.AppLevelFacesMessage;
import jp.co.hankyuhanshin.itec.hmbase.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * PDR#206 【受け入れ】【要望】基幹のペイジェントIDとECのペイジェントIDを一致させる<br/>
 *
 * <pre>
 * 通信ユーティリティ
 * </pre>
 *
 * @author satoh
 */
@Component
// Paygent Customization from here
public class ComTransactionUtility {

    /**
     * 処理区分
     */

    /**
     * 処理区分：オーソリ
     */
    public static final String KIND_AUTHORI = "020";
    /**
     * 処理区分：オーソリキャンセル
     */
    public static final String KIND_AUTHORI_CANCEL = "021";
    /**
     * 処理区分：補正オーソリ
     */
    public static final String KIND_AUTHORI_CHANGE = "028";
    /**
     * 処理区分：売上
     */
    public static final String KIND_SALES = "022";
    /**
     * 処理区分：売上キャンセル
     */
    public static final String KIND_SALES_CANCEL = "023";
    /**
     * 処理区分：補正売上
     */
    public static final String KIND_SALES_CHANGE = "029";
    /**
     * 処理区分：決済情報照会
     */
    public static final String KIND_PAYMENT_INFO = "094";
    /**
     * 処理区分：登録
     */
    public static final String KIND_REGIST = "025";
    /**
     * 処理区分：削除
     */
    public static final String KIND_DELETE = "026";
    /**
     * 処理区分：照会
     */
    public static final String KIND_GET = "027";
    /**
     * 処理区分：コンビニ決済(番号方式)申込
     */
    public static final String KIND_CONVENI = "030";

    /**
     * 支払区分
     */

    /**
     * 支払区分：1回払い
     */
    public static final String PAYGENT_METHOD_LUMP_SUM = "10";
    /**
     * 支払区分：ボーナス1回
     */
    public static final String PAYGENT_METHOD_BONUS_LUMP_SUM = "23";
    /**
     * 支払区分：分割
     */
    public static final String PAYGENT_METHOD_INSTALLMENT = "61";
    /**
     * 支払区分：リボルビング
     */
    public static final String PAYGENT_METHOD_REVOLVING = "80";

    /**
     * エラーコード
     */

    /**
     * 決済代行サービスとの通信エラーで使うデフォルトエラーコード
     */
    public static final String MSGCD_DEFAULT_ERROR = "PG-0001-001-L-E";
    /**
     * クレジットカード番号設定通信エラーで使うデフォルトエラーコード
     */
    public static final String MSGCD_REG_CARDINFO_DEFAULT_ERROR = "PG-0001-009-L-E";
    /**
     * クレジットカード番号照会通信エラーで使うデフォルトエラーコード
     */
    public static final String MSGCD_GET_CARDINFO_DEFAULT_ERROR = "PG-0001-010-L-E";
    /**
     * クレジットカード番号削除通信エラーで使うデフォルトエラーコード
     */
    public static final String MSGCD_DEL_CARDINFO_DEFAULT_ERROR = "PG-0001-011-L-E";

    /**
     * メッセージコード変換用
     */

    /**
     * 定数：重複防止用Prefix(messageIdMapからメッセージID取得用)(決済通信)
     */
    protected static final String MSG_ID_MAP_PREFIX = "smap.";
    /**
     * 定数：重複防止用Prefix(messageIdMapからメッセージID取得用)(クレジットカード番号保持通信)
     */
    protected static final String MSG_ID_MAP_PREFIX_CARDINFO = "cmap.";

    /**
     * メッセージ切り分け
     */

    /**
     * メッセージ切り分け：決済代行サービスとの通信
     */
    public static final String MSG_TYPE_PAYMENT = "1";
    /**
     * メッセージ切り分け：クレジットカード番号設定通信
     */
    public static final String MSG_TYPE_REG_CARDINFO = "2";
    /**
     * メッセージ切り分け：クレジットカード番号照会通信
     */
    public static final String MSG_TYPE_GET_CARDINFO = "3";
    /**
     * メッセージ切り分け：クレジットカード番号削除通信
     */
    public static final String MSG_TYPE_DEL_CARDINFO = "4";

    /**
     * 処理結果リスト
     */

    /**
     * 処理結果コード：通信成功
     */
    public static final String RESCD_TRAN_SUCCESS = "0";
    /**
     * 処理結果コード：通信異常
     */
    public static final String RESCD_TRAN_ERROR = "1";
    /**
     * 処理結果コード：3Dｵｰｿﾘ必要
     */
    public static final String RESCD_TRAN_3D_SECURE = "7";

    /**
     * レスポンスコードリスト
     */

    /**
     * 取引情報が検索結果に存在しないことを表す結果コードのリスト
     */
    protected static final List<String> RESPONSE_CD_NOT_FOUND_LIST = new ArrayList<>();

    /**
     * カード情報が存在しないことを表す結果コードのリスト
     */
    protected static final List<String> RESPONSE_CD_NOT_FOUND_CARDINFO_LIST = new ArrayList<>();

    // PDR Migrate Customization from here
    /**
     * 登録済みカード情報照会時<br/>
     * 決済代行IDに紐付くカード情報が存在しない場合のエラーコード
     * <code>RES_CODE_KIND_GET_NO_REGISTERED_CARD</code>
     */
    public static final String RES_CODE_KIND_GET_NO_REGISTERED_CARD = "P026";
    // PDR Migrate Customization to here

    /**
     *  ペイジェント通信エラー
     */

    /**
     * レスポンスコード
     */
    public static final String RESPONSE_CD_RESULT_NULL = "EX999";
    /**
     * レスポンス詳細
     */
    public static final String RESPONSE_DETAIL_RESULT_NULL = "ペイジェント通信エラー";

    /** メッセージ・ステータスの初期設定 */
    static {
        /* カード情報が存在しないことを表す結果コードのリスト */
        RESPONSE_CD_NOT_FOUND_CARDINFO_LIST.add("P022");// カード情報なしエラー
    }

    /**
     * リクエスト情報に受注情報をセット
     * <pre>
     * クレジット通信用
     * </pre>
     *
     * @param input リクエスト情報
     * @param dto   受注Dto
     */
    public void setReceiveOrderInfo(ComRequestDto input, ReceiveOrderDto dto) {

        OrderSummaryEntity orderSummaryEntity = dto.getOrderSummaryEntity();

        // 受注SEQ
        input.setOrderSeq(orderSummaryEntity.getOrderSeq());
        // 受注履歴連番
        input.setOrderVersionNo(orderSummaryEntity.getOrderVersionNo());
    }

    /**
     * 検索結果が0件かどうかを取得
     *
     * @param output 決済情報（すべて）Dto
     * @return true: 存在しない、false: 存在する
     */
    public boolean isNotFound(ComResultDto output) {

        // 検索結果0件の結果コードかどうかで判定
        // レスポンスコード000000、かつ、output.getCount()が0件の場合は考慮しない ※発生した場合は、別途検討要
        return RESPONSE_CD_NOT_FOUND_LIST.contains(output.getResponseCode());
    }

    /**
     * カード情報が存在しないかどうかを返す
     *
     * @param output 通信アウトプット
     * @return true 存在しない, false 存在する
     */
    public boolean isNotFoundCardInfo(ComResultDto output) {
        return RESPONSE_CD_NOT_FOUND_CARDINFO_LIST.contains(output.getResponseCode());
    }

    /**
     * エラーが発生したかどうかを取得
     *
     * @param result レスポンス情報
     * @return true: エラー発生、false: エラーなし
     */
    public boolean isErrorOccurred(ComResultDto result) {

        // nullが渡された場合はエラー扱い
        if (result == null) {
            return true;
        }

        // PDR Migrate Customization from here
        // ステータスが「通信異常」 かつ レスポンスコードが「P026(決済代行IDに紐付くカード情報なし)」以外の場合 エラー
        return RESCD_TRAN_ERROR.equals(result.getResultStatus()) && !RES_CODE_KIND_GET_NO_REGISTERED_CARD.equals(
                        result.getResponseCode());
        // PDR Migrate Customization to here
    }

    /**
     * 3Dセキュア認証が必要かどうかを取得
     * <pre>
     * 与信の「ExecTran」に対するレスポンス情報にのみ、用いてください。
     * それ以外の場合（「SecureTran」など）、結果コードによる判定はできません。
     * </pre>
     *
     * @param result レスポンス情報
     * @return true: 認証必要、false: 認証不要
     */
    public boolean isSecureTran(ComResultDto result) {
        return RESCD_TRAN_3D_SECURE.equals(result.getResultStatus());
    }

    /**
     * 通信処理エラーチェック
     * <pre>
     * レスポンス情報の「結果コード」を元に、メッセージリストを作成
     * </pre>
     *
     * @param result  レスポンス情報
     * @param msgType メッセージ切り分け
     * @return エラーメッセージリスト (null時はエラーなし)
     */
    public List<CheckMessageDto> checkResultOutput(ComResultDto result, String msgType) {

        // 処理が成功している場合は、nullを返す
        if (RESCD_TRAN_SUCCESS.equals(result.getResultStatus()) || RESCD_TRAN_3D_SECURE.equals(
                        result.getResultStatus())) {
            return null;
        }

        return getMessageList(result.getResponseCode(), result.getResponseDetail(), msgType);
    }

    /**
     * 通信処理エラーメッセージリスト作成
     *
     * @param resCd   レスポンスコード
     * @param resDtl  レスポンス詳細コード
     * @param msgType メッセージ切り分け
     * @return エラーメッセージリスト
     */
    protected List<CheckMessageDto> getMessageList(String resCd, String resDtl, String msgType) {

        List<CheckMessageDto> res = new ArrayList<>();

        // propertiesファイル検索対象のコード
        String propCd = null;
        // レスポンス詳細コードが英数字で構成されるとき、
        // それをエラーコードとする
        if (resDtl != null && resDtl.matches("[0-9a-zA-Z]+")) {
            propCd = resDtl;
        } else {

            propCd = resCd;
        }

        String msgIdMapPrefix = null;
        String defaultCd = null;

        // 決済代行サービスとの通信の場合
        if (MSG_TYPE_PAYMENT.equals(msgType)) {
            msgIdMapPrefix = MSG_ID_MAP_PREFIX;
            defaultCd = MSGCD_DEFAULT_ERROR;
        }
        // クレジットカード番号設定通信の場合
        else if (MSG_TYPE_REG_CARDINFO.equals(msgType)) {
            msgIdMapPrefix = MSG_ID_MAP_PREFIX_CARDINFO;
            defaultCd = MSGCD_REG_CARDINFO_DEFAULT_ERROR;
        }
        // クレジットカード番号照会通信の場合
        else if (MSG_TYPE_GET_CARDINFO.equals(msgType)) {
            msgIdMapPrefix = MSG_ID_MAP_PREFIX_CARDINFO;
            defaultCd = MSGCD_GET_CARDINFO_DEFAULT_ERROR;
        }
        // クレジットカード番号削除通信の場合
        else if (MSG_TYPE_DEL_CARDINFO.equals(msgType)) {
            msgIdMapPrefix = MSG_ID_MAP_PREFIX_CARDINFO;
            defaultCd = MSGCD_DEL_CARDINFO_DEFAULT_ERROR;
        }

        // messageIdMapPaygent.propertiesファイルに従い、結果コードを置換
        String messageCode = PropertiesUtil.getSystemPropertiesValue(msgIdMapPrefix + propCd);

        if (StringUtil.isEmpty(messageCode)) {

            // 置換対象でない場合、デフォルトのメッセージコードを利用
            addMessage(res, defaultCd, resCd, resDtl);

        } else {

            // 置換対象の場合、propertiesファイルに記載されたメッセージコードを利用
            addMessage(res, messageCode, resCd, resDtl);
        }

        return res;
    }

    /**
     * 通信処理エラーメッセージ追加
     *
     * @param messageList エラーメッセージリスト
     * @param messageCode メッセージコード
     * @param resCd       レスポンスコード
     * @param resDtl      レスポンス詳細コード
     */
    protected void addMessage(List<CheckMessageDto> messageList, String messageCode, String resCd, String resDtl) {

        CheckMessageDto e = ApplicationContextUtility.getBean(CheckMessageDto.class);

        e.setMessageId(messageCode);
        Object[] args = null;
        if (resCd != null) {

            if (resDtl != null) {
                args = new Object[] {resCd, resDtl};
            } else {
                args = new Object[] {resCd};
            }
        }
        e.setArgs(args);
        e.setMessage(getMessage(messageCode, args));
        e.setError(true);

        messageList.add(e);
    }

    /**
     * メッセージ取得
     *
     * @param messageCode メッセージコード
     * @param args        引数
     * @return メッセージ
     */
    protected String getMessage(String messageCode, Object[] args) {
        AppLevelFacesMessage facesMessage = AppLevelFacesMessageUtil.getAllMessage(messageCode, args);
        return facesMessage.getMessage();
    }

    /**
     * レスポンス情報の生成
     *
     * @param output      レスポンス情報
     * @param messageList エラーメッセージリスト
     * @return レスポンス情報
     */
    public ComResultDto makeOutput(ComResultDto output, List<CheckMessageDto> messageList) {
        output.setMessageList(messageList);
        return output;
    }

}
// Paygent Customization to here
