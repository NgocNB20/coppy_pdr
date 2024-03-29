/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDetailsDto;

/**
 * 在庫詳細情報取得ロジック<br/>
 *
 * @author MN7017
 * @version $Revision: 1.1 $
 */
public interface StockDetailsGetByGoodsSeqLogic {

    /**
     * 在庫詳細情報取得ロジック実行<br/>
     *
     * @param goodsSeq 商品SEQ
     * @return 在庫詳細Dto
     */
    StockDetailsDto execute(Integer goodsSeq);

}
