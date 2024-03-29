/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.constant.type;

import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携用 支払方法区分
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypePaymentMethod implements EnumType {
    // PDR Migrate Customization from here
    /**
     * コンビニ/郵便振込み
     */
    CONVENIENCE("", "1"),

    /**
     * 代金引換
     */
    CASH_ON_DELIVERY("", "2"),

    /**
     * クレジットカード
     */
    CREDIT_CARD("", "3"),

    /**
     * 月締め請求
     */
    MONTHLY_BILLING("", "8"),

    // 2023-renew No32 from here
    /**
     * 請求なし
     */
    FREE("", "9"),
    // 2023-renew No32 to here

    /**
     * 口座自動引落
     */
    AUTOMATIC_WITHDRAWAL("", "10");

    /**
     * ラベル<br/>
     */
    private String label;
    /**
     * 区分値<br/>
     */
    private String value;

    /**
     * doma用ファクトリメソッド
     */
    public static HTypePaymentMethod of(String value) {

        HTypePaymentMethod hType = EnumTypeUtil.getEnumFromValue(HTypePaymentMethod.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
