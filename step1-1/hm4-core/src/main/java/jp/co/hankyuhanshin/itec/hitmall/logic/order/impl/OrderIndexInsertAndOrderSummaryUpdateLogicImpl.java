/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeProcessType;
import jp.co.hankyuhanshin.itec.hitmall.dao.order.OrderSummaryDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.order.index.OrderIndexDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexInsertAndOrderSummaryUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 受注サマリ更新受注インデックス登録Logic実装クラス
 *
 * @author tateishi
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class OrderIndexInsertAndOrderSummaryUpdateLogicImpl extends AbstractShopLogic
                implements OrderIndexInsertAndOrderSummaryUpdateLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderIndexInsertAndOrderSummaryUpdateLogicImpl.class);

    /**
     * 受注サマリDao
     */
    private final OrderSummaryDao orderSummaryDao;

    /**
     * 受注インデックスDao
     */
    private final OrderIndexDao orderIndexDao;

    @Autowired
    public OrderIndexInsertAndOrderSummaryUpdateLogicImpl(OrderSummaryDao orderSummaryDao,
                                                          OrderIndexDao orderIndexDao) {
        this.orderSummaryDao = orderSummaryDao;
        this.orderIndexDao = orderIndexDao;
    }

    /**
     * 受注サマリを更新し受注インデックスへ登録処理を行います
     *
     * @param orderSeq          Integer
     * @param shopSeq           Integer
     * @param processPersonName String
     * @param processType       String
     * @return boolean
     */
    @Override
    public boolean execute(Integer orderSeq, Integer shopSeq, String processPersonName, HTypeProcessType processType) {
        try {
            // 最新の受注インデックスを取得
            OrderIndexEntity orderIndexEntity = orderIndexDao.getEntityOfMaxOrderVersionNo(orderSeq);
            ArgumentCheckUtil.assertNotNull("orderIndexEntity", orderIndexEntity);

            // 受注サマリにレコードロック
            OrderSummaryEntity orderSummaryEntity = orderSummaryDao.getEntityForUpdatePrimaryKey(orderSeq);
            ArgumentCheckUtil.assertNotNull("orderSummaryEntity", orderSummaryEntity);

            // 日付関連Helper取得
            DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

            Integer orderVersionNo = orderIndexEntity.getOrderVersionNo() + 1;
            /** 受注インデックスの登録内容を設定 */
            // 受注履歴連番
            orderIndexEntity.setOrderVersionNo(orderVersionNo);
            // 処理日時
            orderIndexEntity.setProcessTime(dateUtility.getCurrentTime());
            // ショップSEQ
            orderIndexEntity.setShopSeq(shopSeq);
            // 処理種別
            orderIndexEntity.setProcessType(processType);
            // 処理担当者名
            orderIndexEntity.setProcessPersonName(processPersonName);
            // 更新日時
            orderIndexEntity.setUpdateTime(dateUtility.getCurrentTime());

            /** 受注サマリの更新内容を設定 */
            // 受注履歴連番
            orderSummaryEntity.setOrderVersionNo(orderVersionNo);
            // 更新日時
            orderSummaryEntity.setUpdateTime(dateUtility.getCurrentTime());

            // 受注インデックス登録
            orderIndexDao.insert(orderIndexEntity);

            // 受注サマリ更新
            orderSummaryDao.update(orderSummaryEntity);
        } catch (Exception e) {
            LOGGER.error("OrderIndexInsertAndOrderSummaryUpdateLogicImpl - [" + e + "] 受注履歴更新処理に失敗しました", e);
            throwMessage();
        }

        return true;
    }

}
