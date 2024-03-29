/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo;

import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * PDR#429 1.7次　基幹リニューアル対応　【要望No.6,7,8】　フロント会員更新追加<br/>
 * 診療項目Dtoクラス<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class MedicalTreatmentDto {

    // PDR Migrate Customization from here
    /** 診療項目1_表示判定区分 */
    private static final String MEDICALTREATMENT1DISP = "medical.treatment.disp1";
    /** 診療項目2_表示判定区分 */
    private static final String MEDICALTREATMENT2DISP = "medical.treatment.disp2";
    /** 診療項目3_表示判定区分 */
    private static final String MEDICALTREATMENT3DISP = "medical.treatment.disp3";
    /** 診療項目4_表示判定区分 */
    private static final String MEDICALTREATMENT4DISP = "medical.treatment.disp4";
    /** 診療項目5_表示判定区分 */
    private static final String MEDICALTREATMENT5DISP = "medical.treatment.disp5";
    /** 診療項目6_表示判定区分 */
    private static final String MEDICALTREATMENT6DISP = "medical.treatment.disp6";
    /** 診療項目7_表示判定区分 */
    private static final String MEDICALTREATMENT7DISP = "medical.treatment.disp7";
    /** 診療項目8_表示判定区分 */
    private static final String MEDICALTREATMENT8DISP = "medical.treatment.disp8";
    /** 診療項目9_表示判定区分 */
    private static final String MEDICALTREATMENT9DISP = "medical.treatment.disp9";
    /** 診療項目10_表示判定区分 */
    private static final String MEDICALTREATMENT10DISP = "medical.treatment.disp10";

    /** 診療項目1_タイトル */
    private static final String MEDICALTREATMENT1TITLE = "medical.treatment.title1";
    /** 診療項目2_タイトル */
    private static final String MEDICALTREATMENT2TITLE = "medical.treatment.title2";
    /** 診療項目3_タイトル */
    private static final String MEDICALTREATMENT3TITLE = "medical.treatment.title3";
    /** 診療項目4_タイトル */
    private static final String MEDICALTREATMENT4TITLE = "medical.treatment.title4";
    /** 診療項目5_タイトル */
    private static final String MEDICALTREATMENT5TITLE = "medical.treatment.title5";
    /** 診療項目6_タイトル */
    private static final String MEDICALTREATMENT6TITLE = "medical.treatment.title6";
    /** 診療項目7_タイトル */
    private static final String MEDICALTREATMENT7TITLE = "medical.treatment.title7";
    /** 診療項目8_タイトル */
    private static final String MEDICALTREATMENT8TITLE = "medical.treatment.title8";
    /** 診療項目9_タイトル */
    private static final String MEDICALTREATMENT9TITLE = "medical.treatment.title9";
    /** 診療項目10_タイトル */
    private static final String MEDICALTREATMENT10TITLE = "medical.treatment.title10";

    // 定数設定
    /** 診療項目1_タイトル */
    private String medicalTreatment1Title = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT1TITLE);
    /** 診療項目2_タイトル */
    private String medicalTreatment2Title = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT2TITLE);
    /** 診療項目3_タイトル */
    private String medicalTreatment3Title = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT3TITLE);
    /** 診療項目4_タイトル */
    private String medicalTreatment4Title = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT4TITLE);
    /** 診療項目5_タイトル */
    private String medicalTreatment5Title = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT5TITLE);
    /** 診療項目6_タイトル */
    private String medicalTreatment6Title = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT6TITLE);
    /** 診療項目7_タイトル */
    private String medicalTreatment7Title = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT7TITLE);
    /** 診療項目8_タイトル */
    private String medicalTreatment8Title = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT8TITLE);
    /** 診療項目9_タイトル */
    private String medicalTreatment9Title = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT9TITLE);
    /** 診療項目10_タイトル */
    private String medicalTreatment10Title = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT10TITLE);

    /** 診療項目1_表示判定 */
    private String medicalTreatment1Disp = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT1DISP);
    /** 診療項目2_表示判定 */
    private String medicalTreatment2Disp = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT2DISP);
    /** 診療項目3_表示判定 */
    private String medicalTreatment3Disp = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT3DISP);
    /** 診療項目4_表示判定 */
    private String medicalTreatment4Disp = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT4DISP);
    /** 診療項目5_表示判定 */
    private String medicalTreatment5Disp = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT5DISP);
    /** 診療項目6_表示判定 */
    private String medicalTreatment6Disp = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT6DISP);
    /** 診療項目7_表示判定 */
    private String medicalTreatment7Disp = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT7DISP);
    /** 診療項目8_表示判定 */
    private String medicalTreatment8Disp = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT8DISP);
    /** 診療項目9_表示判定 */
    private String medicalTreatment9Disp = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT9DISP);
    /** 診療項目10_表示判定 */
    private String medicalTreatment10Disp = PropertiesUtil.getSystemPropertiesValue(MEDICALTREATMENT10DISP);
    // PDR Migrate Customization to here
}
