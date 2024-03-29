/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryOpenGoodsCountLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryOpenGoodsCountService;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * カテゴリIDに紐づく公開商品数を取得
 * ※カテゴリIDに紐づく子階層の公開商品数も合わせて取得する
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
@Service
public class CategoryOpenGoodsCountServiceImpl extends AbstractShopService implements CategoryOpenGoodsCountService {

    /**
     * カテゴリ情報取得
     */
    private final CategoryOpenGoodsCountLogic categoryOpenGoodsCountLogic;

    @Autowired
    public CategoryOpenGoodsCountServiceImpl(CategoryOpenGoodsCountLogic categoryOpenGoodsCountLogic) {
        this.categoryOpenGoodsCountLogic = categoryOpenGoodsCountLogic;
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

        AssertionUtil.assertNotNull("categorySeqList", categorySeqList);

        return categoryOpenGoodsCountLogic.execute(categorySeqList);
    }

}
