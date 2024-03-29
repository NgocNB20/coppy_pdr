package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleDayCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleDaySearchForDaoConditionDto;

import java.util.stream.Stream;

/**
 * お届け不可日CSV出力ロジック
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
public interface DeliveryImpossibleDayCsvListGetByYearLogic {

    Stream<DeliveryImpossibleDayCsvDto> execute(DeliveryImpossibleDaySearchForDaoConditionDto conditionDto);

}
