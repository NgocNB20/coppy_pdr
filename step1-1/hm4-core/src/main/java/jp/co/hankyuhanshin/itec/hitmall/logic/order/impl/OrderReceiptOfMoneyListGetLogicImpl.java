/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.bill.OrderReceiptOfMoneyDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderReceiptOfMoneyEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReceiptOfMoneyListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 受注入金リスト取得ロジック
 *
 * @author ueshima
 * @version $Revision: 1.2 $
 */
@Component
public class OrderReceiptOfMoneyListGetLogicImpl extends AbstractShopLogic implements OrderReceiptOfMoneyListGetLogic {

    /**
     * 受注入金Dao
     */
    private final OrderReceiptOfMoneyDao orderReceiptOfMoneyDao;

    @Autowired
    public OrderReceiptOfMoneyListGetLogicImpl(OrderReceiptOfMoneyDao orderReceiptOfMoneyDao) {
        this.orderReceiptOfMoneyDao = orderReceiptOfMoneyDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq                     受注SEQ
     * @param orderReceiptOfMoneyVersionNo 受注入金連番
     * @return 受注入金エンティティリスト
     */
    @Override
    public List<OrderReceiptOfMoneyEntity> execute(Integer orderSeq, Integer orderReceiptOfMoneyVersionNo) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderReceiptOfMoneyVersionNo", orderReceiptOfMoneyVersionNo);

        // 受注入金の検索
        return orderReceiptOfMoneyDao.getOrderReceiptOfMoneyList(orderSeq, orderReceiptOfMoneyVersionNo);
    }

}
