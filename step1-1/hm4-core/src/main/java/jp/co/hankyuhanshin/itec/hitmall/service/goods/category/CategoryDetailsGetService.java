/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;

/**
 * カテゴリ情報DTO取得<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface CategoryDetailsGetService {

    /**
     * カテゴリ情報DTOの取得<br/>
     *
     * @param conditionDto カテゴリ検索条件DTO
     * @return カテゴリ詳細DTO
     */
    CategoryDetailsDto execute(CategorySearchForDaoConditionDto conditionDto);

}
