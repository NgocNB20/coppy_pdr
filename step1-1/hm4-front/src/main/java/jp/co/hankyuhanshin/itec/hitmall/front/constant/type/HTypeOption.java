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
 * オプション （集計）：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeOption implements EnumType {

    /**
     * キャンセル・返品・変更を含まない
     */
    EXCLUSION("キャンセル・返品・変更を含まない", "0"),

    /**
     * キャンセル・返品・変更分も含めて表示
     */
    ALL("キャンセル・返品・変更分も含めて表示", "1");

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
    public static HTypeOption of(String value) {

        HTypeOption hType = EnumTypeUtil.getEnumFromValue(HTypeOption.class, value);
        return hType;
    }
}
