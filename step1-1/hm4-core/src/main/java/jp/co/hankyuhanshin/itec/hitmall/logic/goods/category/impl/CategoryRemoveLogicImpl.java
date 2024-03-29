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
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryRemoveLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * カテゴリ情報削除
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
@Component
public class CategoryRemoveLogicImpl extends AbstractShopLogic implements CategoryRemoveLogic {

    /**
     * カテゴリ情報削除DAO
     */
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryRemoveLogicImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * カテゴリ情報削除<br/>
     *
     * @param categoryEntity カテゴリエンティティ
     * @return 件数
     */
    @Override
    public int execute(CategoryEntity categoryEntity) {
        return categoryDao.delete(categoryEntity);
    }
}
