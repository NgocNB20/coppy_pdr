package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.OrderSummaryDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderCSVDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderCsvDownloadLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

/**
 * 受注CSV情報取得Logic実装クラス
 * <p>
 * Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class OrderCsvDownloadLogicImpl extends AbstractShopLogic implements OrderCsvDownloadLogic {

    private final OrderSummaryDao orderSummaryDao;

    @Autowired
    public OrderCsvDownloadLogicImpl(OrderSummaryDao orderSummaryDao) {
        this.orderSummaryDao = orderSummaryDao;
    }

    @Override
    public Stream<OrderCSVDto> execute(List<Integer> orderSeqList, Integer shopSeq) {
        return this.orderSummaryDao.getOrderSearchOrderCSVListByOrderSeq(orderSeqList, shopSeq);
    }
}
