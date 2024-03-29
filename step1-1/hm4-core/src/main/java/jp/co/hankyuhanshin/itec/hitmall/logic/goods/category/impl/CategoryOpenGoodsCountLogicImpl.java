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
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryOpenGoodsCountLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * カテゴリIDに紐づく公開商品数を取得
 * ※カテゴリIDに紐づく子階層の公開商品数も合わせて取得する
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
@Component
public class CategoryOpenGoodsCountLogicImpl extends AbstractShopLogic implements CategoryOpenGoodsCountLogic {

    /**
     * カテゴリ情報DAO
     */
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryOpenGoodsCountLogicImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * カテゴリIDに紐づく公開商品数を取得
     * ※カテゴリIDに紐づく子階層の公開商品数も合わせて取得する
     *
     * @param categorySeqList 取得するカテゴリのSEQリスト
     * @return 公開商品件数DTOリスト
     */
    @Override
    public List<CategoryEntity> execute(List<Integer> categorySeqList) {

        ArgumentCheckUtil.assertNotNull("categorySeqList", categorySeqList);

        return categoryDao.getCategoryOpenGoodsCount(categorySeqList);
    }

}
