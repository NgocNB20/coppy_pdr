/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.mail;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoMailScreenUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
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
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMailTemplateType;
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
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

/**
 * メールアドレス変更 Helper
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Component
public class MailHelper {

    private final ConversionUtility conversionUtility;

    public MailHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * 初期表示
     *
     * @param memberInfoEntity 会員エンティティ
     * @param mailModel        メールアドレス変更 Model
     */
    public void toPageForLoad(MemberInfoEntity memberInfoEntity, MailModel mailModel) {

        // 会員情報
        mailModel.setMemberInfoEntity(memberInfoEntity);

        // メールアドレス
        mailModel.setMemberInfoMail(memberInfoEntity.getMemberInfoMail());
    }

    /**
     * 変更会員情報の作成
     *
     * @param mailModel メール変更Model
     * @return 会員エンティティ
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public MemberInfoEntity toMemberInfoEntityForMailUpdate(MailModel mailModel)
                    throws IllegalAccessException, InvocationTargetException {

        // 会員情報
        MemberInfoEntity memberInfoEntity = ApplicationContextUtility.getBean(MemberInfoEntity.class);

        BeanUtils.copyProperties(memberInfoEntity, mailModel.getMemberInfoEntity());

        return memberInfoEntity;

    }

    /**
     * 会員クラスに変換
     *
     * @param memberInfoEntityResponse 会員Entityレスポンス
     * @return 会員クラス
     */
    public MemberInfoEntity toMemberInfoEntity(MemberInfoEntityResponse memberInfoEntityResponse) {

        if (ObjectUtils.isEmpty(memberInfoEntityResponse)) {
            return null;
        }

        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();

        memberInfoEntity.setMemberInfoSeq(memberInfoEntityResponse.getMemberInfoSeq());
        memberInfoEntity.setMemberInfoUniqueId(memberInfoEntityResponse.getMemberInfoUniqueId());
        memberInfoEntity.setMemberInfoId(memberInfoEntityResponse.getMemberInfoId());
        memberInfoEntity.setMemberInfoPassword(memberInfoEntityResponse.getMemberInfoPassword());
        memberInfoEntity.setMemberInfoLastName(memberInfoEntityResponse.getMemberInfoLastName());
        memberInfoEntity.setMemberInfoFirstName(memberInfoEntityResponse.getMemberInfoFirstName());
        memberInfoEntity.setMemberInfoLastKana(memberInfoEntityResponse.getMemberInfoLastKana());
        memberInfoEntity.setMemberInfoFirstKana(memberInfoEntityResponse.getMemberInfoFirstKana());
        memberInfoEntity.setMemberInfoBirthday(
                        conversionUtility.toTimeStamp(memberInfoEntityResponse.getMemberInfoBirthday()));
        memberInfoEntity.setMemberInfoTel(memberInfoEntityResponse.getMemberInfoTel());
        memberInfoEntity.setMemberInfoContactTel(memberInfoEntityResponse.getMemberInfoContactTel());
        memberInfoEntity.setMemberInfoZipCode(memberInfoEntityResponse.getMemberInfoZipCode());
        memberInfoEntity.setMemberInfoPrefecture(memberInfoEntityResponse.getMemberInfoPrefecture());
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
        memberInfoEntity.setLastLoginTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getLastLoginTime()));
        memberInfoEntity.setLastLoginUserAgent(memberInfoEntityResponse.getLastLoginUserAgent());
        memberInfoEntity.setPaymentMemberId(memberInfoEntityResponse.getPaymentMemberId());
        memberInfoEntity.setLoginFailureCount(memberInfoEntityResponse.getLoginFailureCount());
        memberInfoEntity.setAccountLockTime(
                        conversionUtility.toTimeStamp(memberInfoEntityResponse.getAccountLockTime()));
        memberInfoEntity.setRegistTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getRegistTime()));
        memberInfoEntity.setUpdateTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getUpdateTime()));
        memberInfoEntity.setCustomerNo(memberInfoEntityResponse.getCustomerNo());
        memberInfoEntity.setRepresentativeName(memberInfoEntityResponse.getRepresentativeName());
        memberInfoEntity.setMemberInfoAddress4(memberInfoEntityResponse.getMemberInfoAddress4());
        memberInfoEntity.setMemberInfoAddress5(memberInfoEntityResponse.getMemberInfoAddress5());
        memberInfoEntity.setNonConsultationDay(memberInfoEntityResponse.getNonConsultationDay());
        memberInfoEntity.setMedicalTreatmentFlag(memberInfoEntityResponse.getMedicalTreatmentFlag());
        memberInfoEntity.setMedicalTreatmentMemo(memberInfoEntityResponse.getMedicalTreatmentMemo());
        memberInfoEntity.setSendMailStartTime(
                        conversionUtility.toTimeStamp(memberInfoEntityResponse.getSendMailStartTime()));
        memberInfoEntity.setSendMailStopTime(
                        conversionUtility.toTimeStamp(memberInfoEntityResponse.getSendMailStopTime()));

        memberInfoEntity.setMemberInfoStatus(EnumTypeUtil.getEnumFromValue(HTypeMemberInfoStatus.class,
                                                                           memberInfoEntityResponse.getMemberInfoStatus()
                                                                          ));
        memberInfoEntity.setMemberInfoSex(EnumTypeUtil.getEnumFromValue(HTypeSexUnnecessaryAnswer.class,
                                                                        memberInfoEntityResponse.getMemberInfoSex()
                                                                       ));
        memberInfoEntity.setPrefectureType(EnumTypeUtil.getEnumFromValue(HTypePrefectureType.class,
                                                                         memberInfoEntityResponse.getPrefectureType()
                                                                        ));
        memberInfoEntity.setPaymentCardRegistType(EnumTypeUtil.getEnumFromValue(HTypeCardRegistType.class,
                                                                                memberInfoEntityResponse.getPaymentCardRegistType()
                                                                               ));
        memberInfoEntity.setPasswordNeedChangeFlag(EnumTypeUtil.getEnumFromValue(HTypePasswordNeedChangeFlag.class,
                                                                                 memberInfoEntityResponse.getPasswordNeedChangeFlag()
                                                                                ));
        memberInfoEntity.setLastLoginDeviceType(EnumTypeUtil.getEnumFromValue(HTypeDeviceType.class,
                                                                              memberInfoEntityResponse.getLastLoginDeviceType()
                                                                             ));
        memberInfoEntity.setBusinessType(EnumTypeUtil.getEnumFromValue(HTypeBusinessType.class,
                                                                       memberInfoEntityResponse.getBusinessType()
                                                                      ));
        memberInfoEntity.setSendFaxPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeSendFaxPermitFlag.class,
                                                                            memberInfoEntityResponse.getSendFaxPermitFlag()
                                                                           ));
        memberInfoEntity.setSendDirectMailFlag(EnumTypeUtil.getEnumFromValue(HTypeSendDirectMailFlag.class,
                                                                             memberInfoEntityResponse.getSendDirectMailFlag()
                                                                            ));
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
        memberInfoEntity.setNoAntiSocialFlag(EnumTypeUtil.getEnumFromValue(HTypeNoAntiSocialFlag.class,
                                                                           memberInfoEntityResponse.getNoAntiSocialFlag()
                                                                          ));

        return memberInfoEntity;
    }

    /**
     * メールアドレス変更画面用会員メールアドレス変更リクエストに変換
     *
     * @param memberInfoEntity 会員
     * @param mid              メールパラメータ
     * @param memberInfoMail   会員メールアドレス
     * @param mailTemplateType メールテンプレート種別
     * @return メールアドレス変更画面用会員メールアドレス変更リクエスト
     */
    public MemberInfoMailScreenUpdateRequest toMemberInfoMailScreenUpdateRequest(MemberInfoEntity memberInfoEntity,
                                                                                 String mid,
                                                                                 String memberInfoMail,
                                                                                 HTypeMailTemplateType mailTemplateType) {

        if (ObjectUtils.isEmpty(memberInfoEntity)) {
            return null;
        }

        MemberInfoMailScreenUpdateRequest request = new MemberInfoMailScreenUpdateRequest();

        request.setMemberInfoEntity(toMemberInfoEntityRequest(memberInfoEntity));
        request.setMid(mid);
        request.setMemberInfoMail(memberInfoMail);
        if (mailTemplateType != null) {
            request.setMailTemplateType(mailTemplateType.getValue());
        }

        return request;
    }

    /**
     * 会員Entityリクエストに変換
     *
     * @param memberInfoEntity 会員クラス
     * @return 会員Entityリクエスト
     */
    private MemberInfoEntityRequest toMemberInfoEntityRequest(MemberInfoEntity memberInfoEntity) {

        if (ObjectUtils.isEmpty(memberInfoEntity)) {
            return null;
        }

        MemberInfoEntityRequest request = new MemberInfoEntityRequest();

        request.setMemberInfoSeq(memberInfoEntity.getMemberInfoSeq());
        request.setMemberInfoUniqueId(memberInfoEntity.getMemberInfoUniqueId());
        request.setMemberInfoId(memberInfoEntity.getMemberInfoId());
        request.setMemberInfoPassword(memberInfoEntity.getMemberInfoPassword());
        request.setMemberInfoLastName(memberInfoEntity.getMemberInfoLastName());
        request.setMemberInfoFirstName(memberInfoEntity.getMemberInfoFirstName());
        request.setMemberInfoLastKana(memberInfoEntity.getMemberInfoLastKana());
        request.setMemberInfoFirstKana(memberInfoEntity.getMemberInfoFirstKana());
        request.setMemberInfoBirthday(memberInfoEntity.getMemberInfoBirthday());
        request.setMemberInfoTel(memberInfoEntity.getMemberInfoTel());
        request.setMemberInfoContactTel(memberInfoEntity.getMemberInfoContactTel());
        request.setMemberInfoZipCode(memberInfoEntity.getMemberInfoZipCode());
        request.setMemberInfoPrefecture(memberInfoEntity.getMemberInfoPrefecture());
        request.setMemberInfoAddress1(memberInfoEntity.getMemberInfoAddress1());
        request.setMemberInfoAddress2(memberInfoEntity.getMemberInfoAddress2());
        request.setMemberInfoAddress3(memberInfoEntity.getMemberInfoAddress3());
        request.setMemberInfoMail(memberInfoEntity.getMemberInfoMail());
        request.setAccessUid(memberInfoEntity.getAccessUid());
        request.setVersionNo(memberInfoEntity.getVersionNo());
        request.setAdmissionYmd(memberInfoEntity.getAdmissionYmd());
        request.setSecessionYmd(memberInfoEntity.getSecessionYmd());
        request.setMemo(memberInfoEntity.getMemo());
        request.setMemberInfoFax(memberInfoEntity.getMemberInfoFax());
        request.setLastLoginTime(memberInfoEntity.getLastLoginTime());
        request.setLastLoginUserAgent(memberInfoEntity.getLastLoginUserAgent());
        request.setPaymentMemberId(memberInfoEntity.getPaymentMemberId());
        request.setLoginFailureCount(memberInfoEntity.getLoginFailureCount());
        request.setAccountLockTime(memberInfoEntity.getAccountLockTime());
        request.setRegistTime(memberInfoEntity.getRegistTime());
        request.setUpdateTime(memberInfoEntity.getUpdateTime());
        request.setCustomerNo(memberInfoEntity.getCustomerNo());
        request.setRepresentativeName(memberInfoEntity.getRepresentativeName());
        request.setMemberInfoAddress4(memberInfoEntity.getMemberInfoAddress4());
        request.setMemberInfoAddress5(memberInfoEntity.getMemberInfoAddress5());
        request.setNonConsultationDay(memberInfoEntity.getNonConsultationDay());
        request.setMedicalTreatmentFlag(memberInfoEntity.getMedicalTreatmentFlag());
        request.setMedicalTreatmentMemo(memberInfoEntity.getMedicalTreatmentMemo());
        request.setSendMailStartTime(memberInfoEntity.getSendMailStartTime());
        request.setSendMailStopTime(memberInfoEntity.getSendMailStopTime());

        if (memberInfoEntity.getMemberInfoStatus() != null) {
            request.setMemberInfoStatus(memberInfoEntity.getMemberInfoStatus().getValue());
        }
        if (memberInfoEntity.getMemberInfoSex() != null) {
            request.setMemberInfoSex(memberInfoEntity.getMemberInfoSex().getValue());
        }
        if (memberInfoEntity.getPrefectureType() != null) {
            request.setPrefectureType(memberInfoEntity.getPrefectureType().getValue());
        }
        if (memberInfoEntity.getPaymentCardRegistType() != null) {
            request.setPaymentCardRegistType(memberInfoEntity.getPaymentCardRegistType().getValue());
        }
        if (memberInfoEntity.getPasswordNeedChangeFlag() != null) {
            request.setPasswordNeedChangeFlag(memberInfoEntity.getPasswordNeedChangeFlag().getValue());
        }
        if (memberInfoEntity.getLastLoginDeviceType() != null) {
            request.setLastLoginDeviceType(memberInfoEntity.getLastLoginDeviceType().getValue());
        }
        if (memberInfoEntity.getBusinessType() != null) {
            request.setBusinessType(memberInfoEntity.getBusinessType().getValue());
        }
        if (memberInfoEntity.getSendFaxPermitFlag() != null) {
            request.setSendFaxPermitFlag(memberInfoEntity.getSendFaxPermitFlag().getValue());
        }
        if (memberInfoEntity.getSendDirectMailFlag() != null) {
            request.setSendDirectMailFlag(memberInfoEntity.getSendDirectMailFlag().getValue());
        }
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
        if (memberInfoEntity.getNoAntiSocialFlag() != null) {
            request.setNoAntiSocialFlag(memberInfoEntity.getNoAntiSocialFlag().getValue());
        }

        return request;
    }
}
