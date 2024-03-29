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
 * 医薬品・注射針販売区分
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypeDrugSalesType implements EnumType {
    // PDR Migrate Customization from here

    // 2023-renew No85-1 from here
    /**
     * 販売不可
     */
    SALEOFF("販売不可", "0"),

    /**
     * 販売可能
     */
    SALEON("販売可能", "1"),

    /**
     * 販売可能（施設内利用のみ）
     */
    ONLY_FACILITY_SALEON("販売可能（施設内利用のみ）", "2"),

    /**
     * 販売可能（目的外保証不可）
     */
    OUTSIDE_SALEON("販売可能（目的外保証不可）", "3");
    // 2023-renew No85-1 to here

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
    public static HTypeDrugSalesType of(String value) {

        HTypeDrugSalesType hType = EnumTypeUtil.getEnumFromValue(HTypeDrugSalesType.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
