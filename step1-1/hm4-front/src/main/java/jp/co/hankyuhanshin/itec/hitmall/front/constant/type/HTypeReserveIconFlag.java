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
 * PDR#004 01_販売可能商品の制御<br/>
 *
 * <pre>
 * お取りおきアイコンフラグ
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypeReserveIconFlag implements EnumType {
    // PDR Migrate Customization from here
    /** － */
    OFF("-", "0"),

    /** ○ */
    ON("○", "1");

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
    public static HTypeReserveIconFlag of(String value) {

        HTypeReserveIconFlag hType = EnumTypeUtil.getEnumFromValue(HTypeReserveIconFlag.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
