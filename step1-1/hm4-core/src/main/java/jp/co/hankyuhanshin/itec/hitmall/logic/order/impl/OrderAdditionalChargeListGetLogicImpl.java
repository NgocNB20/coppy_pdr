/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.additional.OrderAdditionalChargeDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.additional.OrderAdditionalChargeEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderAdditionalChargeListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 受注追加料金リスト取得ロジック
 *
 * @author ueshima
 * @version $Revision: 1.2 $
 */
@Component
public class OrderAdditionalChargeListGetLogicImpl extends AbstractShopLogic
                implements OrderAdditionalChargeListGetLogic {

    /**
     * 受注追加料金Dao
     */
    private final OrderAdditionalChargeDao orderAdditionalChargeDao;

    @Autowired
    public OrderAdditionalChargeListGetLogicImpl(OrderAdditionalChargeDao orderAdditionalChargeDao) {
        this.orderAdditionalChargeDao = orderAdditionalChargeDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq                       受注SEQ
     * @param orderAdditionalChargeVersionNo 受注追加料金連番
     * @return 受注追加料金エンティティリスト
     */
    @Override
    public List<OrderAdditionalChargeEntity> execute(Integer orderSeq, Integer orderAdditionalChargeVersionNo) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderAdditionalChargeVersionNo", orderAdditionalChargeVersionNo);

        // 受注決済の検索
        return orderAdditionalChargeDao.getOrderAdditionalChargeList(orderSeq, orderAdditionalChargeVersionNo);
    }

}
