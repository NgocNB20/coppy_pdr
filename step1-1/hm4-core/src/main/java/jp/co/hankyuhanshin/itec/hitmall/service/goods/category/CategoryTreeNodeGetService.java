/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;

/**
 * カテゴリ木構造取得<br/>
 *
 * @author ozaki
 * @version $Revision: 1.3 $
 */
public interface CategoryTreeNodeGetService {

    /**
     * カテゴリIDを元に展示商品情報の商品リストを取得する。<br/>
     *
     * @param conditionDto      カテゴリ情報検索条件DTO
     * @param currentCategoryId 表示カテゴリID
     * @return カテゴリ情報DTO
     */
    CategoryDto execute(CategorySearchForDaoConditionDto conditionDto,
                        String currentCategoryId,
                        HTypeSiteType siteType);

    /**
     * TOPから全てのカテゴリー情報を階層の形で取得する。<br/>
     *
     * @return カテゴリ情報DTO
     */
    CategoryDto executeForAllCategory(HTypeSiteType siteType);

    /**
     * 最大表示階層が指定されたカテゴリー情報を階層の形で取得する。<br/>
     *
     * @param maxHierarchical 最大表示階層
     * @return カテゴリ情報DTO
     */
    CategoryDto executeSpecificHierarchical(Integer maxHierarchical, HTypeSiteType siteType);

    /**
     * カテゴリIDを元に親・子カテゴリ情報を階層の形で取得する。<br/>
     *
     * @param categoryId カテゴリID
     * @return カテゴリDTO
     */
    CategoryDto executeForAllNode(String categoryId);

    /**
     * エラーコード：カテゴリDTOがNULL
     */
    public static final String MSGCD_CATEGORYDTO_NULL = "SGC000301";
}
