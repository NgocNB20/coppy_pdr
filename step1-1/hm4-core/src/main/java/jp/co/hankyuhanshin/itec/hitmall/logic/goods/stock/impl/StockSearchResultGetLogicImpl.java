/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock.StockDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockSearchResultGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 在庫検索結果リスト取得ロジック<br/>
 *
 * @author MN7017
 * @version $Revision: 1.5 $
 */
@Component
public class StockSearchResultGetLogicImpl extends AbstractShopLogic implements StockSearchResultGetLogic {

    /**
     * StockDao<br/>
     */
    private final StockDao stockDao;

    @Autowired
    public StockSearchResultGetLogicImpl(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    /**
     * 在庫検索結果リスト取得<br/>
     *
     * @param stockSearchForDaoConditionDto 在庫Dao用検索条件DTO
     * @return 在庫検索結果DTOリスト
     */
    @Override
    public List<StockSearchResultDto> execute(StockSearchForDaoConditionDto stockSearchForDaoConditionDto) {

        // (1)パラメータチェック
        ArgumentCheckUtil.assertNotNull("検索条件が取得できません。", stockSearchForDaoConditionDto);

        // (2)在庫検索結果リスト取得処理
        List<StockSearchResultDto> stockSearchResultDtoList = stockDao.getSearchStockList(stockSearchForDaoConditionDto,
                                                                                          stockSearchForDaoConditionDto.getPageInfo()
                                                                                                                       .getSelectOptions()
                                                                                         );
        return stockSearchResultDtoList;
    }
}
