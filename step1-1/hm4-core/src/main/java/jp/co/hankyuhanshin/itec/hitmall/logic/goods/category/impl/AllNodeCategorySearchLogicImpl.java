/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.category.CategoryDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.AllNodeCategorySearchLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 親・子ノードカテゴリ情報取得<br/>
 *
 * @author wt32118
 */
@Component
public class AllNodeCategorySearchLogicImpl extends AbstractShopLogic implements AllNodeCategorySearchLogic {

    /**
     * カテゴリ情報DAO
     */
    private final CategoryDao categoryDao;

    @Autowired
    public AllNodeCategorySearchLogicImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * 指定したカテゴリIdの親・子カテゴリ情報リストを取得する。
     *
     * @param categoryId カテゴリID
     * @return カテゴリ情報詳細DTOリスト
     */
    @Override
    public List<CategoryDetailsDto> execute(String categoryId) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("categoryId", categoryId);

        // カテゴリ詳細情報DTOリスト取得
        List<CategoryDetailsDto> categoryDetailsDtoList = categoryDao.getCategoryListByCategoryId(categoryId);

        return categoryDetailsDtoList;

    }

}
