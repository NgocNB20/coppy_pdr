/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetGoodsOrderListLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryGetGoodsOrderListService;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * カテゴリに紐づくカテゴリ登録商品を取得
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
@Service
public class CategoryGetGoodsOrderListServiceImpl extends AbstractShopService
                implements CategoryGetGoodsOrderListService {

    /**
     * カテゴリ登録商品情報取得
     */
    private final CategoryGetGoodsOrderListLogic categoryGetGoodsOrderListLogic;

    @Autowired
    public CategoryGetGoodsOrderListServiceImpl(CategoryGetGoodsOrderListLogic categoryGetGoodsOrderListLogic) {
        this.categoryGetGoodsOrderListLogic = categoryGetGoodsOrderListLogic;
    }

    /**
     * カテゴリに紐づくカテゴリ登録商品を取得
     *
     * @param categorySeq   カテゴリSEQ
     * @param goodsGroupSeq 商品グループSEQ
     * @return list カテゴリ登録商品リスト
     */
    @Override
    public List<CategoryGoodsEntity> execute(Integer categorySeq, Integer goodsGroupSeq) {
        AssertionUtil.assertNotNull("categorySeq", categorySeq);
        return categoryGetGoodsOrderListLogic.execute(categorySeq, goodsGroupSeq, null, null, true);
    }

}
