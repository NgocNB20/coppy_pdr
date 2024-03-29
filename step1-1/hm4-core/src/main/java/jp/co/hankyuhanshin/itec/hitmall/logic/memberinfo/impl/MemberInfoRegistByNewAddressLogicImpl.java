// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAccountingType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCashDeliveryUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCreditPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDentalMonopolySalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDirectDebitUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDrugSalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMedicalEquipmentSalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMonthlyPayUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineLoginAdvisability;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendDirectMailFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendFaxPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTransferPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoRegistByNewAddressLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * PDR#011 08_データ連携（顧客情報）<br/>
 *
 * <pre>
 * 新しいお届け先 会員登録ロジック
 * </pre>
 *
 * @author satoh
 */
@Component
public class MemberInfoRegistByNewAddressLogicImpl extends AbstractShopLogic
                implements MemberInfoRegistByNewAddressLogic {

    /**
     * 会員情報登録ロジック<br/>
     */
    private final MemberInfoRegistLogic memberInfoRegistLogic;

    /**
     * 会員情報取得ロジック<br/>
     */
    private final MemberInfoGetLogic memberInfoGetLogic;

    @Autowired
    public MemberInfoRegistByNewAddressLogicImpl(MemberInfoRegistLogic memberInfoRegistLogic,
                                                 MemberInfoGetLogic memberInfoGetLogic) {
        this.memberInfoRegistLogic = memberInfoRegistLogic;
        this.memberInfoGetLogic = memberInfoGetLogic;
    }

    /**
     * 新しいお届け先に入力された情報の<br/>
     * 会員登録を行います。
     *
     * @param memberInfoEntity 会員情報
     * @return 顧客番号
     */
    public Integer execute(MemberInfoEntity memberInfoEntity) {

        // 入力チェック
        checkParamater(memberInfoEntity);

        // 会員情報設定
        setMemberInfoEntity(memberInfoEntity);

        // 会員情報登録
        int result = memberInfoRegistLogic.execute(memberInfoEntity);
        if (result == 0) {
            throwMessage(MSGCD_MEMBERINFO_REGIST_FAIL);
        }

        MemberInfoEntity resMemberInfoEntity = memberInfoGetLogic.getMemberInfoEntityByMemberInfoTelAndCustomerNo(
                        memberInfoEntity.getMemberInfoTel(), memberInfoEntity.getCustomerNo().toString());
        if (resMemberInfoEntity == null) {
            throwMessage(MSGCD_MEMBERINFO_REGIST_FAIL);
        }

        // 会員SEQ 返却
        return memberInfoEntity.getCustomerNo();

    }

    /**
     * 会員登録に必要な情報を設定します。<br/>
     * <pre>
     * 画面入力された情報はActionクラスで設定を行うため
     * ここでは固定値のみ設定
     * </pre>
     *
     * @param memberInfoEntity 会員情報
     */
    public void setMemberInfoEntity(MemberInfoEntity memberInfoEntity) {

        // 会員ユニークIDの作成・セット
        Integer shopSeq = 1001;

        // ショップSEQ
        memberInfoEntity.setShopSeq(shopSeq);

        // 状態：退会
        memberInfoEntity.setMemberInfoStatus(HTypeMemberInfoStatus.REMOVE);
        // 承認状態：未承認
        memberInfoEntity.setApproveStatus(HTypeApproveStatus.OFF);
        // 休診曜日:null
        memberInfoEntity.setNonConsultationDay(null);
        // メールによるおトク情報：使用不可
        memberInfoEntity.setSendMailPermitFlag(HTypeSendMailPermitFlag.OFF);
        // FAXによるおトク情報：使用不可
        memberInfoEntity.setSendFaxPermitFlag(HTypeSendFaxPermitFlag.OFF);
        // DMによるおトク情報：使用不可
        memberInfoEntity.setSendDirectMailFlag(HTypeSendDirectMailFlag.OFF);
        // 医薬品・注射針販売区分：販売不可
        memberInfoEntity.setDrugSalesType(HTypeDrugSalesType.SALEOFF);
        // 医療機器販売区分：販売不可
        memberInfoEntity.setMedicalEquipmentSalesType(HTypeMedicalEquipmentSalesType.SALEOFF);
        // 歯科専売品販売区分：販売不可
        memberInfoEntity.setDentalMonopolySalesType(HTypeDentalMonopolySalesType.SALEOFF);
        // クレジット決済使用可否：使用不可
        memberInfoEntity.setCreditPaymentUseFlag(HTypeCreditPaymentUseFlag.OFF);
        // コンビニ・郵便振込使用可否：使用不可
        memberInfoEntity.setTransferPaymentUseFlag(HTypeTransferPaymentUseFlag.OFF);
        // 代金引換使用可否：使用不可
        memberInfoEntity.setCashDeliveryUseFlag(HTypeCashDeliveryUseFlag.OFF);
        // 口座自動引落使用可否：使用不可
        memberInfoEntity.setDirectDebitUseFlag(HTypeDirectDebitUseFlag.OFF);
        // 月締請求使用可否：使用不可
        memberInfoEntity.setMonthlyPayUseFlag(HTypeMonthlyPayUseFlag.OFF);
        // オンライン登録フラグ：非EC会員
        memberInfoEntity.setOnlineRegistFlag(HTypeOnlineRegistFlag.OFF);
        // 経理区分：顧客
        memberInfoEntity.setAccountingType(HTypeAccountingType.CUSTOMER);
        // オンラインログイン可否：仮登録
        memberInfoEntity.setOnlineLoginAdvisability(HTypeOnlineLoginAdvisability.TEMPORARY_ACCOUNT);
    }

    /**
     * 入力チェック<br/>
     *
     * @param memberInfoEntity 会員情報エンティティ
     */
    protected void checkParamater(MemberInfoEntity memberInfoEntity) {

        ArgumentCheckUtil.assertNotNull("memberInfoEntity", memberInfoEntity);
    }
}
// PDR Migrate Customization to here
