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
 * 受注状態：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeOrderStatus implements EnumType {

    /**
     * 入金確認中
     */
    PAYMENT_CONFIRMING("入金確認中", "0"),

    /**
     * 商品準備中
     */
    GOODS_PREPARING("商品準備中", "1"),

    /**
     * 一部出荷
     */
    PART_SHIPMENT("一部出荷", "2"),

    /**
     * 出荷完了
     */
    SHIPMENT_COMPLETION("出荷完了", "3");

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
    public static HTypeOrderStatus of(String value) {

        HTypeOrderStatus hType = EnumTypeUtil.getEnumFromValue(HTypeOrderStatus.class, value);
        return hType;
    }
}
