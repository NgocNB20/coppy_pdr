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
 * フリーエリア公開状態
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeFreeAreaOpenStatus implements EnumType {

    /**
     * 公開終了
     */
    OPEN_PAST("公開終了", "0"),

    /**
     * 公開中
     */
    OPEN("公開中", "1"),

    /**
     * 公開予定
     */
    OPEN_FUTURE("公開予定", "2");

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
    public static HTypeFreeAreaOpenStatus of(String value) {

        HTypeFreeAreaOpenStatus hType = EnumTypeUtil.getEnumFromValue(HTypeFreeAreaOpenStatus.class, value);
        return hType;
    }
}
