/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.credit;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携取得結果DTOクラス
 * 受注詳細情報取得　 詳細情報
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetOrderDetailInformationResponseDetailDto implements Serializable {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 顧客番号 */
    private Integer customerNo;

    /** 事業所名 */
    private String officeName;

    /** 電話番号 */
    private String tel;

    /** 受注番号 */
    private Integer orderNo;

    /** 受付方法 */
    private String receptionTypeName;

    /** 受注日 */
    private Timestamp orderDate;

    /** 出荷予定日 */
    private Timestamp estimatedShipmentDate;

    /** 出荷日 */
    private Timestamp shipmentDate;

    /** 配達指定日 */
    private Timestamp deliveryDesignatedDay;

    /** 請求金額 */
    private BigDecimal billingPrice;

    /** 預り金充当金額 */
    private BigDecimal depositPrice;

    /** 請求残高金額 */
    private BigDecimal billingBalancePrice;

    /** クレジット決済ID */
    private String creditPaymentID;

    /** マーチャント取引ID */
    private String tradingID;

    /** 決済処理結果 */
    private String creditResult;

    /** 保留区分 */
    private Integer holdType;

    /** 保留区分名称 */
    private String holdTypeName;
    // PDR Migrate Customization to here
}
