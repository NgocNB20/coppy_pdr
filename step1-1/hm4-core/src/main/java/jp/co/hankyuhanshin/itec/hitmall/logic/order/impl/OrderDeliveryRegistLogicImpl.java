/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.delivery.OrderDeliveryDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderDeliveryRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 受注配送登録ロジック
 *
 * @author ueshima
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class OrderDeliveryRegistLogicImpl extends AbstractShopLogic implements OrderDeliveryRegistLogic {

    /**
     * 受注配送Dao
     */
    private final OrderDeliveryDao orderDeliveryDao;

    @Autowired
    public OrderDeliveryRegistLogicImpl(OrderDeliveryDao orderDeliveryDao) {
        this.orderDeliveryDao = orderDeliveryDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderDeliveryEntity 受注配送エンティティ
     * @return 登録件数
     */
    @Override
    public int execute(OrderDeliveryEntity orderDeliveryEntity) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderDeliveryEntity", orderDeliveryEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 登録日時・更新日時の更新
        Timestamp currentTime = dateUtility.getCurrentTime();
        orderDeliveryEntity.setRegistTime(currentTime);
        orderDeliveryEntity.setUpdateTime(currentTime);

        // 受注配送登録
        return orderDeliveryDao.insert(orderDeliveryEntity);
    }

}
