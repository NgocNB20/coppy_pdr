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
 * アップロード種別：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeUploadType implements EnumType {

    /**
     * 追加モード
     */
    REGIST("追加モード", "0"),

    /**
     * 更新モード
     */
    MODIFY("更新モード", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeUploadType of(String value) {

        HTypeUploadType hType = EnumTypeUtil.getEnumFromValue(HTypeUploadType.class, value);
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
