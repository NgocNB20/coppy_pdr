/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.PaymentRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;

import java.math.BigDecimal;

/**
 * 入金登録処理<br/>
 *
 * @author yamaguchi
 * @version $Revision: 1.5 $
 */
public interface OrderReceiptOfMoneyRegistService {

    /**
     * 受注履歴連番が異なる場合（他者により更新の可能性あり）
     */
    public static final String MSGCD_ORDERVERSIONNO_DEF = "SOO002001";

    /**
     * 受注インデックスが取得できない場合
     */
    public static final String MSGCD_ORDERINDEX_NULL = "SOO002002";

    /**
     * 入金累計最大値オーバーエラー
     */
    public static final String MAX_RECEIPT_TOTAL_PRICE_OVER = "SOO002003";

    /**
     * 入金額0円
     */
    public static final String MSGCD_RECEIPTPRICE_ZERO_INFO = "SOO002004";

    /**
     * 処理件数0
     */
    public static final String MSGCD_SUCCESS_ZERO = "SOO002005";

    /**
     * 入金累計最小値オーバーエラー
     */
    public static final String MIN_RECEIPT_TOTAL_PRICE_OVER = "SOO002006";

    /**
     * 入金累計最大値
     */
    public static final BigDecimal MAX_RECEIPT_TOTAL_PRICE = new BigDecimal(99999999);

    /**
     * 入金累計最小値
     */
    public static final BigDecimal MIN_RECEIPT_TOTAL_PRICE = new BigDecimal(-99999999);

    /**
     * 実行メソッド<br/>
     * レコードロックを自動で行う<br/>
     *
     * @param paymentRegistDto 入金登録情報
     * @return 処理件数（受注単位)
     */
    CheckMessageDto execute(PaymentRegistDto paymentRegistDto, String administratorName);

    /**
     * 実行メソッド<br/>
     * レコードのロック状態を指定して実行
     *
     * @param paymentRegistDto   入金登録情報
     * @param orderSummaryEntity レコードロック済みの受注サマリーエンティティ
     * @return 処理件数（受注単位)
     */
    CheckMessageDto execute(PaymentRegistDto paymentRegistDto,
                            OrderSummaryEntity orderSummaryEntity,
                            String administratorName);
}
