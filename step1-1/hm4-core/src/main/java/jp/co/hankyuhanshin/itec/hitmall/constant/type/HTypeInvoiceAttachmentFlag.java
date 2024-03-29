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
 * 納品書添付フラグ
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeInvoiceAttachmentFlag implements EnumType {

    /**
     * 納品書不要
     */
    OFF("不要", "0"),

    /**
     * 納品書必要
     */
    ON("必要", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeInvoiceAttachmentFlag of(String value) {

        HTypeInvoiceAttachmentFlag hType = EnumTypeUtil.getEnumFromValue(HTypeInvoiceAttachmentFlag.class, value);
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
