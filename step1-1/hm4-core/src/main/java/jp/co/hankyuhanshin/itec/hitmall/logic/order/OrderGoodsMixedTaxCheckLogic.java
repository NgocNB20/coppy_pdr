/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

import java.util.List;

/**
 * 商品番号に複数税率が含まれるかチェックするLogic
 *
 * @author okawa
 */
public interface OrderGoodsMixedTaxCheckLogic {

    /**
     * 商品番号に複数税率が含まれるかチェック<br/>
     *
     * @param receiveOrderDto 受注Dto
     * @param messageId       メッセージID
     * @return 警告メッセージList
     */
    List<CheckMessageDto> checkOrderGoodsMixedTax(ReceiveOrderDto receiveOrderDto, String messageId);
}
