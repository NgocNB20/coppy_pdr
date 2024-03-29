/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * カテゴリ取得<br/>
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
@Service
public class CategoryGetServiceImpl extends AbstractShopService implements CategoryGetService {

    /**
     * カテゴリ情報取得
     */
    private final CategoryGetLogic categoryGetLogic;

    @Autowired
    public CategoryGetServiceImpl(CategoryGetLogic categoryGetLogic) {
        this.categoryGetLogic = categoryGetLogic;
    }

    /**
     * カテゴリの取得<br/>
     *
     * @param categoryId カテゴリId
     * @return カテゴリ情報エンティティ
     */
    @Override
    public CategoryEntity execute(String categoryId) {

        AssertionUtil.assertNotNull("categoryId", categoryId);

        CategoryEntity categoryEntity = categoryGetLogic.execute(1001, categoryId);

        return categoryEntity;
    }

}
