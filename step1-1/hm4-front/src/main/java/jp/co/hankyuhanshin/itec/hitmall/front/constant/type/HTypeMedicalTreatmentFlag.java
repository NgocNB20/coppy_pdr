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
 * PDR#429 1.7次 基幹リニューアル対応 【要望No.6,7,8】　フロント会員更新追加<br/>
 * <pre>
 * 診療内容
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypeMedicalTreatmentFlag implements EnumType {
    // PDR Migrate Customization from here
    /**
     * 対象外(OFF)
     */
    OFF("ー", "0"),

    /**
     * 対象(ON)
     */
    ON("〇", "1");

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
    public static HTypeMedicalTreatmentFlag of(String value) {

        HTypeMedicalTreatmentFlag hType = EnumTypeUtil.getEnumFromValue(HTypeMedicalTreatmentFlag.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
