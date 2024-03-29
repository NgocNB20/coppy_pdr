/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.constant.type;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.BatchName;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * バッチ処理名：列挙型
 *
 * @author kaneda
 */
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
    BATCH_JOB_MONITORING("ジョブ監視", BatchName.BATCH_JOB_MONITORING);
    // PDR Migrate Customization to here

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
    public static HTypeBatchName of(String value) {

        HTypeBatchName hType = EnumTypeUtil.getEnumFromValue(HTypeBatchName.class, value);
        return hType;
    }
}
