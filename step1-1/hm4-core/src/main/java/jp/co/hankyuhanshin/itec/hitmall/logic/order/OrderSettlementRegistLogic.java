/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;

/**
 * 受注決済登録ロジック
 *
 * @author ueshima
 * @version $Revision: 1.1 $
 */
public interface OrderSettlementRegistLogic {

    // LOO0013;

    /**
     * ロジック実行<br/>
     *
     * @param orderSettlementEntity 受注決済エンティティ
     * @return 登録件数
     */
    int execute(OrderSettlementEntity orderSettlementEntity);
}
