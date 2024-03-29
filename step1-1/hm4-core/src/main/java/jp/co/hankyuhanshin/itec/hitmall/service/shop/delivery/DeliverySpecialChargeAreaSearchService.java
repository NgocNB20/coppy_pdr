/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySpecialChargeAreaConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySpecialChargeAreaResultDto;

import java.util.List;

/**
 * 配送特別料金エリア検索Serviceインタフェース
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public interface DeliverySpecialChargeAreaSearchService {

    /**
     * 配送特別料金エリア検索を実行します
     *
     * @param conditionDto 検索条件
     * @return 取得結果
     */
    List<DeliverySpecialChargeAreaResultDto> execute(DeliverySpecialChargeAreaConditionDto conditionDto);
}
