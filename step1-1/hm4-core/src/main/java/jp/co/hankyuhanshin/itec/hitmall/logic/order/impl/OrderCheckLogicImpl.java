/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderAgeType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliverySpecialChargeAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderForCheckDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractOrderCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsListGetBySeqLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsMapGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.BillPriceCalculateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderCheckLogic;
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
import jp.co.hankyuhanshin.itec.hitmall.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.CheckMessageUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.CouponUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注文可能チェックロジック実装クラス
 *
 * @author ozaki
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class OrderCheckLogicImpl extends AbstractOrderCheckLogic implements OrderCheckLogic {

    // PDR Migrate Customization from here
    /**
     * GoodsUtility
     */
    private final GoodsUtility goodsUtility;

    /**
     * 購入限度回数≦過去の購入数の場合
     * <code>MSGCD_PURCHASE_LIMIT_OVER_ERROR</code>
     */
    public static final String MSGCD_PURCHASE_LIMIT_OVER_ERROR = "PKG-3559-001-A-";

    @Autowired
    public OrderCheckLogicImpl(CheckMessageUtility checkMessageUtility,
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
                               GoodsUtility goodsUtility) {
        super(checkMessageUtility, orderUtility, calculatePriceUtility, couponUtility, orderGoodsListGetLogic,
              goodsDetailsMapGetLogic, billPriceCalculateLogic, deliveryImpossibleAreaGetLogic,
              deliverySpecialChargeAreaDao, deliveryMethodTypeCarriageListGetLogic, deliveryMethodGetLogic,
              deliveryMethodSelectListGetLogic, settlementMethodGetLogic, settlementMethodListGetLogic,
              receiverDateGetLogic, couponCheckLogic, simultaneousOrderExclusionGetLogic, goodsDetailsListGetBySeqLogic
             );
        this.goodsUtility = goodsUtility;
    }
    // PDR Migrate Customization to here

    /**
     * 商品設定時の実行メソッド
     *
     * @param editOrderDto 受注Dto
     * @return 注文メッセージDto
     */
    @Override
    public OrderMessageDto executeForGoodsSetting(ReceiveOrderDto editOrderDto) {

        // 注文メッセージDTOの初期化
        OrderMessageDto orderMessageDto = ApplicationContextUtility.getBean(OrderMessageDto.class);
        orderMessageDto.setOrderGoodsMessageMapMap(new HashMap<Integer, Map<Integer, List<CheckMessageDto>>>());
        orderMessageDto.setOrderMessageList(new ArrayList<CheckMessageDto>());

        // 商品チェック前の準備
        OrderForCheckDto checkDto = prepareForGoodsCheck(editOrderDto, true);

        Map<Integer, List<CheckMessageDto>> msgMap = new HashMap<>();

        for (OrderGoodsEntity orderGoods : editOrderDto.getOrderDeliveryDto().getTmpOrderGoodsEntityList()) {
            List<CheckMessageDto> msgList = new ArrayList<>();

            Integer goodsSeq = orderGoods.getGoodsSeq();
            // マスタ情報から商品詳細Dto取得
            GoodsDetailsDto goodsDetails = editOrderDto.getMasterDto().getGoodsMaster().get(goodsSeq);
            // 購入制限数チェック
            purchasedMaxOverCheck(goodsDetails, orderGoods, checkDto.getGoodsCntMap().get(goodsSeq), msgList);

            if (CollectionUtil.isNotEmpty(msgList)) {
                msgMap.put(goodsSeq, msgList);
            }
        }

        Integer consecutiveNo = editOrderDto.getOrderDeliveryDto().getOrderDeliveryEntity().getOrderConsecutiveNo();

        // MSGDtoにセット
        orderMessageDto.getOrderGoodsMessageMapMap().put(consecutiveNo, msgMap);

        return orderMessageDto;
    }

    /**
     * 初期処理<br/>
     * 請求金額を計算します。<br/>
     *
     * @param receiveOrderDto 受注DTO
     * @param orderMessageDto 注文メッセージDTO
     * @param calculateFlag   受注金額算出フラグ
     */
    @Override
    protected void beforeProcess(ReceiveOrderDto receiveOrderDto,
                                 OrderMessageDto orderMessageDto,
                                 boolean calculateFlag,
                                 String memberInfoId,
                                 HTypeSiteType siteType,
                                 Integer memberInfoSeq,
                                 String adminId) {
        // 受注サマリー作成
        if (receiveOrderDto.getOrderSummaryEntity() == null) {
            receiveOrderDto.setOrderSummaryEntity(ApplicationContextUtility.getBean(OrderSummaryEntity.class));
        }
        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();

        if (orderSummaryEntity.getShopSeq() == null) {

            // ショップSEQ
            orderSummaryEntity.setShopSeq(1001);
            // 受注サイト種別
            orderSummaryEntity.setOrderSiteType(siteType);
            // 会員SEQ
            orderSummaryEntity.setMemberInfoSeq(memberInfoSeq);
            // 都道府県種別
            orderSummaryEntity.setPrefectureType(receiveOrderDto.getOrderPersonEntity().getPrefectureType());
            // 性別
            orderSummaryEntity.setOrderSex(receiveOrderDto.getOrderPersonEntity().getOrderSex());
            // 年代
            orderSummaryEntity.setOrderAgeType(
                            HTypeOrderAgeType.getType(receiveOrderDto.getOrderPersonEntity().getOrderBirthday()));
        }

        // 請求金額算出処理実行
        super.beforeProcess(receiveOrderDto, orderMessageDto, calculateFlag, memberInfoId, siteType, memberInfoSeq,
                            adminId
                           );
    }

    /**
     * 　販売状態チェック<br/>
     *
     * @param goodsDetailsDto 商品詳細DTO
     * @param msgList         チェックメッセージDTOリスト
     */
    @Override
    protected void saleStatusCheck(GoodsDetailsDto goodsDetailsDto, List<CheckMessageDto> msgList) {

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        Timestamp currentTime = dateUtility.getCurrentTime();

        HTypeGoodsSaleStatus saleStatus = goodsDetailsDto.getSaleStatusPC();
        Timestamp saleStartTime = goodsDetailsDto.getSaleStartTimePC();
        Timestamp saleEndTime = goodsDetailsDto.getSaleEndTimePC();

        // 販売中の商品かどうか
        if (saleStatus.equals(HTypeGoodsSaleStatus.SALE)) {
            // 販売状態チェックOKの場合、販売期間をチェックする
            // 販売開始日時が現在日時を過ぎていない。
            if (saleStartTime != null && currentTime.before(saleStartTime)) {
                msgList.add(toCheckMessageDto(MSGCD_NO_SALE_GOODS_EXIST, null, true));

                // 販売終了日時が現在日時を過ぎている。
            } else if (saleEndTime != null && currentTime.after(saleEndTime)) {
                msgList.add(toCheckMessageDto(MSGCD_NO_SALE_GOODS_EXIST, null, true));
            }

        } else {
            // PDR Migrate Customization from here
            // 心意気商品の場合、販売状態が非販売でもエラーとしない
            if (!goodsUtility.isEmotionPriceGoods(goodsDetailsDto)) {
                // 注文フローに入った後で非販売にされた
                msgList.add(toCheckMessageDto(MSGCD_NO_SALE_GOODS_EXIST, null, true));
            }
            // PDR Migrate Customization to here
        }
    }

    /**
     * 配送方法公開状態チェック<br/>
     *
     * @param deliveryMethodEntity 配送方法エンティティ
     * @param msgList              エラーリスト
     */
    @Override
    protected void deliveryMethodOpenStateCheck(DeliveryMethodEntity deliveryMethodEntity,
                                                List<CheckMessageDto> msgList,
                                                HTypeSiteType siteType) {

        HTypeOpenDeleteStatus openStatus = null;
        if (siteType == HTypeSiteType.FRONT_MB) {
            // 携帯サイトからの注文の場合
            openStatus = deliveryMethodEntity.getOpenStatusMB();

        } else {
            // 携帯以外はPCと見なす
            openStatus = deliveryMethodEntity.getOpenStatusPC();
        }

        if (!openStatus.equals(HTypeOpenDeleteStatus.OPEN)) {
            msgList.add(toCheckMessageDto(MSGCD_DELIVERY_NO_OPEN_ERROR, null, true));
        }
    }

    /**
     * 決済方法公開状態チェック<br/>
     *
     * @param settlementMethodEntity 決済方法エンティティ
     * @param msgList                チェックメッセージDTOリスト
     */
    @Override
    protected void settlementMethodOpenStateCheck(SettlementMethodEntity settlementMethodEntity,
                                                  List<CheckMessageDto> msgList,
                                                  HTypeSiteType siteType) {

        HTypeOpenDeleteStatus openStatus = null;
        if (siteType == HTypeSiteType.FRONT_MB) {
            // 携帯サイトからの注文の場合
            openStatus = settlementMethodEntity.getOpenStatusMB();

        } else {
            // 携帯以外はPCと見なす
            openStatus = settlementMethodEntity.getOpenStatusPC();
        }

        if (!openStatus.equals(HTypeOpenDeleteStatus.OPEN)) {
            msgList.add(toCheckMessageDto(MSGCD_SETTLEMENT_NO_OPEN_ERROR, null, true));
        }
    }

    // PDR Migrate Customization from here

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
    // #2200 START
    @Override
    protected void beforeProcess(ReceiveOrderDto receiveOrderDto,
                                 OrderMessageDto orderMessageDto,
                                 boolean calculateFlag,
                                 boolean commissionCalcFlag,
                                 boolean carriageCalcFlag,
                                 String memberInfoId,
                                 HTypeSiteType siteType,
                                 Integer memberInfoSeq,
                                 String adminId) {

        // PDR Migrate Customization from here
        // 配送方法エンティティを最新化する 削除
        // プロモーション連携等で設定した値が消えてしまうため。
        // PDR Migrate Customization to here

        // 決済方法エンティティを最新化する
        // billPriceCalculateLogic#settlementCommission で同様の処理を「条件付き」で実装しているが、
        // 注文チェック処理において、その条件で実行されることが無いため、当クラスにて実装を行う。
        // なお、決済方法エンティティを最新化した場合の billPriceCalculateLogic への影響はない。
        getNewSettlementMethod(receiveOrderDto);

        if (calculateFlag) {
            // 請求金額算出処理実行
            List<CheckMessageDto> calculateMsgList =
                            getBillPriceCalculateLogic().execute(receiveOrderDto, commissionCalcFlag, carriageCalcFlag,
                                                                 siteType, adminId
                                                                );
            if (calculateMsgList != null) {
                orderMessageDto.setOrderMessageList(calculateMsgList);
            }
        } else {
            // PDR Migrate Customization from here
            // 配送方法チェックを実行する ここでは行わないため削除
            // PDR Migrate Customization to here

            // 決済方法チェックを実行する
            // billPriceCalculateLogic#settlementCommission で同様の処理を実装しているが、
            // 以下の理由により当クラスにて実装を行う。
            // ・料金計算の影響範囲を考慮し、billPriceCalculateLogic への修正は行わない
            checkSettlementMethod(receiveOrderDto, orderMessageDto.getOrderMessageList(), siteType);
        }
    }

    /**
     * 配送ごとのチェックを行う<br/>
     *
     * @param receiveOrderDto   受注Dto
     * @param checkDto          チェック用Dto
     * @param deliveryDto       受注配送Dto
     * @param goodsDtailsDtoMap 商品詳細Dtoマップ(配送先に含まれている商品)
     * @param orderMessageDto   メッセージDto
     */
    @Override
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
            // PDR Migrate Customization from here
            /*---------- PDR#013 satoh(NSK) Customization Area is from here. ----------*/
            // 在庫チェック ここでは行わないため削除
            /*---------- PDR#013 satoh(NSK) Customization Area is to here. ----------*/
            // PDR Migrate Customization to here
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

    /**
     * 受注単位のチェックを行う<br/>
     *
     * @param receiveOrderDto 受注Dto
     * @param orderMessageDto メッセージDto
     */
    @Override
    protected void checkPerOrder(ReceiveOrderDto receiveOrderDto,
                                 OrderMessageDto orderMessageDto,
                                 HTypeSiteType siteType) {

        // 商品チェック前の準備
        OrderForCheckDto checkDto = prepareForGoodsCheck(receiveOrderDto, false);

        // 商品詳細マップマップ取得
        Map<Integer, Map<Integer, GoodsDetailsDto>> goodsDetailsMapMap =
                        getGoodsDetailsMapMap(receiveOrderDto.getOrderDeliveryDto(), checkDto);

        // 複数配送チェック
        checkMultipleDelivery(receiveOrderDto, orderMessageDto.getOrderMessageList());

        // 配送単位でチェック
        OrderDeliveryDto deliveryDto = receiveOrderDto.getOrderDeliveryDto();
        Integer orderConsecutiveNo = deliveryDto.getOrderDeliveryEntity().getOrderConsecutiveNo();

        // 商品チェック
        checkPerDelivery(receiveOrderDto, checkDto, deliveryDto, goodsDetailsMapMap.get(orderConsecutiveNo),
                         orderMessageDto
                        );

        // PDR Migrate Customization from here
        /*---------- PDR#013 satoh(NSK) Customization Area is from here. ----------*/
        // 配送方法整合性チェック お届け希望日に関するチェックは別の箇所で行うため削除
        /*---------- PDR#013 satoh(NSK) Customization Area is to here. ----------*/
        // PDR Migrate Customization to here

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
     * 公開状態チェック<br/>
     *
     * <pre>
     * 大まかな流れはPKG標準のソースを流用
     *
     * 心意気商品に関しては非公開状態で登録されるため
     * 「非公開かつ販売」の商品は購入可能にするよう処理を修正
     * </pre>
     *
     * @param goodsDetailsDto 商品詳細DTO
     * @param msgList         チェックメッセージDTOリスト
     */
    @Override
    protected void openStatusCheck(GoodsDetailsDto goodsDetailsDto, List<CheckMessageDto> msgList) {

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        HTypeOpenDeleteStatus openStatus = null;
        Timestamp openStartTime = null;
        Timestamp openEndTime = null;
        Timestamp currentTime = dateUtility.getCurrentTime();

        // 携帯サイトからの注文の場合
        //        HTypeSiteType siteType = this.getCommonInfo().getCommonInfoBase().getSiteType();
        // PKGTODO tezuka-mk
        // 公開状態（HTypeGoodsOpenStatus）かサイトタイプ（HTypeSiteType）条件分岐を吸収したい
        // 携帯以外はPCと見なす
        openStatus = goodsDetailsDto.getGoodsOpenStatusPC();
        openStartTime = goodsDetailsDto.getOpenStartTimePC();
        openEndTime = goodsDetailsDto.getOpenEndTimePC();

        // PKGTODO tezuka-mk
        // GoodsUtility#isGoodsOpenを改良すれば公開時間の比較ロジックは委譲出来る。パラメータにリスナを追加して、Util側でfireすればOK
        // 公開商品かどうか
        if (openStatus.equals(HTypeOpenDeleteStatus.OPEN)) {
            // 公開状態チェックOKの場合、公開期間をチェックする
            // 公開開始日時が現在日時を過ぎていない。
            if (openStartTime != null && currentTime.before(openStartTime)) {
                msgList.add(toCheckMessageDto(MSGCD_NO_OPEN_GOODS_EXIST, null, true));

                // 公開終了日時が現在日時を過ぎている。
            } else if (openEndTime != null && currentTime.after(openEndTime)) {
                msgList.add(toCheckMessageDto(MSGCD_NO_OPEN_GOODS_EXIST, null, true));
            }

            // PDR Migrate Customization from here
        } else if (!HTypeOpenDeleteStatus.NO_OPEN.equals(openStatus)) {
            // PDR Migrate Customization to here
            // 注文フローに入った後で非公開にされた
            msgList.add(toCheckMessageDto(MSGCD_NO_OPEN_GOODS_EXIST, null, true));
        }
    }

    // PDR Migrate Customization to here
}
