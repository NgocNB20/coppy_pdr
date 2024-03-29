/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.orderhistory;

import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.settlement.SettlementMethodEntity;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 注文履歴一覧Dtoクラス
 *
 * @author DtoGenerator
 * @version $Revision: 1.3 $
 */
@Data
@Component
@Scope("prototype")
public class OrderHistoryListDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受注サマリエンティティ
     */
    private OrderSummaryEntity orderSummaryEntity;

    /**
     * 決済方法エンティティ
     */
    private SettlementMethodEntity settlementMethodEntity;

    /**
     * クーポン割引額
     */
    private BigDecimal couponDiscountPrice;
}
