/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryGoodsEntity;

import java.util.List;
import java.util.Map;

/**
 * カテゴリ登録商品マップ取得<br/>
 *
 * @author hirata
 * @version $Revision: 1.2 $
 */
public interface CategoryGoodsMapGetLogic {

    // LGC0016

    /**
     * 実行メソッド<br/>
     *
     * @param goodsGroupSeqList 商品グループSEQリスト
     * @return カテゴリ登録商品マップ
     */
    Map<Integer, List<CategoryGoodsEntity>> execute(List<Integer> goodsGroupSeqList);
}
