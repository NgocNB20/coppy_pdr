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
 * PDR#10 07_データ連携（商品情報）<br/>
 * <pre>
 * 保留フラグ
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypeReserveFlag implements EnumType {
    // PDR Migrate Customization from here
    /**
     * 保留しない
     */
    OFF("保留しない", "0"),

    /**
     * 保留する
     */
    ON("保留する", "1");

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
    public static HTypeReserveFlag of(String value) {

        HTypeReserveFlag hType = EnumTypeUtil.getEnumFromValue(HTypeReserveFlag.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
