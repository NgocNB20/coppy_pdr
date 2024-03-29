/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryMethodDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.AllDeliveryMethodListGetByShopSeqLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 配送方法エンティティリスト取得ロジック<br/>
 *
 * @author negishi
 * @version $Revision: 1.1 $
 */
@Component
public class AllDeliveryMethodListGetByShopSeqLogicImpl extends AbstractShopLogic
                implements AllDeliveryMethodListGetByShopSeqLogic {

    /**
     * 配送方法Dao
     */
    private final DeliveryMethodDao deliveryMethodDao;

    @Autowired
    public AllDeliveryMethodListGetByShopSeqLogicImpl(DeliveryMethodDao deliveryMethodDao) {
        this.deliveryMethodDao = deliveryMethodDao;
    }

    /**
     * ロジック実行
     *
     * @param shopSeq ショップSEQ
     * @return 配送方法エンティティリスト
     */
    @Override
    public List<DeliveryMethodEntity> execute(Integer shopSeq) {

        return deliveryMethodDao.getAllDeliveryMethodList(shopSeq);
    }

}
