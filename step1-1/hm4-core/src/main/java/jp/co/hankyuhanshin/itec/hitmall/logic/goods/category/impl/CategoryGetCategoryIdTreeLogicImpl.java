/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.category.CategoryDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetCategoryIdTreeLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryGetCategoryIdTreeLogicImpl extends AbstractShopLogic implements CategoryGetCategoryIdTreeLogic {
    /**
     *カテゴリDAO
     */
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryGetCategoryIdTreeLogicImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * TOPから指定のカテゴリーIDまでコロンで結合する
     *
     * @param categoryId カテゴリーID
     * @return カテゴリーIDツリー
     */
    @Override
    public String execute(String categoryId) {

        List<String> categoryIdTreeList = categoryDao.getCategoryIdTree(1001, categoryId);
        // TOPカテゴリーは必要ないのでListの先頭を削除する
        if (categoryIdTreeList != null && categoryIdTreeList.size() > 1) {
            categoryIdTreeList.remove(0);
            return String.join(":", categoryIdTreeList);
        }
        // カテゴリーが見つからない場合はそのままカテゴリーIDを渡す
        return categoryId;
    }
}
