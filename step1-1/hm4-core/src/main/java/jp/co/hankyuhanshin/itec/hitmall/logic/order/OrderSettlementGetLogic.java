/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;

/**
 * 受注決済取得ロジック
 *
 * @author ueshima
 * @version $Revision: 1.1 $
 */
public interface OrderSettlementGetLogic {

    // LOO0020;

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq                 受注SEQ
     * @param orderSettlementVersionNo 受注決済連番
     * @return 受注決済エンティティ
     */
    OrderSettlementEntity execute(Integer orderSeq, Integer orderSettlementVersionNo);
}
