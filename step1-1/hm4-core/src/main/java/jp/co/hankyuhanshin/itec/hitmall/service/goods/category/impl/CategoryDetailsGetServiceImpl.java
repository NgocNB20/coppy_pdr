/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryDetailsGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryDetailsGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * カテゴリ情報DTO取得<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
@Service
public class CategoryDetailsGetServiceImpl extends AbstractShopService implements CategoryDetailsGetService {

    /**
     * カテゴリ情報取得
     */
    private final CategoryDetailsGetLogic categoryDetailsGetLogic;

    @Autowired
    public CategoryDetailsGetServiceImpl(CategoryDetailsGetLogic categoryDetailsGetLogic) {
        this.categoryDetailsGetLogic = categoryDetailsGetLogic;
    }

    /**
     * カテゴリ情報DTOの取得<br/>
     *
     * @param conditionDto カテゴリ検索条件DTO
     * @return カテゴリ詳細DTO
     */
    @Override
    public CategoryDetailsDto execute(CategorySearchForDaoConditionDto conditionDto) {

        AssertionUtil.assertNotNull("conditionDto", conditionDto);

        conditionDto.setShopSeq(1001);
        conditionDto.setSiteType(HTypeSiteType.FRONT_PC);

        return categoryDetailsGetLogic.execute(conditionDto);
    }

}
