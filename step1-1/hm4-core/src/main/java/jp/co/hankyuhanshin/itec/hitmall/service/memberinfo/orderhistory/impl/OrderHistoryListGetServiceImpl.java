/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.orderhistory.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.orderhistory.OrderHistoryListDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSummarySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.orderhistory.OrderHistoryListGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 注文履歴情報リスト取得サービス<br/>
 *
 * @author ueshima
 * @author Nishigaki (Itec) 2011/12/22 チケット #2702 対応
 */
@Service
public class OrderHistoryListGetServiceImpl extends AbstractShopService implements OrderHistoryListGetService {

    /**
     * 受注サマリリスト取得ロジック
     */
    private final OrderSummaryListGetLogic orderSummaryListGetLogic;

    @Autowired
    public OrderHistoryListGetServiceImpl(OrderSummaryListGetLogic orderSummaryListGetLogic) {
        this.orderSummaryListGetLogic = orderSummaryListGetLogic;
    }

    /**
     * 注文履歴情報リスト取得処理<br/>
     *
     * @param orderSummarySearchForDaoConditionDto 受注サマリリスト取得検索条件Dto
     * @return 注文履歴一覧Dtoリスト
     */
    @Override
    public List<OrderHistoryListDto> execute(OrderSummarySearchForDaoConditionDto orderSummarySearchForDaoConditionDto) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSummarySearchForDaoConditionDto", orderSummarySearchForDaoConditionDto);

        // 受注サマリリスト取得
        List<OrderSummaryEntity> orderSummaryEntityList =
                        orderSummaryListGetLogic.execute(orderSummarySearchForDaoConditionDto);
        List<OrderHistoryListDto> historyList = new ArrayList<>();

        for (OrderSummaryEntity summary : orderSummaryEntityList) {

            // 決済方法エンティティ生成
            SettlementMethodEntity settlement = ApplicationContextUtility.getBean(SettlementMethodEntity.class);
            settlement.setSettlementMethodName(summary.getSettlementMethodName());
            settlement.setSettlementMethodDisplayNamePC(summary.getSettlementMethodDisplayNamePC());
            settlement.setSettlementMethodDisplayNameMB(summary.getSettlementMethodDisplayNameMB());

            // 受注配送方法エンティティ生成
            OrderDeliveryEntity delivery = ApplicationContextUtility.getBean(OrderDeliveryEntity.class);

            delivery.setReceiverTimeZone(summary.getReceiverTimeZone());

            OrderHistoryListDto histories = ApplicationContextUtility.getBean(OrderHistoryListDto.class);
            histories.setOrderSummaryEntity(summary);
            histories.setSettlementMethodEntity(settlement);
            // クーポン割引額を設定
            histories.setCouponDiscountPrice(summary.getCouponDiscountPrice());

            historyList.add(histories);
        }
        return historyList;
    }
}
