/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.constant;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.EnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 【ポイント部品】<br/>
 * ポイント利用方法定義クラス。<br />
 * <pre>
 * 決済方法選択画面に表示するポイント利用方法の定数。
 * </pre>
 *
 * @author thang
 */
@Getter
@AllArgsConstructor
public enum PointUseType implements EnumType {

    /**
     * 利用しない ※ラベル未使用
     */
    NONE("", "0"),

    /**
     * 一部利用（利用ポイント数を指定する） ※ラベル未使用
     */
    PART("", "1"),

    /**
     * 利用可能なポイントすべて ※ラベル未使用
     */
    ALL("", "2");

    /**
     * ラベル
     */
    private String label;

    /**
     * 区分値
     */
    private String value;

}
