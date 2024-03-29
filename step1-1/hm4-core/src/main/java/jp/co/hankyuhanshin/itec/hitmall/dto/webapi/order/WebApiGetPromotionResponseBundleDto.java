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
import java.sql.Timestamp;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携取得結果DTOクラス
 * プロモーション 同梱情報
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetPromotionResponseBundleDto implements Serializable {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 出荷予定日 */
    private Timestamp shipmentDate;

    // 2023-renew No14 from here
    /** 配達指定日 */
    private Timestamp deliveryDesignatedDay;
    // 2023-renew No14 to here

    /** 入荷予定日 */
    private Timestamp stockDate;

    /** 明細番号 */
    private String orderSeq;

    /** 同梱商品 */
    private String bundleGoodsCode;

    /** 同梱数量 */
    private String bundleGoodsCount;

    /** 納品書印字フラグ */
    private String deliverySlipFlag;
    // PDR Migrate Customization to here
}
