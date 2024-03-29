package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.HolidayDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.HolidayCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.HolidaySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.HolidayCsvListGetByYearLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * 休日CSV出力ロジック
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class HolidayCsvListGetByYearLogicImpl extends AbstractShopLogic implements HolidayCsvListGetByYearLogic {

    /**
     * 休日DAO
     */
    private final HolidayDao holidayDao;

    @Autowired
    public HolidayCsvListGetByYearLogicImpl(HolidayDao holidayDao) {
        this.holidayDao = holidayDao;
    }

    @Override
    public Stream<HolidayCsvDto> execute(HolidaySearchForDaoConditionDto conditionDto) {
        return this.holidayDao.exportCsvSearchHolidayList(conditionDto);
    }
}
