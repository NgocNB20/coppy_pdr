/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodPriceCommissionFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementMethodDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodPriceCommissionEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodMaxOrderDisplayGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodPriceCommissionRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement.SettlementMethodRegistService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 決済方法登録<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.1 $
 */
@Service
public class SettlementMethodRegistServiceImpl extends AbstractShopService implements SettlementMethodRegistService {

    /**
     * 決済方法登録ロジック
     */
    private final SettlementMethodRegistLogic settlementMethodRegistLogic;

    /**
     * 表示順取得ロジック
     */
    private final SettlementMethodMaxOrderDisplayGetLogic settlementMethodMaxOrderDisplayGetLogic;

    /**
     * 決済方法金額別手数料登録ロジック
     */
    private final SettlementMethodPriceCommissionRegistLogic settlementMethodPriceCommissionRegistLogic;

    /**
     * 配送方法取得ロジック
     */
    private final DeliveryMethodGetLogic deliveryMethodGetLogic;

    @Autowired
    public SettlementMethodRegistServiceImpl(SettlementMethodRegistLogic settlementMethodRegistLogic,
                                             SettlementMethodMaxOrderDisplayGetLogic settlementMethodMaxOrderDisplayGetLogic,
                                             SettlementMethodPriceCommissionRegistLogic settlementMethodPriceCommissionRegistLogic,
                                             DeliveryMethodGetLogic deliveryMethodGetLogic) {

        this.settlementMethodRegistLogic = settlementMethodRegistLogic;
        this.settlementMethodMaxOrderDisplayGetLogic = settlementMethodMaxOrderDisplayGetLogic;
        this.settlementMethodPriceCommissionRegistLogic = settlementMethodPriceCommissionRegistLogic;
        this.deliveryMethodGetLogic = deliveryMethodGetLogic;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param settlementMethodDto 決済方法DTO
     * @return 処理件数
     */
    @Override
    public int execute(SettlementMethodDto settlementMethodDto) {

        Integer shopSeq = 1001;

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("settlementMethodDto", settlementMethodDto);
        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);

        SettlementMethodEntity settlementMethodEntity = settlementMethodDto.getSettlementMethodEntity();

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("settlementMethodEntity", settlementMethodEntity);

        // 決済方法登録
        // 表示順取得ロジック実行
        int orderDisplay = settlementMethodMaxOrderDisplayGetLogic.execute(1001);
        // 表示順設定
        settlementMethodEntity.setOrderDisplay(orderDisplay);
        settlementMethodEntity.setShopSeq(shopSeq);

        // クレジットでない かつ 後払の場合は、支払期限猶予日数、期限後取消猶予日数に０をセットする
        if (!HTypeSettlementMethodType.CREDIT.equals(settlementMethodEntity.getSettlementMethodType())
            && HTypeBillType.POST_CLAIM.equals(settlementMethodEntity.getBillType())) {
            settlementMethodEntity.setPaymentTimeLimitDayCount(0);
            settlementMethodEntity.setCancelTimeLimitDayCount(0);
        }
        int res = settlementMethodRegistLogic.execute(settlementMethodEntity);

        // 手数料フラグ
        HTypeSettlementMethodPriceCommissionFlag flag = settlementMethodEntity.getSettlementMethodPriceCommissionFlag();

        // 手数料フラグ=金額別
        if (HTypeSettlementMethodPriceCommissionFlag.EACH_AMOUNT == flag) {
            List<SettlementMethodPriceCommissionEntity> priceCommissionList =
                            settlementMethodDto.getSettlementMethodPriceCommissionEntityList();
            // 決済方法金額別手数料リスト登録処理
            int count = registSettlementMethodPriceCommission(priceCommissionList,
                                                              settlementMethodEntity.getSettlementMethodSeq()
                                                             );
            if (count != priceCommissionList.size()) {
                throwMessage(MSGCD_COMMISSION_INSERT_ERROR);
            }
        }

        // 配送方法削除チェック
        Integer deliveryMethodSeq = settlementMethodEntity.getDeliveryMethodSeq();
        if (deliveryMethodSeq != null) {
            DeliveryMethodEntity deliveryMethod = deliveryMethodGetLogic.execute(deliveryMethodSeq);
            if (deliveryMethod == null) {
                throwMessage(MSGCD_DELIVERY_NULL_ERROR);
            } else if (HTypeOpenDeleteStatus.DELETED == deliveryMethod.getOpenStatusPC()
                       || HTypeOpenDeleteStatus.DELETED == deliveryMethod.getOpenStatusMB()) {
                throwMessage(MSGCD_DELIVERY_DELETE_ERROR, new Object[] {deliveryMethod.getDeliveryMethodName()});
            }
        }

        return res;
    }

    /**
     * 決済方法金額別手数料リスト登録処理<br/>
     *
     * @param priceCommissionList 決済方法金額別手数料登録リスト
     * @param settlementMethodSeq 決済SEQ
     * @return 処理件数
     */
    protected int registSettlementMethodPriceCommission(List<SettlementMethodPriceCommissionEntity> priceCommissionList,
                                                        Integer settlementMethodSeq) {
        int count = 0;

        for (SettlementMethodPriceCommissionEntity settlementMethodPriceCommissionEntity : priceCommissionList) {
            // 決済方法金額別手数料登録処理
            settlementMethodPriceCommissionEntity.setSettlementMethodSeq(settlementMethodSeq);
            count += settlementMethodPriceCommissionRegistLogic.execute(settlementMethodPriceCommissionEntity);
        }

        return count;
    }

}
