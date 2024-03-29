/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.reset;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ConfirmMailEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAccountingType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCardRegistType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCashDeliveryUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeConfDocumentType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeConfirmMailType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCreditPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeliveryCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDirectDebitUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberListType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMetalPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMonthlyPayUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeNoAntiSocialFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOnlineLoginAdvisability;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOrderCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendDirectMailFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendFaxPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSexUnnecessaryAnswer;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTransferPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.confirmmail.ConfirmMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import org.springframework.stereotype.Component;

/**
 * パスワードリセット Helperクラス
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Component
public class ResetPwregistHelper {

    /** 変換ユーティリティクラス */
    private final ConversionUtility conversionUtility;

    public ResetPwregistHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * 取得した会員情報をセット
     *
     * @param memberInfoEntity   注文からの遷移の場合の仮会員情報
     * @param resetPwregistModel パスワードリセット Model
     */
    public void toPageForLoad(MemberInfoEntity memberInfoEntity, ResetPwregistModel resetPwregistModel) {

        // 会員情報セット
        resetPwregistModel.setMemberInfoEntity(memberInfoEntity);
    }

    /**
     * 確認メールに変換
     *
     * @param confirmMailEntityResponse 確認メールEntityレスポンス
     * @return 確認メール
     */
    public ConfirmMailEntity toConfirmMailEntity(ConfirmMailEntityResponse confirmMailEntityResponse) {
        if (confirmMailEntityResponse == null) {
            return null;
        }
        ConfirmMailEntity entity = new ConfirmMailEntity();

        entity.setConfirmMail(confirmMailEntityResponse.getConfirmMail());
        entity.setShopSeq(1001);
        entity.setMemberInfoSeq(confirmMailEntityResponse.getMemberInfoSeq());
        entity.setOrderSeq(confirmMailEntityResponse.getOrderSeq());
        if (confirmMailEntityResponse.getConfirmMailType() != null) {
            entity.setConfirmMailType(EnumTypeUtil.getEnumFromValue(HTypeConfirmMailType.class,
                                                                    confirmMailEntityResponse.getConfirmMailType()
                                                                   ));
        }
        entity.setConfirmMailPassword(confirmMailEntityResponse.getConfirmMailPassword());

        entity.setRegistTime(conversionUtility.toTimeStamp(confirmMailEntityResponse.getRegistTime()));
        entity.setUpdateTime(conversionUtility.toTimeStamp(confirmMailEntityResponse.getUpdateTime()));

        return entity;
    }

    /**
     * 会員に変換
     *
     * @param memberInfoEntityResponse 会員Entityレスポンス
     * @return 会員
     */
    public MemberInfoEntity toMemberInfoEntity(MemberInfoEntityResponse memberInfoEntityResponse) {
        if (memberInfoEntityResponse == null) {
            return null;
        }
        MemberInfoEntity entity = new MemberInfoEntity();

        entity.setMemberInfoSeq(memberInfoEntityResponse.getMemberInfoSeq());
        entity.setMemberInfoUniqueId(memberInfoEntityResponse.getMemberInfoUniqueId());
        entity.setMemberInfoId(memberInfoEntityResponse.getMemberInfoId());
        entity.setMemberInfoPassword(memberInfoEntityResponse.getMemberInfoPassword());
        entity.setMemberInfoLastName(memberInfoEntityResponse.getMemberInfoLastName());
        entity.setMemberInfoFirstName(memberInfoEntityResponse.getMemberInfoFirstName());
        entity.setMemberInfoLastKana(memberInfoEntityResponse.getMemberInfoLastKana());
        entity.setMemberInfoFirstKana(memberInfoEntityResponse.getMemberInfoFirstKana());
        entity.setMemberInfoBirthday(conversionUtility.toTimeStamp(memberInfoEntityResponse.getMemberInfoBirthday()));
        entity.setMemberInfoTel(memberInfoEntityResponse.getMemberInfoTel());
        entity.setMemberInfoContactTel(memberInfoEntityResponse.getMemberInfoContactTel());
        entity.setMemberInfoZipCode(memberInfoEntityResponse.getMemberInfoZipCode());
        entity.setMemberInfoPrefecture(memberInfoEntityResponse.getMemberInfoPrefecture());
        entity.setMemberInfoAddress1(memberInfoEntityResponse.getMemberInfoAddress1());
        entity.setMemberInfoAddress2(memberInfoEntityResponse.getMemberInfoAddress2());
        entity.setMemberInfoAddress3(memberInfoEntityResponse.getMemberInfoAddress3());
        entity.setMemberInfoMail(memberInfoEntityResponse.getMemberInfoMail());
        entity.setShopSeq(1001);
        entity.setAccessUid(memberInfoEntityResponse.getAccessUid());
        entity.setVersionNo(memberInfoEntityResponse.getVersionNo());
        entity.setAdmissionYmd(memberInfoEntityResponse.getAdmissionYmd());
        entity.setSecessionYmd(memberInfoEntityResponse.getSecessionYmd());
        entity.setMemo(memberInfoEntityResponse.getMemo());
        entity.setMemberInfoFax(memberInfoEntityResponse.getMemberInfoFax());
        entity.setLastLoginTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getLastLoginTime()));
        entity.setLastLoginUserAgent(memberInfoEntityResponse.getLastLoginUserAgent());
        entity.setPaymentMemberId(memberInfoEntityResponse.getPaymentMemberId());
        entity.setLoginFailureCount(memberInfoEntityResponse.getLoginFailureCount());
        entity.setAccountLockTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getAccountLockTime()));
        entity.setRegistTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getRegistTime()));
        entity.setUpdateTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getUpdateTime()));
        entity.setCustomerNo(memberInfoEntityResponse.getCustomerNo());
        entity.setRepresentativeName(memberInfoEntityResponse.getRepresentativeName());
        entity.setMemberInfoAddress4(memberInfoEntityResponse.getMemberInfoAddress4());
        entity.setMemberInfoAddress5(memberInfoEntityResponse.getMemberInfoAddress5());
        entity.setNonConsultationDay(memberInfoEntityResponse.getNonConsultationDay());
        entity.setMedicalTreatmentFlag(memberInfoEntityResponse.getMedicalTreatmentFlag());
        entity.setMedicalTreatmentMemo(memberInfoEntityResponse.getMedicalTreatmentMemo());
        entity.setSendMailStartTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getSendMailStartTime()));
        entity.setSendMailStopTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getSendMailStopTime()));

        entity.setMemberInfoStatus(EnumTypeUtil.getEnumFromValue(HTypeMemberInfoStatus.class,
                                                                 memberInfoEntityResponse.getMemberInfoStatus()
                                                                ));
        entity.setMemberInfoSex(EnumTypeUtil.getEnumFromValue(HTypeSexUnnecessaryAnswer.class,
                                                              memberInfoEntityResponse.getMemberInfoSex()
                                                             ));
        entity.setPrefectureType(EnumTypeUtil.getEnumFromValue(HTypePrefectureType.class,
                                                               memberInfoEntityResponse.getPrefectureType()
                                                              ));
        entity.setPaymentCardRegistType(EnumTypeUtil.getEnumFromValue(HTypeCardRegistType.class,
                                                                      memberInfoEntityResponse.getPaymentCardRegistType()
                                                                     ));
        entity.setPasswordNeedChangeFlag(EnumTypeUtil.getEnumFromValue(HTypePasswordNeedChangeFlag.class,
                                                                       memberInfoEntityResponse.getPasswordNeedChangeFlag()
                                                                      ));
        entity.setLastLoginDeviceType(EnumTypeUtil.getEnumFromValue(HTypeDeviceType.class,
                                                                    memberInfoEntityResponse.getLastLoginDeviceType()
                                                                   ));
        entity.setBusinessType(EnumTypeUtil.getEnumFromValue(HTypeBusinessType.class,
                                                             memberInfoEntityResponse.getBusinessType()
                                                            ));
        entity.setSendFaxPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeSendFaxPermitFlag.class,
                                                                  memberInfoEntityResponse.getSendFaxPermitFlag()
                                                                 ));
        entity.setSendDirectMailFlag(EnumTypeUtil.getEnumFromValue(HTypeSendDirectMailFlag.class,
                                                                   memberInfoEntityResponse.getSendDirectMailFlag()
                                                                  ));
        entity.setApproveStatus(EnumTypeUtil.getEnumFromValue(HTypeApproveStatus.class,
                                                              memberInfoEntityResponse.getApproveStatus()
                                                             ));
        entity.setCreditPaymentUseFlag(EnumTypeUtil.getEnumFromValue(HTypeCreditPaymentUseFlag.class,
                                                                     memberInfoEntityResponse.getCreditPaymentUseFlag()
                                                                    ));
        entity.setTransferPaymentUseFlag(EnumTypeUtil.getEnumFromValue(HTypeTransferPaymentUseFlag.class,
                                                                       memberInfoEntityResponse.getTransferPaymentUseFlag()
                                                                      ));
        entity.setCashDeliveryUseFlag(EnumTypeUtil.getEnumFromValue(HTypeCashDeliveryUseFlag.class,
                                                                    memberInfoEntityResponse.getCashDeliveryUseFlag()
                                                                   ));
        entity.setDirectDebitUseFlag(EnumTypeUtil.getEnumFromValue(HTypeDirectDebitUseFlag.class,
                                                                   memberInfoEntityResponse.getDirectDebitUseFlag()
                                                                  ));
        entity.setMonthlyPayUseFlag(EnumTypeUtil.getEnumFromValue(HTypeMonthlyPayUseFlag.class,
                                                                  memberInfoEntityResponse.getMonthlyPayUseFlag()
                                                                 ));
        entity.setMemberListType(EnumTypeUtil.getEnumFromValue(HTypeMemberListType.class,
                                                               memberInfoEntityResponse.getMemberListType()
                                                              ));
        entity.setOnlineRegistFlag(EnumTypeUtil.getEnumFromValue(HTypeOnlineRegistFlag.class,
                                                                 memberInfoEntityResponse.getOnlineRegistFlag()
                                                                ));
        entity.setConfDocumentType(EnumTypeUtil.getEnumFromValue(HTypeConfDocumentType.class,
                                                                 memberInfoEntityResponse.getConfDocumentType()
                                                                ));
        entity.setMetalPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeMetalPermitFlag.class,
                                                                memberInfoEntityResponse.getMetalPermitFlag()
                                                               ));
        // 2023-renew No79 from here
        entity.setOrderCompletePermitFlag(EnumTypeUtil.getEnumFromValue(HTypeOrderCompletePermitFlag.class,
                                                                        memberInfoEntityResponse.getOrderCompletePermitFlag()
                                                                       ));
        entity.setDeliveryCompletePermitFlag(EnumTypeUtil.getEnumFromValue(HTypeDeliveryCompletePermitFlag.class,
                                                                           memberInfoEntityResponse.getDeliveryCompletePermitFlag()
                                                                          ));
        // 2023-renew No79 to here
        entity.setAccountingType(EnumTypeUtil.getEnumFromValue(HTypeAccountingType.class,
                                                               memberInfoEntityResponse.getAccountingType()
                                                              ));
        entity.setOnlineLoginAdvisability(EnumTypeUtil.getEnumFromValue(HTypeOnlineLoginAdvisability.class,
                                                                        memberInfoEntityResponse.getOnlineLoginAdvisability()
                                                                       ));
        entity.setSendMailPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeSendMailPermitFlag.class,
                                                                   memberInfoEntityResponse.getSendMailPermitFlag()
                                                                  ));
        entity.setNoAntiSocialFlag(EnumTypeUtil.getEnumFromValue(HTypeNoAntiSocialFlag.class,
                                                                 memberInfoEntityResponse.getNoAntiSocialFlag()
                                                                ));

        return entity;
    }

    /**
     * 会員Entityリクエストに変換
     *
     * @param memberInfoEntity 会員
     * @return 会員Entityリクエスト
     */
    public MemberInfoEntityRequest toMemberInfoEntityRequest(MemberInfoEntity memberInfoEntity) {
        if (memberInfoEntity == null) {
            return null;
        }
        MemberInfoEntityRequest request = new MemberInfoEntityRequest();

        request.setMemberInfoSeq(memberInfoEntity.getMemberInfoSeq());
        if (memberInfoEntity.getMemberInfoStatus() != null) {
            request.setMemberInfoStatus(memberInfoEntity.getMemberInfoStatus().getValue());
        }
        request.setMemberInfoUniqueId(memberInfoEntity.getMemberInfoUniqueId());
        request.setMemberInfoId(memberInfoEntity.getMemberInfoId());
        request.setMemberInfoPassword(memberInfoEntity.getMemberInfoPassword());
        request.setMemberInfoLastName(memberInfoEntity.getMemberInfoLastName());
        request.setMemberInfoFirstName(memberInfoEntity.getMemberInfoFirstName());
        request.setMemberInfoLastKana(memberInfoEntity.getMemberInfoLastKana());
        request.setMemberInfoFirstKana(memberInfoEntity.getMemberInfoFirstKana());
        if (memberInfoEntity.getMemberInfoSex() != null) {
            request.setMemberInfoSex(memberInfoEntity.getMemberInfoSex().getValue());
        }
        request.setMemberInfoTel(memberInfoEntity.getMemberInfoTel());
        request.setMemberInfoContactTel(memberInfoEntity.getMemberInfoContactTel());
        request.setMemberInfoZipCode(memberInfoEntity.getMemberInfoZipCode());
        request.setMemberInfoPrefecture(memberInfoEntity.getMemberInfoPrefecture());
        if (memberInfoEntity.getPrefectureType() != null) {
            request.setPrefectureType(memberInfoEntity.getPrefectureType().getValue());
        }

        request.setMemberInfoAddress1(memberInfoEntity.getMemberInfoAddress1());
        request.setMemberInfoAddress2(memberInfoEntity.getMemberInfoAddress2());
        request.setMemberInfoAddress3(memberInfoEntity.getMemberInfoAddress3());
        request.setMemberInfoAddress4(memberInfoEntity.getMemberInfoAddress4());
        request.setMemberInfoAddress5(memberInfoEntity.getMemberInfoAddress5());
        request.setMemberInfoMail(memberInfoEntity.getMemberInfoMail());
        request.setAccessUid(memberInfoEntity.getAccessUid());
        request.setVersionNo(memberInfoEntity.getVersionNo());
        request.setAdmissionYmd(memberInfoEntity.getAdmissionYmd());
        request.setSecessionYmd(memberInfoEntity.getSecessionYmd());
        request.setMemo(memberInfoEntity.getMemo());
        request.setMemberInfoFax(memberInfoEntity.getMemberInfoFax());
        request.setLastLoginTime(conversionUtility.toTimeStamp(memberInfoEntity.getLastLoginTime()));
        request.setLastLoginUserAgent(memberInfoEntity.getLastLoginUserAgent());
        request.setPaymentMemberId(memberInfoEntity.getPaymentMemberId());
        if (memberInfoEntity.getPaymentCardRegistType() != null) {
            request.setPaymentCardRegistType(memberInfoEntity.getPaymentCardRegistType().getValue());
        }
        if (memberInfoEntity.getPasswordNeedChangeFlag() != null) {
            request.setPasswordNeedChangeFlag(memberInfoEntity.getPasswordNeedChangeFlag().getValue());
        }
        if (memberInfoEntity.getLastLoginDeviceType() != null) {
            request.setLastLoginDeviceType(memberInfoEntity.getLastLoginDeviceType().getValue());
        }
        request.setLoginFailureCount(memberInfoEntity.getLoginFailureCount());
        request.setAccountLockTime(conversionUtility.toTimeStamp(memberInfoEntity.getAccountLockTime()));
        request.setRegistTime(conversionUtility.toTimeStamp(memberInfoEntity.getRegistTime()));
        request.setUpdateTime(conversionUtility.toTimeStamp(memberInfoEntity.getUpdateTime()));

        request.setCustomerNo(memberInfoEntity.getCustomerNo());
        request.setRepresentativeName(memberInfoEntity.getRepresentativeName());
        if (memberInfoEntity.getBusinessType() != null) {
            request.setBusinessType(memberInfoEntity.getBusinessType().getValue());
        }
        if (memberInfoEntity.getSendFaxPermitFlag() != null) {
            request.setSendFaxPermitFlag(memberInfoEntity.getSendFaxPermitFlag().getValue());
        }
        if (memberInfoEntity.getSendDirectMailFlag() != null) {
            request.setSendDirectMailFlag(memberInfoEntity.getSendDirectMailFlag().getValue());
        }
        request.setNonConsultationDay(memberInfoEntity.getNonConsultationDay());
        if (memberInfoEntity.getApproveStatus() != null) {
            request.setApproveStatus(memberInfoEntity.getApproveStatus().getValue());
        }
        if (memberInfoEntity.getDrugSalesType() != null) {
            request.setDrugSalesType(memberInfoEntity.getDrugSalesType().getValue());
        }
        if (memberInfoEntity.getMedicalEquipmentSalesType() != null) {
            request.setMedicalEquipmentSalesType(memberInfoEntity.getMedicalEquipmentSalesType().getValue());
        }
        if (memberInfoEntity.getDentalMonopolySalesType() != null) {
            request.setDentalMonopolySalesType(memberInfoEntity.getDentalMonopolySalesType().getValue());
        }
        if (memberInfoEntity.getCreditPaymentUseFlag() != null) {
            request.setCreditPaymentUseFlag(memberInfoEntity.getCreditPaymentUseFlag().getValue());
        }
        if (memberInfoEntity.getTransferPaymentUseFlag() != null) {
            request.setTransferPaymentUseFlag(memberInfoEntity.getTransferPaymentUseFlag().getValue());
        }
        if (memberInfoEntity.getCashDeliveryUseFlag() != null) {
            request.setCashDeliveryUseFlag(memberInfoEntity.getCashDeliveryUseFlag().getValue());
        }
        if (memberInfoEntity.getDirectDebitUseFlag() != null) {
            request.setDirectDebitUseFlag(memberInfoEntity.getDirectDebitUseFlag().getValue());
        }
        if (memberInfoEntity.getMonthlyPayUseFlag() != null) {
            request.setMonthlyPayUseFlag(memberInfoEntity.getMonthlyPayUseFlag().getValue());
        }
        if (memberInfoEntity.getMemberListType() != null) {
            request.setMemberListType(memberInfoEntity.getMemberListType().getValue());
        }
        if (memberInfoEntity.getOnlineRegistFlag() != null) {
            request.setOnlineRegistFlag(memberInfoEntity.getOnlineRegistFlag().getValue());
        }
        if (memberInfoEntity.getConfDocumentType() != null) {
            request.setConfDocumentType(memberInfoEntity.getConfDocumentType().getValue());
        }

        request.setMedicalTreatmentFlag(memberInfoEntity.getMedicalTreatmentFlag());
        request.setMedicalTreatmentMemo(memberInfoEntity.getMedicalTreatmentMemo());
        if (memberInfoEntity.getMetalPermitFlag() != null) {
            request.setMetalPermitFlag(memberInfoEntity.getMetalPermitFlag().getValue());
        }
        // 2023-renew No79 from here
        if (memberInfoEntity.getOrderCompletePermitFlag() != null) {
            request.setOrderCompletePermitFlag(memberInfoEntity.getOrderCompletePermitFlag().getValue());
        }
        if (memberInfoEntity.getDeliveryCompletePermitFlag() != null) {
            request.setDeliveryCompletePermitFlag(memberInfoEntity.getDeliveryCompletePermitFlag().getValue());
        }
        // 2023-renew No79 to here
        if (memberInfoEntity.getAccountingType() != null) {
            request.setAccountingType(memberInfoEntity.getAccountingType().getValue());
        }
        if (memberInfoEntity.getOnlineLoginAdvisability() != null) {
            request.setOnlineLoginAdvisability(memberInfoEntity.getOnlineLoginAdvisability().getValue());
        }
        if (memberInfoEntity.getSendMailPermitFlag() != null) {
            request.setSendMailPermitFlag(memberInfoEntity.getSendMailPermitFlag().getValue());
        }
        request.setSendMailStartTime(conversionUtility.toTimeStamp(memberInfoEntity.getSendMailStartTime()));
        request.setSendMailStopTime(conversionUtility.toTimeStamp(memberInfoEntity.getSendMailStopTime()));
        if (memberInfoEntity.getNoAntiSocialFlag() != null) {
            request.setNoAntiSocialFlag(memberInfoEntity.getNoAntiSocialFlag().getValue());
        }

        return request;
    }
}
