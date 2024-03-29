/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;

/**
 * クーポン新規登録ロジックのインタフェースクラス。<br />
 * <pre>
 * クーポン管理画面の登録処理から利用する。
 * </pre>
 *
 * @author Kimura Kanae (itec)
 */
public interface CouponRegistLogic {

    /**
     * クーポン新規登録処理。<br />
     * <pre>
     * クーポン情報を元にクーポンテーブル新規反映。
     * クーポンインデックス新規反映。
     * </pre>
     *
     * @param coupon 登録対象のクーポン
     */
    void execute(CouponEntity coupon, String administratorId);

}
