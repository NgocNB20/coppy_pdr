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
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderPersonRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 受注ご注文主登録ロジック
 *
 * @author ueshima
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class OrderPersonRegistLogicImpl extends AbstractShopLogic implements OrderPersonRegistLogic {

    /**
     * 受注ご注文主Dao
     */
    private final OrderPersonDao orderPersonDao;

    @Autowired
    public OrderPersonRegistLogicImpl(OrderPersonDao orderPersonDao) {
        this.orderPersonDao = orderPersonDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderPersonEntity 受注ご注文主エンティティ
     * @return 登録件数
     */
    @Override
    public int execute(OrderPersonEntity orderPersonEntity) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderPersonEntity", orderPersonEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 登録日時・更新日時の更新
        Timestamp currentTime = dateUtility.getCurrentTime();
        orderPersonEntity.setRegistTime(currentTime);
        orderPersonEntity.setUpdateTime(currentTime);

        // 受注ご注文主登録
        return orderPersonDao.insert(orderPersonEntity);
    }

}
