// CHECKSTYLE:OFF
// ファイルサイズオーバーを許容：単純に項目が多いため。
// CHECKSTYLE:ON
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details.validation.detailsupdate.group.OnceOrderCancelGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCZenkaku;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCZenkakuKana;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVMailAddress;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCancelFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCarrierType;
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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRepeatPurchaseType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalesFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSend;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeWaitingFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.conveni.ConvenienceEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.ConvenienceUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.constant.RegularExpressionsConstants;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.IntegerConversionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 受注詳細修正ページ<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DetailsUpdateModel extends AbstractModel {

    /**
     * 受注サマリ（OrderSummaryEntity）の変更箇所リスト
     */
    private List<String> modifiedOrderSummaryList;

    /**
     * 受注インデックス（OrderIndexEntity）の変更箇所リスト
     */
    private List<String> modifiedOrderIndexList;

    /**
     * 受注お客様（OrderPersonEntity）の変更箇所リスト
     */
    private List<String> modifiedOrderPersonList;

    /**
     * 受注商品（OrderGoodsEntity）の変更箇所リスト
     */
    private List<List<String>> modifiedOrderGoodsList;

    /**
     * 受注配送（OrderdeliveryEntity）の変更箇所リスト
     */
    private List<String> modifiedOrderDeliveryList;

    /**
     * 受注配送（DeliveryMethodEntity）の変更箇所リスト
     */
    private List<String> modifiedDeliveryMethod;

    /**
     * 受注決済（OrderSettlementEntity）の変更箇所リスト
     */
    private List<String> modifiedOrderSettlementList;

    /**
     * 受注決済詳細（SettlementMethodEntity）の変更箇所リスト
     */
    private List<String> modifiedSettlementMethodList;

    /**
     * 受注入金（OrderReceiptOfMoneyEntity）の変更箇所リスト
     */
    private List<String> modifiedReceiptMoneyList;

    /**
     * 受注請求（OrderBillEntity）の変更箇所リスト
     */
    private List<String> modifiedOrderBillList;

    /**
     * マルチペイメント請求（MulPayBillEntity）の変更箇所リスト
     */
    private List<String> modifiedMulPayBillList;

    /**
     * 受注追加料金（OrderAdditionalChargeEntity）の変更箇所リスト
     */
    private List<List<String>> modifiedAdditionalChargeList;

    /**
     * 受注メモ（OrderMemoEntity）の変更箇所リスト
     */
    private List<String> modifiedMemoList;

    /**
     * 差分スタイルクラス
     */
    private String DIFF_STYLE_CLASS = "diff";

    /**
     * デフォルトスタイルクラス
     */
    private String DEFAULT_STYLE_CLASS = "";

    /**
     * ダイナミックプロパティ<br/>
     * 商品数量が変更されてたら修正用のスタイルシート名を返します。<br/>
     * 上記項目はEntityには無く、ユーティリティで判定する
     *
     * @return スタイルシート名
     */
    public String getDiffGoodsCountTotalStyleClass() {
        OrderUtility orderUtility = ApplicationContextUtility.getBean(OrderUtility.class);

        if (originalReceiveOrder != null && modifiedReceiveOrder != null) {
            BigDecimal originalGoodsCountTotal = orderUtility.getGoodsCountTotal(originalReceiveOrder);
            BigDecimal modifyGoodsCountTotal = orderUtility.getGoodsCountTotal(modifiedReceiveOrder);
            if (originalGoodsCountTotal.compareTo(modifyGoodsCountTotal) != 0) {
                return DIFF_STYLE_CLASS;
            }
        }
        return DEFAULT_STYLE_CLASS;
    }

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * 受注詳細修正
     */
    public static final String FLASH_UPDATE_MODEL = "redirectDetailsUpdateModel";

    /**
     * 商品検索モデル
     */
    public static final String FLASH_GOODS_SEARCH_MODEL = "goodsSearchModel";

    /**
     * 追加料金モデル
     */
    public static final String FLASH_ADDITIONAL_CHARGE_MODEL = "additionalChargeModel";

    /**
     * 受注コード
     */
    public static final String FLASH_ORDERCODE = "orderCode";

    /**
     * 商品詳細リスト
     */
    private List<GoodsDetailsDto> goodsDetailsList;

    /**
     * 保留中フラグ（受注インデックスから設定-ダミー）
     */
    private boolean updateWaitingFlag = false;

    /**
     * GMO連携解除フラグ
     */
    private boolean cancelOfCooperation = false;

    /**
     * GMO連携解除チェックボックス表示フラグ
     *
     * @return true:表示 false:非表示
     */
    public boolean isCancelOfCooperationView() {
        return orderStatus == HTypeOrderStatus.SHIPMENT_COMPLETION;
    }

    /**
     * GMO連結解除状態フラグ
     *
     * @return true:GMO連携解除を表示 :GMO連携解除を表示しない
     */
    public boolean isRelease() {
        return HTypeGmoReleaseFlag.RELEASE.equals(gmoReleaseFlag);
    }

    /**
     * クレジット受注 GMO通信エラーフラグ<br />
     * GMOに取引状態を参照した際、通信エラーが発生した場合に使用するフラグ<br />
     * エラー発生あり : TRUE<br />
     * エラー発生なし : FALSE<br />
     */
    private boolean mulPayBillPaymentExceptionFlag = false;

    /**
     * 決済手数料ダイアログ表示フラグ<br/>
     * true..表示する<br/>
     */
    private boolean commissionDialogDisplay = false;

    /**
     * 決済手数料ダイアログ選択フラグ<br/>
     * true..選択済み<br/>
     */
    private boolean commissionSelected = false;

    /**
     * 送料ダイアログ表示フラグ<br/>
     * true..表示する<br/>
     */
    private boolean carriageDialogDisplay = false;

    /**
     * 送料ダイアログ選択フラグ<br/>
     * true..選択済み<br/>
     */
    private boolean carriageSelected = false;

    /**
     * 決済Dtoマップ
     */
    private Map<String, SettlementDto> settlementDtoMap;

    /**
     * @return the mulPayBillPaymentExceptionFlag
     */
    public boolean isMulPayBillPaymentException() {
        return this.mulPayBillPaymentExceptionFlag;
    }

    /**
     * 決済方法変更が可能か不可能かを制御する<br />
     * コンディションメソッド
     *
     * @return true 変更可能
     * false 変更不可能
     */
    public boolean isSettlementUpdatable() {
        // mulPayBillPaymentExceptionFlagがTRUEの場合
        // 決済方法は変更不可
        if (isMulPayBillPaymentException()) {
            return false;
        }
        return true;
    }

    /**
     * 画面項目の編集が可能か不可能かを制御する<br />
     * コンディションメソッド<br />
     * <br />
     * 適応箇所：お客様情報<br />
     * お届け先情報<br />
     * 配送情報<br />
     * 受注商品(「商品の追加」、「その他料金の追加」、「商品の削除」ボタン含む)<br />
     * 「確認」ボタン<br />
     *
     * @return true 編集可能
     * false 編集不可能
     */
    public boolean isOrderUpdatable() {
        // emergencyFlag・mulPayBillPaymentExceptionFlagのどれか一つでもTRUEの場合,対象の画面項目は編集不可
        if (isEmergency() || isMulPayBillPaymentException()) {
            return false;
        }
        return true;
    }

    /**
     * 手数料
     */
    private String orgCommissionDisp;

    /**
     * 送料
     */
    private String orgCarriageDisp;

    /**
     * 送料ダイアログ表示フラグ
     */
    private boolean dispCarriageDialog;

    /**
     * クーポン
     */
    private boolean dispCouponCancelDialog;

    /**
     * 検索条件
     */
    private OrderSearchConditionDto conditionDto;

    /**
     * チェックメッセージ
     */
    private String checkErrorMessage;

    /**
     * 受注コード（必須）
     */
    private String orderCode;

    /**
     * マルチペイメント通信結果エラーメッセージ
     */
    private String mulPayErrMsg;

    /**
     * コンビニリスト
     */
    private Map<String, String> convenienceItems;

    /**
     * コンビニリスト(すべて)
     */
    private List<ConvenienceEntity> convenienceAllList;

    // //////// 受注インデックス
    /**
     * 処理日時
     */
    private Timestamp processTime;

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

    /**
     * 受注履歴連番（必須）
     */
    private Integer orderVersionNo;

    /**
     * 受注日時（必須）
     */
    private Timestamp orderTime;

    /**
     * 売上日時
     */
    private Timestamp salesTime;

    /**
     * キャンセル日時
     */
    private Timestamp cancelTime;

    /**
     * キャンセル可能日
     */
    private Timestamp cancelableDate;

    /**
     * 売上フラグ（必須）
     */
    private HTypeSalesFlag salesFlag = HTypeSalesFlag.OFF;

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

    /**
     * 入金累計（必須）
     */
    private BigDecimal receiptPriceTotal = BigDecimal.ZERO;

    /**
     * 受注サイト種別（必須）
     */
    private HTypeSiteType orderSiteType;

    /**
     * キャリア種別（必須）
     */
    private HTypeCarrierType carrierType;

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
     * ご注文主氏名(姓)（入力）
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @Length(min = 1, max = 16, groups = {ConfirmGroup.class})
    @HCZenkaku
    private String orderLastName;

    /**
     * ご注文主氏名(名)（入力）
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @Length(min = 1, max = 16, groups = {ConfirmGroup.class})
    @HCZenkaku
    private String orderFirstName;

    /**
     * ご注文主フリガナ(姓)（入力）
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @Length(min = 1, max = 40, groups = {ConfirmGroup.class})
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_KANA_REGEX,
             message = "{HZenkakuKanaValidator.INVALID_detail}", groups = {ConfirmGroup.class})
    @HCZenkakuKana
    private String orderLastKana;

    /**
     * ご注文主フリガナ(名)（入力）
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @Length(min = 1, max = 40, groups = {ConfirmGroup.class})
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_KANA_REGEX,
             message = "{HZenkakuKanaValidator.INVALID_detail}", groups = {ConfirmGroup.class})
    @HCZenkakuKana
    private String orderFirstKana;

    /**
     * ご注文主住所-郵便番号（入力）
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @Pattern(regexp = RegularExpressionsConstants.ZIP_CODE_REGEX, message = "{HZipCodeValidator.INVALID_detail}",
             groups = {ConfirmGroup.class})
    @Length(min = 1, max = 7, groups = {ConfirmGroup.class})
    @HCHankaku
    private String orderZipCode;

    /**
     * ご注文主住所-都道府県（入力）
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}", groups = {ConfirmGroup.class})
    private String orderPrefecture;

    /**
     * 都道府県アイテムリスト
     */
    private Map<String, String> orderPrefectureItems;

    /**
     * ご注文主住所-市区郡（入力）
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @Length(min = 1, max = 50, groups = {ConfirmGroup.class})
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_REGEX, message = "{HZenkakuValidator.INVALID_detail}",
             groups = {ConfirmGroup.class})
    @HCZenkaku
    private String orderAddress1;

    /**
     * ご注文主住所-町村・番地（入力）
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @Length(min = 1, max = 100, groups = {ConfirmGroup.class})
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_REGEX, message = "{HZenkakuValidator.INVALID_detail}",
             groups = {ConfirmGroup.class})
    @HCZenkaku
    private String orderAddress2;

    /**
     * ご注文主住所-それ以降の住所（入力）
     */
    @Length(min = 0, max = 200, groups = {ConfirmGroup.class})
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_REGEX, message = "{HZenkakuValidator.INVALID_detail}",
             groups = {ConfirmGroup.class})
    @HCZenkaku
    private String orderAddress3;

    /**
     * ご注文主電話番号（入力）
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @Pattern(regexp = RegularExpressionsConstants.TELEPHONE_NUMBER_REGEX,
             message = "{HTelephoneNumberValidator.INVALID_detail}", groups = {ConfirmGroup.class})
    @Length(min = 1, max = 11, groups = {ConfirmGroup.class})
    @HCHankaku
    private String orderTel;

    /**
     * ご注文主連絡先電話番号（入力）
     */
    @Pattern(regexp = RegularExpressionsConstants.TELEPHONE_NUMBER_REGEX,
             message = "{HTelephoneNumberValidator.INVALID_detail}", groups = {ConfirmGroup.class})
    @Length(min = 1, max = 11, groups = {ConfirmGroup.class})
    @HCHankaku
    private String orderContactTel;

    /**
     * ご注文主メールアドレス（入力）
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @HVMailAddress(groups = {ConfirmGroup.class})
    @Length(min = 1, max = 160, groups = {ConfirmGroup.class})
    @HCHankaku
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

    /**
     * ご注文主生年月日（入力）
     */
    @HVDate(groups = {ConfirmGroup.class})
    @HCDate
    private String updateOrderBirthday;

    /**
     * ご注文主性別（入力ダミー）
     */
    private String updateOrderSex;

    /**
     * ご注文主性別アイテムリスト
     */
    private Map<String, String> updateOrderSexItems;

    // //////// 受注配送
    /**
     * 納品書添付フラグ（必須）
     */
    private HTypeInvoiceAttachmentFlag invoiceAttachmentFlag = HTypeInvoiceAttachmentFlag.OFF;

    /**
     * 受注お届け先アイテム
     */
    @Valid
    private OrderReceiverUpdateItem orderReceiverItem;

    /**
     * 都道府県アイテムリスト
     */
    private Map<String, String> receiverPrefectureItems;

    /**
     * 納品書添付フラグ（入力））
     */
    @NotEmpty(message = "{AOX001006W}", groups = {ConfirmGroup.class})
    private String updateInvoiceAttachmentFlag;

    /**
     * 納品書添付フラグアイテムリスト
     */
    private Map<String, String> updateInvoiceAttachmentFlagItems;

    // //////// 受注請求

    /**
     * 請求状態（必須）
     */
    private HTypeBillStatus billStatus = HTypeBillStatus.BILL_NO_CLAIM;

    /**
     * 請求日時（必須）
     */
    @HCDate(pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp billTime;

    /**
     * 請求金額（必須）
     */
    private BigDecimal billPrice = BigDecimal.ZERO;

    /**
     * 異常フラグ（必須）
     */
    private HTypeEmergencyFlag emergencyFlag = HTypeEmergencyFlag.OFF;

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
    private HTypeSettlementMethodType settlementMethodType;

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
     * 商品金額合計(税込み)（必須）
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
     * 支払先コンビニコード(旧コンビニ分含めたもの)
     */
    private String convenienceOld;

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

    /**
     * 決済方法SEQ（入力）
     */

    @NotEmpty(message = "{AOX001005W}", groups = {ConfirmGroup.class})
    private String updateSettlementMethodSeq;

    /**
     * 決済方法SEQ（変更前）
     */
    private String originalSettlementMethodSeq;

    /**
     * 決済方法SEQアイテムリスト<br/>
     */
    private Map<String, String> updateSettlementMethodSeqItems;

    /**
     * クレジット決済の決済IDリスト（カンマ区切り）
     */
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String creditSettlementMethodSeqList;

    /**
     * コンビニ決済リスト（カンマ区切り）
     */
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String conveniSettlementMethodSeqList;

    /**
     * Pay-easy決済リスト（カンマ区切り）
     */
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String payeasySettlementMethodSeqList;

    /**
     * メール要否設定可能決済リスト（カンマ区切り）
     */
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String mailRequiredSettlementMethodSeqList;

    /**
     * メール送信要否
     */
    private String updateMailRequired;

    // //////// 受注商品
    /**
     * 受注商品DTOリスト
     */
    @Valid
    private List<OrderGoodsUpdateItem> orderGoodsUpdateItems;

    /**
     * 受注商品Index
     */
    private int orderGoodsUpdateIndex;

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
     * オプションバリデータの為のカウンター
     */
    private int validatorCount = 0;

    /**
     * 商品グループ名
     */
    private String goodsGroupName;

    /**
     * 追加料金
     */
    private OrderAdditionalChargeItem orderAdditionalChargeItem;

    /**
     * 追加料金リスト
     */
    private List<OrderAdditionalChargeItem> orderAdditionalChargeItems;

    // //////// 受注メモ

    /**
     * メモ（入力）
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class, OnceOrderCancelGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class, OnceOrderCancelGroup.class})
    @Length(min = 0, max = 2000, groups = {ConfirmGroup.class, OnceOrderCancelGroup.class})
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

    /////////// ポップアップ表示用

    /**
     * 商品合計金額
     */
    private BigDecimal goodsPriceTotalDisp;

    /**
     * クーポン適用金額
     */
    private BigDecimal couponDiscountLowerOrderPriceDisp;

    /**
     * クーポン割引金額
     */
    private BigDecimal couponDiscountPriceDisp;

    /**
     * クーポン対象商品番号
     */
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String couponTargetGoodsCode;

    /**
     * クーポン対象商品名
     */
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String couponTargetGoodsName;

    /**
     * クーポン対象全商品フラグ
     */
    private boolean couponTargetGoodsIsAllFlg = false;

    /**
     * クーポンキャンセルダイアログ表示フラグ<br/>
     */
    private boolean couponCancelDialogDisplay = false;

    /**
     * クーポン対象商品キャンセルメッセージフラグ<br/>
     */
    private boolean couponTargetGoodsCancelMessgeFlg = false;

    /**
     * クーポン適用金額メッセージフラグ<br/>
     */
    private boolean couponDiscountLowerOrderPriceMessgeFlg = false;

    /**
     * クーポン割引情報の表示／非表示判定処理。<br />
     *
     * <pre>
     * 注文情報エリアにクーポン割引情報を表示するかどうか判定する為に利用する。
     * displayCouponDiscount を hidden で持たせている。（受注キャンセルダイアログのクーポンキャンセルに使用）
     * </pre>
     *
     * @return true..クーポン名が設定されている場合
     */
    public boolean isDisplayCouponDiscount() {
        return StringUtils.isNotEmpty(couponName);
    }

    /**
     * クーポン使用フラグ
     * javascriptの判定用のため、Stringで保持する
     */
    private String useCouponFlg;

    /**
     * クーポンキャンセルフラグ
     */
    private HTypeCouponLimitTargetType couponLimitTargetType;

    /**
     * クーポンキャンセルフラグ value値
     */
    private String couponLimitTargetTypeValue;

    /**
     * クーポン使用フラグ<br/>
     *
     * @return "true"..クーポン使用 / "false"..クーポン未使用
     */
    public String getUseCouponFlg() {

        boolean useCouponFlg = isDisplayCouponDiscount();

        return String.valueOf(useCouponFlg);
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
     * 最新のキャンセル状態を他画面に渡す為のダミー
     */
    private boolean cancel;

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
     * コンストラクタ
     */
    public DetailsUpdateModel() {
        // 変更箇所特定オブジェクト名 設定
        diffObjectNameOrderGoods = ApplicationContextUtility.getBean(OrderGoodsEntity.class).getClass().getSimpleName();
    }

    /**
     * 受注商品
     */
    public final String diffObjectNameOrderGoods;

    /**
     * 受注DTO（修正前）
     */
    private ReceiveOrderDto originalReceiveOrder;

    /**
     * 受注DTO（修正後）
     */
    private ReceiveOrderDto modifiedReceiveOrder;

    /**
     * modifiedReceiveOrder に処理履歴詳細画面で取得した受注情報が入っている場合は true
     *
     * <pre>
     * 受注詳細修正確認画面　⇒　処理履歴詳細画面　⇒　ブラウザバックで修正実行
     * とすると処理履歴の内容で更新してしまう為、フラグでチェックする
     * </pre>
     */
    private boolean historyDetailsFlag;

    /**
     * 注文チェックメッセージDTO
     */
    private OrderMessageDto orderMessageDto;

    /**
     * 異常値の項目の表示スタイル
     */
    private String errStyleClass;

    /**
     * クーポン割引額
     */
    private String diffCouponDiscountPriceClass;

    /**
     * 決済方法がコンビニで決済方法に変更なし
     *
     * @return true 決済方法がコンビニで決済方法に変更なし
     */
    public boolean isConveniAndNoDiff() {
        if (HTypeSettlementMethodType.CONVENIENCE.equals(settlementMethodType)
            && HTypeSettlementMethodType.CONVENIENCE == originalReceiveOrder.getOrderSettlementEntity()
                                                                            .getSettlementMethodType()
            && originalReceiveOrder.getMulPayBillEntity()
                                   .getConvenience()
                                   .equals(modifiedReceiveOrder.getMulPayBillEntity().getConvenience())) {
            return true;
        }
        return false;
    }

    /**
     * 決済方法がペイジーで決済方法に変更なし
     *
     * @return true 決済方法がペイジーで決済方法に変更なし
     */
    public boolean isPayeasyAndNoDiff() {
        if (HTypeSettlementMethodType.PAY_EASY.equals(settlementMethodType)
            && HTypeSettlementMethodType.PAY_EASY == originalReceiveOrder.getOrderSettlementEntity()
                                                                         .getSettlementMethodType()) {
            return true;
        }
        return false;
    }

    /**
     * 決済方法がペイジーで決済方法に変更あり
     *
     * @return true 決済方法がペイジーで決済方法に変更あり
     */
    public boolean isPayeasyAndDiff() {
        if (HTypeSettlementMethodType.PAY_EASY.equals(settlementMethodType)
            && HTypeSettlementMethodType.PAY_EASY != originalReceiveOrder.getOrderSettlementEntity()
                                                                         .getSettlementMethodType()) {
            return true;
        }
        return false;
    }

    /**
     * 決済方法がその他で決済方法に変更なし
     *
     * @return true 決済方法がペイジーで決済方法に変更なし
     */
    public boolean isElseAndNoDiff() {
        if (!HTypeSettlementMethodType.CREDIT.equals(settlementMethodType)
            && !HTypeSettlementMethodType.CONVENIENCE.equals(settlementMethodType)
            && !HTypeSettlementMethodType.PAY_EASY.equals(settlementMethodType)) {
            if (settlementMethodType == originalReceiveOrder.getOrderSettlementEntity().getSettlementMethodType()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the cancelFlag is ON or OFF
     */
    public boolean isStateCancel() {
        if (cancelTime != null && cancelTime.compareTo(processTime) <= 0) {
            return true;
        }
        return false;
    }

    /**
     * キャンセル以降の処理の場合、true を返却する
     *
     * @return the cancelFlag is ON or OFF
     */
    public boolean isStateAfterCancel() {
        if (cancelTime != null && cancelTime.compareTo(processTime) < 0) {
            return true;
        }
        return false;
    }

    /**
     * 決済手数料計算フラグ<br/>
     * true..計算する<br/>
     */
    private boolean commissionCalcFlag;

    /**
     * 送料計算フラグ<br/>
     * true..計算する<br/>
     */
    private boolean carriageCalcFlag;

    /**
     * 利用ポイント
     */
    private String diffUsePointClass;

    /**
     * 獲得ポイント
     */
    private String diffAcquisitionPointClass;

    /**
     * 確定ポイント
     */
    private String diffActivatePointClass;

    /**
     * ポイント確定状態
     */
    private String diffPointActivateFlagClass;

    /**
     * 送料ダイアログを表示するか否か<br/>
     *
     * @return true..表示する
     */
    public boolean isDispCarriageDialog() {

        if (carriageDialogDisplay && !carriageSelected && (!commissionDialogDisplay || commissionSelected)) {
            return true;
        }
        return false;
    }

    /**
     * クーポンダイアログを表示するか否か<br/>
     *
     * @return rue..表示する
     */
    public boolean isDispCouponCancelDialog() {
        if (couponCancelDialogDisplay && !isDispCarriageDialog()) {
            return true;
        }
        return false;
    }

    /**
     * クーポンダイアログを表示するか否か<br/>
     *
     * @return rue..表示する
     */
    public boolean isDispCouponInvalid() {
        if (HTypeCouponLimitTargetType.OFF.equals(couponLimitTargetType)) {
            return true;
        }
        return false;
    }

    /**
     * To decide whether to display member information or not
     */
    private boolean displayMemberInfo;

    /**
     * In case of order cancellation and modification is possible or not
     */
    private boolean orderCancelModifyPossible;

    /**
     * ご注文主年齢が存在するか判定<br/>
     *
     * @return true:存在する false:存在しない
     */
    public boolean isDispOrderAge() {
        return StringUtil.isNotEmpty(orderAge);
    }

    /**
     * クーポン割引情報の表示／非表示判定処理を行う。<br />
     *
     * <pre>
     * 注文情報エリアにクーポン割引情報を表示するかどうか判定する為に利用する。
     * </pre>
     *
     * @return クーポン割引額が１円以上の場合 true
     */
    public boolean isDisplayCouponPriceDiscount() {
        return couponDiscountPrice.compareTo(BigDecimal.ZERO) != 0;
    }

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DetailsUpdateModel.class);

    /**
     * 決済方法が督促メール等送信要
     *
     * @return settlementMailRequired
     */
    public boolean isSettlementMailRequiredOn() {
        if (this.modifiedReceiveOrder.getSettlementMethodEntity() != null) {
            return (HTypeMailRequired.REQUIRED == this.modifiedReceiveOrder.getSettlementMethodEntity()
                                                                           .getSettlementMailRequired());
        }
        return false;
    }

    /**
     * 完了画面への遷移有無判定<br/>
     *
     * <pre>
     * 注文チェックでエラーが発生した場合、完了画面へ遷移させないための条件制御。
     * エラー系のメッセージが存在する場合は「false」、存在しない場合は「true」とする。
     * </pre>
     *
     * @return true...遷移許可 / false...遷移不可
     */
    public boolean isNextComplete() {
        return !orderMessageDto.hasErrorMessage();
    }

    /**
     * @return 督促メール送信済みフラグ
     */
    public HTypeSend getReminderSentFlag() {
        return isReset() ? HTypeSend.UNSENT : reminderSentFlag;
    }

    /**
     * @return 期限切れメール送信済みフラグ
     */
    public HTypeSend getExpiredSentFlag() {
        return isReset() ? HTypeSend.UNSENT : expiredSentFlag;
    }

    /**
     * 督促/期限切れメール送信状態のリセット有無を検査する
     *
     * <pre>
     * </pre>
     *
     * @return trueの場合はリセット、それ以外は不要
     */
    private boolean isReset() {
        if (ObjectUtils.isEmpty(originalReceiveOrder) || ObjectUtils.isEmpty(modifiedReceiveOrder)) {
            return false;
        }
        OrderBillEntity original = originalReceiveOrder.getOrderBillEntity();
        OrderBillEntity modified = modifiedReceiveOrder.getOrderBillEntity();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("督促/期限切れメール送信リセット　決済SEQ:修正前[%s]/修正後[%s] 請求金額:修正前[%s]/修正後[%s]",
                                       original.getSettlementMethodSeq(), modified.getSettlementMethodSeq(),
                                       original.getBillPrice(), modified.getBillPrice()
                                      ));
        }
        return !original.getSettlementMethodSeq().equals(modified.getSettlementMethodSeq())
               || original.getBillPrice().compareTo(modified.getBillPrice()) != 0;
    }

    /**
     * コンビニリスト
     */
    private List<Map<String, String>> convenienceItemsConfirm;

    /**
     * GMO連携解除状態フラグ
     *
     * @return true:GMO連携解除を表示 :GMO連携解除を表示しない
     */
    public boolean isReleaseConfirm() {
        if (cancelOfCooperation) {
            return false;
        }
        return HTypeGmoReleaseFlag.RELEASE.equals(gmoReleaseFlag);
    }

    /**
     * 都道府県種別
     */
    private HTypePrefectureType prefectureType;
}
