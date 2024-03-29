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
 * 会員状態
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeMemberInfoStatus implements EnumType {

    /**
     * 入会:0
     */
    ADMISSION("入会", "0"),

    /**
     * 退会:1
     */
    REMOVE("退会", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeMemberInfoStatus of(String value) {

        HTypeMemberInfoStatus hType = EnumTypeUtil.getEnumFromValue(HTypeMemberInfoStatus.class, value);
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
