/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.HolidayEntity;

import java.util.Date;

/**
 * 休日取得ロジック<br/>
 *
 * @author Author: ogawa
 */
public interface HolidayGetByYearAndDateLogic {

    // LSC0005

    /**
     * ロジック実行<br/>
     *
     * @param year              年
     * @param date              年月日
     * @param deliveryMethodSeq 配送方法SEQ
     * @return 休日エンティティDTO
     */
    HolidayEntity execute(Integer year, Date date, Integer deliveryMethodSeq);
}
