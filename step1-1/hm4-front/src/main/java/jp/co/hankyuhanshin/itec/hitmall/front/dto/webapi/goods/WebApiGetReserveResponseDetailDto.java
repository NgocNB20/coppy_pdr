/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods;

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
 * 取りおき情報取得 詳細情報
 * </pre>
 *
 * @author satoh
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetReserveResponseDetailDto implements Serializable {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 商品コード */
    private String goodsCode;

    /** 取りおき可否 */
    private String reserveFlag;

    /** 適用割引 */
    private String discountFlag;
    // PDR Migrate Customization to here

    // 2023-renew No14 from here
    /** 予約可能開始日 */
    private Timestamp possibleReserveFromDay;

    /** 予約可能終了日 */
    private Timestamp possibleReserveToDay;
    // 2023-renew No14 to here
}
