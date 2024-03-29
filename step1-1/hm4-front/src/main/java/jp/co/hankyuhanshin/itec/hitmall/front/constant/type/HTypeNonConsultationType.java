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
 * 休診区分
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypeNonConsultationType implements EnumType {
    // PDR Migrate Customization from here
    /**
     * AM休診
     */
    AM("AM", "1"),

    /**
     * PM休診
     */
    PM("PM", "2"),

    /**
     * 終日休診
     */
    ALLDAY("終日", "3"),

    /**
     * 無休
     */
    NOHOLIDAY("無休", "4");

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
    public static HTypeNonConsultationType of(String value) {

        HTypeNonConsultationType hType = EnumTypeUtil.getEnumFromValue(HTypeNonConsultationType.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
