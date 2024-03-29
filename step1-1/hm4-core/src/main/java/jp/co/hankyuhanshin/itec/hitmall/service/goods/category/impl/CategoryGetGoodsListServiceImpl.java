/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryGoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryGoodsSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetGoodsListLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryGetGoodsListService;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * カテゴリに紐づく商品ｸﾞﾙｰﾌﾟﾘｽﾄを取得
 *
 * @author kimura
 * @version $Revision: 1.4 $
 */
@Service
public class CategoryGetGoodsListServiceImpl extends AbstractShopService implements CategoryGetGoodsListService {

    /**
     * カテゴリ情報取得
     */
    private final CategoryGetGoodsListLogic categoryGetGoodsListLogic;

    @Autowired
    public CategoryGetGoodsListServiceImpl(CategoryGetGoodsListLogic categoryGetGoodsListLogic) {
        this.categoryGetGoodsListLogic = categoryGetGoodsListLogic;
    }

    /**
     * カテゴリに紐づく商品ｸﾞﾙｰﾌﾟﾘｽﾄを取得
     *
     * @param conditionDto カテゴリ商品情報Dao用検索条件Dto
     * @return カテゴリ内商品詳細DTOﾘｽﾄ
     */
    @Override
    public List<CategoryGoodsDetailsDto> execute(CategoryGoodsSearchForDaoConditionDto conditionDto) {
        AssertionUtil.assertNotNull("conditionDto", conditionDto);
        return categoryGetGoodsListLogic.execute(conditionDto);
    }

}
