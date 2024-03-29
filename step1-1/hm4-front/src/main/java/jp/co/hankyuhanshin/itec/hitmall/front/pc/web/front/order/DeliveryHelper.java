/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ZenHanConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeLandSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReceiverTimeZoneJapanPost;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReceiverTimeZoneYamato;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDeliveryNowDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDependingOnReceiptGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderReserveDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.delivery.ReceiverDateDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDeliveryInformationResponseDetailDateInfoDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDeliveryInformationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetPreShipmentOrderHistoryResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetPreShipmentOrderHistoryResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiOrderHistoryResponseGoodsInfoDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.ReserveDetailItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.ReserveItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.DeliveryNowItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.DependingOnReceiptItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.OrderCommonModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.ReserveDeliveryItem;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * PDR#013 09_データ連携（受注データ）<br/>
 * 配送方法選択 Helper
 *
 * <pre>
 * 初期表示処理を追加
 * </pre>
 *
 * @author kaneda
 * @author satoh
 */
@Component
public class DeliveryHelper {

    // PDR Migrate Customization from here
    /**
     * ロガー
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(DeliveryHelper.class);

    /**
     * お届け時間帯_日付単位の区切り文字
     */
    protected static final String DELIMITER_DATE = "_";

    /**
     * お届け時間帯_キーと値の区切り文字
     */
    protected static final String DELIMITER_KEY = ":";

    /**
     * お届け時間帯コード区切り文字
     */
    protected static final String DELIMITER_TIMEZONE_CODE = "|";

    /**
     * 正規表現エスケープ文字
     */
    protected static final String REGEX_ESCAPE = "\\\\";

    /**
     * ConversionUtility
     */
    private final ConversionUtility conversionUtility;

    /**
     * 商品系Utilityクラス
     */
    private final GoodsUtility goodsUtility;

    /**
     * 日付関連Utilityクラス
     */
    private final DateUtility dateUtility;

    /**
     * コンストラク
     *
     * @param conversionUtility 変換ユーティリティクラス
     * @param goodsUtility      商品系ヘルパークラス
     * @param dateUtility       日付関連Utilityクラス
     */
    @Autowired
    public DeliveryHelper(ConversionUtility conversionUtility, GoodsUtility goodsUtility, DateUtility dateUtility) {
        this.conversionUtility = conversionUtility;
        this.goodsUtility = goodsUtility;
        this.dateUtility = dateUtility;
    }
    // PDR Migrate Customization to here

    /**
     * Modelへの変換処理。<br />
     * 受注配送Dto ⇒ Model<br />
     * <pre>
     * 引数に商品在庫情報を追加
     * 今すぐお届け分、入庫次第お届け分、取りおき可能分に振り分け
     * </pre>
     *
     * @param orderCommonModel  注文フロー共通Model
     * @param deliveryModel     配送方法選択画面Model
     * @param orderDeliveryDto  受注配送Dtoクラス
     * @param goodsDetailsMap   商品詳細Dtoマップ
     * @param webApiResponseDto WEB-API連携取得結果DTO
     */
    public void toPageForLoad(OrderCommonModel orderCommonModel,
                              DeliveryModel deliveryModel,
                              OrderDeliveryDto orderDeliveryDto,
                              Map<Integer, GoodsDetailsDto> goodsDetailsMap,
                              WebApiGetPreShipmentOrderHistoryResponseDto webApiResponseDto) {
        // PDR Migrate Customization from here
        // 今すぐお届け分
        LOGGER.info("配送画面組み立て（今すぐお届け分）（開始）：" + dateUtility.format(
                        dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));
        toPageDeliveryNowItems(orderCommonModel, deliveryModel, orderDeliveryDto, goodsDetailsMap);
        LOGGER.info("配送画面組み立て（今すぐお届け分）（終了）：" + dateUtility.format(
                        dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));

        // 取りおき分
        LOGGER.info("配送画面組み立て（取りおき分）（開始）：" + dateUtility.format(
                        dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));
        toPageReserveDeliveryItems(
                        orderCommonModel, deliveryModel, orderDeliveryDto, goodsDetailsMap, webApiResponseDto);
        LOGGER.info("配送画面組み立て（取りおき分）（終了）：" + dateUtility.format(
                        dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));

        // 入荷次第お届け分
        LOGGER.info("配送画面組み立て（入荷次第お届け分）（開始）：" + dateUtility.format(
                        dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));
        toPageDependingOnReceiptItems(orderCommonModel, deliveryModel, orderDeliveryDto, goodsDetailsMap);
        LOGGER.info("配送画面組み立て（入荷次第お届け分）（終了）：" + dateUtility.format(
                        dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));

        // お届け予定日、お届け時間帯を設定
        createDeliveryAssign(orderCommonModel, deliveryModel, orderDeliveryDto);

        // お届け先情報
        toReceiver(orderCommonModel, deliveryModel, orderDeliveryDto);
        // PDR Migrate Customization to here
    }

    // PDR Migrate Customization from here

    /**
     * 今すぐ買う分のページ表示項目作成
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param deliveryModel    配送方法選択画面Model
     * @param orderDeliveryDto 受注配送Dto
     * @param goodsDetailsMap  商品詳細DtoMAP
     */
    public void toPageDeliveryNowItems(OrderCommonModel orderCommonModel,
                                       DeliveryModel deliveryModel,
                                       OrderDeliveryDto orderDeliveryDto,
                                       Map<Integer, GoodsDetailsDto> goodsDetailsMap) {

        List<OrderDeliveryNowDto> deliveryNowDtoList = orderDeliveryDto.getOrderDeliveryNowDtoList();
        if (deliveryNowDtoList == null || deliveryNowDtoList.isEmpty()) {
            return;
        }

        List<DeliveryNowItem> deliveryNowItemList = new ArrayList<>();
        for (OrderDeliveryNowDto deliveryNowDto : deliveryNowDtoList) {

            DeliveryNowItem deliveryNowItem = ApplicationContextUtility.getBean(DeliveryNowItem.class);
            GoodsDetailsDto goodsDetailsDto = goodsDetailsMap.get(deliveryNowDto.getOrderGoodsEntity().getGoodsSeq());

            // 商品画像
            String imageFileName = goodsUtility.getImageFileName(goodsDetailsDto);
            String goodsImageSrc = goodsUtility.getGoodsImagePath(imageFileName);
            String goodsName = goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(goodsDetailsDto);
            deliveryNowItem.setGoodsImage(goodsImageSrc);
            deliveryNowItem.setGoodsImageSrc(goodsImageSrc);
            deliveryNowItem.setGoodsImageAlt(goodsName);

            // 商品SEQ
            deliveryNowItem.setGoodsSeq(goodsDetailsDto.getGoodsSeq());

            // 商品コード
            deliveryNowItem.setGoodsCode(
                            goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(goodsDetailsDto.getGoodsCode()));
            // 商品表示名
            deliveryNowItem.setGoodsName(goodsName);

            // 規格1
            deliveryNowItem.setViewUnit1(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle1()));
            deliveryNowItem.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
            deliveryNowItem.setUnitValue1(goodsDetailsDto.getUnitValue1());

            // 規格2
            deliveryNowItem.setViewUnit2(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle2()));
            deliveryNowItem.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
            deliveryNowItem.setUnitValue2(goodsDetailsDto.getUnitValue2());

            // 商品数量
            deliveryNowItem.setOrderGoodsCount(deliveryNowDto.getOrderGoodsEntity().getGoodsCount().toString());

            // 単位
            deliveryNowItem.setUnit(goodsDetailsDto.getUnit());

            // 心意気商品の場合は数量欄をラベルにする
            deliveryNowItem.setEmotionPriceGoods(goodsUtility.isEmotionPriceGoods(goodsDetailsDto));

            deliveryNowItemList.add(deliveryNowItem);
        }

        deliveryModel.setDeliveryNowItems(deliveryNowItemList);

    }

    /**
     * お取りおき分のページ表示項目作成
     *
     * @param orderCommonModel  注文フロー共通Model
     * @param deliveryModel     配送方法選択画面Model
     * @param orderDeliveryDto  受注配送Dto
     * @param goodsDetailsMap   商品詳細DtoMAP
     * @param webApiResponseDto WEB-API連携取得結果DTO
     */
    public void toPageReserveDeliveryItems(OrderCommonModel orderCommonModel,
                                           DeliveryModel deliveryModel,
                                           OrderDeliveryDto orderDeliveryDto,
                                           Map<Integer, GoodsDetailsDto> goodsDetailsMap,
                                           WebApiGetPreShipmentOrderHistoryResponseDto webApiResponseDto) {

        List<OrderReserveDeliveryDto> reserveDeliveryDtoList = orderDeliveryDto.getOrderReserveDeliveryDtoList();
        if (reserveDeliveryDtoList == null || reserveDeliveryDtoList.isEmpty()) {
            return;
        }

        // 受注履歴から商品の注文数を抽出しマップに格納する
        Map<String, Integer> orderHistoryGoodsCountMap = new HashMap<>();
        // 過去に受注していない場合はNullで返ってくるので戻り値・受注NoのNullチェックを行う。実行ステータスはLogicでチェックし適切なエラーが返される。
        if (null != webApiResponseDto && null != webApiResponseDto.info.get(0).getOrderNo()
            && webApiResponseDto.getResult().getStatus().equals("0")) {

            LOGGER.info("注文履歴（配送済み）集計作成（開始）：" + dateUtility.format(
                            dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));

            for (WebApiGetPreShipmentOrderHistoryResponseDetailDto dto : webApiResponseDto.info) {
                for (WebApiOrderHistoryResponseGoodsInfoDto goodsDetails : dto.getGoodsList()) {
                    String goodsCode = goodsDetails.getGoodsCode();
                    // マップに格納していない商品コードの時
                    if (!orderHistoryGoodsCountMap.containsKey(goodsCode)) {
                        orderHistoryGoodsCountMap.put(goodsCode, goodsDetails.getGoodsCount());
                        continue;
                    }
                    // 商品コードがマップに含まれている時商品数を足してマップを上書き
                    Integer goodsCount = orderHistoryGoodsCountMap.get(goodsCode) + goodsDetails.getGoodsCount();
                    orderHistoryGoodsCountMap.put(goodsCode, goodsCount);
                }
            }
            LOGGER.info("注文履歴（配送済み）集計作成（終了）：" + dateUtility.format(
                            dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));

            // ログ出力　※WEB-APIはログレベルの設定によってログが出力されないため、ここで出力
            for (Map.Entry<String, Integer> historyMap : orderHistoryGoodsCountMap.entrySet()) {
                LOGGER.info("商品番号：" + historyMap.getKey() + " 数量:" + historyMap.getValue());
            }
        }

        // 2023-renew No14 from here
        Map<String, List<ReserveDeliveryItem>> reserveDeliveryItemMap = new LinkedHashMap<>(); // 順序保持（LinkedHashMap）
        // 2023-renew No14 to here

        // 全角、半角の変換Helper取得
        ZenHanConversionUtility zenHanConversionUtility =
                        ApplicationContextUtility.getBean(ZenHanConversionUtility.class);

        for (OrderReserveDeliveryDto orderReserveDeliveryDto : reserveDeliveryDtoList) {

            ReserveDeliveryItem reserveDeliveryItem = ApplicationContextUtility.getBean(ReserveDeliveryItem.class);
            GoodsDetailsDto goodsDetailsDto =
                            goodsDetailsMap.get(orderReserveDeliveryDto.getOrderGoodsEntity().getGoodsSeq());

            // 商品画像
            String imageFileName = goodsUtility.getImageFileName(goodsDetailsDto);
            String goodsImageSrc = goodsUtility.getGoodsImagePath(imageFileName);
            String goodsName = goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(goodsDetailsDto);
            reserveDeliveryItem.setGoodsImage(goodsImageSrc);
            reserveDeliveryItem.setGoodsImageSrc(goodsImageSrc);
            reserveDeliveryItem.setGoodsImageAlt(goodsName);

            // 商品コード
            reserveDeliveryItem.setGoodsCode(
                            goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(goodsDetailsDto.getGoodsCode()));
            // 商品表示名
            reserveDeliveryItem.setGoodsName(goodsName);

            // 規格1
            reserveDeliveryItem.setViewUnit1(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle1()));
            reserveDeliveryItem.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
            reserveDeliveryItem.setUnitValue1(goodsDetailsDto.getUnitValue1());

            // 規格2
            reserveDeliveryItem.setViewUnit2(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle2()));
            reserveDeliveryItem.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
            reserveDeliveryItem.setUnitValue2(goodsDetailsDto.getUnitValue2());

            // 商品数量
            reserveDeliveryItem.setOrderGoodsCount(orderReserveDeliveryDto.getOrderGoodsEntity().getGoodsCount());

            // 単位
            reserveDeliveryItem.setUnit(zenHanConversionUtility.toZenkaku(goodsDetailsDto.getUnit()));

            // 過去の受注数
            // 取りおき商品の過去受注数を代入
            if (orderHistoryGoodsCountMap.containsKey(goodsDetailsDto.getGoodsCode())) {
                reserveDeliveryItem.setReserveDeliveryGoodsHistoryCount(
                                orderHistoryGoodsCountMap.get(goodsDetailsDto.getGoodsCode()));
            } else {
                // 過去に受注の履歴がない時は０を代入
                reserveDeliveryItem.setReserveDeliveryGoodsHistoryCount(0);
            }

            // 2023-renew No14 from here
            // お届け希望日
            reserveDeliveryItem.setReserveDeliveryDate(
                            orderReserveDeliveryDto.getOrderGoodsEntity().getReserveDeliveryDate());
            // セールde予約アイテムMAPに追加
            reserveDeliveryItemMap.computeIfAbsent(goodsDetailsDto.getGoodsCode(), k -> new ArrayList<>())
                                  .add(reserveDeliveryItem);
            // 2023-renew No14 to here
        }

        // 2023-renew No14 from here
        deliveryModel.setReserveDeliveryItemMap(reserveDeliveryItemMap);
        // 2023-renew No14 to here
    }

    /**
     * 入荷次第お届け分のページ表示項目作成
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param deliveryModel    配送方法選択画面Model
     * @param orderDeliveryDto 受注配送Dto
     * @param goodsDetailsMap  商品詳細DtoMAP
     */
    public void toPageDependingOnReceiptItems(OrderCommonModel orderCommonModel,
                                              DeliveryModel deliveryModel,
                                              OrderDeliveryDto orderDeliveryDto,
                                              Map<Integer, GoodsDetailsDto> goodsDetailsMap) {

        List<OrderDependingOnReceiptGoodsDto> orderDependingOnReceiptGoodsDtoList =
                        orderDeliveryDto.getOrderDependingOnReceiptGoodsDtoList();
        if (orderDependingOnReceiptGoodsDtoList == null || orderDependingOnReceiptGoodsDtoList.isEmpty()) {
            return;
        }

        List<DependingOnReceiptItem> dependingOnReceiptItemList = new ArrayList<>();
        for (OrderDependingOnReceiptGoodsDto dependingOnReceiptGoodsDto : orderDependingOnReceiptGoodsDtoList) {

            DependingOnReceiptItem dependingOnReceiptItem =
                            ApplicationContextUtility.getBean(DependingOnReceiptItem.class);
            GoodsDetailsDto goodsDetailsDto =
                            goodsDetailsMap.get(dependingOnReceiptGoodsDto.getOrderGoodsEntity().getGoodsSeq());

            // 商品画像
            String imageFileName = goodsUtility.getImageFileName(goodsDetailsDto);
            String goodsImageSrc = goodsUtility.getGoodsImagePath(imageFileName);
            String goodsName = goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(goodsDetailsDto);
            dependingOnReceiptItem.setGoodsImage(goodsImageSrc);
            dependingOnReceiptItem.setGoodsImageSrc(goodsImageSrc);
            dependingOnReceiptItem.setGoodsImageAlt(goodsName);

            // 商品SEQ
            dependingOnReceiptItem.setGoodsSeq(goodsDetailsDto.getGoodsSeq());

            // 商品コード
            dependingOnReceiptItem.setGoodsCode(
                            goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(goodsDetailsDto.getGoodsCode()));
            // 商品表示名
            dependingOnReceiptItem.setGoodsName(
                            goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(goodsDetailsDto));

            // 規格1
            dependingOnReceiptItem.setViewUnit1(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle1()));
            dependingOnReceiptItem.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
            dependingOnReceiptItem.setUnitValue1(goodsDetailsDto.getUnitValue1());

            // 規格2
            dependingOnReceiptItem.setViewUnit2(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle2()));
            dependingOnReceiptItem.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
            dependingOnReceiptItem.setUnitValue2(goodsDetailsDto.getUnitValue2());

            // 商品数量
            dependingOnReceiptItem.setOrderGoodsCount(dependingOnReceiptGoodsDto.getOrderGoodsEntity().getGoodsCount());

            // 単位
            dependingOnReceiptItem.setUnit(goodsDetailsDto.getUnit());

            dependingOnReceiptItemList.add(dependingOnReceiptItem);
        }

        deliveryModel.setDependingOnReceiptItems(dependingOnReceiptItemList);
    }

    /**
     * 今すぐお届け分が存在する場合、お届け予定日、お届け時間帯を表示する。
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param deliveryModel    配送方法選択画面Model
     * @param orderDeliveryDto 受注配送Dto
     */
    public void createDeliveryAssign(OrderCommonModel orderCommonModel,
                                     DeliveryModel deliveryModel,
                                     OrderDeliveryDto orderDeliveryDto) {

        // 配達指定時間ヤマト
        deliveryModel.setReceiverTimeZoneYamatoItems(EnumTypeUtil.getEnumMap(HTypeReceiverTimeZoneYamato.class));
        // 配達指定時間日本郵便
        deliveryModel.setReceiverTimeZoneJapanPostItems(EnumTypeUtil.getEnumMap(HTypeReceiverTimeZoneJapanPost.class));

        // 配送情報
        WebApiGetDeliveryInformationResponseDetailDto deliveryInformationDetailDto =
                        orderDeliveryDto.getDeliveryInformationDetailDto();

        // 2023-renew No14 from here
        // 今すぐお届け分が存在する場合
        if (deliveryModel.isViewDeliveryNowGoods()) {
            // 2023-renew No14 to here
            deliveryModel.setHideDeliveryAssignFlag(false);

            // 配送情報取得詳細情報がお届け不可である場合、お届け可能日時が連携されないため、お届け希望日・お届け時間帯を表示しない
            if (HTypeDeliveryFlag.OFF.getValue().equals(deliveryInformationDetailDto.getDeliveryFlag())) {
                deliveryModel.setHideDeliveryAssignFlag(true);
                return;
            }

            // お届け予定日 設定
            deliveryModel.setDeliveryDateItems(createDeliveryDateMap(deliveryInformationDetailDto));

            // お届け時間帯コードの設定
            deliveryModel.setChkReceiverTimeZoneCodes(new LinkedHashMap<>());
            deliveryModel.setReceiverTimeZoneCodes(createReceiverTimeZoneCodes(deliveryInformationDetailDto,
                                                                               deliveryModel.getChkReceiverTimeZoneCodes(),
                                                                               deliveryInformationDetailDto.getDeliveryCompanyCode()
                                                                              ));

            // お届け予定日
            if (orderDeliveryDto.getOrderDeliveryEntity().getReceiverDate() == null) {
                // 設定されている値がない場合は一番上を設定
                Map<String, String> deliveryMap = deliveryModel.getDeliveryDateItems();
                deliveryModel.setDeliveryDate(deliveryMap.keySet().iterator().next());
            } else {
                deliveryModel.setDeliveryDate(
                                dateUtility.format(orderDeliveryDto.getOrderDeliveryEntity().getReceiverDate(),
                                                   DateUtility.YMD
                                                  ));
            }

            // 配送会社 設定
            if (HTypeDeliveryType.YAMATO.getValue().equals(deliveryInformationDetailDto.getDeliveryCompanyCode())
                // 2023-renew No14 from here
                || HTypeDeliveryType.AUTOMATIC.getValue()
                                              .equals(deliveryInformationDetailDto.getDeliveryCompanyCode())) {
                // ヤマトまたは自動設定の場合
                // 2023-renew No14 to here
                deliveryModel.setViewReceiverTimeZoneYamato(true);
                deliveryModel.setViewReceiverTimeZoneJapanPost(false);

                // 初期値の設定
                if (StringUtil.isEmpty(orderDeliveryDto.getOrderDeliveryEntity().getReceiverTimeZone())) {
                    Map<String, String> map = deliveryModel.getReceiverTimeZoneYamatoItems(); // #250
                    deliveryModel.setReceiverTimeZoneYamato(map.keySet().iterator().next());
                } else {
                    deliveryModel.setReceiverTimeZoneYamato(
                                    orderDeliveryDto.getOrderDeliveryEntity().getReceiverTimeZone());
                }
            }
            if (HTypeDeliveryType.JAPANPOST.getValue().equals(deliveryInformationDetailDto.getDeliveryCompanyCode())) {
                // 日本郵便の場合
                deliveryModel.setViewReceiverTimeZoneYamato(false); // #250
                deliveryModel.setViewReceiverTimeZoneJapanPost(true); // #250

                // 初期値の設定
                if (StringUtil.isEmpty(orderDeliveryDto.getOrderDeliveryEntity().getReceiverTimeZone())) {
                    Map<String, String> map = deliveryModel.getReceiverTimeZoneJapanPostItems();
                    deliveryModel.setReceiverTimeZoneJapanPost(map.keySet().iterator().next());
                } else {
                    deliveryModel.setReceiverTimeZoneJapanPost(
                                    orderDeliveryDto.getOrderDeliveryEntity().getReceiverTimeZone());
                }
            }

            // 商品詳細情報
            Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap =
                            orderCommonModel.getReceiveOrderDto().getMasterDto().getGoodsMaster();

            // お届け希望日、時間帯指定可否
            // 沖縄チェック
            if (orderDeliveryDto.getOrderDeliveryEntity()
                                .getReceiverAddress1()
                                .startsWith(HTypePrefectureType.OKINAWA.getLabel())) {
                for (GoodsDetailsDto dto : goodsDetailsDtoMap.values()) {
                    if (HTypeLandSendFlag.ON.equals(dto.getLandSendFlag())) {
                        // 沖縄 かつ 陸送の場合
                        // お届け予定日、時間帯指定 非活性
                        deliveryModel.setDeliveryAssignFlag(false);
                        return;
                    }
                }
            }

            // お届け予定日、時間帯指定 活性
            deliveryModel.setDeliveryAssignFlag(true);
        } else {
            // 上記以外の場合、お届け希望日、お届け時間帯を表示しない
            deliveryModel.setHideDeliveryAssignFlag(true);
        }
    }

    /**
     * お届け希望日Map作成
     *
     * <pre>
     * リストの要素を、WEB-API連携 配送情報取得の値に置き換え
     * 形式:yyyy/MM/dd(E)
     * </pre>
     *
     * @param deliveryInformation お届け希望日DTO
     * @return お届け希望日Map
     */
    protected Map<String, String> createDeliveryDateMap(WebApiGetDeliveryInformationResponseDetailDto deliveryInformation) {
        Map<String, String> deliveryDateMap = new LinkedHashMap<>();
        for (WebApiGetDeliveryInformationResponseDetailDateInfoDto date : deliveryInformation.getDateInfo()) {
            // 曜日を取得
            String dayOfTheWeek = ReceiverDateDto.getDayOfTheWeek(date.getReceiveDate());
            // 整形し設定
            deliveryDateMap.put(dateUtility.format(date.getReceiveDate(), DateUtility.YMD),
                                dateUtility.format(date.getReceiveDate(), DateUtility.YMD_SLASH) + dayOfTheWeek
                               );
        }
        return deliveryDateMap;
    }

    /**
     * お届け時間帯コードデータの作成
     *
     * @param deliveryInformation お届け希望日DTO
     * @param chkTimeZoneCodeMap  チェック用お届け時間帯データ格納Map
     * @param deliveryCompanyCode 配送会社コード
     * @return お届け時間帯データ(データ形式 　 yyyyMMdd : 1 | 2_yyyyMMdd : 1 | 2 | 3)
     */
    protected String createReceiverTimeZoneCodes(WebApiGetDeliveryInformationResponseDetailDto deliveryInformation,
                                                 Map<String, List<String>> chkTimeZoneCodeMap,
                                                 String deliveryCompanyCode) {
        StringBuilder sb = new StringBuilder();
        // 「指定なし」コードの取得
        String unSpecified;
        if (HTypeDeliveryType.YAMATO.getValue().equals(deliveryCompanyCode)
            // 2023-renew No14 from here
            || HTypeDeliveryType.AUTOMATIC.getValue().equals(deliveryCompanyCode)) {
            // ヤマトまたは自動設定
            // ※自動設定の時間帯はヤマトの時間帯から選択するため、指定なしコードもヤマトの区分値から設定
            // 2023-renew No14 to here
            unSpecified = HTypeReceiverTimeZoneYamato.UNSPECIFIED.getValue();
        } else {
            // 日本郵便
            unSpecified = HTypeReceiverTimeZoneJapanPost.UNSPECIFIED.getValue();
        }

        for (WebApiGetDeliveryInformationResponseDetailDateInfoDto date : deliveryInformation.getDateInfo()) {
            // お届け日時
            String receiveDateYmd = dateUtility.format(date.getReceiveDate(), DateUtility.YMD);
            // お届け時間帯
            String receiverTimeZone = date.getDispTimeZoneCode();

            if (StringUtil.isEmpty(receiverTimeZone)) {
                receiverTimeZone = unSpecified;
            }

            // チェック用データの格納
            List<String> codeList = new ArrayList<>();
            chkTimeZoneCodeMap.put(receiveDateYmd, codeList);
            for (String code : receiverTimeZone.split(REGEX_ESCAPE + DELIMITER_TIMEZONE_CODE)) {
                if (StringUtil.isNotEmpty(code)) {
                    codeList.add(code);
                }
            }

            // データ作成（yyyyMMdd:1|2_yyyyMMdd:1|2|3）
            if (sb.length() > 0) {
                sb.append(DELIMITER_DATE);
            }
            sb.append(receiveDateYmd);
            sb.append(DELIMITER_KEY);

            // 「指定なし」が存在しない場合、追加
            if (!codeList.contains(unSpecified)) {
                sb.append(unSpecified);
                if (StringUtil.isNotEmpty(date.getDispTimeZoneCode())) {
                    sb.append(DELIMITER_TIMEZONE_CODE);
                }
                codeList.add(unSpecified);
            }

            sb.append(receiverTimeZone);

        }

        return sb.toString();
    }

    /**
     * Modelへの変換処理
     * 受注配送Dto ⇒ お届け先表示情報
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param deliveryModel    配送方法選択画面Model
     * @param orderDeliveryDto 受注配送Dto
     */
    public void toReceiver(OrderCommonModel orderCommonModel,
                           DeliveryModel deliveryModel,
                           OrderDeliveryDto orderDeliveryDto) {
        // お届け先
        OrderDeliveryEntity orderDeliveryEntity = orderDeliveryDto.getOrderDeliveryEntity();
        deliveryModel.setReceiverLastName(orderDeliveryEntity.getReceiverLastName());
        deliveryModel.setReceiverTel(orderDeliveryEntity.getReceiverTel());

        String[] zipCodes = conversionUtility.toZipCodeArray(orderDeliveryEntity.getReceiverZipCode());
        deliveryModel.setReceiverZipCode1(zipCodes[0]);
        deliveryModel.setReceiverZipCode2(zipCodes[1]);

        deliveryModel.setReceiverAddress1(orderDeliveryEntity.getReceiverAddress1());
        deliveryModel.setReceiverAddress2(orderDeliveryEntity.getReceiverAddress2());
        deliveryModel.setReceiverAddress3(conversionUtility.toSpaceConnect(orderDeliveryEntity.getReceiverAddress3(),
                                                                           orderDeliveryEntity.getReceiverAddress4()
                                                                          ));
    }

    // 2023-renew No14 from here

    /**
     * セールde予約情報 Item を作成（注文_セールde予約画面へのデータ渡し用）
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param deliveryModel 配送方法選択画面Model
     */
    public ReserveItem toReserveItem(OrderCommonModel orderCommonModel, DeliveryModel deliveryModel) {

        // 変更対象のセールde予約商品の商品コードを取得
        String goodsCode = deliveryModel.getGoodsCodeIndex();

        ReserveItem reserveItem = ApplicationContextUtility.getBean(ReserveItem.class);

        // 商品コードをセット
        reserveItem.setGoodsCode(goodsCode);

        // セールde予約情報リストをセット
        List<ReserveDetailItem> reserveDetailItemList = new ArrayList<>();
        if (deliveryModel.getReserveDeliveryItemMap().containsKey(goodsCode)) {
            deliveryModel.getReserveDeliveryItemMap().get(goodsCode).forEach(item -> {
                ReserveDetailItem reserveDetailItem = ApplicationContextUtility.getBean(ReserveDetailItem.class);
                reserveDetailItem.setReserveDeliveryDate(dateUtility.formatYmdWithSlash(item.getReserveDeliveryDate()));
                reserveDetailItem.setInputGoodsCount(String.valueOf(item.getOrderGoodsCount()));
                reserveDetailItemList.add(reserveDetailItem);
            });
        }
        reserveItem.setReserveDetailItemList(reserveDetailItemList);

        // 変更対象の商品がセールde予約以外にも存在する場合、数量を集計してセット（チェック用）
        BigDecimal inputDeliveryNowGoodsCount = BigDecimal.ZERO;
        if (deliveryModel.isViewDeliveryNowGoods()) {
            for (DeliveryNowItem deliveryNowItem : deliveryModel.getDeliveryNowItems()) {
                if (goodsCode.equals(deliveryNowItem.getGoodsCode())) {
                    inputDeliveryNowGoodsCount = inputDeliveryNowGoodsCount.add(
                                    new BigDecimal(deliveryNowItem.getOrderGoodsCount()));
                }
            }
        }
        if (deliveryModel.isViewDependingOnReceipt()) {
            for (DependingOnReceiptItem dependingOnReceiptItem : deliveryModel.getDependingOnReceiptItems()) {
                if (goodsCode.equals(dependingOnReceiptItem.getGoodsCode())) {
                    inputDeliveryNowGoodsCount =
                                    inputDeliveryNowGoodsCount.add(dependingOnReceiptItem.getOrderGoodsCount());
                }
            }
        }
        reserveItem.setInputDeliveryNowGoodsCount(inputDeliveryNowGoodsCount);

        return reserveItem;
    }

    /**
     * お取りおき分のページ表示項目を注文_セールde予約画面からの引継ぎデータから再生成する
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param deliveryModel 配送方法選択画面Model
     * @param reserveItem セールde予約情報Item
     */
    public void toPageReserveDeliveryItemsForReserveItem(OrderCommonModel orderCommonModel,
                                                         DeliveryModel deliveryModel,
                                                         ReserveItem reserveItem) {

        // 変更対象のセールde予約商品のReserveDeliveryItemをデフォルトデータとして取得
        // ※セールde予約画面遷移前の1件目を取得
        ReserveDeliveryItem defaultReserveDeliveryItem =
                        deliveryModel.getReserveDeliveryItemMap().get(reserveItem.getGoodsCode()).get(0);

        // 注文_セールde予約画面からの引継ぎデータからリストを再生成
        List<ReserveDeliveryItem> newReserveDeliveryItemList = new ArrayList<>();
        for (ReserveDetailItem reserveDetailItem : reserveItem.getReserveDetailItemList()) {
            ReserveDeliveryItem reserveDeliveryItem = CopyUtil.deepCopy(defaultReserveDeliveryItem);
            // 商品数量
            reserveDeliveryItem.setOrderGoodsCount(new BigDecimal(reserveDetailItem.getInputGoodsCount()));
            // お届け希望日
            reserveDeliveryItem.setReserveDeliveryDate(
                            dateUtility.toTimestampValue(reserveDetailItem.getReserveDeliveryDate(),
                                                         DateUtility.YMD_SLASH
                                                        ));
            newReserveDeliveryItemList.add(reserveDeliveryItem);
        }

        // セールde予約アイテムMAPを書き換える
        deliveryModel.getReserveDeliveryItemMap().replace(reserveItem.getGoodsCode(), newReserveDeliveryItemList);
    }

    // 2023-renew No14 to here

    /**
     * 受注配送Dtoへの変換処理
     * Model ⇒ 受注配送Dto
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param deliveryModel 配送方法選択画面Model
     */
    public void toOrderDeliveryMethodForSettlementMethod(OrderCommonModel orderCommonModel,
                                                         DeliveryModel deliveryModel) {

        // 今すぐお届け分
        forPageDeliveryNowItems(orderCommonModel, deliveryModel);

        // 取りおき分
        forPageReserveDeliveryItems(orderCommonModel, deliveryModel);

        // 入荷次第お届け分
        forPageDependingOnReceiptItems(orderCommonModel, deliveryModel);

        // 配送情報を設定
        forPageDeliveryInformation(orderCommonModel, deliveryModel);

    }

    /**
     * 受注配送Dtoへの変換処理
     * 今すぐお届け分
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param deliveryModel 配送方法選択画面Model
     */
    public void forPageDeliveryNowItems(OrderCommonModel orderCommonModel, DeliveryModel deliveryModel) {

        // 今すぐお届け分が存在しない場合は処理終了
        if (!deliveryModel.isViewDeliveryNowGoods()) {
            return;
        }

        // 2023-renew No14 from here
        OrderDeliveryDto orderDeliveryDto = orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto();

        // 画面入力した数量で上書き
        // ※入荷次第お届けに同じ商品が存在しても構わず上書きしてOK（forPageDependingOnReceiptItemsで考慮するため）
        for (DeliveryNowItem deliveryNowItem : deliveryModel.getDeliveryNowItems()) {
            // 受注配送DTO.受注商品EntityList
            for (OrderGoodsEntity entity : orderDeliveryDto.getOrderGoodsEntityList()) {
                if (entity.getGoodsSeq().equals(deliveryNowItem.getGoodsSeq()) && !HTypeReserveDeliveryFlag.ON.equals(
                                entity.getReserveFlag())) {
                    entity.setGoodsCount(new BigDecimal(deliveryNowItem.getOrderGoodsCount()));
                    break;
                }
            }
        }
        // 2023-renew No14 to here
    }

    /**
     * 受注配送Dtoへの変換処理
     * 取りおき分
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param deliveryModel 配送方法選択画面Model
     */
    public void forPageReserveDeliveryItems(OrderCommonModel orderCommonModel, DeliveryModel deliveryModel) {
        // 2023-renew No14 from here
        // 取りおき分が存在しない場合は処理終了
        if (!deliveryModel.isViewReserveDeliveryGoods()) {
            return;
        }

        // 受注商品エンティティリストの再生成（セールde予約の編集内容を反映）
        List<OrderGoodsEntity> newOrderGoodsEntityList = new ArrayList<>();
        Set<String> goodsCodes = new HashSet<>();
        Map<String, List<ReserveDeliveryItem>> reserveDeliveryItemMap = deliveryModel.getReserveDeliveryItemMap();

        for (OrderGoodsEntity entity : orderCommonModel.getReceiveOrderDto()
                                                       .getOrderDeliveryDto()
                                                       .getOrderGoodsEntityList()) {
            String goodsCode = entity.getGoodsCode();
            if (HTypeReserveDeliveryFlag.ON.equals(entity.getReserveFlag()) && reserveDeliveryItemMap.containsKey(
                            goodsCode)) {
                if (goodsCodes.contains(goodsCode)) {
                    // 処理済の場合はスキップ
                    continue;
                }

                // 画面上のセールde予約商品から該当する商品コードの受注商品エンティティを再生成
                for (ReserveDeliveryItem reserveDeliveryItem : reserveDeliveryItemMap.get(goodsCode)) {
                    OrderGoodsEntity newOrderGoodsEntity = CopyUtil.deepCopy(entity);
                    // 商品数量
                    newOrderGoodsEntity.setGoodsCount(reserveDeliveryItem.getOrderGoodsCount());
                    // 取りおきお届け希望日
                    newOrderGoodsEntity.setReserveDeliveryDate(reserveDeliveryItem.getReserveDeliveryDate());
                    // 新規リストに追加
                    newOrderGoodsEntityList.add(newOrderGoodsEntity);
                }
                // 処理済みとして商品コードを保持
                goodsCodes.add(goodsCode);

            } else {
                // セールde予約商品以外の場合、新規リストにそのまま追加
                newOrderGoodsEntityList.add(entity);

            }
        }
        orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto().setOrderGoodsEntityList(newOrderGoodsEntityList);
        // 2023-renew No14 to here
    }

    /**
     * 受注配送Dtoへの変換処理
     * 入荷次第お届け分
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param deliveryModel 配送方法選択画面Model
     */
    public void forPageDependingOnReceiptItems(OrderCommonModel orderCommonModel, DeliveryModel deliveryModel) {

        // 入荷次第お届け分が存在しない場合は処理終了
        if (!deliveryModel.isViewDependingOnReceipt() || !deliveryModel.isViewDeliveryNowGoods()) {
            return;
        }

        // 2023-renew No14 from here
        OrderDeliveryDto orderDeliveryDto = orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto();

        // 入荷次第お届け分を受注商品Entityに反映する（forPageDeliveryNowItemsにて画面入力値で上書きした分の後始末）
        // ※在庫チェック時に元数量でチェックが必要なため
        for (DependingOnReceiptItem dependingOnReceiptItem : deliveryModel.getDependingOnReceiptItems()) {
            for (DeliveryNowItem deliveryNowItem : deliveryModel.getDeliveryNowItems()) {
                if (deliveryNowItem.getGoodsSeq().equals(dependingOnReceiptItem.getGoodsSeq())) {
                    // 受注配送DTO.受注商品EntityList
                    for (OrderGoodsEntity entity : orderDeliveryDto.getOrderGoodsEntityList()) {
                        if (entity.getGoodsSeq().equals(dependingOnReceiptItem.getGoodsSeq())
                            && !HTypeReserveDeliveryFlag.ON.equals(entity.getReserveFlag())) {
                            entity.setGoodsCount(
                                            entity.getGoodsCount().add(dependingOnReceiptItem.getOrderGoodsCount()));
                            break;
                        }
                    }
                }
            }
        }
        // 2023-renew No14 to here
    }

    /**
     * 配送情報を設定します
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param deliveryModel 配送方法選択画面Model
     */
    public void forPageDeliveryInformation(OrderCommonModel orderCommonModel, DeliveryModel deliveryModel) {

        // 2023-renew No14 from here
        // 今すぐお届け分が存在しない場合、処理終了
        if (!deliveryModel.isViewDeliveryNowGoods()) {
            return;
        }
        // 2023-renew No14 to here

        OrderDeliveryDto orderDeliveryDto = orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto();
        // 配送情報取得 詳細情報が存在しない場合
        if (orderDeliveryDto.getDeliveryInformationDetailDto() == null) {
            // お届け希望日、お届け時間帯をnullで設定
            orderDeliveryDto.getOrderDeliveryEntity().setReceiverDate(null);
            orderDeliveryDto.getOrderDeliveryEntity().setReceiverTimeZone(null);
            return;
        }

        // お届け予定日
        orderDeliveryDto.getOrderDeliveryEntity()
                        .setReceiverDate(
                                        dateUtility.toTimestampValue(deliveryModel.getDeliveryDate(), DateUtility.YMD));

        // お届け時間帯
        if (deliveryModel.isViewReceiverTimeZoneYamato()) {
            orderDeliveryDto.getOrderDeliveryEntity().setReceiverTimeZone(deliveryModel.getReceiverTimeZoneYamato());
        } else if (deliveryModel.isViewReceiverTimeZoneJapanPost()) {
            orderDeliveryDto.getOrderDeliveryEntity().setReceiverTimeZone(deliveryModel.getReceiverTimeZoneJapanPost());
        }
    }

    // PDR Migrate Customization to here

}
