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
 * PDR#037 住所情報の取り込み<br/>
 *
 * <pre>
 * WEB-API連携 配送情報取得 お届け可否
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypeDeliveryFlag implements EnumType {
    // PDR Migrate Customization from here
    /** 指定不可 */
    OFF("指定不可", "0"),

    /** 指定可 */
    ON("指定可", "1");

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
    public static HTypeDeliveryFlag of(String value) {

        HTypeDeliveryFlag hType = EnumTypeUtil.getEnumFromValue(HTypeDeliveryFlag.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
