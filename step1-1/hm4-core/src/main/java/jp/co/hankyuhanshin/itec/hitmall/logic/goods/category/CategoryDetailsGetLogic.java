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
import java.util.Map;

/**
 * カテゴリ情報DTO取得<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface CategoryDetailsGetLogic {

    /**
     * カテゴリ情報DTOの取得<br/>
     *
     * @param conditionDto カテゴリ検索条件DTO
     * @return カテゴリ詳細DTO
     */
    CategoryDetailsDto execute(CategorySearchForDaoConditionDto conditionDto);

    /**
     * To fetch list of CategoryDetailsDto
     *
     * @param categoryIdList List of categoryId
     * @return list of CategoryDetailsDto
     */
    List<CategoryDetailsDto> getCategoryDetailsDtoList(List<String> categoryIdList);

    /**
     * 選択項目リストの作成に利用するデータを返却する<br/>
     *
     * @param shopSeq ショップSEQ
     * @return カテゴリ結果を格納したMap
     */
    Map<String, String> getItemMapList(Integer shopSeq);
}
