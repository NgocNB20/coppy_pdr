/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock;

/**
 * 在庫情報出荷更新ロジック
 *
 * @author yamaguchi
 * @version $Revision: 1.3 $
 */
public interface StockShipmentUpdateLogic {

    /**
     * 通常
     */
    public static final Integer NOMAL = 0;

    /**
     * 差分
     */
    public static final Integer DIFF = 1;

    /**
     * 実行メソッド
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @param orderConsecutiveNo  注文連番
     * @param mode                更新モード 0=通常, 1=差分
     * @return 更新件数
     */
    int execute(Integer orderSeq, Integer orderGoodsVersionNo, Integer orderConsecutiveNo, Integer mode);

}
