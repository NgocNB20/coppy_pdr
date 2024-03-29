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
 * 医療機器販売区分
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeMedicalEquipmentSalesType implements EnumType {
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
     * doma用ファクトリメソッド
     */
    public static HTypeMedicalEquipmentSalesType of(String value) {

        HTypeMedicalEquipmentSalesType hType =
                        EnumTypeUtil.getEnumFromValue(HTypeMedicalEquipmentSalesType.class, value);
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
