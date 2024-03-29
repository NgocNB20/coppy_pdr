// 2023-renew AddNo2 from here
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.confirm.update;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCZenkaku;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.front.base.constant.RegularExpressionsConstants;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeliveryCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMetalPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeNoAntiSocialFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOrderCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendDirectMailFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.validation.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.confirm.MemberConfirmInfoDetailModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.confirm.MemberRegistedCardItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.RegistUploadFile;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;

/**
 * 会員情報画面 Model
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Data
public class MemberConfirmUpdateModel extends AbstractModel {

    /**
     * 診療項目_使用
     */
    private static final String MEDICAL_TREATMENT_ON = "1";

    /**
     * メールアドレス
     */
    private String memberInfoMail;

    /**
     * 事業所名
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_REGEX, message = "{HZenkakuValidator.INVALID_detail}",
             groups = {ConfirmGroup.class})
    @Length(min = 1, max = 40, groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @HCZenkaku
    private String memberInfoLastName;

    /**
     * 氏名(名)
     */
    private String memberInfoFirstName;

    /**
     * フリガナ(セイ)
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @Pattern(regexp = RegularExpressionsConstants.KANA_REGEX, message = "{HKanaValidator.INVALID_detail}",
             groups = {ConfirmGroup.class})
    @Length(min = 1, max = 30, groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String memberInfoLastKana;

    /**
     * フリガナ(メイ)
     */
    private String memberInfoFirstKana;

    /**
     * 性別
     */
    private String memberInfoSex;

    /**
     * 生年月日(年)
     */
    private String memberInfoBirthdayYear;

    /**
     * 生年月日(月)
     */
    private String memberInfoBirthdayMonth;

    /**
     * 生年月日(日)
     */
    private String memberInfoBirthdayDay;

    /**
     * 電話番号
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @Pattern(regexp = RegularExpressionsConstants.TELEPHONE_NUMBER_REGEX,
             message = "{HTelephoneNumberValidator.INVALID_detail}", groups = {ConfirmGroup.class})
    @Length(min = 1, max = 14, groups = {ConfirmGroup.class})
    @HCHankaku
    private String memberInfoTel;

    /**
     * 連絡先電話番号
     */
    private String memberInfoContactTel;

    /**
     * FAX
     */
    @Pattern(regexp = RegularExpressionsConstants.TELEPHONE_NUMBER_REGEX,
             message = "{HTelephoneNumberValidator.INVALID_detail}", groups = {ConfirmGroup.class})
    @Length(max = 14, groups = {ConfirmGroup.class})
    @HCHankaku
    private String memberInfoFax;

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
     * 都道府県
     */
    private String memberInfoPrefecture;

    /**
     * 住所-市区郡
     */
    @NotEmpty(groups = ConfirmGroup.class)
    @Length(min = 1, max = 40, groups = ConfirmGroup.class)
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String memberInfoAddress1;

    /**
     * 住所-町村・番地
     */
    @NotEmpty(groups = ConfirmGroup.class)
    @Length(min = 1, max = 40, groups = ConfirmGroup.class)
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String memberInfoAddress2;

    /**
     * 住所-番地・建物名
     */
    @Length(max = 40, groups = ConfirmGroup.class)
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String memberInfoAddress3;

    /**
     * メール送信希望フラグ
     */
    private HTypeSendMailPermitFlag sendMailPermitFlag;

    /**
     * DMによるおトク情報
     */
    private HTypeSendDirectMailFlag sendDirectMailFlag;

    /**
     * 会員追加属性表示番号（DTO参照）
     */
    private Integer displayNumber;

    /**
     * 顧客番号
     */
    private Integer customerNo;

    /**
     * 代表者名
     */
    @Length(max = 15, groups = ConfirmGroup.class)
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String representativeName;

    /**
     * 住所-方書1
     */
    private String memberInfoAddress4;

    /**
     * 住所-方書2
     */
    private String memberInfoAddress5;

    /**
     * 登録済みカードリスト
     */
    private List<MemberRegistedCardItem> registedCardItems;

    /**
     * カードリストインデックス
     */
    private int registedCardIndex;

    /**
     * カードID
     */
    private String cardId;

    /**
     * カード会社
     */
    private String cardBrand;

    /**
     * カード番号
     */
    private String cardNumber;

    /**
     * 有効期限（月）
     */
    private String expirationDateMonth;

    /**
     * 有効期限（年）
     */
    private String expirationDateYear;

    /**
     * 該当カード情報存在フラグ
     */
    private boolean registedCardFlag = false;

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
    @Length(max = 30, groups = ConfirmGroup.class)
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String medicalTreatmentMemo;

    /**
     * メール情報
     */
    private boolean sendMail;

    /**
     * 金属商品価格お知らせメール
     */
    private HTypeMetalPermitFlag metalPermitFlag;

    /**
     * 金属商品価格お知らせメール選択値
     */
    private boolean metalPermit;

    // 2023-renew No79 from here
    /**
     * 注文完了メール
     */
    private HTypeOrderCompletePermitFlag orderCompletePermitFlag;

    /**
     * 注文完了メール選択値
     */
    private boolean orderCompletePermit;

    /**
     * 発送完了メール
     */
    private HTypeDeliveryCompletePermitFlag deliveryCompletePermitFlag;

    /**
     * 発送完了メール選択値
     */
    private boolean deliveryCompletePermit;
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

    // 2023-renew AddNo2 from here
    /**
     * 顧客区分<br/>
     */
    private HTypeBusinessType businessType;

    /**
     * 休診曜日<br/>
     */
    private String nonConsultationDay;

    /**
     * 休診曜日_表示<br/>
     */
    private String nonConsultationDayDisp;

    /**
     * 反社会的勢力ではないことの保証
     */
    private HTypeNoAntiSocialFlag noAntiSocialFlag;

    /**
     * 会員詳細クラス
     */
    private MemberConfirmInfoDetailModel memberInfoDetail;

    /**
     * 変更箇所リスト
     */
    private List<String> modifiedList;
    // 2023-renew AddNo2 to here

    /**
     * 登録済みカードが存在するかどうか判定します。
     *
     * @return true..存在する / false..存在しない
     */
    public boolean isRegistedCardFlag() {
        return registedCardFlag;
    }

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

    /**
     * 顧客区分選択値
     */
    private Map<String, String> businessTypeItems;

    /**
     * アップロードファイルの登録
     */
    private List<RegistUploadFile> uploadFileModelList;

    /**
     * ファイルをアップロードする
     */
    @Valid
    private MultipartFile[] uploadFiles;

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

    /**
     * 最新会員画像用の連番
     */
    private Integer lastImageImageNo = 0;
}
// 2023-renew AddNo2 to here
