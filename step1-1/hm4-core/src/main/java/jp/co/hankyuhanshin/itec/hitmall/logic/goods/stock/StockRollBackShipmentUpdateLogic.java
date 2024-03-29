/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock;

/**
 * 出荷済み在庫戻しロジック
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.2 $
 */
public interface StockRollBackShipmentUpdateLogic {

    /**
     * 実行メソッド
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @param orderConsecutiveNo  注文連番
     * @return 更新件数
     */
    int execute(Integer orderSeq, Integer orderGoodsVersionNo, Integer orderConsecutiveNo);

}
