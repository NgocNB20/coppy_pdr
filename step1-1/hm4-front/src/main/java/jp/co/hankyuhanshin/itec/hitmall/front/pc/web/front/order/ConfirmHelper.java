/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAllocationDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeliverySlipFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReceiverTimeZoneJapanPost;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReceiverTimeZoneYamato;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.OrderMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.OrderTempDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDeliveryNowDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDependingOnReceiptGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderReserveDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.delivery.ReceiverDateDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.helper.order.OrderConversionHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.DeliveryNowItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.DependingOnReceiptItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.OrderCommonModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.ReserveDeliveryByPeriodItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.ReserveDeliveryItem;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PDR#013 09_データ連携（受注データ）<br/>
 * 注文内容確認画面Helper
 *
 * @author kaneda
 * @author satoh
 */
@Component
public class ConfirmHelper {

    /**
     * 受注関連Utility
     */
    private final OrderUtility orderUtility;

    // PDR Migrate Customization from here
    /**
     * GoodsUtility
     */
    private final GoodsUtility goodsUtility;

    /**
     * ConversionUtility
     */
    private final ConversionUtility conversionUtility;

    /**
     * DateUtility
     */
    private final DateUtility dateUtility;
    // PDR Migrate Customization to here

    /**
     * コンストラクタ
     *
     * @param orderUtility      受注関連Utility
     * @param goodsUtility      GoodsUtility
     * @param conversionUtility ConversionUtility
     * @param dateUtility       DateUtility
     */
    @Autowired
    public ConfirmHelper(OrderUtility orderUtility,
                         GoodsUtility goodsUtility,
                         ConversionUtility conversionUtility,
                         DateUtility dateUtility) {
        this.orderUtility = orderUtility;
        // PDR Migrate Customization from here
        this.goodsUtility = goodsUtility;
        this.conversionUtility = conversionUtility;
        this.dateUtility = dateUtility;
        // PDR Migrate Customization to here
    }

    // PDR Migrate Customization from here

    /**
     * エンティティへの変換処理。
     * Model ⇒ 受注配送エンティティ
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param memberInfoEntity 会員エンティティ
     * @return 受注配送エンティティ
     */
    public OrderDeliveryEntity toOrderDeliveryEntityForDeliveryMethod(OrderCommonModel orderCommonModel,
                                                                      MemberInfoEntity memberInfoEntity) {
        OrderDeliveryEntity orderDeliveryEntity =
                        orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto().getOrderDeliveryEntity();
        // 受注配送エンティティ が null だったら作成する。
        if (orderDeliveryEntity == null) {
            orderDeliveryEntity = ApplicationContextUtility.getBean(OrderDeliveryEntity.class);
        }

        OrderConversionHelper helper = ApplicationContextUtility.getBean(OrderConversionHelper.class);
        helper.toOrderDeliveryEntity(memberInfoEntity, orderDeliveryEntity);

        return orderDeliveryEntity;
    }

    /**
     * Modelへの変換<br/>
     * 受注Dtoリスト ⇒ Model<br/>
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param confirmModel 注文内容確認画面Model
     * @param model        model
     */
    public void toPageForLoad(OrderCommonModel orderCommonModel, ConfirmModel confirmModel, Model model) {

        // 受注受付番号単位で処理
        toPageForLoad(orderCommonModel, confirmModel, model, confirmModel.getDispReceiveOrderDto());

        // 注文番号単位で処理 期間別お届け情報を設定
        setupReceiverByPeriod(orderCommonModel, confirmModel, confirmModel.getReceiveOrderDtoList());

    }

    // PDR Migrate Customization to here

    /**
     * Modelへの変換<br/>
     * 受注Dto ⇒ Model<br/>
     * <pre>
     * 金額の設定 削除
     * ページアイテム作成処理を一新
     * </pre>
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param confirmModel    注文内容確認画面Model
     * @param model           model
     * @param receiveOrderDto 受注Dto
     */
    public void toPageForLoad(OrderCommonModel orderCommonModel,
                              ConfirmModel confirmModel,
                              Model model,
                              ReceiveOrderDto receiveOrderDto) {

        // PDR Migrate Customization from here
        // 各種金額・手数料の設定 使用しないため削除
        // PDR Migrate Customization to here

        // お客様情報を設定
        setupOrderPerson(orderCommonModel, confirmModel, receiveOrderDto);

        // PDR Migrate Customization from here
        // 注文メッセージDto取得
        // ページアイテム作成処理を一新した結果、戻り値が必要なくなったため
        setupOrderMessage(orderCommonModel, confirmModel, model);

        // 商品詳細情報MAP作成
        Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap = getGoodsDetailsDtoMap(orderCommonModel, confirmModel);

        // お届け先設定
        setupReceiver(orderCommonModel, confirmModel, receiveOrderDto);

        // 配送方法設定
        setupDelivery(orderCommonModel, confirmModel, goodsDetailsDtoMap);
        // PDR Migrate Customization to here

        // 決済方法設定
        setupSettlement(orderCommonModel, confirmModel, receiveOrderDto);

    }

    // PDR Migrate Customization from here

    /**
     * 期間別お届け先設定を行います。
     * <pre>
     * 今すぐ、取りおき、入荷次第情報への設定を行います。
     * </pre>
     *
     * @param orderCommonModel    注文フロー共通Model
     * @param confirmModel        注文内容確認画面Model
     * @param receiveOrderDtoList 受注Dto
     */
    protected void setupReceiverByPeriod(OrderCommonModel orderCommonModel,
                                         ConfirmModel confirmModel,
                                         List<ReceiveOrderDto> receiveOrderDtoList) {

        Map<Integer, Map<Integer, List<CheckMessageDto>>> orderGoodsMessageMapMap =
                        confirmModel.getOrderMessageDto().getOrderGoodsMessageMapMap();

        List<ReserveDeliveryByPeriodItem> reserveDeliveryByPeriodList = new ArrayList<>();
        List<DependingOnReceiptItem> dependingOnReceiptItemList = new ArrayList<>();

        for (ReceiveOrderDto receiveOrderDto : receiveOrderDtoList) {
            OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();
            Integer consecutiveNo = orderDeliveryDto.getOrderDeliveryEntity().getOrderConsecutiveNo();

            if (HTypeAllocationDeliveryType.DELIVER_NOW.equals(receiveOrderDto.getAllocationDeliveryType())) {
                // 今すぐお届け
                setupConfirmPageItemByDeliveryNow(orderCommonModel, confirmModel, receiveOrderDto,
                                                  orderGoodsMessageMapMap.get(consecutiveNo)
                                                 );
            } else if (HTypeAllocationDeliveryType.RESERVABLE.equals((receiveOrderDto).getAllocationDeliveryType())) {
                // 取りおき
                reserveDeliveryByPeriodList.add(setupConfirmPageItemByReserveDelivery(receiveOrderDto,
                                                                                      orderGoodsMessageMapMap.get(
                                                                                                      consecutiveNo)
                                                                                     ));
            } else {
                // 入荷次第
                setupConfirmPageItemByDependingOnReceiptItem(orderCommonModel, confirmModel, receiveOrderDto,
                                                             orderGoodsMessageMapMap.get(consecutiveNo),
                                                             dependingOnReceiptItemList
                                                            );
            }
        }
        // 期間別 取りおき情報設定
        confirmModel.setReserveDeliveryByPeriodItems(reserveDeliveryByPeriodList);
        confirmModel.setDependingOnReceiptItems(dependingOnReceiptItemList);
    }

    /**
     * 注文商品一覧から画面用のページアイテム等を設定<br/>
     * 今すぐお届け情報用<br/>
     *
     * @param orderCommonModel     注文フロー共通Model
     * @param confirmModel         注文内容確認画面Model
     * @param receiveOrderDto      受注Dto
     * @param orderGoodsMessageMap orderGoodsMessageMap
     */
    protected void setupConfirmPageItemByDeliveryNow(OrderCommonModel orderCommonModel,
                                                     ConfirmModel confirmModel,
                                                     ReceiveOrderDto receiveOrderDto,
                                                     Map<Integer, List<CheckMessageDto>> orderGoodsMessageMap) {

        List<DeliveryNowItem> deliveryNowItemList = new ArrayList<>();

        Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap = receiveOrderDto.getMasterDto().getGoodsMaster();

        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

        // 商品情報リスト
        for (OrderGoodsEntity orderGoodsEntity : orderDeliveryDto.getOrderGoodsEntityList()) {

            // 納品書印字フラグ:印字しない
            if (HTypeDeliverySlipFlag.OFF.equals(orderGoodsEntity.getDeliverySlipFlag())) {
                // スキップ
                continue;
            }

            deliveryNowItemList.add(createItemByDeliveryNow(orderGoodsEntity,
                                                            orderGoodsMessageMap.get(orderGoodsEntity.getGoodsSeq()),
                                                            goodsDetailsDtoMap
                                                           ));
        }
        confirmModel.setDeliveryNowItems(deliveryNowItemList);

        // 配送情報取得 詳細情報が存在する かつ お届け予定日が設定されている場合
        if (orderDeliveryDto.getDeliveryInformationDetailDto() != null
            && orderDeliveryDto.getOrderDeliveryEntity().getReceiverDate() != null) {

            // お届け予定日
            confirmModel.setDeliveryDateByDeliveryNow(dateUtility.formatYmdWithSlash(
                            orderDeliveryDto.getOrderDeliveryEntity().getReceiverDate()));

            // お届け予定日 曜日を設定
            // Timestampに変換
            confirmModel.setDeliveryDayOfTheWeekByDeliveryNow(ReceiverDateDto.getDayOfTheWeek(
                            orderDeliveryDto.getOrderDeliveryEntity().getReceiverDate()));

            // お届け時間帯
            if (HTypeDeliveryType.YAMATO.getValue()
                                        .equals(orderDeliveryDto.getDeliveryInformationDetailDto()
                                                                .getDeliveryCompanyCode())
                // 2023-renew No14 from here
                || HTypeDeliveryType.AUTOMATIC.getValue()
                                              .equals(orderDeliveryDto.getDeliveryInformationDetailDto()
                                                                      .getDeliveryCompanyCode())) {
                // ヤマトまたは自動設定の場合
                // 2023-renew No14 to here
                confirmModel.setReceiverTimeZoneByDeliveryNow(
                                EnumTypeUtil.getEnumFromValue(HTypeReceiverTimeZoneYamato.class,
                                                              orderDeliveryDto.getOrderDeliveryEntity()
                                                                              .getReceiverTimeZone()
                                                             ).getLabel());
            }
            if (HTypeDeliveryType.JAPANPOST.getValue()
                                           .equals(orderDeliveryDto.getDeliveryInformationDetailDto()
                                                                   .getDeliveryCompanyCode())) {
                // 日本郵便
                confirmModel.setReceiverTimeZoneByDeliveryNow(
                                EnumTypeUtil.getEnumFromValue(HTypeReceiverTimeZoneJapanPost.class,
                                                              orderDeliveryDto.getOrderDeliveryEntity()
                                                                              .getReceiverTimeZone()
                                                             ).getLabel());
            }
        }

        // 注文合計(税抜)
        confirmModel.setAllGoodsPriceOutTaxByDeliveryNow(
                        receiveOrderDto.getOrderSettlementEntity().getGoodsPriceTotal());

        // 値引き
        confirmModel.setGoodsDiscountPriceByDeliveryNow(orderDeliveryDto.getDiscountPrice());

        // 送料
        confirmModel.setCarriageByDeliveryNow(orderDeliveryDto.getOrderDeliveryEntity().getCarriage());

        // 消費税合計
        confirmModel.setTotalTaxByDeliveryNow(orderDeliveryDto.getTotalTax());

        // お支払い合計（税込）
        confirmModel.setTotalPriceInTaxByDeliveryNow(receiveOrderDto.getOrderSummaryEntity().getOrderPrice());

    }

    /**
     * 注文商品一覧から画面用のページアイテム等を設定<br/>
     * 今すぐお届け商品情報用
     *
     * @param orderGoodsEntity   受注商品エンティティ
     * @param msgDtoList         List&lt;CheckMessageDto&gt;
     * @param goodsDetailsDtoMap goodsDetailsDtoMap
     * @return 商品アイテム
     */
    protected DeliveryNowItem createItemByDeliveryNow(OrderGoodsEntity orderGoodsEntity,
                                                      List<CheckMessageDto> msgDtoList,
                                                      Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap) {
        DeliveryNowItem deliveryNowItem = ApplicationContextUtility.getBean(DeliveryNowItem.class);

        // 商品詳細DTOを取得
        GoodsDetailsDto goodsDetailsDto = goodsDetailsDtoMap.get(orderGoodsEntity.getGoodsSeq());

        // 商品SEQ
        deliveryNowItem.setGoodsSeq(orderGoodsEntity.getGoodsSeq());

        // 商品番号
        deliveryNowItem.setGoodsCode(
                        goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(orderGoodsEntity.getGoodsCode()));

        // 画像
        String imageFileName = goodsUtility.getImageFileName(goodsDetailsDto);
        String goodsImageSrc = goodsUtility.getGoodsImagePath(imageFileName);
        String goodsName = goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(orderGoodsEntity);
        deliveryNowItem.setGoodsImage(goodsImageSrc);
        deliveryNowItem.setGoodsImageSrc(goodsImageSrc);
        deliveryNowItem.setGoodsImageAlt(goodsName);

        // 商品表示名
        deliveryNowItem.setGoodsName(goodsName);

        // 規格1
        deliveryNowItem.setViewUnit1(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle1()));
        deliveryNowItem.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
        deliveryNowItem.setUnitValue1(orderGoodsEntity.getUnitValue1());

        // 規格2
        deliveryNowItem.setViewUnit2(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle2()));
        deliveryNowItem.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
        deliveryNowItem.setUnitValue2(orderGoodsEntity.getUnitValue2());

        // 注文数量
        BigDecimal orderGoodsCount = orderGoodsEntity.getGoodsCount();
        deliveryNowItem.setOrderGoodsCount(orderGoodsCount.toString());

        // 単価(税抜)
        BigDecimal goodsPrice = orderGoodsEntity.getGoodsPrice();
        deliveryNowItem.setGoodsPrice(goodsPrice);

        // 金額（税抜）
        deliveryNowItem.setGoodsPriceTotalOutTax(goodsPrice.multiply(orderGoodsCount));

        // 商品お知らせ情報
        deliveryNowItem.setGoodsInformation(orderUtility.getMessageString(msgDtoList));

        return deliveryNowItem;
    }

    /**
     * 注文商品一覧から画面用のページアイテム等を設定<br/>
     * 取りおき情報用<br/>
     *
     * @param receiveOrderDto      受注Dto
     * @param orderGoodsMessageMap orderGoodsMessageMap
     * @return 期間別 取りおき情報Item
     */
    protected ReserveDeliveryByPeriodItem setupConfirmPageItemByReserveDelivery(ReceiveOrderDto receiveOrderDto,
                                                                                Map<Integer, List<CheckMessageDto>> orderGoodsMessageMap) {
        ReserveDeliveryByPeriodItem reserveDeliveryByPeriodItem =
                        ApplicationContextUtility.getBean(ReserveDeliveryByPeriodItem.class);

        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

        Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap = receiveOrderDto.getMasterDto().getGoodsMaster();

        // お届け時期
        reserveDeliveryByPeriodItem.setDeliveryTimeByReserveDelivery(orderDeliveryDto.getDeliveryDate());

        List<ReserveDeliveryItem> reserveDeliveryListItemList = new ArrayList<>();

        // 商品情報リスト
        for (OrderGoodsEntity orderGoodsEntity : orderDeliveryDto.getOrderGoodsEntityList()) {

            // 納品書印字フラグ:印字しない
            if (HTypeDeliverySlipFlag.OFF.equals(orderGoodsEntity.getDeliverySlipFlag())) {
                // スキップ
                continue;
            }

            reserveDeliveryListItemList.add(createItemDetailByReserveDelivery(orderGoodsEntity,
                                                                              orderGoodsMessageMap.get(
                                                                                              orderGoodsEntity.getGoodsSeq()),
                                                                              goodsDetailsDtoMap
                                                                             ));
        }
        reserveDeliveryByPeriodItem.setReserveDeliveryItems(reserveDeliveryListItemList);

        // 注文合計(税抜)
        reserveDeliveryByPeriodItem.setAllGoodsPriceOutTaxByReserveDelivery(
                        receiveOrderDto.getOrderSettlementEntity().getGoodsPriceTotal());

        // 値引き
        reserveDeliveryByPeriodItem.setGoodsDiscountPriceByReserveDelivery(orderDeliveryDto.getDiscountPrice());

        // 送料
        reserveDeliveryByPeriodItem.setCarriageByReserveDelivery(
                        orderDeliveryDto.getOrderDeliveryEntity().getCarriage());

        // 消費税合計
        reserveDeliveryByPeriodItem.setTotalTaxByReserveDelivery(orderDeliveryDto.getTotalTax());

        // お支払い合計（税込）
        reserveDeliveryByPeriodItem.setTotalPriceInTaxByReserveDelivery(
                        receiveOrderDto.getOrderSummaryEntity().getOrderPrice());

        return reserveDeliveryByPeriodItem;
    }

    /**
     * 注文商品一覧から画面用のページアイテム等を設定<br/>
     * 取りおき詳細情報用
     *
     * @param orderGoodsEntity   受注商品エンティティ
     * @param msgDtoList         CheckMessageDtoクラスList
     * @param goodsDetailsDtoMap goodsDetailsDtoMap
     * @return 商品アイテム
     */
    protected ReserveDeliveryItem createItemDetailByReserveDelivery(OrderGoodsEntity orderGoodsEntity,
                                                                    List<CheckMessageDto> msgDtoList,
                                                                    Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap) {
        ReserveDeliveryItem reserveDeliveryItem = ApplicationContextUtility.getBean(ReserveDeliveryItem.class);

        // 商品詳細DTOを取得
        GoodsDetailsDto goodsDetailsDto = goodsDetailsDtoMap.get(orderGoodsEntity.getGoodsSeq());

        // 商品番号
        reserveDeliveryItem.setGoodsCode(
                        goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(orderGoodsEntity.getGoodsCode()));

        // 画像
        String imageFileName = goodsUtility.getImageFileName(goodsDetailsDto);
        String goodsImageSrc = goodsUtility.getGoodsImagePath(imageFileName);
        String goodsName = goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(orderGoodsEntity);
        reserveDeliveryItem.setGoodsImage(goodsImageSrc);
        reserveDeliveryItem.setGoodsImageSrc(goodsImageSrc);
        reserveDeliveryItem.setGoodsImageAlt(goodsName);

        // 商品表示名
        reserveDeliveryItem.setGoodsName(goodsName);

        // 規格1
        reserveDeliveryItem.setViewUnit1(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle1()));
        reserveDeliveryItem.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
        reserveDeliveryItem.setUnitValue1(orderGoodsEntity.getUnitValue1());

        // 規格2
        reserveDeliveryItem.setViewUnit2(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle2()));
        reserveDeliveryItem.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
        reserveDeliveryItem.setUnitValue2(orderGoodsEntity.getUnitValue2());

        // 注文数量
        BigDecimal orderGoodsCount = orderGoodsEntity.getGoodsCount();
        reserveDeliveryItem.setOrderGoodsCount(orderGoodsCount);

        // 単価(税抜)
        BigDecimal goodsPrice = orderGoodsEntity.getGoodsPrice();
        reserveDeliveryItem.setGoodsPrice(goodsPrice);

        // 金額（税抜）
        reserveDeliveryItem.setGoodsPriceTotalOutTax(goodsPrice.multiply(orderGoodsCount));

        // 商品お知らせ情報
        reserveDeliveryItem.setGoodsInformation(orderUtility.getMessageString(msgDtoList));

        // 2023-renew No14 from here
        // お届け希望日
        reserveDeliveryItem.setReserveDeliveryDate(orderGoodsEntity.getReserveDeliveryDate());
        // 2023-renew No14 to here

        return reserveDeliveryItem;
    }

    /**
     * 注文商品一覧から画面用のページアイテム等を設定<br/>
     * 入荷次第お届け用<br/>
     *
     * @param orderCommonModel           注文フロー共通Model
     * @param confirmModel               注文内容確認画面Model
     * @param receiveOrderDto            受注Dto
     * @param orderGoodsMessageMap       orderGoodsMessageMap
     * @param dependingOnReceiptItemList 入荷次第お届けリスト
     */
    protected void setupConfirmPageItemByDependingOnReceiptItem(OrderCommonModel orderCommonModel,
                                                                ConfirmModel confirmModel,
                                                                ReceiveOrderDto receiveOrderDto,
                                                                Map<Integer, List<CheckMessageDto>> orderGoodsMessageMap,
                                                                List<DependingOnReceiptItem> dependingOnReceiptItemList) {
        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

        Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap = receiveOrderDto.getMasterDto().getGoodsMaster();

        // 商品情報リスト
        for (OrderGoodsEntity orderGoodsEntity : orderDeliveryDto.getOrderGoodsEntityList()) {

            // 納品書印字フラグ:印字しない
            if (HTypeDeliverySlipFlag.OFF.equals(orderGoodsEntity.getDeliverySlipFlag())) {
                // スキップ
                continue;
            }

            dependingOnReceiptItemList.add(createItemByDependingOnReceipt(orderGoodsEntity, orderGoodsMessageMap.get(
                            orderGoodsEntity.getGoodsSeq()), goodsDetailsDtoMap));
        }

        // 注文合計(税抜)
        if (confirmModel.getAllGoodsPriceOutTaxByDependingOnReceipt() != null) {
            confirmModel.setAllGoodsPriceOutTaxByDependingOnReceipt(
                            confirmModel.getAllGoodsPriceOutTaxByDependingOnReceipt()
                                        .add(receiveOrderDto.getOrderSettlementEntity().getGoodsPriceTotal()));
        } else {
            confirmModel.setAllGoodsPriceOutTaxByDependingOnReceipt(
                            receiveOrderDto.getOrderSettlementEntity().getGoodsPriceTotal());
        }

        // 値引き
        if (confirmModel.getGoodsDiscountPriceByDependingOnReceipt() != null) {
            confirmModel.setGoodsDiscountPriceByDependingOnReceipt(
                            confirmModel.getGoodsDiscountPriceByDependingOnReceipt()
                                        .add(orderDeliveryDto.getDiscountPrice()));
        } else {
            confirmModel.setGoodsDiscountPriceByDependingOnReceipt(orderDeliveryDto.getDiscountPrice());
        }

        // 送料
        if (confirmModel.getCarriageByDependingOnReceipt() != null) {
            confirmModel.setCarriageByDependingOnReceipt(confirmModel.getCarriageByDependingOnReceipt()
                                                                     .add(orderDeliveryDto.getOrderDeliveryEntity()
                                                                                          .getCarriage()));
        } else {
            confirmModel.setCarriageByDependingOnReceipt(orderDeliveryDto.getOrderDeliveryEntity().getCarriage());
        }

        // 消費税合計
        if (confirmModel.getTotalTaxByDependingOnReceipt() != null) {
            confirmModel.setTotalTaxByDependingOnReceipt(
                            confirmModel.getTotalTaxByDependingOnReceipt().add(orderDeliveryDto.getTotalTax()));
        } else {
            confirmModel.setTotalTaxByDependingOnReceipt(orderDeliveryDto.getTotalTax());
        }

        // お支払い合計（税込）
        if (confirmModel.getTotalPriceInTaxByDependingOnReceipt() != null) {
            confirmModel.setTotalPriceInTaxByDependingOnReceipt(confirmModel.getTotalPriceInTaxByDependingOnReceipt()
                                                                            .add(receiveOrderDto.getOrderSummaryEntity()
                                                                                                .getOrderPrice()));
        } else {
            confirmModel.setTotalPriceInTaxByDependingOnReceipt(
                            receiveOrderDto.getOrderSummaryEntity().getOrderPrice());
        }
    }

    /**
     * 注文商品一覧から画面用のページアイテム等を設定<br/>
     * 入荷次第お届け商品情報用
     *
     * @param orderGoodsEntity   受注商品エンティティ
     * @param msgDtoList         CheckMessageDtoクラスList
     * @param goodsDetailsDtoMap goodsDetailsDtoMap
     * @return 商品アイテム
     */
    protected DependingOnReceiptItem createItemByDependingOnReceipt(OrderGoodsEntity orderGoodsEntity,
                                                                    List<CheckMessageDto> msgDtoList,
                                                                    Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap) {
        DependingOnReceiptItem dependingOnReceiptItem = ApplicationContextUtility.getBean(DependingOnReceiptItem.class);

        // 商品詳細DTOを取得
        GoodsDetailsDto goodsDetailsDto = goodsDetailsDtoMap.get(orderGoodsEntity.getGoodsSeq());

        // 商品SEQ
        dependingOnReceiptItem.setGoodsSeq(orderGoodsEntity.getGoodsSeq());

        // 商品番号
        dependingOnReceiptItem.setGoodsCode(
                        goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(orderGoodsEntity.getGoodsCode()));

        // 画像
        String imageFileName = goodsUtility.getImageFileName(goodsDetailsDto);
        String goodsImageSrc = goodsUtility.getGoodsImagePath(imageFileName);
        String goodsName = goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(orderGoodsEntity);
        dependingOnReceiptItem.setGoodsImage(goodsImageSrc);
        dependingOnReceiptItem.setGoodsImageSrc(goodsImageSrc);
        dependingOnReceiptItem.setGoodsImageAlt(goodsName);

        // 商品表示名
        dependingOnReceiptItem.setGoodsName(goodsName);

        // 規格1
        dependingOnReceiptItem.setViewUnit1(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle1()));
        dependingOnReceiptItem.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
        dependingOnReceiptItem.setUnitValue1(orderGoodsEntity.getUnitValue1());

        // 規格2
        dependingOnReceiptItem.setViewUnit2(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle2()));
        dependingOnReceiptItem.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
        dependingOnReceiptItem.setUnitValue2(orderGoodsEntity.getUnitValue2());

        // 注文数量
        BigDecimal orderGoodsCount = orderGoodsEntity.getGoodsCount();
        dependingOnReceiptItem.setOrderGoodsCount(orderGoodsCount);

        // 単価(税抜)
        BigDecimal goodsPrice = orderGoodsEntity.getGoodsPrice();
        dependingOnReceiptItem.setGoodsPrice(goodsPrice);

        // 金額（税抜）
        dependingOnReceiptItem.setGoodsPriceTotalOutTax(goodsPrice.multiply(orderGoodsCount));

        // 商品お知らせ情報
        dependingOnReceiptItem.setGoodsInformation(orderUtility.getMessageString(msgDtoList));

        return dependingOnReceiptItem;
    }

    // PDR Migrate Customization to here

    /**
     * 決済方法設定
     * <pre>
     * 今回表示しない項目を削除し
     * クレジットカード情報で必要な項目を追加
     * </pre>
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param confirmModel    注文内容確認画面Model
     * @param receiveOrderDto 受注Dto
     */
    protected void setupSettlement(OrderCommonModel orderCommonModel,
                                   ConfirmModel confirmModel,
                                   ReceiveOrderDto receiveOrderDto) {

        // 2023-renew No14 from here
        if (ObjectUtils.isEmpty(receiveOrderDto.getSettlementMethodEntity())) {
            // 決済方法が未設定の場合はスキップ
            return;
        }

        // 決済方法SEQ
        confirmModel.setSettlementMethodSeq(receiveOrderDto.getSettlementMethodEntity().getSettlementMethodSeq());
        // 2023-renew No14 to here

        // 決済方法
        confirmModel.setSettlementMethodName(receiveOrderDto.getSettlementMethodEntity().getSettlementMethodName());
        // 決済方法表示名
        confirmModel.setSettlementMethodDisplayName(
                        receiveOrderDto.getSettlementMethodEntity().getSettlementMethodDisplayNamePC());

        // クレジット
        if (confirmModel.isCreditType()) {
            OrderTempDto orderTempDto = receiveOrderDto.getOrderTempDto();
            // PDR Migrate Customization from here
            // カード番号
            confirmModel.setCardNo(orderTempDto.getCardNo());
            // カード会社
            confirmModel.setCardBrand(orderTempDto.getCardBrand());
            // 有効期限（年）
            confirmModel.setExpirationDateYear(orderTempDto.getExpireYear());
            // 有効期限（月）
            confirmModel.setExpirationDateMonth(orderTempDto.getExpireMonth());
            // 支払い区分 (一括固定)
            confirmModel.setPaymentType(orderTempDto.getMethod());
            // 分割回数 表示しないため削除
            // セキュリティコード 表示しないため削除
            // PDR Migrate Customization to here
        }

        // PDR Migrate Customization from here
        // コンビニ名 表示しないため削除
        // PDR Migrate Customization to here
    }

    /**
     * 配送方法設定
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param confirmModel       注文内容確認画面Model
     * @param goodsDetailsDtoMap 商品詳細情報MAP
     */
    protected void setupDelivery(OrderCommonModel orderCommonModel,
                                 ConfirmModel confirmModel,
                                 Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap) {
        // PDR Migrate Customization from here
        OrderDeliveryDto orderDeliveryDto = confirmModel.getDispReceiveOrderDto().getOrderDeliveryDto();
        // 今すぐお届け
        setDeliveryMethodDeliveryNowItems(orderCommonModel, confirmModel, orderDeliveryDto, goodsDetailsDtoMap);

        // 取りおき
        setDeliveryMethodReserveDeliveryItems(orderCommonModel, confirmModel, orderDeliveryDto, goodsDetailsDtoMap);

        // 入荷次第お届け
        setDeliveryMethodDependingOnReceiptItems(orderCommonModel, confirmModel, orderDeliveryDto, goodsDetailsDtoMap);
        // PDR Migrate Customization to here
    }

    // PDR Migrate Customization from here

    /**
     * 配送法方法:今すぐお届けを設定
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param confirmModel       注文内容確認画面Model
     * @param orderDeliveryDto   受注配送Dto
     * @param goodsDetailsDtoMap 商品詳細情報MAP
     */
    public void setDeliveryMethodDeliveryNowItems(OrderCommonModel orderCommonModel,
                                                  ConfirmModel confirmModel,
                                                  OrderDeliveryDto orderDeliveryDto,
                                                  Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap) {

        List<OrderDeliveryNowDto> orderDeliveryNowDtoList = orderDeliveryDto.getOrderDeliveryNowDtoList();
        // 今すぐお届けなしの場合
        if (orderDeliveryNowDtoList == null || orderDeliveryNowDtoList.isEmpty()) {
            return;
        }

        List<DeliveryNowItem> deliveryNowItemList = new ArrayList<>();
        for (OrderDeliveryNowDto orderDeliveryNowDto : orderDeliveryNowDtoList) {
            DeliveryNowItem deliveryNowItem = ApplicationContextUtility.getBean(DeliveryNowItem.class);
            OrderGoodsEntity orderGoodsEntity = orderDeliveryNowDto.getOrderGoodsEntity();

            // 商品詳細情報
            GoodsDetailsDto goodsDetailsDto = goodsDetailsDtoMap.get(orderGoodsEntity.getGoodsSeq());

            // 商品SEQ
            deliveryNowItem.setGoodsSeq(orderGoodsEntity.getGoodsSeq());

            // 商品番号
            deliveryNowItem.setGoodsCode(orderGoodsEntity.getGoodsCode());

            // 商品表示名
            deliveryNowItem.setGoodsName(goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(orderGoodsEntity));

            // 規格1
            deliveryNowItem.setViewUnit1(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle1()));
            deliveryNowItem.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
            deliveryNowItem.setUnitValue1(orderGoodsEntity.getUnitValue1());

            // 規格2
            deliveryNowItem.setViewUnit2(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle2()));
            deliveryNowItem.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
            deliveryNowItem.setUnitValue2(orderGoodsEntity.getUnitValue2());

            deliveryNowItemList.add(deliveryNowItem);
        }
        confirmModel.setDeliveryMethodDeliveryNowItems(deliveryNowItemList);
    }

    /**
     * 配送方法:取りおきを設定
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param confirmModel       注文内容確認画面Model
     * @param orderDeliveryDto   受注配送Dto
     * @param goodsDetailsDtoMap 商品詳細情報MAP
     */
    public void setDeliveryMethodReserveDeliveryItems(OrderCommonModel orderCommonModel,
                                                      ConfirmModel confirmModel,
                                                      OrderDeliveryDto orderDeliveryDto,
                                                      Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap) {

        List<OrderReserveDeliveryDto> orderReserveDeliveryDtoList = orderDeliveryDto.getOrderReserveDeliveryDtoList();
        // 取りおきなし
        if (orderReserveDeliveryDtoList == null || orderReserveDeliveryDtoList.isEmpty()) {
            return;
        }

        List<ReserveDeliveryItem> reserveDeliveryItemList = new ArrayList<>();
        for (OrderReserveDeliveryDto orderReserveDeliveryDto : orderReserveDeliveryDtoList) {
            ReserveDeliveryItem reserveDeliveryItem = ApplicationContextUtility.getBean(ReserveDeliveryItem.class);

            // 商品詳細情報
            OrderGoodsEntity orderGoodsEntity = orderReserveDeliveryDto.getOrderGoodsEntity();
            GoodsDetailsDto goodsDetailsDto = goodsDetailsDtoMap.get(orderGoodsEntity.getGoodsSeq());

            // 商品番号
            reserveDeliveryItem.setGoodsCode(orderGoodsEntity.getGoodsCode());

            // 商品表示名
            reserveDeliveryItem.setGoodsName(
                            goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(orderGoodsEntity));

            // 規格1
            reserveDeliveryItem.setViewUnit1(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle1()));
            reserveDeliveryItem.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
            reserveDeliveryItem.setUnitValue1(orderGoodsEntity.getUnitValue1());

            // 規格2
            reserveDeliveryItem.setViewUnit2(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle2()));
            reserveDeliveryItem.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
            reserveDeliveryItem.setUnitValue2(orderGoodsEntity.getUnitValue2());

            // 単位
            reserveDeliveryItem.setUnit(goodsDetailsDto.getUnit());

            reserveDeliveryItemList.add(reserveDeliveryItem);
        }
        confirmModel.setDeliveryMethodReserveDeliveryItems(reserveDeliveryItemList);
    }

    /**
     * 配送法方法:入荷次第お届けを設定
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param confirmModel       注文内容確認画面Model
     * @param orderDeliveryDto   受注配送Dto
     * @param goodsDetailsDtoMap 商品詳細情報MAP
     */
    public void setDeliveryMethodDependingOnReceiptItems(OrderCommonModel orderCommonModel,
                                                         ConfirmModel confirmModel,
                                                         OrderDeliveryDto orderDeliveryDto,
                                                         Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap) {

        List<OrderDependingOnReceiptGoodsDto> orderDependingOnReceiptGoodsDtoList =
                        orderDeliveryDto.getOrderDependingOnReceiptGoodsDtoList();

        // 入荷次第お届けなし
        if (orderDependingOnReceiptGoodsDtoList == null || orderDependingOnReceiptGoodsDtoList.isEmpty()) {
            return;
        }
        List<DependingOnReceiptItem> dependingOnReceiptItemList = new ArrayList<>();

        for (OrderDependingOnReceiptGoodsDto orderDependingOnReceiptGoodsDto : orderDependingOnReceiptGoodsDtoList) {
            DependingOnReceiptItem dependingOnReceiptItem =
                            ApplicationContextUtility.getBean(DependingOnReceiptItem.class);
            OrderGoodsEntity orderGoodsEntity = orderDependingOnReceiptGoodsDto.getOrderGoodsEntity();

            // 商品詳細情報
            GoodsDetailsDto goodsDetailsDto = goodsDetailsDtoMap.get(orderGoodsEntity.getGoodsSeq());

            // 商品SEQ
            dependingOnReceiptItem.setGoodsSeq(orderGoodsEntity.getGoodsSeq());

            // 商品番号
            dependingOnReceiptItem.setGoodsCode(orderGoodsEntity.getGoodsCode());

            // 商品表示名
            dependingOnReceiptItem.setGoodsName(
                            goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(orderGoodsEntity));

            // 規格1
            dependingOnReceiptItem.setViewUnit1(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle1()));
            dependingOnReceiptItem.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
            dependingOnReceiptItem.setUnitValue1(orderGoodsEntity.getUnitValue1());

            // 規格2
            dependingOnReceiptItem.setViewUnit2(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle2()));
            dependingOnReceiptItem.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
            dependingOnReceiptItem.setUnitValue2(orderGoodsEntity.getUnitValue2());

            dependingOnReceiptItemList.add(dependingOnReceiptItem);
        }
        confirmModel.setDeliveryMethodDependingOnReceiptItems(dependingOnReceiptItemList);
    }

    // PDR Migrate Customization to here

    /**
     * お届け先設定
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param confirmModel    注文内容確認画面Model
     * @param receiveOrderDto 受注Dto
     */
    protected void setupReceiver(OrderCommonModel orderCommonModel,
                                 ConfirmModel confirmModel,
                                 ReceiveOrderDto receiveOrderDto) {

        OrderDeliveryEntity orderDeliveryEntity = receiveOrderDto.getOrderDeliveryDto().getOrderDeliveryEntity();

        // PDR Migrate Customization from here
        // お届け先
        confirmModel.setReceiverHomeFlag(
                        orderUtility.isSameAddress(orderCommonModel.getReceiveOrderDto().getOrderPersonEntity(),
                                                   orderDeliveryEntity
                                                  ));
        confirmModel.setReceiverLastName(orderDeliveryEntity.getReceiverLastName());
        confirmModel.setReceiverFirstName(orderDeliveryEntity.getReceiverFirstName());
        confirmModel.setReceiverLastKana(orderDeliveryEntity.getReceiverLastKana());
        confirmModel.setReceiverTel(orderDeliveryEntity.getReceiverTel());

        String[] zipCodes = conversionUtility.toZipCodeArray(orderDeliveryEntity.getReceiverZipCode());
        confirmModel.setReceiverZipCode1(zipCodes[0]);
        confirmModel.setReceiverZipCode2(zipCodes[1]);

        confirmModel.setReceiverAddress1(orderDeliveryEntity.getReceiverAddress1());
        confirmModel.setReceiverAddress2(orderDeliveryEntity.getReceiverAddress2());
        confirmModel.setReceiverAddress3(conversionUtility.toSpaceConnect(orderDeliveryEntity.getReceiverAddress3(),
                                                                          orderDeliveryEntity.getReceiverAddress4()
                                                                         ));
        // PDR Migrate Customization to here
    }

    /**
     * お客様情報を設定
     * <pre>
     * 必要のない項目を削除
     * </pre>
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param confirmModel    注文内容確認画面Model
     * @param receiveOrderDto 受注Dto
     */
    protected void setupOrderPerson(OrderCommonModel orderCommonModel,
                                    ConfirmModel confirmModel,
                                    ReceiveOrderDto receiveOrderDto) {

        OrderPersonEntity orderPersonEntity = receiveOrderDto.getOrderPersonEntity();
        // PDR Migrate Customization from here
        // 事業所名のみに変更
        confirmModel.setSenderName(orderPersonEntity.getOrderLastName());
        // フリガナ 削除
        // PDR Migrate Customization to here
        confirmModel.setSenderMail(orderPersonEntity.getOrderMail());
        confirmModel.setSenderTel(orderPersonEntity.getOrderTel());

        // PDR Migrate Customization from here
        // 郵便番号分割
        String[] zipCodes = conversionUtility.toZipCodeArray(orderPersonEntity.getOrderZipCode());
        confirmModel.setSenderZipCode1(zipCodes[0]);
        confirmModel.setSenderZipCode2(zipCodes[1]);

        // 住所作成（都道府県は削除）
        StringBuilder addressBuffer = new StringBuilder(orderPersonEntity.getOrderAddress1());
        // PDR Migrate Customization to here
        addressBuffer.append(orderPersonEntity.getOrderAddress2());
        if (orderPersonEntity.getOrderAddress3() != null) {
            addressBuffer.append(orderPersonEntity.getOrderAddress3());
        }

        confirmModel.setSenderAddress(addressBuffer.toString());

    }

    /**
     * 商品詳細情報MAP作成
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param confirmModel 注文内容確認画面Model
     * @return 商品詳細情報MAP
     */
    protected Map<Integer, GoodsDetailsDto> getGoodsDetailsDtoMap(OrderCommonModel orderCommonModel,
                                                                  ConfirmModel confirmModel) {
        // 商品詳細情報MAP作成
        Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap = new HashMap<>();
        for (GoodsDetailsDto goodsDetailsDto : confirmModel.getGoodsDetailDtoList()) {
            goodsDetailsDtoMap.put(goodsDetailsDto.getGoodsSeq(), goodsDetailsDto);
        }
        return goodsDetailsDtoMap;
    }

    /**
     * 注文メッセージDto取得
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param confirmModel 注文内容確認画面Model
     * @param model        model
     * @return 注文メッセージDto
     */
    protected Map<Integer, List<CheckMessageDto>> setupOrderMessage(OrderCommonModel orderCommonModel,
                                                                    ConfirmModel confirmModel,
                                                                    Model model) {

        OrderMessageDto orderMessageDto = confirmModel.getOrderMessageDto();

        if (orderMessageDto != null && !CollectionUtils.isEmpty(orderMessageDto.getOrderMessageList())) {
            List<String> orderMessages = new ArrayList<>();
            for (CheckMessageDto messageDto : orderMessageDto.getOrderMessageList()) {
                orderMessages.add(messageDto.getMessage());
            }
            model.addAttribute("orderMessages", orderMessages);
        }

        return orderMessageDto.getOrderGoodsMessageMap();
    }

}
