// 2023-renew No85-1 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.reset;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginAdvisabilityEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginAdvisabilityGetRequest;
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
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.loginadvisability.LoginAdvisabilityEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

/**
 * パスワードリセット Helper
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Component
public class ResetHelper {

    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    public ResetHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
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
        entity.setMemberInfoStatus(EnumTypeUtil.getEnumFromValue(HTypeMemberInfoStatus.class,
                                                                 memberInfoEntityResponse.getMemberInfoStatus()
                                                                ));
        entity.setMemberInfoUniqueId(memberInfoEntityResponse.getMemberInfoUniqueId());
        entity.setMemberInfoId(memberInfoEntityResponse.getMemberInfoId());
        entity.setMemberInfoPassword(memberInfoEntityResponse.getMemberInfoPassword());
        entity.setMemberInfoLastName(memberInfoEntityResponse.getMemberInfoLastName());
        entity.setMemberInfoFirstName(memberInfoEntityResponse.getMemberInfoFirstName());
        entity.setMemberInfoLastKana(memberInfoEntityResponse.getMemberInfoLastKana());
        entity.setMemberInfoFirstKana(memberInfoEntityResponse.getMemberInfoFirstKana());
        entity.setMemberInfoSex(EnumTypeUtil.getEnumFromValue(HTypeSexUnnecessaryAnswer.class,
                                                              memberInfoEntityResponse.getMemberInfoSex()
                                                             ));
        entity.setMemberInfoTel(memberInfoEntityResponse.getMemberInfoTel());
        entity.setMemberInfoContactTel(memberInfoEntityResponse.getMemberInfoContactTel());
        entity.setMemberInfoZipCode(memberInfoEntityResponse.getMemberInfoZipCode());
        entity.setMemberInfoPrefecture(memberInfoEntityResponse.getMemberInfoPrefecture());
        entity.setPrefectureType(EnumTypeUtil.getEnumFromValue(HTypePrefectureType.class,
                                                               memberInfoEntityResponse.getPrefectureType()
                                                              ));

        entity.setMemberInfoAddress1(memberInfoEntityResponse.getMemberInfoAddress1());
        entity.setMemberInfoAddress2(memberInfoEntityResponse.getMemberInfoAddress2());
        entity.setMemberInfoAddress3(memberInfoEntityResponse.getMemberInfoAddress3());
        entity.setMemberInfoAddress4(memberInfoEntityResponse.getMemberInfoAddress4());
        entity.setMemberInfoAddress5(memberInfoEntityResponse.getMemberInfoAddress5());
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
        entity.setPaymentCardRegistType(EnumTypeUtil.getEnumFromValue(HTypeCardRegistType.class,
                                                                      memberInfoEntityResponse.getPrefectureType()
                                                                     ));
        entity.setPasswordNeedChangeFlag(EnumTypeUtil.getEnumFromValue(HTypePasswordNeedChangeFlag.class,
                                                                       memberInfoEntityResponse.getPasswordNeedChangeFlag()
                                                                      ));
        entity.setLastLoginDeviceType(EnumTypeUtil.getEnumFromValue(HTypeDeviceType.class,
                                                                    memberInfoEntityResponse.getLastLoginDeviceType()
                                                                   ));
        entity.setLoginFailureCount(memberInfoEntityResponse.getLoginFailureCount());
        entity.setAccountLockTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getAccountLockTime()));
        entity.setRegistTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getRegistTime()));
        entity.setUpdateTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getUpdateTime()));

        entity.setCustomerNo(memberInfoEntityResponse.getCustomerNo());
        entity.setRepresentativeName(memberInfoEntityResponse.getRepresentativeName());
        entity.setBusinessType(EnumTypeUtil.getEnumFromValue(HTypeBusinessType.class,
                                                             memberInfoEntityResponse.getBusinessType()
                                                            ));
        entity.setSendFaxPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeSendFaxPermitFlag.class,
                                                                  memberInfoEntityResponse.getSendFaxPermitFlag()
                                                                 ));
        entity.setSendDirectMailFlag(EnumTypeUtil.getEnumFromValue(HTypeSendDirectMailFlag.class,
                                                                   memberInfoEntityResponse.getSendDirectMailFlag()
                                                                  ));
        entity.setNonConsultationDay(memberInfoEntityResponse.getNonConsultationDay());
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
        entity.setMedicalTreatmentFlag(memberInfoEntityResponse.getMedicalTreatmentFlag());
        entity.setMedicalTreatmentMemo(memberInfoEntityResponse.getMedicalTreatmentMemo());
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
        entity.setSendMailStartTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getSendMailStartTime()));
        entity.setSendMailStopTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getSendMailStopTime()));
        entity.setNoAntiSocialFlag(EnumTypeUtil.getEnumFromValue(HTypeNoAntiSocialFlag.class,
                                                                 memberInfoEntityResponse.getNoAntiSocialFlag()
                                                                ));

        return entity;
    }

    /**
     * ログイン可否判定取得リクエストに変換
     *
     * @param memberInfoEntity 会員Entity
     * @return ログイン可否判定取得リクエスト
     */
    public LoginAdvisabilityGetRequest toLoginAdvisabilityGetRequest(MemberInfoEntity memberInfoEntity) {

        if (ObjectUtils.isEmpty(memberInfoEntity)) {
            return null;
        }

        LoginAdvisabilityGetRequest request = new LoginAdvisabilityGetRequest();

        if (memberInfoEntity.getMemberInfoStatus() != null) {
            request.setMemberInfoStatus(memberInfoEntity.getMemberInfoStatus().getValue());
        }
        if (memberInfoEntity.getApproveStatus() != null) {
            request.setApproveStatus(memberInfoEntity.getApproveStatus().getValue());
        }
        if (memberInfoEntity.getOnlineLoginAdvisability() != null) {
            request.setOnlineloginAdvisability(memberInfoEntity.getOnlineLoginAdvisability().getValue());
        }
        if (memberInfoEntity.getMemberListType() != null) {
            request.setMemberListType(memberInfoEntity.getMemberListType().getValue());
        }
        if (memberInfoEntity.getAccountingType() != null) {
            request.setAccountingType(memberInfoEntity.getAccountingType().getValue());
        }

        return request;
    }

    /**
     * ログイン可否判定Entityに変換
     *
     * @param loginAdvisabilityEntityResponse ログイン可否判定Entityレスポンス
     * @return ログイン可否判定Entity
     */
    public LoginAdvisabilityEntity toLoginAdvisabilityEntity(LoginAdvisabilityEntityResponse loginAdvisabilityEntityResponse) {
        if (loginAdvisabilityEntityResponse == null) {
            return null;
        }
        LoginAdvisabilityEntity entity = new LoginAdvisabilityEntity();

        entity.setLoginAdvisabilityseq(loginAdvisabilityEntityResponse.getLoginAdvisabilityseq());
        entity.setMemberInfoStatus(loginAdvisabilityEntityResponse.getMemberInfoStatus());
        entity.setApproveStatus(loginAdvisabilityEntityResponse.getApproveStatus());
        entity.setOnlineloginAdvisability(loginAdvisabilityEntityResponse.getOnlineloginAdvisability());
        entity.setMemberListType(loginAdvisabilityEntityResponse.getMemberListType());
        entity.setAccountingType(loginAdvisabilityEntityResponse.getAccountingType());
        entity.setLoginAdvisabilitytype(loginAdvisabilityEntityResponse.getLoginAdvisabilitytype());

        return entity;
    }
}
// 2023-renew No85-1 to here