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
 * 会員出力データタイプ
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeMemberInfoOutData implements EnumType {

    /**
     * 会員CSV
     */
    MEMBER_CSV("会員CSV", "6");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeMemberInfoOutData of(String value) {

        HTypeMemberInfoOutData hType = EnumTypeUtil.getEnumFromValue(HTypeMemberInfoOutData.class, value);
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
