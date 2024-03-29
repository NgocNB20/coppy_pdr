/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.additional.OrderAdditionalChargeEntity;

import java.util.List;

/**
 * 受注追加料金リスト取得ロジック
 *
 * @author ueshima
 * @version $Revision: 1.1 $
 */
public interface OrderAdditionalChargeListGetLogic {

    // LOO0021;

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq                       受注SEQ
     * @param orderAdditionalChargeVersionNo 受注追加料金連番
     * @return 受注追加料金エンティティリスト
     */
    List<OrderAdditionalChargeEntity> execute(Integer orderSeq, Integer orderAdditionalChargeVersionNo);
}
