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
 * <pre>
 * 経理区分
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum HTypeAccountingType implements EnumType {
    // PDR Migrate Customization from here
    /**
     * 顧客
     */
    CUSTOMER("顧客", "1"),

    /**
     * 督促顧客
     */
    PRODDING_CUSTOMER("督促顧客", "2"),

    /**
     * 経理要注意顧客
     */
    ACCOUNT_IMPORTANT_CUSTOMER("経理要注意顧客", "3"),

    /**
     * 貸倒見込顧客
     */
    DEBT_EXPECT_CUSTOMER("貸倒見込顧客", "4"),

    /**
     * 貸倒顧客
     */
    DEBT_CUSTOMER("貸倒顧客", "5"),

    /**
     * 携帯番号登録顧客
     */
    MOBILE_REGIST_CUSTOMER("携帯番号登録顧客", "6"),

    /**
     * 督促対象外（督促停止）
     */
    EXCLUDED_COLLECTION("督促対象外（督促停止）", "7");

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
    public static HTypeAccountingType of(String value) {

        HTypeAccountingType hType = EnumTypeUtil.getEnumFromValue(HTypeAccountingType.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
