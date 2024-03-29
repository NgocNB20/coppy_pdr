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
 * サイトマップ出力フラグ：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeSiteMapFlag implements EnumType {

    /**
     * 対象外
     */
    OFF("対象外", "0"),

    /**
     * 対象
     */
    ON("対象", "1");

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
    public static HTypeSiteMapFlag of(String value) {

        HTypeSiteMapFlag hType = EnumTypeUtil.getEnumFromValue(HTypeSiteMapFlag.class, value);
        return hType;
    }
}
