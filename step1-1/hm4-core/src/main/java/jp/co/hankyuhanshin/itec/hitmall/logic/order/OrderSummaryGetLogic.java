/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;

/**
 * 受注サマリ情報取得ロジック<br/>
 *
 * @author natume
 * @version $Revision: 1.2 $
 */
public interface OrderSummaryGetLogic {

    /**
     * 受注サマリ情報を取得する<br/>
     *
     * @param orderSeq 受注SEQ
     * @return 受注サマリエンティティ
     */
    OrderSummaryEntity execute(Integer orderSeq);

    /**
     * 受注サマリ情報を取得する<br/>
     *
     * @param orderCode 受注番号
     * @return 受注サマリエンティティ
     */
    OrderSummaryEntity execute(String orderCode);

    /**
     * 指定のお申込み番号、ご注文主電話番号に紐付く受注サマリを取得する<br>
     * （取得できない場合でもエラーは出しません）<br>
     *
     * @param orderCode お申込み番号
     * @param orderTel  ご注文主電話番号
     * @return 受注サマリEntity
     */
    OrderSummaryEntity execute(String orderCode, String orderTel);
}
