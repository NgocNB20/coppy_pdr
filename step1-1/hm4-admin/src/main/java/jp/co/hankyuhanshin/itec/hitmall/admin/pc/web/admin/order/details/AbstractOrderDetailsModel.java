/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCNumber;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCancelFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCouponLimitTargetType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGmoReleaseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeInvoiceAttachmentFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailRequired;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderAgeType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderSex;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePaymentType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReceiverDateDesignationFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRepeatPurchaseType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSend;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeWaitingFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.conveni.ConvenienceEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.ConvenienceUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.IntegerConversionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringConversionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 受注詳細ページ<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AbstractOrderDetailsModel extends AbstractModel {

    /**
     * @return the memberFlg
     */
    public boolean isMemberFlg() {
        return memberInfoSeq != 0;
    }

    /**
     * 受注コード（必須）
     */
    private String orderCode;

    /**
     * 受注SEQ（必須）
     */
    private Integer orderSeq;

    /**
     * マルチペイメント通信結果エラーメッセージ
     */
    private String mulPayErrMsg;

    /**
     * コンビニリスト
     */
    private List<Map<String, String>> convenienceItems;

    /**
     * コンビニリスト(すべて)
     */
    private List<ConvenienceEntity> convenienceAllList;

    // ////// 受注インデックス

    /**
     * 督促メール送信済みフラグ
     */
    private HTypeSend reminderSentFlag;

    /**
     * 期限切れメール送信済みフラグ
     */
    private HTypeSend expiredSentFlag;

    /**
     * 注文保留理由
     */
    private String orderWaitingMemo;

    // //////// 受注サマリー

    /**
     * 受注日時（必須）
     */
    private Timestamp orderTime;

    /**
     * キャンセル日時
     */
    protected Timestamp cancelTime;

    /**
     * キャンセル可能日
     */
    private Timestamp cancelableDate;

    /**
     * キャンセルフラグ（必須）
     */
    private HTypeCancelFlag cancelFlag = HTypeCancelFlag.OFF;

    /**
     * 保留中フラグ（受注インデックスから設定）
     */
    private HTypeWaitingFlag waitingFlag = HTypeWaitingFlag.OFF;

    /**
     * 受注状態（必須）
     */
    private HTypeOrderStatus orderStatus;

    /**
     * 受注金額（必須）
     */
    private BigDecimal orderPrice = BigDecimal.ZERO;

    // PDR Migrate Customization from here
    /**
     * 受注金額
     * ※プロモーション値引を除く
     */
    private BigDecimal orderPriceExceptPromotionDiscount = BigDecimal.ZERO;
    // PDR Migrate Customization to here

    /**
     * 入金累計（必須）
     */
    private BigDecimal receiptPriceTotal = BigDecimal.ZERO;

    /**
     * 受注サイト種別（必須）
     */
    private HTypeSiteType orderSiteType;

    /**
     * 決済関連メール要否フラグ
     */
    private HTypeMailRequired settlementMailRequired = HTypeMailRequired.NO_NEED;

    // //////// 受注お客様情報

    /**
     * 会員SEQ（必須）
     */
    private Integer memberInfoSeq;

    /**
     * ご注文主氏名(姓)（必須）
     */
    private String orderLastName;

    /**
     * ご注文主氏名(名)
     */
    private String orderFirstName;

    /**
     * ご注文主フリガナ(姓)（必須）
     */
    private String orderLastKana;

    /**
     * ご注文主フリガナ(名)
     */
    private String orderFirstKana;

    /**
     * ご注文主住所-郵便番号（必須）
     */
    private String orderZipCode;

    /**
     * 都道府県種別
     */
    private HTypePrefectureType prefectureType;

    /**
     * ご注文主住所-都道府県（必須）
     */
    private String orderPrefecture;

    /**
     * ご注文主住所-市区郡（必須）
     */
    private String orderAddress1;

    /**
     * ご注文主住所-町村・番地（必須）
     */
    private String orderAddress2;

    /**
     * ご注文主住所-それ以降の住所
     */
    private String orderAddress3;

    /**
     * ご注文主電話番号（必須）
     */
    private String orderTel;

    /**
     * ご注文主連絡先電話番号
     */
    private String orderContactTel;

    /**
     * ご注文主メールアドレス（必須）
     */
    private String orderMail;

    /**
     * ご注文主生年月日
     */
    private Timestamp orderBirthday;

    /**
     * ご注文主年齢
     */
    private String orderAge;

    /**
     * ご注文主年代
     */
    private HTypeOrderAgeType orderAgeType;

    /**
     * ご注文主性別
     */
    private HTypeOrderSex orderSex = HTypeOrderSex.UNKNOWN;

    // //////// 受注配送
    /**
     * 納品書添付フラグ
     */
    private HTypeInvoiceAttachmentFlag invoiceAttachmentFlag = HTypeInvoiceAttachmentFlag.OFF;

    /**
     * 受注お届け先アイテム
     */
    @Valid
    private OrderReceiverUpdateItem orderReceiverItem;

    /**
     * 受注お届け先インデックス
     */
    private int orderReceiverIndex;

    /**
     * @return お届け先No
     */
    public String getReceiverNo() {
        return StringConversionUtil.toString(orderReceiverIndex + 1);
    }

    /**
     * お届け希望日指定フラグ
     **/
    private HTypeReceiverDateDesignationFlag receiverDateDesignationFlag = HTypeReceiverDateDesignationFlag.ON;

    // //////// 受注請求

    /**
     * 請求状態（必須）
     */
    private HTypeBillStatus billStatus = HTypeBillStatus.BILL_NO_CLAIM;

    /**
     * 請求日時（必須）
     */
    private Timestamp billTime;

    /**
     * 請求金額（必須）
     */
    private BigDecimal billPrice = BigDecimal.ZERO;

    // PDR Migrate Customization from here
    /**
     * 請求金額
     * ※プロモーション値引を除く
     */
    private BigDecimal billPriceExceptPromotionDiscount = BigDecimal.ZERO;
    // PDR Migrate Customization to here

    /**
     * 異常フラグ（必須）
     */
    protected HTypeEmergencyFlag emergencyFlag = HTypeEmergencyFlag.OFF;

    /**
     * GMO連携解除フラグ
     */
    private HTypeGmoReleaseFlag gmoReleaseFlag = HTypeGmoReleaseFlag.NORMAL;

    /**
     * 支払期限日
     */
    private Timestamp paymentTimeLimitDate;

    // //////// 受注決済

    /**
     * 決済方法SEQ
     */
    private Integer settlementMethodSeq;

    /**
     * 決済方法名
     */
    private String settlementMethodName;

    /**
     * カードブランド表示名PC
     */
    private String cardBrandDisplayPc;

    /**
     * 決済種別
     */
    protected HTypeSettlementMethodType settlementMethodType;

    /**
     * 請求種別（必須）
     */
    private HTypeBillType billType;

    /**
     * リピート購入種別（必須）
     */
    private HTypeRepeatPurchaseType repeatPurchaseType = HTypeRepeatPurchaseType.GUEST;

    /**
     * 商品金額合計(税抜き)（必須）
     */
    private BigDecimal goodsPriceTotal = BigDecimal.ZERO;

    /**
     * 商品金額合計（税込み）
     */
    private BigDecimal postTaxGoodsPriceTotal = BigDecimal.ZERO;

    /**
     * 決済手数料（必須）
     */
    private BigDecimal settlementCommission = BigDecimal.ZERO;

    /**
     * 送料
     */
    private BigDecimal carriage = BigDecimal.ZERO;

    /**
     * その他金額合計
     */
    private BigDecimal othersPriceTotal = BigDecimal.ZERO;

    /**
     * オーソリ期限日（決済日付＋オーソリ保持期間）
     */
    private Timestamp authoryLimitDate;

    // //////// 受注入金

    /**
     * 入金日時
     */
    private Timestamp receiptTime;

    /**
     * 入金金額（必須）
     */
    private BigDecimal receiptPrice = BigDecimal.ZERO;

    // //////// マルチペイメント請求情報
    /**
     * ﾏﾙﾁﾍﾟｲﾒﾝﾄ請求SEQ
     */
    private Integer mulPayBillSeq;
    ;

    /**
     * 決済方法
     */
    private String payType;

    /**
     * トランザクション種別
     */
    private String tranType;

    /**
     * オーダーID
     */
    private String orderId;

    /**
     * 取引ID
     */
    private String accessId;

    /**
     * 取引パスワード
     */
    private String accessPass;

    /**
     * 処理区分
     */
    private String jobCd;

    /**
     * 支払方法
     */
    private String method;

    /**
     * 支払回数
     */
    private Integer payTimes;

    /**
     * カード登録連番モード
     */
    private String seqMode;

    /**
     * カード登録連番
     */
    private Integer cardSeq;

    /**
     * 利用金額
     */
    private BigDecimal amount;

    /**
     * 税送料
     */
    private BigDecimal tax;

    /**
     * 3Dセキュア使用フラグ
     */
    private String tdFlag;

    /**
     * ACS 呼出判定
     */
    private String acs;

    /**
     * 仕向先コード
     */
    private String forward;

    /**
     * 承認番号
     */
    private String approve;

    /**
     * トランザクション ID
     */
    private String tranId;

    /**
     * 決済日付
     */
    private String tranDate;

    /**
     * 支払先コンビニコード
     */
    private String convenience;

    /**
     * 確認番号
     */
    private String confNo;

    /**
     * 受付番号
     */
    private String receiptNo;

    /**
     * 支払期限日時
     */
    private String paymentTerm;

    /**
     * お客様番号
     */
    private String custId;

    /**
     * 収納機関番号
     */
    private String bkCode;

    /**
     * 暗号化決済番号
     */
    private String encryptReceiptNo;

    /**
     * メールアドレス
     */
    private String mailAddress;

    /**
     * EDY 注文番号
     */
    private String edyOrderNo;

    /**
     * SUICA 注文番号
     */
    private String suicaOrderNo;

    /**
     * エラーコード
     */
    private String errCode;

    /**
     * エラー情報
     */
    private String errInfo;

    /**
     * コンビニ名称
     */
    private String conveniName;

    // //////// 受注商品

    /**
     * 商品消費税種別（必須）
     */
    private HTypeGoodsTaxType goodsTaxType = HTypeGoodsTaxType.OUT_TAX;

    /**
     * 商品金額(税抜き)（表示の為のダミー）
     */
    private BigDecimal orderGoodsPrice = BigDecimal.ZERO;

    /**
     * 商品金額(税込み)（表示の為のダミー）
     */
    private BigDecimal postTaxOrderGoodsPrice = BigDecimal.ZERO;

    /**
     * 商品グループ名
     */
    private String goodsGroupName;

    // //////// 受注追加料金

    /**
     * 追加料金
     */
    @HCNumber
    private OrderAdditionalChargeItem orderAdditionalChargeItem;

    /**
     * 追加料金リスト
     */
    @Valid
    private List<OrderAdditionalChargeItem> orderAdditionalChargeItems;

    // //////// 受注メモ
    /**
     * メモ
     */
    private String memo;

    // //////// 表示用

    /**
     * 入金状態（受注金額及び入金累計金額により設定）
     */
    private String paymentStatus;

    /**
     * 商品合計点数
     */
    private BigDecimal orderGoodsCountTotal;

    /**
     * 消費税
     */
    private BigDecimal taxPrice = BigDecimal.ZERO;

    /**
     * 標準税率対象金額
     */
    private BigDecimal standardTaxTargetPrice = BigDecimal.ZERO;

    /**
     * 標準税率消費税
     */
    private BigDecimal standardTaxPrice = BigDecimal.ZERO;

    /**
     * 軽減税率対象金額
     */
    private BigDecimal reducedTaxTargetPrice = BigDecimal.ZERO;

    /**
     * 軽減税率消費税
     */
    private BigDecimal reducedTaxPrice = BigDecimal.ZERO;

    /**
     * クーポン名
     */
    private String couponName;

    /**
     * クーポン割引額
     */
    private BigDecimal couponDiscountPrice;

    /**
     * 適用クーポン名
     */
    private String applyCouponName;

    /**
     * 適用クーポンID
     */
    private String applyCouponId;

    /**
     * クーポンキャンセルフラグ
     */
    private HTypeCouponLimitTargetType couponLimitTargetType;

    /**
     * クーポン割引情報の表示／非表示判定処理。<br />
     *
     * <pre>
     * 注文情報エリアにクーポン割引情報を表示するかどうか判定する為に利用する。
     * </pre>
     *
     * @return true..クーポン名が設定されている場合
     */
    public boolean isDisplayCouponDiscount() {
        return StringUtils.isNotEmpty(couponName);
    }

    /**
     * 適用クーポン情報の表示／非表示判定処理。<br />
     *
     * <pre>
     * 適用クーポン情報を表示するかどうか判定する為に利用する。
     * </pre>
     *
     * @return クーポンIDがnullでない場合 true
     */
    public boolean isDisplayApplyCoupon() {
        return applyCouponId != null;
    }

    /**
     * @return 有効期限有無
     */
    public boolean isPaymentTimeLimitDateFlag() {
        return paymentTimeLimitDate != null;
    }

    /**
     * @return 有効期限（画面表示用）
     */
    public String getPaymentTermDsp() {
        String paymentTermDsp = "";
        if (paymentTerm != null && paymentTerm.length() >= 8) {
            paymentTermDsp = paymentTerm.substring(0, 4) + "/" + paymentTerm.substring(4, 6) + "/"
                             + paymentTerm.substring(6, 8);
        }
        return paymentTermDsp;
    }

    /**
     * コンビニ受付番号（4桁-7桁）
     *
     * @return コンビニ受付番号（4桁-7桁）
     */
    public String getReceiptNoWithHyphen() {
        String ret = "";
        if (receiptNo != null && receiptNo.length() >= 11) {
            return receiptNo.substring(0, 4) + "-" + receiptNo.substring(4, 11);
        }
        return ret;
    }

    /**
     * マルチペイメント通信結果エラー有無
     *
     * @return true 通信結果エラーあり
     */
    public boolean isMulPayErr() {
        return (mulPayErrMsg != null);
    }

    /**
     * コンビニ受付番号なし
     *
     * @return true コンビニ受付番号がnullまたは空
     */
    public boolean isNullReceiptNo() {
        return (receiptNo == null || "".equals(receiptNo));
    }

    /**
     * 支払回数指定あり
     *
     * @return true 支払回数指定あり
     */
    public boolean isPayTimesSetting() {
        if (payTimes != null && payTimes > 0) {
            return true;
        }
        return false;
    }

    /**
     * 決済方法がコンビニ
     *
     * @return the settlementMethodType is CONVENIENCE or else
     */
    public boolean isConveni() {
        return HTypeSettlementMethodType.CONVENIENCE.equals(settlementMethodType);
    }

    /**
     * 決済方法がコンビニの表示パターン１（ローソン・ファミリーマート・サークルＫサンクス・ミニストップ）
     *
     * @return コンビニ決済判定の結果
     */
    public boolean isConveni1() {
        ConvenienceUtility convenienceUtility = ApplicationContextUtility.getBean(ConvenienceUtility.class);
        return convenienceUtility.isConveni1(convenience);
    }

    /**
     * 決済方法がコンビニの表示パターン2（デイリーヤマザキ・セイコーマート・スリーエフ）
     *
     * @return コンビニ決済判定の結果
     */
    public boolean isConveni2() {
        ConvenienceUtility convenienceUtility = ApplicationContextUtility.getBean(ConvenienceUtility.class);
        return convenienceUtility.isConveni2(convenience);
    }

    /**
     * 決済方法がコンビニの表示パターン3（セブンイレブン）
     *
     * @return コンビニ決済判定の結果
     */
    public boolean isConveni3() {
        ConvenienceUtility convenienceUtility = ApplicationContextUtility.getBean(ConvenienceUtility.class);
        return convenienceUtility.isConveni3(convenience);
    }

    /**
     * 決済方法がコンビニの表示パターン４
     *
     * @return コンビニ決済判定の結果
     */
    public boolean isConveni4() {
        ConvenienceUtility convenienceUtility = ApplicationContextUtility.getBean(ConvenienceUtility.class);
        return convenienceUtility.isConveni4(convenience);
    }

    /**
     * 決済方法がコンビニの表示パターン５
     *
     * @return コンビニ決済判定の結果
     */
    public boolean isConveni5() {
        ConvenienceUtility convenienceUtility = ApplicationContextUtility.getBean(ConvenienceUtility.class);
        return convenienceUtility.isConveni5(convenience);
    }

    /**
     * 決済方法がクレジット
     *
     * @return the settlementMethodType is CREDIT or else
     */
    public boolean isCredit() {
        return HTypeSettlementMethodType.CREDIT.equals(settlementMethodType);
    }

    /**
     * 決済方法がPay-easy
     *
     * @return the settlementMethodType is CREDIT or else
     */
    public boolean isPayEasy() {
        return HTypeSettlementMethodType.PAY_EASY.equals(settlementMethodType);
    }

    /**
     * 決済方法がその他
     *
     * @return the settlementMethodType is else
     */
    public boolean isElse() {
        if (!HTypeSettlementMethodType.CREDIT.equals(settlementMethodType)
            && !HTypeSettlementMethodType.CONVENIENCE.equals(settlementMethodType)
            && !HTypeSettlementMethodType.PAY_EASY.equals(settlementMethodType)) {
            return true;
        }
        return false;
    }

    /**
     * @return the cancelFlag is ON or OFF
     */
    public boolean isCancel() {
        return HTypeCancelFlag.ON.equals(cancelFlag);
    }

    /**
     * @return the waitingFlag is ON or OFF
     */
    public boolean isWaiting() {
        return HTypeWaitingFlag.ON.equals(waitingFlag);
    }

    /**
     * @return the emergencyFlag is ON or OFF
     */
    public boolean isEmergency() {
        return HTypeEmergencyFlag.ON.equals(emergencyFlag);
    }

    /**
     * GMO連携解除状態フラグ
     *
     * @return true:GMO連携解除を表示 :GMO連携解除を表示しない
     */
    public boolean isRelease() {
        return HTypeGmoReleaseFlag.RELEASE.equals(gmoReleaseFlag);
    }

    /**
     * @return 追加料金エンティティ数
     */
    public BigDecimal getOrderAdditionalChargeItemsCount() {
        if (orderAdditionalChargeItems == null || orderAdditionalChargeItems.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(orderAdditionalChargeItems.size());
    }

    /**
     * @return 追加料金あり
     */
    public boolean isOrderAdditionalCharge() {
        if (orderAdditionalChargeItems == null || orderAdditionalChargeItems.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * @return tranId is not null?
     */
    public boolean isNullTranId() {
        if (tranId == null || tranId.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * @return orderId is not null?
     */
    public boolean isNullOrderId() {
        if (orderId == null || orderId.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * ※メソッド名付いての注意書き:
     * isMailRequired() にすると teeda が処理を行わないため、isMailRequiredOn() に変更しています。
     *
     * @return the mailRequired
     */
    public boolean isMailRequiredOn() {
        return HTypeMailRequired.REQUIRED == settlementMailRequired;
    }

    /**
     * 再オーソリ期限関連項目を表示するか否かを判定する<br/>
     *
     * <pre>
     * クレジット仮売上有効期限切れが近い受注を対象に表示。
     * オーソリ期限日 - オーソリ期限メール送信開始期間 <= 処理日 <= オーソリ期限日
     * </pre>
     *
     * @return true:表示する false:表示しない
     */
    public boolean isAuthoryLimit() {
        if (authoryLimitDate == null) {
            return false;
        }

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 現在日の取得
        Timestamp currentDate = dateUtility.getCurrentDate();
        // メール送信開始期間（日）
        int mailSendStartPeriod = IntegerConversionUtil.toInteger(
                        PropertiesUtil.getSystemPropertiesValue("mail.send.start.period"));
        return dateUtility.isOpen(dateUtility.getAmountDayTimestamp(mailSendStartPeriod, false, authoryLimitDate),
                                  authoryLimitDate, currentDate
                                 );
    }

    /**
     * @return the method
     */
    public String getMethodDsp() {
        if (method == null || "".equals(method)
            || EnumTypeUtil.getEnumFromValue(HTypePaymentType.class, method) == null) {
            return "";
        }
        return EnumTypeUtil.getEnumFromValue(HTypePaymentType.class, method).getLabel();
    }

    /**
     * ご注文主年齢が存在するか判定<br/>
     *
     * @return true:存在する false:存在しない
     */
    public boolean isDispOrderAge() {
        return StringUtil.isNotEmpty(orderAge);
    }
}
