/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryImpossibleAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleAreaConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleAreaResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleAreaListGetLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 配送不可能エリア情報リスト取得Logic実装クラス
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
@Component
public class DeliveryImpossibleAreaListGetLogicImpl extends AbstractShopLogic
                implements DeliveryImpossibleAreaListGetLogic {

    /**
     * 配送不可能エリアDao
     */
    private final DeliveryImpossibleAreaDao deliveryImpossibleAreaDao;

    @Autowired
    public DeliveryImpossibleAreaListGetLogicImpl(DeliveryImpossibleAreaDao deliveryImpossibleAreaDao) {
        this.deliveryImpossibleAreaDao = deliveryImpossibleAreaDao;
    }

    /**
     * 配送不可能エリア情報を取得します
     *
     * @param conditionDto 検索条件DTO
     * @return List&ltDeliveryImpossibleAreaResultDto&gt;
     */
    @Override
    public List<DeliveryImpossibleAreaResultDto> execute(DeliveryImpossibleAreaConditionDto conditionDto) {
        if (conditionDto.getDeliveryMethodSeq() != null) {
            return deliveryImpossibleAreaDao.getDeliveryImpossibleAreaListInAddress(conditionDto,
                                                                                    conditionDto.getPageInfo()
                                                                                                .getSelectOptions()
                                                                                   );
        }
        return null;
    }
}
