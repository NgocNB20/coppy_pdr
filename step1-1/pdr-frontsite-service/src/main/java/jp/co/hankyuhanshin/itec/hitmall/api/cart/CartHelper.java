package jp.co.hankyuhanshin.itec.hitmall.api.cart;

import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsChangeRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsCheckMapResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsForTakeOverDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsRegistCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CheckMessageDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsDetailsDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsGroupImageEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsGroupImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsImageEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetConsumptionTaxRateResponseDetailDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetConsumptionTaxRateResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetDiscountsResponseDetailDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetDiscountsResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetQuantityDiscountResultResponseDetailDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetQuantityDiscountResultResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetReserveResponseDetailDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetReserveResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetSaleCheckDetailResponse;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCoolSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDentalMonopolySalesFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDisplayStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsClassType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeLandSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsForTakeOverDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetSaleCheckResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetConsumptionTaxRateResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDiscountsResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetQuantityDiscountResultResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * カートHelperクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class CartHelper {

    /**
     * 変換ユーティリティ
     */
    private final ConversionUtility conversionUtility;

    /**
     * コンストラクター
     */
    @Autowired
    public CartHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * カートDtoに変換
     *
     * @param request カート商品チェックリクエスト
     * @return カートDto
     */
    public CartDto toCartDto(CartGoodsCheckRequest request) {
        if (ObjectUtils.isEmpty(request) || ObjectUtils.isEmpty(request.getCartDto())) {
            return null;
        }

        CartDtoRequest cartDtoRequest = request.getCartDto();

        CartDto cartDto = new CartDto();
        cartDto.setCartGoodsDtoList(this.toCartGoodsDtoList(cartDtoRequest.getCartGoodsDtoList()));

        cartDto.setGoodsTotalCount(cartDtoRequest.getGoodsTotalCount());
        cartDto.setGoodsTotalPrice(cartDtoRequest.getGoodsTotalPrice());
        cartDto.setGoodsTotalPriceInTax(cartDtoRequest.getGoodsTotalPriceInTax());
        if (StringUtils.isNotEmpty(cartDtoRequest.getSettlementMethodType())) {
            cartDto.setSettlementMethodType(EnumTypeUtil.getEnumFromValue(HTypeSettlementMethodType.class,
                                                                          cartDtoRequest.getSettlementMethodType()
                                                                         ));
        }
        cartDto.setDiscountsResponseDetailMap(
                        this.toWebApiGetDiscountsResponseDetailDtoMap(cartDtoRequest.getDiscountsResponseDetailMap()));
        cartDto.setConsumptionTaxRateMap(this.toWebApiGetConsumptionTaxRateResponseDetailDtoMap(
                        cartDtoRequest.getConsumptionTaxRateMap()));
        cartDto.setBeforeTransferEmotionGoodsCodeMap(cartDtoRequest.getBeforeTransferEmotionGoodsCodeMap());
        cartDto.setTotalPriceTax(cartDtoRequest.getTotalPriceTax());
        // 2023-renew No14 from here
        cartDto.setQuantityDiscountsResponseDetailMap(toWebApiGetQuantityDiscountResultResponseDetailDtoMapRequest(
                        cartDtoRequest.getQuantityDiscountsResponseDetailMap()));
        cartDto.setReserveMap(toWebApiGetReserveResponseDetailDtoMap(cartDtoRequest.getReserveMap()));
        cartDto.setSaleCheckMap(toWebApiGetSaleCheckResponseDetailDtoMap(cartDtoRequest.getSaleCheckMap()));
        // 2023-renew No14 to here
        return cartDto;
    }

    /**
     * カート一括登録用の商品情報に変換
     *
     * @param cartGoodsRegistCheckRequest カート投入チェック（一括登録用の簡易版）リクエスト
     * @return カート一括登録用の商品情報
     */
    public List<CartGoodsForTakeOverDto> toCartGoodsForTakeOverDtoListFromRequest(CartGoodsRegistCheckRequest cartGoodsRegistCheckRequest) {
        if (ObjectUtils.isEmpty(cartGoodsRegistCheckRequest)) {
            return new ArrayList<>();
        }

        return this.toCartGoodsForTakeOverDtoList(cartGoodsRegistCheckRequest.getCartGoodsForTakeOverDtoListRequest());
    }

    /**
     * カート一括登録用の商品情報に変換
     *
     * @param cartGoodsForTakeOverDtoRequestList カート一括登録用の商品情報リクエスト
     * @return カート一括登録用の商品情報
     */
    public List<CartGoodsForTakeOverDto> toCartGoodsForTakeOverDtoList(List<CartGoodsForTakeOverDtoRequest> cartGoodsForTakeOverDtoRequestList) {
        if (CollectionUtils.isEmpty(cartGoodsForTakeOverDtoRequestList)) {
            return new ArrayList<>();
        }

        List<CartGoodsForTakeOverDto> cartGoodsForTakeOverDtoList = new ArrayList<>();

        for (CartGoodsForTakeOverDtoRequest request : cartGoodsForTakeOverDtoRequestList) {
            CartGoodsForTakeOverDto cartGoodsForTakeOverDto = new CartGoodsForTakeOverDto();
            cartGoodsForTakeOverDto.setGoodsGroupSeq(request.getGoodsGroupSeq());
            cartGoodsForTakeOverDto.setGoodsSeq(request.getGoodsSeq());
            cartGoodsForTakeOverDto.setGoodsCode(request.getGoodsCode());
            cartGoodsForTakeOverDto.setGoodsCount(request.getGoodsCount());
            // 2023-renew No14 from here
            cartGoodsForTakeOverDto.setReserveDeliveryDate(
                            conversionUtility.toTimeStamp(request.getReserveDeliveryDate()));
            cartGoodsForTakeOverDto.setReserveFlag(
                            EnumTypeUtil.getEnumFromValue(HTypeReserveDeliveryFlag.class, request.getReserveFlag()));
            // 2023-renew No14 to here

            cartGoodsForTakeOverDtoList.add(cartGoodsForTakeOverDto);
        }

        return cartGoodsForTakeOverDtoList;
    }

    /**
     * カートDtoクラスレスポンスに変換
     *
     * @param cartDto カートDto
     * @return カートDtoクラスレスポンス
     */
    public CartDtoResponse toCartDtoResponse(CartDto cartDto) {
        if (ObjectUtils.isEmpty(cartDto)) {
            return null;
        }

        CartDtoResponse cartDtoResponse = new CartDtoResponse();
        cartDtoResponse.setCartGoodsDtoListResponse(this.toCartGoodsDtoListResponse(cartDto.getCartGoodsDtoList()));

        cartDtoResponse.setGoodsTotalCount(cartDto.getGoodsTotalCount());
        cartDtoResponse.setGoodsTotalPrice(cartDto.getGoodsTotalPrice());
        cartDtoResponse.setGoodsTotalPriceInTax(cartDto.getGoodsTotalPriceInTax());
        if (cartDto.getSettlementMethodType() != null) {
            cartDtoResponse.settlementMethodType(cartDto.getSettlementMethodType().getValue());
        }
        cartDtoResponse.setDiscountsResponseDetailMap(
                        this.toWebApiGetDiscountsResponseDetailDtoResponseMap(cartDto.getDiscountsResponseDetailMap()));
        cartDtoResponse.setConsumptionTaxRateMap(this.toWebApiGetConsumptionTaxRateResponseDetailDtoResponseMap(
                        cartDto.getConsumptionTaxRateMap()));
        cartDtoResponse.setBeforeTransferEmotionGoodsCodeMap(cartDto.getBeforeTransferEmotionGoodsCodeMap());
        cartDtoResponse.setTotalPriceTax(cartDto.getTotalPriceTax());
        // 2023-renew No14 from here
        cartDtoResponse.setQuantityDiscountsResponseDetailMap(
                        toWebApiGetQuantityDiscountResultResponseDetailDtoResponseMap(
                                        cartDto.getQuantityDiscountsResponseDetailMap()));
        cartDtoResponse.setReserveMap(toWebApiGetReserveResponseDetailDtoResponseMap(cartDto.getReserveMap()));
        cartDtoResponse.setSaleCheckMap(toWebApiGetSaleCheckDetailResponseMap(cartDto.getSaleCheckMap()));
        // 2023-renew No14 to here
        return cartDtoResponse;
    }

    /**
     * カート商品DtoクラスDtoに変換
     *
     * @param cartGoodsDtoList カート商品Dtoリスト
     * @return カート商品DtoクラスDtoレスポンスリスト
     */
    public List<CartGoodsDtoResponse> toCartGoodsDtoListResponse(List<CartGoodsDto> cartGoodsDtoList) {
        if (CollectionUtils.isEmpty(cartGoodsDtoList)) {
            return new ArrayList<>();
        }

        List<CartGoodsDtoResponse> cartGoodsDtoResponseList = new ArrayList<>();
        for (CartGoodsDto cartGoodsDto : cartGoodsDtoList) {
            CartGoodsDtoResponse response = new CartGoodsDtoResponse();
            // (1) カート商品エンティティ
            response.setCartGoodsEntityResponse(this.toCartGoodsEntityResponse(cartGoodsDto.getCartGoodsEntity()));
            // (2) 商品詳細DTO
            response.setGoodsDetailsDtoResponse(this.toGoodsDetailsDtoResponse(cartGoodsDto.getGoodsDetailsDto()));
            // (3) その他
            response.setGoodsPriceSubtotal(cartGoodsDto.getGoodsPriceSubtotal());
            response.setGoodsPriceInTaxSubtotal(cartGoodsDto.getGoodsPriceInTaxSubtotal());

            cartGoodsDtoResponseList.add(response);
        }

        return cartGoodsDtoResponseList;
    }

    /**
     * カート商品Dtoリストに変換
     *
     * @param cartGoodsChangeRequest カート情報変更リクエスト
     * @return カート商品Dtoリスト
     */
    public List<CartGoodsDto> toCartGoodsDtoListFromRequest(CartGoodsChangeRequest cartGoodsChangeRequest) {
        if (ObjectUtils.isEmpty(cartGoodsChangeRequest)) {
            return new ArrayList<>();
        }

        return this.toCartGoodsDtoList(cartGoodsChangeRequest.getCartGoodsDtoList());
    }

    /**
     * カート商品Dtoリストに変換
     *
     * @param cartGoodsDtoRequestList カート商品Dtoリクエストリスト
     * @return カート商品Dtoリスト
     */
    public List<CartGoodsDto> toCartGoodsDtoList(List<CartGoodsDtoRequest> cartGoodsDtoRequestList) {
        if (CollectionUtils.isEmpty(cartGoodsDtoRequestList)) {
            return new ArrayList<>();
        }

        List<CartGoodsDto> cartGoodsDtoList = new ArrayList<>();

        for (CartGoodsDtoRequest cartGoodsRequest : cartGoodsDtoRequestList) {
            CartGoodsDto cartGoodsDto = new CartGoodsDto();
            // (1) カート商品エンティティ
            cartGoodsDto.setCartGoodsEntity(this.toCartGoodsEntity(cartGoodsRequest.getCartGoodsEntity()));
            // (2) 商品詳細DTO
            cartGoodsDto.setGoodsDetailsDto(this.toGoodsDetailsDto(cartGoodsRequest.getGoodsDetailsDto()));
            // (3) その他
            cartGoodsDto.setGoodsPriceSubtotal(cartGoodsRequest.getGoodsPriceSubtotal());
            cartGoodsDto.setGoodsPriceInTaxSubtotal(cartGoodsRequest.getGoodsPriceInTaxSubtotal());

            cartGoodsDtoList.add(cartGoodsDto);
        }

        return cartGoodsDtoList;
    }

    /**
     * カート商品エンティティレスポンスに変換
     *
     * @param cartGoodsEntity カート商品
     * @return カート商品エンティティレスポンス
     */
    public CartGoodsEntityResponse toCartGoodsEntityResponse(CartGoodsEntity cartGoodsEntity) {
        if (ObjectUtils.isEmpty(cartGoodsEntity)) {
            return null;
        }

        CartGoodsEntityResponse cartGoodsEntityResponse = new CartGoodsEntityResponse();
        cartGoodsEntityResponse.setCartSeq(cartGoodsEntity.getCartSeq());
        cartGoodsEntityResponse.setGoodsSeq(cartGoodsEntity.getGoodsSeq());
        cartGoodsEntityResponse.setCartGoodsCount(cartGoodsEntity.getCartGoodsCount());
        cartGoodsEntityResponse.setMemberInfoSeq(cartGoodsEntity.getMemberInfoSeq());
        cartGoodsEntityResponse.setAccessUid(cartGoodsEntity.getAccessUid());
        cartGoodsEntityResponse.setRegistTime(cartGoodsEntity.getRegistTime());
        cartGoodsEntityResponse.setUpdateTime(cartGoodsEntity.getUpdateTime());
        // 2023-renew No14 from here
        cartGoodsEntityResponse.setReserveFlag(cartGoodsEntity.getReserveFlag().getValue());
        cartGoodsEntityResponse.setReserveDeliveryDate(cartGoodsEntity.getReserveDeliveryDate());
        // 2023-renew No14 to here
        return cartGoodsEntityResponse;
    }

    /**
     * カート商品に変換
     *
     * @param cartGoodsEntityRequest カート商品エンティティリクエスト
     * @return カート商品
     */
    public CartGoodsEntity toCartGoodsEntity(CartGoodsEntityRequest cartGoodsEntityRequest) {
        if (ObjectUtils.isEmpty(cartGoodsEntityRequest)) {
            return null;
        }
        CartGoodsEntity cartGoodsEntity = new CartGoodsEntity();

        cartGoodsEntity.setCartSeq(cartGoodsEntityRequest.getCartSeq());
        cartGoodsEntity.setGoodsSeq(cartGoodsEntityRequest.getGoodsSeq());
        cartGoodsEntity.setCartGoodsCount(cartGoodsEntityRequest.getCartGoodsCount());
        cartGoodsEntity.setMemberInfoSeq(cartGoodsEntityRequest.getMemberInfoSeq());
        cartGoodsEntity.setAccessUid(cartGoodsEntityRequest.getAccessUid());
        cartGoodsEntity.setRegistTime(conversionUtility.toTimeStamp(cartGoodsEntityRequest.getRegistTime()));
        cartGoodsEntity.setUpdateTime(conversionUtility.toTimeStamp(cartGoodsEntityRequest.getUpdateTime()));
        // 2023-renew No14 from here
        cartGoodsEntity.setReserveFlag(EnumTypeUtil.getEnumFromValue(HTypeReserveDeliveryFlag.class,
                                                                     cartGoodsEntityRequest.getReserveFlag()
                                                                    ));
        cartGoodsEntity.setReserveDeliveryDate(
                        conversionUtility.toTimeStamp(cartGoodsEntityRequest.getReserveDeliveryDate()));
        // 2023-renew No14 to here
        return cartGoodsEntity;
    }

    /**
     * 商品詳細Dtoクラスレスポンスに変換
     *
     * @param goodsDetailsDto 商品詳細Dto
     * @return 商品詳細Dtoクラスレスポンス
     */
    public GoodsDetailsDtoResponse toGoodsDetailsDtoResponse(GoodsDetailsDto goodsDetailsDto) {
        if (ObjectUtils.isEmpty(goodsDetailsDto)) {
            return null;
        }

        GoodsDetailsDtoResponse goodsDetailsDtoResponse = new GoodsDetailsDtoResponse();

        goodsDetailsDtoResponse.setGoodsSeq(goodsDetailsDto.getGoodsSeq());
        goodsDetailsDtoResponse.setGoodsGroupSeq(goodsDetailsDto.getGoodsGroupSeq());
        goodsDetailsDtoResponse.setVersionNo(goodsDetailsDto.getVersionNo());
        goodsDetailsDtoResponse.setRegistTime(goodsDetailsDto.getRegistTime());
        goodsDetailsDtoResponse.setUpdateTime(goodsDetailsDto.getUpdateTime());
        goodsDetailsDtoResponse.setGoodsCode(goodsDetailsDto.getGoodsCode());
        goodsDetailsDtoResponse.setGoodsGroupMaxPrice(goodsDetailsDto.getGoodsGroupMaxPrice());
        goodsDetailsDtoResponse.setGoodsGroupMinPrice(goodsDetailsDto.getGoodsGroupMinPrice());
        goodsDetailsDtoResponse.setPreDiscountMinPrice(goodsDetailsDto.getPreDiscountMinPrice());
        goodsDetailsDtoResponse.setPreDiscountMaxPrice(goodsDetailsDto.getPreDiscountMaxPrice());
        if (goodsDetailsDto.getGoodsTaxType() != null) {
            goodsDetailsDtoResponse.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType().getValue());
        }
        goodsDetailsDtoResponse.setTaxRate(goodsDetailsDto.getTaxRate());
        if (goodsDetailsDto.getAlcoholFlag() != null) {
            goodsDetailsDtoResponse.setAlcoholFlag(goodsDetailsDto.getAlcoholFlag().getValue());
        }
        goodsDetailsDtoResponse.setGoodsPriceInTax(goodsDetailsDto.getGoodsPriceInTax());
        goodsDetailsDtoResponse.setGoodsPrice(goodsDetailsDto.getGoodsPrice());
        goodsDetailsDtoResponse.setDeliveryType(goodsDetailsDto.getDeliveryType());
        if (goodsDetailsDto.getSaleStatusPC() != null) {
            goodsDetailsDtoResponse.setSaleStatus(goodsDetailsDto.getSaleStatusPC().getValue());
        }
        goodsDetailsDtoResponse.setSaleStartTime(goodsDetailsDto.getSaleStartTimePC());
        goodsDetailsDtoResponse.setSaleEndTime(goodsDetailsDto.getSaleEndTimePC());
        if (goodsDetailsDto.getUnitManagementFlag() != null) {
            goodsDetailsDtoResponse.setUnitManagementFlag(goodsDetailsDto.getUnitManagementFlag().getValue());
        }
        if (goodsDetailsDto.getStockManagementFlag() != null) {
            goodsDetailsDtoResponse.setStockManagementFlag(goodsDetailsDto.getStockManagementFlag().getValue());
        }
        if (goodsDetailsDto.getIndividualDeliveryType() != null) {
            goodsDetailsDtoResponse.setIndividualDeliveryType(goodsDetailsDto.getIndividualDeliveryType().getValue());
        }
        goodsDetailsDtoResponse.setPurchasedMax(goodsDetailsDto.getPurchasedMax());
        if (goodsDetailsDto.getFreeDeliveryFlag() != null) {
            goodsDetailsDtoResponse.setFreeDeliveryFlag(goodsDetailsDto.getFreeDeliveryFlag().getValue());
        }
        goodsDetailsDtoResponse.setOrderDisplay(goodsDetailsDto.getOrderDisplay());
        goodsDetailsDtoResponse.setUnitValue1(goodsDetailsDto.getUnitValue1());
        goodsDetailsDtoResponse.setUnitValue2(goodsDetailsDto.getUnitValue2());
        goodsDetailsDtoResponse.setPreDiscountPrice(goodsDetailsDto.getPreDiscountPrice());
        goodsDetailsDtoResponse.setPreDisCountPriceInTax(goodsDetailsDto.getPreDisCountPriceInTax());
        goodsDetailsDtoResponse.setJanCode(goodsDetailsDto.getJanCode());
        goodsDetailsDtoResponse.setCatalogCode(goodsDetailsDto.getCatalogCode());
        goodsDetailsDtoResponse.setSalesPossibleStock(goodsDetailsDto.getSalesPossibleStock());
        goodsDetailsDtoResponse.setRealStock(goodsDetailsDto.getRealStock());
        goodsDetailsDtoResponse.setOrderReserveStock(goodsDetailsDto.getOrderReserveStock());
        goodsDetailsDtoResponse.setRemainderFewStock(goodsDetailsDto.getRemainderFewStock());
        goodsDetailsDtoResponse.setOrderPointStock(goodsDetailsDto.getOrderPointStock());
        goodsDetailsDtoResponse.setSafetyStock(goodsDetailsDto.getSafetyStock());
        goodsDetailsDtoResponse.setGoodsGroupCode(goodsDetailsDto.getGoodsGroupCode());
        goodsDetailsDtoResponse.setWhatsnewDate(goodsDetailsDto.getWhatsnewDate());
        if (goodsDetailsDto.getGoodsOpenStatusPC() != null) {
            goodsDetailsDtoResponse.setGoodsOpenStatus(goodsDetailsDto.getGoodsOpenStatusPC().getValue());
        }
        goodsDetailsDtoResponse.setOpenStartTime(goodsDetailsDto.getOpenStartTimePC());
        goodsDetailsDtoResponse.setOpenEndTime(goodsDetailsDto.getOpenEndTimePC());
        goodsDetailsDtoResponse.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
        goodsDetailsDtoResponse.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
        goodsDetailsDtoResponse.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
        goodsDetailsDtoResponse.setGoodsPreDiscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());
        goodsDetailsDtoResponse.setGoodsGroupImageEntityList(
                        this.toGoodsGroupImageEntityRequestList(goodsDetailsDto.getGoodsGroupImageEntityList()));
        if (goodsDetailsDto.getSnsLinkFlag() != null) {
            goodsDetailsDtoResponse.setSnsLinkFlag(goodsDetailsDto.getSnsLinkFlag().getValue());
        }
        goodsDetailsDtoResponse.setMetaDescription(goodsDetailsDto.getMetaDescription());
        if (goodsDetailsDto.getStockStatusPc() != null) {
            goodsDetailsDtoResponse.setStockStatus(goodsDetailsDto.getStockStatusPc().getValue());
        }
        goodsDetailsDtoResponse.setGoodsNote1(goodsDetailsDto.getGoodsNote1());
        goodsDetailsDtoResponse.setGoodsNote2(goodsDetailsDto.getGoodsNote2());
        goodsDetailsDtoResponse.setGoodsNote3(goodsDetailsDto.getGoodsNote3());
        goodsDetailsDtoResponse.setGoodsNote4(goodsDetailsDto.getGoodsNote4());
        goodsDetailsDtoResponse.setGoodsNote5(goodsDetailsDto.getGoodsNote5());
        goodsDetailsDtoResponse.setGoodsNote6(goodsDetailsDto.getGoodsNote6());
        goodsDetailsDtoResponse.setGoodsNote7(goodsDetailsDto.getGoodsNote7());
        goodsDetailsDtoResponse.setGoodsNote8(goodsDetailsDto.getGoodsNote8());
        goodsDetailsDtoResponse.setGoodsNote9(goodsDetailsDto.getGoodsNote9());
        goodsDetailsDtoResponse.setGoodsNote10(goodsDetailsDto.getGoodsNote10());
        goodsDetailsDtoResponse.setOrderSetting1(goodsDetailsDto.getOrderSetting1());
        goodsDetailsDtoResponse.setOrderSetting2(goodsDetailsDto.getOrderSetting2());
        goodsDetailsDtoResponse.setOrderSetting3(goodsDetailsDto.getOrderSetting3());
        goodsDetailsDtoResponse.setOrderSetting4(goodsDetailsDto.getOrderSetting4());
        goodsDetailsDtoResponse.setOrderSetting5(goodsDetailsDto.getOrderSetting5());
        goodsDetailsDtoResponse.setOrderSetting6(goodsDetailsDto.getOrderSetting6());
        goodsDetailsDtoResponse.setOrderSetting7(goodsDetailsDto.getOrderSetting7());
        goodsDetailsDtoResponse.setOrderSetting8(goodsDetailsDto.getOrderSetting8());
        goodsDetailsDtoResponse.setOrderSetting9(goodsDetailsDto.getOrderSetting9());
        goodsDetailsDtoResponse.setOrderSetting10(goodsDetailsDto.getOrderSetting10());
        goodsDetailsDtoResponse.setUnitImage(this.toGoodsImageEntityResponse(goodsDetailsDto.getUnitImage()));
        goodsDetailsDtoResponse.setGoodsOptionDisplayName(goodsDetailsDto.getGoodsOptionDisplayName());
        if (goodsDetailsDto.getGoodsClassType() != null) {
            goodsDetailsDtoResponse.setGoodsClassType(goodsDetailsDto.getGoodsClassType().getValue());
        }
        if (goodsDetailsDto.getDentalMonopolySalesFlg() != null) {
            goodsDetailsDtoResponse.setDentalMonopolySalesFlg(goodsDetailsDto.getDentalMonopolySalesFlg().getValue());
        }
        if (goodsDetailsDto.getSaleIconFlag() != null) {
            goodsDetailsDtoResponse.setSaleIconFlag(goodsDetailsDto.getSaleIconFlag().getValue());
        }
        if (goodsDetailsDto.getReserveIconFlag() != null) {
            goodsDetailsDtoResponse.setReserveIconFlag(goodsDetailsDto.getReserveIconFlag().getValue());
        }
        if (goodsDetailsDto.getNewIconFlag() != null) {
            goodsDetailsDtoResponse.setNewIconFlag(goodsDetailsDto.getNewIconFlag().getValue());
        }
        if (goodsDetailsDto.getLandSendFlag() != null) {
            goodsDetailsDtoResponse.setLandSendFlag(goodsDetailsDto.getLandSendFlag().getValue());
        }
        if (goodsDetailsDto.getCoolSendFlag() != null) {
            goodsDetailsDtoResponse.setCoolSendFlag(goodsDetailsDto.getCoolSendFlag().getValue());
        }
        goodsDetailsDtoResponse.setCoolSendFrom(goodsDetailsDto.getCoolSendFrom());
        goodsDetailsDtoResponse.setCoolSendTo(goodsDetailsDto.getCoolSendTo());
        goodsDetailsDtoResponse.setTax(goodsDetailsDto.getTax());
        goodsDetailsDtoResponse.setUnit(goodsDetailsDto.getUnit());
        goodsDetailsDtoResponse.setGoodsManagementCode(goodsDetailsDto.getGoodsManagementCode());
        goodsDetailsDtoResponse.setGoodsDivisionCode(goodsDetailsDto.getGoodsDivisionCode());
        goodsDetailsDtoResponse.setGoodsCategory1(goodsDetailsDto.getGoodsCategory1());
        goodsDetailsDtoResponse.setGoodsCategory2(goodsDetailsDto.getGoodsCategory2());
        goodsDetailsDtoResponse.setGoodsCategory3(goodsDetailsDto.getGoodsCategory3());
        if (goodsDetailsDto.getReserveFlag() != null) {
            goodsDetailsDtoResponse.setReserveFlag(goodsDetailsDto.getReserveFlag().getValue());
        }
        if (goodsDetailsDto.getPriceMarkDispFlag() != null) {
            goodsDetailsDtoResponse.setPriceMarkDispFlag(goodsDetailsDto.getPriceMarkDispFlag().getValue());
        }
        if (goodsDetailsDto.getSalePriceMarkDispFlag() != null) {
            goodsDetailsDtoResponse.setSalePriceMarkDispFlag(goodsDetailsDto.getSalePriceMarkDispFlag().getValue());
        }
        if (goodsDetailsDto.getSalePriceIntegrityFlag() != null) {
            goodsDetailsDtoResponse.setSalePriceIntegrityFlag(goodsDetailsDto.getSalePriceIntegrityFlag().getValue());
        }
        goodsDetailsDtoResponse.setSaleYesNo(goodsDetailsDto.getSaleYesNo());
        goodsDetailsDtoResponse.setSaleNgMessage(goodsDetailsDto.getSaleNgMessage());
        goodsDetailsDtoResponse.setDeliveryYesNo(goodsDetailsDto.getDeliveryYesNo());
        if (goodsDetailsDto.getEmotionPriceType() != null) {
            goodsDetailsDtoResponse.setEmotionPriceType(goodsDetailsDto.getEmotionPriceType().getValue());
        }

        return goodsDetailsDtoResponse;
    }

    /**
     * 商品詳細Dtoに変換
     *
     * @param goodsDetailsDtoRequest 商品詳細Dtoクラスレスポンス
     * @return 商品詳細Dto
     */
    public GoodsDetailsDto toGoodsDetailsDto(GoodsDetailsDtoRequest goodsDetailsDtoRequest) {
        if (ObjectUtils.isEmpty(goodsDetailsDtoRequest)) {
            return null;
        }

        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();

        goodsDetailsDto.setGoodsSeq(goodsDetailsDtoRequest.getGoodsSeq());
        goodsDetailsDto.setGoodsGroupSeq(goodsDetailsDtoRequest.getGoodsGroupSeq());
        goodsDetailsDto.setVersionNo(goodsDetailsDtoRequest.getVersionNo());
        goodsDetailsDto.setRegistTime(conversionUtility.toTimeStamp(goodsDetailsDtoRequest.getRegistTime()));
        goodsDetailsDto.setUpdateTime(conversionUtility.toTimeStamp(goodsDetailsDtoRequest.getUpdateTime()));
        goodsDetailsDto.setGoodsCode(goodsDetailsDtoRequest.getGoodsCode());
        goodsDetailsDto.setGoodsGroupMaxPrice(goodsDetailsDtoRequest.getGoodsGroupMaxPrice());
        goodsDetailsDto.setGoodsGroupMinPrice(goodsDetailsDtoRequest.getGoodsGroupMinPrice());
        goodsDetailsDto.setPreDiscountMinPrice(goodsDetailsDtoRequest.getPreDiscountMinPrice());
        goodsDetailsDto.setPreDiscountMaxPrice(goodsDetailsDtoRequest.getPreDiscountMaxPrice());
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getGoodsTaxType())) {
            goodsDetailsDto.setGoodsTaxType(EnumTypeUtil.getEnumFromValue(HTypeGoodsTaxType.class,
                                                                          goodsDetailsDtoRequest.getGoodsTaxType()
                                                                         ));
        }
        goodsDetailsDto.setTaxRate(goodsDetailsDtoRequest.getTaxRate());
        if (goodsDetailsDtoRequest.getAlcoholFlag() != null) {
            goodsDetailsDto.setAlcoholFlag(EnumTypeUtil.getEnumFromValue(HTypeAlcoholFlag.class,
                                                                         goodsDetailsDtoRequest.getAlcoholFlag()
                                                                        ));
        }
        goodsDetailsDto.setGoodsPriceInTax(goodsDetailsDtoRequest.getGoodsPriceInTax());
        goodsDetailsDto.setGoodsPrice(goodsDetailsDtoRequest.getGoodsPrice());
        goodsDetailsDto.setDeliveryType(goodsDetailsDtoRequest.getDeliveryType());
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getSaleStatus())) {
            goodsDetailsDto.setSaleStatusPC(EnumTypeUtil.getEnumFromValue(HTypeGoodsSaleStatus.class,
                                                                          goodsDetailsDtoRequest.getSaleStatus()
                                                                         ));
        }
        goodsDetailsDto.setSaleStartTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoRequest.getSaleStartTime()));
        goodsDetailsDto.setSaleEndTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoRequest.getSaleEndTime()));
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getUnitManagementFlag())) {
            goodsDetailsDto.setUnitManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class,
                                                                                goodsDetailsDtoRequest.getUnitManagementFlag()
                                                                               ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getStockManagementFlag())) {
            goodsDetailsDto.setStockManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeStockManagementFlag.class,
                                                                                 goodsDetailsDtoRequest.getStockManagementFlag()
                                                                                ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getIndividualDeliveryType())) {
            goodsDetailsDto.setIndividualDeliveryType(EnumTypeUtil.getEnumFromValue(HTypeIndividualDeliveryType.class,
                                                                                    goodsDetailsDtoRequest.getIndividualDeliveryType()
                                                                                   ));
        }
        goodsDetailsDto.setPurchasedMax(goodsDetailsDtoRequest.getPurchasedMax());
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getFreeDeliveryFlag())) {
            goodsDetailsDto.setFreeDeliveryFlag(EnumTypeUtil.getEnumFromValue(HTypeFreeDeliveryFlag.class,
                                                                              goodsDetailsDtoRequest.getFreeDeliveryFlag()
                                                                             ));
        }
        goodsDetailsDto.setOrderDisplay(goodsDetailsDtoRequest.getOrderDisplay());
        goodsDetailsDto.setUnitValue1(goodsDetailsDtoRequest.getUnitValue1());
        goodsDetailsDto.setUnitValue2(goodsDetailsDtoRequest.getUnitValue2());
        goodsDetailsDto.setPreDiscountPrice(goodsDetailsDtoRequest.getPreDiscountPrice());
        goodsDetailsDto.setPreDisCountPriceInTax(goodsDetailsDtoRequest.getPreDisCountPriceInTax());
        goodsDetailsDto.setJanCode(goodsDetailsDtoRequest.getJanCode());
        goodsDetailsDto.setCatalogCode(goodsDetailsDtoRequest.getCatalogCode());
        goodsDetailsDto.setSalesPossibleStock(goodsDetailsDtoRequest.getSalesPossibleStock());
        goodsDetailsDto.setRealStock(goodsDetailsDtoRequest.getRealStock());
        goodsDetailsDto.setOrderReserveStock(goodsDetailsDtoRequest.getOrderReserveStock());
        goodsDetailsDto.setRemainderFewStock(goodsDetailsDtoRequest.getRemainderFewStock());
        goodsDetailsDto.setOrderPointStock(goodsDetailsDtoRequest.getOrderPointStock());
        goodsDetailsDto.setSafetyStock(goodsDetailsDtoRequest.getSafetyStock());
        goodsDetailsDto.setGoodsGroupCode(goodsDetailsDtoRequest.getGoodsGroupCode());
        goodsDetailsDto.setWhatsnewDate(conversionUtility.toTimeStamp(goodsDetailsDtoRequest.getWhatsnewDate()));
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getGoodsOpenStatus())) {
            goodsDetailsDto.setGoodsOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                               goodsDetailsDtoRequest.getGoodsOpenStatus()
                                                                              ));
        }
        goodsDetailsDto.setOpenStartTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoRequest.getOpenStartTime()));
        goodsDetailsDto.setOpenEndTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoRequest.getOpenEndTime()));
        goodsDetailsDto.setGoodsGroupName(goodsDetailsDtoRequest.getGoodsGroupName());
        goodsDetailsDto.setUnitTitle1(goodsDetailsDtoRequest.getUnitTitle1());
        goodsDetailsDto.setUnitTitle2(goodsDetailsDtoRequest.getUnitTitle2());
        goodsDetailsDto.setGoodsPreDiscountPrice(goodsDetailsDtoRequest.getGoodsPreDiscountPrice());
        goodsDetailsDto.setGoodsGroupImageEntityList(
                        this.toGoodsGroupImageEntityList(goodsDetailsDtoRequest.getGoodsGroupImageEntityList()));
        goodsDetailsDto.setSnsLinkFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeSnsLinkFlag.class, goodsDetailsDtoRequest.getSnsLinkFlag()));
        goodsDetailsDto.setMetaDescription(goodsDetailsDtoRequest.getMetaDescription());
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getStockStatus())) {
            goodsDetailsDto.setStockStatusPc(EnumTypeUtil.getEnumFromValue(HTypeStockStatusType.class,
                                                                           goodsDetailsDtoRequest.getStockStatus()
                                                                          ));
        }
        goodsDetailsDto.setGoodsNote1(goodsDetailsDtoRequest.getGoodsNote1());
        goodsDetailsDto.setGoodsNote2(goodsDetailsDtoRequest.getGoodsNote2());
        goodsDetailsDto.setGoodsNote3(goodsDetailsDtoRequest.getGoodsNote3());
        goodsDetailsDto.setGoodsNote4(goodsDetailsDtoRequest.getGoodsNote4());
        goodsDetailsDto.setGoodsNote5(goodsDetailsDtoRequest.getGoodsNote5());
        goodsDetailsDto.setGoodsNote6(goodsDetailsDtoRequest.getGoodsNote6());
        goodsDetailsDto.setGoodsNote7(goodsDetailsDtoRequest.getGoodsNote7());
        goodsDetailsDto.setGoodsNote8(goodsDetailsDtoRequest.getGoodsNote8());
        goodsDetailsDto.setGoodsNote9(goodsDetailsDtoRequest.getGoodsNote9());
        goodsDetailsDto.setGoodsNote10(goodsDetailsDtoRequest.getGoodsNote10());
        goodsDetailsDto.setOrderSetting1(goodsDetailsDtoRequest.getOrderSetting1());
        goodsDetailsDto.setOrderSetting2(goodsDetailsDtoRequest.getOrderSetting2());
        goodsDetailsDto.setOrderSetting3(goodsDetailsDtoRequest.getOrderSetting3());
        goodsDetailsDto.setOrderSetting4(goodsDetailsDtoRequest.getOrderSetting4());
        goodsDetailsDto.setOrderSetting5(goodsDetailsDtoRequest.getOrderSetting5());
        goodsDetailsDto.setOrderSetting6(goodsDetailsDtoRequest.getOrderSetting6());
        goodsDetailsDto.setOrderSetting7(goodsDetailsDtoRequest.getOrderSetting7());
        goodsDetailsDto.setOrderSetting8(goodsDetailsDtoRequest.getOrderSetting8());
        goodsDetailsDto.setOrderSetting9(goodsDetailsDtoRequest.getOrderSetting9());
        goodsDetailsDto.setOrderSetting10(goodsDetailsDtoRequest.getOrderSetting10());
        goodsDetailsDto.setUnitImage(this.toGoodsImageEntity(goodsDetailsDtoRequest.getUnitImage()));
        goodsDetailsDto.setGoodsOptionDisplayName(goodsDetailsDtoRequest.getGoodsOptionDisplayName());
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getGoodsClassType())) {
            goodsDetailsDto.setGoodsClassType(EnumTypeUtil.getEnumFromValue(HTypeGoodsClassType.class,
                                                                            goodsDetailsDtoRequest.getGoodsClassType()
                                                                           ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getDentalMonopolySalesFlg())) {
            goodsDetailsDto.setDentalMonopolySalesFlg(EnumTypeUtil.getEnumFromValue(HTypeDentalMonopolySalesFlg.class,
                                                                                    goodsDetailsDtoRequest.getDentalMonopolySalesFlg()
                                                                                   ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getSaleIconFlag())) {
            goodsDetailsDto.setSaleIconFlag(EnumTypeUtil.getEnumFromValue(HTypeSaleIconFlag.class,
                                                                          goodsDetailsDtoRequest.getSaleIconFlag()
                                                                         ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getReserveIconFlag())) {
            goodsDetailsDto.setReserveIconFlag(EnumTypeUtil.getEnumFromValue(HTypeReserveIconFlag.class,
                                                                             goodsDetailsDtoRequest.getReserveIconFlag()
                                                                            ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getNewIconFlag())) {
            goodsDetailsDto.setNewIconFlag(EnumTypeUtil.getEnumFromValue(HTypeNewIconFlag.class,
                                                                         goodsDetailsDtoRequest.getNewIconFlag()
                                                                        ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getLandSendFlag())) {
            goodsDetailsDto.setLandSendFlag(EnumTypeUtil.getEnumFromValue(HTypeLandSendFlag.class,
                                                                          goodsDetailsDtoRequest.getLandSendFlag()
                                                                         ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getCoolSendFlag())) {
            goodsDetailsDto.setCoolSendFlag(EnumTypeUtil.getEnumFromValue(HTypeCoolSendFlag.class,
                                                                          goodsDetailsDtoRequest.getCoolSendFlag()
                                                                         ));
        }
        goodsDetailsDto.setCoolSendFrom(goodsDetailsDtoRequest.getCoolSendFrom());
        goodsDetailsDto.setCoolSendTo(goodsDetailsDtoRequest.getCoolSendTo());
        goodsDetailsDto.setTax(goodsDetailsDtoRequest.getTax());
        goodsDetailsDto.setUnit(goodsDetailsDtoRequest.getUnit());
        goodsDetailsDto.setGoodsManagementCode(goodsDetailsDtoRequest.getGoodsManagementCode());
        goodsDetailsDto.setGoodsDivisionCode(goodsDetailsDtoRequest.getGoodsDivisionCode());
        goodsDetailsDto.setGoodsCategory1(goodsDetailsDtoRequest.getGoodsCategory1());
        goodsDetailsDto.setGoodsCategory2(goodsDetailsDtoRequest.getGoodsCategory2());
        goodsDetailsDto.setGoodsCategory3(goodsDetailsDtoRequest.getGoodsCategory3());
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getReserveFlag())) {
            goodsDetailsDto.setReserveFlag(EnumTypeUtil.getEnumFromValue(HTypeReserveFlag.class,
                                                                         goodsDetailsDtoRequest.getReserveFlag()
                                                                        ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getPriceMarkDispFlag())) {
            goodsDetailsDto.setPriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypePriceMarkDispFlag.class,
                                                                               goodsDetailsDtoRequest.getPriceMarkDispFlag()
                                                                              ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getSalePriceMarkDispFlag())) {
            goodsDetailsDto.setSalePriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceMarkDispFlag.class,
                                                                                   goodsDetailsDtoRequest.getSalePriceMarkDispFlag()
                                                                                  ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getSalePriceIntegrityFlag())) {
            goodsDetailsDto.setSalePriceIntegrityFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceIntegrityFlag.class,
                                                                                    goodsDetailsDtoRequest.getSalePriceIntegrityFlag()
                                                                                   ));
        }
        goodsDetailsDto.setSaleYesNo(goodsDetailsDtoRequest.getSaleYesNo());
        goodsDetailsDto.setSaleNgMessage(goodsDetailsDtoRequest.getSaleNgMessage());
        goodsDetailsDto.setDeliveryYesNo(goodsDetailsDtoRequest.getDeliveryYesNo());
        if (StringUtils.isNotEmpty(goodsDetailsDtoRequest.getEmotionPriceType())) {
            goodsDetailsDto.setEmotionPriceType(EnumTypeUtil.getEnumFromValue(HTypeEmotionPriceType.class,
                                                                              goodsDetailsDtoRequest.getEmotionPriceType()
                                                                             ));
        }

        return goodsDetailsDto;
    }

    /**
     * 商品グループ画像レスポンスリストに変換
     *
     * @param imageEntityList 商品グループ画像リスト
     * @return 商品グループ画像レスポンス
     */
    public List<GoodsGroupImageEntityResponse> toGoodsGroupImageEntityRequestList(List<GoodsGroupImageEntity> imageEntityList) {
        if (CollectionUtils.isEmpty(imageEntityList)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntityResponse> imageEntityResponseList = new ArrayList<>();

        for (GoodsGroupImageEntity goodsGroupImage : imageEntityList) {
            GoodsGroupImageEntityResponse imageEntityResponse = new GoodsGroupImageEntityResponse();

            imageEntityResponse.setGoodsGroupSeq(goodsGroupImage.getGoodsGroupSeq());
            imageEntityResponse.setImageFileName(goodsGroupImage.getImageFileName());
            imageEntityResponse.setImageTypeVersionNo(goodsGroupImage.getImageTypeVersionNo());
            imageEntityResponse.setRegistTime(goodsGroupImage.getRegistTime());
            imageEntityResponse.setUpdateTime(goodsGroupImage.getUpdateTime());

            imageEntityResponseList.add(imageEntityResponse);
        }
        return imageEntityResponseList;
    }

    /**
     * 商品グループ画像リストに変換
     *
     * @param goodsGroupImageEntityRequestList 商品グループ画像リクエストリスト
     * @return 商品グループ画像リスト
     */
    public List<GoodsGroupImageEntity> toGoodsGroupImageEntityList(List<GoodsGroupImageEntityRequest> goodsGroupImageEntityRequestList) {
        if (CollectionUtils.isEmpty(goodsGroupImageEntityRequestList)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntity> imageEntityList = new ArrayList<>();

        for (GoodsGroupImageEntityRequest goodsGroupImage : goodsGroupImageEntityRequestList) {
            GoodsGroupImageEntity imageEntity = new GoodsGroupImageEntity();

            imageEntity.setGoodsGroupSeq(goodsGroupImage.getGoodsGroupSeq());
            imageEntity.setImageFileName(goodsGroupImage.getImageFileName());
            imageEntity.setImageTypeVersionNo(goodsGroupImage.getImageTypeVersionNo());
            imageEntity.setRegistTime(conversionUtility.toTimeStamp(goodsGroupImage.getRegistTime()));
            imageEntity.setUpdateTime(conversionUtility.toTimeStamp(goodsGroupImage.getUpdateTime()));

            imageEntityList.add(imageEntity);
        }
        return imageEntityList;
    }

    /**
     * 商品グループ画像レスポンスに変換
     *
     * @param goodsImageEntity 商品画像
     * @return 商品グループ画像レスポンス
     */
    public GoodsImageEntityResponse toGoodsImageEntityResponse(GoodsImageEntity goodsImageEntity) {
        if (ObjectUtils.isEmpty(goodsImageEntity)) {
            return null;
        }
        GoodsImageEntityResponse imageResponse = new GoodsImageEntityResponse();

        imageResponse.setGoodsSeq(goodsImageEntity.getGoodsSeq());
        imageResponse.setGoodsGroupSeq(goodsImageEntity.getGoodsGroupSeq());
        imageResponse.setImageFileName(goodsImageEntity.getImageFileName());
        if (goodsImageEntity.getDisplayFlag() != null) {
            imageResponse.setDisplayFlag(goodsImageEntity.getDisplayFlag().getValue());
        }
        imageResponse.setTmpFilePath(goodsImageEntity.getTmpFilePath());
        imageResponse.setRegistTime(goodsImageEntity.getRegistTime());
        imageResponse.setUpdateTime(goodsImageEntity.getUpdateTime());
        return imageResponse;
    }

    /**
     * 商品画像に変換
     *
     * @param goodsImageEntityRequest 商品グループ画像リクエスト
     * @return 商品画像
     */
    public GoodsImageEntity toGoodsImageEntity(GoodsImageEntityRequest goodsImageEntityRequest) {
        if (ObjectUtils.isEmpty(goodsImageEntityRequest)) {
            return null;
        }
        GoodsImageEntity imageEntity = new GoodsImageEntity();

        imageEntity.setGoodsSeq(goodsImageEntityRequest.getGoodsSeq());
        imageEntity.setGoodsGroupSeq(goodsImageEntityRequest.getGoodsGroupSeq());
        imageEntity.setImageFileName(goodsImageEntityRequest.getImageFileName());
        imageEntity.setDisplayFlag(EnumTypeUtil.getEnumFromValue(HTypeDisplayStatus.class,
                                                                 goodsImageEntityRequest.getDisplayFlag()
                                                                ));
        imageEntity.setTmpFilePath(goodsImageEntityRequest.getTmpFilePath());
        imageEntity.setRegistTime(conversionUtility.toTimeStamp(goodsImageEntityRequest.getRegistTime()));
        imageEntity.setUpdateTime(conversionUtility.toTimeStamp(goodsImageEntityRequest.getUpdateTime()));

        return imageEntity;
    }

    /**
     * 割引適用結果MAPに変換
     *
     * @param webApiGetDiscountMap 割引適用結果MAP
     * @return 割引適用結果MAP
     */
    public Map<String, WebApiGetDiscountsResponseDetailDtoResponse> toWebApiGetDiscountsResponseDetailDtoResponseMap(Map<String, WebApiGetDiscountsResponseDetailDto> webApiGetDiscountMap) {
        Map<String, WebApiGetDiscountsResponseDetailDtoResponse> webApiGetDiscountResponseMap = new HashMap<>();

        if (MapUtils.isNotEmpty(webApiGetDiscountMap)) {
            webApiGetDiscountMap.forEach((key, value) -> {
                if (ObjectUtils.isNotEmpty(value)) {
                    WebApiGetDiscountsResponseDetailDtoResponse response =
                                    new WebApiGetDiscountsResponseDetailDtoResponse();
                    response.setGoodsCode(value.getGoodsCode());
                    response.setOrderDisplay(value.getOrderDisplay());
                    response.setHints(value.getHints());
                    response.setQuantity(value.getQuantity());
                    response.setNote(value.getNote());
                    response.setSaleCode(value.getSaleCode());
                    response.setSaleType(value.getSaleType());
                    response.setSalePrice(value.getSalePrice());
                    response.setSaleGroupCode(value.getSaleGroupCode());

                    webApiGetDiscountResponseMap.put(key, response);
                }
            });
        }

        return webApiGetDiscountResponseMap;
    }

    /**
     * 割引適用結果MAPに変換
     *
     * @param webApiGetDiscountRequestMap 割引適用結果MAP
     * @return 割引適用結果MAP
     */
    public Map<String, WebApiGetDiscountsResponseDetailDto> toWebApiGetDiscountsResponseDetailDtoMap(Map<String, WebApiGetDiscountsResponseDetailDtoRequest> webApiGetDiscountRequestMap) {
        Map<String, WebApiGetDiscountsResponseDetailDto> webApiGetDiscountMap = new HashMap<>();

        if (MapUtils.isNotEmpty(webApiGetDiscountRequestMap)) {
            webApiGetDiscountRequestMap.forEach((key, value) -> {
                if (ObjectUtils.isNotEmpty(value)) {
                    WebApiGetDiscountsResponseDetailDto detailDto = new WebApiGetDiscountsResponseDetailDto();
                    detailDto.setGoodsCode(value.getGoodsCode());
                    detailDto.setOrderDisplay(value.getOrderDisplay());
                    detailDto.setHints(value.getHints());
                    detailDto.setQuantity(value.getQuantity());
                    detailDto.setNote(value.getNote());
                    detailDto.setSaleCode(value.getSaleCode());
                    detailDto.setSaleType(value.getSaleType());
                    detailDto.setSalePrice(value.getSalePrice());
                    detailDto.setSaleGroupCode(value.getSaleGroupCode());

                    webApiGetDiscountMap.put(key, detailDto);
                }
            });
        }

        return webApiGetDiscountMap;
    }

    /**
     * 消費税率MAPに変換
     *
     * @param webApiGetConsumptionTaxRate 消費税率MAP
     * @return 消費税率MAP
     */
    public Map<String, WebApiGetConsumptionTaxRateResponseDetailDtoResponse> toWebApiGetConsumptionTaxRateResponseDetailDtoResponseMap(
                    Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> webApiGetConsumptionTaxRate) {
        Map<String, WebApiGetConsumptionTaxRateResponseDetailDtoResponse> webApiGetConsumptionTaxRateResponseMap =
                        new HashMap<>();

        if (MapUtils.isNotEmpty(webApiGetConsumptionTaxRate)) {
            webApiGetConsumptionTaxRate.forEach((key, value) -> {
                if (ObjectUtils.isNotEmpty(value)) {
                    WebApiGetConsumptionTaxRateResponseDetailDtoResponse response =
                                    new WebApiGetConsumptionTaxRateResponseDetailDtoResponse();
                    response.setGoodsCode(value.getGoodsCode());
                    response.setTaxRate(value.getTaxRate());

                    webApiGetConsumptionTaxRateResponseMap.put(key, response);
                }
            });
        }

        return webApiGetConsumptionTaxRateResponseMap;
    }

    /**
     * 消費税率MAPに変換
     *
     * @param webApiGetConsumptionTaxRateRequest 割引適用結果MAP
     * @return 消費税率MAP
     */
    public Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> toWebApiGetConsumptionTaxRateResponseDetailDtoMap(
                    Map<String, WebApiGetConsumptionTaxRateResponseDetailDtoRequest> webApiGetConsumptionTaxRateRequest) {
        Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> webApiGetConsumptionTaxRateMap = new HashMap<>();

        if (MapUtils.isNotEmpty(webApiGetConsumptionTaxRateRequest)) {
            webApiGetConsumptionTaxRateRequest.forEach((key, value) -> {
                if (ObjectUtils.isNotEmpty(value)) {
                    WebApiGetConsumptionTaxRateResponseDetailDto detailDto =
                                    new WebApiGetConsumptionTaxRateResponseDetailDto();
                    detailDto.setGoodsCode(value.getGoodsCode());
                    detailDto.setTaxRate(value.getTaxRate());

                    webApiGetConsumptionTaxRateMap.put(key, detailDto);
                }
            });
        }

        return webApiGetConsumptionTaxRateMap;
    }

    /**
     * チェックメッセージDtoListレスポンスに変換
     *
     * @param checkMessageDtoList エラーリスト
     * @return チェックメッセージDtoListレスポンス
     */
    public List<CheckMessageDtoResponse> toCheckMessageDtoResponseList(List<CheckMessageDto> checkMessageDtoList) {
        if (CollectionUtils.isEmpty(checkMessageDtoList)) {
            return new ArrayList<>();
        }
        List<CheckMessageDtoResponse> checkMessageDtoResponseList = new ArrayList<>();
        for (CheckMessageDto msgDto : checkMessageDtoList) {
            CheckMessageDtoResponse msgResponse = new CheckMessageDtoResponse();
            msgResponse.setMessageId(msgDto.getMessageId());
            msgResponse.setMessage(msgDto.getMessage());
            if (ObjectUtils.isNotEmpty(msgDto.getArgs())) {
                msgResponse.setArgs(Arrays.asList(msgDto.getArgs()));
            }
            msgResponse.setError(msgDto.isError());
            msgResponse.setOrderConsecutiveNo(msgDto.getOrderConsecutiveNo());

            checkMessageDtoResponseList.add(msgResponse);
        }

        return checkMessageDtoResponseList;
    }

    /**
     * カート商品チェックMapレスポンスに変換
     *
     * @param cartGoodsCheck エラー情報MAP
     * @return カート商品チェックMapレスポンス
     */
    public CartGoodsCheckMapResponse toCartGoodsCheckMapResponse(Map<Integer, List<CheckMessageDto>> cartGoodsCheck) {
        if (MapUtils.isEmpty(cartGoodsCheck)) {
            return null;
        }

        CartGoodsCheckMapResponse cartGoodsCheckResponse = new CartGoodsCheckMapResponse();
        Map<String, List<CheckMessageDtoResponse>> messageResponseMap = new HashMap<>();

        cartGoodsCheck.forEach((key, value) -> {
            messageResponseMap.put(conversionUtility.toString(key), this.toCheckMessageDtoResponseList(value));
        });

        cartGoodsCheckResponse.setMessages(messageResponseMap);

        return cartGoodsCheckResponse;
    }

    // 2023-renew No14 from here

    /**
     * 数量割引適用結果取得レスポンスMAPに変換
     *
     * @param quantityDiscountsResponseDetailMap 数量割引適用結果MAP
     * @return 数量割引適用結果取得レスポンスMAP
     */
    public Map<String, WebApiGetQuantityDiscountResultResponseDetailDtoResponse> toWebApiGetQuantityDiscountResultResponseDetailDtoResponseMap(
                    Map<String, WebApiGetQuantityDiscountResultResponseDetailDto> quantityDiscountsResponseDetailMap) {
        Map<String, WebApiGetQuantityDiscountResultResponseDetailDtoResponse>
                        quantityDiscountResultResponseDetailDtoResponseMap = new HashMap<>();

        if (MapUtils.isNotEmpty(quantityDiscountsResponseDetailMap)) {
            quantityDiscountsResponseDetailMap.forEach((key, value) -> {
                if (ObjectUtils.isNotEmpty(value)) {
                    WebApiGetQuantityDiscountResultResponseDetailDtoResponse response =
                                    new WebApiGetQuantityDiscountResultResponseDetailDtoResponse();

                    response.setGoodsCode(value.getGoodsCode());
                    response.setSaleType(value.getSaleType());
                    response.setSaleGroupCode(value.getSaleGroupCode());
                    response.setSalePrice(value.getSalePrice());
                    response.setQuantity(value.getQuantity());
                    response.setSaleCode(value.getSaleCode());
                    response.setNote(value.getNote());
                    response.setHints(value.getHints());
                    response.setPrice(value.getPrice());

                    quantityDiscountResultResponseDetailDtoResponseMap.put(key, response);
                }
            });
        }

        return quantityDiscountResultResponseDetailDtoResponseMap;
    }

    /**
     * 数量割引適用結果取得MAPに変換
     *
     * @param quantityDiscountsResponseDetailRequestMap 数量割引適用結果取得リクエストMAP
     * @return 数量割引適用結果取得MAP
     */
    public Map<String, WebApiGetQuantityDiscountResultResponseDetailDto> toWebApiGetQuantityDiscountResultResponseDetailDtoMapRequest(
                    Map<String, WebApiGetQuantityDiscountResultResponseDetailDtoRequest> quantityDiscountsResponseDetailRequestMap) {
        Map<String, WebApiGetQuantityDiscountResultResponseDetailDto> quantityDiscountResultResponseDetailDtoMap =
                        new HashMap<>();

        if (MapUtils.isNotEmpty(quantityDiscountsResponseDetailRequestMap)) {
            quantityDiscountsResponseDetailRequestMap.forEach((key, value) -> {
                if (ObjectUtils.isNotEmpty(value)) {
                    WebApiGetQuantityDiscountResultResponseDetailDto detailDto =
                                    new WebApiGetQuantityDiscountResultResponseDetailDto();

                    detailDto.setGoodsCode(value.getGoodsCode());
                    detailDto.setSaleType(value.getSaleType());
                    detailDto.setSaleGroupCode(value.getSaleGroupCode());
                    detailDto.setSalePrice(value.getSalePrice());
                    detailDto.setQuantity(value.getQuantity());
                    detailDto.setSaleCode(value.getSaleCode());
                    detailDto.setNote(value.getNote());
                    detailDto.setHints(value.getHints());
                    detailDto.setPrice(value.getPrice());

                    quantityDiscountResultResponseDetailDtoMap.put(key, detailDto);
                }
            });
        }

        return quantityDiscountResultResponseDetailDtoMap;
    }

    /**
     * 取りおき情報レスポンスMAPに変換
     *
     * @param reserveMap 取りおき情報MAP
     * @return the map
     */
    public Map<String, WebApiGetReserveResponseDetailDtoResponse> toWebApiGetReserveResponseDetailDtoResponseMap(Map<String, WebApiGetReserveResponseDetailDto> reserveMap) {
        Map<String, WebApiGetReserveResponseDetailDtoResponse> reserveResponseDetailDtoResponseMap = new HashMap<>();

        if (MapUtils.isNotEmpty(reserveMap)) {
            reserveMap.forEach((key, value) -> {
                if (ObjectUtils.isNotEmpty(value)) {
                    WebApiGetReserveResponseDetailDtoResponse response =
                                    new WebApiGetReserveResponseDetailDtoResponse();

                    response.setGoodsCode(value.getGoodsCode());
                    response.setReserveFlag(value.getReserveFlag());
                    response.setDiscountFlag(value.getDiscountFlag());
                    response.setPossibleReserveFromDay(value.getPossibleReserveFromDay());
                    response.setPossibleReserveToDay(value.getPossibleReserveToDay());

                    reserveResponseDetailDtoResponseMap.put(key, response);
                }
            });
        }

        return reserveResponseDetailDtoResponseMap;
    }

    /**
     * 取りおき情報MAPに変換
     *
     * @param reserveMap 取りおき情報リクエストMAP
     * @return 取りおき情報MAP
     */
    public Map<String, WebApiGetReserveResponseDetailDto> toWebApiGetReserveResponseDetailDtoMap(Map<String, WebApiGetReserveResponseDetailDtoRequest> reserveMap) {
        Map<String, WebApiGetReserveResponseDetailDto> webApiGetReserveResponseDetailDtoMap = new HashMap<>();

        if (MapUtils.isNotEmpty(reserveMap)) {
            reserveMap.forEach((key, value) -> {
                if (ObjectUtils.isNotEmpty(value)) {
                    WebApiGetReserveResponseDetailDto detailDto = new WebApiGetReserveResponseDetailDto();

                    detailDto.setGoodsCode(value.getGoodsCode());
                    detailDto.setReserveFlag(value.getReserveFlag());
                    detailDto.setDiscountFlag(value.getDiscountFlag());
                    detailDto.setPossibleReserveFromDay(
                                    conversionUtility.toTimeStamp(value.getPossibleReserveFromDay()));
                    detailDto.setPossibleReserveToDay(conversionUtility.toTimeStamp(value.getPossibleReserveToDay()));

                    webApiGetReserveResponseDetailDtoMap.put(key, detailDto);
                }
            });
        }

        return webApiGetReserveResponseDetailDtoMap;
    }

    /**
     * 販売可否判定レスポンスMAPに変換
     *
     * @param saleCheckMap 販売可否判定MAP
     * @return the map
     */
    public Map<String, WebApiGetSaleCheckDetailResponse> toWebApiGetSaleCheckDetailResponseMap(Map<String, WebApiGetSaleCheckResponseDetailDto> saleCheckMap) {
        Map<String, WebApiGetSaleCheckDetailResponse> saleCheckDetailResponseMap = new HashMap<>();

        if (MapUtils.isNotEmpty(saleCheckMap)) {
            saleCheckMap.forEach((key, value) -> {
                if (ObjectUtils.isNotEmpty(value)) {
                    WebApiGetSaleCheckDetailResponse response = new WebApiGetSaleCheckDetailResponse();

                    response.setGoodsCode(value.getGoodsCode());
                    response.setGoodsSaleYesNo(value.getGoodsSaleYesNo());

                    saleCheckDetailResponseMap.put(key, response);
                }
            });
        }

        return saleCheckDetailResponseMap;
    }

    /**
     * 販売可否判定MAPに変換
     *
     * @param saleCheckMap 販売可否判定レスポンスMAP
     * @return 販売可否判定MAP
     */
    public Map<String, WebApiGetSaleCheckResponseDetailDto> toWebApiGetSaleCheckResponseDetailDtoMap(Map<String, WebApiGetSaleCheckDetailResponse> saleCheckMap) {
        Map<String, WebApiGetSaleCheckResponseDetailDto> webApiGetSaleCheckResponseDetailDtoMap = new HashMap<>();

        if (MapUtils.isNotEmpty(saleCheckMap)) {
            saleCheckMap.forEach((key, value) -> {
                if (ObjectUtils.isNotEmpty(value)) {
                    WebApiGetSaleCheckResponseDetailDto detailDto = new WebApiGetSaleCheckResponseDetailDto();

                    detailDto.setGoodsCode(value.getGoodsCode());
                    detailDto.setGoodsSaleYesNo(value.getGoodsSaleYesNo());

                    webApiGetSaleCheckResponseDetailDtoMap.put(key, detailDto);
                }
            });
        }

        return webApiGetSaleCheckResponseDetailDtoMap;
    }

    // 2023-renew No14 to here

}
