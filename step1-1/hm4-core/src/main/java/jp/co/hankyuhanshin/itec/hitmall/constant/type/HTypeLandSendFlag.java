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
 * PDR#013 09_データ連携（受注データ）<br/>
 *
 * <pre>
 * 陸送商品フラグ
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeLandSendFlag implements EnumType {
    // PDR Migrate Customization from here
    /** 通常商品 */
    OFF("通常商品", "0"),

    /** 陸送商品 */
    ON("陸送便", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeLandSendFlag of(String value) {

        HTypeLandSendFlag hType = EnumTypeUtil.getEnumFromValue(HTypeLandSendFlag.class, value);
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
