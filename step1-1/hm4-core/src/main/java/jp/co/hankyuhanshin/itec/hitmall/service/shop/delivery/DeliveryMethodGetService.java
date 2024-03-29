/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;

/**
 * 配送方法取得サービス<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.3 $
 */
public interface DeliveryMethodGetService {

    /**
     * 実行メソッド<br/>
     *
     * @param deliveryMethodSeq 配送方法SEQ
     * @return 配送方法エンティティ
     */
    DeliveryMethodEntity execute(Integer deliveryMethodSeq);

    /**
     * 実行メソッド
     *
     * @param deliveryMethodName 配送方法名
     * @return 配送方法エンティティ
     */
    DeliveryMethodEntity execute(String deliveryMethodName);

}
