/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetGoodsOrderListLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGoodsTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryModifyGoodsLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryModifyGoodsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * カテゴリ内商品の並び順変更<br/>
 *
 * @author kimura
 * @version $Revision: 1.2 $
 */
@Service
public class CategoryModifyGoodsOrderServiceImpl extends AbstractShopService
                implements CategoryModifyGoodsOrderService {

    /**
     * カテゴリ登録商品テーブルロック取得
     */
    private final CategoryGoodsTableLockLogic categoryGoodsTableLockLogic;

    /**
     * カテゴリ登録商品情報取得
     */
    private final CategoryGetGoodsOrderListLogic categoryGetGoodsOrderListLogic;

    /**
     * カテゴリ商品修正
     */
    private final CategoryModifyGoodsLogic categoryModifyGoodsLogic;

    @Autowired
    public CategoryModifyGoodsOrderServiceImpl(CategoryGoodsTableLockLogic categoryGoodsTableLockLogic,
                                               CategoryGetGoodsOrderListLogic categoryGetGoodsOrderListLogic,
                                               CategoryModifyGoodsLogic categoryModifyGoodsLogic) {

        this.categoryGoodsTableLockLogic = categoryGoodsTableLockLogic;
        this.categoryGetGoodsOrderListLogic = categoryGetGoodsOrderListLogic;
        this.categoryModifyGoodsLogic = categoryModifyGoodsLogic;
    }

    /**
     * カテゴリの並び順変更<br/>
     *
     * @param categorySeq      カテゴリSEQ
     * @param fromOrderDisplay From並び順
     * @param toOrderDisplay   To並び順
     * @return 件数
     */
    @Override
    public int execute(Integer categorySeq, Integer fromOrderDisplay, Integer toOrderDisplay) {

        int size = 0;

        // カテゴリ登録商品テーブルロック
        categoryGoodsTableLockLogic.execute();

        List<CategoryGoodsEntity> list = null;

        if (fromOrderDisplay.compareTo(toOrderDisplay) > 0) {
            list = categoryGetGoodsOrderListLogic.execute(categorySeq, null, toOrderDisplay, fromOrderDisplay, true);
        } else {
            list = categoryGetGoodsOrderListLogic.execute(categorySeq, null, fromOrderDisplay, toOrderDisplay, false);
        }

        for (int i = 0; i < list.size() - 1; i++) {
            CategoryGoodsEntity entity = list.get(i);
            CategoryGoodsEntity entity2 = list.get(i + 1);
            entity.setOrderDisplay(entity2.getOrderDisplay());
        }

        CategoryGoodsEntity tagetEntity = list.get(list.size() - 1);
        tagetEntity.setOrderDisplay(toOrderDisplay);
        Iterator<CategoryGoodsEntity> ite = list.iterator();
        while (ite.hasNext()) {
            CategoryGoodsEntity entity = ite.next();
            if (categoryModifyGoodsLogic.execute(entity) != 1) {
                return 0;
            }
            size++;
        }

        return size;

    }

}
