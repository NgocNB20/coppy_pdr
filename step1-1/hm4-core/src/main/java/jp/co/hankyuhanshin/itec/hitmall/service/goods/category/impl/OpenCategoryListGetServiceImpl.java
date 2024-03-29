/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryDetailsListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.OpenCategoryListGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公開カテゴリリスト取得
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
@Service
public class OpenCategoryListGetServiceImpl extends AbstractShopService implements OpenCategoryListGetService {

    /**
     * カテゴリ情報リスト取得ロジッククラス
     */
    private final CategoryDetailsListGetLogic categoryDetailsListGetLogic;

    @Autowired
    public OpenCategoryListGetServiceImpl(CategoryDetailsListGetLogic categoryDetailsListGetLogic) {
        this.categoryDetailsListGetLogic = categoryDetailsListGetLogic;
    }

    /**
     * カテゴリSEQリストを元にカテゴリDTOのリストを取得する<br/>
     *
     * @param categorySeqList カテゴリSEQリスト
     * @return カテゴリ情報DTOリスト
     */
    @Override
    public List<CategoryDetailsDto> execute(List<Integer> categorySeqList) {

        // <処理内容>
        // (1) パラメータチェック
        // ・カテゴリSEQリスト ： null 又は 0件の場合 エラーとして処理を終了する
        ArgumentCheckUtil.assertNotEmpty("categorySeqList", categorySeqList);

        // (2) 共通情報チェック
        // ・サイト区分 ： null(or空文字) の場合 エラーとして処理を終了する
        HTypeSiteType siteType = HTypeSiteType.FRONT_PC;

        // (3) カテゴリ情報リスト取得処理実行
        // ・カテゴリ情報Dao用検索条件Dtoを作成する
        // ・カテゴリ情報Dao用検索条件Dto
        // ‥ショップSEQ = null
        // ‥カテゴリID = null
        // ‥カテゴリSEQリスト = パラメータ.カテゴリSEQリスト
        // ‥最大表示階層数 = null
        // ‥サイト区分 = 共通情報.サイト区分
        // ‥公開状態 = 公開中
        // ‥ソート条件 = カテゴリ.カテゴリパス
        // ‥OFFSET = 0
        // ‥LIMIT = 100
        // ‥COUNT = null
        CategorySearchForDaoConditionDto categorySearchForDaoConditionDto =
                        ApplicationContextUtility.getBean(CategorySearchForDaoConditionDto.class);
        categorySearchForDaoConditionDto.setShopSeq(null);
        categorySearchForDaoConditionDto.setCategoryId(null);
        categorySearchForDaoConditionDto.setCategorySeqList(categorySeqList);
        categorySearchForDaoConditionDto.setSiteType(siteType);
        categorySearchForDaoConditionDto.setOpenStatus(HTypeOpenStatus.OPEN);

        categorySearchForDaoConditionDto.setOrderField("categorypath");
        categorySearchForDaoConditionDto.setOrderAsc(true);

        // ・カテゴリ情報リスト取得処理を実行する
        // ※『logic基本設計書（カテゴリ情報リスト取得）.xls』参照
        // Logic CategoryListGetLogic
        // パラメータ ・カテゴリ情報Dao用検索条件Dto
        // (公開状態=公開中)
        // 戻り値 カテゴリエンティティリスト
        return categoryDetailsListGetLogic.execute(categorySearchForDaoConditionDto);
    }

}
