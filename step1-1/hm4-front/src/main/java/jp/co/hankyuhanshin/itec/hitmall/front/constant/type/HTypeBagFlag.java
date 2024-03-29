// PDR Migrate Customization from here
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
 * 手付袋対応フラグ
 *
 * @author hh32100
 */
@Getter
@AllArgsConstructor
public enum HTypeBagFlag implements EnumType {

    /**
     * 設定不可
     */
    OFF("設定不可", "0"),

    /**
     * 設定可
     */
    ON("設定可", "1");

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
    public static HTypeBagFlag of(String value) {

        HTypeBagFlag hType = EnumTypeUtil.getEnumFromValue(HTypeBagFlag.class, value);
        return hType;
    }
}
// PDR Migrate Customization to here
