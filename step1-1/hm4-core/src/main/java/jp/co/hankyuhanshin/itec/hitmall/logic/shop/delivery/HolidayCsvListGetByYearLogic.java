package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.HolidayCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.HolidaySearchForDaoConditionDto;

import java.util.stream.Stream;

/**
 * 休日CSV出力ロジック
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
public interface HolidayCsvListGetByYearLogic {

    Stream<HolidayCsvDto> execute(HolidaySearchForDaoConditionDto conditionDto);

}
