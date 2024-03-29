/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hmbase.constant;

/**
 * 入力規制の情報を集約したクラス
 * <pre>
 * 散乱している全ての情報を集約するには工数がかかってしまうため
 * 案件毎に変わりそうな物を抜粋して集約
 * </pre>
 *
 * @author negishi
 */
public class ValidatorConstants {

    /**
     * チェックスタイル対応（コンストラクタがなければ警告される）
     */
    protected ValidatorConstants() {
    }

    /**
     * 相関チェックの複数チェック用エラーフィールド指定定数
     */
    public static final String NOTARGET = "NOTARGET";

    /****************************** HRegularExpressionValidator start ******************************/

    /* ━━━━━━━━━━━━ 共通化の一時情報のためprotected ━━━━━━━━━━━━ */

    /**
     * +
     */
    protected static final String REGEX_PLUS = "+";

    /**
     * カテゴリーIDの正規表現
     */
    protected static final String REGEX_CATEGORY_ID_BASE = "[a-zA-Z0-9_-]";

    /**
     * セールIDの正規表現
     */
    protected static final String REGEX_SALE_ID_BASE = "[a-zA-Z0-9_-]";

    /**
     * 商品グループコード（商品管理番号）の正規表現
     */
    protected static final String REGEX_GOODS_GROUP_CODE_BASE = "[a-zA-Z0-9-]";

    /**
     * 商品コードの正規表現
     */
    protected static final String REGEX_GOODS_CODE_BASE = "[a-zA-Z0-9-]";

    /**
     * JANコードの正規表現
     */
    protected static final String REGEX_JAN_CODE_BASE = "[0-9]";

    /**
     * カタログコードの正規表現
     */
    protected static final String REGEX_CATALOG_CODE_BASE = "[a-zA-Z0-9_-]";

    /**
     * 商品規格画像コードの正規表現
     */
    protected static final String REGEX_GOODS_IMAGE_CODE_BASE = "[a-zA-Z0-9-]";

    /**
     * 受注コードの正規表現
     */
    protected static final String REGEX_ORDER_CODE_BASE = "[A-Z0-9]";

    /**
     * 定期受注コードの正規表現
     */
    protected static final String REGEX_PERIODIC_ORDER_CODE_BASE = "[A-Z0-9]";

    /**
     * 伝票番号の正規表現
     */
    protected static final String REGEX_DELIVERY_CODE_BASE = "[a-zA-Z0-9_-]";

    /**
     * 商品規格画像コードの正規表現
     */
    protected static final String REGEX_COUPON_ID_BASE = "[a-zA-Z0-9_-]";

    /**
     * セールコードの正規表現
     */
    protected static final String REGEX_SALE_CODE_BASE = "[a-zA-Z0-9-]";

    /**
     * カテゴリーIDの長さの正規表現
     */
    protected static final String REGEX_CATEGORY_ID_LENGTH = "{1,20}";

    /**
     * セールIDの長さの正規表現
     */
    protected static final String REGEX_SALE_ID_LENGTH = "{1,20}";

    /**
     * 商品グループコード（商品管理番号）の長さの正規表現
     */
    protected static final String REGEX_GOODS_GROUP_CODE_LENGTH = "{1,20}";

    /**
     * 商品コードの長さの正規表現
     */
    protected static final String REGEX_GOODS_CODE_LENGTH = "{1,20}";

    /**
     * JANコードの長さの正規表現
     */
    protected static final String REGEX_JAN_CODE_LENGTH = "{0,16}";

    /**
     * カタログコードの長さの正規表現
     */
    protected static final String REGEX_CATALOG_CODE_LENGTH = "{0,16}";

    /**
     * 商品規格画像コードの長さの正規表現
     */
    protected static final String REGEX_GOODS_IMAGE_CODE_LENGTH = "{1,20}";

    /**
     * アンケートキーの正規表現
     */
    protected static final String REGEX_QUESTIONNAIREKEY_BASE = "[a-zA-Z0-9_-]";

    /**
     * 会員SEQの正規表現
     */
    protected static final String REGEX_MEMBERINFO_SEQ_BASE = "[0-9]";

    /**
     * セールコードの長さの正規表現
     */
    protected static final String REGEX_SALE_CODE_LENGTH = "{0,5}";
    /* ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ */

    /**
     * カテゴリーIDの正規表現（画面用）
     */
    public static final String REGEX_CATEGORY_ID = REGEX_CATEGORY_ID_BASE + REGEX_PLUS;

    /**
     * セールIDの正規表現（画面用）
     */
    public static final String REGEX_SALE_ID = REGEX_SALE_ID_BASE + REGEX_PLUS;

    /**
     * 商品グループコード（商品管理番号）の正規表現（画面用）
     */
    public static final String REGEX_GOODS_GROUP_CODE = REGEX_GOODS_GROUP_CODE_BASE + REGEX_PLUS;

    /**
     * 商品コードの正規表現（画面用）
     */
    public static final String REGEX_GOODS_CODE = REGEX_GOODS_CODE_BASE + REGEX_PLUS;

    /**
     * JANコードの正規表現（画面用）（JANは変わらないと思うが、カタログコードのそばにあるのでついでにやっておく）
     */
    public static final String REGEX_JAN_CODE = REGEX_JAN_CODE_BASE + REGEX_PLUS;

    /**
     * カタログコードの正規表現（画面用）（"JAN/カタログコード"となっている項目はこれを使う）
     */
    public static final String REGEX_CATALOG_CODE = REGEX_CATALOG_CODE_BASE + REGEX_PLUS;

    /**
     * 商品規格画像コードの正規表現（画面用）
     */
    public static final String REGEX_GOODS_IMAGE_CODE = REGEX_GOODS_IMAGE_CODE_BASE + REGEX_PLUS;

    /**
     * セールコードの正規表現（画面用）
     */
    public static final String REGEX_SALE_CODE = REGEX_SALE_CODE_BASE + REGEX_PLUS;

    /**
     * カテゴリーIDの正規表現（一括アップロード用）
     */
    public static final String REGEX_UL_CATEGORY_ID = REGEX_CATEGORY_ID_BASE + REGEX_CATEGORY_ID_LENGTH;

    /**
     * セールIDの正規表現（一括アップロード用）
     */
    public static final String REGEX_UL_SALE_ID = REGEX_SALE_ID_BASE + REGEX_SALE_ID_LENGTH;

    /**
     * 商品グループコード（商品管理番号）の正規表現（一括アップロード用）
     */
    public static final String REGEX_UL_GOODS_GROUP_CODE = REGEX_GOODS_GROUP_CODE_BASE + REGEX_GOODS_GROUP_CODE_LENGTH;

    /**
     * 商品コードの正規表現 （一括アップロード用）
     */
    public static final String REGEX_UL_GOODS_CODE = REGEX_GOODS_CODE_BASE + REGEX_GOODS_CODE_LENGTH;

    /**
     * JANコードの正規表現（一括アップロード用）
     */
    public static final String REGEX_UL_JAN_CODE = REGEX_JAN_CODE_BASE + REGEX_JAN_CODE_LENGTH;

    /**
     * カタログコードの正規表現（一括アップロード用）
     */
    public static final String REGEX_UL_CATALOG_CODE = REGEX_CATALOG_CODE_BASE + REGEX_CATALOG_CODE_LENGTH;

    /**
     * セールコードの正規表現（一括アップロード用）
     */
    public static final String REGEX_UL_SALE_CODE = REGEX_SALE_CODE_BASE + REGEX_SALE_CODE_LENGTH;

    // PDR Migrate Customization from here
    /** 商品規格画像コードの正規表現 （一括アップロード用） */
    public static final String REGEX_UL_GOODS_IMAGE_CODE = REGEX_GOODS_IMAGE_CODE_BASE + REGEX_GOODS_IMAGE_CODE_LENGTH;
    // PDR Migrate Customization to here

    /**
     * 受注コードの正規表現
     */
    public static final String REGEX_ORDER_CODE = REGEX_ORDER_CODE_BASE + REGEX_PLUS;

    /**
     * 定期受注コードの正規表現
     */
    public static final String REGEX_PERIODIC_ORDER_CODE = REGEX_PERIODIC_ORDER_CODE_BASE + REGEX_PLUS;

    /**
     * 伝票番号の正規表現
     */
    public static final String REGEX_DELIVERY_CODE = REGEX_DELIVERY_CODE_BASE + REGEX_PLUS;

    /**
     * 商品規格画像コードの正規表現
     */
    public static final String REGEX_COUPON_ID = REGEX_COUPON_ID_BASE + REGEX_PLUS;

    /**
     * アンケートキーの正規表現
     */
    public static final String REGEX_QUESTIONNAIREKEY = REGEX_QUESTIONNAIREKEY_BASE + REGEX_PLUS;

    /**
     * 会員SEQの正規表現
     */
    public static final String REGEX_MEMBERINFO_SEQ = REGEX_MEMBERINFO_SEQ_BASE + REGEX_PLUS;

    /**
     * お問い合わせ番号 正規表現
     */
    public static final String REGEX_INQUIRY_CODE = "^Q[0-9][0-1][0-9][0-3][0-9][0-9]{6}$";
    /******************************* HRegularExpressionValidator end *******************************/

    /*********************************** HLengthValidator start ************************************/

    /**
     * カテゴリーIDの最大文字数
     */
    public static final int LENGTH_CATEGORY_ID_MAXIMUM = 20;

    /**
     * セールIDの最大文字数
     */
    public static final int LENGTH_SALE_ID_MAXIMUM = 20;

    /**
     * 商品グループコード（商品管理番号）の最大文字数
     */
    public static final int LENGTH_GOODS_GROUP_CODE_MAXIMUM = 20;

    /**
     * 商品コードの最大文字数
     */
    public static final int LENGTH_GOODS_CODE_MAXIMUM = 20;

    /**
     * JANコードの最大文字数
     */
    public static final int LENGTH_JAN_CODE_MAXIMUM = 16;

    /** カタログコードの最大文字数 */
    public static final int LENGTH_CATALOG_CODE_MAXIMUM = 16;

    /**
     * 商品説明の最大文字数
     */
    public static final int LENGTH_GOODS_NOTE_MAXIMUM = 4000;

    // 2023-renew No19 from here
    /**
     * フリーエリア商品説明の最大文字数
     */
    public static final int LENGTH_GOODS_NOTE_FREEAREA_MAXIMUM = 8000;
    // 2023-renew No19 to here

    /**
     * 商品_受注連携設定の最大文字数
     */
    public static final int LENGTH_GOODS_ORDERSETTING_MAXIMUM = 4000;

    /**
     * 受注コードの最大文字数
     */
    public static final int LENGTH_ORDER_CODE_MAXIMUM = 12;

    /**
     * 定期受注コードの最大文字数
     */
    public static final int LENGTH_PERIODIC_ORDER_CODE_MAXIMUM = 12;

    /**
     * クーポンIDの最大文字列長
     */
    public static final int LENGTH_COUPON_ID_MAXIMUM = 12;

    /**
     * クーポンコードの最大文字列長
     */
    public static final int LENGTH_COUPON_CODE_MAXIMUM = 20;

    /**
     * アンケートキーの最大文字列長
     */
    public static final int LENGTH_QUESTIONNAIRE_KEY_MAXIMUM = 20;

    /**
     * アンケート名の最大文字列長
     */
    public static final int LENGTH_QUESTIONNAIRE_NAME_MAXIMUM = 100;

    /**
     * 管理メモの最大文字列長
     */
    public static final int LENGTH_MANAGEMENT_MEMO_MAXIMUM = 2000;

    /**
     * 会員SEQの最大文字長
     */
    public static final int LENGTH_MEMBERINFO_SEQ_MAXIMUM = 8;

    /**
     * 会員IDの最大文字長
     */
    public static final int LENGTH_MEMBERINFO_ID_MAXIMUM = 160;

    /**
     * 氏名(姓)の最大文字長
     */
    public static final int LENGTH_LASTNAME_MAXIMUM = 16;

    /**
     * 氏名(名)の最大文字長
     */
    public static final int LENGTH_FIRSTNAME_MAXIMUM = 16;

    /**
     * フリガナ(姓)の最大文字長
     */
    public static final int LENGTH_LASTKANA_MAXIMUM = 40;

    /**
     * フリガナ(名)の最大文字長
     */
    public static final int LENGTH_FIRSTKANA_MAXIMUM = 40;

    /**
     * 電話番号の最大文字長
     */
    public static final int LENGTH_TEL_MAXIMUM = 11;

    /**
     * 連絡先電話番号の最大文字長
     */
    public static final int LENGTH_CONTACT_TEL_MAXIMUM = 11;

    /**
     * FAXの最大文字長
     */
    public static final int LENGTH_FAX_MAXIMUM = 11;

    /**
     * 都道府県の最大文字長
     */
    public static final int LENGTH_PREFECTURE_MAXMUM = 4;

    /**
     * 市区郡の最大文字長
     */
    public static final int LENGTH_ADDRESS_1 = 50;

    /**
     * 町村・番地の最大文字長
     */
    public static final int LENGTH_ADDRESS_2 = 100;

    /**
     * マンション・建物名の最大文字長
     */
    public static final int LENGTH_ADDRESS_3 = 200;

    /**
     * ニックネームの最大文字長
     */
    public static final int LENGTH_NICKNAME_MAXIMUM = 20;

    /**
     * お問い合わせ内容の最大文字数
     */
    public static final int LENGTH_INQUIRYBODY_MAXIMUM = 4000;

    /**
     * 問い合わせ管理者連絡用電話番号の最大文字数
     */
    public static final int LENGTH_OPERATOR_CONTACT_TEL_MAXIMUM = 11;

    /**
     * 部署名の最大文字数
     */
    public static final int LENGTH_DIVISIONNAME_MAXIMUM = 20;

    /**
     * 担当者の最大文字数
     */
    public static final int LENGTH_REPRESENTATIVE_MAXIMUM = 18;

    /**
     * 連携メモの最大文字数
     */
    public static final int LENGTH_COOPERATIONMEMO_MAXIMUM = 100;

    /**
     * 管理メモの最大文字数
     */
    public static final int LENGTH_MEMO_MAXIMUM = 2000;

    /**
     * ユーザーレビュータイトルの最大文字長
     */
    public static final int LENGTH_USERREVIEW_TITLE_MAXIMUM = 100;

    /**
     * ユーザーレビュー本文の最大文字数
     */
    public static final int LENGTH_USERREVIEW_BODY_MAXIMUM = 2000;

    /************************************ HLengthValidator end *************************************/

    /*********************************** MSCODE start ************************************/

    /**
     * 商品管理番号　正規表現　メッセージ
     */
    public static final String MSGCD_REGEX_GOODS_GROUP_CODE = "{CGG000101E}";

    /**
     * 商品番号　正規表現　メッセージ
     */
    public static final String MSGCD_REGEX_GOODS_CODE = "{CGG000102E}";

    /**
     * JANコード　正規表現　メッセージ
     */
    public static final String MSGCD_REGEX_JAN_CODE = "{CGG000103E}";

    /** カタログコード　正規表現　メッセージ */
    public static final String MSGCD_REGEX_CATALOG_CODE = "{CGG000104E}";

    /**
     * JAN / カタログ コード　正規表現　メッセージ
     */
    public static final String MSGCD_REGEX_JAN_CATALOG_CODE = "{CGG000106E}";

    /**
     * カテゴリID 正規表現 メッセージ
     */
    public static final String MSGCD_REGEX_CATEGORY_ID = "{AGC000024W}";

    /**
     * 伝票番号　正規表現　メッセージ
     */
    public static final String MSGCD_REGEX_DELIVERY_CODE = "{COX000101E}";

    /**
     * クーポンID　正規表現　メッセージ
     */
    public static final String MSGCD_COUPON_ID_INVALID = "{ACP000202W}";

    /** アンケートキー　正規表現　メッセージ */
    public static final String MSGCD_QUESTIONNAIREKEY_INVALID = "{AGC000024W}";
    /**
     * クーポンID　正規表現　メッセージ
     */
    public static final String MSGCD_CAMPAIGN_CODE_INVALID = "{ASC000403W}";

    /**
     * セールコード　正規表現　メッセージ
     */
    public static final String MSGCD_REGEX_SALE_CODE = "{CGG000107E}";

    /*********************************** MSCODE start ************************************/

}
