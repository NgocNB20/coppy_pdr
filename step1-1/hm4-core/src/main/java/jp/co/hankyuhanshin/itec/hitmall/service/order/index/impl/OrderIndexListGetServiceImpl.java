/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.index.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.index.OrderIndexSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.index.OrderIndexListGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 受注インデックスリスト取得サービス<br/>
 *
 * @author USER
 */
@Service
public class OrderIndexListGetServiceImpl extends AbstractShopService implements OrderIndexListGetService {

    /**
     * 受注インデックスリスト取得ロジック
     */
    private final OrderIndexListGetLogic orderIndexListGetLogic;

    @Autowired
    public OrderIndexListGetServiceImpl(OrderIndexListGetLogic orderIndexListGetLogic) {
        this.orderIndexListGetLogic = orderIndexListGetLogic;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param conditionDto 受注インデックスDao用検索条件
     * @return 受注インデックスエンティティリスト
     */
    @Override
    public List<OrderIndexEntity> execute(OrderIndexSearchForDaoConditionDto conditionDto) {

        // 共通情報チェック
        ArgumentCheckUtil.assertNotNull("shopSeq", conditionDto.getShopSeq());
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("orderCode", conditionDto.getOrderCode());

        return orderIndexListGetLogic.execute(conditionDto);

    }

}
