/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携リクエストDTOクラス
 * 受注連携 詳細情報
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiAddOrderInformationRequestDetailDto implements Serializable {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 受注番号 */
    private String orderNo;

    /** 関連受注番号 */
    private String relOrderNo;

    /** 入力担当者 */
    private String inputUserID;

    /** 確定担当者 */
    private String comfirmUserID;

    /** 受付方法 */
    private String orderType;

    /** 受注日 */
    private Timestamp orderDate;

    /** 出荷予定日 */
    private Timestamp shipmentDate;

    /** 入荷予定日 */
    private Timestamp stockDate;

    /** 顧客番号 */
    private String customerNo;

    /** 電話番号 */
    private String telNo;

    /** 広告媒体 */
    private String mediaCode;

    /** 倉庫 */
    private String stockroomCode;

    /** お届け先顧客番号 */
    private String deliveryCustomerNo;

    /** お届け先連絡番号 */
    private String deliveryTelNo;

    /** 使用ポイント */
    private String usePoint;

    /** 支払方法 */
    private String paymentType;

    /** クレジット会社 */
    private String creditCompanyCode;

    /** クレジット番号 */
    private String creditCardNo;

    /** クレジット有効期限 */
    private String creditExpirationDate;

    /** クレジット支払回数 */
    private String creditSplitNumber;

    /** クレジット決済ID */
    private String creditPaymentID;

    /** 配送方法 */
    private String deliveryType;

    /** 請求書 */
    private String requisitionType;

    /** 保留区分 */
    private String holdType;

    /** 配達指定日 */
    private Timestamp deliveryDesignatedDay;

    /** 配達指定時間 */
    private String deliveryDesignatedTimeCode;

    /** 出荷場メモ１ */
    private String shippingMemo1;

    /** 出荷場メモ２ */
    private String shippingMemo2;

    /** 合計金額 */
    private BigDecimal totalPrice;

    /** ポイント値引 */
    private BigDecimal pointDiscountPrice;

    /** プロモ値引 */
    private BigDecimal promotionDiscountPrice;

    /** 値引 */
    private BigDecimal discountPrice;

    /** 合計値引 */
    private BigDecimal totalDiscountPrice;

    /** 送料 */
    private BigDecimal shippingPrice;

    /** 請求金額 */
    private BigDecimal billingPrice;

    /** 内消費税 */
    private BigDecimal taxPrice;

    /** 受注連携 商品情報 */
    private List<WebApiAddOrderInformationRequestGoodsDto> goodsInfo;

    /** マーチャント取引ID */
    private String tradingID;

    /** クーポンコード */
    private String couponCode;

    /** プロモーションコード */
    private String promotionCode;
    // PDR Migrate Customization to here
}
