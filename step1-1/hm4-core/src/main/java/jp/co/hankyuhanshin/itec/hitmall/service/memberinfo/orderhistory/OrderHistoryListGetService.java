/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.orderhistory;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.orderhistory.OrderHistoryListDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSummarySearchForDaoConditionDto;

import java.util.List;

/**
 * 注文履歴情報リスト取得サービス<br/>
 *
 * @author ueshima
 * @version $Revision: 1.4 $
 */
public interface OrderHistoryListGetService {

    /**
     * 受注インデックス情報取得失敗エラー<br/>
     * <code>MSGCD_ORDERINDEXENTITYDTO_NULL</code><br/>
     */
    public static final String MSGCD_ORDERINDEXENTITYDTO_NULL = "SMO000101";

    /**
     * 受注商品リスト情報取得失敗エラー<br/>
     * <code>MSGCD_ORDERGOODSENTITYDTOLIST_EMPTY</code><br/>
     */
    public static final String MSGCD_ORDERGOODSENTITYDTOLIST_EMPTY = "SMO000102";

    /**
     * 決済方法情報取得失敗エラー<br/>
     * <code>MSGCD_SETTLEMENTMETHODENTITYDTO_NULL</code><br/>
     */
    public static final String MSGCD_SETTLEMENTMETHODENTITYDTO_NULL = "SMO000103";

    /**
     * 注文履歴情報リスト取得処理<br/>
     *
     * @param orderSummarySearchForDaoConditionDto 受注サマリリスト取得検索条件Dto
     * @return 注文履歴一覧Dtoリスト
     */
    List<OrderHistoryListDto> execute(OrderSummarySearchForDaoConditionDto orderSummarySearchForDaoConditionDto);
}
