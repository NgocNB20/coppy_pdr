/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.OrderSummaryDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.summary.OrderSummaryForHistoryDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryForHistoryDtoGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 受注サマリDto取得Logic実装クラス<br/>
 *
 * @author s_tsuru
 */
@Component
public class OrderSummaryForHistoryDtoGetLogicImpl extends AbstractShopLogic
                implements OrderSummaryForHistoryDtoGetLogic {

    /**
     * 受注サマリDao<br/>
     */
    private final OrderSummaryDao orderSummaryDao;

    @Autowired
    public OrderSummaryForHistoryDtoGetLogicImpl(OrderSummaryDao orderSummaryDao) {
        this.orderSummaryDao = orderSummaryDao;
    }

    /**
     * 受注サマリ情報を取得する<br/>
     *
     * @param orderCode 受注番号
     * @return 受注サマリエンティティ
     */
    @Override
    public OrderSummaryForHistoryDto execute(String orderCode) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("orderCode", orderCode);

        // 受注サマリ取得
        return orderSummaryDao.getHistoryDtoByCode(orderCode);
    }
}
