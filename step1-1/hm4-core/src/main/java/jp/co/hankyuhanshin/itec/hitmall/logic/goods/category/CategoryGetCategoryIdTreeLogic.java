/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

/**
 * カテゴリーIDツリー取得Logic
 *
 * @author tk32120
 */
public interface CategoryGetCategoryIdTreeLogic {

    /**
     * TOPから指定のカテゴリーIDまでコロンで結合する
     *
     * @param categoryId カテゴリーID
     * @return カテゴリーIDツリー
     */
    String execute(String categoryId);
}
