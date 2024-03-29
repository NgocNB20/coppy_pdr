/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;

/**
 * 受注サマリ登録ロジック
 *
 * @author negishi
 * @version $Revision: 1.2 $
 */
public interface OrderSummaryRegistLogic {

    /**
     * ロジック実行<br/>
     *
     * @param orderSummaryEntity 受注サマリエンティティ
     * @return 登録件数
     */
    int execute(OrderSummaryEntity orderSummaryEntity);

}
