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
 * 酒類フラグ
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeAlcoholFlag implements EnumType {

    /**
     * 酒類以外
     */
    NOT_ALCOHOL("酒類以外", "0"),

    /**
     * 酒類
     */
    ALCOHOL("酒類", "1");

    /**
     *
     * コンストラクタ<br/>
     *
     * @param ordinal Integer
     * @param name String
     * @param value String
     */

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeAlcoholFlag of(String value) {

        HTypeAlcoholFlag hType = EnumTypeUtil.getEnumFromValue(HTypeAlcoholFlag.class, value);
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
