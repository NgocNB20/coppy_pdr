/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;

/**
 * クーポン更新ロジックのインタフェースクラス。<br />
 * <pre>
 * クーポン管理画面の更新処理から利用する。
 * </pre>
 *
 * @author Kimura Kanae (itec)
 */
public interface CouponUpdateLogic {

    /**
     * 修正対象のクーポンが事前に修正されていた場合エラー（排他）
     */
    public static final String MSGCD_EXCLUSION_ERROR = "LCP000201";

    /**
     * クーポン更新処理。<br />
     * <pre>
     * クーポン情報を元にクーポンテーブルに新規反映。
     * クーポンインデックスに更新反映。
     * </pre>
     *
     * @param coupon 更新対象クーポン
     */
    void execute(CouponEntity coupon);
}
