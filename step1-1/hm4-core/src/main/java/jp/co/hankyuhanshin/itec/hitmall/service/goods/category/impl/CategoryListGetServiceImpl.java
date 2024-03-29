/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryListGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * カテゴリリスト取得
 *
 * @author MN7017
 * @version $Revision: 1.2 $
 */
@Service
public class CategoryListGetServiceImpl extends AbstractShopService implements CategoryListGetService {

    /**
     * カテゴリ情報リスト取得ロジッククラス
     */
    private final CategoryListGetLogic categoryListGetLogic;

    @Autowired
    public CategoryListGetServiceImpl(CategoryListGetLogic categoryListGetLogic) {
        this.categoryListGetLogic = categoryListGetLogic;
    }

    /**
     * カテゴリDTOのリストを取得する<br/>
     *
     * @param categorySearchForDaoConditionDto 検索条件DTO
     * @return カテゴリ情報エンティティリスト
     */
    @Override
    public List<CategoryEntity> execute(CategorySearchForDaoConditionDto categorySearchForDaoConditionDto,
                                        HTypeSiteType siteType) {

        // <処理内容>
        // (1) パラメータチェック
        // ・カテゴリDao用検索条件DTO ： null の場合、 エラーとして処理を終了する
        ArgumentCheckUtil.assertNotNull("categorySearchForDaoConditionDto", categorySearchForDaoConditionDto);

        // (2) 共通情報チェック
        // ・サイト区分 ： null(or空文字) の場合 エラーとして処理を終了する

        AssertionUtil.assertNotNull("siteType", siteType);
        // ・ショップSEQ ： null(or空文字）の場合エラーとして処理を終了する
        Integer shopSeq = 1001;
        AssertionUtil.assertNotNull("shopSeq", shopSeq);

        // (3) 共通情報設定
        // ‥ショップSEQ = 共通情報.ショップSEQ
        // ‥サイト区分 = 共通情報.サイト区分
        categorySearchForDaoConditionDto.setShopSeq(shopSeq);
        categorySearchForDaoConditionDto.setSiteType(siteType);

        // ・カテゴリ情報リスト取得処理を実行する
        // ※『logic基本設計書（カテゴリ情報リスト取得）.xls』参照
        // Logic CategoryListGetLogic
        // パラメータ ・カテゴリ情報Dao用検索条件Dto
        // (公開状態=null)
        // 戻り値 カテゴリエンティティリスト
        return categoryListGetLogic.execute(categorySearchForDaoConditionDto);
    }

}
