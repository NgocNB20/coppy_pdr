/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment;

import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;

/**
 * マルチペイメント請求ロジック<br/>
 *
 * @author MN7017
 * @author matsumoto (itec) 2012/01/18 #2776 対応
 */
public interface MulPayBillLogic {
    /**
     * マルペイ請求.オーダーIDの値が不正な場合のエラーメッセージコード
     */
    final String ERROR_CODE_ORDER_ID_IS_INVALID = "LOX000317";

    /**
     * オーダーID取得<br/>
     * 指定する受注SEQから最新トランザクション履歴のオーダーIDを取得します。<br/>
     *
     * @param orderSeq 受注SEQ
     * @return オーダーID
     */
    String getCurrentOrderId(Integer orderSeq);

    /**
     * オーダーID取得<br/>
     * 指定する取引ID、取引パスワードから最新トランザクション履歴のオーダーIDを取得します。<br/>
     *
     * @param accessId   取引ID
     * @param accessPass 取引パスワード
     * @return オーダーID
     */
    String getCurrentOrderId(String accessId, String accessPass);

    /**
     * 新規登録時のオーダーID<br/>
     * 新規登録する際のオーダーIDを取得します。<br/>
     *
     * @param shopSeq   ショップSEQ
     * @param orderSeq  受注SEQ
     * @param orderCode 受注コード
     * @return オーダーID
     */
    String getNextOrderId(Integer shopSeq, Integer orderSeq, String orderCode);

    /**
     * マルチペイメント請求データを取得します。<br/>
     *
     * @param orderCode 受注コード
     * @return マルチペイメント請求
     */
    MulPayBillEntity getMulPayBillByOrderCode(String orderCode);

    /**
     * マルチペイメント請求データを取得します。<br/>
     *
     * @param orderSeq       受注SEQ
     * @param orderVersionNo 受注履歴番号
     * @return マルチペイメント請求情報
     */
    MulPayBillEntity getMulPayBillByOrderSeqAndOrderVersionNo(Integer orderSeq, Integer orderVersionNo);

    /**
     * マルチペイメント請求データを取得します。<br/>
     * 指定した受注SEQで最新のマルチペイメント請求情報を取得する
     *
     * @param orderSeq 受注SEQ
     * @return マルチペイメント請求情報
     */
    MulPayBillEntity getMulPayBillByOrderSeq(Integer orderSeq);

    /**
     * 受注SEQをもとにのクレジットのマルチペイメント通信用orderIdを取得します。<br/>
     *
     * @param orderSeq 受注SEQ
     * @return クレジットのマルチペイメント請求用orderId
     */
    String getLatestCreditOrderId(Integer orderSeq);

    /**
     * マルチペイメント請求データを取得します。<br/>
     * 指定した受注SEQでAmazonPayment入金確認ステータスが 9 以外の最新のマルチペイメント請求情報を取得する
     *
     * @param orderSeq 受注SEQ
     * @return マルチペイメント請求情報
     */
    MulPayBillEntity getLatestEntityExceptAmazonPayErrorOccured(Integer orderSeq);

    // Paygent Customization from here

    /**
     * マルチペイメント請求の登録(別トランザクション)
     *
     * @param entity マルチペイメント請求Entity
     */
    void registAnotherTran(MulPayBillEntity entity);

    /**
     * マルチペイメント請求の登録
     *
     * @param entity マルチペイメント請求Entity
     */
    void regist(MulPayBillEntity entity);

    /**
     * マルチペイメント請求の更新(別トランザクション)
     *
     * @param entity マルチペイメント請求Entity
     */
    void updateAnotherTran(MulPayBillEntity entity);

    /**
     * マルチペイメント請求の更新
     *
     * @param entity マルチペイメント請求Entity
     */
    void update(MulPayBillEntity entity);
    // Paygent Customization to here
}
