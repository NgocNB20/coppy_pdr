/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSummarySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;

import java.util.List;

/**
 * 受注サマリリスト取得ロジック<br/>
 *
 * @author natume
 * @author Nishigaki (Itec) 2011/12/22 チケット #2702 対応
 */
public interface OrderSummaryListGetLogic {

    /**
     * 受注サマリリスト取得処理<br/>
     *
     * @param orderSummarySearchForDaoConditionDto 受注サマリリスト取得検索条件Dto
     * @return 受注サマリエンティティリスト
     */
    List<OrderSummaryEntity> execute(OrderSummarySearchForDaoConditionDto orderSummarySearchForDaoConditionDto);

    /**
     * 受注サマリリスト取得処理<br/>
     *
     * @param shopSeq      ショップSEQ
     * @param orderSeqList 受注SEQリスト
     * @return 受注サマリエンティティリスト
     */
    List<OrderSummaryEntity> execute(Integer shopSeq, List<Integer> orderSeqList);
}
