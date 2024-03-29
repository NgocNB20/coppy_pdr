/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySearchForDaoConditionDto;

import java.util.List;

/**
 * 配送方法別送料リスト取得ロジック
 *
 * @author negishi
 * @version $Revision: 1.3 $
 */
public interface DeliveryMethodSelectListGetLogic {

    /**
     * 選択可能な配送方法がない level=error
     */
    public static final String MSGCD_NO_DELIVERY_METHOD = "LSD000201";

    /**
     * 実行メソッド
     *
     * @param conditionDto          配送方法Dao用検索条件DTO
     * @param deliveryMethodSeqList 配送方法SEQリスト
     * @param freeDeliveryFlag      無料配送フラグ
     * @param available             利用可能、不可能どちらかを指定。null..全部 / true..利用可能のみ / false..利用不可能のみ
     * @return 配送Dtoリスト
     */
    List<DeliveryDto> execute(DeliverySearchForDaoConditionDto conditionDto,
                              List<Integer> deliveryMethodSeqList,
                              HTypeFreeDeliveryFlag freeDeliveryFlag,
                              Boolean available);

    /**
     * 実行メソッド
     *
     * @param conditionDto          配送方法Dao用検索条件DTO
     * @param deliveryMethodSeqList 配送方法SEQリスト
     * @param freeDeliveryFlag      無料配送フラグ
     * @return 配送Dtoリスト
     */
    List<DeliveryDto> execute(DeliverySearchForDaoConditionDto conditionDto,
                              List<Integer> deliveryMethodSeqList,
                              HTypeFreeDeliveryFlag freeDeliveryFlag);

    /**
     * 実行メソッド
     *
     * @param deliveryMethodSeqList 配送方法SEQリスト
     * @return 配送Dtoリスト
     */
    List<DeliveryDto> execute(List<Integer> deliveryMethodSeqList);

}
