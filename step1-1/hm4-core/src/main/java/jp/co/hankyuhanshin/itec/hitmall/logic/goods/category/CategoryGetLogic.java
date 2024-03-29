/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;

/**
 * カテゴリ情報取得
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
public interface CategoryGetLogic {

    /**
     * カテゴリ情報を取得する。<br/>
     *
     * @param shopSeq    ショップSEQ
     * @param categoryId カテゴリID
     * @return カテゴリエンティティ
     */
    CategoryEntity execute(Integer shopSeq, String categoryId);
}
