/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryTableLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * カテゴリテーブルロック<br/>
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
@Service
public class CategoryTableLockServiceImpl extends AbstractShopService implements CategoryTableLockService {

    /**
     * カテゴリテーブルロック
     */
    private final CategoryTableLockLogic categoryTableLockLogic;

    @Autowired
    public CategoryTableLockServiceImpl(CategoryTableLockLogic categoryTableLockLogic) {
        this.categoryTableLockLogic = categoryTableLockLogic;
    }

    /**
     * カテゴリテーブルロック<br/>
     */
    @Override
    public void execute() {

        // カテゴリテーブルロック処理
        categoryTableLockLogic.execute();
    }

}
