/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryGoodsEntity;

import java.util.List;

/**
 * カテゴリに紐づくカテゴリ登録商品を取得
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
public interface CategoryGetGoodsOrderListService {

    /**
     * カテゴリに紐づくカテゴリ登録商品を取得
     *
     * @param categorySeq   カテゴリSEQ
     * @param goodsGroupSeq 商品グループSEQ
     * @return list カテゴリ登録商品リスト
     */
    List<CategoryGoodsEntity> execute(Integer categorySeq, Integer goodsGroupSeq);

}
