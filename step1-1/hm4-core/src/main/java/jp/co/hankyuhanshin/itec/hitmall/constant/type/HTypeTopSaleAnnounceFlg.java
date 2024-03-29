// 2023-renew No71 from here
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
 * TOP画面にセール対象商品が存在する旨を通知するフラグ
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeTopSaleAnnounceFlg implements EnumType {

    /**
     * 通知なし
     */
    OFF("通知なし", "0"),

    /**
     * 通知あり
     */
    ON("通知あり", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeTopSaleAnnounceFlg of(String value) {

        HTypeTopSaleAnnounceFlg hType = EnumTypeUtil.getEnumFromValue(HTypeTopSaleAnnounceFlg.class, value);
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
// 2023-renew No71 to here
