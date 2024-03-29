/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.HolidayEntity;

/**
 * 休日登録ロジック<br/>
 *
 * @author Author: ogawa
 */
public interface HolidayRegistUpdateLogic {

    /**
     * ロジック実行<br/>
     *
     * @param holidayEntity 休日エンティティ
     * @return 登録件数
     */
    int execute(HolidayEntity holidayEntity);
}
