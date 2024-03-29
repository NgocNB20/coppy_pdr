/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.AllDeliveryMethodListGetByShopSeqLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.AllDeliveryMethodListGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配送方法エンティティリスト取得
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
@Service
public class AllDeliveryMethodListGetServiceImpl extends AbstractShopService
                implements AllDeliveryMethodListGetService {

    /**
     * 配送方法エンティティリスト取得ロジック<br/>
     */
    private final AllDeliveryMethodListGetByShopSeqLogic deliveryMethodListGetLogic;

    @Autowired
    public AllDeliveryMethodListGetServiceImpl(AllDeliveryMethodListGetByShopSeqLogic deliveryMethodListGetLogic) {
        this.deliveryMethodListGetLogic = deliveryMethodListGetLogic;
    }

    /**
     * 配送方法エンティティリスト取得
     *
     * @return 配送方法エンティティリスト
     */
    @Override
    public List<DeliveryMethodEntity> execute() {
        // ショップSEQを取得
        Integer shopSeq = 1001;

        return deliveryMethodListGetLogic.execute(shopSeq);
    }

}
