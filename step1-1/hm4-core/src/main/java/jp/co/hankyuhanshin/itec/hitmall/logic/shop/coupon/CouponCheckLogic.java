/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;

/**
 * クーポン利用制限数チェックLogic<br/>
 *
 * @author s_tsuru
 */
public interface CouponCheckLogic {

    /**
     * 指定したクーポンの利用期間が終了していた場合エラー
     */
    public static final String MSGCD_AFTER_COUPONENDTIME = "LOD000202";

    /**
     * 指定したクーポンがまだ利用開始されていない場合エラー
     */
    public static final String MSGCD_BEFORE_COUPONSTARTTIME = "LOD000203";

    /**
     * 割引対象金額がクーポン適用金額の条件を満たしていない場合エラー
     */
    public static final String MSGCD_NOTFULL_COUPONDISCOUNTCONDITION = "LOD000204";

    /**
     * 新規登録クーポンのIDが既存クーポンと重複した場合エラー
     */
    public static final String MSGCD_REPETITION_COUPONID = "LCP000101";

    /**
     * 登録・更新時に再利用不可期間のクーポンコードと重複した場合エラー
     */
    public static final String MSGCD_REPETITION_COUPONCODE = "LCP000102";

    /**
     * 登録・更新時に現在日時よりも過去に利用開始日時を指定した場合エラー
     */
    public static final String MSGCD_CANNOT_SET_STRATTIME = "LCP000103";

    /**
     * 利用期間中に利用開始日を変更した場合エラー
     */
    public static final String MSGCD_CANNOT_CHANGE_STARTTIME = "LCP000104";

    /**
     * 利用期間中にクーポンコードを変更した場合エラー
     */
    public static final String MSGCD_CANNOT_CHANGE_COUPONCODE = "LCP000105";

    /**
     * 利用期間終了後にクーポンを変更した場合エラー
     */
    public static final String MSGCD_CANNOT_CAHNGE_COUPONDATA = "LCP000106";

    /**
     * 対象商品が重複した場合エラー：PKG-3555-001-L-
     */
    public static final String MSGCD_DUPLICATION_TARGET_GOODS = "PKG-3555-001-L-";

    /**
     * 対象商品が存在しない場合エラー：PKG-3555-002-L-
     */
    public static final String MSGCD_NOT_EXIST_TARGET_GOODS = "PKG-3555-002-L-";

    /**
     * 対象会員が重複した場合エラー：PKG-3555-003-L-
     */
    public static final String MSGCD_DUPLICATION_TARGET_MEMBERS = "PKG-3555-003-L-";

    /**
     * 対象会員が存在しない場合エラー：PKG-3555-004-L-
     */
    public static final String MSGCD_NOT_EXIST_TARGET_MEMBERS = "PKG-3555-004-L-";

    /**
     * ゲストが利用回数の設定されたクーポンを利用しようとした場合エラー：PKG-3555-011-L-
     */
    public static final String MSGCD_GUEST_CANT_USE_COUPON_ERROR = "PKG-3555-011-L-";

    /**
     * クーポン対象商品がカート内商品と一致しなかった場合エラー
     */
    public static final String MSGCD_NO_TARGETGOODS = "PKG-3555-008-L-";

    /**
     * クーポン対象会員が会員と一致しなかった場合エラー
     */
    public static final String MSGCD_NO_TARGETMEMBERS = "PKG-3555-009-L-";

    /**
     * クーポン利用回数を超過した場合エラー
     */
    public static final String MSGCD_OVER_COUPON_USE_CNT = "PKG-3555-010-L-";

    /**
     * Amazon pay-all coupons cannot use message
     */
    public static final String MSGCD_AMAZON_PAY_ALL_AMOUNT_COUPON_USE = "PKG-3773-004-L-";

    /**
     * 新規登録のクーポンチェックを行う。<br />
     * <pre>
     * クーポンID・開始日時・クーポンコードに対しチェックを行う。
     * </pre>
     *
     * @param couponEntity チェック対象のクーポン
     */
    void checkForRegist(CouponEntity couponEntity);

    /**
     * 更新のクーポンチェックを行う。<br />
     * <pre>
     * 開催前：開始日時・クーポンコードに対しチェックを行う。
     * 開催中：開始日時・クーポンコードに対しチェックを行う。
     * クーポンが終了していた場合、エラーメッセージを返す。
     * </pre>
     *
     * @param preUpdateCoupon  更新前のクーポン
     * @param postUpdateCoupon 更新後のクーポン
     */
    void checkForUpdate(CouponEntity preUpdateCoupon, CouponEntity postUpdateCoupon);

    /**
     * 適用可能なクーポンかを判定する<br/>
     *
     * @param entity          クーポンEntity
     * @param receiveOrderDto 受注Dto
     */
    void checkUsableCoupon(CouponEntity entity,
                           ReceiveOrderDto receiveOrderDto,
                           HTypeSiteType siteType,
                           Integer memberInfoSeq);
}
