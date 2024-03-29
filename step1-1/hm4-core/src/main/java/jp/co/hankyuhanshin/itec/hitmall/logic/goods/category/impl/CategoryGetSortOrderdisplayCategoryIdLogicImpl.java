/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.category.CategoryDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetSortOrderdisplayCategoryIdLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * カテゴリ並び順整備対象カテゴリIDリストを取得
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
@Component
public class CategoryGetSortOrderdisplayCategoryIdLogicImpl extends AbstractShopLogic
                implements CategoryGetSortOrderdisplayCategoryIdLogic {

    /**
     * カテゴリ情報DAO
     */
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryGetSortOrderdisplayCategoryIdLogicImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * カテゴリ並び順整備対象カテゴリIDリストを取得
     *
     * @param shopSeq               ショップSEQ
     * @param parentCategorySeqPath 親のカテゴリSEQパス
     * @param categoryLevel         レベル
     * @param orderdisplay          表示順
     * @return カテゴリIDリスト
     */
    @Override
    public List<String> execute(Integer shopSeq,
                                String parentCategorySeqPath,
                                Integer categoryLevel,
                                Integer orderdisplay) {

        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);
        ArgumentCheckUtil.assertNotNull("categorySeqPath", parentCategorySeqPath);
        ArgumentCheckUtil.assertNotNull("categoryLevel", categoryLevel);
        ArgumentCheckUtil.assertNotNull("orderdisplay", orderdisplay);

        return categoryDao.getSortOrderdisplayCategoryId(shopSeq, parentCategorySeqPath, categoryLevel, orderdisplay);
    }
}
