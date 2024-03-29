/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.stock.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockSearchResultGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.stock.StockSearchResultGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 在庫検索結果リスト取得<br/>
 * 在庫検索結果リスト（画面表示用）を取得する。<br/>
 *
 * @author MN7017
 * @version $Revision: 1.5 $
 */
@Service
public class StockSearchResultGetServiceImpl extends AbstractShopService implements StockSearchResultGetService {

    /**
     * stockSearchResultGetLogic<br/>
     */
    private final StockSearchResultGetLogic stockSearchResultGetLogic;

    @Autowired
    public StockSearchResultGetServiceImpl(StockSearchResultGetLogic stockSearchResultGetLogic) {
        this.stockSearchResultGetLogic = stockSearchResultGetLogic;
    }

    /**
     * 在庫検索結果リスト取得<br/>
     * 在庫検索結果リスト（画面表示用）を取得する。<br/>
     *
     * @param stockSearchForDaoConditionDto 在庫検索条件DTO
     * @return 在庫検索結果リスト
     */
    @Override
    public List<StockSearchResultDto> execute(StockSearchForDaoConditionDto stockSearchForDaoConditionDto,
                                              HTypeSiteType siteType) {

        // (1)パラメータチェック
        ArgumentCheckUtil.assertNotNull("検索条件が取得できません。", stockSearchForDaoConditionDto);

        // (2)検索条件DTOを編集
        stockSearchForDaoConditionDto.setShopSeq(1001);
        stockSearchForDaoConditionDto.setSiteType(siteType);

        // (3)ロジック処理を実行
        List<StockSearchResultDto> resultList = stockSearchResultGetLogic.execute(stockSearchForDaoConditionDto);

        // (4)戻り値
        return resultList;
    }
}
