/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.coupon.CouponSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;

import java.util.List;

/**
 * クーポン検索ロジックのインタフェースクラス。<br />
 * <pre>
 * クーポン検索画面で利用する。
 * </pre>
 *
 * @author Kimura Kanae (itec)
 */
public interface CouponSearchResultListgetLogic {

    /**
     * クーポン検索結果を取得する。<br />
     * <pre>
     * 検索条件を元に、クーポン情報を取得する。
     * </pre>
     *
     * @param condition 検索条件
     * @return クーポンリスト
     */
    List<CouponEntity> execute(CouponSearchForDaoConditionDto condition);

}
