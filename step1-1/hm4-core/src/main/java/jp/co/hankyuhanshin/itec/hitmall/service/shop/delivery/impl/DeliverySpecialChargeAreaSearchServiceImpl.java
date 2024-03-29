/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySpecialChargeAreaConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySpecialChargeAreaResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliverySpecialChargeAreaListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliverySpecialChargeAreaSearchService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配送特別料金エリア検索Service実装クラス
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
@Service
public class DeliverySpecialChargeAreaSearchServiceImpl extends AbstractShopService
                implements DeliverySpecialChargeAreaSearchService {
    /**
     * 配送特別料金エリアエンティティリスト取得Logic
     */
    private final DeliverySpecialChargeAreaListGetLogic deliverySpecialChargeAreaListGetLogic;

    @Autowired
    public DeliverySpecialChargeAreaSearchServiceImpl(DeliverySpecialChargeAreaListGetLogic deliverySpecialChargeAreaListGetLogic) {
        this.deliverySpecialChargeAreaListGetLogic = deliverySpecialChargeAreaListGetLogic;
    }

    /**
     * 配送特別料金エリア検索を実行します
     *
     * @param conditionDto 検索条件
     * @return List&lt;DeliverySpecialChargeAreaResultDto&gt;
     */
    @Override
    public List<DeliverySpecialChargeAreaResultDto> execute(DeliverySpecialChargeAreaConditionDto conditionDto) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("conditionDto", conditionDto);

        // 検索処理を実行
        return deliverySpecialChargeAreaListGetLogic.execute(conditionDto);
    }
}
