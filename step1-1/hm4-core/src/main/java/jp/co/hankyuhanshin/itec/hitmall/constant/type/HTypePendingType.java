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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * PDR#028 注文情報の保留<br/>
 *
 * <pre>
 * 保留区分
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypePendingType implements EnumType {
    // PDR Migrate Customization from here
    /**
     * カード承認待ち
     */
    WAIT_CARD_APPROVAL("", "31"),

    /**
     * 預かり金保留
     */
    DEPOSIT_HOLDING("", "32"),

    /**
     * 未収残確認要
     */
    CONFIRMATION_REQUIRED("", "33"),

    /**
     * 特別対応
     */
    SPECIAL_CORRESPONDENCE("", "34"),

    /**
     * 指定商品保留
     */
    SPECIFIED_MERCHANDISE_HOLD("", "35"),

    /**
     * 0円伝票
     */
    ZERO_YEN_SLIP("", "36"),

    // 2023-renew No15 from here
    /**
     * 初回注文金額確認要
     */
    FIRST_CONVENIENCE_ORDER_LIMIT("", "37"),
    // 2023-renew No15 to here

    /**
     * 新規お届け先
     */
    NEW_RECEPTION_DESK("", "41"),

    /**
     * 陸送クール沖縄
     */
    LAND_COOL_SEND("", "42"),

    /**
     * 最短お届日取得エラー
     */
    ZIP_CODE_UNKNOWN("", "50"),

    /**
     * プロモ読取エラー
     */
    PROMO_READ_ERROR("", "51"),

    /**
     * お届け先連携エラー
     */
    DESTINATION_API_ERROR("", "52"),

    // 2023-renew No26 from here
    /**
     * 複数回注文
     */
    MULTIPLE_ORDER("", "53");
    // 2023-renew No26 to here

    /** 優先順位 MAP */
    public static final Map<HTypePendingType, Integer> PRIMARY_MAP = new LinkedHashMap<>();

    static {
        PRIMARY_MAP.put(PROMO_READ_ERROR, 1); // プロモ読取エラー
        PRIMARY_MAP.put(ZIP_CODE_UNKNOWN, 2);// 最短お届日取得エラー
        PRIMARY_MAP.put(NEW_RECEPTION_DESK, 3); // 新規お届け先
        PRIMARY_MAP.put(CONFIRMATION_REQUIRED, 4); // 未収残確認要
        // 2023-renew No15 from here
        PRIMARY_MAP.put(FIRST_CONVENIENCE_ORDER_LIMIT, 5); // 初回注文金額確認要
        // 2023-renew No15 to here
        PRIMARY_MAP.put(SPECIAL_CORRESPONDENCE, 6); // 特別対応
        PRIMARY_MAP.put(SPECIFIED_MERCHANDISE_HOLD, 7); // 指定商品保留
        PRIMARY_MAP.put(LAND_COOL_SEND, 8); // 陸送クール沖縄
        PRIMARY_MAP.put(ZERO_YEN_SLIP, 9); // 0円伝票
        PRIMARY_MAP.put(DEPOSIT_HOLDING, 10); // 預かり金チェック
        PRIMARY_MAP.put(WAIT_CARD_APPROVAL, 11); // 支払方法チェック
        // 2023-renew No26 from here
        PRIMARY_MAP.put(MULTIPLE_ORDER, 12); // 複数回注文
        // 2023-renew No26 to here

        // 以下は最下位としておく（OrderController側で上書きするので、実質優先度3位）
        PRIMARY_MAP.put(DESTINATION_API_ERROR, 13); // お届け先連携エラー
    }

    /**
     * doma用ファクトリメソッド
     */
    public static HTypePendingType of(String value) {

        HTypePendingType hType = EnumTypeUtil.getEnumFromValue(HTypePendingType.class, value);
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
