/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.HolidayDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.HolidaySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.HolidayEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.HolidayListGetByYearLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 休日リスト取得ロジック<br/>
 *
 * @author Author: ogawa
 */
@Component
public class HolidayListGetByYearLogicImpl extends AbstractShopLogic implements HolidayListGetByYearLogic {

    /**
     * 休日Dao
     */
    private final HolidayDao holidayDao;

    @Autowired
    public HolidayListGetByYearLogicImpl(HolidayDao holidayDao) {
        this.holidayDao = holidayDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param holidaySearchForDaoConditionDto 休日検索条件DTO
     * @return 休日エンティティリスト
     */
    @Override
    public List<HolidayEntity> execute(HolidaySearchForDaoConditionDto holidaySearchForDaoConditionDto) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("holidaySearchForDaoConditionDto", holidaySearchForDaoConditionDto);

        // 休日の検索
        return holidayDao.getSearchHolidayList(
                        holidaySearchForDaoConditionDto,
                        holidaySearchForDaoConditionDto.getPageInfo().getSelectOptions()
                                              );
    }

}
