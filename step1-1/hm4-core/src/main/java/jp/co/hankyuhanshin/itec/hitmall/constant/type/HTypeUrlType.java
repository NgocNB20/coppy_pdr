/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.hitmall.constant.type;

import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.seasar.doma.Domain;

/**
 * サイトマップタイプ：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeUrlType implements EnumType {

    /**
     * 0:静的 ※ラベル未使用
     */
    UNCHANGED("", "0"),

    /**
     * 1:商品一覧 ※ラベル未使用
     */
    ITEM_LIST("", "1"),

    /**
     * 2:商品詳細 ※ラベル未使用
     */
    GOODS_DETAIL("", "2"),

    /**
     * 3:セット商品商品詳細 ※ラベル未使用
     */
    SET_GOODA_DETAIL("", "3"),

    /**
     * 4:ニュース詳細 ※ラベル未使用
     */
    NEWS_DETAIL("", "4"),

    /**
     * 5:特集ページ ※ラベル未使用
     */
    SPECIAL_PAGE("", "5"),

    /**
     * 6:ランディングページ ※ラベル未使用
     */
    LP_PAGE("", "6"),

    /**
     * 7:セール一覧 ※ラベル未使用
     */
    SALEL_IST("", "7"),

    /**
     * 8:アンケート ※ラベル未使用
     */
    QUESTIONNAIRE("", "8"),

    /**
     * 9:商品説明（MBのみ） ※ラベル未使用
     */
    GOODS_EXPLANATION("", "9"),

    /**
     * 10:在庫一覧（MBのみ） ※ラベル未使用
     */
    STOCK_LIST("", "10"),

    /**
     * 11:商品画像一覧（MBのみ） ※ラベル未使用
     */
    GOODS_PICTURE_LIST("", "11"),

    /**
     * 12:商品画像一覧(規格)（MBのみ） ※ラベル未使用
     */
    GOODS_UNIT_PICTURE_LIST("", "12");

    /**
     * 隠蔽コンストラクタ<br/>
     * 区分値の設定
     *
     * @param ordinal Integer
     * @param name String
     * @param value String
     */

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeUrlType of(String value) {

        HTypeUrlType hType = EnumTypeUtil.getEnumFromValue(HTypeUrlType.class, value);
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
