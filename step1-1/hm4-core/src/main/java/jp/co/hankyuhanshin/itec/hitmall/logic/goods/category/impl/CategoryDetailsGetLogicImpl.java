/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.category.CategoryDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryDetailsGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * カテゴリ情報DTO取得<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
@Component
public class CategoryDetailsGetLogicImpl extends AbstractShopLogic implements CategoryDetailsGetLogic {

    /**
     * カテゴリ分類リスト用valueカラム名
     */
    protected static final String VALUE_COLNAME = "categoryid";
    /**
     * カテゴリ分類リスト用ラベルカラム名
     */
    protected static final String LABEL_COLNAME = "categoryname";

    /**
     * カテゴリDAO
     */
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryDetailsGetLogicImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * カテゴリ情報DTOの取得<br/>
     *
     * @param conditionDto カテゴリ検索条件DTO
     * @return カテゴリ詳細DTO
     */
    @Override
    public CategoryDetailsDto execute(CategorySearchForDaoConditionDto conditionDto) {
        ArgumentCheckUtil.assertNotNull("conditionDto", conditionDto);
        ArgumentCheckUtil.assertGreaterThanZero("shopSeq", conditionDto.getShopSeq());
        ArgumentCheckUtil.assertNotEmpty("categoryId", conditionDto.getCategoryId());

        CategoryDetailsDto categoryDetailsDto = categoryDao.getCategoryInfo(conditionDto);

        return categoryDetailsDto;
    }

    /**
     * To fetch list of CategoryDetailsDto
     *
     * @param categoryIdList List of categoryId
     * @return list of CategoryDetailsDto
     */
    @Override
    public List<CategoryDetailsDto> getCategoryDetailsDtoList(List<String> categoryIdList) {

        List<CategoryDetailsDto> categoryDetailsDtoList =
                        categoryDao.getCategoryDetailsDtoListByCategoryId(categoryIdList, 1001, HTypeSiteType.FRONT_PC,
                                                                          HTypeOpenStatus.OPEN
                                                                         );

        List<CategoryDetailsDto> resultCategoryDetailsDtoList = new ArrayList<>();
        for (String categoryId : categoryIdList) {
            for (CategoryDetailsDto categoryDetailsDto : categoryDetailsDtoList) {
                if (categoryId.equals(categoryDetailsDto.getCategoryId())) {
                    resultCategoryDetailsDtoList.add(categoryDetailsDto);
                    break;
                }
            }
        }

        return resultCategoryDetailsDtoList;
    }

    /**
     * カテゴリ分類リスト取得ロジック(ショップSEQのみ指定)
     *
     * @param shopSeq ショップSEQ
     * @return カテゴリ分類エンティティリスト
     */
    @Override
    public Map<String, String> getItemMapList(Integer shopSeq) {

        // 取得
        List<Map<String, Object>> deliveryMapList = categoryDao.getItemMapList(shopSeq);

        Map<String, String> map = new LinkedHashMap<String, String>();
        if (map != null) {
            for (Map<String, ?> deliveryMap : deliveryMapList) {
                map.put(deliveryMap.get(VALUE_COLNAME).toString(), deliveryMap.get(LABEL_COLNAME).toString());
            }
        }

        return map;
    }
}
