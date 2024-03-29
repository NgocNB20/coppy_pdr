/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryImpossibleAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliverySpecialChargeAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleAreaConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleAreaResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryMethodDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySpecialChargeAreaConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySpecialChargeAreaResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodTypeCarriageEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleAreaCountGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodTypeCarriageListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliverySpecialChargeAreaCountGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryMethodDetailsGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配送方法詳細取得サービス
 *
 * @author negishi
 * @version $Revision: 1.1 $
 */
@Service
public class DeliveryMethodDetailsGetServiceImpl extends AbstractShopService
                implements DeliveryMethodDetailsGetService {

    /**
     * 配送方法取得ロジック
     */
    private final DeliveryMethodGetLogic deliveryMethodGetLogic;

    /**
     * 配送区分別送料取得ロジック
     */
    private final DeliveryMethodTypeCarriageListGetLogic deliveryMethodTypeCarriageListGetLogic;

    /**
     * 配送特別料金エリアカウント取得ロジック
     */
    private final DeliverySpecialChargeAreaCountGetLogic deliverySpecialChargeAreaCountGetLogic;

    /**
     * 配送不可能エリアカウント取得ロジック
     */
    private final DeliveryImpossibleAreaCountGetLogic deliveryImpossibleAreaCountGetLogic;

    /**
     * 配送不可能エリアDao
     */
    private final DeliveryImpossibleAreaDao deliveryImpossibleAreaDao;

    /**
     * 配送特別料金エリアDao
     */
    private final DeliverySpecialChargeAreaDao deliverySpecialChargeAreaDao;

    @Autowired
    public DeliveryMethodDetailsGetServiceImpl(DeliveryMethodGetLogic deliveryMethodGetLogic,
                                               DeliveryMethodTypeCarriageListGetLogic deliveryMethodTypeCarriageListGetLogic,
                                               DeliverySpecialChargeAreaCountGetLogic deliverySpecialChargeAreaCountGetLogic,
                                               DeliveryImpossibleAreaCountGetLogic deliveryImpossibleAreaCountGetLogic,
                                               DeliveryImpossibleAreaDao deliveryImpossibleAreaDao,
                                               DeliverySpecialChargeAreaDao deliverySpecialChargeAreaDao) {
        this.deliveryMethodGetLogic = deliveryMethodGetLogic;
        this.deliveryMethodTypeCarriageListGetLogic = deliveryMethodTypeCarriageListGetLogic;
        this.deliverySpecialChargeAreaCountGetLogic = deliverySpecialChargeAreaCountGetLogic;
        this.deliveryImpossibleAreaCountGetLogic = deliveryImpossibleAreaCountGetLogic;
        this.deliveryImpossibleAreaDao = deliveryImpossibleAreaDao;
        this.deliverySpecialChargeAreaDao = deliverySpecialChargeAreaDao;
    }

    /**
     * サービス実行
     *
     * @param deliveryMethodSeq 配送方法SEQ
     * @return 配送方法詳細DTO
     */
    @Override
    public DeliveryMethodDetailsDto execute(Integer deliveryMethodSeq) {
        // パラメータチェック
        ArgumentCheckUtil.assertGreaterThanZero("deliveryMethodSeq", deliveryMethodSeq);

        // 配送方法取得ロジック実行
        DeliveryMethodEntity deliveryMethodEntity = deliveryMethodGetLogic.execute(deliveryMethodSeq);

        // 配送区分別送料取得ロジック実行
        List<DeliveryMethodTypeCarriageEntity> deliveryMethodTypeCarriageEntityList =
                        deliveryMethodTypeCarriageListGetLogic.execute(deliveryMethodSeq);

        // 配送特別料金エリアカウントロジック実行
        int deliverySpecialChargeAreaCount = deliverySpecialChargeAreaCountGetLogic.execute(deliveryMethodSeq);

        // 配送不可能エリアカウント取得ロジック実行
        int deliveryImpossibleAreaCount = deliveryImpossibleAreaCountGetLogic.execute(deliveryMethodSeq);

        DeliveryMethodDetailsDto deliveryMethodDetailsDto = getComponent(DeliveryMethodDetailsDto.class);
        deliveryMethodDetailsDto.setDeliveryMethodEntity(deliveryMethodEntity);
        deliveryMethodDetailsDto.setDeliveryMethodTypeCarriageEntityList(deliveryMethodTypeCarriageEntityList);
        deliveryMethodDetailsDto.setDeliverySpecialChargeAreaCount(deliverySpecialChargeAreaCount);
        deliveryMethodDetailsDto.setDeliveryImpossibleAreaCount(deliveryImpossibleAreaCount);

        return deliveryMethodDetailsDto;
    }

    /**
     * サービス実行
     *
     * @param deliveryMethodSeqList 配送方法SEQリスト
     * @return 配送情報マスタ
     */
    @Override
    public Map<Integer, DeliveryMethodDetailsDto> execute(List<Integer> deliveryMethodSeqList) {

        Map<Integer, DeliveryMethodDetailsDto> dMap = new HashMap<>();

        for (Integer deliveryMethodSeq : deliveryMethodSeqList) {

            DeliveryMethodDetailsDto deliveryMethodDetailsDto = execute(deliveryMethodSeq);

            // 配送不可能エリアを設定
            deliveryMethodDetailsDto.setDeliveryImpossibleAreaResultDtoList(getImpossibleArea(deliveryMethodSeq));

            // 配送特別料金エリアを設定
            deliveryMethodDetailsDto.setDeliverySpecialChargeAreaResultDtoList(
                            getySpecialChargeAreaDao(deliveryMethodSeq));

            dMap.put(deliveryMethodSeq, deliveryMethodDetailsDto);
        }

        return dMap;
    }

    /**
     * 配送不可能エリア詳細リスト取得
     *
     * @param deliveryMethodSeq 配送方法SEQ
     * @return 配送不可能エリア詳細リスト
     */
    public List<DeliveryImpossibleAreaResultDto> getImpossibleArea(Integer deliveryMethodSeq) {

        DeliveryImpossibleAreaConditionDto deliveryImpossibleAreaConditionDto =
                        getComponent(DeliveryImpossibleAreaConditionDto.class);
        deliveryImpossibleAreaConditionDto.setDeliveryMethodSeq(deliveryMethodSeq);

        // 配送不可能エリアDTOリスト取得
        return deliveryImpossibleAreaDao.getDeliveryImpossibleAreaListInPrefecture(deliveryImpossibleAreaConditionDto);
    }

    /**
     * 配送特別料金エリア詳細リスト取得
     *
     * @param deliveryMethodSeq 配送方法SEQ
     * @return 配送不可能エリア詳細リスト
     */
    private List<DeliverySpecialChargeAreaResultDto> getySpecialChargeAreaDao(Integer deliveryMethodSeq) {

        DeliverySpecialChargeAreaConditionDto deliverySpecialChargeAreaConditionDto =
                        getComponent(DeliverySpecialChargeAreaConditionDto.class);
        deliverySpecialChargeAreaConditionDto.setDeliveryMethodSeq(deliveryMethodSeq);

        // 配送不可能エリアDTOリスト取得
        return deliverySpecialChargeAreaDao.getDeliverySpecialChargeAreaListInPrefecture(
                        deliverySpecialChargeAreaConditionDto);
    }
}
