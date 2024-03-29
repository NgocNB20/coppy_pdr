/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.utility;

import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.settlement.OrderSettlementEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * クーポン用のUtilityクラス
 *
 * @author EntityGenerator
 */
@Component
public class CouponUtility {

    // 2023-renew No24 from here
    /**
     * クーポン適用あり時のメッセージリスト
     */
    protected static final List<String> COUPON_INFO_MESSAGE_LIST = new ArrayList<>();

    /**
     * クーポン適用なし時のメッセージリスト
     */
    protected static final List<String> COUPON_ERROR_MESSAGE_LIST = new ArrayList<>();

    /** メッセージ・ステータスの初期設定 */
    static {
        /* クーポン適用あり時のメッセージリスト */
        COUPON_INFO_MESSAGE_LIST.add("PDR-0436-014-A-"); //【値引きデータパターン No.8】クーポン・プロモーションどちらも適用（プレゼント）
        COUPON_INFO_MESSAGE_LIST.add("PDR-0436-008-A-"); //【値引きデータパターン No.9】クーポン・プロモーションどちらも適用

        /* クーポン適用なし時のメッセージリスト */
        COUPON_ERROR_MESSAGE_LIST.add("PDR-0436-006-A-"); //【値引きデータパターン No.4】クーポン未適用
        COUPON_ERROR_MESSAGE_LIST.add("PDR-0436-011-A-"); //【値引きデータパターン No.5】プロモーションのみ適用（クーポン未適用）
        COUPON_ERROR_MESSAGE_LIST.add("PDR-0436-007-A-"); //【値引きデータパターン No.6】クーポン不可
        COUPON_ERROR_MESSAGE_LIST.add("PDR-0436-009-A-"); //【値引きデータパターン No.7】プロモーションのみ適用（クーポン不可）
        COUPON_ERROR_MESSAGE_LIST.add("PDR-2023RENEW-24-001-"); // API処理結果メッセージをそのまま返却
    }

    /**
     * クーポン適用あり時のメッセージリストに該当するかどうか
     *
     * @param messageId メッセージID
     * @return true 該当する, false 該当しない
     */
    public boolean isCouponInfoMessageList(String messageId) {
        return COUPON_INFO_MESSAGE_LIST.contains(messageId);
    }

    /**
     * クーポン適用なし時のメッセージリストに該当するかどうか
     *
     * @param messageId メッセージID
     * @return true 該当する, false 該当しない
     */
    public boolean isCouponErrorMessageList(String messageId) {
        return COUPON_ERROR_MESSAGE_LIST.contains(messageId);
    }

    // 2023-renew No24 to here

    /**
     * クーポン割引を考慮して決済金額を算出する。<br />
     * ※決済金額とは手数料算出の基になる金額。<br />
     * 商品金額合計＋送料＋その他追加料金+決済手数料+消費税－クーポン割引額
     *
     * @param receiveOrderDto 受注Dto
     * @return 決済金額
     */
    public BigDecimal getSettlementCharge(ReceiveOrderDto receiveOrderDto) {
        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();

        // 割引前受注金額（商品金額合計＋送料＋その他追加料金+決済手数料+消費税）
        BigDecimal beforeDiscountOrderPrice = orderSettlementEntity.getBeforeDiscountOrderPrice();
        // クーポン割引額
        BigDecimal couponDiscountPrice = orderSettlementEntity.getCouponDiscountPrice();
        // 決済金額
        return beforeDiscountOrderPrice.subtract(couponDiscountPrice);
    }

}
