/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.front.base.constant.ValidatorConstants;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePaymentType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.settlement.SettlementDto;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.validation.group.PaymentGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePaymentType.INSTALLMENT;
import static jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePaymentType.REVOLVING;
import static jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePaymentType.SINGLE;

/**
 * お支払い方法選択画面Model
 *
 * @author ota-s5
 */
@Data
public class PaymentModel extends AbstractModel {

    /**
     * 「納品書を希望する」ラジオボタンが「はい」
     **/
    public static final String INVOICE_ATTACHMENT_HOPE = "0";

    /**
     * 「納品書を希望する」ラジオボタンが「いいえ」
     **/
    public static final String INVOICE_ATTACHMENT_NO_HOPE = "1";

    /**
     * クーポンコード未入力正規表現（全半角スペース可）
     */
    public static final String REGEX_NON_INPUT_COUPON_CODE = "^[\\s　]*$";

    /**
     * クーポンコード入力有りエラー
     */
    public static final String MSGCD_INPUT_COUPON_CODE_ERROR = "{FRONT-ORDER-COUPONCODE}";

    // << 受注情報
    /**
     * 表示用 受注DTO
     */
    private ReceiveOrderDto dispReceiveOrderDto;

    /**
     * 注文者の姓
     */
    private String orderLastName;

    /**
     * 注文者の名
     */
    private String orderFirstName;

    /**
     * 商品金額合計
     */
    private BigDecimal goodsPriceTotal;

    /**
     * 送料
     */
    private BigDecimal carriage;

    /**
     * 消費税
     */
    private BigDecimal taxPrice;

    /**
     * お支払い金額
     */
    private BigDecimal tempOrderPrice;

    // 2023-renew No14 from here
    /**
     * 受注金額0円判定フラグ
     */
    private boolean orderPriceZero;
    // 2023-renew No14 to here
    // >>

    // << 決済方法全般
    /**
     * 決済方法リスト(画面表示用アイテム)
     */
    private List<PaymentModelItem> paymentModelItems;

    /**
     * 決済方法選択値
     */
    private String settlementMethod;

    /**
     * 決済Dtoマップ
     */
    private Map<String, SettlementDto> settlementDtoMap;
    // >>

    // << クレジット情報
    /**
     * 有効期限リスト（月）
     */
    private Map<String, String> expirationDateMonthItems;

    /**
     * 有効期限リスト（年）
     */
    private Map<String, String> expirationDateYearItems;

    /**
     * 支払区分Items
     */
    private Map<String, String> paymentTypeItems;

    /**
     * 分割回数リスト
     */
    private Map<String, String> dividedNumberItems;

    /**
     * カード情報登録状態フラグ
     */
    private boolean displayCredit = false;

    // Paygent Customization from here
    /**
     * カード照会結果
     */
    private ComResultDto comResultDto;

    /**
     * トークン
     */
    private String token;

    /**
     * カード預かり通信用トークン
     */
    private String keepToken;

    /**
     * PAYGENTトークン決済  マーチャントID
     */
    private String merchantId;

    /**
     * トークン生成鍵
     */
    @HVSpecialCharacter(groups = {PaymentGroup.class}, allowPunctuation = true)
    private String paygentTokenKey;

    /**
     * クレジットカード会社 リスト
     */
    private Map<String, String> cardBrandItems;
    // PDR Migrate Customization to here
    // >>

    // << コンビニ情報（PDRでは画面上利用なし）
    /**
     * コンビニリスト
     */
    private Map<String, String> convenienceItems;

    /**
     * 選択コンビニ名
     */
    private String selectedConveniName;
    // >>

    // << クーポン関連（PDRでは画面上利用なし）
    /**
     * クーポンコード
     */
    @NotEmpty
    @Length(min = 1, max = ValidatorConstants.LENGTH_COUPON_CODE_MAXIMUM)
    @HVSpecialCharacter(allowPunctuation = true)
    @Pattern(regexp = REGEX_NON_INPUT_COUPON_CODE, message = MSGCD_INPUT_COUPON_CODE_ERROR)
    private String couponCode;

    /**
     * クーポン名
     */
    private String couponName;

    /**
     * クーポン割引額
     */
    private BigDecimal couponDiscountPrice;

    /**
     * クーポン適用前受注金額。<br />
     * この金額に対してクーポンによる割引を実施する。
     */
    private BigDecimal preCouponDiscountOrderPrice;
    // >>

    // << 納品書関連（PDRでは画面上利用なし）
    /**
     * 納品書添付フラグ
     */
    @NotEmpty(groups = {PaymentGroup.class})
    private String invoiceAttachmentFlag;
    // >>

    /**
     * コンディション<br />
     * 利用可能な決済方法が存在するかどうか
     * <pre>
     * 選択可 不可のチェックを削除
     * </pre>
     *
     * @return true..存在する / false..存在しない
     */
    public boolean isExistSettlementMethod() {
        // PDR Migrate Customization from here
        if (CollectionUtil.isEmpty(this.paymentModelItems)) {
            return false;
        }

        boolean usabilityFlagAllOff = false;
        for (PaymentModelItem paymentModelItem : this.paymentModelItems) {
            if ((paymentModelItem.isUsabilityFlag())) {
                usabilityFlagAllOff = true;
                break;
            }
        }

        if (!usabilityFlagAllOff) {
            return false;
        }
        // PDR Migrate Customization to here

        if (this.settlementDtoMap == null || this.settlementDtoMap.isEmpty()) {
            return false;
        }

        // PDR Migrate Customization from here
        // 選択可不可チェックを削除
        return true;
        // PDR Migrate Customization to here
    }

    /**
     * コンディション<br />
     * 決済方法（画面表示用項目）が存在するかどうか
     *
     * @return true..存在する / false..存在しない
     */
    public boolean isExistPaymentModelItems() {
        return !CollectionUtils.isEmpty(this.paymentModelItems);
    }

    /**
     * 決済方法=クレジットの場合、支払い区分と支払い回数からpaymentTypeを取得します。<br/>
     *
     * @return 支払区分
     */
    public HTypePaymentType selectHTypePaymentType() {
        HTypePaymentType paymentType = null;
        if (HTypeSettlementMethodType.CREDIT == this.settlementDtoMap.get(this.settlementMethod)
                                                                     .getSettlementDetailsDto()
                                                                     .getSettlementMethodType()) {

            for (PaymentModelItem paymentModelItem : this.paymentModelItems) {
                if (this.settlementMethod.equals(paymentModelItem.getSettlementMethodValue())) {
                    if (REVOLVING.getValue().equals(paymentModelItem.getPaymentType())) {
                        paymentType = REVOLVING;
                    } else if (INSTALLMENT.getValue().equals(paymentModelItem.getPaymentType())) {
                        paymentType = INSTALLMENT;
                    } else if (SINGLE.getValue().equals(paymentModelItem.getPaymentType())) {
                        paymentType = SINGLE;
                    }
                    break;
                }
            }
        }
        return paymentType;
    }

    /**
     * クーポン割引情報の表示／非表示判定処理。<br />
     * 注文情報エリアにクーポン割引情報を表示するかどうか判定する為に利用する。
     *
     * @return クーポン割引額が１円以上の場合 true
     */
    public boolean isDisplayCouponDiscount() {
        return couponDiscountPrice.compareTo(BigDecimal.ZERO) != 0;
    }

    /**
     * クーポン使用可否判定処理。<br />
     * <pre>
     * クーポンの表示は行わないため 固定でfalseを返却
     * </pre>
     *
     * @return false
     */
    public boolean isCanUseCoupon() {
        // PDR Migrate Customization from here
        return false;
        // PDR Migrate Customization to here
    }

}
