/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.admin.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.additional.OrderAdditionalChargeEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderReceiptOfMoneyEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.memo.OrderMemoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 処理履歴詳細Helper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class OrderHistoryDetailsHelper extends AbstractOrderDetailsHelper {

    public OrderHistoryDetailsHelper(DateUtility dateUtility,
                                     ConversionUtility conversionUtility,
                                     CommonInfoUtility commonInfoUtility,
                                     OrderUtility orderUtility,
                                     CalculatePriceUtility calculatePriceUtility) {
        super(dateUtility, conversionUtility, commonInfoUtility, orderUtility, calculatePriceUtility);
    }

    /**
     * 処理履歴詳細ページ項目設定<br/>
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void toPage(OrderHistoryDetailsModel orderHistorydetailsModel, ReceiveOrderDto receiveOrderDto) {

        super.toPage(orderHistorydetailsModel, receiveOrderDto);

        /*
         * 修正元の情報を取得するが、ordersummary テーブルは履歴管理していないため 実際は original も modified
         * も同じ内容がセットされている。 受注金額などは後で計算してセットし直しているので注意。
         */
        ReceiveOrderDto original = orderHistorydetailsModel.getOriginalReceiveOrder();
        ReceiveOrderDto modified = orderHistorydetailsModel.getModifiedReceiveOrder();

        // 選択されたレコードが「キャンセル」の時
        if (orderHistorydetailsModel.getCancelTime() != null) {
            /*
             * orderindex テーブルの処理時間 >= ordersummary テーブルのキャンセル日時の場合 ordersummary
             * のキャンセル日時以降の処理は全てキャンセル扱い。
             */
            if (orderHistorydetailsModel.isStateCancel()) {
                /*
                 * orderindex テーブルの処理時間 == ordersummary テーブルのキャンセル日時の場合のみ
                 * キャンセル日時をクリアする。
                 */
                if (!orderHistorydetailsModel.isStateAfterCancel()) {
                    original.getOrderSummaryEntity().setCancelTime(null);
                }
            } else {
                orderHistorydetailsModel.setCancelTime(null);
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
    @Override
    protected void putOrderSummaryInOrder(ReceiveOrderDto receiveOrderDto) {
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
     * @param orderDeliveryDto          受注配送
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
        orderSummaryEntity.setOrderStatus(
                        super.getOrderUtility().getOrderStatusByOrderDelivery(orderstatus, orderDeliveryDto));

    }

    /**
     * 追加料金情報リストをセット<br/>
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void setOrderAdditionalChargeDtoList(OrderHistoryDetailsModel orderHistorydetailsModel,
                                                   ReceiveOrderDto receiveOrderDto) {
        List<OrderAdditionalChargeItem> orderAdditionalChargeItems = new ArrayList<>();
        List<OrderAdditionalChargeEntity> orderAdditionalChargeEntityList =
                        receiveOrderDto.getOrderAdditionalChargeEntityList();
        for (OrderAdditionalChargeEntity entity : orderAdditionalChargeEntityList) {
            OrderAdditionalChargeItem item = toOrderAdditionalChargeItem(entity);
            orderAdditionalChargeItems.add(item);
        }
        orderHistorydetailsModel.setOrderAdditionalChargeItems(orderAdditionalChargeItems);
    }

    /**
     * 受注配送情報をセット<br/>
     * 配送情報内の商品情報リストをセット<br/>
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void setOrderDeliveryDto(OrderHistoryDetailsModel orderHistorydetailsModel,
                                       ReceiveOrderDto receiveOrderDto) {

        // お届け先情報リストをセット
        BigDecimal orderGoodsCountTotal = BigDecimal.ZERO;
        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

        // 納品書 全配送共通の為、itemでは保持しない
        orderHistorydetailsModel.setInvoiceAttachmentFlag(
                        orderDeliveryDto.getOrderDeliveryEntity().getInvoiceAttachmentFlag());
        OrderReceiverUpdateItem receiverItem = toOrderReceiverItem(orderDeliveryDto.getOrderDeliveryEntity(),
                                                                   orderDeliveryDto.getDeliveryMethodEntity()
                                                                  );

        // 商品情報リストをセット
        List<OrderGoodsEntity> orderGoodsList = orderDeliveryDto.getOrderGoodsEntityList();
        List<OrderGoodsUpdateItem> orderGoodsItems = new ArrayList<>();
        for (OrderGoodsEntity orderGoodsEntity : orderGoodsList) {
            OrderGoodsUpdateItem goodsItem = toOrderGoodsUpdateItem(orderGoodsEntity);
            orderGoodsItems.add(goodsItem);
            orderGoodsCountTotal = orderGoodsCountTotal.add(orderGoodsEntity.getGoodsCount());
        }

        // 配送アイテムに商品アイテムリストをセット
        receiverItem.setOrderGoodsUpdateItems(orderGoodsItems);

        orderHistorydetailsModel.setOrderReceiverItem(receiverItem);
        orderHistorydetailsModel.setOrderGoodsCountTotal(orderGoodsCountTotal);
    }

    // //////// 相違点設定 //////////

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
     * 変更箇所の表示スタイル設定処理<br/>
     */
    public void setDiff(OrderHistoryDetailsModel orderHistorydetailsModel) {

        ReceiveOrderDto modified = orderHistorydetailsModel.getModifiedReceiveOrder();
        ReceiveOrderDto original = orderHistorydetailsModel.getOriginalReceiveOrder();

        this.putOrderSummaryInOrder(original);

        /** 受注サマリー */
        setOrderSummaryDiff(orderHistorydetailsModel, modified, original);

        /** 受注お客様 */
        setOrderPersonDiff(orderHistorydetailsModel, modified, original);

        /** 受注配送/受注商品 */
        setOrderDeliveryDiff(orderHistorydetailsModel);

        /** 受注決済 */
        setOrderSettlementDiff(orderHistorydetailsModel, modified, original);

        /** その他料金 */
        setAddtionalChargeDiff(orderHistorydetailsModel, modified.getOrderAdditionalChargeEntityList(),
                               original.getOrderAdditionalChargeEntityList()
                              );

        /** 受注請求 */
        setOrderBillDiff(orderHistorydetailsModel, modified, original);

        /** 受注メモ */
        setOrderMemoDiff(orderHistorydetailsModel, modified, original);

        /** 受注入金 */
        setReceiptMoneyDiff(orderHistorydetailsModel, modified, original);

        /** 受注インデックス */
        setOrderIndexDiff(orderHistorydetailsModel, modified, original);

        /** 受注決済詳細 */
        setOrderSettlementDetailDiff(orderHistorydetailsModel, modified, original);

        /** マルペイ請求 */
        setMulPayBillDiff(orderHistorydetailsModel, modified, original);
    }

    /**
     * 受注サマリの変更箇所の差分セット<br/>
     *
     * @param orderHistorydetailsModel Model
     * @param modified                 修正後ReceiveOrderDto
     * @param original                 修正前ReceiveOrderDto
     * @param customParams             案件用引数
     */
    protected void setOrderSummaryDiff(OrderHistoryDetailsModel orderHistorydetailsModel,
                                       ReceiveOrderDto modified,
                                       ReceiveOrderDto original,
                                       Object... customParams) {

        orderHistorydetailsModel.setModifiedOrderSummaryList(
                        DiffUtil.diff(original.getOrderSummaryEntity(), modified.getOrderSummaryEntity()));
    }

    /**
     * 受注お客様の変更箇所の差分セット<br/>
     *
     * @param orderHistorydetailsModel Model
     * @param modified                 修正後ReceiveOrderDto
     * @param original                 修正前ReceiveOrderDto
     * @param customParams             案件用引数
     */
    protected void setOrderPersonDiff(OrderHistoryDetailsModel orderHistorydetailsModel,
                                      ReceiveOrderDto modified,
                                      ReceiveOrderDto original) {

        orderHistorydetailsModel.setModifiedOrderPersonList(
                        DiffUtil.diff(original.getOrderPersonEntity(), modified.getOrderPersonEntity()));
    }

    /**
     * 受注配送の変更箇所の差分セット<br/>
     *
     * @param orderHistorydetailsModel Model
     * @param customParams             案件用引数
     */
    protected void setOrderDeliveryDiff(OrderHistoryDetailsModel orderHistorydetailsModel, Object... customParams) {

        OrderReceiverUpdateItem orderReceiverUpdateItem = orderHistorydetailsModel.getOrderReceiverItem();
        OrderDeliveryDto originalOrderDeliveryDto =
                        orderHistorydetailsModel.getOriginalReceiveOrder().getOrderDeliveryDto();
        OrderDeliveryDto modifiedOrderDeliveryDto =
                        orderHistorydetailsModel.getModifiedReceiveOrder().getOrderDeliveryDto();

        orderHistorydetailsModel.setModifiedOrderDeliveryList(
                        DiffUtil.diff(originalOrderDeliveryDto.getOrderDeliveryEntity(),
                                      modifiedOrderDeliveryDto.getOrderDeliveryEntity()
                                     ));
        orderHistorydetailsModel.setModifiedDeliveryMethod(
                        DiffUtil.diff(originalOrderDeliveryDto.getDeliveryMethodEntity(),
                                      modifiedOrderDeliveryDto.getDeliveryMethodEntity()
                                     ));

        // 受注商品の変更箇所の表示スタイル設定
        setGoodsDiff(orderHistorydetailsModel, orderReceiverUpdateItem, modifiedOrderDeliveryDto,
                     originalOrderDeliveryDto
                    );
    }

    /**
     * 受注商品の変更箇所の差分セット<br/>
     *
     * @param orderHistorydetailsModel Model
     * @param orderReceiverUpdateItem  お届け先表示情報Item
     * @param modified                 修正後ReceiveOrderDto
     * @param original                 修正前ReceiveOrderDto
     * @param customParams             案件用引数
     */
    protected void setGoodsDiff(OrderHistoryDetailsModel orderHistorydetailsModel,
                                OrderReceiverUpdateItem orderReceiverUpdateItem,
                                OrderDeliveryDto modified,
                                OrderDeliveryDto original,
                                Object... customParams) {

        // 初期化
        orderHistorydetailsModel.setModifiedOrderGoodsList(new ArrayList<>());

        // 対象Entity名
        String ordergoods = orderHistorydetailsModel.diffObjectNameOrderGoods + DiffUtil.SEPARATOR;

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
                    orderHistorydetailsModel.getModifiedOrderGoodsList().add(goodsDiffList);
                } else {
                    // 商品を追加した場合
                    List<String> goodsDiffList = new ArrayList<>();
                    goodsDiffList.add(ordergoods + "goodsCount");
                    orderHistorydetailsModel.getModifiedOrderGoodsList().add(goodsDiffList);
                }
            }
        }
    }

    /**
     * 受注決済の変更箇所の差分セット<br/>
     *
     * @param orderHistorydetailsModel Model
     * @param modified                 修正後ReceiveOrderDto
     * @param original                 修正前ReceiveOrderDto
     * @param customParams             案件用引数
     */
    protected void setOrderSettlementDiff(OrderHistoryDetailsModel orderHistorydetailsModel,
                                          ReceiveOrderDto modified,
                                          ReceiveOrderDto original,
                                          Object... customParams) {

        orderHistorydetailsModel.setModifiedOrderSettlementList(
                        DiffUtil.diff(original.getOrderSettlementEntity(), modified.getOrderSettlementEntity()));
    }

    /**
     * その他追加料金の変更箇所の差分セット<br/>
     *
     * @param orderHistorydetailsModel Model
     * @param modified                 修正後ReceiveOrderDto
     * @param original                 修正前ReceiveOrderDto
     * @param customParams             案件用引数
     */
    protected void setAddtionalChargeDiff(OrderHistoryDetailsModel orderHistorydetailsModel,
                                          List<OrderAdditionalChargeEntity> modified,
                                          List<OrderAdditionalChargeEntity> original,
                                          Object... customParams) {

        List<OrderAdditionalChargeItem> orderAdditionalChargeItems =
                        orderHistorydetailsModel.getOrderAdditionalChargeItems();
        Iterator<OrderAdditionalChargeItem> orderAdditionalChargeIte = orderAdditionalChargeItems.iterator();
        Iterator<OrderAdditionalChargeEntity> modifiedAddChargeIte = modified.iterator();
        Iterator<OrderAdditionalChargeEntity> originalAddChargeIte = original.iterator();

        orderHistorydetailsModel.setModifiedAdditionalChargeList(new ArrayList<>());

        while (orderAdditionalChargeIte.hasNext()) {
            orderAdditionalChargeIte.next();
            if (modifiedAddChargeIte.hasNext()) {
                OrderAdditionalChargeEntity modifiedAddCharge = modifiedAddChargeIte.next();
                if (originalAddChargeIte.hasNext()) {
                    OrderAdditionalChargeEntity originalAddCharge = originalAddChargeIte.next();
                    orderHistorydetailsModel.getModifiedAdditionalChargeList()
                                            .add(DiffUtil.diff(originalAddCharge, modifiedAddCharge));
                } else {
                    orderHistorydetailsModel.getModifiedAdditionalChargeList()
                                            .add(DiffUtil.diff(new OrderAdditionalChargeEntity(), modifiedAddCharge));
                }
            }
        }
    }

    /**
     * 受注請求の変更箇所の差分セット<br/>
     *
     * @param orderHistorydetailsModel Model
     * @param modified                 修正後ReceiveOrderDto
     * @param original                 修正前ReceiveOrderDto
     * @param customParams             案件用引数
     */
    protected void setOrderBillDiff(OrderHistoryDetailsModel orderHistorydetailsModel,
                                    ReceiveOrderDto modified,
                                    ReceiveOrderDto original,
                                    Object... customParams) {

        orderHistorydetailsModel.setModifiedOrderBillList(
                        DiffUtil.diff(original.getOrderBillEntity(), modified.getOrderBillEntity()));
    }

    /**
     * 受注メモの変更箇所の差分セット<br/>
     *
     * @param orderHistorydetailsModel Model
     * @param modified                 修正後ReceiveOrderDto
     * @param original                 修正前ReceiveOrderDto
     * @param customParams             案件用引数
     */
    protected void setOrderMemoDiff(OrderHistoryDetailsModel orderHistorydetailsModel,
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

        orderHistorydetailsModel.setModifiedMemoList(
                        DiffUtil.diff(original.getOrderMemoEntity(), modified.getOrderMemoEntity()));
    }

    /**
     * 受注入金の変更箇所の差分セット<br/>
     *
     * @param orderHistorydetailsModel Model
     * @param modified                 修正後ReceiveOrderDto
     * @param original                 修正前ReceiveOrderDto
     * @param customParams             案件用引数
     */
    protected void setReceiptMoneyDiff(OrderHistoryDetailsModel orderHistorydetailsModel,
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

                orderHistorydetailsModel.setModifiedReceiptMoneyList(moneyDiffList);
            }
        }
    }

    /**
     * 受注インデックスの変更箇所の差分セット<br/>
     *
     * @param orderHistorydetailsModel Model
     * @param modified                 修正後ReceiveOrderDto
     * @param original                 修正前ReceiveOrderDto
     * @param customParams             案件用引数
     */
    protected void setOrderIndexDiff(OrderHistoryDetailsModel orderHistorydetailsModel,
                                     ReceiveOrderDto modified,
                                     ReceiveOrderDto original,
                                     Object... customParams) {

        orderHistorydetailsModel.setModifiedOrderIndexList(
                        DiffUtil.diff(original.getOrderIndexEntity(), modified.getOrderIndexEntity()));
    }

    /**
     * 受注決済詳細の変更箇所の差分セット<br/>
     *
     * @param orderHistorydetailsModel Model
     * @param modified                 修正後ReceiveOrderDto
     * @param original                 修正前ReceiveOrderDto
     * @param customParams             案件用引数
     */
    protected void setOrderSettlementDetailDiff(OrderHistoryDetailsModel orderHistorydetailsModel,
                                                ReceiveOrderDto modified,
                                                ReceiveOrderDto original,
                                                Object... customParams) {

        orderHistorydetailsModel.setModifiedSettlementMethodList(
                        DiffUtil.diff(original.getSettlementMethodEntity(), modified.getSettlementMethodEntity()));
    }

    /**
     * お支払方法詳細（マルペイ請求）の変更箇所の差分セット<br/>
     *
     * @param orderHistorydetailsModel Model
     * @param modified                 修正後ReceiveOrderDto
     * @param original                 修正前ReceiveOrderDto
     * @param customParams             案件用引数
     */
    protected void setMulPayBillDiff(OrderHistoryDetailsModel orderHistorydetailsModel,
                                     ReceiveOrderDto modified,
                                     ReceiveOrderDto original,
                                     Object... customParams) {

        orderHistorydetailsModel.setModifiedMulPayBillList(
                        DiffUtil.diff(original.getMulPayBillEntity(), modified.getMulPayBillEntity()));
    }
}
