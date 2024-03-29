// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.OrderSummaryDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.NewOrderNoSeqGetLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * #013 09_データ連携（受注データ）<br/>
 *
 * <pre>
 * 受注番号SEQ取得
 * </pre>
 *
 * @author satoh
 */
@Component
public class NewOrderNoSeqGetLogicImpl implements NewOrderNoSeqGetLogic {
    /** 受注インデックスDao */
    private final OrderSummaryDao orderSummaryDao;

    @Autowired
    public NewOrderNoSeqGetLogicImpl(OrderSummaryDao orderSummaryDao) {
        this.orderSummaryDao = orderSummaryDao;
    }

    /**
     * 実行メソッド
     *
     * @return 受注番号SEQ
     */
    @Override
    public Integer execute() {
        return orderSummaryDao.getOrderNoSeqNextVal();
    }

}
// PDR Migrate Customization to here
