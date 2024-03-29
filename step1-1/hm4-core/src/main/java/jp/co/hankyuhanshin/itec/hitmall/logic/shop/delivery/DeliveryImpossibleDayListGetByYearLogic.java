/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleDaySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleDayEntity;

import java.util.List;

/**
 * お届け不可日リスト取得ロジック<br/>
 *
 * @author Author: ty32113
 */
public interface DeliveryImpossibleDayListGetByYearLogic {

    /**
     * ロジック実行<br/>
     *
     * @param deliveryImpossibleDaySearchForDaoConditionDto お届け不可日検索条件DTO
     * @return list お届け不可日エンティティリスト
     */
    List<DeliveryImpossibleDayEntity> execute(DeliveryImpossibleDaySearchForDaoConditionDto deliveryImpossibleDaySearchForDaoConditionDto);
}
