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
 * 自動承認フラグ定義クラス
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeAutoApprovalFlag implements EnumType {

    /**
     * 自動承認しない
     */
    OFF("自動承認しない", "0"),

    /**
     * 自動承認する
     */
    ON("自動承認する", "1");

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
    public static HTypeAutoApprovalFlag of(String value) {

        HTypeAutoApprovalFlag hType = EnumTypeUtil.getEnumFromValue(HTypeAutoApprovalFlag.class, value);
        return hType;
    }
}
