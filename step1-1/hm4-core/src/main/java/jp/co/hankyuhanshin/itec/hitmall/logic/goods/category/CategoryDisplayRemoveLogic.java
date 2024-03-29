/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryDisplayEntity;

/**
 * カテゴリ表示情報削除
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
public interface CategoryDisplayRemoveLogic {

    /**
     * カテゴリ表示情報を削除<br/>
     *
     * @param categoryDisplayEntity カテゴリ表示エンティティ
     * @return int 件数
     */
    int execute(CategoryDisplayEntity categoryDisplayEntity);
}
