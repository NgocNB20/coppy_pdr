/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchOrderResultDto;

import java.util.List;

/**
 * 受注番号別一覧検索<br/>
 *
 * @author YAMAUGUCHI
 * @version $Revision: 1.2 $
 */
public interface OrderSearchOrderListGetService {

    /**
     * 受注番号別一覧検索実行メソッド<br/>
     *
     * @param orderSearchListConditionDto 受注検索条件
     * @return 受注検索受注一覧Dtoリスト
     */
    List<OrderSearchOrderResultDto> execute(OrderSearchConditionDto orderSearchListConditionDto);

}
