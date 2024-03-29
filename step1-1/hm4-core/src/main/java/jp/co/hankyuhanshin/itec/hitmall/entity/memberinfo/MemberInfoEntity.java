/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAccountingType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCardRegistType;
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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNoAntiSocialFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineLoginAdvisability;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleAnnounceWatchFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendDirectMailFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendFaxPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSexUnnecessaryAnswer;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockAnnounceWatchFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTopSaleAnnounceFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTopStockAnnounceFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTransferPaymentUseFlag;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.SequenceGenerator;
import org.seasar.doma.Table;
import org.seasar.doma.Version;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 会員クラス
 *
 * @author EntityGenerator
 * @version $Revision: 1.0 $
 */
@Entity
@Table(name = "MemberInfo")
@Data
@Component
@Scope("prototype")
public class MemberInfoEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 会員SEQ（必須）
     */
    @Column(name = "memberInfoSeq")
    @Id
    // PDR Migrate Customization from here
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "memberInfoSeq")
    // PDR Migrate Customization to here
    private Integer memberInfoSeq;

    /**
     * 会員状態（必須）
     */
    @Column(name = "memberInfoStatus")
    private HTypeMemberInfoStatus memberInfoStatus = HTypeMemberInfoStatus.ADMISSION;

    /**
     * 会員一意制約用ID
     */
    @Column(name = "memberInfoUniqueId")
    private String memberInfoUniqueId;

    /**
     * 会員ID
     */
    @Column(name = "memberInfoId")
    private String memberInfoId;

    /**
     * 会員パスワード
     */
    @Column(name = "memberInfoPassword")
    private String memberInfoPassword;

    /**
     * 会員氏名(姓)（必須）
     */
    @Column(name = "memberInfoLastName")
    private String memberInfoLastName;

    /**
     * 会員氏名(名）
     */
    @Column(name = "memberInfoFirstName")
    private String memberInfoFirstName;

    /**
     * 会員フリガナ(姓)（必須）
     */
    @Column(name = "memberInfoLastKana")
    private String memberInfoLastKana;

    /**
     * 会員フリガナ(名)
     */
    @Column(name = "memberInfoFirstKana")
    private String memberInfoFirstKana;

    /**
     * 会員性別
     */
    @Column(name = "memberInfoSex")
    private HTypeSexUnnecessaryAnswer memberInfoSex;

    /**
     * 会員生年月日（必須）
     */
    @Column(name = "memberInfoBirthday")
    private Timestamp memberInfoBirthday;

    /**
     * 会員TEL
     */
    @Column(name = "memberInfoTel")
    private String memberInfoTel;

    /**
     * 会員連絡先TEL
     */
    @Column(name = "memberInfoContactTel")
    private String memberInfoContactTel;

    /**
     * 会員住所-郵便番号（必須）
     */
    @Column(name = "memberInfoZipCode")
    private String memberInfoZipCode;

    /**
     * 会員住所-都道府県（必須）
     */
    @Column(name = "memberInfoPrefecture")
    private String memberInfoPrefecture;

    /**
     * 都道府県種別
     */
    @Column(name = "prefectureType")
    private HTypePrefectureType prefectureType;

    /**
     * 会員住所-市区郡（必須）
     */
    @Column(name = "memberInfoAddress1")
    private String memberInfoAddress1;

    /**
     * 会員住所-町村・番地
     */
    @Column(name = "memberInfoAddress2")
    private String memberInfoAddress2;

    /**
     * 会員住所-それ以降の住所
     */
    @Column(name = "memberInfoAddress3")
    private String memberInfoAddress3;

    /**
     * 会員メールアドレス
     */
    @Column(name = "memberInfoMail")
    private String memberInfoMail;

    /**
     * ショップSEQ（必須）
     */
    @Column(name = "shopSeq")
    private Integer shopSeq;

    /**
     * 端末識別情報
     */
    @Column(name = "accessUid")
    private String accessUid;

    /**
     * 更新カウンタ（必須）
     */
    @Version
    @Column(name = "versionNo")
    private Integer versionNo = 0;

    /**
     * 入会日Ymd
     */
    @Column(name = "admissionYmd")
    private String admissionYmd;

    /**
     * 退会日Ymd
     */
    @Column(name = "secessionYmd")
    private String secessionYmd;

    /**
     * メモ
     */
    @Column(name = "memo")
    private String memo;

    /**
     * 会員FAX
     */
    @Column(name = "memberInfoFax")
    private String memberInfoFax;

    /**
     * 最終ログイン日時
     */
    @Column(name = "lastLoginTime")
    private Timestamp lastLoginTime;

    /**
     * 最終ログインユーザーエージェント
     */
    @Column(name = "lastLoginUserAgent")
    private String lastLoginUserAgent;

    /**
     * 決済代行会社会員ID
     */
    @Column(name = "paymentMemberId")
    private String paymentMemberId;

    /**
     * 決済代行会社カード登録種別
     */
    @Column(name = "paymentCardRegistType")
    private HTypeCardRegistType paymentCardRegistType;

    // PDR Migrate Customization from here
    /**
     * パスワード変更要求フラグ
     */
    @Column(name = "passwordNeedChangeFlag")
    private HTypePasswordNeedChangeFlag passwordNeedChangeFlag;
    // PDR Migrate Customization to here

    /**
     * 最終ログインデバイス種別
     */
    @Column(name = "lastLoginDeviceType")
    private HTypeDeviceType lastLoginDeviceType;

    /**
     * ログイン失敗回数
     */
    @Column(name = "loginFailureCount")
    private Integer loginFailureCount;

    /**
     * アカウントロック日時
     */
    @Column(name = "accountLockTime")
    private Timestamp accountLockTime;

    /**
     * 登録日時（必須）
     */
    @Column(name = "registTime")
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    @Column(name = "updateTime")
    private Timestamp updateTime;

    // PDR Migrate Customization from here
    //    /**
    //     * PDR#11 08_データ連携（顧客情報）新規情報を会員情報に追加<br/>
    //     * <pre>
    //     * ・顧客番号を追加
    //     * ・代表者名を追加
    //     * ・会員住所-方書1を追加
    //     * ・会員住所-方書2を追加
    //     * ・顧客区分を追加
    //     * ・FAX配信希望フラグを追加
    //     * ・ダイレクトメール送信希望フラグを追加
    //     * ・休診曜日を追加
    //     * ・承認状態を追加
    //     * ・医薬品・注射針販売区分を追加
    //     * ・医療機器販売区分を追加
    //     * ・歯科専売品販売区分を追加
    //     * ・クレジット決済使用可否を追加
    //     * ・コンビニ・郵便振込使用可否を追加
    //     * ・代金引換使用可否を追加
    //     * ・口座自動引落使用可否を追加
    //     * ・月締請求使用可否を追加
    //     * ・名簿区分を追加
    //     * ・オンライン登録フラグを追加
    //     * </pre>
    //     *
    //     */
    /**
     * 顧客番号<br/>
     */
    @Column(name = "customerNo")
    private Integer customerNo;

    /**
     * 代表者名<br/>
     */
    @Column(name = "representativeName")
    private String representativeName;

    /**
     * 会員住所-方書1<br/>
     */
    @Column(name = "memberInfoAddress4")
    private String memberInfoAddress4;

    /**
     * 会員住所-方書2<br/>
     */
    @Column(name = "memberInfoAddress5")
    private String memberInfoAddress5;

    /**
     * 顧客区分<br/>
     */
    @Column(name = "businessType")
    private HTypeBusinessType businessType;

    /**
     * FAX配信希望フラグ<br/>
     */
    @Column(name = "sendFaxPermitFlag")
    private HTypeSendFaxPermitFlag sendFaxPermitFlag;

    /**
     * ダイレクトメール送信希望フラグ<br/>
     */
    @Column(name = "sendDirectMailFlag")
    private HTypeSendDirectMailFlag sendDirectMailFlag;

    /**
     * 休診曜日<br/>
     */
    @Column(name = "nonConsultationDay")
    private String nonConsultationDay;

    /**
     * 承認状態<br/>
     */
    @Column(name = "approveStatus")
    private HTypeApproveStatus approveStatus;

    /**
     * 医薬品・注射針販売区分<br/>
     */
    @Column(name = "drugSalesType")
    private HTypeDrugSalesType drugSalesType;
    /**
     * 医療機器販売区分<br/>
     */
    @Column(name = "medicalEquipmentSalesType")
    private HTypeMedicalEquipmentSalesType medicalEquipmentSalesType;

    /**
     * 歯科専売品販売区分<br/>
     */
    @Column(name = "dentalMonopolySalesType")
    private HTypeDentalMonopolySalesType dentalMonopolySalesType;

    /**
     * クレジット決済使用可否<br/>
     */
    @Column(name = "creditPaymentUseFlag")
    private HTypeCreditPaymentUseFlag creditPaymentUseFlag;

    /**
     * コンビニ・郵便振込使用可否<br/>
     */
    @Column(name = "transferPaymentUseFlag")
    private HTypeTransferPaymentUseFlag transferPaymentUseFlag;

    /**
     * 代金引換使用可否<br/>
     */
    @Column(name = "cashDeliveryUseFlag")
    private HTypeCashDeliveryUseFlag cashDeliveryUseFlag;

    /**
     * 口座自動引落使用可否<br/>
     */
    @Column(name = "directDebitUseFlag")
    private HTypeDirectDebitUseFlag directDebitUseFlag;

    /**
     * 月締請求使用可否<br/>
     */
    @Column(name = "monthlyPayUseFlag")
    private HTypeMonthlyPayUseFlag monthlyPayUseFlag;

    /**
     * 名簿区分<br/>
     */
    @Column(name = "memberListType")
    private HTypeMemberListType memberListType;

    /**
     * オンライン登録フラグ<br/>
     */
    @Column(name = "onlineRegistFlag")
    private HTypeOnlineRegistFlag onlineRegistFlag;

    /**
     * 確認書類<br/>
     */
    @Column(name = "confDocumentType")
    private HTypeConfDocumentType confDocumentType;

    /**
     * 診療内容<br/>
     */
    @Column(name = "medicalTreatmentFlag")
    private String medicalTreatmentFlag;

    /**
     * その他診療内容<br/>
     */
    @Column(name = "medicalTreatmentMemo")
    private String medicalTreatmentMemo;

    /**
     * 金属商品価格お知らせメール<br/>
     */
    @Column(name = "metalPermitFlag")
    private HTypeMetalPermitFlag metalPermitFlag;

    // 2023-renew No79 from here
    /**
     * 注文完了メール
     */
    @Column(name = "orderCompletePermitFlag")
    private HTypeOrderCompletePermitFlag orderCompletePermitFlag;

    /**
     * 発送完了メール
     */
    @Column(name = "deliveryCompletePermitFlag")
    private HTypeDeliveryCompletePermitFlag deliveryCompletePermitFlag;
    // 2023-renew No79 to here

    /**
     * 経理区分<br/>
     */
    @Column(name = "accountingType")
    private HTypeAccountingType accountingType;

    /**
     * オンラインログイン可否<br/>
     */
    @Column(name = "onlineLoginAdvisability")
    private HTypeOnlineLoginAdvisability onlineLoginAdvisability;

    /**
     * メールによるおトク情報
     */
    @Column(name = "sendMailPermitFlag")
    private HTypeSendMailPermitFlag sendMailPermitFlag;

    /**
     * メール配信開始日時
     */
    @Column(name = "sendMailStartTime")
    private Timestamp sendMailStartTime;

    /**
     * メール配信停止日時
     */
    @Column(name = "sendMailStopTime")
    private Timestamp sendMailStopTime;

    /**
     * 反社会的勢力ではないことの保証
     */
    @Column(name = "noAntiSocialFlag")
    private HTypeNoAntiSocialFlag noAntiSocialFlag;
    // PDR Migrate Customization to here

    // 2023-renew No71 from here
    /**
     *TOP画面にセール対象商品が存在する旨を通知するフラグ
     */
    @Column(name = "topSaleAnnounceFlg")
    private HTypeTopSaleAnnounceFlg topSaleAnnounceFlg = HTypeTopSaleAnnounceFlg.OFF;

    /**
     * セール通知の既読状況
     */
    @Column(name = "saleAnnounceWatchFlg")
    private HTypeSaleAnnounceWatchFlg saleAnnounceWatchFlg = HTypeSaleAnnounceWatchFlg.UNREAD;

    /**
     * トップ入荷通知フラグ
     */
    @Column(name = "topStockAnnounceFlg")
    private HTypeTopStockAnnounceFlg topStockAnnounceFlg = HTypeTopStockAnnounceFlg.OFF;

    /**
     * 入荷通知既読フラグ
     */
    @Column(name = "stockAnnounceWatchFlg")
    private HTypeStockAnnounceWatchFlg stockAnnounceWatchFlg = HTypeStockAnnounceWatchFlg.UNREAD;
    // 2023-renew No71 to here
    
    /**
     * パスワード変更要求フラグ: ON
     */
    // PDR Migrate Customization from here
    public boolean isPasswordNeedChange() {
        // PDR Migrate Customization to here
        return HTypePasswordNeedChangeFlag.ON.equals(passwordNeedChangeFlag);
    }
}
