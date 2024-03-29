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
 * 利用可能クーポン取得ロジックのインタフェースクラス。
 *
 * @author Kimura Kanae (itec)
 */
public interface CouponGetForOrderLogic {

    /**
     * 一致するクーポンコードが存在しなかった場合エラー
     */
    public static final String MSGCD_NO_TARGETCOUPON = "LOD000201";

    /**
     * 渡されたクーポンコードを利用できるかを判断する。<br />
     * <pre>
     * 判定結果が利用可能である場合クーポン情報を返す。
     * 注文・新規受注のクーポン適用判定に利用する。
     * クーポンコードが利用できなかった場合エラーを投げる。
     * </pre>
     *
     * @param couponCode      クーポンコード
     * @param receiveOrderDto 受注DTO
     * @return クーポン
     */
    CouponEntity execute(String couponCode,
                         ReceiveOrderDto receiveOrderDto,
                         HTypeSiteType siteType,
                         Integer memberInfoSeq);

}
