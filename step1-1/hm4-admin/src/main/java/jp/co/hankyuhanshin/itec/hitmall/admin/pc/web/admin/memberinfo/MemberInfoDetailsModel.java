/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAccountingType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCashDeliveryUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeConfDocumentType;
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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendDirectMailFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendFaxPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSexUnnecessaryAnswer;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTransferPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.loginadvisability.LoginAdvisabilityEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.loginadvisability.LoginAdvisabilityGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.List;

/**
 * 会員詳細モデル
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class MemberInfoDetailsModel extends AbstractModel {

    /**
     * 検索条件保持判定用
     * 遷移元パッケージを格納
     * チケット対応#2743対応
     */
    private String from;

    /**
     * 検索条件保持判定用
     * 「戻る」ボタンに使用
     * チケット対応#2743対応
     */
    private String md = "list";

    /** 診療項目_使用 */
    public static final String MEDICAL_TREATMENT_ON = "1";

    /**
     * ユーザレビューSEQ
     */
    private Integer userReviewSeq;

    /**
     * 会員状態情報
     * 問い合わせ検索用会員ID(メールアドレス)
     */
    private String resultInquiryMemberInfoMail;

    /**
     * 会員SEQ（アカウントロック解除用）
     */
    private Integer unlockTargetSeq;

    /**
     * 会員情報エンティティ
     */
    private MemberInfoEntity memberInfoEntity;

    /**
     * 会員状態
     */
    private HTypeMemberInfoStatus memberInfoStatus;

    /**
     * 入会日
     */
    private String admissionYmd;

    /**
     * 退会日
     */
    private String secessionYmd;

    /**
     * 登録日時
     */
    private Timestamp registTime;

    /**
     * 更新日時
     */
    private Timestamp updateTime;

    /**
     * アカウントロック状態
     * true..ロックされている
     */
    private boolean accountLock;

    /**
     * アカウントロック日時
     */
    private Timestamp accountLockTime;

    /**
     * ログイン失敗回数
     */
    private Integer loginFailureCount;

    /**
     * デバイス種別
     */
    private HTypeDeviceType lastLoginDeviceType;

    /************************************
     ** 最終ログイン情報
     ************************************/
    /**
     * 最終ログイン情報
     * 最終ログイン日時
     */
    private String lastLoginTime;

    /**
     * 最終ログインユーザーエージェント
     */
    private String lastLoginUserAgent;

    /**
     * お客様情報
     * 会員SEQ
     */
    private Integer memberInfoSeq;

    /**
     * 会員ID
     */
    private String memberInfoId;

    /**
     * 氏名
     */
    private String memberInfoName;

    /**
     * パスワード変更要求フラグ
     */
    private String passwordNeedChangeFlag;

    /**
     * フリガナ
     */
    private String memberInfoKana;

    /**
     * 性別
     */
    private HTypeSexUnnecessaryAnswer memberInfoSex;

    /**
     * 生年月日
     */
    private Timestamp memberInfoBirthday;

    /**
     * Tel
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
     * 郵便番号
     */
    private String memberInfoZipCode;

    /**
     * 住所
     */
    private String memberInfoAddress;

    // 2023-renew general-mail from here
    /**
     * メールアドレス
     */
    private String memberInfoMail;
    // 2023-renew general-mail to here

    /**
     * 問い合わせフラグ
     * 問い合わせ検索画面リンクの表示・非表示に使用
     */
    private boolean inquiryFlag;

    /**
     * 注文履歴
     * 注文履歴アイテムリスト
     */
    private List<MemberInfoDetailsOrderItem> orderHistoryItems;

    /**
     * ページング用
     * ページ番号
     */
    private String pageNumber;

    /**
     * 最大表示件数
     */
    private int limit;

    /**
     * ソート項目
     */
    private String orderField;

    /**
     * ソート条件
     */
    private boolean orderAsc;

    /**
     * 検索結果総件数
     */
    private int totalCount;

    /** オンライン登録状態 */
    private HTypeOnlineRegistFlag onlineRegistFlag;

    /** 承認状態管理フラグ */
    private boolean approveStatusFlag;

    /** 顧客番号 */
    private Integer customerNo;

    /** 代表者名 */
    private String representativeName;

    /** 顧客区分 */
    private HTypeBusinessType businessType;

    /** 確認書類 */
    private HTypeConfDocumentType confDocumentType;

    /** 休診曜日 */
    private String nonConsultationDay;

    /** 診療項目 */
    private String medicalTreatment;

    // PDR Migrate Customization from here
    ///**
    // *
    // * PDR#11 08_データ連携（顧客情報） 会員情報の項目追加・変更<br/>
    // * PDR#035 代理注文時のフロントサイト利用<br/>
    // * 会員管理・会員詳細ページ<br/>
    // *
    // */
    /**
     * 承認状態
     */
    private HTypeApproveStatus approveStatus;

    /**
     * ダイレクトメール送信希望フラグ<br/>
     */
    private HTypeSendDirectMailFlag sendDirectMailFlag;

    /**
     * メール配信希望フラグ：列挙型
     */
    private HTypeSendMailPermitFlag sendMailPermitFlag;

    /**
     * FAX配信希望フラグ<br/>
     */
    private HTypeSendFaxPermitFlag sendFaxPermitFlag;

    /**
     * 金属メール希望フラグ
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
     * 管理用メモ
     */
    private String memo;

    /**
     * 反社会的勢力ではないことの保証
     */
    private boolean noAntiSocialFlag;
    // PDR Migrate Customization to here

    /** 診療項目1 */
    private String medicalTreatment1;

    /** 診療項目2 */
    private String medicalTreatment2;

    /** 診療項目3 */
    private String medicalTreatment3;

    /** 診療項目4 */
    private String medicalTreatment4;

    /** 診療項目5 */
    private String medicalTreatment5;

    /** 診療項目6 */
    private String medicalTreatment6;

    /** 診療項目7 */
    private String medicalTreatment7;

    /** 診療項目8 */
    private String medicalTreatment8;

    /** 診療項目9 */
    private String medicalTreatment9;

    /** 診療項目10 */
    private String medicalTreatment10;

    /** その他診療内容 */
    private String medicalTreatmentMemo;

    /** 診療項目1_表示判定 */
    private String medicalTreatment1Disp;

    /** 診療項目2_表示判定 */
    private String medicalTreatment2Disp;

    /** 診療項目3_表示判定 */
    private String medicalTreatment3Disp;

    /** 診療項目4_表示判定 */
    private String medicalTreatment4Disp;

    /** 診療項目5_表示判定 */
    private String medicalTreatment5Disp;

    /** 診療項目6_表示判定 */
    private String medicalTreatment6Disp;

    /** 診療項目7_表示判定 */
    private String medicalTreatment7Disp;

    /** 診療項目8_表示判定 */
    private String medicalTreatment8Disp;

    /** 診療項目9_表示判定 */
    private String medicalTreatment9Disp;

    /** 診療項目10_表示判定 */
    private String medicalTreatment10Disp;

    /** 診療項目1_タイトル */
    private String medicalTreatment1Title;

    /** 診療項目2_タイトル */
    private String medicalTreatment2Title;

    /** 診療項目3_タイトル */
    private String medicalTreatment3Title;

    /** 診療項目4_タイトル */
    private String medicalTreatment4Title;

    /** 診療項目5_タイトル */
    private String medicalTreatment5Title;

    /** 診療項目6_タイトル */
    private String medicalTreatment6Title;

    /** 診療項目7_タイトル */
    private String medicalTreatment7Title;

    /** 診療項目8_タイトル */
    private String medicalTreatment8Title;

    /** 診療項目9_タイトル */
    private String medicalTreatment9Title;

    /** 診療項目10_タイトル */
    private String medicalTreatment10Title;

    /** 歯科専売品販売区分 */
    private HTypeDentalMonopolySalesType dentalMonopolySalesType;

    /** クレジット決済使用可否 */
    private HTypeCreditPaymentUseFlag creditPaymentUseFlag;

    /** コンビニ・郵便振込使用可否 */
    private HTypeTransferPaymentUseFlag transferPaymentUseFlag;

    /** 代金引換使用可否 */
    private HTypeCashDeliveryUseFlag cashDeliveryUseFlag;

    /** 口座自動引落使用可否 */
    private HTypeDirectDebitUseFlag directDebitUseFlag;

    /** 月締請求使用可否 */
    private HTypeMonthlyPayUseFlag monthlyPayUseFlag;

    /** 名簿区分 */
    private HTypeMemberListType memberListType;

    /** 経理区分 */
    private HTypeAccountingType accountingType;

    /** オンラインログイン可否 */
    private HTypeOnlineLoginAdvisability onlineLoginAdvisability;

    /** 医薬品・注射針販売区分 */
    private HTypeDrugSalesType drugSalesType;

    /** 医療機器販売区分 */
    private HTypeMedicalEquipmentSalesType medicalEquipmentSalesType;

    // PDR Migrate Customization from here

    /**
     * 注文履歴の有無
     *
     * @return true...有、false...無
     */
    public boolean isOrderHistoryExist() {
        return orderHistoryItems != null && !orderHistoryItems.isEmpty();
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
     * 入会かつ名簿区分が顧客かどうか判定<br/>
     * 新規受注ボタンの表示判定に使用<br/>
     *
     * @return true入会かつログイン可、false=入会以外もしくはログイン不可
     */
    public boolean isAdmission() {
        String memberInfoIdOrCustomerNo = null;
        if (this.customerNo == null) {
            memberInfoIdOrCustomerNo = this.memberInfoId;
        } else {
            memberInfoIdOrCustomerNo = this.customerNo.toString();
        }

        if (memberInfoIdOrCustomerNo == null) {
            // 会員ID、顧客番号どちらもnullの場合はFALSEを返却
            return false;
        }

        MemberInfoGetLogic memberInfoGetLogic = ApplicationContextUtility.getBean(MemberInfoGetLogic.class);

        // 顧客番号から会員情報取得
        MemberInfoEntity memberInfoEntity =
                        memberInfoGetLogic.getMemberInfoEntityByMemberInfoIdOrCustomerNo(memberInfoIdOrCustomerNo);

        if (memberInfoEntity == null) {
            // 会員情報取得に失敗した場合、FALSEを返却
            return false;
        }

        if (memberInfoEntity.getMemberInfoStatus() == null || memberInfoEntity.getApproveStatus() == null
            || memberInfoEntity.getOnlineLoginAdvisability() == null || memberInfoEntity.getMemberListType() == null
            || memberInfoEntity.getAccountingType() == null) {
            // 項目に不備がある場合はFALSEを返却
            return false;
        }
        LoginAdvisabilityGetLogic loginAdvisabilityGetLogic =
                        ApplicationContextUtility.getBean(LoginAdvisabilityGetLogic.class);

        LoginAdvisabilityEntity loginAdvisabilityEntity =
                        loginAdvisabilityGetLogic.getLoginAdvisabilityPdrEntityByMemberInfo(memberInfoEntity);

        if (loginAdvisabilityEntity.getLoginAdvisabilitytype() == null) {
            // ログイン可否判定取得に失敗した場合、FALSEを返却
            return false;
        }

        return HTypeMemberInfoStatus.ADMISSION.equals(memberInfoStatus)
               && (!loginAdvisabilityEntity.getLoginAdvisabilitytype().equals("0"));
    }
    // PDR Migrate Customization to here

    // 2023-renew No85-2 from here

    /**
     * 会員解除ボタンを表示
     *
     * @return TRUE:表示する / FALSE:表示しない
     */
    public boolean isCancelMember() {
        return memberInfoEntity.getMemberInfoStatus().equals(HTypeMemberInfoStatus.REMOVE)
               && memberInfoEntity.getApproveStatus().equals(HTypeApproveStatus.OFF)
               && memberInfoEntity.getOnlineRegistFlag().equals(HTypeOnlineRegistFlag.ON);
    }
    // 2023-renew No85-2 to here
    // 2023-renew No22 from here
    /**
     * アップロードファイルの登録
     */
    private List<UploadFileModel> uploadFileModelList;
    /**
     * ファイルインデックス
     */
    private Integer fileNo;
    // 2023-renew No22 to here
}
