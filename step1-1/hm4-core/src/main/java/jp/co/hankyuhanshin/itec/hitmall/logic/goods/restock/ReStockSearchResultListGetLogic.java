/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockSearchResultDto;

import java.util.List;

/**
 * 入荷お知らせ商品検索結果リスト取得<br/>
 *
 * @author st75001
 * @version $Revision: 1.2 $
 */
public interface ReStockSearchResultListGetLogic {

    /**
     * 実行メソッド<br/>
     *
     * @param searchCondition 商品Dao用検索条件（管理機能）Dto
     * @return 商品検索結果DTOリスト
     */
    List<ReStockSearchResultDto> execute(ReStockSearchForBackDaoConditionDto searchCondition);
}
