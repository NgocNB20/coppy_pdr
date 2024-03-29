/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.orderperson.OrderPersonDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderPersonGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 受注ご注文主取得ロジック
 *
 * @author ueshima
 * @version $Revision: 1.2 $
 */
@Component
public class OrderPersonGetLogicImpl extends AbstractShopLogic implements OrderPersonGetLogic {

    /**
     * 受注ご注文主Dao
     */
    private final OrderPersonDao orderPersonDao;

    @Autowired
    public OrderPersonGetLogicImpl(OrderPersonDao orderPersonDao) {
        this.orderPersonDao = orderPersonDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq             受注SEQ
     * @param orderPersonVersionNo 受注ご注文主連番
     * @return 受注ご注文主エンティティ
     */
    @Override
    public OrderPersonEntity execute(Integer orderSeq, Integer orderPersonVersionNo) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderPersonVersionNo", orderPersonVersionNo);

        // 受注ご注文主の検索
        return orderPersonDao.getEntity(orderSeq, orderPersonVersionNo);
    }

}
