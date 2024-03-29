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
 * PDR#11 08_データ連携（顧客情報）<br/>
 * <pre>
 * オンライン登録フラグ
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeOnlineRegistFlag implements EnumType {
    // PDR Migrate Customization from here
    /**
     * 非EC会員
     */
    OFF("TEL・FAXのみ会員", "0"),

    /**
     * EC会員
     */
    ON("EC会員", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeOnlineRegistFlag of(String value) {

        HTypeOnlineRegistFlag hType = EnumTypeUtil.getEnumFromValue(HTypeOnlineRegistFlag.class, value);
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
    // PDR Migrate Customization to here
}
