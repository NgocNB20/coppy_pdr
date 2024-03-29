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
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderAdditionalChargeRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 受注追加料金登録ロジック<br/>
 *
 * @author YAMAGUCHI
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class OrderAdditionalChargeRegistLogicImpl extends AbstractShopLogic
                implements OrderAdditionalChargeRegistLogic {

    /**
     * OrderAdditionalChargeDao
     */
    private final OrderAdditionalChargeDao orderAdditionalChargeDao;

    @Autowired
    public OrderAdditionalChargeRegistLogicImpl(OrderAdditionalChargeDao orderAdditionalChargeDao) {
        this.orderAdditionalChargeDao = orderAdditionalChargeDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param orderAdditionalChargeEntity 受注追加料金
     * @return 処理件数
     */
    @Override
    public int execute(OrderAdditionalChargeEntity orderAdditionalChargeEntity) {

        ArgumentCheckUtil.assertNotNull("orderAdditionalChargeEntity", orderAdditionalChargeEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        Timestamp processTime = dateUtility.getCurrentTime();
        orderAdditionalChargeEntity.setRegistTime(processTime);
        orderAdditionalChargeEntity.setUpdateTime(processTime);

        return orderAdditionalChargeDao.insert(orderAdditionalChargeEntity);
    }

}
