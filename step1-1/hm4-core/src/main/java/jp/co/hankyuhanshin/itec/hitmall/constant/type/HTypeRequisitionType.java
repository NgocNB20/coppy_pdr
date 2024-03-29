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
 * 請求書種別
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeRequisitionType implements EnumType {
    // PDR Migrate Customization from here
    /** 請求書種別：同梱 */
    INCLUDE("", "1"),

    /** 請求書種別：別送 */
    SEPARATE("", "2");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeRequisitionType of(String value) {

        HTypeRequisitionType hType = EnumTypeUtil.getEnumFromValue(HTypeRequisitionType.class, value);
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
