/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliverySpecialChargeAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySpecialChargeAreaConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySpecialChargeAreaResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliverySpecialChargeAreaListGetLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 配送特別料金エリア情報リスト取得Logic実装クラス
 *
 * @author $author$
 * @version $Revision: 1.2 $
 */
@Component
public class DeliverySpecialChargeAreaListGetLogicImpl extends AbstractShopLogic
                implements DeliverySpecialChargeAreaListGetLogic {
    /**
     * 配送特別料金エリアDao
     */
    private final DeliverySpecialChargeAreaDao deliverySpecialChargeAreaDao;

    @Autowired
    public DeliverySpecialChargeAreaListGetLogicImpl(DeliverySpecialChargeAreaDao deliverySpecialChargeAreaDao) {
        this.deliverySpecialChargeAreaDao = deliverySpecialChargeAreaDao;
    }

    /**
     * 配送特別料金エリア情報を取得します
     *
     * @param conditionDto 検索条件DTO
     * @return List&ltDeliverySpecialChargeAreaResultDto&gt;
     */
    @Override
    public List<DeliverySpecialChargeAreaResultDto> execute(DeliverySpecialChargeAreaConditionDto conditionDto) {
        if (conditionDto.getDeliveryMethodSeq() != null) {
            return deliverySpecialChargeAreaDao.getDeliverySpecialChargeAreaListInAddress(conditionDto,
                                                                                          conditionDto.getPageInfo()
                                                                                                      .getSelectOptions()
                                                                                         );
        }

        return null;
    }
}
