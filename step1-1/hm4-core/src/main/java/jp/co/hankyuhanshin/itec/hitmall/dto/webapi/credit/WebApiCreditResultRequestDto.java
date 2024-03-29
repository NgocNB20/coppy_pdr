/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.credit;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携リクエストDTOクラス
 * オーソリ結果連携
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiCreditResultRequestDto extends AbstractWebApiRequestDto {

    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 受注番号 */
    private Integer orderNo;

    /** クレジット決済ID */
    private String creditPaymentID;

    /** マーチャント取引ID */
    private String tradingID;

    /** 請求金額 */
    private BigDecimal billingPrice;

    /** 与信取得日時 */
    private Timestamp creditDate;

    /** 担当者コード */
    private Integer userID;
    // PDR Migrate Customization to here
}
