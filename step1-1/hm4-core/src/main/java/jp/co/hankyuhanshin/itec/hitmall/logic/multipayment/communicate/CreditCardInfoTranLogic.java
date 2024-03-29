/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComResultDto;

/**
 * PDR#011 08_データ連携（顧客情報）<br/>
 * 【決済オプション部品（ペイジェント）】<br />
 * クレジットカード番号保持用通信処理
 * <pre>
 * 指定クレジットカード番号削除用メソッド追加
 * </pre>
 *
 * @author s.kume
 */
// Paygent Customization from here
public interface CreditCardInfoTranLogic {

    /**
     * カード情報設定実行
     *
     * @param customerId    決済代行会員ID
     * @param cardNumber    カード番号
     * @param cardValidTerm カード有効期限
     * @param token         トークン
     * @return レスポンス情報
     */
    ComResultDto doRegistCardInfoTran(String customerId, String cardNumber, String cardValidTerm, String token);

    /**
     * カード情報照会実行
     *
     * @param customerId 決済代行会員ID
     * @return レスポンス情報
     */
    ComResultDto doGetCardInfoTran(String customerId);

    /**
     * カード情報削除実行
     *
     * @param customerId 決済代行会員ID
     * @return レスポンス情報
     */
    ComResultDto doDeleteCardInfoTran(String customerId);

    // PDR Migrate Customization from here

    /**
     * 削除対象クレジットカードの顧客IDと顧客カードIDでカード情報を削除<br/>
     *
     * @param customerId 顧客ID
     * @param cardId     顧客カードID
     * @return 処理結果
     */
    ComResultDto doDeleteDesignateCardInfoTran(String customerId, String cardId);

    // PDR Migrate Customization to here
}
// Paygent Customization to here
