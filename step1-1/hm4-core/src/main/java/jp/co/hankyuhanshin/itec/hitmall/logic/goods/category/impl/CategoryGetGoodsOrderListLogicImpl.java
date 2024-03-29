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
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetGoodsOrderListLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * カテゴリに紐づくカテゴリ登録商品を取得
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
@Component
public class CategoryGetGoodsOrderListLogicImpl extends AbstractShopLogic implements CategoryGetGoodsOrderListLogic {

    /**
     * カテゴリ登録商品情報DAO
     */
    private final CategoryGoodsDao categoryGoodsDao;

    @Autowired
    public CategoryGetGoodsOrderListLogicImpl(CategoryGoodsDao categoryGoodsDao) {
        this.categoryGoodsDao = categoryGoodsDao;
    }

    /**
     * カテゴリに紐づくカテゴリ登録商品を取得
     *
     * @param categorySeq      カテゴリSEQ
     * @param goodsGroupSeq    商品グループSEQ
     * @param fromOrderDisplay From並び順
     * @param toOrderDisplay   To並び順
     * @param orderBy          昇順、降順
     * @return list カテゴリ登録商品リスト
     */
    @Override
    public List<CategoryGoodsEntity> execute(Integer categorySeq,
                                             Integer goodsGroupSeq,
                                             Integer fromOrderDisplay,
                                             Integer toOrderDisplay,
                                             boolean orderBy) {
        ArgumentCheckUtil.assertNotNull("categorySeq", categorySeq);
        return categoryGoodsDao.getCategoryGoodsOrderList(
                        categorySeq, goodsGroupSeq, fromOrderDisplay, toOrderDisplay, orderBy);
    }
}
