// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.history;

import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.AbstractWebApiResponseResultDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.GoodsGroupImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.GoodsImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetNotYetShippingOrderHistoryResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetNotYetShippingOrderHistoryResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPreShipmentOrderHistoryResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPreShipmentOrderHistoryResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiOrderHistoryResponseGoodsInfoDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCoolSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDentalMonopolySalesFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDisplayStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsClassType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeLandSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.delivery.ReceiverDateDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.AbstractWebApiResponseResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetNotYetShippingOrderHistoryResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetNotYetShippingOrderHistoryResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetPreShipmentOrderHistoryResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetPreShipmentOrderHistoryResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiOrderHistoryResponseGoodsInfoDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiShipmentOrderHistoryResponseBaseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 注文履歴 Helper
 *
 * @author kimura
 */
@Component
public class BaseShipHelper {

    /**
     * 共通情報
     */
    protected CommonInfo commonInfo;

    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    /**
     * コンストラクタ
     *
     * @param conversionUtility 変換ユーティリティクラス
     */
    @Autowired
    public BaseShipHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * Web-APIから取得した情報をページに反映する
     *
     * @param shippedOrderHistoryInfoDtoList 注文履歴（出荷済）情報リスト
     * @param baseShipModel                  注文履歴（出荷済） Model
     */
    public void toPageForShippedOrderHistory(List<WebApiGetPreShipmentOrderHistoryResponseDetailDto> shippedOrderHistoryInfoDtoList,
                                             BaseShipModel baseShipModel) {

        // 共通情報取得
        this.commonInfo = baseShipModel.getCommonInfo();
        // 注文履歴情報詳細アイテムリストの作成
        List<OrderHistoryOrderItem> orderHistoryItemList = new ArrayList<>();

        for (WebApiShipmentOrderHistoryResponseBaseDetailDto orderHistoryInfo : shippedOrderHistoryInfoDtoList) {
            orderHistoryItemList.add(createOrderHistoryItem(orderHistoryInfo));
        }
        baseShipModel.setOrderHistoryOrderItems(orderHistoryItemList);
    }

    /**
     * Web-APIから取得した情報をページに反映する
     *
     * @param unshippedOrderHistoryInfoDtoList 注文履歴（未出荷）情報リスト
     * @param baseShipModel                    注文履歴（未出荷） Model
     * @param displayByGoodsCode               表示フラグ
     */
    public void toPageForUnshippedOrderHistory(List<WebApiGetNotYetShippingOrderHistoryResponseDetailDto> unshippedOrderHistoryInfoDtoList,
                                               BaseShipModel baseShipModel,
                                               boolean displayByGoodsCode) {
        // 共通情報取得
        this.commonInfo = baseShipModel.getCommonInfo();
        // 2024-renew No06 from here
        if (displayByGoodsCode) {
            baseShipModel.setOrderHistoryOrderItems(null);
            baseShipModel.setOrderHistoryOrderItemByGoodsCodeMap(
                            toPageForUnshippedByGcd(unshippedOrderHistoryInfoDtoList));
        } else {
            baseShipModel.setOrderHistoryOrderItems(toPageForUnshippedByDate(unshippedOrderHistoryInfoDtoList));
            baseShipModel.setOrderHistoryOrderItemByGoodsCodeMap(null);
        }
        // 2024-renew No06 to here
    }

    /**
     * 注文履歴詳細情報アイテムMAPに変換
     * <pre>商品別一覧の注文履歴一覧を作成</pre>
     *
     * @param unshippedOrderHistoryInfoDtoList 注文履歴（未配送）取得 詳細情報
     * @return 詳細情報アイテムMAP
     */
    // 2024-renew No06 from here
    protected Map<String, List<OrderHistoryOrderItem>> toPageForUnshippedByGcd(List<WebApiGetNotYetShippingOrderHistoryResponseDetailDto> unshippedOrderHistoryInfoDtoList) {

        List<OrderHistoryOrderItem> orderHistoryOrderItems = toPageForUnshippedByDate(unshippedOrderHistoryInfoDtoList);

        Map<String, List<OrderHistoryOrderItem>> orderHistoryOrderItemByGoodsCodeMap = new HashMap<>();

        List<OrderHistoryOrderItem> newOrderHistoryOrderItems = new ArrayList<>();

        for (OrderHistoryOrderItem orderHistoryItem : orderHistoryOrderItems) {
            for (OrderHistoryGoodsItem goodsItem : orderHistoryItem.getOrderHistoryGoodsItems()) {
                OrderHistoryOrderItem newOrderHistoryOrderItem = new OrderHistoryOrderItem();
                BeanUtils.copyProperties(orderHistoryItem, newOrderHistoryOrderItem);

                newOrderHistoryOrderItem.setGoodsItem(goodsItem);

                newOrderHistoryOrderItems.add(newOrderHistoryOrderItem);
            }
        }

        newOrderHistoryOrderItems = newOrderHistoryOrderItems.stream()
                                                             .sorted(Comparator.comparing(
                                                                             OrderHistoryOrderItem::getReceiveDate))
                                                             .collect(Collectors.toList());

        newOrderHistoryOrderItems.forEach(item -> {
            OrderHistoryGoodsItem orderHistoryGoodsItem = item.getGoodsItem();

            List<OrderHistoryOrderItem> orderHistoryItemList =
                            orderHistoryOrderItemByGoodsCodeMap.get(orderHistoryGoodsItem.getGoodsCode());

            if (CollectionUtil.isEmpty(orderHistoryItemList)) {
                orderHistoryItemList = new ArrayList<>();
            }

            orderHistoryItemList.add(item);
            orderHistoryOrderItemByGoodsCodeMap.put(orderHistoryGoodsItem.getGoodsCode(), orderHistoryItemList);
        });

        return orderHistoryOrderItemByGoodsCodeMap;
    }

    /**
     * 注文履歴詳細情報アイテムリストに変換
     * <pre>お届け予定日別一覧の注文履歴一覧を作成</pre>
     *
     * @param unshippedOrderHistoryInfoDtoList 注文履歴（未配送）取得 詳細情報リスト
     * @return 詳細情報アイテムリスト
     */

    protected List<OrderHistoryOrderItem> toPageForUnshippedByDate(List<WebApiGetNotYetShippingOrderHistoryResponseDetailDto> unshippedOrderHistoryInfoDtoList) {

        List<OrderHistoryOrderItem> orderHistoryItemList = new ArrayList<>();

        for (WebApiShipmentOrderHistoryResponseBaseDetailDto orderHistoryInfo : unshippedOrderHistoryInfoDtoList) {
            orderHistoryItemList.add(createOrderHistoryItem(orderHistoryInfo));
        }

        return orderHistoryItemList;
    }
    // 2024-renew No06 to here

    /**
     * 注文履歴情報アイテムページクラスにWeb-API取得情報を反映
     *
     * @param orderHistoryInfo 注文履歴情報
     * @return 注文履歴情報ページアイテムクラス
     */
    protected OrderHistoryOrderItem createOrderHistoryItem(WebApiShipmentOrderHistoryResponseBaseDetailDto orderHistoryInfo) {

        // 注文履歴情報アイテムクラス
        OrderHistoryOrderItem item = ApplicationContextUtility.getBean(OrderHistoryOrderItem.class);
        // 詳細情報セット
        setOrderHistoryInfo(item, orderHistoryInfo);

        // 注文履歴商品アイテムリスト
        List<OrderHistoryGoodsItem> orderHistoryGoodsItemList = new ArrayList<>();
        // 商品詳細情報マップ取得
        Map<String, GoodsDetailsDto> goodsDetailsMap = orderHistoryInfo.getGoodsDetailsMap();

        // 商品情報リストから情報を取り出す
        for (WebApiOrderHistoryResponseGoodsInfoDto orderGoodsInfo : orderHistoryInfo.getGoodsList()) {
            orderHistoryGoodsItemList.add(createOrderGoodsItem(orderGoodsInfo, goodsDetailsMap));
        }
        // 商品情報セット
        item.setOrderHistoryGoodsItems(orderHistoryGoodsItemList);

        return item;
    }

    /**
     * 注文履歴詳細情報を画面に反映
     * 出荷済情報、未出荷情報で追加した情報がある場合は継承したクラスでこのメソッドをOverrideして追加を行う
     *
     * @param item             注文履歴情報ページアイテムクラス
     * @param orderHistoryInfo 注文履歴詳細情報
     */
    protected void setOrderHistoryInfo(OrderHistoryOrderItem item,
                                       WebApiShipmentOrderHistoryResponseBaseDetailDto orderHistoryInfo) {

        // 日付Utility取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 注文日時
        item.setOrderTime(orderHistoryInfo.getOrderDate());
        // 注文番号
        item.setOrderCode(orderHistoryInfo.getOrderNo().toString());
        // 合計金額
        item.setPaymentTotalPrice(orderHistoryInfo.getPaymentTotalPrice());
        // 受付
        item.setReceptionTypeName(orderHistoryInfo.getReceptionTypeName());
        // お届け先事業所名
        item.setReceiveOfficeName(orderHistoryInfo.getReceiveOfficeName());
        // お届け先郵便番号
        item.setReceiveZipCode(orderHistoryInfo.getReceiveZipcode());
        // お届け先住所(都道府県・市区町村)
        item.setReceiveAddress1(orderHistoryInfo.getReceiveAddress1());
        // お届け先住所(丁目・番地)
        item.setReceiveAddress2(orderHistoryInfo.getReceiveAddress2());
        // お届け先住所(建物名・部屋番号)
        item.setReceiveAddress3(orderHistoryInfo.getReceiveAddress3());
        // お届け先住所(方書1)
        item.setReceiveAddress4(orderHistoryInfo.getReceiveAddress4());
        // お届け先住所(方書2)
        item.setReceiveAddress5(orderHistoryInfo.getReceiveAddress5());
        // お届け日
        if (orderHistoryInfo.getReceiveDate() != null) {
            // 存在する場合は設定
            item.setReceiveDate(dateUtility.formatYmdWithSlash(orderHistoryInfo.getReceiveDate())
                                + ReceiverDateDto.getDayOfTheWeek(orderHistoryInfo.getReceiveDate()));
        } else {
            // 存在しない場合は固定文言
            item.setReceiveDate(BaseShipModel.RECEIVEDATE_PENDING);
        }
        // お支払い方法
        item.setPaymentType(orderHistoryInfo.getPaymentType());

        // 2023-renew No24 from here
        // クーポンコード
        item.setCouponCode(orderHistoryInfo.getCouponNo());
        // クーポン名
        item.setCouponName(orderHistoryInfo.getCouponName());
        // 2023-renew No24 to here

    }

    /**
     * 注文履歴詳細情報商品ページアイテムクラスにWeb-API取得情報を反映
     *
     * @param orderGoodsInfo  注文履歴商品情報
     * @param goodsDetailsMap 商品詳細情報マップ
     * @return 注文履歴情報商品ページアイテムクラス
     */
    protected OrderHistoryGoodsItem createOrderGoodsItem(WebApiOrderHistoryResponseGoodsInfoDto orderGoodsInfo,
                                                         Map<String, GoodsDetailsDto> goodsDetailsMap) {

        // 注文履歴情報商品ページアイテムクラス
        OrderHistoryGoodsItem goodsItem = ApplicationContextUtility.getBean(OrderHistoryGoodsItem.class);

        // 商品がDBに存在するかどうか(初期値:存在しない)
        goodsItem.setGoodsPresenceFlag(false);

        // 選択商品コード
        goodsItem.setGcd(orderGoodsInfo.getGoodsCode());
        // 申込商品
        goodsItem.setGoodsCode(orderGoodsInfo.getGoodsCode());
        // 商品名
        goodsItem.setGoodsName(orderGoodsInfo.getGoodsName());
        // 数量
        goodsItem.setGoodsCount(orderGoodsInfo.getGoodsCount());
        // 単位
        goodsItem.setUnitName(orderGoodsInfo.getUnitName());
        // 適用割引
        goodsItem.setDiscountFlag(orderGoodsInfo.getDiscountFlag());

        // 商品詳細情報がない(EC側DBに商品が存在しない)
        if (goodsDetailsMap.isEmpty()) {
            return goodsItem;
        }

        // 商品系Helper取得
        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);

        // 商品コードをキーに商品詳細情報を取得
        GoodsDetailsDto goodsDetailsDto = goodsDetailsMap.get(orderGoodsInfo.getGoodsCode());
        if (goodsDetailsDto != null) {
            // 公開状態
            goodsItem.setOpenDeleteStatus(goodsDetailsDto.getGoodsOpenStatusPC());
            // 販売状態
            goodsItem.setSaleStatus(goodsDetailsDto.getSaleStatusPC());
            // 販売可能区分
            // 2023-renew No2 from here

            // 2023-renew No2 to here

            // 商品がDBに存在する
            goodsItem.setGoodsPresenceFlag(true);

            // 2024-renew No06 from here
            if (CollectionUtil.isNotEmpty(goodsDetailsDto.getGoodsGroupImageEntityList())) {
                // 商品画像
                String imageFileName = goodsUtility.getImageFileName(goodsDetailsDto);
                String goodsImageSrc = goodsUtility.getGoodsImagePath(imageFileName);
                goodsItem.setGoodsImage(goodsImageSrc);
            }
            // 2024-renew No06 to here
        }
        return goodsItem;
    }

    /**
     * WEB-API連携取得結果DTOクラスに反映
     * 注文履歴（出荷済）取得
     *
     * @param res 注文履歴（出荷済）取得Web-APIレスポンス
     * @return WEB-API連携取得結果DTOクラス
     */
    public WebApiGetPreShipmentOrderHistoryResponseDto toWebApiGetPreShipmentOrderHistoryResponseDto(
                    WebApiGetPreShipmentOrderHistoryResponse res) {
        if (ObjectUtils.isEmpty(res)) {
            return null;
        }

        WebApiGetPreShipmentOrderHistoryResponseDto response = new WebApiGetPreShipmentOrderHistoryResponseDto();
        response.setInfo(toWebApiGetPreShipmentOrderHistoryResponseDetailDto(res.getInfo()));
        response.setResult(toAbstractWebApiResponseResultDto(res.getResult()));
        return response;
    }

    /**
     * WEB-API連携取得結果DTOクラスに反映
     * 注文履歴（未配送）取得
     *
     * @param res 注文履歴（未出荷）取得Web-APIレスポンス
     * @return WEB-API連携取得結果DTOクラス
     */
    public WebApiGetNotYetShippingOrderHistoryResponseDto toWebApiGetNotYetShippingOrderHistoryResponseDto(
                    WebApiGetNotYetShippingOrderHistoryResponse res) {
        if (ObjectUtils.isEmpty(res)) {
            return null;
        }

        WebApiGetNotYetShippingOrderHistoryResponseDto response = new WebApiGetNotYetShippingOrderHistoryResponseDto();
        response.setInfo(toWebApiGetNotYetShippingOrderHistoryResponseDetailDto(res.getInfo()));
        response.setResult(toAbstractWebApiResponseResultDto(res.getResult()));
        return response;
    }

    /**
     * WEB-API連携取得結果DTOクラスに反映
     * 注文履歴（未配送）取得 詳細情報
     *
     * @param responseList 注文履歴（未出荷）取得Web-APIレスポンス
     * @return WEB-API連携取得結果DTOクラス
     */
    private List<WebApiGetNotYetShippingOrderHistoryResponseDetailDto> toWebApiGetNotYetShippingOrderHistoryResponseDetailDto(
                    List<WebApiGetNotYetShippingOrderHistoryResponseDetailDtoResponse> responseList) {

        if (CollectionUtil.isEmpty(responseList)) {
            return new ArrayList<>();
        }

        List<WebApiGetNotYetShippingOrderHistoryResponseDetailDto> list = new ArrayList<>();

        for (WebApiGetNotYetShippingOrderHistoryResponseDetailDtoResponse dto : responseList) {
            WebApiGetNotYetShippingOrderHistoryResponseDetailDto res =
                            new WebApiGetNotYetShippingOrderHistoryResponseDetailDto();
            res.setOrderNo(dto.getOrderNo());
            res.setReceptionTypeName(dto.getReceptionTypeName());
            res.setOrderDate(conversionUtility.toTimeStamp(dto.getOrderDate()));
            res.setReceiveOfficeName(dto.getReceiveOfficeName());
            res.setReceiveZipcode(dto.getReceiveZipcode());
            res.setReceiveAddress1(dto.getReceiveAddress1());
            res.setReceiveAddress2(dto.getReceiveAddress2());
            res.setReceiveAddress3(dto.getReceiveAddress3());
            res.setReceiveAddress4(dto.getReceiveAddress4());
            res.setReceiveAddress5(dto.getReceiveAddress5());
            res.setReceiveDate(conversionUtility.toTimeStamp(dto.getReceiveDate()));
            res.setPaymentType(dto.getPaymentType());
            res.setPaymentTotalPrice(dto.getPaymentTotalPrice());
            // 2023-renew No24 from here
            res.setCouponNo(dto.getCouponNo());
            res.setCouponName(dto.getCouponName());
            // 2023-renew No24 to here
            // 2023-renew No68 from here
            res.setCancelYesNo(dto.getCancelYesNo());
            // 2023-renew No68 to here
            res.setGoodsList(toWebApiOrderHistoryResponseGoodsInfoDto(dto.getGoodsList()));
            res.setGoodsDetailsMap(toGoodsDetailsDtoResponse(dto.getGoodsDetailsMap()));
            list.add(res);
        }

        return list;
    }

    /**
     * WEB-API連携取得結果DTOクラスに反映
     * 注文履歴（出荷済）取得 詳細情報
     *
     * @param responseList WEB-API連携取得結果DTOレスポンス
     * @return WEB-API連携取得結果DTOクラス
     */
    private List<WebApiGetPreShipmentOrderHistoryResponseDetailDto> toWebApiGetPreShipmentOrderHistoryResponseDetailDto(
                    List<WebApiGetPreShipmentOrderHistoryResponseDetailDtoResponse> responseList) {

        if (CollectionUtil.isEmpty(responseList)) {
            return new ArrayList<>();
        }

        List<WebApiGetPreShipmentOrderHistoryResponseDetailDto> list = new ArrayList<>();

        for (WebApiGetPreShipmentOrderHistoryResponseDetailDtoResponse dto : responseList) {
            WebApiGetPreShipmentOrderHistoryResponseDetailDto res =
                            new WebApiGetPreShipmentOrderHistoryResponseDetailDto();
            res.setOrderNo(dto.getOrderNo());
            res.setReceptionTypeName(dto.getReceptionTypeName());
            res.setOrderDate(conversionUtility.toTimeStamp(dto.getOrderDate()));
            res.setReceiveOfficeName(dto.getReceiveOfficeName());
            res.setReceiveZipcode(dto.getReceiveZipcode());
            res.setReceiveAddress1(dto.getReceiveAddress1());
            res.setReceiveAddress2(dto.getReceiveAddress2());
            res.setReceiveAddress3(dto.getReceiveAddress3());
            res.setReceiveAddress4(dto.getReceiveAddress4());
            res.setReceiveAddress5(dto.getReceiveAddress5());
            res.setReceiveDate(conversionUtility.toTimeStamp(dto.getReceiveDate()));
            res.setPaymentType(dto.getPaymentType());
            res.setPaymentTotalPrice(dto.getPaymentTotalPrice());
            // 2023-renew No24 from here
            res.setCouponNo(dto.getCouponNo());
            res.setCouponName(dto.getCouponName());
            // 2023-renew No24 to here
            res.setGoodsList(toWebApiOrderHistoryResponseGoodsInfoDto(dto.getGoodsList()));
            res.setGoodsDetailsMap(toGoodsDetailsDtoResponse(dto.getGoodsDetailsMap()));
            list.add(res);
        }

        return list;
    }

    /**
     * 商品詳細Dtoクラスに反映
     *
     * @param goodsDetailsMap 商品詳細Dtoクラスレスポンス
     * @return 商品詳細DtoクラスMap
     */
    public Map<String, GoodsDetailsDto> toGoodsDetailsDtoResponse(Map<String, GoodsDetailsDtoResponse> goodsDetailsMap) {
        if (goodsDetailsMap == null) {
            return new HashMap<>();
        }

        Map<String, GoodsDetailsDto> map = new HashMap<>();

        for (Map.Entry<String, GoodsDetailsDtoResponse> entry : goodsDetailsMap.entrySet()) {
            String newKey = entry.getKey();
            GoodsDetailsDto newValue = toGoodsDetailsDto(entry.getValue());
            map.put(newKey, newValue);
        }

        return map;
    }

    /**
     * 商品詳細Dtoクラスに反映
     *
     * @param dtoResponse 商品詳細Dtoクラスレスポンス
     * @return 商品詳細Dtoクラス
     */
    public GoodsDetailsDto toGoodsDetailsDto(GoodsDetailsDtoResponse dtoResponse) {
        if (dtoResponse == null) {
            return null;
        }

        GoodsDetailsDto response = new GoodsDetailsDto();
        response.setGoodsSeq(dtoResponse.getGoodsSeq());
        response.setGoodsGroupSeq(dtoResponse.getGoodsGroupSeq());
        response.setVersionNo(dtoResponse.getVersionNo());
        response.setRegistTime(conversionUtility.toTimeStamp(dtoResponse.getRegistTime()));
        response.setUpdateTime(conversionUtility.toTimeStamp(dtoResponse.getUpdateTime()));
        response.setGoodsCode(dtoResponse.getGoodsCode());
        response.setGoodsGroupMaxPrice(dtoResponse.getGoodsGroupMaxPrice());
        response.setGoodsGroupMinPrice(dtoResponse.getGoodsGroupMinPrice());
        response.setPreDiscountMinPrice(dtoResponse.getPreDiscountMinPrice());
        response.setPreDiscountMaxPrice(dtoResponse.getPreDiscountMaxPrice());
        response.setGoodsTaxType(EnumTypeUtil.getEnumFromValue(HTypeGoodsTaxType.class, dtoResponse.getGoodsTaxType()));
        response.setTaxRate(dtoResponse.getTaxRate());
        response.setAlcoholFlag(EnumTypeUtil.getEnumFromValue(HTypeAlcoholFlag.class, dtoResponse.getAlcoholFlag()));
        response.setGoodsPriceInTax(dtoResponse.getGoodsPriceInTax());
        response.setGoodsPrice(dtoResponse.getGoodsPrice());
        response.setDeliveryType(dtoResponse.getDeliveryType());
        response.setSaleStatusPC(
                        EnumTypeUtil.getEnumFromValue(HTypeGoodsSaleStatus.class, dtoResponse.getSaleStatus()));
        response.setSaleStartTimePC(conversionUtility.toTimeStamp(dtoResponse.getSaleStartTime()));
        response.setSaleEndTimePC(conversionUtility.toTimeStamp(dtoResponse.getSaleEndTime()));
        response.setUnitManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class,
                                                                     dtoResponse.getUnitManagementFlag()
                                                                    ));
        response.setStockManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeStockManagementFlag.class,
                                                                      dtoResponse.getStockManagementFlag()
                                                                     ));
        response.setIndividualDeliveryType(EnumTypeUtil.getEnumFromValue(HTypeIndividualDeliveryType.class,
                                                                         dtoResponse.getIndividualDeliveryType()
                                                                        ));
        response.setPurchasedMax(dtoResponse.getPurchasedMax());
        response.setFreeDeliveryFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeFreeDeliveryFlag.class, dtoResponse.getFreeDeliveryFlag()));
        response.setOrderDisplay(dtoResponse.getOrderDisplay());
        response.setUnitValue1(dtoResponse.getUnitValue1());
        response.setUnitValue2(dtoResponse.getUnitValue2());
        response.setPreDiscountPrice(dtoResponse.getPreDiscountPrice());
        response.setPreDisCountPriceInTax(dtoResponse.getPreDisCountPriceInTax());
        response.setJanCode(dtoResponse.getJanCode());
        response.setCatalogCode(dtoResponse.getCatalogCode());
        response.setSalesPossibleStock(dtoResponse.getSalesPossibleStock());
        response.setRealStock(dtoResponse.getRealStock());
        response.setOrderReserveStock(dtoResponse.getOrderReserveStock());
        response.setRemainderFewStock(dtoResponse.getRemainderFewStock());
        response.setOrderPointStock(dtoResponse.getOrderPointStock());
        response.setSafetyStock(dtoResponse.getSafetyStock());
        response.setGoodsGroupCode(dtoResponse.getGoodsGroupCode());
        response.setWhatsnewDate(conversionUtility.toTimeStamp(dtoResponse.getWhatsnewDate()));
        response.setGoodsOpenStatusPC(
                        EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class, dtoResponse.getGoodsOpenStatus()));
        response.setOpenStartTimePC(conversionUtility.toTimeStamp(dtoResponse.getOpenStartTime()));
        response.setOpenEndTimePC(conversionUtility.toTimeStamp(dtoResponse.getOpenEndTime()));
        response.setGoodsGroupName(dtoResponse.getGoodsGroupName());
        response.setUnitTitle1(dtoResponse.getUnitTitle1());
        response.setUnitTitle2(dtoResponse.getUnitTitle2());
        response.setGoodsPreDiscountPrice(dtoResponse.getGoodsPreDiscountPrice());
        response.setGoodsGroupImageEntityList(
                        toGoodsGroupImageEntityResponse(dtoResponse.getGoodsGroupImageEntityList()));
        response.setSnsLinkFlag(EnumTypeUtil.getEnumFromValue(HTypeSnsLinkFlag.class, dtoResponse.getSnsLinkFlag()));
        response.setMetaDescription(dtoResponse.getMetaDescription());
        response.setStockStatusPc(
                        EnumTypeUtil.getEnumFromValue(HTypeStockStatusType.class, dtoResponse.getStockStatus()));
        response.setGoodsNote1(dtoResponse.getGoodsNote1());
        response.setGoodsNote2(dtoResponse.getGoodsNote2());
        response.setGoodsNote3(dtoResponse.getGoodsNote3());
        response.setGoodsNote4(dtoResponse.getGoodsNote4());
        response.setGoodsNote5(dtoResponse.getGoodsNote5());
        response.setGoodsNote6(dtoResponse.getGoodsNote6());
        response.setGoodsNote7(dtoResponse.getGoodsNote7());
        response.setGoodsNote8(dtoResponse.getGoodsNote8());
        response.setGoodsNote9(dtoResponse.getGoodsNote9());
        response.setGoodsNote10(dtoResponse.getGoodsNote10());
        response.setOrderSetting1(dtoResponse.getOrderSetting1());
        response.setOrderSetting2(dtoResponse.getOrderSetting2());
        response.setOrderSetting3(dtoResponse.getOrderSetting3());
        response.setOrderSetting4(dtoResponse.getOrderSetting4());
        response.setOrderSetting5(dtoResponse.getOrderSetting5());
        response.setOrderSetting6(dtoResponse.getOrderSetting6());
        response.setOrderSetting7(dtoResponse.getOrderSetting7());
        response.setOrderSetting8(dtoResponse.getOrderSetting8());
        response.setOrderSetting9(dtoResponse.getOrderSetting9());
        response.setOrderSetting10(dtoResponse.getOrderSetting10());
        response.setUnitImage(toGoodsImageEntityResponse(dtoResponse.getUnitImage()));
        response.setGoodsOptionDisplayName(dtoResponse.getGoodsOptionDisplayName());
        response.setGoodsClassType(
                        EnumTypeUtil.getEnumFromValue(HTypeGoodsClassType.class, dtoResponse.getGoodsClassType()));
        response.setDentalMonopolySalesFlg(EnumTypeUtil.getEnumFromValue(HTypeDentalMonopolySalesFlg.class,
                                                                         dtoResponse.getDentalMonopolySalesFlg()
                                                                        ));
        response.setSaleIconFlag(EnumTypeUtil.getEnumFromValue(HTypeSaleIconFlag.class, dtoResponse.getSaleIconFlag()));
        response.setReserveIconFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeReserveIconFlag.class, dtoResponse.getReserveIconFlag()));
        response.setNewIconFlag(EnumTypeUtil.getEnumFromValue(HTypeNewIconFlag.class, dtoResponse.getNewIconFlag()));
        response.setLandSendFlag(EnumTypeUtil.getEnumFromValue(HTypeLandSendFlag.class, dtoResponse.getLandSendFlag()));
        response.setCoolSendFlag(EnumTypeUtil.getEnumFromValue(HTypeCoolSendFlag.class, dtoResponse.getCoolSendFlag()));
        response.setCoolSendFrom(dtoResponse.getCoolSendFrom());
        response.setCoolSendTo(dtoResponse.getCoolSendTo());
        response.setTax(dtoResponse.getTax());
        response.setUnit(dtoResponse.getUnit());
        response.setGoodsManagementCode(dtoResponse.getGoodsManagementCode());
        response.setGoodsDivisionCode(dtoResponse.getGoodsDivisionCode());
        response.setGoodsCategory1(dtoResponse.getGoodsCategory1());
        response.setGoodsCategory2(dtoResponse.getGoodsCategory2());
        response.setGoodsCategory3(dtoResponse.getGoodsCategory3());
        response.setReserveFlag(EnumTypeUtil.getEnumFromValue(HTypeReserveFlag.class, dtoResponse.getReserveFlag()));
        response.setPriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypePriceMarkDispFlag.class,
                                                                    dtoResponse.getPriceMarkDispFlag()
                                                                   ));
        response.setSalePriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceMarkDispFlag.class,
                                                                        dtoResponse.getSalePriceMarkDispFlag()
                                                                       ));
        response.setSalePriceIntegrityFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceIntegrityFlag.class,
                                                                         dtoResponse.getSalePriceIntegrityFlag()
                                                                        ));
        response.setSaleYesNo(dtoResponse.getSaleYesNo());
        response.setSaleNgMessage(dtoResponse.getSaleNgMessage());
        response.setDeliveryYesNo(dtoResponse.getDeliveryYesNo());
        response.setEmotionPriceType(
                        EnumTypeUtil.getEnumFromValue(HTypeEmotionPriceType.class, dtoResponse.getEmotionPriceType()));
        return response;
    }

    /**
     * 商品グループ画像クラスに反映
     *
     * @param goodsGroupImageEntityResponseList 商品グループ画像レスポンス
     * @return 商品グループ画像クラスList
     */
    private List<GoodsGroupImageEntity> toGoodsGroupImageEntityResponse(List<GoodsGroupImageEntityResponse> goodsGroupImageEntityResponseList) {
        if (CollectionUtils.isEmpty(goodsGroupImageEntityResponseList)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntity> response = new ArrayList<>();

        for (GoodsGroupImageEntityResponse dto : goodsGroupImageEntityResponseList) {
            GoodsGroupImageEntity res = new GoodsGroupImageEntity();
            res.setGoodsGroupSeq(dto.getGoodsGroupSeq());
            res.setRegistTime(conversionUtility.toTimeStamp(dto.getRegistTime()));
            res.setUpdateTime(conversionUtility.toTimeStamp(dto.getUpdateTime()));
            res.setImageFileName(dto.getImageFileName());
            res.setImageTypeVersionNo(dto.getImageTypeVersionNo());
            response.add(res);
        }

        return response;
    }

    /**
     * 商品画像クラスに反映
     *
     * @param goodsImageEntityResponse 商品グループ画像レスポンス
     * @return 商品画像クラス
     */
    private GoodsImageEntity toGoodsImageEntityResponse(GoodsImageEntityResponse goodsImageEntityResponse) {
        if (goodsImageEntityResponse == null) {
            return null;
        }

        GoodsImageEntity response = new GoodsImageEntity();
        response.setGoodsGroupSeq(goodsImageEntityResponse.getGoodsGroupSeq());
        response.setGoodsSeq(goodsImageEntityResponse.getGoodsSeq());
        response.setRegistTime(conversionUtility.toTimeStamp(goodsImageEntityResponse.getRegistTime()));
        response.setImageFileName(goodsImageEntityResponse.getImageFileName());
        response.setUpdateTime(conversionUtility.toTimeStamp(goodsImageEntityResponse.getUpdateTime()));
        response.setDisplayFlag(EnumTypeUtil.getEnumFromValue(HTypeDisplayStatus.class,
                                                              goodsImageEntityResponse.getDisplayFlag()
                                                             ));
        response.setTmpFilePath(goodsImageEntityResponse.getTmpFilePath());
        return response;
    }

    /**
     * WEB-API連携取得結果DTOクラスに反映
     * 注文履歴取得 共通商品情報
     *
     * @param responseList WEB-API連携取得結果DTOクラスレスポンス
     * @return 注文履歴取得 共通商品情報List
     */
    private List<WebApiOrderHistoryResponseGoodsInfoDto> toWebApiOrderHistoryResponseGoodsInfoDto(List<WebApiOrderHistoryResponseGoodsInfoDtoResponse> responseList) {
        if (CollectionUtil.isEmpty(responseList)) {
            return new ArrayList<>();
        }

        List<WebApiOrderHistoryResponseGoodsInfoDto> list = new ArrayList<>();

        for (WebApiOrderHistoryResponseGoodsInfoDtoResponse dto : responseList) {
            WebApiOrderHistoryResponseGoodsInfoDto res = new WebApiOrderHistoryResponseGoodsInfoDto();
            res.setGoodsCount(dto.getGoodsCount());
            res.setGoodsCode(dto.getGoodsCode());
            res.setDiscountFlag(dto.getDiscountFlag());
            res.setGoodsName(dto.getGoodsName());
            res.setUnitName(dto.getUnitName());
            list.add(res);
        }
        return list;
    }

    /**
     * WEB-API連携取得結果DTO共通情報に変換
     *
     * @param abstractWebApiResponseResultDtoResponse API連携取得結果DTO共通情報レスポンス
     * @return WEB-API連携取得結果DTO共通情報
     */
    private AbstractWebApiResponseResultDto toAbstractWebApiResponseResultDto(AbstractWebApiResponseResultDtoResponse abstractWebApiResponseResultDtoResponse) {

        if (ObjectUtils.isEmpty(abstractWebApiResponseResultDtoResponse)) {
            return null;
        }

        AbstractWebApiResponseResultDto abstractWebApiResponseResultDto = new AbstractWebApiResponseResultDto();

        abstractWebApiResponseResultDto.setMessage(abstractWebApiResponseResultDtoResponse.getMessage());
        abstractWebApiResponseResultDto.setStatus(abstractWebApiResponseResultDtoResponse.getStatus());

        return abstractWebApiResponseResultDto;
    }

    /**
     * 商品詳細Dtoクラスに変換
     *
     * @param responseMap 商品詳細DtoクラスレスポンスMap
     * @return 商品詳細DtoクラスMap
     */
    public Map<String, GoodsDetailsDto> goodsDetailsDtoMap(Map<String, GoodsDetailsDtoResponse> responseMap) {
        if (responseMap == null) {
            return new HashMap<>();
        }

        Map<String, GoodsDetailsDto> map = new HashMap<>();
        for (Map.Entry<String, GoodsDetailsDtoResponse> entry : responseMap.entrySet()) {
            String newKey = entry.getKey();
            GoodsDetailsDto newValue = toGoodsDetailsDto(entry.getValue());
            map.put(newKey, newValue);
        }
        return map;
    }

    /**
     * 商品詳細Dtoクラスに変換
     *
     * @param responseMap 商品詳細DtoクラスレスポンスMap
     * @return 商品詳細DtoクラスMap
     */
    public Map<String, GoodsDetailsDto> goodsDetailsDtoMapGoods(Map<String, jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsDtoResponse> responseMap) {
        if (responseMap == null) {
            return new HashMap<>();
        }

        Map<String, GoodsDetailsDto> map = new HashMap<>();
        for (Map.Entry<String, jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsDtoResponse> entry : responseMap.entrySet()) {
            String newKey = entry.getKey();
            GoodsDetailsDto newValue = toGoodsDetailsDtoGoods(entry.getValue());
            map.put(newKey, newValue);
        }
        return map;
    }

    /**
     * 商品詳細Dtoクラスに変換
     *
     * @param dtoResponse 商品詳細Dtoクラスレスポンス
     * @return 商品詳細Dtoクラス
     */
    public GoodsDetailsDto toGoodsDetailsDtoGoods(jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsDtoResponse dtoResponse) {
        if (dtoResponse == null) {
            return null;
        }

        GoodsDetailsDto response = new GoodsDetailsDto();
        response.setGoodsSeq(dtoResponse.getGoodsSeq());
        response.setGoodsGroupSeq(dtoResponse.getGoodsGroupSeq());
        response.setVersionNo(dtoResponse.getVersionNo());
        response.setRegistTime(conversionUtility.toTimeStamp(dtoResponse.getRegistTime()));
        response.setUpdateTime(conversionUtility.toTimeStamp(dtoResponse.getUpdateTime()));
        response.setGoodsCode(dtoResponse.getGoodsCode());
        response.setGoodsGroupMaxPrice(dtoResponse.getGoodsGroupMaxPrice());
        response.setGoodsGroupMinPrice(dtoResponse.getGoodsGroupMinPrice());
        response.setPreDiscountMinPrice(dtoResponse.getPreDiscountMinPrice());
        response.setPreDiscountMaxPrice(dtoResponse.getPreDiscountMaxPrice());
        response.setGoodsTaxType(EnumTypeUtil.getEnumFromValue(HTypeGoodsTaxType.class, dtoResponse.getGoodsTaxType()));
        response.setTaxRate(dtoResponse.getTaxRate());
        response.setAlcoholFlag(EnumTypeUtil.getEnumFromValue(HTypeAlcoholFlag.class, dtoResponse.getAlcoholFlag()));
        response.setGoodsPriceInTax(dtoResponse.getGoodsPriceInTax());
        response.setGoodsPrice(dtoResponse.getGoodsPrice());
        response.setDeliveryType(dtoResponse.getDeliveryType());
        response.setSaleStatusPC(
                        EnumTypeUtil.getEnumFromValue(HTypeGoodsSaleStatus.class, dtoResponse.getSaleStatus()));
        response.setSaleStartTimePC(conversionUtility.toTimeStamp(dtoResponse.getSaleStartTime()));
        response.setSaleEndTimePC(conversionUtility.toTimeStamp(dtoResponse.getSaleEndTime()));
        response.setUnitManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class,
                                                                     dtoResponse.getUnitManagementFlag()
                                                                    ));
        response.setStockManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeStockManagementFlag.class,
                                                                      dtoResponse.getStockManagementFlag()
                                                                     ));
        response.setIndividualDeliveryType(EnumTypeUtil.getEnumFromValue(HTypeIndividualDeliveryType.class,
                                                                         dtoResponse.getIndividualDeliveryType()
                                                                        ));
        response.setPurchasedMax(dtoResponse.getPurchasedMax());
        response.setFreeDeliveryFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeFreeDeliveryFlag.class, dtoResponse.getFreeDeliveryFlag()));
        response.setOrderDisplay(dtoResponse.getOrderDisplay());
        response.setUnitValue1(dtoResponse.getUnitValue1());
        response.setUnitValue2(dtoResponse.getUnitValue2());
        response.setPreDiscountPrice(dtoResponse.getPreDiscountPrice());
        response.setPreDisCountPriceInTax(dtoResponse.getPreDisCountPriceInTax());
        response.setJanCode(dtoResponse.getJanCode());
        response.setCatalogCode(dtoResponse.getCatalogCode());
        response.setSalesPossibleStock(dtoResponse.getSalesPossibleStock());
        response.setRealStock(dtoResponse.getRealStock());
        response.setOrderReserveStock(dtoResponse.getOrderReserveStock());
        response.setRemainderFewStock(dtoResponse.getRemainderFewStock());
        response.setOrderPointStock(dtoResponse.getOrderPointStock());
        response.setSafetyStock(dtoResponse.getSafetyStock());
        response.setGoodsGroupCode(dtoResponse.getGoodsGroupCode());
        response.setWhatsnewDate(conversionUtility.toTimeStamp(dtoResponse.getWhatsnewDate()));
        response.setGoodsOpenStatusPC(
                        EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class, dtoResponse.getGoodsOpenStatus()));
        response.setOpenStartTimePC(conversionUtility.toTimeStamp(dtoResponse.getOpenStartTime()));
        response.setOpenEndTimePC(conversionUtility.toTimeStamp(dtoResponse.getOpenEndTime()));
        response.setGoodsGroupName(dtoResponse.getGoodsGroupName());
        response.setUnitTitle1(dtoResponse.getUnitTitle1());
        response.setUnitTitle2(dtoResponse.getUnitTitle2());
        response.setGoodsPreDiscountPrice(dtoResponse.getGoodsPreDiscountPrice());
        response.setGoodsGroupImageEntityList(
                        toGoodsGroupImageEntityResponseGoods(dtoResponse.getGoodsGroupImageEntityList()));
        response.setSnsLinkFlag(EnumTypeUtil.getEnumFromValue(HTypeSnsLinkFlag.class, dtoResponse.getSnsLinkFlag()));
        response.setMetaDescription(dtoResponse.getMetaDescription());
        response.setStockStatusPc(
                        EnumTypeUtil.getEnumFromValue(HTypeStockStatusType.class, dtoResponse.getStockStatus()));
        response.setGoodsNote1(dtoResponse.getGoodsNote1());
        response.setGoodsNote2(dtoResponse.getGoodsNote2());
        response.setGoodsNote3(dtoResponse.getGoodsNote3());
        response.setGoodsNote4(dtoResponse.getGoodsNote4());
        response.setGoodsNote5(dtoResponse.getGoodsNote5());
        response.setGoodsNote6(dtoResponse.getGoodsNote6());
        response.setGoodsNote7(dtoResponse.getGoodsNote7());
        response.setGoodsNote8(dtoResponse.getGoodsNote8());
        response.setGoodsNote9(dtoResponse.getGoodsNote9());
        response.setGoodsNote10(dtoResponse.getGoodsNote10());
        response.setOrderSetting1(dtoResponse.getOrderSetting1());
        response.setOrderSetting2(dtoResponse.getOrderSetting2());
        response.setOrderSetting3(dtoResponse.getOrderSetting3());
        response.setOrderSetting4(dtoResponse.getOrderSetting4());
        response.setOrderSetting5(dtoResponse.getOrderSetting5());
        response.setOrderSetting6(dtoResponse.getOrderSetting6());
        response.setOrderSetting7(dtoResponse.getOrderSetting7());
        response.setOrderSetting8(dtoResponse.getOrderSetting8());
        response.setOrderSetting9(dtoResponse.getOrderSetting9());
        response.setOrderSetting10(dtoResponse.getOrderSetting10());
        response.setUnitImage(toGoodsImageEntityResponseGoods(dtoResponse.getUnitImage()));
        response.setGoodsOptionDisplayName(dtoResponse.getGoodsOptionDisplayName());
        response.setGoodsClassType(
                        EnumTypeUtil.getEnumFromValue(HTypeGoodsClassType.class, dtoResponse.getGoodsClassType()));
        response.setDentalMonopolySalesFlg(EnumTypeUtil.getEnumFromValue(HTypeDentalMonopolySalesFlg.class,
                                                                         dtoResponse.getDentalMonopolySalesFlg()
                                                                        ));
        response.setSaleIconFlag(EnumTypeUtil.getEnumFromValue(HTypeSaleIconFlag.class, dtoResponse.getSaleIconFlag()));
        response.setReserveIconFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeReserveIconFlag.class, dtoResponse.getReserveIconFlag()));
        response.setNewIconFlag(EnumTypeUtil.getEnumFromValue(HTypeNewIconFlag.class, dtoResponse.getNewIconFlag()));
        response.setLandSendFlag(EnumTypeUtil.getEnumFromValue(HTypeLandSendFlag.class, dtoResponse.getLandSendFlag()));
        response.setCoolSendFlag(EnumTypeUtil.getEnumFromValue(HTypeCoolSendFlag.class, dtoResponse.getCoolSendFlag()));
        response.setCoolSendFrom(dtoResponse.getCoolSendFrom());
        response.setCoolSendTo(dtoResponse.getCoolSendTo());
        response.setTax(dtoResponse.getTax());
        response.setUnit(dtoResponse.getUnit());
        response.setGoodsManagementCode(dtoResponse.getGoodsManagementCode());
        response.setGoodsDivisionCode(dtoResponse.getGoodsDivisionCode());
        response.setGoodsCategory1(dtoResponse.getGoodsCategory1());
        response.setGoodsCategory2(dtoResponse.getGoodsCategory2());
        response.setGoodsCategory3(dtoResponse.getGoodsCategory3());
        response.setReserveFlag(EnumTypeUtil.getEnumFromValue(HTypeReserveFlag.class, dtoResponse.getReserveFlag()));
        response.setPriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypePriceMarkDispFlag.class,
                                                                    dtoResponse.getPriceMarkDispFlag()
                                                                   ));
        response.setSalePriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceMarkDispFlag.class,
                                                                        dtoResponse.getSalePriceMarkDispFlag()
                                                                       ));
        response.setSalePriceIntegrityFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceIntegrityFlag.class,
                                                                         dtoResponse.getSalePriceIntegrityFlag()
                                                                        ));
        response.setSaleYesNo(dtoResponse.getSaleYesNo());
        response.setSaleNgMessage(dtoResponse.getSaleNgMessage());
        response.setDeliveryYesNo(dtoResponse.getDeliveryYesNo());
        response.setEmotionPriceType(
                        EnumTypeUtil.getEnumFromValue(HTypeEmotionPriceType.class, dtoResponse.getEmotionPriceType()));
        return response;
    }

    /**
     * 商品グループ画像クラスに変換
     *
     * @param goodsGroupImageEntityResponseList 商品グループ画像レスポンス
     * @return 商品グループ画像クラス
     */
    private List<GoodsGroupImageEntity> toGoodsGroupImageEntityResponseGoods(List<jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupImageEntityResponse> goodsGroupImageEntityResponseList) {
        if (CollectionUtils.isEmpty(goodsGroupImageEntityResponseList)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntity> response = new ArrayList<>();

        for (jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupImageEntityResponse dto : goodsGroupImageEntityResponseList) {
            GoodsGroupImageEntity res = new GoodsGroupImageEntity();
            res.setGoodsGroupSeq(dto.getGoodsGroupSeq());
            res.setRegistTime(conversionUtility.toTimeStamp(dto.getRegistTime()));
            res.setUpdateTime(conversionUtility.toTimeStamp(dto.getUpdateTime()));
            res.setImageFileName(dto.getImageFileName());
            res.setImageTypeVersionNo(dto.getImageTypeVersionNo());
            response.add(res);
        }

        return response;
    }

    /**
     * 商品画像クラスに変換
     *
     * @param goodsImageEntityResponse 商品グループ画像レスポンス
     * @return 商品画像クラス
     */
    private GoodsImageEntity toGoodsImageEntityResponseGoods(jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsImageEntityResponse goodsImageEntityResponse) {
        if (goodsImageEntityResponse == null) {
            return null;
        }

        GoodsImageEntity response = new GoodsImageEntity();
        response.setGoodsGroupSeq(goodsImageEntityResponse.getGoodsGroupSeq());
        response.setGoodsSeq(goodsImageEntityResponse.getGoodsSeq());
        response.setRegistTime(conversionUtility.toTimeStamp(goodsImageEntityResponse.getRegistTime()));
        response.setImageFileName(goodsImageEntityResponse.getImageFileName());
        response.setUpdateTime(conversionUtility.toTimeStamp(goodsImageEntityResponse.getUpdateTime()));
        response.setDisplayFlag(EnumTypeUtil.getEnumFromValue(HTypeDisplayStatus.class,
                                                              goodsImageEntityResponse.getDisplayFlag()
                                                             ));
        response.setTmpFilePath(goodsImageEntityResponse.getTmpFilePath());
        return response;
    }

}
// PDR Migrate Customization to here
