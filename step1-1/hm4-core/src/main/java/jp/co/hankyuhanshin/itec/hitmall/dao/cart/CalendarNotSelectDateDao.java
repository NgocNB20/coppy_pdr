/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.cart;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.CalendarNotSelectDateEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * カレンダー選択不可日付Daoクラス
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Dao
@ConfigAutowireable
// 2023-renew No14 from here
public interface CalendarNotSelectDateDao {

    /**
     * カレンダー選択不可日付取得
     *
     * @return カレンダー選択不可日付リスト
     */
    @Select
    List<CalendarNotSelectDateEntity> getCalendarNotSelectDate();

}
// 2023-renew No14 to here
