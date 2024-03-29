/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.settlement.OrderSettlementDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSettlementGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 受注決済取得ロジック
 *
 * @author ueshima
 * @version $Revision: 1.2 $
 */
@Component
public class OrderSettlementGetLogicImpl extends AbstractShopLogic implements OrderSettlementGetLogic {

    /**
     * 受注決済Dao
     */
    private final OrderSettlementDao orderSettlementDao;

    @Autowired
    public OrderSettlementGetLogicImpl(OrderSettlementDao orderSettlementDao) {
        this.orderSettlementDao = orderSettlementDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq                 受注SEQ
     * @param orderSettlementVersionNo 受注決済連番
     * @return 受注決済エンティティ
     */
    @Override
    public OrderSettlementEntity execute(Integer orderSeq, Integer orderSettlementVersionNo) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderSettlementVersionNo", orderSettlementVersionNo);

        // 受注決済の検索
        return orderSettlementDao.getEntity(orderSeq, orderSettlementVersionNo);
    }

}
