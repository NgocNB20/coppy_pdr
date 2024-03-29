/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementMethodDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodPriceCommissionEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodPriceCommissionListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryMethodGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement.SettlementMethodConfigGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 決済方法詳細設定取得<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.1 $
 */
@Service
public class SettlementMethodConfigGetServiceImpl extends AbstractShopService
                implements SettlementMethodConfigGetService {

    /**
     * 決済方法エンティティ取得ロジック
     */
    private final SettlementMethodGetLogic settlementMethodGetLogic;

    /**
     * 決済方法金額別手数料リスト取得ロジック
     */
    private final SettlementMethodPriceCommissionListGetLogic settlementMethodPriceCommissionListGetLogic;

    /**
     * 配送方法エンティティ取得
     */
    private final DeliveryMethodGetService deliveryMethodGetService;

    @Autowired
    public SettlementMethodConfigGetServiceImpl(SettlementMethodGetLogic settlementMethodGetLogic,
                                                SettlementMethodPriceCommissionListGetLogic settlementMethodPriceCommissionListGetLogic,
                                                DeliveryMethodGetService deliveryMethodGetService) {

        this.settlementMethodGetLogic = settlementMethodGetLogic;
        this.settlementMethodPriceCommissionListGetLogic = settlementMethodPriceCommissionListGetLogic;
        this.deliveryMethodGetService = deliveryMethodGetService;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param settlementMethodSeq 決済方法SEQ
     * @return 決済方法DTO
     */
    @Override
    public SettlementMethodDto execute(Integer settlementMethodSeq) {

        // 共通情報取得
        Integer shopSeq = 1001;

        // パラメータチェック
        ArgumentCheckUtil.assertGreaterThanZero("settlementMethodSeq", settlementMethodSeq);

        // 決済方法取得
        SettlementMethodEntity settlementMethodEntity = settlementMethodGetLogic.execute(settlementMethodSeq, shopSeq);

        if (settlementMethodEntity == null) {
            return null;
        }

        // 決済方法金額別手数料リスト取得
        List<SettlementMethodPriceCommissionEntity> settlementMethodPriceCommissionEntityList =
                        settlementMethodPriceCommissionListGetLogic.execeute(settlementMethodSeq);

        // 戻り値に取得値をセット
        SettlementMethodDto settlementMethodDto = ApplicationContextUtility.getBean(SettlementMethodDto.class);
        settlementMethodDto.setSettlementMethodEntity(settlementMethodEntity);
        settlementMethodDto.setSettlementMethodPriceCommissionEntityList(settlementMethodPriceCommissionEntityList);

        // 配送方法名の取得
        Integer deliveryMethodSeq = settlementMethodEntity.getDeliveryMethodSeq();
        if (deliveryMethodSeq != null) {
            DeliveryMethodEntity deliveryMethod = deliveryMethodGetService.execute(deliveryMethodSeq);
            settlementMethodDto.setDeliveryMethodName(deliveryMethod.getDeliveryMethodName());
        }

        return settlementMethodDto;
    }

}
