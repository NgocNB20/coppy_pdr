/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.stock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockDetailsGetByGoodsSeqLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.stock.StockDetailsGetByGoodsSeqService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 在庫詳細情報取得<br/>
 *
 * @author MN7017
 * @version $Revision: 1.1 $
 */
@Service
public class StockDetailsGetByGoodsSeqServiceImpl extends AbstractShopService
                implements StockDetailsGetByGoodsSeqService {

    /**
     * 在庫詳細取得ロジック<br/>
     */
    private final StockDetailsGetByGoodsSeqLogic stockDetailsGetByGoodsSeqLogic;

    @Autowired
    public StockDetailsGetByGoodsSeqServiceImpl(StockDetailsGetByGoodsSeqLogic stockDetailsGetByGoodsSeqLogic) {
        this.stockDetailsGetByGoodsSeqLogic = stockDetailsGetByGoodsSeqLogic;
    }

    /**
     * サービス実行処理<br/>
     *
     * @param goodsSeq 商品SEQ
     * @return 在庫詳細Dto
     */
    @Override
    public StockDetailsDto execute(Integer goodsSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertGreaterThanZero("goodsSeq", goodsSeq);

        // 取得ロジック実行
        return stockDetailsGetByGoodsSeqLogic.execute(goodsSeq);
    }
}
