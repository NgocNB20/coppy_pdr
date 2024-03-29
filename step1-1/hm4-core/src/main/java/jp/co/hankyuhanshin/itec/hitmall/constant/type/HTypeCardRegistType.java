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
 * クレジットカード情報保持種別
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeCardRegistType implements EnumType {

    /**
     * カード情報未登録 ※ラベル未使用
     */
    UNREGISTERED("", "0"),

    /**
     * カード情報登録済 ※ラベル未使用
     */
    REGISTERED("", "1"),

    /**
     * カード情報認証済 ※ラベル未使用
     */
    AUTHENTICATED("", "2");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeCardRegistType of(String value) {

        HTypeCardRegistType hType = EnumTypeUtil.getEnumFromValue(HTypeCardRegistType.class, value);
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
