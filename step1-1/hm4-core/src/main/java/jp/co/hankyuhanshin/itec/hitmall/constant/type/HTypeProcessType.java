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
 * 処理種別：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeProcessType implements EnumType {

    /**
     * 受注
     */
    ORDER("受注", "O01"),

    /**
     * 変更
     */
    CHANGE("変更", "O02"),

    /**
     * キャンセル
     */
    CANCELLATION("キャンセル", "O03"),

    /**
     * 出荷
     */
    SHIPMENT("出荷", "S01"),

    /**
     * 一部出荷
     */
    PART_SHIPMENT("一部出荷", "S02"),

    /**
     * 入金
     */
    PAYMENT("入金", "R01"),

    /**
     * 返金
     */
    REPAYMENT("返金", "R02"),

    /**
     * 納品書出力
     */
    DELIVERY_SHEET_OUTPUT("納品書出力", "P01"),

    /**
     * 受注明細出力
     */
    ORDER_DETAILED_SHEET_OUTPUT("受注明細出力", "P02"),

    /**
     * 商品別明細出力
     */
    GOODS_DETAILED_SHEET_OUTPUT("商品別明細出力", "P03"),

    /**
     * メール送信
     */
    SEND_MAIL("メール送信", "M01"),

    /**
     * 督促メール送信
     */
    REMINDER_SEND_MAIL("督促メール送信", "M02"),

    /**
     * 期限切れメール送信
     */
    EXPIRED_SEND_MAIL("期限切れメール送信", "M03"),

    /**
     * ポイント有効化
     */
    POINT_ACTIVATE("ポイント有効化", "P06");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeProcessType of(String value) {

        HTypeProcessType hType = EnumTypeUtil.getEnumFromValue(HTypeProcessType.class, value);
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
