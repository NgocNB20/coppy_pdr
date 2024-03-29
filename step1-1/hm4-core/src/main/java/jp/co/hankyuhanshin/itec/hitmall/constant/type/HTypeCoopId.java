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
 * PDR#036 商品価格の基幹連携<br/>
 * 基幹連携ID<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeCoopId implements EnumType {
    // PDR Migrate Customization from here
    /** 会員変更情報取得連携 */
    MEMBER_CHGINFO_GET_COOP("会員変更情報取得連携", "IDM001"),

    /** 価格変更情報取得連携 */
    GOODS_CHGINFO_GET_COOP("価格変更情報取得連携", "IDG001"),

    /** 出荷情報取得連携 */
    ORDER_DELV_INFO_IMPORT("出荷情報取得連携", "IDO001"),

    /** 定期商品更新情報取得連携 */
    REGULARSERVICE_GOODS_CHGINF_GET_COOP("", "IDG002"),

    /** 定期便出荷情報取込連携 */
    SUBSCRIPTION_DELV_INFO_IMPORT("", "IDO002"),

    /** セール情報連携 */
    GOODS_SALE_GET_COOP("基幹連携", "IDG003");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeCoopId of(String value) {

        HTypeCoopId hType = EnumTypeUtil.getEnumFromValue(HTypeCoopId.class, value);
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
    // PDR Migrate Customization to here
}
