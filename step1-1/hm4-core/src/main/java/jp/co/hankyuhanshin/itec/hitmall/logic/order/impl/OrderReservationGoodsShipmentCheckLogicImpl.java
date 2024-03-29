/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.goods.OrderGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ShipmentRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReservationGoodsShipmentCheckLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 受注予約商品出荷チェックロジック<br/>
 *
 * @author s_nose
 */
@Component
public class OrderReservationGoodsShipmentCheckLogicImpl extends AbstractShopLogic
                implements OrderReservationGoodsShipmentCheckLogic {

    /**
     * 受注商品Dao
     */
    private final OrderGoodsDao orderGoodsDao;

    @Autowired
    public OrderReservationGoodsShipmentCheckLogicImpl(OrderGoodsDao orderGoodsDao) {
        this.orderGoodsDao = orderGoodsDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq          受注SEQ
     * @param shipmentRegistDto 出荷登録DTO
     * @return 処理結果 true..出荷可能 false..出荷不可
     */
    @Override
    public boolean execute(Integer orderSeq, ShipmentRegistDto shipmentRegistDto) {
        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("shipmentRegistDto", shipmentRegistDto);

        // 出荷対象に紐付く受注予約商品の最遅の販売開始日を取得
        Timestamp latestSaleStartTime = orderGoodsDao.getOrderReservationGoodsLatestSaleStartTime(orderSeq,
                                                                                                  shipmentRegistDto.getOrderConsecutiveNo()
                                                                                                 );

        // 出荷可能チェック
        // 受注予約商品が無い、または出荷日が受注予約商品の最遅の販売開始後であれば、出荷可能
        if (latestSaleStartTime == null || shipmentRegistDto.getShipmentDate().compareTo(latestSaleStartTime) > 0) {
            return true;
        }
        return false;
    }

}
