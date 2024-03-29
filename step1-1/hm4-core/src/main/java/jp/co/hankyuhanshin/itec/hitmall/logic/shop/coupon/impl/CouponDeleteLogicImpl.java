/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.coupon.CouponDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.coupon.CouponIndexDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon.CouponDeleteLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * クーポン削除ロジック。
 *
 * @author Kimura Kanae (itec)
 */
@Component
public class CouponDeleteLogicImpl extends AbstractShopLogic implements CouponDeleteLogic {

    /**
     * クーポンDAO
     */
    private final CouponDao couponDao;

    /**
     * クーポンインデックスDAO
     */
    private final CouponIndexDao couponIndexDao;

    @Autowired
    public CouponDeleteLogicImpl(CouponDao couponDao, CouponIndexDao couponIndexDao) {
        this.couponDao = couponDao;
        this.couponIndexDao = couponIndexDao;
    }

    /**
     * クーポン削除処理。
     *
     * @param coupon 削除対象のクーポン
     * @return 削除件数
     */
    @Override
    public int execute(CouponEntity coupon) {

        // クーポンSEQが同じものを全て削除する（変更前の情報も削除する）
        if (couponDao.deleteCoupon(coupon.getCouponSeq(), coupon.getShopSeq()) == 0) {
            // 他の人が削除している場合は削除件数が0となり、クーポンインデックスも削除できないため
            return 0;
        }

        // クーポンインデックス情報を削除する
        return couponIndexDao.deleteCouponIndex(coupon.getCouponSeq(), coupon.getShopSeq());

    }
}
