/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;

/**
 * 受注請求決済エラー登録ロジック
 *
 * @author yamazaki
 */
public interface OrderBillEmergencyRegistLogic {

    /**
     * ロジック実行<br/>
     *
     * @param orderSummaryEntity 受注サマリ
     */
    void execute(OrderSummaryEntity orderSummaryEntity, String administratorName);
}
