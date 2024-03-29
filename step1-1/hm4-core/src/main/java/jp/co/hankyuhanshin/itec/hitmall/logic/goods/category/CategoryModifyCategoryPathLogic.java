/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;

/**
 * 子階層のカテゴリーパスを更新<br/>
 *
 * @author wt32118
 */
public interface CategoryModifyCategoryPathLogic {

    /**
     * 子階層のカテゴリーパスを更新<br/>
     *
     * @param categoryEntity カテゴリーEntity
     * @return 更新件数
     */
    int execute(CategoryEntity categoryEntity);

}
