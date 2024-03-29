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
 * 自動承認フラグ定義クラス
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
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
     * doma用ファクトリメソッド
     */
    public static HTypeAutoApprovalFlag of(String value) {

        HTypeAutoApprovalFlag hType = EnumTypeUtil.getEnumFromValue(HTypeAutoApprovalFlag.class, value);
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
}
