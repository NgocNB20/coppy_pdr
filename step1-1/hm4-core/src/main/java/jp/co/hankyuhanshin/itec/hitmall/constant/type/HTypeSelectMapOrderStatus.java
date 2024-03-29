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
 * 受注状態（受注検索）：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeSelectMapOrderStatus implements EnumType {

    /**
     * 入金確認中
     */
    PAYMENT_WAITING("入金確認中", "0"),

    /**
     * 商品準備中
     */
    GOODS_PREPARING("商品準備中", "1"),

    /**
     * 一部出荷済
     */
    PART_SHIPPRD("一部出荷済", "2"),

    /**
     * 出荷完了
     */
    SHIPMENT_COMPLETION("出荷完了", "3"),

    /**
     * 出荷可能
     */
    SHIPMENT_POSSIBLE("出荷可能", "4"),

    /**
     * キャンセル
     */
    CANCEL("キャンセル", "5"),

    /**
     * 保留
     */
    WAITINGFLAG("保留", "6"),

    /**
     * 請求決済エラー
     */
    BILL_SETTLEMENT_ERROR("請求決済エラー", "7"),

    /**
     * キャンセル以外
     */
    OTHERCANCEL("キャンセル以外", "8");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeSelectMapOrderStatus of(String value) {

        HTypeSelectMapOrderStatus hType = EnumTypeUtil.getEnumFromValue(HTypeSelectMapOrderStatus.class, value);
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
