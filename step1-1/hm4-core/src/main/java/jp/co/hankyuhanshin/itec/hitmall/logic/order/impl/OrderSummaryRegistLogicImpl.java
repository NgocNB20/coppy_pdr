/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.OrderSummaryDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 受注サマリ登録ロジック実装クラス
 *
 * @author negishi
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class OrderSummaryRegistLogicImpl implements OrderSummaryRegistLogic {

    /**
     * 受注サマリDao
     */
    private final OrderSummaryDao orderSummaryDao;

    @Autowired
    public OrderSummaryRegistLogicImpl(OrderSummaryDao orderSummaryDao) {
        this.orderSummaryDao = orderSummaryDao;
    }

    /**
     * ロジック実行
     *
     * @param orderSummaryEntity 受注サマリエンティティ
     * @return 登録件数
     */
    @Override
    public int execute(OrderSummaryEntity orderSummaryEntity) {

        // 引数チェック
        checkParameter(orderSummaryEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 現在日時を、更新日時と登録日時に設定
        Timestamp currentTimestamp = dateUtility.getCurrentTime();
        orderSummaryEntity.setUpdateTime(currentTimestamp);
        orderSummaryEntity.setRegistTime(currentTimestamp);

        // 登録処理実行
        return orderSummaryDao.insert(orderSummaryEntity);
    }

    /**
     * 引数チェック
     *
     * @param orderSummaryEntity 受注サマリエンティティ
     */
    protected void checkParameter(OrderSummaryEntity orderSummaryEntity) {
        ArgumentCheckUtil.assertNotNull("orderSummaryEntity", orderSummaryEntity);
    }

}
