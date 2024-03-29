/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipmentStatus;

/**
 * 注文確保在庫をロールバックするサービス
 * OrderGoodsReserveServiceと対になることを想定
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.1 $
 */
public interface OrderGoodsRollbackService {

    /**
     * 実行メソッド
     *
     * @param orderSeq               受注SEQ
     * @param newOrderGoodsVersionNo 変更後の受注商品連番
     * @param orderConsecutiveNo     注文連番
     * @param shipmentStatus         出荷状態
     */
    void execute(Integer orderSeq,
                 Integer newOrderGoodsVersionNo,
                 Integer orderConsecutiveNo,
                 HTypeShipmentStatus shipmentStatus);
}
