/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipmentStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.additional.OrderAdditionalChargeEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.BillPriceCalculateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodSelectListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.CheckMessageUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * PDR#013 09_データ連携（受注データ）
 * 請求金額算出ロジック
 *
 * @author yamaguchi
 * @author matsumoto(itec) 2012/02/06 #2761 対応
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class BillPriceCalculateLogicImpl extends AbstractShopLogic implements BillPriceCalculateLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BillPriceCalculateLogicImpl.class);

    /**
     * 配送方法別送料リスト取得ロジック
     */
    private final DeliveryMethodSelectListGetLogic deliveryMethodSelectListGetLogic;

    /**
     * 決済方法別手数料リスト取得ロジック
     */
    private final SettlementMethodListGetLogic settlementMethodListGetLogic;

    /**
     * DateUtility
     */
    private final DateUtility dateUtility;

    /**
     * 受注ユーティリティ
     */
    private final OrderUtility orderUtility;

    /**
     * 金額計算ユーティリティ
     */
    private final CalculatePriceUtility calculatePriceUtility;

    /**
     * CheckMessageDto生成Utility
     */
    private final CheckMessageUtility checkMessageUtility;

    @Autowired
    public BillPriceCalculateLogicImpl(DeliveryMethodSelectListGetLogic deliveryMethodSelectListGetLogic,
                                       SettlementMethodListGetLogic settlementMethodListGetLogic,
                                       DateUtility dateUtility,
                                       OrderUtility orderUtility,
                                       CalculatePriceUtility calculatePriceUtility,
                                       CheckMessageUtility checkMessageUtility) {
        this.deliveryMethodSelectListGetLogic = deliveryMethodSelectListGetLogic;
        this.settlementMethodListGetLogic = settlementMethodListGetLogic;
        this.dateUtility = dateUtility;
        this.orderUtility = orderUtility;
        this.calculatePriceUtility = calculatePriceUtility;
        this.checkMessageUtility = checkMessageUtility;
    }

    @Override
    public List<CheckMessageDto> execute(ReceiveOrderDto receiveOrderDto, HTypeSiteType siteType, String adminId) {
        return execute(receiveOrderDto, false, false, siteType, adminId);
    }

    @Override
    public List<CheckMessageDto> execute(ReceiveOrderDto receiveOrderDto,
                                         boolean carriageCalcFlag,
                                         HTypeSiteType siteType,
                                         String adminId) {
        // 引数チェック
        argumentCheck(receiveOrderDto);

        // 受注サマリEntity初期化
        OrderSummaryEntity orderSummaryEntity = initOrderSummaryEntity(receiveOrderDto);
        if (orderSummaryEntity == null) {
            return null;
        }

        // 受注決済Entity初期化
        receiveOrderDto.setOrderSettlementEntity(initOrderSettlementEntity(receiveOrderDto.getOrderSettlementEntity()));

        // 定期注文の場合次回決済Entity初期化
        if (receiveOrderDto.isPeriodicOrderFlag()) {
            receiveOrderDto.setOrderNextSettlementEntity(
                            initOrderSettlementEntity(receiveOrderDto.getOrderNextSettlementEntity()));
        }

        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();

        // 受注配送Dto
        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

        // PDR Migrate Customization from here
        // ① 受注商品金額算出
        orderGoodsPriceTotal(receiveOrderDto, orderSummaryEntity, orderDeliveryDto, orderSettlementEntity);
        logForDebug(receiveOrderDto, 0);
        // ②送料算出 削除
        // 別の箇所で行うため
        logForDebug(receiveOrderDto, 1);
        // PDR Migrate Customization to here

        // ③その他金額合計算出
        othersPriceTotal(receiveOrderDto.getOrderAdditionalChargeEntityList(), orderSettlementEntity);
        logForDebug(receiveOrderDto, 2);
        // ④決済手数料算出 ⇒ この時点、決済手数料が必要ではない
        // PDR Migrate Customization from here
        // ⑤消費税算出 削除
        // 別の箇所で行うため
        // PDR Migrate Customization to here
        logForDebug(receiveOrderDto, 4);
        // 割引前受注金額を計算
        calcBeforeDiscountOrderPrice(receiveOrderDto);
        // ⑥注文に対してクーポンを適用する
        CheckMessageDto couponMsg = couponDiscountPrice(receiveOrderDto);
        logForDebug(receiveOrderDto, 5);

        // ⑦利用ポイント算出 ⇒ この時点ポイント算出必要がない。
        // ※ ポイントも算出したら、AmazonPayの注文フローには不具合発生：
        // 全額ポイント選択し、確認画面に進み、また決済方法画面戻ったらAmazonPayの決済方法がなくなる
        // 原因：ポイントも算出したので、受注金額が０になってしまう

        // ⑧受注金額算出
        orderPrice(receiveOrderDto, orderSettlementEntity, orderSummaryEntity);
        logForDebug(receiveOrderDto, 7);
        // PDR Migrate Customization from here
        // 定期受注の次回決済情報作成を削除
        // PDR Migrate Customization to here

        // 戻り値作成
        List<CheckMessageDto> msgList = new ArrayList<>();

        // クーポン適用ボタン押下時はcouponMsg以外のチェックは行わない
        // PDR Migrate Customization from here
        // 送料算出 削除
        // PDR Migrate Customization to here

        if (couponMsg != null) {
            msgList.add(couponMsg);
        }
        if (msgList.isEmpty()) {
            return null;
        }
        return msgList;
    }

    @Override
    public List<CheckMessageDto> execute(ReceiveOrderDto receiveOrderDto,
                                         boolean commissionCalcFlag,
                                         boolean carriageCalcFlag,
                                         HTypeSiteType siteType,
                                         String adminId) {
        // 引数チェック
        argumentCheck(receiveOrderDto);

        // 一回目の計算を呼び出す
        List<CheckMessageDto> msgList = execute(receiveOrderDto, carriageCalcFlag, siteType, adminId);

        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();
        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();

        CheckMessageDto commissionMsg = null;

        logForDebug(receiveOrderDto, 6);
        // ⑧受注金額算出
        orderPrice(receiveOrderDto, orderSettlementEntity, orderSummaryEntity);
        logForDebug(receiveOrderDto, 7);

        if (orderSettlementEntity.getOrderPrice().compareTo(BigDecimal.ZERO) != 0) {
            // 受注金額がゼロではない場合、決済手数料考慮して再計算を行う
            // ①,②,③の金額は変わらないため、再計算しない
            // ④決済手数料算出
            commissionMsg = settlementCommission(receiveOrderDto, commissionCalcFlag, siteType, adminId);
            logForDebug(receiveOrderDto, 3);
            // PDR Migrate Customization from here
            // ⑤消費税算出 削除
            // 別の箇所で行うため
            // PDR Migrate Customization to here
            logForDebug(receiveOrderDto, 4);
            // 割引前受注金額を計算
            calcBeforeDiscountOrderPrice(receiveOrderDto);
            // ⑥,⑦の金額は変わらないため、再計算しない
            // ⑧受注金額算出
            orderPrice(receiveOrderDto, orderSettlementEntity, orderSummaryEntity);
            logForDebug(receiveOrderDto, 7);
        } else {
            // 受注修正で決済方法が変更された場合は変更後の決済方法を取得する
            SettlementMethodEntity selectSettlementMethodEntity = receiveOrderDto.getSettlementMethodEntity();

            // 受注修正時、settlementCommissionを通らないため、orderSettlementEntity.settlementMethodTypeが更新されない
            // settlementCommission内の決済方法更新処理のみを実行(periodicFlag=falseのため、定期注文の考慮は不要)

            // バック新規注文のとき、settlementMethodEntity=nullのため、商品選択時にエラー
            // 受注修正時のみ実行するため条件文を追加
            if (selectSettlementMethodEntity != null) {
                orderSettlementEntity.setBillType(selectSettlementMethodEntity.getBillType());
                orderSettlementEntity.setSettlementMethodType(selectSettlementMethodEntity.getSettlementMethodType());
                orderSettlementEntity.setSettlementMethodSeq(selectSettlementMethodEntity.getSettlementMethodSeq());
            }
        }

        // PDR Migrate Customization from here
        // 定期受注の次回決済情報作成を削除
        // PDR Migrate Customization to here

        // 戻り値作成
        if (msgList == null) {
            msgList = new ArrayList<>();
        }
        if (!receiveOrderDto.isApplyCouponFlg() && commissionMsg != null) {
            msgList.add(commissionMsg);
        }
        if (msgList.isEmpty()) {
            return null;
        }
        return msgList;
    }

    /**
     * 受注サマリエンティティを初期化する<br/>
     * <pre>
     * 受注DTOから受注サマリエンティティを取得し初期化する。
     * 受注サマリエンティティが存在しない場合はnullとする。
     * 初期化した受注サマリエンティティを返す。
     * </pre>
     *
     * @param dto          受注DTO
     * @param customParams 案件用引数
     * @return 受注サマリエンティティ
     */
    protected OrderSummaryEntity initOrderSummaryEntity(ReceiveOrderDto dto, Object... customParams) {
        // 受注サマリエンティティ取得
        OrderSummaryEntity entity = dto.getOrderSummaryEntity();
        if (entity == null) {
            return null;
        }
        // PDR Migrate Customization from here
        // 受注サマリエンティティ初期化 削除
        // PDR Migrate Customization to here

        return entity;
    }

    /**
     * 受注決済エンティティを初期化する<br/>
     * <pre>
     * 受注DTOから受注決済エンティティを取得し初期化する。
     * 受注決済エンティティが存在しない場合は新たに生成し初期化する。
     * 初期化した受注決済エンティティを返す。
     * </pre>
     *
     * @param orderSettlementEntity 初期化する受注決済エンティティ
     * @param customParams          案件用引数
     * @return 受注決済エンティティ
     */
    protected OrderSettlementEntity initOrderSettlementEntity(OrderSettlementEntity orderSettlementEntity,
                                                              Object... customParams) {
        // 初期化するエンティティがnullの場合、新規作成する
        if (orderSettlementEntity == null) {
            orderSettlementEntity = getComponent(OrderSettlementEntity.class);
        }
        // PDR Migrate Customization from here
        // 受注決済エンティティ初期化 削除
        // PDR Migrate Customization to here
        return orderSettlementEntity;
    }

    /**
     * 引数チェック<br/>
     *
     * @param receiveOrderDto 受注詳細DTO
     */
    protected void argumentCheck(ReceiveOrderDto receiveOrderDto) {
        ArgumentCheckUtil.assertNotNull("receiveOrderDto", receiveOrderDto);
        ArgumentCheckUtil.assertNotNull("orderPersonEntity", receiveOrderDto.getOrderPersonEntity());
    }

    /**
     * 受注商品金額算出<br/>
     *
     * @param receiveOrderDto       受注Dto
     * @param orderSummaryEntity    受注サマリー
     * @param orderDeliveryDto      受注配送Dto
     * @param orderSettlementEntity 受注決済
     * @return 無料配送フラグリスト
     */
    protected List<HTypeFreeDeliveryFlag> orderGoodsPriceTotal(ReceiveOrderDto receiveOrderDto,
                                                               OrderSummaryEntity orderSummaryEntity,
                                                               OrderDeliveryDto orderDeliveryDto,
                                                               OrderSettlementEntity orderSettlementEntity) {

        // PDR Migrate Customization from here
        // 受注商品金額は別の箇所で算出済なので削除。無料配送フラグ判定だけ残しておく
        // PDR Migrate Customization to here

        List<HTypeFreeDeliveryFlag> freeDeliveryFlagList = new ArrayList<>();

        if (orderDeliveryDto == null || CollectionUtil.isEmpty(orderDeliveryDto.getOrderGoodsEntityList())) {
            return freeDeliveryFlagList;
        }

        HTypeFreeDeliveryFlag freeDeliveryFlag = HTypeFreeDeliveryFlag.OFF;
        BigDecimal countTotal = BigDecimal.ZERO;

        for (OrderGoodsEntity orderGoodsEntity : orderDeliveryDto.getOrderGoodsEntityList()) {
            BigDecimal goodsCount = orderGoodsEntity.getGoodsCount();
            countTotal = countTotal.add(goodsCount);

            // 送料無料の商品の場合、無料配送フラグをセット
            if (orderGoodsEntity.getFreeDeliveryFlag() == HTypeFreeDeliveryFlag.ON) {
                // 商品数量が0個の場合は判定から除外
                if (goodsCount.compareTo(BigDecimal.ZERO) > 0) {
                    freeDeliveryFlag = HTypeFreeDeliveryFlag.ON;
                }
            }
        }

        if (BigDecimal.ZERO.equals(countTotal)) {
            if (orderDeliveryDto.getOrderDeliveryEntity() != null
                && HTypeShipmentStatus.UNSHIPMENT == orderDeliveryDto.getOrderDeliveryEntity().getShipmentStatus()) {
                // 出荷状態 = 未出荷 且つ 商品合計点数 = 0の場合、送料=0円にする為に設定
                freeDeliveryFlag = HTypeFreeDeliveryFlag.ON;
            } else if (BigDecimal.ZERO.equals(orderSettlementEntity.getCarriage())) {
                // 出荷状態 = 出荷 且つ 前回の送料=0円の場合、整合性を取る為に設定
                freeDeliveryFlag = HTypeFreeDeliveryFlag.ON;
            }
        }
        freeDeliveryFlagList.add(freeDeliveryFlag);

        return freeDeliveryFlagList;
    }

    /**
     * 配送Dtoリストを取得<br/>
     *
     * @param receiveOrderDto       受注Dto
     * @param orderDeliveryDto      受注配送Dto
     * @param orderSummaryEntity    受注サマリ
     * @param orderSettlementEntity 受注決済
     * @param orderDeliveryEntity   受注配送
     * @param freeDeliveryFlagList  無料配送フラグリスト
     * @param deliveryCount         配送連番
     * @param isFirstOrder          初回受注
     * @return 配送Dtoリスト
     */
    protected List<DeliveryDto> getDeliveryDtoList(ReceiveOrderDto receiveOrderDto,
                                                   OrderDeliveryDto orderDeliveryDto,
                                                   OrderSummaryEntity orderSummaryEntity,
                                                   OrderSettlementEntity orderSettlementEntity,
                                                   OrderDeliveryEntity orderDeliveryEntity,
                                                   List<HTypeFreeDeliveryFlag> freeDeliveryFlagList,
                                                   int deliveryCount,
                                                   boolean isFirstOrder,
                                                   Boolean isSiteBack) {

        HTypePrefectureType prefectureType = EnumTypeUtil.getEnumFromLabel(HTypePrefectureType.class,
                                                                           orderDeliveryEntity.getReceiverPrefecture()
                                                                          );
        DeliverySearchForDaoConditionDto conditionDto = getComponent(DeliverySearchForDaoConditionDto.class);
        conditionDto.setShopSeq(orderSummaryEntity.getShopSeq());
        conditionDto.setPrefectureType(prefectureType);
        conditionDto.setTotalGoodsPrice(orderUtility.getGoodsPriceTotal(orderDeliveryDto.getOrderGoodsEntityList()));
        conditionDto.setZipcode(orderDeliveryEntity.getReceiverZipCode());
        List<Integer> list = new ArrayList<>();
        list.add(orderDeliveryEntity.getDeliveryMethodSeq());

        // AmazonPaymentかつバックの時は、配送不可エリアのチェックを無効化
        if ((HTypeSettlementMethodType.AMAZON_PAYMENT == orderSettlementEntity.getSettlementMethodType())
            && isSiteBack) {
            conditionDto.setIgnoreImpossibleAreaFlag(true);
        }

        return deliveryMethodSelectListGetLogic.execute(conditionDto, list, freeDeliveryFlagList.get(deliveryCount));
    }

    /**
     * その他合計金額算出
     *
     * @param orderAdditionalChargeEntityList 受注追加料金リスト
     * @param orderSettlementEntity           受注決済
     */
    protected void othersPriceTotal(List<OrderAdditionalChargeEntity> orderAdditionalChargeEntityList,
                                    OrderSettlementEntity orderSettlementEntity) {
        if (orderAdditionalChargeEntityList == null) {
            return;
        }

        BigDecimal othersPriceTotal = BigDecimal.ZERO;

        for (OrderAdditionalChargeEntity orderAdditionalChargeEntity : orderAdditionalChargeEntityList) {
            othersPriceTotal = othersPriceTotal.add(orderAdditionalChargeEntity.getAdditionalDetailsPrice());
        }

        orderSettlementEntity.setOthersPriceTotal(othersPriceTotal);
        // その他金額合計は標準税率対象になる
        orderSettlementEntity.setStandardTaxTargetPrice(
                        orderSettlementEntity.getStandardTaxTargetPrice().add(othersPriceTotal));
    }

    /**
     * 決済手数料算出
     *
     * @param receiveOrderDto    受注DTO
     * @param commissionCalcFlag 決済手数料適用フラグ。true..計算前の手数料を適用する
     * @return チェックメッセージDTO（エラー時のみ）
     */
    protected CheckMessageDto settlementCommission(ReceiveOrderDto receiveOrderDto,
                                                   boolean commissionCalcFlag,
                                                   HTypeSiteType siteType,
                                                   String adminId) {
        return settlementCommission(receiveOrderDto, commissionCalcFlag, false, siteType, adminId);
    }

    /**
     * 決済手数料算出
     *
     * @param receiveOrderDto    受注DTO
     * @param commissionCalcFlag 決済手数料適用フラグ。true..計算前の手数料を適用する
     * @param periodicFlag       定期注文用次回決済手数料の算出
     * @return チェックメッセージDTO（エラー時のみ）
     */
    protected CheckMessageDto settlementCommission(ReceiveOrderDto receiveOrderDto,
                                                   boolean commissionCalcFlag,
                                                   boolean periodicFlag,
                                                   HTypeSiteType siteType,
                                                   String adminId) {
        OrderSettlementEntity orderSettlementEntity;
        SettlementMethodEntity settlementMethodEntity;
        if (periodicFlag) {
            orderSettlementEntity = receiveOrderDto.getOrderNextSettlementEntity();
            settlementMethodEntity = receiveOrderDto.getNextSettlementMethodEntity();
        } else {
            orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();
            settlementMethodEntity = receiveOrderDto.getSettlementMethodEntity();
        }

        orderSettlementEntity.setSettlementCommission(BigDecimal.ZERO);

        if (receiveOrderDto.getOrderDeliveryDto() == null) {
            return null;
        }

        // 選択可能決済方法リスト取得のため
        BigDecimal goodsPriceTotal = orderSettlementEntity.getGoodsPriceTotal();
        BigDecimal carriage = orderSettlementEntity.getCarriage();
        // 選択された決済方法のを取得するため
        Integer settlementMethodSeq = orderSettlementEntity.getSettlementMethodSeq();

        // 選択された配送方法SEQリストを取得
        // 選択可能決済方法リスト取得に使用するため
        List<Integer> deliveryMethodSeqList = orderUtility.createSelectDeliveryMethodSeqList(receiveOrderDto);
        if (goodsPriceTotal == null || CollectionUtil.isEmpty(deliveryMethodSeqList) || settlementMethodSeq == null) {
            return null;
        }

        // 選択可能決済方法リスト取得検索条件を設定
        SettlementSearchForDaoConditionDto conditionDto =
                        calculatePriceUtility.getSettlementSearchForDaoConditionDto(receiveOrderDto, siteType,
                                                                                    orderSettlementEntity
                                                                                   );
        BigDecimal commissionLessOrderPrice =
                        calculatePriceUtility.getCommissionLessTaxIncludedOrderPrice(orderSettlementEntity);

        List<Integer> settlementMethodSeqList = new ArrayList<>();
        settlementMethodSeqList.add(settlementMethodSeq);

        // 選択可能決済方法リスト取得
        List<SettlementDto> settlementDtoList =
                        settlementMethodListGetLogic.execute(conditionDto, settlementMethodSeqList, goodsPriceTotal,
                                                             commissionLessOrderPrice, deliveryMethodSeqList, carriage
                                                            );
        if (CollectionUtil.isEmpty(settlementDtoList)) {
            return getMessage(MSGCD_GETSETTOLEMENT_NULL, null);
        }

        // 決済手数料を設定
        boolean isSettlementError = true;
        for (SettlementDto settlementDto : settlementDtoList) {
            if (settlementDto.isSelectClass()) {
                if (BigDecimal.ZERO.equals(commissionLessOrderPrice)) {
                    // 決済に対する手数料である為、決済する料金がなければ手数料=0とする
                    orderSettlementEntity.setSettlementCommission(BigDecimal.ZERO);
                } else {
                    orderSettlementEntity.setSettlementCommission(settlementDto.getCharge());
                }
                orderSettlementEntity.setBillType(settlementMethodEntity.getBillType());
                orderSettlementEntity.setSettlementMethodType(settlementMethodEntity.getSettlementMethodType());
                orderSettlementEntity.setSettlementMethodSeq(
                                settlementDto.getSettlementDetailsDto().getSettlementMethodSeq());
                isSettlementError = false;

                BigDecimal beforeCommission = receiveOrderDto.getOriginalCommission();
                BigDecimal afterCommission = orderSettlementEntity.getSettlementCommission();
                if (commissionCalcFlag) {
                    // 手数料計算フラグ=ONの場合、計算前の手数料を適用する
                    orderSettlementEntity.setSettlementCommission(beforeCommission);
                }
                // 決済手数料は標準税率計算の対象
                orderSettlementEntity.setStandardTaxTargetPrice(orderSettlementEntity.getStandardTaxTargetPrice()
                                                                                     .add(orderSettlementEntity.getSettlementCommission()));

                // 決済手数料計算結果をログ出力
                logCalcMessage(receiveOrderDto, beforeCommission, afterCommission, commissionCalcFlag, adminId);
            }
        }
        if (isSettlementError) {
            // PDR Migrate Customization from here
            // 上限金額チェックを商品合計金額でしてしまうと、プロモーション値引きによる減額の場合でもエラーとなってしまう為、コメントアウト
            // return getMessage(MSGCD_GETSETTOLEMENT_ERROR, null);
            // PDR Migrate Customization to here
        }

        return null;
    }

    /**
     * 割引前受注金額を算出する
     *
     * @param receiveOrderDto 受注Dto
     */
    protected void calcBeforeDiscountOrderPrice(ReceiveOrderDto receiveOrderDto) {
        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();
        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();

        // 計算
        orderSettlementEntity.setBeforeDiscountOrderPrice(
                        calculatePriceUtility.getTaxIncludedBeforeDiscountOrderPrice(receiveOrderDto));
        orderSummaryEntity.setBeforeDiscountOrderPrice(orderSettlementEntity.getBeforeDiscountOrderPrice());
    }

    /**
     * 受注金額算出
     *
     * @param receiveOrderDto       受注DTO
     * @param orderSettlementEntity 受注決済
     * @param orderSummaryEntity    受注サマリー
     */
    protected void orderPrice(ReceiveOrderDto receiveOrderDto,
                              OrderSettlementEntity orderSettlementEntity,
                              OrderSummaryEntity orderSummaryEntity) {

        OrderBillEntity orderBillEntity = receiveOrderDto.getOrderBillEntity();
        OrderPersonEntity orderPersonEntity = receiveOrderDto.getOrderPersonEntity();
        SettlementMethodEntity settlementMethodEntity = receiveOrderDto.getSettlementMethodEntity();

        // 受注金額 = 商品金額合計 + 送料 + 追加料金合計 + 決済手数料 + 消費税 - ポイント利用額 - クーポン割引額
        BigDecimal orderPrice = getOrderPrice(receiveOrderDto, orderSettlementEntity);

        // 受注サマリに未設定項目を設定
        orderSettlementEntity.setOrderPrice(orderPrice);
        orderSummaryEntity.setOrderPrice(orderPrice);
        orderSummaryEntity.setSettlementMethodSeq(orderSettlementEntity.getSettlementMethodSeq());
        orderSummaryEntity.setPrefectureType(orderPersonEntity.getPrefectureType());
        orderSummaryEntity.setOrderAgeType(orderPersonEntity.getOrderAgeType());
        orderSummaryEntity.setOrderSex(orderPersonEntity.getOrderSex());
        setOrderStatus(receiveOrderDto);

        HTypeShipmentStatus shipmentStatus =
                        orderUtility.getShipmentStatusByOrderStatus(orderSummaryEntity.getOrderStatus());

        // 受注請求情報を作成
        orderBillEntity = getOrderBillEntity(orderBillEntity, shipmentStatus, orderSettlementEntity);
        orderBillEntity.setBillPrice(orderPrice);

        // クレジット決済の場合、定期注文の請求エラー時以外は「支払期限猶予日数」、「期限後ｷｬﾝｾﾙ可能日数」を 0 で初期化
        if (settlementMethodEntity != null
            && HTypeSettlementMethodType.CREDIT == settlementMethodEntity.getSettlementMethodType()) {
            if (!(HTypeOrderType.PERIODIC == orderSummaryEntity.getOrderType()
                  && HTypeEmergencyFlag.ON == orderBillEntity.getEmergencyFlag())) {
                settlementMethodEntity.setPaymentTimeLimitDayCount(0);
                settlementMethodEntity.setCancelTimeLimitDayCount(0);
            }
        }

        // 支払期限算出・ キャンセル可能日時算出
        Calendar cal = Calendar.getInstance();
        if (settlementMethodEntity != null && settlementMethodEntity.getPaymentTimeLimitDayCount() != null
            && settlementMethodEntity.getPaymentTimeLimitDayCount() != 0) {
            cal.setTime(dateUtility.getCurrentTime());
            cal.add(Calendar.DAY_OF_MONTH, settlementMethodEntity.getPaymentTimeLimitDayCount());
            orderBillEntity.setPaymentTimeLimitDate(new Timestamp(cal.getTimeInMillis()));

            if (settlementMethodEntity.getCancelTimeLimitDayCount() != null) {
                cal.add(Calendar.DAY_OF_MONTH, settlementMethodEntity.getCancelTimeLimitDayCount());
                orderBillEntity.setCancelableDate(new Timestamp(cal.getTimeInMillis()));
            } else {
                orderBillEntity.setCancelableDate(null);
            }

        } else {
            orderBillEntity.setPaymentTimeLimitDate(null);
            orderBillEntity.setCancelableDate(null);
        }
        receiveOrderDto.setOrderBillEntity(orderBillEntity);
    }

    /**
     * 受注請求作成
     *
     * @param orderBillEntity       受注請求
     * @param shipmentStatus        出荷状態
     * @param orderSettlementEntity 受注決済
     * @return 新しい受注請求
     */
    protected OrderBillEntity getOrderBillEntity(OrderBillEntity orderBillEntity,
                                                 HTypeShipmentStatus shipmentStatus,
                                                 OrderSettlementEntity orderSettlementEntity) {

        if (orderBillEntity == null) {
            orderBillEntity = getComponent(OrderBillEntity.class);
            orderBillEntity.setShopSeq(orderSettlementEntity.getShopSeq());
            orderBillEntity.setBillStatus(HTypeBillStatus.BILL_NO_CLAIM);
            orderBillEntity.setEmergencyFlag(HTypeEmergencyFlag.OFF);
        }

        // 前請求又は出荷済みの場合
        if (HTypeBillType.PRE_CLAIM == orderSettlementEntity.getBillType()
            || HTypeShipmentStatus.SHIPPED == shipmentStatus) {
            // 請求済み
            orderBillEntity.setBillStatus(HTypeBillStatus.BILL_CLAIM);
        } else {
            // 未請求
            orderBillEntity.setBillStatus(HTypeBillStatus.BILL_NO_CLAIM);
        }
        orderBillEntity.setPaymentTimeLimitDate(null);
        orderBillEntity.setSettlementCommission(orderSettlementEntity.getSettlementCommission());
        orderBillEntity.setSettlementMethodSeq(orderSettlementEntity.getSettlementMethodSeq());

        return orderBillEntity;
    }

    /**
     * メッセージ取得
     *
     * @param msgCode メッセージコード1
     * @param args    メッセージ引数
     * @return チェックメッセージDTO
     */
    protected CheckMessageDto getMessage(String msgCode, Object[] args) {
        return checkMessageUtility.createCheckMessageDto(msgCode, args);
    }

    /**
     * 受注状態を設定
     *
     * @param receiveOrderDto 受注Dto
     */
    protected void setOrderStatus(ReceiveOrderDto receiveOrderDto) {
        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();
        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();
        BigDecimal receiptPriceTotal = orderSummaryEntity.getReceiptPriceTotal();
        BigDecimal orderPrice = orderSummaryEntity.getOrderPrice();
        HTypeBillType billtype = orderSettlementEntity.getBillType();
        HTypeSettlementMethodType settlementMethodType = orderSettlementEntity.getSettlementMethodType();
        HTypeOrderStatus orderStatus = orderSummaryEntity.getOrderStatus();

        if (HTypeOrderStatus.SHIPMENT_COMPLETION != orderStatus && HTypeOrderStatus.PART_SHIPMENT != orderStatus) {
            if (HTypeBillType.PRE_CLAIM == billtype && HTypeSettlementMethodType.CREDIT != settlementMethodType
                && !orderPrice.equals(receiptPriceTotal)) {
                orderSummaryEntity.setOrderStatus(HTypeOrderStatus.PAYMENT_CONFIRMING);
            } else {
                orderSummaryEntity.setOrderStatus(HTypeOrderStatus.GOODS_PREPARING);
            }
        }
    }

    /**
     * 注文に対してクーポンを適用する。<br />
     * <pre>
     * ・クーポン割引前金額を算出し、注文にセットする。
     * ・クーポン利用可否の判定を行う。
     * ・クーポンが利用可能の場合、以下を実行。
     *  - クーポン情報を設定。
     *  - クーポン割引額を算出し、注文にセットする。
     * </pre>
     *
     * @param order 受注DTO
     * @return クーポン取得エラーメッセージのリスト。
     * 受注DTOに受注決済エンティティDTOが存在しない場合、null。
     */
    protected CheckMessageDto couponDiscountPrice(ReceiveOrderDto order) {
        // PDR Migrate Customization from here
        // HM既存のクーポン機能は利用しない
        return null;
        // PDR Migrate Customization to here
    }

    /**
     * クーポン割引を考慮して受注金額を算出する。<br/>
     * 「受注金額」はフロントでは「お支払金額合計」、実際に顧客が支払う金額<br/>
     *
     * @param receiveOrderDto       受注Dto
     * @param orderSettlementEntity 受注決済Dto
     * @return 受注金額
     */
    protected BigDecimal getOrderPrice(ReceiveOrderDto receiveOrderDto, OrderSettlementEntity orderSettlementEntity) {

        // PDR Migrate Customization from here
        // 受注金額は計算済みのためクーポン割引の再計算は行わない
        BigDecimal orderPrice = receiveOrderDto.getOrderSummaryEntity().getOrderPrice();
        // PDR Migrate Customization to here
        BigDecimal pointDiscountPrice = orderSettlementEntity.getPointDiscountPrice();
        return orderPrice.subtract(pointDiscountPrice);
    }

    /**
     * 決済手数料計算結果をログ出力<br/>
     *
     * @param receiveOrderDto    受注DTO
     * @param beforeCommission   計算前決済手数料
     * @param afterCommission    計算後決済手数料
     * @param commissionCalcFlag 決済手数料適用フラグ。true..計算前の手数料を適用する
     */
    protected void logCalcMessage(ReceiveOrderDto receiveOrderDto,
                                  BigDecimal beforeCommission,
                                  BigDecimal afterCommission,
                                  boolean commissionCalcFlag,
                                  String adminId) {

        // 管理者情報(共通情報)、受注番号があるかチェック
        // ※受注修正時のみ出力する想定なので、フロントor新規受注の判定としてこの条件にしている
        if (adminId == null || receiveOrderDto.getOrderSummaryEntity().getOrderCode() == null) {
            // なければログ出力しない
            return;
        }

        // ログに出力する文言を取得
        String orderCd = receiveOrderDto.getOrderSummaryEntity().getOrderCode();

        // ログ出力処理
        LOGGER.info("決済手数料の再計算が行われました。");
        LOGGER.info(String.format("運用者ID[%s]：受注番号[%s]", adminId, orderCd));
        LOGGER.info(String.format("計算前決済手数料[%s]", beforeCommission));
        LOGGER.info(String.format("計算後決済手数料[%s]", afterCommission));
        if (commissionCalcFlag) {
            LOGGER.info(String.format("手数料適用フラグ=ONのため、計算前決済手数料[%s]が適用されます。", beforeCommission));
        } else {
            LOGGER.info(String.format("手数料適用フラグ=OFFのため、計算後決済手数料[%s]が適用されます。", afterCommission));
        }

    }

    /**
     * 計算ステップずつ受注情報をデバッグする
     *
     * @param orderDto ReceiveOrderDto
     * @param step     ステップ番号
     */
    protected void logForDebug(ReceiveOrderDto orderDto, int step) {
        // デバッグモードではない時何もしない
        if (!LOGGER.isDebugEnabled()) {
            return;
        }

        String[] stepMessages =
                        {"①商品金額算出", "②送料算出", "③その他金額合計算出", "④決済手数料算出", "⑤消費税算出", "⑥クーポン算出", "⑦ポイント算出", "⑧受注金額算出",
                                        "⑨取得ポイント算出"};

        OrderSettlementEntity orderSettlement = orderDto.getOrderSettlementEntity();

        String log = stepMessages[step] + "------------------";
        log += "\n・８％対象金額：" + orderSettlement.getReducedTaxTargetPrice();
        log += "\n・10％対象金額：" + orderSettlement.getStandardTaxTargetPrice();
        log += "\n・商品金額：" + orderSettlement.getGoodsPriceTotal();
        log += "\n・送料：" + orderSettlement.getCarriage();
        log += "\n・その他金額合計：" + orderSettlement.getOthersPriceTotal();
        log += "\n・決済手数料：" + orderSettlement.getSettlementCommission();
        log += "\n・消費税：" + orderSettlement.getReducedTaxPrice() + ", " + orderSettlement.getStandardTaxPrice() + ", "
               + orderSettlement.getTaxPrice();
        log += "\n・クーポン、ポイント割引額：" + orderSettlement.getCouponDiscountPrice() + ", "
               + orderSettlement.getPointDiscountPrice();
        log += "\n・受注金額：" + orderSettlement.getOrderPrice();
        LOGGER.debug(log);

    }

}
