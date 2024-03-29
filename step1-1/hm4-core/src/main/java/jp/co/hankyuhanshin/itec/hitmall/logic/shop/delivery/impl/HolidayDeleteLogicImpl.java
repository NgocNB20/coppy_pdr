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
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.HolidayDeleteLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 休日削除ロジック<br/>
 *
 * @author Author: ogawa
 */
@Component
public class HolidayDeleteLogicImpl extends AbstractShopLogic implements HolidayDeleteLogic {

    /**
     * 休日Dao
     */
    private final HolidayDao holidayDao;

    @Autowired
    public HolidayDeleteLogicImpl(HolidayDao holidayDao) {
        this.holidayDao = holidayDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param holidayDeleteEntity 休日エンティティ
     * @return 登録件数
     */
    @Override
    public int execute(HolidayEntity holidayDeleteEntity) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("holidayEntity", holidayDeleteEntity);

        // 削除
        return holidayDao.deleteHolidayByYear(holidayDeleteEntity);
    }
}
