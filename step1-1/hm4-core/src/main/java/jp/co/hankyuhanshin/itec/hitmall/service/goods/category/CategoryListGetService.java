/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;

import java.util.List;

/**
 * カテゴリ情報リスト取得<br/>
 *
 * @author MN7017
 * @version $Revision: 1.2 $
 */
public interface CategoryListGetService {

    /**
     * カテゴリDTOのリストを取得する<br/>
     *
     * @param categorySearchForDaoConditionDto 検索条件DTO
     * @return カテゴリエンティティリスト
     */
    List<CategoryEntity> execute(CategorySearchForDaoConditionDto categorySearchForDaoConditionDto,
                                 HTypeSiteType siteType);

}
