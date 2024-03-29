/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * WEB-API連携取得結果DTOクラス
 * 数量割引情報取得 顧客セール価格情報
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetQuantityDiscountResponseCustomerSalePriceDto implements Serializable {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 割引価格 */
    private BigDecimal customerSalePrice;

    /** 割引数量閾値 */
    private String customerSaleLevel;
    // PDR Migrate Customization to here
}
