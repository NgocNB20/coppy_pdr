/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.category.CategoryDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetMaxOrderdisplayLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * カテゴリ情報取得
 *
 * @author kimura
 * @version $Revision: 1.2 $
 */
@Component
public class CategoryGetMaxOrderdisplayLogicImpl extends AbstractShopLogic implements CategoryGetMaxOrderdisplayLogic {

    /**
     * カテゴリ情報DAO
     */
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryGetMaxOrderdisplayLogicImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * @param shopSeq         ショップSEQ
     * @param categorySeqPath 親カテゴリSEQパス
     * @param categoryLevel   カレントのレベル
     * @return int 指定レベルの最大の並び順
     */
    @Override
    public int execute(Integer shopSeq, String categorySeqPath, Integer categoryLevel) {

        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);
        ArgumentCheckUtil.assertNotNull("categorySeqPath", categorySeqPath);
        ArgumentCheckUtil.assertNotNull("categoryLevel", categoryLevel);

        return categoryDao.getMaxOrderdisplay(shopSeq, categorySeqPath, categoryLevel);
    }
}
