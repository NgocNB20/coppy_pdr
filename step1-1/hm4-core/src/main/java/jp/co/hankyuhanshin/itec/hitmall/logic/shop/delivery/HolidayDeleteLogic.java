/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.HolidayEntity;

/**
 * 休日削除ロジック<br/>
 *
 * @author Author: ogawa
 */
public interface HolidayDeleteLogic {

    /**
     * ロジック実行<br/>
     *
     * @param holidayDeleteEntity 休日エンティティ
     * @return 登録件数
     */
    int execute(HolidayEntity holidayDeleteEntity);
}
