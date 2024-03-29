package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.pass;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAccountingType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCardRegistType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCashDeliveryUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeConfDocumentType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCreditPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDirectDebitUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberListType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMetalPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMonthlyPayUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeNoAntiSocialFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOnlineLoginAdvisability;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendDirectMailFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendFaxPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSexUnnecessaryAnswer;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTransferPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import org.springframework.stereotype.Component;

/**
 *
 *メンバーパスHelper
 */
@Component
public class MemberPassHelper {
    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    /**
     *
     * @param conversionUtility
     */

    public MemberPassHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * 会員クラスに変換
     *
     * @param memberInfoEntityResponse 会員Entityレスポンス
     * @return 会員クラス
     */
    public MemberInfoEntity toMemberEntity(MemberInfoEntityResponse memberInfoEntityResponse) {
        if (memberInfoEntityResponse == null) {
            return null;
        }
        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();

        memberInfoEntity.setMemberInfoSeq(memberInfoEntityResponse.getMemberInfoSeq());
        memberInfoEntity.setMemberInfoStatus(EnumTypeUtil.getEnumFromValue(HTypeMemberInfoStatus.class,
                                                                           memberInfoEntityResponse.getMemberInfoStatus()
                                                                          ));
        memberInfoEntity.setMemberInfoUniqueId(memberInfoEntityResponse.getMemberInfoUniqueId());
        memberInfoEntity.setMemberInfoId(memberInfoEntityResponse.getMemberInfoId());
        memberInfoEntity.setMemberInfoPassword(memberInfoEntityResponse.getMemberInfoPassword());
        memberInfoEntity.setMemberInfoLastName(memberInfoEntityResponse.getMemberInfoLastName());
        memberInfoEntity.setMemberInfoFirstName(memberInfoEntityResponse.getMemberInfoFirstName());
        memberInfoEntity.setMemberInfoLastKana(memberInfoEntityResponse.getMemberInfoLastKana());
        memberInfoEntity.setMemberInfoFirstKana(memberInfoEntityResponse.getMemberInfoFirstKana());
        memberInfoEntity.setMemberInfoSex(EnumTypeUtil.getEnumFromValue(HTypeSexUnnecessaryAnswer.class,
                                                                        memberInfoEntityResponse.getMemberInfoSex()
                                                                       ));
        memberInfoEntity.setMemberInfoBirthday(
                        conversionUtility.toTimeStamp(memberInfoEntityResponse.getMemberInfoBirthday()));
        memberInfoEntity.setMemberInfoTel(memberInfoEntityResponse.getMemberInfoTel());
        memberInfoEntity.setMemberInfoContactTel(memberInfoEntityResponse.getMemberInfoContactTel());
        memberInfoEntity.setMemberInfoZipCode(memberInfoEntityResponse.getMemberInfoZipCode());
        memberInfoEntity.setMemberInfoPrefecture(memberInfoEntityResponse.getMemberInfoPrefecture());

        memberInfoEntity.setPrefectureType(EnumTypeUtil.getEnumFromValue(HTypePrefectureType.class,
                                                                         memberInfoEntityResponse.getMemberInfoPrefecture()
                                                                        ));
        memberInfoEntity.setMemberInfoAddress1(memberInfoEntityResponse.getMemberInfoAddress1());
        memberInfoEntity.setMemberInfoAddress2(memberInfoEntityResponse.getMemberInfoAddress2());
        memberInfoEntity.setMemberInfoAddress3(memberInfoEntityResponse.getMemberInfoAddress3());
        memberInfoEntity.setMemberInfoMail(memberInfoEntityResponse.getMemberInfoMail());
        memberInfoEntity.setShopSeq(1001);
        memberInfoEntity.setAccessUid(memberInfoEntityResponse.getAccessUid());
        memberInfoEntity.setVersionNo(memberInfoEntityResponse.getVersionNo());
        memberInfoEntity.setAdmissionYmd(memberInfoEntityResponse.getAdmissionYmd());
        memberInfoEntity.setSecessionYmd(memberInfoEntityResponse.getSecessionYmd());
        memberInfoEntity.setMemo(memberInfoEntityResponse.getMemo());
        memberInfoEntity.setMemberInfoFax(memberInfoEntityResponse.getMemberInfoFax());
        memberInfoEntity.setPaymentMemberId(memberInfoEntityResponse.getPaymentMemberId());
        memberInfoEntity.setLastLoginTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getLastLoginTime()));
        memberInfoEntity.setLastLoginUserAgent(memberInfoEntityResponse.getLastLoginUserAgent());
        memberInfoEntity.setPaymentMemberId(memberInfoEntityResponse.getPaymentMemberId());

        memberInfoEntity.setPaymentCardRegistType(EnumTypeUtil.getEnumFromValue(HTypeCardRegistType.class,
                                                                                memberInfoEntityResponse.getPaymentCardRegistType()
                                                                               ));
        memberInfoEntity.setPasswordNeedChangeFlag(EnumTypeUtil.getEnumFromValue(HTypePasswordNeedChangeFlag.class,
                                                                                 memberInfoEntityResponse.getPasswordNeedChangeFlag()
                                                                                ));
        memberInfoEntity.setLastLoginDeviceType(EnumTypeUtil.getEnumFromValue(HTypeDeviceType.class,
                                                                              memberInfoEntityResponse.getLastLoginDeviceType()
                                                                             ));
        memberInfoEntity.setLoginFailureCount(memberInfoEntityResponse.getLoginFailureCount());
        memberInfoEntity.setRegistTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getRegistTime()));
        memberInfoEntity.setUpdateTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getUpdateTime()));
        memberInfoEntity.setAccountLockTime(
                        conversionUtility.toTimeStamp(memberInfoEntityResponse.getAccountLockTime()));
        memberInfoEntity.setCustomerNo(memberInfoEntityResponse.getCustomerNo());
        memberInfoEntity.setRepresentativeName(memberInfoEntityResponse.getRepresentativeName());
        memberInfoEntity.setMemberInfoAddress4(memberInfoEntityResponse.getMemberInfoAddress4());
        memberInfoEntity.setMemberInfoAddress5(memberInfoEntityResponse.getMemberInfoAddress5());
        memberInfoEntity.setBusinessType(EnumTypeUtil.getEnumFromValue(HTypeBusinessType.class,
                                                                       memberInfoEntityResponse.getBusinessType()
                                                                      ));

        // FAXによるおトク情報希望フラグ
        memberInfoEntity.setSendFaxPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeSendFaxPermitFlag.class,
                                                                            memberInfoEntityResponse.getSendFaxPermitFlag()
                                                                           ));

        // DMによるおトク情報希望フラグ
        memberInfoEntity.setSendDirectMailFlag(EnumTypeUtil.getEnumFromValue(HTypeSendDirectMailFlag.class,
                                                                             memberInfoEntityResponse.getSendDirectMailFlag()
                                                                            ));
        memberInfoEntity.setNonConsultationDay(memberInfoEntityResponse.getNonConsultationDay());

        memberInfoEntity.setApproveStatus(EnumTypeUtil.getEnumFromValue(HTypeApproveStatus.class,
                                                                        memberInfoEntityResponse.getApproveStatus()
                                                                       ));
        memberInfoEntity.setCreditPaymentUseFlag(EnumTypeUtil.getEnumFromValue(HTypeCreditPaymentUseFlag.class,
                                                                               memberInfoEntityResponse.getCreditPaymentUseFlag()
                                                                              ));
        memberInfoEntity.setTransferPaymentUseFlag(EnumTypeUtil.getEnumFromValue(HTypeTransferPaymentUseFlag.class,
                                                                                 memberInfoEntityResponse.getTransferPaymentUseFlag()
                                                                                ));
        memberInfoEntity.setCashDeliveryUseFlag(EnumTypeUtil.getEnumFromValue(HTypeCashDeliveryUseFlag.class,
                                                                              memberInfoEntityResponse.getCashDeliveryUseFlag()
                                                                             ));
        memberInfoEntity.setDirectDebitUseFlag(EnumTypeUtil.getEnumFromValue(HTypeDirectDebitUseFlag.class,
                                                                             memberInfoEntityResponse.getDirectDebitUseFlag()
                                                                            ));
        memberInfoEntity.setMonthlyPayUseFlag(EnumTypeUtil.getEnumFromValue(HTypeMonthlyPayUseFlag.class,
                                                                            memberInfoEntityResponse.getMonthlyPayUseFlag()
                                                                           ));
        memberInfoEntity.setMemberListType(EnumTypeUtil.getEnumFromValue(HTypeMemberListType.class,
                                                                         memberInfoEntityResponse.getMemberListType()
                                                                        ));
        memberInfoEntity.setOnlineRegistFlag(EnumTypeUtil.getEnumFromValue(HTypeOnlineRegistFlag.class,
                                                                           memberInfoEntityResponse.getOnlineRegistFlag()
                                                                          ));
        memberInfoEntity.setConfDocumentType(EnumTypeUtil.getEnumFromValue(HTypeConfDocumentType.class,
                                                                           memberInfoEntityResponse.getConfDocumentType()
                                                                          ));
        memberInfoEntity.setMedicalTreatmentFlag(memberInfoEntityResponse.getMedicalTreatmentFlag());
        memberInfoEntity.setMedicalTreatmentMemo(memberInfoEntityResponse.getMedicalTreatmentMemo());
        memberInfoEntity.setMetalPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeMetalPermitFlag.class,
                                                                          memberInfoEntityResponse.getMetalPermitFlag()
                                                                         ));
        memberInfoEntity.setAccountingType(EnumTypeUtil.getEnumFromValue(HTypeAccountingType.class,
                                                                         memberInfoEntityResponse.getAccountingType()
                                                                        ));
        memberInfoEntity.setOnlineLoginAdvisability(EnumTypeUtil.getEnumFromValue(HTypeOnlineLoginAdvisability.class,
                                                                                  memberInfoEntityResponse.getOnlineLoginAdvisability()
                                                                                 ));
        memberInfoEntity.setSendMailPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeSendMailPermitFlag.class,
                                                                             memberInfoEntityResponse.getSendMailPermitFlag()
                                                                            ));
        memberInfoEntity.setSendMailStartTime(
                        conversionUtility.toTimeStamp(memberInfoEntityResponse.getSendMailStartTime()));
        memberInfoEntity.setSendMailStopTime(
                        conversionUtility.toTimeStamp(memberInfoEntityResponse.getSendMailStopTime()));
        memberInfoEntity.setNoAntiSocialFlag(EnumTypeUtil.getEnumFromValue(HTypeNoAntiSocialFlag.class,
                                                                           memberInfoEntityResponse.getNoAntiSocialFlag()
                                                                          ));
        return memberInfoEntity;
    }

    /**
     * 会員Entityリクエストに変換
     *
     * @param memberInfoEntity 会員クラス
     * @return 会員Entityリクエスト
     */

    public MemberInfoEntityRequest toMemberInfoEntityRequest(MemberInfoEntity memberInfoEntity) {
        MemberInfoEntityRequest memberInfoEntityRequest = new MemberInfoEntityRequest();
        if (memberInfoEntity == null) {
            return null;
        }

        memberInfoEntityRequest.setMemberInfoSeq(memberInfoEntity.getMemberInfoSeq());
        if (memberInfoEntity.getMemberInfoStatus() != null) {
            memberInfoEntityRequest.setMemberInfoStatus(memberInfoEntity.getMemberInfoStatus().getValue());
        }
        memberInfoEntityRequest.setMemberInfoUniqueId(memberInfoEntity.getMemberInfoUniqueId());
        memberInfoEntityRequest.setMemberInfoId(memberInfoEntity.getMemberInfoId());
        memberInfoEntityRequest.setMemberInfoPassword(memberInfoEntity.getMemberInfoPassword());
        memberInfoEntityRequest.setMemberInfoLastName(memberInfoEntity.getMemberInfoLastName());
        memberInfoEntityRequest.setMemberInfoFirstName(memberInfoEntity.getMemberInfoFirstName());
        memberInfoEntityRequest.setMemberInfoLastKana(memberInfoEntity.getMemberInfoLastKana());
        memberInfoEntityRequest.setMemberInfoFirstKana(memberInfoEntity.getMemberInfoFirstKana());
        if (memberInfoEntity.getMemberInfoSex() != null) {
            memberInfoEntityRequest.setMemberInfoSex(memberInfoEntity.getMemberInfoSex().getValue());
        }
        memberInfoEntityRequest.setMemberInfoBirthday(
                        conversionUtility.toTimeStamp(memberInfoEntity.getMemberInfoBirthday()));
        memberInfoEntityRequest.setMemberInfoTel(memberInfoEntity.getMemberInfoTel());
        memberInfoEntityRequest.setMemberInfoContactTel(memberInfoEntity.getMemberInfoContactTel());
        memberInfoEntityRequest.setMemberInfoZipCode(memberInfoEntity.getMemberInfoZipCode());
        memberInfoEntityRequest.setMemberInfoPrefecture(memberInfoEntity.getMemberInfoPrefecture());

        memberInfoEntityRequest.setPrefectureType(memberInfoEntity.getMemberInfoPrefecture());
        memberInfoEntityRequest.setMemberInfoAddress1(memberInfoEntity.getMemberInfoAddress1());
        memberInfoEntityRequest.setMemberInfoAddress2(memberInfoEntity.getMemberInfoAddress2());
        memberInfoEntityRequest.setMemberInfoAddress3(memberInfoEntity.getMemberInfoAddress3());
        memberInfoEntityRequest.setMemberInfoMail(memberInfoEntity.getMemberInfoMail());
        memberInfoEntityRequest.setAccessUid(memberInfoEntity.getAccessUid());
        memberInfoEntityRequest.setVersionNo(memberInfoEntity.getVersionNo());
        memberInfoEntityRequest.setAdmissionYmd(memberInfoEntity.getAdmissionYmd());
        memberInfoEntityRequest.setSecessionYmd(memberInfoEntity.getSecessionYmd());
        memberInfoEntityRequest.setMemo(memberInfoEntity.getMemo());
        memberInfoEntityRequest.setMemberInfoFax(memberInfoEntity.getMemberInfoFax());
        memberInfoEntityRequest.setPaymentMemberId(memberInfoEntity.getPaymentMemberId());
        memberInfoEntityRequest.setLastLoginTime(conversionUtility.toTimeStamp(memberInfoEntity.getLastLoginTime()));
        memberInfoEntityRequest.setLastLoginUserAgent(memberInfoEntity.getLastLoginUserAgent());
        memberInfoEntityRequest.setPaymentMemberId(memberInfoEntity.getPaymentMemberId());
        if (memberInfoEntity.getPaymentCardRegistType() != null) {
            memberInfoEntityRequest.setPaymentCardRegistType(memberInfoEntity.getPaymentCardRegistType().getValue());
        }
        if (memberInfoEntity.getPasswordNeedChangeFlag() != null) {
            memberInfoEntityRequest.setPasswordNeedChangeFlag(memberInfoEntity.getPasswordNeedChangeFlag().getValue());
        }
        if (memberInfoEntity.getLastLoginDeviceType() != null) {
            memberInfoEntityRequest.setLastLoginDeviceType(memberInfoEntity.getLastLoginDeviceType().getValue());
        }
        memberInfoEntityRequest.setLoginFailureCount(memberInfoEntity.getLoginFailureCount());
        memberInfoEntityRequest.setRegistTime(conversionUtility.toTimeStamp(memberInfoEntity.getRegistTime()));
        memberInfoEntityRequest.setUpdateTime(conversionUtility.toTimeStamp(memberInfoEntity.getUpdateTime()));
        memberInfoEntityRequest.setAccountLockTime(
                        conversionUtility.toTimeStamp(memberInfoEntity.getAccountLockTime()));
        memberInfoEntityRequest.setCustomerNo(memberInfoEntity.getCustomerNo());
        memberInfoEntityRequest.setRepresentativeName(memberInfoEntity.getRepresentativeName());
        memberInfoEntityRequest.setMemberInfoAddress4(memberInfoEntity.getMemberInfoAddress4());
        memberInfoEntityRequest.setMemberInfoAddress5(memberInfoEntity.getMemberInfoAddress5());
        if (memberInfoEntity.getBusinessType() != null) {
            memberInfoEntityRequest.setBusinessType(memberInfoEntity.getBusinessType().getValue());
        }
        if (memberInfoEntity.getSendFaxPermitFlag() != null) {
            // FAXによるおトク情報希望フラグ
            memberInfoEntityRequest.setSendFaxPermitFlag(memberInfoEntity.getSendFaxPermitFlag().getValue());
        }
        if (memberInfoEntity.getSendDirectMailFlag() != null) {
            // DMによるおトク情報希望フラグ
            memberInfoEntityRequest.setSendDirectMailFlag(memberInfoEntity.getSendDirectMailFlag().getValue());
        }
        memberInfoEntityRequest.setNonConsultationDay(memberInfoEntity.getNonConsultationDay());
        if (memberInfoEntity.getApproveStatus() != null) {
            memberInfoEntityRequest.setApproveStatus(memberInfoEntity.getApproveStatus().getValue());
        }
        if (memberInfoEntity.getDrugSalesType() != null) {
            memberInfoEntityRequest.setDrugSalesType(memberInfoEntity.getDrugSalesType().getValue());
        }
        if (memberInfoEntity.getMedicalEquipmentSalesType() != null) {
            memberInfoEntityRequest.setMedicalEquipmentSalesType(
                            memberInfoEntity.getMedicalEquipmentSalesType().getValue());
        }
        if (memberInfoEntity.getDentalMonopolySalesType() != null) {
            memberInfoEntityRequest.setDentalMonopolySalesType(
                            memberInfoEntity.getDentalMonopolySalesType().getValue());
        }
        if (memberInfoEntity.getCreditPaymentUseFlag() != null) {
            memberInfoEntityRequest.setCreditPaymentUseFlag(memberInfoEntity.getCreditPaymentUseFlag().getValue());
        }
        if (memberInfoEntity.getTransferPaymentUseFlag() != null) {
            memberInfoEntityRequest.setTransferPaymentUseFlag(memberInfoEntity.getTransferPaymentUseFlag().getValue());
        }
        if (memberInfoEntity.getCashDeliveryUseFlag() != null) {
            memberInfoEntityRequest.setCashDeliveryUseFlag(memberInfoEntity.getCashDeliveryUseFlag().getValue());
        }
        if (memberInfoEntity.getDirectDebitUseFlag() != null) {
            memberInfoEntityRequest.setDirectDebitUseFlag(memberInfoEntity.getDirectDebitUseFlag().getValue());
        }
        if (memberInfoEntity.getMonthlyPayUseFlag() != null) {
            memberInfoEntityRequest.setMonthlyPayUseFlag(memberInfoEntity.getMonthlyPayUseFlag().getValue());
        }
        if (memberInfoEntity.getMemberListType() != null) {
            memberInfoEntityRequest.setMemberListType(memberInfoEntity.getMemberListType().getValue());
        }
        if (memberInfoEntity.getOnlineRegistFlag() != null) {
            memberInfoEntityRequest.setOnlineRegistFlag(memberInfoEntity.getOnlineRegistFlag().getValue());
        }
        if (memberInfoEntity.getConfDocumentType() != null) {
            memberInfoEntityRequest.setConfDocumentType(memberInfoEntity.getConfDocumentType().getValue());
        }
        memberInfoEntityRequest.setMedicalTreatmentFlag(memberInfoEntity.getMedicalTreatmentFlag());
        memberInfoEntityRequest.setMedicalTreatmentMemo(memberInfoEntity.getMedicalTreatmentMemo());
        if (memberInfoEntity.getMetalPermitFlag() != null) {
            memberInfoEntityRequest.setMetalPermitFlag(memberInfoEntity.getMetalPermitFlag().getValue());
        }
        // 2023-renew No79 from here
        if (memberInfoEntity.getOrderCompletePermitFlag() != null) {
            memberInfoEntityRequest.setOrderCompletePermitFlag(
                            memberInfoEntity.getOrderCompletePermitFlag().getValue());
        }
        if (memberInfoEntity.getDeliveryCompletePermitFlag() != null) {
            memberInfoEntityRequest.setDeliveryCompletePermitFlag(
                            memberInfoEntity.getDeliveryCompletePermitFlag().getValue());
        }
        // 2023-renew No79 to here
        if (memberInfoEntity.getAccountingType() != null) {
            memberInfoEntityRequest.setAccountingType(memberInfoEntity.getAccountingType().getValue());
        }
        if (memberInfoEntity.getOnlineLoginAdvisability() != null) {
            memberInfoEntityRequest.setOnlineLoginAdvisability(
                            memberInfoEntity.getOnlineLoginAdvisability().getValue());
        }
        if (memberInfoEntity.getSendMailPermitFlag() != null) {
            memberInfoEntityRequest.setSendMailPermitFlag(memberInfoEntity.getSendMailPermitFlag().getValue());
        }
        memberInfoEntityRequest.setSendMailStartTime(
                        conversionUtility.toTimeStamp(memberInfoEntity.getSendMailStartTime()));
        memberInfoEntityRequest.setSendMailStopTime(
                        conversionUtility.toTimeStamp(memberInfoEntity.getSendMailStopTime()));
        if (memberInfoEntity.getNoAntiSocialFlag() != null) {
            memberInfoEntityRequest.setNoAntiSocialFlag(memberInfoEntity.getNoAntiSocialFlag().getValue());
        }

        return memberInfoEntityRequest;
    }
}
