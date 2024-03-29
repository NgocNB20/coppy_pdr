/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;

import java.util.List;

/**
 * カテゴリ情報リスト取得ロジック
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface CategoryListGetLogic {

    /**
     * カテゴリ情報リストを取得する。
     *
     * @param conditionDto カテゴリ情報Dao用検索条件DTO
     * @return カテゴリエンティティリスト
     */
    List<CategoryEntity> execute(CategorySearchForDaoConditionDto conditionDto);

}
