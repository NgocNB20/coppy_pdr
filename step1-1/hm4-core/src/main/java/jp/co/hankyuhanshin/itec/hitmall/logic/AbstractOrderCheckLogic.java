/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliverySpecialChargeAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderForCheckDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSummarySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.ReceiverDateDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodTypeCarriageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliverySpecialChargeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsListGetBySeqLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsMapGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.BillPriceCalculateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsListGetLogic;
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
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jp.co.hankyuhanshin.itec.hitmall.logic.order.BillPriceCalculateLogic.MSGCD_GETDELIVERY_ERROR;
import static jp.co.hankyuhanshin.itec.hitmall.logic.order.BillPriceCalculateLogic.MSGCD_GETDELIVERY_NULL;
import static jp.co.hankyuhanshin.itec.hitmall.logic.order.BillPriceCalculateLogic.MSGCD_GETSETTOLEMENT_ERROR;
import static jp.co.hankyuhanshin.itec.hitmall.logic.order.BillPriceCalculateLogic.MSGCD_GETSETTOLEMENT_NULL;

/**
 * 対照の受注情報が購入可能な情報であるかをチェックする抽象クラス<br/>
 *
 * @author ozaki
 */
@Component
@Data
public abstract class AbstractOrderCheckLogic extends AbstractShopLogic implements OrderCheckable {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractOrderCheckLogic.class);

    /**
     * CheckMessageUtility
     */
    private final CheckMessageUtility checkMessageUtility;

    /**
     * 受注ユーティリティ
     */
    private final OrderUtility orderUtility;

    /**
     * 金額計算ユーティリティ
     */
    private final CalculatePriceUtility calculatePriceUtility;

    /**
     * クーポンユーティティティ
     */
    private final CouponUtility couponUtility;

    /**
     * 商品リスト取得Logic
     */
    private final OrderGoodsListGetLogic orderGoodsListGetLogic;

    /**
     * 商品詳細情報マップ取得
     */
    private final GoodsDetailsMapGetLogic goodsDetailsMapGetLogic;

    /**
     * 請求金額算出処理
     */
    private final BillPriceCalculateLogic billPriceCalculateLogic;

    /**
     * 配送不可能エリア取得
     */
    private final DeliveryImpossibleAreaGetLogic deliveryImpossibleAreaGetLogic;

    /**
     * 特別配送料金エリアDao
     */
    private final DeliverySpecialChargeAreaDao deliverySpecialChargeAreaDao;

    /**
     * 配送区分送料リスト取得ロジック
     */
    private final DeliveryMethodTypeCarriageListGetLogic deliveryMethodTypeCarriageListGetLogic;

    /**
     * 配送方法エンティティ取得ロジック
     */
    private final DeliveryMethodGetLogic deliveryMethodGetLogic;

    /**
     * 配送方法別送料リスト取得ロジック
     */
    private final DeliveryMethodSelectListGetLogic deliveryMethodSelectListGetLogic;

    /**
     * 決済方法エンティティ取得ロジック
     */
    private final SettlementMethodGetLogic settlementMethodGetLogic;

    /**
     * 決済方法別手数料リスト取得ロジック
     */
    private final SettlementMethodListGetLogic settlementMethodListGetLogic;

    /**
     * お届け希望日取得ロジック
     */
    private final ReceiverDateGetLogic receiverDateGetLogic;

    /**
     * クーポンチェックLogic
     */
    private final CouponCheckLogic couponCheckLogic;

    /**
     * 同時注文排他情報取得ロジック
     */
    private final SimultaneousOrderExclusionGetLogic simultaneousOrderExclusionGetLogic;

    /**
     * GoodsDetailsListGetBySeqLogic object
     */
    private final GoodsDetailsListGetBySeqLogic goodsDetailsListGetBySeqLogic;

    @Autowired
    public AbstractOrderCheckLogic(CheckMessageUtility checkMessageUtility,
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
                                   GoodsDetailsListGetBySeqLogic goodsDetailsListGetBySeqLogic) {

        this.checkMessageUtility = checkMessageUtility;
        this.orderUtility = orderUtility;
        this.calculatePriceUtility = calculatePriceUtility;
        this.couponUtility = couponUtility;
        this.orderGoodsListGetLogic = orderGoodsListGetLogic;
        this.goodsDetailsMapGetLogic = goodsDetailsMapGetLogic;
        this.billPriceCalculateLogic = billPriceCalculateLogic;
        this.deliveryImpossibleAreaGetLogic = deliveryImpossibleAreaGetLogic;
        this.deliverySpecialChargeAreaDao = deliverySpecialChargeAreaDao;
        this.deliveryMethodTypeCarriageListGetLogic = deliveryMethodTypeCarriageListGetLogic;
        this.deliveryMethodGetLogic = deliveryMethodGetLogic;
        this.deliveryMethodSelectListGetLogic = deliveryMethodSelectListGetLogic;
        this.settlementMethodGetLogic = settlementMethodGetLogic;
        this.settlementMethodListGetLogic = settlementMethodListGetLogic;
        this.receiverDateGetLogic = receiverDateGetLogic;
        this.couponCheckLogic = couponCheckLogic;
        this.simultaneousOrderExclusionGetLogic = simultaneousOrderExclusionGetLogic;
        this.goodsDetailsListGetBySeqLogic = goodsDetailsListGetBySeqLogic;
    }

    /**
     * 初期処理<br/>
     * 受注情報が購入可能かチェックします。<br/>
     *
     * @param receiveOrderDto 受注DTO
     * @return 注文メッセージDTO
     */
    @Override
    public OrderMessageDto execute(ReceiveOrderDto receiveOrderDto,
                                   String memberInfoId,
                                   HTypeSiteType siteType,
                                   Integer memberInfoSeq,
                                   String adminId) {
        return execute(receiveOrderDto, true, memberInfoId, siteType, memberInfoSeq, adminId);
    }

    /**
     * 初期処理<br/>
     * 受注情報が購入可能かチェックします。<br/>
     *
     * @param receiveOrderDto 受注DTO
     * @param calculateFlag   受注金額算出フラグ。true..計算する / false..計算しない
     * @return 注文メッセージDTO
     */
    public OrderMessageDto execute(ReceiveOrderDto receiveOrderDto,
                                   boolean calculateFlag,
                                   String memberInfoId,
                                   HTypeSiteType siteType,
                                   Integer memberInfoSeq,
                                   String adminId) {

        // 注文メッセージDTOの初期化
        OrderMessageDto orderMessageDto = ApplicationContextUtility.getBean(OrderMessageDto.class);
        orderMessageDto.setOrderGoodsMessageMapMap(new HashMap<Integer, Map<Integer, List<CheckMessageDto>>>());
        orderMessageDto.setOrderMessageList(new ArrayList<CheckMessageDto>());

        // 初期処理
        beforeProcess(receiveOrderDto, orderMessageDto, calculateFlag, memberInfoId, siteType, memberInfoSeq, adminId);

        // カートが空の場合
        List<OrderGoodsEntity> orderGoodsEntityList = orderUtility.getALLGoodsEntityList(receiveOrderDto);
        if (CollectionUtil.isEmpty(orderGoodsEntityList)) {
            return orderMessageDto;
        }

        // 受注単位のチェック
        checkPerOrder(receiveOrderDto, orderMessageDto, siteType);

        // 決済方法エンティティDTOが受注DTOに存在しない場合は、処理を行わない
        if (receiveOrderDto.getSettlementMethodEntity() != null) {
            // 後続の処理を行うかの判定用に、エラー数を保持
            List<CheckMessageDto> messageList = orderMessageDto.getOrderMessageList();
            int errorCount = messageList.size();

            // 全額割引決済方法と受注金額の整合性チェックを行う
            checkCanUseDiscountSettlement(receiveOrderDto, messageList);
            if (errorCount != messageList.size()) {
                // 決済方法の選択エラーが発生しているので以降の処理は不要
                return orderMessageDto;
            }
        }

        // クーポンチェック
        try {
            couponCheckLogic.checkUsableCoupon(receiveOrderDto.getCoupon(), receiveOrderDto, siteType, memberInfoSeq);
        } catch (AppLevelListException appe) {
            LOGGER.error("例外処理が発生しました", appe);
            // クーポンチェックでスローされたエラーを詰め替える
            List<AppLevelException> errList = appe.getErrorList();
            for (AppLevelException err : errList) {
                orderMessageDto.getOrderMessageList()
                               .add((toCheckMessageDto(err.getMessageCode(), err.getArgs(), true)));
            }
        }

        // 決済可能金額チェック
        if (receiveOrderDto.getSettlementMethodEntity() != null) {
            maxPurchasedPriceOverCheck(receiveOrderDto.getSettlementMethodEntity(),
                                       receiveOrderDto.getOrderSettlementEntity().getOrderPrice(),
                                       orderMessageDto.getOrderMessageList(), siteType
                                      );
        }
        // 定期商品決済可能金額チェック
        if (receiveOrderDto.isPeriodicOrderFlag()) {
            if (receiveOrderDto.getNextSettlementMethodEntity() != null) {
                maxPurchasedPriceOverCheck(receiveOrderDto.getNextSettlementMethodEntity(),
                                           receiveOrderDto.getOrderNextSettlementEntity().getOrderPrice(),
                                           orderMessageDto.getOrderMessageList(), siteType
                                          );
            }
        }
        // 同時注文の排他制御の為、同時注文排他情報を取得する
        if (orderUtility.isMember(memberInfoId, siteType, receiveOrderDto)) {
            receiveOrderDto.setSimultaneousOrderExclusionEntity(simultaneousOrderExclusionGetLogic.execute(
                            receiveOrderDto.getOrderSummaryEntity().getMemberInfoSeq()));
        }
        return orderMessageDto;
    }

    /**
     * 受注単位のチェックを行う<br/>
     *
     * @param receiveOrderDto 受注Dto
     * @param orderMessageDto メッセージDto
     */
    protected void checkPerOrder(ReceiveOrderDto receiveOrderDto,
                                 OrderMessageDto orderMessageDto,
                                 HTypeSiteType siteType) {

        // 商品チェック前の準備
        OrderForCheckDto checkDto = prepareForGoodsCheck(receiveOrderDto, false);

        // 商品詳細マップマップ取得
        Map<Integer, Map<Integer, GoodsDetailsDto>> goodsDetailsMapMap =
                        getGoodsDetailsMapMap(receiveOrderDto.getOrderDeliveryDto(), checkDto);

        // 配送チェック
        Integer orderConsecutiveNo =
                        receiveOrderDto.getOrderDeliveryDto().getOrderDeliveryEntity().getOrderConsecutiveNo();

        // 商品チェック
        checkPerDelivery(receiveOrderDto, checkDto, receiveOrderDto.getOrderDeliveryDto(),
                         goodsDetailsMapMap.get(orderConsecutiveNo), orderMessageDto
                        );

        // 未成年のアルコール商品購買不可チェック
        checkUnderageAlcohol(receiveOrderDto, receiveOrderDto.getOrderDeliveryDto(),
                             goodsDetailsMapMap.get(orderConsecutiveNo), orderMessageDto.getOrderMessageList()
                            );

        // 配送方法整合性チェック
        if (CollectionUtil.isNotEmpty(receiveOrderDto.getOrderDeliveryDto().getDeliveryDtoList())) {
            checkDeliveryMethodCombination(receiveOrderDto, checkDto.getDeliveryList(),
                                           orderMessageDto.getOrderMessageList(), siteType
                                          );
        }

        // 利用可能決済方法チェック
        possibleSettlementCheck(checkDto.getSettlementList(), orderMessageDto.getOrderMessageList());

        // 決済方法整合性チェック
        if (receiveOrderDto.getSettlementMethodEntity() != null) {
            checkSettlementMethod(receiveOrderDto, checkDto.getSettlementList(), orderMessageDto.getOrderMessageList(),
                                  siteType
                                 );
        }
        if (receiveOrderDto.isPeriodicOrderFlag() && receiveOrderDto.getNextSettlementMethodEntity() != null) {
            checkPeriodicSettlementMethod(receiveOrderDto, checkDto.getSettlementList(),
                                          orderMessageDto.getOrderMessageList(), siteType
                                         );
        }
    }

    /**
     * 配送チェックを行う<br/>
     *
     * @param receiveOrderDto   受注Dto
     * @param checkDto          チェック用Dto
     * @param deliveryDto       受注配送Dto
     * @param goodsDtailsDtoMap 商品詳細Dtoマップ(配送先に含まれている商品)
     * @param orderMessageDto   メッセージDto
     */
    protected void checkPerDelivery(ReceiveOrderDto receiveOrderDto,
                                    OrderForCheckDto checkDto,
                                    OrderDeliveryDto deliveryDto,
                                    Map<Integer, GoodsDetailsDto> goodsDtailsDtoMap,
                                    OrderMessageDto orderMessageDto) {
        // 注文連番
        Integer consecutiveNo = deliveryDto.getOrderDeliveryEntity().getOrderConsecutiveNo();

        Map<Integer, List<CheckMessageDto>> msgMap = new HashMap<>();

        if (CollectionUtil.isEmpty(deliveryDto.getOrderGoodsEntityList())) {
            return;
        }
        for (OrderGoodsEntity orderGoods : deliveryDto.getOrderGoodsEntityList()) {
            Integer goodsSeq = orderGoods.getGoodsSeq();
            GoodsDetailsDto goodsDetails = goodsDtailsDtoMap.get(goodsSeq);

            List<CheckMessageDto> msgList = new ArrayList<>();
            // 公開状態チェック
            openStatusCheck(goodsDetails, msgList);
            // 販売状態チェック
            saleStatusCheck(goodsDetails, msgList);
            // 商品数量取出し
            BigDecimal goodsCnt = checkDto.getGoodsCntMap().get(goodsSeq);
            // 在庫チェック
            salesPossibleStockOverCheck(goodsDetails, goodsCnt, msgList);
            // 購入制限数チェック
            purchasedMaxOverCheck(goodsDetails, orderGoods, goodsCnt, msgList);

            if (msgList != null && msgList.size() > 0) {
                msgMap.put(goodsSeq, msgList);
            }
        }

        // 個別配送チェック
        individualDeliveryCheck(goodsDtailsDtoMap, deliveryDto.getOrderGoodsEntityList(), msgMap);

        // MSGDtoにセット
        orderMessageDto.getOrderGoodsMessageMapMap().put(consecutiveNo, msgMap);
    }

    // PDR Migrate Customization from here

    /**
     * 複数配送のチェックを行う<br/>
     *
     * @param receiveOrderDto 受注Dto
     * @param msgList         メッセージリスト
     */
    protected void checkMultipleDelivery(ReceiveOrderDto receiveOrderDto, List<CheckMessageDto> msgList) {
        // 複数配送非対応なので、全てコメントアウト

        //// この注文が複数配送かどうかのフラグ
        //boolean isMultiDeliMode = receiveOrderDto.orderDeliveryDtoList.size() > 1;
        //
        //// 単一配送なので以下のチェックは不要
        //if (!isMultiDeliMode) {
        //    return;
        //}
        //
        //if (receiveOrderDto.isPeriodicOrderFlag()) {
        //    // 複数配送未対応で複数配送になっている
        //    msgList.add(toCheckMessageDto(MSGCD_MOBILE_MULTIPLE_DELIVERY_ERROR, null, true));
        //
        //} else if (!isMultiDeliSite(receiveOrderDto)) {
        //    // 複数配送未対応で複数配送になっている
        //    msgList.add(toCheckMessageDto(MSGCD_MOBILE_MULTIPLE_DELIVERY_ERROR, null, true));
        //}
    }
    // PDR Migrate Customization to here

    /**
     * 初期処理<br/>
     * 請求金額を計算します。<br/>
     *
     * @param receiveOrderDto 受注Dto
     * @param orderMessageDto 注文メッセージDTO
     * @param calculateFlag   受注金額算出フラグ
     */
    protected void beforeProcess(ReceiveOrderDto receiveOrderDto,
                                 OrderMessageDto orderMessageDto,
                                 boolean calculateFlag,
                                 String memberInfoId,
                                 HTypeSiteType siteType,
                                 Integer memberInfoSeq,
                                 String adminId) {
        beforeProcess(receiveOrderDto, orderMessageDto, calculateFlag, false, false, memberInfoId, siteType,
                      memberInfoSeq, adminId
                     );
        return;
    }

    /**
     * 初期処理<br/>
     * 請求金額を計算します。<br/>
     *
     * @param receiveOrderDto    受注Dto
     * @param orderMessageDto    注文メッセージDTO
     * @param calculateFlag      受注金額算出フラグ
     * @param commissionCalcFlag 手数料金額適用フラグ。true..計算前の手数料を適用する
     * @param carriageCalcFlag   送料金額適用フラグ。true..計算前の送料を適用する
     */
    protected void beforeProcess(ReceiveOrderDto receiveOrderDto,
                                 OrderMessageDto orderMessageDto,
                                 boolean calculateFlag,
                                 boolean commissionCalcFlag,
                                 boolean carriageCalcFlag,
                                 String memberInfoId,
                                 HTypeSiteType siteType,
                                 Integer memberInfoSeq,
                                 String adminId) {

        // 配送方法エンティティを最新化する
        // billPriceCalculateLogic#carriage で同様の処理を「条件付き」で実装しているが、
        // 注文チェック処理において、その条件で実行されることが無いため、当クラスにて実装を行う。
        // なお、配送方法エンティティを最新化した場合の billPriceCalculateLogic への影響はない。
        getNewDeliveryMethod(receiveOrderDto);

        // 決済方法エンティティを最新化する
        // billPriceCalculateLogic#settlementCommission で同様の処理を「条件付き」で実装しているが、
        // 注文チェック処理において、その条件で実行されることが無いため、当クラスにて実装を行う。
        // なお、決済方法エンティティを最新化した場合の billPriceCalculateLogic への影響はない。
        getNewSettlementMethod(receiveOrderDto);

        if (calculateFlag) {
            // 請求金額算出処理実行
            List<CheckMessageDto> calculateMsgList =
                            billPriceCalculateLogic.execute(receiveOrderDto, commissionCalcFlag, carriageCalcFlag,
                                                            siteType, adminId
                                                           );
            if (calculateMsgList != null) {
                orderMessageDto.setOrderMessageList(calculateMsgList);
            }
        } else {
            // 配送方法チェックを実行する
            // billPriceCalculateLogic#carriage で同様の処理を実装しているが、
            // 以下の理由により当クラスにて実装を行う。
            // ・料金計算の影響範囲を考慮し、billPriceCalculateLogic への修正は行わない
            checkDeliveryMethod(receiveOrderDto, orderMessageDto.getOrderMessageList(), siteType.isBack());

            // 決済方法チェックを実行する
            // billPriceCalculateLogic#settlementCommission で同様の処理を実装しているが、
            // 以下の理由により当クラスにて実装を行う。
            // ・料金計算の影響範囲を考慮し、billPriceCalculateLogic への修正は行わない
            checkSettlementMethod(receiveOrderDto, orderMessageDto.getOrderMessageList(), siteType);
        }
    }

    /**
     * 配送方法エンティティを最新化する。<br />
     * <pre>
     * 最新の配送方法エンティティを取得し、受注DTOに格納する。
     * 受注配送の情報は正しく設定されていることを前提とするため、必須入力項目のチェックは行わない。
     * </pre>
     *
     * @param receiveOrderDto 受注Dto
     */
    protected void getNewDeliveryMethod(ReceiveOrderDto receiveOrderDto) {

        // 配送方法SEQを取得する
        // 受注配送、配送方法SEQがNullの場合、配送方法エンティティが取得できないため、処理を抜ける。
        // 通常、配送方法が選択されていない状態で注文チェックが実行されることはないが、
        // 新規受注登録、受注詳細修正では配送方法未設定でも注文チェックが可能であるため、当考慮が必要となる。
        OrderDeliveryEntity orderDeliveryEntity = receiveOrderDto.getOrderDeliveryDto().getOrderDeliveryEntity();
        if (orderDeliveryEntity == null) {
            return;
        }
        Integer deliveryMethodSeq = orderDeliveryEntity.getDeliveryMethodSeq();
        if (deliveryMethodSeq == null) {
            return;
        }

        // 受注DTOの配送方法エンティティを最新化する
        DeliveryMethodEntity deliveryMethodEntity = receiveOrderDto.getOrderDeliveryDto().getDeliveryMethodEntity();
        deliveryMethodEntity = deliveryMethodGetLogic.execute(deliveryMethodSeq);
        receiveOrderDto.getOrderDeliveryDto().setDeliveryMethodEntity(deliveryMethodEntity);
    }

    /**
     * 決済方法エンティティを最新化する。<br />
     * <pre>
     * 最新の決済方法エンティティを取得し、受注DTOに格納する。
     * 受注決済の情報は正しく設定されていることを前提とするため、必須入力項目のチェックは行わない。
     * </pre>
     *
     * @param receiveOrderDto 受注Dto
     */
    protected void getNewSettlementMethod(ReceiveOrderDto receiveOrderDto) {

        // 決済方法SEQを取得する
        // 受注決済、決済方法SEQがNullの場合、決済方法エンティティが取得できないため、処理を抜ける。
        // 通常、決済方法が選択されていない状態で注文チェックが実行されることはないが、
        // 新規受注登録、受注詳細修正では決済方法未設定でも注文チェックが可能であるため、当考慮が必要となる。
        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();
        if (orderSettlementEntity == null) {
            return;
        }
        Integer settlementMethodSeq = orderSettlementEntity.getSettlementMethodSeq();
        if (settlementMethodSeq == null) {
            return;
        }

        // 受注DTOの決済方法エンティティを最新化する
        SettlementMethodEntity settlementMethodEntity = receiveOrderDto.getSettlementMethodEntity();
        settlementMethodEntity = settlementMethodGetLogic.execute(settlementMethodSeq);
        receiveOrderDto.setSettlementMethodEntity(settlementMethodEntity);
    }

    /**
     * 配送方法チェックを行う。<br />
     * <pre>
     * 請求金額算出ロジックが実行されない場合に実行する。
     * 受注配送の情報をもとに、利用可能な配送方法が存在するかのチェック処理を行う。
     * チェックエラーが発生した場合、チェックメッセージDTOリストにエラーメッセージを追加する。
     * 受注配送の情報は正しく設定されていることを前提とするため、必須入力項目のチェックは行わない。
     * </pre>
     *
     * @param receiveOrderDto 受注Dto
     * @param msgList         チェックメッセージDTOリスト
     */
    protected void checkDeliveryMethod(ReceiveOrderDto receiveOrderDto,
                                       List<CheckMessageDto> msgList,
                                       Boolean isSiteBack) {

        // 配送DTOリスト取得
        List<DeliveryDto> deliveryDtoList =
                        getDeliveryDtoList(receiveOrderDto, receiveOrderDto.getOrderDeliveryDto(), isSiteBack);

        // 配送方法別送料リストの有無をチェックする
        if (deliveryDtoList == null || deliveryDtoList.size() == 0) {
            msgList.add(toCheckMessageDto(MSGCD_GETDELIVERY_NULL, null, true));
        }

        // 利用可能な配送方法別送料リストが存在するかチェックする
        boolean isDeliveryError = true;
        for (DeliveryDto deliveryDto : deliveryDtoList) {
            if (deliveryDto.isSelectClass()) {
                isDeliveryError = false;
            }
        }
        if (isDeliveryError) {
            msgList.add(toCheckMessageDto(MSGCD_GETDELIVERY_ERROR, null, true));
        }
    }

    /**
     * 配送DTOリストを取得する。<br/>
     * <pre>
     * 配送DTOリストを取得する。
     * 無料配送フラグはOFF（当処理では送料を取得する必要が無いため設定は不要）
     * 最大取得件数は「0」を設定（無制限）
     * </pre>
     *
     * @param receiveOrderDto  受注Dto
     * @param orderDeliveryDto 受注配送DTO
     * @return 配送DTOリスト
     */
    protected List<DeliveryDto> getDeliveryDtoList(ReceiveOrderDto receiveOrderDto,
                                                   OrderDeliveryDto orderDeliveryDto,
                                                   Boolean isSiteBack) {

        // 受注配送エンティティより、配送方法別送料リスト取得に必要な検索条件を取得する
        OrderDeliveryEntity orderDeliveryEntity = orderDeliveryDto.getOrderDeliveryEntity();
        // 配送方法SEQ
        Integer deliveryMethodSeq = orderDeliveryEntity.getDeliveryMethodSeq();
        // お届け先住所-郵便番号
        String zipCode = orderDeliveryEntity.getReceiverZipCode();
        // お届け先住所-都道府県（区分ラベルから都道府県タイプを取得する）
        HTypePrefectureType prefectureType = EnumTypeUtil.getEnumFromLabel(HTypePrefectureType.class,
                                                                           orderDeliveryEntity.getReceiverPrefecture()
                                                                          );

        // 受注サマリエンティティより、配送方法別送料リスト取得に必要な検索条件を取得する
        // ショップSEQ
        Integer shopSeq = receiveOrderDto.getOrderSummaryEntity().getShopSeq();
        // 商品金額合計
        BigDecimal goodsPriceTotal = orderUtility.getGoodsPriceTotal(orderDeliveryDto.getOrderGoodsEntityList());

        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();
        HTypeSettlementMethodType settlementMethodType = orderSettlementEntity.getSettlementMethodType();

        // 配送方法別送料リスト取得の検索条件を設定する
        // 無料配送フラグはOFF（当処理では送料を取得する必要が無いため設定は不要）
        // 最大取得件数は「0」を設定（無制限）
        DeliverySearchForDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(DeliverySearchForDaoConditionDto.class);
        conditionDto.setShopSeq(shopSeq);
        conditionDto.setPrefectureType(prefectureType);
        conditionDto.setTotalGoodsPrice(goodsPriceTotal);
        conditionDto.setZipcode(zipCode);
        // バックオフィスでAmazonPaymentの時は配送不可エリアを無視
        if (ignoreImpossibleArea(settlementMethodType, isSiteBack)) {
            conditionDto.setIgnoreImpossibleAreaFlag(true);
        }
        List<Integer> deliveryMethodSeqlist = new ArrayList<>();
        deliveryMethodSeqlist.add(deliveryMethodSeq);

        // 配送方法別送料リスト取得を実行する
        List<DeliveryDto> deliveryDtoList =
                        deliveryMethodSelectListGetLogic.execute(conditionDto, deliveryMethodSeqlist,
                                                                 HTypeFreeDeliveryFlag.OFF
                                                                );
        return deliveryDtoList;
    }

    /**
     * 決済方法チェックを行う。<br />
     * <pre>
     * 請求金額算出ロジックが実行されない場合に実行する。
     * 受注決済の情報をもとに、利用可能な決済方法が存在するかのチェック処理を行う。
     * チェックエラーが発生した場合、チェックメッセージDTOリストにエラーメッセージを追加する。
     * 受注決済の情報は正しく設定されていることを前提とするため、必須入力項目のチェックは行わない。
     * </pre>
     *
     * @param receiveOrderDto 受注Dto
     * @param msgList         チェックメッセージDTOリスト
     */
    protected void checkSettlementMethod(ReceiveOrderDto receiveOrderDto,
                                         List<CheckMessageDto> msgList,
                                         HTypeSiteType siteType) {

        // 決済DTOリスト取得
        List<SettlementDto> settlementDtoList = getReceiveOrderDto(receiveOrderDto, siteType);

        // 決済方法別手数料リストの有無をチェックする
        if (settlementDtoList == null || settlementDtoList.size() == 0) {
            msgList.add(toCheckMessageDto(MSGCD_GETSETTOLEMENT_NULL, null, true));
            return;
        }

        // 利用可能な決済方法別手数料リストが存在するかチェックする
        boolean isSettlementError = true;
        for (SettlementDto settlementDto : settlementDtoList) {
            if (settlementDto.isSelectClass()) {
                isSettlementError = false;
            }
        }
        if (isSettlementError) {
            msgList.add(toCheckMessageDto(MSGCD_GETSETTOLEMENT_ERROR, null, true));
        }
    }

    /**
     * 決済DTOリストの取得<br/>
     * <pre>
     * 決済DTOリストの取得する。
     * 最大取得件数は「0」を設定（無制限）
     * </pre>
     *
     * @param receiveOrderDto 受注Dto
     * @return 決済DTOリスト
     */
    protected List<SettlementDto> getReceiveOrderDto(ReceiveOrderDto receiveOrderDto, HTypeSiteType siteType) {

        // 受注決済エンティティより、決済方法リスト取得に必要な検索条件を取得する
        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();
        // 決済方法SEQ
        Integer settlementMethodSeq = orderSettlementEntity.getSettlementMethodSeq();
        // 商品金額合計
        BigDecimal goodsPriceTotal = orderSettlementEntity.getGoodsPriceTotal();
        // 送料
        BigDecimal carriage = orderSettlementEntity.getCarriage();
        // 商品金額合計 + 送料 + その他金額合計 - クーポン割引額 - ポイント割引額
        BigDecimal commissionLessOrderPrice =
                        calculatePriceUtility.getCommissionLessTaxIncludedOrderPrice(orderSettlementEntity);
        if (commissionLessOrderPrice.compareTo(BigDecimal.ZERO) < 0) {
            // 全額ポイント・クーポン決済の場合、０より小さくなるので、SQLで違う結果出るため
            // 理由：クーポン割引額やポイント割引額は消費税含んでるため
            commissionLessOrderPrice = BigDecimal.ZERO;
        }
        // 配送方法SEQリスト
        List<Integer> deliveryMethodSeqList = orderUtility.createSelectDeliveryMethodSeqList(receiveOrderDto);

        // 決済方法別手数料リスト取得の検索条件を設定
        // 最大取得件数は「0」を設定（無制限）
        SettlementSearchForDaoConditionDto conditionDto =
                        calculatePriceUtility.getSettlementSearchForDaoConditionDto(receiveOrderDto, siteType,
                                                                                    orderSettlementEntity
                                                                                   );
        List<Integer> settlementMethodSeqList = new ArrayList<>();
        settlementMethodSeqList.add(settlementMethodSeq);

        // 決済方法別手数料リスト取得を実行する
        List<SettlementDto> settlementDtoList =
                        settlementMethodListGetLogic.execute(conditionDto, settlementMethodSeqList, goodsPriceTotal,
                                                             commissionLessOrderPrice, deliveryMethodSeqList, carriage
                                                            );
        return settlementDtoList;
    }

    /**
     * 購入履歴を取得する際の条件Dtoを作成する<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @return OrderSummarySearchForDaoConditionDto
     */
    protected OrderSummarySearchForDaoConditionDto createHistoryConditonDto(Integer memberInfoSeq) {
        OrderSummarySearchForDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(OrderSummarySearchForDaoConditionDto.class);
        conditionDto.setMemberInfoSeq(memberInfoSeq);

        return conditionDto;
    }

    /**
     * 最新の商品詳細情報を取得する<br/>
     * <li>受注単位での商品数量マップをチェックDtoに格納する</li>
     * <li>受注単位で試供品が含まれていれば、購入履歴をチェックDtoに格納する</li>
     * <li>受注単位で以外の商品が含まれていれば、チェックDtoのフラグをたてる</li>
     *
     * @param orderDeliveryDto 受注配送Dto
     * @param checkDto         チェック用Dto
     * @return 商品詳細Dtoリストマップ &lt;Key=注文連番, Value=商品詳細Dtoリスト&gt;
     */
    protected Map<Integer, Map<Integer, GoodsDetailsDto>> getGoodsDetailsMapMap(OrderDeliveryDto orderDeliveryDto,
                                                                                OrderForCheckDto checkDto) {

        // 商品詳細情報取得
        Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap = new HashMap<>();

        if (CollectionUtil.isNotEmpty(checkDto.getGoodsSeqList())) {
            goodsDetailsDtoMap = goodsDetailsMapGetLogic.execute(checkDto.getGoodsSeqList());
        }

        // 配送と商品を紐づける
        Map<Integer, Map<Integer, GoodsDetailsDto>> resultMap = new HashMap<>();

        Map<Integer, GoodsDetailsDto> detailsDtoMap = new HashMap<>();

        // TODO-QUAD-1222
        // HM36もnullの場合、NullPointerException発生
        //if (orderDeliveryDto == null || CollectionUtil.isEmpty(orderDeliveryDto.getOrderGoodsEntityList())) {
        // continue;
        //}
        for (OrderGoodsEntity orderGoodsEntity : orderDeliveryDto.getOrderGoodsEntityList()) {
            if (goodsDetailsDtoMap.containsKey(orderGoodsEntity.getGoodsSeq())) {
                detailsDtoMap.put(
                                orderGoodsEntity.getGoodsSeq(), goodsDetailsDtoMap.get(orderGoodsEntity.getGoodsSeq()));
            }
        }

        resultMap.put(orderDeliveryDto.getOrderDeliveryEntity().getOrderConsecutiveNo(), detailsDtoMap);

        return resultMap;
    }

    /**
     * 商品チェック前の準備を行う<br/>
     *
     * @param receiveOrderDto 受注Dto
     * @param useTmpFlg       商品チェックを仮リストで行うかを指定するフラグ
     * @return チェック用Dto
     */
    protected OrderForCheckDto prepareForGoodsCheck(ReceiveOrderDto receiveOrderDto, boolean useTmpFlg) {
        OrderForCheckDto checkDto = ApplicationContextUtility.getBean(OrderForCheckDto.class);
        // 会員SEQセット
        checkDto.setMemberInfoSeq(receiveOrderDto.getOrderSummaryEntity().getMemberInfoSeq());

        // 初期化
        checkDto.setGoodsSeqList(new ArrayList<>());
        checkDto.setGoodsCntMap(new HashMap<>());

        // TODO-QUAD-1222
        // HM36もnullの場合、NullPointerException発生
        //if (receiveOrderDto.getOrderDeliveryDto() == null) {
        // continue;
        //}
        // 確認画面からの呼び出しでなければ仮リストを回す
        List<OrderGoodsEntity> orderGoodsEntityList = new ArrayList<>();
        if (useTmpFlg) {
            orderGoodsEntityList = receiveOrderDto.getOrderDeliveryDto().getTmpOrderGoodsEntityList();
        } else {
            orderGoodsEntityList = receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList();
        }
        // TODO-QUAD-1222
        // HM36もnullの場合、NullPointerException発生
        //if (CollectionUtil.isEmpty(orderGoodsEntityList)) {
        // continue;
        //}
        for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {
            Integer goodsSeq = orderGoodsEntity.getGoodsSeq();

            // 初めは0を格納
            if (!checkDto.getGoodsCntMap().containsKey(goodsSeq)) {
                checkDto.getGoodsCntMap().put(goodsSeq, BigDecimal.ZERO);
            }
            BigDecimal goodsCnt = checkDto.getGoodsCntMap().get(goodsSeq);
            goodsCnt = goodsCnt.add(orderGoodsEntity.getGoodsCount());
            checkDto.getGoodsCntMap().put(goodsSeq, goodsCnt);

            // 既に追加済みなら対象外
            if (checkDto.getGoodsSeqList().contains(goodsSeq)) {
                continue;
            }

            // 商品SEQリストをセット
            checkDto.getGoodsSeqList().add(goodsSeq);
        }

        return checkDto;
    }

    /**
     * 公開状態チェック<br/>
     *
     * @param goodsDetailsDto 商品詳細DTO
     * @param msgList         チェックメッセージDTOリスト
     */
    protected void openStatusCheck(GoodsDetailsDto goodsDetailsDto, List<CheckMessageDto> msgList) {
        if (HTypeOpenDeleteStatus.DELETED.equals(goodsDetailsDto.getGoodsOpenStatusPC())) {
            msgList.add(toCheckMessageDto(MSGCD_GOODS_OPNE_STATE_DELETE_ERROR, null, true));
        }
    }

    /**
     * 販売状態チェック<br/>
     *
     * @param goodsDetailsDto 商品詳細DTO
     * @param msgList         チェックメッセージDTOリスト
     */
    protected void saleStatusCheck(GoodsDetailsDto goodsDetailsDto, List<CheckMessageDto> msgList) {
        if (!HTypeOpenDeleteStatus.DELETED.equals(goodsDetailsDto.getGoodsOpenStatusPC())
            && HTypeGoodsSaleStatus.DELETED.equals(goodsDetailsDto.getSaleStatusPC())) {
            msgList.add(toCheckMessageDto(MSGCD_GOODS_SALE_STATE_DELETE_ERROR, null, true));
        }
    }

    /**
     * 販売可能在庫数オーバーチェック<br/>
     *
     * @param goodsDetailsDto 商品詳細DTO
     * @param goodsCount      商品数量
     * @param msgList         チェックメッセージDTOリスト
     * @return
     */
    protected void salesPossibleStockOverCheck(GoodsDetailsDto goodsDetailsDto,
                                               BigDecimal goodsCount,
                                               List<CheckMessageDto> msgList) {
        if (goodsCount != null && HTypeStockManagementFlag.ON.equals(goodsDetailsDto.getStockManagementFlag())) {

            // 販売可能数 = 実在個数-安全在庫数-注文確保数
            BigDecimal salesPossibleStock = goodsDetailsDto.getRealStock()
                                                           .subtract(goodsDetailsDto.getSafetyStock())
                                                           .subtract(goodsDetailsDto.getOrderReserveStock());

            // 在庫チェック
            if (salesPossibleStock.compareTo(BigDecimal.ZERO) <= 0) {
                // 販売可能数 <= 0 の場合、在庫なしエラー
                msgList.add(toCheckMessageDto(MSGCD_NO_STOCK_GOODS, null, true));
            } else if (salesPossibleStock.compareTo(goodsCount) < 0) {
                // 販売可能数 < 注文数 の場合、チェックエラー
                msgList.add(toCheckMessageDto(MSGCD_STOCK_SHORTAGE_GOODS, null, true));
            }
        }
    }

    /**
     * 購入制限数オーバーチェック<br/>
     *
     * @param goodsDetailsDto 商品詳細DTO
     * @param orderGoods      受注商品Entity
     * @param goodsCount      商品数量
     * @param msgList         チェックメッセージDTOリスト
     */
    protected void purchasedMaxOverCheck(GoodsDetailsDto goodsDetailsDto,
                                         OrderGoodsEntity orderGoods,
                                         BigDecimal goodsCount,
                                         List<CheckMessageDto> msgList) {
        BigDecimal purchasedMax = goodsDetailsDto.getPurchasedMax();

        // 購入制限数が０(無制限)ではなく、商品数量より小さい場合はエラー
        if (purchasedMax.compareTo(BigDecimal.ZERO) != 0 && purchasedMax.compareTo(goodsCount) < 0) {
            msgList.add(toCheckMessageDto(MSGCD_PURCHSEDMAX_OVER, null, true));
        }
    }

    /**
     * 利用可能決済方法チェック<br/>
     *
     * @param possibleSettlementSeqList 利用可能決済方法Seqリスト
     * @param msgList                   チェックメッセージDTOリスト
     */
    protected void possibleSettlementCheck(List<Integer> possibleSettlementSeqList, List<CheckMessageDto> msgList) {
        if (possibleSettlementSeqList != null && possibleSettlementSeqList.isEmpty()) {
            msgList.add(toCheckMessageDto(MSGCD_SELECT_SETTLEMENTMETHOD_ZERO, null, true));
        }
    }

    /**
     * 個別配送商品チェック
     * <pre>
     * セット商品の子商品は無視する
     * </pre>
     *
     * @param goodsDetailsMap      商品詳細DTOリスト
     * @param orderGoodsEntityList 受注商品リスト
     * @param msgDtoMap            エラーマップ
     */
    protected void individualDeliveryCheck(Map<Integer, GoodsDetailsDto> goodsDetailsMap,
                                           List<OrderGoodsEntity> orderGoodsEntityList,
                                           Map<Integer, List<CheckMessageDto>> msgDtoMap) {
        // 商品情報を取得
        List<GoodsDetailsDto> checkTargetList = new ArrayList<>();
        for (OrderGoodsEntity orderGoods : orderGoodsEntityList) {
            GoodsDetailsDto target = goodsDetailsMap.get(orderGoods.getGoodsSeq());
            if (!checkTargetList.contains(target)) {
                checkTargetList.add(target);
            }
        }

        boolean individualDeliveryFlag = false;
        boolean notIndividualDeliveryFlag = false;

        for (GoodsDetailsDto goodsDetailsDto : checkTargetList) {

            List<CheckMessageDto> msgDtoList = msgDtoMap.get(goodsDetailsDto.getGoodsSeq());
            if (msgDtoList == null) {
                msgDtoList = new ArrayList<>();
            }

            // 商品の個別配送フラグに応じて、フラグを設定
            if (HTypeIndividualDeliveryType.ON.equals(goodsDetailsDto.getIndividualDeliveryType())) {

                if (individualDeliveryFlag) {
                    // 複数個別配送エラー
                    msgDtoList.add(toCheckMessageDto(MSGCD_INDIVIDUALDELIVERY_ERROR, null, true));
                } else {
                    if (notIndividualDeliveryFlag) {
                        // 個別配送と通常両方が存在エラー
                        msgDtoList.add(toCheckMessageDto(MSGCD_NOMAL_INDIVIDUAL_BOTH_ERROR, null, true));
                    }
                    individualDeliveryFlag = true;
                }
            } else {
                if (individualDeliveryFlag) {
                    // 個別配送と通常両方が存在エラー
                    msgDtoList.add(toCheckMessageDto(MSGCD_NOMAL_INDIVIDUAL_BOTH_ERROR, null, true));
                }
                notIndividualDeliveryFlag = true;
            }

            if (msgDtoList.size() > 0) {
                msgDtoMap.put(goodsDetailsDto.getGoodsSeq(), msgDtoList);
            }
        }
    }

    /**
     * 配送方法整合性チェック<br/>
     *
     * @param receiveOrderDto 受注Dto
     * @param deliveryList    利用可能配送方法リスト
     * @param msgList         チェックメッセージDTOリスト
     */
    protected void checkDeliveryMethodCombination(ReceiveOrderDto receiveOrderDto,
                                                  List<Integer> deliveryList,
                                                  List<CheckMessageDto> msgList,
                                                  HTypeSiteType siteType) {

        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();

        DeliveryMethodEntity deliveryMethodEntity = receiveOrderDto.getOrderDeliveryDto().getDeliveryMethodEntity();
        OrderDeliveryEntity orderDeliveryEntity = receiveOrderDto.getOrderDeliveryDto().getOrderDeliveryEntity();
        // どちらかのEntityが nullの場合不備とみなす
        if (deliveryMethodEntity == null || orderDeliveryEntity == null) {
            // TODO-QUAD-1222
            // frotもadminもnullのとき、チェックせずに注文や受注修正している・・・
            // 下記のようにメッセージリストを作成し、throwさせるほうが良いと判断。
            // メッセージについては要確認 ⇒　システムエラーが発生しました。システム管理者へお問い合わせください。
            msgList.add(toCheckMessageDto(MSGCD_ENTITY_ILLEGAL_OPERATION, null, true));
            return;
        }

        // 公開状態チェック
        deliveryMethodOpenStateCheck(deliveryMethodEntity, msgList, siteType);

        // 受注商品による選択可能チェック
        deliveryMethodPossibleSelectByGoodsCheck(orderDeliveryEntity, deliveryList, msgList);

        // 配送方法不可能エリアチェック
        deliveryImpossibleAreaCheck(receiveOrderDto.getOrderDeliveryDto(), orderSettlementEntity, msgList, siteType);

        // 配送種別ごとの制約チェック
        deliveryRestrictionCheck(receiveOrderDto.getOrderDeliveryDto(), msgList, siteType);

        // お届け希望日範囲チェック
        // 定期注文の場合はチェックしない
        if (!receiveOrderDto.isPeriodicOrderFlag()) {
            deliveryDateRangeCheck(deliveryMethodEntity, orderDeliveryEntity, msgList);
        }
    }

    /**
     * 配送方法公開状態チェック<br/>
     *
     * @param deliveryMethodEntity 配送方法エンティティ
     * @param msgList              エラーリスト
     */
    protected void deliveryMethodOpenStateCheck(DeliveryMethodEntity deliveryMethodEntity,
                                                List<CheckMessageDto> msgList,
                                                HTypeSiteType siteType) {
        if (HTypeOpenDeleteStatus.DELETED.equals(deliveryMethodEntity.getOpenStatusPC())
            || HTypeOpenDeleteStatus.DELETED.equals(deliveryMethodEntity.getOpenStatusMB())) {
            msgList.add(toCheckMessageDto(MSGCD_DELIVERY_OPNE_STATE_DELETE_ERROR, null, true));
        }
    }

    /**
     * 受注商品による選択可能チェック<br/>
     *
     * @param orderDeliveryEntity 受注配送エンティティ
     * @param deliveryList        選択可能配送リスト
     * @param msgList             チェックメッセージDTOリスト
     */
    protected void deliveryMethodPossibleSelectByGoodsCheck(OrderDeliveryEntity orderDeliveryEntity,
                                                            List<Integer> deliveryList,
                                                            List<CheckMessageDto> msgList) {
        Integer deliveryMethodSeq = orderDeliveryEntity.getDeliveryMethodSeq();
        if (deliveryList != null && deliveryMethodSeq != null && !deliveryList.contains(deliveryMethodSeq)) {
            msgList.add(toCheckMessageDto(MSGCD_DELIVERYMETHOD_ERROR, null, true));
        }
    }

    /**
     * 配送方法不可能エリアチェック<br/>
     *
     * @param orderDeliveryDto      受注配送DTO
     * @param orderSettlementEntity 受注決済
     * @param msgList               エラーリスト
     */
    protected void deliveryImpossibleAreaCheck(OrderDeliveryDto orderDeliveryDto,
                                               OrderSettlementEntity orderSettlementEntity,
                                               List<CheckMessageDto> msgList,
                                               HTypeSiteType siteType) {

        Integer deliveryMethodSeq = orderDeliveryDto.getOrderDeliveryEntity().getDeliveryMethodSeq();
        String receiverZipCode = orderDeliveryDto.getOrderDeliveryEntity().getReceiverZipCode();

        DeliveryImpossibleAreaEntity deliveryImpossibleAreaEntity = null;
        if (deliveryMethodSeq != null && receiverZipCode != null && !ignoreImpossibleArea(
                        orderSettlementEntity.getSettlementMethodType(), siteType.isBack())) {
            deliveryImpossibleAreaEntity = deliveryImpossibleAreaGetLogic.execute(deliveryMethodSeq, receiverZipCode);
        }

        if (deliveryImpossibleAreaEntity != null) {

            DeliveryMethodEntity deliveryMethodEntity = orderDeliveryDto.getDeliveryMethodEntity();
            String name = orderUtility.getDeliveryMethodName(deliveryMethodEntity, siteType);

            msgList.add(toCheckMessageDto(MSGCD_DELIVERY_IMPOSSIBLE_AREA, new Object[] {name}, true));
        }
    }

    /**
     * 配送種別ごとの制約チェック<br/>
     *
     * @param orderDeliveryDto 受注配送DTO
     * @param msgList          チェックメッセージDTOリスト
     */
    protected void deliveryRestrictionCheck(OrderDeliveryDto orderDeliveryDto,
                                            List<CheckMessageDto> msgList,
                                            HTypeSiteType siteType) {

        Integer deliveryMethodSeq = orderDeliveryDto.getOrderDeliveryEntity().getDeliveryMethodSeq();
        String zipCode = orderDeliveryDto.getOrderDeliveryEntity().getReceiverZipCode();
        if (deliveryMethodSeq == null || zipCode == null) {
            return;
        }

        // 配送種別に応じてチェックを行なう
        HTypeDeliveryMethodType deliveryMethodType = orderDeliveryDto.getDeliveryMethodEntity().getDeliveryMethodType();
        if (deliveryMethodType.equals(HTypeDeliveryMethodType.PREFECTURE)) {
            // 配送種別：都道府県別

            // 配送特別料金エリアの取得
            DeliverySpecialChargeAreaEntity deliverySpecialChargeArea =
                            deliverySpecialChargeAreaDao.getEntity(deliveryMethodSeq, zipCode);

            // 配送区分別送料の一覧を取得
            List<DeliveryMethodTypeCarriageEntity> deliveryMethodTypeCarriageEntityList =
                            deliveryMethodTypeCarriageListGetLogic.execute(deliveryMethodSeq);

            // 配送区分送料リスト存在チェック
            boolean isExistDelivery = false;
            for (DeliveryMethodTypeCarriageEntity deliveryMethodTypeCarriageEntity : deliveryMethodTypeCarriageEntityList) {
                if (deliveryMethodTypeCarriageEntity.getPrefectureType()
                                                    .getLabel()
                                                    .contains(orderDeliveryDto.getOrderDeliveryEntity()
                                                                              .getReceiverPrefecture())) {
                    isExistDelivery = true;
                    break;
                }
            }
            // 送料未設定の都道府県で特別料金エリアにも登録されていない場合
            if (!isExistDelivery && deliverySpecialChargeArea == null) {

                String name = orderUtility.getDeliveryMethodName(orderDeliveryDto.getDeliveryMethodEntity(), siteType);

                msgList.add(toCheckMessageDto(MSGCD_PREFECTURE_ERROR, new Object[] {name}, true));
            }

            // ※金額別送料は現在未使用
        } else if (deliveryMethodType.equals(HTypeDeliveryMethodType.AMOUNT)) {
            // 配送種別：金額別

            // 配送区分別送料の一覧を取得
            List<DeliveryMethodTypeCarriageEntity> deliveryMethodTypeCarriageEntityList =
                            deliveryMethodTypeCarriageListGetLogic.execute(deliveryMethodSeq);

            // 一覧より最大の金額を取得
            BigDecimal maxPrice = BigDecimal.ZERO;
            for (DeliveryMethodTypeCarriageEntity deliveryMethodTypeCarriageEntity : deliveryMethodTypeCarriageEntityList) {
                if (maxPrice.compareTo(deliveryMethodTypeCarriageEntity.getMaxPrice()) < 0) {
                    maxPrice = deliveryMethodTypeCarriageEntity.getMaxPrice();
                }
            }

            // 配送可能な上限金額 ＜ 商品合計金額 の場合、エラー
            BigDecimal goodsTotalPrice = orderUtility.getGoodsPriceTotal(orderDeliveryDto.getOrderGoodsEntityList());
            if (maxPrice.compareTo(goodsTotalPrice) < 0) {
                msgList.add(toCheckMessageDto(MSGCD_DELIVERY_PURCHSEDMAX_OVER, null, true));
            }
        }
    }

    /**
     * お届け希望日範囲チェック<br/>
     *
     * @param deliveryMethodEntity 配送方法エンティティ
     * @param orderDeliveryEntity  受注配送エンティティ
     * @param msgList              チェックメッセージDTOリスト
     */
    protected void deliveryDateRangeCheck(DeliveryMethodEntity deliveryMethodEntity,
                                          OrderDeliveryEntity orderDeliveryEntity,
                                          List<CheckMessageDto> msgList) {

        if (orderDeliveryEntity.getOrderSeq() != null) {
            return;
        }

        // お届け希望日がない場合、チェック不要
        Timestamp receiverDate = orderDeliveryEntity.getReceiverDate();
        if (receiverDate == null) {
            return;
        }

        // 現在の選択可能日付Mapを取得
        int leadTime = deliveryMethodEntity.getDeliveryLeadTime();
        int selectDays = deliveryMethodEntity.getPossibleSelectDays();
        ReceiverDateDto receiverDateDto = receiverDateGetLogic.checkCreateReceiverDateList(leadTime, selectDays, false,
                                                                                           deliveryMethodEntity.getDeliveryMethodSeq()
                                                                                          );

        // お届け希望日をチェック用に変換
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        String checkReceiverDate = dateUtility.formatYmd(receiverDate);

        // 選択されたお届け希望日が選択可能日付Mapに存在するか
        if (receiverDateDto.getDateMap() != null && receiverDateDto.getDateMap().containsKey(checkReceiverDate)) {
            return;
        } else {
            msgList.add(toCheckMessageDto(MSGCD_DELIVERY_RECEIVER_DATE_RANGE_ERROR, null, true));
        }
    }

    /**
     * 決済方法整合性チェック<br/>
     *
     * @param receiveOrderDto 受注Dto
     * @param settlementList  利用可能配送方法リスト
     * @param msgList         チェックメッセージDTOリスト
     */
    protected void checkSettlementMethod(ReceiveOrderDto receiveOrderDto,
                                         List<Integer> settlementList,
                                         List<CheckMessageDto> msgList,
                                         HTypeSiteType siteType) {

        SettlementMethodEntity settlementMethodEntity = receiveOrderDto.getSettlementMethodEntity();
        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();

        checkSettlementMethodCombination(receiveOrderDto, settlementMethodEntity, orderSettlementEntity, settlementList,
                                         msgList, siteType
                                        );
    }

    /**
     * 定期商品の決済方法整合性チェック<br/>
     *
     * @param receiveOrderDto 受注Dto
     * @param settlementList  利用可能配送方法リスト
     * @param msgList         チェックメッセージDTOリスト
     */
    protected void checkPeriodicSettlementMethod(ReceiveOrderDto receiveOrderDto,
                                                 List<Integer> settlementList,
                                                 List<CheckMessageDto> msgList,
                                                 HTypeSiteType siteType) {

        SettlementMethodEntity settlementMethodEntity = receiveOrderDto.getNextSettlementMethodEntity();
        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderNextSettlementEntity();

        checkSettlementMethodCombination(receiveOrderDto, settlementMethodEntity, orderSettlementEntity, settlementList,
                                         msgList, siteType
                                        );
    }

    /**
     * 決済方法整合性チェック<br/>
     *
     * @param receiveOrderDto        受注Dto
     * @param settlementMethodEntity 決済方法エンティティ
     * @param orderSettlementEntity  受注決済エンティティ
     * @param settlementList         利用可能配送方法リスト
     * @param msgList                チェックメッセージDTOリスト
     */
    protected void checkSettlementMethodCombination(ReceiveOrderDto receiveOrderDto,
                                                    SettlementMethodEntity settlementMethodEntity,
                                                    OrderSettlementEntity orderSettlementEntity,
                                                    List<Integer> settlementList,
                                                    List<CheckMessageDto> msgList,
                                                    HTypeSiteType siteType) {

        // 全額割引決済が選択されている場合には、商品に設定されている決済方法との関連チェックは不要
        HTypeSettlementMethodType methodType = settlementMethodEntity.getSettlementMethodType();
        // 全額割引決済が選択されている
        if (methodType == HTypeSettlementMethodType.DISCOUNT) {
            return;
        }

        // 公開状態チェック
        settlementMethodOpenStateCheck(settlementMethodEntity, msgList, siteType);

        // 受注商品による選択可能チェック
        settlementMethodPossibleSelectByGoodsCheck(orderSettlementEntity, settlementList, msgList);

        // 決済方法と配送方法の整合性チェック
        settlementAndDeliveryCombinationCheck(
                        settlementMethodEntity, receiveOrderDto.getOrderDeliveryDto(), msgList, siteType);

        // 決済種別ごとの制約チェック
        settlementRestrictionCheck(receiveOrderDto, msgList);
    }

    /**
     * 決済方法公開状態チェック<br/>
     *
     * @param settlementMethodEntity 決済方法エンティティ
     * @param msgList                チェックメッセージDTOリスト
     */
    protected void settlementMethodOpenStateCheck(SettlementMethodEntity settlementMethodEntity,
                                                  List<CheckMessageDto> msgList,
                                                  HTypeSiteType siteType) {
        if (settlementMethodEntity.getOpenStatusPC().equals(HTypeOpenDeleteStatus.DELETED)
            || settlementMethodEntity.getOpenStatusMB().equals(HTypeOpenDeleteStatus.DELETED)) {
            msgList.add(toCheckMessageDto(MSGCD_SETTLEMENT_OPNE_STATE_DELETE_ERROR, null, true));
        }
    }

    /**
     * 受注商品による選択可能チェック<br/>
     *
     * @param orderSettlementEntity 受注決済エンティティ
     * @param settlementList        選択可能決済方法リスト
     * @param msgList               チェックメッセージDTOリスト
     */
    protected void settlementMethodPossibleSelectByGoodsCheck(OrderSettlementEntity orderSettlementEntity,
                                                              List<Integer> settlementList,
                                                              List<CheckMessageDto> msgList) {
        Integer deliveryMethodSeq = orderSettlementEntity.getSettlementMethodSeq();
        if (settlementList != null && deliveryMethodSeq != null && !settlementList.contains(deliveryMethodSeq)) {
            msgList.add(toCheckMessageDto(MSGCD_SETTLEMENTMETHOD_ERROR, null, true));
        }
    }

    /**
     * 決済方法と配送方法の整合性チェック<br/>
     *
     * @param settlementMethodEntity 決済方法エンティティ
     * @param orderDeliveryDto       受注配送DTO
     * @param msgList                エラーリスト
     */
    protected void settlementAndDeliveryCombinationCheck(SettlementMethodEntity settlementMethodEntity,
                                                         OrderDeliveryDto orderDeliveryDto,
                                                         List<CheckMessageDto> msgList,
                                                         HTypeSiteType siteType) {

        if (settlementMethodEntity.getDeliveryMethodSeq() == null) {
            return;
        }
        DeliveryMethodEntity deliveryMethodEntity = null;

        deliveryMethodEntity = orderDeliveryDto.getDeliveryMethodEntity();
        if (settlementMethodEntity.getDeliveryMethodSeq().compareTo(deliveryMethodEntity.getDeliveryMethodSeq()) != 0) {

            String settlementName = orderUtility.getSettlementMethodName(settlementMethodEntity, siteType);
            String deliveryName = orderUtility.getDeliveryMethodName(deliveryMethodEntity, siteType);

            msgList.add(toCheckMessageDto(
                            MSGCD_SETTLEMENTMETHOD_DEF, new Object[] {settlementName, deliveryName}, true));
        }
    }

    /**
     * 決済種別ごとの制約チェック<br/>
     *
     * @param receiveOrderDto 受注Dto
     * @param msgList         チェックメッセージDTOリスト
     */
    protected void settlementRestrictionCheck(ReceiveOrderDto receiveOrderDto, List<CheckMessageDto> msgList) {

        SettlementMethodEntity settlementMethodEntity = receiveOrderDto.getSettlementMethodEntity();
        OrderPersonEntity orderPersonEntity = receiveOrderDto.getOrderPersonEntity();

        // コンビニ決済
        if (settlementMethodEntity.getSettlementMethodType().equals(HTypeSettlementMethodType.CONVENIENCE)) {
            String tel = orderPersonEntity.getOrderTel();
            if (tel == null || tel.length() < 9 || 11 < tel.length()) {
                msgList.add(toCheckMessageDto(TELEPHONE_NUMBE_LENGTH_ERROR, null, true));
            }
        }
    }

    /**
     * 全額割引決済と受注金額の整合性チェックを行う。<br />
     * <pre>
     * 決済方法が全額割引決済であるのに、受注金額が0円でなければエラーとする。
     * 受注金額が0円の場合に決済方法が全額割引決済でなければエラーとする。
     * </pre>
     *
     * @param receiveOrderDto 受注Dto
     * @param messageList     エラーメッセージリスト
     */
    protected void checkCanUseDiscountSettlement(ReceiveOrderDto receiveOrderDto, List<CheckMessageDto> messageList) {
        OrderSummaryEntity summary = receiveOrderDto.getOrderSummaryEntity();
        OrderSettlementEntity settlement = receiveOrderDto.getOrderSettlementEntity();

        // 受注金額が0円か
        boolean orderPriceIsZero = summary.getOrderPrice().compareTo(BigDecimal.ZERO) == 0;
        // クーポン割引額が0円か
        boolean isCouponDiscountPriceEqualsZero = settlement.getCouponDiscountPrice().compareTo(BigDecimal.ZERO) == 0;
        // ポイント利用前受注金額が0円か
        boolean isPrePointDiscountOrderPriceEqualsZero =
                        couponUtility.getSettlementCharge(receiveOrderDto).compareTo(BigDecimal.ZERO) == 0;

        BigDecimal couponDiscount = settlement.getCouponDiscountPrice();
        BigDecimal usePoint = settlement.getUsePoint();

        if (settlement.getSettlementMethodSeq() == null) {
            // 決済方法が選択されていない場合は処理しない
            return;
        }

        Integer freeSettlementMethodSeq = orderUtility.getFreeSettlementMethodSeq();

        // 無料配送 かつ 受注金額が0円であれば正常と判定
        if (freeSettlementMethodSeq.equals(settlement.getSettlementMethodSeq()) && orderPriceIsZero) {
            return;
        }
        if (!isCouponDiscountPriceEqualsZero && isPrePointDiscountOrderPriceEqualsZero && orderPriceIsZero) {
            // 以下の条件を満たす場合、クーポン割引を適用していて、全額決済できるが、全額クーポン決済をしていないと判定
            // 受注決済.クーポン割引額 ≠ 0 且つ
            // 受注サマリ.ポイント利用前受注金額 ＝ 0 且つ
            // 受注サマリ.受注金額 ＝ 0 且つ
            // 決済方法 ≠ 全額クーポン決済
            // ※受注金額が0円という条件を追加しているのは受注修正で追加料金が発生した場合の考慮
            // ※例）クーポン利用前 5,000円, クーポン割引額 5,000円, 追加料金 3,000円の場合、全額クーポンは利用できない

            // クーポン割引を適用していて、全額決済できる場合、決済方法は全額クーポン決済でなければならない
            if (!couponUtility.isCouponSettlementMethod(receiveOrderDto)) {
                messageList.add(toCheckMessageDto(MSGCD_UNUSABLE_ALL_COUPON_SETTLEMENT, null, true));
                return;
            }
        }

        // 決済方法に全額割引決済が選択されている場合、受注金額は0円でなければならない
        // クーポン/ポイントを利用せずに、追加料金で受注金額が0円になった場合の考慮として、クーポンかポイントを利用している場合という条件を追加している
        boolean couponUsed = couponDiscount.compareTo(BigDecimal.ZERO) != 0;
        boolean pointUsed = usePoint.compareTo(BigDecimal.ZERO) != 0;
        boolean discountSettlementUsed =
                        HTypeSettlementMethodType.DISCOUNT.equals(settlement.getSettlementMethodType());
        if (((couponUsed || pointUsed) && discountSettlementUsed && !orderPriceIsZero) || (!couponUsed && !pointUsed
                                                                                           && discountSettlementUsed)) {
            messageList.add(toCheckMessageDto(MSGCD_UNUSABLE_ALL_POINT_SETTLEMENT, null, true));
            return;
        }
    }

    /**
     * 決済可能金額チェック<br/>
     *
     * @param settlementMethodEntity 決済方法エンティティ
     * @param orderPrice             受注金額
     * @param msgList                チェックメッセージDTOリスト
     */
    protected void maxPurchasedPriceOverCheck(SettlementMethodEntity settlementMethodEntity,
                                              BigDecimal orderPrice,
                                              List<CheckMessageDto> msgList,
                                              HTypeSiteType siteType) {

        // 最大購入金額
        BigDecimal maxPrice = settlementMethodEntity.getMaxPurchasedPrice();
        // 最小購入金額
        BigDecimal minPrice = settlementMethodEntity.getMinPurchasedPrice();

        if (maxPrice.compareTo(orderPrice) < 0) {

            String name = orderUtility.getSettlementMethodName(settlementMethodEntity, siteType);

            msgList.add(toCheckMessageDto(
                            MSGCD_SETTLEMENT_PURCHSEDMAX_OVER, new Object[] {name, maxPrice.toString()}, true));
        }
        if (!minPrice.equals(BigDecimal.ZERO) && minPrice.compareTo(orderPrice) > 0) {

            String name = orderUtility.getSettlementMethodName(settlementMethodEntity, siteType);

            msgList.add(toCheckMessageDto(
                            MSGCD_SETTLEMENT_PURCHSEDMIN_OVER, new Object[] {name, minPrice.toString()}, true));
        }

    }

    /**
     * 文字列をSEPARATORで区切りInteger型のリストにして返す。<br/>
     *
     * @param str 変更前文字列
     * @return 変換後リスト
     */
    protected List<Integer> stringToIntegerList(String str) {
        List<Integer> list = new ArrayList<>();
        String[] strs = str.split(SEPARATOR);
        for (String s : strs) {
            list.add(Integer.valueOf(s));
        }
        return list;
    }

    /**
     * リストデータの絞り込み<br/>
     * <p>
     * list1のデータがlist2にない場合、list1から削除します。
     * list1がnullの場合list2のデータを全て移行します。
     *
     * @param list1 データリスト
     * @param list2 データリスト
     * @return 削除後リスト
     */
    protected List<Integer> narrowingList(List<Integer> list1, List<Integer> list2) {
        List<Integer> tmpList = new ArrayList<>();
        if (list1 == null) {
            list1 = list2;
        } else {
            for (Integer index = 0; index < list1.size(); index++) {
                Integer contents = list1.get(index);
                if (!list2.contains(contents)) {
                    tmpList.add(contents);
                }
            }
            list1.removeAll(tmpList);
        }
        return list1;
    }

    /**
     * エラー内容からメッセージリスト作成<br/>
     *
     * @param msgCode            メッセージコード
     * @param args               パラメータ
     * @param orderConsecutiveNo 注文連番
     * @param isError            エラーフラグ
     * @return エラーメッセージDTO
     */
    protected CheckMessageDto toCheckMessageDto(String msgCode,
                                                Object[] args,
                                                Integer orderConsecutiveNo,
                                                boolean isError) {
        CheckMessageDto checkMessageDto = checkMessageUtility.createCheckMessageDto(msgCode, isError, args);
        checkMessageDto.setOrderConsecutiveNo(orderConsecutiveNo);
        return checkMessageDto;
    }

    /**
     * エラー内容からメッセージリスト作成<br/>
     *
     * @param msgCode メッセージコード
     * @param args    パラメータ
     * @param isError エラーフラグ
     * @return エラーメッセージDTO
     */
    protected CheckMessageDto toCheckMessageDto(String msgCode, Object[] args, boolean isError) {
        return toCheckMessageDto(msgCode, args, null, isError);
    }

    /**
     * 配送不可エリア設定を無視するかどうか判定します。<br/>
     *
     * @param settlementMethodType 決済方法
     * @return true:不可エリア無効 / false:不可エリア適用
     */
    protected boolean ignoreImpossibleArea(HTypeSettlementMethodType settlementMethodType, Boolean isSiteBack) {
        return HTypeSettlementMethodType.AMAZON_PAYMENT == settlementMethodType && isSiteBack;
    }

    /**
     * 未成年のアルコール商品購買確認<br/>
     *
     * @param receiveOrderDto    受注商品DTOリスト
     * @param deliveryDto        配送先情報DTO
     * @param goodsDetailsDtoMap 商品詳細DTOマップ
     * @param msgList            エラーリスト
     */
    protected void checkUnderageAlcohol(ReceiveOrderDto receiveOrderDto,
                                        OrderDeliveryDto deliveryDto,
                                        Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap,
                                        List<CheckMessageDto> msgList) {
        // 受注ご注文主の生年月日、年齢を取得
        Timestamp birthday = receiveOrderDto.getOrderPersonEntity().getOrderBirthday();
        Integer orderAge = receiveOrderDto.getOrderPersonEntity().getOrderAge();

        boolean alcoholFlag = false;

        // カートに酒類を含んでいるかチェック
        for (OrderGoodsEntity orderGoods : deliveryDto.getOrderGoodsEntityList()) {
            Integer goodsSeq = orderGoods.getGoodsSeq();
            GoodsDetailsDto goodsDetails = goodsDetailsDtoMap.get(goodsSeq);
            if (goodsDetails.getAlcoholFlag().equals(HTypeAlcoholFlag.ALCOHOL)) {
                alcoholFlag = true;
                break;
            }
        }

        // 酒類が含まれている場合に生年月日チェック実行
        if (alcoholFlag) {
            // 生年月日が未入力の場合は、エラーメッセージ表示
            if (birthday == null) {
                msgList.add(toCheckMessageDto(MSGCD_ALCOHOL_BIRTHDAY_NO_INPUT_ERROR, null, true));
                return;
            }
            // 成人チェック（会員の場合は年齢はnullで生年月日のみで年齢チェック。ゲストは入力した生年月日と年齢の両方で確認）
            if (!orderUtility.isAdultCheck(birthday, orderAge)) {
                msgList.add(toCheckMessageDto(MSGCD_ALCOHOL_CHECK_ERROR, null, true));
            }
        }

    }
}
