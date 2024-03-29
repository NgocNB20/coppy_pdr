/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;

/**
 * 注文商品登録ロジック
 *
 * @author negishi
 * @version $Revision: 1.1 $
 */
public interface OrderGoodsRegistLogic {

    /**
     * 実行メソッド
     *
     * @param orderGoodsEntity 注文商品エンティティ
     * @return 登録件数
     */
    int execute(OrderGoodsEntity orderGoodsEntity);
}
