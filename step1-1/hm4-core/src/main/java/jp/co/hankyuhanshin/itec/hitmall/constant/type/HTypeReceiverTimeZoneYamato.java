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
 * PDR#026 休業日の配送制御<br/>
 *
 * <pre>
 * 配達指定時間ヤマト
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeReceiverTimeZoneYamato implements EnumType {
    // PDR Migrate Customization from here
    /** 指定なし */
    UNSPECIFIED("指定なし", "0"),

    /** 午前中 */
    RECEIVERTIME_AM("午前中", "1"),

    /** 12時～14時 */
    RECEIVERTIME_12TO14("12時～14時", "2"),

    /** 14時～16時 */
    RECEIVERTIME_14TO16("14時～16時", "3"),

    /** 16時～18時 */
    RECEIVERTIME_16TO18("16時～18時", "4"),

    /** 18時～20時 */
    RECEIVERTIME_18TO20("18時～20時", "5"),

    /** 20時～21時 */
    RECEIVERTIME_20TO21("19時～21時", "6");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeReceiverTimeZoneYamato of(String value) {

        HTypeReceiverTimeZoneYamato hType = EnumTypeUtil.getEnumFromValue(HTypeReceiverTimeZoneYamato.class, value);
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
