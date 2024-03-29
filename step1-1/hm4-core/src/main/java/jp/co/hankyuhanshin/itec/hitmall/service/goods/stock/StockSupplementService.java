/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.stock;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockResultEntity;

/**
 * 入庫登録<br/>
 * 入庫情報を登録する。<br/>
 *
 * @author MN7017
 * @version $Revision: 1.7 $
 */
public interface StockSupplementService {

    /**
     * エラーコード：入庫登録失敗
     */
    String MSGCD_STOCKSUPPLEMENT_FAILE = "SGS000101";

    /**
     * エラーコード：商品か規格が削除されている
     */
    String MSGCD_STOCKSUPPLEMENT_STATUS_FAILE = "SGS000102";

    /**
     * 入庫登録<br/>
     * 入庫情報を登録する。<br/>
     *
     * @param stockResultEntity 入庫実績エンティティ
     * @param goodsCode         商品コード
     * @return 登録件数
     */
    int execute(StockResultEntity stockResultEntity, String goodsCode, String administratorName);

}
