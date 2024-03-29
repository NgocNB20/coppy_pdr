/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.utility;

import jp.co.hankyuhanshin.itec.hitmall.front.dto.multipayment.ComResultDto;
import org.springframework.stereotype.Component;

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
     * エラーコード：カード情報照会エラー
     */
    public static final String MSGCD_CREDIT_CARD_INFO_GET_ERROR = "PG-0001-013-L-E";

    /**
     * 処理結果コード：通信異常
     */
    public static final String RESCD_TRAN_ERROR = "1";

    /**
     * 登録済みカード情報照会時<br/>
     * 決済代行IDに紐付くカード情報が存在しない場合のエラーコード
     * <code>RES_CODE_KIND_GET_NO_REGISTERED_CARD</code>
     */
    public static final String RES_CODE_KIND_GET_NO_REGISTERED_CARD = "P026";

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

}
// Paygent Customization to here
