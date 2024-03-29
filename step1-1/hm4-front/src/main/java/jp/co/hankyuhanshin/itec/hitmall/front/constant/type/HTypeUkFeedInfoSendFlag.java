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
 * フィード情報送信フラグ
 *
 * @author tk32120
 */
@Getter
@AllArgsConstructor
public enum HTypeUkFeedInfoSendFlag implements EnumType {

    /**
     * 送信しない
     */
    OFF("送信しない", "0"),

    /**
     * 送信する
     */
    ON("送信する", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeUkFeedInfoSendFlag of(String value) {

        HTypeUkFeedInfoSendFlag hType = EnumTypeUtil.getEnumFromValue(HTypeUkFeedInfoSendFlag.class, value);
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
