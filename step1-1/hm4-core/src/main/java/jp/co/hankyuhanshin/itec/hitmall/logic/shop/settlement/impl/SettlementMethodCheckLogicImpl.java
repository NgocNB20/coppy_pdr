/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.settlement.SettlementMethodDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodEntityListGetLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 決済方法チェックロジック<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.1 $
 */
@Component
public class SettlementMethodCheckLogicImpl extends AbstractShopLogic implements SettlementMethodCheckLogic {

    // Paygent Customization from here
    /**
     * 「Pay-easy」を選択することは出来ません。
     */
    public static final String MSGCD_PAYEASY_SELECT_CLAIM = "LST001011";
    // Paygent Customization to here

    /**
     * 決済方法DAO
     */
    private final SettlementMethodDao settlementMethodDao;

    /**
     * 決済方法リスト取得
     */
    private final SettlementMethodEntityListGetLogic settlementMethodEntityListGetLogic;

    @Autowired
    public SettlementMethodCheckLogicImpl(SettlementMethodDao settlementMethodDao,
                                          SettlementMethodEntityListGetLogic settlementMethodEntityListGetLogic) {
        this.settlementMethodDao = settlementMethodDao;
        this.settlementMethodEntityListGetLogic = settlementMethodEntityListGetLogic;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param settlementMethodEntity 決済方法エンティティ
     */
    @Override
    public void execute(SettlementMethodEntity settlementMethodEntity) {

        // 決済方法SEQ
        Integer settlementMethodSeq = settlementMethodEntity.getSettlementMethodSeq();

        if (settlementMethodSeq == null) {
            // 新規登録チェック
            registCheck(settlementMethodEntity);
        } else {
            // 更新チェック
            updateCheck(settlementMethodEntity);
        }

        // エラーチェック
        if (this.hasErrorList()) {
            this.throwMessage();
        }
    }

    /**
     * 決済方法登録チェック<br/>
     *
     * @param settlementMethodEntity 決済方法エンティティ
     */
    protected void registCheck(SettlementMethodEntity settlementMethodEntity) {
        // ショップSEQ
        Integer shopSeq = settlementMethodEntity.getShopSeq();

        HTypeSettlementMethodType settlementMethodType = settlementMethodEntity.getSettlementMethodType();
        HTypeBillType billType = settlementMethodEntity.getBillType();

        // Paygent Customization from here
        // ペイジェント非対応の決済方法選択時にエラーとする
        if (HTypeSettlementMethodType.CREDIT == settlementMethodType && HTypeBillType.PRE_CLAIM == billType) {
            // 決済種別=クレジット 且つ 請求種別=前請求
            this.addErrorMessage(
                            MSGCD_STATUS_NO_SELECT_CLAIM, new Object[] {HTypeSettlementMethodType.CREDIT.getLabel(),
                                            HTypeBillType.POST_CLAIM.getLabel()});
        } else if (HTypeSettlementMethodType.PAY_EASY == settlementMethodType) {
            // 決済種別=ペイジー
            this.addErrorMessage(MSGCD_PAYEASY_SELECT_CLAIM);
        }
        // Paygent Customization to here

        if (HTypeSettlementMethodType.CONVENIENCE == settlementMethodType && HTypeBillType.POST_CLAIM == billType) {
            // 決済種別=コンビニ 且つ 請求種別=後請求
            this.addErrorMessage(MSGCD_CONVENIENCE_POST_CLAIM);
        } else if (HTypeSettlementMethodType.RECEIPT_PAYMENT == settlementMethodType
                   && HTypeBillType.PRE_CLAIM == billType) {
            // 決済種別=代金引換 且つ 請求種別=前請求
            this.addErrorMessage(MSGCD_RECEIPT_PAYMENT_PRE_CLAIM);
            // } else if (HTypeSettlementMethodType.MOBILE_SUICA ==
            // settlementMethodType && HTypeBillType.POST_CLAIM == billType) {
            // // 決済種別=モバイルSUICA 且つ 請求種別=後請求
            // this.addErrorMessage(MSGCD_STATUS_NO_SELECT_CLAIM
            // , new Object[] {
            // HTypeSettlementMethodType.MOBILE_SUICA.getLabel(),
            // HTypeBillType.PRE_CLAIM.getLabel() });
            // } else if (HTypeSettlementMethodType.MOBILE_EDY ==
            // settlementMethodType && HTypeBillType.POST_CLAIM == billType) {
            // // 決済種別=Mobile Edy 且つ 請求種別=後請求
            // this.addErrorMessage(MSGCD_STATUS_NO_SELECT_CLAIM
            // , new Object[] { HTypeSettlementMethodType.MOBILE_EDY.getLabel(),
            // HTypeBillType.PRE_CLAIM.getLabel() });
        } else if (HTypeSettlementMethodType.PAY_EASY == settlementMethodType && HTypeBillType.POST_CLAIM == billType) {
            // 決済種別Pay-easy 且つ 請求種別=後請求
            this.addErrorMessage(
                            MSGCD_STATUS_NO_SELECT_CLAIM, new Object[] {HTypeSettlementMethodType.PAY_EASY.getLabel(),
                                            HTypeBillType.PRE_CLAIM.getLabel()});
            // } else if (HTypeSettlementMethodType.CASH == settlementMethodType
            // && HTypeBillType.POST_CLAIM == billType) {
            // // 決済種別=現金支払 且つ 請求種別=後請求
            // this.addErrorMessage(MSGCD_STATUS_NO_SELECT_CLAIM
            // , new Object[] { HTypeSettlementMethodType.CASH.getLabel(),
            // HTypeBillType.PRE_CLAIM.getLabel() });
        } else if (HTypeSettlementMethodType.BANK_TRANSFER == settlementMethodType
                   && HTypeBillType.POST_CLAIM == billType) {
            // 決済種別=銀行振込 請求種別=後請求
            this.addErrorMessage(
                            MSGCD_STATUS_NO_SELECT_CLAIM,
                            new Object[] {HTypeSettlementMethodType.BANK_TRANSFER.getLabel(),
                                            HTypeBillType.PRE_CLAIM.getLabel()}
                                );
        } else if (HTypeSettlementMethodType.CASH_REGISTERED_MAIL == settlementMethodType
                   && HTypeBillType.POST_CLAIM == billType) {
            // 決済種別=現金書留 且つ 請求種別=後請求
            this.addErrorMessage(
                            MSGCD_STATUS_NO_SELECT_CLAIM,
                            new Object[] {HTypeSettlementMethodType.CASH_REGISTERED_MAIL.getLabel(),
                                            HTypeBillType.PRE_CLAIM.getLabel()}
                                );
        } else if (HTypeSettlementMethodType.PRE_PAYMENT == settlementMethodType
                   && HTypeBillType.POST_CLAIM == billType) {
            // 決済種別=入金確認 且つ 請求種別=後請求
            this.addErrorMessage(
                            MSGCD_STATUS_NO_SELECT_CLAIM,
                            new Object[] {HTypeSettlementMethodType.PRE_PAYMENT.getLabel(),
                                            HTypeBillType.PRE_CLAIM.getLabel()}
                                );
        } else if (HTypeSettlementMethodType.AMAZON_PAYMENT == settlementMethodType
                   && HTypeBillType.PRE_CLAIM == billType) {
            // 決済種別=Amazonペイメント 且つ 請求種別=前請求
            this.addErrorMessage(
                            MSGCD_STATUS_NO_SELECT_CLAIM,
                            new Object[] {HTypeSettlementMethodType.AMAZON_PAYMENT.getLabel(),
                                            HTypeBillType.POST_CLAIM.getLabel()}
                                );
        }
        // 決済方法リスト取得
        List<SettlementMethodEntity> settlementMethodEntityList = settlementMethodEntityListGetLogic.execute(shopSeq);

        // 決済方法名称
        String settlementMethodName = settlementMethodEntity.getSettlementMethodName();

        // 決済方法リストの件数は少数であることが前提で、ループにてチェック
        for (SettlementMethodEntity settlementMethodEntityTmp : settlementMethodEntityList) {
            HTypeSettlementMethodType settlementMethodTypeTmp = settlementMethodEntity.getSettlementMethodType();
            String settlementMethodNameTmp = settlementMethodEntityTmp.getSettlementMethodName();
            // Amazon Payment重複登録チェック
            if (HTypeSettlementMethodType.AMAZON_PAYMENT == settlementMethodType
                && settlementMethodType == settlementMethodTypeTmp) {
                this.addErrorMessage(MSGCD_AMAZON_PAYMENT_EXIST);
                break;
            }
            // 同名チェック
            if (settlementMethodName.equals(settlementMethodNameTmp)) {
                this.addErrorMessage(MSGCD_NAME_EXIST, new Object[] {settlementMethodName});
            }
        }
    }

    /**
     * 決済方法更新チェック<br/>
     *
     * @param settlementMethodEntity 決済方法エンティティ
     */
    protected void updateCheck(SettlementMethodEntity settlementMethodEntity) {
        // ショップSEQ
        Integer shopSeq = settlementMethodEntity.getShopSeq();
        // 決済方法SEQ
        Integer settlementMethodSeq = settlementMethodEntity.getSettlementMethodSeq();

        // 同名チェック
        checkSameName(settlementMethodEntity, shopSeq, settlementMethodSeq);

    }

    /**
     * 同名チェック<br/>
     *
     * @param modified            決済方法DTO
     * @param shopSeq             ショップSEQ
     * @param settlementMethodSeq 決済SEQ
     */
    protected void checkSameName(SettlementMethodEntity modified, Integer shopSeq, Integer settlementMethodSeq) {
        // 決済方法名
        String settlementMethodName = modified.getSettlementMethodName();
        // 同名チェック
        int count = settlementMethodDao.getSameNameCount(settlementMethodSeq, shopSeq, settlementMethodName);
        if (count > 0) {
            this.addErrorMessage(MSGCD_NAME_EXIST, new Object[] {settlementMethodName});
        }
    }

    /**
     * 実行メソッド<br/>
     *
     * @param modified 決済方法DTO
     * @param original 決済方法DTO
     */
    @Override
    public void execute(SettlementMethodEntity modified, SettlementMethodEntity original) {
        // ショップSEQ
        Integer shopSeq = modified.getShopSeq();
        // 決済方法SEQ
        Integer settlementMethodSeq = modified.getSettlementMethodSeq();

        // 同名チェック
        checkSameName(modified, shopSeq, settlementMethodSeq);

        // エラーチェック
        if (this.hasErrorList()) {
            this.throwMessage();
        }
    }

}
