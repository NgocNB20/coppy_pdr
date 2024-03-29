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
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.HolidayRegistUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 休日登録ロジック<br/>
 *
 * @author Author: ogawa
 */
@Component
public class HolidayRegistUpdateLogicImpl extends AbstractShopLogic implements HolidayRegistUpdateLogic {

    /**
     * 休日Dao
     */
    private final HolidayDao holidayDao;

    /**
     * 休日取得ロジック
     */
    private final HolidayGetByYearAndDateLogic holidayGetByYearAndDateLogic;

    @Autowired
    public HolidayRegistUpdateLogicImpl(HolidayDao holidayDao,
                                        HolidayGetByYearAndDateLogic holidayGetByYearAndDateLogic) {
        this.holidayDao = holidayDao;
        this.holidayGetByYearAndDateLogic = holidayGetByYearAndDateLogic;
    }

    /**
     * ロジック実行<br/>
     *
     * @param holidayEntity 休日エンティティ
     * @return 登録件数
     */
    @Override
    public int execute(HolidayEntity holidayEntity) {

        // 存在チェック
        HolidayEntity resultEntity =
                        holidayGetByYearAndDateLogic.execute(holidayEntity.getYear(), holidayEntity.getDate(),
                                                             holidayEntity.getDeliveryMethodSeq()
                                                            );
        int result = 0;

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 登録日時の設定
        holidayEntity.setRegistTime(dateUtility.getCurrentTime());

        if (resultEntity != null) {
            // レコードが存在する場合は更新
            result = holidayDao.update(holidayEntity);
        } else {
            // レコードが存在しない場合は登録
            result = holidayDao.insert(holidayEntity);
        }

        return result;
    }

}
