/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;

import java.util.List;

/**
 * 子ノードカテゴリ情報検索
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface ChildNodeCategorySearchLogic {

    /**
     * 指定したカテゴリの子カテゴリ情報リストを取得する。
     *
     * @param conditionDto      カテゴリ情報Dao用検索条件DTO
     * @param currentCategoryId 現在表示しているカテゴリID
     * @return カテゴリ詳細情報Dtoリスト
     */
    List<CategoryDetailsDto> execute(CategorySearchForDaoConditionDto conditionDto, String currentCategoryId);

}
