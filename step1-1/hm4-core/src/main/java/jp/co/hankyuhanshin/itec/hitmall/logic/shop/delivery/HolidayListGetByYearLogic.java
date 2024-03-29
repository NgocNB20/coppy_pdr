/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.HolidaySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.HolidayEntity;

import java.util.List;

/**
 * 休日リスト取得ロジック<br/>
 *
 * @author Author: ogawa
 */
public interface HolidayListGetByYearLogic {

    /**
     * ロジック実行<br/>
     *
     * @param holidaySearchForDaoConditionDto 休日検索条件DTO
     * @return list 休日エンティティリスト
     */
    List<HolidayEntity> execute(HolidaySearchForDaoConditionDto holidaySearchForDaoConditionDto);
}
