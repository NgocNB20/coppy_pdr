/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.CalendarNotSelectDateEntity;

import java.util.List;

/**
 * カレンダー選択不可日付Logic
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
// 2023-renew No14 from here
public interface CalendarNotSelectDateLogic {

    /**
     * カレンダー選択不可日付取得
     *
     * @return カレンダー選択不可日付リスト
     */
    List<CalendarNotSelectDateEntity> execute();
}
// 2023-renew No14 to here
