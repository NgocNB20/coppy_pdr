/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryDisplayModifyLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryModifyLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryModifyService;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * カテゴリ修正<br/>
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
@Service
public class CategoryModifyServiceImpl extends AbstractShopService implements CategoryModifyService {

    /**
     * カテゴリ修正
     */
    private final CategoryModifyLogic categoryModifyLogic;

    /**
     * カテゴリ表示修正
     */
    private final CategoryDisplayModifyLogic categoryDisplayModigyLogic;

    @Autowired
    public CategoryModifyServiceImpl(CategoryModifyLogic categoryModifyLogic,
                                     CategoryDisplayModifyLogic categoryDisplayModigyLogic) {

        this.categoryModifyLogic = categoryModifyLogic;
        this.categoryDisplayModigyLogic = categoryDisplayModigyLogic;
    }

    /**
     * カテゴリの修正<br/>
     *
     * @param categoryDto カテゴリ情報DTO
     * @return 件数
     */
    @Override
    public int execute(CategoryDto categoryDto) {

        int size = 0;
        AssertionUtil.assertNotNull("categoryDto", categoryDto);

        size = categoryModifyLogic.execute(categoryDto.getCategoryEntity());

        if (size != 1) {
            return size;
        }

        size = categoryDisplayModigyLogic.execute(categoryDto.getCategoryDisplayEntity());

        if (size != 1) {
            return size;
        }

        return size;
    }

}
