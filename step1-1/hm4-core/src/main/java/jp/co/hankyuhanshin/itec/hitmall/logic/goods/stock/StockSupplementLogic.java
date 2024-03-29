/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockResultEntity;

/**
 * 入庫登録ロジック<br/>
 *
 * @author MN7017
 * @version $Revision: 1.3 $
 */
public interface StockSupplementLogic {

    /**
     * 実行メソッド<br/>
     *
     * @param stockResultEntity 入庫実績エンティティ
     * @param shopSeq           ショップSEQ
     * @param goodsCode         商品コード
     * @return 登録件数
     */
    int execute(StockResultEntity stockResultEntity, int shopSeq, String goodsCode);

}
