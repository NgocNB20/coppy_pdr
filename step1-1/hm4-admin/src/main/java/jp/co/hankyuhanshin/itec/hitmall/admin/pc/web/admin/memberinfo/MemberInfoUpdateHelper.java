package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAccountingType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCashDeliveryUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeConfDocumentType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCreditPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDentalMonopolySalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDirectDebitUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDrugSalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMedicalEquipmentSalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMedicalTreatmentFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberListType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMetalPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMonthlyPayUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNoAntiSocialFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineLoginAdvisability;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendDirectMailFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendFaxPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSexUnnecessaryAnswer;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTransferPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MedicalTreatmentDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.MemberInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.NonConsultationDayUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 会員検索HELPER
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class MemberInfoUpdateHelper {

    /** Reset value of login failure count */
    protected static final Integer RESET_LOGIN_FAILURE_COUNT = 0;

    private static final String FULL_WIDTH_CHAR_BLANK = "\u3000";

    /**
     * 変換Utility取得
     */
    private final ConversionUtility conversionUtility;

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    /** 会員業務ヘルパークラス */
    private final MemberInfoUtility memberInfoUtility;

    /** 休診曜日Utility */
    private final NonConsultationDayUtility nonConsultationDayUtility;

    /** 診療項目Dto */
    private final MedicalTreatmentDto medicalTreatmentDto;

    /**
     * コンストラクタ
     *
     * @param conversionUtility   変換ユーティリティクラス
     * @param dateUtility         日付関連Utilityクラス
     * @param memberInfoUtility   会員業務ヘルパークラス
     * @param medicalTreatmentDto 診療項目Dto
     */
    @Autowired
    public MemberInfoUpdateHelper(ConversionUtility conversionUtility,
                                  DateUtility dateUtility,
                                  MemberInfoUtility memberInfoUtility,
                                  MedicalTreatmentDto medicalTreatmentDto) {
        this.conversionUtility = conversionUtility;
        this.dateUtility = dateUtility;
        this.memberInfoUtility = memberInfoUtility;
        this.medicalTreatmentDto = medicalTreatmentDto;
        this.nonConsultationDayUtility = ApplicationContextUtility.getBean(NonConsultationDayUtility.class);
    }

    /**
     * ページに反映
     *
     * @param memberInfoDetailsDto  会員詳細Dto
     * @param memberInfoUpdateModel 会員情報変更モデル
     */
    public void toPageForLoad(MemberInfoDetailsDto memberInfoDetailsDto, MemberInfoUpdateModel memberInfoUpdateModel) {

        // 会員情報取得
        MemberInfoEntity memberInfoEntity = memberInfoDetailsDto.getMemberInfoEntity();

        memberInfoUpdateModel.setMemberInfoSeq(memberInfoDetailsDto.getMemberInfoEntity().getMemberInfoSeq());

        // 画面タイトルに表示する会員名
        memberInfoUpdateModel.setMemberInfoName(
                        MemberInfoHelper.buildMemberInfoNameOrKana(memberInfoEntity.getMemberInfoLastName(),
                                                                   memberInfoEntity.getMemberInfoFirstName()
                                                                  ));

        // 会員状態情報
        memberInfoUpdateModel.setMemberInfoStatus(memberInfoEntity.getMemberInfoStatus().getValue());
        memberInfoUpdateModel.setRegistTime(memberInfoEntity.getRegistTime());
        memberInfoUpdateModel.setUpdateTime(memberInfoEntity.getUpdateTime());
        memberInfoUpdateModel.setAdmissionYmd(
                        dateUtility.getYmdFormatValue(memberInfoEntity.getAdmissionYmd(), "yyyy/MM/dd"));
        memberInfoUpdateModel.setSecessionYmd(
                        dateUtility.getYmdFormatValue(memberInfoEntity.getSecessionYmd(), "yyyy/MM/dd"));
        memberInfoUpdateModel.setAccountLock(
                        memberInfoUtility.isAccountStatusLock(memberInfoEntity.getAccountLockTime()));
        memberInfoUpdateModel.setAccountLockTime(memberInfoEntity.getAccountLockTime());
        memberInfoUpdateModel.setLoginFailureCount(memberInfoEntity.getLoginFailureCount());

        // 最終ログイン情報
        if (memberInfoEntity.getLastLoginTime() != null) {
            memberInfoUpdateModel.setLastLoginTime(conversionUtility.toYmd(memberInfoEntity.getLastLoginTime()) + " "
                                                   + conversionUtility.toHms(memberInfoEntity.getLastLoginTime()));
        }

        if (memberInfoEntity.getLastLoginDeviceType() != null) {
            memberInfoUpdateModel.setLastLoginDeviceType(memberInfoEntity.getLastLoginDeviceType());
        } else {
            memberInfoUpdateModel.setLastLoginDeviceType(null);
        }
        memberInfoUpdateModel.setLastLoginUserAgent(memberInfoEntity.getLastLoginUserAgent());

        // PDR Migrate Customization from here
        // 顧客番号
        memberInfoUpdateModel.setCustomerNo(memberInfoEntity.getCustomerNo());

        // 管理用メモ
        memberInfoUpdateModel.setMemo(memberInfoEntity.getMemo());

        memberInfoUpdateModel.setNonConsultationDay(memberInfoEntity.getMemo());

        // 反社会的勢力ではないことの保証
        memberInfoUpdateModel.setNoAntiSocialFlag(
                        HTypeNoAntiSocialFlag.ON.equals(memberInfoEntity.getNoAntiSocialFlag()));

        // お客様情報
        memberInfoUpdateModel.setMemberInfoId(memberInfoEntity.getMemberInfoId());
        memberInfoUpdateModel.setMemberInfoLastName(memberInfoEntity.getMemberInfoLastName());
        memberInfoUpdateModel.setMemberInfoFirstName(memberInfoEntity.getMemberInfoFirstName());
        memberInfoUpdateModel.setMemberInfoLastKana(memberInfoEntity.getMemberInfoLastKana());
        memberInfoUpdateModel.setMemberInfoFirstKana(memberInfoEntity.getMemberInfoFirstKana());
        memberInfoUpdateModel.setMemberInfoSex(EnumTypeUtil.getValue(memberInfoEntity.getMemberInfoSex()));
        memberInfoUpdateModel.setMemberInfoBirthday(conversionUtility.toYmd(memberInfoEntity.getMemberInfoBirthday()));
        memberInfoUpdateModel.setMemberInfoTel(memberInfoEntity.getMemberInfoTel());
        memberInfoUpdateModel.setMemberInfoContactTel(memberInfoEntity.getMemberInfoContactTel());
        memberInfoUpdateModel.setMemberInfoFax(memberInfoEntity.getMemberInfoFax());
        memberInfoUpdateModel.setMemberInfoZipCode(memberInfoEntity.getMemberInfoZipCode());
        memberInfoUpdateModel.setMemberInfoPrefecture(memberInfoEntity.getMemberInfoPrefecture());
        memberInfoUpdateModel.setMemberInfoAddress1(memberInfoEntity.getMemberInfoAddress1());
        memberInfoUpdateModel.setMemberInfoAddress2(memberInfoEntity.getMemberInfoAddress2());
        memberInfoUpdateModel.setMemberInfoAddress3(memberInfoEntity.getMemberInfoAddress3());
        // 方書1
        memberInfoUpdateModel.setMemberInfoAddress4(memberInfoEntity.getMemberInfoAddress4());

        // 方書2
        memberInfoUpdateModel.setMemberInfoAddress5(memberInfoEntity.getMemberInfoAddress5());

        // 承認状態
        memberInfoUpdateModel.setApproveStatus(EnumTypeUtil.getValue(memberInfoEntity.getApproveStatus()));

        // 承認状態が「承認」のとき管理フラグを立てる
        if (HTypeApproveStatus.ON == memberInfoEntity.getApproveStatus()) {
            memberInfoUpdateModel.setApproveStatusFlag(true);
        }

        // 代表者名
        memberInfoUpdateModel.setRepresentativeName(memberInfoEntity.getRepresentativeName());
        // オンライン登録フラグ
        memberInfoUpdateModel.setOnlineRegistFlag(EnumTypeUtil.getValue(memberInfoEntity.getOnlineRegistFlag()));
        // 顧客区分
        memberInfoUpdateModel.setBusinessType(EnumTypeUtil.getValue(memberInfoEntity.getBusinessType()));
        // 確認書類
        memberInfoUpdateModel.setConfDocumentType(EnumTypeUtil.getValue(memberInfoEntity.getConfDocumentType()));
        // 医薬品・注射針販売区分
        memberInfoUpdateModel.setDrugSalesType(EnumTypeUtil.getValue(memberInfoEntity.getDrugSalesType()));
        // 医療機器販売区分
        memberInfoUpdateModel.setMedicalEquipmentSalesType(
                        EnumTypeUtil.getValue(memberInfoEntity.getMedicalEquipmentSalesType()));
        // 歯科専売品販売区分
        memberInfoUpdateModel.setDentalMonopolySalesType(
                        EnumTypeUtil.getValue(memberInfoEntity.getDentalMonopolySalesType()));
        // クレジット決済使用可否
        memberInfoUpdateModel.setCreditPaymentUseFlag(
                        EnumTypeUtil.getValue(memberInfoEntity.getCreditPaymentUseFlag()));
        // コンビニ・郵便振込使用可否
        memberInfoUpdateModel.setTransferPaymentUseFlag(
                        EnumTypeUtil.getValue(memberInfoEntity.getTransferPaymentUseFlag()));
        // 代金引換使用可否
        memberInfoUpdateModel.setCashDeliveryUseFlag(EnumTypeUtil.getValue(memberInfoEntity.getCashDeliveryUseFlag()));
        // 口座自動引落使用可否
        memberInfoUpdateModel.setDirectDebitUseFlag(EnumTypeUtil.getValue(memberInfoEntity.getDirectDebitUseFlag()));
        // 月締請求使用可否
        memberInfoUpdateModel.setMonthlyPayUseFlag(EnumTypeUtil.getValue(memberInfoEntity.getMonthlyPayUseFlag()));
        // 名簿区分
        memberInfoUpdateModel.setMemberListType(EnumTypeUtil.getValue(memberInfoEntity.getMemberListType()));
        // 経理区分
        memberInfoUpdateModel.setAccountingType(EnumTypeUtil.getValue(memberInfoEntity.getAccountingType()));
        // オンラインログイン可否
        memberInfoUpdateModel.setOnlineLoginAdvisability(
                        EnumTypeUtil.getValue(memberInfoEntity.getOnlineLoginAdvisability()));

        // メール情報
        memberInfoUpdateModel.setSendMailPermitFlag(memberInfoEntity.getSendMailPermitFlag());
        if (memberInfoUpdateModel.getSendMailPermitFlag() == HTypeSendMailPermitFlag.ON) {
            memberInfoUpdateModel.setSendMail(true);
        }

        // FAXによるおトク情報
        memberInfoUpdateModel.setSendFaxPermitFlag(memberInfoEntity.getSendFaxPermitFlag());
        if (memberInfoUpdateModel.getSendFaxPermitFlag() == HTypeSendFaxPermitFlag.ON) {
            memberInfoUpdateModel.setSendFax(true);
        }

        // DMによるおトク情報
        memberInfoUpdateModel.setSendDirectMailFlag(memberInfoEntity.getSendDirectMailFlag());
        if (memberInfoUpdateModel.getSendDirectMailFlag() == HTypeSendDirectMailFlag.ON) {
            memberInfoUpdateModel.setSendDirectMail(true);
        }

        // 金属商品価格お知らせメール
        memberInfoUpdateModel.setMetalPermitFlag(memberInfoEntity.getMetalPermitFlag());
        if (memberInfoUpdateModel.getMetalPermitFlag() == HTypeMetalPermitFlag.ON) {
            memberInfoUpdateModel.setMetalPermit(true);
        }

        // 2023-renew No79 from here
        // 注文完了メール
        memberInfoUpdateModel.setOrderCompletePermitFlag(memberInfoEntity.getOrderCompletePermitFlag());
        if (memberInfoUpdateModel.getOrderCompletePermitFlag() == HTypeOrderCompletePermitFlag.ON) {
            memberInfoUpdateModel.setOrderCompletePermit(true);
        }

        // 発送完了メール
        memberInfoUpdateModel.setDeliveryCompletePermitFlag(memberInfoEntity.getDeliveryCompletePermitFlag());
        if (memberInfoUpdateModel.getDeliveryCompletePermitFlag() == HTypeDeliveryCompletePermitFlag.ON) {
            memberInfoUpdateModel.setDeliveryCompletePermit(true);
        }
        // 2023-renew No79 to here

        // 診療項目
        setMedicalTreatment(memberInfoEntity, memberInfoUpdateModel);

        // 診療内容その他
        memberInfoUpdateModel.setMedicalTreatmentMemo(memberInfoEntity.getMedicalTreatmentMemo());

        // 休診曜日取得処理
        // AM欄用チェック配列取得
        boolean[] amCheckArray = nonConsultationDayUtility.setAmNonConsultationCheckList(
                        memberInfoEntity.getNonConsultationDay());
        // PM欄用チェック配列取得
        boolean[] pmCheckArray = nonConsultationDayUtility.setPmNonConsultationCheckList(
                        memberInfoEntity.getNonConsultationDay());

        // 各AM休診曜日に真偽値代入
        memberInfoUpdateModel.setAmSunNonConsultation(amCheckArray[0]);
        memberInfoUpdateModel.setAmMonNonConsultation(amCheckArray[1]);
        memberInfoUpdateModel.setAmTueNonConsultation(amCheckArray[2]);
        memberInfoUpdateModel.setAmWedNonConsultation(amCheckArray[3]);
        memberInfoUpdateModel.setAmThuNonConsultation(amCheckArray[4]);
        memberInfoUpdateModel.setAmFriNonConsultation(amCheckArray[5]);
        memberInfoUpdateModel.setAmSatNonConsultation(amCheckArray[6]);
        memberInfoUpdateModel.setAmHolidayNonConsultation(amCheckArray[7]);

        // 各PM休診曜日に真偽値代入
        memberInfoUpdateModel.setPmSunNonConsultation(pmCheckArray[0]);
        memberInfoUpdateModel.setPmMonNonConsultation(pmCheckArray[1]);
        memberInfoUpdateModel.setPmTueNonConsultation(pmCheckArray[2]);
        memberInfoUpdateModel.setPmWedNonConsultation(pmCheckArray[3]);
        memberInfoUpdateModel.setPmThuNonConsultation(pmCheckArray[4]);
        memberInfoUpdateModel.setPmFriNonConsultation(pmCheckArray[5]);
        memberInfoUpdateModel.setPmSatNonConsultation(pmCheckArray[6]);
        memberInfoUpdateModel.setPmHolidayNonConsultation(pmCheckArray[7]);

        // 無休
        memberInfoUpdateModel.setAllConsultation(nonConsultationDayUtility.setNoHolidayNonConsultation(
                        memberInfoEntity.getNonConsultationDay()));

        // PDR Migrate Customization from here
        // パスワード変更要求
        // PDR Migrate Customization to here
        memberInfoUpdateModel.setPasswordNeedChangeFlag(memberInfoEntity.getPasswordNeedChangeFlag());

        boolean passwordNeedChange =
                        HTypePasswordNeedChangeFlag.ON == memberInfoUpdateModel.getPasswordNeedChangeFlag();
        memberInfoUpdateModel.setPasswordNeedChange(passwordNeedChange);

        // 会員詳細DTO
        memberInfoUpdateModel.setMemberInfoDetailsDto(memberInfoDetailsDto);
        // 会員エンティティ。修正箇所比較用に持っておく。
        MemberInfoEntity memberInfoEntityCopy = CopyUtil.deepCopy(memberInfoEntity);
        memberInfoUpdateModel.setMemberInfoEntity(memberInfoEntityCopy);

        // 郵便番号
        if (memberInfoUpdateModel.getMemberInfoZipCode() != null) {

            String[] zipCodeArray = conversionUtility.toZipCodeArray(memberInfoUpdateModel.getMemberInfoZipCode());
            memberInfoUpdateModel.setMemberInfoZipCode1(zipCodeArray[0]);
            memberInfoUpdateModel.setMemberInfoZipCode2(zipCodeArray[1]);
        }
    }

    /**
     * ページから会員詳細DTOに変換
     *
     * @param memberInfoUpdateModel 会員情報変更モデル
     * @return 会員詳細DTO
     */
    public MemberInfoDetailsDto toMemberInfoDetailsDtoForUpdate(MemberInfoUpdateModel memberInfoUpdateModel) {
        // 会員詳細DTOを取得
        MemberInfoDetailsDto memberInfoDetailsDto = memberInfoUpdateModel.getMemberInfoDetailsDto();
        // 会員情報エンティティを作成
        MemberInfoEntity memberInfoEntity = toMemberInfoEntityForConfirm(memberInfoUpdateModel);

        // 会員詳細DTOに設定
        memberInfoDetailsDto.setMemberInfoEntity(memberInfoEntity);

        return memberInfoDetailsDto;
    }

    // PDR Migrate Customization from here

    /**
     * ページから会員情報エンティティに変換
     *
     * @param memberInfoUpdateModel 会員情報変更モデル
     * @return 会員情報エンティティ
     */
    protected MemberInfoEntity toMemberInfoEntityForConfirm(MemberInfoUpdateModel memberInfoUpdateModel) {

        MemberInfoEntity inputData = memberInfoUpdateModel.getMemberInfoDetailsDto().getMemberInfoEntity();
        inputData.setMemberInfoId(memberInfoUpdateModel.getMemberInfoId());
        inputData.setMemberInfoStatus(EnumTypeUtil.getEnumFromValue(HTypeMemberInfoStatus.class,
                                                                    memberInfoUpdateModel.getMemberInfoStatus()
                                                                   ));
        inputData.setMemberInfoPassword(memberInfoUpdateModel.getMemberInfoPassword());
        inputData.setMemberInfoLastName(memberInfoUpdateModel.getMemberInfoLastName());
        inputData.setMemberInfoFirstName(memberInfoUpdateModel.getMemberInfoFirstName());
        inputData.setMemberInfoLastKana(memberInfoUpdateModel.getMemberInfoLastKana());
        inputData.setMemberInfoFirstKana(memberInfoUpdateModel.getMemberInfoFirstKana());
        inputData.setMemberInfoMail(memberInfoUpdateModel.getMemberInfoId());
        inputData.setMemberInfoSex(EnumTypeUtil.getEnumFromValue(HTypeSexUnnecessaryAnswer.class,
                                                                 memberInfoUpdateModel.getMemberInfoSex()
                                                                ));
        inputData.setMemberInfoBirthday(conversionUtility.toTimeStamp(memberInfoUpdateModel.getMemberInfoBirthday()));
        inputData.setMemberInfoTel(memberInfoUpdateModel.getMemberInfoTel());
        inputData.setMemberInfoContactTel(memberInfoUpdateModel.getMemberInfoContactTel());
        inputData.setMemberInfoFax(memberInfoUpdateModel.getMemberInfoFax());
        inputData.setMemberInfoZipCode(memberInfoUpdateModel.getMemberInfoZipCode());
        inputData.setMemberInfoPrefecture(memberInfoUpdateModel.getMemberInfoPrefecture());
        inputData.setPrefectureType(EnumTypeUtil.getEnumFromLabel(HTypePrefectureType.class,
                                                                  memberInfoUpdateModel.getMemberInfoPrefecture()
                                                                 ));
        inputData.setMemberInfoAddress1(memberInfoUpdateModel.getMemberInfoAddress1());
        inputData.setMemberInfoAddress2(memberInfoUpdateModel.getMemberInfoAddress2());
        inputData.setMemberInfoAddress3(memberInfoUpdateModel.getMemberInfoAddress3());

        // PDR Migrate Customization from here
        inputData.setMemo(memberInfoUpdateModel.getMemo());
        // 反社会的勢力ではないことの保証フラグ
        if (memberInfoUpdateModel.isNoAntiSocialFlag()) {
            inputData.setNoAntiSocialFlag(HTypeNoAntiSocialFlag.ON);
        } else {
            inputData.setNoAntiSocialFlag(HTypeNoAntiSocialFlag.OFF);
        }
        // 方書1
        inputData.setMemberInfoAddress4(memberInfoUpdateModel.getMemberInfoAddress4());
        // 方書2
        inputData.setMemberInfoAddress5(memberInfoUpdateModel.getMemberInfoAddress5());

        inputData.setAdmissionYmd(
                        dateUtility.convertStringDate(memberInfoUpdateModel.getAdmissionYmd(), DateUtility.YMD_SLASH,
                                                      DateUtility.YMD
                                                     ));
        inputData.setSecessionYmd(
                        dateUtility.convertStringDate(memberInfoUpdateModel.getSecessionYmd(), DateUtility.YMD_SLASH,
                                                      DateUtility.YMD
                                                     ));
        // 承認状態
        inputData.setApproveStatus(EnumTypeUtil.getEnumFromValue(HTypeApproveStatus.class,
                                                                 memberInfoUpdateModel.getApproveStatus()
                                                                ));
        // パスワード変更要求フラグ
        memberInfoUpdateModel.setPasswordNeedChangeFlag(
                        HTypePasswordNeedChangeFlag.getFlagByBoolean(memberInfoUpdateModel.isPasswordNeedChange()));

        // パスワード変更要求フラグ
        inputData.setPasswordNeedChangeFlag(memberInfoUpdateModel.getPasswordNeedChangeFlag());

        // メール送信希望フラグ
        if (memberInfoUpdateModel.isSendMail()) {
            memberInfoUpdateModel.setSendMailPermitFlag(HTypeSendMailPermitFlag.ON);
        } else {
            memberInfoUpdateModel.setSendMailPermitFlag(HTypeSendMailPermitFlag.OFF);
        }
        inputData.setSendMailPermitFlag(memberInfoUpdateModel.getSendMailPermitFlag());

        // メール送信希望フラグに変更がなかったため、現在のDB値を開始・停止日時に設定
        if (memberInfoUpdateModel.getMemberInfoEntity().getSendMailPermitFlag() == inputData.getSendMailPermitFlag()) {
            inputData.setSendMailStartTime(memberInfoUpdateModel.getMemberInfoEntity().getSendMailStartTime());
            inputData.setSendMailStopTime(memberInfoUpdateModel.getMemberInfoEntity().getSendMailStopTime());

        } else {
            // メール送信希望フラグに変更があった場合 開始・停止日時をセット
            // 希望→希望しない
            if (memberInfoUpdateModel.getMemberInfoEntity().getSendMailPermitFlag() == HTypeSendMailPermitFlag.ON) {

                /*
                 * 希望→希望しない 開始日時：何もしない 停止日時：現在日時
                 */
                inputData.setSendMailStopTime(dateUtility.getCurrentTime());
            } else {

                /*
                 * 希望しない→希望 開始日時：現在日時 停止日時：null
                 */
                inputData.setSendMailStartTime(dateUtility.getCurrentTime());
                inputData.setSendMailStopTime(null);
            }
        }

        // Reset login failure count and account lock time if password is
        // updated.
        if (!(StringUtil.isEmpty(memberInfoUpdateModel.getMemberInfoPassword()))) {
            inputData.setLoginFailureCount(RESET_LOGIN_FAILURE_COUNT);
            inputData.setAccountLockTime(null);
        } else {
            inputData.setLoginFailureCount(memberInfoUpdateModel.getLoginFailureCount());
            inputData.setAccountLockTime(memberInfoUpdateModel.getAccountLockTime());
        }
        // 代表者名
        inputData.setRepresentativeName(memberInfoUpdateModel.getRepresentativeName());

        // 顧客区分
        inputData.setBusinessType(EnumTypeUtil.getEnumFromValue(HTypeBusinessType.class,
                                                                memberInfoUpdateModel.getBusinessType()
                                                               ));

        // 確認書類
        inputData.setConfDocumentType(EnumTypeUtil.getEnumFromValue(HTypeConfDocumentType.class,
                                                                    memberInfoUpdateModel.getConfDocumentType()
                                                                   ));

        // 医薬品・注射針販売区分
        inputData.setDrugSalesType(EnumTypeUtil.getEnumFromValue(HTypeDrugSalesType.class,
                                                                 memberInfoUpdateModel.getDrugSalesType()
                                                                ));

        // 医療機器販売区分
        inputData.setMedicalEquipmentSalesType(EnumTypeUtil.getEnumFromValue(HTypeMedicalEquipmentSalesType.class,
                                                                             memberInfoUpdateModel.getMedicalEquipmentSalesType()
                                                                            ));

        // 歯科専売品販売区分
        inputData.setDentalMonopolySalesType(EnumTypeUtil.getEnumFromValue(HTypeDentalMonopolySalesType.class,
                                                                           memberInfoUpdateModel.getDentalMonopolySalesType()
                                                                          ));

        // クレジット決済使用可否
        inputData.setCreditPaymentUseFlag(EnumTypeUtil.getEnumFromValue(HTypeCreditPaymentUseFlag.class,
                                                                        memberInfoUpdateModel.getCreditPaymentUseFlag()
                                                                       ));

        // コンビニ・郵便振込使用可否
        inputData.setTransferPaymentUseFlag(EnumTypeUtil.getEnumFromValue(HTypeTransferPaymentUseFlag.class,
                                                                          memberInfoUpdateModel.getTransferPaymentUseFlag()
                                                                         ));

        // 代金引換使用可否
        inputData.setCashDeliveryUseFlag(EnumTypeUtil.getEnumFromValue(HTypeCashDeliveryUseFlag.class,
                                                                       memberInfoUpdateModel.getCashDeliveryUseFlag()
                                                                      ));

        // 口座自動引落使用可否
        inputData.setDirectDebitUseFlag(EnumTypeUtil.getEnumFromValue(HTypeDirectDebitUseFlag.class,
                                                                      memberInfoUpdateModel.getDirectDebitUseFlag()
                                                                     ));

        // 月締請求使用可否
        inputData.setMonthlyPayUseFlag(EnumTypeUtil.getEnumFromValue(HTypeMonthlyPayUseFlag.class,
                                                                     memberInfoUpdateModel.getMonthlyPayUseFlag()
                                                                    ));

        // 名簿区分
        inputData.setMemberListType(EnumTypeUtil.getEnumFromValue(HTypeMemberListType.class,
                                                                  memberInfoUpdateModel.getMemberListType()
                                                                 ));

        // 経理区分
        inputData.setAccountingType(EnumTypeUtil.getEnumFromValue(HTypeAccountingType.class,
                                                                  memberInfoUpdateModel.getAccountingType()
                                                                 ));

        // オンラインログイン可否
        inputData.setOnlineLoginAdvisability(EnumTypeUtil.getEnumFromValue(HTypeOnlineLoginAdvisability.class,
                                                                           memberInfoUpdateModel.getOnlineLoginAdvisability()
                                                                          ));
        // メール送信希望フラグ
        if (memberInfoUpdateModel.isSendMail()) {
            memberInfoUpdateModel.setSendMailPermitFlag(HTypeSendMailPermitFlag.ON);
        } else {
            memberInfoUpdateModel.setSendMailPermitFlag(HTypeSendMailPermitFlag.OFF);
        }
        inputData.setSendMailPermitFlag(memberInfoUpdateModel.getSendMailPermitFlag());
        // FAXによるおトク情報
        if (memberInfoUpdateModel.isSendFax()) {
            memberInfoUpdateModel.setSendFaxPermitFlag(HTypeSendFaxPermitFlag.ON);
        } else {
            memberInfoUpdateModel.setSendFaxPermitFlag(HTypeSendFaxPermitFlag.OFF);
        }
        inputData.setSendFaxPermitFlag(memberInfoUpdateModel.getSendFaxPermitFlag());

        // DMによるおトク情報
        if (memberInfoUpdateModel.isSendDirectMail()) {
            memberInfoUpdateModel.setSendDirectMailFlag(HTypeSendDirectMailFlag.ON);
        } else {
            memberInfoUpdateModel.setSendDirectMailFlag(HTypeSendDirectMailFlag.OFF);
        }
        inputData.setSendDirectMailFlag(memberInfoUpdateModel.getSendDirectMailFlag());

        // 金属商品価格お知らせメール
        if (memberInfoUpdateModel.isMetalPermit()) {
            memberInfoUpdateModel.setMetalPermitFlag(HTypeMetalPermitFlag.ON);
        } else {
            memberInfoUpdateModel.setMetalPermitFlag(HTypeMetalPermitFlag.OFF);
        }
        inputData.setMetalPermitFlag(memberInfoUpdateModel.getMetalPermitFlag());

        // 2023-renew No79 from here
        //注文完了メール
        if (memberInfoUpdateModel.isOrderCompletePermit()) {
            memberInfoUpdateModel.setOrderCompletePermitFlag(HTypeOrderCompletePermitFlag.ON);
        } else {
            memberInfoUpdateModel.setOrderCompletePermitFlag(HTypeOrderCompletePermitFlag.OFF);
        }
        inputData.setOrderCompletePermitFlag(memberInfoUpdateModel.getOrderCompletePermitFlag());

        //発送完了メール
        if (memberInfoUpdateModel.isDeliveryCompletePermit()) {
            memberInfoUpdateModel.setDeliveryCompletePermitFlag(HTypeDeliveryCompletePermitFlag.ON);
        } else {
            memberInfoUpdateModel.setDeliveryCompletePermitFlag(HTypeDeliveryCompletePermitFlag.OFF);
        }
        inputData.setDeliveryCompletePermitFlag(memberInfoUpdateModel.getDeliveryCompletePermitFlag());
        // 2023-renew No79 to here

        // 診療項目
        String tempMedicalTreatmentFlag = "";
        if (memberInfoUpdateModel.isMedicalTreatment1()) {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
        }
        if (memberInfoUpdateModel.isMedicalTreatment2()) {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
        }
        if (memberInfoUpdateModel.isMedicalTreatment3()) {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
        }
        if (memberInfoUpdateModel.isMedicalTreatment4()) {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
        }
        if (memberInfoUpdateModel.isMedicalTreatment5()) {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
        }
        if (memberInfoUpdateModel.isMedicalTreatment6()) {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
        }
        if (memberInfoUpdateModel.isMedicalTreatment7()) {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
        }
        if (memberInfoUpdateModel.isMedicalTreatment8()) {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
        }
        if (memberInfoUpdateModel.isMedicalTreatment9()) {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
        }
        if (memberInfoUpdateModel.isMedicalTreatment10()) {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        inputData.setMedicalTreatmentFlag(tempMedicalTreatmentFlag);

        // 診療項目メモ
        inputData.setMedicalTreatmentMemo(memberInfoUpdateModel.getMedicalTreatmentMemo());

        // 休診曜日処理
        // 画面表示のAM休診曜日を配列へ
        boolean[] pageAmArray = new boolean[] {memberInfoUpdateModel.isAmSunNonConsultation(),
                        memberInfoUpdateModel.isAmMonNonConsultation(), memberInfoUpdateModel.isAmTueNonConsultation(),
                        memberInfoUpdateModel.isAmWedNonConsultation(), memberInfoUpdateModel.isAmThuNonConsultation(),
                        memberInfoUpdateModel.isAmFriNonConsultation(), memberInfoUpdateModel.isAmSatNonConsultation(),
                        memberInfoUpdateModel.isAmHolidayNonConsultation(), memberInfoUpdateModel.isAllConsultation()};
        // 画面表示のPM休診曜日を配列へ
        boolean[] pagePmArray = new boolean[] {memberInfoUpdateModel.isPmSunNonConsultation(),
                        memberInfoUpdateModel.isPmMonNonConsultation(), memberInfoUpdateModel.isPmTueNonConsultation(),
                        memberInfoUpdateModel.isPmWedNonConsultation(), memberInfoUpdateModel.isPmThuNonConsultation(),
                        memberInfoUpdateModel.isPmFriNonConsultation(), memberInfoUpdateModel.isPmSatNonConsultation(),
                        memberInfoUpdateModel.isPmHolidayNonConsultation(), memberInfoUpdateModel.isAllConsultation()};
        // 画面の休診曜日から休診曜日文字列を取得
        String nonConsultationStr = nonConsultationDayUtility.getNonConsultationDayString(pageAmArray, pagePmArray);
        // 休診曜日文字列がすべて"0"の場合は休診曜日は変更なしとする(次画面での修正差分チェックが当たらないようにするため)
        if (nonConsultationStr.equals(NonConsultationDayUtility.NON_CONSULTATION_NULL_STRING)) {
            inputData.setNonConsultationDay(memberInfoUpdateModel.getMemberInfoEntity().getNonConsultationDay());
        } else {
            inputData.setNonConsultationDay(nonConsultationStr);
        }
        // PDR Migrate Customization to here
        return inputData;
    }

    /**
     * 診療項目設定<br/>
     *
     * @param memberInfoEntity      会員Entity
     * @param memberInfoUpdateModel memberInfoUpdateModel
     */
    protected void setMedicalTreatment(MemberInfoEntity memberInfoEntity, MemberInfoUpdateModel memberInfoUpdateModel) {

        /** 診療項目1_タイトル */
        memberInfoUpdateModel.setMedicalTreatment1Title(medicalTreatmentDto.getMedicalTreatment1Title());
        /** 診療項目2_タイトル */
        memberInfoUpdateModel.setMedicalTreatment2Title(medicalTreatmentDto.getMedicalTreatment2Title());
        /** 診療項目3_タイトル */
        memberInfoUpdateModel.setMedicalTreatment3Title(medicalTreatmentDto.getMedicalTreatment3Title());
        /** 診療項目4_タイトル */
        memberInfoUpdateModel.setMedicalTreatment4Title(medicalTreatmentDto.getMedicalTreatment4Title());
        /** 診療項目5_タイトル */
        memberInfoUpdateModel.setMedicalTreatment5Title(medicalTreatmentDto.getMedicalTreatment5Title());
        /** 診療項目6_タイトル */
        memberInfoUpdateModel.setMedicalTreatment6Title(medicalTreatmentDto.getMedicalTreatment6Title());
        /** 診療項目7_タイトル */
        memberInfoUpdateModel.setMedicalTreatment7Title(medicalTreatmentDto.getMedicalTreatment7Title());
        /** 診療項目8_タイトル */
        memberInfoUpdateModel.setMedicalTreatment8Title(medicalTreatmentDto.getMedicalTreatment8Title());
        /** 診療項目9_タイトル */
        memberInfoUpdateModel.setMedicalTreatment9Title(medicalTreatmentDto.getMedicalTreatment9Title());
        /** 診療項目10_タイトル */
        memberInfoUpdateModel.setMedicalTreatment10Title(medicalTreatmentDto.getMedicalTreatment10Title());

        /** 診療項目1_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment1Disp(medicalTreatmentDto.getMedicalTreatment1Disp());
        /** 診療項目2_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment2Disp(medicalTreatmentDto.getMedicalTreatment2Disp());
        /** 診療項目3_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment3Disp(medicalTreatmentDto.getMedicalTreatment3Disp());
        /** 診療項目4_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment4Disp(medicalTreatmentDto.getMedicalTreatment4Disp());
        /** 診療項目5_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment5Disp(medicalTreatmentDto.getMedicalTreatment5Disp());
        /** 診療項目6_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment6Disp(medicalTreatmentDto.getMedicalTreatment6Disp());
        /** 診療項目7_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment7Disp(medicalTreatmentDto.getMedicalTreatment7Disp());
        /** 診療項目8_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment8Disp(medicalTreatmentDto.getMedicalTreatment8Disp());
        /** 診療項目9_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment9Disp(medicalTreatmentDto.getMedicalTreatment9Disp());
        /** 診療項目10_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment10Disp(medicalTreatmentDto.getMedicalTreatment10Disp());

        // 診療項目
        memberInfoUpdateModel.setMedicalTreatment(memberInfoEntity.getMedicalTreatmentFlag());

        String[] medicalTreatment =
                        String.format("%-10s", memberInfoEntity.getMedicalTreatmentFlag()).replace(" ", "0").split("");

        for (int i = 1; i <= medicalTreatment.length; i++) {
            if (HTypeMedicalTreatmentFlag.ON.getValue().equals(medicalTreatment[i - 1])) {
                switch (i) {
                    case 1:
                        memberInfoUpdateModel.setMedicalTreatment1(true);
                        continue;
                    case 2:
                        memberInfoUpdateModel.setMedicalTreatment2(true);
                        continue;
                    case 3:
                        memberInfoUpdateModel.setMedicalTreatment3(true);
                        continue;
                    case 4:
                        memberInfoUpdateModel.setMedicalTreatment4(true);
                        continue;
                    case 5:
                        memberInfoUpdateModel.setMedicalTreatment5(true);
                        continue;
                    case 6:
                        memberInfoUpdateModel.setMedicalTreatment6(true);
                        continue;
                    case 7:
                        memberInfoUpdateModel.setMedicalTreatment7(true);
                        continue;
                    case 8:
                        memberInfoUpdateModel.setMedicalTreatment8(true);
                        continue;
                    case 9:
                        memberInfoUpdateModel.setMedicalTreatment9(true);
                        continue;
                    case 10:
                        memberInfoUpdateModel.setMedicalTreatment10(true);
                        continue;
                    default:
                        break;
                }
            } else {
                switch (i) {
                    case 1:
                        memberInfoUpdateModel.setMedicalTreatment1(false);
                        continue;
                    case 2:
                        memberInfoUpdateModel.setMedicalTreatment2(false);
                        continue;
                    case 3:
                        memberInfoUpdateModel.setMedicalTreatment3(false);
                        continue;
                    case 4:
                        memberInfoUpdateModel.setMedicalTreatment4(false);
                        continue;
                    case 5:
                        memberInfoUpdateModel.setMedicalTreatment5(false);
                        continue;
                    case 6:
                        memberInfoUpdateModel.setMedicalTreatment6(false);
                        continue;
                    case 7:
                        memberInfoUpdateModel.setMedicalTreatment7(false);
                        continue;
                    case 8:
                        memberInfoUpdateModel.setMedicalTreatment8(false);
                        continue;
                    case 9:
                        memberInfoUpdateModel.setMedicalTreatment9(false);
                        continue;
                    case 10:
                        memberInfoUpdateModel.setMedicalTreatment10(false);
                        continue;
                    default:
                        break;
                }
            }
        }
    }
    // PDR Migrate Customization to here
}
