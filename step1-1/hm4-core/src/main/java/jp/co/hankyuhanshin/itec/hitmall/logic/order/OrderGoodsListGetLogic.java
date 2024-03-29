/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;

import java.util.List;

/**
 * 受注商品リスト取得ロジック<br/>
 *
 * @author ueshima
 */
public interface OrderGoodsListGetLogic {

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @return 受注商品エンティティリスト
     */
    List<OrderGoodsEntity> execute(Integer orderSeq, Integer orderGoodsVersionNo);

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @param orderConsecutiveNo  注文連番
     * @return 受注商品エンティティリスト
     */
    List<OrderGoodsEntity> execute(Integer orderSeq, Integer orderGoodsVersionNo, Integer orderConsecutiveNo);

}
