/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.constant.type;

import jp.co.hankyuhanshin.itec.hitmall.constant.BatchName;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.seasar.doma.Domain;

/**
 * バッチ処理名：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeBatchName implements EnumType {

    /**
     * TOP画面集計
     */
    BATCH_TOP_TOTALING("TOP画面集計", BatchName.BATCH_TOP_TOTALING),

    /**
     * メール送信バッチ全般
     */
    BATCH_MAIL("メール送信バッチ全般", BatchName.BATCH_MAIL),

    /**
     * 郵便番号自動更新
     */
    BATCH_ZIP_CODE("郵便番号自動更新", BatchName.BATCH_ZIP_CODE),

    /**
     * 事業所郵便番号自動更新
     */
    BATCH_OFFICE_ZIP_CODE("事業所郵便番号自動更新", BatchName.BATCH_OFFICE_ZIP_CODE),

    /**
     * 商品画像更新
     */
    BATCH_GOODSIMAGE_UPDATE("商品画像更新", BatchName.BATCH_GOODSIMAGE_UPDATE),

    /**
     * 商品登録非同期バッチ
     */
    BATCH_GOODS_ASYNCHRONOUS("商品登録非同期バッチ", BatchName.BATCH_GOODS_ASYNCHRONOUS),

    /**
     * 商品グループ在庫状態更新
     */
    BATCH_STOCKSTATUSDISPLAY_UPDATE("商品グループ在庫状態更新", BatchName.BATCH_STOCKSTATUSDISPLAY_UPDATE),

    /**
     * アンケート回答集計
     */
    BATCH_QUESTIONNAIRE_TOTALING("アンケート回答集計", BatchName.BATCH_QUESTIONNAIRE_TOTALING),

    /**
     * サイトマップXML出力バッチ
     */
    BATCH_SITEMAPXML_OUTPUT("サイトマップXML出力バッチ", BatchName.BATCH_SITEMAPXML_OUTPUT),

    /**
     * クリアバッチ
     */
    BATCH_CLEAR("クリアバッチ", BatchName.BATCH_CLEAR),

    /**
     * タスククリーンバッチ
     */
    BATCH_TASK_CLEAN("タスククリーンバッチ", BatchName.BATCH_TASK_CLEAN),

    /**
     * 受注CSV非同期バッチ
     */
    BATCH_ORDER_CSV_ASYNCHRONOUS("受注CSV非同期", BatchName.BATCH_ORDER_CSV_ASYNCHRONOUS),

    // PDR Migrate Customization from here
    /**
     * 会員情報更新バッチ
     */
    BATCH_MEMBER_INFO_UPDATE("会員情報更新バッチ", BatchName.BATCH_MEMBER_INFO_UPDATE),

    /**
     * 商品価格更新
     */
    BATCH_GOODS_PRICE_UPDATE("商品価格更新バッチ", BatchName.BATCH_GOODS_PRICE_UPDATE),

    /**
     * 出荷情報取込バッチ
     */
    BATCH_DELV_PRICE_UPDATE("出荷情報取込バッチ", BatchName.BATCH_DELV_PRICE_UPDATE),

    /**
     * ジョブ監視バッチ
     */
    BATCH_JOB_MONITORING("ジョブ監視", BatchName.BATCH_JOB_MONITORING),
    // PDR Migrate Customization to here
    // 2023-renew No42 from here
    /**
     * デジタルカタログ取込バッチ
     */
    BATCH_DIGITALCATALOG_IMPORT("デジタルカタログ取込バッチ", BatchName.BATCH_DIGITALCATALOG_IMPORT),
    // 2023-renew No42 to here

    // 2023-renew No3 from here
    /**
     * 商品フィード出力バッチ
     */
    BATCH_GOODS_FEED_OUTPUT("商品フィード出力バッチ", BatchName.BATCH_GOODS_FEED_OUTPUT),
    // 2023-renew No3 from here

    // 2023-renew No36-1, No61,67,95 from here
    /**
     * コンテンツフィード出力バッチ
     */
    BATCH_CONTENTS_FEED_OUTPUT("コンテンツフィード出力バッチ", BatchName.BATCH_CONTENTS_FEED_OUTPUT),
    // 2023-renew No36-1, No61,67,95 to here

    // 2023-renew No65 from here
    /**
     * 入荷情報取得バッチ
     */
    BATCH_STOCK_DATA_IMPORT("入荷情報取得バッチ", BatchName.BATCH_STOCK_DATA_IMPORT),
    // 2023-renew No65 from here

    // 2023-renew No41 from here
    /**
     * お気に入りセール通知メール配信バッチ
     */
    BATCH_CUENOTEFC_SALE_MAIL("お気に入りセール通知メール配信バッチ", BatchName.BATCH_CUENOTEFC_SALE_MAIL),

    /**
     * お気に入りセール通知メール配信確認バッチ
     */
    BATCH_CUENOTEFC_SALE_MAIL_CONFIRM("お気に入りセール通知メール配信確認バッチ", BatchName.BATCH_CUENOTEFC_SALE_MAIL_CONFIRM),
    /**
     * セール情報取得バッチ
     */
    BATCH_SALE_DATA_IMPORT("セール情報取得バッチ", BatchName.BATCH_SALE_DATA_IMPORT),
    // 2023-renew No41 to here

    // 2023-renew No21 from here
    /**
     * 一緒によく購入される商品更新バッチ
     */
    BATCH_GOODS_PURCHASED_TOGETHER_UPDATE("一緒によく購入される商品更新バッチ", BatchName.BATCH_GOODS_PURCHASED_TOGETHER_UPDATE);
    // 2023-renew No21 to here

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeBatchName of(String value) {

        HTypeBatchName hType = EnumTypeUtil.getEnumFromValue(HTypeBatchName.class, value);
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
