/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.coupon.CouponDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon.CouponCodeCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.CouponUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * クーポンコード重複チェックロジックの実装クラス。
 *
 * @author k.harada (itec)
 */
@Component
public class CouponCodeCheckLogicImpl extends AbstractShopLogic implements CouponCodeCheckLogic {

    /**
     * クーポンDAO
     */
    private final CouponDao couponDao;

    /**
     * クーポン関連ユーティリティクラス
     */
    private final CouponUtility couponUtility;

    /**
     * 日付関連ユーティリティクラス
     */
    private final DateUtility dateUtility;

    @Autowired
    public CouponCodeCheckLogicImpl(CouponDao couponDao, CouponUtility couponUtility, DateUtility dateUtility) {
        this.couponDao = couponDao;
        this.couponUtility = couponUtility;
        this.dateUtility = dateUtility;
    }

    /**
     * クーポンコード登録可否チェック。<br />
     * ※ バックのクーポンコード自動生成の際に使用。
     *
     * @param couponCode クーポンコード
     * @return 登録可能な場合trueを返す
     */
    @Override
    public boolean execute(String couponCode) {

        // クーポン再利用可能期間をプロパティファイルから取得する
        int amountDays = couponUtility.getCouponCodeCantRecycleTerm();

        // 現在より再利用可能期間を引いた日付を求める
        Timestamp reUseEndDate = dateUtility.getAmountDayTimestamp(amountDays, false, dateUtility.getCurrentTime());

        // page情報のクーポンコードが重複不可期間の既存クーポンコードに使用されていないかをチェックする
        Integer shopSeq = 1001;

        if (couponDao.checkCouponCode(shopSeq, couponCode, reUseEndDate) == 0) {
            // 一致したものがないのでtrueを返す
            return true;
        }
        // 一致したのでfalseを返す
        return false;

    }

    /**
     * クーポンコード登録可否チェック。<br /><br />
     * 過去に終了したクーポン、未来に開始されるクーポンに再利用不可期間（日）を<br />
     * 考慮して同一クーポンコードが存在する場合はエラーとしてfalseを返す。<br />
     *
     * @param couponEntity クーポン情報
     * @return 登録可能な場合trueを返す
     */
    @Override
    public boolean execute(CouponEntity couponEntity) {

        // クーポンコード
        String couponCode = couponEntity.getCouponCode();
        // クーポンSEQ(登録の場合はNULL)
        Integer couponSeq = couponEntity.getCouponSeq();
        // クーポン開始日時
        Timestamp coponStartTime = couponEntity.getCouponStartTime();
        // クーポン終了日時
        Timestamp coponEndTime = couponEntity.getCouponEndTime();
        // 再利用不可期間（日）
        int reUseTermValue = couponUtility.getCouponCodeCantRecycleTerm().intValue();
        // 日時にそれぞれ再利用不可期間を適用する。
        Timestamp reUsePastDate = dateUtility.getAmountDayTimestamp(reUseTermValue, false, coponStartTime);
        Timestamp reUseFutureDate = dateUtility.getAmountDayTimestamp(reUseTermValue, true, coponEndTime);

        // page情報のクーポンコードが重複不可期間の既存クーポンコードに使用されていないかをチェックする
        Integer shopSeq = 1001;

        if (couponDao.checkCouponCodeByCouponTime(shopSeq, couponCode, reUsePastDate, reUseFutureDate, couponSeq)
            == 0) {
            // ０件の場合はOKとしてtrueを返す
            return true;
        }
        // 数件登録されているのでアウト！
        return false;
    }
}
