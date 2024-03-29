package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchOrderGoodsResultDto;

import java.util.List;

/**
 * 受注検索（商品別一覧）取得ロジック
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
public interface OrderSearchOrderGoodsListGetLogic {

    /**
     *
     * 受注検索（商品別一覧）取得<br/>
     *
     * @param orderSearchListConditionDto 検索条件
     * @return 受注検索商品別一覧結果リスト
     */
    List<OrderSearchOrderGoodsResultDto> execute(OrderSearchConditionDto orderSearchListConditionDto);
}
