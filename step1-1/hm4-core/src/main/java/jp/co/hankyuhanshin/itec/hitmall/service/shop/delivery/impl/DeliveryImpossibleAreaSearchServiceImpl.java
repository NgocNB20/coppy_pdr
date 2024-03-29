/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleAreaConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleAreaResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleAreaListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryImpossibleAreaSearchService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配送不可能エリア検索Service実装クラス
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
@Service
public class DeliveryImpossibleAreaSearchServiceImpl extends AbstractShopService
                implements DeliveryImpossibleAreaSearchService {

    /**
     * 配送不可能エリアエンティティリスト取得Logic
     */
    private final DeliveryImpossibleAreaListGetLogic deliveryImpossibleAreaListGetLogic;

    @Autowired
    public DeliveryImpossibleAreaSearchServiceImpl(DeliveryImpossibleAreaListGetLogic deliveryImpossibleAreaListGetLogic) {
        this.deliveryImpossibleAreaListGetLogic = deliveryImpossibleAreaListGetLogic;
    }

    /**
     * 配送不可能エリア検索を実行します
     *
     * @param conditionDto 検索条件DTO
     * @return List&lt;DeliveryImpossibleAreaResultDto&gt;
     */
    @Override
    public List<DeliveryImpossibleAreaResultDto> execute(DeliveryImpossibleAreaConditionDto conditionDto) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("conditionDto", conditionDto);

        // 検索処理を実行
        return deliveryImpossibleAreaListGetLogic.execute(conditionDto);
    }
}
