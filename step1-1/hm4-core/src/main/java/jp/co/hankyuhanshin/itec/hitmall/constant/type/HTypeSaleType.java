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
 * セール種別
 *
 * @author s_tsuru
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeSaleType implements EnumType {
    // PDR Migrate Customization from here
    /** 通常セール */
    NORMAL("", "0"),

    /** シークレットセール */
    SECRET("", "1"),

    /** 予約注文 */
    RESERVATION("", "2");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeSaleType of(String value) {

        HTypeSaleType hType = EnumTypeUtil.getEnumFromValue(HTypeSaleType.class, value);

        if (hType == null) {
            throw new IllegalArgumentException(value);
        } else {
            return hType;
        }
    }

    /**
     * セール種別が同時購入可能フラグの設定が必要か判定する<br/>
     * 以下の場合、設定が必要
     * ・RESERVATION
     *
     * @return true:RESERVATION false:NORMAL, SECRET
     */
    public boolean isNeedSimultaneousPurchaseflag() {
        return RESERVATION.equals(this);
    }

    /**
     * セール種別が通常商品公開状態の設定が必要か判定する<br/>
     * 以下の場合、設定が必要
     * ・SECRET
     *
     * @return true:SECRET false:NORMAL, RESERVATION
     */
    public boolean isNeedNormalGoodsOpenStatus() {
        return SECRET.equals(this);
    }

    /**
     * ラベル<br/>
     */
    private String label;

    /**
     * 区分値<br/>
     */
    private String value;
    // PDR Migrate Customization to here
}
