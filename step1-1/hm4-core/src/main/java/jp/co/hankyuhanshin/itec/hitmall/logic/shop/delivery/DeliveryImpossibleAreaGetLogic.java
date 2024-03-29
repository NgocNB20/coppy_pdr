/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleAreaEntity;

/**
 * 配送不可能エリアエンティティ取得Logicインターフェース
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public interface DeliveryImpossibleAreaGetLogic {

    /**
     * 配送不可能エリアエンティティを取得します
     *
     * @param deliveryMethodSeq Integer
     * @param zipCode           String
     * @return DeliveryImpossibleAreaEntity
     */
    DeliveryImpossibleAreaEntity execute(Integer deliveryMethodSeq, String zipCode);
}
