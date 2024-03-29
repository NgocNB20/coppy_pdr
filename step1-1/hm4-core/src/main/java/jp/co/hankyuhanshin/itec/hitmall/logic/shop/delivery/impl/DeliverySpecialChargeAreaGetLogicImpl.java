/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliverySpecialChargeAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliverySpecialChargeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliverySpecialChargeAreaGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 配送特別料金エリアエンティティ取得Logicインターフェース
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
@Component
public class DeliverySpecialChargeAreaGetLogicImpl extends AbstractShopLogic
                implements DeliverySpecialChargeAreaGetLogic {
    /**
     * 配送特別料金エリアDao
     */
    private final DeliverySpecialChargeAreaDao deliverySpecialChargeAreaDao;

    @Autowired
    public DeliverySpecialChargeAreaGetLogicImpl(DeliverySpecialChargeAreaDao deliverySpecialChargeAreaDao) {
        this.deliverySpecialChargeAreaDao = deliverySpecialChargeAreaDao;
    }

    /**
     * 配送特別料金エリアエンティティを取得します
     *
     * @param deliveryMethodSeq Integer
     * @param zipCode           String
     * @return DeliverySpecialChargeAreaEntity
     */
    @Override
    public DeliverySpecialChargeAreaEntity execute(Integer deliveryMethodSeq, String zipCode) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("deliveryMethodSeq", deliveryMethodSeq);
        ArgumentCheckUtil.assertNotNull("zipCode", zipCode);

        return deliverySpecialChargeAreaDao.getEntity(deliveryMethodSeq, zipCode);
    }
}
