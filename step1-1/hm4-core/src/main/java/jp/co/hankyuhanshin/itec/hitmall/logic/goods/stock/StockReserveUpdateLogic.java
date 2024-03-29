/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock;

/**
 * 在庫情報注文確保更新ロジック
 *
 * @author negishi
 * @version $Revision: 1.2 $
 */
public interface StockReserveUpdateLogic {

    /**
     * 実行メソッド
     *
     * @param orderSeq       受注SEQ
     * @param orderVersionNo 受注履歴連番
     * @return 更新件数
     */
    int execute(Integer orderSeq, Integer orderVersionNo);

}
