/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.mail;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAccountingType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCardRegistType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCashDeliveryUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeConfDocumentType;
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
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import org.springframework.stereotype.Component;

/**
 * メールアドレス変更 Helper
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Component
public class MemberMailHelper {

    /**
     * 初期表示<br/>
     * 既存メールアドレスのセット<br/>
     *
     * @param memberInfoEntity 会員情報
     * @param memberMailModel  メールアドレス変更Model
     */
    public void toPageForLoad(MemberInfoEntity memberInfoEntity, MemberMailModel memberMailModel) {
        memberMailModel.setMemberInfoMail(memberInfoEntity.getMemberInfoMail());
    }

    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    /**
     *
     * @param conversionUtility 変換ユーティリティクラス
     */
    public MemberMailHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * 会員クラスに変換
     *
     * @param memberInfoEntityResponse 会員Entityレスポンス
     * @return 会員クラス
     */
    public MemberInfoEntity toMemberInfoEntity(MemberInfoEntityResponse memberInfoEntityResponse) {
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
        memberInfoEntity.setPrefectureType(EnumTypeUtil.getEnumFromLabel(HTypePrefectureType.class,
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

        memberInfoEntity.setPaymentCardRegistType(EnumTypeUtil.getEnumFromLabel(HTypeCardRegistType.class,
                                                                                memberInfoEntityResponse.getPaymentCardRegistType()
                                                                               ));
        memberInfoEntity.setPasswordNeedChangeFlag(EnumTypeUtil.getEnumFromLabel(HTypePasswordNeedChangeFlag.class,
                                                                                 memberInfoEntityResponse.getPasswordNeedChangeFlag()
                                                                                ));
        memberInfoEntity.setLastLoginDeviceType(EnumTypeUtil.getEnumFromLabel(HTypeDeviceType.class,
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
        memberInfoEntity.setBusinessType(EnumTypeUtil.getEnumFromLabel(HTypeBusinessType.class,
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
        // 2023-renew No79 from here
        memberInfoEntity.setOrderCompletePermitFlag(EnumTypeUtil.getEnumFromValue(HTypeOrderCompletePermitFlag.class,
                                                                                  memberInfoEntityResponse.getOrderCompletePermitFlag()
                                                                                 ));
        memberInfoEntity.setDeliveryCompletePermitFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeDeliveryCompletePermitFlag.class,
                                                      memberInfoEntityResponse.getDeliveryCompletePermitFlag()
                                                     ));
        // 2023-renew No79 to here
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
}
