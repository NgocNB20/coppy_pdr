/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.index.OrderIndexDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.NewOrderSeqGetLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 新規受注SEQ取得ロジック実装クラス
 *
 * @author negishi
 * @version $Revision: 1.2 $
 */
@Component
public class NewOrderSeqGetLogicImpl extends AbstractShopLogic implements NewOrderSeqGetLogic {

    /**
     * 受注インデックスDao
     */
    private final OrderIndexDao orderIndexDao;

    @Autowired
    public NewOrderSeqGetLogicImpl(OrderIndexDao orderIndexDao) {
        this.orderIndexDao = orderIndexDao;
    }

    /**
     * 実行メソッド
     *
     * @return 受注SEQ
     */
    @Override
    public Integer execute() {
        return orderIndexDao.getOrderSeqNextVal();
    }

}
