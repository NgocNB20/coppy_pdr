/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order;

import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.AbstractWebApiRequestDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * PDR#015 12_WebAPI
 *
 * <pre>
 * WEB-API連携リクエストDTOクラス
 * 配送情報取得
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetDeliveryInformationRequestDto extends AbstractWebApiRequestDto {
    // PDR Migrate Customization from here
    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 注文者顧客番号
     */
    private Integer orderCustomerNo;

    /**
     * 配送先顧客番号
     */
    private Integer deliveryCustomerNo;

    /**
     * 配送先郵便番号
     */
    private String deliveryZipcode;

    /**
     * 申込商品
     */
    private String goodsCode;
    // PDR Migrate Customization to here
}
