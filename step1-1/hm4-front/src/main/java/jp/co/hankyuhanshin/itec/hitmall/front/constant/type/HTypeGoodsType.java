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
 * 商品種別
 *
 * @author hirata
 * @author Kaneko 2012/09/03 Enum廃止対応
 */
@Getter
@AllArgsConstructor
public enum HTypeGoodsType implements EnumType {
    // PDR Migrate Customization from here
    /** 通常 */
    DEFAULT("", "0"),

    /** 受注生産品 */
    BUILD_TO_ORDER("", "1"),

    /** セット商品 */
    BUNDLE("", "2"),

    /** セット専用商品 */
    BUNDLE_TARGET("", "3"),

    /** 定期商品 */
    PERIODIC("", "4"),

    /** 試供品 */
    SAMPLE("", "5"),

    /** ラッピング */
    WRAPPING("", "6");

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
    public static HTypeGoodsType of(String value) {

        HTypeGoodsType hType = EnumTypeUtil.getEnumFromValue(HTypeGoodsType.class, value);

        if (hType == null) {
            throw new IllegalArgumentException(value);
        } else {
            return hType;
        }
    }

    /**
     * 新規受注時、追加可能な商品タイプか判定する<br/>
     *
     * @param value String
     * @return true:追加可能 false:追加不可
     */
    public static boolean isAddableOrderRegistForBack(String value) {
        return DEFAULT.getValue().equals(value) || BUILD_TO_ORDER.getValue().equals(value) || PERIODIC.getValue()
                                                                                                      .equals(value)
               || SAMPLE.getValue().equals(value) || WRAPPING.getValue().equals(value);
    }

    /**
     * 受注修正時、追加可能な商品タイプか判定する<br/>
     *
     * @param value String
     * @return true:追加可能 false:追加不可
     */
    public static boolean isAddableOrderUpdateForBack(String value) {
        return DEFAULT.getValue().equals(value) || BUILD_TO_ORDER.getValue().equals(value) || SAMPLE.getValue()
                                                                                                    .equals(value)
               || WRAPPING.getValue().equals(value);
    }

    /**
     * セット商品の対象に設定可能な商品種別か判定する<br/>
     * 以下種別の場合trueを返す
     * <li>通常</li>
     * <li>セット専用商品</li>
     * <li>受注生産品</li>
     *
     * @return true:通常、試供品、セット専用商品 false:それ以外
     */
    public boolean isSettableForGoodsBundle() {
        if (this == DEFAULT) {
            return true;
        } else if (this == BUNDLE_TARGET) {
            return true;
        } else if (this == BUILD_TO_ORDER) {
            return true;
        }

        return false;
    }

    /**
     * セール商品の対象に設定可能な商品種別か判定する<br/>
     * 以下種別の場合trueを返す
     * <li>通常</li>
     * <li>受注生産品</li>
     * <li>セット商品</li>
     *
     * @return true:通常、受注生産品、セット商品 false:それ以外
     */
    public boolean isSettableForSale() {
        if (this == DEFAULT) {
            return true;
        } else if (this == BUILD_TO_ORDER) {
            return true;
        } else if (this == BUNDLE) {
            return true;
        }
        return false;
    }

    /**
     * フロント商品詳細に表示するかを判定する<br/>
     * 以下種別の場合trueを返す
     * <li>受注生産品</li>
     * <li>定期商品</li>
     * <li>試供品</li>
     *
     * @return true:受注生産品、定期商品、試供品 false:それ以外
     */
    public boolean isViewLabelForFront() {
        if (this == BUILD_TO_ORDER) {
            return true;
        } else if (this == PERIODIC) {
            return true;
        } else if (this == SAMPLE) {
            return true;
        }

        return false;
    }

    /**
     * 指定の種別がRMS連携対象か否か<br/>
     *
     * @return true：RMS連携対象の種別
     */
    public boolean isRmsSyncTarget() {
        if (this == DEFAULT) {
            return true;
        } else if (this == BUILD_TO_ORDER) {
            return true;
        }
        return false;
    }

    /**
     * 指定の種別がRMS商品情報のDB登録対象か否か<br/>
     *
     * @return true：登録対象の種別
     */
    public boolean isRmsGoodsRegistTarget() {
        if (this == DEFAULT) {
            return true;
        } else if (this == BUILD_TO_ORDER) {
            return true;
        } else if (this == BUNDLE_TARGET) {
            return true;
        } else if (this == PERIODIC) {
            return true;
        } else if (this == SAMPLE) {
            return true;
        } else if (this == WRAPPING) {
            return true;
        }
        return false;
    }

    /**
     * ランディングページ内購入を許可するか？
     *
     * @return true = 許可
     */
    public boolean isLpOrderAccept() {
        if (this == DEFAULT || this == BUILD_TO_ORDER) {
            return true;
        }
        return false;
    }

    /**
     * セット商品か否かでURL文字列を取得<br/>
     * ※パラメータGgcd用<br/>
     * フロント画面のHREFダイナミックプロパティで使用<br/>
     *
     * @return URL文字列
     */
    public String getNextUrlSuffixForGgcd() {
        if (this == BUNDLE) {
            return "/goods/setgoods/index.html?ggcd=";
        } else {
            return "/goods/index.html?ggcd=";
        }
    }

    /**
     * セット商品か否かでURL文字列を取得<br/>
     * ※パラメータGcd用<br/>
     * フロント画面のHREFダイナミックプロパティで使用<br/>
     *
     * @return URL文字列
     */
    public String getNextUrlSuffixForGcd() {
        if (this == BUNDLE) {
            return "/goods/setgoods/index.html?gcd=";
        } else {
            return "/goods/index.html?gcd=";
        }
    }
    // PDR Migrate Customization to here
}
