/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockSearchResultDto;

import java.util.List;

/**
 * 在庫検索結果リスト取得ロジック<br/>
 *
 * @author MN7017
 * @version $Revision: 1.2 $
 */
public interface StockSearchResultGetLogic {

    /**
     * 実行メソッド<br/>
     *
     * @param stockSearchForDaoConditionDto 在庫Dao用検索結果DTO
     * @return 在庫検索結果DTOリスト
     */
    List<StockSearchResultDto> execute(StockSearchForDaoConditionDto stockSearchForDaoConditionDto);
}
