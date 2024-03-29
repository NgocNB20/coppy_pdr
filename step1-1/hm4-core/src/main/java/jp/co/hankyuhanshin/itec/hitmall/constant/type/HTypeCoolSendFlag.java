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
 * クール便フラグ
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeCoolSendFlag implements EnumType {
    // PDR Migrate Customization from here
    /** クール便対象外 */
    OFF("クール便対象外", "0"),

    /** クール便対象 */
    ON("クール便", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeCoolSendFlag of(String value) {

        HTypeCoolSendFlag hType = EnumTypeUtil.getEnumFromValue(HTypeCoolSendFlag.class, value);
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
