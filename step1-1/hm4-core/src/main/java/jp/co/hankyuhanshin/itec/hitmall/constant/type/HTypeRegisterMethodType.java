// 2023-renew No21 from here
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
 * 登録方法
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeRegisterMethodType implements EnumType {

    /**
     * 管理画面
     */
    BACK("管理画面", "0"),

    /**
     * 集計バッチ
     */
    BATCH("集計バッチ", "1");

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
    public static HTypeRegisterMethodType of(String value) {

        HTypeRegisterMethodType hType = EnumTypeUtil.getEnumFromValue(HTypeRegisterMethodType.class, value);
        return hType;
    }

}
// 2023-renew No21 to here
