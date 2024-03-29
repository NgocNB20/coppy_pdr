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
 * 問い合わせ状態：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeInquiryStatus implements EnumType {

    /**
     * 受付中
     */
    RECEIVING("受付中", "0"),

    /**
     * 連絡案内中
     */
    SENDING("連絡案内中", "1"),

    /**
     * 完了
     */
    COMPLETION("完了", "2");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeInquiryStatus of(String value) {

        HTypeInquiryStatus hType = EnumTypeUtil.getEnumFromValue(HTypeInquiryStatus.class, value);
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
