/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

/**
 * 受注取得サービス
 *
 * @author ueshima
 */
public interface ReceiveOrderGetService {

    /**
     * 受注ご注文主情報取得失敗<br/>
     * <code>MSGCD_ORDERPERSONENTITYDTO_NULL</code><br/>
     */
    public static final String MSGCD_ORDERPERSONENTITYDTO_NULL = "SOO000601";

    /**
     * 受注決済情報取得失敗<br/>
     * <code>MSGCD_ORDERSETTLEMENTENTITYDTO_NULL</code><br/>
     */
    public static final String MSGCD_ORDERSETTLEMENTENTITYDTO_NULL = "SOO000604";

    /**
     * 受注配送情報取得失敗<br/>
     * <code>MSGCD_ORDERDELIVERYENTITYDTO_NULL</code><br/>
     */
    public static final String MSGCD_ORDERDELIVERYENTITYDTO_NULL = "SOO000602";

    /**
     * 決済方法情報取得失敗<br/>
     * <code>MSGCD_SETTLEMENTMETHODENTITYDTO_NULL</code><br/>
     */
    public static final String MSGCD_SETTLEMENTMETHODENTITYDTO_NULL = "SOO000605";

    /**
     * 受注サマリー情報取得失敗<br/>
     * <code>MSGCD_ORDERSUMMARYENTITYDTO_NULL</code><br/>
     */
    public static final String MSGCD_ORDERSUMMARYENTITYDTO_NULL = "SOO000607";

    /**
     * コンビニ名称取得失敗<br/>
     * <code>MSGCD_CONVENIENCEENTITYDTO_NULL</code><br/>
     */
    public static final String MSGCD_CONVENIENCEENTITYDTO_NULL = "SOO000608";

    /**
     * 受注情報を取得する。
     *
     * @param orderSeq       受注SEQ
     * @param orderVersionNo 受注履歴番号
     * @return 受注Dto
     */
    ReceiveOrderDto execute(Integer orderSeq, Integer orderVersionNo);

    /**
     * 受注情報を取得する。
     *
     * @param orderCode      受注番号
     * @param orderVersionNo 受注履歴連番
     * @return 受注情報
     */
    ReceiveOrderDto execute(String orderCode, Integer orderVersionNo);

    /**
     * 受注情報を取得する。
     *
     * @param orderCode 受注番号
     * @return 受注情報
     */
    ReceiveOrderDto execute(String orderCode);

    /**
     * 受注情報を取得する。
     *
     * @param orderSeq 受注SEQ
     * @return 受注情報
     */
    ReceiveOrderDto execute(Integer orderSeq);

}
