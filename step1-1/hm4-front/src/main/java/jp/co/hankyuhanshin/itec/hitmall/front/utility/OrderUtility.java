/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.utility;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAllocationDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDeliveryNowDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDependingOnReceiptGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderReserveDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetConsumptionTaxRateRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetConsumptionTaxRateResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.conveni.ConvenienceEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.multipayment.CardBrandEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.settlement.SettlementMethodEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 受注業務ユーティリティクラス
 *
 * @author negishi
 * @author Kaneko (itec) 2012/08/20 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class OrderUtility {

    // PDR Migrate Customization from here
    /**
     * ダミー配送方法SEQ
     */
    public static final String DUMMY_DELIVERY_METHOD_SEQ = "1001";

    /**
     * クレジットカードの有効期限（年）を何年分出すかをシステムプロパティから取得するキー
     */
    protected static final String EXPIRATION_DATE_YEAR = "expiration.date.year";
    
    /**
     * 無料決済SEQ（設定値）
     */
    protected static final String FREE_SETTLEMENT_METHOD_SEQ = "free.settlement.method.seq";

    /**
     * 消費税率が8%
     */
    protected static final double TAXRATE08 = 0.08;
    /**
     * 消費税率が10%
     */
    protected static final double TAXRATE10 = 0.1;

    /**
     * 変換Utility
     */
    private final ConversionUtility conversionUtility;

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    /**
     * 商品系ヘルパークラス
     */
    private final GoodsUtility goodsUtility;

    @Autowired
    public OrderUtility(ConversionUtility conversionUtility, DateUtility dateUtility, GoodsUtility goodsUtility) {
        this.conversionUtility = conversionUtility;
        this.dateUtility = dateUtility;
        this.goodsUtility = goodsUtility;
    }

    // PDR Migrate Customization to here

    /**
     * カード入力情報の有効期限（年）プルダウンを作成する
     *
     * @return 有効期限（年）プルダウン
     */
    public Map<String, String> createExpirationDateYearItems() {
        Map<String, String> expirationDateYearMap = new LinkedHashMap<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        // システムプロパティから何年分表示するかを取得。
        int years = Integer.parseInt(PropertiesUtil.getSystemPropertiesValue(EXPIRATION_DATE_YEAR));

        for (int i = currentYear; i < currentYear + years; i++) {
            expirationDateYearMap.put(Integer.toString(i).substring(2), Integer.toString(i));
        }

        return expirationDateYearMap;
    }

    // 2023-renew No52 from here

    /**
     * 4文字列型の年から2文字列のキーを作成
     * @param yearValue　4文字列年
     * @return 2文字列年
     */
    public String toKeyOfYear(int yearValue) {
        return Integer.toString(yearValue).substring(2);
    }
    // 2023-renew No52 to here

    /**
     * コンビニプルダウン取得
     *
     * @param convenienceEntityList コンビニ
     * @return コンビニプルダウン
     */
    public Map<String, String> createConvenienceItems(List<ConvenienceEntity> convenienceEntityList) {
        Map<String, String> convenienceMap = new LinkedHashMap<>();
        for (ConvenienceEntity convenience : convenienceEntityList) {
            convenienceMap.put(convenience.getConveniSeq().toString(), convenience.getConveniName());
        }
        return convenienceMap;
    }

    /**
     * 受注Dtoにセットされている全商品の、商品SEQリストを取得
     *
     * @param receiveOrderDto 受注Dto
     * @return 商品Seqリスト
     */
    public List<Integer> getALLGoodsSeqList(ReceiveOrderDto receiveOrderDto) {

        // 商品SEQのセットを初期化
        Set<Integer> goodsSeqSet = new LinkedHashSet<>();

        for (OrderGoodsEntity orderGoodsEntity : receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList()) {
            goodsSeqSet.add(orderGoodsEntity.getGoodsSeq());
        }

        // 商品がない場合
        if (goodsSeqSet.isEmpty()) {
            return null;
        }
        return new ArrayList<>(goodsSeqSet);
    }

    /**
     * 受注Dtoにセットされている全商品の、商品エンティティリストを取得
     *
     * @param receiveOrderDto 受注Dto
     * @return 商品エンティティリスト
     */
    public List<OrderGoodsEntity> getALLGoodsEntityList(ReceiveOrderDto receiveOrderDto) {

        List<OrderGoodsEntity> orderGoodsEntityList = new ArrayList<>();
        if (receiveOrderDto.getOrderDeliveryDto() == null) {
            return orderGoodsEntityList;
        }

        if (CollectionUtil.isEmpty(receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList())) {
            return orderGoodsEntityList;
        }
        for (OrderGoodsEntity orderGoodsEntity : receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList()) {
            orderGoodsEntityList.add(orderGoodsEntity);
        }

        return orderGoodsEntityList;
    }

    // PDR Migrate Customization from here

    /**
     * 注文主の住所とお届け先の住所が同じか判定する<br/>
     * <pre>完全一致</pre>
     *
     * @param personEntity   受注ご注文主Entity
     * @param deliveryEntity 受注配送Entity
     * @return true:同じ false:違う
     */
    public boolean isSameAddress(OrderPersonEntity personEntity, OrderDeliveryEntity deliveryEntity) {
        // 注文主住所
        String orderAddress = personEntity.getOrderPrefecture() + personEntity.getOrderAddress1()
                              + personEntity.getOrderAddress2() + personEntity.getOrderAddress3();
        // お届け先住所
        String receiverAddress = deliveryEntity.getReceiverPrefecture() + deliveryEntity.getReceiverAddress1()
                                 + deliveryEntity.getReceiverAddress2() + deliveryEntity.getReceiverAddress3();

        return orderAddress.equals(receiverAddress);
    }

    // PDR Migrate Customization to here

    /**
     * メッセージDtoリストから出力するメッセージ文字列を取得する。<br/>
     * 空リスト、またnull を指定した場合、null を返す
     *
     * @param msgDtoList メッセージDtoリスト
     * @return 文字列
     */
    public String getMessageString(List<CheckMessageDto> msgDtoList) {
        if (CollectionUtil.isEmpty(msgDtoList)) {
            return null;
        }
        List<String> msgList = new ArrayList<>();
        for (CheckMessageDto msgDto : msgDtoList) {
            msgList.add(msgDto.getMessage());
        }

        return conversionUtility.toUnitStr(msgList, ConversionUtility.DIV_CHAR_BR);
    }

    // PDR Migrate Customization from here

    /**
     * 無料配送の決済方法 SEQを取得する。
     *
     * @return 無料決済方法SEQ
     */
    public Integer getFreeSettlementMethodSeq() {
        return PropertiesUtil.getSystemPropertiesValueToInt(FREE_SETTLEMENT_METHOD_SEQ);
    }

    // PDR Migrate Customization to here

    /**
     * 配送追跡URLの取得
     *
     * @param deliveryMethodEntity 配送方法
     * @param orderDeliveryEntity  受注配送
     * @return 配送追跡URL
     */
    public String getDeliveryChaseURL(DeliveryMethodEntity deliveryMethodEntity,
                                      OrderDeliveryEntity orderDeliveryEntity) {

        // 配送追跡URLの設定が無い場合は無し
        if (StringUtil.isEmpty(deliveryMethodEntity.getDeliveryChaseURL())) {
            return "";
        }

        // 出荷日が無い場合は無し
        if (orderDeliveryEntity.getShipmentDate() == null) {
            return "";
        }

        // 伝票番号が無い場合は無し
        if (StringUtil.isEmpty(orderDeliveryEntity.getDeliveryCode())) {
            return "";
        }

        // 配送追跡URLの表示期間が無い場合は無し
        if (deliveryMethodEntity.getDeliveryChaseURLDisplayPeriod() == null) {
            return "";
        }

        // 出荷日が未来日なら無し
        if (dateUtility.isAfterCurrentTime(orderDeliveryEntity.getShipmentDate())) {
            return "";
        }

        // 配送追跡URLの表示期間が、期間内、または、無期限ならURLを返す
        int deliveryChaseURLDisplayPeriod =
                        conversionUtility.toInteger(deliveryMethodEntity.getDeliveryChaseURLDisplayPeriod());
        Timestamp targetDay = dateUtility.getAmountDayTimestamp(deliveryChaseURLDisplayPeriod, true,
                                                                orderDeliveryEntity.getShipmentDate()
                                                               );
        if (deliveryChaseURLDisplayPeriod == 0 || dateUtility.isAfterCurrentTime(targetDay)) {
            return MessageFormat.format(
                            deliveryMethodEntity.getDeliveryChaseURL(), orderDeliveryEntity.getDeliveryCode());

        }
        return "";
    }

    /**
     * カード会社プルダウンを作成します。
     *
     * @param cardBrandEntityList カードブランドEntityリスト
     * @return カード会社プルダウン
     */
    public Map<String, String> createCardBrandItemList(List<CardBrandEntity> cardBrandEntityList) {

        Map<String, String> cardBranbMap = new LinkedHashMap<>();

        for (CardBrandEntity cardBrandEntity : cardBrandEntityList) {

            cardBranbMap.put(cardBrandEntity.getCardBrandDisplayPc(), cardBrandEntity.getCardBrandDisplayPc());
        }
        return cardBranbMap;
    }

    // PDR Migrate Customization from here

    /**
     * 受注DTOを注文時期ごとに分割します。<br/>
     * <pre>
     * 前提
     * ・今すぐ 取りおき 入荷次第 全て振り分け済みであること。
     * </pre>
     *
     * @param receiveOrderDto 受注DTO
     * @return 注文時期ごとに分割された受注DTOリスト
     */
    public List<ReceiveOrderDto> createSplitReceiveOrderDto(ReceiveOrderDto receiveOrderDto) {

        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();
        StockUtility stockUtility = ApplicationContextUtility.getBean(StockUtility.class);

        // 今すぐお届け分
        List<OrderDeliveryNowDto> orderDeliveryNowDtoList =
                        stockUtility.copyDeliveryNowDtoList(orderDeliveryDto.getOrderDeliveryNowDtoList());

        // 取りおき分
        List<OrderReserveDeliveryDto> reserveDeliveryDtoList =
                        stockUtility.copyReserveDeliveryDtoList(orderDeliveryDto.getOrderReserveDeliveryDtoList());

        // 入荷次第お届け分
        List<OrderDependingOnReceiptGoodsDto> dependingOnReceiptGoodsDtoList =
                        stockUtility.copyDependingOnReceiptGoodsDtoList(
                                        orderDeliveryDto.getOrderDependingOnReceiptGoodsDtoList());

        // リストの中身を一旦削除
        orderDeliveryDto.getOrderDeliveryNowDtoList().clear();
        orderDeliveryDto.getOrderReserveDeliveryDtoList().clear();
        orderDeliveryDto.getOrderDependingOnReceiptGoodsDtoList().clear();

        List<ReceiveOrderDto> resReceiveOrderDtoList = new ArrayList<>();

        // 今すぐお届け分が存在する場合
        if (orderDeliveryNowDtoList != null && !orderDeliveryNowDtoList.isEmpty()) {

            // 今すぐお届け用の受注DTO作成
            ReceiveOrderDto deliveryNowReceiveReceiveOrderDto = CopyUtil.deepCopy(receiveOrderDto);

            // 受注商品情報一旦クリア
            List<OrderGoodsEntity> orderGoodsEntityList =
                            deliveryNowReceiveReceiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList();
            orderGoodsEntityList.clear();

            // 受注商品情報を置き換え
            for (OrderDeliveryNowDto orderDeliveryNowDto : orderDeliveryNowDtoList) {
                orderGoodsEntityList.add(orderDeliveryNowDto.getOrderGoodsEntity());
            }

            // 合計金額 計算(税抜)
            deliveryNowReceiveReceiveOrderDto.getOrderSettlementEntity()
                                             .setGoodsPriceTotal(getGoodsPriceTotalNoTax(orderGoodsEntityList));

            // 振分区分 今すぐお届け
            deliveryNowReceiveReceiveOrderDto.setAllocationDeliveryType(HTypeAllocationDeliveryType.DELIVER_NOW);

            resReceiveOrderDtoList.add(deliveryNowReceiveReceiveOrderDto);
        }

        // 取りおき分が存在する場合
        if (reserveDeliveryDtoList != null && !reserveDeliveryDtoList.isEmpty()) {
            ReceiveOrderDto reserveDeliveryReceiveOrderDto = CopyUtil.deepCopy(receiveOrderDto);
            resReceiveOrderDtoList.addAll(createReceiveDtoOrderByReserveDeliveryDto(reserveDeliveryDtoList,
                                                                                    reserveDeliveryReceiveOrderDto
                                                                                   ));
        }

        // 入荷次第お届け分が存在する場合
        if (dependingOnReceiptGoodsDtoList != null && !dependingOnReceiptGoodsDtoList.isEmpty()) {
            ReceiveOrderDto dependingOnReceiptGoodsReceiveOrderDto = CopyUtil.deepCopy(receiveOrderDto);
            resReceiveOrderDtoList.addAll(createDependingOnReceiptGoodsDto(dependingOnReceiptGoodsDtoList,
                                                                           dependingOnReceiptGoodsReceiveOrderDto
                                                                          ));
        }

        // 注文連番採番
        Integer orderConsecutiveNo = 1;
        for (ReceiveOrderDto dto : resReceiveOrderDtoList) {
            dto.getOrderDeliveryDto().getOrderDeliveryEntity().setOrderConsecutiveNo(orderConsecutiveNo);

            for (OrderGoodsEntity orderGoodsEntity : dto.getOrderDeliveryDto().getOrderGoodsEntityList()) {
                orderGoodsEntity.setOrderConsecutiveNo(orderConsecutiveNo);
            }
            orderConsecutiveNo++;
        }

        return resReceiveOrderDtoList;
    }

    /**
     * 取りおき分の受注DTOリストを作成します。
     *
     * @param reserveDeliveryDtoList         取りおき分の商品情報
     * @param reserveDeliveryReceiveOrderDto 元の受注DTO
     * @return 取りおき分の受注DTOリスト
     */
    public List<ReceiveOrderDto> createReceiveDtoOrderByReserveDeliveryDto(List<OrderReserveDeliveryDto> reserveDeliveryDtoList,
                                                                           ReceiveOrderDto reserveDeliveryReceiveOrderDto) {
        // 2023-renew No14 from here
        // APIから取得した出荷予定日MAP（KEY：商品コード + | + 配達指定日、VALUE：出荷予定日）
        Map<String, Timestamp> shipmentDateApiMap =
                        reserveDeliveryReceiveOrderDto.getOrderDeliveryDto().getShipmentDateMap();

        // 期間別受注商品MAP（KEY：出荷予定日+ | + 配達指定日, VALUE：受注商品リスト）
        Map<String, List<OrderGoodsEntity>> orderGoodsEntityByPeriodMap = new LinkedHashMap<>();

        // お届け時期設定用MAP（KEY：出荷予定日+ | + 配達指定日, VALUE：お届け時期）
        Map<String, String> reserveDeliveryDateMap = new HashMap<>();

        // 配達指定日設定用Map（KEY：出荷予定日+ | + 配達指定日, VALUE：配達指定日）
        Map<String, Timestamp> deliveryDesignatedDayMap = new HashMap<>();

        // 出荷予定日設定用Map（KEY：出荷予定日+ | + 配達指定日, VALUE：出荷予定日）
        Map<String, Timestamp> shipmentDateMap = new HashMap<>();

        for (OrderReserveDeliveryDto reserveDeliveryDto : reserveDeliveryDtoList) {

            OrderGoodsEntity orderGoodsEntity = reserveDeliveryDto.getOrderGoodsEntity();

            // 取りおきお届け希望日（＝配達指定日）
            Timestamp reserveDeliveryDate = orderGoodsEntity.getReserveDeliveryDate();
            // 出荷予定日を取得
            Timestamp shipmentDate = shipmentDateApiMap.get(
                            getShipmentDateMapKey(orderGoodsEntity.getGoodsCode(), reserveDeliveryDate));

            // 期間別受注商品MAPのKEYを作成（出荷予定日+ | + 配達指定日）
            String key = getOrderGoodsEntityByPeriodMapKey(shipmentDate, reserveDeliveryDate);

            // 期間ごとの受注商品MAPを作成
            // 登録済みの期間
            List<OrderGoodsEntity> orderGoodsEntityList = orderGoodsEntityByPeriodMap.get(key);
            if (orderGoodsEntityList == null) {
                // 新規
                orderGoodsEntityList = new ArrayList<>();
            }
            orderGoodsEntityList.add(orderGoodsEntity);
            orderGoodsEntityByPeriodMap.put(key, orderGoodsEntityList);

            reserveDeliveryDateMap.put(key, dateUtility.formatYmdWithSlash(reserveDeliveryDate));
            deliveryDesignatedDayMap.put(key, reserveDeliveryDate);
            shipmentDateMap.put(key, shipmentDate);
        }

        // 受注DTOリストの作成
        List<ReceiveOrderDto> resReceiveOrderDtoList = new ArrayList<>();

        for (String key : orderGoodsEntityByPeriodMap.keySet()) {

            ReceiveOrderDto copyReserveDeliveryReceiveOrderDto = CopyUtil.deepCopy(reserveDeliveryReceiveOrderDto);

            OrderDeliveryDto orderDeliveryDto = copyReserveDeliveryReceiveOrderDto.getOrderDeliveryDto();

            // お届け時期を設定
            orderDeliveryDto.setDeliveryDate(reserveDeliveryDateMap.get(key));

            // 出荷予定日を設定
            orderDeliveryDto.getOrderDeliveryEntity().setShipmentDate(shipmentDateMap.get(key));

            // お届け希望日 配達指定日を設定
            orderDeliveryDto.getOrderDeliveryEntity().setReceiverDate(deliveryDesignatedDayMap.get(key));

            // お届け時間帯をクリア
            orderDeliveryDto.getOrderDeliveryEntity().setReceiverTimeZone(null);

            // 受注商品情報一旦クリア
            List<OrderGoodsEntity> orderGoodsEntityList = orderDeliveryDto.getOrderGoodsEntityList();
            orderGoodsEntityList.clear();
            // 受注商品情報を置き換え
            for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityByPeriodMap.get(key)) {
                orderGoodsEntityList.add(orderGoodsEntity);

            }

            // 合計金額 計算(税抜)
            copyReserveDeliveryReceiveOrderDto.getOrderSettlementEntity()
                                              .setGoodsPriceTotal(getGoodsPriceTotalNoTax(orderGoodsEntityList));

            // 振分区分 取りおき
            copyReserveDeliveryReceiveOrderDto.setAllocationDeliveryType(HTypeAllocationDeliveryType.RESERVABLE);

            resReceiveOrderDtoList.add(copyReserveDeliveryReceiveOrderDto);
        }

        // 出荷予定日で並べ替え
        Collections.sort(resReceiveOrderDtoList, (obj1, obj2) -> {
            Timestamp shippingTime1 = obj1.getOrderDeliveryDto().getOrderDeliveryEntity().getShipmentDate();
            Timestamp shippingTime2 = obj2.getOrderDeliveryDto().getOrderDeliveryEntity().getShipmentDate();
            if (shippingTime1.compareTo(shippingTime2) != 0) {
                return shippingTime1.compareTo(shippingTime2);
            }
            Timestamp receiverDate1 = obj1.getOrderDeliveryDto().getOrderDeliveryEntity().getReceiverDate();
            Timestamp receiverDate2 = obj2.getOrderDeliveryDto().getOrderDeliveryEntity().getReceiverDate();
            return receiverDate1.compareTo(receiverDate2);
        });
        // 2023-renew No14 to here

        return resReceiveOrderDtoList;

    }

    /**
     * 入荷次第の受注DTOリストを作成します。
     *
     * @param dependingOnReceiptGoodsList            入荷次第お届け分の商品情報
     * @param dependingOnReceiptGoodsReceiveOrderDto 元の受注DTO
     * @return 入荷次第お届け分の受注DTOリスト
     */
    public List<ReceiveOrderDto> createDependingOnReceiptGoodsDto(List<OrderDependingOnReceiptGoodsDto> dependingOnReceiptGoodsList,
                                                                  ReceiveOrderDto dependingOnReceiptGoodsReceiveOrderDto) {
        // 期間別受注商品MAP
        Map<String, List<OrderGoodsEntity>> orderGoodsEntityByPeriodMap = new LinkedHashMap<>();

        // お届け時期設定用MAP
        Map<String, String> dependingOnReceiptGoodsDateMap = new HashMap<>();

        // 配達指定日設定用Map
        Map<String, Timestamp> deliveryDesignatedDayMap = new HashMap<>();
        for (OrderDependingOnReceiptGoodsDto orderDependingOnReceiptGoodsDto : dependingOnReceiptGoodsList) {

            // 値が設定されていない場合
            if (orderDependingOnReceiptGoodsDto.getOrderGoodsEntity().getGoodsCount() == null
                || orderDependingOnReceiptGoodsDto.getOrderGoodsEntity().getGoodsCount().equals(BigDecimal.ZERO)) {
                // スキップ
                continue;
            }
            List<OrderGoodsEntity> orderGoodsEntityList;
            // 登録済みの期間
            String getKey = null;
            if (orderDependingOnReceiptGoodsDto.getStockDate() != null) {
                getKey = orderDependingOnReceiptGoodsDto.getStockDate().toString()
                         + orderDependingOnReceiptGoodsDto.getStockManagementFlag().getValue();
            }
            orderGoodsEntityList = orderGoodsEntityByPeriodMap.get(getKey);
            if (orderGoodsEntityList == null) {
                // 新規
                orderGoodsEntityList = new ArrayList<>();
            }

            orderGoodsEntityList.add(orderDependingOnReceiptGoodsDto.getOrderGoodsEntity());

            String setKey = null;
            if (orderDependingOnReceiptGoodsDto.getStockDate() != null) {
                setKey = orderDependingOnReceiptGoodsDto.getStockDate().toString()
                         + orderDependingOnReceiptGoodsDto.getStockManagementFlag().getValue();
            }
            orderGoodsEntityByPeriodMap.put(setKey, orderGoodsEntityList);

        }
        // 受注DTOリストの作成
        List<ReceiveOrderDto> resReceiveOrderDtoList = new ArrayList<>();

        for (String key : orderGoodsEntityByPeriodMap.keySet()) {

            ReceiveOrderDto copyDependingOnReceiptGoodsReceiveOrderDto =
                            CopyUtil.deepCopy(dependingOnReceiptGoodsReceiveOrderDto);

            OrderDeliveryDto orderDeliveryDto = copyDependingOnReceiptGoodsReceiveOrderDto.getOrderDeliveryDto();

            // お届け時期を設定
            orderDeliveryDto.setDeliveryDate(dependingOnReceiptGoodsDateMap.get(key));

            // 出荷予定日を設定
            if (key == null) {
                orderDeliveryDto.getOrderDeliveryEntity().setShipmentDate(null);
            } else {
                String shipmentDate = key.substring(0, key.length() - 1);

                Timestamp ts = Timestamp.valueOf(shipmentDate);
                orderDeliveryDto.getOrderDeliveryEntity().setShipmentDate(ts);
            }

            // お届け希望日 配達指定日を設定
            orderDeliveryDto.getOrderDeliveryEntity().setReceiverDate(deliveryDesignatedDayMap.get(key));

            // お届け時間帯をクリア
            orderDeliveryDto.getOrderDeliveryEntity().setReceiverTimeZone(null);

            // 受注商品情報一旦クリア
            List<OrderGoodsEntity> orderGoodsEntityList = orderDeliveryDto.getOrderGoodsEntityList();
            orderGoodsEntityList.clear();
            // 受注商品情報を置き換え
            for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityByPeriodMap.get(key)) {
                orderGoodsEntityList.add(orderGoodsEntity);
            }

            // 合計金額 計算(税抜)
            copyDependingOnReceiptGoodsReceiveOrderDto.getOrderSettlementEntity()
                                                      .setGoodsPriceTotal(
                                                                      getGoodsPriceTotalNoTax(orderGoodsEntityList));

            // 振分区分 取りおき
            copyDependingOnReceiptGoodsReceiveOrderDto.setAllocationDeliveryType(
                            HTypeAllocationDeliveryType.DEPENDING_ON_RECEIPT);

            resReceiveOrderDtoList.add(copyDependingOnReceiptGoodsReceiveOrderDto);
        }

        return resReceiveOrderDtoList;

    }

    /**
     * 受注商品エンティティリストから商品合計金額(税抜)を算出する<br/>
     *
     * @param orderGoodsEntityList 受注商品エンティティリスト
     * @return 商品合計金額(税抜)
     */
    public BigDecimal getGoodsPriceTotalNoTax(List<OrderGoodsEntity> orderGoodsEntityList) {

        BigDecimal goodsPriceTotal = BigDecimal.ZERO;

        if (CollectionUtil.isEmpty(orderGoodsEntityList)) {
            return goodsPriceTotal;
        }

        for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {
            goodsPriceTotal = goodsPriceTotal.add(
                            orderGoodsEntity.getGoodsPrice().multiply(orderGoodsEntity.getGoodsCount()));
        }

        return goodsPriceTotal;
    }

    /**
     * WEB-APIで取得した消費税の合計を計算します。
     *
     * @param taxMap          消費税率取得 詳細情報MAP
     * @param receiveOrderDto 受注DTO
     * @return 消費税合計
     */
    public BigDecimal getTotalTax(Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> taxMap,
                                  ReceiveOrderDto receiveOrderDto) {

        BigDecimal goodsTotalPriceNoTax = BigDecimal.ZERO;
        BigDecimal taxRate08 = new BigDecimal(0);
        BigDecimal taxRate10 = new BigDecimal(0);

        for (OrderGoodsEntity orderGoodsEntity : receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList()) {
            WebApiGetConsumptionTaxRateResponseDetailDto dto =
                            taxMap.get((goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                                            orderGoodsEntity.getGoodsCode())));
            if (dto == null) {
                continue;
            }

            if (dto.getTaxRate().compareTo(BigDecimal.valueOf(TAXRATE08)) == 0) {
                taxRate08 = taxRate08.add(orderGoodsEntity.getGoodsPrice().multiply(orderGoodsEntity.getGoodsCount()));
                // 受注商品エンティティに税率を保持
                orderGoodsEntity.setTaxRate(BigDecimal.valueOf(TAXRATE08).multiply(CalculatePriceUtility.ONE_HUNDRED));
            }
            if (dto.getTaxRate().compareTo(BigDecimal.valueOf(TAXRATE10)) == 0) {
                taxRate10 = taxRate10.add(orderGoodsEntity.getGoodsPrice().multiply(orderGoodsEntity.getGoodsCount()));
                // 受注商品エンティティに税率を保持
                orderGoodsEntity.setTaxRate(BigDecimal.valueOf(TAXRATE10).multiply(CalculatePriceUtility.ONE_HUNDRED));
            }
        }

        // 消費税額を計算
        BigDecimal taxPrice08 = goodsUtility.calculationGoodsPriceTax(taxRate08, BigDecimal.valueOf(TAXRATE08));
        BigDecimal taxPrice10 = goodsUtility.calculationGoodsPriceTax(taxRate10, BigDecimal.valueOf(TAXRATE10));

        // 受注決済エンティティに保持
        receiveOrderDto.getOrderSettlementEntity().setReducedTaxTargetPrice(taxRate08);
        receiveOrderDto.getOrderSettlementEntity().setStandardTaxTargetPrice(taxRate10);
        receiveOrderDto.getOrderSettlementEntity().setReducedTaxPrice(taxPrice08);
        receiveOrderDto.getOrderSettlementEntity().setStandardTaxPrice(taxPrice10);

        return goodsTotalPriceNoTax.add(taxPrice08).add(taxPrice10);
    }

    /**
     * お支払合計(税込)の算出を行います。
     *
     * @param orderDeliveryDto    受注配送DTO
     * @param totalTax            消費税合計
     * @param allGoodsPriceOutTax 商品合計(税抜)
     * @return お支払合計(税込)
     */
    public BigDecimal calculationTotalPriceInTax(OrderDeliveryDto orderDeliveryDto,
                                                 BigDecimal allGoodsPriceOutTax,
                                                 BigDecimal totalTax) {

        // 商品合計
        BigDecimal totalPriceInTaxBigDecimal = allGoodsPriceOutTax;

        // 送料
        if (orderDeliveryDto.getOrderDeliveryEntity().getCarriage() != null) {
            totalPriceInTaxBigDecimal =
                            totalPriceInTaxBigDecimal.add(orderDeliveryDto.getOrderDeliveryEntity().getCarriage());
        }
        // 消費税
        if (totalTax != null) {
            totalPriceInTaxBigDecimal = totalPriceInTaxBigDecimal.add(totalTax);
        }

        // プロモーション値引き
        if (orderDeliveryDto.getDiscountPrice() != null) {
            totalPriceInTaxBigDecimal = totalPriceInTaxBigDecimal.subtract(orderDeliveryDto.getDiscountPrice());
        }

        // お支払い合計がマイナス金額となった場合
        if (BigDecimal.ZERO.compareTo(totalPriceInTaxBigDecimal) > 0) {
            // 0円
            return BigDecimal.ZERO;
        }

        return totalPriceInTaxBigDecimal;
    }

    /**
     * 合計金額の計算をし、各受注DTOに設定します。
     *
     * @param dispReceiveOrderDto        表示用 受注DTO
     * @param receiveOrderDtoList        受注DTOリスト
     * @param taxRateMap                 消費税率MAP
     * @param freeSettlementMethodEntity 無料決済エンティティ
     */
    public void calculationTotalPrice(ReceiveOrderDto dispReceiveOrderDto,
                                      List<ReceiveOrderDto> receiveOrderDtoList,
                                      Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> taxRateMap,
                                      SettlementMethodEntity freeSettlementMethodEntity) {

        WebApiGetConsumptionTaxRateResponseDetailDto carriageTaxRateDto =
                        taxRateMap.get(WebApiGetConsumptionTaxRateRequestDto.CARRIAGE_CODE);

        WebApiGetConsumptionTaxRateResponseDetailDto promoDiscountRateDto =
                        taxRateMap.get(WebApiGetConsumptionTaxRateRequestDto.PROMO_DISCOUNT_CODE);

        // 送料の消費税
        BigDecimal carriageTaxRate = BigDecimal.ZERO;
        if (carriageTaxRateDto != null) {
            carriageTaxRate = carriageTaxRateDto.getTaxRate();
        }

        // プロモーション値引の消費税
        BigDecimal promoDiscountTaxRate = BigDecimal.ZERO;
        if (promoDiscountRateDto != null) {
            promoDiscountTaxRate = promoDiscountRateDto.getTaxRate();
        }

        // 2023-renew No14 from here
        boolean isAllFree = true;
        // 2023-renew No14 to here
        for (ReceiveOrderDto receiveOrderDto : receiveOrderDtoList) {

            OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

            // 注文合計金額
            BigDecimal allGoodsPriceOutTax = getGoodsPriceTotalNoTax(orderDeliveryDto.getOrderGoodsEntityList());
            receiveOrderDto.getOrderSettlementEntity().setGoodsPriceTotal(allGoodsPriceOutTax);

            // 商品ごとの消費税合計
            BigDecimal totalTax = getTotalTax(taxRateMap, receiveOrderDto);

            // 送料が発生した場合は送料分の消費税を加算
            BigDecimal carriage = orderDeliveryDto.getOrderDeliveryEntity().getCarriage();
            if (BigDecimal.ZERO.compareTo(carriage) != 0) {
                BigDecimal taxPriceCarriage = goodsUtility.calculationGoodsPriceTax(carriage, carriageTaxRate);
                totalTax = totalTax.add(taxPriceCarriage);
                // 受注決済エンティティに保持
                if (carriageTaxRate.compareTo(BigDecimal.valueOf(TAXRATE08)) == 0) {
                    receiveOrderDto.getOrderSettlementEntity()
                                   .setReducedTaxTargetPrice(receiveOrderDto.getOrderSettlementEntity()
                                                                            .getReducedTaxTargetPrice()
                                                                            .add(carriage));
                    receiveOrderDto.getOrderSettlementEntity()
                                   .setReducedTaxPrice(receiveOrderDto.getOrderSettlementEntity()
                                                                      .getReducedTaxPrice()
                                                                      .add(taxPriceCarriage));
                }
                if (carriageTaxRate.compareTo(BigDecimal.valueOf(TAXRATE10)) == 0) {
                    receiveOrderDto.getOrderSettlementEntity()
                                   .setStandardTaxTargetPrice(receiveOrderDto.getOrderSettlementEntity()
                                                                             .getStandardTaxTargetPrice()
                                                                             .add(carriage));
                    receiveOrderDto.getOrderSettlementEntity()
                                   .setStandardTaxPrice(receiveOrderDto.getOrderSettlementEntity()
                                                                       .getStandardTaxPrice()
                                                                       .add(taxPriceCarriage));
                }
            }

            // 消費税はプロモ値引を含まない形で受注決済エンティティに保持する
            // ※現行PDRもプロモ値引値はHM側受注関連TBLに保持していないため（基幹側に送る情報にしか反映しない）
            receiveOrderDto.getOrderSettlementEntity().setTaxPrice(totalTax);

            BigDecimal totalTaxInDiscount = new BigDecimal(totalTax.toString());
            // プロモ値引が発生した場合はプロモ値引分の消費税率を減算
            if (BigDecimal.ZERO.compareTo(orderDeliveryDto.getDiscountPrice()) != 0) {
                totalTaxInDiscount = totalTaxInDiscount.subtract(orderDeliveryDto.getTaxPrice());
            }

            // 消費税がマイナス金額となった場合
            if (BigDecimal.ZERO.compareTo(totalTaxInDiscount) > 0) {
                // 0円
                totalTaxInDiscount = BigDecimal.ZERO;
            }
            orderDeliveryDto.setTotalTax(totalTaxInDiscount);

            // お支払合計(請求金額)
            BigDecimal totalPriceInTax = calculationTotalPriceInTax(orderDeliveryDto, allGoodsPriceOutTax,
                                                                    orderDeliveryDto.getTotalTax()
                                                                   );
            if (BigDecimal.ZERO.compareTo(totalPriceInTax) >= 0) {
                // 支払方法を無料配送に変換
                receiveOrderDto.setSettlementMethodEntity(freeSettlementMethodEntity);

                // 決済方法SEQを設定
                receiveOrderDto.getOrderSettlementEntity()
                               .setSettlementMethodSeq(freeSettlementMethodEntity.getSettlementMethodSeq());
                // 決済種別
                receiveOrderDto.getOrderSettlementEntity()
                               .setSettlementMethodType(freeSettlementMethodEntity.getSettlementMethodType());
                // 請求種別を設定
                receiveOrderDto.getOrderSettlementEntity().setBillType(freeSettlementMethodEntity.getBillType());
            }
            // 2023-renew No14 from here
            else {
                isAllFree = false;
            }
            // 2023-renew No14 to here

            receiveOrderDto.getOrderSummaryEntity().setOrderPrice(totalPriceInTax);
            receiveOrderDto.getOrderBillEntity().setBillPrice(totalPriceInTax);
        }

        // 2023-renew No14 from here
        // 全件無料に置き換わった場合、表示用の受注DTOの値も書き換える
        if (isAllFree) {
            // 支払方法を無料配送に変換
            dispReceiveOrderDto.setSettlementMethodEntity(freeSettlementMethodEntity);

            // 決済方法SEQを設定
            dispReceiveOrderDto.getOrderSettlementEntity()
                               .setSettlementMethodSeq(freeSettlementMethodEntity.getSettlementMethodSeq());
            // 決済種別
            dispReceiveOrderDto.getOrderSettlementEntity()
                               .setSettlementMethodType(freeSettlementMethodEntity.getSettlementMethodType());
            // 請求種別を設定
            dispReceiveOrderDto.getOrderSettlementEntity().setBillType(freeSettlementMethodEntity.getBillType());
        }
        // 2023-renew No14 to here
    }

    /**
     * 受注商品エンティティから<br/>
     * 商品コードリストを作成します。
     *
     * @param orderGoodsEntityList 受注商品エンティティ
     * @return 商品コードリスト
     */
    public List<String> getGoodsCodeList(List<OrderGoodsEntity> orderGoodsEntityList) {

        List<String> goodsCodeList = new ArrayList<>();
        for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {
            goodsCodeList.add(orderGoodsEntity.getGoodsCode());
        }
        return goodsCodeList;
    }

    /**
     * 受注商品エンティティから<br/>
     * 数量リストを作成します。
     *
     * @param orderGoodsEntityList 受注商品エンティティ
     * @return 数量リスト
     */
    public List<String> getQuantityList(List<OrderGoodsEntity> orderGoodsEntityList) {

        List<String> quantityList = new ArrayList<>();
        for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {
            quantityList.add(orderGoodsEntity.getGoodsCount().toString());
        }
        return quantityList;
    }

    /**
     * エラーメッセージ表示で使用する「商品表示名（規格１/規格２)」を作成します。<br/>
     *
     * @param orderGoodsEntity 受注商品クラス
     * @return 商品表示名（規格１/規格２)
     */
    public String createErrDispGoodsName(OrderGoodsEntity orderGoodsEntity) {
        // PDR Migrate Customization from here
        return this.createErrDispGoodsName(goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(orderGoodsEntity),
                                           orderGoodsEntity.getUnitValue1(), orderGoodsEntity.getUnitValue2()
                                          );
        // PDR Migrate Customization to here
    }

    /**
     * エラーメッセージ表示で使用する「商品表示名（規格１/規格２)」を作成します。<br/>
     *
     * @param namePC     商品表示PC
     * @param unitValue1 規格値1
     * @param unitValue2 規格値2
     * @return 商品表示名（規格１/規格２)
     */
    public String createErrDispGoodsName(String namePC, String unitValue1, String unitValue2) {
        StringBuilder displayGoodsName = new StringBuilder();

        displayGoodsName.append(namePC);

        if (unitValue1 != null) {
            displayGoodsName.append("(");
            // 規格管理ありの場合は規格値１は必須項目なので必ず取得できる
            displayGoodsName.append(unitValue1);
            // 規格値２の値がNULLの場合は商品表示名（規格値１）のみをエラーメッセージに表示する
            // 規格値２の値が存在する場合は規格値２をエラーメッセージに表示する
            if (unitValue2 != null) {
                displayGoodsName.append("/");
                displayGoodsName.append(unitValue2);
            }
            displayGoodsName.append(")");
        }

        return displayGoodsName.toString();
    }

    /**
     * 出荷予定日（メッセージ用）を取得します。
     * ※実質はお届け希望日のこと（受注用の出荷予定日ではない）
     *
     * @param receiveOrderDto 受注DTO
     * @return 出荷予定日
     */
    public String getDeliveryDate(ReceiveOrderDto receiveOrderDto) {

        String deliveryDate;

        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

        if (HTypeAllocationDeliveryType.DELIVER_NOW.equals(receiveOrderDto.getAllocationDeliveryType())) {
            // 今すぐお届け
            deliveryDate = dateUtility.formatYmdWithSlash(orderDeliveryDto.getOrderDeliveryEntity().getReceiverDate());
        } else if (HTypeAllocationDeliveryType.RESERVABLE.equals((receiveOrderDto).getAllocationDeliveryType())) {
            // 取りおき
            deliveryDate = orderDeliveryDto.getDeliveryDate();
        } else {
            // 入荷次第お届け
            deliveryDate = HTypeAllocationDeliveryType.DEPENDING_ON_RECEIPT.getLabel();
        }
        return deliveryDate;
    }

    /**
     * 旬情報を取得します<br/>
     *
     * @param radio 取りおき分お届け時期ラジオボタン
     * @return 旬情報
     */
    public String getSeason(String radio) {

        String season = "";
        if ("0".equals(radio)) {
            season = "上旬";
        } else if ("1".equals(radio)) {
            season = "中旬";
        } else if ("2".equals(radio)) {
            season = "下旬";
        }
        return season;
    }

    /**
     * Mapのkeyとvalueを反転させます<br />
     *
     * @param beforeMap 反転させたいMap
     * @return 反転したMap
     */
    public Map<String, String> inverseMap(Map<String, String> beforeMap) {

        Map<String, String> afterMap = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : beforeMap.entrySet()) {
            afterMap.put(entry.getValue(), entry.getKey());
        }
        return afterMap;
    }

    // PDR Migrate Customization to here

    // 2023-renew No14 from here

    /**
     * 出荷予定日MapのKEYを生成
     * @param goodsCode 商品コード
     * @param deliveryDesignatedDay 配達指定日
     * @return KEY；商品コード + | + 配達指定日
     */
    public String getShipmentDateMapKey(String goodsCode, Timestamp deliveryDesignatedDay) {
        StringBuilder sb = new StringBuilder();
        sb.append(goodsCode);
        if (deliveryDesignatedDay != null) {
            sb.append("|");
            sb.append(dateUtility.formatYmdWithSlash(deliveryDesignatedDay));
        }
        return sb.toString();
    }

    /**
     * 期間別受注商品MAPのKEYを生成
     * @param shipmentDate 出荷予定日
     * @param deliveryDesignatedDay 配達指定日
     * @return KEY；出荷予定日 + | + 配達指定日
     */
    public String getOrderGoodsEntityByPeriodMapKey(Timestamp shipmentDate, Timestamp deliveryDesignatedDay) {
        StringBuilder sb = new StringBuilder();
        sb.append(dateUtility.formatYmdWithSlash(shipmentDate));
        if (deliveryDesignatedDay != null) {
            sb.append("|");
            sb.append(dateUtility.formatYmdWithSlash(deliveryDesignatedDay));
        }
        return sb.toString();
    }

    // 2023-renew No14 to here

}
