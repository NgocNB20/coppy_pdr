// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.confirm;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeNoAntiSocialFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.RegistUploadFile;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;

import java.util.List;

/**
 * 会員情報画面 Model
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Data
public class MemberConfirmModel extends AbstractModel {

    /**
     * お客様情報
     * 会員SEQ
     */
    private Integer memberInfoSeq;

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
    private String memberInfoLastName;

    /**
     * 氏名(名)
     */
    private String memberInfoFirstName;

    /**
     * フリガナ(セイ)
     */
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
    private String memberInfoTel;

    /**
     * 連絡先電話番号
     */
    private String memberInfoContactTel;

    /**
     * FAX
     */
    private String memberInfoFax;

    /**
     * 郵便番号1
     */
    private String memberInfoZipCode1;

    /**
     * 郵便番号2
     */
    private String memberInfoZipCode2;

    /**
     * 都道府県
     */
    private String memberInfoPrefecture;

    /**
     * 住所-市区郡
     */
    private String memberInfoAddress1;

    /**
     * 住所-町村・番地
     */
    private String memberInfoAddress2;

    /**
     * 住所-番地・建物名
     */
    private String memberInfoAddress3;

    /**
     * メール送信希望フラグ
     */
    private HTypeSendMailPermitFlag sendMailPermitFlag;

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
    //    @HCTypeLabel(component = "medicalTreatmentFlagPdr")
    private String medicalTreatment1;

    /**
     * 診療項目2
     */
    //    @HCTypeLabel(component = "medicalTreatmentFlagPdr")
    private String medicalTreatment2;

    /**
     * 診療項目3
     */
    //    @HCTypeLabel(component = "medicalTreatmentFlagPdr")
    private String medicalTreatment3;

    /**
     * 診療項目4
     */
    //    @HCTypeLabel(component = "medicalTreatmentFlagPdr")
    private String medicalTreatment4;

    /**
     * 診療項目5
     */
    //    @HCTypeLabel(component = "medicalTreatmentFlagPdr")
    private String medicalTreatment5;

    /**
     * 診療項目6
     */
    //    @HCTypeLabel(component = "medicalTreatmentFlagPdr")
    private String medicalTreatment6;

    /**
     * 診療項目7
     */
    //    @HCTypeLabel(component = "medicalTreatmentFlagPdr")
    private String medicalTreatment7;

    /**
     * 診療項目8
     */
    //    @HCTypeLabel(component = "medicalTreatmentFlagPdr")
    private String medicalTreatment8;

    /**
     * 診療項目9
     */
    //    @HCTypeLabel(component = "medicalTreatmentFlagPdr")
    private String medicalTreatment9;

    /**
     * 診療項目10
     */
    //    @HCTypeLabel(component = "medicalTreatmentFlagPdr")
    private String medicalTreatment10;

    /**
     * その他診療内容
     */
    private String medicalTreatmentMemo;

    /**
     * メール情報
     */
    private boolean sendMail;

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
    // 2023-renew No22 from here
    /**
     * アップロードファイルの登録
     */
    private List<RegistUploadFile> uploadFiles;
    // 2023-renew No22 to here
}
// PDR Migrate Customization to here
