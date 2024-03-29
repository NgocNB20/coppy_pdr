/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryGoodsEntity;

/**
 * カテゴリ商品修正
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
public interface CategoryModifyGoodsLogic {

    /**
     * カテゴリ商品修正<br/>
     *
     * @param categoryGoodsEntity カテゴリ商品
     * @return 件数
     */
    int execute(CategoryGoodsEntity categoryGoodsEntity);
}
