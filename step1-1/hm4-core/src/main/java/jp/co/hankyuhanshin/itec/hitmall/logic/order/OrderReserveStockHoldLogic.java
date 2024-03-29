/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

import java.util.List;

/**
 * 在庫確保ロジック
 *
 * @author yt23807
 */
public interface OrderReserveStockHoldLogic {

    /**
     * 実行メソッド
     *
     * @param receiveOrderDto 受注Dto
     */
    void execute(ReceiveOrderDto receiveOrderDto);

    /**
     * 受注商品登録<br/>
     *
     * @param receiveOrderDto 受注DTO
     * @return 受注商品SEQリスト
     */
    List<Integer> registOrderGoods(ReceiveOrderDto receiveOrderDto);
}
