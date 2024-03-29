/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;

/**
 * 配送方法データチェックサービス
 *
 * @author negishi
 * @version $Revision: 1.1 $
 */
public interface DeliveryMethodDataCheckService {

    /**
     * サービス実行
     *
     * @param deliveryMethodEntity 配送方法エンティティ
     */
    void execute(DeliveryMethodEntity deliveryMethodEntity);

}
