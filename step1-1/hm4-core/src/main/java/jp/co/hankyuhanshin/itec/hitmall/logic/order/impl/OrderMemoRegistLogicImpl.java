/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.memo.OrderMemoDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.memo.OrderMemoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderMemoRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 受注メモ登録処理<br/>
 *
 * @author YAMAGUCHI
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class OrderMemoRegistLogicImpl extends AbstractShopLogic implements OrderMemoRegistLogic {

    /**
     * OrderMemoDao
     */
    private final OrderMemoDao orderMemoDao;

    @Autowired
    public OrderMemoRegistLogicImpl(OrderMemoDao orderMemoDao) {
        this.orderMemoDao = orderMemoDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param orderMemoEntity 受注メモ
     * @return 処理件数
     */
    @Override
    public int execute(OrderMemoEntity orderMemoEntity) {

        ArgumentCheckUtil.assertNotNull("orderMemoEntity", orderMemoEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        Timestamp processTime = dateUtility.getCurrentTime();
        orderMemoEntity.setRegistTime(processTime);
        orderMemoEntity.setUpdateTime(processTime);

        return orderMemoDao.insert(orderMemoEntity);
    }

}
