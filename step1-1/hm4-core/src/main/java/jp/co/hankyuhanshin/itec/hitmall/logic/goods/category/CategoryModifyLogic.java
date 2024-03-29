/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;

/**
 * カテゴリ情報修正
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
public interface CategoryModifyLogic {

    /**
     * カテゴリ情報を修正<br/>
     *
     * @param categoryEntity カテゴリ情報
     * @return 件数
     */
    int execute(CategoryEntity categoryEntity);
}
