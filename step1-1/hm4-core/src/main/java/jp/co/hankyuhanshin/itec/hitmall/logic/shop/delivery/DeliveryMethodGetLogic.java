/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;

import java.util.Map;

/**
 * 配送方法取得ロジック
 *
 * @author ueshima
 * @version $Revision: 1.3 $
 */
public interface DeliveryMethodGetLogic {

    // LSD0001;

    /**
     * ロジック実行<br/>
     *
     * @param deliveryMethodSeq 配送方法SEQ
     * @return 配送方法エンティティ
     */
    DeliveryMethodEntity execute(Integer deliveryMethodSeq);

    /**
     * ロジック実行<br/>
     *
     * @param deliveryMethodName 配送方法名
     * @param shopSeq            ショップSEQ
     * @return 配送方法エンティティ
     */
    DeliveryMethodEntity execute(String deliveryMethodName, Integer shopSeq);

    /**
     * 選択項目リストの作成に利用するデータを返却する<br/>
     *
     * @param shopSeq ショップSEQ
     * @return 配送方法結果を格納したMap
     */
    Map<String, String> getItemMapList(Integer shopSeq);
}
