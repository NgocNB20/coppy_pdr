/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAccountingType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCardRegistType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCashDeliveryUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeConfDocumentType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCreditPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeliveryCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDentalMonopolySalesType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDirectDebitUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDrugSalesType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMedicalEquipmentSalesType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberListType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMetalPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMonthlyPayUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeNoAntiSocialFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOnlineLoginAdvisability;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOrderCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleAnnounceWatchFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendDirectMailFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendFaxPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSexUnnecessaryAnswer;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockAnnounceWatchFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTopSaleAnnounceFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTopStockAnnounceFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTransferPaymentUseFlag;
import lombok.Data;
import org.springframework.context.annotation.Primary;
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
@Data
@Component
@Primary
@Scope("prototype")
public class MemberInfoEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 会員SEQ（必須）
     */
    private Integer memberInfoSeq;

    /**
     * 会員状態（必須）
     */
    private HTypeMemberInfoStatus memberInfoStatus = HTypeMemberInfoStatus.ADMISSION;

    /**
     * 会員一意制約用ID
     */
    private String memberInfoUniqueId;

    /**
     * 会員ID
     */
    private String memberInfoId;

    /**
     * 会員パスワード
     */
    private String memberInfoPassword;

    /**
     * 会員氏名(姓)（必須）
     */
    private String memberInfoLastName;

    /**
     * 会員氏名(名）
     */
    private String memberInfoFirstName;

    /**
     * 会員フリガナ(姓)（必須）
     */
    private String memberInfoLastKana;

    /**
     * 会員フリガナ(名)
     */
    private String memberInfoFirstKana;

    /**
     * 会員性別
     */
    private HTypeSexUnnecessaryAnswer memberInfoSex;

    /**
     * 会員生年月日（必須）
     */
    private Timestamp memberInfoBirthday;

    /**
     * 会員TEL
     */
    private String memberInfoTel;

    /**
     * 会員連絡先TEL
     */
    private String memberInfoContactTel;

    /**
     * 会員住所-郵便番号（必須）
     */
    private String memberInfoZipCode;

    /**
     * 会員住所-都道府県（必須）
     */
    private String memberInfoPrefecture;

    /**
     * 都道府県種別
     */
    private HTypePrefectureType prefectureType;

    /**
     * 会員住所-市区郡（必須）
     */
    private String memberInfoAddress1;

    /**
     * 会員住所-町村・番地
     */
    private String memberInfoAddress2;

    /**
     * 会員住所-それ以降の住所
     */
    private String memberInfoAddress3;

    /**
     * 会員メールアドレス
     */
    private String memberInfoMail;

    /**
     * ショップSEQ（必須）
     */
    private Integer shopSeq;

    /**
     * 端末識別情報
     */
    private String accessUid;

    /**
     * 更新カウンタ（必須）
     */
    private Integer versionNo = 0;

    /**
     * 入会日Ymd
     */
    private String admissionYmd;

    /**
     * 退会日Ymd
     */
    private String secessionYmd;

    /**
     * メモ
     */
    private String memo;

    /**
     * 会員FAX
     */
    private String memberInfoFax;

    /**
     * 最終ログイン日時
     */
    private Timestamp lastLoginTime;

    /**
     * 最終ログインユーザーエージェント
     */
    private String lastLoginUserAgent;

    /**
     * 決済代行会社会員ID
     */
    private String paymentMemberId;

    /**
     * 決済代行会社カード保持種別
     */
    private HTypeCardRegistType paymentCardRegistType;

    // PDR Migrate Customization from here
    /** パスワード変更要求フラグ */
    private HTypePasswordNeedChangeFlag passwordNeedChangeFlag;
    // PDR Migrate Customization to here

    /**
     * 決済代行会社カード保持種別
     */
    private HTypeDeviceType lastLoginDeviceType;

    /**
     * ログイン失敗回数
     */
    private Integer loginFailureCount;

    /**
     * 登録日時（必須）
     */
    private Timestamp accountLockTime;

    /**
     * 登録日時（必須）
     */
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
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
    private Integer customerNo;

    /**
     * 代表者名<br/>
     */
    private String representativeName;

    /**
     * 会員住所-方書1<br/>
     */
    private String memberInfoAddress4;

    /**
     * 会員住所-方書2<br/>
     */
    private String memberInfoAddress5;

    /**
     * 顧客区分<br/>
     */
    private HTypeBusinessType businessType;

    /**
     * FAX配信希望フラグ<br/>
     */
    private HTypeSendFaxPermitFlag sendFaxPermitFlag;

    /**
     * ダイレクトメール送信希望フラグ<br/>
     */
    private HTypeSendDirectMailFlag sendDirectMailFlag;

    /**
     * 休診曜日<br/>
     */
    private String nonConsultationDay;

    /**
     * 承認状態<br/>
     */
    private HTypeApproveStatus approveStatus;

    /**
     * 医薬品・注射針販売区分<br/>
     */
    private HTypeDrugSalesType drugSalesType = HTypeDrugSalesType.SALEOFF;

    /**
     * 医療機器販売区分<br/>
     */
    private HTypeMedicalEquipmentSalesType medicalEquipmentSalesType = HTypeMedicalEquipmentSalesType.SALEOFF;

    /**
     * 歯科専売品販売区分<br/>
     */
    private HTypeDentalMonopolySalesType dentalMonopolySalesType = HTypeDentalMonopolySalesType.SALEOFF;

    /**
     * クレジット決済使用可否<br/>
     */
    private HTypeCreditPaymentUseFlag creditPaymentUseFlag;

    /**
     * コンビニ・郵便振込使用可否<br/>
     */
    private HTypeTransferPaymentUseFlag transferPaymentUseFlag;

    /**
     * 代金引換使用可否<br/>
     */
    private HTypeCashDeliveryUseFlag cashDeliveryUseFlag;

    /**
     * 口座自動引落使用可否<br/>
     */
    private HTypeDirectDebitUseFlag directDebitUseFlag;

    /**
     * 月締請求使用可否<br/>
     */
    private HTypeMonthlyPayUseFlag monthlyPayUseFlag;

    /**
     * 名簿区分<br/>
     */
    private HTypeMemberListType memberListType;

    /**
     * オンライン登録フラグ<br/>
     */
    private HTypeOnlineRegistFlag onlineRegistFlag;

    /**
     * 確認書類<br/>
     */
    private HTypeConfDocumentType confDocumentType;

    /**
     * 診療内容<br/>
     */
    private String medicalTreatmentFlag;

    /**
     * その他診療内容<br/>
     */
    private String medicalTreatmentMemo;

    /**
     * 金属商品価格お知らせメール<br/>
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
     * 経理区分<br/>
     */
    private HTypeAccountingType accountingType;

    /**
     * オンラインログイン可否<br/>
     */
    private HTypeOnlineLoginAdvisability onlineLoginAdvisability;

    /**
     * メールによるおトク情報
     */
    private HTypeSendMailPermitFlag sendMailPermitFlag;

    /**
     * メール配信開始日時
     */
    private Timestamp sendMailStartTime;

    /**
     * メール配信停止日時
     */
    private Timestamp sendMailStopTime;

    /**
     * 反社会的勢力ではないことの保証
     */
    private HTypeNoAntiSocialFlag noAntiSocialFlag;
    // PDR Migrate Customization to here

    // 2023-renew No71 from here
    /**
     * TOP画面にセール対象商品が存在する旨を通知するフラグ
     */
    private HTypeTopSaleAnnounceFlg topSaleAnnounceFlg;

    /**
     * セール通知の既読状況
     */
    private HTypeSaleAnnounceWatchFlg saleAnnounceWatchFlg;

    /**
     * トップ入荷通知フラグ
     */
    private HTypeTopStockAnnounceFlg topStockAnnounceFlg;

    /**
     * 入荷通知既読フラグ
     */
    private HTypeStockAnnounceWatchFlg stockAnnounceWatchFlg;
    // 2023-renew No71 to here

    /** パスワード変更要求フラグ: ON */
    // PDR Migrate Customization from here
    public boolean isPasswordNeedChange() {
        // PDR Migrate Customization to here
        return HTypePasswordNeedChangeFlag.ON.equals(passwordNeedChangeFlag);
    }
}
