/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringConversionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCreditCardType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeEffectiveFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeInvoiceAttachmentFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePaymentType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.OrderTempDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.settlement.SettlementDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.settlement.SettlementDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.conveni.ConvenienceEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.OrderCommonModel;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.ComTransactionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CouponUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * PDR#022 ユーザー毎の支払方法表示制御<br/>
 * お支払い方法選択画面 Helper
 *
 * @author kimura
 * @author satoh
 */
@Component
public class PaymentHelper {

    /**
     * CouponUtility
     */
    private final CouponUtility couponUtility;

    // Paygent Customization from here
    /**
     * 通信ユーティリティ
     */
    private final ComTransactionUtility comTransactionUtility;
    // Paygent Customization to here

    /**
     * コンストラクタ
     *
     * @param couponUtility         CouponUtility
     * @param comTransactionUtility 通信ユーティリティ
     */
    @Autowired
    public PaymentHelper(CouponUtility couponUtility, ComTransactionUtility comTransactionUtility) {
        this.couponUtility = couponUtility;
        // Paygent Customization from here
        this.comTransactionUtility = comTransactionUtility;
        // Paygent Customization to here
    }

    /**
     * Modelへの変換処理。<br />
     * 受注Dto ⇒ Model<br />
     * <pre>
     * 登録済みクレジットカード情報設定処理 削除
     * </pre>
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param paymentModel    お支払い方法選択画面Model
     * @param receiveOrderDto 受注Dto
     */
    public void toPageForLoad(OrderCommonModel orderCommonModel,
                              PaymentModel paymentModel,
                              ReceiveOrderDto receiveOrderDto) {

        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();

        OrderTempDto orderTempDto = receiveOrderDto.getOrderTempDto();

        if (orderSettlementEntity != null && orderSettlementEntity.getSettlementMethodSeq() != null) {
            if (receiveOrderDto.getOrderNextSettlementEntity() != null) {
                // 次回以降の決済方法を設定
                paymentModel.setSettlementMethod(
                                receiveOrderDto.getOrderNextSettlementEntity().getSettlementMethodSeq().toString());
            } else {
                // 決済方法を設定
                paymentModel.setSettlementMethod(orderSettlementEntity.getSettlementMethodSeq().toString());
            }
        }

        if (orderTempDto != null) {
            // コンビニ選択初期化
            if (orderTempDto.getConvenience() != null) {
                // すでに選択している場合は選択しているコンビニをPageに設定
                SettlementDto sDto = paymentModel.getSettlementDtoMap().get(paymentModel.getSettlementMethod());
                // 注文画面から遷移した際にコンビニ決済が使用不可になっている場合sDtoはnullのためnullチェックが必要
                // コンビニ以外の決済を選択して、注文確認画面から遷移した際は#getConvenienceEntityListはnullのためnullチェックが必要
                if (sDto != null && sDto.getConvenienceEntityList() != null) {
                    for (ConvenienceEntity cdto : sDto.getConvenienceEntityList()) {
                        if (cdto.getConveniCode().equals(orderTempDto.getConvenience())) {
                            // コンビニプルダウンのvalue値となるコンビニSEQをセット
                            for (PaymentModelItem item : paymentModel.getPaymentModelItems()) {
                                if (paymentModel.getSettlementMethod().equals(item.getSettlementMethodValue())) {
                                    item.setConvenience(cdto.getConveniSeq().toString());
                                }
                            }
                        }
                    }
                }
            }
        }

        // 納品書添付のラジオボタンの初期化
        // 1件目の配送情報から判断
        HTypeInvoiceAttachmentFlag invoiceAttachmentFlag =
                        receiveOrderDto.getOrderDeliveryDto().getOrderDeliveryEntity().getInvoiceAttachmentFlag();

        // 決済方法選択画面初期表示時 初期値は必要
        if (orderSettlementEntity == null || orderSettlementEntity.getSettlementMethodSeq() == null) {
            invoiceAttachmentFlag = HTypeInvoiceAttachmentFlag.OFF;
        }
        paymentModel.setInvoiceAttachmentFlag(invoiceAttachmentFlag == HTypeInvoiceAttachmentFlag.OFF ?
                                                              PaymentModel.INVOICE_ATTACHMENT_NO_HOPE :
                                                              PaymentModel.INVOICE_ATTACHMENT_HOPE);

        // クレジット情報支払い区分初期化
        for (PaymentModelItem paymentModelItem : paymentModel.getPaymentModelItems()) {
            if (HTypeSettlementMethodType.CREDIT == paymentModelItem.getSettlementMethodType()) {
                paymentModelItem.setPaymentType(HTypePaymentType.SINGLE.getValue());
                // PDR Migrate Customization from here
                // 選択状態設定
                if (orderSettlementEntity != null && HTypeSettlementMethodType.CREDIT.equals(
                                orderSettlementEntity.getSettlementMethodType())) {
                    if (orderTempDto.isUseRegistCardFlg()) {
                        // 保持カード 選択
                        paymentModelItem.setCreditCardType(HTypeCreditCardType.REGISTERED_CARD.getValue());
                        // 選択カード
                        paymentModelItem.setRegistCardSelect(orderTempDto.getCustomerCardId());
                    } else {
                        // 新しいカード 選択
                        paymentModelItem.setCreditCardType(HTypeCreditCardType.NEW_CARD.getValue());
                    }
                }
                // PDR Migrate Customization to here
            }
        }

        // クーポン情報設定
        toPageForLoadForCoupon(orderCommonModel, paymentModel, receiveOrderDto);

        // お支払い金額
        paymentModel.setTempOrderPrice(couponUtility.getSettlementCharge(receiveOrderDto));

        // PDR Migrate Customization from here
        // クレジットカード 画面設定処理 削除
        // 決済方法ラジオボタン作成時に設定するため
        // PDR Migrate Customization to here

        // Paygent Customization from here
        // トークン決済用項目
        paymentModel.setMerchantId(PropertiesUtil.getSystemPropertiesValue("paygent.merchant.id"));
        paymentModel.setPaygentTokenKey(PropertiesUtil.getSystemPropertiesValue("paygent.token.key"));
        // Paygent Customization to here
    }

    /**
     * 受注情報を画面に反映する。<br />
     * <pre>
     * クーポンで追加したクーポン関連の項目を画面に反映する。
     * </pre>
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param paymentModel    お支払い方法選択画面Model
     * @param receiveOrderDto 受注DTO
     */
    public void toPageForLoadForCoupon(OrderCommonModel orderCommonModel,
                                       PaymentModel paymentModel,
                                       ReceiveOrderDto receiveOrderDto) {

        // 注文者の姓
        paymentModel.setOrderLastName(receiveOrderDto.getOrderPersonEntity().getOrderLastName());
        // 注文者の名
        paymentModel.setOrderFirstName(receiveOrderDto.getOrderPersonEntity().getOrderFirstName());
        // 商品金額合計
        paymentModel.setGoodsPriceTotal(receiveOrderDto.getOrderSettlementEntity().getGoodsPriceTotal());
        // 送料
        paymentModel.setCarriage(receiveOrderDto.getOrderSettlementEntity().getCarriage());
        // 消費税
        paymentModel.setTaxPrice(receiveOrderDto.getOrderSettlementEntity().getTaxPrice());

        // 割引前受注金額
        paymentModel.setPreCouponDiscountOrderPrice(
                        receiveOrderDto.getOrderSettlementEntity().getBeforeDiscountOrderPrice());

        // クーポンコード（未入力)
        paymentModel.setCouponCode("");

        // クーポン情報を取得する
        CouponEntity couponEntity = receiveOrderDto.getCoupon();

        // クーポン情報が取得できたら、クーポンが適用されているので、クーポン名と割引金額を取得する
        if (couponEntity != null) {
            paymentModel.setCouponName(couponEntity.getCouponDisplayNamePC());

            // クーポン割引額は画面上ではマイナス表記
            paymentModel.setCouponDiscountPrice(
                            receiveOrderDto.getOrderSettlementEntity().getCouponDiscountPrice().negate());

            // クーポン情報が取得できなかったら、割引金額に0円をセットする
        } else {
            paymentModel.setCouponDiscountPrice(BigDecimal.ZERO);
        }

        // お支払い金額
        paymentModel.setTempOrderPrice(couponUtility.getSettlementCharge(receiveOrderDto));
    }

    /**
     * Dtoへの変換処理<br/>
     * Model ⇒ 受注Dto<br/>
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param paymentModel お支払い方法選択画面Model
     * @return 受注Dto
     */
    public ReceiveOrderDto toReceiveOrderDtoForConfirm(OrderCommonModel orderCommonModel, PaymentModel paymentModel) {

        // Modelから決済Dtoマップを取得
        Map<String, SettlementDto> settlementMethodDtoMap = paymentModel.getSettlementDtoMap();
        // 決済Dtoマップから決済Dtoを取得
        SettlementDto settlementDto = settlementMethodDtoMap.get(paymentModel.getSettlementMethod());
        // 決済Dtoから決済詳細Dtoを取得
        SettlementDetailsDto settlementDetailsDto = settlementDto.getSettlementDetailsDto();
        // Modelから受注Dtoを取得
        ReceiveOrderDto receiveOrderDto = orderCommonModel.getReceiveOrderDto();

        OrderSettlementEntity orderSettlementEntity =
                        prepareOrderSettlement(orderCommonModel, settlementDto, settlementDetailsDto,
                                               receiveOrderDto.getOrderSettlementEntity(),
                                               paymentModel.getSettlementMethod()
                                              );
        // 受注DTOに設定
        receiveOrderDto.setOrderSettlementEntity(
                        makeReceiveOrderDtoForConfirm(orderCommonModel, paymentModel, receiveOrderDto, settlementDto,
                                                      settlementDetailsDto, orderSettlementEntity
                                                     ));

        return receiveOrderDto;
    }

    /**
     * 受注情報の作成<br/>
     *
     * @param orderCommonModel      注文フロー共通Model
     * @param paymentModel          お支払い方法選択画面Model
     * @param receiveOrderDto       受注DTO
     * @param settlementDto         決済DTO
     * @param settlementDetailsDto  決済詳細DTO
     * @param orderSettlementEntity 受注決済エンティティ
     * @return 受注決済エンティティ
     */
    private OrderSettlementEntity makeReceiveOrderDtoForConfirm(OrderCommonModel orderCommonModel,
                                                                PaymentModel paymentModel,
                                                                ReceiveOrderDto receiveOrderDto,
                                                                SettlementDto settlementDto,
                                                                SettlementDetailsDto settlementDetailsDto,
                                                                OrderSettlementEntity orderSettlementEntity) {

        if (settlementDetailsDto.getSettlementMethodType().equals(HTypeSettlementMethodType.CREDIT)) {
            // 決済タイプがクレジットの場合
            setupCreditPayment(orderCommonModel, paymentModel, settlementDetailsDto, receiveOrderDto,
                               orderSettlementEntity
                              );
        }

        if (settlementDetailsDto.getSettlementMethodType().equals(HTypeSettlementMethodType.CONVENIENCE)) {
            // 決済タイプがコンビニの場合
            setupConveniPayment(orderCommonModel, paymentModel, settlementDto, receiveOrderDto);
        }

        // 納品書を希望してるかどうか
        setupInvoice(orderCommonModel, paymentModel, receiveOrderDto);

        return orderSettlementEntity;
    }

    /**
     * 納品書要否関連の処理
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param paymentModel    お支払い方法選択画面Model
     * @param receiveOrderDto 受注DTO
     * @param customParams    案件用変数
     */
    protected void setupInvoice(OrderCommonModel orderCommonModel,
                                PaymentModel paymentModel,
                                ReceiveOrderDto receiveOrderDto,
                                Object... customParams) {

        HTypeInvoiceAttachmentFlag invoiceAttachmentFlag = HTypeInvoiceAttachmentFlag.OFF;
        if (PaymentModel.INVOICE_ATTACHMENT_HOPE.equals(paymentModel.getInvoiceAttachmentFlag())) {
            invoiceAttachmentFlag = HTypeInvoiceAttachmentFlag.ON;
        }

        // 配送方法に設定する
        receiveOrderDto.getOrderDeliveryDto().getOrderDeliveryEntity().setInvoiceAttachmentFlag(invoiceAttachmentFlag);

        // 納品書要否フラグを保持しておく
        receiveOrderDto.setInvoiceAttachmentSetFlag(invoiceAttachmentFlag);
    }

    /**
     * コンビニ支払の設定
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param paymentModel    お支払い方法選択画面Model
     * @param settlementDto   決済DTO
     * @param receiveOrderDto 受注DTO
     * @param customParams    案件用変数
     */
    protected void setupConveniPayment(OrderCommonModel orderCommonModel,
                                       PaymentModel paymentModel,
                                       SettlementDto settlementDto,
                                       ReceiveOrderDto receiveOrderDto,
                                       Object... customParams) {
        // 注文一時情報Dtoの設定
        OrderTempDto orderTempDto = receiveOrderDto.getOrderTempDto();
        if (orderTempDto == null) {
            // nullだったら作成する。
            orderTempDto = ApplicationContextUtility.getBean(OrderTempDto.class);
        }

        // コンビニエンティティリスト取得
        List<ConvenienceEntity> convenienceEntityList = settlementDto.getConvenienceEntityList();
        for (ConvenienceEntity convenienceEntity : convenienceEntityList) {
            for (PaymentModelItem paymentModelItem : paymentModel.getPaymentModelItems()) {
                if ((paymentModelItem.getSettlementMethodValue()).equals(StringConversionUtil.toString(
                                settlementDto.getSettlementDetailsDto().getSettlementMethodSeq()))
                    && convenienceEntity.getConveniSeq().toString().equals(paymentModelItem.getConvenience())) {
                    // 選択コンビニ支払い方法コード設定
                    orderTempDto.setMethod(convenienceEntity.getPayCode());
                    // 選択コンビニコード設定
                    orderTempDto.setConvenience(convenienceEntity.getConveniCode());
                    // コンビニ名をPage（サブアプリケーションスコープ）に保持
                    paymentModel.setSelectedConveniName(convenienceEntity.getConveniName());
                    break;
                }
            }
        }

        // 受注DTOに設定
        receiveOrderDto.setOrderTempDto(orderTempDto);
    }

    /**
     * クレジット支払の設定
     * <pre>
     * ペイジェント用の処理を追加
     * 保持済 保持していないカード情報を設定
     * </pre>
     *
     * @param orderCommonModel      注文フロー共通Model
     * @param paymentModel          お支払い方法選択画面Model
     * @param settlementDetailsDto  決済詳細DTO
     * @param receiveOrderDto       受注DTO
     * @param orderSettlementEntity 受注決済エンティティ
     * @param customParams          案件用変数
     */
    protected void setupCreditPayment(OrderCommonModel orderCommonModel,
                                      PaymentModel paymentModel,
                                      SettlementDetailsDto settlementDetailsDto,
                                      ReceiveOrderDto receiveOrderDto,
                                      OrderSettlementEntity orderSettlementEntity,
                                      Object... customParams) {
        // 注文一時情報Dtoの設定
        OrderTempDto orderTempDto = receiveOrderDto.getOrderTempDto();
        if (orderTempDto == null) {
            // nullだったら作成する。
            orderTempDto = ApplicationContextUtility.getBean(OrderTempDto.class);
        }

        // クレジット請求情報の値の初期化
        // カード番号
        String cardNumber = null;
        // 有効期限（年）
        String expirationDateYear = null;
        // 有効期限（月）
        String expirationDateMonth = null;
        // セキュリティコード
        String securityCode = null;
        // 分割回数
        String dividedNumber = null;
        // カード保存フラグ
        // PDR Migrate Customization from here
        boolean saveFlg = true;
        // カード会社
        String cardBrand = null;
        // 顧客カードID
        String customerCardId = null;
        // PDR Migrate Customization to here

        for (PaymentModelItem paymentModelItem : paymentModel.getPaymentModelItems()) {
            if (paymentModel.getSettlementMethod().equals(paymentModelItem.getSettlementMethodValue())) {
                // カードブランド表示名を取得してModelにセットしておく。
                // DTO に設定する値が表示名だとそのまま DB に登録されてしまうため。
                cardNumber = paymentModelItem.getCardNumber();
                expirationDateYear = paymentModelItem.getExpirationDateYear();
                expirationDateMonth = paymentModelItem.getExpirationDateMonth();
                securityCode = paymentModelItem.getSecurityCode();
                orderTempDto.setMethod(paymentModelItem.getPaymentType());
                dividedNumber = paymentModelItem.getDividedNumber();
                saveFlg = paymentModelItem.isSaveFlg();

                // PDR Migrate Customization from here
                if (HTypeCreditCardType.REGISTERED_CARD.getValue().equals(paymentModelItem.getCreditCardType())) {
                    // 保持カード選択時

                    // 保持カードリスト
                    List<PaymentModelRegistedCardItem> registedCardItemList = paymentModelItem.getRegistedCardItems();

                    // 選択された保持カード
                    String selectCustomerCardId = paymentModelItem.getRegistCardSelect();

                    for (PaymentModelRegistedCardItem registedCardItem : registedCardItemList) {

                        // 選択された保持カードと一致する場合
                        if (selectCustomerCardId.equals(registedCardItem.getCustomerCardId())) {
                            // カード情報を設定
                            // 顧客カードID
                            customerCardId = registedCardItem.getCustomerCardId();
                            // 分割回数は設定しない
                            // 登録済みのカードのため 固定でfalseを設定
                            saveFlg = false;
                            // この決済で登録済みカードを使用するためtrueを設定
                            orderTempDto.setUseRegistCardFlg(true);

                            // 以下表示用に設定
                            // カード番号
                            cardNumber = registedCardItem.getRegistedCardNumber();
                            // カード会社
                            cardBrand = registedCardItem.getRegistedCardBrand();
                            // 支払回数(一括のみ)
                            orderTempDto.setMethod(paymentModelItem.getPaymentType());
                            // 有効期限 年
                            expirationDateYear = registedCardItem.getRegistedExpirationDateYear();
                            // 有効期限 月
                            expirationDateMonth = registedCardItem.getRegistedExpirationDateMonth();

                            break;
                        }
                    }
                } else {

                    // カードブランド表示名を取得してページクラスにセットしておく。
                    // DTO に設定する値が表示名だとそのまま DB に登録されてしまうため。
                    cardNumber = paymentModelItem.getCardNumber();
                    expirationDateYear = paymentModelItem.getExpirationDateYear();
                    expirationDateMonth = paymentModelItem.getExpirationDateMonth();
                    // セキュリティコード なし
                    // 支払回数(一括のみ)
                    orderTempDto.setMethod(paymentModelItem.getPaymentType());
                    // 分割回数(使用しないためnull)
                    dividedNumber = paymentModelItem.getDividedNumber();
                    // カードは毎回保存するため 固定でtrueを設定
                    saveFlg = true;
                    // カード会社
                    cardBrand = paymentModelItem.getCardBrand();
                }
                // PDR Migrate Customization to here
                break;
            }
        }

        // カード番号
        orderTempDto.setCardNo(cardNumber);
        // カード有効期限。フォーマット：YYMM
        orderTempDto.setExpire(expirationDateYear + expirationDateMonth);
        // セキュリティコード
        orderTempDto.setSecurityCode(securityCode);
        // 支払い区分
        // 分割回数
        if (HTypePaymentType.INSTALLMENT.getValue().equals(orderTempDto.getMethod())) {
            if (dividedNumber != null) {
                orderTempDto.setPayTimes(Integer.valueOf(dividedNumber));
            }
        } else {
            // 分割以外はnullを設定しておく。
            orderTempDto.setPayTimes(null);
        }

        // 保存フラグ
        orderTempDto.setSaveFlg(saveFlg);

        // PDR Migrate Customization from here
        // カード会社
        orderTempDto.setCardBrand(cardBrand);

        // 顧客カードID
        orderTempDto.setCustomerCardId(customerCardId);
        // PDR Migrate Customization to here

        // トークン
        orderTempDto.setToken(paymentModel.getToken());

        // Paygent Customization from here
        // 預かりカード用トークン
        orderTempDto.setKeepToken(paymentModel.getKeepToken());
        // Paygent Customization to here

        // 決済代行会員ID格納
        orderTempDto.setPaymentMemberId(orderCommonModel.getMemberInfoEntity().getPaymentMemberId());

        // 受注DTOに設定
        receiveOrderDto.setOrderTempDto(orderTempDto);
    }

    /**
     * 受注決済エンティティを準備する
     *
     * @param orderCommonModel            決済方法選択画面
     * @param settlementDto         決済DTO
     * @param settlementDetailsDto  決済詳細DTO
     * @param orderSettlementEntity 受注決済エンティティ
     * @param settlementMethod      決済方法SEQ
     * @param customParams          案件用変数
     * @return 受注決済エンティティ
     */
    protected OrderSettlementEntity prepareOrderSettlement(OrderCommonModel orderCommonModel,
                                                           SettlementDto settlementDto,
                                                           SettlementDetailsDto settlementDetailsDto,
                                                           OrderSettlementEntity orderSettlementEntity,
                                                           String settlementMethod,
                                                           Object... customParams) {
        // 受注決済エンティティの設定
        if (orderSettlementEntity == null) {
            // nullだったら作成する。
            orderSettlementEntity = ApplicationContextUtility.getBean(OrderSettlementEntity.class);
        }
        // 決済方法SEQを設定
        orderSettlementEntity.setSettlementMethodSeq(Integer.valueOf(settlementMethod));
        // 決済種別
        orderSettlementEntity.setSettlementMethodType(settlementDetailsDto.getSettlementMethodType());
        // 決済手数料を設定
        orderSettlementEntity.setSettlementCommission(settlementDto.getCharge());
        // 請求種別を設定
        orderSettlementEntity.setBillType(settlementDetailsDto.getBillType());
        return orderSettlementEntity;
    }

    /**
     * エンティティへ変換<br/>
     * 決済詳細DTO ⇒ 済方法エンティティ（登録日時、更新日時はセットできない）<br/>
     *
     * @param settlementDetailsDto 決済詳細DTO
     * @return 決済方法エンティティ
     */
    public SettlementMethodEntity toSettlementMethodEntityForConfirm(SettlementDetailsDto settlementDetailsDto) {

        SettlementMethodEntity settlementMethodEntity = ApplicationContextUtility.getBean(SettlementMethodEntity.class);

        settlementMethodEntity.setSettlementMethodSeq(settlementDetailsDto.getSettlementMethodSeq());
        settlementMethodEntity.setShopSeq(settlementDetailsDto.getShopSeq());
        settlementMethodEntity.setSettlementMethodName(settlementDetailsDto.getSettlementMethodName());
        settlementMethodEntity.setSettlementMethodDisplayNamePC(
                        settlementDetailsDto.getSettlementMethodDisplayNamePC());
        settlementMethodEntity.setSettlementMethodDisplayNameMB(
                        settlementDetailsDto.getSettlementMethodDisplayNameMB());
        settlementMethodEntity.setOpenStatusPC(settlementDetailsDto.getOpenStatusPC());
        settlementMethodEntity.setOpenStatusMB(settlementDetailsDto.getOpenStatusMB());
        settlementMethodEntity.setSettlementNotePC(settlementDetailsDto.getSettlementNotePC());
        settlementMethodEntity.setSettlementNoteMB(settlementDetailsDto.getSettlementNoteMB());
        settlementMethodEntity.setSettlementMethodType(settlementDetailsDto.getSettlementMethodType());
        settlementMethodEntity.setSettlementMethodCommissionType(
                        settlementDetailsDto.getSettlementMethodCommissionType());
        settlementMethodEntity.setBillType(settlementDetailsDto.getBillType());
        settlementMethodEntity.setDeliveryMethodSeq(settlementDetailsDto.getDeliveryMethodSeq());
        settlementMethodEntity.setEqualsCommission(settlementDetailsDto.getEqualsCommission());
        settlementMethodEntity.setSettlementMethodPriceCommissionFlag(
                        settlementDetailsDto.getSettlementMethodPriceCommissionFlag());
        settlementMethodEntity.setLargeAmountDiscountPrice(settlementDetailsDto.getLargeAmountDiscountPrice());
        settlementMethodEntity.setLargeAmountDiscountCommission(
                        settlementDetailsDto.getLargeAmountDiscountCommission());
        settlementMethodEntity.setOrderDisplay(settlementDetailsDto.getOrderDisplay());
        settlementMethodEntity.setMaxPurchasedPrice(settlementDetailsDto.getMaxPurchasedPrice());
        settlementMethodEntity.setPaymentTimeLimitDayCount(settlementDetailsDto.getPaymentTimeLimitDayCount());
        settlementMethodEntity.setMinPurchasedPrice(settlementDetailsDto.getMinPurchasedPrice());
        settlementMethodEntity.setCancelTimeLimitDayCount(settlementDetailsDto.getCancelTimeLimitDayCount());
        settlementMethodEntity.setEnableCardNoHolding(settlementDetailsDto.getEnableCardNoHolding());
        settlementMethodEntity.setEnableSecurityCode(settlementDetailsDto.getEnableSecurityCode());
        settlementMethodEntity.setEnable3dSecure(settlementDetailsDto.getEnable3dSecure());
        settlementMethodEntity.setEnableInstallment(settlementDetailsDto.getEnableInstallment());
        settlementMethodEntity.setSettlementMailRequired(settlementDetailsDto.getSettlementMailRequired());
        settlementMethodEntity.setEnableRevolving(settlementDetailsDto.getEnableRevolving());

        return settlementMethodEntity;
    }

    /**
     * 退避した決済方法リストが有る場合、決済方法SEQが一致する情報に入力情報を設定する
     *
     * @param newItems 新規生成した決済方法Item
     * @param oldItems 保持していた決済方法Item
     */
    public void restorePaymentPageItem(List<PaymentModelItem> newItems, List<PaymentModelItem> oldItems) {
        if (CollectionUtil.isEmpty(newItems) || CollectionUtil.isEmpty(oldItems)) {
            return;
        }

        for (PaymentModelItem newItem : newItems) {
            for (PaymentModelItem oldItem : oldItems) {
                // 退避情報の決済方法SEQと再取得した情報の決済方法SEQを比較
                if (newItem.getSettlementMethodValue().equals(oldItem.getSettlementMethodValue())) {
                    restorePaymentPageItem(newItem, oldItem);
                }
            }
        }
    }

    /**
     * 決済方法の設定内容を復元する
     *
     * @param newItem 新規生成した決済方法Item
     * @param oldItem 保持していた決済方法Item
     */
    protected void restorePaymentPageItem(PaymentModelItem newItem, PaymentModelItem oldItem) {
        // 決済方法別に設定
        if (HTypeSettlementMethodType.CREDIT == newItem.getSettlementMethodType()) {
            // クレジット決済
            // カード番号設定
            newItem.setCardNumber(oldItem.getCardNumber());
            // 分割回数設定
            // 分割が有効の場合に設定
            if (HTypeEffectiveFlag.VALID == newItem.getEnableInstallment()) {
                newItem.setDividedNumber(oldItem.getDividedNumber());
            }
            // 有効期限（月）設定
            newItem.setExpirationDateMonth(oldItem.getExpirationDateMonth());
            // 有効期限（年）設定
            newItem.setExpirationDateYear(oldItem.getExpirationDateYear());
            // 支払区分設定
            // 選択が分割で有効状態、選択がリボで有効状態の場合
            if ((HTypePaymentType.INSTALLMENT.getValue().equals(oldItem.getPaymentType())
                 && HTypeEffectiveFlag.VALID == newItem.getEnableInstallment()) || (
                                HTypePaymentType.REVOLVING.getValue().equals(oldItem.getPaymentType())
                                && HTypeEffectiveFlag.VALID == newItem.getEnableRevolving())) {
                // 選択した支払区分を設定
                newItem.setPaymentType(oldItem.getPaymentType());
            } else {
                // 選択した支払区分が無効状態または選択が一括の場合に、一括を設定
                newItem.setPaymentType(HTypePaymentType.SINGLE.getValue());
            }
            // セキュリティコード設定
            newItem.setSecurityCode(oldItem.getSecurityCode());

        } else if (HTypeSettlementMethodType.CONVENIENCE == newItem.getSettlementMethodType()) {
            // コンビニ決済
            // コンビニ選択値設定
            newItem.setConvenience(oldItem.getConvenience());
        }
    }

    /**
     * お預かりカード情報をModelに反映する<br/>
     * <pre>
     * 登録済みカード情報を複数件表示するよう修正
     * </pre>
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param paymentModel     お支払い方法選択画面Model
     * @param paymentModelItem クレジットカード情報Item
     */
    public void setupRegistedCardInfo(OrderCommonModel orderCommonModel,
                                      PaymentModel paymentModel,
                                      PaymentModelItem paymentModelItem) {
        // Paygent Customization from here
        ComResultDto cardInfo = paymentModel.getComResultDto();
        if (comTransactionUtility.isErrorOccurred(cardInfo)) {
            // Paygent Customization to here
            // PDR Migrate Customization from here
            // エラー発生時は処理を行わない
            // PDR Migrate Customization to here
            return;
        }

        // PDR Migrate Customization from here
        List<PaymentModelRegistedCardItem> registedCardItemList = new ArrayList<>();
        // 登録済みのカード情報複数件表示
        for (Map<String, String> registCardInfo : cardInfo.getResultMapList()) {

            PaymentModelRegistedCardItem registedCardItem =
                            ApplicationContextUtility.getBean(PaymentModelRegistedCardItem.class);

            // Paygent Customization from here
            // カード番号
            registedCardItem.setRegistedCardNumber(registCardInfo.get(ComResultDto.KEY_CARD_NUMBER));
            String cardValidTerm = registCardInfo.get(ComResultDto.KEY_CARD_VALID_TERM);
            // 有効期限：月
            registedCardItem.setRegistedExpirationDateMonth(cardValidTerm.substring(0, 2));
            // 有効期限：年
            registedCardItem.setRegistedExpirationDateYear(cardValidTerm.substring(2));
            // Paygent Customization to here

            // カード会社
            registedCardItem.setRegistedCardBrand(registCardInfo.get(ComResultDto.KEY_CARD_BRAND));
            // 顧客カードID
            registedCardItem.setCustomerCardId(registCardInfo.get(ComResultDto.KEY_CUSTOMER_CARD_ID));
            // 保持カード値
            registedCardItem.setRegistCardSelectValue(registedCardItem.getCustomerCardId());

            registedCardItemList.add(registedCardItem);
        }
        paymentModelItem.setRegistedCardItems(registedCardItemList);
        // PDR Migrate Customization to here
    }

    // 2023-renew No14 from here

    /**
     * 注文確認画面の初期表示用に前回決済方法を設定する
     *
     * @param paymentModel         お支払い方法選択画面Model
     * @param settlementMethodType 決済タイプ（前回支払方法取得レスポンス）
     * @param customerCardId       顧客カードID（前回支払方法取得レスポンス）
     */
    public void toPageForLoadForConfirm(PaymentModel paymentModel, String settlementMethodType, String customerCardId) {

        if (StringUtils.isEmpty(settlementMethodType)) {
            // 決済タイプが返却されなかった場合、スキップ
            return;
        }

        // 決済方法リストでループ
        for (PaymentModelItem paymentModelItem : paymentModel.getPaymentModelItems()) {
            if (!paymentModelItem.getSettlementMethodType().getValue().equals(settlementMethodType)
                || !paymentModelItem.isUsabilityFlag()) {
                // 返却された決済タイプ以外 又は 使用不可の決済の場合、スキップ
                continue;
            }

            // クレジット決済の場合
            if (HTypeSettlementMethodType.CREDIT.getValue().equals(settlementMethodType)) {
                if (!paymentModelItem.isRegistedCard()) {
                    // 保持カードが1件も存在しない場合、スキップ
                    break;
                }

                // 保持カードリストに返却された顧客カードIDが存在するかチェック
                List<PaymentModelRegistedCardItem> registedCardItems = paymentModelItem.getRegistedCardItems();
                boolean isExistCard = false;
                for (PaymentModelRegistedCardItem cardItem : registedCardItems) {
                    if (cardItem.getCustomerCardId().equals(customerCardId)) {
                        isExistCard = true;
                        break;
                    }
                }

                // 区分：保持カードを設定
                paymentModelItem.setCreditCardType(HTypeCreditCardType.REGISTERED_CARD.getValue());

                // 顧客カードIDを設定
                // ※返却された顧客カードIDが保持カードにリストに存在しない場合、1件目の保持カードを設定
                paymentModelItem.setRegistCardSelect(
                                isExistCard ? customerCardId : registedCardItems.get(0).getCustomerCardId());
            }

            // 決済方法選択値に決済方法SEQを設定
            paymentModel.setSettlementMethod(paymentModelItem.getSettlementMethodValue());
            break;
        }

    }

    // 2023-renew No14 to here

}
