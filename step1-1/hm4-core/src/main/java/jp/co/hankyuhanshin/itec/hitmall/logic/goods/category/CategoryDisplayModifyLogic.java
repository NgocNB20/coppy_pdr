/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryDisplayEntity;

/**
 * カテゴリ表示情報修正
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
public interface CategoryDisplayModifyLogic {

    /**
     * カテゴリ表示情報を修正<br/>
     *
     * @param categoryDisplayEntity カテゴリ表示情報
     * @return 件数
     */
    int execute(CategoryDisplayEntity categoryDisplayEntity);
}
