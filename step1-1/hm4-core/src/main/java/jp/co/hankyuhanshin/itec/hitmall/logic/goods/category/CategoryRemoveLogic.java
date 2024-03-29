/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;

/**
 * カテゴリ情報削除
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
public interface CategoryRemoveLogic {

    /**
     * カテゴリ情報を削除<br/>
     *
     * @param categoryEntity カテゴリエンティティ
     * @return int 件数
     */
    int execute(CategoryEntity categoryEntity);
}
