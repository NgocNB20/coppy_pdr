/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;

/**
 * カテゴリ取得<br/>
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
public interface CategoryGetService {

    /**
     * カテゴリの取得<br/>
     *
     * @param categoryId カテゴリId
     * @return カテゴリ情報エンティティ
     */
    CategoryEntity execute(String categoryId);

}
