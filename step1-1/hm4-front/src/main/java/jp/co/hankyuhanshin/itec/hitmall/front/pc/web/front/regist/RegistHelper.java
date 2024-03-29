/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginAdvisabilityEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginAdvisabilityGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoScreenRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoScreenUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ZenHanConversionUtility;
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
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeFrontBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMedicalTreatmentFlag;
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
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.TempMemberInfoDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.loginadvisability.LoginAdvisabilityEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.NonConsultationDayUtility;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

/**
 * 本会員登録 Helperクラス
 * // PDR Migrate Customization from here
 * PDR#011 08_データ連携（顧客情報）
 * <pre>
 * 会員情報表示アクション
 * </pre>
 * <pre>
 * 本会員登録確認Dxo
 * </pre>
 * // PDR Migrate Customization to here
 *
 * @author kimura
 */
@Component
public class RegistHelper {

    // PDR Migrate Customization from here
    /**
     * 休診曜日用Utility
     */
    private final NonConsultationDayUtility nonConsultationDayUtility;

    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    /**
     * 診療項目_使用
     */
    public static final String MEDICAL_TREATMENT_ON = "1";

    /**
     * コンストラクタ
     *
     * @param nonConsultationDayUtility データ連携（顧客情報）休診曜日判定用Utilityクラス
     * @param conversionUtility         変換ユーティリティクラス
     */
    @Autowired
    public RegistHelper(NonConsultationDayUtility nonConsultationDayUtility, ConversionUtility conversionUtility) {
        this.nonConsultationDayUtility = nonConsultationDayUtility;
        this.conversionUtility = conversionUtility;
    }

    /**
     * 会員追加属性取得結果をページに反映
     *
     * @param registModel 本会員登録入力画面
     */
    public void toPageForLoad(RegistModel registModel) {

        // メールによるおトク情報　デフォルト「1：希望する」
        registModel.setSendFax(true);
        // FAXによるおトク情報 　デフォルト「1：希望する」
        registModel.setSendMail(true);
        // 2023-renew No79 from here
        // 注文完了メール「1：希望する」
        registModel.setOrderCompletePermitFlag(true);
        // 発送完了メール「1：希望する」
        registModel.setDeliveryCompletePermitFlag(true);
        // 2023-renew No79 to here
        // 2023-renew No85-1 from here
        // 反社会的勢力ではないことの保証フラグによるおトク情報 　デフォルト「0：保証しない」
        registModel.setNoAntiSocialFlag(HTypeNoAntiSocialFlag.OFF.getValue());

        if (registModel.getMemberInfoEntity() != null) {
            MemberInfoEntity memberInfoEntity = registModel.getMemberInfoEntity();

            ZenHanConversionUtility zenHanConversionUtility =
                            ApplicationContextUtility.getBean(ZenHanConversionUtility.class);

            // メールアドレス
            if (StringUtils.isEmpty(registModel.getMemberInfoMail())) {
                registModel.setMemberInfoMail(memberInfoEntity.getMemberInfoMail());
            }

            // 氏名
            registModel.setMemberInfoLastName(
                            zenHanConversionUtility.toZenkaku(memberInfoEntity.getMemberInfoLastName()));

            // フリガナ
            registModel.setMemberInfoLastKana(memberInfoEntity.getMemberInfoLastKana());

            //代表者名
            registModel.setRepresentativeName(
                            zenHanConversionUtility.toZenkaku(memberInfoEntity.getRepresentativeName()));

            //業種
            if (memberInfoEntity.getBusinessType() != null) {
                registModel.setBusinessType(memberInfoEntity.getBusinessType().getValue());
            }

            // 電話番号
            registModel.setMemberInfoTel(memberInfoEntity.getMemberInfoTel());
            registModel.setMemberInfoContactTel(memberInfoEntity.getMemberInfoContactTel());
            registModel.setMemberInfoFax(memberInfoEntity.getMemberInfoFax());

            // 住所情報のセット
            registModel.setMemberInfoPrefecture(memberInfoEntity.getMemberInfoPrefecture());
            registModel.setMemberInfoAddress1(
                            zenHanConversionUtility.toZenkaku(memberInfoEntity.getMemberInfoAddress1()));
            registModel.setMemberInfoAddress2(
                            zenHanConversionUtility.toZenkaku(memberInfoEntity.getMemberInfoAddress2()));
            registModel.setMemberInfoAddress3(
                            zenHanConversionUtility.toZenkaku(memberInfoEntity.getMemberInfoAddress3()));

            // 郵便番号
            if (memberInfoEntity.getMemberInfoZipCode() != null) {

                // 変換Helper取得
                ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

                String[] zipCodeArray = conversionUtility.toZipCodeArray(memberInfoEntity.getMemberInfoZipCode());
                registModel.setMemberInfoZipCode1(zipCodeArray[0]);
                registModel.setMemberInfoZipCode2(zipCodeArray[1]);
            }
        }
        // 2023-renew No85-1 to here
    }

    /**
     * 会員追加属性選択結果、休診曜日をページに反映
     *
     * <pre>
     * 休診曜日の反映を追加
     * </pre>
     *
     * @param registModel 本会員登録確認画面
     */
    public void toPageForLoadDoLoadConfirm(RegistModel registModel) {
        // PDR Migrate Customization from here
        // 休診曜日を設定
        registModel.setNonConsultation(
                        nonConsultationDayUtility.getNonConsultationDay(createNonConsultationDay(registModel)));
        // PDR Migrate Customization to here

        // 診療項目を設定
        setMedicalTreatment(registModel);

    }

    /**
     * PDR#011 08_データ連携（顧客情報）
     * <p>
     * // PDR Migrate Customization to here
     * 初期表示
     * ・画面表示用の各種ラジオ選択子のセット
     * ・仮会員情報を画面項目にセット
     *
     * @param tempMemberInfoDto 仮会員登録情報
     * @param registModel       本会員登録入力画面
     */
    public void toPageForLoad(TempMemberInfoDto tempMemberInfoDto, RegistModel registModel) {

        MemberInfoEntity memberInfoEntity = tempMemberInfoDto.getMemberInfoEntity();

        // メールアドレス
        registModel.setMemberInfoMail(memberInfoEntity.getMemberInfoMail());

        // 氏名
        registModel.setMemberInfoLastName(memberInfoEntity.getMemberInfoLastName());

        // フリガナ
        registModel.setMemberInfoLastKana(memberInfoEntity.getMemberInfoLastKana());

        // 性別
        if (memberInfoEntity.getMemberInfoSex() != null) {
            registModel.setMemberInfoSex(memberInfoEntity.getMemberInfoSex().getValue());
        }

        // 電話番号
        registModel.setMemberInfoTel(memberInfoEntity.getMemberInfoTel());
        registModel.setMemberInfoContactTel(memberInfoEntity.getMemberInfoContactTel());
        registModel.setMemberInfoFax(memberInfoEntity.getMemberInfoFax());

        // 住所情報のセット
        registModel.setMemberInfoPrefecture(memberInfoEntity.getMemberInfoPrefecture());
        registModel.setMemberInfoAddress1(memberInfoEntity.getMemberInfoAddress1());
        registModel.setMemberInfoAddress2(memberInfoEntity.getMemberInfoAddress2());
        registModel.setMemberInfoAddress3(memberInfoEntity.getMemberInfoAddress3());

        // 郵便番号
        if (memberInfoEntity.getMemberInfoZipCode() != null) {

            // 変換Helper取得
            ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

            String[] zipCodeArray = conversionUtility.toZipCodeArray(memberInfoEntity.getMemberInfoZipCode());
            registModel.setMemberInfoZipCode1(zipCodeArray[0]);
            registModel.setMemberInfoZipCode2(zipCodeArray[1]);
        }
    }

    /**
     * 登録する会員情報の作成
     * // PDR Migrate Customization from here
     * <pre>
     * 大まかな流れはPKG標準のソースを流用
     * 必要な項目を追加
     * 変数名等を今回用に変更
     * </pre>
     * // PDR Migrate Customization to here
     *
     * @param registModel 本会員登録確認画面
     * @return 登録する会員情報
     */
    public MemberInfoEntity toMemberInfoEntityForMemberInfoRegist(RegistModel registModel) {
        // PDR Migrate Customization from here
        // 本会員登録確認画面
        // 会員情報の作成
        MemberInfoEntity memberInfoEntity = ApplicationContextUtility.getBean(MemberInfoEntity.class);
        // PDR Migrate Customization to here

        // ID・メールアドレス
        memberInfoEntity.setMemberInfoId(registModel.getMemberInfoMail());
        memberInfoEntity.setMemberInfoMail(registModel.getMemberInfoMail());

        // 画面入力値で上書き
        // PDR Migrate Customization from here
        // 使用しない項目を削除 */
        // フリガナ(姓)
        // フリガナ(名)
        // 連絡先番号
        // 性別
        // 生年月日
        memberInfoEntity.setMemberInfoLastName(registModel.getMemberInfoLastName());
        memberInfoEntity.setMemberInfoLastKana(registModel.getMemberInfoLastKana());
        // 都道府県は会員住所市区群の先頭に連結する
        memberInfoEntity.setMemberInfoPrefecture(registModel.getMemberInfoPrefecture());
        memberInfoEntity.setMemberInfoAddress1(
                        registModel.getMemberInfoPrefecture() + registModel.getMemberInfoAddress1());
        memberInfoEntity.setMemberInfoAddress2(registModel.getMemberInfoAddress2());
        memberInfoEntity.setMemberInfoAddress3(registModel.getMemberInfoAddress3());
        memberInfoEntity.setMemberInfoTel(registModel.getMemberInfoTel());
        memberInfoEntity.setMemberInfoContactTel(registModel.getMemberInfoContactTel());
        memberInfoEntity.setMemberInfoFax(registModel.getMemberInfoFax());
        // 代表者名
        memberInfoEntity.setRepresentativeName(registModel.getRepresentativeName());
        memberInfoEntity.setMemberInfoPassword(registModel.getMemberInfoPassWord());

        // 業種
        if (HTypeFrontBusinessType.OTHER.getValue().equals(registModel.getBusinessType())) {
            // 3:その他が選択されていた場合は18:その他（上記以外）で登録
            memberInfoEntity.setBusinessType(HTypeBusinessType.OTHERS);
        } else {
            memberInfoEntity.setBusinessType(
                            EnumTypeUtil.getEnumFromValue(HTypeBusinessType.class, registModel.getBusinessType()));
        }
        // 休診曜日
        memberInfoEntity.setNonConsultationDay(createNonConsultationDay(registModel));
        // PDR Migrate Customization to here

        // 性別
        memberInfoEntity.setMemberInfoSex(
                        EnumTypeUtil.getEnumFromValue(HTypeSexUnnecessaryAnswer.class, registModel.getMemberInfoSex()));

        // 郵便番号
        memberInfoEntity.setMemberInfoZipCode(
                        registModel.getMemberInfoZipCode1() + registModel.getMemberInfoZipCode2());

        // 反社会的勢力ではないことの保証フラグ
        if (!StringUtils.isEmpty(registModel.getNoAntiSocialFlag())) {
            memberInfoEntity.setNoAntiSocialFlag(HTypeNoAntiSocialFlag.ON);
        } else {
            memberInfoEntity.setNoAntiSocialFlag(HTypeNoAntiSocialFlag.OFF);
        }

        // メール送信希望フラグのセット
        if (registModel.isSendMail()) {
            memberInfoEntity.setSendMailPermitFlag(HTypeSendMailPermitFlag.ON);
        } else {
            memberInfoEntity.setSendMailPermitFlag(HTypeSendMailPermitFlag.OFF);
        }

        // PDR Migrate Customization from here
        // FAX配信希望フラグのセット
        memberInfoEntity.setSendFaxPermitFlag(
                        registModel.isSendFax() ? HTypeSendFaxPermitFlag.ON : HTypeSendFaxPermitFlag.OFF);
        // PDR Migrate Customization to here

        // 確認書類
        if (HTypeFrontBusinessType.OTHER.getValue().equals(registModel.getBusinessType())) {
            // 3:その他が選択されていた場合は７:― を設定
            memberInfoEntity.setConfDocumentType(HTypeConfDocumentType.NOT_SET);
        } else {
            // それ以外は3:未確認 を設定
            memberInfoEntity.setConfDocumentType(HTypeConfDocumentType.UNCONF);
        }

        // 診療項目
        memberInfoEntity.setMedicalTreatmentFlag(registModel.getMedicalTreatment());
        // 診療項目その他
        memberInfoEntity.setMedicalTreatmentMemo(registModel.getMedicalTreatmentMemo());

        // 金属商品価格お知らせメールのセット
        if (registModel.isMetalPermitFlag()) {
            memberInfoEntity.setMetalPermitFlag(HTypeMetalPermitFlag.ON);
        } else {
            memberInfoEntity.setMetalPermitFlag(HTypeMetalPermitFlag.OFF);
        }

        // 2023-renew No79 from here
        //注文完了メール
        if (registModel.isOrderCompletePermitFlag()) {
            memberInfoEntity.setOrderCompletePermitFlag(HTypeOrderCompletePermitFlag.ON);
        } else {
            memberInfoEntity.setOrderCompletePermitFlag(HTypeOrderCompletePermitFlag.OFF);
        }

        //発送完了メール
        if (registModel.isDeliveryCompletePermitFlag()) {
            memberInfoEntity.setDeliveryCompletePermitFlag(HTypeDeliveryCompletePermitFlag.ON);
        } else {
            memberInfoEntity.setDeliveryCompletePermitFlag(HTypeDeliveryCompletePermitFlag.OFF);
        }
        // 2023-renew No79 to here

        // 経理区分（１：顧客　固定）
        memberInfoEntity.setAccountingType(HTypeAccountingType.CUSTOMER);

        // オンラインログイン可否（０：仮登録　固定）
        memberInfoEntity.setOnlineLoginAdvisability(HTypeOnlineLoginAdvisability.TEMPORARY_ACCOUNT);
        // PDR Migrate Customization to here

        return memberInfoEntity;
    }

    /**
     * 本会員登録画面用会員情報更新リクエストに変換
     *
     * @param memberInfoEntity 会員情報表示画面
     * @return 本会員登録画面用会員情報更新リクエスト
     */
    public MemberInfoScreenUpdateRequest toMemberInfoScreenUpdateRequest(MemberInfoEntity memberInfoEntity) {

        // 元会員情報コピー
        MemberInfoScreenUpdateRequest request = new MemberInfoScreenUpdateRequest();

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
        if (memberInfoEntity.getOrderCompletePermitFlag() != null){
            request.setOrderCompletePermitFlag(memberInfoEntity.getOrderCompletePermitFlag().getValue());
        }
        if (memberInfoEntity.getDeliveryCompletePermitFlag() != null){
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
    // PDR Migrate Customization from here

    /**
     * 画面に入力された休診曜日の値から休診曜日の文字列を作成
     *
     * @param registModel 本会員登録確認画面
     * @return 休診曜日文字列
     */
    public String createNonConsultationDay(RegistModel registModel) {
        boolean[] amNonConsultation = new boolean[9];
        amNonConsultation[0] = registModel.isAmSunNonConsultation();
        amNonConsultation[1] = registModel.isAmMonNonConsultation();
        amNonConsultation[2] = registModel.isAmTueNonConsultation();
        amNonConsultation[3] = registModel.isAmWedNonConsultation();
        amNonConsultation[4] = registModel.isAmThuNonConsultation();
        amNonConsultation[5] = registModel.isAmFriNonConsultation();
        amNonConsultation[6] = registModel.isAmSatNonConsultation();
        amNonConsultation[7] = registModel.isAmHolidayNonConsultation();
        amNonConsultation[8] = registModel.isAllConsultation();

        boolean[] pmNonConsultation = new boolean[9];
        pmNonConsultation[0] = registModel.isPmSunNonConsultation();
        pmNonConsultation[1] = registModel.isPmMonNonConsultation();
        pmNonConsultation[2] = registModel.isPmTueNonConsultation();
        pmNonConsultation[3] = registModel.isPmWedNonConsultation();
        pmNonConsultation[4] = registModel.isPmThuNonConsultation();
        pmNonConsultation[5] = registModel.isPmFriNonConsultation();
        pmNonConsultation[6] = registModel.isPmSatNonConsultation();
        pmNonConsultation[7] = registModel.isPmHolidayNonConsultation();
        pmNonConsultation[8] = registModel.isAllConsultation();

        return nonConsultationDayUtility.getNonConsultationDayString(amNonConsultation, pmNonConsultation);
    }

    /**
     * 診療項目設定
     *
     * @param registModel 本会員登録 Model
     */
    protected void setMedicalTreatment(RegistModel registModel) {

        // 診療項目
        String tempMedicalTreatment = "";

        // 入力画面でチェックかつ、表示対象の診療項目を対象とする。
        // 診療項目1
        if (Boolean.valueOf(registModel.isMedicalTreatment1()) && registModel.getMedicalTreatment1Disp()
                                                                             .equals(MEDICAL_TREATMENT_ON)) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目2
        if (Boolean.valueOf(registModel.isMedicalTreatment2()) && registModel.getMedicalTreatment2Disp()
                                                                             .equals(MEDICAL_TREATMENT_ON)) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目3
        if (Boolean.valueOf(registModel.isMedicalTreatment3()) && registModel.getMedicalTreatment3Disp()
                                                                             .equals(MEDICAL_TREATMENT_ON)) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目4
        if (Boolean.valueOf(registModel.isMedicalTreatment4()) && registModel.getMedicalTreatment4Disp()
                                                                             .equals(MEDICAL_TREATMENT_ON)) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目5
        if (Boolean.valueOf(registModel.isMedicalTreatment5()) && registModel.getMedicalTreatment5Disp()
                                                                             .equals(MEDICAL_TREATMENT_ON)) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目6
        if (Boolean.valueOf(registModel.isMedicalTreatment6()) && registModel.getMedicalTreatment6Disp()
                                                                             .equals(MEDICAL_TREATMENT_ON)) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目7
        if (Boolean.valueOf(registModel.isMedicalTreatment7()) && registModel.getMedicalTreatment7Disp()
                                                                             .equals(MEDICAL_TREATMENT_ON)) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目8
        if (Boolean.valueOf(registModel.isMedicalTreatment8()) && registModel.getMedicalTreatment8Disp()
                                                                             .equals(MEDICAL_TREATMENT_ON)) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目9
        if (Boolean.valueOf(registModel.isMedicalTreatment9()) && registModel.getMedicalTreatment9Disp()
                                                                             .equals(MEDICAL_TREATMENT_ON)) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目10
        if (Boolean.valueOf(registModel.isMedicalTreatment10()) && registModel.getMedicalTreatment10Disp()
                                                                              .equals(MEDICAL_TREATMENT_ON)) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目設定
        registModel.setMedicalTreatment(tempMedicalTreatment);

    }

    /**
     * お客様番号入力ページで取得した会員情報をページに設定します。
     *
     * @param registModel 会員情報表示ページ
     */
    public void toPageForLoadConfirmInfo(RegistModel registModel) {

        MemberInfoEntity memberInfoEntity = registModel.getMemberInfoEntity();

        // 顧客番号
        registModel.setCustomerNo(memberInfoEntity.getCustomerNo().toString());

        // 事業所名
        registModel.setMemberInfoLastName(memberInfoEntity.getMemberInfoLastName());

        // 代表者名
        registModel.setRepresentativeName(memberInfoEntity.getRepresentativeName());

        // 2023-renew No85-1 from here
        // 顧客区分
        registModel.setBusinessType(memberInfoEntity.getBusinessType().getLabel());
        // 2023-renew No85-1 to here

        // メールアドレス
        registModel.setMemberInfoMail(memberInfoEntity.getMemberInfoMail());

        // 入力用メールアドレス
        registModel.setInputMemberInfoMail(memberInfoEntity.getMemberInfoMail());
        registModel.setInputMemberInfoMailConfirm(memberInfoEntity.getMemberInfoMail());

        // 電話番号
        registModel.setMemberInfoTel(memberInfoEntity.getMemberInfoTel());

        // FAX番号
        registModel.setMemberInfoFax(memberInfoEntity.getMemberInfoFax());

        // 郵便番号
        String[] zipCodes = conversionUtility.toZipCodeArray(memberInfoEntity.getMemberInfoZipCode());
        registModel.setMemberInfoZipCode1(zipCodes[0]);
        registModel.setMemberInfoZipCode2(zipCodes[1]);

        // 住所
        registModel.setMemberInfoAddress1(memberInfoEntity.getMemberInfoAddress1());
        registModel.setMemberInfoAddress2(memberInfoEntity.getMemberInfoAddress2());
        registModel.setMemberInfoAddress3(memberInfoEntity.getMemberInfoAddress3());
        registModel.setMemberInfoAddress4(memberInfoEntity.getMemberInfoAddress4());
        registModel.setMemberInfoAddress5(memberInfoEntity.getMemberInfoAddress5());

        // メールによるおトク情報 デフォルト「1：希望する」
        registModel.setSendMail(true);
        // 金属商品価格お知らせメール デフォルト「1：希望する」
        registModel.setMetalPermitFlag(true);

        // パスワードが設定済みか判定
        registModel.setSetPassword(!StringUtils.isEmpty(memberInfoEntity.getMemberInfoPassword()));
    }

    /**
     * 会員情報Entityに画面入力されたメールアドレスを反映する。
     *
     * @param registModel 会員情報表示画面
     * @return 会員情報Entity
     */
    public MemberInfoEntity toMemberInfoEntityForMemberInfoUpdate(RegistModel registModel)
                    throws IllegalAccessException, InvocationTargetException {

        // 元会員情報コピー
        MemberInfoEntity memberInfoEntity = ApplicationContextUtility.getBean(MemberInfoEntity.class);
        BeanUtils.copyProperties(memberInfoEntity, registModel.getMemberInfoEntity());

        // メールによるおトク情報
        if (registModel.isSendMail()) {
            memberInfoEntity.setSendMailPermitFlag(HTypeSendMailPermitFlag.ON);
        } else {
            memberInfoEntity.setSendMailPermitFlag(HTypeSendMailPermitFlag.OFF);
        }

        // 金属商品価格お知らせメール
        if (registModel.isMetalPermitFlag()) {
            memberInfoEntity.setMetalPermitFlag(HTypeMetalPermitFlag.ON);
        } else {
            memberInfoEntity.setMetalPermitFlag(HTypeMetalPermitFlag.OFF);
        }

        // 2023-renew No79 from here
        if (registModel.isOrderCompletePermitFlag()) {
            memberInfoEntity.setOrderCompletePermitFlag(HTypeOrderCompletePermitFlag.ON);
        } else {
            memberInfoEntity.setOrderCompletePermitFlag(HTypeOrderCompletePermitFlag.OFF);
        }

        if (registModel.isDeliveryCompletePermitFlag()) {
            memberInfoEntity.setDeliveryCompletePermitFlag(HTypeDeliveryCompletePermitFlag.ON);
        } else {
            memberInfoEntity.setDeliveryCompletePermitFlag(HTypeDeliveryCompletePermitFlag.OFF);
        }
        // 2023-renew No79 to here

        // 2023-renew No85-1 from here
        // 画面入力されたの会員FAXを反映
        if (StringUtils.isEmpty(memberInfoEntity.getMemberInfoFax())) {
            memberInfoEntity.setMemberInfoFax(registModel.getMemberInfoFax());
        }

        // 診療項目設定
        setMedicalTreatment(registModel);

        // 画面入力されたのメールアドレスを反映
        memberInfoEntity.setMemberInfoId(registModel.getMemberInfoMail());
        memberInfoEntity.setMemberInfoMail(registModel.getMemberInfoMail());

        // 画面入力されたの休診曜日を反映
        memberInfoEntity.setNonConsultationDay(createNonConsultationDay(registModel));

        // 画面入力されたの診療内容を反映
        memberInfoEntity.setMedicalTreatmentFlag(registModel.getMedicalTreatment());

        // 画面入力されたの診療項目その他を反映
        memberInfoEntity.setMedicalTreatmentMemo(registModel.getMedicalTreatmentMemo());

        // 画面入力されたの反社会的勢力ではないことの保証を反映
        memberInfoEntity.setNoAntiSocialFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeNoAntiSocialFlag.class, registModel.getNoAntiSocialFlag()));

        // 会員情報更新完了ページに表示するため、メールアドレスを上書きする。
        registModel.setMemberInfoMail(registModel.getMemberInfoMail());
        // 2023-renew No85-1 to here

        return memberInfoEntity;
    }
    // PDR Migrate Customization to here

    /**
     * 本会員登録画面用会員登録リクエストに変換
     *
     * @param memberInfoEntity 会員情報表示画面
     * @return 本会員登録画面用会員登録リクエスト
     */
    public MemberInfoScreenRegistRequest toMemberInfoScreenRegistRequest(MemberInfoEntity memberInfoEntity,
                                                                         String accessUidCommonInfo,
                                                                         Boolean isSiteBack,
                                                                         String campainCode) {

        if (ObjectUtils.isEmpty(memberInfoEntity)) {
            return null;
        }

        MemberInfoScreenRegistRequest memberInfoScreenRegistRequest = new MemberInfoScreenRegistRequest();

        memberInfoScreenRegistRequest.setMemberInfoSeq(memberInfoEntity.getMemberInfoSeq());
        memberInfoScreenRegistRequest.setMemberInfoUniqueId(memberInfoEntity.getMemberInfoUniqueId());
        memberInfoScreenRegistRequest.setMemberInfoId(memberInfoEntity.getMemberInfoId());
        memberInfoScreenRegistRequest.setMemberInfoPassword(memberInfoEntity.getMemberInfoPassword());
        memberInfoScreenRegistRequest.setMemberInfoLastName(memberInfoEntity.getMemberInfoLastName());
        memberInfoScreenRegistRequest.setMemberInfoFirstName(memberInfoEntity.getMemberInfoFirstName());
        memberInfoScreenRegistRequest.setMemberInfoLastKana(memberInfoEntity.getMemberInfoLastKana());
        memberInfoScreenRegistRequest.setMemberInfoFirstKana(memberInfoEntity.getMemberInfoFirstKana());
        memberInfoScreenRegistRequest.setMemberInfoBirthday(memberInfoEntity.getMemberInfoBirthday());
        memberInfoScreenRegistRequest.setMemberInfoTel(memberInfoEntity.getMemberInfoTel());
        memberInfoScreenRegistRequest.setMemberInfoContactTel(memberInfoEntity.getMemberInfoContactTel());
        memberInfoScreenRegistRequest.setMemberInfoZipCode(memberInfoEntity.getMemberInfoZipCode());
        // 2023-renew No85-1 from here
        //memberInfoScreenRegistRequest.setMemberInfoPrefecture(memberInfoEntity.getMemberInfoPrefecture());
        // 2023-renew No85-1 to here
        memberInfoScreenRegistRequest.setMemberInfoAddress1(memberInfoEntity.getMemberInfoAddress1());
        memberInfoScreenRegistRequest.setMemberInfoAddress2(memberInfoEntity.getMemberInfoAddress2());
        memberInfoScreenRegistRequest.setMemberInfoAddress3(memberInfoEntity.getMemberInfoAddress3());
        memberInfoScreenRegistRequest.setMemberInfoMail(memberInfoEntity.getMemberInfoMail());
        memberInfoScreenRegistRequest.setAccessUid(memberInfoEntity.getAccessUid());
        memberInfoScreenRegistRequest.setVersionNo(memberInfoEntity.getVersionNo());
        memberInfoScreenRegistRequest.setAdmissionYmd(memberInfoEntity.getAdmissionYmd());
        memberInfoScreenRegistRequest.setSecessionYmd(memberInfoEntity.getSecessionYmd());
        memberInfoScreenRegistRequest.setMemo(memberInfoEntity.getMemo());
        memberInfoScreenRegistRequest.setMemberInfoFax(memberInfoEntity.getMemberInfoFax());
        memberInfoScreenRegistRequest.setLastLoginTime(memberInfoEntity.getLastLoginTime());
        memberInfoScreenRegistRequest.setLastLoginUserAgent(memberInfoEntity.getLastLoginUserAgent());
        memberInfoScreenRegistRequest.setPaymentMemberId(memberInfoEntity.getPaymentMemberId());
        memberInfoScreenRegistRequest.setLoginFailureCount(memberInfoEntity.getLoginFailureCount());
        memberInfoScreenRegistRequest.setAccountLockTime(memberInfoEntity.getAccountLockTime());
        memberInfoScreenRegistRequest.setRegistTime(memberInfoEntity.getRegistTime());
        memberInfoScreenRegistRequest.setUpdateTime(memberInfoEntity.getUpdateTime());
        memberInfoScreenRegistRequest.setCustomerNo(memberInfoEntity.getCustomerNo());
        memberInfoScreenRegistRequest.setRepresentativeName(memberInfoEntity.getRepresentativeName());
        memberInfoScreenRegistRequest.setMemberInfoAddress4(memberInfoEntity.getMemberInfoAddress4());
        memberInfoScreenRegistRequest.setMemberInfoAddress5(memberInfoEntity.getMemberInfoAddress5());
        memberInfoScreenRegistRequest.setNonConsultationDay(memberInfoEntity.getNonConsultationDay());
        memberInfoScreenRegistRequest.setMedicalTreatmentFlag(memberInfoEntity.getMedicalTreatmentFlag());
        memberInfoScreenRegistRequest.setMedicalTreatmentMemo(memberInfoEntity.getMedicalTreatmentMemo());
        memberInfoScreenRegistRequest.setSendMailStartTime(memberInfoEntity.getSendMailStartTime());
        memberInfoScreenRegistRequest.setSendMailStopTime(memberInfoEntity.getSendMailStopTime());

        if (memberInfoEntity.getMemberInfoStatus() != null) {
            memberInfoScreenRegistRequest.setMemberInfoStatus(memberInfoEntity.getMemberInfoStatus().getValue());
        }
        if (memberInfoEntity.getMemberInfoSex() != null) {
            memberInfoScreenRegistRequest.setMemberInfoSex(memberInfoEntity.getMemberInfoSex().getValue());
        }
        if (memberInfoEntity.getPrefectureType() != null) {
            memberInfoScreenRegistRequest.setPrefectureType(memberInfoEntity.getPrefectureType().getValue());
        }
        if (memberInfoEntity.getPaymentCardRegistType() != null) {
            memberInfoScreenRegistRequest.setPaymentCardRegistType(
                            memberInfoEntity.getPaymentCardRegistType().getValue());
        }
        if (memberInfoEntity.getPasswordNeedChangeFlag() != null) {
            memberInfoScreenRegistRequest.setPasswordNeedChangeFlag(
                            memberInfoEntity.getPasswordNeedChangeFlag().getValue());
        }
        if (memberInfoEntity.getLastLoginDeviceType() != null) {
            memberInfoScreenRegistRequest.setLastLoginDeviceType(memberInfoEntity.getLastLoginDeviceType().getValue());
        }
        if (memberInfoEntity.getBusinessType() != null) {
            memberInfoScreenRegistRequest.setBusinessType(memberInfoEntity.getBusinessType().getValue());
        }
        if (memberInfoEntity.getSendFaxPermitFlag() != null) {
            memberInfoScreenRegistRequest.setSendFaxPermitFlag(memberInfoEntity.getSendFaxPermitFlag().getValue());
        }
        if (memberInfoEntity.getSendDirectMailFlag() != null) {
            memberInfoScreenRegistRequest.setSendDirectMailFlag(memberInfoEntity.getSendDirectMailFlag().getValue());
        }
        if (memberInfoEntity.getApproveStatus() != null) {
            memberInfoScreenRegistRequest.setApproveStatus(memberInfoEntity.getApproveStatus().getValue());
        }
        if (memberInfoEntity.getDrugSalesType() != null) {
            memberInfoScreenRegistRequest.setDrugSalesType(memberInfoEntity.getDrugSalesType().getValue());
        }
        if (memberInfoEntity.getMedicalEquipmentSalesType() != null) {
            memberInfoScreenRegistRequest.setMedicalEquipmentSalesType(
                            memberInfoEntity.getMedicalEquipmentSalesType().getValue());
        }
        if (memberInfoEntity.getDentalMonopolySalesType() != null) {
            memberInfoScreenRegistRequest.setDentalMonopolySalesType(
                            memberInfoEntity.getDentalMonopolySalesType().getValue());
        }
        if (memberInfoEntity.getCreditPaymentUseFlag() != null) {
            memberInfoScreenRegistRequest.setCreditPaymentUseFlag(
                            memberInfoEntity.getCreditPaymentUseFlag().getValue());
        }
        if (memberInfoEntity.getTransferPaymentUseFlag() != null) {
            memberInfoScreenRegistRequest.setTransferPaymentUseFlag(
                            memberInfoEntity.getTransferPaymentUseFlag().getValue());
        }
        if (memberInfoEntity.getCashDeliveryUseFlag() != null) {
            memberInfoScreenRegistRequest.setCashDeliveryUseFlag(memberInfoEntity.getCashDeliveryUseFlag().getValue());
        }
        if (memberInfoEntity.getDirectDebitUseFlag() != null) {
            memberInfoScreenRegistRequest.setDirectDebitUseFlag(memberInfoEntity.getDirectDebitUseFlag().getValue());
        }
        if (memberInfoEntity.getMonthlyPayUseFlag() != null) {
            memberInfoScreenRegistRequest.setMonthlyPayUseFlag(memberInfoEntity.getMonthlyPayUseFlag().getValue());
        }
        if (memberInfoEntity.getMemberListType() != null) {
            memberInfoScreenRegistRequest.setMemberListType(memberInfoEntity.getMemberListType().getValue());
        }
        if (memberInfoEntity.getOnlineRegistFlag() != null) {
            memberInfoScreenRegistRequest.setOnlineRegistFlag(memberInfoEntity.getOnlineRegistFlag().getValue());
        }
        if (memberInfoEntity.getConfDocumentType() != null) {
            memberInfoScreenRegistRequest.setConfDocumentType(memberInfoEntity.getConfDocumentType().getValue());
        }
        if (memberInfoEntity.getMetalPermitFlag() != null) {
            memberInfoScreenRegistRequest.setMetalPermitFlag(memberInfoEntity.getMetalPermitFlag().getValue());
        }
        if (memberInfoEntity.getAccountingType() != null) {
            memberInfoScreenRegistRequest.setAccountingType(memberInfoEntity.getAccountingType().getValue());
        }
        if (memberInfoEntity.getOnlineLoginAdvisability() != null) {
            memberInfoScreenRegistRequest.setOnlineLoginAdvisability(
                            memberInfoEntity.getOnlineLoginAdvisability().getValue());
        }
        if (memberInfoEntity.getSendMailPermitFlag() != null) {
            memberInfoScreenRegistRequest.setSendMailPermitFlag(memberInfoEntity.getSendMailPermitFlag().getValue());
        }
        if (memberInfoEntity.getNoAntiSocialFlag() != null) {
            memberInfoScreenRegistRequest.setNoAntiSocialFlag(memberInfoEntity.getNoAntiSocialFlag().getValue());
        }
        // 2023-renew No79 from here
        if (memberInfoEntity.getOrderCompletePermitFlag() != null) {
            memberInfoScreenRegistRequest.setOrderCompletePermitFlag(
                            memberInfoEntity.getOrderCompletePermitFlag().getValue());
        }
        if (memberInfoEntity.getDeliveryCompletePermitFlag() != null) {
            memberInfoScreenRegistRequest.setDeliveryCompletePermitFlag(
                            memberInfoEntity.getDeliveryCompletePermitFlag().getValue());
        }
        // 2023-renew No79 to here

        memberInfoScreenRegistRequest.setOnlineFlg(true);
        memberInfoScreenRegistRequest.setAccessUidCommonInfo(accessUidCommonInfo);
        memberInfoScreenRegistRequest.setIsSiteBack(isSiteBack);
        memberInfoScreenRegistRequest.setCampainCode(campainCode);

        return memberInfoScreenRegistRequest;
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

}
