/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.bill.OrderBillDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderBillGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 受注請求取得ロジック
 *
 * @author ueshima
 * @version $Revision: 1.2 $
 */
@Component
public class OrderBillGetLogicImpl extends AbstractShopLogic implements OrderBillGetLogic {

    /**
     * 受注請求Dao
     */
    private final OrderBillDao orderBillDao;

    @Autowired
    public OrderBillGetLogicImpl(OrderBillDao orderBillDao) {
        this.orderBillDao = orderBillDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq           受注SEQ
     * @param orderBillVersionNo 受注請求連番
     * @return 受注請求エンティティ
     */
    @Override
    public OrderBillEntity execute(Integer orderSeq, Integer orderBillVersionNo) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderBillVersionNo", orderBillVersionNo);

        // 受注請求の検索
        return orderBillDao.getEntityWithCardbrand(orderSeq, orderBillVersionNo);
    }

}
