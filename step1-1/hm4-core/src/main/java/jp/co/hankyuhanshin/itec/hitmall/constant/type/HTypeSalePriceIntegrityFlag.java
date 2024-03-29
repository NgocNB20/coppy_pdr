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
 * PDR#10 07_データ連携（商品情報）<br/>
 * <pre>
 * セール価格整合性フラグ
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeSalePriceIntegrityFlag implements EnumType {
    // PDR Migrate Customization from here
    /** 整合 */
    MATCH("整合", "0"),

    /** 不整合 */
    MISMATCH("不整合", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeSalePriceIntegrityFlag of(String value) {

        HTypeSalePriceIntegrityFlag hType = EnumTypeUtil.getEnumFromValue(HTypeSalePriceIntegrityFlag.class, value);
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
