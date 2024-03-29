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
 * PDR#037 住所録情報の取り込み<br/>
 *
 * <pre>
 * 住所録承認フラグ
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeAddressBookApproveFlag implements EnumType {
    // PDR Migrate Customization from here
    /** 未承認 */
    OFF("未承認", "0"),

    /** 承認 */
    ON("承認", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeAddressBookApproveFlag of(String value) {

        HTypeAddressBookApproveFlag hType = EnumTypeUtil.getEnumFromValue(HTypeAddressBookApproveFlag.class, value);
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
