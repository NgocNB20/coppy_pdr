/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.stock.StockStatusDisplayEntity;

import java.util.List;
import java.util.Map;

/**
 * 在庫状態表示Map取得ロジック
 *
 * @author Kaneko
 */
public interface StockStatusDisplayMapGetLogic {

    /**
     * 商品グループ在庫表示Map取得<br/>
     *
     * @param goodsGroupSeqList 商品グループシーケンスリスト
     * @return 商品グループ在庫表示Map
     */
    Map<Integer, StockStatusDisplayEntity> execute(List<Integer> goodsGroupSeqList);

}
