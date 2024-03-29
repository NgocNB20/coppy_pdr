package jp.co.hankyuhanshin.itec.hitmall.api.webapi;

import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.AbstractWebApiRequestDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.AbstractWebApiResponseResultDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.GoodsGroupImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.GoodsImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiAddCouponResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiAddCouponResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiAddDestinationRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiAddDestinationResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiCouponCheckResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiCouponCheckResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetCouponListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetCouponListResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDestinationResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDestinationResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDiscountsResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDiscountsResultRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDiscountsResultResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetNotYetShippingOrderHistoryResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetNotYetShippingOrderHistoryResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetOtherGoodsDetailResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetOtherGoodsRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetOtherGoodsResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPreShipmentOrderHistoryAggregateResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPreShipmentOrderHistoryResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPreShipmentOrderHistoryResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPurchasedCommodityInformationResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPurchasedCommodityInformationResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponseCustomerSalePriceDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponsePriceDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponseSalePriceDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetReserveRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetReserveResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetReserveResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckDetailResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetShipmentDateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetShipmentDateRequestDetailDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetShipmentDateResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetShipmentDateResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiOrderHistoryResponseGoodsInfoDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetOtherGoodsRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetOtherGoodsResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetQuantityDiscountResponseCustomerSalePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetQuantityDiscountResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetQuantityDiscountResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetQuantityDiscountResponsePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetQuantityDiscountResponseSalePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetSaleCheckRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetSaleCheckResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetStockResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.member.WebApiAddDestinationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.member.WebApiGetDestinationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.member.WebApiGetDestinationResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiAddCouponResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiAddCouponResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiCouponCheckResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiCouponCheckResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetCouponListResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetCouponListResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDiscountsRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDiscountsResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDiscountsResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetNotYetShippingOrderHistoryResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetNotYetShippingOrderHistoryResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPreShipmentOrderHistoryResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPreShipmentOrderHistoryResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPurchasedCommodityInformationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPurchasedCommodityInformationResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetShipmentDateRequestDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetShipmentDateRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetShipmentDateResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetShipmentDateResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiOrderHistoryResponseGoodsInfoDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class WebapiHelper {

    // 2023-renew No14 from here
    /**
     * 変換ユーティリティ
     */
    private final ConversionUtility conversionUtility;

    /**
     * コンストラクター
     */
    @Autowired
    public WebapiHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }
    // 2023-renew No14 to here

    /**
     * 数量割引情報取得レスポンスに変換
     *
     * @param responseDto WEB-API連携取得結果DTO基底クラス
     * @return 数量割引情報取得レスポンス
     */
    public WebApiGetDestinationResponse toWebApiGetDestinationResponse(AbstractWebApiResponseDto responseDto) {
        if (ObjectUtils.isEmpty(responseDto)) {
            return null;
        }

        WebApiGetDestinationResponse response = new WebApiGetDestinationResponse();
        List<WebApiGetDestinationResponseDetailDtoResponse> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(((WebApiGetDestinationResponseDto) responseDto).getInfo())) {
            for (WebApiGetDestinationResponseDetailDto dto : ((WebApiGetDestinationResponseDto) responseDto).getInfo()) {
                WebApiGetDestinationResponseDetailDtoResponse res = new WebApiGetDestinationResponseDetailDtoResponse();
                res.setReceiveCustomerNo(dto.getReceiveCustomerNo());
                res.setOfficeName(dto.getOfficeName());
                res.setOfficeKana(dto.getOfficeKana());
                res.setRepresentative(dto.getRepresentative());
                res.setBusinessType(dto.getBusinessType());
                res.setTel(dto.getTel());
                res.setZipCode(dto.getZipCode());
                res.setCity(dto.getCity());
                res.setAddress(dto.getAddress());
                res.setBuilding(dto.getBuilding());
                res.setOther(dto.getOther());
                res.setApprovalFlag(dto.getApprovalFlag());
                list.add(res);
            }
        }

        AbstractWebApiResponseResultDtoResponse abResponse = new AbstractWebApiResponseResultDtoResponse();
        abResponse.setMessage(responseDto.getResult().getMessage());
        abResponse.setStatus(responseDto.getResult().getStatus());

        response.setInfo(list);
        response.setResult(abResponse);
        return response;
    }

    /**
     * WEB-API連携割引適用結果取得Dtoに変換
     *
     * @param request WEB-API連携割引適用結果取得Dto
     * @return
     * <pre>
     * WEB-API連携リクエストDTOクラス
     * 割引適用結果取得
     * </pre>
     */
    public WebApiGetDiscountsRequestDto toWebApiGetDiscountsRequestDto(WebApiGetDiscountsResultRequest request) {
        WebApiGetDiscountsRequestDto reqDto = ApplicationContextUtility.getBean(WebApiGetDiscountsRequestDto.class);
        reqDto.setCustomerNo(request.getCustomerNo());
        reqDto.setGoodsCode(request.getGoodsCode());
        reqDto.setQuantity(request.getQuantity());
        reqDto.setOrderDisplay(request.getOrderDisplay());
        return reqDto;
    }

    /**
     * WEB-API連携注文履歴（過去1年）取得レスポンスに変換
     *
     * @param responseDto WEB-API連携取得結果DTO基底クラス
     * @return WEB-API連携注文履歴（過去1年）取得レスポンス
     */
    public WebApiGetDiscountsResultResponse toWebApiGetDiscountsResultResponse(AbstractWebApiResponseDto responseDto) {
        if (responseDto == null) {
            return null;
        }

        WebApiGetDiscountsResultResponse response = new WebApiGetDiscountsResultResponse();

        // 2023-renew No65 from here
        if (((WebApiGetDiscountsResponseDto) responseDto).getInfo() == null) {
            AbstractWebApiResponseResultDtoResponse abResponse = new AbstractWebApiResponseResultDtoResponse();
            abResponse.setStatus("0");
            abResponse.setMessage(null);

            response.setInfo(null);
            response.setResult(abResponse);
            return response;
        }
        // 2023-renew No65 from here

        List<WebApiGetDiscountsResponseDetailDtoResponse> list = new ArrayList<>();

        for (WebApiGetDiscountsResponseDetailDto dto : ((WebApiGetDiscountsResponseDto) responseDto).getInfo()) {
            WebApiGetDiscountsResponseDetailDtoResponse res = new WebApiGetDiscountsResponseDetailDtoResponse();
            res.setGoodsCode(dto.getGoodsCode());
            res.setSaleType(dto.getSaleType());
            res.setSaleGroupCode(dto.getSaleGroupCode());
            res.setSalePrice(dto.getSalePrice());
            res.setQuantity(dto.getQuantity());
            res.setSaleCode(dto.getSaleCode());
            res.setNote(dto.getNote());
            res.setHints(dto.getHints());
            res.setOrderDisplay(dto.getOrderDisplay());
            list.add(res);
        }
        AbstractWebApiResponseResultDtoResponse abResponse = new AbstractWebApiResponseResultDtoResponse();
        abResponse.setStatus(responseDto.getResult().getStatus());
        abResponse.setMessage(responseDto.getResult().getMessage());

        response.setInfo(list);
        response.setResult(abResponse);
        return response;
    }

    /**
     * WEB-API連携取得結果DTOクラスレスポンスに変換
     *
     * @param webApiOrderHistoryResponseGoodsInfoDtoList WEB-API連携取得結果DTOクラス
     * @return WEB-API連携取得結果DTOクラスレスポンス
     */
    private List<WebApiOrderHistoryResponseGoodsInfoDtoResponse> toWebApiOrderHistoryResponseGoodsInfoDtoResponseList(
                    List<WebApiOrderHistoryResponseGoodsInfoDto> webApiOrderHistoryResponseGoodsInfoDtoList) {
        if (CollectionUtils.isEmpty(webApiOrderHistoryResponseGoodsInfoDtoList)) {
            return new ArrayList<>();
        }

        List<WebApiOrderHistoryResponseGoodsInfoDtoResponse> response = new ArrayList<>();

        for (WebApiOrderHistoryResponseGoodsInfoDto dto : webApiOrderHistoryResponseGoodsInfoDtoList) {
            WebApiOrderHistoryResponseGoodsInfoDtoResponse res = new WebApiOrderHistoryResponseGoodsInfoDtoResponse();
            res.setGoodsCode(dto.getGoodsCode());
            res.setGoodsName(dto.getGoodsName());
            res.setGoodsCount(dto.getGoodsCount());
            res.setUnitName(dto.getUnitName());
            res.setDiscountFlag(dto.getDiscountFlag());
            response.add(res);
        }

        return response;
    }

    /**
     * 注文履歴（未出荷）取得Web-APIレスポンスに変換
     *
     * @param responseDto WEB-API連携取得結果DTO基底クラス
     * @return 注文履歴（未出荷）取得Web-APIレスポンス
     */
    public WebApiGetNotYetShippingOrderHistoryResponse toWebApiGetNotYetShippingOrderHistoryResponse(
                    AbstractWebApiResponseDto responseDto) {
        if (responseDto == null) {
            return null;
        }

        WebApiGetNotYetShippingOrderHistoryResponse response = new WebApiGetNotYetShippingOrderHistoryResponse();

        List<WebApiGetNotYetShippingOrderHistoryResponseDetailDtoResponse> list = new ArrayList<>();

        for (WebApiGetNotYetShippingOrderHistoryResponseDetailDto dto : (((WebApiGetNotYetShippingOrderHistoryResponseDto) responseDto).getInfo())) {
            WebApiGetNotYetShippingOrderHistoryResponseDetailDtoResponse res =
                            new WebApiGetNotYetShippingOrderHistoryResponseDetailDtoResponse();
            res.setOrderNo(dto.getOrderNo());
            res.setReceptionTypeName(dto.getReceptionTypeName());
            res.setOrderDate(dto.getOrderDate());
            res.setReceiveOfficeName(dto.getReceiveOfficeName());
            res.setReceiveZipcode(dto.getReceiveZipcode());
            res.setReceiveAddress1(dto.getReceiveAddress1());
            res.setReceiveAddress2(dto.getReceiveAddress2());
            res.setReceiveAddress3(dto.getReceiveAddress3());
            res.setReceiveAddress4(dto.getReceiveAddress4());
            res.setReceiveAddress5(dto.getReceiveAddress5());
            res.setReceiveDate(dto.getReceiveDate());
            res.setPaymentType(dto.getPaymentType());
            res.setPaymentTotalPrice(dto.getPaymentTotalPrice());
            // 2023-renew No24 from here
            res.setCouponNo(dto.getCouponNo());
            res.setCouponName(dto.getCouponName());
            // 2023-renew No24 to here
            // 2023-renew No68 from here
            res.setCancelYesNo(dto.getCancelYesNo());
            // 2023-renew No68 to here
            res.setGoodsList(toWebApiOrderHistoryResponseGoodsInfoDtoResponseList(dto.getGoodsList()));
            res.setGoodsDetailsMap(toGoodsDetailsDtoResponse(dto.getGoodsDetailsMap()));
            list.add(res);
        }

        AbstractWebApiResponseResultDtoResponse abResponse = new AbstractWebApiResponseResultDtoResponse();
        abResponse.setMessage(responseDto.getResult().getMessage());
        abResponse.setStatus(responseDto.getResult().getStatus());

        response.setInfo(list);
        response.setResult(abResponse);

        return response;
    }

    /**
     * 商品詳細Dtoクラスレスポンスに変換
     *
     * @param goodsDetailsMap 商品詳細Dtoクラス
     * @return 商品詳細Dtoクラスレスポンス
     */
    private Map<String, GoodsDetailsDtoResponse> toGoodsDetailsDtoResponse(Map<String, GoodsDetailsDto> goodsDetailsMap) {
        if (goodsDetailsMap == null) {
            return new HashMap<>();
        }

        Map<String, GoodsDetailsDtoResponse> map = new HashMap<>();

        for (Map.Entry<String, GoodsDetailsDto> entry : goodsDetailsMap.entrySet()) {
            String newKey = entry.getKey();
            GoodsDetailsDtoResponse newValue = toGoodsDetailsDto(entry.getValue());
            map.put(newKey, newValue);
        }

        return map;
    }

    /**
     * 商品詳細Dtoクラスレスポンスに変換
     *
     * @param goodsDetailsDto 商品詳細Dtoクラス
     * @return 商品詳細Dtoクラスレスポンス
     */
    private GoodsDetailsDtoResponse toGoodsDetailsDto(GoodsDetailsDto goodsDetailsDto) {
        if (goodsDetailsDto == null) {
            return null;
        }

        GoodsDetailsDtoResponse response = new GoodsDetailsDtoResponse();
        response.setGoodsSeq(goodsDetailsDto.getGoodsSeq());
        response.setGoodsGroupSeq(goodsDetailsDto.getGoodsGroupSeq());
        response.setVersionNo(goodsDetailsDto.getVersionNo());
        response.setRegistTime(goodsDetailsDto.getRegistTime());
        response.setUpdateTime(goodsDetailsDto.getUpdateTime());
        response.setGoodsCode(goodsDetailsDto.getGoodsCode());
        response.setGoodsGroupMaxPrice(goodsDetailsDto.getGoodsGroupMaxPrice());
        response.setGoodsGroupMinPrice(goodsDetailsDto.getGoodsGroupMinPrice());
        response.setPreDiscountMinPrice(goodsDetailsDto.getPreDiscountMinPrice());
        response.setPreDiscountMaxPrice(goodsDetailsDto.getPreDiscountMaxPrice());
        if (goodsDetailsDto.getGoodsTaxType() != null) {
            response.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType().getValue());
        }
        response.setTaxRate(goodsDetailsDto.getTaxRate());
        if (goodsDetailsDto.getAlcoholFlag() != null) {
            response.setAlcoholFlag(goodsDetailsDto.getAlcoholFlag().getValue());
        }
        response.setGoodsPriceInTax(goodsDetailsDto.getGoodsPriceInTax());
        response.setGoodsPrice(goodsDetailsDto.getGoodsPrice());
        response.setDeliveryType(goodsDetailsDto.getDeliveryType());
        if (goodsDetailsDto.getSaleStatusPC() != null) {
            response.setSaleStatus(goodsDetailsDto.getSaleStatusPC().getValue());
        }

        response.setSaleStartTime(goodsDetailsDto.getSaleStartTimePC());
        response.setSaleEndTime(goodsDetailsDto.getSaleEndTimePC());
        if (goodsDetailsDto.getUnitManagementFlag() != null) {
            response.setUnitManagementFlag(goodsDetailsDto.getUnitManagementFlag().getValue());
        }
        if (goodsDetailsDto.getStockManagementFlag() != null) {
            response.setStockManagementFlag(goodsDetailsDto.getStockManagementFlag().getValue());
        }
        if (goodsDetailsDto.getIndividualDeliveryType() != null) {
            response.setIndividualDeliveryType(goodsDetailsDto.getIndividualDeliveryType().getValue());
        }
        response.setPurchasedMax(goodsDetailsDto.getPurchasedMax());
        if (goodsDetailsDto.getFreeDeliveryFlag() != null) {
            response.setFreeDeliveryFlag(goodsDetailsDto.getFreeDeliveryFlag().getValue());
        }
        response.setOrderDisplay(goodsDetailsDto.getOrderDisplay());
        response.setUnitValue1(goodsDetailsDto.getUnitValue1());
        response.setUnitValue2(goodsDetailsDto.getUnitValue2());
        response.setPreDiscountPrice(goodsDetailsDto.getPreDiscountPrice());
        response.setPreDisCountPriceInTax(goodsDetailsDto.getPreDisCountPriceInTax());
        response.setJanCode(goodsDetailsDto.getJanCode());
        response.setCatalogCode(goodsDetailsDto.getCatalogCode());
        response.setSalesPossibleStock(goodsDetailsDto.getSalesPossibleStock());
        response.setRealStock(goodsDetailsDto.getRealStock());
        response.setOrderReserveStock(goodsDetailsDto.getOrderReserveStock());
        response.setRemainderFewStock(goodsDetailsDto.getRemainderFewStock());
        response.setOrderPointStock(goodsDetailsDto.getOrderPointStock());
        response.setSafetyStock(goodsDetailsDto.getSafetyStock());
        response.setGoodsGroupCode(goodsDetailsDto.getGoodsGroupCode());
        response.setWhatsnewDate(goodsDetailsDto.getWhatsnewDate());
        if (goodsDetailsDto.getGoodsOpenStatusPC() != null) {
            response.setGoodsOpenStatus(goodsDetailsDto.getGoodsOpenStatusPC().getValue());
        }

        response.setOpenStartTime(goodsDetailsDto.getOpenStartTimePC());
        response.setOpenEndTime(goodsDetailsDto.getOpenEndTimePC());
        response.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
        response.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
        response.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
        response.setGoodsPreDiscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());
        response.setGoodsGroupImageEntityList(
                        toGoodsGroupImageEntityResponse(goodsDetailsDto.getGoodsGroupImageEntityList()));
        if (goodsDetailsDto.getSnsLinkFlag() != null) {
            response.setSnsLinkFlag(goodsDetailsDto.getSnsLinkFlag().getValue());
        }
        response.setMetaDescription(goodsDetailsDto.getMetaDescription());
        if (goodsDetailsDto.getStockStatusPc() != null) {
            response.setStockStatus(goodsDetailsDto.getStockStatusPc().getValue());
        }
        response.setGoodsNote1(goodsDetailsDto.getGoodsNote1());
        response.setGoodsNote2(goodsDetailsDto.getGoodsNote2());
        response.setGoodsNote3(goodsDetailsDto.getGoodsNote3());
        response.setGoodsNote4(goodsDetailsDto.getGoodsNote4());
        response.setGoodsNote5(goodsDetailsDto.getGoodsNote5());
        response.setGoodsNote6(goodsDetailsDto.getGoodsNote6());
        response.setGoodsNote7(goodsDetailsDto.getGoodsNote7());
        response.setGoodsNote8(goodsDetailsDto.getGoodsNote8());
        response.setGoodsNote9(goodsDetailsDto.getGoodsNote9());
        response.setGoodsNote10(goodsDetailsDto.getGoodsNote10());
        response.setOrderSetting1(goodsDetailsDto.getOrderSetting1());
        response.setOrderSetting2(goodsDetailsDto.getOrderSetting2());
        response.setOrderSetting3(goodsDetailsDto.getOrderSetting3());
        response.setOrderSetting4(goodsDetailsDto.getOrderSetting4());
        response.setOrderSetting5(goodsDetailsDto.getOrderSetting5());
        response.setOrderSetting6(goodsDetailsDto.getOrderSetting6());
        response.setOrderSetting7(goodsDetailsDto.getOrderSetting7());
        response.setOrderSetting8(goodsDetailsDto.getOrderSetting8());
        response.setOrderSetting9(goodsDetailsDto.getOrderSetting9());
        response.setOrderSetting10(goodsDetailsDto.getOrderSetting10());
        response.setUnitImage(toGoodsImageEntityResponse(goodsDetailsDto.getUnitImage()));
        response.setGoodsOptionDisplayName(goodsDetailsDto.getGoodsOptionDisplayName());
        if (goodsDetailsDto.getGoodsClassType() != null) {
            response.setGoodsClassType(goodsDetailsDto.getGoodsClassType().getValue());
        }
        if (goodsDetailsDto.getDentalMonopolySalesFlg() != null) {
            response.setDentalMonopolySalesFlg(goodsDetailsDto.getDentalMonopolySalesFlg().getValue());
        }
        if (goodsDetailsDto.getSaleIconFlag() != null) {
            response.setSaleIconFlag(goodsDetailsDto.getSaleIconFlag().getValue());
        }
        if (goodsDetailsDto.getReserveIconFlag() != null) {
            response.setReserveIconFlag(goodsDetailsDto.getReserveIconFlag().getValue());
        }
        if (goodsDetailsDto.getNewIconFlag() != null) {
            response.setNewIconFlag(goodsDetailsDto.getNewIconFlag().getValue());
        }
        if (goodsDetailsDto.getLandSendFlag() != null) {
            response.setLandSendFlag(goodsDetailsDto.getLandSendFlag().getValue());
        }
        if (goodsDetailsDto.getCoolSendFlag() != null) {
            response.setCoolSendFlag(goodsDetailsDto.getCoolSendFlag().getValue());
        }
        response.setCoolSendFrom(goodsDetailsDto.getCoolSendFrom());
        response.setCoolSendTo(goodsDetailsDto.getCoolSendTo());
        response.setTax(goodsDetailsDto.getTax());
        response.setUnit(goodsDetailsDto.getUnit());
        response.setGoodsManagementCode(goodsDetailsDto.getGoodsManagementCode());
        response.setGoodsDivisionCode(goodsDetailsDto.getGoodsDivisionCode());
        response.setGoodsCategory1(goodsDetailsDto.getGoodsCategory1());
        response.setGoodsCategory2(goodsDetailsDto.getGoodsCategory2());
        response.setGoodsCategory3(goodsDetailsDto.getGoodsCategory3());
        if (goodsDetailsDto.getReserveFlag() != null) {
            response.setReserveFlag(goodsDetailsDto.getReserveFlag().getValue());
        }
        if (goodsDetailsDto.getPriceMarkDispFlag() != null) {
            response.setPriceMarkDispFlag(goodsDetailsDto.getPriceMarkDispFlag().getValue());
        }
        if (goodsDetailsDto.getSalePriceMarkDispFlag() != null) {
            response.setSalePriceMarkDispFlag(goodsDetailsDto.getSalePriceMarkDispFlag().getValue());
        }
        if (goodsDetailsDto.getSalePriceIntegrityFlag() != null) {
            response.setSalePriceIntegrityFlag(goodsDetailsDto.getSalePriceIntegrityFlag().getValue());
        }
        response.setSaleYesNo(goodsDetailsDto.getSaleYesNo());
        response.setSaleNgMessage(goodsDetailsDto.getSaleNgMessage());
        response.setDeliveryYesNo(goodsDetailsDto.getDeliveryYesNo());
        if (goodsDetailsDto.getEmotionPriceType() != null) {
            response.setEmotionPriceType(goodsDetailsDto.getEmotionPriceType().getValue());
        }

        return response;
    }

    /**
     * 商品グループ画像レスポンスに変換
     *
     * @param goodsGroupImageEntities 商品グループ画像クラス
     * @return 商品グループ画像レスポンス
     */
    private List<GoodsGroupImageEntityResponse> toGoodsGroupImageEntityResponse(List<GoodsGroupImageEntity> goodsGroupImageEntities) {
        if (CollectionUtils.isEmpty(goodsGroupImageEntities)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntityResponse> response = new ArrayList<>();

        for (GoodsGroupImageEntity dto : goodsGroupImageEntities) {
            GoodsGroupImageEntityResponse res = new GoodsGroupImageEntityResponse();
            res.setGoodsGroupSeq(dto.getGoodsGroupSeq());
            res.setRegistTime(dto.getRegistTime());
            res.setUpdateTime(dto.getUpdateTime());
            res.setImageFileName(dto.getImageFileName());
            res.setImageTypeVersionNo(dto.getImageTypeVersionNo());
            response.add(res);
        }

        return response;
    }

    /**
     * 商品グループ画像レスポンスに変換
     *
     * @param goodsImageEntity 商品画像クラス
     * @return 品グループ画像レスポンス
     */
    private GoodsImageEntityResponse toGoodsImageEntityResponse(GoodsImageEntity goodsImageEntity) {
        if (goodsImageEntity == null) {
            return null;
        }

        GoodsImageEntityResponse response = new GoodsImageEntityResponse();
        response.setGoodsGroupSeq(goodsImageEntity.getGoodsGroupSeq());
        response.setGoodsSeq(goodsImageEntity.getGoodsSeq());
        response.setRegistTime(goodsImageEntity.getRegistTime());
        response.setImageFileName(goodsImageEntity.getImageFileName());
        response.setUpdateTime(goodsImageEntity.getUpdateTime());
        if (goodsImageEntity.getDisplayFlag() != null) {
            response.setDisplayFlag(goodsImageEntity.getDisplayFlag().getValue());
        }
        response.setTmpFilePath(goodsImageEntity.getTmpFilePath());
        return response;
    }

    /**
     * 注文履歴（出荷済）取得Web-APIレスポンスに変換
     *
     * @param responseDto WEB-API連携取得結果DTO基底クラス
     * @return 注文履歴（出荷済）取得Web-APIレスポンス
     */
    public WebApiGetPreShipmentOrderHistoryResponse toWebApigetPreShipmentOrderHistoryResponse(AbstractWebApiResponseDto responseDto) {
        if (responseDto == null) {
            return null;
        }

        WebApiGetPreShipmentOrderHistoryResponse response = new WebApiGetPreShipmentOrderHistoryResponse();
        List<WebApiGetPreShipmentOrderHistoryResponseDetailDtoResponse> list = new ArrayList<>();

        for (WebApiGetPreShipmentOrderHistoryResponseDetailDto dto : ((WebApiGetPreShipmentOrderHistoryResponseDto) responseDto).getInfo()) {
            WebApiGetPreShipmentOrderHistoryResponseDetailDtoResponse res =
                            new WebApiGetPreShipmentOrderHistoryResponseDetailDtoResponse();
            res.setOrderNo(dto.getOrderNo());
            res.setReceptionTypeName(dto.getReceptionTypeName());
            res.setOrderDate(dto.getOrderDate());
            res.setReceiveOfficeName(dto.getReceiveOfficeName());
            res.setReceiveZipcode(dto.getReceiveZipcode());
            res.setReceiveAddress1(dto.getReceiveAddress1());
            res.setReceiveAddress2(dto.getReceiveAddress2());
            res.setReceiveAddress3(dto.getReceiveAddress3());
            res.setReceiveAddress4(dto.getReceiveAddress4());
            res.setReceiveAddress5(dto.getReceiveAddress5());
            res.setReceiveDate(dto.getReceiveDate());
            res.setPaymentType(dto.getPaymentType());
            res.setPaymentTotalPrice(dto.getPaymentTotalPrice());
            // 2023-renew No24 from here
            res.setCouponNo(dto.getCouponNo());
            res.setCouponName(dto.getCouponName());
            // 2023-renew No24 to here
            res.setGoodsList(toWebApiOrderHistoryResponseGoodsInfoDtoResponseList(dto.getGoodsList()));
            res.setGoodsDetailsMap(toGoodsDetailsDtoResponse(dto.getGoodsDetailsMap()));
            list.add(res);
        }
        AbstractWebApiResponseResultDtoResponse abResponse = new AbstractWebApiResponseResultDtoResponse();
        abResponse.setStatus(responseDto.getResult().getStatus());
        abResponse.setMessage(responseDto.getResult().getMessage());

        response.setInfo(list);
        response.setResult(abResponse);
        return response;
    }

    /**
     * WEB-API連携割引適用結果取得レスポンスに変換
     *
     * @param responseDto WEB-API連携取得結果DTO基底クラス
     * @return WEB-API連携割引適用結果取得レスポンス
     */
    public WebApiGetPreShipmentOrderHistoryAggregateResponse toWebApigetPreShipmentOrderHistoryAggregateResponse(
                    AbstractWebApiResponseDto responseDto) {
        if (responseDto == null) {
            return null;
        }

        WebApiGetPreShipmentOrderHistoryAggregateResponse response =
                        new WebApiGetPreShipmentOrderHistoryAggregateResponse();
        List<WebApiGetPreShipmentOrderHistoryResponseDetailDtoResponse> list = new ArrayList<>();

        for (WebApiGetPreShipmentOrderHistoryResponseDetailDto dto : ((WebApiGetPreShipmentOrderHistoryResponseDto) responseDto).getInfo()) {
            WebApiGetPreShipmentOrderHistoryResponseDetailDtoResponse res =
                            new WebApiGetPreShipmentOrderHistoryResponseDetailDtoResponse();
            res.setOrderNo(dto.getOrderNo());
            res.setReceptionTypeName(dto.getReceptionTypeName());
            res.setOrderDate(dto.getOrderDate());
            res.setReceiveOfficeName(dto.getReceiveOfficeName());
            res.setReceiveZipcode(dto.getReceiveZipcode());
            res.setReceiveAddress1(dto.getReceiveAddress1());
            res.setReceiveAddress2(dto.getReceiveAddress2());
            res.setReceiveAddress3(dto.getReceiveAddress3());
            res.setReceiveAddress4(dto.getReceiveAddress4());
            res.setReceiveAddress5(dto.getReceiveAddress5());
            res.setReceiveDate(dto.getReceiveDate());
            res.setPaymentType(dto.getPaymentType());
            res.setPaymentTotalPrice(dto.getPaymentTotalPrice());
            res.setGoodsList(toWebApiOrderHistoryResponseGoodsInfoDtoResponseList(dto.getGoodsList()));
            res.setGoodsDetailsMap(toGoodsDetailsDtoResponse(dto.getGoodsDetailsMap()));
            list.add(res);
        }
        AbstractWebApiResponseResultDtoResponse abResponse = new AbstractWebApiResponseResultDtoResponse();
        abResponse.setMessage(responseDto.getResult().getMessage());
        abResponse.setStatus(responseDto.getResult().getStatus());

        response.setInfo(list);
        response.setResult(abResponse);
        return response;
    }

    /**
     * 数量割引情報取得レスポンスに変換
     *
     * @param responseDto WEB-API連携取得結果DTO基底クラス
     * @return 数量割引情報取得レスポンス
     */
    public WebApiGetQuantityDiscountResponse toWebApiGetQuantityDiscountResponse(AbstractWebApiResponseDto responseDto) {
        if (responseDto == null) {
            return null;
        }

        WebApiGetQuantityDiscountResponse response = new WebApiGetQuantityDiscountResponse();
        List<WebApiGetQuantityDiscountResponseDetailDtoResponse> list = new ArrayList<>();

        for (WebApiGetQuantityDiscountResponseDetailDto dto : ((WebApiGetQuantityDiscountResponseDto) responseDto).getInfo()) {
            WebApiGetQuantityDiscountResponseDetailDtoResponse res =
                            new WebApiGetQuantityDiscountResponseDetailDtoResponse();
            res.setGoodsCode(dto.getGoodsCode());
            res.setPriceList(toWebApiGetQuantityDiscountResponsePriceDtoResponse(dto.getPriceList()));
            res.setSalePriceList(toWebApiGetQuantityDiscountResponseSalePriceDtoResponse(dto.getSalePriceList()));
            res.setCustomerSalePriceList(toWebApiGetQuantityDiscountResponseCustomerSalePriceDtoResponse(
                            dto.getCustomerSalePriceList()));
            res.setSaleFlag(dto.getSaleFlag());
            res.setMarketPriceFlag(dto.getMarketPriceFlag());
            res.setCustomerSaleFlag(dto.getCustomerSaleFlag());
            // 2023-renew No5 from here
            res.setSaleEndDay(dto.getSaleEndDay());
            // 2023-renew No5 to here
            list.add(res);
        }
        AbstractWebApiResponseResultDtoResponse abResponse = new AbstractWebApiResponseResultDtoResponse();
        abResponse.setMessage(responseDto.getResult().getMessage());
        abResponse.setStatus(responseDto.getResult().getStatus());

        response.setInfo(list);
        response.setResult(abResponse);
        return response;
    }

    /**
     * 数量割引情報取得 価格情報レスポンスに変換
     *
     * @param webApiGetQuantityDiscountResponsePriceDtoList WEB-API連携取得結果DTOクラス
     *                                                      *数量割引情報取得 価格情報
     * @return 数量割引情報取得 価格情報レスポンス
     */
    private List<WebApiGetQuantityDiscountResponsePriceDtoResponse> toWebApiGetQuantityDiscountResponsePriceDtoResponse(
                    List<WebApiGetQuantityDiscountResponsePriceDto> webApiGetQuantityDiscountResponsePriceDtoList) {

        if (CollectionUtils.isEmpty(webApiGetQuantityDiscountResponsePriceDtoList)) {
            return new ArrayList<>();
        }

        List<WebApiGetQuantityDiscountResponsePriceDtoResponse> response = new ArrayList<>();
        for (WebApiGetQuantityDiscountResponsePriceDto dto : webApiGetQuantityDiscountResponsePriceDtoList) {
            WebApiGetQuantityDiscountResponsePriceDtoResponse res =
                            new WebApiGetQuantityDiscountResponsePriceDtoResponse();
            res.setPrice(dto.getPrice());
            res.setLevel(dto.getLevel());
            response.add(res);
        }
        return response;
    }

    /**
     * 数量割引情報取得 割引価格情報レスポンスに変換
     *
     * @param webApiGetQuantityDiscountResponseSalePriceDtoList WEB-API連携取得結果DTOクラス
     *                                                      *数量割引情報取得 割引価格情報
     * @return 数量割引情報取得 割引価格情報レスポンス
     */
    private List<WebApiGetQuantityDiscountResponseSalePriceDtoResponse> toWebApiGetQuantityDiscountResponseSalePriceDtoResponse(
                    List<WebApiGetQuantityDiscountResponseSalePriceDto> webApiGetQuantityDiscountResponseSalePriceDtoList) {

        if (CollectionUtils.isEmpty(webApiGetQuantityDiscountResponseSalePriceDtoList)) {
            return new ArrayList<>();
        }

        List<WebApiGetQuantityDiscountResponseSalePriceDtoResponse> response = new ArrayList<>();
        for (WebApiGetQuantityDiscountResponseSalePriceDto dto : webApiGetQuantityDiscountResponseSalePriceDtoList) {
            WebApiGetQuantityDiscountResponseSalePriceDtoResponse res =
                            new WebApiGetQuantityDiscountResponseSalePriceDtoResponse();
            res.setSalePrice(dto.getSalePrice());
            res.setSaleLevel(dto.getSaleLevel());
            response.add(res);
        }
        return response;
    }

    /**
     * 数量割引情報取得 顧客セール価格情報レスポンスに変換
     *
     * @param webApiGetQuantityDiscountResponseCustomerSalePriceDtoList WEB-API連携取得結果DTOクラス
     *                                                      *数量割引情報取得 顧客セール価格情報
     * @return 数量割引情報取得 顧客セール価格情報レスポンス
     */
    private List<WebApiGetQuantityDiscountResponseCustomerSalePriceDtoResponse> toWebApiGetQuantityDiscountResponseCustomerSalePriceDtoResponse(
                    List<WebApiGetQuantityDiscountResponseCustomerSalePriceDto> webApiGetQuantityDiscountResponseCustomerSalePriceDtoList) {

        if (CollectionUtils.isEmpty(webApiGetQuantityDiscountResponseCustomerSalePriceDtoList)) {
            return new ArrayList<>();
        }

        List<WebApiGetQuantityDiscountResponseCustomerSalePriceDtoResponse> response = new ArrayList<>();
        for (WebApiGetQuantityDiscountResponseCustomerSalePriceDto dto : webApiGetQuantityDiscountResponseCustomerSalePriceDtoList) {
            WebApiGetQuantityDiscountResponseCustomerSalePriceDtoResponse res =
                            new WebApiGetQuantityDiscountResponseCustomerSalePriceDtoResponse();
            res.setCustomerSaleLevel(dto.getCustomerSaleLevel());
            res.setCustomerSalePrice(dto.getCustomerSalePrice());
            response.add(res);
        }
        return response;
    }

    /**
     * 商品在庫数取得レスポンスに変換
     *
     * @param responseDto WEB-API連携取得結果DTO基底クラス
     * @return 商品在庫数取得レスポンス
     */
    public WebApiGetStockResponse toWebApiGetStockResponse(AbstractWebApiResponseDto responseDto) {
        if (responseDto == null) {
            return null;
        }

        WebApiGetStockResponse response = new WebApiGetStockResponse();
        List<WebApiGetStockResponseDetailDtoResponse> list = new ArrayList<>();
        for (WebApiGetStockResponseDetailDto dto : ((WebApiGetStockResponseDto) responseDto).getInfo()) {
            WebApiGetStockResponseDetailDtoResponse res = new WebApiGetStockResponseDetailDtoResponse();
            res.setGoodsCode(dto.getGoodsCode());
            res.setDeliveryYesNo(dto.getDeliveryYesNo());
            res.setSaleNgMessage(dto.getSaleNgMessage());
            res.setSaleYesNo(dto.getSaleYesNo());
            res.setStockDate(dto.getStockDate());
            res.setStockQuantity(dto.getStockQuantity());
            // 2023-renew No11 from here
            res.setSaleControl(dto.getSaleControl());
            // 2023-renew No11 to here
            list.add(res);
        }
        AbstractWebApiResponseResultDtoResponse abResponse = new AbstractWebApiResponseResultDtoResponse();
        abResponse.setMessage(responseDto.getResult().getMessage());
        abResponse.setStatus(responseDto.getResult().getStatus());

        response.setInfo(list);
        response.setResult(abResponse);
        return response;
    }

    /**
     * WEB-API連携 購入済み商品情報レスポンスに変換
     *
     * @param purchasedCommodityInformationDto WEB-API連携取得結果DTO
     * @return WEB-API連携 購入済み商品情報レスポンス
     */
    public WebApiGetPurchasedCommodityInformationResponse toWebApiGetPurchasedCommodityInformationResponse(
                    WebApiGetPurchasedCommodityInformationResponseDto purchasedCommodityInformationDto) {
        if (ObjectUtils.isEmpty(purchasedCommodityInformationDto)) {
            return null;
        }

        WebApiGetPurchasedCommodityInformationResponse response = new WebApiGetPurchasedCommodityInformationResponse();
        List<WebApiGetPurchasedCommodityInformationResponseDetailDtoResponse> infoList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(purchasedCommodityInformationDto.getInfo())) {
            for (WebApiGetPurchasedCommodityInformationResponseDetailDto dto : purchasedCommodityInformationDto.getInfo()) {
                WebApiGetPurchasedCommodityInformationResponseDetailDtoResponse dtoResponse =
                                new WebApiGetPurchasedCommodityInformationResponseDetailDtoResponse();
                dtoResponse.setGoodsCode(dto.getGoodsCode());

                infoList.add(dtoResponse);
            }
        }

        AbstractWebApiResponseResultDtoResponse abResponse = new AbstractWebApiResponseResultDtoResponse();
        abResponse.setMessage(purchasedCommodityInformationDto.getResult().getMessage());
        abResponse.setStatus(purchasedCommodityInformationDto.getResult().getStatus());

        response.setInfo(infoList);
        response.setResult(abResponse);

        return response;
    }

    /**
     * WEB-API連携リクエストDTOクラスに変換
     *
     * @param webApiAddDestinationRequest WEB-API連携 お届け先登録リクエスト
     * @return WEB-API連携リクエストDTOクラス
     */
    public WebApiAddDestinationRequestDto toAbstractWebApiRequestDto(WebApiAddDestinationRequest webApiAddDestinationRequest) {

        if (ObjectUtils.isEmpty(webApiAddDestinationRequest) || ObjectUtils.isEmpty(
                        webApiAddDestinationRequest.getAbstractWebApiRequestDtoRequest())) {
            return null;
        }
        AbstractWebApiRequestDtoRequest abstractWebApiRequestDtoRequest =
                        webApiAddDestinationRequest.getAbstractWebApiRequestDtoRequest();
        WebApiAddDestinationRequestDto webApiAddDestinationRequestDto = new WebApiAddDestinationRequestDto();

        webApiAddDestinationRequestDto.setCustomerNo(abstractWebApiRequestDtoRequest.getCustomerNo());
        webApiAddDestinationRequestDto.setReceiveCustomerNo(abstractWebApiRequestDtoRequest.getReceiveCustomerNo());
        webApiAddDestinationRequestDto.setOfficeName(abstractWebApiRequestDtoRequest.getOfficeName());
        webApiAddDestinationRequestDto.setOfficeKana(abstractWebApiRequestDtoRequest.getOfficeKana());
        webApiAddDestinationRequestDto.setRepresentative(abstractWebApiRequestDtoRequest.getRepresentative());
        webApiAddDestinationRequestDto.setTel(abstractWebApiRequestDtoRequest.getTel());
        webApiAddDestinationRequestDto.setZipCode(abstractWebApiRequestDtoRequest.getZipCode());
        webApiAddDestinationRequestDto.setCityCd(abstractWebApiRequestDtoRequest.getCityCd());
        webApiAddDestinationRequestDto.setCity(abstractWebApiRequestDtoRequest.getCity());
        webApiAddDestinationRequestDto.setAddress(abstractWebApiRequestDtoRequest.getAddress());
        webApiAddDestinationRequestDto.setBuilding(abstractWebApiRequestDtoRequest.getBuilding());
        webApiAddDestinationRequestDto.setOther(abstractWebApiRequestDtoRequest.getOther());
        webApiAddDestinationRequestDto.setBusinessType(abstractWebApiRequestDtoRequest.getBusinessType());
        webApiAddDestinationRequestDto.setKakuninShoKbn(abstractWebApiRequestDtoRequest.getKakuninShoKbn());
        webApiAddDestinationRequestDto.setKeiriKbn(abstractWebApiRequestDtoRequest.getKeiriKbn());
        webApiAddDestinationRequestDto.setOnlineYesNo(abstractWebApiRequestDtoRequest.getOnlineYesNo());
        webApiAddDestinationRequestDto.setMemberListType(abstractWebApiRequestDtoRequest.getMemberListType());

        return webApiAddDestinationRequestDto;
    }

    /**
     * WEB-API連携 お届け先登録レスポンスに変換
     *
     * @param webApiResponseDto WEB-API連携取得結果DTO基底クラス
     * @return WEB-API連携 お届け先登録レスポンス
     */
    public WebApiAddDestinationResponse toWebApiAddDestinationResponse(AbstractWebApiResponseDto webApiResponseDto) {
        if (ObjectUtils.isEmpty(webApiResponseDto)) {
            return null;
        }

        AbstractWebApiResponseResultDtoResponse abstractWebApiResponseResultDtoResponse =
                        new AbstractWebApiResponseResultDtoResponse();
        abstractWebApiResponseResultDtoResponse.setMessage(webApiResponseDto.getResult().getMessage());
        abstractWebApiResponseResultDtoResponse.setStatus(webApiResponseDto.getResult().getStatus());

        WebApiAddDestinationResponse webApiAddDestinationResponse = new WebApiAddDestinationResponse();
        webApiAddDestinationResponse.setResult(abstractWebApiResponseResultDtoResponse);

        return webApiAddDestinationResponse;
    }

    /**
     * クーポン有効性チェックWeb-APIレスポンスに変換
     *
     * @param couponCheckResponseDto WEB-API連携レスポンスDTOクラス
     * @return クーポン有効性チェックWeb-APIレスポンス
     */
    public WebApiCouponCheckResponse toWebApiCouponCheckResponse(WebApiCouponCheckResponseDto couponCheckResponseDto) {

        if (ObjectUtils.isEmpty(couponCheckResponseDto)) {
            return null;
        }

        WebApiCouponCheckResponse response = new WebApiCouponCheckResponse();
        List<WebApiCouponCheckResponseDetailDtoResponse> infoList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(couponCheckResponseDto.getInfo())) {
            for (WebApiCouponCheckResponseDetailDto dto : couponCheckResponseDto.getInfo()) {
                WebApiCouponCheckResponseDetailDtoResponse dtoResponse =
                                new WebApiCouponCheckResponseDetailDtoResponse();
                dtoResponse.setCouponName(dto.getCouponName());

                infoList.add(dtoResponse);
            }
        }

        AbstractWebApiResponseResultDtoResponse abResponse = new AbstractWebApiResponseResultDtoResponse();
        abResponse.setMessage(couponCheckResponseDto.getResult() != null ?
                                              couponCheckResponseDto.getResult().getMessage() :
                                              null);
        abResponse.setStatus(couponCheckResponseDto.getResult() != null ?
                                             couponCheckResponseDto.getResult().getStatus() :
                                             null);

        response.setInfo(infoList);
        response.setResult(abResponse);

        return response;
    }

    // 2023-renew No14 from here

    /**
     * WEB-API連携リクエストDTOクラス 取りおき情報取得 に変換
     *
     * @param getReserveRequest WEB-API連携取りおき情報取得リクエスト
     * @return WEB-API連携リクエストDTOクラス 取りおき情報取得
     */
    public WebApiGetReserveRequestDto toWebApiGetReserveRequestDto(WebApiGetReserveRequest getReserveRequest) {

        if (ObjectUtils.isEmpty(getReserveRequest)) {
            return null;
        }

        WebApiGetReserveRequestDto reqDto = new WebApiGetReserveRequestDto();
        reqDto.setCustomerNo(getReserveRequest.getCustomerNo());
        reqDto.setDeliveryCustomerNo(getReserveRequest.getCustomerNo());
        reqDto.setDeliveryZipcode(getReserveRequest.getDeliveryZipcode());
        reqDto.setGoodsCode(getReserveRequest.getGoodsCode());

        return reqDto;
    }

    /**
     * 取りおき情報取得Web-APIレスポンスに変換
     *
     * @param getReserveResponseDto WEB-API連携レスポンスDTO 取りおき情報取得
     * @return 取りおき情報取得Web-APIレスポンス
     */
    public WebApiGetReserveResponse toWebApiGetReserveResponse(WebApiGetReserveResponseDto getReserveResponseDto) {

        if (ObjectUtils.isEmpty(getReserveResponseDto)) {
            return null;
        }

        WebApiGetReserveResponse response = new WebApiGetReserveResponse();
        List<WebApiGetReserveResponseDetailDtoResponse> infoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(getReserveResponseDto.getInfo())) {
            for (WebApiGetReserveResponseDetailDto dto : getReserveResponseDto.getInfo()) {
                WebApiGetReserveResponseDetailDtoResponse dtoResponse = new WebApiGetReserveResponseDetailDtoResponse();
                dtoResponse.setGoodsCode(dto.getGoodsCode());
                dtoResponse.setReserveFlag(dto.getReserveFlag());
                dtoResponse.setDiscountFlag(dto.getDiscountFlag());
                dtoResponse.setPossibleReserveFromDay(dto.getPossibleReserveFromDay());
                dtoResponse.setPossibleReserveToDay(dto.getPossibleReserveToDay());
                infoList.add(dtoResponse);
            }
        }

        AbstractWebApiResponseResultDtoResponse abResponse = new AbstractWebApiResponseResultDtoResponse();
        abResponse.setMessage(getReserveResponseDto.getResult().getMessage());
        abResponse.setStatus(getReserveResponseDto.getResult().getStatus());

        response.setInfo(infoList);
        response.setResult(abResponse);
        return response;
    }

    /**
     * WEB-API連携リクエストDTOクラス 出荷予定日取得 に変換
     *
     * @param getShipmentDateRequest WEB-API連携出荷予定日取得リクエスト
     * @return WEB-API連携リクエストDTOクラス 出荷予定日取得
     */
    public WebApiGetShipmentDateRequestDto toWebApiGetShipmentDateRequestDto(WebApiGetShipmentDateRequest getShipmentDateRequest) {

        if (ObjectUtils.isEmpty(getShipmentDateRequest)) {
            return null;
        }

        WebApiGetShipmentDateRequestDto reqDto = new WebApiGetShipmentDateRequestDto();
        reqDto.setOrderCustomerNo(getShipmentDateRequest.getOrderCustomerNo());
        reqDto.setDeliveryCustomerNo(getShipmentDateRequest.getDeliveryCustomerNo());
        reqDto.setDeliveryZipcode(getShipmentDateRequest.getDeliveryZipcode());
        reqDto.setDeliveryCompanyCode(getShipmentDateRequest.getDeliveryCompanyCode());
        List<WebApiGetShipmentDateRequestDetailDto> infoList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(getShipmentDateRequest.getInfo())) {
            for (WebApiGetShipmentDateRequestDetailDtoRequest request : getShipmentDateRequest.getInfo()) {
                WebApiGetShipmentDateRequestDetailDto detailDto = new WebApiGetShipmentDateRequestDetailDto();
                detailDto.setGoodsCode(request.getGoodsCode());
                detailDto.setDeliveryDesignatedDay(conversionUtility.toTimeStamp(request.getDeliveryDesignatedDay()));
                detailDto.setDeliveryDesignatedTimeCode(request.getDeliveryDesignatedTimeCode());
                infoList.add(detailDto);
            }
        }
        reqDto.setInfo(infoList);

        return reqDto;
    }

    /**
     * 出荷予定日取得Web-APIレスポンスに変換
     *
     * @param getShipmentDateResponse WEB-API連携レスポンスDTO 出荷予定日取得
     * @return 出荷予定日取得Web-APIレスポンス
     */
    public WebApiGetShipmentDateResponse toWebApiGetShipmentDateResponse(WebApiGetShipmentDateResponseDto getShipmentDateResponse) {

        if (ObjectUtils.isEmpty(getShipmentDateResponse)) {
            return null;
        }

        WebApiGetShipmentDateResponse response = new WebApiGetShipmentDateResponse();
        List<WebApiGetShipmentDateResponseDetailDtoResponse> infoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(getShipmentDateResponse.getInfo())) {
            for (WebApiGetShipmentDateResponseDetailDto dto : getShipmentDateResponse.getInfo()) {
                WebApiGetShipmentDateResponseDetailDtoResponse dtoResponse =
                                new WebApiGetShipmentDateResponseDetailDtoResponse();
                dtoResponse.setGoodsCode(dto.getGoodsCode());
                dtoResponse.setDeliveryDesignatedDay(dto.getDeliveryDesignatedDay());
                dtoResponse.setShipmentDate(dto.getShipmentDate());
                infoList.add(dtoResponse);
            }
        }

        AbstractWebApiResponseResultDtoResponse abResponse = new AbstractWebApiResponseResultDtoResponse();
        abResponse.setMessage(getShipmentDateResponse.getResult().getMessage());
        abResponse.setStatus(getShipmentDateResponse.getResult().getStatus());

        response.setInfo(infoList);
        response.setResult(abResponse);
        return response;
    }

    // 2023-renew No14 to here
    // 2023-renew No24 from here

    /**
     * クーポン取得Web-APIレスポンスに変換
     *
     * @param addCouponResponseDto WEB-API連携レスポンスDTO クーポン取得
     * @return クーポン取得Web-APIレスポンス
     */
    public WebApiAddCouponResponse toWebApiAddCouponResponse(WebApiAddCouponResponseDto addCouponResponseDto) {

        if (ObjectUtils.isEmpty(addCouponResponseDto)) {
            return null;
        }

        WebApiAddCouponResponse response = new WebApiAddCouponResponse();
        List<WebApiAddCouponResponseDetailDtoResponse> infoList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(addCouponResponseDto.getInfo())) {
            for (WebApiAddCouponResponseDetailDto dto : addCouponResponseDto.getInfo()) {
                WebApiAddCouponResponseDetailDtoResponse dtoResponse = new WebApiAddCouponResponseDetailDtoResponse();
                dtoResponse.setCouponName(dto.getCouponName());
                infoList.add(dtoResponse);
            }
        }

        AbstractWebApiResponseResultDtoResponse abResponse = new AbstractWebApiResponseResultDtoResponse();
        abResponse.setMessage(addCouponResponseDto.getResult().getMessage());
        abResponse.setStatus(addCouponResponseDto.getResult().getStatus());

        response.setInfo(infoList);
        response.setResult(abResponse);
        return response;
    }

    /**
     * 利用可能クーポン一覧取得Web-APIレスポンスに変換
     *
     * @param getCouponListResponseDto WEB-API連携レスポンスDTO 利用可能クーポン一覧取得
     * @return 利用可能クーポン一覧取得Web-APIレスポンス
     */
    public WebApiGetCouponListResponse toWebApiGetCouponListResponse(WebApiGetCouponListResponseDto getCouponListResponseDto) {

        if (ObjectUtils.isEmpty(getCouponListResponseDto)) {
            return null;
        }

        WebApiGetCouponListResponse response = new WebApiGetCouponListResponse();
        List<WebApiGetCouponListResponseDetailDtoResponse> infoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(getCouponListResponseDto.getInfo())) {
            for (WebApiGetCouponListResponseDetailDto dto : getCouponListResponseDto.getInfo()) {
                WebApiGetCouponListResponseDetailDtoResponse dtoResponse =
                                new WebApiGetCouponListResponseDetailDtoResponse();
                dtoResponse.setCouponNo(dto.getCouponNo());
                dtoResponse.setCouponName(dto.getCouponName());
                dtoResponse.setCouponConditions(dto.getCouponConditions());
                dtoResponse.setCouponExplain(dto.getCouponExplain());
                infoList.add(dtoResponse);
            }
        }

        AbstractWebApiResponseResultDtoResponse abResponse = new AbstractWebApiResponseResultDtoResponse();
        abResponse.setMessage(getCouponListResponseDto.getResult().getMessage());
        abResponse.setStatus(getCouponListResponseDto.getResult().getStatus());

        response.setInfo(infoList);
        response.setResult(abResponse);
        return response;
    }

    // 2023-renew No24 to here

    // 2023-renew No11 from here

    /**
     * WEB-API連携リクエストDTOクラスに変換
     *
     * @param webApiGetSaleCheckRequest WEB-API連携 販売可否判定リクエスト
     * @return WEB-API連携リクエストDTOクラス
     */
    public WebApiGetSaleCheckRequestDto toAbstractWebApiRequestDto(WebApiGetSaleCheckRequest webApiGetSaleCheckRequest) {

        if (ObjectUtils.isEmpty(webApiGetSaleCheckRequest)) {
            return null;
        }
        WebApiGetSaleCheckRequestDto webApiGetSaleCheckRequestDto = new WebApiGetSaleCheckRequestDto();
        webApiGetSaleCheckRequestDto.setCustomerNo(webApiGetSaleCheckRequest.getCustomerNo());
        webApiGetSaleCheckRequestDto.setGoodsCode(webApiGetSaleCheckRequest.getGoodsCode());
        return webApiGetSaleCheckRequestDto;
    }

    /**
     * WEB-API連携 販売可否判定レスポンスに変換
     *
     * @param webApiResponseDto WEB-API連携取得結果DTO基底クラス
     * @return WEB-API連携 販売可否判定レスポンス
     */
    public WebApiGetSaleCheckResponse toWebApiGetSaleCheckResponse(AbstractWebApiResponseDto webApiResponseDto) {
        if (ObjectUtils.isEmpty(webApiResponseDto)) {
            return null;
        }

        WebApiGetSaleCheckResponse webApiGetSaleCheckResponse = new WebApiGetSaleCheckResponse();
        if (Objects.nonNull(((WebApiGetSaleCheckResponseDto) webApiResponseDto).getInfo())) {
            webApiGetSaleCheckResponse.setInfo(((WebApiGetSaleCheckResponseDto) webApiResponseDto).getInfo()
                                                                                                  .stream()
                                                                                                  .map(o -> new WebApiGetSaleCheckDetailResponse().goodsCode(
                                                                                                                                                                  o.getGoodsCode())
                                                                                                                                                  .goodsSaleYesNo(o.getGoodsSaleYesNo()))
                                                                                                  .collect(Collectors.toList()));
        }
        AbstractWebApiResponseResultDtoResponse result = new AbstractWebApiResponseResultDtoResponse();
        result.setStatus(webApiResponseDto.getResult().getStatus());
        result.setMessage(webApiResponseDto.getResult().getMessage());
        webApiGetSaleCheckResponse.setResult(result);

        return webApiGetSaleCheckResponse;
    }

    // 2023-renew No11 to here

    // 2023-renew No92 from here

    /**
     * WEB-API連携リクエストDTOクラスに変換
     *
     * @param webApiGetOtherGoodsRequest WEB-API連携 後継品代替品情報リクエスト
     * @return WEB-API連携リクエストDTOクラス
     */
    public WebApiGetOtherGoodsRequestDto toAbstractWebApiRequestDto(WebApiGetOtherGoodsRequest webApiGetOtherGoodsRequest) {

        if (ObjectUtils.isEmpty(webApiGetOtherGoodsRequest)) {
            return null;
        }
        WebApiGetOtherGoodsRequestDto webApiGetOtherGoodsRequestDto = new WebApiGetOtherGoodsRequestDto();
        webApiGetOtherGoodsRequestDto.setGoodsCode(webApiGetOtherGoodsRequest.getGoodsCode());
        return webApiGetOtherGoodsRequestDto;
    }

    /**
     * WEB-API連携 後継品代替品レスポンスに変換
     *
     * @param webApiResponseDto WEB-API連携取得結果DTO基底クラス
     * @return WEB-API連携 後継品代替品レスポンス
     */
    public WebApiGetOtherGoodsResponse toWebApiGetOtherGoodsResponse(AbstractWebApiResponseDto webApiResponseDto) {
        if (ObjectUtils.isEmpty(webApiResponseDto)) {
            return null;
        }

        WebApiGetOtherGoodsResponse webApiGetOtherGoodsResponse = new WebApiGetOtherGoodsResponse();
        if (Objects.nonNull(((WebApiGetOtherGoodsResponseDto) webApiResponseDto).getInfo())) {
            webApiGetOtherGoodsResponse.setInfo(((WebApiGetOtherGoodsResponseDto) webApiResponseDto).getInfo()
                                                                                                    .stream()
                                                                                                    .map(o -> new WebApiGetOtherGoodsDetailResponse().goodsCode(
                                                                                                                                                                     o.getGoodsCode())
                                                                                                                                                     .otherGoodsCode(o.getOtherGoodsCode()))
                                                                                                    .collect(Collectors.toList()));
        }
        AbstractWebApiResponseResultDtoResponse result = new AbstractWebApiResponseResultDtoResponse();
        result.setStatus(webApiResponseDto.getResult().getStatus());
        result.setMessage(webApiResponseDto.getResult().getMessage());
        webApiGetOtherGoodsResponse.setResult(result);

        return webApiGetOtherGoodsResponse;
    }

    // 2023-renew No92 to here

}
