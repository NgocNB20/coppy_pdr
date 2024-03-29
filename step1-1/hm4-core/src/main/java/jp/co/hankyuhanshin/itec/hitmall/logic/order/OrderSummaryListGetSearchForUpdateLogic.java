/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;

import java.util.List;

/**
 * 受注検索受注サマリー排他取得ロジック<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.4 $
 */
public interface OrderSummaryListGetSearchForUpdateLogic {

    /**
     * SQLエラー
     */
    public static final String MSGCD_SELECT_FORUPDATE__FATAL = "LOO004901";

    /**
     * 受注情報を取得する。（排他）<br/>
     *
     * @param orderSearchListConditionDto 検索条件
     * @return 受注検索受注一覧結果リスト
     */
    List<OrderSummaryEntity> execute(OrderSearchConditionDto orderSearchListConditionDto);

}
