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
 * cuenoteFCメール配信状態
 *
 * @author st75001
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeMailDeliveryStatus implements EnumType {

    /**
     * 未配信
     */
    UNDELIVERED("未配信", "0"),

    /**
     * 配信中
     */
    DELIVERING("配信中", "1"),

    /**
     * 配信済み
     */
    DELIVERED("配信済み", "2"),

    /**
     * 配信失敗
     */
    FAILED("配信失敗", "3"),

    /**
     * 配信除外
     */
    EXCLUSION("配信除外", "4"),

    /**
     * システムエラー
     */
    SYSTEM_ERROR("システムエラー", "9");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeMailDeliveryStatus of(String value) {

        return EnumTypeUtil.getEnumFromValue(HTypeMailDeliveryStatus.class, value);
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
