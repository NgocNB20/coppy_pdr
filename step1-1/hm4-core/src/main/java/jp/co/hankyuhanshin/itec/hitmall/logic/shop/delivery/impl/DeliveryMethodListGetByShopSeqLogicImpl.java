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
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodListGetByShopSeqLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 配送方法エンティティリスト取得ロジック<br/>
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
@Component
public class DeliveryMethodListGetByShopSeqLogicImpl extends AbstractShopLogic
                implements DeliveryMethodListGetByShopSeqLogic {

    /**
     * 配送方法Dao
     */
    private final DeliveryMethodDao deliveryMethodDao;

    @Autowired
    public DeliveryMethodListGetByShopSeqLogicImpl(DeliveryMethodDao deliveryMethodDao) {
        this.deliveryMethodDao = deliveryMethodDao;
    }

    /**
     * メソッド概要<br/>
     * メソッドの説明・概要<br/>
     *
     * @param shopSeq ショップSEQ
     * @return 配送方法エンティティリスト
     */
    @Override
    public List<DeliveryMethodEntity> execute(Integer shopSeq) {

        return deliveryMethodDao.getDeliveryMethodList(shopSeq);
    }

}
