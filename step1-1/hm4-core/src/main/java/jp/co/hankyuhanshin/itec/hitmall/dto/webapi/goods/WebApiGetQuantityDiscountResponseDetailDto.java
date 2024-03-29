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
import java.sql.Timestamp;
import java.util.List;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携取得結果DTOクラス
 * 数量割引情報取得 詳細情報
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetQuantityDiscountResponseDetailDto implements Serializable {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 申込商品 */
    private String goodsCode;

    /** 価格リスト */
    private List<WebApiGetQuantityDiscountResponsePriceDto> priceList;

    /** 割引価格リスト */
    private List<WebApiGetQuantityDiscountResponseSalePriceDto> salePriceList;

    /** 顧客セール価格リスト */
    private List<WebApiGetQuantityDiscountResponseCustomerSalePriceDto> customerSalePriceList;

    /** 数量割引適用有無 */
    private String saleFlag;

    /** 時価フラグ */
    private String marketPriceFlag;

    /** 顧客セール割引適用有無 */
    private Integer customerSaleFlag;
    // PDR Migrate Customization to here

    // 2023-renew No5 from here
    /** セール終了日 */
    private Timestamp saleEndDay;
    // 2023-renew No5 to here
}
