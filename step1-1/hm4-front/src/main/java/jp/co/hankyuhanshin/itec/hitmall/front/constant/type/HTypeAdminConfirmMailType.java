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
 * 確認メール機能ID
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeAdminConfirmMailType implements EnumType {

    /**
     * パスワード再発行 ※ラベル未使用
     */
    PASSWORD_REISSUE("", "0");

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
    public static HTypeAdminConfirmMailType of(String value) {

        HTypeAdminConfirmMailType hType = EnumTypeUtil.getEnumFromValue(HTypeAdminConfirmMailType.class, value);
        return hType;
    }
}
