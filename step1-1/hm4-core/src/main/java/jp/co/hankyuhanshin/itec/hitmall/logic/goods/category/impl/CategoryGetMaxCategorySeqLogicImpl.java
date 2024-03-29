/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.category.CategoryDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetMaxCategorySeqLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 現在のMAXSEQを取得
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
@Component
public class CategoryGetMaxCategorySeqLogicImpl extends AbstractShopLogic implements CategoryGetMaxCategorySeqLogic {

    /**
     * カテゴリ情報DAO
     */
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryGetMaxCategorySeqLogicImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * 現在のMAXSEQを取得<br/>
     *
     * @return MAXSEQ
     */
    @Override
    public int execute() {
        return categoryDao.getCategoryMaxCategorySeq();
    }
}
