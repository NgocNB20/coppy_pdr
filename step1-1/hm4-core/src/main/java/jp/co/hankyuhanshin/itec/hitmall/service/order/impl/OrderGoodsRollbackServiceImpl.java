/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipmentStatus;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockOrderReserveUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsListDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderGoodsRollbackService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 注文確保在庫をロールバックするサービス
 * OrderGoodsReserveServiceと対になることを想定
 *
 * @author USER
 * @version $Revision: 1.1 $
 */
@Service
public class OrderGoodsRollbackServiceImpl extends AbstractShopService implements OrderGoodsRollbackService {

    /**
     * 在庫確保ロジック
     */
    private final StockOrderReserveUpdateLogic stockOrderReserveUpdateLogic;

    /**
     * 受注商品削除ロジック
     */
    private final OrderGoodsListDeleteLogic orderGoodsListDeleteLogic;

    @Autowired
    public OrderGoodsRollbackServiceImpl(StockOrderReserveUpdateLogic stockOrderReserveUpdateLogic,
                                         OrderGoodsListDeleteLogic orderGoodsListDeleteLogic) {

        this.stockOrderReserveUpdateLogic = stockOrderReserveUpdateLogic;
        this.orderGoodsListDeleteLogic = orderGoodsListDeleteLogic;
    }

    /**
     * 実行メソッド
     *
     * @param orderSeq               受注SEQ
     * @param newOrderGoodsVersionNo 新しい受注商品連番
     * @param orderConsecutiveNo     注文連番
     * @param shipmentStatus         出荷状態
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void execute(Integer orderSeq,
                        Integer newOrderGoodsVersionNo,
                        Integer orderConsecutiveNo,
                        HTypeShipmentStatus shipmentStatus) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);

        if (newOrderGoodsVersionNo != null) {

            // #2233 START
            // 出荷状態を元に、在庫確保モードを設定
            int stockOrderReserveUpdateMode = StockOrderReserveUpdateLogic.NOT_RESERVE;
            if (HTypeShipmentStatus.SHIPPED.equals(shipmentStatus)) {
                stockOrderReserveUpdateMode = StockOrderReserveUpdateLogic.NOT_RESERVE_SHIPPED;
            }

            // 差分確保分在庫戻し
            stockOrderReserveUpdateLogic.execute(
                            orderSeq, newOrderGoodsVersionNo, orderConsecutiveNo, stockOrderReserveUpdateMode);

            // 受注商品削除
            orderGoodsListDeleteLogic.execute(orderSeq, newOrderGoodsVersionNo, orderConsecutiveNo);
        }
    }
}
