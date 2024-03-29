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
 * PDR#006 03_取りおきサービス<br/>
 *
 * <pre>
 * 取りおきフラグ
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypeReserveDeliveryFlag implements EnumType {
    // PDR Migrate Customization from here
    /** 取りおき不可 */
    OFF("", "0"),

    /** 取りおき可 */
    ON("", "1");

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
    public static HTypeReserveDeliveryFlag of(String value) {

        HTypeReserveDeliveryFlag hType = EnumTypeUtil.getEnumFromValue(HTypeReserveDeliveryFlag.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
