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
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * カテゴリ情報登録
 *
 * @author kimura
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class CategoryRegistLogicImpl extends AbstractShopLogic implements CategoryRegistLogic {

    /**
     * カテゴリ情報登録DAO
     */
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryRegistLogicImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * カテゴリ情報登録<br/>
     *
     * @param categoryEntity カテゴリDTO
     * @return 件数
     */
    @Override
    public int execute(CategoryEntity categoryEntity) {

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        Timestamp currentTime = dateUtility.getCurrentTime();
        categoryEntity.setRegistTime(currentTime);
        categoryEntity.setUpdateTime(currentTime);
        return categoryDao.insert(categoryEntity);
    }
}
