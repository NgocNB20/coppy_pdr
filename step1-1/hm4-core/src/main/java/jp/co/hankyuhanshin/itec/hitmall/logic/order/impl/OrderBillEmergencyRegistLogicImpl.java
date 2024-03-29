/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeProcessType;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderBillEmergencyRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderBillGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderBillRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 受注請求決済エラー登録ロジック
 *
 * @author yamazaki
 */
@Component
public class OrderBillEmergencyRegistLogicImpl extends AbstractShopLogic implements OrderBillEmergencyRegistLogic {

    /**
     * DateUtility
     */
    private final DateUtility dateUtility;

    /**
     * 受注インデックス取得ロジック
     */
    private final OrderIndexGetLogic orderIndexGetLogic;

    /**
     * 受注請求取得ロジック
     */
    private final OrderBillGetLogic orderBillGetLogic;

    /**
     * 受注請求登録ロジック
     */
    private final OrderBillRegistLogic orderBillRegistLogic;

    /**
     * 受注インデックス登録ロジック
     */
    private final OrderIndexRegistLogic orderIndexRegistLogic;

    /**
     * 受注サマリー更新ロジック
     */
    private final OrderSummaryUpdateLogic orderSummaryUpdateLogic;

    @Autowired
    public OrderBillEmergencyRegistLogicImpl(DateUtility dateUtility,
                                             OrderIndexGetLogic orderIndexGetLogic,
                                             OrderBillGetLogic orderBillGetLogic,
                                             OrderBillRegistLogic orderBillRegistLogic,
                                             OrderIndexRegistLogic orderIndexRegistLogic,
                                             OrderSummaryUpdateLogic orderSummaryUpdateLogic) {

        this.dateUtility = dateUtility;
        this.orderIndexGetLogic = orderIndexGetLogic;
        this.orderBillGetLogic = orderBillGetLogic;
        this.orderBillRegistLogic = orderBillRegistLogic;
        this.orderIndexRegistLogic = orderIndexRegistLogic;
        this.orderSummaryUpdateLogic = orderSummaryUpdateLogic;
    }

    @Override
    public void execute(OrderSummaryEntity orderSummaryEntity, String administratorName) {

        // 受注インデックスを取得
        OrderIndexEntity orderIndexEntity = orderIndexGetLogic.execute(orderSummaryEntity.getOrderSeq(),
                                                                       orderSummaryEntity.getOrderVersionNo()
                                                                      );
        // 受注請求を取得
        OrderBillEntity orderBillEntity = orderBillGetLogic.execute(orderIndexEntity.getOrderSeq(),
                                                                    orderIndexEntity.getOrderBillVersionNo()
                                                                   );

        // 受注請求登録内容を作成
        // 異常フラグに請求決済エラーを設定
        orderBillEntity.setEmergencyFlag(HTypeEmergencyFlag.ON);
        orderBillEntity.setOrderBillVersionNo(orderBillEntity.getOrderBillVersionNo() + 1);
        // 受注請求を登録
        orderBillRegistLogic.execute(orderBillEntity);

        // 受注インデックス登録内容を作成
        // 受注入金連番
        orderIndexEntity.setOrderBillVersionNo(orderBillEntity.getOrderBillVersionNo());
        // 受注連番
        orderIndexEntity.setOrderVersionNo(orderIndexEntity.getOrderVersionNo() + 1);
        // 処理担当者名
        orderIndexEntity.setProcessPersonName(administratorName);
        // 処理時間
        orderIndexEntity.setProcessTime(dateUtility.getCurrentTime());
        // 処理区分
        orderIndexEntity.setProcessType(HTypeProcessType.CHANGE);
        // 受注インデックスを登録
        orderIndexRegistLogic.execute(orderIndexEntity);

        // 受注サマリー更新内容作成
        // 受注連番
        orderSummaryEntity.setOrderVersionNo(orderIndexEntity.getOrderVersionNo());
        // 受注サマリーを更新
        orderSummaryUpdateLogic.execute(orderSummaryEntity);

    }

}
