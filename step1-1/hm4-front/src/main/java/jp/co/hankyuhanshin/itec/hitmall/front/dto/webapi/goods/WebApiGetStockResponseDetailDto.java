/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleControlType;
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
 * 在庫数取得 詳細情報
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetStockResponseDetailDto implements Serializable {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 商品コード */
    private String goodsCode;

    /** 在庫数 */
    private BigDecimal stockQuantity;

    /** 販売可否判定結果 */
    private Integer saleYesNo;

    /** 販売不可メッセージ */
    private String saleNgMessage;

    /** 入荷予定日 */
    private Timestamp stockDate;

    /** 入荷次第お届け可否 */
    private String deliveryYesNo;

    // 2023-renew No11 from here
    /** 販売制御区分 */
    private HTypeSaleControlType saleControl;
    // 2023-renew No11 to here

    // PDR Migrate Customization to here
}
