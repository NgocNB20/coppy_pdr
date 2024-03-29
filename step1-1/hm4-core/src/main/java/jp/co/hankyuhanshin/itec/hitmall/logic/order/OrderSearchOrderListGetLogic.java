/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchOrderResultDto;

import java.util.List;

/**
 * 受注検索（受注一覧）取得ロジック<br/>
 *
 * @author yamaguchi
 * @version $Revision: 1.3 $
 */
public interface OrderSearchOrderListGetLogic {

    /**
     * 実行メソッド<br/>
     *
     * @param orderSearchListConditionDto 検索条件
     * @return 受注検索受注一覧結果Dtoリスト
     */
    List<OrderSearchOrderResultDto> execute(OrderSearchConditionDto orderSearchListConditionDto);

}
