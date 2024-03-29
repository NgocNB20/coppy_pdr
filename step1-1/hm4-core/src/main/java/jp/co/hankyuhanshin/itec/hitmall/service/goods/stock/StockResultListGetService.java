/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.stock;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockResultSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockResultEntity;

import java.util.List;

/**
 * 入庫実績リスト取得<br/>
 * 検索条件に該当する入庫実績リストを取得する。<br/>
 *
 * @author MN7017
 * @version $Revision: 1.4 $
 */
public interface StockResultListGetService {

    /**
     * 入庫実績リスト取得<br/>
     * 検索条件に該当する入庫実績リストを取得する。<br/>
     *
     * @param stockResultSearchForDaoConditionDto 入庫実績Dao用検索条件DTO
     * @return 入庫実績エンティティリスト
     */
    List<StockResultEntity> execute(StockResultSearchForDaoConditionDto stockResultSearchForDaoConditionDto);

}
