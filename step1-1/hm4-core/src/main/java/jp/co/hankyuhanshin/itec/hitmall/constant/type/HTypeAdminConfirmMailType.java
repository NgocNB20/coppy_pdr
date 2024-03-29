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
 * 確認メール機能ID
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeAdminConfirmMailType implements EnumType {

    /**
     * パスワード再発行 ※ラベル未使用
     */
    PASSWORD_REISSUE("", "0");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeAdminConfirmMailType of(String value) {

        HTypeAdminConfirmMailType hType = EnumTypeUtil.getEnumFromValue(HTypeAdminConfirmMailType.class, value);
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
