// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.history;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CancelOrderHistoryGoodsItemRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CancelOrderHistoryOrderItemRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.OrderHistoryCancelOrderRequest;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiShipmentOrderHistoryResponseBaseDetailDto;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * PDR#032 配送状況・注文履歴の基幹連携
 * 注文履歴（未出荷）画面Dxoクラス
 * 注文連携Web-APIより取得した情報を画面に表示
 *
 * @author s.kume
 */
@Component
public class UnshippedHelper extends BaseShipHelper {

    // 2023-renew No68 from here
    /**
     * キャンセル可否フラグ：キャンセル可
     */
    public static final int CANCEL_POSSIBLE = 1;
    // 2023-renew No68 to here

    /**
     * コンストラクタ
     */
    @Autowired
    public UnshippedHelper(ConversionUtility conversionUtility) {
        super(conversionUtility);
    }

    // 2023-renew No68 from here

    /**
     * 注文履歴詳細情報を画面に反映
     * 出荷済情報、未出荷情報で追加した情報がある場合は継承したクラスでこのメソッドをOverrideして追加を行う
     *
     * @param item             注文履歴情報ページアイテムクラス
     * @param orderHistoryInfo 注文履歴詳細情報
     */
    @Override
    protected void setOrderHistoryInfo(OrderHistoryOrderItem item,
                                       WebApiShipmentOrderHistoryResponseBaseDetailDto orderHistoryInfo) {

        // 親クラスの処理を実行
        super.setOrderHistoryInfo(item, orderHistoryInfo);

        // キャンセル可否フラグ
        item.setCancelYesNo(orderHistoryInfo.getCancelYesNo() == CANCEL_POSSIBLE);

    }

    /**
     * 注文キャンセル情報リクエストに変換
     *
     * @param orderHistoryOrderItem 注文履歴（未出荷）画面 詳細情報アイテムクラス
     * @param memberInfoSeq 会員SEQ
     * @param officeName 事業所名
     * @return 注文キャンセル情報リクエスト
     */
    public OrderHistoryCancelOrderRequest toOrderHistoryCancelOrderRequest(OrderHistoryOrderItem orderHistoryOrderItem,
                                                                           Integer memberInfoSeq,
                                                                           String officeName) {
        OrderHistoryCancelOrderRequest request = new OrderHistoryCancelOrderRequest();

        if (ObjectUtils.isEmpty(orderHistoryOrderItem)) {
            return request;
        }

        request.setOrderNo(Integer.valueOf(orderHistoryOrderItem.getOrderCode()));
        request.setMemberInfoSeq(memberInfoSeq);
        request.setCancelOrderHistoryOrderItem(toCancelOrderHistoryOrderItemRequest(orderHistoryOrderItem, officeName));

        return request;
    }

    /**
     * キャンセル対象受注Itemに変換
     *
     * @param orderHistoryOrderItem 注文履歴（未出荷）画面 詳細情報アイテムクラス
     * @param officeName 事業所名
     * @return キャンセル対象受注Item
     */
    public CancelOrderHistoryOrderItemRequest toCancelOrderHistoryOrderItemRequest(OrderHistoryOrderItem orderHistoryOrderItem,
                                                                                   String officeName) {

        CancelOrderHistoryOrderItemRequest orderItemRequest = new CancelOrderHistoryOrderItemRequest();

        orderItemRequest.setOfficeName(officeName);
        orderItemRequest.setOrderTime(orderHistoryOrderItem.getOrderTime());
        orderItemRequest.setOrderCode(orderHistoryOrderItem.getOrderCode());
        orderItemRequest.setReceiveDate(orderHistoryOrderItem.getReceiveDate());
        orderItemRequest.setReceiveOfficeName(orderHistoryOrderItem.getReceiveOfficeName());
        orderItemRequest.setReceiveZipCode(orderHistoryOrderItem.getReceiveZipCode());
        orderItemRequest.setReceiveAddress1(orderHistoryOrderItem.getReceiveAddress1());
        orderItemRequest.setReceiveAddress2(orderHistoryOrderItem.getReceiveAddress2());
        orderItemRequest.setReceiveAddress3(orderHistoryOrderItem.getReceiveAddress3());
        orderItemRequest.setReceiveAddress4(orderHistoryOrderItem.getReceiveAddress4());
        orderItemRequest.setReceiveAddress5(orderHistoryOrderItem.getReceiveAddress5());
        orderItemRequest.setPaymentType(orderHistoryOrderItem.getPaymentType());
        orderItemRequest.setCouponCode(orderHistoryOrderItem.getCouponCode());
        orderItemRequest.setCouponName(orderHistoryOrderItem.getCouponName());
        orderItemRequest.setPaymentTotalPrice(orderHistoryOrderItem.getPaymentTotalPrice());

        List<CancelOrderHistoryGoodsItemRequest> cancelOrderHistoryGoodsItemRequestList = new ArrayList<>();
        for (OrderHistoryGoodsItem goodsItem : orderHistoryOrderItem.getOrderHistoryGoodsItems()) {
            CancelOrderHistoryGoodsItemRequest goodsItemRequest = new CancelOrderHistoryGoodsItemRequest();

            goodsItemRequest.setGoodsCode(goodsItem.getGoodsCode());
            goodsItemRequest.setGoodsName(goodsItem.getGoodsName());
            goodsItemRequest.setGoodsCount(goodsItem.getGoodsCount());
            goodsItemRequest.setUnitName(goodsItem.getUnitName());
            goodsItemRequest.setDiscountFlag(goodsItem.getDiscountFlag());

            cancelOrderHistoryGoodsItemRequestList.add(goodsItemRequest);
        }
        orderItemRequest.setCancelOrderHistoryGoodsItems(cancelOrderHistoryGoodsItemRequestList);

        return orderItemRequest;
    }

    // 2023-renew No68 to here

}
// PDR Migrate Customization to here
