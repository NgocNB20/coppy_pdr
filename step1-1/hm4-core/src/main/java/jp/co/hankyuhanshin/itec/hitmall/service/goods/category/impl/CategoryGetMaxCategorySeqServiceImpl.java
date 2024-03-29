/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetMaxCategorySeqLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryGetMaxCategorySeqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 現在のMAXSEQを取得
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
@Service
public class CategoryGetMaxCategorySeqServiceImpl extends AbstractShopService
                implements CategoryGetMaxCategorySeqService {

    /**
     * カテゴリ情報取得
     */
    private final CategoryGetMaxCategorySeqLogic categoryGetMaxCategorySeqLogic;

    @Autowired
    public CategoryGetMaxCategorySeqServiceImpl(CategoryGetMaxCategorySeqLogic categoryGetMaxCategorySeqLogic) {
        this.categoryGetMaxCategorySeqLogic = categoryGetMaxCategorySeqLogic;
    }

    /**
     * 現在のMAXSEQを取得<br/>
     *
     * @return MAXSEQ
     */
    @Override
    public int execute() {
        return categoryGetMaxCategorySeqLogic.execute();
    }

}
