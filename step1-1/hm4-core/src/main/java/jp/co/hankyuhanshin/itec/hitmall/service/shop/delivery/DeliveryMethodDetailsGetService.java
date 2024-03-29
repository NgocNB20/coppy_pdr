/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryMethodDetailsDto;

import java.util.List;
import java.util.Map;

/**
 * 配送方法詳細取得サービス<br/>
 *
 * @author negishi
 * @version $Revision: 1.1 $
 */
public interface DeliveryMethodDetailsGetService {

    /**
     * サービス実行
     *
     * @param deliveryMethodSeq 配送方法SEQ
     * @return 配送方法詳細DTO
     */
    DeliveryMethodDetailsDto execute(Integer deliveryMethodSeq);

    /**
     * サービス実行
     *
     * @param deliveryMethodSeqList 配送方法SEQリスト
     * @return 配送情報マスタ
     */
    Map<Integer, DeliveryMethodDetailsDto> execute(List<Integer> deliveryMethodSeqList);

}
