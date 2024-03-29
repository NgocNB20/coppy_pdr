package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.OrderSummaryDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderCSVDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderAllCsvDownloadLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * 受注CSV情報取得Logic
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class OrderAllCsvDownloadLogicImpl extends AbstractShopLogic implements OrderAllCsvDownloadLogic {

    /**
     * 受注サマリDao
     */
    private final OrderSummaryDao orderSummaryDao;

    @Autowired
    public OrderAllCsvDownloadLogicImpl(OrderSummaryDao orderSummaryDao) {
        this.orderSummaryDao = orderSummaryDao;
    }

    @Override
    public Stream<OrderCSVDto> execute(OrderSearchConditionDto conditionDto) {
        return this.orderSummaryDao.getOrderSearchOrderCSVList(conditionDto);
    }
}
