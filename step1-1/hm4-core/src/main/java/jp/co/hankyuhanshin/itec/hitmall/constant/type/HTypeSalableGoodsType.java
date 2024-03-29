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
 * PDR#004 01_販売可能商品の制御<br/>
 *
 * <pre>
 * 販売可能商品区分
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
// PDR Migrate Customization from here
public enum HTypeSalableGoodsType implements EnumType {

    /**
     * 閲覧不可(ログイン前)
     */
    NOT_VIEW("閲覧不可商品", "1"),

    /**
     * 価格非表示(ログイン前)
     */
    PRICE_HIDE("価格非表示商品", "2"),

    /**
     * 購入可能(ログイン後)
     */
    SALEON("購入可能商品", "3"),

    /**
     * 購入不可(ログイン後)
     */
    SALEOFF("購入不可商品", "4"),

    /**
     * 閲覧不可(ログイン後)
     */
    NOT_VIEW_LOGIN("閲覧不可商品", "5");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeSalableGoodsType of(String value) {

        HTypeSalableGoodsType hType = EnumTypeUtil.getEnumFromValue(HTypeSalableGoodsType.class, value);
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
// PDR Migrate Customization to here