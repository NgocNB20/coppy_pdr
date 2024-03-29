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
 * 代金引換使用可否
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeCashDeliveryUseFlag implements EnumType {
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
     * doma用ファクトリメソッド
     */
    public static HTypeCashDeliveryUseFlag of(String value) {

        HTypeCashDeliveryUseFlag hType = EnumTypeUtil.getEnumFromValue(HTypeCashDeliveryUseFlag.class, value);
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
