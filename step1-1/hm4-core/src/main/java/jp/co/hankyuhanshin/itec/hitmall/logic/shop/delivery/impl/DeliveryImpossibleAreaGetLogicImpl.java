/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryImpossibleAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleAreaGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 配送不可能エリアエンティティ取得Logicインターフェース
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
@Component
public class DeliveryImpossibleAreaGetLogicImpl extends AbstractShopLogic implements DeliveryImpossibleAreaGetLogic {

    /**
     * 配送特別料金エリアDao
     */
    private final DeliveryImpossibleAreaDao deliveryImpossibleAreaDao;

    @Autowired
    public DeliveryImpossibleAreaGetLogicImpl(DeliveryImpossibleAreaDao deliveryImpossibleAreaDao) {
        this.deliveryImpossibleAreaDao = deliveryImpossibleAreaDao;
    }

    /**
     * 配送不可能エリアエンティティを取得します
     *
     * @param deliveryMethodSeq Integer
     * @param zipCode           String
     * @return DeliverySpecialChargeAreaEntity
     */
    @Override
    public DeliveryImpossibleAreaEntity execute(Integer deliveryMethodSeq, String zipCode) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("deliveryMethodSeq", deliveryMethodSeq);
        ArgumentCheckUtil.assertNotNull("zipCode", zipCode);

        return deliveryImpossibleAreaDao.getEntity(deliveryMethodSeq, zipCode);
    }
}
