/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.cart.CalendarNotSelectDateDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.CalendarNotSelectDateEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CalendarNotSelectDateLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * カレンダー選択不可日付LogicImpl
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Component
// 2023-renew No14 from here
public class CalendarNotSelectDateLogicImpl implements CalendarNotSelectDateLogic {

    /**
     * カレンダー選択不可日付Dao
     */
    private final CalendarNotSelectDateDao calendarNotSelectDateDao;

    /**
     * コンストラクタ
     *
     * @param calendarNotSelectDateDao カレンダー選択不可日付Dao
     */
    @Autowired
    public CalendarNotSelectDateLogicImpl(CalendarNotSelectDateDao calendarNotSelectDateDao) {
        this.calendarNotSelectDateDao = calendarNotSelectDateDao;
    }

    /**
     * カレンダー選択不可日付取得
     *
     * @return カレンダー選択不可日付リスト
     */
    @Override
    public List<CalendarNotSelectDateEntity> execute() {
        return calendarNotSelectDateDao.getCalendarNotSelectDate();
    }

}
// 2023-renew No14 to here
