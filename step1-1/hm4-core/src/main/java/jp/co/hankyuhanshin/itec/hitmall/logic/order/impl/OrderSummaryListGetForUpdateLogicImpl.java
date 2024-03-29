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
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryListGetForUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 受注サマリー排他取得<br/>
 *
 * @author hirata
 * @version $Revision: 1.3 $
 */
@Component
public class OrderSummaryListGetForUpdateLogicImpl extends AbstractShopLogic
                implements OrderSummaryListGetForUpdateLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSummaryListGetForUpdateLogicImpl.class);

    /**
     * 受注サマリーDao
     */
    private final OrderSummaryDao orderSummaryDao;

    @Autowired
    public OrderSummaryListGetForUpdateLogicImpl(OrderSummaryDao orderSummaryDao) {
        this.orderSummaryDao = orderSummaryDao;
    }

    /**
     * 受注サマリー排他取得(受注SEQリスト)<br/>
     *
     * @param orderSeqList 受注SEQリスト
     * @return 受注サマリーエンティティリスト
     */
    @Override
    public List<OrderSummaryEntity> execute(List<Integer> orderSeqList) {

        ArgumentCheckUtil.assertNotEmpty("orderSeqList", orderSeqList);

        List<OrderSummaryEntity> list = new ArrayList<>();
        try {
            list = orderSummaryDao.getEntityListForUpdateSeq(orderSeqList);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            this.throwMessage(MSGCD_SELECT_FORUPDATE__FATAL);
        }

        return list;
    }

    /**
     * 受注サマリー排他取得(受注コードリスト, ショップSEQ)<br/>
     *
     * @param orderCodeList 受注コードリスト
     * @param shopSeq       ショップSEQ
     * @return 受注サマリーエンティティリスト
     */
    @Override
    public List<OrderSummaryEntity> execute(List<String> orderCodeList, Integer shopSeq) {

        ArgumentCheckUtil.assertNotEmpty("orderCodeList", orderCodeList);
        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);
        List<OrderSummaryEntity> list = new ArrayList<>();
        try {
            list = orderSummaryDao.getEntityListForUpdateCode(orderCodeList, shopSeq);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            this.throwMessage(MSGCD_SELECT_FORUPDATE__FATAL);
        }
        return list;
    }

}
