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
 * メールテンプレートタイプ
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeMailTemplateType implements EnumType {

    /**
     * 仮会員登録
     */
    MEMBER_PREREGISTRATION("仮会員登録", "01"),

    /**
     * 会員登録完了
     */
    MEMBER_REGISTRATION("会員登録完了", "02"),

    /**
     * 会員退会完了
     */
    MEMBER_UNREGISTRATION("会員退会完了", "03"),

    /**
     * パスワード再設定
     */
    PASSWORD_NOTIFICATION("パスワード再設定", "04"),

    /**
     * メールアドレス変更確認
     */
    EMAIL_MODIFICATION("メールアドレス変更確認", "05"),

    /**
     * メールアドレス変更完了
     */
    EMAIL_MODIFICATION_COMPLETE("メールアドレス変更完了", "06"),

    /**
     * 注文確認
     */
    ORDER_CONFIRMATION("注文確認", "07"),

    /**
     * 出荷完了
     */
    SHIPMENT_NOTIFICATION("出荷完了", "08"),

    /**
     * 入荷のお知らせ
     */
    GOODS_ARRIVAL_NOTIFICATION("入荷のお知らせ", "09"),

    /**
     * 受注決済督促
     */
    SETTLEMENT_REMINDER("受注決済督促", "10"),

    /**
     * 受注決済期限切れ
     */
    SETTLEMENT_EXPIRATION_NOTIFICATION("受注決済期限切れ", "11"),

    /**
     * 期限切れポイント告知1
     */
    POINT_INVALID_01("期限切れポイント告知1", "17"),

    /**
     * 期限切れポイント告知2
     */
    POINT_INVALID_02("期限切れポイント告知2", "18"),

    /**
     * 期限切れポイント告知3
     */
    POINT_INVALID_03("期限切れポイント告知3", "19"),

    /**
     * 会員ランク告知1
     */
    MEMBER_RANK_NOTIFICATION_01("会員ランク告知1", "20"),

    /**
     * 会員ランク告知2
     */
    MEMBER_RANK_NOTIFICATION_02("会員ランク告知2", "21"),

    /**
     * 会員ランク告知3
     */
    MEMBER_RANK_NOTIFICATION_03("会員ランク告知3", "22"),

    /**
     * 定期注文確認（初回）
     */
    PERIODIC_ORDER_CONFIRMATION_FIRST("定期注文確認（初回）", "27"),

    /**
     * 定期注文確認
     */
    PERIODIC_ORDER_CONFIRMATION("定期注文確認", "28"),

    /**
     * 定期注文作成エラー
     */
    PERIODIC_ORDER_ERROR("定期注文作成エラー", "29"),

    /**
     * 運用者お問い合わせ
     */
    INQUIRY_OPERATOR("運用者お問い合わせ", "30"),

    // PDR Migrate Customization from here
    /**
     * 会員登録申請受付メール
     */
    MEMBER_REGIST_RECEPTION("会員登録申請受付", "31"),

    // 2023-renew general-mail from here
    /**
     * 顧客番号・パスワード通知メール_初回
     */
    SEND_CUSTOMERNO_PASSWORD("顧客番号・パスワード通知メール_初回", "32"),
    // 2023-renew general-mail to here

    /**
     * 発送完了メール
     */
    SHIPMENT_COMPLETE("発送完了メール", "33"),

    // 2023-renew No25 from here
    /**
     * 会員ID・仮パスワードのお知らせ
     */
    RESEND_CUSTOMERNO_PASSWORD("顧客番号・パスワード通知メール_再送信", "42"),
    // 2023-renew No25 to here

    /**
     * 会員情報変更完了メール (基幹情報変更完了メール)
     */
    CORE_MEMBERINFO_MODIFICATION_COMPLETE("会員情報変更完了メール", "44"),
    // PDR Migrate Customization to here

    // 2023-renew No68 from here
    /**
     * 注文キャンセルメール
     */
    ORDER_CANCEL("注文キャンセル", "45"),
    // 2023-renew No68 to here

    // 2023-renew general-mail from here
    /**
     * 受注向け汎用
     */
    GENERAL_ORDER("受注向け汎用", "97"),

    /**
     * 会員向け汎用
     */
    GENERAL_MEMBER("会員向け汎用", "98"),
    // 2023-renew general-mail to here

    /**
     * 誕生月ポイント付与
     */
    POINT_ADD_BIRTH("誕生月ポイント付与", ""),

    /**
     * 在庫状況
     */
    BATCH_MAIL_STOCK_REPORT("在庫状況を管理者にメール送信", ""),

    /**
     * 受注決済期限切れメール送信通知
     */
    SETTLEMENT_ADMINISTRATOR_MAIL("受注決済期限切れメール送信通知", ""),

    /**
     * 受注決済期限切れメール送信異常
     */
    SETTLEMENT_ADMINISTRATOR_ERROR_MAIL("受注決済期限切れメール送信異常", ""),

    /**
     * オーソリ期限切れ間近注文通知
     */
    AUTH_TIME_LIMIT_ADMINISTRATOR_MAIL("オーソリ期限切れ間近注文通知", ""),

    /**
     * オーソリ期限切れ間近注文異常
     */
    AUTH_TIME_LIMIT_ADMINISTRATOR_ERROR_MAIL("オーソリ期限切れ間近注文異常", ""),

    /**
     * 出荷登録異常
     */
    SHIPMENT_REGIST_ADMINISTRATOR_ERROR_MAIL("出荷登録異常", ""),

    /**
     * 郵便番号マスタ更新通知
     */
    ZIPCODE_ADMINISTRATOR_MAIL("郵便番号マスタ更新通知", ""),

    /**
     * 郵便番号マスタ更新異常
     */
    ZIPCODE_ADMINISTRATOR_ERROR_MAIL("郵便番号マスタ更新異常", ""),

    /**
     * クリア通知
     */
    CLEAR_RESULT_ADMINISTRATOR_MAIL("クリア通知", ""),

    /**
     * クリア異常
     */
    CLEAR_RESULT_ADMINISTRATOR_ERROR_MAIL("クリア異常", ""),

    /**
     * マルチペイメント通知
     */
    MULTIPAYMENT_ADMINISTRATOR_MAIL("マルチペイメント通知", ""),

    /**
     * マルチペイメント異常
     */
    MULTIPAYMENT_ADMINISTRATOR_ERROR_MAIL("マルチペイメント異常", ""),

    /**
     * 在庫開放異常
     */
    STOCK_RELEASE_ADMINISTRATOR_ERROR_MAIL("在庫開放異常", ""),

    /**
     * クレジット決済通知
     */
    CREDIT_LINE_REPORT_MAIL("クレジット決済通知", ""),

    /**
     * クレジット決済作動通知
     */
    CREDIT_LINE_OPERATION_REPORT_MAIL("クレジット決済作動通知", ""),

    /**
     * クレジット決済異常
     */
    CREDIT_LINE_REPORT_ERROR_MAIL("クレジット決済異常", ""),

    /**
     * 商品グループ在庫状態更新異常
     */
    STOCK_STATUS_ADMINISTRATOR_ERROR_MAIL("商品グループ在庫状態更新異常", ""),

    /**
     * サイトマップXML出力通知
     */
    SITEMAP_XML_OUTPUT_ADMINISTRATOR_MAIL("サイトマップXML出力通知", ""),

    /**
     * サイトマップXML出力異常
     */
    SITEMAP_XML_OUTPUT_ADMINISTRATOR_ERROR_MAIL("サイトマップXML出力異常", ""),

    /**
     * 商品グループ規格画像更新（商品一括アップロード）通知
     */
    GOODS_ASYNCHRONOUS_MAIL("商品グループ規格画像更新（商品一括アップロード）通知", ""),

    /**
     * 商品グループ規格画像更新（商品一括アップロード）異常
     */
    GOODS_ASYNCHRONOUS_ERROR_MAIL("商品グループ規格画像更新（商品一括アップロード）異常", ""),

    /**
     * 商品グループ規格画像更新通知
     */
    GOODSIMAGE_UPDATE_MAIL("商品グループ規格画像更新（商品画像更新）通知", ""),

    /**
     * 商品グループ規格画像更新異常
     */
    GOODSIMAGE_UPDATE_ERROR_MAIL("商品グループ規格画像更新（商品画像更新）異常", ""),

    /**
     * GMO会員カードアラート
     */
    GMO_MEMBER_CARD_ALERT("GMO会員カードアラート", ""),

    /**
     * マルチペイメントアラート
     */
    MULTI_PAYMENT_ALERT("マルチペイメントアラート", ""),

    /**
     * 注文データ作成アラート
     */
    ORDER_REGIST_ALERT("注文データ作成アラート", ""),

    /**
     * 請求不整合報告
     */
    SETTLEMENT_MISMATCH("請求不整合報告", ""),

    /**
     * 受注CSVダウンロード完了報告
     */
    ORDER_CSV_ADMINISTRATOR_MAIL("受注CSVダウンロード完了報告", ""),

    /**
     * 受注CSVダウンロードエラー報告
     */
    ORDER_CSV_ADMINISTRATOR_ERROR_MAIL("受注CSVダウンロードエラー報告", ""),

    /**
     * アンケート回答集計バッチ
     */
    QUESTIONNAIRE_TOTALING_ERROR_MAIL("アンケート回答集計バッチ", ""),

    // PDR Migrate Customization from here
    /**
     * 未登録会員照会発生通知
     */
    MEMBERINFO_UNREGIST_INQUIRY("未登録会員照会発生通知", ""),

    /**
     * 住所録登録失敗通知
     */
    ORDER_REGIST_FAILURE_ADDRESS_BOOK_ALERT("住所録登録失敗通知", ""),

    /**
     * 会員情報更新バッチ
     */
    MEMBER_INFO_UPDATE_MAIL("会員情報更新バッチ", ""),

    /**
     * 商品価格更新バッチ
     */
    GOODS_PRICE_UPDATE_MAIL("商品価格更新バッチ", ""),

    /**
     * 出荷情報取込バッチ
     */
    DELV_INFO_IMPORT_MAIL("出荷情報取込バッチ", ""),

    /**
     * ジョブ監視バッチ
     */
    JOB_MONITORING_MAIL("ジョブ監視バッチ", ""),

    /**
     * ジョブ監視バッチ
     */
    JOB_MONITORING_ERROR_MAIL("ジョブ監視バッチ", ""),
    // PDR Migrate Customization to here

    // 2023-renew No42 from here
    /**
     * デジタルカタログ取込バッチ
     */
    DIGITAL_CATALOG_IMPORT_MAIL("デジタルカタログ取込バッチ", ""),

    /**
     * デジタルカタログ取込バッチ
     */
    DIGITAL_CATALOG_IMPORT_ERROR_MAIL("デジタルカタログ取込バッチ", ""),
    // 2023-renew No42 to here

    // 2023-renew No3 from here
    /**
     * 商品フィード出力バッチ
     */
    GOODS_FEED_OUTPUT_MAIL("商品フィード出力バッチ", ""),

    /**
     * 商品フィード出力バッチ
     */
    GOODS_FEED_OUTPUT_ERROR_MAIL("商品フィード出力バッチ", ""),
    // 2023-renew No3 to here

    // 2023-renew No36-1, No61,67,95 from here
    /**
     * コンテンツフィード出力バッチ
     */
    CONTENTS_FEED_OUTPUT_MAIL("コンテンツフィード出力バッチ", ""),

    /**
     * コンテンツフィード出力バッチ
     */
    CONTENTS_FEED_OUTPUT_ERROR_MAIL("コンテンツフィード出力バッチ", ""),
    // 2023-renew No36-1, No61,67,95 to here

    // 2023-renew from here
    /**
     * 入荷お知らせメール
     */
    RESTOCK_SEND_MAIL("入荷お知らせメール", ""),

    /**
     * 入荷お知らせメール異常
     */
    RESTOCK_SEND_ERROR_MAIL("入荷お知らせメール異常", ""),

    /**
     * 配信情報取得
     */
    DELIVERY_CONFIRM("配信情報取得", ""),

    /**
     * 配信情報取得異常
     */
    DELIVERY_CONFIRM_ERROR("配信情報取得異常", ""),
    // 2023-renew to here

    // 2023-renew No60 from here
    /**
     * アカウントロック通知（クレジット登録エラー）
     */
    LOCK_ACCOUNT_BY_REGIST_CREDIT_ERROR("アカウントロック通知（クレジット登録エラー）", ""),
    // 2023-renew No60 to here

    // 2023-renew AddNo2 from here
    /**
     * 会員情報変更通知メール(顧客向け)
     */
    UPDATE_MEMBERINFO_COMPLETE_NOTIFICATION_FOR_MEMBER_MAIL("会員情報変更通知メール(顧客向け)", "43"),

    /**
     * 会員情報変更通知メール(管理者向け)
     */
    UPDATE_MEMBERINFO_COMPLETE_NOTIFICATION_FOR_ADMIN_MAIL("会員情報変更通知メール(管理者向け)", ""),
    // 2023-renew AddNo2 to here

    // 2023-renew No65 from here
    /**
     * 入荷情報取得バッチ
     */
    BATCH_STOCK_DATA_IMPORT_MAIL("入荷情報取得バッチ", ""),

    /**
     * 入荷情報取得バッチ異常
     */
    BATCH_STOCK_DATA_IMPORT_ERROR_MAIL("入荷情報取得バッチ異常", ""),

    /**
     * 入荷情報取得バッチAPI異常
     */
    BATCH_STOCK_DATA_IMPORT_API_ERROR_MAIL("入荷情報取得バッチAPI異常", ""),
    // 2023-renew No65 to here

    // 2023-renew No41 from here

    /**
     * お気に入りセール通知メール配信バッチ
     */
    CUENOTEFC_SALE_MAIL("お気に入りセール通知メール配信バッチ", ""),

    /**
     * お気に入りセール通知メール配信バッチ異常
     */
    CUENOTEFC_SALE_ERROR_MAIL("お気に入りセール通知メール配信バッチ異常", ""),

    /**
     * お気に入りセール通知メール配信確認バッチ
     */
    CUENOTEFC_SALE_MAIL_CONFIRM("お気に入りセール通知メール配信確認バッチ", ""),

    /**
     * お気に入りセール通知メール配信バッチ異常
     */
    CUENOTEFC_SALE_ERROR_MAIL_CONFIRM("お気に入りセール通知メール配信バッチ異常", ""),

    /**
     * セール情報取得バッチ
     */
    BATCH_SALE_DATA_IMPORT_MAIL("セール情報取得バッチ", ""),

    /**
     * セール情報取得バッチ異常
     */
    BATCH_SALE_DATA_IMPORT_ERROR_MAIL("セール情報取得バッチ異常", ""),

    /**
     * セール情報取得バッチAPI異常
     */
    BATCH_SALE_DATA_IMPORT_API_ERROR_MAIL("セール情報取得バッチAPI異常", ""),
    // 2023-renew No3 to here

    // 2023-renew No21 from here
    /**
     * 一緒によく購入される商品更新バッチ
     */
    BATCH_GOODS_PURCHASED_TOGETHER_UPDATE_MAIL("一緒によく購入される商品更新バッチ", ""),

    /**
     * 一緒によく購入される商品更新バッチ
     */
    BATCH_GOODS_PURCHASED_TOGETHER_UPDATE_ERROR_MAIL("一緒によく購入される商品更新バッチ", "");
    // 2023-renew No21 to here

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
    public static HTypeMailTemplateType of(String value) {

        HTypeMailTemplateType hType = EnumTypeUtil.getEnumFromValue(HTypeMailTemplateType.class, value);
        return hType;
    }

}
