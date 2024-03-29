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
 * アンケート回答文字種別：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeReplyValidatePattern implements EnumType {

    /**
     * 制限なし
     */
    NO_LIMIT("制限なし", "0"),

    /**
     * 全角のみ
     */
    ONLY_EM_SIZE("全角のみ", "1"),

    /**
     * 半角英数のみ
     */
    ONLY_AN_CHAR("半角英数のみ", "2"),

    /**
     * 半角英字のみ
     */
    ONLY_A_CHAR("半角英字のみ", "3"),

    /**
     * 半角数字のみ
     */
    ONLY_N_CHAR("半角数字のみ", "4");

    /**
     * @return true = 半角の入力のみ許可
     */
    public boolean isHalfOnly() {
        if (this == ONLY_AN_CHAR || this == ONLY_A_CHAR || this == ONLY_N_CHAR) {
            return true;
        }
        return false;
    }

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeReplyValidatePattern of(String value) {

        HTypeReplyValidatePattern hType = EnumTypeUtil.getEnumFromValue(HTypeReplyValidatePattern.class, value);
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
