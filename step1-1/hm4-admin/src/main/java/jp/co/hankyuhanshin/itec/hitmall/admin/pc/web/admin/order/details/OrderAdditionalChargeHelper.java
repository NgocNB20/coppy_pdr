/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.additional.OrderAdditionalChargeEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.BigDecimalConversionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 追加料金ページ用Helper<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class OrderAdditionalChargeHelper {

    /**
     * ページへ内容を設定<br/>
     *
     * @param page 追加料金ページ
     */
    public void toPage(OrderAdditionalChargeModel orderAdditionalChargeModel) {

        ReceiveOrderDto modified = orderAdditionalChargeModel.getAdditionalChargeModified();
        OrderBillEntity workOrderBillEntity = modified.getOrderBillEntity();
        OrderSettlementEntity workOrderSettlementEntity = modified.getOrderSettlementEntity();

        orderAdditionalChargeModel.setBillPrice(workOrderBillEntity.getBillPrice());
        orderAdditionalChargeModel.setGoodsPriceTotal(workOrderSettlementEntity.getGoodsPriceTotal());
        orderAdditionalChargeModel.setCarriage(workOrderSettlementEntity.getCarriage());
        orderAdditionalChargeModel.setSettlementCommission(workOrderSettlementEntity.getSettlementCommission());
        orderAdditionalChargeModel.setOthersPriceTotal(workOrderSettlementEntity.getOthersPriceTotal());
        // 消費税を設定
        orderAdditionalChargeModel.setTaxPrice(workOrderSettlementEntity.getTaxPrice());
        orderAdditionalChargeModel.setStandardTaxTargetPrice(workOrderSettlementEntity.getStandardTaxTargetPrice());
        orderAdditionalChargeModel.setStandardTaxPrice(workOrderSettlementEntity.getStandardTaxPrice());
        orderAdditionalChargeModel.setReducedTaxTargetPrice(workOrderSettlementEntity.getReducedTaxTargetPrice());
        orderAdditionalChargeModel.setReducedTaxPrice(workOrderSettlementEntity.getReducedTaxPrice());

        List<OrderAdditionalChargeEntity> list = modified.getOrderAdditionalChargeEntityList();
        orderAdditionalChargeModel.setOrderAdditionalChargeItems(list);

        // クーポン情報
        CouponEntity coupon = modified.getCoupon();
        if (coupon != null) {
            orderAdditionalChargeModel.setCouponName(coupon.getCouponDisplayNamePC());
            // クーポンエンティティの割引額は受注に適用した割引額ではなく、クーポンの最大割引額
            orderAdditionalChargeModel.setCouponDiscountPrice(
                            workOrderSettlementEntity.getCouponDiscountPrice().negate());
        } else {
            orderAdditionalChargeModel.setCouponDiscountPrice(BigDecimal.ZERO);
        }
    }

    /**
     * ページ内追加料金リストへ入力内容を追加<br/>
     *
     * @param page 追加料金ページ
     */
    public void addOrderAdditionalCharge(OrderAdditionalChargeModel orderAdditionalChargeModel) {
        String name = orderAdditionalChargeModel.getInputAdditionalDetailsName();
        String price = orderAdditionalChargeModel.getInputAdditionalDetailsPrice();
        if (name == null || price == null) {
            return;
        }
        ReceiveOrderDto modified = orderAdditionalChargeModel.getAdditionalChargeModified();
        OrderSummaryEntity workOrderSummaryEntity = modified.getOrderSummaryEntity();
        List<OrderAdditionalChargeEntity> list = orderAdditionalChargeModel.getOrderAdditionalChargeItems();
        if (list == null) {
            list = new ArrayList<>();
            orderAdditionalChargeModel.setOrderAdditionalChargeItems(list);
        }

        OrderAdditionalChargeEntity additionalCharge =
                        ApplicationContextUtility.getBean(OrderAdditionalChargeEntity.class);
        additionalCharge.setAdditionalDetailsName(name);
        additionalCharge.setAdditionalDetailsPrice(BigDecimalConversionUtil.toBigDecimal(price));
        additionalCharge.setOrderSeq(workOrderSummaryEntity.getOrderSeq());
        additionalCharge.setOrderDisplay(list.size());
        list.add(additionalCharge);
        modified.setOrderAdditionalChargeEntityList(list);
        orderAdditionalChargeModel.setInputAdditionalDetailsName(null);
        orderAdditionalChargeModel.setInputAdditionalDetailsPrice(null);
    }

    /**
     * ページ内で追加した追加料金情報を反映<br/>
     *
     * @param page 追加料金ページ
     */
    public void toWorkReceiveOrderDto(OrderAdditionalChargeModel orderAdditionalChargeModel) {
        ReceiveOrderDto modified = orderAdditionalChargeModel.getModifiedReceiveOrder();
        modified.setOrderAdditionalChargeEntityList(orderAdditionalChargeModel.getOrderAdditionalChargeItems());
    }
}
