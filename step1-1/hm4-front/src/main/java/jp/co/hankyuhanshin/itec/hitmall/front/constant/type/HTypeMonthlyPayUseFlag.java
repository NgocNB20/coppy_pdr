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
 * PDR#11 08_データ連携（顧客情報）<br/>
 * <pre>
 * 月締請求使用可否
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypeMonthlyPayUseFlag implements EnumType {
    // PDR Migrate Customization from here
    /**
     * 使用不可(OFF)
     */
    OFF("使用不可", "0"),

    /**
     * 使用可能(ON)
     */
    ON("使用可能", "1");

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
    public static HTypeMonthlyPayUseFlag of(String value) {

        HTypeMonthlyPayUseFlag hType = EnumTypeUtil.getEnumFromValue(HTypeMonthlyPayUseFlag.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
