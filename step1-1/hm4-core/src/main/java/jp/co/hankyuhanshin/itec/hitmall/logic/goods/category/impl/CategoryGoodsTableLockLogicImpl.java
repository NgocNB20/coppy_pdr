/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.category.CategoryGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGoodsTableLockLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * カテゴリ登録商品テーブルロック取得ロジック
 *
 * @author hirata
 * @version $Revision: 1.2 $
 */
@Component
public class CategoryGoodsTableLockLogicImpl extends AbstractShopLogic implements CategoryGoodsTableLockLogic {

    /**
     * カテゴリ登録商品DAO
     */
    private final CategoryGoodsDao categoryGoodsDao;

    @Autowired
    public CategoryGoodsTableLockLogicImpl(CategoryGoodsDao categoryGoodsDao) {
        this.categoryGoodsDao = categoryGoodsDao;
    }

    /**
     * カテゴリ登録商品テーブルロック<br/>
     * カテゴリ登録商品テーブルのレコードを排他取得する。<br/>
     */
    @Override
    public void execute() {

        // (1) カテゴリ登録商品テーブル排他取得
        categoryGoodsDao.updateLockTableShareModeNowait();
    }
}
