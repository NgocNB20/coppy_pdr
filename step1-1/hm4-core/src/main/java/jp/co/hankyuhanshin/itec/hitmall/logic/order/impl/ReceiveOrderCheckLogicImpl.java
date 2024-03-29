/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipmentStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.order.settlement.OrderSettlementDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliverySpecialChargeAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderForCheckDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractOrderCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsListGetBySeqLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsMapGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.BillPriceCalculateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.ReceiveOrderCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.member.SimultaneousOrderExclusionGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon.CouponCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleAreaGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodSelectListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodTypeCarriageListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.ReceiverDateGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.CheckMessageUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.CouponUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 受注可能チェックロジック実装クラス
 *
 * @author hirata
 * @version $Revision: 1.12 $
 */
@Component
public class ReceiveOrderCheckLogicImpl extends AbstractOrderCheckLogic implements ReceiveOrderCheckLogic {

    /**
     * 受注決済Dao
     */
    private final OrderSettlementDao orderSettlementDao;

    @Autowired
    public ReceiveOrderCheckLogicImpl(CheckMessageUtility checkMessageUtility,
                                      OrderUtility orderUtility,
                                      CalculatePriceUtility calculatePriceUtility,
                                      CouponUtility couponUtility,
                                      OrderGoodsListGetLogic orderGoodsListGetLogic,
                                      GoodsDetailsMapGetLogic goodsDetailsMapGetLogic,
                                      BillPriceCalculateLogic billPriceCalculateLogic,
                                      DeliveryImpossibleAreaGetLogic deliveryImpossibleAreaGetLogic,
                                      DeliverySpecialChargeAreaDao deliverySpecialChargeAreaDao,
                                      DeliveryMethodTypeCarriageListGetLogic deliveryMethodTypeCarriageListGetLogic,
                                      DeliveryMethodGetLogic deliveryMethodGetLogic,
                                      DeliveryMethodSelectListGetLogic deliveryMethodSelectListGetLogic,
                                      SettlementMethodGetLogic settlementMethodGetLogic,
                                      SettlementMethodListGetLogic settlementMethodListGetLogic,
                                      ReceiverDateGetLogic receiverDateGetLogic,
                                      CouponCheckLogic couponCheckLogic,
                                      SimultaneousOrderExclusionGetLogic simultaneousOrderExclusionGetLogic,
                                      GoodsDetailsListGetBySeqLogic goodsDetailsListGetBySeqLogic,
                                      OrderSettlementDao orderSettlementDao) {
        super(checkMessageUtility, orderUtility, calculatePriceUtility, couponUtility, orderGoodsListGetLogic,
              goodsDetailsMapGetLogic, billPriceCalculateLogic, deliveryImpossibleAreaGetLogic,
              deliverySpecialChargeAreaDao, deliveryMethodTypeCarriageListGetLogic, deliveryMethodGetLogic,
              deliveryMethodSelectListGetLogic, settlementMethodGetLogic, settlementMethodListGetLogic,
              receiverDateGetLogic, couponCheckLogic, simultaneousOrderExclusionGetLogic, goodsDetailsListGetBySeqLogic
             );
        this.orderSettlementDao = orderSettlementDao;
    }

    /**
     * 初期処理<br/>
     * 請求金額を計算します。<br/>
     *
     * @param receiveOrderDto    受注DTO
     * @param orderMessageDto    注文メッセージDTO
     * @param calculateFlag      受注金額算出フラグ
     * @param commissionCalcFlag 手数料金額適用フラグ。true..計算前の手数料を適用する
     * @param carriageCalcFlag   送料金額適用フラグ。true..計算前の送料を適用する
     */
    @Override
    public void beforeProcess(ReceiveOrderDto receiveOrderDto,
                              OrderMessageDto orderMessageDto,
                              boolean calculateFlag,
                              boolean commissionCalcFlag,
                              boolean carriageCalcFlag,
                              String memberInfoId,
                              HTypeSiteType siteType,
                              Integer memberInfoSeq,
                              String adminId) {
        // 受注サマリー作成
        if (receiveOrderDto.getOrderSummaryEntity() == null) {

            OrderSummaryEntity orderSummaryEntity = ApplicationContextUtility.getBean(OrderSummaryEntity.class);
            orderSummaryEntity.setShopSeq(1001);
            // 受注サイト種別
            orderSummaryEntity.setOrderSiteType(siteType);
            // 会員SEQ
            orderSummaryEntity.setMemberInfoSeq(receiveOrderDto.getOrderPersonEntity().getMemberInfoSeq());
            // 都道府県別種別
            orderSummaryEntity.setPrefectureType(receiveOrderDto.getOrderPersonEntity().getPrefectureType());
            // ご注文主性別
            orderSummaryEntity.setOrderSex(receiveOrderDto.getOrderPersonEntity().getOrderSex());
            // ご注文主年代
            orderSummaryEntity.setOrderAgeType(receiveOrderDto.getOrderPersonEntity().getOrderAgeType());
            receiveOrderDto.setOrderSummaryEntity(orderSummaryEntity);
        }

        // 請求金額算出処理実行
        super.beforeProcess(receiveOrderDto, orderMessageDto, calculateFlag, commissionCalcFlag, carriageCalcFlag,
                            memberInfoId, siteType, memberInfoSeq, adminId
                           );
    }

    /**
     * 受注修正用初期処理<br/>
     * 請求金額を計算します。<br/>
     *
     * @param receiveOrderDto    受注DTO
     * @param baseDto            編集前受注DTO
     * @param orderMessageDto    注文メッセージDTO
     * @param calculateFlag      受注金額算出フラグ
     * @param commissionCalcFlag 決済手数料金額適用フラグ。true..計算前の手数料を適用する
     * @param carriageCalcFlag   送料金額適用フラグ。true..計算前の送料を適用する
     */
    protected void beforeProcess(ReceiveOrderDto receiveOrderDto,
                                 ReceiveOrderDto baseDto,
                                 OrderMessageDto orderMessageDto,
                                 boolean calculateFlag,
                                 boolean commissionCalcFlag,
                                 boolean carriageCalcFlag,
                                 String memberInfoId,
                                 HTypeSiteType siteType,
                                 Integer memberInfoSeq,
                                 String adminId) {

        // 初期処理
        beforeProcess(receiveOrderDto, orderMessageDto, calculateFlag, commissionCalcFlag, carriageCalcFlag,
                      memberInfoId, siteType, memberInfoSeq, adminId
                     );

        if (receiveOrderDto.getOrderSettlementEntity().getSettlementMethodSeq() == null) {
            return;
        }

        // 支払期限とキャンセル可能日はそのままかどうか
        if (isNotChangePaymentTimeLimitDateAndCancelableDate(receiveOrderDto, baseDto)) {
            receiveOrderDto.getOrderBillEntity()
                           .setPaymentTimeLimitDate(baseDto.getOrderBillEntity().getPaymentTimeLimitDate());
            receiveOrderDto.getOrderBillEntity().setCancelableDate(baseDto.getOrderBillEntity().getCancelableDate());
        }
    }

    /**
     * 支払期限・キャンセル可能日変更なしチェック処理
     * <pre>
     * 支払期限・キャンセル可能日の変更は行わない状態かどうかチェックする
     * </pre>
     *
     * @param receiveOrderDto 受注DTO
     * @param baseDto         編集前受注DTO
     * @return true:支払期限・キャンセル可能日変更なし false:支払期限・キャンセル可能日変更あり
     */
    protected boolean isNotChangePaymentTimeLimitDateAndCancelableDate(ReceiveOrderDto receiveOrderDto,
                                                                       ReceiveOrderDto baseDto) {

        // 決済方法/決済金額に変更がない場合、支払期限とキャンセル可能日はそのまま
        if (isNotChangeSettlement(receiveOrderDto, baseDto) && (isNotChangeOrderPrice(receiveOrderDto, baseDto))) {
            return true;
        }

        // 修正前＆修正後の決済方法＝コンビニ決済 且つ、出荷状態＝出荷済み 且つ、修正前の受注金額＞受注金額の場合
        // 支払期限とキャンセル可能日はそのまま
        if (receiveOrderDto.isConvenience() && baseDto.isConvenience() && receiveOrderDto.isShipment()
            && isChangeOrderPriceMinus(receiveOrderDto, baseDto)) {
            return true;
        }
        // 修正前の決済方法＝Pay-easy決済 且つ、出荷状態＝出荷済み 且つ、修正前の受注金額＞受注金額 であるかをチェック
        if (receiveOrderDto.isPayEasy() && baseDto.isPayEasy() && receiveOrderDto.isShipment()
            && isChangeOrderPriceMinus(receiveOrderDto, baseDto)) {
            return true;
        }
        return false;
    }

    /**
     * 修正前後決済方法変更なしチェック処理
     * <pre>
     * 変更前の受注DTOと変更後の受注DTOで決済方法が変更されてないかチェックする
     * </pre>
     *
     * @param receiveOrderDto 変更後DTO
     * @param baseDto         変更前DTO
     * @return true:変更なし false:変更あり
     */
    protected boolean isNotChangeSettlement(ReceiveOrderDto receiveOrderDto, ReceiveOrderDto baseDto) {
        return receiveOrderDto.getOrderSettlementEntity().getSettlementMethodSeq().intValue()
               == baseDto.getOrderSettlementEntity().getSettlementMethodSeq().intValue();
    }

    /**
     * 受注金額変更なしチェック処理
     * <pre>
     * 受注金額に変更がないことをチェックする
     * </pre>
     *
     * @param receiveOrderDto 変更後DTO
     * @param baseDto         変更前DTO
     * @return true:変更なし false:変更あり
     */
    protected boolean isNotChangeOrderPrice(ReceiveOrderDto receiveOrderDto, ReceiveOrderDto baseDto) {
        return baseDto.getOrderSummaryEntity()
                      .getOrderPrice()
                      .compareTo(receiveOrderDto.getOrderSummaryEntity().getOrderPrice()) == 0;
    }

    /**
     * 受注金額減額チェック処理
     * <pre>
     * 受注金額が減額されているかチェックする
     * </pre>
     *
     * @param receiveOrderDto 変更後DTO
     * @param baseDto         変更前DTO
     * @return true;減額されている false:減額されていない
     */
    protected boolean isChangeOrderPriceMinus(ReceiveOrderDto receiveOrderDto, ReceiveOrderDto baseDto) {
        return baseDto.getOrderSummaryEntity()
                      .getOrderPrice()
                      .compareTo(receiveOrderDto.getOrderSummaryEntity().getOrderPrice()) > 0;
    }

    /**
     * 受注修正チェック処理<br/>
     *
     * @param dto     編集中の受注DTO
     * @param baseDto 編集前の受注DTO
     * @return 受注メッセージDto
     */
    @Override
    public OrderMessageDto execute(ReceiveOrderDto dto,
                                   ReceiveOrderDto baseDto,
                                   String memberInfoId,
                                   HTypeSiteType siteType,
                                   Integer memberInfoSeq,
                                   String adminId) {
        return execute(dto, baseDto, true, false, false, memberInfoId, siteType, memberInfoSeq, adminId);
    }

    /**
     * 受注修正チェック処理<br/>
     *
     * @param dto                編集中の受注DTO
     * @param baseDto            編集前の受注DTO
     * @param calculateFlag      受注計算フラグ。true..計算する / false..計算しない
     * @param commissionCalcFlag 決済手数料適用フラグ。true..計算前の手数料を適用する
     * @param carriageCalcFlag   送料適用フラグ。true..計算前の送料を適用する
     * @return メッセージDTO
     */
    @Override
    public OrderMessageDto execute(ReceiveOrderDto dto,
                                   ReceiveOrderDto baseDto,
                                   boolean calculateFlag,
                                   boolean commissionCalcFlag,
                                   boolean carriageCalcFlag,
                                   String memberInfoId,
                                   HTypeSiteType siteType,
                                   Integer memberInfoSeq,
                                   String adminId) {
        // 注文メッセージDTOの初期化
        OrderMessageDto orderMessageDto = ApplicationContextUtility.getBean(OrderMessageDto.class);
        orderMessageDto.setOrderGoodsMessageMapMap(new HashMap<Integer, Map<Integer, List<CheckMessageDto>>>());
        orderMessageDto.setOrderMessageList(new ArrayList<CheckMessageDto>());

        // 初期処理
        beforeProcess(dto, baseDto, orderMessageDto, calculateFlag, commissionCalcFlag, carriageCalcFlag, memberInfoId,
                      siteType, memberInfoSeq, adminId
                     );

        // カートが空の場合
        List<OrderGoodsEntity> orderGoodsEntityList = super.getOrderUtility().getALLGoodsEntityList(dto);
        if (CollectionUtil.isEmpty(orderGoodsEntityList)) {
            return orderMessageDto;
        }

        // チェック用Dto取得
        OrderForCheckDto checkDto = ApplicationContextUtility.getBean(OrderForCheckDto.class);
        // 会員SEQセット
        checkDto.setMemberInfoSeq(dto.getOrderSummaryEntity().getMemberInfoSeq());

        List<Integer> goodsSeqList = new ArrayList<>();
        for (OrderGoodsEntity orderGoodsEntity : dto.getOrderDeliveryDto().getOrderGoodsEntityList()) {
            goodsSeqList.add(orderGoodsEntity.getGoodsSeq());
        }
        checkDto.setGoodsSeqList(goodsSeqList);

        // 受注単位のチェック
        checkPerOrder(dto, baseDto, orderMessageDto, siteType);

        // 決済方法エンティティDTOが受注DTOに存在しない場合は、処理を行わない
        if (dto.getSettlementMethodEntity() != null) {
            // 後続の処理を行うかの判定用に、エラー数を保持
            List<CheckMessageDto> messageList = orderMessageDto.getOrderMessageList();
            int errorCount = messageList.size();

            // 全額割引決済方法と受注金額の整合性チェックを行う
            checkCanUseDiscountSettlement(dto, messageList);
            if (errorCount != messageList.size()) {
                // 決済方法の選択エラーが発生しているので以降の処理は不要
                return orderMessageDto;
            }
        }

        // 決済可能金額チェック
        if (dto.getSettlementMethodEntity() != null) {
            maxPurchasedPriceOverCheck(dto.getSettlementMethodEntity(), dto.getOrderSummaryEntity().getOrderPrice(),
                                       orderMessageDto.getOrderMessageList(), siteType
                                      );
        }

        // 決済別変更制約チェック
        settlementChangeRestrictCheck(dto, baseDto, orderMessageDto);

        return orderMessageDto;
    }

    /**
     * 商品チェック<br/>
     * 商品の状態、組み合わせより、購入できる商品かチェックします。<br/>
     *
     * @param dto             受注DTO
     * @param baseDto         編集前受注DTO
     * @param orderMessageDto 注文メッセージDTO
     */
    protected void checkPerOrder(ReceiveOrderDto dto,
                                 ReceiveOrderDto baseDto,
                                 OrderMessageDto orderMessageDto,
                                 HTypeSiteType siteType) {

        // 商品チェック前の準備
        OrderForCheckDto checkDto = prepareForGoodsCheck(dto, false);

        // 商品詳細マップマップ取得
        Map<Integer, Map<Integer, GoodsDetailsDto>> goodsDetailsMapMap =
                        getGoodsDetailsMapMap(dto.getOrderDeliveryDto(), checkDto);

        // TODO-QUAD-1222
        // 配送チェック
        Integer orderConsecutiveNo = dto.getOrderDeliveryDto().getOrderDeliveryEntity().getOrderConsecutiveNo();

        // 商品チェック
        checkPerDelivery(dto, checkDto, dto.getOrderDeliveryDto(), baseDto.getOrderDeliveryDto(),
                         goodsDetailsMapMap.get(orderConsecutiveNo), orderMessageDto
                        );

        // 配送方法整合性チェック
        if (CollectionUtil.isNotEmpty(dto.getOrderDeliveryDto().getDeliveryDtoList())) {
            checkDeliveryMethodCombination(
                            dto, checkDto.getDeliveryList(), orderMessageDto.getOrderMessageList(), siteType);
        }

        // 利用可能決済方法チェック
        possibleSettlementCheck(checkDto.getSettlementList(), orderMessageDto.getOrderMessageList());

        // 決済方法整合性チェック
        if (dto.getSettlementMethodEntity() != null) {
            checkSettlementMethod(dto, checkDto.getSettlementList(), orderMessageDto.getOrderMessageList(), siteType);
        }
        if (dto.isPeriodicOrderFlag()) {
            checkPeriodicSettlementMethod(
                            dto, checkDto.getSettlementList(), orderMessageDto.getOrderMessageList(), siteType);
        }
    }

    /**
     * 商品ごとに数量差分をマージ<br/>
     *
     * @param deliveryDto     受注配送Dto
     * @param baseDeliveryDto 編集前受注配送Dto
     * @param addOnlyFlg      増加分のみフラグ (true : 増加分のみを差分数量マージする)
     * @return 商品SEQごとの差分数量MAP
     */
    protected Map<Integer, BigDecimal> getGoodsCountDiffMap(OrderDeliveryDto deliveryDto,
                                                            OrderDeliveryDto baseDeliveryDto,
                                                            boolean addOnlyFlg) {
        // 商品SEQ別差分数量MAP
        Map<Integer, BigDecimal> goodsCountDiffMap = new HashMap<>();

        // 修正前の受注配送Dtoがnull == 新しくできた配送先なので、
        // 空マップを返す
        if (baseDeliveryDto == null) {
            return goodsCountDiffMap;
        }
        List<OrderGoodsEntity> entityList = deliveryDto.getOrderGoodsEntityList();
        List<OrderGoodsEntity> baseEntityList = baseDeliveryDto.getOrderGoodsEntityList();

        if (CollectionUtil.isEmpty(entityList) || CollectionUtil.isEmpty(baseEntityList)) {
            return goodsCountDiffMap;
        }

        // 各商品SEQ毎にマップへ差分を設定
        for (int i = 0; i < baseEntityList.size() || i < entityList.size(); i++) {
            if (i < baseEntityList.size() && i < entityList.size()) {
                // 編集前と編集中の両方に商品を持つ場合（注文数変更）
                setGoodsCountDiffMapForCountChange(
                                addOnlyFlg, goodsCountDiffMap, baseEntityList.get(i), entityList.get(i));
            } else if (i >= baseEntityList.size() && i < entityList.size()) {
                // 編集中にのみ商品を持つ場合（商品追加）
                setGoodsCountDiffMapForAddGoods(goodsCountDiffMap, entityList.get(i));
            }
        }

        return goodsCountDiffMap;
    }

    /**
     * 商品SEQ別差分数量MAP設定（商品追加）<br/>
     *
     * @param goodsCountDiffMap 商品SEQ別差分数量MAP
     * @param orderGoodsEntity  受注商品エンティティ
     * @param customParams      案件用引数
     */
    protected void setGoodsCountDiffMapForAddGoods(Map<Integer, BigDecimal> goodsCountDiffMap,
                                                   OrderGoodsEntity orderGoodsEntity,
                                                   Object... customParams) {
        // マップに登録済みの数量
        BigDecimal tempGoodsDiffCount = goodsCountDiffMap.get(orderGoodsEntity.getGoodsSeq());
        // 数量の取得
        BigDecimal goodsCount = orderGoodsEntity.getGoodsCount();
        // 差分数量マップの設定
        if (tempGoodsDiffCount == null) {
            goodsCountDiffMap.put(orderGoodsEntity.getGoodsSeq(), goodsCount);
        } else {
            goodsCountDiffMap.put(orderGoodsEntity.getGoodsSeq(), tempGoodsDiffCount.add(goodsCount));
        }
    }

    /**
     * 商品SEQ別差分数量MAP設定（注文数変更）<br/>
     *
     * @param addOnlyFlg           増加分のみフラグ (true : 増加分のみを差分数量マージする)
     * @param goodsCountDiffMap    商品SEQ別差分数量MAP
     * @param baseOrderGoodsEntity 編集前受注商品エンティティ
     * @param orderGoodsEntity     受注商品エンティティ
     * @param customParams         案件用引数
     */
    protected void setGoodsCountDiffMapForCountChange(boolean addOnlyFlg,
                                                      Map<Integer, BigDecimal> goodsCountDiffMap,
                                                      OrderGoodsEntity baseOrderGoodsEntity,
                                                      OrderGoodsEntity orderGoodsEntity,
                                                      Object... customParams) {
        if (baseOrderGoodsEntity.getGoodsSeq().equals(orderGoodsEntity.getGoodsSeq())) {
            // マップに登録済みの数量
            BigDecimal tempGoodsDiffCount = goodsCountDiffMap.get(orderGoodsEntity.getGoodsSeq());
            // 編集前数量
            BigDecimal baseGoodsCount = baseOrderGoodsEntity.getGoodsCount();
            // 数量
            BigDecimal goodsCount = orderGoodsEntity.getGoodsCount();
            if (goodsCount.compareTo(baseGoodsCount) > 0 || (goodsCount.compareTo(baseGoodsCount) < 0 && !addOnlyFlg)) {
                // 差分数量マップの設定
                if (tempGoodsDiffCount == null) {
                    goodsCountDiffMap.put(orderGoodsEntity.getGoodsSeq(), goodsCount.subtract(baseGoodsCount));
                } else {
                    goodsCountDiffMap.put(orderGoodsEntity.getGoodsSeq(),
                                          tempGoodsDiffCount.add(goodsCount.subtract(baseGoodsCount))
                                         );
                }
            }
        }
    }

    /**
     * 商品チェック<br/>
     *
     * @param dto               受注Dto
     * @param checkDto          チェック用Dto
     * @param deliveryDto       受注配送Dto
     * @param baseDeliveryDto   修正前受注配送Dto
     * @param goodsDtailsDtoMap 商品詳細Dtoマップ(配送先に含まれている商品)
     * @param orderMessageDto   メッセージDto
     */
    protected void checkPerDelivery(ReceiveOrderDto dto,
                                    OrderForCheckDto checkDto,
                                    OrderDeliveryDto deliveryDto,
                                    OrderDeliveryDto baseDeliveryDto,
                                    Map<Integer, GoodsDetailsDto> goodsDtailsDtoMap,
                                    OrderMessageDto orderMessageDto) {

        // 注文連番
        Integer consecutiveNo = deliveryDto.getOrderDeliveryEntity().getOrderConsecutiveNo();

        Map<Integer, List<CheckMessageDto>> msgMap = new HashMap<>();

        Map<Integer, BigDecimal> goodsCntDiffMap = getGoodsCountDiffMap(deliveryDto, baseDeliveryDto, false);

        // 数量0の商品を除外したリストを作成する
        List<OrderGoodsEntity> orderGoodsEntityList = super.getOrderUtility()
                                                           .getGoodsEntityListExceptCountZero(
                                                                           deliveryDto.getOrderGoodsEntityList());

        if (CollectionUtil.isEmpty(orderGoodsEntityList)) {
            return;
        }

        for (OrderGoodsEntity orderGoods : orderGoodsEntityList) {
            Integer goodsSeq = orderGoods.getGoodsSeq();
            GoodsDetailsDto goodsDtails = goodsDtailsDtoMap.get(goodsSeq);

            List<CheckMessageDto> msgList = new ArrayList<>();

            // 差分数
            BigDecimal diffCount = goodsCntDiffMap.get(goodsSeq);
            // 差分数 > 0 の商品
            if (diffCount != null && diffCount.compareTo(BigDecimal.ZERO) > 0) {
                // 公開状態チェック
                openStatusCheck(goodsDtails, msgList);
                // 販売状態チェック
                saleStatusCheck(goodsDtails, msgList);
                // 在庫チェック
                salesPossibleStockOverCheck(goodsDtails, diffCount, msgList);
            }

            // マージ数
            BigDecimal goodsCount = checkDto.getGoodsCntMap().get(goodsSeq);
            // マージ数 > 0の商品
            if (goodsCount != null && goodsCount.compareTo(BigDecimal.ZERO) > 0) {
                // 購入制限数チェック
                purchasedMaxOverCheck(goodsDtails, orderGoods, goodsCount, msgList);
            }
            if (msgList != null && msgList.size() > 0) {
                msgMap.put(goodsSeq, msgList);
            }
        }

        // 個別配送チェック
        individualDeliveryCheck(goodsDtailsDtoMap, orderGoodsEntityList, msgMap);

        orderMessageDto.getOrderGoodsMessageMapMap().put(consecutiveNo, msgMap);

    }

    /**
     * 決済別変更制約チェック<br/>
     * 決済方法が変更可能かどうかチェックします。<br/>
     *
     * @param dto             受注DTO
     * @param baseDto         編集前受注DTO
     * @param orderMessageDto 注文メッセージDTO
     */
    protected void settlementChangeRestrictCheck(ReceiveOrderDto dto,
                                                 ReceiveOrderDto baseDto,
                                                 OrderMessageDto orderMessageDto) {

        // 選択した受注決済方法
        HTypeSettlementMethodType settlementMethodType = dto.getOrderSettlementEntity().getSettlementMethodType();

        if (HTypeSettlementMethodType.CREDIT == settlementMethodType) {
            // クレジット決済への変更の場合
            restrictCheckSettlementChangeForCredit(dto, baseDto, orderMessageDto);
        } else if (HTypeSettlementMethodType.CONVENIENCE == settlementMethodType) {
            // コンビニ決済への変更の場合
            restrictCheckSettlementChangeForConvenience(dto, baseDto, orderMessageDto);
        } else if (HTypeSettlementMethodType.PAY_EASY == settlementMethodType) {
            // Pay-easy決済への変更の場合
            restrictCheckSettlementChangeForPayEasy(dto, baseDto, orderMessageDto);
        } else if (HTypeSettlementMethodType.RECEIPT_PAYMENT == settlementMethodType) {
            // 代金引換への変更の場合
            restrictCheckSettlementChangeForReceiptPayment(dto, baseDto, orderMessageDto);

        }

    }

    /**
     * クレジット決済変更制約チェック
     *
     * @param dto             編集後受注DTO
     * @param baseDto         編集前受注DTO
     * @param orderMessageDto 注文メッセージDTO
     * @param customParams    案件用引数
     */
    protected void restrictCheckSettlementChangeForCredit(ReceiveOrderDto dto,
                                                          ReceiveOrderDto baseDto,
                                                          OrderMessageDto orderMessageDto,
                                                          Object... customParams) {
        HTypeSettlementMethodType baseSettlementMethodType =
                        baseDto.getOrderSettlementEntity().getSettlementMethodType();

        // 編集前の決済方法がクレジットでない場合、エラー
        if (HTypeSettlementMethodType.CREDIT != baseSettlementMethodType) {
            HTypeSettlementMethodType settlementMethodType = dto.getOrderSettlementEntity().getSettlementMethodType();
            Object[] args = new Object[] {EnumTypeUtil.getLabelValue(settlementMethodType)};
            CheckMessageDto checkMessageDto = super.toCheckMessageDto(MSGCD_SETTLEMENT_CHANGE_ERROR, args, true);
            orderMessageDto.getOrderMessageList().add(checkMessageDto);
        }
    }

    /**
     * コンビニ決済変更制約チェック
     *
     * @param dto             編集後受注DTO
     * @param baseDto         編集前受注DTO
     * @param orderMessageDto 注文メッセージDTO
     * @param customParams    案件用引数
     */
    protected void restrictCheckSettlementChangeForConvenience(ReceiveOrderDto dto,
                                                               ReceiveOrderDto baseDto,
                                                               OrderMessageDto orderMessageDto,
                                                               Object... customParams) {
        HTypeSettlementMethodType baseSettlementMethodType =
                        baseDto.getOrderSettlementEntity().getSettlementMethodType();
        OrderSettlementEntity settlement = dto.getOrderSettlementEntity();
        HTypeSettlementMethodType settlementMethodType = settlement.getSettlementMethodType();

        // 決済方法=コンビニへ変更、かつ、同受注で過去にコンビニを選択していた場合、エラー
        if (HTypeSettlementMethodType.CONVENIENCE != baseSettlementMethodType && isSelectedSettlementMethodType(
                        settlement)) {
            Object[] args = new Object[] {EnumTypeUtil.getLabelValue(settlementMethodType)};
            CheckMessageDto checkMessageDto = super.toCheckMessageDto(MSGCD_POST_SETTLEMENT_ERROR, args, true);
            orderMessageDto.getOrderMessageList().add(checkMessageDto);
        }
        // 決済方法=コンビニのままの場合
        else if (HTypeSettlementMethodType.CONVENIENCE == baseSettlementMethodType) {
            HTypeShipmentStatus shipmentStatus = super.getOrderUtility()
                                                      .getShipmentStatusByOrderStatus(
                                                                      dto.getOrderSummaryEntity().getOrderStatus());
            BigDecimal orderPrice = dto.getOrderSummaryEntity().getOrderPrice();
            BigDecimal baseOrderPrice = baseDto.getOrderSummaryEntity().getOrderPrice();

            // 出荷状態=未出荷、かつ、金額変更の場合、エラー
            if (HTypeShipmentStatus.UNSHIPMENT == shipmentStatus && orderPrice.compareTo(baseOrderPrice) != 0) {
                Object[] args = new Object[] {EnumTypeUtil.getLabelValue(settlementMethodType)};
                CheckMessageDto checkMessageDto =
                                super.toCheckMessageDto(MSGCD_SETTLEMENT_CHANGE_AMOUNT_ERROR, args, true);
                orderMessageDto.getOrderMessageList().add(checkMessageDto);
            }
            // 出荷状態=出荷済、かつ、修正前受注金額<受注金額の場合、エラー
            else if (HTypeShipmentStatus.SHIPPED == shipmentStatus && orderPrice.compareTo(baseOrderPrice) > 0) {
                Object[] args = new Object[] {EnumTypeUtil.getLabelValue(settlementMethodType)};
                CheckMessageDto checkMessageDto =
                                super.toCheckMessageDto(MSGCD_SETTLEMENT_CHANGE_AMOUNT_ERROR, args, true);
                orderMessageDto.getOrderMessageList().add(checkMessageDto);
            }
        }
    }

    /**
     * 決済別変更制約チェック（Pay-easy決済）
     *
     * @param dto             編集後受注DTO
     * @param baseDto         編集前受注DTO
     * @param orderMessageDto 注文メッセージDTO
     * @param customParams    案件用引数
     */
    protected void restrictCheckSettlementChangeForPayEasy(ReceiveOrderDto dto,
                                                           ReceiveOrderDto baseDto,
                                                           OrderMessageDto orderMessageDto,
                                                           Object... customParams) {
        HTypeSettlementMethodType baseSettlementMethodType =
                        baseDto.getOrderSettlementEntity().getSettlementMethodType();
        OrderSettlementEntity settlement = dto.getOrderSettlementEntity();
        HTypeSettlementMethodType settlementMethodType = settlement.getSettlementMethodType();

        // 決済方法=Pay-easyへ変更、かつ、同受注で過去にPay-easyを選択していた場合、エラー
        if (HTypeSettlementMethodType.PAY_EASY != baseSettlementMethodType && isSelectedSettlementMethodType(
                        settlement)) {
            Object[] args = new Object[] {EnumTypeUtil.getLabelValue(settlementMethodType)};
            CheckMessageDto checkMessageDto = super.toCheckMessageDto(MSGCD_POST_SETTLEMENT_ERROR, args, true);
            orderMessageDto.getOrderMessageList().add(checkMessageDto);
        }
        // 決済方法=Pay-easyのままの場合
        else if (HTypeSettlementMethodType.PAY_EASY == baseSettlementMethodType) {
            HTypeShipmentStatus shipmentStatus = super.getOrderUtility()
                                                      .getShipmentStatusByOrderStatus(
                                                                      dto.getOrderSummaryEntity().getOrderStatus());
            BigDecimal orderPrice = dto.getOrderSummaryEntity().getOrderPrice();
            BigDecimal baseOrderPrice = baseDto.getOrderSummaryEntity().getOrderPrice();

            // 出荷状態=未出荷、かつ、金額変更の場合、エラー
            if (HTypeShipmentStatus.UNSHIPMENT == shipmentStatus && orderPrice.compareTo(baseOrderPrice) != 0) {
                Object[] args = new Object[] {EnumTypeUtil.getLabelValue(settlementMethodType)};
                CheckMessageDto checkMessageDto =
                                super.toCheckMessageDto(MSGCD_SETTLEMENT_CHANGE_AMOUNT_ERROR, args, true);
                orderMessageDto.getOrderMessageList().add(checkMessageDto);
            }
            // 出荷状態=出荷済、かつ、修正前受注金額<受注金額の場合、エラー
            else if (HTypeShipmentStatus.SHIPPED == shipmentStatus && orderPrice.compareTo(baseOrderPrice) > 0) {
                Object[] args = new Object[] {EnumTypeUtil.getLabelValue(settlementMethodType)};
                CheckMessageDto checkMessageDto =
                                super.toCheckMessageDto(MSGCD_SETTLEMENT_CHANGE_AMOUNT_ERROR, args, true);
                orderMessageDto.getOrderMessageList().add(checkMessageDto);
            }
        }
    }

    /**
     * 決済別変更制約チェック（代金引換）
     *
     * @param dto             編集後受注DTO
     * @param baseDto         編集前受注DTO
     * @param orderMessageDto 注文メッセージDTO
     * @param customParams    案件用引数
     */
    protected void restrictCheckSettlementChangeForReceiptPayment(ReceiveOrderDto dto,
                                                                  ReceiveOrderDto baseDto,
                                                                  OrderMessageDto orderMessageDto,
                                                                  Object... customParams) {
        // 出荷状態=出荷済の場合
        if (HTypeOrderStatus.SHIPMENT_COMPLETION == dto.getOrderSummaryEntity().getOrderStatus()) {
            OrderSettlementEntity baseSettlement = baseDto.getOrderSettlementEntity();

            // 代金引換以外⇒代金引換の場合、エラー（出荷済で本決済での受注金額の回収が出来ない為）
            if (HTypeSettlementMethodType.RECEIPT_PAYMENT != baseSettlement.getSettlementMethodType()) {
                CheckMessageDto checkMessageDto =
                                super.toCheckMessageDto(MSGCD_IMPOSSIBLE_RECEIPTMONEY_ERROR, null, true);
                orderMessageDto.getOrderMessageList().add(checkMessageDto);
            }
            // 編集前の決済方法が代金引換、かつ、受注合計金額が前回より大きくなった場合、エラー
            else if (dto.getOrderSummaryEntity()
                        .getOrderPrice()
                        .compareTo(baseDto.getOrderSummaryEntity().getOrderPrice()) > 0) {
                CheckMessageDto checkMessageDto =
                                super.toCheckMessageDto(MSGCD_IMPOSSIBLE_RECEIPTMONEY_ERROR, null, true);
                orderMessageDto.getOrderMessageList().add(checkMessageDto);
            }
        }
    }

    /**
     * 決済方法選択済みチェック<br/>
     *
     * @param settlement   受注決済方法エンティティ
     * @param customParams 案件用引数
     * @return 選択済み（true）、非選択（false）
     */
    protected boolean isSelectedSettlementMethodType(OrderSettlementEntity settlement, Object... customParams) {
        return orderSettlementDao.isSelectedSettlementMethodType(settlement.getOrderSeq(),
                                                                 settlement.getSettlementMethodSeq(),
                                                                 settlement.getSettlementMethodType()
                                                                );
    }
}
