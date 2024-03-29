/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliverySpecialChargeAreaEntity;

/**
 * 配送特別料金エリアエンティティ取得Logicインターフェース
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public interface DeliverySpecialChargeAreaGetLogic {
    /**
     * 配送特別料金エリアエンティティを取得します
     *
     * @param deliveryMethodSeq Integer
     * @param zipCode           String
     * @return DeliverySpecialChargeAreaEntity
     */
    DeliverySpecialChargeAreaEntity execute(Integer deliveryMethodSeq, String zipCode);
}
