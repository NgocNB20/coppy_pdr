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
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryLockLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * カテゴリレコードロック取得ロジック
 *
 * @author hirata
 * @version $Revision: 1.2 $
 */
@Component
public class CategoryLockLogicImpl extends AbstractShopLogic implements CategoryLockLogic {

    /**
     * カテゴリ情報DAO
     */
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryLockLogicImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * カテゴリレコードロック<br/>
     * カテゴリテーブルのレコードを排他取得する。<br/>
     *
     * @param categorySeqList カテゴリSEQリスト
     */
    @Override
    public void execute(List<Integer> categorySeqList) {

        // (1) パラメータチェック
        // カテゴリSEQリストが null または 0件 でないかをチェック
        ArgumentCheckUtil.assertNotEmpty("categorySeqList", categorySeqList);

        // (2) カテゴリのレコード排他取得
        List<CategoryEntity> categoryEntityList = categoryDao.getCategoryBySeqForUpdate(categorySeqList);
        // カテゴリSEQリストと取得件数が異なる場合
        if (categorySeqList.size() != categoryEntityList.size()) {
            // カテゴリ排他取得エラーを投げる。
            throwMessage(MSGCD_CATEGORY_SELECT_FOR_UPDATE_FAIL);
        }
    }
}
