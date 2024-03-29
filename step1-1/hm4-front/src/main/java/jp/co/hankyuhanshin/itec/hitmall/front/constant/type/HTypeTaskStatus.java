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
 * バッチタスク状態：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeTaskStatus implements EnumType {

    /**
     * 未処理
     */
    UNTREATED("未処理", "0"),

    /**
     * 処理中
     */
    UNDER_PROCESSING("処理中", "1"),

    /**
     * 正常終了
     */
    NORMAL_TERMINATION("正常終了", "2"),

    /**
     * 異常終了
     */
    ABNORMAL_TERMINATION("異常終了", "3"),

    /**
     * キャンセル終了
     */
    CANCELLATION_END("キャンセル終了", "4");

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
    public static HTypeTaskStatus of(String value) {

        HTypeTaskStatus hType = EnumTypeUtil.getEnumFromValue(HTypeTaskStatus.class, value);
        return hType;
    }
}
