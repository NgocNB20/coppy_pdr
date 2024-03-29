package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchOrderGoodsResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSearchOrderGoodsListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderSearchOrderGoodsListGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 受注検索（商品別一覧）取得サービス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@Service
public class OrderSearchOrderGoodsListGetServiceImpl extends AbstractShopService
                implements OrderSearchOrderGoodsListGetService {

    /** 受注検索（商品別一覧）取得ロジック */
    private final OrderSearchOrderGoodsListGetLogic orderGoodsListGetLogic;

    @Autowired
    public OrderSearchOrderGoodsListGetServiceImpl(OrderSearchOrderGoodsListGetLogic orderGoodsListGetLogic) {
        this.orderGoodsListGetLogic = orderGoodsListGetLogic;
    }

    /**
     *  受注検索（商品別一覧）取得
     *
     * @param orderSearchListConditionDto 検索条件
     * @return 受注検索商品別一覧結果リスト
     */
    @Override
    public List<OrderSearchOrderGoodsResultDto> execute(OrderSearchConditionDto orderSearchListConditionDto) {
        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSearchListConditionDto", orderSearchListConditionDto);

        // 共通情報取得
        Integer shopSeq = 1001;

        // 問題なければセット
        orderSearchListConditionDto.setShopSeq(shopSeq);

        return orderGoodsListGetLogic.execute(orderSearchListConditionDto);
    }
}
