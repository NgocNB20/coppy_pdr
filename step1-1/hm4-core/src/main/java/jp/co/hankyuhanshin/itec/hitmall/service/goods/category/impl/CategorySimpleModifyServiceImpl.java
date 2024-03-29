/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryModifyLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategorySimpleModifyService;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * カテゴリ修正(カテゴリエンティティのみ更新)<br/>
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
@Service
public class CategorySimpleModifyServiceImpl extends AbstractShopService implements CategorySimpleModifyService {

    /**
     * カテゴリ修正
     */
    private final CategoryModifyLogic categoryModifyLogic;

    @Autowired
    public CategorySimpleModifyServiceImpl(CategoryModifyLogic categoryModifyLogic) {
        this.categoryModifyLogic = categoryModifyLogic;
    }

    /**
     * カテゴリの修正<br/>
     *
     * @param categoryEntity カテゴリエンティティ
     * @return 件数
     */
    @Override
    public int execute(CategoryEntity categoryEntity) {
        AssertionUtil.assertNotNull("categoryEntity", categoryEntity);
        return categoryModifyLogic.execute(categoryEntity);
    }

}
