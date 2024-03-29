/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryMethodGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 配送方法取得サービス<br/>
 *
 * @author USER
 * @version $Revision: 1.3 $
 */
@Service
public class DeliveryMethodGetServiceImpl extends AbstractShopService implements DeliveryMethodGetService {

    /**
     * 配送方法取得ロジック
     */
    private final DeliveryMethodGetLogic deliveryMethodGetLogic;

    @Autowired
    public DeliveryMethodGetServiceImpl(DeliveryMethodGetLogic deliveryMethodGetLogic) {
        this.deliveryMethodGetLogic = deliveryMethodGetLogic;
    }

    /**
     * メソッド概要<br/>
     * メソッドの説明・概要<br/>
     *
     * @param deliveryMethodSeq 配送方法SEQ
     * @return 配送方法エンティティ
     */
    @Override
    public DeliveryMethodEntity execute(Integer deliveryMethodSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertGreaterThanZero("deliveryMethodSeq", deliveryMethodSeq);

        return deliveryMethodGetLogic.execute(deliveryMethodSeq);
    }

    /**
     * サービス実行
     *
     * @param deliveryMethodName 配送方法名
     * @return 配送方法エンティティ
     */
    @Override
    public DeliveryMethodEntity execute(String deliveryMethodName) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("deliveryMethodName", deliveryMethodName);

        // 配送方法取得ロジック実行
        return deliveryMethodGetLogic.execute(deliveryMethodName, 1001);
    }
}
