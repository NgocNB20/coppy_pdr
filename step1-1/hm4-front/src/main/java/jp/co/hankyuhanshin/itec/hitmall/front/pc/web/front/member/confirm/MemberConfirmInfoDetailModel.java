package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.confirm;

import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 会員情報詳細 Model
 *
 * @author EntityGenerator
 * @version $Revision: 1.0 $
 */
@Data
@Component
@Scope("prototype")
public class MemberConfirmInfoDetailModel extends MemberInfoEntity implements Serializable {

    /**
     * 郵便番号(上3桁)
     */
    private String memberInfoZipCode1;

    /**
     * 郵便番号(下4桁)
     */
    private String memberInfoZipCode2;

    /**
     * 休診曜日_表示<br/>
     */
    private String nonConsultationDayDisp;

    /**
     * メール情報
     */
    private boolean sendMail;

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
}
