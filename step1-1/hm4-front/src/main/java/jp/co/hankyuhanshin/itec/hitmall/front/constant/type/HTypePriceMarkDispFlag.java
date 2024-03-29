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
 * 価格記号表示フラグ
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypePriceMarkDispFlag implements EnumType {
    // PDR Migrate Customization from here
    /** 表示しない */
    OFF("表示しない", "0"),

    /** 表示する */
    ON("表示する", "1");

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
    public static HTypePriceMarkDispFlag of(String value) {

        HTypePriceMarkDispFlag hType = EnumTypeUtil.getEnumFromValue(HTypePriceMarkDispFlag.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
