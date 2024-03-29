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
import java.sql.Timestamp;
import java.util.List;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携取得結果DTOクラス
 * 出荷情報取得 詳細情報
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetShipmentInformationResponseDetailDto implements Serializable {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 受注番号 */
    private Integer orderNo;

    /** 事業所名 */
    private String officeName;

    /** お届け先事業所名 */
    private String receiveOfficeName;

    /** お届け先郵便番号 */
    private String receiveZipcode;

    /** お届け先住所(都道府県・市区町村) */
    private String receiveAddress1;

    /** お届け先住所(丁目・番地) */
    private String receiveAddress2;

    /** お届け先住所(建物名・部屋番号) */
    private String receiveAddress3;

    /** お届け先住所(方書1) */
    private String receiveAddress4;

    /** お届け先住所(方書2) */
    private String receiveAddress5;

    /** 小計 */
    private String subTotalPrice;

    /** 値引 */
    private String discountPrice;

    /** 送料 */
    private String shippingPrice;

    /** 消費税 */
    private String taxPrice;

    /** 合計金額 */
    private String totalPrice;

    /** 配送方法 */
    private String deliveryName;

    /** 支払方法 */
    private String paymentName;

    /** 配達指定日 */
    private Timestamp deliveryDesignatedDay;

    /** 配達指定時間 */
    private String deliveryDesignatedTimeName;

    /** 配送確認URL */
    private String deliveryComfirmURL;

    /** 送り状番号 */
    private String invoiceNo;

    /** メールアドレス */
    private String mailAddress;

    /** 出荷情報取得 詳細情報-商品情報 */
    private List<WebApiGetShipmentInformationResponseDetailGoodsInfoDto> goodsList;
    // PDR Migrate Customization to here
}
