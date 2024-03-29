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
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryDetailsListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * カテゴリ詳細情報リスト取得ロジック
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
@Component
public class CategoryDetailsListGetLogicImpl extends AbstractShopLogic implements CategoryDetailsListGetLogic {

    /**
     * カテゴリ情報DAO
     */
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryDetailsListGetLogicImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * カテゴリ詳細情報リストを取得する。
     *
     * @param conditionDto カテゴリ情報Dao用検索条件DTO
     * @return カテゴリ情報エンティティリスト
     */
    @Override
    public List<CategoryDetailsDto> execute(CategorySearchForDaoConditionDto conditionDto) {

        // (1) パラメータチェック
        // カテゴリ情報Dao用検索条件DTOが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("conditionDto", conditionDto);

        // (2) カテゴリエンティティリスト取得
        // カテゴリDaoのカテゴリエンティティリスト取得処理を実行する。
        // DAO CategoryDao
        // メソッド List<カテゴリエンティティ> getCategoryList( （パラメータ）カテゴリ情報Dao用検索条件DTO)
        List<CategoryDetailsDto> categoryDetailsDtoList = categoryDao.getCategoryInfoList(conditionDto);

        // (3) 戻り値
        // 取得したカテゴリエンティティリストを返す。
        // （該当するカテゴリ情報がない場合は 空のリスト を返す）
        return categoryDetailsDtoList;

    }
}
