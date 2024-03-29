/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.OrderSummaryDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSummarySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 受注サマリリスト取得ロジック<br/>
 *
 * @author natume
 * @author Nishigaki (Itec) 2011/12/22 チケット #2702 対応
 */
@Component
public class OrderSummaryListGetLogicImpl extends AbstractShopLogic implements OrderSummaryListGetLogic {

    /**
     * 受注サマリDao<br/>
     */
    private final OrderSummaryDao orderSummaryDao;

    @Autowired
    public OrderSummaryListGetLogicImpl(OrderSummaryDao orderSummaryDao) {
        this.orderSummaryDao = orderSummaryDao;
    }

    /**
     * 受注サマリリスト取得処理<br/>
     *
     * @param conditionDto 受注サマリリスト取得検索条件Dto
     * @return 受注サマリエンティティリスト
     */
    @Override
    public List<OrderSummaryEntity> execute(OrderSummarySearchForDaoConditionDto conditionDto) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("conditionDto", conditionDto);

        // 受注サマリリスト取得実行
        return orderSummaryDao.getSearchAllOrderSummaryList(
                        conditionDto, conditionDto.getPageInfo().getSelectOptions());
    }

    /**
     * 受注サマリリスト取得処理<br/>
     *
     * @param shopSeq      ショップSEQ
     * @param orderSeqList 受注SEQリスト
     * @return 受注サマリエンティティリスト
     */
    @Override
    public List<OrderSummaryEntity> execute(Integer shopSeq, List<Integer> orderSeqList) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("orderSeqList", orderSeqList);

        return orderSummaryDao.getEntityListByOrderSeqList(orderSeqList);
    }
}
