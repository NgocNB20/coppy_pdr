/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.service.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;

import java.util.List;
import java.util.Map;

/**
 * 受注明細PDF出力サービスインターフェース
 *
 * @author $author$
 */
public interface OrderDetailPdfOutputService {

    /**
     * 受注明細PDF情報を取得しPDFで出力します
     *
     * @param condition        OrderSearchListConditionDto
     * @param settlememntItems 検索条件-決済方法リスト
     * @param timeTypeItems    検索条件-期間種別リスト
     * @param conditionDto     検索条件
     * @return 出力件数
     */
    int execute(Object condition,
                List<Map<String, String>> settlememntItems,
                List<Map<String, String>> timeTypeItems,
                OrderSearchConditionDto conditionDto);
}
