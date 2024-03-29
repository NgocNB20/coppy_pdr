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
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携取得結果DTOクラス
 * 商品価格情報取得 詳細情報
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetPriceResponseDetailDto implements Serializable {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 申込商品 */
    private String goodsCode;

    /// 2023-renew AddNo5 from here
    /** 価格（最低） */
    private BigDecimal priceLow;

    /** 価格（最高） */
    private BigDecimal priceHight;

    /** セール価格（最低） */
    private BigDecimal salePriceLow;

    /** セール価格（最高） */
    private BigDecimal salePriceHight;
    // 2023-renew AddNo5 to here

    /** セールコメント */
    private String saleComment;

    // 2023-renew No11,33,75,112 from here
    /** 販売制御区分 */
    private Integer saleControl;
    // 2023-renew No11,33,75,112 to here
    // PDR Migrate Customization to here
}
