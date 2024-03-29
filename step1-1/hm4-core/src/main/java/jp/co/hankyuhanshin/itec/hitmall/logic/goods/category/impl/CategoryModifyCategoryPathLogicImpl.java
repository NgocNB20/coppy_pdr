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
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryModifyCategoryPathLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 子階層のカテゴリーパスを更新<br/>
 *
 * @author wt32118
 */
@Component
public class CategoryModifyCategoryPathLogicImpl extends AbstractShopLogic implements CategoryModifyCategoryPathLogic {

    /**
     * カテゴリ情報DAO
     */
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryModifyCategoryPathLogicImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public int execute(CategoryEntity categoryEntity) {

        AssertionUtil.assertNotNull("categoryEntity", categoryEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        categoryEntity.setUpdateTime(dateUtility.getCurrentTime());

        return categoryDao.updateCategoryPath(categoryEntity);
    }

}
