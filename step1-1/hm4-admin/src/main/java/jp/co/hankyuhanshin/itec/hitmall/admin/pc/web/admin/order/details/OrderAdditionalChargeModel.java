/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVNumber;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.additional.OrderAdditionalChargeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

/**
 * 追加料金ページ<br />
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderAdditionalChargeModel extends AbstractModel {

    /**
     * 追加料金画面用
     */
    private ReceiveOrderDto additionalChargeModified;

    /**
     * 検索条件<br/>
     */
    private OrderSearchConditionDto conditionDto;

    /**
     * 受注コード（必須）
     */
    private String orderCode;

    /**
     * 受注DTO（修正前）
     */
    private ReceiveOrderDto originalReceiveOrder;

    /**
     * 受注DTO（修正後）
     */
    private ReceiveOrderDto modifiedReceiveOrder;

    /**
     * 商品合計
     */
    private BigDecimal goodsPriceTotal = BigDecimal.ZERO;

    /**
     * 送料
     */
    private BigDecimal carriage = BigDecimal.ZERO;

    /**
     * 手数料
     */
    private BigDecimal settlementCommission = BigDecimal.ZERO;

    /**
     * 請求金額
     */
    private BigDecimal billPrice = BigDecimal.ZERO;

    /**
     * 追加項目名（入力欄）
     */
    @Length(min = 1, max = 60)
    @NotEmpty
    private String inputAdditionalDetailsName;

    /**
     * 追加金額（入力欄）
     */
    @HVNumber(minus = true)
    @Digits(integer = 8, fraction = 0, message = "{HNumberLengthValidator.FRACTION_MAX_ZERO_detail}")
    @NotEmpty
    @HCNumber
    private String inputAdditionalDetailsPrice;

    /**
     * 受注追加料金リスト
     */
    private List<OrderAdditionalChargeEntity> orderAdditionalChargeItems;

    /**
     * その他追加料金
     */
    private BigDecimal othersPriceTotal = BigDecimal.ZERO;

    /**
     * 消費税
     */
    private BigDecimal taxPrice = BigDecimal.ZERO;

    /**
     * 標準税率対象金額
     */
    private BigDecimal standardTaxTargetPrice = BigDecimal.ZERO;

    /**
     * 標準税率消費税
     */
    private BigDecimal standardTaxPrice = BigDecimal.ZERO;

    /**
     * 軽減税率対象金額
     */
    private BigDecimal reducedTaxTargetPrice = BigDecimal.ZERO;

    /**
     * 軽減税率消費税
     */
    private BigDecimal reducedTaxPrice = BigDecimal.ZERO;

    /**
     * クーポン名
     */
    private String couponName;

    /**
     * クーポン割引額
     */
    private BigDecimal couponDiscountPrice;

    /**
     * クーポン割引情報の表示／非表示判定処理を行う。<br />
     * <pre>
     * 注文情報エリアにクーポン割引情報を表示するかどうか判定する為に利用する。
     * </pre>
     *
     * @return クーポン割引額が１円以上の場合 true
     */
    public boolean isDisplayCouponDiscount() {
        return couponDiscountPrice.compareTo(BigDecimal.ZERO) != 0;
    }

    /**
     * @return 追加料金エンティティ数
     */
    public Integer getOrderAdditionalChargeItemsCount() {
        if (orderAdditionalChargeItems == null || orderAdditionalChargeItems.isEmpty()) {
            return null;
        }
        return orderAdditionalChargeItems.size();
    }

}
