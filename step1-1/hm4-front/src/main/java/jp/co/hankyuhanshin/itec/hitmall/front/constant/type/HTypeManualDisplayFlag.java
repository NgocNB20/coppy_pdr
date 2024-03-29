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
 * 手動表示フラグ
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeManualDisplayFlag implements EnumType {

    /**
     * 手動表示しない
     */
    OFF("手動表示しない", "0"),

    /**
     * 手動表示する
     */
    ON("手動表示する", "1");

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
    public static HTypeManualDisplayFlag of(String value) {

        HTypeManualDisplayFlag hType = EnumTypeUtil.getEnumFromValue(HTypeManualDisplayFlag.class, value);
        return hType;
    }
}
