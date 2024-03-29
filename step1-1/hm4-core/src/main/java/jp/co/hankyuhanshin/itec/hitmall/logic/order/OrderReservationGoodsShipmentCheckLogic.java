/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.ShipmentRegistDto;

/**
 * 受注予約商品出荷チェックロジック
 *
 * @author s_nose
 */
public interface OrderReservationGoodsShipmentCheckLogic {

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq          受注SEQ
     * @param shipmentRegistDto 出荷登録DTO
     * @return 処理結果 true..出荷可能 false..出荷不可
     */
    boolean execute(Integer orderSeq, ShipmentRegistDto shipmentRegistDto);
}
