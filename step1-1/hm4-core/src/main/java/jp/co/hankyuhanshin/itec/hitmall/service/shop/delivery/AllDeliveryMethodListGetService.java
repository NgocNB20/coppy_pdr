/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;

import java.util.List;

/**
 * 全配送方法エンティティリスト取得
 *
 * @author negishi
 * @version $Revision: 1.1 $
 */
public interface AllDeliveryMethodListGetService {

    /**
     * 配送方法エンティティリスト取得
     *
     * @return 配送方法エンティティリスト
     */
    List<DeliveryMethodEntity> execute();
}
