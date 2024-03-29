/*
 * Project Name : HIT-MALL
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.annotation.csv.CsvColumn;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAccountingType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCardRegistType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCashDeliveryUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeConfDocumentType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCreditPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDentalMonopolySalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDirectDebitUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDrugSalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeLoginSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMedicalEquipmentSalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMedicalTreatmentFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberListType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberRank;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberRankAutoFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMetalPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMonthlyPayUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineLoginAdvisability;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendDirectMailFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendFaxPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSex;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTransferPaymentUseFlag;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 会員情報CSVDTO
 *
 * @author DtoGenerator
 */
@Entity
@Data
@Component
@Scope("prototype")
public class MemberCsvDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 会員SEQ
     */
    @CsvColumn(order = 10, columnLabel = "会員SEQ")
    private Integer memberInfoSeq;

    /**
     * 会員状態
     */
    @CsvColumn(order = 20, columnLabel = "会員状態", enumOutputType = "value")
    private HTypeMemberInfoStatus memberInfoStatus;

    /**
     * 会員ID
     */
    @CsvColumn(order = 40, columnLabel = "会員ID")
    private String memberInfoId;

    /**
     * 最終ログイン日時
     */
    @CsvColumn(order = 50, columnLabel = "最終ログイン日時", dateFormat = "yyyy/MM/dd HH:mm:ss")
    private Timestamp lastLoginTime;

    /**
     * 最終ログインユーザーエージェント
     */
    @CsvColumn(order = 80, columnLabel = "最終ログインユーザーエージェント")
    private String lastLoginUserAgent;

    /**
     * 会員氏名(姓)
     */
    @CsvColumn(order = 90, columnLabel = "会員姓")
    private String memberInfoLastName;

    /**
     * 会員氏名(名）
     */
    @CsvColumn(order = 100, columnLabel = "会員名")
    private String memberInfoFirstName;

    /**
     * 会員フリガナ(姓)
     */
    @CsvColumn(order = 110, columnLabel = "会員フリガナ姓")
    private String memberInfoLastKana;

    /**
     * 会員フリガナ(名)
     */
    @CsvColumn(order = 120, columnLabel = "会員フリガナ名")
    private String memberInfoFirstKana;

    /**
     * 会員性別
     */
    @CsvColumn(order = 130, columnLabel = "性別", enumOutputType = "value")
    private HTypeSex memberInfoSex;

    /**
     * 会員生年月日
     */
    @CsvColumn(order = 140, columnLabel = "生年月日", dateFormat = "yyyy/MM/dd")
    private Timestamp memberInfoBirthday;

    /**
     * 会員TEL
     */
    @CsvColumn(order = 150, columnLabel = "電話番号")
    private String memberInfoTel;

    /**
     * 会員連絡先TEL
     */
    @CsvColumn(order = 160, columnLabel = "連絡先電話番号")
    private String memberInfoContactTel;

    /**
     * 会員FAX
     */
    @CsvColumn(order = 170, columnLabel = "FAX")
    private String memberInfoFax;

    /**
     * 会員住所-郵便番号
     */
    @CsvColumn(order = 180, columnLabel = "郵便番号")
    private String memberInfoZipCode;

    /**
     * 会員住所-都道府県
     */
    @CsvColumn(order = 190, columnLabel = "都道府県")
    private String memberInfoPrefecture;

    /**
     * 会員住所-市区郡
     */
    @CsvColumn(order = 200, columnLabel = "市区郡")
    private String memberInfoAddress1;

    /**
     * 会員住所-町村・番地
     */
    @CsvColumn(order = 210, columnLabel = "町村_番地")
    private String memberInfoAddress2;

    /**
     * 会員住所-マンション・建物名
     */
    @CsvColumn(order = 220, columnLabel = "マンション_建物名")
    private String memberInfoAddress3;

    /**
     * 入会日Ymd
     */
    @CsvColumn(order = 230, columnLabel = "入会日")
    private String admissionYmd;

    /**
     * 退会日Ymd
     */
    @CsvColumn(order = 240, columnLabel = "退会日")
    private String secessionYmd;

    /**
     * 決済代行会社会員ID
     */
    @CsvColumn(order = 250, columnLabel = "決済代行会社会員ID")
    private String paymentMemberId;

    /**
     * 決済代行会社カード保持種別
     */
    @CsvColumn(order = 260, columnLabel = "決済代行会社カード保持種別", enumOutputType = "value")
    private HTypeCardRegistType paymentCardRegistType;

    /**
     * 会員パスワード
     */
    @CsvColumn(order = 270, columnLabel = "パスワード")
    private String memberInfoPassword;

    /**
     * 登録日時
     */
    @CsvColumn(order = 290, columnLabel = "登録日時", dateFormat = "yyyy/MM/dd HH:mm:ss")
    private Timestamp registTime;

    /**
     * 更新日時
     */
    @CsvColumn(order = 300, columnLabel = "更新日時", dateFormat = "yyyy/MM/dd HH:mm:ss")
    private Timestamp updateTime;

    // PDR Migrate Customization from here
    //    /**
    //     * PDR#11 08_データ連携（顧客情報）会員情報の項目追加・変更<br/>
    //     * 会員情報CSVDTOクラス<br/>
    //     *
    //     */
    /** 顧客番号 */
    @CsvColumn(order = 310, columnLabel = "顧客番号")
    private Integer customerNo;

    /** 会員サイト種別（必須） */
    @CsvColumn(order = 320, columnLabel = "受付サイト", enumOutputType = "value")
    private HTypeSiteType memberInfoSiteType;

    /** 最終ログインサイト種別 */
    @CsvColumn(order = 330, columnLabel = "最終ログインサイト種別", enumOutputType = "value")
    private HTypeLoginSiteType lastLoginSiteType;

    /** 最終ログインデバイス種別 */
    @CsvColumn(order = 340, columnLabel = "最終ログインデバイス種別", enumOutputType = "value")
    private HTypeDeviceType lastLoginDeviceType;

    /** 会員住所-方書1 */
    @CsvColumn(order = 350, columnLabel = "方書1")
    private String memberInfoAddress4;

    /** 会員住所-方書2 */
    @CsvColumn(order = 360, columnLabel = "方書2")
    private String memberInfoAddress5;

    /** メールによるおトク情報 */
    @CsvColumn(order = 370, columnLabel = "メールによるおトク情報", enumOutputType = "value")
    private HTypeSendMailPermitFlag sendMailPermitFlag;

    /** FAXによるおトク情報 */
    @CsvColumn(order = 380, columnLabel = "FAXによるおトク情報", enumOutputType = "value")
    private HTypeSendFaxPermitFlag sendFaxPermitFlag;

    /** DMによるおトク情報 */
    @CsvColumn(order = 390, columnLabel = "DMによるおトク情報", enumOutputType = "value")
    private HTypeSendDirectMailFlag sendDirectMailFlag;

    /** 購入金額累計 */
    @CsvColumn(order = 400, columnLabel = "購入金額累計")
    private BigDecimal purchasePriceTotal;

    /** 購入金額累計(前回) */
    @CsvColumn(order = 410, columnLabel = "購入金額累計(前回)")
    private BigDecimal purchasePriceTotalPrev;

    /** 会員ランク自動設定フラグ */
    @CsvColumn(order = 420, columnLabel = "会員ランク自動設定", enumOutputType = "value")
    private HTypeMemberRankAutoFlag memberRankAutoFlag;

    /** 会員ランク */
    @CsvColumn(order = 430, columnLabel = "会員ランク", enumOutputType = "value")
    private HTypeMemberRank memberRank;

    /** ポイント有効期限 */
    @CsvColumn(order = 440, columnLabel = "ポイント有効期限")
    private Timestamp pointLimitDate;

    /** 獲得（仮） */
    @CsvColumn(order = 450, columnLabel = "獲得（仮）")
    private BigDecimal temmporaryPointSum;

    /** 獲得（確定） */
    @CsvColumn(order = 460, columnLabel = "獲得（確定）")
    private BigDecimal acquisitionPointSum;

    /** 利用（出荷前） */
    @CsvColumn(order = 470, columnLabel = "利用（出荷前）")
    private BigDecimal usePointBeforeShipmentSum;

    /** 返還（出荷前） */
    @CsvColumn(order = 480, columnLabel = "返還（出荷前）")
    private BigDecimal returnPointBeforeShipmentSum;

    /** 利用（出荷後） */
    @CsvColumn(order = 490, columnLabel = "利用（出荷後）")
    private BigDecimal usePointAfterShipmentSum;

    /** 返還（出荷後） */
    @CsvColumn(order = 500, columnLabel = "返還（出荷後）")
    private BigDecimal returnPointAfterShipmentSum;

    /** 調整 */
    @CsvColumn(order = 510, columnLabel = "調整")
    private BigDecimal adjustPointSum;

    /** 確定後無効化 */
    @CsvColumn(order = 520, columnLabel = "確定後無効化")
    private BigDecimal returnGoodsInvalidPointSum;

    /** 期限切れ無効化 */
    @CsvColumn(order = 530, columnLabel = "期限切れ無効化")
    private BigDecimal overTermInvalidPointSum;

    /** 退会無効化 */
    @CsvColumn(order = 540, columnLabel = "退会無効化")
    private BigDecimal secessionInvalidPointSum;

    /** 仮ポイント */
    @CsvColumn(order = 550, columnLabel = "仮ポイント")
    private BigDecimal pointSum;

    /** 確定ポイント */
    @CsvColumn(order = 560, columnLabel = "確定ポイント")
    private BigDecimal activatePointSum;

    /** 合計ポイント */
    @CsvColumn(order = 570, columnLabel = "合計ポイント")
    private BigDecimal totalPointSum;

    /** 顧客区分 */
    @CsvColumn(order = 580, columnLabel = "顧客区分", enumOutputType = "value")
    private HTypeBusinessType businessType;

    /** 代表者名 */
    @CsvColumn(order = 590, columnLabel = "代表者名")
    private String representativeName;

    /** 休診曜日 */
    @CsvColumn(order = 600, columnLabel = "休診曜日")
    private String nonConsultationDay;

    /** 医薬品・注射針販売区分 */
    @CsvColumn(order = 610, columnLabel = "医薬品・注射針販売区分", enumOutputType = "value")
    private HTypeDrugSalesType drugSalesType;

    /** 医療機器販売区分 */
    @CsvColumn(order = 620, columnLabel = "医療機器販売区分", enumOutputType = "value")
    private HTypeMedicalEquipmentSalesType medicalEquipmentSalesType;

    /** 歯科専売品区分 */
    @CsvColumn(order = 630, columnLabel = "歯科専売品区分", enumOutputType = "value")
    private HTypeDentalMonopolySalesType dentalMonopolySalesType;

    /** クレジット決済使用可否 */
    @CsvColumn(order = 640, columnLabel = "クレジット決済使用可否", enumOutputType = "value")
    private HTypeCreditPaymentUseFlag creditPaymentUseFlag;

    /** コンビニ・郵便振込使用可否 */
    @CsvColumn(order = 650, columnLabel = "コンビニ・郵便振込使用可否", enumOutputType = "value")
    private HTypeTransferPaymentUseFlag transferPaymentUseFlag;

    /** 代金引換使用可否 */
    @CsvColumn(order = 660, columnLabel = "代金引換使用可否", enumOutputType = "value")
    private HTypeCashDeliveryUseFlag cashDeliveryUseFlag;

    /** 口座自動引落使用可否 */
    @CsvColumn(order = 670, columnLabel = "口座自動引落使用可否", enumOutputType = "value")
    private HTypeDirectDebitUseFlag directDebitUseFlag;

    /** 月締請求使用可否 */
    @CsvColumn(order = 680, columnLabel = "月締請求使用可否", enumOutputType = "value")
    private HTypeMonthlyPayUseFlag monthlyPayUseFlag;

    /** 名簿区分 */
    @CsvColumn(order = 690, columnLabel = "名簿区分", enumOutputType = "value")
    private HTypeMemberListType memberListType;

    /** オンライン登録フラグ */
    @CsvColumn(order = 700, columnLabel = "オンライン登録フラグ", enumOutputType = "value")
    private HTypeOnlineRegistFlag onlineRegistFlag;

    /** 診療項目1 */
    @CsvColumn(order = 710, columnLabel = "診療内容１", enumOutputType = "value")
    private HTypeMedicalTreatmentFlag medicalTreatment1;

    /** 診療項目2 */
    @CsvColumn(order = 720, columnLabel = "診療内容２", enumOutputType = "value")
    private HTypeMedicalTreatmentFlag medicalTreatment2;

    /** 診療項目3 */
    @CsvColumn(order = 730, columnLabel = "診療内容３", enumOutputType = "value")
    private HTypeMedicalTreatmentFlag medicalTreatment3;

    /** 診療項目4 */
    @CsvColumn(order = 740, columnLabel = "診療内容４", enumOutputType = "value")
    private HTypeMedicalTreatmentFlag medicalTreatment4;

    /** 診療項目5 */
    @CsvColumn(order = 750, columnLabel = "診療内容５", enumOutputType = "value")
    private HTypeMedicalTreatmentFlag medicalTreatment5;

    /** 診療項目6 */
    @CsvColumn(order = 760, columnLabel = "診療内容６", enumOutputType = "value")
    private HTypeMedicalTreatmentFlag medicalTreatment6;

    /** 診療項目7 */
    @CsvColumn(order = 770, columnLabel = "診療内容７", enumOutputType = "value")
    private HTypeMedicalTreatmentFlag medicalTreatment7;

    /** 診療項目8 */
    @CsvColumn(order = 780, columnLabel = "診療内容８", enumOutputType = "value")
    private HTypeMedicalTreatmentFlag medicalTreatment8;

    /** 診療項目9 */
    @CsvColumn(order = 790, columnLabel = "診療内容９", enumOutputType = "value")
    private HTypeMedicalTreatmentFlag medicalTreatment9;

    /** 診療項目10 */
    @CsvColumn(order = 800, columnLabel = "診療内容１０", enumOutputType = "value")
    private HTypeMedicalTreatmentFlag medicalTreatment10;

    /** その他診療内容 */
    @CsvColumn(order = 810, columnLabel = "その他診療内容")
    private String medicalTreatmentMemo;

    /** 金属商品価格お知らせメール */
    @CsvColumn(order = 820, columnLabel = "金属商品価格お知らせメール", enumOutputType = "value")
    private HTypeMetalPermitFlag metalPermitFlag;

    /** 確認書類 */
    @CsvColumn(order = 830, columnLabel = "確認書類", enumOutputType = "value")
    private HTypeConfDocumentType confDocumentType;

    /** 経理区分 */
    @CsvColumn(order = 840, columnLabel = "経理区分", enumOutputType = "value")
    private HTypeAccountingType accountingType;

    /** オンラインログイン可否 */
    @CsvColumn(order = 850, columnLabel = "オンラインログイン可否", enumOutputType = "value")
    private HTypeOnlineLoginAdvisability onlineLoginAdvisability;

    /** パスワード変更要求フラグ */
    private HTypePasswordNeedChangeFlag passwordNeedChangeFlag;
    // PDR Migrate Customization to here
}
