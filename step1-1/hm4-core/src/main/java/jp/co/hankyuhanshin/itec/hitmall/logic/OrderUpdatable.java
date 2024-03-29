/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

/**
 * 注文内容をもとに受注情報を修正する抽象クラス<br/>
 * 受注修正処理を行なう場合、このクラスを継承してください。<br/>
 *
 * @author hirata
 * @version $Revision: 1.0 $
 */
public interface OrderUpdatable {

    /**
     * マルチペイメント通信処理種別（取消）
     */
    public static final int MULPAY_TORIKESHI = 0;
    /**
     * マルチペイメント通信処理種別（変更）
     */
    public static final int MULPAY_HENKOU = 1;
    /**
     * マルチペイメント通信処理種別（金額変更）
     */
    public static final int MULPAY_KINGAKUHENKOU = 2;

    /**
     * 受注サマリー取得エラー
     */
    public static final String MSGCD_SELECT_SUMMARY_ERROR = "LOX000301";

    /**
     * 受注キャンセルエラー
     */
    public static final String MSGCD_ORDER_CANCEL_ERROR = "LOX000302";

    /**
     * 在庫確保処理エラー
     */
    public static final String MSGCD_GOODS_RESERVE_ERROR = "LOX000303";

    /**
     * 出荷状態変更不可能エラー
     */
    public static final String MSGCD_DELIVERY_STATUS_CHANGE_ERROR = "LOX000304";

    /**
     * 出荷日変更不可能エラー
     */
    public static final String MSGCD_DELIVERY_DATE_CHANGE_ERROR = "LOX000305";

    /**
     * クレジット決済異常エラー
     */
    public static final String MSGCD_CREDIT_EMERGENCY_ERROR = "LOX000306";

    /**
     * ロールバック処理中に例外が発生
     */
    public static final String MSGCD_ORDER_ROLLBACK_ERROR = "LOX000308";

    /**
     * 受注修正処理中に例外が発生
     */
    public static final String MSGCD_ORDER_CHANGE_SYS_ERROR = "LOX000309";

    /**
     * コンビニ請求情報更新処理中に例外が発生
     */
    public static final String MSGCD_CONVENIBILL_CHANGE_ERROR = "LOX000310";

    /**
     * 受注修正処理。<br/>
     *
     * @param receiveOrderDto 受注DTO
     */
    public void execute(ReceiveOrderDto receiveOrderDto);
}
