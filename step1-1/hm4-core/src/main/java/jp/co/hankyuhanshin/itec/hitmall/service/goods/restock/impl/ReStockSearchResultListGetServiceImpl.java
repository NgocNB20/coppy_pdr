/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.restock.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock.ReStockSearchResultListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.restock.ReStockSearchResultListGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 入荷お知らせ商品管理機能詳細リスト取得サービス実装クラス<br/>
 *
 * @author st75001
 * @version $Revision: 1.2 $
 */
@Service
public class ReStockSearchResultListGetServiceImpl extends AbstractShopService
                implements ReStockSearchResultListGetService {

    /**
     * 商品検索結果リスト取得ロジック
     */
    private final ReStockSearchResultListGetLogic reStockSearchResultListGetLogic;

    @Autowired
    public ReStockSearchResultListGetServiceImpl(ReStockSearchResultListGetLogic reStockSearchResultListGetLogic) {
        this.reStockSearchResultListGetLogic = reStockSearchResultListGetLogic;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param searchCondition 商品Dao用検索条件Dto
     * @return 商品検索結果Dtoリスト
     */
    @Override
    public List<ReStockSearchResultDto> execute(ReStockSearchForBackDaoConditionDto searchCondition,
                                                HTypeSiteType siteType) {

        // (1)パラメータチェック
        ArgumentCheckUtil.assertNotNull("searchCondition", searchCondition);

        // (2) 共通情報チェック

        // (3)検索条件Dtoの編集

        // (4)Logic処理を実行
        return reStockSearchResultListGetLogic.execute(searchCondition);
    }
}
