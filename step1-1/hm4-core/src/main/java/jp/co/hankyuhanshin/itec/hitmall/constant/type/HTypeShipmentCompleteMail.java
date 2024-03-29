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
 * 出荷メール送信区分：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeShipmentCompleteMail implements EnumType {

    /**
     * 送信 ※ラベル未使用
     */
    MAIL_SEND("", "1"),

    /**
     * 未送信 ※ラベル未使用
     */
    MAIL_NOT_SEND("", "0");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeShipmentCompleteMail of(String value) {

        HTypeShipmentCompleteMail hType = EnumTypeUtil.getEnumFromValue(HTypeShipmentCompleteMail.class, value);
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
