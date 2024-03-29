/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchOrderResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSearchOrderListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderSearchOrderListGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 受注番号別一覧検索<br/>
 *
 * @author YAMAUGUCHI
 * @version $Revision: 1.2 $
 */
@Service
public class OrderSearchOrderListGetServiceImpl extends AbstractShopService implements OrderSearchOrderListGetService {

    /**
     * 受注番号別一覧検索ロジック
     */
    private final OrderSearchOrderListGetLogic orderSearchOrderListGetLogic;

    @Autowired
    public OrderSearchOrderListGetServiceImpl(OrderSearchOrderListGetLogic orderSearchOrderListGetLogic) {
        this.orderSearchOrderListGetLogic = orderSearchOrderListGetLogic;
    }

    /**
     * 受注番号別一覧検索実行メソッド<br/>
     *
     * @param orderSearchListConditionDto 受注検索条件
     * @return 受注検索受注一覧Dtoリスト
     */
    @Override
    public List<OrderSearchOrderResultDto> execute(OrderSearchConditionDto orderSearchListConditionDto) {

        Integer shopSeq = 1001;

        orderSearchListConditionDto.setShopSeq(shopSeq);

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSearchListConditionDto", orderSearchListConditionDto);
        ArgumentCheckUtil.assertNotNull("shopSeq", orderSearchListConditionDto.getShopSeq());

        return orderSearchOrderListGetLogic.execute(orderSearchListConditionDto);
    }
}
