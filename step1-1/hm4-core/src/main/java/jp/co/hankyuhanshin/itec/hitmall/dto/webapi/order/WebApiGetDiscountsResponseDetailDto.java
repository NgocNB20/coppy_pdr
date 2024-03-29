/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * 割引適用結果取得 詳細情報
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebApiGetDiscountsResponseDetailDto implements Serializable {

    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 申込商品 */
    private String goodsCode;

    /** 適用割引 */
    private String saleType;

    /** 数量割引グループコード */
    private String saleGroupCode;

    /** 割引価格 */
    private BigDecimal salePrice;

    /** 数量 */
    private String quantity;

    /** セールコード */
    private String saleCode;

    /** 備考 */
    private String note;

    /** 注意事項 */
    private String hints;

    /** 表示順 */
    private String orderDisplay;

    /**
     * 表示順用ソートキーを返却します。
     *
     * @return 表示順用ソートキー(orderDisplay + goodsCode)
     */
    public String getSortKey() {
        return this.orderDisplay + this.goodsCode;
    }
    // PDR Migrate Customization to here
}
