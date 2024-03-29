/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.front.base.constant;

/**
 * 正規表現一覧<br/>
 * Bean Validationの@Patternで使用
 *
 * @author kimura
 */
public class RegularExpressionsConstants {

    /**
     * 半角チェックの正規表現
     */
    public static final String HANKAKU_REGEX = "^[ -~｡-ﾟ\\s]+$";

    /**
     * 全角チェックの正規表現
     */
    public static final String ZENKAKU_REGEX = "^[^\\x00-\\x1F\\x7F-\\x9F -~｡-ﾟ\\s]+$";

    /**
     * 全角カナチェックの正規表現
     */
    public static final String ZENKAKU_KANA_REGEX = "^[ァ-ヶー－]+$";

    /**
     * 全角・半角カナチェックの正規表現
     */
    public static final String KANA_REGEX = "[ｧ-ﾞｰ－ﾝﾞﾟァ-ヶー－-]+$";
    // PDR Migrate Customization from here
    /**
     * パスワードの正規表現（半角英数記号6～20桁）
     */
    // Alphanumeric password required.Special characters not mandatory.
    public static final String PASSWORD_NUMBER_REGEX = "^[!-~]{6,20}$";
    // PDR Migrate Customization to here

    // 2023-renew No85-1 from here
    /**
     * 半角カナチェックの正規表現 (半角ハイフンあり)
     */
    public static final String HANKAKU_KANA_REGEX = "^[ｧ-ﾞｰ－ﾝﾞﾟ-]+$";
    // 2023-renew No85-1 to here

    /**
     * 電話番号の正規表現（数値とハイフンの組み合わせかどうか）
     */
    public static final String TELEPHONE_NUMBER_REGEX = "[\\d]*$";

    /**
     * 郵便番号の正規表現（数値３桁＋数値４桁）
     */
    public static final String ZIP_CODE_REGEX = "\\d{3}\\d{4}";

    /**
     * キャンペーンコードの正規表現
     */
    public static final String CAMPAIGN_CODE_REGEX = "[a-zA-Z0-9_-]+";
}
