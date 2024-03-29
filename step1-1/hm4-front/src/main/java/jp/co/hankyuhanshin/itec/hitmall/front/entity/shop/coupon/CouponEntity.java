/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.coupon;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * クーポンエンティティクラス。
 *
 * @author EntityGenerator
 */
@Data
@Component
@Scope("prototype")
public class CouponEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * クーポンSEQ（FK）
     */
    private Integer couponSeq;

    /**
     * クーポン枝番
     */
    private Integer couponVersionNo;

    /**
     * ショップSEQ
     */
    private Integer shopSeq;

    /**
     * クーポンID
     */
    private String couponId;

    /**
     * クーポン名
     */
    private String couponName;

    /**
     * クーポン表示名PC
     */
    private String couponDisplayNamePC;

    /**
     * クーポン表示名モバイル
     */
    private String couponDisplayNameMB;

    /**
     * クーポンコード
     */
    private String couponCode;

    /**
     * クーポン開始日時
     */
    private Timestamp couponStartTime;

    /**
     * クーポン終了日時
     */
    private Timestamp couponEndTime;

    /**
     * 割引金額
     */
    private BigDecimal discountPrice;

    /**
     * クーポン適用金額
     */
    private BigDecimal discountLowerOrderPrice;

    /**
     * 利用回数
     */
    private Integer useCountLimit = 0;

    /**
     * 対象商品
     */
    private String targetGoods;

    /**
     * 対象会員
     */
    private String targetMembers;

    /**
     * 管理用メモ
     */
    private String memo;

    /**
     * 管理者ID
     */
    private String administratorId;

    /**
     * 登録日時
     */
    private Timestamp registTime;

    /**
     * 更新日時
     */
    private Timestamp updateTime;

}
