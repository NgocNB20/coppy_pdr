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
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReceiptOfMoneyRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 受注入金登録ロジック
 *
 * @author ueshima
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class OrderReceiptOfMoneyRegistLogicImpl extends AbstractShopLogic implements OrderReceiptOfMoneyRegistLogic {

    /**
     * 受注入金Dao
     */
    private final OrderReceiptOfMoneyDao orderReceiptOfMoneyDao;

    @Autowired
    public OrderReceiptOfMoneyRegistLogicImpl(OrderReceiptOfMoneyDao orderReceiptOfMoneyDao) {
        this.orderReceiptOfMoneyDao = orderReceiptOfMoneyDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderReceiptOfMoneyEntity 受注入金エンティティ
     * @return 登録件数
     */
    @Override
    public int execute(OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderReceiptOfMoneyEntity", orderReceiptOfMoneyEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 登録日時・更新日時の更新
        Timestamp currentTime = dateUtility.getCurrentTime();
        orderReceiptOfMoneyEntity.setRegistTime(currentTime);
        orderReceiptOfMoneyEntity.setUpdateTime(currentTime);

        // 受注入金登録
        return orderReceiptOfMoneyDao.insert(orderReceiptOfMoneyEntity);
    }

}
