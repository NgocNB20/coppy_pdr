/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCHankakuTrim;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCZenkaku;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVItems;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVMailAddress;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVNumber;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVRItems;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVRStringEqual;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVRZipCode;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.front.base.constant.RegularExpressionsConstants;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSexUnnecessaryAnswer;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.validation.group.ConfirmInfoGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.validation.group.InputMailGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.validation.group.InputmemnoGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.validation.group.InputtelGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.validation.group.RegistGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;

/**
 * 本会員登録 Model
 * // PDR Migrate Customization from here
 * PDR#011 08_データ連携（顧客情報）<br/>
 *
 * <pre>
 * お客様情報入力ページクラス
 * </pre>
 * <pre>
 *  本会員登録完了ページ
 *  ・メールアドレスを追加
 *  /pre>
 *  <pre>
 *  申込み完了ページ
 *  ・メールアドレスを追加
 *  </pre>
 *  <pre>
 *  会員情報表示ページ
 *  </pre>
 *  <pre>
 *  本会員登録確認ページ
 *  </pre>
 *  <pre>
 *  お客様番号入力ページクラス
 *  </pre>
 *  <pre>
 *  電話番号入力ページクラス
 *  </pre>
 *
 * @author satoh
 * // PDR Migrate Customization to here
 */
@Data
@HVRZipCode(targetLeft = "memberInfoZipCode1", targetRight = "memberInfoZipCode2")
@HVRItems(target = "memberInfoPrefecture", comparison = "memberInfoPrefectureItems")
// PDR Migrate Customization from here
// 2023-renew No85-1 from here
@HVRStringEqual(target = "memberInfoMailConfirm", comparison = "memberInfoMail",
                groups = {RegistGroup.class, ConfirmInfoGroup.class})
// 2023-renew No85-1 to here
// PDR Migrate Customization to here
public class RegistModel extends AbstractModel {

    // PDR Migrate Customization from here
    /**
     * メッセージコード：正規表現エラー(制限なし)
     */
    private static final String MSGCD_REGULAR_EXPRESSION_ERR = "PKG34-3552-302-A-";
    /**
     * メッセージコード：正規表現エラー(全角のみ)
     */
    private static final String MSGCD_REGULAR_EXPRESSION_EM_SIZE_ERR = "PKG34-3552-303-A-";
    /**
     * メッセージコード：正規表現エラー(半角英数のみ)
     */
    private static final String MSGCD_REGULAR_EXPRESSION_AN_CHAR_ERR = "PKG34-3552-304-A-";
    /**
     * メッセージコード：正規表現エラー(半角英字のみ)
     */
    private static final String MSGCD_REGULAR_EXPRESSION_A_CHAR_ERR = "PKG34-3552-305-A-";
    /**
     * メッセージコード：正規表現エラー(半角数字のみ)
     */
    private static final String MSGCD_REGULAR_EXPRESSION_N_CHAR_ERR = "PKG34-3552-306-A-";
    // PDR Migrate Customization to here

    /**
     * セッション切れ<br/>
     * ※Controllerからの連番
     */
    protected static final String MSGCD_SESSION_DESTROY = "AMR000404";

    // PDR Migrate Customization from here
    /**
     * 診療項目_使用
     */
    private static final String MEDICAL_TREATMENT_ON = "1";
    // PDR Migrate Customization to here

    /**
     * 初期値セット<br/>
     * // PDR Migrate Customization from here
     * <pre>
     * 性別、メール関連のチェックボックスの初期値設定を削除
     * FAXによる情報提供の初期値を設定
     * 金属商品価格お知らせ情報の初期値を設定
     * 診療項目の初期値を設定
     * </pre>
     * // PDR Migrate Customization to here
     */
    public RegistModel() {
        memberInfoSex = HTypeSexUnnecessaryAnswer.UNKNOWN.getValue();
        // PDR Migrate Customization from here
        sendMail = false;
        sendFax = false;
        metalPermitFlag = true;
        medicalTreatment1 = false;
        medicalTreatment2 = false;
        medicalTreatment3 = false;
        medicalTreatment4 = false;
        medicalTreatment5 = false;
        medicalTreatment6 = false;
        medicalTreatment7 = false;
        medicalTreatment8 = false;
        medicalTreatment9 = false;
        medicalTreatment10 = false;
        errorUrl = true;
        // PDR Migrate Customization to here
    }

    /**
     * 仮会員ID(URLパラメータ)<br/>
     */
    private String mid;

    // PDR Migrate Customization from here
    /**
     * 反社会的勢力ではないことの保証フラグ<br/>
     */
    // 2023-renew No85-1 from here
    @NotEmpty(groups = {RegistGroup.class, ConfirmInfoGroup.class})
    // 2023-renew No85-1 to here
    private String noAntiSocialFlag;

    /**
     * メール配信希望<br/>
     */
    private boolean sendMail;

    /**
     * 会員メールアドレス
     */
    // 2023-renew No85-1 from here
    @NotEmpty(groups = {RegistGroup.class, InputMailGroup.class, ConfirmInfoGroup.class})
    @HVMailAddress(groups = {RegistGroup.class, InputMailGroup.class, ConfirmInfoGroup.class})
    @Length(min = 1, max = 256, groups = {RegistGroup.class, InputMailGroup.class, ConfirmInfoGroup.class})
    @HCHankaku
    // 2023-renew No85-1 to here
    // PDR Migrate Customization to here
    private String memberInfoMail;

    // PDR Migrate Customization from here
    /**
     * 会員メールアドレス(確認用)
     */
    // 2023-renew No85-1 from here
    @NotEmpty(groups = {RegistGroup.class, ConfirmInfoGroup.class})
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {RegistGroup.class, ConfirmInfoGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {RegistGroup.class, ConfirmInfoGroup.class})
    // 2023-renew No85-1 to here
    @HCHankaku
    private String memberInfoMailConfirm;

    /**
     * FAXによる情報提供
     */
    private boolean sendFax;

    /**
     * 休診曜日
     */
    private String nonConsultation;

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
     * その他診療内容
     */
    @Length(min = 0, max = 30)
    private String medicalTreatmentMemo;

    /**
     * 金属商品価格お知らせメール
     */
    private boolean metalPermitFlag;

    // 2023-renew No79 from here
    /**
     * 注文完了メール
     */
    private boolean orderCompletePermitFlag;

    /**
     * 発送完了メール
     */
    private boolean deliveryCompletePermitFlag;
    // 2023-renew No79 to here

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
     * 事業所名
     */
    @NotEmpty(groups = {RegistGroup.class}) @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_REGEX,
                                                     message = "{HZenkakuValidator.INVALID_detail}",
                                                     groups = {RegistGroup.class}) @Length(min = 1, max = 40,
                                                                                           groups = {RegistGroup.class})
    // PDR Migrate Customization to here
    @HCZenkaku
    private String memberInfoLastName;

    // PDR Migrate Customization from here
    /**
     * 事業所名(フリガナ)
     */
    // 2023-renew No85-1 from here
    @NotEmpty(groups = {RegistGroup.class}) @Pattern(groups = {RegistGroup.class},
                                                     regexp = RegularExpressionsConstants.HANKAKU_KANA_REGEX,
                                                     message = "{HHankakuKanaValidator.INVALID_detail}") @Length(
                    min = 1, max = 30, groups = {RegistGroup.class})
    // PDR Migrate Customization to here
    @HCHankaku
    // 2023-renew No85-1 to here
    private String memberInfoLastKana;

    // PDR Migrate Customization from here
    /**
     * 代表者名
     */
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_REGEX, message = "{HZenkakuValidator.INVALID_detail}")
    @Length(min = 0, max = 15)
    @HCZenkaku
    private String representativeName;

    /**
     * 顧客区分
     */
    @NotEmpty(groups = {RegistGroup.class})
    @Length(min = 1, max = 2, groups = {RegistGroup.class})
    private String businessType;

    /**
     * 顧客区分選択肢
     */
    private Map<String, String> businessTypeItems;
    // PDR Migrate Customization to here

    /**
     * 性別<br/>
     */
    @HVItems(target = HTypeSexUnnecessaryAnswer.class)
    @NotEmpty
    private String memberInfoSex;

    /**
     * 性別選択肢<br/>
     */
    private Map<String, String> memberInfoSexItems;

    // PDR Migrate Customization from here
    /**
     * 電話番号<br/>
     */
    @NotEmpty(groups = {InputtelGroup.class, RegistGroup.class})
    // 2023-renew No85-1 from here
    @Pattern(regexp = RegularExpressionsConstants.TELEPHONE_NUMBER_REGEX,
             message = "{HTelephoneNumberValidator.INVALID_detail}",
             groups = {InputtelGroup.class, RegistGroup.class}) @Length(min = 1, max = 14,
                                                                        groups = {InputtelGroup.class,
                                                                                        RegistGroup.class})
    // 2023-renew No85-1 to here
    // PDR Migrate Customization to here
    @HCHankakuTrim
    private String memberInfoTel;

    /**
     * 連絡先電話番号<br/>
     */
    @Pattern(regexp = RegularExpressionsConstants.TELEPHONE_NUMBER_REGEX,
             message = "{HTelephoneNumberValidator.INVALID_detail}")
    @Length(min = 0, max = 11)
    @HCHankaku
    private String memberInfoContactTel;

    /**
     * Fax<br/>
     */
    // 2023-renew No85-1 from here
    @Pattern(groups = {RegistGroup.class, ConfirmInfoGroup.class},
             regexp = RegularExpressionsConstants.TELEPHONE_NUMBER_REGEX,
             message = "{HTelephoneNumberValidator.INVALID_detail}")
    // PDR Migrate Customization from here
    @Length(min = 0, max = 14, groups = {RegistGroup.class, ConfirmInfoGroup.class})
    // 2023-renew No85-1 to here
    // PDR Migrate Customization to here
    @HCHankaku
    private String memberInfoFax;

    /**
     * 郵便番号(上3桁)<br/>
     */
    // PDR Migrate Customization from here
    @NotEmpty(groups = {RegistGroup.class})
    // PDR Migrate Customization to here
    @Length(min = 1, max = 3, groups = {RegistGroup.class})
    @HCHankaku
    private String memberInfoZipCode1;

    /**
     * 郵便番号(下4桁)<br/>
     */
    // PDR Migrate Customization from here
    @NotEmpty(groups = {RegistGroup.class})
    // PDR Migrate Customization to here
    @Length(min = 1, max = 4, groups = {RegistGroup.class})
    @HCHankaku
    private String memberInfoZipCode2;

    /**
     * 都道府県<br/>
     */
    // PDR Migrate Customization from here
    @NotEmpty(groups = {RegistGroup.class}) @Length(min = 1, max = 4, groups = {RegistGroup.class})
    // PDR Migrate Customization to here
    private String memberInfoPrefecture;

    /**
     * 都道府県プルダウン用リスト
     **/
    private Map<String, String> memberInfoPrefectureItems;

    /**
     * 住所(市区郡)<br/>
     */
    @NotEmpty(groups = {RegistGroup.class}) @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_REGEX,
                                                     message = "{HZenkakuValidator.INVALID_detail}",
                                                     groups = {RegistGroup.class})
    // PDR Migrate Customization from here
    // 2023-renew No85-1 from here
    @Length(min = 1, max = 36, groups = {RegistGroup.class})
    // 2023-renew No85-1 to here
    // PDR Migrate Customization to here
    @HCZenkaku
    private String memberInfoAddress1;

    /**
     * 住所(町村)<br/>
     */
    @NotEmpty(groups = {RegistGroup.class}) @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_REGEX,
                                                     message = "{HZenkakuValidator.INVALID_detail}",
                                                     groups = {RegistGroup.class})
    // PDR Migrate Customization from here
    // 2023-renew No85-1 from here
    @Length(min = 1, max = 40, groups = {RegistGroup.class})
    // 2023-renew No85-1 to here
    // PDR Migrate Customization to here
    @HCZenkaku
    private String memberInfoAddress2;

    /**
     * 住所(番地・ビル名)<br/>
     */
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_REGEX, message = "{HZenkakuValidator.INVALID_detail}",
             groups = {RegistGroup.class})
    // PDR Migrate Customization from here
    // 2023-renew No85-1 from here
    @Length(min = 0, max = 40, groups = {RegistGroup.class})
    // 2023-renew No85-1 to here
    // PDR Migrate Customization to here
    @HCZenkaku
    private String memberInfoAddress3;

    // PDR Migrate Customization from here
    /**
     * 方書1
     */
    private String memberInfoAddress4;

    /**
     * 方書2
     */
    private String memberInfoAddress5;

    /**
     * 更新用会員メールアドレス
     */
    // 2023-renew No85-1 from here
    private String inputMemberInfoMail;

    /**
     * 更新用会員メールアドレス(確認用)
     */
    private String inputMemberInfoMailConfirm;
    // 2023-renew No85-1 to here

    /**
     * パスワード<br/>
     */
    @NotEmpty
    @Pattern(regexp = RegularExpressionsConstants.PASSWORD_NUMBER_REGEX,
             message = "{HPasswordValidator.INVALID_detail}")
    private String memberInfoPassWord;

    /**
     * パスワード設定済みフラグ
     */
    private boolean setPassword;
    // PDR Migrate Customization to here

    /**
     * 無効なURLからの遷移かどうか<br/>
     * ※デフォルトtrue。Controllerで正常処理実行時のみfalseが設定
     */
    private boolean errorUrl;

    /**
     * ショップSEQ
     */
    private Integer shopSeq;

    // PDR Migrate Customization from here
    /**
     * 顧客番号(お客様番号)
     */
    @NotEmpty(groups = {InputmemnoGroup.class})
    @Length(min = 1, max = 9, groups = {InputmemnoGroup.class})
    @HVNumber(groups = {InputmemnoGroup.class})
    @HCHankaku
    private String customerNo;

    // 2023-renew No85-1 from here
    /**
     * ご登録電話番号入力画面(電話番号存在あり)から
     */
    private boolean fromInputTelExist = false;

    /**
     * お客様番号画面（お客様番号存在あり）から
     */
    private boolean fromInputMemNoExist = false;

    /**
     * Faxが存在する
     * true: 存在する false: 存在しない
     */
    private boolean memberInfoFaxExist = false;
    // 2023-renew No85-1 to here

    /**
     * 会員情報エンティティ
     */
    private MemberInfoEntity memberInfoEntity;
    // PDR Migrate Customization to here

    // 2023-renew No22 from here
    /**
     * ファイルをアップロードする
     */
    private MultipartFile[] uploadFiles;

    /**
     * アップロードファイルの登録
     */
    private List<RegistUploadFile> registUploadFiles;

    /**
     * POST後のF5押下の防止対策フラグ
     */
    private boolean reloadFlag;

    /**
     * 序数
     */
    private Integer fileNo;

    /**
     * アップロード時のエラー
     */
    private boolean isErrorUpload;
    // 2023-renew No22 to here

    /**
     * 不正なメールからの処理の場合<br/>
     *
     * @return true=不正メール, false=正常終了
     */
    private boolean isErrorMemberInfoMail() {
        if (errorUrl) {
            return true;
        }
        return false;
    }

    // PDR Migrate Customization from here

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
