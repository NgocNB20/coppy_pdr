/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodCommissionType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodPriceCommissionFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementMethodDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodPriceCommissionEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement.SettlementMethodConfigurationCheckService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 決済方法設定チェックサービス<br/>
 * <p>
 * 設定可能な決済方法であるかをチェックします。<br/>
 *
 * @author YAMAGUCHI
 */
@Service
public class SettlementMethodConfigurationCheckServiceImpl extends AbstractShopService
                implements SettlementMethodConfigurationCheckService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SettlementMethodConfigurationCheckServiceImpl.class);

    /**
     * 決済方法チェックロジック<br/>
     */
    private final SettlementMethodCheckLogic settlementMethodCheckLogic;

    @Autowired
    public SettlementMethodConfigurationCheckServiceImpl(SettlementMethodCheckLogic settlementMethodCheckLogic) {
        this.settlementMethodCheckLogic = settlementMethodCheckLogic;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param settlementMethodDto 決済方法DTO
     */
    @Override
    public void execute(SettlementMethodDto settlementMethodDto) {

        // 決済方法エンティティ取得
        SettlementMethodEntity settlementMethodEntity = getSettlementMethodEntity(settlementMethodDto);

        // 手数料設定チェック
        HTypeSettlementMethodPriceCommissionFlag settlementMethodPriceCommissionFlag =
                        settlementMethodEntity.getSettlementMethodPriceCommissionFlag();
        if (HTypeSettlementMethodPriceCommissionFlag.EACH_AMOUNT == settlementMethodPriceCommissionFlag) {
            // 金額別手数料チェック
            checkByCommissionTypeForEachAmount(settlementMethodDto);
        } else {
            BigDecimal maxPrice = settlementMethodEntity.getMaxPurchasedPrice();
            BigDecimal commission = settlementMethodEntity.getEqualsCommission();
            HTypeSettlementMethodCommissionType commissionType =
                            settlementMethodEntity.getSettlementMethodCommissionType();
            // 手数料入力チェック
            checkCommission(commission);

            // 利用可能最大金額チェック
            checkMaxPrice(maxPrice);

            // 一律手数料種別毎チェック
            checkByCommissionTypeForFlat(maxPrice, commission, commissionType);
        }

        // 決済方法チェック
        checkSettlementMethod(settlementMethodEntity);

        // Open status check for Mobile-Amazon
        checkOpenStatusAmazonMobile(settlementMethodEntity);

        if (hasErrorMessage()) {
            throwMessage();
        }

    }

    /**
     * 決済方法エンティティ取得<br/>
     *
     * @param settlementMethodDto 決済方法DTO
     * @param customParams        案件用引数
     * @return 決済方法エンティティ
     */
    protected SettlementMethodEntity getSettlementMethodEntity(SettlementMethodDto settlementMethodDto,
                                                               Object... customParams) {
        SettlementMethodEntity settlementMethodEntity = settlementMethodDto.getSettlementMethodEntity();
        settlementMethodEntity.setShopSeq(1001);
        return settlementMethodEntity;
    }

    /**
     * 金額別手数料チェック<br/>
     *
     * @param settlementMethodDto 決済方法DTO
     * @param customParams        案件用引数
     */
    protected void checkByCommissionTypeForEachAmount(SettlementMethodDto settlementMethodDto, Object... customParams) {
        List<SettlementMethodPriceCommissionEntity> list =
                        settlementMethodDto.getSettlementMethodPriceCommissionEntityList();
        if (list == null || list.isEmpty()) {
            this.addErrorMessage(MSGCD_COMMISSION_NO_SET);
        }
    }

    /**
     * 手数料チェック<br/>
     *
     * @param commission   手数料
     * @param customParams 案件用引数
     */
    protected void checkCommission(BigDecimal commission, Object... customParams) {
        if (commission == null) {
            this.addErrorMessage(MSGCD_EQUALS_COMMISSION_NO_SET);
        }
    }

    /**
     * 利用可能最大金額チェック<br/>
     *
     * @param maxPrice     利用可能最大金額
     * @param customParams 案件用引数
     */
    protected void checkMaxPrice(BigDecimal maxPrice, Object... customParams) {
        if (maxPrice == null) {
            this.addErrorMessage(MSGCD_MAX_PURCHASED_PRICE_NO_SET);
        } else if (BigDecimal.ZERO.compareTo(maxPrice) >= 0) {
            this.addErrorMessage(MSGCD_MAX_PURCHASED_PRICE_ZERO);
        }
    }

    /**
     * 一律手数料種別毎のチェック<br/>
     *
     * @param maxPrice       利用可能最大金額
     * @param commission     手数料
     * @param commissionType 手数料種別
     * @param customParams   案件用引数
     */
    protected void checkByCommissionTypeForFlat(BigDecimal maxPrice,
                                                BigDecimal commission,
                                                HTypeSettlementMethodCommissionType commissionType,
                                                Object... customParams) {
        // 手数料種別＝一律(円)の場合のチェック
        if (HTypeSettlementMethodCommissionType.FLAT_YEN.equals(commissionType) && commission != null
            && maxPrice != null && commission.compareTo(maxPrice) >= 0) {
            this.addErrorMessage(MSGCD_COMMISSION_ORVER_MAX_PURCHASED_PRICE);
        }
        // 手数料種別＝一律(％)の場合のチェック
        // if
        // (HTypeSettlementMethodCommissionType.FLAT_PERCENTAGE.equals(commissionType)
        // && commission != null && commission.compareTo(new BigDecimal(100)) >=
        // 0) {
        // this.addErrorMessage(MSGCD_COMMISSION_ORVER_MAX_PERCENTAGE_PRICE);
        // }
    }

    /**
     * 決済方法チェック<br/>
     *
     * @param settlementMethodEntity 決済方法エンティティ
     * @param customParams           案件用引数
     */
    protected void checkSettlementMethod(SettlementMethodEntity settlementMethodEntity, Object... customParams) {
        try {
            settlementMethodCheckLogic.execute(settlementMethodEntity);
        } catch (AppLevelListException e) {
            LOGGER.error("例外処理が発生しました", e);
            this.addErrorMessage(e);
        }
    }

    /**
     * Open status check for Mobile-Amazon Pay
     * Throw error message if status is Open.
     *
     * @param settlementMethodEntity Settlement method entity.
     */
    protected void checkOpenStatusAmazonMobile(SettlementMethodEntity settlementMethodEntity) {
        if (HTypeSettlementMethodType.AMAZON_PAYMENT == settlementMethodEntity.getSettlementMethodType()) {
            HTypeOpenDeleteStatus status = settlementMethodEntity.getOpenStatusMB();
            if (HTypeOpenDeleteStatus.OPEN == status) {
                this.addErrorMessage(MSG_CD_OPEN_STATUS_MOBILE_NOT_ALLOWED);
            }
        }
    }
}
