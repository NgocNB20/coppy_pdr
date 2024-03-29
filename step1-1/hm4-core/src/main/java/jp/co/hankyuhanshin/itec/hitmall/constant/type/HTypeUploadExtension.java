// 2023-renew No22 from here
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
 * アップロード拡張子の種類：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeUploadExtension implements EnumType {

    /**
     * JPEG
     */
    JPEG("JPEG", "0"),

    /**
     * PNG
     */
    PNG("PNG", "1"),

    /**
     * PDF
     */
    PDF("PDF", "2");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeUploadExtension of(String value) {

        HTypeUploadExtension hType = EnumTypeUtil.getEnumFromValue(HTypeUploadExtension.class, value);
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
// 2023-renew No22 to here