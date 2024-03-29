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
 * 手動起動用バッチ処理名：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeManualBatch implements EnumType {

    /**
     * TOP画面集計
     */
    TOP_TOTALING("TOP画面集計", BatchName.BATCH_TOP_TOTALING),

    /**
     * 郵便番号自動更新
     */
    ZIP_CODE("郵便番号自動更新", BatchName.BATCH_ZIP_CODE),

    /**
     * 事業所郵便番号自動更新
     */
    OFFICE_ZIP_CODE("事業所郵便番号自動更新", BatchName.BATCH_OFFICE_ZIP_CODE),

    /**
     * 商品画像更新
     */
    BATCH_GOODSIMAGE_UPDATE("商品画像更新", BatchName.BATCH_GOODSIMAGE_UPDATE),

    /**
     * 商品グループ在庫状態更新
     */
    STOCKSTATUSDISPLAY_UPDATE("商品グループ在庫状態更新", BatchName.BATCH_STOCKSTATUSDISPLAY_UPDATE),

    /**
     * アンケート回答集計
     */
    QUESTIONNAIRE_TOTALING("アンケート回答集計", BatchName.BATCH_QUESTIONNAIRE_TOTALING),

    /**
     * サイトマップXML出力バッチ
     */
    SITEMAPXML_OUTPUT("サイトマップXML出力バッチ", BatchName.BATCH_SITEMAPXML_OUTPUT),

    // PDR Migrate Customization from here
    /**
     * 会員情報更新バッチ
     */
    MEMBER_INFO_UPDATE("会員情報更新", BatchName.BATCH_MEMBER_INFO_UPDATE),

    /**
     * 商品価格更新
     */
    GOODS_PRICE_UPDATE("商品価格更新", BatchName.BATCH_GOODS_PRICE_UPDATE),

    /**
     * タスククリーンバッチ
     */
    DELV_PRICE_UPDATE("出荷情報取込", BatchName.BATCH_DELV_PRICE_UPDATE),
    // PDR Migrate Customization to here

    /**
     * 入荷情報取得バッチ
     */
    STOCK_DATA_IMPORT("入荷情報取得バッチ", BatchName.BATCH_STOCK_DATA_IMPORT),

    /**
     * セール情報取得バッチ
     */
    SALE_DATA_IMPORT("セール情報取得バッチ", BatchName.BATCH_SALE_DATA_IMPORT),

    /**
     * お気に入りセール通知メール配信バッチ
     */
    CUENOTEFC_SALE_MAIL("お気に入りセール通知メール配信バッチ", BatchName.BATCH_CUENOTEFC_SALE_MAIL),

    /**
     * お気に入りセール通知メール配信確認バッチ
     */
    CUENOTEFC_SALE_MAIL_CONFIRM("お気に入りセール通知メール配信確認バッチ", BatchName.BATCH_CUENOTEFC_SALE_MAIL_CONFIRM);

    /**
     * 商品フィード出力バッチ
     */
    // GOODS_FEED_OUTPUT("商品フィード出力バッチ", BatchName.BATCH_GOODS_FEED_OUTPUT),

    /**
     * コンテンツフィード出力バッチ
     */
    // CONTENTS_FEED_OUTPUT("コンテンツフィード出力バッチ", BatchName.BATCH_CONTENTS_FEED_OUTPUT);

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeManualBatch of(String value) {

        HTypeManualBatch hType = EnumTypeUtil.getEnumFromValue(HTypeManualBatch.class, value);
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
