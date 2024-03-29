/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAddressType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCouponLimitTargetType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeInvoiceAttachmentFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailRequired;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderAgeType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderSex;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeWaitingFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.conveni.ConvenienceEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.additional.OrderAdditionalChargeEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderReceiptOfMoneyEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.memo.OrderMemoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.MulPayUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringConversionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 受注修正修正・確認画面Helperクラス。
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class DetailsUpdateHelper {

    /**
     * 受注関連ユーティリティ
     */
    private OrderUtility orderUtility;

    /**
     * 変換ユーティリティ
     */
    private ConversionUtility conversionUtility;

    /**
     * 金額計算ユーティリティ
     */
    private CalculatePriceUtility calculatePriceUtility;

    @Autowired
    public DetailsUpdateHelper(OrderUtility orderUtility,
                               ConversionUtility conversionUtility,
                               CalculatePriceUtility calculatePriceUtility) {
        this.orderUtility = orderUtility;
        this.conversionUtility = conversionUtility;
        this.calculatePriceUtility = calculatePriceUtility;
    }

    /**
     * メソッド概要<br/>
     * メソッドの説明・概要<br/>
     *
     * @param model           セットするページ
     * @param receiveOrderDto 受注DTO
     */
    public void toPage(DetailsUpdateModel model, ReceiveOrderDto receiveOrderDto) {

        // 受注情報をページにセット
        putOrderSummaryInOrder(receiveOrderDto);
        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();

        Set<String> ignoreFieldNameSet = new HashSet<>();

        // 受注インデックス（受注履歴連番をセット)
        ignoreFieldNameSet.addAll(
                        CopyUtil.copyProperties(receiveOrderDto.getOrderIndexEntity(), model, ignoreFieldNameSet));

        // 受注お客様情報の値をセット
        ignoreFieldNameSet.addAll(
                        CopyUtil.copyProperties(receiveOrderDto.getOrderPersonEntity(), model, ignoreFieldNameSet));

        // 受注配送情報、受注商品情報の値をセット
        setOrderDeliveryDto(model, receiveOrderDto);

        // 受注決済の値をセット
        ignoreFieldNameSet.addAll(
                        CopyUtil.copyProperties(receiveOrderDto.getOrderSettlementEntity(), model, ignoreFieldNameSet));
        // 商品金額合計(税込み)を設定
        model.setPostTaxGoodsPriceTotal(calculatePriceUtility.getTaxIncludedGoodsPriceTotal(receiveOrderDto));

        // 受注請求の値をセット
        ignoreFieldNameSet.addAll(
                        CopyUtil.copyProperties(receiveOrderDto.getOrderBillEntity(), model, ignoreFieldNameSet));

        // 受注メモの値をセット
        if (receiveOrderDto.getOrderMemoEntity() != null) {
            ignoreFieldNameSet.addAll(
                            CopyUtil.copyProperties(receiveOrderDto.getOrderMemoEntity(), model, ignoreFieldNameSet));
        }

        CouponEntity coupon = receiveOrderDto.getCoupon();
        if (coupon != null) {
            // クーポンが適用されている場合のみクーポン情報を画面に反映する
            // クーポン名
            model.setCouponName(coupon.getCouponDisplayNamePC());
            // クーポン割引額 画面上マイナス表示
            model.setCouponDiscountPrice(receiveOrderDto.getOrderSettlementEntity().getCouponDiscountPrice().negate());
            // 適用クーポン名
            model.setApplyCouponName(coupon.getCouponName());
            // 適用クーポンID
            model.setApplyCouponId(coupon.getCouponId());
            // クーポン利用制限フラグ
            model.setCouponLimitTargetType(receiveOrderDto.getOrderIndexEntity().getCouponLimitTargetType());
            model.setCouponLimitTargetTypeValue(
                            receiveOrderDto.getOrderIndexEntity().getCouponLimitTargetType().getValue());
            // クーポン対象商品適用
            setCouponTargetGoods(model, coupon);
        } else {
            // クーポンが適用されていない場合はクーポン情報を初期化する
            // クーポン割引額 割引額を0円として処理する
            model.setCouponDiscountPrice(BigDecimal.ZERO);
            // 適用クーポン名
            model.setApplyCouponName(null);
            // 適用クーポンID
            model.setApplyCouponId(null);
        }

        // 受注サマリの値をセット
        ignoreFieldNameSet.addAll(CopyUtil.copyProperties(orderSummaryEntity, model, ignoreFieldNameSet));
        model.setSettlementMailRequired(receiveOrderDto.getOrderIndexEntity().getSettlementMailRequired());

        // 受注入金の値をセット
        List<OrderReceiptOfMoneyEntity> orderReceiptOfMoneyEntityList =
                        receiveOrderDto.getOrderReceiptOfMoneyEntityList();
        OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity =
                        ApplicationContextUtility.getBean(OrderReceiptOfMoneyEntity.class);
        if (orderReceiptOfMoneyEntityList != null && !orderReceiptOfMoneyEntityList.isEmpty()) {
            int index = orderReceiptOfMoneyEntityList.size() - 1;
            orderReceiptOfMoneyEntity = orderReceiptOfMoneyEntityList.get(index);
        }
        ignoreFieldNameSet.addAll(CopyUtil.copyProperties(orderReceiptOfMoneyEntity, model, ignoreFieldNameSet));
        model.setReceiptTime(orderReceiptOfMoneyEntity.getReceiptTime());

        // マルチペイメント請求情報の値をセット
        if (receiveOrderDto.getMulPayBillEntity() != null) {
            ignoreFieldNameSet.addAll(
                            CopyUtil.copyProperties(receiveOrderDto.getMulPayBillEntity(), model, ignoreFieldNameSet));

            if (model.getConvenience() != null) {
                model.setConvenienceOld(model.getConvenience());
                String conveniName = "";
                for (ConvenienceEntity ce : model.getConvenienceAllList()) {
                    if (model.getConvenience() != null && model.getConvenience().equals(ce.getConveniCode())) {
                        conveniName = orderUtility.getConveniName(ce);
                        break;
                    }
                }
                model.setConveniName(conveniName);
            }
        }

        // 入金状態をセット
        model.setPaymentStatus(getPaymentStatus(orderSummaryEntity, orderReceiptOfMoneyEntity));

        // 受注追加料金をセット
        setOrderAdditionalChargeDtoList(model, receiveOrderDto);

        // 決済方法名をセット
        model.setSettlementMethodName(receiveOrderDto.getSettlementMethodEntity().getSettlementMethodName());
        model.setCardBrandDisplayPc(receiveOrderDto.getOrderBillEntity().getCardBrandDisplayPC());

        // 請求金額に受注金額をセット
        // キャンセル後に表示される値がマイナス値になってしまう為
        model.setBillPrice(orderSummaryEntity.getOrderPrice());

        // オーソリ期限日（決済日付＋オーソリ保持期間）
        model.setAuthoryLimitDate(receiveOrderDto.getAuthoryLimitDate());

        // 注文保留理由
        model.setOrderWaitingMemo(receiveOrderDto.getOrderIndexEntity().getOrderWaitingMemo());

        OrderMessageDto orderMessageDto = model.getOrderMessageDto();
        orderMessageDto.setOrderMessageList(orderMessageDto.getOrderMessageList());

        // メール送信要否
        if (model.getSettlementMailRequired() != null) {
            if (HTypeMailRequired.REQUIRED == model.getSettlementMailRequired()) {
                model.setUpdateMailRequired("true");
            } else {
                model.setUpdateMailRequired("false");
            }
        }

        if (receiveOrderDto.getOriginalCommission() != null) {
            model.setOrgCommissionDisp(receiveOrderDto.getOriginalCommission().toString());
        }

        if (receiveOrderDto.getOriginalCarriage() != null) {
            model.setOrgCarriageDisp(receiveOrderDto.getOriginalCarriage().toString());
        }

        // 更新項目を設定
        setUpdateItem(model);

    }

    /**
     * クーポン対象商品取得<br/>
     *
     * @param model  ページクラス
     * @param coupon クーポンエンティティ
     */
    protected void setCouponTargetGoods(DetailsUpdateModel model, CouponEntity coupon) {
        // クーポン対象商品が入力されていない場合は判定しない
        if (coupon.getTargetGoods() == null) {
            return;
        }
        List<String> targetGoodsList = Arrays.asList(conversionUtility.toDivArray(coupon.getTargetGoods()));

        for (OrderGoodsUpdateItem orderGoodsUpdateItem : model.getOrderReceiverItem().getOrderGoodsUpdateItems()) {
            if (targetGoodsList.contains(orderGoodsUpdateItem.getGoodsCode())) {
                orderGoodsUpdateItem.setCouponTargetGoodsFlg(true);
            }
        }
    }

    /**
     * CopyUtilで設定できない更新項目を設定する<br/>
     *
     * @param model セットするページ
     */
    public void setUpdateItem(DetailsUpdateModel model) {

        model.setUpdateWaitingFlag(model.getWaitingFlag() == HTypeWaitingFlag.ON);
        // 受注お客様情報
        // 性別
        if (model.getOrderSex() == null) {
            model.setUpdateOrderSex(null);
        } else {
            model.setUpdateOrderSex(model.getOrderSex().getValue());
        }

        // 生年月日
        model.setUpdateOrderBirthday(conversionUtility.toYmd(model.getOrderBirthday()));
    }

    /**
     * 受注詳細系ページ項目設定（編集前マルチペイメント情報）<br/>
     *
     * @param model            受注詳細ページ
     * @param mulPayBillEntity マルチペイメント請求
     */
    protected void toPage(DetailsUpdateModel model, MulPayBillEntity mulPayBillEntity) {

        // マルチペイメント請求情報の値をセット
        if (mulPayBillEntity != null) {
            CopyUtil.copyProperties(mulPayBillEntity, model, null);

            if (model.getConvenience() != null) {
                String conveniName = "";
                Map<String, String> convenienceItem = model.getConvenienceItems();

                if (model.getConvenience() != null && convenienceItem.containsKey(model.getConvenience())) {
                    conveniName = convenienceItem.get(model.getConvenience());
                }
                model.setConveniName(conveniName);
            }
        }
    }

    /**
     * 受注サマリー情報の精査<br/>
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void putOrderSummaryInOrder(ReceiveOrderDto receiveOrderDto) {
        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();
        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();
        List<OrderReceiptOfMoneyEntity> orderReceiptOfMoneyEntityList =
                        receiveOrderDto.getOrderReceiptOfMoneyEntityList();

        OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity =
                        ApplicationContextUtility.getBean(OrderReceiptOfMoneyEntity.class);
        if (orderReceiptOfMoneyEntityList != null && !orderReceiptOfMoneyEntityList.isEmpty()) {
            int index = orderReceiptOfMoneyEntityList.size() - 1;
            orderReceiptOfMoneyEntity = orderReceiptOfMoneyEntityList.get(index);
        }
        orderSummaryEntity.setReceiptPriceTotal(orderReceiptOfMoneyEntity.getReceiptPriceTotal());
        orderSummaryEntity.setOrderPrice(orderSettlementEntity.getOrderPrice());
    }

    /**
     * 入金ステータス取得<br/>
     *
     * @param orderSummaryEntity        受注サマリーエンティティ
     * @param orderReceiptOfMoneyEntity 受注入金エンティティ
     * @return 入金ステータス
     */
    protected String getPaymentStatus(OrderSummaryEntity orderSummaryEntity,
                                      OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity) {
        BigDecimal receiptPriceTotal = BigDecimal.ZERO;
        BigDecimal orderPrice = orderSummaryEntity.getOrderPrice();
        if (orderReceiptOfMoneyEntity != null) {
            receiptPriceTotal = orderReceiptOfMoneyEntity.getReceiptPriceTotal();
        }

        if (receiptPriceTotal.equals(BigDecimal.ZERO)) {
            return "1";
        }

        int status = orderPrice.compareTo(receiptPriceTotal);
        if (status == 0) {
            return "2";
        } else {
            return "3";
        }
    }

    /**
     * 追加料金情報リストをセット<br/>
     *
     * @param page            セットするページ
     * @param receiveOrderDto 受注DTO
     */
    protected void setOrderAdditionalChargeDtoList(DetailsUpdateModel page, ReceiveOrderDto receiveOrderDto) {
        List<OrderAdditionalChargeItem> orderAdditionalChargeItems = new ArrayList<>();
        List<OrderAdditionalChargeEntity> orderAdditionalChargeEntityList =
                        receiveOrderDto.getOrderAdditionalChargeEntityList();
        for (OrderAdditionalChargeEntity entity : orderAdditionalChargeEntityList) {
            OrderAdditionalChargeItem item = toOrderAdditionalChargeItem(entity);
            orderAdditionalChargeItems.add(item);
        }
        page.setOrderAdditionalChargeItems(orderAdditionalChargeItems);
    }

    /**
     * @param orderAdditionalChargeEntity 受注追加料金エンティティ
     * @return 追加料金アイテム
     */
    protected OrderAdditionalChargeItem toOrderAdditionalChargeItem(OrderAdditionalChargeEntity orderAdditionalChargeEntity) {
        OrderAdditionalChargeItem item = ApplicationContextUtility.getBean(OrderAdditionalChargeItem.class);
        CopyUtil.copyProperties(orderAdditionalChargeEntity, item, null);
        return item;
    }

    /**
     * 受注配送情報をセット<br/>
     * 配送情報内の商品情報リストをセット<br/>
     *
     * @param page            セットするページ
     * @param receiveOrderDto 受注DTO
     */
    protected void setOrderDeliveryDto(DetailsUpdateModel page, ReceiveOrderDto receiveOrderDto) {

        // お届け先情報リストをセット
        BigDecimal orderGoodsCountTotal = BigDecimal.ZERO;

        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

        // 商品メッセージ取得
        OrderMessageDto orderMessageDto = page.getOrderMessageDto();
        Map<Integer, Map<Integer, List<CheckMessageDto>>> orderGoodsMessageMap =
                        orderMessageDto.getOrderGoodsMessageMapMap();

        // 納品書 全配送共通の為、itemでは保持しない
        page.setInvoiceAttachmentFlag(orderDeliveryDto.getOrderDeliveryEntity().getInvoiceAttachmentFlag());
        // 納品書更新用フラグ
        if (page.getInvoiceAttachmentFlag() == null) {
            page.setUpdateInvoiceAttachmentFlag(null);
        } else {
            page.setUpdateInvoiceAttachmentFlag(page.getInvoiceAttachmentFlag().getValue());
        }

        OrderReceiverUpdateItem receiverItem = toOrderReceiverItem(orderDeliveryDto.getOrderDeliveryEntity(),
                                                                   orderDeliveryDto.getDeliveryMethodEntity()
                                                                  );

        // 商品情報リストをセット
        Integer consecutiveNo = orderDeliveryDto.getOrderDeliveryEntity().getOrderConsecutiveNo();
        List<OrderGoodsEntity> orderGoodsList = orderDeliveryDto.getOrderGoodsEntityList();
        List<OrderGoodsUpdateItem> items = page.getOrderGoodsUpdateItems();
        List<OrderGoodsUpdateItem> orderGoodsUpdateItems = new ArrayList<>();
        int index = 0;
        for (OrderGoodsEntity orderGoodsEntity : orderGoodsList) {
            // 商品アイテム作成
            OrderGoodsUpdateItem goodsItem = null;
            if (items != null && items.size() > index) {
                goodsItem = items.get(index);
                index++;
            }

            goodsItem = toOrderGoodsUpdateItem(orderGoodsEntity, goodsItem);
            orderGoodsUpdateItems.add(goodsItem);
            orderGoodsCountTotal = orderGoodsCountTotal.add(orderGoodsEntity.getGoodsCount());

            // 商品メッセージの設定
            setOrderGoodsMessageMap(orderGoodsMessageMap.get(consecutiveNo), goodsItem, orderGoodsEntity, page);
        }

        // 配送アイテムに商品アイテムリストをセット
        receiverItem.setOrderGoodsUpdateItems(orderGoodsUpdateItems);

        page.setOrderReceiverItem(receiverItem);
        page.setOrderGoodsCountTotal(orderGoodsCountTotal);
    }

    /**
     * @param orderDeliveryEntity  受注配送エンティティ
     * @param deliveryMethodEntity 配送エンティティ
     * @return お届け先アイテム
     */
    protected OrderReceiverUpdateItem toOrderReceiverItem(OrderDeliveryEntity orderDeliveryEntity,
                                                          DeliveryMethodEntity deliveryMethodEntity) {
        OrderReceiverUpdateItem receiverItem = ApplicationContextUtility.getBean(OrderReceiverUpdateItem.class);
        CopyUtil.copyProperties(orderDeliveryEntity, receiverItem, null);
        CopyUtil.copyProperties(deliveryMethodEntity, receiverItem, null);

        // CopyUtilで設定できない更新項目を設定する
        // 配送方法SEQ
        receiverItem.setUpdateDeliveryMethodSeq(StringConversionUtil.toString(receiverItem.getDeliveryMethodSeq()));
        // お届け希望日
        receiverItem.setUpdateReceiverDate(conversionUtility.toYmd(receiverItem.getReceiverDate()));
        receiverItem.setReadOnlyDeliveryNote(receiverItem.getDeliveryNote());

        return receiverItem;
    }

    /**
     * @param orderGoodsEntity     受注商品エンティティ
     * @param orderGoodsUpdateItem 修正中の受注商品情報アイテム
     * @return 商品アイテム
     */
    protected OrderGoodsUpdateItem toOrderGoodsUpdateItem(OrderGoodsEntity orderGoodsEntity,
                                                          OrderGoodsUpdateItem orderGoodsUpdateItem) {
        Set<String> ignoreFieldNameSet = new HashSet<>();
        ignoreFieldNameSet.add("messageCardOrderType");
        ignoreFieldNameSet.add("bagOrderType");
        ignoreFieldNameSet.add("packingOrderType");

        OrderGoodsUpdateItem goodsItem = ApplicationContextUtility.getBean(OrderGoodsUpdateItem.class);
        CopyUtil.copyProperties(orderGoodsEntity, goodsItem, ignoreFieldNameSet);

        // 価格(税込み)を設定
        goodsItem.setGoodsPriceInTax(calculatePriceUtility.getTaxIncludedPrice(orderGoodsEntity.getGoodsPrice(),
                                                                               orderGoodsEntity.getTaxRate()
                                                                              ));

        goodsItem.setUpdateGoodsCount(orderGoodsEntity.getGoodsCount().toString());
        // if (orderGoodsUpdateItem != null) {
        // goodsItem.setUpdateGoodsCount(orderGoodsUpdateItem.getUpdateGoodsCount());
        // }

        goodsItem.setOriginalUpdateGoodsCount(goodsItem.getUpdateGoodsCount());
        return goodsItem;
    }

    /**
     * 商品詳細リストを商品詳細マップに変換<br/>
     *
     * @param goodsDetailsList 商品詳細DTOリスト
     * @return 商品詳細マップ key:商品SEQ、value:商品詳細DTO
     */
    protected Map<Integer, GoodsDetailsDto> toGoodsDetailsMap(List<GoodsDetailsDto> goodsDetailsList) {
        Map<Integer, GoodsDetailsDto> goodsDetailsMap = new HashMap<>();
        for (GoodsDetailsDto goodsDetailsDto : goodsDetailsList) {
            goodsDetailsMap.put(goodsDetailsDto.getGoodsSeq(), goodsDetailsDto);
        }
        return goodsDetailsMap;
    }

    /**
     * エラーメッセージを受注詳細商品アイテムにセット<br/>
     *
     * @param orderGoodsMessageMap 商品エラーメッセージマップ
     * @param goodsItem            受注詳細商品アイテム
     * @param orderGoodsEntity     受注商品エンティティ
     * @param page                 受注詳細修正ページ
     * @param customParams         案件用引数
     */
    protected void setOrderGoodsMessageMap(Map<Integer, List<CheckMessageDto>> orderGoodsMessageMap,
                                           OrderGoodsUpdateItem goodsItem,
                                           OrderGoodsEntity orderGoodsEntity,
                                           DetailsUpdateModel page,
                                           Object... customParams) {
        if (orderGoodsMessageMap != null) {
            List<CheckMessageDto> messageList = orderGoodsMessageMap.get(orderGoodsEntity.getGoodsSeq());
            boolean errorflag = false;
            if (messageList != null) {
                StringBuilder errContent = new StringBuilder();
                for (int index = 0; index < messageList.size(); index++) {
                    CheckMessageDto messageDto = messageList.get(index);
                    if (messageDto != null && index > 0) {
                        errContent.append("<br />");
                    }
                    if (!errorflag) {
                        errorflag = messageDto.isError();
                    }
                    errContent.append(messageDto.getMessage());
                }
                goodsItem.setErrContent(errContent.toString());
                if (errorflag) {
                    String errorClass = page.getErrStyleClass();
                    String styleClass = goodsItem.getDiffGoodsClass();
                    goodsItem.setDiffGoodsClass(getClassValue(styleClass, errorClass));
                }
            }
        }
    }

    /**
     * スタイルクラスを返す
     *
     * @param settingStyleValue HTML設定スタイルクラス
     * @param styleValue        変更スタイルクラス
     * @return styleClass
     */
    public String getClassValue(String settingStyleValue, String styleValue) {
        if (settingStyleValue == null || settingStyleValue.isEmpty()) {
            return styleValue;
        } else if (styleValue == null || styleValue.isEmpty()) {
            return settingStyleValue;
        } else {
            return settingStyleValue + String.valueOf(styleValue.charAt(0)).toUpperCase() + styleValue.substring(1);
        }
    }

    /**
     * 削除対象の商品の数量を0に更新する<br/>
     *
     * @param model 受注詳細修正ページ
     */
    public void deleteGoods(DetailsUpdateModel model) {

        ReceiveOrderDto work = model.getModifiedReceiveOrder();
        ReceiveOrderDto base = model.getOriginalReceiveOrder();
        List<OrderGoodsEntity> workGoodsList = work.getOrderDeliveryDto().getOrderGoodsEntityList();
        List<OrderGoodsEntity> baseGoodsList = base.getOrderDeliveryDto().getOrderGoodsEntityList();
        List<OrderGoodsUpdateItem> goodsItems = model.getOrderReceiverItem().getOrderGoodsUpdateItems();
        Iterator<OrderGoodsEntity> baseIte = baseGoodsList.iterator();
        Iterator<OrderGoodsUpdateItem> goodsIte = goodsItems.iterator();

        for (int index = 0; index < workGoodsList.size(); index++) {
            OrderGoodsEntity orderGoods = workGoodsList.get(index);

            if (goodsIte.hasNext()) {

                OrderGoodsUpdateItem goodsItem = goodsIte.next();
                if (baseIte.hasNext()) {
                    baseIte.next();
                    if (goodsItem.isGoodsCheck()) {
                        orderGoods.setGoodsCount(BigDecimal.ZERO);
                        goodsItem.setUpdateGoodsCount("0");
                    }
                } else if (goodsItem.isGoodsCheck()) {
                    workGoodsList.remove(index);
                    goodsIte.remove();
                    index--;
                }
            }
        }
    }

    /**
     * 削除対象配送に紐づく商品の数量を0に更新する<br/>
     *
     * @param page 受注詳細修正ページ
     */
    public void deleteDelivery(DetailsUpdateModel page) {

        ReceiveOrderDto work = page.getModifiedReceiveOrder();
        ReceiveOrderDto base = page.getOriginalReceiveOrder();
        List<OrderGoodsEntity> workGoodsList = work.getOrderDeliveryDto().getOrderGoodsEntityList();
        List<OrderGoodsEntity> baseGoodsList = base.getOrderDeliveryDto().getOrderGoodsEntityList();
        List<OrderGoodsUpdateItem> goodsItems = page.getOrderReceiverItem().getOrderGoodsUpdateItems();
        Iterator<OrderGoodsEntity> baseIte = baseGoodsList.iterator();
        Iterator<OrderGoodsUpdateItem> goodsIte = goodsItems.iterator();

        for (int index = 0; index < workGoodsList.size(); index++) {
            OrderGoodsEntity orderGoods = workGoodsList.get(index);
            if (goodsIte.hasNext()) {
                OrderGoodsUpdateItem goodsItem = goodsIte.next();
                if (baseIte.hasNext()) {
                    baseIte.next();
                    orderGoods.setGoodsCount(BigDecimal.ZERO);
                    goodsItem.setUpdateGoodsCount("0");
                } else {
                    workGoodsList.remove(index);
                    goodsIte.remove();
                    index--;
                }
            }
        }
    }

    /**
     * 商品情報の整理<br />
     * 追加商品で数量0の商品をリストから削除
     *
     * @param page 受注詳細修正ページ
     */
    public void fixingGoods(DetailsUpdateModel page) {
        ReceiveOrderDto modified = page.getModifiedReceiveOrder();
        ReceiveOrderDto original = page.getOriginalReceiveOrder();

        List<OrderGoodsEntity> modifiedGoodsList = modified.getOrderDeliveryDto().getOrderGoodsEntityList();
        List<OrderGoodsEntity> originalGoodsList = original.getOrderDeliveryDto().getOrderGoodsEntityList();
        Iterator<OrderGoodsEntity> originalIte = originalGoodsList.iterator();

        for (int index = 0; index < modifiedGoodsList.size(); index++) {
            OrderGoodsEntity orderGoods = modifiedGoodsList.get(index);
            if (originalIte.hasNext()) {
                originalIte.next();
            } else if (BigDecimal.ZERO.equals(orderGoods.getGoodsCount())) {
                modifiedGoodsList.remove(index);
                index--;
            }
        }
    }

    /**
     * ページ内容を作業用DTOへセット<br/>
     *
     * @param page 受注詳細修正ページ
     */
    public void toModifiedReceiveOrderDto(DetailsUpdateModel page) {

        // 修正後受注DTO
        ReceiveOrderDto modified = page.getModifiedReceiveOrder();

        // 選択した決済方法SEQ設定
        setSelectSettlementMethodSeq(page);

        // ページから決済Dtoを取得
        SettlementDto settlementDto = getSettlementDto(page);

        // 受注インデックス設定
        setOrderIndex(page, modified, settlementDto);

        // 受注ご注文主設定
        setOrderPerson(page, modified);

        // 受注配送方法設定 / 受注商品設定
        setOrderDelivery(page, modified);

        // 受注メモ設定
        setOrderMemo(page, modified);

        // 受注決済方法設定
        setOrderSettlement(page, modified, settlementDto);

    }

    /**
     * ページ内容を作業用DTOへセット<br/>
     *
     * @param page 受注詳細修正ページ
     */
    public void toModifiedCouponLimitTarget(DetailsUpdateModel page) {

        // クーポン情報設定設定
        setCouponLimitTarget(page);

        this.toModifiedReceiveOrderDto(page);

    }

    /**
     * 選択した決済方法SEQ設定<br/>
     *
     * @param page         受注詳細修正ページ
     * @param customParams 案件用引数
     */
    protected void setSelectSettlementMethodSeq(DetailsUpdateModel page, Object... customParams) {
        String updateSettlementMethodSeq = page.getUpdateSettlementMethodSeq();
        if (updateSettlementMethodSeq != null) {
            // 選択された決済方法をセット
            page.setSettlementMethodSeq(Integer.parseInt(page.getUpdateSettlementMethodSeq()));
        } else {
            page.setSettlementMethodSeq(null);
        }
    }

    /**
     * 決済Dto取得<br/>
     *
     * @param page         受注詳細修正ページ
     * @param customParams 案件用引数
     * @return 決済Dto
     */
    protected SettlementDto getSettlementDto(DetailsUpdateModel page, Object... customParams) {
        String updateSettlementMethodSeq = page.getUpdateSettlementMethodSeq();
        Map<String, SettlementDto> settlementMethodDtoMap = page.getSettlementDtoMap();
        SettlementDto settlementDto = null;
        if (settlementMethodDtoMap != null) {
            // 決済Dtoマップから決済Dtoを取得
            settlementDto = settlementMethodDtoMap.get(updateSettlementMethodSeq);
        }
        return settlementDto;
    }

    /**
     * 受注インデックス設定要<br/>
     *
     * @param model         受注詳細修正ページ
     * @param modified      修正後受注DTO
     * @param settlementDto 決済DTO
     * @param customParams  案件用引数
     */
    protected void setOrderIndex(DetailsUpdateModel model,
                                 ReceiveOrderDto modified,
                                 SettlementDto settlementDto,
                                 Object... customParams) {
        OrderSummaryEntity orderSummary = modified.getOrderSummaryEntity();
        OrderIndexEntity orderIndex = modified.getOrderIndexEntity();

        if (model.isUpdateWaitingFlag()) {
            orderSummary.setWaitingFlag(HTypeWaitingFlag.ON);
        } else {
            orderSummary.setWaitingFlag(HTypeWaitingFlag.OFF);
        }
        String mailRequiredStatus = model.getUpdateMailRequired();

        // 受注修正時は決済マスタの督促/期限切れメールの値は参照しない
        if ("true".equals(mailRequiredStatus)) {
            orderIndex.setSettlementMailRequired(HTypeMailRequired.REQUIRED);
        } else {
            orderIndex.setSettlementMailRequired(HTypeMailRequired.NO_NEED);
        }

        orderIndex.setWaitingFlag(orderSummary.getWaitingFlag());

        if (model.getCouponLimitTargetTypeValue() != null) {
            if (HTypeCouponLimitTargetType.OFF.getValue().equals(model.getCouponLimitTargetTypeValue())) {
                orderIndex.setCouponLimitTargetType(HTypeCouponLimitTargetType.OFF);
            } else if (HTypeCouponLimitTargetType.ON_CHECKED.getValue().equals(model.getCouponLimitTargetTypeValue())) {
                orderIndex.setCouponLimitTargetType(HTypeCouponLimitTargetType.ON_CHECKED);
            }
        }

    }

    /**
     * 受注ご注文主設定<br/>
     *
     * @param model        受注詳細修正ページ
     * @param modified     修正後受注DTO
     * @param customParams 案件用引数
     */
    protected void setOrderPerson(DetailsUpdateModel model, ReceiveOrderDto modified, Object... customParams) {
        OrderPersonEntity orderPerson = modified.getOrderPersonEntity();
        orderPerson.setOrderLastName(model.getOrderLastName());
        orderPerson.setOrderFirstName(model.getOrderFirstName());
        orderPerson.setOrderLastKana(model.getOrderLastKana());
        orderPerson.setOrderFirstKana(model.getOrderFirstKana());
        orderPerson.setOrderMail(model.getOrderMail());
        HTypeAddressType addressType = HTypeAddressType.PC;
        orderPerson.setAddressType(addressType);
        if (StringUtils.isEmpty(model.getUpdateOrderSex())) {
            orderPerson.setOrderSex(HTypeOrderSex.UNKNOWN);
        } else {
            orderPerson.setOrderSex(EnumTypeUtil.getEnumFromValue(HTypeOrderSex.class, model.getUpdateOrderSex()));
        }
        orderPerson.setOrderBirthday(conversionUtility.toTimeStamp(model.getUpdateOrderBirthday()));
        orderPerson.setOrderAgeType(HTypeOrderAgeType.getType(model.getOrderBirthday()));
        orderPerson.setOrderTel(model.getOrderTel());
        orderPerson.setOrderContactTel(model.getOrderContactTel());
        orderPerson.setOrderZipCode(model.getOrderZipCode());
        orderPerson.setOrderPrefecture(model.getOrderPrefecture());
        HTypePrefectureType prefectureType =
                        EnumTypeUtil.getEnumFromLabel(HTypePrefectureType.class, model.getOrderPrefecture());
        if (prefectureType != null) {
            orderPerson.setPrefectureType(prefectureType);
            orderPerson.setOrderPrefecture(prefectureType.getLabel());
        }
        orderPerson.setOrderAddress1(model.getOrderAddress1());
        orderPerson.setOrderAddress2(model.getOrderAddress2());
        orderPerson.setOrderAddress3(model.getOrderAddress3());
    }

    /**
     * 受注配送方法設定<br/>
     *
     * @param page         受注詳細修正ページ
     * @param modified     修正後受注DTO
     * @param customParams 案件用引数
     */
    protected void setOrderDelivery(DetailsUpdateModel page, ReceiveOrderDto modified, Object... customParams) {
        BigDecimal orderGoodsCountTotal = BigDecimal.ZERO;

        OrderDeliveryDto orderDeliveryDto = modified.getOrderDeliveryDto();
        OrderDeliveryEntity orderDelivery = orderDeliveryDto.getOrderDeliveryEntity();

        orderDelivery.setReceiverLastName(page.getOrderReceiverItem().getReceiverLastName());
        orderDelivery.setReceiverFirstName(page.getOrderReceiverItem().getReceiverFirstName());
        orderDelivery.setReceiverLastKana(page.getOrderReceiverItem().getReceiverLastKana());
        orderDelivery.setReceiverFirstKana(page.getOrderReceiverItem().getReceiverFirstKana());
        orderDelivery.setReceiverTel(page.getOrderReceiverItem().getReceiverTel());
        orderDelivery.setReceiverZipCode(page.getOrderReceiverItem().getReceiverZipCode());
        orderDelivery.setReceiverPrefecture(page.getOrderReceiverItem().getReceiverPrefecture());
        orderDelivery.setReceiverAddress1(page.getOrderReceiverItem().getReceiverAddress1());
        orderDelivery.setReceiverAddress2(page.getOrderReceiverItem().getReceiverAddress2());
        orderDelivery.setReceiverAddress3(page.getOrderReceiverItem().getReceiverAddress3());
        orderDelivery.setDeliveryMethodSeq(
                        conversionUtility.toInteger(page.getOrderReceiverItem().getUpdateDeliveryMethodSeq()));
        orderDelivery.setDeliveryCode(page.getOrderReceiverItem().getDeliveryCode());
        orderDelivery.setReceiverDate(
                        conversionUtility.toTimeStamp(page.getOrderReceiverItem().getUpdateReceiverDate()));
        orderDelivery.setReceiverTimeZone(page.getOrderReceiverItem().getReceiverTimeZone());
        orderDelivery.setInvoiceAttachmentFlag(EnumTypeUtil.getEnumFromValue(HTypeInvoiceAttachmentFlag.class,
                                                                             page.getUpdateInvoiceAttachmentFlag()
                                                                            ));
        orderDelivery.setDeliveryNote(page.getOrderReceiverItem().getDeliveryNote());

        BigDecimal thisDeliveryGoodsCount = setOrderGoodsAndReturnOrderGoodsCountTotalOfThisList(page,
                                                                                                 orderDeliveryDto.getOrderGoodsEntityList(),
                                                                                                 page.getOrderReceiverItem()
                                                                                                     .getOrderGoodsUpdateItems()
                                                                                                );
        orderGoodsCountTotal = orderGoodsCountTotal.add(thisDeliveryGoodsCount);

        // 数量をセット
        page.setOrderGoodsCountTotal(orderGoodsCountTotal);
    }

    /**
     * 受注商品設定<br/>
     *
     * @param model                 受注詳細修正ページ
     * @param orderGoodsEntityList  受注商品リスト
     * @param orderGoodsUpdateItems 修正後受注商品リスト
     * @param customParams          案件用引数
     * @return BigDecimal 商品数量の合計
     */
    protected BigDecimal setOrderGoodsAndReturnOrderGoodsCountTotalOfThisList(DetailsUpdateModel model,
                                                                              List<OrderGoodsEntity> orderGoodsEntityList,
                                                                              List<OrderGoodsUpdateItem> orderGoodsUpdateItems,
                                                                              Object... customParams) {
        Iterator<OrderGoodsEntity> orderGoodsEntityIte = orderGoodsEntityList.iterator();
        BigDecimal orderGoodsCountTotalOfThisList = BigDecimal.ZERO;

        for (OrderGoodsUpdateItem pageItem : orderGoodsUpdateItems) {
            // 変換Helper取得
            ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

            OrderGoodsEntity orderGoods = orderGoodsEntityIte.next();
            orderGoods.setGoodsCount(conversionUtility.toBigDecimal(pageItem.getUpdateGoodsCount()));
            if (pageItem.getGoodsCount() != null) {
                orderGoodsCountTotalOfThisList = orderGoodsCountTotalOfThisList.add(pageItem.getGoodsCount());
            }

        }
        return orderGoodsCountTotalOfThisList;
    }

    /**
     * 受注メモ設定<br/>
     *
     * @param model        受注詳細修正ページ
     * @param modified     修正後受注DTO
     * @param customParams 案件用引数
     */
    protected void setOrderMemo(DetailsUpdateModel model, ReceiveOrderDto modified, Object... customParams) {
        OrderMemoEntity orderMemo = modified.getOrderMemoEntity();
        OrderSummaryEntity orderSummary = modified.getOrderSummaryEntity();
        if ((model.getMemo() != null && !model.getMemo().isEmpty()) || orderMemo != null) {
            if (orderMemo == null) {
                orderMemo = ApplicationContextUtility.getBean(OrderMemoEntity.class);
                orderMemo.setOrderSeq(orderSummary.getOrderSeq());
                modified.setOrderMemoEntity(orderMemo);
            }
            orderMemo.setMemo(model.getMemo());
        }
    }

    /**
     * 受注決済方法設定<br/>
     *
     * @param model         受注詳細修正ページ
     * @param modified      修正後受注DTO
     * @param settlementDto 決済DTO
     * @param customParams  案件用引数
     */
    protected void setOrderSettlement(DetailsUpdateModel model,
                                      ReceiveOrderDto modified,
                                      SettlementDto settlementDto,
                                      Object... customParams) {
        // 受注決済方法設定
        modified.getOrderSettlementEntity().setSettlementMethodSeq(model.getSettlementMethodSeq());

        // 受注詳細情報設定
        if (model.getSettlementMethodSeq() != null) {
            SettlementDetailsDto settlementDetailsDto = model.getSettlementDtoMap()
                                                             .get(String.valueOf(model.getSettlementMethodSeq()))
                                                             .getSettlementDetailsDto();

            // マルチペイメントHelper取得
            MulPayUtility mulPayUtility = ApplicationContextUtility.getBean(MulPayUtility.class);
            if (mulPayUtility.isMulPaySettlement(settlementDetailsDto.getSettlementMethodType())) {
                // マルチペイメント請求情報エンティティの設定
                setMulPayBillEntity(model, modified, settlementDto, settlementDetailsDto);
                return;
            }
        }
        // 受注DTOのマルチペイメント請求にnullを設定
        modified.setMulPayBillEntity(null);
    }

    /**
     * マルチペイメント請求情報エンティティの設定<br/>
     *
     * @param model                受注詳細修正ページ
     * @param modified             修正後受注DTO
     * @param settlementDto        決済DTO
     * @param settlementDetailsDto 決済詳細DTO
     * @param customParams         案件用引数
     */
    protected void setMulPayBillEntity(DetailsUpdateModel model,
                                       ReceiveOrderDto modified,
                                       SettlementDto settlementDto,
                                       SettlementDetailsDto settlementDetailsDto,
                                       Object... customParams) {
        // マルチペイメント請求情報エンティティ取得
        MulPayBillEntity mulPayBillEntity = getMulPayBillEntity(model, modified);

        // コンビニ決済の場合
        if (settlementDto != null
            && settlementDetailsDto.getSettlementMethodType() == HTypeSettlementMethodType.CONVENIENCE) {
            // 選択コンビニ名を設定
            setConveniName(model, settlementDto, mulPayBillEntity);
        }

        // 受注DTOに設定
        modified.setMulPayBillEntity(mulPayBillEntity);
    }

    /**
     * マルチペイメント請求情報エンティティ取得<br/>
     *
     * @param model        受注詳細修正ページ
     * @param modified     修正後受注DTO
     * @param customParams 案件用引数
     * @return マルチペイメント請求情報エンティティ
     */
    protected MulPayBillEntity getMulPayBillEntity(DetailsUpdateModel model,
                                                   ReceiveOrderDto modified,
                                                   Object... customParams) {
        MulPayBillEntity mulPayBillEntity = ApplicationContextUtility.getBean(MulPayBillEntity.class);
        if (modified.getMulPayBillEntity() != null) {
            mulPayBillEntity = modified.getMulPayBillEntity();
        } else if (model.getSettlementMethodSeq()
                        .equals(model.getOriginalReceiveOrder().getOrderSettlementEntity().getSettlementMethodSeq())) {
            // 決済方法を一度変更してみたが元に戻した場合
            mulPayBillEntity = model.getOriginalReceiveOrder().getMulPayBillEntity();
        }
        return mulPayBillEntity;
    }

    /**
     * 選択コンビニ名を設定<br/>
     *
     * @param model            受注詳細修正ページ
     * @param settlementDto    決済DTO
     * @param mulPayBillEntity マルチペイメント請求エンティティ
     * @param customParams     案件用引数
     */
    protected void setConveniName(DetailsUpdateModel model,
                                  SettlementDto settlementDto,
                                  MulPayBillEntity mulPayBillEntity,
                                  Object... customParams) {
        // 選択コンビニ名を設定
        String conveniName = "";
        // コンビニエンティティリスト取得
        List<ConvenienceEntity> convenienceEntityList = settlementDto.getConvenienceEntityList();
        for (ConvenienceEntity convenienceEntity : convenienceEntityList) {
            if (convenienceEntity.getConveniCode().toString().equals(model.getConvenience())) {
                // 選択コンビニコード設定
                mulPayBillEntity.setConvenience(convenienceEntity.getConveniCode());
                // コンビニ名をPage（サブアプリケーションスコープ）に保持
                conveniName = convenienceEntity.getConveniName();
                break;
            }
        }
        model.setConveniName(conveniName);
    }

    /**
     * @param model           受注詳細修正ページ
     * @param deliveryDtoList 選択可能配送DTOリスト
     */
    public void toDeliveryList(DetailsUpdateModel model, List<DeliveryDto> deliveryDtoList) {
        Map<Object, Object> map = new HashMap<>();

        // 修正前の決済方法
        Integer originDeliverySeq = model.getOriginalReceiveOrder()
                                         .getOrderDeliveryDto()
                                         .getOrderDeliveryEntity()
                                         .getDeliveryMethodSeq();
        Integer deliveryMethodSeq = model.getOrderReceiverItem().getDeliveryMethodSeq();
        model.getModifiedReceiveOrder().getOrderDeliveryDto().setDeliveryDtoList(deliveryDtoList);
        for (DeliveryDto deliveryDto : deliveryDtoList) {
            if (deliveryDto == null) {
                continue;
            }
            DeliveryDetailsDto deliveryDetailsDto = deliveryDto.getDeliveryDetailsDto();
            if (deliveryDto.isSelectClass() || (originDeliverySeq.equals(deliveryDetailsDto.getDeliveryMethodSeq()))
                || (deliveryMethodSeq != null && deliveryMethodSeq.equals(deliveryDetailsDto.getDeliveryMethodSeq()))) {
                map.put(deliveryDetailsDto.getDeliveryMethodSeq(), deliveryDetailsDto.getDeliveryMethodName());
            }
        }
        model.getOrderReceiverItem().setUpdateDeliveryMethodSeqItems(map);
    }

    /**
     * お届け時間帯選択アイテムリスト作成<br/>
     *
     * @param page                 受注詳細ページ
     * @param deliveryMethodEntity 配送方法エンティティ
     */
    public void setTimeZoneItem(DetailsUpdateModel page, DeliveryMethodEntity deliveryMethodEntity) {
        if (deliveryMethodEntity == null) {
            return;
        }
        Map<String, String> list = new HashMap<>();
        if (deliveryMethodEntity.getReceiverTimeZone1() != null) {
            list.put(deliveryMethodEntity.getReceiverTimeZone1(), deliveryMethodEntity.getReceiverTimeZone1());
        }
        if (deliveryMethodEntity.getReceiverTimeZone2() != null) {
            list.put(deliveryMethodEntity.getReceiverTimeZone2(), deliveryMethodEntity.getReceiverTimeZone2());
        }
        if (deliveryMethodEntity.getReceiverTimeZone3() != null) {
            list.put(deliveryMethodEntity.getReceiverTimeZone3(), deliveryMethodEntity.getReceiverTimeZone3());
        }
        if (deliveryMethodEntity.getReceiverTimeZone4() != null) {
            list.put(deliveryMethodEntity.getReceiverTimeZone4(), deliveryMethodEntity.getReceiverTimeZone4());
        }
        if (deliveryMethodEntity.getReceiverTimeZone5() != null) {
            list.put(deliveryMethodEntity.getReceiverTimeZone5(), deliveryMethodEntity.getReceiverTimeZone5());
        }
        if (deliveryMethodEntity.getReceiverTimeZone6() != null) {
            list.put(deliveryMethodEntity.getReceiverTimeZone6(), deliveryMethodEntity.getReceiverTimeZone6());
        }
        if (deliveryMethodEntity.getReceiverTimeZone7() != null) {
            list.put(deliveryMethodEntity.getReceiverTimeZone7(), deliveryMethodEntity.getReceiverTimeZone7());
        }
        if (deliveryMethodEntity.getReceiverTimeZone8() != null) {
            list.put(deliveryMethodEntity.getReceiverTimeZone8(), deliveryMethodEntity.getReceiverTimeZone8());
        }
        if (deliveryMethodEntity.getReceiverTimeZone9() != null) {
            list.put(deliveryMethodEntity.getReceiverTimeZone9(), deliveryMethodEntity.getReceiverTimeZone9());
        }
        if (deliveryMethodEntity.getReceiverTimeZone10() != null) {
            list.put(deliveryMethodEntity.getReceiverTimeZone10(), deliveryMethodEntity.getReceiverTimeZone10());
        }
        page.getOrderReceiverItem().setReceiverTimeZoneItems(list);
    }

    /**
     * @param timeZone お届け時間帯
     * @return Map
     */
    protected Map<Object, Object> getTimeZoneMap(String timeZone) {
        if (timeZone == null) {
            return null;
        }
        Map<Object, Object> map = new HashMap<>();
        map.put("label", timeZone);
        map.put("value", timeZone);
        return map;
    }

    /**
     * @param model             受注詳細修正ページ
     * @param settlementDtoList 選択可能決済DTOリスト
     */
    public void toSettlementList(DetailsUpdateModel model, List<SettlementDto> settlementDtoList) {

        Map<String, String> map = new HashMap<>();
        // 修正前の決済方法
        Integer originSettlementSeq =
                        model.getOriginalReceiveOrder().getOrderSettlementEntity().getSettlementMethodSeq();
        // // 現在の決済方法
        // Integer settlementMethodSeq = page.getSettlementMethodSeq();
        model.setSettlementDtoMap(new HashMap<>());

        String creditSettlementMethodSeqList = "";
        String conveniSettlementMethodSeqList = "";
        String payeasySettlementMethodSeqList = "";
        String mailRequiredSettlementMethodSeqList = "";
        for (SettlementDto settlementDto : settlementDtoList) {
            if (settlementDto == null) {
                continue;
            }
            SettlementDetailsDto settlementDetailsDto = settlementDto.getSettlementDetailsDto();

            // クレジット・コンビニチェック
            boolean credit = false;
            if (HTypeSettlementMethodType.CREDIT.equals(settlementDetailsDto.getSettlementMethodType())
                || HTypeSettlementMethodType.AMAZON_PAYMENT == settlementDetailsDto.getSettlementMethodType()) {
                credit = true;
            }
            // 選択可能な決済方法かつ、クレジット以外の決済方法である
            // または、修正前の決済である
            if ((settlementDto.isSelectClass() && !credit) || originSettlementSeq.equals(
                            settlementDetailsDto.getSettlementMethodSeq())) {
                map.put(settlementDetailsDto.getSettlementMethodSeq().toString(),
                        settlementDetailsDto.getSettlementMethodName()
                       );
            }

            // クレジット決済・コンビニ決済・Pay-easy決済リストへの設定
            if (HTypeSettlementMethodType.CREDIT.equals(settlementDetailsDto.getSettlementMethodType())) {
                if (!"".equals(creditSettlementMethodSeqList)) {
                    creditSettlementMethodSeqList += ",";
                }
                creditSettlementMethodSeqList += settlementDetailsDto.getSettlementMethodSeq().toString();
            }
            if (HTypeSettlementMethodType.CONVENIENCE.equals(settlementDetailsDto.getSettlementMethodType())) {
                if (!"".equals(conveniSettlementMethodSeqList)) {
                    conveniSettlementMethodSeqList += ",";
                }
                conveniSettlementMethodSeqList += settlementDetailsDto.getSettlementMethodSeq().toString();
            }
            if (HTypeSettlementMethodType.PAY_EASY.equals(settlementDetailsDto.getSettlementMethodType())) {
                if (!"".equals(payeasySettlementMethodSeqList)) {
                    payeasySettlementMethodSeqList += ",";
                }
                payeasySettlementMethodSeqList += settlementDetailsDto.getSettlementMethodSeq().toString();
            }

            // メール要否設定可能決済リスト
            //
            // 受注修正時は決済マスタの督促/期限切れメールの値は参照しない
            // 前請求決済選択時は無条件で表示する仕様に修正
            // mailRequiredSettlementMethodSeqListは受注修正画面の督促/期限切れメール項目の表示制御に使用
            //
            // PKGTODO tezuka-mk
            // HTypeBillType.PRE_CLAIMをコメントアウトすると、前請求＆クレジットの条件で分岐している箇所が多い。
            // 前請求と言いながら入金を待たず即時売上しているからと思うが。可能な限りTypeで吸収したい
            // 前請求 かつ 「クレジット または 全額割引決済」以外の場合、表示する
            if (HTypeBillType.PRE_CLAIM == settlementDetailsDto.getBillType()
                && HTypeSettlementMethodType.CREDIT != settlementDetailsDto.getSettlementMethodType()
                && HTypeSettlementMethodType.DISCOUNT != settlementDetailsDto.getSettlementMethodType()) {
                if (!"".equals(mailRequiredSettlementMethodSeqList)) {
                    mailRequiredSettlementMethodSeqList += ",";
                }
                mailRequiredSettlementMethodSeqList += settlementDetailsDto.getSettlementMethodSeq().toString();
            }
            model.getSettlementDtoMap().put(settlementDetailsDto.getSettlementMethodSeq().toString(), settlementDto);
        }
        model.setOriginalSettlementMethodSeq(originSettlementSeq.toString());
        model.setCreditSettlementMethodSeqList(creditSettlementMethodSeqList);
        model.setConveniSettlementMethodSeqList(conveniSettlementMethodSeqList);
        model.setPayeasySettlementMethodSeqList(payeasySettlementMethodSeqList);
        model.setMailRequiredSettlementMethodSeqList(mailRequiredSettlementMethodSeqList);
        model.setUpdateSettlementMethodSeqItems(map);
    }

    /**
     * 受注商品の商品SEQリストを取得<br/>
     *
     * @param receiveOrderDto 受注DTO
     * @return 商品SEQリスト
     */
    public List<Integer> getOrderGoodsSeqList(ReceiveOrderDto receiveOrderDto) {
        List<OrderGoodsEntity> orderGoodsEntityList = orderUtility.getALLGoodsEntityList(receiveOrderDto);
        List<Integer> goodsSeqList = new ArrayList<>();

        for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {
            goodsSeqList.add(orderGoodsEntity.getGoodsSeq());
        }
        return goodsSeqList;
    }

    /**
     * お届け時間帯動的チェック用<br/>
     * 配送方法にお届け時間帯が設定されているかを判定しておく<br/>
     *
     * @param map                  お届け時間帯動的チェック用リスト
     * @param deliveryMethodEntity 配送方法エンティティ
     * @param deliveryMethodSeq    配送方法Seq
     * @return お届け時間帯動的チェック用リスト
     */
    public Map<Object, Object> toTimeZoneDeleveryList(Map<Object, Object> map,
                                                      DeliveryMethodEntity deliveryMethodEntity,
                                                      Integer deliveryMethodSeq) {

        map.put("deliveryMethodSeq", deliveryMethodSeq);

        if (deliveryMethodEntity.getReceiverTimeZone1() != null || deliveryMethodEntity.getReceiverTimeZone2() != null
            || deliveryMethodEntity.getReceiverTimeZone3() != null
            || deliveryMethodEntity.getReceiverTimeZone4() != null
            || deliveryMethodEntity.getReceiverTimeZone5() != null
            || deliveryMethodEntity.getReceiverTimeZone6() != null
            || deliveryMethodEntity.getReceiverTimeZone7() != null
            || deliveryMethodEntity.getReceiverTimeZone8() != null
            || deliveryMethodEntity.getReceiverTimeZone9() != null
            || deliveryMethodEntity.getReceiverTimeZone10() != null) {

            // お届け時間帯が設定されている
            map.put("existTimeZone", true);
        } else {
            // お届け時間帯が設定されていない
            map.put("existTimeZone", false);
        }

        return map;
    }

    /**
     * Set Original Receivber data to page in case of Amazon<br/>
     *
     * @param detailsupdatePage detailsupdatePage
     */
    public void setOriginalReceiverData(DetailsUpdateModel detailsupdatePage) {
        SettlementDetailsDto settlementDetailsDto = detailsupdatePage.getSettlementDtoMap()
                                                                     .get(detailsupdatePage.getUpdateSettlementMethodSeq())
                                                                     .getSettlementDetailsDto();
        // In case of Amazon User can not modify receiver details.
        // Hence set the original values.
        if (HTypeSettlementMethodType.AMAZON_PAYMENT == settlementDetailsDto.getSettlementMethodType()) {
            ReceiveOrderDto original = detailsupdatePage.getOriginalReceiveOrder();
            if (original.getOrderDeliveryDto() != null
                && original.getOrderDeliveryDto().getOrderDeliveryEntity() != null) {

                OrderDeliveryEntity orderDeliveryEntity = original.getOrderDeliveryDto().getOrderDeliveryEntity();
                OrderReceiverUpdateItem receiverItem = detailsupdatePage.getOrderReceiverItem();

                receiverItem.setReceiverLastName(orderDeliveryEntity.getReceiverLastName());
                receiverItem.setReceiverLastKana(orderDeliveryEntity.getReceiverLastKana());
                receiverItem.setReceiverTel(orderDeliveryEntity.getReceiverTel());
                receiverItem.setReceiverFirstName(orderDeliveryEntity.getReceiverFirstName());
                receiverItem.setReceiverFirstKana(orderDeliveryEntity.getReceiverFirstKana());
                receiverItem.setReceiverZipCode(orderDeliveryEntity.getReceiverZipCode());
                receiverItem.setReceiverPrefecture(orderDeliveryEntity.getReceiverPrefecture());
                receiverItem.setReceiverAddress1(orderDeliveryEntity.getReceiverAddress1());
                receiverItem.setReceiverAddress2(orderDeliveryEntity.getReceiverAddress2());
                receiverItem.setReceiverAddress3(orderDeliveryEntity.getReceiverAddress3());
            }
        }
    }

    /**
     * 変更後クーポン状態設定<br/>
     *
     * @param page         受注詳細修正ページ
     * @param customParams 案件用引数
     */
    protected void setCouponLimitTarget(DetailsUpdateModel page, Object... customParams) {
        String couponLimitTargetTypeValue = page.getCouponLimitTargetTypeValue();
        if (HTypeCouponLimitTargetType.OFF.getValue().equals(couponLimitTargetTypeValue)) {
            couponLimitTargetTypeValue = HTypeCouponLimitTargetType.ON_CHECKED.getValue();
        } else {
            couponLimitTargetTypeValue = HTypeCouponLimitTargetType.OFF.getValue();
        }

        page.setCouponLimitTargetTypeValue(couponLimitTargetTypeValue);
    }

    /**
     * クーポン対象商品設定<br/>
     *
     * @param page   ページクラス
     * @param coupon クーポンエンティティ
     */
    public void setCouponTargetGoodsForJs(DetailsUpdateModel page, CouponEntity coupon) {
        // 全商品対象の場合
        if (StringUtils.isEmpty(coupon.getTargetGoods())) {
            page.setCouponTargetGoodsIsAllFlg(true);
            return;
        }

        List<String> targetGoodsList = Arrays.asList(conversionUtility.toDivArray(coupon.getTargetGoods()));
        Map<String, String> couponTargetGoodsMap = new LinkedHashMap<>();
        // クーポン対象の商品コードと商品名を取得
        for (OrderGoodsUpdateItem orderGoodsUpdateItem : page.getOrderReceiverItem().getOrderGoodsUpdateItems()) {
            if (targetGoodsList.contains(orderGoodsUpdateItem.getGoodsCode())) {
                couponTargetGoodsMap.put(orderGoodsUpdateItem.getGoodsCode(), orderGoodsUpdateItem.getGoodsGroupName());
            }
        }
        // JS用に文字列に変換する。
        String couponTargetGoodsCode = null;
        String couponTargetGoodsName = null;
        for (Entry<String, String> goodsCodeEntry : couponTargetGoodsMap.entrySet()) {
            if (couponTargetGoodsCode == null) {
                couponTargetGoodsCode = goodsCodeEntry.getKey();
                couponTargetGoodsName = goodsCodeEntry.getValue();
            } else {
                couponTargetGoodsCode = couponTargetGoodsCode + "," + goodsCodeEntry.getKey();
                couponTargetGoodsName = couponTargetGoodsName + "," + goodsCodeEntry.getValue();
            }
        }

        page.setCouponTargetGoodsName(couponTargetGoodsName);
        page.setCouponTargetGoodsCode(couponTargetGoodsCode);
    }

    /**********************************************************************************
     * 受注修正確認
     **********************************************************************************/
    /**
     * 処理履歴詳細ページ項目設定<br/>
     *
     * @param detailsUpdateModel 処理履歴詳細ページ
     * @param receiveOrderDto    受注DTO
     */
    protected void toPageConfirm(DetailsUpdateModel detailsUpdateModel, ReceiveOrderDto receiveOrderDto) {

        putOrderSummaryInOrderConfirm(receiveOrderDto);
        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();

        Set<String> ignoreFieldNameSet = new HashSet<>();

        // 受注インデックス（受注履歴連番をセット)
        ignoreFieldNameSet.addAll(CopyUtil.copyProperties(receiveOrderDto.getOrderIndexEntity(), detailsUpdateModel,
                                                          ignoreFieldNameSet
                                                         ));

        // 受注お客様情報の値をセット
        ignoreFieldNameSet.addAll(CopyUtil.copyProperties(receiveOrderDto.getOrderPersonEntity(), detailsUpdateModel,
                                                          ignoreFieldNameSet
                                                         ));

        // 受注配送情報、受注商品情報の値をセット
        setOrderDeliveryDtoConfirm(detailsUpdateModel, receiveOrderDto);

        // 受注決済の値をセット
        ignoreFieldNameSet.addAll(
                        CopyUtil.copyProperties(receiveOrderDto.getOrderSettlementEntity(), detailsUpdateModel,
                                                ignoreFieldNameSet
                                               ));
        // 商品金額合計(税込み)を設定
        detailsUpdateModel.setPostTaxGoodsPriceTotal(
                        calculatePriceUtility.getTaxIncludedGoodsPriceTotal(receiveOrderDto));

        // 受注請求の値をセット
        ignoreFieldNameSet.addAll(CopyUtil.copyProperties(receiveOrderDto.getOrderBillEntity(), detailsUpdateModel,
                                                          ignoreFieldNameSet
                                                         ));

        // 受注メモの値をセット
        if (receiveOrderDto.getOrderMemoEntity() != null) {
            ignoreFieldNameSet.addAll(CopyUtil.copyProperties(receiveOrderDto.getOrderMemoEntity(), detailsUpdateModel,
                                                              ignoreFieldNameSet
                                                             ));
        }

        CouponEntity coupon = receiveOrderDto.getCoupon();
        if (coupon != null) {
            // クーポンが適用されている場合のみクーポン情報を画面に反映する
            // クーポン名
            detailsUpdateModel.setCouponName(coupon.getCouponDisplayNamePC());
            // クーポン割引額 画面上マイナス表示
            detailsUpdateModel.setCouponDiscountPrice(
                            receiveOrderDto.getOrderSettlementEntity().getCouponDiscountPrice().negate());
            // 適用クーポン名
            detailsUpdateModel.setApplyCouponName(coupon.getCouponName());
            // 適用クーポンID
            detailsUpdateModel.setApplyCouponId(coupon.getCouponId());
            // クーポン利用制限フラグ
            detailsUpdateModel.setCouponLimitTargetType(
                            receiveOrderDto.getOrderIndexEntity().getCouponLimitTargetType());
            // クーポン対象商品適用
            setCouponTargetGoods(detailsUpdateModel, coupon);

        } else {
            // クーポンが適用されていない場合はクーポン情報を初期化する
            // クーポン割引額 割引額を0円として処理する
            detailsUpdateModel.setCouponDiscountPrice(BigDecimal.ZERO);
            // 適用クーポン名
            detailsUpdateModel.setApplyCouponName(null);
            // 適用クーポンID
            detailsUpdateModel.setApplyCouponId(null);
        }

        // 受注サマリの値をセット
        ignoreFieldNameSet.addAll(CopyUtil.copyProperties(orderSummaryEntity, detailsUpdateModel, ignoreFieldNameSet));
        detailsUpdateModel.setSettlementMailRequired(receiveOrderDto.getOrderIndexEntity().getSettlementMailRequired());

        // 受注入金の値をセット
        List<OrderReceiptOfMoneyEntity> orderReceiptOfMoneyEntityList =
                        receiveOrderDto.getOrderReceiptOfMoneyEntityList();
        OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity =
                        ApplicationContextUtility.getBean(OrderReceiptOfMoneyEntity.class);
        if (orderReceiptOfMoneyEntityList != null && !orderReceiptOfMoneyEntityList.isEmpty()) {
            int index = orderReceiptOfMoneyEntityList.size() - 1;
            orderReceiptOfMoneyEntity = orderReceiptOfMoneyEntityList.get(index);
        }
        ignoreFieldNameSet.addAll(
                        CopyUtil.copyProperties(orderReceiptOfMoneyEntity, detailsUpdateModel, ignoreFieldNameSet));

        // マルチペイメント請求情報の値をセット
        if (receiveOrderDto.getMulPayBillEntity() != null) {
            ignoreFieldNameSet.addAll(CopyUtil.copyProperties(receiveOrderDto.getMulPayBillEntity(), detailsUpdateModel,
                                                              ignoreFieldNameSet
                                                             ));

            if (detailsUpdateModel.getConvenience() != null) {
                String conveniName = "";
                for (ConvenienceEntity ce : detailsUpdateModel.getConvenienceAllList()) {
                    if (detailsUpdateModel.getConvenience() != null && detailsUpdateModel.getConvenience()
                                                                                         .equals(ce.getConveniCode())) {
                        conveniName = orderUtility.getConveniName(ce);
                        break;
                    }
                }
                detailsUpdateModel.setConveniName(conveniName);
            }
        }

        // 入金状態をセット
        detailsUpdateModel.setPaymentStatus(getPaymentStatus(orderSummaryEntity, orderReceiptOfMoneyEntity));

        // 受注追加料金をセット
        setOrderAdditionalChargeDtoListConfirm(detailsUpdateModel, receiveOrderDto);

        // 決済方法名をセット
        detailsUpdateModel.setSettlementMethodName(
                        receiveOrderDto.getSettlementMethodEntity().getSettlementMethodName());
        detailsUpdateModel.setCardBrandDisplayPc(receiveOrderDto.getOrderBillEntity().getCardBrandDisplayPC());

        // 請求金額に受注金額をセット
        // キャンセル後に表示される値がマイナス値になってしまう為
        detailsUpdateModel.setBillPrice(orderSummaryEntity.getOrderPrice());

        // オーソリ期限日（決済日付＋オーソリ保持期間）
        detailsUpdateModel.setAuthoryLimitDate(receiveOrderDto.getAuthoryLimitDate());

        // 注文保留理由
        detailsUpdateModel.setOrderWaitingMemo(receiveOrderDto.getOrderIndexEntity().getOrderWaitingMemo());

        /*
         * 修正元の情報を取得するが、ordersummary テーブルは履歴管理していないため 実際は original も modified
         * も同じ内容がセットされている。 受注金額などは後で計算してセットし直しているので注意。
         */
        ReceiveOrderDto original = detailsUpdateModel.getOriginalReceiveOrder();
        ReceiveOrderDto modified = detailsUpdateModel.getModifiedReceiveOrder();

        // 選択されたレコードが「キャンセル」の時
        if (detailsUpdateModel.getCancelTime() != null) {
            /*
             * orderindex テーブルの処理時間 >= ordersummary テーブルのキャンセル日時の場合 ordersummary
             * のキャンセル日時以降の処理は全てキャンセル扱い。
             */
            if (detailsUpdateModel.isStateCancel()) {
                /*
                 * orderindex テーブルの処理時間 == ordersummary テーブルのキャンセル日時の場合のみ
                 * キャンセル日時をクリアする。
                 */
                if (!detailsUpdateModel.isStateAfterCancel()) {
                    original.getOrderSummaryEntity().setCancelTime(null);
                }
            } else {
                detailsUpdateModel.setCancelTime(null);
                // 1 件目の時
                if (original == null) {
                    return;
                }
                original.getOrderSummaryEntity().setCancelTime(null);
                modified.getOrderSummaryEntity().setCancelTime(null);
            }
        }
    }

    /**
     * 受注サマリー情報の精査<br/>
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void putOrderSummaryInOrderConfirm(ReceiveOrderDto receiveOrderDto) {
        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();
        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();
        OrderBillEntity orderBillEntity = receiveOrderDto.getOrderBillEntity();
        List<OrderReceiptOfMoneyEntity> orderReceiptOfMoneyEntityList =
                        receiveOrderDto.getOrderReceiptOfMoneyEntityList();

        OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity =
                        ApplicationContextUtility.getBean(OrderReceiptOfMoneyEntity.class);
        if (orderReceiptOfMoneyEntityList != null && !orderReceiptOfMoneyEntityList.isEmpty()) {
            int index = orderReceiptOfMoneyEntityList.size() - 1;
            orderReceiptOfMoneyEntity = orderReceiptOfMoneyEntityList.get(index);
        }
        orderSummaryEntity.setReceiptPriceTotal(orderReceiptOfMoneyEntity.getReceiptPriceTotal());
        orderSummaryEntity.setOrderPrice(orderSettlementEntity.getOrderPrice());
        setOrderStatus(orderSummaryEntity, orderSettlementEntity, orderBillEntity, orderReceiptOfMoneyEntity,
                       receiveOrderDto.getOrderDeliveryDto()
                      );
    }

    /**
     * 受注状態を設定<br/>
     *
     * @param orderSummaryEntity        受注サマリー
     * @param orderSettlementEntity     受注決済
     * @param orderBillEntity           受注請求
     * @param orderReceiptOfMoneyentity 受注入金
     * @param orderDeliveryDto          受注配送Dto
     */
    protected void setOrderStatus(OrderSummaryEntity orderSummaryEntity,
                                  OrderSettlementEntity orderSettlementEntity,
                                  OrderBillEntity orderBillEntity,
                                  OrderReceiptOfMoneyEntity orderReceiptOfMoneyentity,
                                  OrderDeliveryDto orderDeliveryDto) {
        // 入金累計額
        BigDecimal receiptPriceTotal = orderReceiptOfMoneyentity.getReceiptPriceTotal();
        // 請求金額
        BigDecimal billPrice = orderBillEntity.getBillPrice();
        // 請求種別
        HTypeBillType billtype = orderSettlementEntity.getBillType();
        // 受注状態
        HTypeOrderStatus orderstatus;

        // 請求種別=前払い 且つ 請求金額 != 入金累計額
        if (HTypeBillType.PRE_CLAIM.equals(billtype) && !billPrice.equals(receiptPriceTotal)) {
            orderstatus = HTypeOrderStatus.PAYMENT_CONFIRMING;
        } else {
            orderstatus = HTypeOrderStatus.GOODS_PREPARING;
        }

        // 受注状態を設定
        orderSummaryEntity.setOrderStatus(orderUtility.getOrderStatusByOrderDelivery(orderstatus, orderDeliveryDto));

    }

    /**
     * 追加料金情報リストをセット<br/>
     *
     * @param detailsUpdateModel セットするページ
     * @param receiveOrderDto    受注DTO
     */
    protected void setOrderAdditionalChargeDtoListConfirm(DetailsUpdateModel detailsUpdateModel,
                                                          ReceiveOrderDto receiveOrderDto) {
        List<OrderAdditionalChargeItem> orderAdditionalChargeItems = new ArrayList<>();
        List<OrderAdditionalChargeEntity> orderAdditionalChargeEntityList =
                        receiveOrderDto.getOrderAdditionalChargeEntityList();
        for (OrderAdditionalChargeEntity entity : orderAdditionalChargeEntityList) {
            OrderAdditionalChargeItem item = toOrderAdditionalChargeItem(entity);
            orderAdditionalChargeItems.add(item);
        }
        detailsUpdateModel.setOrderAdditionalChargeItems(orderAdditionalChargeItems);
    }

    /**
     * 受注配送情報をセット<br/>
     * 配送情報内の商品情報リストをセット<br/>
     *
     * @param detailsUpdateModel セットするページ
     * @param receiveOrderDto    受注DTO
     */
    protected void setOrderDeliveryDtoConfirm(DetailsUpdateModel detailsUpdateModel, ReceiveOrderDto receiveOrderDto) {
        // 商品メッセージを取得
        OrderMessageDto orderMessageDto = detailsUpdateModel.getOrderMessageDto();
        Map<Integer, Map<Integer, List<CheckMessageDto>>> orderGoodsMessageMap =
                        orderMessageDto.getOrderGoodsMessageMapMap();

        // お届け先情報リストをセット
        BigDecimal orderGoodsCountTotal = BigDecimal.ZERO;
        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

        // 納品書 全配送共通の為、itemでは保持しない
        detailsUpdateModel.setInvoiceAttachmentFlag(
                        orderDeliveryDto.getOrderDeliveryEntity().getInvoiceAttachmentFlag());
        OrderReceiverUpdateItem receiverItem = toOrderReceiverItemConfirm(orderDeliveryDto.getOrderDeliveryEntity(),
                                                                          orderDeliveryDto.getDeliveryMethodEntity()
                                                                         );

        // 商品情報リストをセット
        List<OrderGoodsEntity> orderGoodsList = orderDeliveryDto.getOrderGoodsEntityList();
        List<OrderGoodsUpdateItem> orderGoodsItems = new ArrayList<>();
        Integer consecutiveNo = orderDeliveryDto.getOrderDeliveryEntity().getOrderConsecutiveNo();
        for (OrderGoodsEntity orderGoodsEntity : orderGoodsList) {
            OrderGoodsUpdateItem goodsItem = toOrderGoodsUpdateItem(orderGoodsEntity);
            setOrderGoodsMessageMap(
                            orderGoodsMessageMap.get(consecutiveNo), goodsItem, orderGoodsEntity, detailsUpdateModel);
            orderGoodsItems.add(goodsItem);
            orderGoodsCountTotal = orderGoodsCountTotal.add(orderGoodsEntity.getGoodsCount());
        }

        // 配送アイテムに商品アイテムリストをセット
        receiverItem.setOrderGoodsUpdateItems(orderGoodsItems);

        detailsUpdateModel.setOrderReceiverItem(receiverItem);
        detailsUpdateModel.setOrderGoodsCountTotal(orderGoodsCountTotal);
    }

    /**
     * @param orderDeliveryEntity  受注配送エンティティ
     * @param deliveryMethodEntity 配送エンティティ
     * @return お届け先アイテム
     */
    protected OrderReceiverUpdateItem toOrderReceiverItemConfirm(OrderDeliveryEntity orderDeliveryEntity,
                                                                 DeliveryMethodEntity deliveryMethodEntity) {
        OrderReceiverUpdateItem receiverItem = ApplicationContextUtility.getBean(OrderReceiverUpdateItem.class);
        CopyUtil.copyProperties(orderDeliveryEntity, receiverItem, null);
        CopyUtil.copyProperties(deliveryMethodEntity, receiverItem, null);
        return receiverItem;
    }

    /**
     * @param orderGoodsEntity 受注商品エンティティ
     * @return 商品アイテム
     */
    protected OrderGoodsUpdateItem toOrderGoodsUpdateItem(OrderGoodsEntity orderGoodsEntity) {
        Set<String> ignoreFieldNameSet = new HashSet<>();
        ignoreFieldNameSet.add("messageCardOrderType");
        ignoreFieldNameSet.add("bagOrderType");
        ignoreFieldNameSet.add("packingOrderType");

        OrderGoodsUpdateItem goodsItem = ApplicationContextUtility.getBean(OrderGoodsUpdateItem.class);
        CopyUtil.copyProperties(orderGoodsEntity, goodsItem, ignoreFieldNameSet);

        // 価格(税込み)を設定
        goodsItem.setGoodsPriceInTax(calculatePriceUtility.getTaxIncludedPrice(orderGoodsEntity.getGoodsPrice(),
                                                                               orderGoodsEntity.getTaxRate()
                                                                              ));

        return goodsItem;
    }

    /**
     * 項目変更チェック
     *
     * @param diffList          相違情報リスト
     * @param dataPath          データ項目名（パス形式）
     * @param styleValue        相違点が見つかった時のスタイル設定値
     * @param settingStyleValue HTMLに設定されたスタイル設定値
     * @return スタイル設定値
     */
    protected String checkDiff(List<String> diffList, String dataPath, String styleValue, String settingStyleValue) {
        if (diffList.contains(dataPath)) {
            if (settingStyleValue == null || settingStyleValue.isEmpty()) {
                return styleValue;
            } else if (styleValue == null || styleValue.isEmpty()) {
                return settingStyleValue;
            } else {
                return settingStyleValue + String.valueOf(styleValue.charAt(0)).toUpperCase() + styleValue.substring(1);
            }
        }
        return settingStyleValue;
    }

    /**
     * 変更箇所の表示スタイル設定処理<br/>
     *
     * @param detailsUpdateModel 確認ページ
     */
    public void setDiff(DetailsUpdateModel detailsUpdateModel) {

        ReceiveOrderDto modified = detailsUpdateModel.getModifiedReceiveOrder();
        ReceiveOrderDto original = detailsUpdateModel.getOriginalReceiveOrder();

        this.putOrderSummaryInOrder(original);

        /** 受注サマリー */
        setOrderSummaryDiff(detailsUpdateModel, modified, original);

        /** 受注お客様 */
        setOrderPersonDiff(detailsUpdateModel, modified, original);

        /** 受注配送/受注商品 */
        setOrderDeliveryDiff(detailsUpdateModel);

        /** 受注決済 */
        setOrderSettlementDiff(detailsUpdateModel, modified, original);

        /** その他料金 */
        setAddtionalChargeDiff(detailsUpdateModel, modified.getOrderAdditionalChargeEntityList(),
                               original.getOrderAdditionalChargeEntityList()
                              );

        /** 受注請求 */
        setOrderBillDiff(detailsUpdateModel, modified, original);

        /** 受注メモ */
        setOrderMemoDiff(detailsUpdateModel, modified, original);

        /** 受注入金 */
        setReceiptMoneyDiff(detailsUpdateModel, modified, original);

        /** 受注インデックス */
        setOrderIndexDiff(detailsUpdateModel, modified, original);

        /** 受注決済詳細 */
        setOrderSettlementDetailDiff(detailsUpdateModel, modified, original);

        /** マルペイ請求 */
        setMulPayBillDiff(detailsUpdateModel, modified, original);
    }

    /**
     * 受注サマリの変更箇所の差分セット<br/>
     *
     * @param detailsUpdateModel Model
     * @param modified           修正後ReceiveOrderDto
     * @param original           修正前ReceiveOrderDto
     * @param customParams       案件用引数
     */
    protected void setOrderSummaryDiff(DetailsUpdateModel detailsUpdateModel,
                                       ReceiveOrderDto modified,
                                       ReceiveOrderDto original,
                                       Object... customParams) {

        detailsUpdateModel.setModifiedOrderSummaryList(
                        DiffUtil.diff(original.getOrderSummaryEntity(), modified.getOrderSummaryEntity()));
    }

    /**
     * 受注お客様の変更箇所の差分セット<br/>
     *
     * @param detailsUpdateModel Model
     * @param modified           修正後ReceiveOrderDto
     * @param original           修正前ReceiveOrderDto
     */
    protected void setOrderPersonDiff(DetailsUpdateModel detailsUpdateModel,
                                      ReceiveOrderDto modified,
                                      ReceiveOrderDto original) {

        detailsUpdateModel.setModifiedOrderPersonList(
                        DiffUtil.diff(original.getOrderPersonEntity(), modified.getOrderPersonEntity()));
    }

    /**
     * 受注配送の変更箇所の差分セット<br/>
     *
     * @param detailsUpdateModel Model
     * @param customParams       案件用引数
     */
    protected void setOrderDeliveryDiff(DetailsUpdateModel detailsUpdateModel, Object... customParams) {

        OrderReceiverUpdateItem orderReceiverUpdateItem = detailsUpdateModel.getOrderReceiverItem();
        OrderDeliveryDto originalOrderDeliveryDto = detailsUpdateModel.getOriginalReceiveOrder().getOrderDeliveryDto();
        OrderDeliveryDto modifiedOrderDeliveryDto = detailsUpdateModel.getModifiedReceiveOrder().getOrderDeliveryDto();

        detailsUpdateModel.setModifiedOrderDeliveryList(DiffUtil.diff(originalOrderDeliveryDto.getOrderDeliveryEntity(),
                                                                      modifiedOrderDeliveryDto.getOrderDeliveryEntity()
                                                                     ));
        detailsUpdateModel.setModifiedDeliveryMethod(DiffUtil.diff(originalOrderDeliveryDto.getDeliveryMethodEntity(),
                                                                   modifiedOrderDeliveryDto.getDeliveryMethodEntity()
                                                                  ));

        // 受注商品の変更箇所の表示スタイル設定
        setGoodsDiff(detailsUpdateModel, orderReceiverUpdateItem, modifiedOrderDeliveryDto, originalOrderDeliveryDto);
    }

    /**
     * 受注商品の変更箇所の差分セット<br/>
     *
     * @param detailsUpdateModel      Model
     * @param orderReceiverUpdateItem お届け先表示情報Item
     * @param modified                修正後ReceiveOrderDto
     * @param original                修正前ReceiveOrderDto
     * @param customParams            案件用引数
     */
    protected void setGoodsDiff(DetailsUpdateModel detailsUpdateModel,
                                OrderReceiverUpdateItem orderReceiverUpdateItem,
                                OrderDeliveryDto modified,
                                OrderDeliveryDto original,
                                Object... customParams) {

        // 初期化
        detailsUpdateModel.setModifiedOrderGoodsList(new ArrayList<>());

        // 対象Entity名
        String ordergoods = detailsUpdateModel.diffObjectNameOrderGoods + DiffUtil.SEPARATOR;

        List<OrderGoodsUpdateItem> orderGoodsUpdateItems = orderReceiverUpdateItem.getOrderGoodsUpdateItems();
        Iterator<OrderGoodsUpdateItem> orderGoodsUpdateIte = orderGoodsUpdateItems.iterator();

        List<OrderGoodsEntity> modifiedGoodsList = modified.getOrderGoodsEntityList();
        Iterator<OrderGoodsEntity> modifiedGoodIte = modifiedGoodsList.iterator();
        List<OrderGoodsEntity> originalGoodsList = original.getOrderGoodsEntityList();
        Iterator<OrderGoodsEntity> originalGoodIte = originalGoodsList.iterator();
        while (orderGoodsUpdateIte.hasNext()) {
            orderGoodsUpdateIte.next();
            if (modifiedGoodIte.hasNext()) {
                OrderGoodsEntity modifiedGoods = modifiedGoodIte.next();
                if (originalGoodIte.hasNext()) {
                    OrderGoodsEntity originalGoods = originalGoodIte.next();
                    List<String> goodsDiffList = DiffUtil.diff(originalGoods, modifiedGoods);
                    detailsUpdateModel.getModifiedOrderGoodsList().add(goodsDiffList);
                } else {
                    // 商品を追加した場合
                    List<String> goodsDiffList = new ArrayList<>();
                    goodsDiffList.add(ordergoods + "goodsCount");
                    detailsUpdateModel.getModifiedOrderGoodsList().add(goodsDiffList);
                }
            }
        }
    }

    /**
     * 受注決済の変更箇所の差分セット<br/>
     *
     * @param detailsUpdateModel Model
     * @param modified           修正後ReceiveOrderDto
     * @param original           修正前ReceiveOrderDto
     * @param customParams       案件用引数
     */
    protected void setOrderSettlementDiff(DetailsUpdateModel detailsUpdateModel,
                                          ReceiveOrderDto modified,
                                          ReceiveOrderDto original,
                                          Object... customParams) {

        detailsUpdateModel.setModifiedOrderSettlementList(
                        DiffUtil.diff(original.getOrderSettlementEntity(), modified.getOrderSettlementEntity()));
    }

    /**
     * その他追加料金の変更箇所の差分セット<br/>
     *
     * @param detailsUpdateModel Model
     * @param modified           修正後ReceiveOrderDto
     * @param original           修正前ReceiveOrderDto
     * @param customParams       案件用引数
     */
    protected void setAddtionalChargeDiff(DetailsUpdateModel detailsUpdateModel,
                                          List<OrderAdditionalChargeEntity> modified,
                                          List<OrderAdditionalChargeEntity> original,
                                          Object... customParams) {

        List<OrderAdditionalChargeItem> orderAdditionalChargeItems = detailsUpdateModel.getOrderAdditionalChargeItems();
        Iterator<OrderAdditionalChargeItem> orderAdditionalChargeIte = orderAdditionalChargeItems.iterator();
        Iterator<OrderAdditionalChargeEntity> modifiedAddChargeIte = modified.iterator();
        Iterator<OrderAdditionalChargeEntity> originalAddChargeIte = original.iterator();

        detailsUpdateModel.setModifiedAdditionalChargeList(new ArrayList<>());

        while (orderAdditionalChargeIte.hasNext()) {
            orderAdditionalChargeIte.next();
            if (modifiedAddChargeIte.hasNext()) {
                OrderAdditionalChargeEntity modifiedAddCharge = modifiedAddChargeIte.next();
                if (originalAddChargeIte.hasNext()) {
                    OrderAdditionalChargeEntity originalAddCharge = originalAddChargeIte.next();
                    detailsUpdateModel.getModifiedAdditionalChargeList()
                                      .add(DiffUtil.diff(originalAddCharge, modifiedAddCharge));
                } else {
                    detailsUpdateModel.getModifiedAdditionalChargeList()
                                      .add(DiffUtil.diff(new OrderAdditionalChargeEntity(), modifiedAddCharge));
                }
            }
        }
    }

    /**
     * 受注請求の変更箇所の差分セット<br/>
     *
     * @param detailsUpdateModel Model
     * @param modified           修正後ReceiveOrderDto
     * @param original           修正前ReceiveOrderDto
     * @param customParams       案件用引数
     */
    protected void setOrderBillDiff(DetailsUpdateModel detailsUpdateModel,
                                    ReceiveOrderDto modified,
                                    ReceiveOrderDto original,
                                    Object... customParams) {

        detailsUpdateModel.setModifiedOrderBillList(
                        DiffUtil.diff(original.getOrderBillEntity(), modified.getOrderBillEntity()));
    }

    /**
     * 受注メモの変更箇所の差分セット<br/>
     *
     * @param detailsUpdateModel Model
     * @param modified           修正後ReceiveOrderDto
     * @param original           修正前ReceiveOrderDto
     * @param customParams       案件用引数
     */
    protected void setOrderMemoDiff(DetailsUpdateModel detailsUpdateModel,
                                    ReceiveOrderDto modified,
                                    ReceiveOrderDto original,
                                    Object... customParams) {

        // 初回修正時はnullのため
        if (original.getOrderMemoEntity() == null) {
            original.setOrderMemoEntity(new OrderMemoEntity());
        }

        // まずありえないが、念のため
        if (modified.getOrderMemoEntity() == null) {
            modified.setOrderMemoEntity(new OrderMemoEntity());
        }

        detailsUpdateModel.setModifiedMemoList(
                        DiffUtil.diff(original.getOrderMemoEntity(), modified.getOrderMemoEntity()));
    }

    /**
     * 受注入金の変更箇所の差分セット<br/>
     *
     * @param detailsUpdateModel Model
     * @param modified           修正後ReceiveOrderDto
     * @param original           修正前ReceiveOrderDto
     * @param customParams       案件用引数
     */
    protected void setReceiptMoneyDiff(DetailsUpdateModel detailsUpdateModel,
                                       ReceiveOrderDto modified,
                                       ReceiveOrderDto original,
                                       Object... customParams) {

        List<OrderReceiptOfMoneyEntity> modifiedMoneyList = modified.getOrderReceiptOfMoneyEntityList();
        if (modifiedMoneyList != null && !modifiedMoneyList.isEmpty()) {
            OrderReceiptOfMoneyEntity modifiedMoney = modifiedMoneyList.get(modifiedMoneyList.size() - 1);

            List<OrderReceiptOfMoneyEntity> originalMoneyList = original.getOrderReceiptOfMoneyEntityList();
            if (originalMoneyList != null && !originalMoneyList.isEmpty()) {
                OrderReceiptOfMoneyEntity originalMoney = originalMoneyList.get(originalMoneyList.size() - 1);
                List<String> moneyDiffList = DiffUtil.diff(originalMoney, modifiedMoney);

                detailsUpdateModel.setModifiedReceiptMoneyList(moneyDiffList);
            }
        }
    }

    /**
     * 受注インデックスの変更箇所の差分セット<br/>
     *
     * @param detailsUpdateModel Model
     * @param modified           修正後ReceiveOrderDto
     * @param original           修正前ReceiveOrderDto
     * @param customParams       案件用引数
     */
    protected void setOrderIndexDiff(DetailsUpdateModel detailsUpdateModel,
                                     ReceiveOrderDto modified,
                                     ReceiveOrderDto original,
                                     Object... customParams) {

        detailsUpdateModel.setModifiedOrderIndexList(
                        DiffUtil.diff(original.getOrderIndexEntity(), modified.getOrderIndexEntity()));
    }

    /**
     * 受注決済詳細の変更箇所の差分セット<br/>
     *
     * @param detailsUpdateModel Model
     * @param modified           修正後ReceiveOrderDto
     * @param original           修正前ReceiveOrderDto
     * @param customParams       案件用引数
     */
    protected void setOrderSettlementDetailDiff(DetailsUpdateModel detailsUpdateModel,
                                                ReceiveOrderDto modified,
                                                ReceiveOrderDto original,
                                                Object... customParams) {

        detailsUpdateModel.setModifiedSettlementMethodList(
                        DiffUtil.diff(original.getSettlementMethodEntity(), modified.getSettlementMethodEntity()));
    }

    /**
     * お支払方法詳細（マルペイ請求）の変更箇所の差分セット<br/>
     *
     * @param detailsUpdateModel Model
     * @param modified           修正後ReceiveOrderDto
     * @param original           修正前ReceiveOrderDto
     * @param customParams       案件用引数
     */
    protected void setMulPayBillDiff(DetailsUpdateModel detailsUpdateModel,
                                     ReceiveOrderDto modified,
                                     ReceiveOrderDto original,
                                     Object... customParams) {

        detailsUpdateModel.setModifiedMulPayBillList(
                        DiffUtil.diff(original.getMulPayBillEntity(), modified.getMulPayBillEntity()));
    }
}
