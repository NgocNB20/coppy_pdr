/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.stock.StockStatusDisplayDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.stock.StockStatusDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockStatusDisplayMapGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在庫状態表示Map取得ロジック<br/>
 *
 * @author kt16122
 */
@Component
public class StockStatusDisplayMapGetLogicImpl extends AbstractShopLogic implements StockStatusDisplayMapGetLogic {

    /**
     * 　在庫状況表示Dao
     */
    private final StockStatusDisplayDao stockStatusDisplayDao;

    @Autowired
    public StockStatusDisplayMapGetLogicImpl(StockStatusDisplayDao stockStatusDisplayDao) {
        this.stockStatusDisplayDao = stockStatusDisplayDao;
    }

    /**
     * 実行メソッド
     *
     * @param goodsGroupSeqList 商品グループシーケンスリスト
     * @return 在庫状況表示Map
     */
    @Override
    public Map<Integer, StockStatusDisplayEntity> execute(List<Integer> goodsGroupSeqList) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("goodsGroupSeqList", goodsGroupSeqList);

        // 在庫状況表示リスト取得
        List<StockStatusDisplayEntity> stockStatusDisplayEntityList =
                        stockStatusDisplayDao.getStockStatusDisplayList(goodsGroupSeqList);

        // 取得したリストをマップに編集
        Map<Integer, StockStatusDisplayEntity> map = new HashMap<>();
        for (StockStatusDisplayEntity tmp : stockStatusDisplayEntityList) {
            map.put(tmp.getGoodsGroupSeq(), tmp);
        }

        // 編集したマップを返す。
        return map;
    }
}
