/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.category.CategoryDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.ChildNodeCategorySearchLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 子ノードカテゴリ情報検索
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
@Component
public class ChildNodeCategorySearchLogicImpl extends AbstractShopLogic implements ChildNodeCategorySearchLogic {

    /**
     * カテゴリ情報DAO
     */
    private final CategoryDao categoryDao;

    @Autowired
    public ChildNodeCategorySearchLogicImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * 指定したカテゴリの子カテゴリ情報リストを取得する。
     *
     * @param conditionDto      カテゴリ情報Dao用検索条件DTO
     * @param currentCategoryId 表示するカテゴリID
     * @return カテゴリ情報詳細DTOリスト
     */
    @Override
    public List<CategoryDetailsDto> execute(CategorySearchForDaoConditionDto conditionDto, String currentCategoryId) {

        // (1) パラメータチェック
        // カテゴリ情報Dao用検索条件DTOが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("conditionDto", conditionDto);

        // (2) カテゴリ詳細情報DTOリスト取得
        // カテゴリとカテゴリ表示テーブルの情報を取得する。
        // DAO CategoryDao
        // メソッド List<カテゴリ詳細DTO> getSearchCategoryList(
        // （パラメータ）カテゴリ情報Dao用検索条件DTO,カテゴリID)
        List<CategoryDetailsDto> categoryDetailsDtoList =
                        categoryDao.getSearchCategoryList(conditionDto, currentCategoryId);

        // (3) 戻り値
        // 取得したカテゴリ詳細DTOリストを返す。
        // （該当するカテゴリ情報がない場合は 空のリスト を返す）
        return categoryDetailsDtoList;

    }
}
