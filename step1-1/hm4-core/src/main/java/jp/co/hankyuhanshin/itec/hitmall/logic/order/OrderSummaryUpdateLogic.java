/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;

/**
 * 受注サマリー更新ロジック<br/>
 *
 * @author yamaguchi
 * @version $Revision: 1.3 $
 */
public interface OrderSummaryUpdateLogic {

    /**
     * 実行メソッド<br/>
     *
     * @param orderSummaryEntity 受注サマリーエンティティ
     * @return 更新件数
     */
    int execute(OrderSummaryEntity orderSummaryEntity);
}
