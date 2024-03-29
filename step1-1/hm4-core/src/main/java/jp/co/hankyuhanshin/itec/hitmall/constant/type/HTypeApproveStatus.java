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
 * 承認状態
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeApproveStatus implements EnumType {
    // PDR Migrate Customization from here
    /**
     * 未承認
     */
    OFF("未承認", "0"),

    /**
     * 承認
     */
    ON("承認", "1"),

    /**
     * 否認
     */
    DENIAL("否認", "2");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeApproveStatus of(String value) {

        HTypeApproveStatus hType = EnumTypeUtil.getEnumFromValue(HTypeApproveStatus.class, value);
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
