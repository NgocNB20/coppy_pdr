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

import java.util.List;

/**
 *
 * WEB-API連携リクエストDTOクラス
 * 出荷予定日取得
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
@Scope("prototype")
@Data
// 2023-renew No14 from here
public class WebApiGetShipmentDateRequestDto extends AbstractWebApiRequestDto {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 注文者顧客番号 */
    private Integer orderCustomerNo;

    /** 配送先顧客番号 */
    private Integer deliveryCustomerNo;

    /** 配送先郵便番号 */
    private String deliveryZipcode;

    /** 配送会社コード */
    private String deliveryCompanyCode;

    /** 情報 */
    private List<WebApiGetShipmentDateRequestDetailDto> info;

}
// 2023-renew No14 to here
