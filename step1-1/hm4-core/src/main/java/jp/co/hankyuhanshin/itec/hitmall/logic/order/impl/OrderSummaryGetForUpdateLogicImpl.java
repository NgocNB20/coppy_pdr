/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.OrderSummaryDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryGetForUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 受注サマリー取得ロジック<br/>
 * 受注サマリーを取得する際に対象のデータレコードをロックします。<br/>
 *
 * @author yamaguchi
 * @version $Revision: 1.4 $
 */
@Component
public class OrderSummaryGetForUpdateLogicImpl extends AbstractShopLogic implements OrderSummaryGetForUpdateLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSummaryGetForUpdateLogicImpl.class);

    /**
     * OrderSummaryDao
     */
    private final OrderSummaryDao orderSummaryDao;

    @Autowired
    public OrderSummaryGetForUpdateLogicImpl(OrderSummaryDao orderSummaryDao) {
        this.orderSummaryDao = orderSummaryDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param orderCode      受注番号
     * @param orderVersionNo 受注履歴番号
     * @param shopSeq        ショップSEQ
     * @return 受注サマリ
     */
    @Override
    public OrderSummaryEntity execute(String orderCode, Integer orderVersionNo, Integer shopSeq) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("orderCode", orderCode);

        OrderSummaryEntity orderSummaryEntity = null;
        try {
            orderSummaryEntity = orderSummaryDao.getEntityForUpdateCode(orderCode, orderVersionNo, shopSeq);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            this.throwMessage(MSGCD_SELECT_FORUPDATE__FATAL, new Object[] {orderCode});
        }
        // 受注サマリ取得
        return orderSummaryEntity;
    }

}
