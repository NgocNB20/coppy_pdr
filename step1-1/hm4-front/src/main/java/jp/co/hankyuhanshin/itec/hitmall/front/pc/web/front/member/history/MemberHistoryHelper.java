/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.history;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.OrderHistoryDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.OrderHistoryDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.OrderSummaryEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ReceiveOrderForHistoryDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.SettlementMethodEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.ConvenienceEntityListResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCancelFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCarrierType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeEffectiveFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMailRequired;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberRank;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOrderAgeType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOrderSex;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOrderType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePaymentType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePointActivateFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePointType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReceiverDateDesignationFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeRepeatPurchaseType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalesFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSend;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodCommissionType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodPriceCommissionFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeWaitingFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.orderhistory.OrderHistoryListDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.OrderSummarySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.ReceiveOrderForHistoryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.summary.OrderSummaryForHistoryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.delivery.ReceiverDateDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.conveni.ConvenienceEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.additional.OrderAdditionalChargeEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderUtility;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 注文履歴 Helper
 *
 * @author kimura
 */
@Component
public class MemberHistoryHelper {

    /**
     * 変換Utility
     */
    private final ConversionUtility conversionUtility;

    /**
     * 受注関連Utility
     */
    private final OrderUtility orderUtility;

    /**
     * 商品金額計算Utility
     */
    private final CalculatePriceUtility calculatePriceUtility;

    /**
     * GoodsUtility
     */
    private final GoodsUtility goodsUtility;

    /**
     * コンストラクタ
     *
     * @param conversionUtility
     * @param orderUtility
     * @param calculatePriceUtility
     * @param goodsUtility
     */
    public MemberHistoryHelper(ConversionUtility conversionUtility,
                               OrderUtility orderUtility,
                               CalculatePriceUtility calculatePriceUtility,
                               GoodsUtility goodsUtility) {
        this.conversionUtility = conversionUtility;
        this.orderUtility = orderUtility;
        this.calculatePriceUtility = calculatePriceUtility;
        this.goodsUtility = goodsUtility;
    }

    /**
     * 注文履歴リスト取得検索条件Dtoの作成<br/>
     *
     * @param memberHistoryModel ページ
     * @return 注文履歴リスト取得検索条件Dto
     */
    public OrderSummarySearchForDaoConditionDto toOrderSummarySearchForDaoConditionDtoForLoad(MemberHistoryModel memberHistoryModel) {

        // 受注サマリリスト取得検索条件Dto
        OrderSummarySearchForDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(OrderSummarySearchForDaoConditionDto.class);

        conditionDto.setMemberInfoSeq(memberHistoryModel.getCommonInfo().getCommonInfoUser().getMemberInfoSeq());

        return conditionDto;
    }

    /**
     * Model変換、初期表示<br/>
     *
     * @param orderHistoryListDtoList 注文履歴一覧DTOリスト
     * @param conditionDto            検索条件Dto
     * @param memberHistoryModel      Model
     */
    public void toPageForLoad(List<OrderHistoryListDto> orderHistoryListDtoList,
                              OrderSummarySearchForDaoConditionDto conditionDto,
                              MemberHistoryModel memberHistoryModel) {

        // 一覧用アイテムリストの作成
        List<MemberHistoryModelItem> itemlist = new ArrayList<>();

        for (OrderHistoryListDto orderHistoryListDto : orderHistoryListDtoList) {
            itemlist.add(createOrderItem(orderHistoryListDto));
        }
        memberHistoryModel.setOrderHistoryItems(itemlist);
    }

    /**
     * 注文Itemを作成する<br/>
     *
     * @param orderHistoryListDto 注文履歴一覧Dto
     * @return MemberHistoryModelItem
     */
    protected MemberHistoryModelItem createOrderItem(OrderHistoryListDto orderHistoryListDto) {

        MemberHistoryModelItem item = ApplicationContextUtility.getBean(MemberHistoryModelItem.class);

        // 受注サマリ情報の取得
        OrderSummaryEntity orderSummaryEntity = orderHistoryListDto.getOrderSummaryEntity();

        item.setOrderVersionNo(orderSummaryEntity.getOrderVersionNo());
        item.setOrderTime(orderSummaryEntity.getOrderTime());
        item.setOrderCode(orderSummaryEntity.getOrderCode());
        item.setOrderPrice(orderSummaryEntity.getOrderPrice());

        // 受注ステータス
        if (orderSummaryEntity.getCancelFlag() == HTypeCancelFlag.ON) {
            // キャンセル
            item.setStatus(orderSummaryEntity.getCancelFlag().getLabel());
            item.setStatusValue(MemberHistoryModel.CANCEL_STATUS);
        } else if (orderSummaryEntity.getWaitingFlag() == HTypeWaitingFlag.ON) {
            // 保留
            item.setStatus(orderSummaryEntity.getWaitingFlag().getLabel());
        } else {
            // 通常の受注ステータス
            item.setStatus(orderSummaryEntity.getOrderStatus().getLabel());
            if (orderSummaryEntity.getOrderStatus() != null && HTypeOrderStatus.GOODS_PREPARING.getValue()
                                                                                               .equals(orderSummaryEntity
                                                                                                                       .getOrderStatus()
                                                                                                                       .getValue())) {
                item.setStatusValue(MemberHistoryModel.GOODS_PREPARING_STATUS);
            }
            if (orderSummaryEntity.getOrderStatus() != null && HTypeOrderStatus.PAYMENT_CONFIRMING.getValue()
                                                                                                  .equals(orderSummaryEntity
                                                                                                                          .getOrderStatus()
                                                                                                                          .getValue())) {
                item.setStatusValue(MemberHistoryModel.PAYMENT_CONFIRMING_STATUS);
            }
            if (orderSummaryEntity.getOrderStatus() != null && HTypeOrderStatus.SHIPMENT_COMPLETION.getValue()
                                                                                                   .equals(orderSummaryEntity
                                                                                                                           .getOrderStatus()
                                                                                                                           .getValue())) {
                item.setStatusValue(MemberHistoryModel.SHIPMENT_COMPLETION_STATUS);
            }
        }

        return item;
    }

    /**
     * Model変換、初期表示<br/>
     *
     * @param receiveOrderDto    受注Dto
     * @param goodsDetailsList   商品詳細リスト
     * @param conveniName        コンビニ名称
     * @param memberHistoryModel 対象Model
     */
    public void toPageForLoadDetail(ReceiveOrderForHistoryDto receiveOrderDto,
                                    List<GoodsDetailsDto> goodsDetailsList,
                                    String conveniName,
                                    MemberHistoryModel memberHistoryModel) {
        // 受注状態
        setupOrder(receiveOrderDto, memberHistoryModel);
        // ご注文主
        setupOrderPerson(receiveOrderDto, memberHistoryModel);
        // お届け先リスト
        setupDeliveryList(memberHistoryModel, receiveOrderDto, goodsDetailsList);
        /* 決済情報 */
        setupSettlement(receiveOrderDto, memberHistoryModel, conveniName);
        // 追加料金
        setupExtraCharge(receiveOrderDto, memberHistoryModel);
        // クーポン
        setCoupon(receiveOrderDto, memberHistoryModel);
    }

    /**
     * 注文情報をModelに設定する
     *
     * @param receiveOrderDto    受注
     * @param memberHistoryModel 対象Model
     * @param customParams       案件用引数
     */
    protected void setupOrder(ReceiveOrderForHistoryDto receiveOrderDto,
                              MemberHistoryModel memberHistoryModel,
                              Object... customParams) {
        // 決済
        SettlementMethodEntity settlementEntity = receiveOrderDto.getSettlementMethodEntity();
        // 受注サマリ
        OrderSummaryForHistoryDto summaryDto = receiveOrderDto.getOrderSummaryDto();
        // 受注ステータス
        if (HTypeCancelFlag.ON.equals(summaryDto.getCancelFlag())) {
            // キャンセル
            memberHistoryModel.setStatus(summaryDto.getCancelFlag().getLabel());
            memberHistoryModel.setStatusValue(MemberHistoryModel.CANCEL_STATUS);
        } else if (HTypeWaitingFlag.ON.equals(summaryDto.getWaitingFlag())) {
            // 保留
            memberHistoryModel.setStatus(summaryDto.getWaitingFlag().getLabel());
        } else {
            // 通常の受注ステータス
            memberHistoryModel.setStatus(summaryDto.getOrderStatus().getLabel());
            if (HTypeOrderStatus.GOODS_PREPARING.getValue().equals(summaryDto.getOrderStatus().getValue())) {
                memberHistoryModel.setStatusValue(MemberHistoryModel.GOODS_PREPARING_STATUS);
            }
            if (HTypeOrderStatus.PAYMENT_CONFIRMING.getValue().equals(summaryDto.getOrderStatus().getValue())) {
                memberHistoryModel.setStatusValue(MemberHistoryModel.PAYMENT_CONFIRMING_STATUS);
            }
            if (HTypeOrderStatus.SHIPMENT_COMPLETION.getValue().equals(summaryDto.getOrderStatus().getValue())) {
                memberHistoryModel.setStatusValue(MemberHistoryModel.SHIPMENT_COMPLETION_STATUS);
            }
        }
        memberHistoryModel.setOrderCode(summaryDto.getOrderCode());
        memberHistoryModel.setOrderTime(summaryDto.getOrderTime());
        memberHistoryModel.setOrderPrice(summaryDto.getOrderPrice());
        memberHistoryModel.setReceiptPriceTotal(summaryDto.getReceiptPriceTotal());
        memberHistoryModel.setSettlementMethodName(settlementEntity.getSettlementMethodName());

        memberHistoryModel.setMemberInfoSeq(summaryDto.getMemberInfoSeq());

        // 保持用
        memberHistoryModel.setOcd(summaryDto.getOrderCode());
        memberHistoryModel.setSaveOcd(summaryDto.getOrderCode());
    }

    /**
     * 注文者情報をModelに設定する
     *
     * @param receiveOrderDto    受注
     * @param memberHistoryModel 対象Model
     * @param customParams       案件用引数
     */
    protected void setupOrderPerson(ReceiveOrderForHistoryDto receiveOrderDto,
                                    MemberHistoryModel memberHistoryModel,
                                    Object... customParams) {

        // ご注文主
        OrderPersonEntity orderPersonEntity = receiveOrderDto.getOrderPersonEntity();

        memberHistoryModel.setOrderTel(orderPersonEntity.getOrderTel());
        memberHistoryModel.setOrderContactTel(orderPersonEntity.getOrderContactTel());
        memberHistoryModel.setOrderMail(orderPersonEntity.getOrderMail());
        memberHistoryModel.setOrderPersonFirstKana(orderPersonEntity.getOrderFirstKana());
        memberHistoryModel.setOrderPersonLastKana(orderPersonEntity.getOrderLastKana());
        memberHistoryModel.setOrderPersonFirstName(orderPersonEntity.getOrderFirstName());
        memberHistoryModel.setOrderPersonLastName(orderPersonEntity.getOrderLastName());

        String[] zipcodeArray = conversionUtility.toZipCodeArray(orderPersonEntity.getOrderZipCode());
        memberHistoryModel.setOrderZipCode1(zipcodeArray[0]);
        memberHistoryModel.setOrderZipCode2(zipcodeArray[1]);
        memberHistoryModel.setOrderPrefecture(orderPersonEntity.getOrderPrefecture());
        memberHistoryModel.setOrderAddress1(orderPersonEntity.getOrderAddress1());
        memberHistoryModel.setOrderAddress2(orderPersonEntity.getOrderAddress2());
        memberHistoryModel.setOrderAddress3(orderPersonEntity.getOrderAddress3());
    }

    /**
     * お届け先アイテムをModelに設定する<br/>
     *
     * @param receiveOrderDto    受注Dto
     * @param goodsDetailsList   商品詳細リスト
     * @param memberHistoryModel 対象Model
     */
    protected void setupDeliveryList(MemberHistoryModel memberHistoryModel,
                                     ReceiveOrderForHistoryDto receiveOrderDto,
                                     List<GoodsDetailsDto> goodsDetailsList) {
        // 送料
        BigDecimal carriage = BigDecimal.ZERO;

        HistoryModelDeliveryItem HistoryModelDeliveryItem =
                        createDeliveryItem(receiveOrderDto, receiveOrderDto.getOrderDeliveryDto(), goodsDetailsList);
        carriage = carriage.add(receiveOrderDto.getOrderDeliveryDto().getOrderDeliveryEntity().getCarriage());
        memberHistoryModel.setOrderDeliveryItem(HistoryModelDeliveryItem);
        memberHistoryModel.setCarriage(carriage);
    }

    /**
     * 決済方法をModelに設定する
     *
     * @param receiveOrderDto    受注
     * @param memberHistoryModel 対象Model
     * @param conveniName        コンビニ名称
     * @param customParams       案件用引数
     */
    protected void setupSettlement(ReceiveOrderForHistoryDto receiveOrderDto,
                                   MemberHistoryModel memberHistoryModel,
                                   String conveniName,
                                   Object... customParams) {

        OrderBillEntity orderBillEntity = receiveOrderDto.getOrderBillEntity();

        // 通信結果
        MulPayBillEntity mulPayBillEntity = receiveOrderDto.getMulPayBillEntity();

        // 受注決済
        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();

        memberHistoryModel.setCarriage(orderSettlementEntity.getCarriage());
        memberHistoryModel.setSettlementCommission(orderSettlementEntity.getSettlementCommission());
        memberHistoryModel.setSettlementMethodType(orderSettlementEntity.getSettlementMethodType());
        memberHistoryModel.setCardbranddisplay(orderBillEntity.getCardBrandDisplayPC());
        memberHistoryModel.setGoodsPriceTotal(orderSettlementEntity.getGoodsPriceTotal());
        // 消費税算出
        memberHistoryModel.setTaxPrice(orderSettlementEntity.getTaxPrice());
        memberHistoryModel.setStandardTaxPrice(orderSettlementEntity.getStandardTaxPrice());
        memberHistoryModel.setReducedTaxPrice(orderSettlementEntity.getReducedTaxPrice());
        memberHistoryModel.setStandardTaxTargetPrice(orderSettlementEntity.getStandardTaxTargetPrice());
        memberHistoryModel.setReducedTaxTargetPrice(orderSettlementEntity.getReducedTaxTargetPrice());

        if (mulPayBillEntity == null) {
            return;
        }

        if (mulPayBillEntity.getPayTimes() != null) {
            memberHistoryModel.setPaytimes(mulPayBillEntity.getPayTimes().toString());
        }

        memberHistoryModel.setConvenienceCode(mulPayBillEntity.getConvenience());
        memberHistoryModel.setConvenienceName(conveniName);
        memberHistoryModel.setReceiptNo(mulPayBillEntity.getReceiptNo());
        memberHistoryModel.setConfNo(mulPayBillEntity.getConfNo());
        memberHistoryModel.setPaymentTimeLimitDate(orderBillEntity.getPaymentTimeLimitDate());
        memberHistoryModel.setBkCode(mulPayBillEntity.getBkCode());
        memberHistoryModel.setCustId(mulPayBillEntity.getCustId());
        memberHistoryModel.setCode(mulPayBillEntity.getEncryptReceiptNo());
        memberHistoryModel.setPaymentUrl(mulPayBillEntity.getPaymentURL());

        if (mulPayBillEntity.getMethod() == null) {
            return;
        }

        if (mulPayBillEntity.getMethod().equals(HTypePaymentType.SINGLE.getValue())) {
            memberHistoryModel.setPaymentTypeDisplay(HTypePaymentType.SINGLE.getLabel());
        } else if (mulPayBillEntity.getMethod().equals(HTypePaymentType.INSTALLMENT.getValue())) {
            memberHistoryModel.setPaymentTypeDisplay(HTypePaymentType.INSTALLMENT.getLabel());
        } else if (mulPayBillEntity.getMethod().equals(HTypePaymentType.REVOLVING.getValue())) {
            memberHistoryModel.setPaymentTypeDisplay(HTypePaymentType.REVOLVING.getLabel());
        }
    }

    /**
     * 追加料金情報をModelに設定する
     *
     * @param receiveOrderDto    受注
     * @param memberHistoryModel 対象Model
     * @param customParams       案件用引数
     */
    protected void setupExtraCharge(ReceiveOrderForHistoryDto receiveOrderDto,
                                    MemberHistoryModel memberHistoryModel,
                                    Object... customParams) {
        List<HistoryModelAdditionalChargeItem> historyPageAdditionalChargeItems = new ArrayList<>();
        BigDecimal additionalPriceTotal = BigDecimal.ZERO;
        for (OrderAdditionalChargeEntity orderAdditionalChargeEntity : receiveOrderDto.getOrderAdditionalChargeEntityList()) {
            historyPageAdditionalChargeItems.add(createAdditionalChargeItem(orderAdditionalChargeEntity));
            additionalPriceTotal = additionalPriceTotal.add(orderAdditionalChargeEntity.getAdditionalDetailsPrice());
        }
        memberHistoryModel.setOrderAdditionalChargeItems(historyPageAdditionalChargeItems);
    }

    /**
     * 追加料金Itemを作成する<br/>
     *
     * @param orderAdditionalChargeEntity 追加料金Entity
     * @return DetailPageAdditionalChargeItem
     */
    protected HistoryModelAdditionalChargeItem createAdditionalChargeItem(OrderAdditionalChargeEntity orderAdditionalChargeEntity) {
        HistoryModelAdditionalChargeItem historyPageAdditionalChargeItem =
                        ApplicationContextUtility.getBean(HistoryModelAdditionalChargeItem.class);
        historyPageAdditionalChargeItem.setAdditionalDetailsName(
                        orderAdditionalChargeEntity.getAdditionalDetailsName());
        historyPageAdditionalChargeItem.setAdditionalDetailsPrice(
                        orderAdditionalChargeEntity.getAdditionalDetailsPrice());

        return historyPageAdditionalChargeItem;
    }

    /**
     * 配送Itemを作成する
     *
     * @param receiveOrderDto  受注Dto
     * @param orderDeliveryDto 受注配送Dto
     * @param goodsDetailsList 商品詳細リスト
     * @param customParams     案件用引数
     * @return DetailPageDeliveryItem
     */
    protected HistoryModelDeliveryItem createDeliveryItem(ReceiveOrderForHistoryDto receiveOrderDto,
                                                          OrderDeliveryDto orderDeliveryDto,
                                                          List<GoodsDetailsDto> goodsDetailsList,
                                                          Object... customParams) {
        HistoryModelDeliveryItem HistoryModelDeliveryItem =
                        ApplicationContextUtility.getBean(HistoryModelDeliveryItem.class);
        // お届け先情報
        setupReceiver(HistoryModelDeliveryItem, orderDeliveryDto, receiveOrderDto.getOrderPersonEntity());
        // 配送情報
        setupDelivery(HistoryModelDeliveryItem, orderDeliveryDto, receiveOrderDto);
        // 商品
        HistoryModelDeliveryItem.setGoodsListItems(new ArrayList<>());
        for (OrderGoodsEntity orderGoodsEntity : orderDeliveryDto.getOrderGoodsEntityList()) {
            HistoryModelGoodsItem historyPageGoodsItem = createGoodsItem(orderGoodsEntity, goodsDetailsList);
            HistoryModelDeliveryItem.getGoodsListItems().add(historyPageGoodsItem);
        }

        return HistoryModelDeliveryItem;
    }

    /**
     * 商品Itemを作成する<br/>
     *
     * @param orderGoodsEntity 受注商品Entity
     * @param goodsDetailsList 商品詳細リスト
     * @return HistoryModelGoodsItem
     */
    protected HistoryModelGoodsItem createGoodsItem(OrderGoodsEntity orderGoodsEntity,
                                                    List<GoodsDetailsDto> goodsDetailsList) {
        HistoryModelGoodsItem historyPageGoodsItem = ApplicationContextUtility.getBean(HistoryModelGoodsItem.class);
        historyPageGoodsItem.setGoodsCode(orderGoodsEntity.getGoodsCode());
        historyPageGoodsItem.setGoodsGroupName(orderGoodsEntity.getGoodsGroupName());
        historyPageGoodsItem.setGoodsPrice(orderGoodsEntity.getGoodsPrice());
        historyPageGoodsItem.setTaxRate(orderGoodsEntity.getTaxRate());
        historyPageGoodsItem.setGoodsTaxType(orderGoodsEntity.getGoodsTaxType());
        historyPageGoodsItem.setGoodsPriceInTax(
                        calculatePriceUtility.getTaxIncludedPrice(historyPageGoodsItem.getGoodsPrice(),
                                                                  historyPageGoodsItem.getTaxRate()
                                                                 ));
        historyPageGoodsItem.setGoodsCount(orderGoodsEntity.getGoodsCount());
        historyPageGoodsItem.setSubTotalGoodsPrice(
                        orderGoodsEntity.getGoodsPrice().multiply(orderGoodsEntity.getGoodsCount()));
        historyPageGoodsItem.setSubTotalGoodsPriceInTax(
                        historyPageGoodsItem.getGoodsPriceInTax().multiply(orderGoodsEntity.getGoodsCount()));
        historyPageGoodsItem.setUnitValue1(orderGoodsEntity.getUnitValue1());
        historyPageGoodsItem.setUnitValue2(orderGoodsEntity.getUnitValue2());

        GoodsDetailsDto goodsDetails = getGoodsDetails(goodsDetailsList, orderGoodsEntity.getGoodsSeq());
        if (goodsDetails != null) {
            historyPageGoodsItem.setGoodsOpenStatus(goodsDetails.getGoodsOpenStatusPC());
            historyPageGoodsItem.setOpenStartTime(goodsDetails.getOpenStartTimePC());
            historyPageGoodsItem.setOpenEndTime(goodsDetails.getOpenEndTimePC());
            historyPageGoodsItem.setUnitTitle1(goodsDetails.getUnitTitle1());
            historyPageGoodsItem.setUnitTitle2(goodsDetails.getUnitTitle2());

            List<String> goodsImageList = new ArrayList<>();
            String goodsItemImageFileName = null;
            if (goodsDetails.getUnitImage() != null) {
                goodsItemImageFileName = goodsDetails.getUnitImage().getImageFileName();
                goodsImageList.add(goodsUtility.getGoodsImagePath(goodsItemImageFileName));
            } else {
                List<GoodsGroupImageEntity> goodsGroupImageEntityList = goodsDetails.getGoodsGroupImageEntityList();
                if (goodsGroupImageEntityList != null) {
                    // 商品画像リストを取り出す。
                    for (GoodsGroupImageEntity goodsGroupImageEntity : goodsGroupImageEntityList) {
                        goodsImageList.add(goodsUtility.getGoodsImagePath(goodsGroupImageEntity.getImageFileName()));
                    }
                }
            }
            historyPageGoodsItem.setGoodsImageItems(goodsImageList);
        }

        return historyPageGoodsItem;
    }

    /**
     * 配送先情報をModelに設定する
     *
     * @param orderDeliveryDto         受注配送Dto
     * @param HistoryModelDeliveryItem ModelItem
     * @param orderPersonEntity        受注ご注文主
     * @param customParams             案件用引数
     */
    protected void setupReceiver(HistoryModelDeliveryItem HistoryModelDeliveryItem,
                                 OrderDeliveryDto orderDeliveryDto,
                                 OrderPersonEntity orderPersonEntity,
                                 Object... customParams) {

        OrderDeliveryEntity orderDeliveryEntity = orderDeliveryDto.getOrderDeliveryEntity();
        HistoryModelDeliveryItem.setReceiverHomeFlag(
                        orderUtility.isSameAddress(orderPersonEntity, orderDeliveryEntity));
        HistoryModelDeliveryItem.setReceiverTel(orderDeliveryEntity.getReceiverTel());
        HistoryModelDeliveryItem.setReceiverFirstKana(orderDeliveryEntity.getReceiverFirstKana());
        HistoryModelDeliveryItem.setReceiverLastKana(orderDeliveryEntity.getReceiverLastKana());
        HistoryModelDeliveryItem.setReceiverFirstName(orderDeliveryEntity.getReceiverFirstName());
        HistoryModelDeliveryItem.setReceiverLastName(orderDeliveryEntity.getReceiverLastName());

        String[] receiverZipcodeArray = conversionUtility.toZipCodeArray(orderDeliveryEntity.getReceiverZipCode());
        HistoryModelDeliveryItem.setReceiverZipCode1(receiverZipcodeArray[0]);
        HistoryModelDeliveryItem.setReceiverZipCode2(receiverZipcodeArray[1]);
        HistoryModelDeliveryItem.setReceiverPrefecture(orderDeliveryEntity.getReceiverPrefecture());
        HistoryModelDeliveryItem.setReceiverAddress1(orderDeliveryEntity.getReceiverAddress1());
        HistoryModelDeliveryItem.setReceiverAddress2(orderDeliveryEntity.getReceiverAddress2());
        HistoryModelDeliveryItem.setReceiverAddress3(orderDeliveryEntity.getReceiverAddress3());
    }

    /**
     * 配送先情報をModelに設定する
     *
     * @param orderDeliveryDto         受注配送Dto
     * @param HistoryModelDeliveryItem ModelItem
     * @param receiveOrderDto          受注Dto
     * @param customParams             案件用引数
     */
    protected void setupDelivery(HistoryModelDeliveryItem HistoryModelDeliveryItem,
                                 OrderDeliveryDto orderDeliveryDto,
                                 ReceiveOrderForHistoryDto receiveOrderDto,
                                 Object... customParams) {
        OrderDeliveryEntity orderDeliveryEntity = orderDeliveryDto.getOrderDeliveryEntity();
        DeliveryMethodEntity deliveryMethodEntity = orderDeliveryDto.getDeliveryMethodEntity();
        // 配送方法名
        HistoryModelDeliveryItem.setDeliveryMethodName(deliveryMethodEntity.getDeliveryMethodDisplayNamePC());
        // お届け先
        HistoryModelDeliveryItem.setDeliveryCode(orderDeliveryEntity.getDeliveryCode());
        // お届け希望日設定
        HistoryModelDeliveryItem.setReceiverDateDesignationFlag(orderDeliveryEntity.getReceiverDateDesignationFlag());
        if (orderDeliveryEntity.getReceiverDate() != null) {
            // お届け希望日指定あり
            HistoryModelDeliveryItem.setReceiverDate(
                            ReceiverDateDto.getFormatMdWithWeek(orderDeliveryEntity.getReceiverDate()));
        } else if (HTypeReceiverDateDesignationFlag.ON.equals(
                        HistoryModelDeliveryItem.getReceiverDateDesignationFlag())) {
            // お届け希望日指定可能かつ、指定なし
            HistoryModelDeliveryItem.setReceiverDate(ReceiverDateDto.NON_SELECT_VALUE);
        } else {
            // お届け希望日指定不可
            HistoryModelDeliveryItem.setReceiverDate(null);
        }
        HistoryModelDeliveryItem.setReceiverTimeZone(orderDeliveryEntity.getReceiverTimeZone());
        // 備考
        HistoryModelDeliveryItem.setDeliveryNote(orderDeliveryEntity.getDeliveryNote());
        // 出荷状態
        HistoryModelDeliveryItem.setShipmentStatus(orderDeliveryDto.getOrderDeliveryEntity().getShipmentStatus());

        // 配送追跡URL表示を判定し、trueなら値を MessageFormat してセット
        // 受注状態がキャンセルだったら表示しない
        String deliveryChaseURL = orderUtility.getDeliveryChaseURL(orderDeliveryDto.getDeliveryMethodEntity(),
                                                                   orderDeliveryDto.getOrderDeliveryEntity()
                                                                  );
        if (StringUtils.isNotEmpty(deliveryChaseURL) && !HTypeCancelFlag.ON.equals(
                        receiveOrderDto.getOrderSummaryDto().getCancelFlag())) {
            HistoryModelDeliveryItem.setDeliveryChaseURL(deliveryChaseURL);
        }
    }

    /**
     * 受注商品の商品SEQリストを取得<br/>
     *
     * @param receiveOrderDto 受注DTO
     * @return 商品SEQリスト
     */
    public List<Integer> getOrderGoodsSeqList(ReceiveOrderForHistoryDto receiveOrderDto) {
        List<OrderGoodsEntity> orderGoodsEntityList = new ArrayList<>();
        orderGoodsEntityList.addAll(receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList());

        List<Integer> goodsSeqList = new ArrayList<>();

        for (int index = 0; index < orderGoodsEntityList.size(); index++) {
            OrderGoodsEntity orderGoodsEntity = orderGoodsEntityList.get(index);
            goodsSeqList.add(orderGoodsEntity.getGoodsSeq());
        }
        return goodsSeqList;
    }

    /**
     * 商品詳細情報取得<br/>
     *
     * @param goodsDetailsList 商品詳細情報リスト
     * @param goodsSeq         商品SEQ
     * @return 商品詳細情報
     */
    protected GoodsDetailsDto getGoodsDetails(List<GoodsDetailsDto> goodsDetailsList, Integer goodsSeq) {
        for (GoodsDetailsDto details : goodsDetailsList) {
            if (goodsSeq.equals(details.getGoodsSeq())) {
                return details;
            }
        }
        return null;
    }

    /**
     * クーポン情報を設定する<br/>
     *
     * @param receiveOrderDto    受注
     * @param memberHistoryModel 対象Model
     * @param customParams       案件用引数
     */
    protected void setCoupon(ReceiveOrderForHistoryDto receiveOrderDto,
                             MemberHistoryModel memberHistoryModel,
                             Object... customParams) {

        // 受注決済
        OrderSettlementEntity orderSettlement = receiveOrderDto.getOrderSettlementEntity();

        // クーポン情報
        CouponEntity coupon = receiveOrderDto.getCoupon();
        // クーポン情報設定
        if (coupon != null) {
            // クーポン名
            memberHistoryModel.setCouponName(coupon.getCouponDisplayNamePC());
        }
        // クーポン割引額
        BigDecimal couponDiscountPrice = orderSettlement.getCouponDiscountPrice();
        // マイナスで表示
        memberHistoryModel.setCouponDiscountPrice(couponDiscountPrice.negate());
    }

    public List<OrderHistoryListDto> toOrderHistoryListDtoList(OrderHistoryDtoListResponse response) {
        if (CollectionUtils.isEmpty(response.getOrderHistoryDtoListResponse())) {
            return new ArrayList<>();
        }

        List<OrderHistoryListDto> orderHistoryListDtoList = new ArrayList<>();

        for (OrderHistoryDtoResponse res : response.getOrderHistoryDtoListResponse()) {
            OrderHistoryListDto orderHistoryListDto = new OrderHistoryListDto();
            orderHistoryListDto.setOrderSummaryEntity(toOrderSummaryEntity(res.getOrderSummaryEntity()));
            orderHistoryListDto.setCouponDiscountPrice(res.getCouponDiscountPrice());
            orderHistoryListDto.setSettlementMethodEntity(toSettlementMethodEntity(res.getSettlementMethodEntity()));
            orderHistoryListDtoList.add(orderHistoryListDto);
        }

        return orderHistoryListDtoList;
    }

    public OrderSummaryEntity toOrderSummaryEntity(OrderSummaryEntityResponse response) {
        if (ObjectUtils.isEmpty(response)) {
            return null;
        }

        OrderSummaryEntity orderSummaryEntity = new OrderSummaryEntity();
        orderSummaryEntity.setOrderSeq(response.getOrderSeq());
        orderSummaryEntity.setOrderVersionNo(response.getOrderVersionNo());
        orderSummaryEntity.setOrderCode(response.getOrderCode());
        orderSummaryEntity.setOrderType(EnumTypeUtil.getEnumFromValue(HTypeOrderType.class, response.getOrderType()));
        orderSummaryEntity.setOrderTime(conversionUtility.toTimeStamp(response.getOrderTime()));
        orderSummaryEntity.setSalesTime(conversionUtility.toTimeStamp(response.getSalesTime()));
        orderSummaryEntity.setCancelTime(conversionUtility.toTimeStamp(response.getCancelTime()));
        orderSummaryEntity.setSalesFlag(EnumTypeUtil.getEnumFromValue(HTypeSalesFlag.class, response.getSalesFlag()));
        orderSummaryEntity.setCancelFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeCancelFlag.class, response.getCancelFlag()));
        orderSummaryEntity.setWaitingFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeWaitingFlag.class, response.getWaitingFlag()));
        orderSummaryEntity.setOrderStatus(
                        EnumTypeUtil.getEnumFromValue(HTypeOrderStatus.class, response.getOrderStatus()));
        orderSummaryEntity.setBeforeDiscountOrderPrice(response.getBeforeDiscountOrderPrice());
        orderSummaryEntity.setOrderPrice(response.getOrderPrice());
        orderSummaryEntity.setReceiptPriceTotal(response.getReceiptPriceTotal());
        orderSummaryEntity.setOrderSiteType(
                        EnumTypeUtil.getEnumFromValue(HTypeSiteType.class, response.getOrderSiteType()));
        orderSummaryEntity.setOrderDeviceType(
                        EnumTypeUtil.getEnumFromValue(HTypeDeviceType.class, response.getOrderDeviceType()));
        orderSummaryEntity.setCarrierType(
                        EnumTypeUtil.getEnumFromValue(HTypeCarrierType.class, response.getCarrierType()));
        orderSummaryEntity.setPeriodicOrderSeq(response.getPeriodicOrderSeq());
        orderSummaryEntity.setSettlementMethodSeq(response.getSettlementMethodSeq());
        orderSummaryEntity.setTaxSeq(response.getTaxSeq());
        orderSummaryEntity.setMemberInfoSeq(response.getMemberInfoSeq());
        orderSummaryEntity.setMemberRank(
                        EnumTypeUtil.getEnumFromValue(HTypeMemberRank.class, response.getMemberRank()));
        orderSummaryEntity.setPrefectureType(
                        EnumTypeUtil.getEnumFromValue(HTypePrefectureType.class, response.getPrefectureType()));
        orderSummaryEntity.setOrderSex(EnumTypeUtil.getEnumFromValue(HTypeOrderSex.class, response.getOrderSex()));
        orderSummaryEntity.setOrderAgeType(
                        EnumTypeUtil.getEnumFromValue(HTypeOrderAgeType.class, response.getOrderAgeType()));
        orderSummaryEntity.setRepeatPurchaseType(
                        EnumTypeUtil.getEnumFromValue(HTypeRepeatPurchaseType.class, response.getRepeatPurchaseType()));
        orderSummaryEntity.setSettlementMailRequired(
                        EnumTypeUtil.getEnumFromValue(HTypeMailRequired.class, response.getSettlementMailRequired()));
        orderSummaryEntity.setReminderSentFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeSend.class, response.getReminderSentFlag()));
        orderSummaryEntity.setExpiredSentFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeSend.class, response.getExpiredSentFlag()));
        orderSummaryEntity.setPointActivateFlag(
                        EnumTypeUtil.getEnumFromValue(HTypePointActivateFlag.class, response.getPointActivateFlag()));
        orderSummaryEntity.setUserAgent(response.getUserAgent());
        orderSummaryEntity.setFreeAreaKey(response.getFreeAreaKey());
        orderSummaryEntity.setOrderPointAddBasePrice(response.getOrderPointAddBasePrice());
        orderSummaryEntity.setOrderPointAddRate(response.getOrderPointAddRate());
        orderSummaryEntity.setShopSeq(1001);
        orderSummaryEntity.setVersionNo(response.getVersionNo());
        orderSummaryEntity.setRegistTime(conversionUtility.toTimeStamp(response.getRegistTime()));
        orderSummaryEntity.setUpdateTime(conversionUtility.toTimeStamp(response.getUpdateTime()));
        orderSummaryEntity.setOrderGoodsVersionNo(response.getOrderGoodsVersionNo());
        orderSummaryEntity.setPointSeq(response.getPointSeq());
        orderSummaryEntity.setCouponDiscountPrice(response.getCouponDiscountPrice());
        orderSummaryEntity.setUsePoint(response.getUsePoint());
        orderSummaryEntity.setPointDiscountPrice(response.getPointDiscountPrice());
        orderSummaryEntity.setPointType(EnumTypeUtil.getEnumFromValue(HTypePointType.class, response.getPointType()));
        orderSummaryEntity.setPoint(response.getPoint());
        orderSummaryEntity.setTotalAcquisitionPoint(response.getTotalAcquisitionPoint());
        orderSummaryEntity.setSettlementMethodName(response.getSettlementMethodName());
        orderSummaryEntity.setSettlementMethodDisplayNamePC(response.getSettlementMethodDisplayNamePC());
        orderSummaryEntity.setReceiverTimeZone(response.getReceiverTimeZone());
        orderSummaryEntity.setOrderName(response.getOrderName());
        orderSummaryEntity.setOrderKana(response.getOrderKana());
        orderSummaryEntity.setOrderTel(response.getOrderTel());
        orderSummaryEntity.setOrderContactTel(response.getOrderContactTel());
        orderSummaryEntity.setOrderMail(response.getOrderMail());
        orderSummaryEntity.setReceiverName(response.getReceiverName());
        orderSummaryEntity.setReceiverKana(response.getReceiverKana());
        orderSummaryEntity.setReceiverTel(response.getReceiverTel());
        orderSummaryEntity.setOrderConsecutiveNo(response.getOrderConsecutiveNo());
        orderSummaryEntity.setDeliveryCode(response.getDeliveryCode());
        orderSummaryEntity.setShipmentStatus(response.getShipmentStatus());
        orderSummaryEntity.setDeliveryNote(response.getDeliveryNote());
        orderSummaryEntity.setReceiptTime(conversionUtility.toTimeStamp(response.getReceiptTime()));
        orderSummaryEntity.setEmergencyFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeEmergencyFlag.class, response.getEmergencyFlag()));
        orderSummaryEntity.setPaymentStatus(response.getPaymentStatus());
        orderSummaryEntity.setDeliveryMethodName(response.getDeliveryMethodName());
        orderSummaryEntity.setOrderStatusForSearchResult(response.getOrderStatusForSearchResult());
        return orderSummaryEntity;
    }

    public SettlementMethodEntity toSettlementMethodEntity(SettlementMethodEntityResponse response) {

        if (ObjectUtils.isEmpty(response)) {
            return null;
        }
        SettlementMethodEntity settlementMethodEntity = new SettlementMethodEntity();
        settlementMethodEntity.setSettlementMethodSeq(response.getSettlementMethodSeq());
        settlementMethodEntity.setShopSeq(1001);
        settlementMethodEntity.setSettlementMethodName(response.getSettlementMethodName());
        settlementMethodEntity.setSettlementMethodDisplayNamePC(response.getSettlementMethodDisplayName());
        settlementMethodEntity.setOpenStatusPC(
                        EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class, response.getOpenStatus()));
        settlementMethodEntity.setSettlementMethodType(EnumTypeUtil.getEnumFromValue(HTypeSettlementMethodType.class,
                                                                                     response.getSettlementMethodType()
                                                                                    ));
        settlementMethodEntity.setSettlementMethodCommissionType(
                        EnumTypeUtil.getEnumFromValue(HTypeSettlementMethodCommissionType.class,
                                                      response.getSettlementMethodCommissionType()
                                                     ));
        settlementMethodEntity.setBillType(EnumTypeUtil.getEnumFromValue(HTypeBillType.class, response.getBillType()));
        settlementMethodEntity.setDeliveryMethodSeq(response.getDeliveryMethodSeq());
        settlementMethodEntity.setEqualsCommission(response.getEqualsCommission());
        settlementMethodEntity.setSettlementMethodPriceCommissionFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeSettlementMethodPriceCommissionFlag.class,
                                                      response.getSettlementMethodPriceCommissionFlag()
                                                     ));
        settlementMethodEntity.setLargeAmountDiscountPrice(response.getLargeAmountDiscountPrice());
        settlementMethodEntity.setLargeAmountDiscountCommission(response.getLargeAmountDiscountCommission());
        settlementMethodEntity.setOrderDisplay(response.getOrderDisplay());
        settlementMethodEntity.setMaxPurchasedPrice(response.getMaxPurchasedPrice());
        settlementMethodEntity.setPaymentTimeLimitDayCount(response.getPaymentTimeLimitDayCount());
        settlementMethodEntity.setMinPurchasedPrice(response.getMinPurchasedPrice());
        settlementMethodEntity.setCancelTimeLimitDayCount(response.getCancelTimeLimitDayCount());
        settlementMethodEntity.setSettlementMailRequired(
                        EnumTypeUtil.getEnumFromValue(HTypeMailRequired.class, response.getSettlementMailRequired()));
        settlementMethodEntity.setEnableCardNoHolding(
                        EnumTypeUtil.getEnumFromValue(HTypeEffectiveFlag.class, response.getEnableCardNoHolding()));
        settlementMethodEntity.setEnableSecurityCode(
                        EnumTypeUtil.getEnumFromValue(HTypeEffectiveFlag.class, response.getEnableSecurityCode()));
        settlementMethodEntity.setEnable3dSecure(
                        EnumTypeUtil.getEnumFromValue(HTypeEffectiveFlag.class, response.getEnable3dSecure()));
        settlementMethodEntity.setEnableInstallment(
                        EnumTypeUtil.getEnumFromValue(HTypeEffectiveFlag.class, response.getEnableInstallment()));
        settlementMethodEntity.setEnableBonusSinglePayment(EnumTypeUtil.getEnumFromValue(HTypeEffectiveFlag.class,
                                                                                         response.getEnableBonusSinglePayment()
                                                                                        ));
        settlementMethodEntity.setEnableBonusInstallment(
                        EnumTypeUtil.getEnumFromValue(HTypeEffectiveFlag.class, response.getEnableBonusInstallment()));
        settlementMethodEntity.setEnableRevolving(
                        EnumTypeUtil.getEnumFromValue(HTypeEffectiveFlag.class, response.getEnableRevolving()));
        settlementMethodEntity.setRegistTime(conversionUtility.toTimeStamp(response.getRegistTime()));
        settlementMethodEntity.setUpdateTime(conversionUtility.toTimeStamp(response.getUpdateTime()));
        return null;
    }

    public ReceiveOrderForHistoryDto toReceiveOrderForHistoryDto(ReceiveOrderForHistoryDtoResponse response) {
        if (ObjectUtils.isEmpty(response)) {
            return null;
        }

        return null;
    }

    public List<GoodsDetailsDto> toGoodsDetailsList(List<GoodsDetailsDtoResponse> responses) {
        if (CollectionUtils.isEmpty(responses)) {
            return new ArrayList<>();
        }

        return null;
    }

    public List<ConvenienceEntity> toConvenienceList(ConvenienceEntityListResponse response) {
        if (ObjectUtils.isEmpty(response)) {
            return null;
        }

        return null;
    }
}
