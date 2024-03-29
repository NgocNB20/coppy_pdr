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
 * Front表示フラグ
 * <pre>
 * 本カラムはカードブランドの一覧を取得(CardBrandDao_getCardBrandList.sql)のみで使用
 * PKGの製造ルール上、EnumTypeクラスを作成しているが制御する想定はしていないため@Deprecatedを指定している
 * </pre>
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeFrontDisplayFlag implements EnumType {

    /**
     * 表示する ※ラベル未使用
     */
    @Deprecated ON("", "1"),

    /**
     * 表示しない ※ラベル未使用
     */
    @Deprecated OFF("", "0");

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
    public static HTypeFrontDisplayFlag of(String value) {

        HTypeFrontDisplayFlag hType = EnumTypeUtil.getEnumFromValue(HTypeFrontDisplayFlag.class, value);
        return hType;
    }
}
