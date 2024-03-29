/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodTypeCarriageEntity;

import java.util.List;

/**
 * 配送区分別送料登録サービス<br/>
 * サービスコード：SSD0015
 *
 * @author negishi
 * @version $Revision: 1.2 $
 */
public interface DeliveryMethodTypeCarriageRegistService {

    /**
     * 配送区分別送料の登録に失敗
     */
    String MSGCD_DELIVERY_METHOD_TYPE_CARRIAGE_REGIST_FAIL = "SSD001501";

    /**
     * サービス実行
     *
     * @param deliveryMethodTypeCarriageEntity 配送区分別送料エンティティ
     * @return 登録件数
     */
    int execute(DeliveryMethodTypeCarriageEntity deliveryMethodTypeCarriageEntity);

    /**
     * サービス実行
     *
     * @param deliveryMethodTypeCarriageEntityList 配送区分別送料エンティティリスト
     * @return 登録件数
     */
    int execute(List<DeliveryMethodTypeCarriageEntity> deliveryMethodTypeCarriageEntityList);

}
