/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;

import java.util.List;

/**
 * 配送方法エンティティリスト取得ロジック<br/>
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
public interface DeliveryMethodListGetByShopSeqLogic {

    /**
     * ロジック実行<br/>
     *
     * @param shopSeq ショップSEQ
     * @return 配送方法エンティティリスト
     */
    List<DeliveryMethodEntity> execute(Integer shopSeq);
}
