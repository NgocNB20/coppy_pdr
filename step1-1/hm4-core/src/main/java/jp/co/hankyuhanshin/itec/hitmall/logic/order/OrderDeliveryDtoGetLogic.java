/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;

/**
 * 受注配送取得Logic
 *
 * @author s_tsuru
 */
public interface OrderDeliveryDtoGetLogic {

    /**
     * 配送方法情報取得失敗<br/>
     * <code>MSGCD_DELIVERYMETHODENTITYDTO_NULL</code><br/>
     */
    public static final String MSGCD_DELIVERYMETHODENTITYDTO_NULL = "SOO000606";

    /**
     * 受注商品情報が失敗<br/>
     * <code>MSGCD_ORDERGOODSENTITYDTOLIST_EMPTY</code><br/>
     */
    public static final String MSGCD_ORDERGOODSENTITYDTOLIST_EMPTY = "SOO000603";

    /**
     * メソッド実行<br/>
     *
     * @param orderSeq               受注SEQ
     * @param orderDeliveryVersionNo 受注配送連番
     * @param orderGoodsVersionNo    受注商品連番
     * @return List&lt;OrderDeliveryDto&gt;
     */
    OrderDeliveryDto execute(Integer orderSeq, Integer orderDeliveryVersionNo, Integer orderGoodsVersionNo);
}
