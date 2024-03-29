// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.json.category;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCategoryType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;

import java.util.List;

/**
 * カテゴリーデータ取得ロジックインターフェース<br/>
 *
 * @author kato
 */
public interface CategoryDataGetLogic {

    /**
     * 指定した条件でカテゴリー情報リストを取得する。
     *
     * @param conditionDto       カテゴリ検索条件DTO
     * @param startCategorylevel 開始カテゴリー階層
     * @param endCategorylevel   終了カテゴリー階層
     * @param categoryType       カテゴリー種類
     * @return カテゴリ詳細情報DTOリスト
     */
    List<CategoryDetailsDto> execute(CategorySearchForDaoConditionDto conditionDto,
                                     Integer startCategorylevel,
                                     Integer endCategorylevel,
                                     HTypeCategoryType categoryType);

}
// PDR Migrate Customization to here
