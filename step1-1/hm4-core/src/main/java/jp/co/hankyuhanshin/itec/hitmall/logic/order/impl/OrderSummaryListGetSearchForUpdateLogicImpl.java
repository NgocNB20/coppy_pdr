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
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryListGetForUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryListGetSearchForUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 受注検索受注サマリー排他取得ロジック（実装）<br/>
 *
 * @author yamaguchi
 * @version $Revision: 1.3 $
 */
@Component
public class OrderSummaryListGetSearchForUpdateLogicImpl extends AbstractShopLogic
                implements OrderSummaryListGetSearchForUpdateLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSummaryListGetSearchForUpdateLogicImpl.class);

    /**
     * orderSummaryDao
     */
    private final OrderSummaryDao orderSummaryDao;

    /**
     * 受注サマリー排他取得ロジック
     */
    private final OrderSummaryListGetForUpdateLogic orderSummaryListGetForUpdateLogic;

    @Autowired
    public OrderSummaryListGetSearchForUpdateLogicImpl(OrderSummaryDao orderSummaryDao,
                                                       OrderSummaryListGetForUpdateLogic orderSummaryListGetForUpdateLogic) {
        this.orderSummaryDao = orderSummaryDao;
        this.orderSummaryListGetForUpdateLogic = orderSummaryListGetForUpdateLogic;
    }

    /**
     * 検索条件から検索結果を取得する。<br/>
     * 取得した検索データは排他取得する。
     *
     * @param orderSearchListConditionDto 検索条件
     * @return 受注サマリリスト
     */
    @Override
    public List<OrderSummaryEntity> execute(OrderSearchConditionDto orderSearchListConditionDto) {
        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSearchListConditionDto", orderSearchListConditionDto);
        ArgumentCheckUtil.assertNotNull("shopSeq", orderSearchListConditionDto.getShopSeq());

        List<OrderSearchOrderResultDto> orderSearchOrderResultList = null;
        try {
            orderSearchOrderResultList = orderSummaryDao.getOrderSearchOrderResultList(orderSearchListConditionDto,
                                                                                       orderSearchListConditionDto.getPageInfo()
                                                                                                                  .getSelectOptions()
                                                                                      );
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            this.throwMessage(MSGCD_SELECT_FORUPDATE__FATAL);
        }

        if (orderSearchOrderResultList.size() == 0) {
            return new ArrayList<>();
        }

        List<Integer> orderSeqList = getOrderSeqList(orderSearchOrderResultList);

        return orderSummaryListGetForUpdateLogic.execute(orderSeqList);
    }

    /**
     * 受注SEQリストを作成<br/>
     *
     * @param orderSearchOrderResultList 受注検索結果リスト
     * @return 受注SEQリスト
     */
    protected List<Integer> getOrderSeqList(List<OrderSearchOrderResultDto> orderSearchOrderResultList) {
        List<Integer> orderSeqList = new ArrayList<>();
        for (int index = 0; index < orderSearchOrderResultList.size(); index++) {
            OrderSearchOrderResultDto orderSearchOrderResultEntity = orderSearchOrderResultList.get(index);
            orderSeqList.add(orderSearchOrderResultEntity.getOrderSeq());
        }
        return orderSeqList;
    }

}
