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
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSettlementRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 受注決済登録ロジック
 *
 * @author ueshima
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class OrderSettlementRegistLogicImpl extends AbstractShopLogic implements OrderSettlementRegistLogic {

    /**
     * 受注決済Dao
     */
    private final OrderSettlementDao orderSettlementDao;

    @Autowired
    public OrderSettlementRegistLogicImpl(OrderSettlementDao orderSettlementDao) {
        this.orderSettlementDao = orderSettlementDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderSettlementEntity 受注決済エンティティ
     * @return 登録件数
     */
    @Override
    public int execute(OrderSettlementEntity orderSettlementEntity) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSettlementEntity", orderSettlementEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 登録日時・更新日時の更新
        Timestamp currentTime = dateUtility.getCurrentTime();
        orderSettlementEntity.setRegistTime(currentTime);
        orderSettlementEntity.setUpdateTime(currentTime);

        // 受注決済登録
        return orderSettlementDao.insert(orderSettlementEntity);
    }

}
