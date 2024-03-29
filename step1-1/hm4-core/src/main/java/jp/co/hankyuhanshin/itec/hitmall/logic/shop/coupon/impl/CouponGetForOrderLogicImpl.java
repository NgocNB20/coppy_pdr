/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.coupon.CouponDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon.CouponCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon.CouponGetForOrderLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 注文時クーポン取得ロジックの実装クラス。
 *
 * @author Kimura Kanae (itec)
 */
@Component
public class CouponGetForOrderLogicImpl extends AbstractShopLogic implements CouponGetForOrderLogic {

    /**
     * クーポンDAO
     */
    private final CouponDao couponDao;

    /**
     * クーポンチェックLogic
     */
    private final CouponCheckLogic couponCheckLogic;

    @Autowired
    public CouponGetForOrderLogicImpl(CouponDao couponDao, CouponCheckLogic couponCheckLogic) {
        this.couponDao = couponDao;
        this.couponCheckLogic = couponCheckLogic;
    }

    /**
     * 指定クーポンコードのクーポン情報を取得する。
     *
     * @param couponCode      クーポンコード
     * @param receiveOrderDto 受注DTO
     * @return クーポンエンティティ
     */
    @Override
    public CouponEntity execute(String couponCode,
                                ReceiveOrderDto receiveOrderDto,
                                HTypeSiteType siteType,
                                Integer memberInfoSeq) {

        // クーポンコードを指定しクーポン情報を取得する
        // どれだけ重複したクーポンコードがあっても最新の1件しか取得しない
        CouponEntity entity = couponDao.getCouponByCouponCode(couponCode);

        // クーポン情報が取得できなかった場合はエラーとする
        if (entity == null) {
            throwMessage(MSGCD_NO_TARGETCOUPON);
        }
        // チェック
        couponCheckLogic.checkUsableCoupon(entity, receiveOrderDto, siteType, memberInfoSeq);

        // エラーがなければ受注情報に受注クーポン情報をセットする
        // エラーがあるときは都度スローするので、エラー有無の判定はしない
        return entity;

    }
}
