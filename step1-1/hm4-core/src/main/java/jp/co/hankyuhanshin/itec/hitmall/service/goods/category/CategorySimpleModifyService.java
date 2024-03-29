/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;

/**
 * カテゴリ修正(カテゴリエンティティのみ更新)<br/>
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
public interface CategorySimpleModifyService {

    /**
     * カテゴリの修正<br/>
     *
     * @param categoryEntity カテゴリエンティティ
     * @return 件数
     */
    int execute(CategoryEntity categoryEntity);

}
