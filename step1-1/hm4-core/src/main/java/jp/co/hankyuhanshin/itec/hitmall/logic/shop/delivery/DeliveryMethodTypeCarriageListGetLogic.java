/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodTypeCarriageEntity;

import java.util.List;

/**
 * 配送区分別送料リスト取得ロジック
 *
 * @author negishi
 * @version $Revision: 1.1 $
 */
public interface DeliveryMethodTypeCarriageListGetLogic {

    /**
     * ロジック実行
     *
     * @param deliveryMethodSeq 配送方法SEQ
     * @return 配送区分別送料エンティティリスト
     */
    List<DeliveryMethodTypeCarriageEntity> execute(Integer deliveryMethodSeq);

}
