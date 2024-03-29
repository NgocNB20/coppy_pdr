/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleAreaConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleAreaResultDto;

import java.util.List;

/**
 * 配送不可能エリア検索Serviceインタフェース
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public interface DeliveryImpossibleAreaSearchService {

    /**
     * 配送特別料金エリア検索を実行します
     *
     * @param conditionDto 配送不可能エリア検索条件DTO
     * @return 配送不可能エリアDTOリスト
     */
    List<DeliveryImpossibleAreaResultDto> execute(DeliveryImpossibleAreaConditionDto conditionDto);

}
