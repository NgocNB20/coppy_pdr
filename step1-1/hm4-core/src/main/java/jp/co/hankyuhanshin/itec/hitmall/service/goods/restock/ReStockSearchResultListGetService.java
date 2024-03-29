/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockSearchResultDto;

import java.util.List;

/**
 * 入荷お知らせ商品検索結果リスト取得サービス<br/>
 *
 * @author st75001
 * @version $Revision: 1.2 $
 */
public interface ReStockSearchResultListGetService {

    /**
     * 実行メソッド<br/>
     *
     * @param searchCondition 入荷お知らせ商品Dao用検索条件Dto
     * @return 入荷お知らせ商品検索結果Dtoリスト
     */
    List<ReStockSearchResultDto> execute(ReStockSearchForBackDaoConditionDto searchCondition, HTypeSiteType siteType);
}
