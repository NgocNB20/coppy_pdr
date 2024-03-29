/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.coupon;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * クーポン一覧 ModelItemクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
// 2023-renew No24 from here
public class CouponModelItem implements Serializable {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 1L;

    /** クーポンコード */
    private String couponCode;

    /** クーポン名 */
    private String couponName;

    /** 適用条件 */
    private String couponConditions;

    /** 詳細説明 */
    private String couponExplain;

}
// 2023-renew No24 to here
