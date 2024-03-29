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
 *
 * WEB-API連携リクエストDTOクラス
 * 出荷予定日取得 詳細情報
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
@Scope("prototype")
@Data
// 2023-renew No14 from here
public class WebApiGetShipmentDateRequestDetailDto implements Serializable {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 申込商品 */
    private String goodsCode;

    /** 配達指定日 */
    private Timestamp deliveryDesignatedDay;

    /** 配達指定時間 */
    private Integer deliveryDesignatedTimeCode;

}
// 2023-renew No14 to here
