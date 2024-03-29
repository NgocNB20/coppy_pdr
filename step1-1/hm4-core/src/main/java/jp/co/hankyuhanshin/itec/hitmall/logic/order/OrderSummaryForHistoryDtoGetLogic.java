/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.summary.OrderSummaryForHistoryDto;

/**
 * 受注サマリDto取得ロジック<br/>
 *
 * @author s_tsuru
 */
public interface OrderSummaryForHistoryDtoGetLogic {

    /**
     * 受注サマリ情報を取得する<br/>
     *
     * @param orderCode 受注番号
     * @return 受注サマリエンティティ
     */
    OrderSummaryForHistoryDto execute(String orderCode);
}
