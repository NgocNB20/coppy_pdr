/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodPriceCommissionFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementMethodDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodPriceCommissionEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodPriceCommissionDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodPriceCommissionListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodPriceCommissionRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodPriceCommissionUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement.SettlementMethodUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 決済方法更新<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.1 $
 */
@Service
public class SettlementMethodUpdateServiceImpl extends AbstractShopService implements SettlementMethodUpdateService {

    /**
     * 決済方法チェックロジック
     */
    private final SettlementMethodCheckLogic settlementMethodCheckLogic;

    /**
     * 決済方法取得ロジック
     */
    private final SettlementMethodGetLogic settlementMethodGetLogic;

    /**
     * 決済方法更新ロジック
     */
    private final SettlementMethodUpdateLogic settlementMethodUpdateLogic;

    /**
     * 決済方法金額別手数料削除ロジック
     */
    private final SettlementMethodPriceCommissionDeleteLogic settlementMethodPriceCommissionDeleteLogic;

    /**
     * 決済方法金額別手数料登録ロジック
     */
    private final SettlementMethodPriceCommissionRegistLogic settlementMethodPriceCommissionRegistLogic;

    /**
     * 決済方法金額別手数料リスト取得ロジック
     */
    private final SettlementMethodPriceCommissionListGetLogic settlementMethodPriceCommissionListGetLogic;

    /**
     * 決済方法金額別手数料更新ロジック
     */
    private final SettlementMethodPriceCommissionUpdateLogic settlementMethodPriceCommissionUpdateLogic;

    @Autowired
    public SettlementMethodUpdateServiceImpl(SettlementMethodCheckLogic settlementMethodCheckLogic,
                                             SettlementMethodGetLogic settlementMethodGetLogic,
                                             SettlementMethodUpdateLogic settlementMethodUpdateLogic,
                                             SettlementMethodPriceCommissionDeleteLogic settlementMethodPriceCommissionDeleteLogic,
                                             SettlementMethodPriceCommissionRegistLogic settlementMethodPriceCommissionRegistLogic,
                                             SettlementMethodPriceCommissionListGetLogic settlementMethodPriceCommissionListGetLogic,
                                             SettlementMethodPriceCommissionUpdateLogic settlementMethodPriceCommissionUpdateLogic) {

        this.settlementMethodCheckLogic = settlementMethodCheckLogic;
        this.settlementMethodGetLogic = settlementMethodGetLogic;
        this.settlementMethodUpdateLogic = settlementMethodUpdateLogic;
        this.settlementMethodPriceCommissionDeleteLogic = settlementMethodPriceCommissionDeleteLogic;
        this.settlementMethodPriceCommissionRegistLogic = settlementMethodPriceCommissionRegistLogic;
        this.settlementMethodPriceCommissionListGetLogic = settlementMethodPriceCommissionListGetLogic;
        this.settlementMethodPriceCommissionUpdateLogic = settlementMethodPriceCommissionUpdateLogic;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param settlementMethodDto 決済方法DTO
     * @return 処理件数
     */
    @Override
    public int execute(SettlementMethodDto settlementMethodDto) {

        // 決済方法エンティティ
        SettlementMethodEntity settlementMethodEntity = settlementMethodDto.getSettlementMethodEntity();
        // 決済方法SEQ
        Integer settlementMethodSeq = settlementMethodEntity.getSettlementMethodSeq();

        // 排他チェック
        SettlementMethodEntity original = settlementMethodGetLogic.execute(settlementMethodSeq);

        // 更新情報をセット
        setUpdateData(settlementMethodEntity, original);

        // クレジットでない かつ 後払の場合は、支払期限猶予日数、期限後取消猶予日数に０をセットする
        if (!HTypeSettlementMethodType.CREDIT.equals(settlementMethodEntity.getSettlementMethodType())
            && HTypeBillType.POST_CLAIM.equals(settlementMethodEntity.getBillType())) {
            settlementMethodEntity.setPaymentTimeLimitDayCount(0);
            settlementMethodEntity.setCancelTimeLimitDayCount(0);
        }

        // 決済方法更新
        int res = settlementMethodUpdateLogic.execute(settlementMethodEntity);

        // 決済方法手数料種別
        HTypeSettlementMethodPriceCommissionFlag originalCommissionFlag =
                        original.getSettlementMethodPriceCommissionFlag();
        HTypeSettlementMethodPriceCommissionFlag modifiedCommissionFlag =
                        settlementMethodEntity.getSettlementMethodPriceCommissionFlag();

        // 決済方法金額別手数料エンティティリスト
        List<SettlementMethodPriceCommissionEntity> settlementMethodPriceCommissionEntityList =
                        settlementMethodDto.getSettlementMethodPriceCommissionEntityList();

        if (HTypeSettlementMethodPriceCommissionFlag.FLAT == originalCommissionFlag
            && HTypeSettlementMethodPriceCommissionFlag.EACH_AMOUNT == modifiedCommissionFlag) {
            // 一律→金額別
            // 決済方法金額別手数料登録
            registSettlementMethodPriceCommission(settlementMethodPriceCommissionEntityList, settlementMethodSeq);
        } else if (HTypeSettlementMethodPriceCommissionFlag.EACH_AMOUNT == originalCommissionFlag
                   && HTypeSettlementMethodPriceCommissionFlag.FLAT == modifiedCommissionFlag) {
            // 金額別→一律
            // 決済方法金額別手数料削除
            settlementMethodPriceCommissionDeleteLogic.execute(settlementMethodSeq);
        } else if (HTypeSettlementMethodPriceCommissionFlag.EACH_AMOUNT == originalCommissionFlag
                   && HTypeSettlementMethodPriceCommissionFlag.EACH_AMOUNT == modifiedCommissionFlag) {
            // 金額別→金額別
            // 決済方法金額別手数料更新
            updateSettlementMethodPriceCommission(settlementMethodPriceCommissionEntityList, settlementMethodSeq);
        }

        // 決済方法チェック
        settlementMethodCheckLogic.execute(settlementMethodEntity, original);

        return res;
    }

    /**
     * 変更不可能な値を更新前情報から取得してセット<br/>
     *
     * @param modified 更新情報
     * @param original 更新前情報
     */
    protected void setUpdateData(SettlementMethodEntity modified, SettlementMethodEntity original) {
        modified.setSettlementMethodType(original.getSettlementMethodType());
        modified.setBillType(original.getBillType());
        modified.setDeliveryMethodSeq(original.getDeliveryMethodSeq());
        modified.setRegistTime(original.getRegistTime());
    }

    /**
     * 決済方法金額別手数料リスト登録処理<br/>
     *
     * @param settlementMethodPriceCommissionEntityList 決済方法金額別手数料登録リスト
     * @param settlementMethodSeq                       決済方法SEQ
     */
    protected void registSettlementMethodPriceCommission(List<SettlementMethodPriceCommissionEntity> settlementMethodPriceCommissionEntityList,
                                                         Integer settlementMethodSeq) {
        for (SettlementMethodPriceCommissionEntity settlementMethodPriceCommissionEntity : settlementMethodPriceCommissionEntityList) {
            // 決済方法金額別手数料登録処理
            settlementMethodPriceCommissionEntity.setSettlementMethodSeq(settlementMethodSeq);
            settlementMethodPriceCommissionRegistLogic.execute(settlementMethodPriceCommissionEntity);
        }
    }

    /**
     * メソッド概要<br/>
     * メソッドの説明・概要<br/>
     *
     * @param modifiedList        決済方法金額別手数料エンティティリスト
     * @param settlementMethodSeq 決済方法SEQ
     */
    protected void updateSettlementMethodPriceCommission(List<SettlementMethodPriceCommissionEntity> modifiedList,
                                                         Integer settlementMethodSeq) {

        // 更新前決済方法金額別手数料エンティティリスト
        List<SettlementMethodPriceCommissionEntity> originalList =
                        settlementMethodPriceCommissionListGetLogic.execeute(settlementMethodSeq);

        for (SettlementMethodPriceCommissionEntity original : originalList) {
            boolean deleteflag = true;
            for (int modIndex = 0; modIndex < modifiedList.size(); modIndex++) {
                SettlementMethodPriceCommissionEntity modified = modifiedList.get(modIndex);
                if (original.getMaxPrice().compareTo(modified.getMaxPrice()) == 0) {
                    if (original.getCommission().compareTo(modified.getCommission()) != 0) {
                        // 決済方法金額別手数料更新
                        modified.setSettlementMethodSeq(settlementMethodSeq);
                        modified.setRegistTime(original.getRegistTime());
                        settlementMethodPriceCommissionUpdateLogic.execute(modified);
                    }
                    modifiedList.remove(modified);
                    modIndex--;
                    deleteflag = false;
                    break;
                }
            }
            if (deleteflag) {
                // 決済方法金額別手数料削除
                settlementMethodPriceCommissionDeleteLogic.execute(original);
            }
        }

        // 決済方法金額別手数料登録
        registSettlementMethodPriceCommission(modifiedList, settlementMethodSeq);

    }

}
