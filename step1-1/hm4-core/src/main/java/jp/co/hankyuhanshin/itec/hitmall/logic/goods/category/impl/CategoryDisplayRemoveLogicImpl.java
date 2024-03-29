/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.category.CategoryDisplayDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryDisplayRemoveLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * カテゴリ表示情報削除
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
@Component
public class CategoryDisplayRemoveLogicImpl extends AbstractShopLogic implements CategoryDisplayRemoveLogic {

    /**
     * カテゴリ表示情報削除DAO
     */
    private final CategoryDisplayDao categoryDisplayDao;

    @Autowired
    public CategoryDisplayRemoveLogicImpl(CategoryDisplayDao categoryDisplayDao) {
        this.categoryDisplayDao = categoryDisplayDao;
    }

    /**
     * カテゴリ表示情報削除<br/>
     *
     * @param categoryDisplayEntity カテゴリ表示エンティティ
     * @return 件数
     */
    @Override
    public int execute(CategoryDisplayEntity categoryDisplayEntity) {
        return categoryDisplayDao.delete(categoryDisplayEntity);
    }
}
