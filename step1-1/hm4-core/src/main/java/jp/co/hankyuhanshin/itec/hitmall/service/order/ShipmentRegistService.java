/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ShipmentRegistDto;

/**
 * 出荷登録サービス<br/>
 *
 * @author yamaguchi
 * @author tomo (itec) 2011/08/29 #2717 GMO側に取引データが存在しない場合の対応
 */
public interface ShipmentRegistService {

    /**
     * 受注履歴連番が異なる場合
     */
    public static final String MSGCD_ORDERVERSIONNO_DEF = "SOO002201";

    /**
     * 受注インデックス取得不能の場合
     */
    public static final String MSGCD_ORDERINDEX_NULL = "SOO002202";

    /**
     * 受注DB登録で例外が発生した場合
     */
    public static final String MSGCD_DB_INSERT_FATAL = "SOO002203";

    /**
     * 在庫更新件数が商品SEQ件数と異なる場合
     */
    public static final String MSGCD_STOCK_UPDATE_ERROR = "SOO002204";

    /**
     * 処理対象が出荷済みの場合
     */
    public static final String MSGCD_SHIPMENT_COMPLETION_ERROR = "SOO002205";

    /**
     * 処理対象がキャンセル済みの場合
     */
    public static final String MSGCD_CANCEL_ERROR = "SOO002206";

    /**
     * 処理対象が請求決済エラーの場合
     */
    public static final String MSGCD_EMERGENCY_ERROR = "SOO002207";

    /**
     * 処理対象が請求エラーかつ受注状態が出荷完了ではない場合
     */
    public static final String MSGCD_EMERGENCY_BEFOLE_SHIPMENT_ERROR = "PKG-4177-003-A-W";
    /**
     * オーソリ通信（売上計上）が失敗した場合
     */
    // バッチからも使用しているためEがついている。
    public static final String MSGCD_SALES_AUTHORI_ERROR = "SOO002208E";

    /**
     * クレジット決済またはAmazonPay決済の処理区分が想定外の場合
     */
    public static final String MSGCD_AUTHORI_JOBCD_ERROR = "SOO002209";

    /**
     * 受注サマリーの取得（レコードロック）に失敗した場合
     */
    public static final String MSGCD_SELECT_FORUPDATE_FATAL = "SOO002210";

    /**
     * 決済方法情報の取得失敗に失敗した場合
     */
    public static final String MSGCD_SETTLEMENTMETHOD_NULL = "SOO002211";

    /**
     * マルチペイメント請求情報の取得に失敗した場合
     */
    public static final String MSGCD_MULPAYBILL_NULL = "SOO002212";

    /**
     * マルチペイメント通信中に例外が発生した場合
     */
    public static final String MSGCD_MULPAY_ERROR = "SOO002213";

    /**
     * 無効な取引情報を使用して売上にしようとした場合
     */
    public static final String MSGCD_INVALID_TRAN_ID_WARNING = "SOO002214";

    /**
     * 売上にしようとした仮売上の有効期限が切れていた場合
     */
    public static final String MSGCD_AUTH_EXPIRED_WARNING = "SOO002215";

    /**
     * 出荷日に受注商品の予約終了前の日付を指定した場合
     */
    public static final String MSGCD_SHIPMENT_DATE_BEFORE_SALE_END = "PKG-3553-048-L-";

    /**
     * 実行メソッド<br/>
     *
     * @param shipmentRegistDto 出荷登録DTO
     * @return 処理結果メッセージ（オーソリエラー発生時のみ）
     */
    CheckMessageDto execute(ShipmentRegistDto shipmentRegistDto, String administratorName);
}
