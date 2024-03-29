package jp.co.hankyuhanshin.itec.hitmall.constant;

/**
 * バッチ名
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public class BatchName {

    /**
     * TOP画面集計
     */
    public static final String BATCH_TOP_TOTALING = "BATCH_TOP_TOTALING";

    /**
     * 出荷アップロード
     */
    public static final String BATCH_SHIPMENT_REGIST = "BATCH_SHIPMENT_REGIST";

    /**
     * メール送信バッチ全般
     */
    public static final String BATCH_MAIL = "BATCH_MAIL";

    /**
     * 郵便番号自動更新
     */
    public static final String BATCH_ZIP_CODE = "BATCH_ZIP_CODE";

    /**
     * 事業所郵便番号自動更新
     */
    public static final String BATCH_OFFICE_ZIP_CODE = "BATCH_OFFICE_ZIP_CODE";

    /**
     * 商品画像更新
     */
    public static final String BATCH_GOODSIMAGE_UPDATE = "BATCH_GOODSIMAGE_UPDATE";

    /**
     * 商品登録非同期バッチ
     */
    public static final String BATCH_GOODS_ASYNCHRONOUS = "BATCH_GOODS_ASYNCHRONOUS";

    /**
     * 商品グループ在庫状態更新
     */
    public static final String BATCH_STOCKSTATUSDISPLAY_UPDATE = "BATCH_STOCKSTATUSDISPLAY_UPDATE";

    /**
     * サイトマップXML出力バッチ
     */
    public static final String BATCH_SITEMAPXML_OUTPUT = "BATCH_SITEMAPXML_OUTPUT";

    /**
     * クリアバッチ
     */
    public static final String BATCH_CLEAR = "BATCH_CLEAR";

    /**
     * タスククリーンバッチ
     */
    public static final String BATCH_TASK_CLEAN = "BATCH_TASK_CLEAN";

    /**
     * 受注CSV非同期バッチ
     */
    public static final String BATCH_ORDER_CSV_ASYNCHRONOUS = "BATCH_ORDER_CSV_ASYNCHRONOUS";

    /**
     * アンケート回答集計
     */
    public static final String BATCH_QUESTIONNAIRE_TOTALING = "BATCH_QUESTIONNAIRE_TOTALING";

    // PDR Migrate Customization from here
    /**
     * 会員情報更新バッチ
     */
    public static final String BATCH_MEMBER_INFO_UPDATE = "BATCH_MEMBER_INFO_UPDATE";

    /**
     * 商品価格更新
     */
    public static final String BATCH_GOODS_PRICE_UPDATE = "BATCH_GOODS_PRICE_UPDATE";

    /**
     * 出荷情報取込バッチ
     */
    public static final String BATCH_DELV_PRICE_UPDATE = "BATCH_DELV_PRICE_UPDATE";

    /**
     * ジョブ監視バッチ
     */
    public static final String BATCH_JOB_MONITORING = "BATCH_JOB_MONITORING";
    // PDR Migrate Customization to here
    // 2023-renew No42 from here
    /**
     * デジタルカタログ取込バッチ
     */
    public static final String BATCH_DIGITALCATALOG_IMPORT = "BATCH_DIGITALCATALOG_IMPORT";
    // 2023-renew No42 to here

    // 2023-renew No3 from here
    /**
     * 商品フィード出力バッチ
     */
    public static final String BATCH_GOODS_FEED_OUTPUT = "BATCH_GOODS_FEED_OUTPUT";
    // 2023-renew No3 to here

    /**
     * コンテンツフィード出力バッチ
     */
    public static final String BATCH_CONTENTS_FEED_OUTPUT = "BATCH_CONTENTS_FEED_OUTPUT";

    // 2023-renew No65 from here
    /**
     * 入荷情報取得バッチ
     */
    public static final String BATCH_STOCK_DATA_IMPORT = "BATCH_STOCK_DATA_IMPORT";
    // 2023-renew No65 to here

    // 2023-renew No41 from here
    /**
     * お気に入りセール通知メール配信バッチ
     */
    public static final String BATCH_CUENOTEFC_SALE_MAIL = "BATCH_CUENOTEFC_SALE_MAIL";

    /**
     * お気に入りセール通知メール配信確認バッチ
     */
    public static final String BATCH_CUENOTEFC_SALE_MAIL_CONFIRM = "BATCH_CUENOTEFC_SALE_MAIL_CONFIRM";

    /**
     * セール情報取得バッチ
     */
    public static final String BATCH_SALE_DATA_IMPORT = "BATCH_SALE_DATA_IMPORT";
    // 2023-renew No41 to here

    // 2023-renew No21 from here
    /**
     * 一緒によく購入される商品更新バッチ
     */
    public static final String BATCH_GOODS_PURCHASED_TOGETHER_UPDATE = "BATCH_GOODS_PURCHASED_TOGETHER_UPDATE";
    // 2023-renew No21 to here
}
