package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.OrderSummaryDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchOrderGoodsResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSearchOrderGoodsListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 受注検索（商品別一覧）取得ロジック
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@Component
public class OrderSearchOrderGoodsListGetLogicImpl extends AbstractShopLogic
                implements OrderSearchOrderGoodsListGetLogic {

    /** 受注サマリDao */
    private final OrderSummaryDao orderSummaryDao;

    @Autowired
    public OrderSearchOrderGoodsListGetLogicImpl(OrderSummaryDao orderSummaryDao) {
        this.orderSummaryDao = orderSummaryDao;
    }

    /**
     * 受注検索（商品別一覧）取得<br/>
     *
     * @param orderSearchListConditionDto 検索条件
     * @return 受注検索商品別一覧結果リスト
     */
    @Override
    public List<OrderSearchOrderGoodsResultDto> execute(OrderSearchConditionDto orderSearchListConditionDto) {
        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSearchListConditionDto", orderSearchListConditionDto);
        ArgumentCheckUtil.assertGreaterThanZero("shopSeq", orderSearchListConditionDto.getShopSeq());

        return orderSummaryDao.getOrderSearchOrderGoodsResultList(orderSearchListConditionDto,
                                                                  orderSearchListConditionDto.getPageInfo()
                                                                                             .getSelectOptions()
                                                                 );
    }
}
