/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.category.CategoryGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGoodsRemoveLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * カテゴリ登録商品情報削除
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
@Component
public class CategoryGoodsRemoveLogicImpl extends AbstractShopLogic implements CategoryGoodsRemoveLogic {

    /**
     * カテゴリ登録商品情報DAO
     */
    private final CategoryGoodsDao categoryGoodsDao;

    @Autowired
    public CategoryGoodsRemoveLogicImpl(CategoryGoodsDao categoryGoodsDao) {
        this.categoryGoodsDao = categoryGoodsDao;
    }

    /**
     * カテゴリ登録商品情報削除<br/>
     *
     * @param categorySeq カテゴリSEQ
     * @return 件数
     */
    @Override
    public int execute(Integer categorySeq) {
        return categoryGoodsDao.deleteByCategorySeq(categorySeq);
    }
}
