/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleDayEntity;

import java.util.Date;

/**
 * お届け不可日取得ロジック<br/>
 *
 * @author Author: ty32113
 */
public interface DeliveryImpossibleDayGetByYearAndDateLogic {

    /**
     * ロジック実行<br/>
     *
     * @param year              年
     * @param date              年月日
     * @param deliveryMethodSeq 配送方法SEQ
     * @return お届け不可日エンティティDTO
     */
    DeliveryImpossibleDayEntity execute(Integer year, Date date, Integer deliveryMethodSeq);
}
