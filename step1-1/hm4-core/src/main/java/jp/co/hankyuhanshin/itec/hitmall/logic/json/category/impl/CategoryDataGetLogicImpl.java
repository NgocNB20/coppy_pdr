// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.json.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCategoryType;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.category.CategoryDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.json.category.CategoryDataGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * カテゴリーデータ取得ロジック<br/>
 *
 * @author kato
 */
@Component
public class CategoryDataGetLogicImpl extends AbstractShopLogic implements CategoryDataGetLogic {

    /** カテゴリ情報DAO */
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryDataGetLogicImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * 指定した条件でカテゴリー情報リストを取得する。
     *
     * @param conditionDto       カテゴリ検索条件DTO
     * @param startCategorylevel 開始カテゴリー階層
     * @param endCategorylevel   終了カテゴリー階層
     * @param categoryType       カテゴリー種類
     * @return カテゴリ情報エンティティDTOリスト
     */
    @Override
    public List<CategoryDetailsDto> execute(CategorySearchForDaoConditionDto conditionDto,
                                            Integer startCategorylevel,
                                            Integer endCategorylevel,
                                            HTypeCategoryType categoryType) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("conditionDto", conditionDto);
        ArgumentCheckUtil.assertNotNull("startCategorylevel", startCategorylevel);
        ArgumentCheckUtil.assertNotNull("endCategorylevel", endCategorylevel);
        ArgumentCheckUtil.assertNotNull("categoryType", categoryType);

        // カテゴリ詳細情報DTOリスト取得
        List<CategoryDetailsDto> categoryDetailsDtoList =
                        categoryDao.getCategoryDataJsonList(conditionDto, startCategorylevel, endCategorylevel,
                                                            categoryType
                                                           );

        // カテゴリ詳細情報DTOリストの返却
        // （該当するカテゴリ情報がない場合は 空のリスト を返す）
        return categoryDetailsDtoList;

    }

}
// PDR Migrate Customization to here
