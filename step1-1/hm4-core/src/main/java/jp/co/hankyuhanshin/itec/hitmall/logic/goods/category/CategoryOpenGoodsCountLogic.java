/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;

import java.util.List;

/**
 * カテゴリIDに紐づく公開商品数を取得
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
public interface CategoryOpenGoodsCountLogic {

    /**
     * カテゴリIDに紐づく公開商品数を取得
     * ※カテゴリIDに紐づく子階層の公開商品数も合わせて取得する
     *
     * @param categorySeqList 取得するカテゴリのSEQリスト
     * @return 公開商品件数エンティティリスト
     */
    List<CategoryEntity> execute(List<Integer> categorySeqList);

}
