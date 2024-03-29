/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryMethodDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;

import java.util.List;

/**
 * 配送方法更新サービス
 *
 * @author negishi
 * @version $Revision: 1.1 $
 */
public interface DeliveryMethodUpdateService {

    /**
     * 配送方法更新
     *
     * @param deliveryMethodEntity 配送方法エンティティ
     * @return 更新件数
     */
    int execute(DeliveryMethodEntity deliveryMethodEntity);

    /**
     * 配送方法更新。複数
     *
     * @param deliveryMethodEntityList 配送方法エンティティリスト
     * @return 更新件数
     */
    int execute(List<DeliveryMethodEntity> deliveryMethodEntityList);

    /**
     * 配送方法更新
     *
     * @param deliveryMethodDetailsDto 配送方法詳細DTO
     * @return 更新件数
     */
    int execute(DeliveryMethodDetailsDto deliveryMethodDetailsDto);

}
