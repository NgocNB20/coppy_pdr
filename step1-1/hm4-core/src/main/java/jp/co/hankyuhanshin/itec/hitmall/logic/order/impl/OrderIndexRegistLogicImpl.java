/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.index.OrderIndexDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 受注インデックス登録ロジック
 *
 * @author ueshima
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class OrderIndexRegistLogicImpl extends AbstractShopLogic implements OrderIndexRegistLogic {

    /**
     * 受注インデックスDao
     */
    private final OrderIndexDao orderIndexDao;

    @Autowired
    public OrderIndexRegistLogicImpl(OrderIndexDao orderIndexDao) {
        this.orderIndexDao = orderIndexDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderIndexEntity 受注インデックスエンティティ
     * @return 登録件数
     */
    @Override
    public int execute(OrderIndexEntity orderIndexEntity) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderIndexEntity", orderIndexEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 登録日時・更新日時の更新
        Timestamp currentTime = dateUtility.getCurrentTime();
        orderIndexEntity.setRegistTime(currentTime);
        orderIndexEntity.setUpdateTime(currentTime);

        // 受注インデックス登録
        return orderIndexDao.insert(orderIndexEntity);
    }

}
