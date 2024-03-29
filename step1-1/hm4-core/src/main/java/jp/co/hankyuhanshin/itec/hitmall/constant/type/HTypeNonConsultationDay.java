/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.constant.type;

import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.seasar.doma.Domain;

/**
 * PDR#11 08_データ連携（顧客情報）<br/>
 * <pre>
 * 休診曜日
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeNonConsultationDay implements EnumType {
    // PDR Migrate Customization from here
    /**
     * 日曜日
     */
    SUNDAY("日曜", "0"),

    /**
     * 月曜日
     */
    MONDAY("月曜", "1"),

    /**
     * 火曜日
     */
    TUESDAY("火曜", "2"),

    /**
     * 水曜日
     */
    WEDNESDAY("水曜", "3"),

    /**
     * 木曜日
     */
    THURSDAY("木曜", "4"),

    /**
     * 金曜日
     */
    FRIDAY("金曜", "5"),

    /**
     * 土曜日
     */
    SATURDAY("土曜", "6"),

    /**
     * 祝日
     */
    HOLIDAY("祝日", "7");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeNonConsultationDay of(String value) {

        HTypeNonConsultationDay hType = EnumTypeUtil.getEnumFromValue(HTypeNonConsultationDay.class, value);
        return hType;
    }

    /**
     * ラベル<br/>
     */
    private String label;

    /**
     * 区分値<br/>
     */
    private String value;
    // PDR Migrate Customization to here
}
