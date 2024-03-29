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
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderBillRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 受注請求登録ロジック
 *
 * @author ueshima
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class OrderBillRegistLogicImpl extends AbstractShopLogic implements OrderBillRegistLogic {

    /**
     * 受注請求Dao
     */
    private final OrderBillDao orderBillDao;

    @Autowired
    public OrderBillRegistLogicImpl(OrderBillDao orderBillDao) {
        this.orderBillDao = orderBillDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderBillEntity 受注請求エンティティ
     * @return 登録件数
     */
    @Override
    public int execute(OrderBillEntity orderBillEntity) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderBillEntity", orderBillEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 登録日時・更新日時の更新
        Timestamp currentTime = dateUtility.getCurrentTime();
        orderBillEntity.setRegistTime(currentTime);
        orderBillEntity.setUpdateTime(currentTime);

        // 受注請求登録
        return orderBillDao.insert(orderBillEntity);
    }

}
