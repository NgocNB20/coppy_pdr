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
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携リクエストDTOクラス
 * 消費税率取得
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetConsumptionTaxRateRequestDto extends AbstractWebApiRequestDto {
    // PDR Migrate Customization from here
    /** 送料取得用コード */
    public static final String CARRIAGE_CODE = "#99999999999";

    /** プロモーション値引取得用コード */
    public static final String PROMO_DISCOUNT_CODE = "#88888888888";

    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 申込商品 (複数パイプ区切り) */
    private String goodsCode;
    // PDR Migrate Customization to here
}
