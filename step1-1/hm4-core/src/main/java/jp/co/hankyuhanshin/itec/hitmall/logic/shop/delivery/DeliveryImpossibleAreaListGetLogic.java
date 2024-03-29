/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleAreaConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleAreaResultDto;

import java.util.List;

/**
 * 配送不可能エリアエンティティリスト取得Logic
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public interface DeliveryImpossibleAreaListGetLogic {

    /**
     * 配送不可能エリアエンティティリストを取得します
     *
     * @param conditionDto DeliveryImpossibleAreaConditionDto
     * @return List&lt;DeliveryImpossibleAreaResultDto&gt;
     */
    List<DeliveryImpossibleAreaResultDto> execute(DeliveryImpossibleAreaConditionDto conditionDto);

}
