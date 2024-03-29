/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.HolidayDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.HolidayEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.HolidayGetByYearAndDateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 休日取得ロジック<br/>
 *
 * @author Author: ogawa
 */
@Component
public class HolidayGetByYearAndDateLogicImpl extends AbstractShopLogic implements HolidayGetByYearAndDateLogic {

    /**
     * 休日Dao
     */
    private final HolidayDao holidayDao;

    @Autowired
    public HolidayGetByYearAndDateLogicImpl(HolidayDao holidayDao) {
        this.holidayDao = holidayDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param year              年
     * @param date              年月日
     * @param deliveryMethodSeq 配送方法SEQ
     * @return 休日エンティティ
     */
    @Override
    public HolidayEntity execute(Integer year, Date date, Integer deliveryMethodSeq) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("year", year);
        ArgumentCheckUtil.assertNotNull("date", date);
        ArgumentCheckUtil.assertNotNull("deliveryMethodSeq", deliveryMethodSeq);

        // 休日の検索
        return holidayDao.getHolidayByYearAndDate(year, date, deliveryMethodSeq);
    }
}
