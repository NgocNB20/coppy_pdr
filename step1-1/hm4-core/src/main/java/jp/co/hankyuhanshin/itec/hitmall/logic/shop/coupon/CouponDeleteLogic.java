/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;

/**
 * クーポン削除ロジックのインタフェースクラス。<br />
 * <pre>
 * クーポン管理画面の削除処理から利用する。
 * </pre>
 *
 * @author Kimura Kanae (itec)
 */
public interface CouponDeleteLogic {

    /**
     * 指定したクーポンSEQのクーポンテーブルの情報を全て削除する。<br />
     * <pre>
     * 指定したクーポンSEQのクーポンインデックステーブルの情報を削除する。
     * </pre>
     *
     * @param coupon 削除対象のクーポン
     * @return 削除件数
     */
    int execute(CouponEntity coupon);

}
