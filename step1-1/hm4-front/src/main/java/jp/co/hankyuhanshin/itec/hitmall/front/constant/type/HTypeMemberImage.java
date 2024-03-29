// 2023-renew No22 from here
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
 * メンバー画像の種類: 列挙型
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */

@Getter
@AllArgsConstructor
public enum HTypeMemberImage implements EnumType {
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
    public static HTypeMemberImage of(String value) {

        HTypeMemberImage hType = EnumTypeUtil.getEnumFromValue(HTypeMemberImage.class, value);
        return hType;
    }
}
// 2023-renew No22 to here
