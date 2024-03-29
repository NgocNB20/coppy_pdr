/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.orderhistory;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderForHistoryDto;

/**
 * 注文履歴詳細情報取得サービス<br/>
 *
 * @author natume
 * @version $Revision: 1.3 $
 */
public interface OrderHistoryDetailsGetService {

    // SMO0002

    /**
     * 受注サマリ情報取得失敗エラー<br/>
     * <code>MSGCD_ORDERSUMMARYENTITYDTO_NULL</code>
     */
    String MSGCD_ORDERSUMMARYENTITYDTO_NULL = "SMO000201";

    /**
     * 別会員の受注情報エラー<br/>
     * <code>MSGCD_MEMBER_DEFERENT_ORDER</code>
     */
    String MSGCD_MEMBER_DEFERENT_ORDER = "SMO000202";

    /**
     * 受注ご注文主情報取得失敗<br/>
     * <code>MSGCD_ORDERPERSONENTITYDTO_NULL</code><br/>
     */
    public static final String MSGCD_ORDERPERSONENTITYDTO_NULL = "SOO000601";

    /**
     * 受注配送情報取得失敗<br/>
     * <code>MSGCD_ORDERDELIVERYENTITYDTO_NULL</code><br/>
     */
    String MSGCD_ORDERDELIVERYENTITYDTO_NULL = "SOO000602";

    /**
     * 受注商品情報が失敗<br/>
     * <code>MSGCD_ORDERGOODSENTITYDTOLIST_EMPTY</code><br/>
     */
    String MSGCD_ORDERGOODSENTITYDTOLIST_EMPTY = "SOO000603";

    /**
     * 受注決済情報取得失敗<br/>
     * <code>MSGCD_ORDERSETTLEMENTENTITYDTO_NULL</code><br/>
     */
    String MSGCD_ORDERSETTLEMENTENTITYDTO_NULL = "SOO000604";

    /**
     * 決済方法情報取得失敗<br/>
     * <code>MSGCD_SETTLEMENTMETHODENTITYDTO_NULL</code><br/>
     */
    String MSGCD_SETTLEMENTMETHODENTITYDTO_NULL = "SOO000605";

    /**
     * 配送方法情報取得失敗<br/>
     * <code>MSGCD_DELIVERYMETHODENTITYDTO_NULL</code><br/>
     */
    String MSGCD_DELIVERYMETHODENTITYDTO_NULL = "SOO000606";

    /**
     * コンビニ名称取得失敗<br/>
     * <code>MSGCD_CONVENIENCEENTITYDTO_NULL</code><br/>
     */
    String MSGCD_CONVENIENCEENTITYDTO_NULL = "SOO000608";

    /**
     * 注文履歴詳細情報取得処理<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param orderCode     受注番号
     * @return 受注Dto
     */
    ReceiveOrderForHistoryDto execute(Integer memberInfoSeq, String orderCode);
}
