/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.category.CategoryDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * カテゴリ情報取得
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
@Component
public class CategoryGetLogicImpl extends AbstractShopLogic implements CategoryGetLogic {

    /**
     * カテゴリ情報DAO
     */
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryGetLogicImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * カテゴリ情報を取得<br/>
     *
     * @param shopSeq    ショップSEQ
     * @param categoryId カテゴリID
     * @return カテゴリ情報エンティティ
     */
    @Override
    public CategoryEntity execute(Integer shopSeq, String categoryId) {

        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);
        ArgumentCheckUtil.assertNotNull("categoryId", categoryId);

        CategoryEntity categoryEntity = categoryDao.getCategory(shopSeq, categoryId);

        return categoryEntity;
    }
}
