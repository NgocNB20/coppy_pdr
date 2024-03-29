/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.OrderSummaryDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchOrderResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSearchOrderListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 受注検索（受注一覧）取得ロジック<br/>
 *
 * @author yamaguchi
 * @version $Revision: 1.3 $
 */
@Component
public class OrderSearchOrderListGetLogicImpl extends AbstractShopLogic implements OrderSearchOrderListGetLogic {

    /**
     * 受注サマリーDao
     */
    private final OrderSummaryDao orderSummaryDao;

    @Autowired
    public OrderSearchOrderListGetLogicImpl(OrderSummaryDao orderSummaryDao) {
        this.orderSummaryDao = orderSummaryDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param orderSearchListConditionDto 受注検索条件
     * @return 受注検索受注一覧結果Dtoリスト
     */
    @Override
    public List<OrderSearchOrderResultDto> execute(OrderSearchConditionDto orderSearchListConditionDto) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSearchListConditionDto", orderSearchListConditionDto);

        orderSearchListConditionDto.setShopSeq(1001);

        return orderSummaryDao.getOrderSearchOrderResultList(orderSearchListConditionDto,
                                                             orderSearchListConditionDto.getPageInfo()
                                                                                        .getSelectOptions()
                                                            );
    }
}
