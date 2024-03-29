/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.stock.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockResultEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsStatusCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockSupplementLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.stock.StockSupplementService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 入庫登録<br/>
 * 入庫情報を登録する。<br/>
 *
 * @author MN7017
 */
@Service
public class StockSupplementServiceImpl extends AbstractShopService implements StockSupplementService {

    /**
     * 入庫登録ロジック
     */
    private final StockSupplementLogic stockSupplementLogic;

    /**
     * 商品&規格の削除チェック
     */
    private final GoodsStatusCheckLogic goodsStatusCheckLogic;

    @Autowired
    public StockSupplementServiceImpl(StockSupplementLogic stockSupplementLogic,
                                      GoodsStatusCheckLogic goodsStatusCheckLogic) {
        this.stockSupplementLogic = stockSupplementLogic;
        this.goodsStatusCheckLogic = goodsStatusCheckLogic;
    }

    /**
     * 入庫登録実行<br/>
     *
     * @param stockResultEntity 入庫実績エンティティ
     * @param goodsCode         商品コード
     * @return 登録件数
     */
    @Override
    public int execute(StockResultEntity stockResultEntity, String goodsCode, String administratorName) {
        // (1)パラメータチェック
        ArgumentCheckUtil.assertNotNull("入庫情報が取得できません。", stockResultEntity);
        ArgumentCheckUtil.assertNotNull("商品コードが取得できません。", goodsCode);

        int shopSeq = 1001;

        // 商品か規格が削除されていたらエラー
        if (!goodsStatusCheckLogic.execute(shopSeq, goodsCode)) {
            throwMessage(MSGCD_STOCKSUPPLEMENT_STATUS_FAILE);
        }

        stockResultEntity.setProcessPersonName(administratorName);

        // (2)入庫登録ロジック実行→戻り値返却
        int result = stockSupplementLogic.execute(stockResultEntity, shopSeq, goodsCode);
        if (result == 0) {
            throwMessage(MSGCD_STOCKSUPPLEMENT_FAILE);
        }
        return result;
    }

}
