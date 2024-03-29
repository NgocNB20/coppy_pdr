/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.goods.OrderGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 注文商品登録ロジック実装クラス
 *
 * @author negishi
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class OrderGoodsRegistLogicImpl extends AbstractShopLogic implements OrderGoodsRegistLogic {

    /**
     * 受注商品Dao
     */
    private final OrderGoodsDao orderGoodsDao;

    @Autowired
    public OrderGoodsRegistLogicImpl(OrderGoodsDao orderGoodsDao) {
        this.orderGoodsDao = orderGoodsDao;
    }

    /**
     * 実行メソッド
     *
     * @param orderGoodsEntity 注文商品エンティティ
     * @return 登録件数
     */
    @Override
    public int execute(OrderGoodsEntity orderGoodsEntity) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderGoodsEntity", orderGoodsEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 登録日時・更新日時の更新
        Timestamp currentTime = dateUtility.getCurrentTime();
        orderGoodsEntity.setRegistTime(currentTime);
        orderGoodsEntity.setUpdateTime(currentTime);

        // 受注商品登録
        return orderGoodsDao.insert(orderGoodsEntity);
    }

}
