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

/**
 *
 * WEB-API連携取得結果DTO
 * 前回支払方法取得 詳細情報
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
// 2023-renew No14 from here
public class WebApiGetBeforePaymentResponseDetailDto implements Serializable {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 前回支払方法 */
    private String beforePaymentType;

    /** 決済ID */
    private String paymentId;

}
// 2023-renew No14 to here
