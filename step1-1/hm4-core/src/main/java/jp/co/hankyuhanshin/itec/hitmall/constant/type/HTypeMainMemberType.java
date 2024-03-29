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
 * 本会員フラグ
 *
 * @author kimura
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeMainMemberType implements EnumType {

    /**
     * 本会員
     */
    MAIN_MENBER("本会員", "0"),

    /**
     * 暫定会員
     */
    PROVISIONAL_MEMBER("暫定会員", "1");

    /**
     *
     * コンストラクタ<br/>
     *
     * @param ordinal Integer
     * @param name String
     * @param value String
     */

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeMainMemberType of(String value) {

        HTypeMainMemberType hType = EnumTypeUtil.getEnumFromValue(HTypeMainMemberType.class, value);
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
