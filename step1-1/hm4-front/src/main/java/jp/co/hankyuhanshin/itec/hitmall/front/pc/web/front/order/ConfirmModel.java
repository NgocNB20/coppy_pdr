/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.OrderMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.DeliveryNowItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.DependingOnReceiptItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.ReserveDeliveryByPeriodItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.ReserveDeliveryItem;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

import static jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType.CREDIT;

/**
 * 注文内容確認画面Model
 *
 * @author ota-s5
 */
@Data
public class ConfirmModel extends AbstractModel {

    // PDR Migrate Customization from here
    /**
     * 送料無料基準注文金額
     */
    public static final String FREESHIPPING_STANDARD_AMOUNT = "freeshipping.standard.amount";
    // PDR Migrate Customization to here

    /**
     * コンストラクタ
     **/
    public ConfirmModel() {
        // モデルクリア処理のためprivate変数に直アクセスしない
        setOrderMessageDto(new OrderMessageDto());
        // 2023-renew No14 from here
        setPaymentModel(new PaymentModel());
        // 2023-renew No14 to here
    }

    /**  ***********　Confirm Start　*********** **/
    // 2023-renew No14 from here
    /**
     * お支払い方法選択画面Model
     * ※確認画面の初期表示時に決済情報を作成する過程で利用する
     *   当Modelの初期化で全て初期化できるように、当Model内に保持しておく
     */
    private PaymentModel paymentModel;
    // 2023-renew No14 to here
    // PDR Migrate Customization from here
    /**
     * 表示用 受注DTO
     */
    private ReceiveOrderDto dispReceiveOrderDto;

    /**
     * 受注用 受注DTOリスト
     */
    private List<ReceiveOrderDto> receiveOrderDtoList;
    // PDR Migrate Customization to here

    /**
     * 注文メッセージDto
     */
    private OrderMessageDto orderMessageDto;

    /**
     * トークン<br/>
     * 注文確認画面の表示内容が最新であるかチェックするために使用（注文確認画面にhiddenとしてセット）
     */
    private String orderConfirmToken;

    /**
     * トークン（比較用）<br/>
     * 注文確認画面の表示内容が最新であるかチェックするために使用
     */
    private String diffOrderConfirmToken;

    /**
     * 商品詳細Dtoリスト
     */
    private List<GoodsDetailsDto> goodsDetailDtoList;

    // PDR Migrate Customization from here
    // << 今すぐお届け分（最短発送）
    /**
     * 今すぐお届け分
     */
    private List<DeliveryNowItem> deliveryNowItems;

    /**
     * 今すぐお届け：お届け時期
     */
    private String deliveryDateByDeliveryNow;

    /**
     * 今すぐお届け：お届け時期 曜日
     */
    private String deliveryDayOfTheWeekByDeliveryNow;

    /**
     * 今すぐお届け：お届け時間帯
     */
    private String receiverTimeZoneByDeliveryNow;

    /**
     * 今すぐお届け：注文合計(税抜)
     */
    private BigDecimal allGoodsPriceOutTaxByDeliveryNow;

    /**
     * 今すぐお届け：値引き価格
     */
    private BigDecimal goodsDiscountPriceByDeliveryNow;

    /**
     * 今すぐお届け：送料
     */
    private BigDecimal carriageByDeliveryNow;

    /**
     * 今すぐお届け：消費税
     */
    private BigDecimal totalTaxByDeliveryNow;

    /**
     * 今すぐお届け：支払い合計金額
     */
    private BigDecimal totalPriceInTaxByDeliveryNow;
    // >>

    // << 取りおき商品（セールde予約）
    /**
     * 期間別 取りおき商品情報リスト
     */
    private List<ReserveDeliveryByPeriodItem> reserveDeliveryByPeriodItems;
    // >>

    // << 入荷次第お届け商品
    /**
     * 入庫次第お届け分
     */
    private List<DependingOnReceiptItem> dependingOnReceiptItems;

    /**
     * 入荷次第お届け：注文合計(税抜)
     */
    private BigDecimal allGoodsPriceOutTaxByDependingOnReceipt;

    /**
     * 入荷次第お届け：値引き価格
     */
    private BigDecimal goodsDiscountPriceByDependingOnReceipt;

    /**
     * 入荷次第お届け：送料
     */
    private BigDecimal carriageByDependingOnReceipt;

    /**
     * 入荷次第お届け：消費税
     */
    private BigDecimal totalTaxByDependingOnReceipt;

    /**
     * 入荷次第お届け：お支払い合計(税込)
     */
    private BigDecimal totalPriceInTaxByDependingOnReceipt;
    // >>
    // PDR Migrate Customization to here

    // << お客様情報
    /**
     * 事業所名
     **/
    private String senderName;

    /**
     * メールアドレス
     **/
    private String senderMail;

    /**
     * 電話番号
     **/
    private String senderTel;

    /**
     * 郵便番号（左側）
     **/
    private String senderZipCode1;

    /**
     * 郵便番号（右側）
     **/
    private String senderZipCode2;

    /**
     * お客様情報-住所
     **/
    private String senderAddress;
    // >>

    // << お届け先情報
    /**
     * 事業所名
     */
    private String receiverLastName;

    /**
     * 事業所名フリガナ
     */
    private String receiverLastKana;

    /**
     * 代表者名
     */
    private String receiverFirstName;

    /**
     * 電話番号
     */
    private String receiverTel;

    /**
     * 郵便番号（左側）
     */
    private String receiverZipCode1;

    /**
     * 郵便番号（右側）
     */
    private String receiverZipCode2;

    /**
     * 市区郡
     */
    private String receiverAddress1;

    /**
     * 町村・番地
     */
    private String receiverAddress2;

    /**
     * マンション・建物名 ＋ 部屋番号
     */
    private String receiverAddress3;

    /**
     * お届け先種別
     */
    private boolean receiverHomeFlag;
    // >>

    // << 配送方法
    /**
     * 配送方法
     **/
    private String deliveryMethodName;

    /**
     * 送料無料の基準金額
     */
    private BigDecimal standardAmount =
                    new BigDecimal(PropertiesUtil.getSystemPropertiesValue(FREESHIPPING_STANDARD_AMOUNT));

    // PDR Migrate Customization from here
    /**
     * 配送方法:今すぐお届け商品情報リスト（画面上利用なし）
     */
    private List<DeliveryNowItem> deliveryMethodDeliveryNowItems;

    /**
     * 配送方法:取りおき商品情報リスト（画面上利用なし）
     */
    private List<ReserveDeliveryItem> deliveryMethodReserveDeliveryItems;

    /**
     * 配送方法:入荷次第お届け商品情報リスト（画面上利用なし）
     */
    private List<DependingOnReceiptItem> deliveryMethodDependingOnReceiptItems;
    // PDR Migrate Customization to here
    // >>

    // << お支払い方法
    /**
     * 決済方法名
     */
    private String settlementMethodName;

    /**
     * カード番号
     */
    private String cardNo;

    /**
     * カード有効期限（年）
     */
    private String expirationDateYear;

    /**
     * カード有効期限（月）
     */
    private String expirationDateMonth;

    /**
     * 支払い区分
     */
    private String paymentType;

    // PDR Migrate Customization from here
    /**
     * クレジットカード会社
     */
    private String cardBrand;

    /**
     * 決済方法表示名
     */
    private String settlementMethodDisplayName;
    // PDR Migrate Customization to here
    // 2023-renew No14 from here
    /**
     * 決済方法SEQ
     */
    private Integer settlementMethodSeq;
    // 2023-renew No14 to here
    // >>

    // PDR Migrate Customization from here

    /**
     * コンディション<br/>
     * 今すぐお届け情報表示判定
     *
     * @return true...表示
     */
    public boolean isDispDeliveryNowItems() {
        return !CollectionUtil.isEmpty(this.deliveryNowItems);
    }

    /**
     * コンディション<br/>
     * 取りおき情報表示判定
     *
     * @return true...表示
     */
    public boolean isDispReserveDeliveryByPeriodItems() {
        return !CollectionUtil.isEmpty(this.reserveDeliveryByPeriodItems);
    }

    /**
     * コンディション<br/>
     * 入荷次第お届け表示判定
     *
     * @return true...表示
     */
    public boolean isDispDependingOnReceiptItems() {
        return !CollectionUtil.isEmpty(this.dependingOnReceiptItems);
    }

    /**
     * コンディション<br/>
     * 今すぐお届け情報の値引き価格表示判定
     *
     * @return true...表示
     */
    public boolean isDispGoodsDiscountPriceByDeliveryNow() {
        return this.goodsDiscountPriceByDeliveryNow != null
               && BigDecimal.ZERO.compareTo(this.goodsDiscountPriceByDeliveryNow) < 0;
    }

    /**
     * コンディション<br/>
     * 入荷次第お届けの値引き価格表示判定
     *
     * @return true...表示
     */
    public boolean isDispGoodsDiscountPriceByDependingOnReceipt() {
        return this.goodsDiscountPriceByDependingOnReceipt != null
               && BigDecimal.ZERO.compareTo(this.goodsDiscountPriceByDependingOnReceipt) < 0;
    }

    /**
     * コンディション<br/>
     * 配送方法:今すぐお届け情報表示判定
     *
     * @return true...表示
     */
    public boolean isDispDeliveryMethodDeliveryNowItems() {
        return !CollectionUtil.isEmpty(this.deliveryMethodDeliveryNowItems);
    }

    /**
     * コンディション<br/>
     * 配送方法:取りおき情報表示判定
     *
     * @return true...表示
     */
    public boolean isDispDeliveryMethodReserveDeliveryItems() {
        return !CollectionUtil.isEmpty(this.deliveryMethodReserveDeliveryItems);
    }

    /**
     * コンディション<br/>
     * 配送方法:入荷次第お届け表示判定
     *
     * @return true...表示
     */
    public boolean isDispDeliveryMethodDependingOnReceiptItems() {
        return !CollectionUtil.isEmpty(this.deliveryMethodDependingOnReceiptItems);
    }

    /**
     * コンディション<br/>
     * 今すぐお届けのお届け希望日、お届け時間帯の表示判定
     *
     * @return true...表示
     */
    public boolean isDispDeliveryDateByDeliveryNow() {
        return !StringUtil.isEmpty(this.deliveryDateByDeliveryNow) && !StringUtil.isEmpty(
                        this.receiverTimeZoneByDeliveryNow);
    }
    // PDR Migrate Customization to here

    /**
     * コンディション<br />
     * 選択されている決済方法がクレジットかどうか
     *
     * @return true..クレジット / false..クレジット以外
     */
    public boolean isCreditType() {
        if (dispReceiveOrderDto == null || dispReceiveOrderDto.getSettlementMethodEntity() == null) {
            return false;
        }
        return this.dispReceiveOrderDto.getSettlementMethodEntity().getSettlementMethodType().equals(CREDIT);
    }

    // 2023-renew No14 from here

    /**
     * コンディション<br />
     * 決済方法が選択されているかどうか
     *
     * @return true..選択 / false..未選択
     */
    public boolean isSelectedSettlement() {
        return StringUtil.isNotEmpty(this.settlementMethodName) && StringUtil.isNotEmpty(
                        this.settlementMethodDisplayName);
    }

    /**
     * コンディション<br />
     * 無料かどうか
     *
     * @return true..無料 / false..無料でない
     */
    public boolean isFreeSettlement() {
        return ApplicationContextUtility.getBean(OrderUtility.class)
                                        .getFreeSettlementMethodSeq()
                                        .equals(settlementMethodSeq);
    }

    // 2023-renew No14 to here

    /**
     * 完了画面への遷移有無判定<br/>
     * 注文チェックでエラーが発生した場合、完了画面へ遷移させないための条件制御
     *
     * @return true...遷移許可 / false...遷移不可
     */
    public boolean isNextComplete() {
        return !orderMessageDto.hasErrorMessage();
    }

    /**
     * 注文確認画面に設定されたトークンとセッションのトークンの比較を行う。
     *
     * @return true - 比較OK / false - 比較NG
     */
    public boolean checkOrderConfirmToken() {
        // トークン情報未設定
        if (diffOrderConfirmToken == null) {
            return false;
        }
        // トークン情報、比較用トークン情報が不一致
        if (!diffOrderConfirmToken.equals(orderConfirmToken)) {
            return false;
        }
        return true;
    }

    /**  ***********　Confirm End　*********** **/
    /**  ***********　Complete Start　*********** **/

    // 2023-renew No14 from here
    /**
     * 受注コードリスト
     */
    private List<String> orderCodeList;
    // 2023-renew No14 to here

    /**  ***********　Complete End　*********** **/
}
