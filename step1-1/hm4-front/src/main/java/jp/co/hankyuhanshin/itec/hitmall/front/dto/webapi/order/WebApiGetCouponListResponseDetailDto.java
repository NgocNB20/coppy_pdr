/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 *
 * WEB-API連携取得結果DTO
 * 利用可能クーポン一覧取得 詳細情報
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
// 2023-renew No24 from here
public class WebApiGetCouponListResponseDetailDto implements Serializable {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** クーポン番号 */
    private String couponNo;

    /** クーポン名 */
    private String couponName;

    /** 適用条件 */
    private String couponConditions;

    /** 詳細説明 */
    private String couponExplain;

}
// 2023-renew No24 to here
