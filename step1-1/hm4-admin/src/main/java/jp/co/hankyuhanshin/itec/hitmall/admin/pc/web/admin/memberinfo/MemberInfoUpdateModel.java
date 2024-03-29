/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCZenkaku;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCZenkakuKana;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVItems;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVMailAddress;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAccountingType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCashDeliveryUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCreditPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDentalMonopolySalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDirectDebitUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDrugSalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMedicalEquipmentSalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberListType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMetalPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMonthlyPayUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineLoginAdvisability;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendDirectMailFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendFaxPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTransferPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hmbase.constant.RegularExpressionsConstants;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 会員情報変更モデル
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class MemberInfoUpdateModel extends AbstractModel {

    /** 診療項目_使用 */
    public static final String MEDICAL_TREATMENT_ON = "1";

    /**
     * お客様情報
     * 会員SEQ
     */
    private Integer memberInfoSeq;

    /**
     * 会員詳細Dtoクラス
     */
    private MemberInfoDetailsDto memberInfoDetailsDto;

    /**
     * 会員情報エンティティ
     */
    private MemberInfoEntity memberInfoEntity;

    /**
     * 会員状態
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVItems(target = HTypeMemberInfoStatus.class, groups = {ConfirmGroup.class})
    @Length(max = 1, groups = {ConfirmGroup.class})
    private String memberInfoStatus;

    /**
     * 会員状態
     */
    private Map<String, String> memberInfoStatusItems;

    /**
     * 登録日時
     */
    private Timestamp registTime;

    /**
     * 更新日時
     */
    private Timestamp updateTime;

    /**
     * 入会日
     */
    @NotEmpty
    @HVDate(groups = {ConfirmGroup.class})
    @HCDate
    private String admissionYmd;

    /**
     * 退会日
     */
    @HVDate(groups = {ConfirmGroup.class})
    @HCDate
    private String secessionYmd;

    /**
     * アカウントロック状態
     * true..ロックされている
     */
    private boolean accountLock;

    /**
     * アカウントロック日時
     */
    @HCDate(pattern = DateUtility.YMD_SLASH_HMS)
    private Timestamp accountLockTime;

    /**
     * ログイン失敗回数
     */
    private Integer loginFailureCount;

    /**
     * 最終ログイン情報
     * 最終ログイン日時
     */
    @HCDate(pattern = DateUtility.YMD_SLASH_HMS)
    private String lastLoginTime;

    /**
     * デバイス種別
     */
    private HTypeDeviceType lastLoginDeviceType;

    /**
     * 最終ログインユーザーエージェント
     */
    private String lastLoginUserAgent;

    /**
     * 会員ID
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    // 2023-renew No85-1 from here
    @Length(max = 256, groups = {ConfirmGroup.class})
    // 2023-renew No85-1 to here
    @HVMailAddress(groups = {ConfirmGroup.class})
    @HCHankaku
    private String memberInfoId;

    /**
     * 事業所名(姓)
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_REGEX, message = "{HZenkakuValidator.INVALID_detail}",
             groups = {ConfirmGroup.class})
    @Length(max = 40, groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @HCZenkaku
    private String memberInfoLastName;

    /**
     * 事業所名(名)
     */
    @NotEmpty
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_REGEX, message = "{HZenkakuValidator.INVALID_detail}")
    @Length(min = 1, max = 16)
    @HCZenkaku
    private String memberInfoFirstName;

    /**
     * 事業所名(フリガナ)(セイ)
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    // 2023-renew No85-1 from here
    @Pattern(regexp = RegularExpressionsConstants.HANKAKU_KANA_REGEX,
             message = "{HHankakuKanaValidator.INVALID_detail}", groups = {ConfirmGroup.class}) @Length(max = 30,
                                                                                                        groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @HCHankaku
    // 2023-renew No85-1 to here
    private String memberInfoLastKana;

    /**
     * 事業所名(フリガナ)(メイ)
     */
    @NotEmpty
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_KANA_REGEX,
             message = "{HZenkakuKanaValidator.INVALID_detail}")
    @Length(min = 1, max = 40)
    @HCZenkakuKana
    private String memberInfoFirstKana;

    /**
     * 性別
     */
    @NotEmpty
    @Length(max = 1)
    private String memberInfoSex;

    /**
     * 性別選択肢
     */
    private Map<String, String> memberInfoSexItems;

    /**
     * 生年月日
     */
    @NotEmpty
    @HVDate
    @HCDate
    private String memberInfoBirthday;

    /**
     * 電話番号
     */
    @NotEmpty(groups = {ConfirmGroup.class}) @Pattern(regexp = RegularExpressionsConstants.TELEPHONE_NUMBER_REGEX,
                                                      message = "{HTelephoneNumberValidator.INVALID_detail}",
                                                      groups = {ConfirmGroup.class})
    // 2023-renew No85-1 from here
    @Length(max = 14, groups = {ConfirmGroup.class})
    // 2023-renew No85-1 to here
    @HCHankaku
    private String memberInfoTel;

    /**
     * 連絡先電話番号
     */
    @Pattern(regexp = RegularExpressionsConstants.TELEPHONE_NUMBER_REGEX,
             message = "{HTelephoneNumberValidator.INVALID_detail}", groups = {ConfirmGroup.class})
    @Length(max = 11, groups = {ConfirmGroup.class})
    @HCHankaku
    private String memberInfoContactTel;

    /**
     * FAX
     */
    @Pattern(regexp = RegularExpressionsConstants.TELEPHONE_NUMBER_REGEX,
             message = "{HTelephoneNumberValidator.INVALID_detail}", groups = {ConfirmGroup.class})
    // 2023-renew No85-1 from here
    @Length(max = 14, groups = {ConfirmGroup.class})
    // 2023-renew No85-1 to here
    @HCHankaku
    private String memberInfoFax;

    /**
     * 郵便番号
     */
    @NotEmpty(groups = ConfirmGroup.class)
    @Length(max = 7, groups = ConfirmGroup.class)
    @Pattern(regexp = RegularExpressionsConstants.ZIP_CODE_REGEX, message = "{HZipCodeValidator.INVALID_detail}",
             groups = ConfirmGroup.class)
    @HCHankaku
    private String memberInfoZipCode;

    /**
     * 会員住所-都道府県（必須）
     */
    @NotEmpty
    @Length(max = 4)
    private String memberInfoPrefecture;

    /**
     * 都道府県プルダウン用リスト
     **/
    private Map<String, String> memberInfoPrefectureItems;

    // PDR Migrate Customization from here
    //    /**
    //     *
    //     * PDR#11 08_データ連携（顧客情報）会員情報の項目追加・変更<br/>
    //     * 会員管理・会員詳細修正ページ<br/>
    //     *
    //     */
    /**
     * 住所
     */
    @NotEmpty(groups = ConfirmGroup.class)
    // 2023-renew No85-1 from here
    @Length(max = 40, groups = ConfirmGroup.class)
    // 2023-renew No85-1 to here
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String memberInfoAddress1;

    /**
     * 会員住所-町村・番地
     */
    @NotEmpty(groups = ConfirmGroup.class)
    // 2023-renew No85-1 from here
    @Length(max = 40, groups = ConfirmGroup.class)
    // 2023-renew No85-1 to here
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String memberInfoAddress2;

    /**
     * 会員住所-それ以降の住所
     */
    // 2023-renew No85-1 from here
    @Length(max = 40, groups = ConfirmGroup.class)
    // 2023-renew No85-1 to here
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String memberInfoAddress3;

    /** 方書1 */
    @Length(max = 15, groups = ConfirmGroup.class)
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String memberInfoAddress4;

    /** 方書2 */
    @Length(max = 15, groups = ConfirmGroup.class)
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String memberInfoAddress5;
    // PDR Migrate Customization to here

    /**
     * 会員パスワード
     */
    @Pattern(regexp = RegularExpressionsConstants.PASSWORD_NUMBER_REGEX,
             message = "{HPasswordValidator.INVALID_detail}", groups = ConfirmGroup.class)
    private String memberInfoPassword;

    /**
     * パスワード変更要求フラグ(画面引継ぎ用)
     */
    private HTypePasswordNeedChangeFlag passwordNeedChangeFlag;

    /**
     * パスワード変更要求
     */
    private boolean passwordNeedChange;

    /**
     * 事業所名
     */
    private String memberInfoName;
    /**
     * 事業所名(フリガナ)
     */
    private String memberInfoKana;

    /**
     * 郵便番号Ajax 返却メッセージ
     */
    private String memberInfoZipErrorMsg;

    /**
     * 郵便番号(上3桁)
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @Length(min = 1, max = 3, groups = {ConfirmGroup.class})
    @HCHankaku
    private String memberInfoZipCode1;

    /**
     * 郵便番号(下4桁)
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @Length(min = 1, max = 4, groups = {ConfirmGroup.class})
    @HCHankaku
    private String memberInfoZipCode2;

    /**
     * 住所
     */
    private String memberInfoAddress;

    /**
     * 変更箇所リスト
     */
    private List<String> modifiedList;

    // PDR Migrate Customization from here
    /**
     * 承認状態管理フラグ
     */
    private boolean approveStatusFlag = false;

    /**
     * 顧客番号
     */
    private Integer customerNo;

    /**
     * 承認状態
     */
    @NotEmpty(groups = ConfirmGroup.class)
    @HVItems(target = HTypeApproveStatus.class, groups = ConfirmGroup.class)
    private String approveStatus;

    /**
     * 承認状態選択値
     */
    private Map<String, String> approveStatusItems;

    /**
     * オンライン登録状態
     */
    private String onlineRegistFlag;

    /**
     * 顧客番号・パスワード通知メール送信
     */
    private boolean sendNoticeMailFlag;

    /**
     * 代表者名
     */
    @Length(max = 15, groups = ConfirmGroup.class)
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String representativeName;

    /**
     * 顧客区分
     */
    @NotEmpty(groups = ConfirmGroup.class)
    private String businessType;

    /**
     * 顧客区分選択値
     */
    private Map<String, String> businessTypeItems;

    /**
     * 確認書類
     */
    @NotEmpty(groups = ConfirmGroup.class)
    private String confDocumentType;

    /**
     * 確認書類選択値
     */
    private Map<String, String> confDocumentTypeItems;

    /**
     * 医薬品・注射針販売区分
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVItems(target = HTypeDrugSalesType.class, groups = ConfirmGroup.class)
    private String drugSalesType;

    /**
     * 医薬品・注射針販売区分選択値
     */
    private Map<String, String> drugSalesTypeItems;

    /**
     * 医療機器販売区分
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVItems(target = HTypeMedicalEquipmentSalesType.class, groups = ConfirmGroup.class)
    private String medicalEquipmentSalesType;

    /**
     * 医療機器販売区分選択値
     */
    private Map<String, String> medicalEquipmentSalesTypeItems;

    /**
     * 歯科専売品販売区分
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVItems(target = HTypeDentalMonopolySalesType.class, groups = ConfirmGroup.class)
    private String dentalMonopolySalesType;

    /**
     * 歯科専売品販売区分選択値
     */
    private Map<String, String> dentalMonopolySalesTypeItems;

    /**
     * クレジット決済使用可否
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVItems(target = HTypeCreditPaymentUseFlag.class, groups = ConfirmGroup.class)
    private String creditPaymentUseFlag;

    /**
     * クレジット決済使用可否選択値
     */
    private Map<String, String> creditPaymentUseFlagItems;

    /**
     * コンビニ・郵便振込使用可否
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVItems(target = HTypeTransferPaymentUseFlag.class, groups = ConfirmGroup.class)
    private String transferPaymentUseFlag;

    /**
     * コンビニ・郵便振込使用可否選択値
     */
    private Map<String, String> transferPaymentUseFlagItems;

    /* 休診曜日表示用項目 */

    /**
     * 日曜日AM休診
     */
    private boolean amSunNonConsultation;

    /**
     * 月曜日AM休診
     */
    private boolean amMonNonConsultation;

    /**
     * 火曜日AM休診
     */
    private boolean amTueNonConsultation;

    /**
     * 水曜日AM休診
     */
    private boolean amWedNonConsultation;

    /**
     * 木曜日AM休診
     */
    private boolean amThuNonConsultation;

    /**
     * 金曜日AM休診
     */
    private boolean amFriNonConsultation;
    /**
     * 土曜日AM休診
     */
    private boolean amSatNonConsultation;

    /**
     * 祝日AM休診
     */
    private boolean amHolidayNonConsultation;

    /**
     * 日曜日PM休診
     */
    private boolean pmSunNonConsultation;

    /**
     * 月曜日PM休診
     */
    private boolean pmMonNonConsultation;

    /**
     * 火曜日PM休診
     */
    private boolean pmTueNonConsultation;

    /**
     * 水曜日PM休診
     */
    private boolean pmWedNonConsultation;

    /**
     * 木曜日PM休診
     */
    private boolean pmThuNonConsultation;

    /**
     * 金曜日PM休診
     */
    private boolean pmFriNonConsultation;

    /**
     * 土曜日PM休診
     */
    private boolean pmSatNonConsultation;

    /**
     * 祝日PM休診
     */
    private boolean pmHolidayNonConsultation;

    /**
     * 無休
     */
    private boolean allConsultation;

    /* disabled回避の為の待避 */

    /**
     * 日曜日AM休診対応
     */
    private boolean amSunNonConsultationSubstitute;

    /**
     * 月曜日AM休診対応
     */
    private boolean amMonNonConsultationSubstitute;

    /**
     * 火曜日AM休診対応
     */
    private boolean amTueNonConsultationSubstitute;

    /**
     * 水曜日AM休診対応
     */
    private boolean amWedNonConsultationSubstitute;

    /**
     * 木曜日AM休診対応
     */
    private boolean amThuNonConsultationSubstitute;

    /**
     * 金曜日AM休診対応
     */
    private boolean amFriNonConsultationSubstitute;

    /**
     * 土曜日AM休診対応
     */
    private boolean amSatNonConsultationSubstitute;

    /**
     * 祝日AM休診対応
     */
    private boolean amHolidayNonConsultationSubstitute;

    /**
     * 日曜日PM休診対応
     */
    private boolean pmSunNonConsultationSubstitute;

    /**
     * 月曜日PM休診対応
     */
    private boolean pmMonNonConsultationSubstitute;

    /**
     * 火曜日PM休診対応
     */
    private boolean pmTueNonConsultationSubstitute;

    /**
     * 水曜日PM休診対応
     */
    private boolean pmWedNonConsultationSubstitute;

    /**
     * 木曜日PM休診対応
     */
    private boolean pmThuNonConsultationSubstitute;

    /**
     * 金曜日PM休診対応
     */
    private boolean pmFriNonConsultationSubstitute;

    /**
     * 土曜日PM休診対応
     */
    private boolean pmSatNonConsultationSubstitute;

    /**
     * 祝日PM休診対応
     */
    private boolean pmHolidayNonConsultationSubstitute;

    /**
     * 無休対応
     */
    private boolean allConsultationSubstitute;

    /**
     * その他診療内容
     */
    @Length(max = 30, groups = ConfirmGroup.class)
    private String medicalTreatmentMemo;

    /**
     * 承認状態対応
     */
    private String approveStatusSubstitute;

    /**
     * 顧客区分対応
     */
    private String businessTypeSubstitute;

    /**
     * 確認書類対応
     */
    private String confDocumentTypeSubstitute;

    /**
     * 医薬品・注射針販売区分対応
     */
    private String drugSalesTypeSubstitute;

    /**
     * 医療機器販売区分対応
     */
    private String medicalEquipmentSalesTypeSubstitute;

    /**
     * 歯科専売品販売区分対応
     */
    private String dentalMonopolySalesTypeSubstitute;

    /**
     * クレジット決済使用可否対応
     */
    private String creditPaymentUseFlagSubstitute;

    /**
     * コンビニ・郵便振込使用可否対応
     */
    private String transferPaymentUseFlagSubstitute;

    /**
     * 代金引換使用可否対応
     */
    private String cashDeliveryUseFlagSubstitute;

    /**
     * 口座自動引落使用可否対応
     */
    private String directDebitUseFlagSubstitute;

    /**
     * 月締請求使用可否対応
     */
    private String monthlyPayUseFlagSubstitute;

    /**
     * 名簿区分対応
     */
    private String memberListTypeSubstitute;

    /**
     * 経理区分対応
     */
    private String accountingTypeSubstitute;

    /**
     * オンラインログイン可否対応
     */
    private String onlineLoginAdvisabilitySubstitute;

    /**
     * FAXによるおトク情報選択値対応
     */
    private boolean sendFaxSubstitute;

    /**
     * DMによるおトク情報選択値対応
     */
    private boolean sendDirectMailSubstitute;

    /**
     * 休診曜日
     */
    private String nonConsultationDay;

    /**
     * 管理用メモ
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE)
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(max = 2000, groups = {ConfirmGroup.class})
    private String memo;

    /**
     * 代金引換使用可否
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVItems(target = HTypeCashDeliveryUseFlag.class, groups = ConfirmGroup.class)
    private String cashDeliveryUseFlag;

    /**
     * 代金引換使用可否選択値
     */
    private Map<String, String> cashDeliveryUseFlagItems;

    /**
     * 口座自動引落使用可否
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVItems(target = HTypeDirectDebitUseFlag.class, groups = ConfirmGroup.class)
    private String directDebitUseFlag;

    /**
     * 口座自動引落使用可否選択値
     */
    private Map<String, String> directDebitUseFlagItems;

    /**
     * 月締請求使用可否
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVItems(target = HTypeMonthlyPayUseFlag.class, groups = ConfirmGroup.class)
    private String monthlyPayUseFlag;

    /**
     * 月締請求使用可否選択値
     */
    private Map<String, String> monthlyPayUseFlagItems;

    /**
     * 名簿区分
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVItems(target = HTypeMemberListType.class, groups = ConfirmGroup.class)
    private String memberListType;

    /**
     * 名簿区分選択値
     */
    private Map<String, String> memberListTypeItems;

    /**
     * 経理区分
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVItems(target = HTypeAccountingType.class, groups = ConfirmGroup.class)
    private String accountingType;

    /**
     * 経理区分選択値
     */
    private Map<String, String> accountingTypeItems;

    /**
     * オンラインログイン可否
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVItems(target = HTypeOnlineLoginAdvisability.class, groups = ConfirmGroup.class)
    private String onlineLoginAdvisability;

    /**
     * オンラインログイン可否選択値
     */
    private Map<String, String> onlineLoginAdvisabilityItems;

    /**
     * メールによるおトク情報選択値対応
     */
    private boolean sendMailSubstitute;

    /**
     * 金属商品価格お知らせメール選択値
     */
    private boolean metalPermit;

    // 2023-renew No79 from here
    /**
     * 注文完了メール選択値
     */
    private boolean orderCompletePermit;

    /**
     * 発送完了メール選択値
     */
    private boolean deliveryCompletePermit;
    // 2023-renew No79 to here

    /**
     * DMによるおトク情報選択値
     */
    private boolean sendDirectMail;

    /**
     * メール情報
     */
    private boolean sendMail;

    /**
     * FAXによるおトク情報選択値
     */
    private boolean sendFax;

    /**
     * DMによるおトク情報
     */
    private HTypeSendDirectMailFlag sendDirectMailFlag;

    /**
     * FAXによるおトク情報
     */
    private HTypeSendFaxPermitFlag sendFaxPermitFlag;

    /**
     * 金属商品価格お知らせメール
     */
    private HTypeMetalPermitFlag metalPermitFlag;

    // 2023-renew No79 from here
    /**
     * 注文完了メール
     */
    private HTypeOrderCompletePermitFlag orderCompletePermitFlag;

    /**
     * 発送完了メール
     */
    private HTypeDeliveryCompletePermitFlag deliveryCompletePermitFlag;
    // 2023-renew No79 to here

    /**
     * 診療項目1_表示判定
     */
    private String medicalTreatment1Disp;

    /**
     * 診療項目2_表示判定
     */
    private String medicalTreatment2Disp;

    /**
     * 診療項目3_表示判定
     */
    private String medicalTreatment3Disp;

    /**
     * 診療項目4_表示判定
     */
    private String medicalTreatment4Disp;

    /**
     * 診療項目5_表示判定
     */
    private String medicalTreatment5Disp;

    /**
     * 診療項目6_表示判定
     */
    private String medicalTreatment6Disp;

    /**
     * 診療項目7_表示判定
     */
    private String medicalTreatment7Disp;

    /**
     * 診療項目8_表示判定
     */
    private String medicalTreatment8Disp;

    /**
     * 診療項目9_表示判定
     */
    private String medicalTreatment9Disp;

    /**
     * 診療項目10_表示判定
     */
    private String medicalTreatment10Disp;

    /**
     * 診療項目1_タイトル
     */
    private String medicalTreatment1Title;

    /**
     * 診療項目2_タイトル
     */
    private String medicalTreatment2Title;

    /**
     * 診療項目3_タイトル
     */
    private String medicalTreatment3Title;

    /**
     * 診療項目4_タイトル
     */
    private String medicalTreatment4Title;

    /**
     * 診療項目5_タイトル
     */
    private String medicalTreatment5Title;

    /**
     * 診療項目6_タイトル
     */
    private String medicalTreatment6Title;

    /**
     * 診療項目7_タイトル
     */
    private String medicalTreatment7Title;

    /**
     * 診療項目8_タイトル
     */
    private String medicalTreatment8Title;

    /**
     * 診療項目9_タイトル
     */
    private String medicalTreatment9Title;

    /**
     * 診療項目10_タイトル
     */
    private String medicalTreatment10Title;

    /**
     * 診療項目
     */
    private String medicalTreatment;

    /**
     * 診療項目1
     */
    private boolean medicalTreatment1;

    /**
     * 診療項目2
     */
    private boolean medicalTreatment2;

    /**
     * 診療項目3
     */
    private boolean medicalTreatment3;

    /**
     * 診療項目4
     */
    private boolean medicalTreatment4;

    /**
     * 診療項目5
     */
    private boolean medicalTreatment5;

    /**
     * 診療項目6
     */
    private boolean medicalTreatment6;

    /**
     * 診療項目7
     */
    private boolean medicalTreatment7;

    /**
     * 診療項目8
     */
    private boolean medicalTreatment8;

    /**
     * 診療項目9
     */
    private boolean medicalTreatment9;

    /**
     * 診療項目10
     */
    private boolean medicalTreatment10;

    /**
     * 案内メール希望
     */
    private HTypeSendMailPermitFlag sendMailPermitFlag;

    /**
     * 反社会的勢力ではないことの保証
     */
    private boolean noAntiSocialFlag;

    /**
     * 診療項目1 スタイル
     */
    private String diffMedicalTreatment1Class;

    /**
     * 診療項目2 スタイル
     */
    private String diffMedicalTreatment2Class;

    /**
     * 診療項目3 スタイル
     */
    private String diffMedicalTreatment3Class;

    /**
     * 診療項目4 スタイル
     */
    private String diffMedicalTreatment4Class;

    /**
     * 診療項目5 スタイル
     */
    private String diffMedicalTreatment5Class;

    /**
     * 診療項目6 スタイル
     */
    private String diffMedicalTreatment6Class;

    /**
     * 診療項目7 スタイル
     */
    private String diffMedicalTreatment7Class;

    /**
     * 診療項目8 スタイル
     */
    private String diffMedicalTreatment8Class;

    /**
     * 診療項目9 スタイル
     */
    private String diffMedicalTreatment9Class;

    /**
     * 診療項目10 スタイル
     */
    private String diffMedicalTreatment10Class;

    // 2023-renew No22 from here
    /**
     * アップロードファイルの登録
     */
    private List<UploadFileModel> uploadFileModelList;

    /**
     * ファイルをアップロードする
     */
    @Valid
    private MultipartFile[] uploadFiles;

    /**
     * File No
     */
    private Integer fileNo;

    /**
     * アップロード時のエラー
     */
    private boolean isErrorUpload;
    // 2023-renew No22 to here

    /**
     * 診療項目１表示判定
     *
     * @return true=表示、false=非表示
     */
    public boolean isMedicalTreatmentJudge1() {
        return medicalTreatment1Disp.equals(MEDICAL_TREATMENT_ON);
    }

    /**
     * 診療項目２表示判定
     *
     * @return true=表示、false=非表示
     */
    public boolean isMedicalTreatmentJudge2() {
        return medicalTreatment2Disp.equals(MEDICAL_TREATMENT_ON);
    }

    /**
     * 診療項目３表示判定
     *
     * @return true=表示、false=非表示
     */
    public boolean isMedicalTreatmentJudge3() {
        return medicalTreatment3Disp.equals(MEDICAL_TREATMENT_ON);
    }

    /**
     * 診療項目４表示判定
     *
     * @return true=表示、false=非表示
     */
    public boolean isMedicalTreatmentJudge4() {
        return medicalTreatment4Disp.equals(MEDICAL_TREATMENT_ON);
    }

    /**
     * 診療項目５表示判定
     *
     * @return true=表示、false=非表示
     */
    public boolean isMedicalTreatmentJudge5() {
        return medicalTreatment5Disp.equals(MEDICAL_TREATMENT_ON);
    }

    /**
     * 診療項目６表示判定
     *
     * @return true=表示、false=非表示
     */
    public boolean isMedicalTreatmentJudge6() {
        return medicalTreatment6Disp.equals(MEDICAL_TREATMENT_ON);
    }

    /**
     * 診療項目７表示判定
     *
     * @return true=表示、false=非表示
     */
    public boolean isMedicalTreatmentJudge7() {
        return medicalTreatment7Disp.equals(MEDICAL_TREATMENT_ON);
    }

    /**
     * 診療項目８表示判定
     *
     * @return true=表示、false=非表示
     */
    public boolean isMedicalTreatmentJudge8() {
        return medicalTreatment8Disp.equals(MEDICAL_TREATMENT_ON);
    }

    /**
     * 診療項目９表示判定
     *
     * @return true=表示、false=非表示
     */
    public boolean isMedicalTreatmentJudge9() {
        return medicalTreatment9Disp.equals(MEDICAL_TREATMENT_ON);
    }

    /**
     * 診療項目１０表示判定
     *
     * @return true=表示、false=非表示
     */
    public boolean isMedicalTreatmentJudge10() {
        return medicalTreatment10Disp.equals(MEDICAL_TREATMENT_ON);
    }
    // PDR Migrate Customization to here
}
