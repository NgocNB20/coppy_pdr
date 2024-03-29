/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.category.CategoryGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryModifyGoodsLogic;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * カテゴリ商品情報修正
 *
 * @author kimura
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class CategoryModifyGoodsLogicImpl extends AbstractShopLogic implements CategoryModifyGoodsLogic {

    /**
     * カテゴリ商品情報修正DAO
     */
    private final CategoryGoodsDao categoryGoodsDao;

    @Autowired
    public CategoryModifyGoodsLogicImpl(CategoryGoodsDao categoryGoodsDao) {
        this.categoryGoodsDao = categoryGoodsDao;
    }

    /**
     * カテゴリ商品修正<br/>
     *
     * @param categoryGoodsEntity カテゴリ商品
     * @return 件数
     */
    @Override
    public int execute(CategoryGoodsEntity categoryGoodsEntity) {

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        categoryGoodsEntity.setUpdateTime(dateUtility.getCurrentTime());
        return categoryGoodsDao.update(categoryGoodsEntity);
    }
}
