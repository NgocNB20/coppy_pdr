/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;

/**
 * クーポン情報取得ロジックのインタフェースクラス。<br />
 * <pre>
 * クーポン管理画面の一覧画面で選択されたクーポンの詳細情報を取得する為に利用する。
 * </pre>
 *
 * @author Kimura Kanae (itec)
 */
public interface CouponGetLogic {

    /**
     * クーポン情報を取得する。<br />
     * <pre>
     * クーポンSEQよりクーポン情報を取得する。
     * </pre>
     *
     * @param couponSeq クーポンSEQ
     * @return クーポン
     */
    CouponEntity execute(Integer couponSeq);

}
