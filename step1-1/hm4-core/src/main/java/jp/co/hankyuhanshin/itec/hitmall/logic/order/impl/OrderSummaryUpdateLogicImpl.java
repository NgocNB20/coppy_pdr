/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.OrderSummaryDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 受注サマリー更新ロジック(実装)<br/>
 *
 * @author YAMAGUCHI
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class OrderSummaryUpdateLogicImpl extends AbstractShopLogic implements OrderSummaryUpdateLogic {

    /**
     * OrderSummaryDao
     */
    private final OrderSummaryDao orderSummaryDao;

    @Autowired
    public OrderSummaryUpdateLogicImpl(OrderSummaryDao orderSummaryDao) {
        this.orderSummaryDao = orderSummaryDao;
    }

    /**
     * 受注サマリを更新<br/>
     *
     * @param orderSummaryEntity 受注サマリ
     * @return 処理件数
     */
    @Override
    public int execute(OrderSummaryEntity orderSummaryEntity) {

        ArgumentCheckUtil.assertNotNull("orderSummaryEntity", orderSummaryEntity);
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSummaryEntity.getOrderSeq());

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        orderSummaryEntity.setUpdateTime(dateUtility.getCurrentTime());

        return orderSummaryDao.update(orderSummaryEntity);
    }

}
