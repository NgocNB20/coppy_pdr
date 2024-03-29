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
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 受注サマリ情報取得ロジック<br/>
 *
 * @author natume
 * @version $Revision: 1.3 $
 */
@Component
public class OrderSummaryGetLogicImpl extends AbstractShopLogic implements OrderSummaryGetLogic {

    /**
     * 受注サマリDao<br/>
     */
    private final OrderSummaryDao orderSummaryDao;

    @Autowired
    public OrderSummaryGetLogicImpl(OrderSummaryDao orderSummaryDao) {
        this.orderSummaryDao = orderSummaryDao;
    }

    /**
     * 受注サマリ情報を取得する<br/>
     *
     * @param orderSeq 受注SEQ
     * @return 受注サマリエンティティ
     */
    @Override
    public OrderSummaryEntity execute(Integer orderSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertGreaterThanZero("orderSeq", orderSeq);

        // 受注サマリ取得
        return orderSummaryDao.getEntity(orderSeq);
    }

    /**
     * 受注サマリ情報を取得する<br/>
     *
     * @param orderCode 受注番号
     * @return 受注サマリエンティティ
     */
    @Override
    public OrderSummaryEntity execute(String orderCode) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("orderCode", orderCode);

        // 受注サマリ取得
        return orderSummaryDao.getEntityByOrderCode(orderCode);
    }

    /**
     * 指定のお申込み番号、ご注文主電話番号に紐付く受注サマリを取得する<br>
     * （取得できない場合でもエラーは出しません）<br>
     *
     * @param orderCode お申込み番号
     * @param orderTel  ご注文主電話番号
     * @return 受注サマリEntity
     */
    @Override
    public OrderSummaryEntity execute(String orderCode, String orderTel) {
        // 共通情報の取得
        Integer shopSeq = 1001;

        OrderSummaryEntity orderSummary = orderSummaryDao.getEntityForGestOrderLogin(orderCode, orderTel, shopSeq);

        return orderSummary;
    }

}
