/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.category.CategoryDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryExclusiveDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetExclusiveLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * カテゴリ排他を取得
 *
 * @author chinhmc (ALX)  2019/11/28 チケット #4119 対応  排他チェック
 * @author chinhmc (ALX)  2019/11/29 チケット #4119 対応  カテゴリー削除の排他制御
 */
@Component
public class CategoryGetExclusiveLogicImpl extends AbstractShopLogic implements CategoryGetExclusiveLogic {

    /**
     * カテゴリ情報DAO
     */
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryGetExclusiveLogicImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public CategoryExclusiveDto execute() {
        return categoryDao.getExclusiveCategory();
    }
}
