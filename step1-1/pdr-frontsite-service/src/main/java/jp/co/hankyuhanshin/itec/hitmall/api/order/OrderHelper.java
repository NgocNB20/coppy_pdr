package jp.co.hankyuhanshin.itec.hitmall.api.order;

import jp.co.hankyuhanshin.itec.hitmall.api.order.param.BillPriceCalculateResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.CardBrandResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.CartDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.CartGoodsDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.CartGoodsEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.CheckMessageDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.CheckMessageDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.ConsumptionTaxRateResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.ConvenienceEntityListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.ConvenienceResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryDetailsDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryImpossibleAreaResultDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryMethodDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryMethodEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryMethodRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryMethodTypeCarriageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliverySpecialChargeAreaResultDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.GoodsDetailsDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.GoodsGroupImageEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.GoodsGroupImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.GoodsImageEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.GoodsImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderDeliveryDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderDeliveryInformationDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderDeliveryNowDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderDeliveryRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderDependingOnReceiptGoodsDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderGetReserveMapResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderGetStockMapResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderGoodsRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderInfoMasterDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderInfoMasterGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderMessageDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderReserveDeliveryDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.ReceiverDateDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.ReceiverDateDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.SettlementDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.SettlementDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.TaxRateEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.WebApiGetConsumptionTaxRateResponseDetailDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.WebApiGetConsumptionTaxRateResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.WebApiGetDeliveryInformationRequestDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.WebApiGetDeliveryInformationResponseDetailDateInfoDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.WebApiGetDeliveryInformationResponseDetailDateInfoDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.WebApiGetDeliveryInformationResponseDetailDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.WebApiGetDiscountsResponseDetailDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.WebApiGetReserveResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.WebApiGetStockResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCardRegistType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeConfDocumentType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCoolSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryCycle;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliverySlipFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDentalMonopolySalesFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDiscountsType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDisplayStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFrontBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsClassType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeInvoiceAttachmentFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeLandSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberListType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReceiverDateDesignationFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRequisitionType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReservationDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalesFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipmentStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShortfallDisplayFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTaxRateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTotalingType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderInfoMasterDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryNowDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDependingOnReceiptGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderReserveDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleAreaResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryMethodDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySpecialChargeAreaResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.ReceiverDateDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetConsumptionTaxRateResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDeliveryInformationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDeliveryInformationResponseDetailDateInfoDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDeliveryInformationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDiscountsResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.conveni.ConvenienceEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.CardBrandEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodTypeCarriageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.tax.TaxRateEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetCutomerNoNextValLogic;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
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
 * 注文Helper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class OrderHelper {

    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    /**
     * 顧客番号採番ロジック
     */
    private final MemberInfoGetCutomerNoNextValLogic memberInfoGetCutomerNoNextValLogic;

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    /**
     * コンストラクタ
     *
     * @param conversionUtility                  変換ユーティリティクラス
     * @param memberInfoGetCutomerNoNextValLogic 顧客番号採番ロジック
     * @param dateUtility                        日付関連Utility
     */
    @Autowired
    public OrderHelper(ConversionUtility conversionUtility,
                       MemberInfoGetCutomerNoNextValLogic memberInfoGetCutomerNoNextValLogic,
                       DateUtility dateUtility) {
        this.conversionUtility = conversionUtility;
        this.memberInfoGetCutomerNoNextValLogic = memberInfoGetCutomerNoNextValLogic;
        this.dateUtility = dateUtility;
    }

    /**
     * カートDtoに変換
     *
     * @param orderInfoMasterGetRequest 注文情報マスタ取得リクエスト
     * @return カートDto cart dto
     */
    public CartDto toCartDto(OrderInfoMasterGetRequest orderInfoMasterGetRequest) {

        if (ObjectUtils.isEmpty(orderInfoMasterGetRequest)) {
            return null;
        }

        CartDtoRequest cartDtoRequest = orderInfoMasterGetRequest.getCartDto();
        CartDto cartDto = new CartDto();

        cartDto.setGoodsTotalCount(cartDtoRequest.getGoodsTotalCount());
        cartDto.setGoodsTotalPrice(cartDtoRequest.getGoodsTotalPrice());
        cartDto.setGoodsTotalPriceInTax(cartDtoRequest.getGoodsTotalPriceInTax());
        cartDto.setBeforeTransferEmotionGoodsCodeMap(cartDto.getBeforeTransferEmotionGoodsCodeMap());
        cartDto.setTotalPriceTax(cartDtoRequest.getTotalPriceTax());

        cartDto.setCartGoodsDtoList(toCartGoodsDtoList(cartDtoRequest.getCartGoodsDtoList()));
        cartDto.setSettlementMethodType(EnumTypeUtil.getEnumFromValue(HTypeSettlementMethodType.class,
                                                                      cartDtoRequest.getSettlementMethodType()
                                                                     ));
        cartDto.setDiscountsResponseDetailMap(
                        toDiscountsResponseDetailMap(cartDtoRequest.getDiscountsResponseDetailMap()));
        cartDto.setConsumptionTaxRateMap(toConsumptionTaxRateMap(cartDtoRequest.getConsumptionTaxRateMap()));

        return cartDto;
    }

    /**
     * コンビニEntityListレスポンスに変換
     *
     * @param convenienceEntityList コンビニEntityList
     * @return コンビニEntityListレスポンス
     */
    public ConvenienceEntityListResponse toConvenienceEntityListResponse(List<ConvenienceEntity> convenienceEntityList) {

        ConvenienceEntityListResponse response = new ConvenienceEntityListResponse();

        if (CollectionUtil.isEmpty(convenienceEntityList)) {
            response.setConvenienceList(new ArrayList<>());
            return response;
        }

        List<ConvenienceResponse> convenienceListResponse = new ArrayList<>();
        convenienceEntityList.forEach(convenienceEntity -> {
            ConvenienceResponse convenienceResponse = new ConvenienceResponse();

            convenienceResponse.setConveniSeq(convenienceEntity.getConveniSeq());
            convenienceResponse.setPayCode(convenienceEntity.getPayCode());
            convenienceResponse.setConveniCode(convenienceEntity.getConveniCode());
            convenienceResponse.setPayName(convenienceEntity.getPayName());
            convenienceResponse.setConveniName(convenienceEntity.getConveniName());
            convenienceResponse.setRegistTime(convenienceEntity.getRegistTime());
            convenienceResponse.setUpdateTime(convenienceEntity.getUpdateTime());
            convenienceResponse.setDisplayOrder(convenienceEntity.getDisplayOrder());

            if (convenienceEntity.getUseFlag() != null) {
                convenienceResponse.setUseFlag(convenienceEntity.getUseFlag().getValue());
            }

            convenienceListResponse.add(convenienceResponse);
        });

        response.setConvenienceList(convenienceListResponse);

        return response;
    }

    /**
     * 注文情報マスタDtoレスポンスに変換
     *
     * @param orderInfoMasterDto 注文情報マスタDto
     * @return 注文情報マスタDtoレスポンス
     */
    public OrderInfoMasterDtoResponse toOrderInfoMasterDtoResponse(OrderInfoMasterDto orderInfoMasterDto) {

        OrderInfoMasterDtoResponse orderInfoMasterDtoResponse = new OrderInfoMasterDtoResponse();

        if (MapUtils.isNotEmpty(orderInfoMasterDto.getGoodsMaster())) {
            orderInfoMasterDtoResponse.setGoodsMaster(
                            toOrderInfoMasterDtoResponse(orderInfoMasterDto.getGoodsMaster()));
        }
        if (MapUtils.isNotEmpty(orderInfoMasterDto.getTaxRateMaster())) {
            orderInfoMasterDtoResponse.setTaxRateMaster(
                            toTaxRateMasterMapResponse(orderInfoMasterDto.getTaxRateMaster()));
        }
        if (MapUtils.isNotEmpty(orderInfoMasterDto.getDeliveryMethodMaster())) {
            orderInfoMasterDtoResponse.setDeliveryMethodMaster(
                            toDeliveryMethodMasterMapResponse(orderInfoMasterDto.getDeliveryMethodMaster()));
        }

        return orderInfoMasterDtoResponse;
    }

    /**
     * 受注商品クラスリストに変換
     *
     * @param orderGoodsRequestList 受注商品リクエストリスト
     * @return 受注商品クラスリスト
     */
    public List<OrderGoodsEntity> toOrderGoodsEntityList(List<OrderGoodsRequest> orderGoodsRequestList) {

        if (CollectionUtil.isEmpty(orderGoodsRequestList)) {
            return new ArrayList<>();
        }

        List<OrderGoodsEntity> orderGoodsEntityList = new ArrayList<>();

        orderGoodsRequestList.forEach(orderGoodsRequest -> {

            OrderGoodsEntity orderGoodsEntity = new OrderGoodsEntity();

            orderGoodsEntity.setOrderSeq(orderGoodsRequest.getOrderSeq());
            orderGoodsEntity.setOrderGoodsVersionNo(orderGoodsRequest.getOrderGoodsVersionNo());
            orderGoodsEntity.setOrderConsecutiveNo(orderGoodsRequest.getOrderConsecutiveNo());
            orderGoodsEntity.setGoodsSeq(orderGoodsRequest.getGoodsSeq());
            orderGoodsEntity.setOrderDisplay(orderGoodsRequest.getOrderDisplay());
            orderGoodsEntity.setProcessTime(conversionUtility.toTimeStamp(orderGoodsRequest.getProcessTime()));
            orderGoodsEntity.setGoodsGroupCode(orderGoodsRequest.getGoodsGroupCode());
            orderGoodsEntity.setGoodsCode(orderGoodsRequest.getGoodsCode());
            orderGoodsEntity.setJanCode(orderGoodsRequest.getJanCode());
            orderGoodsEntity.setGoodsGroupName(orderGoodsRequest.getGoodsGroupName());
            orderGoodsEntity.setTaxRate(orderGoodsRequest.getTaxRate());
            orderGoodsEntity.setGoodsPrice(orderGoodsRequest.getGoodsPrice());
            orderGoodsEntity.setPreGoodsCount(orderGoodsRequest.getPreGoodsCount());
            orderGoodsEntity.setGoodsCount(orderGoodsRequest.getGoodsCount());
            orderGoodsEntity.setUnitValue1(orderGoodsRequest.getUnitValue1());
            orderGoodsEntity.setUnitValue2(orderGoodsRequest.getUnitValue2());
            orderGoodsEntity.setDeliveryType(orderGoodsRequest.getDeliveryType());
            orderGoodsEntity.setOrderSetting1(orderGoodsRequest.getOrderSetting1());
            orderGoodsEntity.setOrderSetting2(orderGoodsRequest.getOrderSetting2());
            orderGoodsEntity.setOrderSetting3(orderGoodsRequest.getOrderSetting3());
            orderGoodsEntity.setOrderSetting4(orderGoodsRequest.getOrderSetting4());
            orderGoodsEntity.setOrderSetting5(orderGoodsRequest.getOrderSetting5());
            orderGoodsEntity.setOrderSetting6(orderGoodsRequest.getOrderSetting6());
            orderGoodsEntity.setOrderSetting7(orderGoodsRequest.getOrderSetting7());
            orderGoodsEntity.setOrderSetting8(orderGoodsRequest.getOrderSetting8());
            orderGoodsEntity.setOrderSetting9(orderGoodsRequest.getOrderSetting9());
            orderGoodsEntity.setOrderSetting10(orderGoodsRequest.getOrderSetting10());
            orderGoodsEntity.setRegistTime(conversionUtility.toTimeStamp(orderGoodsRequest.getRegistTime()));
            orderGoodsEntity.setUpdateTime(conversionUtility.toTimeStamp(orderGoodsRequest.getUpdateTime()));
            orderGoodsEntity.setGroupCode(orderGoodsRequest.getGroupCode());
            orderGoodsEntity.setSaleCode(orderGoodsRequest.getSaleCode());
            orderGoodsEntity.setNote(orderGoodsRequest.getNote());
            orderGoodsEntity.setHints(orderGoodsRequest.getHints());
            orderGoodsEntity.setPrice(orderGoodsRequest.getPrice());
            orderGoodsEntity.setBundleFlag(orderGoodsRequest.getBundleFlag());
            orderGoodsEntity.setOrderSerial(orderGoodsRequest.getOrderSerial());
            orderGoodsEntity.setUnitDiscountPrice(orderGoodsRequest.getUnitDiscountPrice());
            orderGoodsEntity.setStockDate(conversionUtility.toTimeStamp(orderGoodsRequest.getStockDate()));
            orderGoodsEntity.setDeliveryYesNo(orderGoodsRequest.getDeliveryYesNo());
            orderGoodsEntity.setDipendingOnReceiptOrderCode(orderGoodsRequest.getDipendingOnReceiptOrderCode());

            orderGoodsEntity.setTotalingType(EnumTypeUtil.getEnumFromValue(HTypeTotalingType.class,
                                                                           orderGoodsRequest.getTotalingType()
                                                                          ));
            orderGoodsEntity.setSalesFlag(
                            EnumTypeUtil.getEnumFromValue(HTypeSalesFlag.class, orderGoodsRequest.getSalesFlag()));
            orderGoodsEntity.setGoodsTaxType(EnumTypeUtil.getEnumFromValue(HTypeGoodsTaxType.class,
                                                                           orderGoodsRequest.getGoodsTaxType()
                                                                          ));
            orderGoodsEntity.setFreeDeliveryFlag(EnumTypeUtil.getEnumFromValue(HTypeFreeDeliveryFlag.class,
                                                                               orderGoodsRequest.getFreeDeliveryFlag()
                                                                              ));
            orderGoodsEntity.setIndividualDeliveryType(EnumTypeUtil.getEnumFromValue(HTypeIndividualDeliveryType.class,
                                                                                     orderGoodsRequest.getIndividualDeliveryType()
                                                                                    ));
            orderGoodsEntity.setDeliverySlipFlag(EnumTypeUtil.getEnumFromValue(HTypeDeliverySlipFlag.class,
                                                                               orderGoodsRequest.getDeliverySlipFlag()
                                                                              ));
            orderGoodsEntity.setDiscountsType(EnumTypeUtil.getEnumFromValue(HTypeDiscountsType.class,
                                                                            orderGoodsRequest.getDiscountsType()
                                                                           ));
            orderGoodsEntity.setStockManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeStockManagementFlag.class,
                                                                                  orderGoodsRequest.getStockManagementFlag()
                                                                                 ));

            orderGoodsEntityList.add(orderGoodsEntity);
        });

        return orderGoodsEntityList;
    }

    /**
     * 配送情報取得に変換
     *
     * @param webApiGetDeliveryInformationRequestDtoRequest API連携リクエストDTOリクエスト
     * @return 配送情報取得
     */
    public WebApiGetDeliveryInformationRequestDto toWebApiGetDeliveryInformationRequestDto(
                    WebApiGetDeliveryInformationRequestDtoRequest webApiGetDeliveryInformationRequestDtoRequest) {

        if (ObjectUtils.isEmpty(webApiGetDeliveryInformationRequestDtoRequest)) {
            return null;
        }

        WebApiGetDeliveryInformationRequestDto reqDto = new WebApiGetDeliveryInformationRequestDto();

        reqDto.setOrderCustomerNo(webApiGetDeliveryInformationRequestDtoRequest.getOrderCustomerNo());
        reqDto.setDeliveryCustomerNo(webApiGetDeliveryInformationRequestDtoRequest.getDeliveryCustomerNo());
        reqDto.setDeliveryZipcode(webApiGetDeliveryInformationRequestDtoRequest.getDeliveryZipcode());

        return reqDto;
    }

    /**
     * チェックメッセージDtoリストに変換
     *
     * @param checkMessageDtoRequestList チェックメッセージDtoリストリクエスト
     * @return チェックメッセージDtoリスト
     */
    public List<CheckMessageDto> toCheckMessageDtoList(List<CheckMessageDtoRequest> checkMessageDtoRequestList) {

        if (CollectionUtil.isEmpty(checkMessageDtoRequestList)) {
            return new ArrayList<>();
        }

        List<CheckMessageDto> checkMessageDtoList = new ArrayList<>();

        checkMessageDtoRequestList.forEach(checkMessageDtoRequest -> {
            CheckMessageDto checkMessageDto = new CheckMessageDto();

            checkMessageDto.setMessageId(checkMessageDtoRequest.getMessageId());
            checkMessageDto.setArgs(checkMessageDtoRequest.getArgs().toArray());
            checkMessageDto.setMessage(checkMessageDtoRequest.getMessage());
            checkMessageDto.setOrderConsecutiveNo(checkMessageDtoRequest.getOrderConsecutiveNo());
            checkMessageDto.setError(checkMessageDtoRequest.getError());

            checkMessageDtoList.add(checkMessageDto);
        });

        return checkMessageDtoList;
    }

    /**
     * 休業日の配送制御Dtoレスポンスに変換
     *
     * @param resDto 配送情報取得 詳細情報
     * @return 休業日の配送制御Dtoレスポンス
     */
    public OrderDeliveryInformationDtoResponse toOrderDeliveryInformationDtoResponse(
                    WebApiGetDeliveryInformationResponseDetailDto resDto) {

        if (ObjectUtils.isEmpty(resDto)) {
            return null;
        }

        OrderDeliveryInformationDtoResponse response = new OrderDeliveryInformationDtoResponse();

        response.setDeliveryAssignFlag(resDto.getDeliveryAssignFlag());
        response.setDeliveryCompanyCode(resDto.getDeliveryCompanyCode());
        response.setDeliveryFlag(resDto.getDeliveryFlag());
        response.setNodeliveryGoodsCode(resDto.getNodeliveryGoodsCode());
        // 2023-renew No14 from here
        response.setComEarlyReceiveDate(resDto.getComEarlyReceiveDate());
        // 2023-renew No14 to here
        if (CollectionUtil.isNotEmpty(resDto.getDateInfo())) {
            response.setDateInfo(
                            toWebApiGetDeliveryInformationResponseDetailDateInfoDtoResponseList(resDto.getDateInfo()));
        }

        return response;
    }

    /**
     * 配送Dtoリストレスポンスに変換
     *
     * @param deliveryDtoList 配送DTOリスト
     * @return 配送Dtoリストレスポンス
     */
    public DeliveryDtoListResponse toDeliveryDtoListResponse(List<DeliveryDto> deliveryDtoList) {

        DeliveryDtoListResponse response = new DeliveryDtoListResponse();

        if (CollectionUtil.isEmpty(deliveryDtoList)) {
            response.setDeliveryDtoList(new ArrayList<>());
        }

        List<DeliveryDtoResponse> deliveryDtoListResponse = new ArrayList<>();

        deliveryDtoList.forEach(deliveryDto -> {
            DeliveryDtoResponse deliveryDtoResponse = new DeliveryDtoResponse();

            deliveryDtoResponse.setDeliveryDetailsDto(
                            toDeliveryDetailsDtoResponse(deliveryDto.getDeliveryDetailsDto()));
            deliveryDtoResponse.setCarriage(deliveryDto.getCarriage());
            deliveryDtoResponse.setSelectClass(deliveryDto.isSelectClass());
            deliveryDtoResponse.setReceiverDateDto(toReceiverDateDtoResponse(deliveryDto.getReceiverDateDto()));
            deliveryDtoResponse.setSpecialChargeAreaFlag(deliveryDto.isSpecialChargeAreaFlag());

            deliveryDtoListResponse.add(deliveryDtoResponse);
        });

        response.setDeliveryDtoList(deliveryDtoListResponse);
        return response;
    }

    /**
     * 受注配送Dtoクラスに変換
     *
     * @param orderDeliveryDtoRequest the order delivery dto request
     * @return the order delivery dto
     */
    public OrderDeliveryDto toOrderDeliveryDto(OrderDeliveryDtoRequest orderDeliveryDtoRequest) {

        if (ObjectUtils.isEmpty(orderDeliveryDtoRequest)) {
            return null;
        }

        OrderDeliveryDto orderDeliveryDto = new OrderDeliveryDto();

        orderDeliveryDto.setAddType(orderDeliveryDtoRequest.getAddType());
        orderDeliveryDto.setTmpOrderGoodsEntityList(
                        toOrderGoodsEntityList(orderDeliveryDtoRequest.getTmpOrderGoodsEntityList()));
        orderDeliveryDto.setOrderGoodsEntityList(
                        toOrderGoodsEntityList(orderDeliveryDtoRequest.getOrderGoodsEntityList()));
        orderDeliveryDto.setOrderDeliveryEntity(
                        toOrderDeliveryEntity(orderDeliveryDtoRequest.getOrderDeliveryEntity()));
        orderDeliveryDto.setDeliveryMethodEntity(
                        toDeliveryMethodEntity(orderDeliveryDtoRequest.getDeliveryMethodEntity()));
        orderDeliveryDto.setDeliveryDtoList(toDeliveryDtoList(orderDeliveryDtoRequest.getDeliveryDtoList()));
        orderDeliveryDto.setOriginalCarriage(orderDeliveryDtoRequest.getOriginalCarriage());
        orderDeliveryDto.setFirstSelectedOrderGoodsEntityList(
                        toOrderGoodsEntityList(orderDeliveryDtoRequest.getFirstSelectedOrderGoodsEntityList()));
        orderDeliveryDto.setDeliveryInformationDetailDto(toWebApiGetDeliveryInformationResponseDetailDto(
                        orderDeliveryDtoRequest.getDeliveryInformationDetailDto()));
        orderDeliveryDto.setOrderDeliveryNowDtoList(
                        toOrderDeliveryNowDtoList(orderDeliveryDtoRequest.getOrderDeliveryNowDtoList()));
        orderDeliveryDto.setOrderReserveDeliveryDtoList(
                        toOrderReserveDeliveryDtoList(orderDeliveryDtoRequest.getOrderReserveDeliveryDtoList()));
        orderDeliveryDto.setOrderDependingOnReceiptGoodsDtoList(toOrderDependingOnReceiptGoodsDtoList(
                        orderDeliveryDtoRequest.getOrderDependingOnReceiptGoodsDtoList()));
        orderDeliveryDto.setCustomerNo(orderDeliveryDtoRequest.getCustomerNo());
        orderDeliveryDto.setAddressBookSeq(orderDeliveryDtoRequest.getAddressBookSeq());
        orderDeliveryDto.setDeliveryDate(orderDeliveryDtoRequest.getDeliveryDate());
        orderDeliveryDto.setDiscountPrice(orderDeliveryDtoRequest.getDiscountPrice());
        orderDeliveryDto.setTaxPrice(orderDeliveryDtoRequest.getTaxPrice());
        orderDeliveryDto.setTotalTax(orderDeliveryDtoRequest.getTotalTax());
        orderDeliveryDto.setCityCd(orderDeliveryDtoRequest.getCityCd());

        orderDeliveryDto.setBusinessType(EnumTypeUtil.getEnumFromValue(HTypeFrontBusinessType.class,
                                                                       orderDeliveryDtoRequest.getBusinessType()
                                                                      ));
        orderDeliveryDto.setConfDocumentType(EnumTypeUtil.getEnumFromValue(HTypeConfDocumentType.class,
                                                                           orderDeliveryDtoRequest.getConfDocumentType()
                                                                          ));
        orderDeliveryDto.setRequisitionType(EnumTypeUtil.getEnumFromValue(HTypeRequisitionType.class,
                                                                          orderDeliveryDtoRequest.getRequisitionType()
                                                                         ));

        return orderDeliveryDto;
    }

    /**
     * 取りおきMapレスポンスに変換
     *
     * @param reserveDtoMap 在庫数取得 詳細情報Map
     * @return 取りおきMapレスポンス
     */
    public OrderGetReserveMapResponse toOrderGetReserveMapResponse(Map<String, WebApiGetReserveResponseDetailDto> reserveDtoMap) {

        OrderGetReserveMapResponse orderGetReserveMapResponse = new OrderGetReserveMapResponse();

        Map<String, WebApiGetReserveResponseDetailDtoResponse> reserveDtoMapResponse = new HashMap<>();

        if (MapUtils.isNotEmpty(reserveDtoMap)) {
            for (Map.Entry<String, WebApiGetReserveResponseDetailDto> entry : reserveDtoMap.entrySet()) {

                WebApiGetReserveResponseDetailDto webApiGetReserveResponseDetailDto = entry.getValue();
                WebApiGetReserveResponseDetailDtoResponse webApiGetReserveResponseDetailDtoResponse =
                                new WebApiGetReserveResponseDetailDtoResponse();

                webApiGetReserveResponseDetailDtoResponse.setGoodsCode(
                                webApiGetReserveResponseDetailDto.getGoodsCode());
                webApiGetReserveResponseDetailDtoResponse.setReserveFlag(
                                webApiGetReserveResponseDetailDto.getReserveFlag());
                webApiGetReserveResponseDetailDtoResponse.setDiscountFlag(
                                webApiGetReserveResponseDetailDto.getDiscountFlag());
                // 2023-renew No14 from here
                webApiGetReserveResponseDetailDtoResponse.setPossibleReserveFromDay(
                                webApiGetReserveResponseDetailDto.getPossibleReserveFromDay());
                webApiGetReserveResponseDetailDtoResponse.setPossibleReserveToDay(
                                webApiGetReserveResponseDetailDto.getPossibleReserveToDay());
                // 2023-renew No14 to here
                reserveDtoMapResponse.put(entry.getKey(), webApiGetReserveResponseDetailDtoResponse);
            }

            orderGetReserveMapResponse.setReserveDtoMap(reserveDtoMapResponse);
        }

        return orderGetReserveMapResponse;
    }

    /**
     * 商品在庫情報Mapレスポンスに変換
     *
     * @param stockMap 在庫数取得 詳細情報 Map
     * @return 商品在庫情報Mapレスポンス
     */
    public OrderGetStockMapResponse toOrderGetStockMapResponse(Map<String, WebApiGetStockResponseDetailDto> stockMap) {

        OrderGetStockMapResponse orderGetStockMapResponse = new OrderGetStockMapResponse();

        Map<String, WebApiGetStockResponseDetailDtoResponse> stockMapResponse = new HashMap<>();

        if (MapUtils.isNotEmpty(stockMap)) {
            for (Map.Entry<String, WebApiGetStockResponseDetailDto> entry : stockMap.entrySet()) {

                WebApiGetStockResponseDetailDto webApiGetStockResponseDetailDto = entry.getValue();
                WebApiGetStockResponseDetailDtoResponse webApiGetStockResponseDetailDtoResponse =
                                new WebApiGetStockResponseDetailDtoResponse();

                webApiGetStockResponseDetailDtoResponse.setGoodsCode(webApiGetStockResponseDetailDto.getGoodsCode());
                webApiGetStockResponseDetailDtoResponse.setStockQuantity(
                                webApiGetStockResponseDetailDto.getStockQuantity());
                webApiGetStockResponseDetailDtoResponse.setSaleYesNo(webApiGetStockResponseDetailDto.getSaleYesNo());
                webApiGetStockResponseDetailDtoResponse.setSaleNgMessage(
                                webApiGetStockResponseDetailDto.getSaleNgMessage());
                webApiGetStockResponseDetailDtoResponse.setStockDate(webApiGetStockResponseDetailDto.getStockDate());
                webApiGetStockResponseDetailDtoResponse.setDeliveryYesNo(
                                webApiGetStockResponseDetailDto.getDeliveryYesNo());

                stockMapResponse.put(entry.getKey(), webApiGetStockResponseDetailDtoResponse);
            }

            orderGetStockMapResponse.setStockMap(stockMapResponse);
        }

        return orderGetStockMapResponse;
    }

    /**
     * 消費税率取得 詳細情報Mapに変換
     *
     * @param consumptionTaxRateMapRequest 割引適用結果MAP
     * @return 消費税率取得 詳細情報Map
     */
    public Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> toConsumptionTaxRateMap(Map<String, WebApiGetConsumptionTaxRateResponseDetailDtoRequest> consumptionTaxRateMapRequest) {

        if (MapUtils.isEmpty(consumptionTaxRateMapRequest)) {
            return new HashMap<>();
        }

        Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> consumptionTaxRateMap = new HashMap<>();

        for (Map.Entry<String, WebApiGetConsumptionTaxRateResponseDetailDtoRequest> entry : consumptionTaxRateMapRequest.entrySet()) {

            WebApiGetConsumptionTaxRateResponseDetailDtoRequest webApiGetConsumptionTaxRateResponseDetailDtoRequest =
                            entry.getValue();
            WebApiGetConsumptionTaxRateResponseDetailDto webApiGetConsumptionTaxRateResponseDetailDto =
                            new WebApiGetConsumptionTaxRateResponseDetailDto();

            webApiGetConsumptionTaxRateResponseDetailDto.setGoodsCode(
                            webApiGetConsumptionTaxRateResponseDetailDtoRequest.getGoodsCode());
            webApiGetConsumptionTaxRateResponseDetailDto.setTaxRate(
                            webApiGetConsumptionTaxRateResponseDetailDtoRequest.getTaxRate());

            consumptionTaxRateMap.put(entry.getKey(), webApiGetConsumptionTaxRateResponseDetailDto);
        }

        return consumptionTaxRateMap;
    }

    /**
     * 割引適用結果取得 詳細情報Mapに変換
     *
     * @param discountsResponseDetailMapRequest 割引適用結果MAP
     * @return 割引適用結果取得 詳細情報Map
     */
    private Map<String, WebApiGetDiscountsResponseDetailDto> toDiscountsResponseDetailMap(Map<String, WebApiGetDiscountsResponseDetailDtoRequest> discountsResponseDetailMapRequest) {

        if (MapUtils.isEmpty(discountsResponseDetailMapRequest)) {
            return new HashMap<>();
        }

        Map<String, WebApiGetDiscountsResponseDetailDto> discountsResponseDetailDtoMap = new HashMap<>();

        for (Map.Entry<String, WebApiGetDiscountsResponseDetailDtoRequest> entry : discountsResponseDetailMapRequest.entrySet()) {

            WebApiGetDiscountsResponseDetailDtoRequest webApiGetDiscountsResponseDetailDtoRequest = entry.getValue();
            WebApiGetDiscountsResponseDetailDto webApiGetDiscountsResponseDetailDto =
                            new WebApiGetDiscountsResponseDetailDto();

            webApiGetDiscountsResponseDetailDto.setGoodsCode(webApiGetDiscountsResponseDetailDtoRequest.getGoodsCode());
            webApiGetDiscountsResponseDetailDto.setSaleType(webApiGetDiscountsResponseDetailDtoRequest.getSaleType());
            webApiGetDiscountsResponseDetailDto.setSaleGroupCode(
                            webApiGetDiscountsResponseDetailDtoRequest.getSaleGroupCode());
            webApiGetDiscountsResponseDetailDto.setSalePrice(webApiGetDiscountsResponseDetailDtoRequest.getSalePrice());
            webApiGetDiscountsResponseDetailDto.setQuantity(webApiGetDiscountsResponseDetailDtoRequest.getQuantity());
            webApiGetDiscountsResponseDetailDto.setSaleCode(webApiGetDiscountsResponseDetailDtoRequest.getSaleCode());
            webApiGetDiscountsResponseDetailDto.setNote(webApiGetDiscountsResponseDetailDtoRequest.getNote());
            webApiGetDiscountsResponseDetailDto.setHints(webApiGetDiscountsResponseDetailDtoRequest.getHints());
            webApiGetDiscountsResponseDetailDto.setOrderDisplay(
                            webApiGetDiscountsResponseDetailDtoRequest.getOrderDisplay());

            discountsResponseDetailDtoMap.put(entry.getKey(), webApiGetDiscountsResponseDetailDto);
        }

        return discountsResponseDetailDtoMap;
    }

    /**
     * カート商品Dtoリストに変換
     *
     * @param cartGoodsDtoRequestList カート商品Dtoリクエストリスト
     * @return カート商品Dtoリスト
     */
    private List<CartGoodsDto> toCartGoodsDtoList(List<CartGoodsDtoRequest> cartGoodsDtoRequestList) {

        if (CollectionUtil.isEmpty(cartGoodsDtoRequestList)) {
            return new ArrayList<>();
        }

        List<CartGoodsDto> cartGoodsDtoList = new ArrayList<>();

        cartGoodsDtoRequestList.forEach(cartGoodsDtoRequest -> {
            CartGoodsDto cartGoodsDto = new CartGoodsDto();

            cartGoodsDto.setCartGoodsEntity(toCartGoodsEntity(cartGoodsDtoRequest.getCartGoodsEntity()));
            cartGoodsDto.setGoodsDetailsDto(toGoodsDetailsDto(cartGoodsDtoRequest.getGoodsDetailsDto()));
            cartGoodsDto.setGoodsPriceSubtotal(cartGoodsDtoRequest.getGoodsPriceSubtotal());
            cartGoodsDto.setGoodsPriceInTaxSubtotal(cartGoodsDtoRequest.getGoodsPriceInTaxSubtotal());

            cartGoodsDtoList.add(cartGoodsDto);
        });

        return cartGoodsDtoList;
    }

    /**
     * 商品詳細Dtoに変換
     *
     * @param goodsDetailsDtoRequest 商品詳細Dtoクラスリクエスト
     * @return 商品詳細Dto
     */
    private GoodsDetailsDto toGoodsDetailsDto(GoodsDetailsDtoRequest goodsDetailsDtoRequest) {

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
        goodsDetailsDto.setTaxRate(goodsDetailsDtoRequest.getTaxRate());
        goodsDetailsDto.setGoodsPriceInTax(goodsDetailsDtoRequest.getGoodsPriceInTax());
        goodsDetailsDto.setGoodsPrice(goodsDetailsDtoRequest.getGoodsPrice());
        goodsDetailsDto.setDeliveryType(goodsDetailsDtoRequest.getDeliveryType());
        goodsDetailsDto.setSaleStartTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoRequest.getSaleStartTime()));
        goodsDetailsDto.setSaleEndTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoRequest.getSaleEndTime()));
        goodsDetailsDto.setPurchasedMax(goodsDetailsDtoRequest.getPurchasedMax());
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
        goodsDetailsDto.setOpenStartTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoRequest.getOpenStartTime()));
        goodsDetailsDto.setOpenEndTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoRequest.getOpenEndTime()));
        goodsDetailsDto.setGoodsGroupName(goodsDetailsDtoRequest.getGoodsGroupName());
        goodsDetailsDto.setUnitTitle1(goodsDetailsDtoRequest.getUnitTitle1());
        goodsDetailsDto.setUnitTitle2(goodsDetailsDtoRequest.getUnitTitle2());
        goodsDetailsDto.setGoodsPreDiscountPrice(goodsDetailsDtoRequest.getGoodsPreDiscountPrice());
        goodsDetailsDto.setGoodsGroupImageEntityList(
                        toGoodsGroupImageEntityList(goodsDetailsDtoRequest.getGoodsGroupImageEntityList()));
        goodsDetailsDto.setMetaDescription(goodsDetailsDtoRequest.getMetaDescription());
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
        goodsDetailsDto.setUnitImage(toUnitImage(goodsDetailsDtoRequest.getUnitImage()));
        goodsDetailsDto.setGoodsOptionDisplayName(goodsDetailsDtoRequest.getGoodsOptionDisplayName());
        goodsDetailsDto.setCoolSendFrom(goodsDetailsDtoRequest.getCoolSendFrom());
        goodsDetailsDto.setCoolSendTo(goodsDetailsDtoRequest.getCoolSendTo());
        goodsDetailsDto.setTax(goodsDetailsDtoRequest.getTax());
        goodsDetailsDto.setUnit(goodsDetailsDtoRequest.getUnit());
        goodsDetailsDto.setGoodsManagementCode(goodsDetailsDtoRequest.getGoodsManagementCode());
        goodsDetailsDto.setGoodsDivisionCode(goodsDetailsDtoRequest.getGoodsDivisionCode());
        goodsDetailsDto.setGoodsCategory1(goodsDetailsDtoRequest.getGoodsCategory1());
        goodsDetailsDto.setGoodsCategory2(goodsDetailsDtoRequest.getGoodsCategory2());
        goodsDetailsDto.setGoodsCategory3(goodsDetailsDtoRequest.getGoodsCategory3());
        goodsDetailsDto.setSaleYesNo(goodsDetailsDtoRequest.getSaleYesNo());
        goodsDetailsDto.setSaleNgMessage(goodsDetailsDtoRequest.getSaleNgMessage());
        goodsDetailsDto.setDeliveryYesNo(goodsDetailsDtoRequest.getDeliveryYesNo());

        goodsDetailsDto.setSaleStatusPC(EnumTypeUtil.getEnumFromValue(HTypeGoodsSaleStatus.class,
                                                                      goodsDetailsDtoRequest.getSaleStatus()
                                                                     ));
        goodsDetailsDto.setStockStatusPc(EnumTypeUtil.getEnumFromValue(HTypeStockStatusType.class,
                                                                       goodsDetailsDtoRequest.getStockStatus()
                                                                      ));
        goodsDetailsDto.setGoodsTaxType(EnumTypeUtil.getEnumFromValue(HTypeGoodsTaxType.class,
                                                                      goodsDetailsDtoRequest.getGoodsTaxType()
                                                                     ));
        goodsDetailsDto.setAlcoholFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeAlcoholFlag.class, goodsDetailsDtoRequest.getAlcoholFlag()));
        goodsDetailsDto.setUnitManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class,
                                                                            goodsDetailsDtoRequest.getUnitManagementFlag()
                                                                           ));
        goodsDetailsDto.setStockManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeStockManagementFlag.class,
                                                                             goodsDetailsDtoRequest.getStockManagementFlag()
                                                                            ));
        goodsDetailsDto.setIndividualDeliveryType(EnumTypeUtil.getEnumFromValue(HTypeIndividualDeliveryType.class,
                                                                                goodsDetailsDtoRequest.getIndividualDeliveryType()
                                                                               ));
        goodsDetailsDto.setFreeDeliveryFlag(EnumTypeUtil.getEnumFromValue(HTypeFreeDeliveryFlag.class,
                                                                          goodsDetailsDtoRequest.getFreeDeliveryFlag()
                                                                         ));
        goodsDetailsDto.setGoodsOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                           goodsDetailsDtoRequest.getGoodsOpenStatus()
                                                                          ));
        goodsDetailsDto.setSnsLinkFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeSnsLinkFlag.class, goodsDetailsDtoRequest.getSnsLinkFlag()));
        goodsDetailsDto.setGoodsClassType(EnumTypeUtil.getEnumFromValue(HTypeGoodsClassType.class,
                                                                        goodsDetailsDtoRequest.getGoodsClassType()
                                                                       ));
        goodsDetailsDto.setDentalMonopolySalesFlg(EnumTypeUtil.getEnumFromValue(HTypeDentalMonopolySalesFlg.class,
                                                                                goodsDetailsDtoRequest.getDentalMonopolySalesFlg()
                                                                               ));
        goodsDetailsDto.setSaleIconFlag(EnumTypeUtil.getEnumFromValue(HTypeSaleIconFlag.class,
                                                                      goodsDetailsDtoRequest.getSaleIconFlag()
                                                                     ));
        goodsDetailsDto.setReserveIconFlag(EnumTypeUtil.getEnumFromValue(HTypeReserveIconFlag.class,
                                                                         goodsDetailsDtoRequest.getReserveIconFlag()
                                                                        ));
        goodsDetailsDto.setNewIconFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeNewIconFlag.class, goodsDetailsDtoRequest.getNewIconFlag()));
        goodsDetailsDto.setLandSendFlag(EnumTypeUtil.getEnumFromValue(HTypeLandSendFlag.class,
                                                                      goodsDetailsDtoRequest.getLandSendFlag()
                                                                     ));
        goodsDetailsDto.setCoolSendFlag(EnumTypeUtil.getEnumFromValue(HTypeCoolSendFlag.class,
                                                                      goodsDetailsDtoRequest.getCoolSendFlag()
                                                                     ));
        goodsDetailsDto.setReserveFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeReserveFlag.class, goodsDetailsDtoRequest.getReserveFlag()));
        goodsDetailsDto.setPriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypePriceMarkDispFlag.class,
                                                                           goodsDetailsDtoRequest.getPriceMarkDispFlag()
                                                                          ));
        goodsDetailsDto.setSalePriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceMarkDispFlag.class,
                                                                               goodsDetailsDtoRequest.getSalePriceMarkDispFlag()
                                                                              ));
        goodsDetailsDto.setSalePriceIntegrityFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceIntegrityFlag.class,
                                                                                goodsDetailsDtoRequest.getSalePriceIntegrityFlag()
                                                                               ));
        goodsDetailsDto.setEmotionPriceType(EnumTypeUtil.getEnumFromValue(HTypeEmotionPriceType.class,
                                                                          goodsDetailsDtoRequest.getEmotionPriceType()
                                                                         ));

        return goodsDetailsDto;
    }

    /**
     * 商品グループ画像リストに変換
     *
     * @param goodsGroupImageEntityRequestList 商品グループ画像リクエストリスト
     * @return 商品グループ画像リスト
     */
    private List<GoodsGroupImageEntity> toGoodsGroupImageEntityList(List<GoodsGroupImageEntityRequest> goodsGroupImageEntityRequestList) {

        if (CollectionUtil.isEmpty(goodsGroupImageEntityRequestList)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntity> goodsGroupImageEntityList = new ArrayList<>();

        goodsGroupImageEntityRequestList.forEach(goodsGroupImageEntityRequest -> {
            GoodsGroupImageEntity goodsGroupImageEntity = new GoodsGroupImageEntity();

            goodsGroupImageEntity.setGoodsGroupSeq(goodsGroupImageEntityRequest.getGoodsGroupSeq());
            goodsGroupImageEntity.setImageTypeVersionNo(goodsGroupImageEntityRequest.getImageTypeVersionNo());
            goodsGroupImageEntity.setImageFileName(goodsGroupImageEntityRequest.getImageFileName());
            goodsGroupImageEntity.setRegistTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntityRequest.getRegistTime()));
            goodsGroupImageEntity.setUpdateTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntityRequest.getUpdateTime()));

            goodsGroupImageEntityList.add(goodsGroupImageEntity);
        });

        return goodsGroupImageEntityList;
    }

    /**
     * 商品画像に変換
     *
     * @param goodsImageEntityRequest 商品グループ画像リクエスト
     * @return 商品画像
     */
    private GoodsImageEntity toUnitImage(GoodsImageEntityRequest goodsImageEntityRequest) {

        if (ObjectUtils.isEmpty(goodsImageEntityRequest)) {
            return null;
        }

        GoodsImageEntity goodsImageEntity = new GoodsImageEntity();

        goodsImageEntity.setGoodsGroupSeq(goodsImageEntityRequest.getGoodsGroupSeq());
        goodsImageEntity.setGoodsSeq(goodsImageEntityRequest.getGoodsSeq());
        goodsImageEntity.setImageFileName(goodsImageEntityRequest.getImageFileName());
        goodsImageEntity.setDisplayFlag(EnumTypeUtil.getEnumFromValue(HTypeDisplayStatus.class,
                                                                      goodsImageEntityRequest.getDisplayFlag()
                                                                     ));
        goodsImageEntity.setRegistTime(conversionUtility.toTimeStamp(goodsImageEntityRequest.getRegistTime()));
        goodsImageEntity.setUpdateTime(conversionUtility.toTimeStamp(goodsImageEntityRequest.getUpdateTime()));
        goodsImageEntity.setTmpFilePath(goodsImageEntityRequest.getTmpFilePath());

        return goodsImageEntity;
    }

    /**
     * カート商品に変換
     *
     * @param cartGoodsEntityRequest カート商品エンティティリクエスト
     * @return カート商品
     */
    private CartGoodsEntity toCartGoodsEntity(CartGoodsEntityRequest cartGoodsEntityRequest) {

        if (ObjectUtils.isEmpty(cartGoodsEntityRequest)) {
            return null;
        }

        CartGoodsEntity cartGoodsEntity = new CartGoodsEntity();

        cartGoodsEntity.setCartSeq(cartGoodsEntityRequest.getCartSeq());
        cartGoodsEntity.setAccessUid(cartGoodsEntityRequest.getAccessUid());
        cartGoodsEntity.setMemberInfoSeq(cartGoodsEntityRequest.getMemberInfoSeq());
        cartGoodsEntity.setGoodsSeq(cartGoodsEntityRequest.getGoodsSeq());
        cartGoodsEntity.setCartGoodsCount(cartGoodsEntityRequest.getCartGoodsCount());
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
     * 商品マスタマップレスポンスに変換
     *
     * @param goodsMaster 商品マスタマップ
     * @return 商品マスタマップレスポンス
     */
    private Map<String, GoodsDetailsDtoResponse> toOrderInfoMasterDtoResponse(Map<Integer, GoodsDetailsDto> goodsMaster) {

        if (MapUtils.isEmpty(goodsMaster)) {
            return new HashMap<>();
        }

        Map<String, GoodsDetailsDtoResponse> goodsDetailsDtoResponseMap = new HashMap<>();

        for (Map.Entry<Integer, GoodsDetailsDto> entry : goodsMaster.entrySet()) {

            GoodsDetailsDtoResponse goodsDetailsDtoResponse = new GoodsDetailsDtoResponse();
            GoodsDetailsDto goodsDetailsDto = entry.getValue();

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
            goodsDetailsDtoResponse.setTaxRate(goodsDetailsDto.getTaxRate());
            goodsDetailsDtoResponse.setGoodsPriceInTax(goodsDetailsDto.getGoodsPriceInTax());
            goodsDetailsDtoResponse.setGoodsPrice(goodsDetailsDto.getGoodsPrice());
            goodsDetailsDtoResponse.setDeliveryType(goodsDetailsDto.getDeliveryType());
            goodsDetailsDtoResponse.setSaleStartTime(goodsDetailsDto.getSaleStartTimePC());
            goodsDetailsDtoResponse.setSaleEndTime(goodsDetailsDto.getSaleEndTimePC());
            goodsDetailsDtoResponse.setPurchasedMax(goodsDetailsDto.getPurchasedMax());
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
            goodsDetailsDtoResponse.setOpenStartTime(goodsDetailsDto.getOpenStartTimePC());
            goodsDetailsDtoResponse.setOpenEndTime(goodsDetailsDto.getOpenEndTimePC());
            goodsDetailsDtoResponse.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
            goodsDetailsDtoResponse.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
            goodsDetailsDtoResponse.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
            goodsDetailsDtoResponse.setGoodsPreDiscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());
            goodsDetailsDtoResponse.setGoodsGroupImageEntityList(
                            toGoodsGroupImageEntityListResponse(goodsDetailsDto.getGoodsGroupImageEntityList()));
            goodsDetailsDtoResponse.setMetaDescription(goodsDetailsDto.getMetaDescription());
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
            goodsDetailsDtoResponse.setUnitImage(toGoodsImageEntityResponse(goodsDetailsDto.getUnitImage()));
            goodsDetailsDtoResponse.setGoodsOptionDisplayName(goodsDetailsDto.getGoodsOptionDisplayName());
            goodsDetailsDtoResponse.setCoolSendFrom(goodsDetailsDto.getCoolSendFrom());
            goodsDetailsDtoResponse.setCoolSendTo(goodsDetailsDto.getCoolSendTo());
            goodsDetailsDtoResponse.setTax(goodsDetailsDto.getTax());
            goodsDetailsDtoResponse.setUnit(goodsDetailsDto.getUnit());
            goodsDetailsDtoResponse.setGoodsManagementCode(goodsDetailsDto.getGoodsManagementCode());
            goodsDetailsDtoResponse.setGoodsDivisionCode(goodsDetailsDto.getGoodsDivisionCode());
            goodsDetailsDtoResponse.setGoodsCategory1(goodsDetailsDto.getGoodsCategory1());
            goodsDetailsDtoResponse.setGoodsCategory2(goodsDetailsDto.getGoodsCategory2());
            goodsDetailsDtoResponse.setGoodsCategory3(goodsDetailsDto.getGoodsCategory3());
            goodsDetailsDtoResponse.setSaleYesNo(goodsDetailsDto.getSaleYesNo());
            goodsDetailsDtoResponse.setSaleNgMessage(goodsDetailsDto.getSaleNgMessage());
            goodsDetailsDtoResponse.setDeliveryYesNo(goodsDetailsDto.getDeliveryYesNo());
            if (goodsDetailsDto.getGoodsTaxType() != null) {
                goodsDetailsDtoResponse.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType().getValue());
            }
            if (goodsDetailsDto.getAlcoholFlag() != null) {
                goodsDetailsDtoResponse.setAlcoholFlag(goodsDetailsDto.getAlcoholFlag().getValue());
            }
            if (goodsDetailsDto.getSaleStatusPC() != null) {
                goodsDetailsDtoResponse.setSaleStatus(goodsDetailsDto.getSaleStatusPC().getValue());
            }
            if (goodsDetailsDto.getUnitManagementFlag() != null) {
                goodsDetailsDtoResponse.setUnitManagementFlag(goodsDetailsDto.getUnitManagementFlag().getValue());
            }
            if (goodsDetailsDto.getStockManagementFlag() != null) {
                goodsDetailsDtoResponse.setStockManagementFlag(goodsDetailsDto.getStockManagementFlag().getValue());
            }
            if (goodsDetailsDto.getIndividualDeliveryType() != null) {
                goodsDetailsDtoResponse.setIndividualDeliveryType(
                                goodsDetailsDto.getIndividualDeliveryType().getValue());
            }
            if (goodsDetailsDto.getFreeDeliveryFlag() != null) {
                goodsDetailsDtoResponse.setFreeDeliveryFlag(goodsDetailsDto.getFreeDeliveryFlag().getValue());
            }
            if (goodsDetailsDto.getGoodsOpenStatusPC() != null) {
                goodsDetailsDtoResponse.setGoodsOpenStatus(goodsDetailsDto.getGoodsOpenStatusPC().getValue());
            }
            if (goodsDetailsDto.getSnsLinkFlag() != null) {
                goodsDetailsDtoResponse.setSnsLinkFlag(goodsDetailsDto.getSnsLinkFlag().getValue());
            }
            if (goodsDetailsDto.getStockStatusPc() != null) {
                goodsDetailsDtoResponse.setStockStatus(goodsDetailsDto.getStockStatusPc().getValue());
            }
            if (goodsDetailsDto.getGoodsClassType() != null) {
                goodsDetailsDtoResponse.setGoodsClassType(goodsDetailsDto.getGoodsClassType().getValue());
            }
            if (goodsDetailsDto.getDentalMonopolySalesFlg() != null) {
                goodsDetailsDtoResponse.setDentalMonopolySalesFlg(
                                goodsDetailsDto.getDentalMonopolySalesFlg().getValue());
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
                goodsDetailsDtoResponse.setSalePriceIntegrityFlag(
                                goodsDetailsDto.getSalePriceIntegrityFlag().getValue());
            }
            if (goodsDetailsDto.getEmotionPriceType() != null) {
                goodsDetailsDtoResponse.setEmotionPriceType(goodsDetailsDto.getEmotionPriceType().getValue());
            }

            goodsDetailsDtoResponseMap.put(String.valueOf(entry.getKey()), goodsDetailsDtoResponse);
        }

        return goodsDetailsDtoResponseMap;
    }

    /**
     * 商品グループ画像レスポンスリストに変換
     *
     * @param goodsGroupImageEntityList 商品グループ画像リスト
     * @return 商品グループ画像レスポンスリスト
     */
    private List<GoodsGroupImageEntityResponse> toGoodsGroupImageEntityListResponse(List<GoodsGroupImageEntity> goodsGroupImageEntityList) {

        if (CollectionUtil.isEmpty(goodsGroupImageEntityList)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntityResponse> goodsGroupImageEntityResponseList = new ArrayList<>();

        goodsGroupImageEntityList.forEach(goodsGroupImageEntity -> {
            GoodsGroupImageEntityResponse goodsGroupImageEntityResponse = new GoodsGroupImageEntityResponse();

            goodsGroupImageEntityResponse.setGoodsGroupSeq(goodsGroupImageEntity.getGoodsGroupSeq());
            goodsGroupImageEntityResponse.setImageTypeVersionNo(goodsGroupImageEntity.getImageTypeVersionNo());
            goodsGroupImageEntityResponse.setImageFileName(goodsGroupImageEntity.getImageFileName());
            goodsGroupImageEntityResponse.setRegistTime(goodsGroupImageEntity.getRegistTime());
            goodsGroupImageEntityResponse.setUpdateTime(goodsGroupImageEntity.getUpdateTime());

            goodsGroupImageEntityResponseList.add(goodsGroupImageEntityResponse);
        });

        return goodsGroupImageEntityResponseList;
    }

    /**
     * 商品グループ画像レスポンスに変換
     *
     * @param goodsImageEntity 商品画像クラス
     * @return 商品グループ画像レスポンス
     */
    private GoodsImageEntityResponse toGoodsImageEntityResponse(GoodsImageEntity goodsImageEntity) {

        if (ObjectUtils.isEmpty(goodsImageEntity)) {
            return null;
        }

        GoodsImageEntityResponse goodsImageEntityResponse = new GoodsImageEntityResponse();

        goodsImageEntityResponse.setGoodsGroupSeq(goodsImageEntity.getGoodsGroupSeq());
        goodsImageEntityResponse.setGoodsSeq(goodsImageEntity.getGoodsSeq());
        goodsImageEntityResponse.setImageFileName(goodsImageEntity.getImageFileName());
        if (goodsImageEntity.getDisplayFlag() != null) {
            goodsImageEntityResponse.setDisplayFlag(goodsImageEntity.getDisplayFlag().getValue());
        }
        goodsImageEntityResponse.setTmpFilePath(goodsImageEntity.getTmpFilePath());
        goodsImageEntityResponse.setRegistTime(goodsImageEntity.getRegistTime());

        return goodsImageEntityResponse;
    }

    /**
     * 商品マスタマップレスポンスに変換
     *
     * @param taxRateMaster 税率マスタマップ
     * @return 商品マスタマップレスポンス
     */
    private Map<String, TaxRateEntityResponse> toTaxRateMasterMapResponse(Map<HTypeTaxRateType, TaxRateEntity> taxRateMaster) {

        if (MapUtils.isEmpty(taxRateMaster)) {
            return new HashMap<>();
        }

        Map<String, TaxRateEntityResponse> taxRateEntityResponseMap = new HashMap<>();

        for (Map.Entry<HTypeTaxRateType, TaxRateEntity> entry : taxRateMaster.entrySet()) {

            TaxRateEntity taxRateEntity = entry.getValue();
            TaxRateEntityResponse taxRateEntityResponse = new TaxRateEntityResponse();

            taxRateEntityResponse.setTaxSeq(taxRateEntity.getTaxSeq());
            taxRateEntityResponse.setRate(taxRateEntity.getRate());
            if (taxRateEntity.getRateType() != null) {
                taxRateEntityResponse.setRateType(taxRateEntity.getRateType().getValue());
            }
            taxRateEntityResponse.setOrderDisplay(taxRateEntity.getOrderDisplay());
            taxRateEntityResponse.setRegistTime(taxRateEntity.getRegistTime());
            taxRateEntityResponse.setUpdateTime(taxRateEntity.getUpdateTime());

            taxRateEntityResponseMap.put(entry.getKey().getValue(), taxRateEntityResponse);
        }

        return taxRateEntityResponseMap;
    }

    /**
     * 商品マスタマップレスポンスに変換
     *
     * @param deliveryMethodMaster 配送マスタマップ
     * @return 商品マスタマップレスポンス
     */
    private Map<String, DeliveryMethodDetailsDtoResponse> toDeliveryMethodMasterMapResponse(Map<Integer, DeliveryMethodDetailsDto> deliveryMethodMaster) {

        if (MapUtils.isEmpty(deliveryMethodMaster)) {
            return new HashMap<>();
        }

        Map<String, DeliveryMethodDetailsDtoResponse> deliveryMethodMasterMapResponse = new HashMap<>();

        for (Map.Entry<Integer, DeliveryMethodDetailsDto> entry : deliveryMethodMaster.entrySet()) {

            DeliveryMethodDetailsDto dto = entry.getValue();
            DeliveryMethodDetailsDtoResponse deliveryMethodDetailsDtoResponse = new DeliveryMethodDetailsDtoResponse();

            deliveryMethodDetailsDtoResponse.setDeliveryMethodEntity(
                            toDeliveryMethodEntityResponse(dto.getDeliveryMethodEntity()));
            deliveryMethodDetailsDtoResponse.setDeliveryMethodTypeCarriageEntityList(
                            toDeliveryMethodTypeCarriageEntityListResponse(
                                            dto.getDeliveryMethodTypeCarriageEntityList()));
            deliveryMethodDetailsDtoResponse.setDeliverySpecialChargeAreaCount(dto.getDeliverySpecialChargeAreaCount());
            deliveryMethodDetailsDtoResponse.setDeliveryImpossibleAreaCount(dto.getDeliveryImpossibleAreaCount());
            deliveryMethodDetailsDtoResponse.setDeliveryImpossibleAreaResultDtoList(
                            toDeliveryImpossibleAreaResultDtoListResponse(
                                            dto.getDeliveryImpossibleAreaResultDtoList()));
            deliveryMethodDetailsDtoResponse.setDeliverySpecialChargeAreaResultDtoList(
                            toDeliverySpecialChargeAreaResultDtoListResponse(
                                            dto.getDeliverySpecialChargeAreaResultDtoList()));

            deliveryMethodMasterMapResponse.put(String.valueOf(entry.getKey()), deliveryMethodDetailsDtoResponse);
        }

        return deliveryMethodMasterMapResponse;

    }

    /**
     * フリーエリアEntityレスポンスに変換
     *
     * @param deliveryMethodEntity 配送方法エンティティ
     * @return フリーエリアEntityレスポンス
     */
    private DeliveryMethodEntityResponse toDeliveryMethodEntityResponse(DeliveryMethodEntity deliveryMethodEntity) {

        if (ObjectUtils.isEmpty(deliveryMethodEntity)) {
            return null;
        }

        DeliveryMethodEntityResponse deliveryMethodEntityResponse = new DeliveryMethodEntityResponse();

        deliveryMethodEntityResponse.setDeliveryMethodSeq(deliveryMethodEntity.getDeliveryMethodSeq());
        deliveryMethodEntityResponse.setDeliveryMethodName(deliveryMethodEntity.getDeliveryMethodName());
        deliveryMethodEntityResponse.setDeliveryMethodDisplayName(
                        deliveryMethodEntity.getDeliveryMethodDisplayNamePC());
        deliveryMethodEntityResponse.setDeliveryNote(deliveryMethodEntity.getDeliveryNotePC());
        deliveryMethodEntityResponse.setEqualsCarriage(deliveryMethodEntity.getEqualsCarriage());
        deliveryMethodEntityResponse.setLargeAmountDiscountPrice(deliveryMethodEntity.getLargeAmountDiscountPrice());
        deliveryMethodEntityResponse.setLargeAmountDiscountCarriage(
                        deliveryMethodEntity.getLargeAmountDiscountCarriage());
        deliveryMethodEntityResponse.setDeliveryLeadTime(deliveryMethodEntity.getDeliveryLeadTime());
        deliveryMethodEntityResponse.setDeliveryChaseURL(deliveryMethodEntity.getDeliveryChaseURL());
        deliveryMethodEntityResponse.setDeliveryChaseURLDisplayPeriod(
                        deliveryMethodEntity.getDeliveryChaseURLDisplayPeriod());
        deliveryMethodEntityResponse.setPossibleSelectDays(deliveryMethodEntity.getPossibleSelectDays());
        deliveryMethodEntityResponse.setReceiverTimeZone1(deliveryMethodEntity.getReceiverTimeZone1());
        deliveryMethodEntityResponse.setReceiverTimeZone2(deliveryMethodEntity.getReceiverTimeZone2());
        deliveryMethodEntityResponse.setReceiverTimeZone3(deliveryMethodEntity.getReceiverTimeZone3());
        deliveryMethodEntityResponse.setReceiverTimeZone4(deliveryMethodEntity.getReceiverTimeZone4());
        deliveryMethodEntityResponse.setReceiverTimeZone5(deliveryMethodEntity.getReceiverTimeZone5());
        deliveryMethodEntityResponse.setReceiverTimeZone6(deliveryMethodEntity.getReceiverTimeZone6());
        deliveryMethodEntityResponse.setReceiverTimeZone7(deliveryMethodEntity.getReceiverTimeZone7());
        deliveryMethodEntityResponse.setReceiverTimeZone8(deliveryMethodEntity.getReceiverTimeZone8());
        deliveryMethodEntityResponse.setReceiverTimeZone9(deliveryMethodEntity.getReceiverTimeZone9());
        deliveryMethodEntityResponse.setReceiverTimeZone10(deliveryMethodEntity.getReceiverTimeZone10());
        deliveryMethodEntityResponse.setOrderDisplay(deliveryMethodEntity.getOrderDisplay());
        deliveryMethodEntityResponse.setRegistTime(deliveryMethodEntity.getRegistTime());
        deliveryMethodEntityResponse.setUpdateTime(deliveryMethodEntity.getUpdateTime());
        if (deliveryMethodEntity.getOpenStatusPC() != null) {
            deliveryMethodEntityResponse.setOpenStatus(deliveryMethodEntity.getOpenStatusPC().getValue());
        }
        if (deliveryMethodEntity.getDeliveryMethodType() != null) {
            deliveryMethodEntityResponse.setDeliveryMethodType(deliveryMethodEntity.getDeliveryMethodType().getValue());
        }
        if (deliveryMethodEntity.getShortfallDisplayFlag() != null) {
            deliveryMethodEntityResponse.setShortfallDisplayFlag(
                            deliveryMethodEntity.getShortfallDisplayFlag().getValue());
        }

        return deliveryMethodEntityResponse;
    }

    /**
     * 配送区分別送料クラスリストに変換
     *
     * @param deliveryMethodTypeCarriageEntityList 配送区分別送料リスト
     * @return 配送区分別送料クラスリスト
     */
    private List<DeliveryMethodTypeCarriageEntityResponse> toDeliveryMethodTypeCarriageEntityListResponse(List<DeliveryMethodTypeCarriageEntity> deliveryMethodTypeCarriageEntityList) {

        if (CollectionUtil.isEmpty(deliveryMethodTypeCarriageEntityList)) {
            return new ArrayList<>();
        }

        List<DeliveryMethodTypeCarriageEntityResponse> deliveryMethodTypeCarriageEntityResponseList = new ArrayList<>();

        deliveryMethodTypeCarriageEntityList.forEach(deliveryMethodTypeCarriageEntity -> {
            DeliveryMethodTypeCarriageEntityResponse deliveryMethodTypeCarriageEntityResponse =
                            new DeliveryMethodTypeCarriageEntityResponse();

            deliveryMethodTypeCarriageEntityResponse.setDeliveryMethodSeq(
                            deliveryMethodTypeCarriageEntity.getDeliveryMethodSeq());
            if (deliveryMethodTypeCarriageEntity.getPrefectureType() != null) {
                deliveryMethodTypeCarriageEntityResponse.setPrefectureType(
                                deliveryMethodTypeCarriageEntity.getPrefectureType().getValue());
            }
            deliveryMethodTypeCarriageEntityResponse.setMaxPrice(deliveryMethodTypeCarriageEntity.getMaxPrice());
            deliveryMethodTypeCarriageEntityResponse.setCarriage(deliveryMethodTypeCarriageEntity.getCarriage());
            deliveryMethodTypeCarriageEntityResponse.setRegistTime(deliveryMethodTypeCarriageEntity.getRegistTime());

            deliveryMethodTypeCarriageEntityResponseList.add(deliveryMethodTypeCarriageEntityResponse);
        });

        return deliveryMethodTypeCarriageEntityResponseList;
    }

    /**
     * 配送不可能エリア詳細Dtoクラスレスポンスリストに変換
     *
     * @param deliveryImpossibleAreaResultDtoList 配送不可能エリア詳細Dtoリスト
     * @return 配送不可能エリア詳細Dtoクラスレスポンスリスト
     */
    private List<DeliveryImpossibleAreaResultDtoResponse> toDeliveryImpossibleAreaResultDtoListResponse(List<DeliveryImpossibleAreaResultDto> deliveryImpossibleAreaResultDtoList) {

        if (CollectionUtil.isEmpty(deliveryImpossibleAreaResultDtoList)) {
            return new ArrayList<>();
        }

        List<DeliveryImpossibleAreaResultDtoResponse> deliveryImpossibleAreaResultDtoResponseList = new ArrayList<>();

        deliveryImpossibleAreaResultDtoList.forEach(deliveryImpossibleAreaResultDto -> {

            DeliveryImpossibleAreaResultDtoResponse deliveryImpossibleAreaResultDtoResponse =
                            new DeliveryImpossibleAreaResultDtoResponse();

            deliveryImpossibleAreaResultDtoResponse.setDeliveryMethodSeq(
                            deliveryImpossibleAreaResultDto.getDeliveryMethodSeq());
            deliveryImpossibleAreaResultDtoResponse.setZipcode(deliveryImpossibleAreaResultDto.getZipcode());
            deliveryImpossibleAreaResultDtoResponse.setPrefecture(deliveryImpossibleAreaResultDto.getPrefecture());
            deliveryImpossibleAreaResultDtoResponse.setCity(deliveryImpossibleAreaResultDto.getCity());
            deliveryImpossibleAreaResultDtoResponse.setTown(deliveryImpossibleAreaResultDto.getTown());
            deliveryImpossibleAreaResultDtoResponse.setNumbers(deliveryImpossibleAreaResultDto.getNumbers());
            deliveryImpossibleAreaResultDtoResponse.setAddressList(deliveryImpossibleAreaResultDto.getAddressList());

            deliveryImpossibleAreaResultDtoResponseList.add(deliveryImpossibleAreaResultDtoResponse);
        });

        return deliveryImpossibleAreaResultDtoResponseList;
    }

    /**
     * 配送特別料金エリア詳細Dtoクラスレスポンスリストに変換
     *
     * @param deliverySpecialChargeAreaResultDtoList 配送特別料金エリア詳細Dtoリスト
     * @return 配送特別料金エリア詳細Dtoクラスレスポンスリスト
     */
    private List<DeliverySpecialChargeAreaResultDtoResponse> toDeliverySpecialChargeAreaResultDtoListResponse(List<DeliverySpecialChargeAreaResultDto> deliverySpecialChargeAreaResultDtoList) {

        if (CollectionUtil.isEmpty(deliverySpecialChargeAreaResultDtoList)) {
            return new ArrayList<>();
        }

        List<DeliverySpecialChargeAreaResultDtoResponse> deliverySpecialChargeAreaResultDtoResponseList =
                        new ArrayList<>();

        deliverySpecialChargeAreaResultDtoList.forEach(deliverySpecialChargeAreaResultDto -> {
            DeliverySpecialChargeAreaResultDtoResponse deliverySpecialChargeAreaResultDtoResponse =
                            new DeliverySpecialChargeAreaResultDtoResponse();

            deliverySpecialChargeAreaResultDtoResponse.setDeliveryMethodSeq(
                            deliverySpecialChargeAreaResultDto.getDeliveryMethodSeq());
            deliverySpecialChargeAreaResultDtoResponse.setZipcode(deliverySpecialChargeAreaResultDto.getZipcode());
            deliverySpecialChargeAreaResultDtoResponse.setCarriage(deliverySpecialChargeAreaResultDto.getCarriage());
            deliverySpecialChargeAreaResultDtoResponse.setPrefecture(
                            deliverySpecialChargeAreaResultDto.getPrefecture());
            deliverySpecialChargeAreaResultDtoResponse.setCity(deliverySpecialChargeAreaResultDto.getCity());
            deliverySpecialChargeAreaResultDtoResponse.setTown(deliverySpecialChargeAreaResultDto.getTown());
            deliverySpecialChargeAreaResultDtoResponse.setNumbers(deliverySpecialChargeAreaResultDto.getNumbers());
            deliverySpecialChargeAreaResultDtoResponse.setAddressList(
                            deliverySpecialChargeAreaResultDto.getAddressList());

            deliverySpecialChargeAreaResultDtoResponseList.add(deliverySpecialChargeAreaResultDtoResponse);
        });

        return deliverySpecialChargeAreaResultDtoResponseList;
    }

    /**
     * WEB-API連携取得結果DTOリストレスポンスに変換
     *
     * @param dateInfoList WEB-API連携取得結果DTOクラス - 配送情報取得 詳細情報-日付情報
     * @return WEB-API連携取得結果DTOリストレスポンス
     */
    private List<WebApiGetDeliveryInformationResponseDetailDateInfoDtoResponse> toWebApiGetDeliveryInformationResponseDetailDateInfoDtoResponseList(
                    List<WebApiGetDeliveryInformationResponseDetailDateInfoDto> dateInfoList) {

        if (CollectionUtil.isEmpty(dateInfoList)) {
            return new ArrayList<>();
        }
        List<WebApiGetDeliveryInformationResponseDetailDateInfoDtoResponse> responseList = new ArrayList<>();

        dateInfoList.forEach(dateInfo -> {
            WebApiGetDeliveryInformationResponseDetailDateInfoDtoResponse response =
                            new WebApiGetDeliveryInformationResponseDetailDateInfoDtoResponse();

            response.setReceiveDate(dateInfo.getReceiveDate());
            response.setDispTimeZoneCode(dateInfo.getDispTimeZoneCode());

            responseList.add(response);
        });

        return responseList;
    }

    /**
     * 配送方法詳細DTOレスポンスに変換
     *
     * @param deliveryDetailsDto 配送方法詳細DTO
     * @return 配送方法詳細DTOレスポンス
     */
    private DeliveryDetailsDtoResponse toDeliveryDetailsDtoResponse(DeliveryDetailsDto deliveryDetailsDto) {

        if (ObjectUtils.isEmpty(deliveryDetailsDto)) {
            return null;
        }

        DeliveryDetailsDtoResponse deliveryDetailsDtoResponse = new DeliveryDetailsDtoResponse();

        deliveryDetailsDtoResponse.setDeliveryMethodSeq(deliveryDetailsDto.getDeliveryMethodSeq());
        deliveryDetailsDtoResponse.setDeliveryMethodName(deliveryDetailsDto.getDeliveryMethodName());
        deliveryDetailsDtoResponse.setDeliveryMethodDisplayName(deliveryDetailsDto.getDeliveryMethodDisplayNamePC());
        deliveryDetailsDtoResponse.setDeliveryNote(deliveryDetailsDto.getDeliveryNotePC());
        deliveryDetailsDtoResponse.setEqualsCarriage(deliveryDetailsDto.getEqualsCarriage());
        deliveryDetailsDtoResponse.setLargeAmountDiscountPrice(deliveryDetailsDto.getLargeAmountDiscountPrice());
        deliveryDetailsDtoResponse.setLargeAmountDiscountCarriage(deliveryDetailsDto.getLargeAmountDiscountCarriage());
        deliveryDetailsDtoResponse.setDeliveryLeadTime(deliveryDetailsDto.getDeliveryLeadTime());
        deliveryDetailsDtoResponse.setPossibleSelectDays(deliveryDetailsDto.getPossibleSelectDays());
        deliveryDetailsDtoResponse.setReceiverTimeZone1(deliveryDetailsDto.getReceiverTimeZone1());
        deliveryDetailsDtoResponse.setReceiverTimeZone2(deliveryDetailsDto.getReceiverTimeZone2());
        deliveryDetailsDtoResponse.setReceiverTimeZone3(deliveryDetailsDto.getReceiverTimeZone3());
        deliveryDetailsDtoResponse.setReceiverTimeZone4(deliveryDetailsDto.getReceiverTimeZone4());
        deliveryDetailsDtoResponse.setReceiverTimeZone5(deliveryDetailsDto.getReceiverTimeZone5());
        deliveryDetailsDtoResponse.setReceiverTimeZone6(deliveryDetailsDto.getReceiverTimeZone6());
        deliveryDetailsDtoResponse.setReceiverTimeZone7(deliveryDetailsDto.getReceiverTimeZone7());
        deliveryDetailsDtoResponse.setReceiverTimeZone8(deliveryDetailsDto.getReceiverTimeZone8());
        deliveryDetailsDtoResponse.setReceiverTimeZone9(deliveryDetailsDto.getReceiverTimeZone9());
        deliveryDetailsDtoResponse.setReceiverTimeZone10(deliveryDetailsDto.getReceiverTimeZone10());
        deliveryDetailsDtoResponse.setOrderDisplay(deliveryDetailsDto.getOrderDisplay());
        deliveryDetailsDtoResponse.setMaxPrice(deliveryDetailsDto.getMaxPrice());
        deliveryDetailsDtoResponse.setCarriage(deliveryDetailsDto.getCarriage());
        if (deliveryDetailsDto.getOpenStatusPC() != null) {
            deliveryDetailsDtoResponse.setOpenStatus(deliveryDetailsDto.getOpenStatusPC().getValue());
        }
        if (deliveryDetailsDto.getDeliveryMethodType() != null) {
            deliveryDetailsDtoResponse.setDeliveryMethodType(deliveryDetailsDto.getDeliveryMethodType().getValue());
        }
        if (deliveryDetailsDto.getShortfallDisplayFlag() != null) {
            deliveryDetailsDtoResponse.setShortfallDisplayFlag(deliveryDetailsDto.getShortfallDisplayFlag().getValue());
        }
        if (deliveryDetailsDto.getPrefectureType() != null) {
            deliveryDetailsDtoResponse.setPrefectureType(deliveryDetailsDto.getPrefectureType().getValue());
        }

        return deliveryDetailsDtoResponse;
    }

    /**
     * お届け希望日Dtoレスポンスに変換
     *
     * @param receiverDateDto お届け希望日Dto
     * @return お届け希望日Dtoレスポンス
     */
    private ReceiverDateDtoResponse toReceiverDateDtoResponse(ReceiverDateDto receiverDateDto) {

        if (ObjectUtils.isEmpty(receiverDateDto)) {
            return null;
        }

        ReceiverDateDtoResponse response = new ReceiverDateDtoResponse();

        response.setDateMap(receiverDateDto.getDateMap());
        response.setShortestDeliveryDateToRegist(receiverDateDto.getShortestDeliveryDateToRegist());
        if (receiverDateDto.getReceiverDateDesignationFlag() != null) {
            response.setReceiverDateDesignationFlag(receiverDateDto.getReceiverDateDesignationFlag().getValue());
        }

        return response;
    }

    /**
     * 入荷次第お届け分DTOリストに変換
     *
     * @param orderDependingOnReceiptGoodsDtoRequestList 入荷次第お届け分DTOリクエストリスト
     * @return 入荷次第お届け分DTOリスト
     */
    private List<OrderDependingOnReceiptGoodsDto> toOrderDependingOnReceiptGoodsDtoList(List<OrderDependingOnReceiptGoodsDtoRequest> orderDependingOnReceiptGoodsDtoRequestList) {

        if (CollectionUtil.isEmpty(orderDependingOnReceiptGoodsDtoRequestList)) {
            return new ArrayList<>();
        }

        List<OrderDependingOnReceiptGoodsDto> orderDependingOnReceiptGoodsDtoList = new ArrayList<>();

        orderDependingOnReceiptGoodsDtoRequestList.forEach(orderDependingOnReceiptGoodsDtoRequest -> {
            OrderDependingOnReceiptGoodsDto orderDependingOnReceiptGoodsDto = new OrderDependingOnReceiptGoodsDto();

            orderDependingOnReceiptGoodsDto.setOrderGoodsEntity(
                            toOrderGoodsEntity(orderDependingOnReceiptGoodsDtoRequest.getOrderGoodsEntity()));
            orderDependingOnReceiptGoodsDto.setStockDate(
                            conversionUtility.toTimeStamp(orderDependingOnReceiptGoodsDtoRequest.getStockDate()));
            orderDependingOnReceiptGoodsDto.setStockManagementFlag(
                            EnumTypeUtil.getEnumFromValue(HTypeStockManagementFlag.class,
                                                          orderDependingOnReceiptGoodsDtoRequest.getStockManagementFlag()
                                                         ));

            orderDependingOnReceiptGoodsDtoList.add(orderDependingOnReceiptGoodsDto);
        });

        return orderDependingOnReceiptGoodsDtoList;
    }

    /**
     * 取りおき情報DTOリストに変換
     *
     * @param orderReserveDeliveryDtoRequestList 取りおき情報DTOリクエストリスト
     * @return 取りおき情報DTOリスト
     */
    private List<OrderReserveDeliveryDto> toOrderReserveDeliveryDtoList(List<OrderReserveDeliveryDtoRequest> orderReserveDeliveryDtoRequestList) {

        if (CollectionUtil.isEmpty(orderReserveDeliveryDtoRequestList)) {
            return new ArrayList<>();
        }

        List<OrderReserveDeliveryDto> orderReserveDeliveryDtoList = new ArrayList<>();

        orderReserveDeliveryDtoRequestList.forEach(orderReserveDeliveryDtoRequest -> {
            OrderReserveDeliveryDto orderReserveDeliveryDto = new OrderReserveDeliveryDto();
            orderReserveDeliveryDto.setOrderGoodsEntity(
                            toOrderGoodsEntity(orderReserveDeliveryDtoRequest.getOrderGoodsEntity()));
        });

        return orderReserveDeliveryDtoList;
    }

    /**
     * 今すぐお届け分DTOリストに変換
     *
     * @param orderDeliveryNowDtoRequestList 今すぐお届け分DTOリクエストリスト
     * @return 今すぐお届け分DTOリスト
     */
    private List<OrderDeliveryNowDto> toOrderDeliveryNowDtoList(List<OrderDeliveryNowDtoRequest> orderDeliveryNowDtoRequestList) {

        if (CollectionUtil.isEmpty(orderDeliveryNowDtoRequestList)) {
            return new ArrayList<>();
        }

        List<OrderDeliveryNowDto> orderDeliveryNowDtoList = new ArrayList<>();

        orderDeliveryNowDtoRequestList.forEach(orderDeliveryNowDtoRequest -> {

            OrderDeliveryNowDto orderDeliveryNowDto = new OrderDeliveryNowDto();

            orderDeliveryNowDto.setOrderGoodsEntity(
                            toOrderGoodsEntity(orderDeliveryNowDtoRequest.getOrderGoodsEntity()));

            orderDeliveryNowDtoList.add(orderDeliveryNowDto);
        });

        return orderDeliveryNowDtoList;
    }

    /**
     * 受注商品に変換
     *
     * @param orderGoodsRequest 受注商品リクエスト
     * @return 受注商品
     */
    private OrderGoodsEntity toOrderGoodsEntity(OrderGoodsRequest orderGoodsRequest) {

        if (ObjectUtils.isEmpty(orderGoodsRequest)) {
            return null;
        }

        OrderGoodsEntity orderGoodsEntity = new OrderGoodsEntity();

        orderGoodsEntity.setOrderSeq(orderGoodsRequest.getOrderSeq());
        orderGoodsEntity.setOrderGoodsVersionNo(orderGoodsRequest.getOrderGoodsVersionNo());
        orderGoodsEntity.setOrderConsecutiveNo(orderGoodsRequest.getOrderConsecutiveNo());
        orderGoodsEntity.setGoodsSeq(orderGoodsRequest.getGoodsSeq());
        orderGoodsEntity.setOrderDisplay(orderGoodsRequest.getOrderDisplay());
        orderGoodsEntity.setProcessTime(conversionUtility.toTimeStamp(orderGoodsRequest.getProcessTime()));
        orderGoodsEntity.setGoodsGroupCode(orderGoodsRequest.getGoodsGroupCode());
        orderGoodsEntity.setGoodsCode(orderGoodsRequest.getGoodsCode());
        orderGoodsEntity.setJanCode(orderGoodsRequest.getJanCode());
        orderGoodsEntity.setGoodsGroupName(orderGoodsRequest.getGoodsGroupName());
        orderGoodsEntity.setTaxRate(orderGoodsRequest.getTaxRate());
        orderGoodsEntity.setGoodsPrice(orderGoodsRequest.getGoodsPrice());
        orderGoodsEntity.setPreGoodsCount(orderGoodsRequest.getPreGoodsCount());
        orderGoodsEntity.setGoodsCount(orderGoodsRequest.getGoodsCount());
        orderGoodsEntity.setUnitValue1(orderGoodsRequest.getUnitValue1());
        orderGoodsEntity.setUnitValue2(orderGoodsRequest.getUnitValue2());
        orderGoodsEntity.setDeliveryType(orderGoodsRequest.getDeliveryType());
        orderGoodsEntity.setOrderSetting1(orderGoodsRequest.getOrderSetting1());
        orderGoodsEntity.setOrderSetting2(orderGoodsRequest.getOrderSetting2());
        orderGoodsEntity.setOrderSetting3(orderGoodsRequest.getOrderSetting3());
        orderGoodsEntity.setOrderSetting4(orderGoodsRequest.getOrderSetting4());
        orderGoodsEntity.setOrderSetting5(orderGoodsRequest.getOrderSetting5());
        orderGoodsEntity.setOrderSetting6(orderGoodsRequest.getOrderSetting6());
        orderGoodsEntity.setOrderSetting7(orderGoodsRequest.getOrderSetting7());
        orderGoodsEntity.setOrderSetting8(orderGoodsRequest.getOrderSetting8());
        orderGoodsEntity.setOrderSetting9(orderGoodsRequest.getOrderSetting9());
        orderGoodsEntity.setOrderSetting10(orderGoodsRequest.getOrderSetting10());
        orderGoodsEntity.setRegistTime(conversionUtility.toTimeStamp(orderGoodsRequest.getRegistTime()));
        orderGoodsEntity.setUpdateTime(conversionUtility.toTimeStamp(orderGoodsRequest.getUpdateTime()));
        orderGoodsEntity.setGroupCode(orderGoodsRequest.getGroupCode());
        orderGoodsEntity.setSaleCode(orderGoodsRequest.getSaleCode());
        orderGoodsEntity.setNote(orderGoodsRequest.getNote());
        orderGoodsEntity.setHints(orderGoodsRequest.getHints());
        orderGoodsEntity.setPrice(orderGoodsRequest.getPrice());
        orderGoodsEntity.setBundleFlag(orderGoodsRequest.getBundleFlag());
        orderGoodsEntity.setOrderSerial(orderGoodsRequest.getOrderSerial());
        orderGoodsEntity.setUnitDiscountPrice(orderGoodsRequest.getUnitDiscountPrice());
        orderGoodsEntity.setStockDate(conversionUtility.toTimeStamp(orderGoodsRequest.getStockDate()));
        orderGoodsEntity.setDeliveryYesNo(orderGoodsRequest.getDeliveryYesNo());
        orderGoodsEntity.setDipendingOnReceiptOrderCode(orderGoodsRequest.getDipendingOnReceiptOrderCode());

        orderGoodsEntity.setTotalingType(
                        EnumTypeUtil.getEnumFromValue(HTypeTotalingType.class, orderGoodsRequest.getTotalingType()));
        orderGoodsEntity.setSalesFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeSalesFlag.class, orderGoodsRequest.getSalesFlag()));
        orderGoodsEntity.setGoodsTaxType(
                        EnumTypeUtil.getEnumFromValue(HTypeGoodsTaxType.class, orderGoodsRequest.getGoodsTaxType()));
        orderGoodsEntity.setFreeDeliveryFlag(EnumTypeUtil.getEnumFromValue(HTypeFreeDeliveryFlag.class,
                                                                           orderGoodsRequest.getFreeDeliveryFlag()
                                                                          ));
        orderGoodsEntity.setIndividualDeliveryType(EnumTypeUtil.getEnumFromValue(HTypeIndividualDeliveryType.class,
                                                                                 orderGoodsRequest.getIndividualDeliveryType()
                                                                                ));
        orderGoodsEntity.setDeliverySlipFlag(EnumTypeUtil.getEnumFromValue(HTypeDeliverySlipFlag.class,
                                                                           orderGoodsRequest.getDeliverySlipFlag()
                                                                          ));
        orderGoodsEntity.setDiscountsType(
                        EnumTypeUtil.getEnumFromValue(HTypeDiscountsType.class, orderGoodsRequest.getDiscountsType()));
        orderGoodsEntity.setStockManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeStockManagementFlag.class,
                                                                              orderGoodsRequest.getStockManagementFlag()
                                                                             ));

        return orderGoodsEntity;
    }

    /**
     * 配送情報取得 詳細情報に変換
     *
     * @param webApiGetDeliveryInformationResponseDetailDtoRequest WEB-API連携取得結果DTOリクエスト
     * @return 配送情報取得 詳細情報
     */
    private WebApiGetDeliveryInformationResponseDetailDto toWebApiGetDeliveryInformationResponseDetailDto(
                    WebApiGetDeliveryInformationResponseDetailDtoRequest webApiGetDeliveryInformationResponseDetailDtoRequest) {

        if (ObjectUtils.isEmpty(webApiGetDeliveryInformationResponseDetailDtoRequest)) {
            return null;
        }

        WebApiGetDeliveryInformationResponseDetailDto webApiGetDeliveryInformationResponseDetailDto =
                        new WebApiGetDeliveryInformationResponseDetailDto();

        webApiGetDeliveryInformationResponseDetailDto.setDeliveryAssignFlag(
                        webApiGetDeliveryInformationResponseDetailDtoRequest.getDeliveryAssignFlag());
        webApiGetDeliveryInformationResponseDetailDto.setDeliveryCompanyCode(
                        webApiGetDeliveryInformationResponseDetailDtoRequest.getDeliveryCompanyCode());
        webApiGetDeliveryInformationResponseDetailDto.setDeliveryFlag(
                        webApiGetDeliveryInformationResponseDetailDtoRequest.getDeliveryFlag());
        webApiGetDeliveryInformationResponseDetailDto.setNodeliveryGoodsCode(
                        webApiGetDeliveryInformationResponseDetailDtoRequest.getNodeliveryGoodsCode());
        webApiGetDeliveryInformationResponseDetailDto.setDateInfo(
                        toWebApiGetDeliveryInformationResponseDetailDateInfoDtoList(
                                        webApiGetDeliveryInformationResponseDetailDtoRequest.getDateInfo()));

        return webApiGetDeliveryInformationResponseDetailDto;
    }

    /**
     * 配送情報取得 詳細情報-日付情報リストに変換
     *
     * @param dateInfoRequestList WEB-API連携取得結果DTOクラスリクエストリスト
     * @return 配送情報取得 詳細情報-日付情報リスト
     */
    private List<WebApiGetDeliveryInformationResponseDetailDateInfoDto> toWebApiGetDeliveryInformationResponseDetailDateInfoDtoList(
                    List<WebApiGetDeliveryInformationResponseDetailDateInfoDtoRequest> dateInfoRequestList) {

        if (CollectionUtil.isEmpty(dateInfoRequestList)) {
            return new ArrayList<>();
        }

        List<WebApiGetDeliveryInformationResponseDetailDateInfoDto> dateInfoDtoList = new ArrayList<>();

        dateInfoRequestList.forEach(dateInfoRequest -> {
            WebApiGetDeliveryInformationResponseDetailDateInfoDto dateInfoDto =
                            new WebApiGetDeliveryInformationResponseDetailDateInfoDto();

            dateInfoDto.setReceiveDate(conversionUtility.toTimeStamp(dateInfoRequest.getReceiveDate()));
            dateInfoDto.setDispTimeZoneCode(dateInfoRequest.getDispTimeZoneCode());

            dateInfoDtoList.add(dateInfoDto);
        });

        return dateInfoDtoList;
    }

    /**
     * 受注配送クラスに変換
     *
     * @param orderDeliveryRequest 受注配送リクエスト
     * @return 受注配送クラス
     */
    private OrderDeliveryEntity toOrderDeliveryEntity(OrderDeliveryRequest orderDeliveryRequest) {

        if (ObjectUtils.isEmpty(orderDeliveryRequest)) {
            return null;
        }

        OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();

        orderDeliveryEntity.setOrderSeq(orderDeliveryRequest.getOrderSeq());
        orderDeliveryEntity.setOrderDeliveryVersionNo(orderDeliveryRequest.getOrderDeliveryVersionNo());
        orderDeliveryEntity.setOrderConsecutiveNo(orderDeliveryRequest.getOrderConsecutiveNo());
        orderDeliveryEntity.setPlanDate(conversionUtility.toTimeStamp(orderDeliveryRequest.getPlanDate()));
        orderDeliveryEntity.setShipmentDate(conversionUtility.toTimeStamp(orderDeliveryRequest.getShipmentDate()));
        orderDeliveryEntity.setDeliveryCode(orderDeliveryRequest.getDeliveryCode());
        orderDeliveryEntity.setDeliveryMethodSeq(orderDeliveryRequest.getDeliveryMethodSeq());
        orderDeliveryEntity.setReceiverLastName(orderDeliveryRequest.getReceiverLastName());
        orderDeliveryEntity.setReceiverFirstName(orderDeliveryRequest.getReceiverFirstName());
        orderDeliveryEntity.setReceiverLastKana(orderDeliveryRequest.getReceiverLastKana());
        orderDeliveryEntity.setReceiverFirstKana(orderDeliveryRequest.getReceiverFirstKana());
        orderDeliveryEntity.setReceiverTel(orderDeliveryRequest.getReceiverTel());
        orderDeliveryEntity.setReceiverZipCode(orderDeliveryRequest.getReceiverZipCode());
        orderDeliveryEntity.setReceiverPrefecture(orderDeliveryRequest.getReceiverPrefecture());
        orderDeliveryEntity.setReceiverAddress1(orderDeliveryRequest.getReceiverAddress1());
        orderDeliveryEntity.setReceiverAddress2(orderDeliveryRequest.getReceiverAddress2());
        orderDeliveryEntity.setReceiverAddress3(orderDeliveryRequest.getReceiverAddress3());
        orderDeliveryEntity.setReceiverDate(conversionUtility.toTimeStamp(orderDeliveryRequest.getReceiverDate()));
        orderDeliveryEntity.setReceiverTimeZone(orderDeliveryRequest.getReceiverTimeZone());
        orderDeliveryEntity.setDeliveryNote(orderDeliveryRequest.getDeliveryNote());
        orderDeliveryEntity.setOthersNote(orderDeliveryRequest.getOthersNote());
        orderDeliveryEntity.setMessage(orderDeliveryRequest.getMessage());
        orderDeliveryEntity.setCarriage(orderDeliveryRequest.getCarriage());
        orderDeliveryEntity.setRegistTime(conversionUtility.toTimeStamp(orderDeliveryRequest.getRegistTime()));
        orderDeliveryEntity.setUpdateTime(conversionUtility.toTimeStamp(orderDeliveryRequest.getUpdateTime()));
        orderDeliveryEntity.setShortestDeliveryDateToRegist(
                        conversionUtility.toTimeStamp(orderDeliveryRequest.getShortestDeliveryDateToRegist()));
        orderDeliveryEntity.setDeliveryDay(orderDeliveryRequest.getDeliveryDay());
        orderDeliveryEntity.setReceiverAddress4(orderDeliveryRequest.getReceiverAddress4());

        orderDeliveryEntity.setShipmentStatus(EnumTypeUtil.getEnumFromValue(HTypeShipmentStatus.class,
                                                                            orderDeliveryRequest.getShipmentStatus()
                                                                           ));
        orderDeliveryEntity.setReservationDeliveryFlag(EnumTypeUtil.getEnumFromValue(HTypeReservationDeliveryFlag.class,
                                                                                     orderDeliveryRequest.getReservationDeliveryFlag()
                                                                                    ));
        orderDeliveryEntity.setInvoiceAttachmentFlag(EnumTypeUtil.getEnumFromValue(HTypeInvoiceAttachmentFlag.class,
                                                                                   orderDeliveryRequest.getInvoiceAttachmentFlag()
                                                                                  ));
        orderDeliveryEntity.setReceiverDateDesignationFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeReceiverDateDesignationFlag.class,
                                                      orderDeliveryRequest.getReceiverDateDesignationFlag()
                                                     ));
        orderDeliveryEntity.setDeliveryCycle(EnumTypeUtil.getEnumFromValue(HTypeDeliveryCycle.class,
                                                                           orderDeliveryRequest.getDeliveryCycle()
                                                                          ));

        return orderDeliveryEntity;
    }

    /**
     * 配送方法クラスに変換
     *
     * @param deliveryMethodRequest 配送方法リクエスト
     * @return 配送方法クラス
     */
    private DeliveryMethodEntity toDeliveryMethodEntity(DeliveryMethodRequest deliveryMethodRequest) {

        if (ObjectUtils.isEmpty(deliveryMethodRequest)) {
            return null;
        }

        DeliveryMethodEntity deliveryMethodEntity = new DeliveryMethodEntity();

        deliveryMethodEntity.setDeliveryMethodSeq(deliveryMethodRequest.getDeliveryMethodSeq());
        deliveryMethodEntity.setDeliveryMethodName(deliveryMethodRequest.getDeliveryMethodName());
        deliveryMethodEntity.setDeliveryMethodDisplayNamePC(deliveryMethodRequest.getDeliveryMethodDisplayName());
        deliveryMethodEntity.setDeliveryNotePC(deliveryMethodRequest.getDeliveryNote());
        deliveryMethodEntity.setEqualsCarriage(deliveryMethodRequest.getEqualsCarriage());
        deliveryMethodEntity.setLargeAmountDiscountPrice(deliveryMethodRequest.getLargeAmountDiscountPrice());
        deliveryMethodEntity.setLargeAmountDiscountCarriage(deliveryMethodRequest.getLargeAmountDiscountCarriage());
        deliveryMethodEntity.setDeliveryLeadTime(deliveryMethodRequest.getDeliveryLeadTime());
        deliveryMethodEntity.setDeliveryChaseURL(deliveryMethodRequest.getDeliveryChaseURL());
        deliveryMethodEntity.setDeliveryChaseURLDisplayPeriod(deliveryMethodRequest.getDeliveryChaseURLDisplayPeriod());
        deliveryMethodEntity.setPossibleSelectDays(deliveryMethodRequest.getPossibleSelectDays());
        deliveryMethodEntity.setReceiverTimeZone1(deliveryMethodRequest.getReceiverTimeZone1());
        deliveryMethodEntity.setReceiverTimeZone2(deliveryMethodRequest.getReceiverTimeZone2());
        deliveryMethodEntity.setReceiverTimeZone3(deliveryMethodRequest.getReceiverTimeZone3());
        deliveryMethodEntity.setReceiverTimeZone4(deliveryMethodRequest.getReceiverTimeZone4());
        deliveryMethodEntity.setReceiverTimeZone5(deliveryMethodRequest.getReceiverTimeZone5());
        deliveryMethodEntity.setReceiverTimeZone6(deliveryMethodRequest.getReceiverTimeZone6());
        deliveryMethodEntity.setReceiverTimeZone7(deliveryMethodRequest.getReceiverTimeZone7());
        deliveryMethodEntity.setReceiverTimeZone8(deliveryMethodRequest.getReceiverTimeZone8());
        deliveryMethodEntity.setReceiverTimeZone9(deliveryMethodRequest.getReceiverTimeZone9());
        deliveryMethodEntity.setReceiverTimeZone10(deliveryMethodRequest.getReceiverTimeZone10());
        deliveryMethodEntity.setOrderDisplay(deliveryMethodRequest.getOrderDisplay());
        deliveryMethodEntity.setRegistTime(conversionUtility.toTimeStamp(deliveryMethodRequest.getRegistTime()));
        deliveryMethodEntity.setUpdateTime(conversionUtility.toTimeStamp(deliveryMethodRequest.getUpdateTime()));

        deliveryMethodEntity.setOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                           deliveryMethodRequest.getOpenStatus()
                                                                          ));
        deliveryMethodEntity.setDeliveryMethodType(EnumTypeUtil.getEnumFromValue(HTypeDeliveryMethodType.class,
                                                                                 deliveryMethodRequest.getDeliveryMethodType()
                                                                                ));
        deliveryMethodEntity.setShortfallDisplayFlag(EnumTypeUtil.getEnumFromValue(HTypeShortfallDisplayFlag.class,
                                                                                   deliveryMethodRequest.getShortfallDisplayFlag()
                                                                                  ));

        return deliveryMethodEntity;
    }

    /**
     * 配送DTOクリストに変換
     *
     * @param deliveryDtoRequestList 配送DTOリクエストリスト
     * @return 配送DTOクリスト
     */
    private List<DeliveryDto> toDeliveryDtoList(List<DeliveryDtoRequest> deliveryDtoRequestList) {

        if (CollectionUtil.isEmpty(deliveryDtoRequestList)) {
            return new ArrayList<>();
        }

        List<DeliveryDto> deliveryDtoList = new ArrayList<>();

        deliveryDtoRequestList.forEach(deliveryDtoRequest -> {
            DeliveryDto deliveryDto = new DeliveryDto();

            deliveryDto.setDeliveryDetailsDto(toDeliveryDetailsDto(deliveryDtoRequest.getDeliveryDetailsDto()));
            deliveryDto.setCarriage(deliveryDtoRequest.getCarriage());
            deliveryDto.setSelectClass(deliveryDtoRequest.getSelectClass());
            deliveryDto.setReceiverDateDto(toReceiverDateDto(deliveryDtoRequest.getReceiverDateDto()));
            deliveryDto.setSpecialChargeAreaFlag(deliveryDtoRequest.getSpecialChargeAreaFlag());

            deliveryDtoList.add(deliveryDto);
        });

        return deliveryDtoList;
    }

    /**
     * お届け希望日Dtoに変換
     *
     * @param receiverDateDtoRequest お届け希望日Dtoリクエスト
     * @return お届け希望日Dto
     */
    private ReceiverDateDto toReceiverDateDto(ReceiverDateDtoRequest receiverDateDtoRequest) {

        if (ObjectUtils.isEmpty(receiverDateDtoRequest)) {
            return null;
        }

        ReceiverDateDto receiverDateDto = new ReceiverDateDto();

        receiverDateDto.setDateMap(receiverDateDtoRequest.getDateMap());
        receiverDateDto.setReceiverDateDesignationFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeReceiverDateDesignationFlag.class,
                                                      receiverDateDtoRequest.getReceiverDateDesignationFlag()
                                                     ));
        receiverDateDto.setShortestDeliveryDateToRegist(
                        conversionUtility.toTimeStamp(receiverDateDtoRequest.getShortestDeliveryDateToRegist()));

        return receiverDateDto;
    }

    /**
     * 配送方法詳細DTOに変換
     *
     * @param deliveryDetailsDtoRequest 配送方法詳細DTOレスポンス
     * @return 配送方法詳細DTO
     */
    private DeliveryDetailsDto toDeliveryDetailsDto(DeliveryDetailsDtoRequest deliveryDetailsDtoRequest) {

        if (ObjectUtils.isEmpty(deliveryDetailsDtoRequest)) {
            return null;
        }

        DeliveryDetailsDto deliveryDetailsDto = new DeliveryDetailsDto();

        deliveryDetailsDto.setDeliveryMethodSeq(deliveryDetailsDtoRequest.getDeliveryMethodSeq());
        deliveryDetailsDto.setDeliveryMethodName(deliveryDetailsDtoRequest.getDeliveryMethodName());
        deliveryDetailsDto.setDeliveryMethodDisplayNamePC(deliveryDetailsDtoRequest.getDeliveryMethodDisplayName());
        deliveryDetailsDto.setDeliveryNotePC(deliveryDetailsDtoRequest.getDeliveryNote());
        deliveryDetailsDto.setEqualsCarriage(deliveryDetailsDtoRequest.getEqualsCarriage());
        deliveryDetailsDto.setLargeAmountDiscountPrice(deliveryDetailsDtoRequest.getLargeAmountDiscountPrice());
        deliveryDetailsDto.setLargeAmountDiscountCarriage(deliveryDetailsDtoRequest.getLargeAmountDiscountCarriage());
        deliveryDetailsDto.setDeliveryLeadTime(deliveryDetailsDtoRequest.getDeliveryLeadTime());
        deliveryDetailsDto.setPossibleSelectDays(deliveryDetailsDtoRequest.getPossibleSelectDays());
        deliveryDetailsDto.setReceiverTimeZone1(deliveryDetailsDtoRequest.getReceiverTimeZone1());
        deliveryDetailsDto.setReceiverTimeZone2(deliveryDetailsDtoRequest.getReceiverTimeZone2());
        deliveryDetailsDto.setReceiverTimeZone3(deliveryDetailsDtoRequest.getReceiverTimeZone3());
        deliveryDetailsDto.setReceiverTimeZone4(deliveryDetailsDtoRequest.getReceiverTimeZone4());
        deliveryDetailsDto.setReceiverTimeZone5(deliveryDetailsDtoRequest.getReceiverTimeZone5());
        deliveryDetailsDto.setReceiverTimeZone6(deliveryDetailsDtoRequest.getReceiverTimeZone6());
        deliveryDetailsDto.setReceiverTimeZone7(deliveryDetailsDtoRequest.getReceiverTimeZone7());
        deliveryDetailsDto.setReceiverTimeZone8(deliveryDetailsDtoRequest.getReceiverTimeZone8());
        deliveryDetailsDto.setReceiverTimeZone9(deliveryDetailsDtoRequest.getReceiverTimeZone9());
        deliveryDetailsDto.setReceiverTimeZone10(deliveryDetailsDtoRequest.getReceiverTimeZone10());
        deliveryDetailsDto.setOrderDisplay(deliveryDetailsDtoRequest.getOrderDisplay());
        deliveryDetailsDto.setMaxPrice(deliveryDetailsDtoRequest.getMaxPrice());
        deliveryDetailsDto.setCarriage(deliveryDetailsDtoRequest.getCarriage());

        deliveryDetailsDto.setOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                         deliveryDetailsDtoRequest.getOpenStatus()
                                                                        ));
        deliveryDetailsDto.setDeliveryMethodType(EnumTypeUtil.getEnumFromValue(HTypeDeliveryMethodType.class,
                                                                               deliveryDetailsDtoRequest.getDeliveryMethodType()
                                                                              ));
        deliveryDetailsDto.setShortfallDisplayFlag(EnumTypeUtil.getEnumFromValue(HTypeShortfallDisplayFlag.class,
                                                                                 deliveryDetailsDtoRequest.getShortfallDisplayFlag()
                                                                                ));
        deliveryDetailsDto.setPrefectureType(EnumTypeUtil.getEnumFromValue(HTypePrefectureType.class,
                                                                           deliveryDetailsDtoRequest.getPrefectureType()
                                                                          ));

        return deliveryDetailsDto;
    }

    /**
     * 受注Dtoクラスに変換
     *
     * @param receiveOrderDtoJson 受注DtoクラスJSON
     * @return 受注Dtoクラス
     */
    public ReceiveOrderDto toReceiveOrderDto(String receiveOrderDtoJson) {

        if (StringUtils.isEmpty(receiveOrderDtoJson)) {
            return null;
        }

        return conversionUtility.toObject(receiveOrderDtoJson, ReceiveOrderDto.class);
    }

    /**
     * 受注Dtoクラスに変換
     *
     * @param receiveOrderDto 受注DtoクラスJSON
     * @return 受注Dtoクラス
     */
    public String toReceiveOrderDtoJS(ReceiveOrderDto receiveOrderDto) {

        if (ObjectUtils.isEmpty(receiveOrderDto)) {
            return null;
        }

        return conversionUtility.toJson(receiveOrderDto);
    }

    /**
     * 請求金額算出レスポンスに変換
     *
     * @param receiveOrderDto 受注Dtoクラス
     * @return 請求金額算出レスポンス
     */
    public BillPriceCalculateResponse toBillPriceCalculateResponse(ReceiveOrderDto receiveOrderDto) {

        if (ObjectUtils.isEmpty(receiveOrderDto)) {
            return null;
        }

        BillPriceCalculateResponse response = new BillPriceCalculateResponse();

        String receiveOrderDtoJson = conversionUtility.toJson(receiveOrderDto);
        response.setReceiveOrderDto(receiveOrderDtoJson);

        return response;
    }

    /**
     * 決済Dtoリストレスポンスリストに変換
     *
     * @param settlementDtoList 決済DTOクラススリスト
     * @return 決済Dtoリストレスポンスリスト
     */
    public List<SettlementDtoResponse> toSettlementDtoResponseList(List<SettlementDto> settlementDtoList) {

        if (CollectionUtil.isEmpty(settlementDtoList)) {
            return new ArrayList<>();
        }

        List<SettlementDtoResponse> response = new ArrayList<>();

        for (SettlementDto dto : settlementDtoList) {
            SettlementDtoResponse settlementDtoResponse = new SettlementDtoResponse();
            settlementDtoResponse.settlementDetailsDto(toSettlementDetailsDtoResponse(dto.getSettlementDetailsDto()));
            settlementDtoResponse.setConvenienceEntityList(
                            toConvenienceEntityListResponse(dto.getConvenienceEntityList()).getConvenienceList());
            settlementDtoResponse.setCardBrandEntityList(toCardBrandResponseList(dto.getCardBrandEntityList()));
            settlementDtoResponse.setCharge(dto.getCharge());
            settlementDtoResponse.setSelectClass(dto.isSelectClass());

            response.add(settlementDtoResponse);
        }

        return response;
    }

    /**
     * 決済詳細DTOレスポンスに変換
     *
     * @param settlementDetailsDto 決済詳細DTOクラス
     * @return 決済詳細DTOレスポンス
     */
    private SettlementDetailsDtoResponse toSettlementDetailsDtoResponse(SettlementDetailsDto settlementDetailsDto) {

        if (ObjectUtils.isEmpty(settlementDetailsDto)) {
            return null;
        }

        SettlementDetailsDtoResponse settlementDetailsDtoResponse = new SettlementDetailsDtoResponse();

        settlementDetailsDtoResponse.setSettlementMethodSeq(settlementDetailsDto.getSettlementMethodSeq());
        settlementDetailsDtoResponse.setSettlementMethodName(settlementDetailsDto.getSettlementMethodName());
        settlementDetailsDtoResponse.setSettlementMethodDisplayName(
                        settlementDetailsDto.getSettlementMethodDisplayNamePC());
        if (settlementDetailsDto.getOpenStatusPC() != null) {
            settlementDetailsDtoResponse.setOpenStatus(settlementDetailsDto.getOpenStatusPC().getValue());
        }
        settlementDetailsDtoResponse.setSettlementNote(settlementDetailsDto.getSettlementNotePC());
        if (settlementDetailsDto.getSettlementMethodType() != null) {
            settlementDetailsDtoResponse.setSettlementMethodType(
                            settlementDetailsDto.getSettlementMethodType().getValue());
        }
        if (settlementDetailsDto.getSettlementMethodCommissionType() != null) {
            settlementDetailsDtoResponse.setSettlementMethodCommissionType(
                            settlementDetailsDto.getSettlementMethodCommissionType().getValue());
        }
        if (settlementDetailsDto.getBillType() != null) {
            settlementDetailsDtoResponse.setBillType(settlementDetailsDto.getBillType().getValue());
        }
        settlementDetailsDtoResponse.setDeliveryMethodSeq(settlementDetailsDto.getDeliveryMethodSeq());
        settlementDetailsDtoResponse.setEqualsCommission(settlementDetailsDto.getEqualsCommission());
        if (settlementDetailsDto.getSettlementMethodPriceCommissionFlag() != null) {
            settlementDetailsDtoResponse.setSettlementMethodPriceCommissionFlag(
                            settlementDetailsDto.getSettlementMethodPriceCommissionFlag().getValue());
        }
        settlementDetailsDtoResponse.setLargeAmountDiscountPrice(settlementDetailsDto.getLargeAmountDiscountPrice());
        settlementDetailsDtoResponse.setLargeAmountDiscountCommission(
                        settlementDetailsDto.getLargeAmountDiscountCommission());
        settlementDetailsDtoResponse.setOrderDisplay(settlementDetailsDto.getOrderDisplay());
        settlementDetailsDtoResponse.setMaxPurchasedPrice(settlementDetailsDto.getMaxPurchasedPrice());
        settlementDetailsDtoResponse.setPaymentTimeLimitDayCount(settlementDetailsDto.getPaymentTimeLimitDayCount());
        settlementDetailsDtoResponse.setMinPurchasedPrice(settlementDetailsDto.getMinPurchasedPrice());
        settlementDetailsDtoResponse.setCancelTimeLimitDayCount(settlementDetailsDto.getCancelTimeLimitDayCount());
        if (settlementDetailsDto.getSettlementMailRequired() != null) {
            settlementDetailsDtoResponse.setSettlementMailRequired(
                            settlementDetailsDto.getSettlementMailRequired().getValue());
        }
        if (settlementDetailsDto.getEnableCardNoHolding() != null) {
            settlementDetailsDtoResponse.setEnableCardNoHolding(
                            settlementDetailsDto.getEnableCardNoHolding().getValue());
        }
        if (settlementDetailsDto.getEnableSecurityCode() != null) {
            settlementDetailsDtoResponse.setEnableSecurityCode(settlementDetailsDto.getEnableSecurityCode().getValue());
        }
        if (settlementDetailsDto.getEnable3dSecure() != null) {
            settlementDetailsDtoResponse.setEnable3dSecure(settlementDetailsDto.getEnable3dSecure().getValue());
        }
        if (settlementDetailsDto.getEnableInstallment() != null) {
            settlementDetailsDtoResponse.setEnableInstallment(settlementDetailsDto.getEnableInstallment().getValue());
        }
        if (settlementDetailsDto.getEnableBonusSinglePayment() != null) {
            settlementDetailsDtoResponse.setEnableBonusSinglePayment(
                            settlementDetailsDto.getEnableBonusSinglePayment().getValue());
        }
        if (settlementDetailsDto.getEnableBonusInstallment() != null) {
            settlementDetailsDtoResponse.setEnableBonusInstallment(
                            settlementDetailsDto.getEnableBonusInstallment().getValue());
        }
        if (settlementDetailsDto.getEnableRevolving() != null) {
            settlementDetailsDtoResponse.setEnableRevolving(settlementDetailsDto.getEnableRevolving().getValue());
        }
        settlementDetailsDtoResponse.setMaxPrice(settlementDetailsDto.getMaxPrice());
        settlementDetailsDtoResponse.setCommission(settlementDetailsDto.getCommission());

        return settlementDetailsDtoResponse;
    }

    /**
     * クレジットカードブレスポンスリストに変換
     *
     * @param cardBrandEntityList クレジットカードブランドリスト
     * @return クレジットカードブレスポンスリスト
     */
    private List<CardBrandResponse> toCardBrandResponseList(List<CardBrandEntity> cardBrandEntityList) {

        if (CollectionUtil.isEmpty(cardBrandEntityList)) {
            return new ArrayList<>();
        }

        List<CardBrandResponse> cardBrandResponses = new ArrayList<>();

        for (CardBrandEntity cardBrandEntity : cardBrandEntityList) {
            CardBrandResponse cardBrandResponse = new CardBrandResponse();
            cardBrandResponse.setCardBrandSeq(cardBrandEntity.getCardBrandSeq());
            cardBrandResponse.setCardBrandCode(cardBrandEntity.getCardBrandCode());
            cardBrandResponse.setCardBrandName(cardBrandEntity.getCardBrandName());
            cardBrandResponse.setCardBrandDisplay(cardBrandEntity.getCardBrandDisplayPc());
            cardBrandResponse.setOrderDisplay(cardBrandEntity.getOrderDisplay());
            cardBrandResponse.setInstallment(cardBrandEntity.getInstallment());
            cardBrandResponse.setBounusSingle(cardBrandEntity.getBounusSingle());
            cardBrandResponse.setBounusInstallment(cardBrandEntity.getBounusInstallment());
            cardBrandResponse.setRevolving(cardBrandEntity.getRevolving());
            cardBrandResponse.setInstallmentCounts(cardBrandEntity.getInstallmentCounts());
            if (cardBrandEntity.getFrontDisplayFlag() != null) {
                cardBrandResponse.setFrontDisplayFlag(cardBrandEntity.getFrontDisplayFlag().getValue());
            }

            cardBrandResponses.add(cardBrandResponse);
        }

        return cardBrandResponses;
    }

    /**
     * 注文メッセージDtoレスポンスに変換
     *
     * @param orderMessageDto 注文メッセージDtoクラス
     * @return 注文メッセージDtoレスポンス
     */
    public OrderMessageDtoResponse toOrderMessageDtoResponse(OrderMessageDto orderMessageDto) {

        if (ObjectUtils.isEmpty(orderMessageDto)) {
            return new OrderMessageDtoResponse();
        }

        OrderMessageDtoResponse response = new OrderMessageDtoResponse();

        response.setOrderGoodsMessageMapMap(toOrderGoodsMessageMapMap(orderMessageDto.getOrderGoodsMessageMapMap()));
        response.setOrderGoodsMessageMap(toOrderGoodsMessageMap(orderMessageDto.getOrderGoodsMessageMap()));
        response.setOrderMessageList(toCheckMessageDtoResponseList(orderMessageDto.getOrderMessageList()));

        return response;
    }

    /**
     * 受注商品詳細メッセージマップレスポンスに変換
     * Key=注文連番, 子マップKey=商品SEQ
     *
     * @param orderGoodsMessageMapMap 受注商品詳細メッセージマップ：Key=注文連番, 子マップKey=商品SEQ
     * @return 受注商品詳細メッセージマップレスポンス：Key=注文連番, 子マップKey=商品SEQ
     */
    private Map<String, Map<String, List<CheckMessageDtoResponse>>> toOrderGoodsMessageMapMap(Map<Integer, Map<Integer, List<CheckMessageDto>>> orderGoodsMessageMapMap) {

        if (MapUtils.isEmpty(orderGoodsMessageMapMap)) {
            return new HashMap<>();
        }

        Map<String, Map<String, List<CheckMessageDtoResponse>>> orderGoodsMessageMapMapResponse = new HashMap<>();

        for (Map.Entry<Integer, Map<Integer, List<CheckMessageDto>>> entry : orderGoodsMessageMapMap.entrySet()) {
            String newKey = entry.getKey().toString();
            Map<String, List<CheckMessageDtoResponse>> newValue = toOrderGoodsMessageMap(entry.getValue());
            orderGoodsMessageMapMapResponse.put(newKey, newValue);
        }

        return orderGoodsMessageMapMapResponse;
    }

    /**
     * 受注商品詳細メッセージマップレスポンスに変換
     * Key=注文連番, 子マップKey=商品SEQ
     *
     * @param orderGoodsMessageMap 受注商品詳細メッセージマップ：Key=注文連番, 子マップKey=商品SEQ
     * @return 受注商品詳細メッセージマップレスポンス：Key=注文連番, 子マップKey=商品SEQ
     */
    private Map<String, List<CheckMessageDtoResponse>> toOrderGoodsMessageMap(Map<Integer, List<CheckMessageDto>> orderGoodsMessageMap) {

        if (MapUtils.isEmpty(orderGoodsMessageMap)) {
            return new HashMap<>();
        }

        Map<String, List<CheckMessageDtoResponse>> map = new HashMap<>();

        for (Map.Entry<Integer, List<CheckMessageDto>> entry : orderGoodsMessageMap.entrySet()) {
            String newKey = entry.getKey().toString();
            List<CheckMessageDtoResponse> newValue = toCheckMessageDtoResponseList(entry.getValue());
            map.put(newKey, newValue);
        }

        return map;
    }

    /**
     * チェックメッセージDtoクラスレスポンスリストに変換
     *
     * @param checkMessageDtoList チェックメッセージDtoクラスリスト
     * @return チェックメッセージDtoクラスレスポンスリスト
     */
    public List<CheckMessageDtoResponse> toCheckMessageDtoResponseList(List<CheckMessageDto> checkMessageDtoList) {

        if (CollectionUtil.isEmpty(checkMessageDtoList)) {
            return new ArrayList<>();
        }

        List<CheckMessageDtoResponse> responses = new ArrayList<>();

        for (CheckMessageDto dto : checkMessageDtoList) {
            CheckMessageDtoResponse checkMessageDtoResponse = new CheckMessageDtoResponse();

            checkMessageDtoResponse.setMessageId(dto.getMessageId());
            if (ObjectUtils.isNotEmpty(dto.getArgs())) {
                checkMessageDtoResponse.setArgs(Arrays.asList(dto.getArgs()));
            }
            checkMessageDtoResponse.setMessage(dto.getMessage());
            checkMessageDtoResponse.setOrderConsecutiveNo(dto.getOrderConsecutiveNo());
            checkMessageDtoResponse.setError(dto.isError());

            responses.add(checkMessageDtoResponse);
        }

        return responses;
    }

    /**
     * 受注Dtoクラスリストに変換
     *
     * @param receiveOrderDtoJSONList 受注DtoクラスJSONリスト
     * @return 受注Dtoクラスリスト
     */
    public List<ReceiveOrderDto> toReceiveOrderDtoList(List<String> receiveOrderDtoJSONList) {

        if (CollectionUtil.isEmpty(receiveOrderDtoJSONList)) {
            return new ArrayList<>();
        }

        List<ReceiveOrderDto> receiveOrderDtoList = new ArrayList<>();

        for (String receiveOrderDtoJson : receiveOrderDtoJSONList) {

            ReceiveOrderDto receiveOrderDto = toReceiveOrderDto(receiveOrderDtoJson);

            receiveOrderDtoList.add(receiveOrderDto);
        }

        return receiveOrderDtoList;
    }

    /**
     * 新しいお届け先として入力された情報を<br/>
     * 会員情報に変換します。
     *
     * @param orderDeliveryDto 受注配送DTO
     * @return 会員情報
     */
    public MemberInfoEntity toMemberInfoEntityForMemberInfoRegist(OrderDeliveryDto orderDeliveryDto) {

        if (ObjectUtils.isEmpty(orderDeliveryDto)) {
            return null;
        }

        OrderDeliveryEntity orderDeliveryEntity = orderDeliveryDto.getOrderDeliveryEntity();

        // 会員情報の作成
        MemberInfoEntity memberInfoEntity = ApplicationContextUtility.getBean(MemberInfoEntity.class);

        // 画面入力値で上書き
        // 事業所名
        memberInfoEntity.setMemberInfoLastName(orderDeliveryEntity.getReceiverLastName());
        // 事業所名フリガナ
        memberInfoEntity.setMemberInfoLastKana(orderDeliveryEntity.getReceiverLastKana());
        // 代表者名
        memberInfoEntity.setRepresentativeName(orderDeliveryEntity.getReceiverFirstName());
        // 郵便番号
        memberInfoEntity.setMemberInfoZipCode(orderDeliveryEntity.getReceiverZipCode());
        // 会員住所-市区郡
        memberInfoEntity.setMemberInfoAddress1(orderDeliveryEntity.getReceiverAddress1());
        // 会員住所-町村・番地
        memberInfoEntity.setMemberInfoAddress2(orderDeliveryEntity.getReceiverAddress2());
        // 会員住所-それ以降の住所
        memberInfoEntity.setMemberInfoAddress3(orderDeliveryEntity.getReceiverAddress3());
        // 会員住所-方書1
        memberInfoEntity.setMemberInfoAddress4(orderDeliveryEntity.getReceiverAddress4());
        // 電話番号
        memberInfoEntity.setMemberInfoTel(orderDeliveryEntity.getReceiverTel());
        // 顧客区分
        String frontBusinessType = orderDeliveryDto.getBusinessType().getValue();
        if (HTypeFrontBusinessType.OTHER.getValue().equals(frontBusinessType)) {
            // その他の場合
            memberInfoEntity.setBusinessType(HTypeBusinessType.OTHERS);
        } else {
            memberInfoEntity.setBusinessType(EnumTypeUtil.getEnumFromValue(HTypeBusinessType.class, frontBusinessType));
        }
        // 確認書類
        if (HTypeFrontBusinessType.OTHER.getValue().equals(frontBusinessType)) {
            memberInfoEntity.setConfDocumentType(HTypeConfDocumentType.NOT_SET);
        } else {
            memberInfoEntity.setConfDocumentType(HTypeConfDocumentType.UNCONF);
        }
        // 顧客番号
        memberInfoEntity.setCustomerNo(memberInfoGetCutomerNoNextValLogic.execute());

        // サブシステム側との連携のため、予め決済代行IDに顧客番号を設定
        memberInfoEntity.setPaymentMemberId(memberInfoEntity.getCustomerNo().toString());
        // 決済代行会社カード保持種別 カード情報登録済 を設定
        memberInfoEntity.setPaymentCardRegistType(HTypeCardRegistType.REGISTERED);

        // 退会日
        memberInfoEntity.setSecessionYmd(dateUtility.getCurrentYmd());

        // 名簿区分
        memberInfoEntity.setMemberListType(HTypeMemberListType.OFFLINE_GENERAL_CUSTOMER);

        return memberInfoEntity;
    }

    /**
     * WEB-API連携 消費税率取得ロジッククラスレスポンスに変換
     *
     * @param taxRateMap WEB-API連携取得結果DTOクラスMAP
     * @return WEB-API連携 消費税率取得ロジッククラスレスポンス
     */
    public ConsumptionTaxRateResponse toConsumptionTaxRateResponse(Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> taxRateMap) {

        ConsumptionTaxRateResponse consumptionTaxRateResponse = new ConsumptionTaxRateResponse();
        Map<String, WebApiGetConsumptionTaxRateResponseDetailDtoResponse>
                        stringWebApiGetConsumptionTaxRateResponseDetailDtoResponseHashMap = new HashMap<>();

        if (MapUtils.isNotEmpty(taxRateMap)) {
            for (Map.Entry<String, WebApiGetConsumptionTaxRateResponseDetailDto> entry : taxRateMap.entrySet()) {
                WebApiGetConsumptionTaxRateResponseDetailDtoResponse
                                webApiGetConsumptionTaxRateResponseDetailDtoResponse =
                                new WebApiGetConsumptionTaxRateResponseDetailDtoResponse();
                webApiGetConsumptionTaxRateResponseDetailDtoResponse.setGoodsCode(entry.getValue().getGoodsCode());
                webApiGetConsumptionTaxRateResponseDetailDtoResponse.setTaxRate(entry.getValue().getTaxRate());

                stringWebApiGetConsumptionTaxRateResponseDetailDtoResponseHashMap.put(
                                entry.getKey(), webApiGetConsumptionTaxRateResponseDetailDtoResponse);
            }

            consumptionTaxRateResponse.setTaxRateMap(stringWebApiGetConsumptionTaxRateResponseDetailDtoResponseHashMap);
        }

        return consumptionTaxRateResponse;
    }

    /**
     * チェックメッセージDtoレスポンスに変換
     *
     * @param checkMessageDtoList チェックメッセージDtoリスト
     * @return チェックメッセージDtoレスポンス
     */
    public List<CheckMessageDtoResponse> toCheckMessageDtoListResponse(List<CheckMessageDto> checkMessageDtoList) {
        if (CollectionUtil.isEmpty(checkMessageDtoList)) {
            return new ArrayList<>();
        }

        List<CheckMessageDtoResponse> checkMessageDtoListResponse = new ArrayList<>();

        checkMessageDtoList.forEach(checkMessageDto -> {
            CheckMessageDtoResponse checkMessageDtoResponse = new CheckMessageDtoResponse();

            checkMessageDtoResponse.setMessageId(checkMessageDto.getMessageId());
            checkMessageDtoResponse.setArgs(List.of(checkMessageDto.getArgs()));
            checkMessageDtoResponse.setMessage(checkMessageDto.getMessage());
            checkMessageDtoResponse.setOrderConsecutiveNo(checkMessageDto.getOrderConsecutiveNo());
            checkMessageDtoResponse.setError(checkMessageDto.isError());

            checkMessageDtoListResponse.add(checkMessageDtoResponse);
        });

        return checkMessageDtoListResponse;
    }
}
