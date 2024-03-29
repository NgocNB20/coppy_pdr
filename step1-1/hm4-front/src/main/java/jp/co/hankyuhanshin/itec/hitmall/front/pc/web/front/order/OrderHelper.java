/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsCheckMapResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetQuantityDiscountResultResponseDetailDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetReserveResponseDetailDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetSaleCheckDetailResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardInfoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.BillPriceCalculateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.CardBrandResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.CartDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.CartGoodsDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.CartGoodsEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.CheckMessageDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.CheckMessageDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.ConsumptionTaxRateResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.ConvenienceResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryDetailsDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryImpossibleAreaResultDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryMethodDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryMethodEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryMethodRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryMethodSelectListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryMethodTypeCarriageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliverySpecialChargeAreaResultDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.GoodsDetailsDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.GoodsGroupImageEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.GoodsGroupImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.GoodsImageEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.GoodsImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderBeforePaymentRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderDeliveryDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderDeliveryInformationDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderDeliveryInformationRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderDeliveryNowDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderDeliveryRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderDependingOnReceiptGoodsDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderGetReserveMapResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderGetStockMapResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderGetStockRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderGoodsRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderInfoMasterDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderInfoMasterGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderMessageDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderPromotionInformationRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderQuantityDiscountRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderReserveDeliveryDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderScreenRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.ReceiverDateDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.ReceiverDateDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.SettlementDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.SettlementDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.SettlementMethodSelectListGetRequest;
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
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.SettlementMethodResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.AbstractWebApiResponseResultDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDestinationResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDestinationResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDiscountsResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDiscountsResultRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDiscountsResultResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPreShipmentOrderHistoryAggregateResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPreShipmentOrderHistoryResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetShipmentDateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetShipmentDateRequestDetailDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetShipmentDateResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetShipmentDateResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiOrderHistoryResponseGoodsInfoDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAccountingType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCardRegistType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCashDeliveryUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeConfDocumentType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCoolSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCreditPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeliveryCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeliveryMethodType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDentalMonopolySalesFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDirectDebitUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDisplayStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeEffectiveFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeFrontDisplayFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsClassType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeLandSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMailRequired;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberListType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMetalPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMonthlyPayUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeNoAntiSocialFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOnlineLoginAdvisability;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOrderCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReceiverDateDesignationFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendDirectMailFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendFaxPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodCommissionType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodPriceCommissionFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSexUnnecessaryAnswer;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeShortfallDisplayFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTaxRateType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTransferPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeUseConveni;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.OrderInfoMasterDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.OrderMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDeliveryNowDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDependingOnReceiptGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderReserveDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.delivery.DeliveryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.delivery.DeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.delivery.DeliveryImpossibleAreaResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.delivery.DeliveryMethodDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.delivery.DeliverySpecialChargeAreaResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.delivery.ReceiverDateDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.settlement.SettlementDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.settlement.SettlementDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.AbstractWebApiResponseResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetSaleCheckResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.member.WebApiGetDestinationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.member.WebApiGetDestinationResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetConsumptionTaxRateResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDeliveryInformationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDeliveryInformationResponseDetailDateInfoDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDeliveryInformationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDiscountsRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDiscountsResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDiscountsResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetPreShipmentOrderHistoryResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetPreShipmentOrderHistoryResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetQuantityDiscountResultResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetShipmentDateRequestDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetShipmentDateRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetShipmentDateResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetShipmentDateResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiOrderHistoryResponseGoodsInfoDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.conveni.ConvenienceEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.multipayment.CardBrandEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.delivery.DeliveryMethodTypeCarriageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.tax.TaxRateEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.OrderCommonModel;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 注文 Helper
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
     * コンストラクタ
     *
     * @param conversionUtility 変換ユーティリティクラス
     */
    @Autowired
    public OrderHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * お届け希望日DTO作成処理<br/>
     * お届け希望日DTOを作成し、配送DTOにセットする<br/>
     *
     * @param deliveryDtoList 配送DTOリスト
     * @param nonSelectFlag   指定なし選択肢有無(true..あり、false..なし)
     */
    public void createReceiverDateList(List<DeliveryDto> deliveryDtoList, boolean nonSelectFlag) {

        // 配送方法ごとに処理
        for (DeliveryDto dto : deliveryDtoList) {
            // リードタイム、選択可能日数を取得
            int leadTime = dto.getDeliveryDetailsDto().getDeliveryLeadTime();
            int selectDays = dto.getDeliveryDetailsDto().getPossibleSelectDays();

            // お届け日リスト作成
            ReceiverDateDto receiverDateDto = this.checkCreateReceiverDateList(leadTime, selectDays, nonSelectFlag,
                                                                               dto.getDeliveryDetailsDto()
                                                                                  .getDeliveryMethodSeq()
                                                                              );
            // 配送方法DTOにお届け日リストをセット
            dto.setReceiverDateDto(receiverDateDto);
        }

    }

    /**
     * お届け希望日DTO作成判定<br/>
     * 配送リードタイム、選択可能日数を元に、お届け希望日DTOを作成判定<br/>
     *
     * @param leadTime      配送リードタイム
     * @param selectDays    選択可能日数
     * @param nonSelectFlag 指定なし選択肢有無(true..あり、false..なし)
     * @return お届け希望日DTO
     */
    public ReceiverDateDto checkCreateReceiverDateList(int leadTime,
                                                       int selectDays,
                                                       boolean nonSelectFlag,
                                                       Integer deliveryMethodSeq) {

        ReceiverDateDto receiverDateDto = ApplicationContextUtility.getBean(ReceiverDateDto.class);

        // 最短お届け日の算出
        // 選択日数 1日、指定なし選択肢 なし で作成
        receiverDateDto.setShortestDeliveryDateToRegist(
                        createReceiverDateList(receiverDateDto, leadTime, 1, deliveryMethodSeq));

        // 選択可能日数が0か判定
        if (selectDays == 0) {
            receiverDateDto.setDateMap(null);
            receiverDateDto.setReceiverDateDesignationFlag(HTypeReceiverDateDesignationFlag.OFF);
            return receiverDateDto;
        } else {
            receiverDateDto.setReceiverDateDesignationFlag(HTypeReceiverDateDesignationFlag.ON);
        }

        Timestamp insertTime = createReceiverDateList(receiverDateDto, leadTime, selectDays, deliveryMethodSeq);

        // 日付Mapを作成し、DTOにセット
        receiverDateDto.setDateMap(this.createDateMap(insertTime, selectDays, nonSelectFlag, deliveryMethodSeq));

        return receiverDateDto;
    }

    /**
     * お届け希望日DTO作成処理<br/>
     * 配送リードタイム、選択可能日数を元に、お届け希望日DTOを作成する<br/>
     * <p>
     * <br/>【以下説明】 2015/07/10<br/>
     * 出荷予定日 : 現在日 + 配送リードタイム<br/>
     * （現在日から出荷予定日内に休業日の場合は、日付をずらす）<br/>
     * お届け希望日開始日 : 出荷予定日 + 配送準備日数 + 1<br/>
     * お届け希望日終了日 : 出荷予定日 + 配送準備日数 + 選択可能日数<br/>
     * （配送準備日数はあくまでも準備のため、希望時間帯に配送するには +1日必要）<br/>
     *
     * @param receiverDateDto お届け希望日DTO
     * @param leadTime        配送リードタイム
     * @param selectDays      選択可能日数
     * @return お届け希望日プルダウン開始日
     */
    public Timestamp createReceiverDateList(ReceiverDateDto receiverDateDto,
                                            int leadTime,
                                            int selectDays,
                                            Integer deliveryMethodSeq) {

        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // システム日付を取得
        Timestamp now = dateUtility.getCurrentDate();
        Timestamp leadTimeDate;
        // 即日出荷フラグ
        boolean sameDayShippingFlag;

        if (leadTime == 0) {
            // リードタイム 0 の場合は注文日が、休業日でないか判定する必要がある
            leadTimeDate = now;
            sameDayShippingFlag = true;
        } else {
            // 注文日はリードタイムに含めないため、翌日からの判定となる
            leadTimeDate = dateUtility.getAmountDayTimestamp(1, true, now);
            sameDayShippingFlag = false;
        }
        // 休業日を考慮した加算日数
        int leadTimeCnt = leadTime;

        while (true) {
            // PDR Migrate Customization from here
            //            int holiday = holidayDao.getCountByDate(leadTimeDate, deliveryMethodSeq);
            int holiday = 0;
            // PDR Migrate Customization to here
            if (holiday != 0) {
                // 注文日はリードタイムに含めない
                if (!leadTimeDate.equals(now)) {
                    leadTimeCnt++;
                }
            } else {
                leadTime--;

                if (leadTimeCalculationEnd(sameDayShippingFlag, leadTime)) {
                    break;
                }
            }
            leadTimeDate = dateUtility.getAmountDayTimestamp(1, true, leadTimeDate);
        }

        // システムプロパティ値から配送準備日数を取得
        String deliveryNumberOfDays = PropertiesUtil.getSystemPropertiesValue("delivery.number.days");
        // リードタイムに加算
        leadTimeCnt += Integer.parseInt(deliveryNumberOfDays);

        // 開始日(システム日付+リードタイム)を取得
        // お届け希望時間帯に配送するために 1日加算
        Timestamp insertTime = dateUtility.getAmountDayTimestamp(leadTimeCnt + 1, true, now);

        return insertTime;
    }

    /**
     * リードタイム計算終了判定メソッド<br/>
     * 即日配送 : リードタイム < 0 <br/>
     * その他 : リードタイム <= 0 <br/>
     * 即日配送の場合は、リードタイム 0のため考慮に入れる必要がある。<br/>
     *
     * @param sameDayShippingFlag 即日配送フラグ
     * @param leadTime            リードタイム
     * @return true:終了 / false:未終了
     */
    private boolean leadTimeCalculationEnd(boolean sameDayShippingFlag, int leadTime) {
        boolean calculationEndFlag = false;

        if (sameDayShippingFlag) {
            if (leadTime < 0) {
                calculationEndFlag = true;
            }
        } else {
            if (leadTime <= 0) {
                calculationEndFlag = true;
            }
        }
        return calculationEndFlag;
    }

    /**
     * 日付Mapを作成する<br/>
     * パラメータの指定日付~選択可能日数の日付分でMapを作成する<br/>
     * 選択可能日に配送不可日が存在する場合は配送不可であることを明示する<br/>
     *
     * @param insertTime        指定日付
     * @param selectDays        選択可能日数
     * @param nonSelectFlag     指定なし選択肢有無(true..あり、false..なし)
     * @param deliveryMethodSeq 配送方法SEQ
     * @return 日時Map(key = YYYYMMDD, value = YYYY / MM / DD)
     */
    private Map<String, String> createDateMap(Timestamp insertTime,
                                              int selectDays,
                                              boolean nonSelectFlag,
                                              Integer deliveryMethodSeq) {

        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        Map<String, String> map = new LinkedHashMap<>();

        // フラグがONの場合、「指定なし」をMapの先頭にセット
        if (nonSelectFlag) {
            map.put(ReceiverDateDto.NON_SELECT_KEY, ReceiverDateDto.NON_SELECT_VALUE);
        }

        // 選択可能日数分ループ
        for (int i = 0; i < selectDays; i++) {
            // 配送不可日判定
            // PDR Migrate Customization from here
            //            int deliveryImpossibleDay = deliveryImpossibleDayDao.getCountByDate(insertTime, deliveryMethodSeq);
            int deliveryImpossibleDay = 0;
            // PDR Migrate Customization to here
            if (deliveryImpossibleDay != 0) {
                // Map(YYYY/MM/DD,YYYY/MM/DD ×配送不可)を作成
                map.put(dateUtility.formatYmd(insertTime), ReceiverDateDto.getFormatMdWithWeek(insertTime) + " ×配送不可");
            } else {
                // Map(YYYY/MM/DD,YYYY/MM/DD)を作成
                map.put(dateUtility.formatYmd(insertTime), ReceiverDateDto.getFormatMdWithWeek(insertTime));
            }
            // 日付を1日進める
            insertTime = dateUtility.getAmountDayTimestamp(1, true, insertTime);
        }
        return map;
    }

    /**
     * 注文画面用注文登録リクエストに変換
     *
     * @param orderCommonModel   注文Model
     * @param confirmModel 注文内容確認画面Model
     * @param customerNo   顧客番号
     * @param commonInfo   共通情報
     * @return 注文画面用注文登録リクエスト
     */
    public OrderScreenRegistRequest toOrderScreenRegistRequest(OrderCommonModel orderCommonModel,
                                                               ConfirmModel confirmModel,
                                                               Integer customerNo,
                                                               CommonInfo commonInfo) {

        OrderScreenRegistRequest orderScreenRegistRequest = new OrderScreenRegistRequest();

        orderScreenRegistRequest.setReceiveOrderDto(toReceiveOrderDtoJson(orderCommonModel.getReceiveOrderDto()));
        orderScreenRegistRequest.setReceiveOrderDtoList(
                        toReceiveOrderDtoJsonList(confirmModel.getReceiveOrderDtoList()));
        orderScreenRegistRequest.setMemberInfoId(commonInfo.getCommonInfoUser().getMemberInfoId());
        orderScreenRegistRequest.setMemberInfoSeq(commonInfo.getCommonInfoUser().getMemberInfoSeq());
        orderScreenRegistRequest.setUserAgent(commonInfo.getCommonInfoBase().getUserAgent());
        orderScreenRegistRequest.setDeviceType(EnumTypeUtil.getValue(commonInfo.getCommonInfoBase().getDeviceType()));
        orderScreenRegistRequest.setAdministratorLastName(
                        commonInfo.getCommonInfoAdministrator().getAdministratorLastName());
        orderScreenRegistRequest.setAdministratorFirstName(
                        commonInfo.getCommonInfoAdministrator().getAdministratorFirstName());
        orderScreenRegistRequest.setAccessUid(commonInfo.getCommonInfoBase().getAccessUid());
        orderScreenRegistRequest.setSessionId(commonInfo.getCommonInfoBase().getSessionId());
        orderScreenRegistRequest.setCustomerNo(customerNo);

        return orderScreenRegistRequest;
    }

    /**
     * 受注DtoクラスJSONに変換
     *
     * @param receiveOrderDto 受注Dtoクラス
     * @return 受注DtoクラスJSON
     */
    public String toReceiveOrderDtoJson(ReceiveOrderDto receiveOrderDto) {
        if (ObjectUtils.isEmpty(receiveOrderDto)) {
            return null;
        }
        return conversionUtility.toJson(receiveOrderDto);
    }

    /**
     * 受注DtoクラスJSONリストに変換
     *
     * @param receiveOrderDtoList 受注Dtoクラスリスト
     * @return 受注DtoクラスJSONリスト
     */
    public List<String> toReceiveOrderDtoJsonList(List<ReceiveOrderDto> receiveOrderDtoList) {
        if (CollectionUtils.isEmpty(receiveOrderDtoList)) {
            return new ArrayList<>();
        }

        List<String> receiveOrderDtoJsonList = new ArrayList<>();

        for (ReceiveOrderDto receiveOrderDto : receiveOrderDtoList) {
            String receiveOrderDtoJson = toReceiveOrderDtoJson(receiveOrderDto);

            receiveOrderDtoJsonList.add(receiveOrderDtoJson);
        }

        return receiveOrderDtoJsonList;
    }

    /**
     * 注文情報マスタ取得リクエストに変換
     *
     * @param cartDto カートDtoクラス
     * @return 注文情報マスタ取得リクエスト
     */
    public OrderInfoMasterGetRequest toOrderInfoMasterGetRequest(CartDto cartDto) {

        OrderInfoMasterGetRequest orderInfoMasterGetRequest = new OrderInfoMasterGetRequest();

        orderInfoMasterGetRequest.setCartDto(toCartDtoOrderRequest(cartDto));

        return orderInfoMasterGetRequest;
    }

    /**
     * カートDtoリクエストに変換
     *
     * @param cartDto カートDtoクラス
     * @return カートDtoリクエスト
     */
    private CartDtoRequest toCartDtoOrderRequest(CartDto cartDto) {
        CartDtoRequest cartDtoRequest = new CartDtoRequest();

        cartDtoRequest.setGoodsTotalCount(cartDto.getGoodsTotalCount());
        cartDtoRequest.setGoodsTotalPrice(cartDto.getGoodsTotalPrice());
        cartDtoRequest.setGoodsTotalPriceInTax(cartDto.getGoodsTotalPriceInTax());
        cartDtoRequest.setCartGoodsDtoList(toCartGoodsDtoOrderList(cartDto.getCartGoodsDtoList()));
        if (cartDto.getSettlementMethodType() != null) {
            cartDtoRequest.setSettlementMethodType(cartDto.getSettlementMethodType().getValue());
        }
        cartDtoRequest.setDiscountsResponseDetailMap(
                        toDiscountsResponseDetailMapOrder(cartDto.getDiscountsResponseDetailMap()));
        cartDtoRequest.setConsumptionTaxRateMap(toConsumptionTaxRateMapOrder(cartDto.getConsumptionTaxRateMap()));

        return cartDtoRequest;
    }

    /**
     * 注文情報マスタDtoに変換
     *
     * @param orderInfoMasterDtoResponse 注文情報マスタDtoレスポンス
     * @return 注文情報マスタDto
     */
    public OrderInfoMasterDto toOrderInfoMasterDto(OrderInfoMasterDtoResponse orderInfoMasterDtoResponse) {

        if (ObjectUtils.isEmpty(orderInfoMasterDtoResponse)) {
            return null;
        }

        OrderInfoMasterDto masterDto = new OrderInfoMasterDto();

        if (MapUtils.isNotEmpty(orderInfoMasterDtoResponse.getGoodsMaster())) {
            masterDto.setGoodsMaster(toGoodsMasterMap(orderInfoMasterDtoResponse.getGoodsMaster()));
        }
        if (MapUtils.isNotEmpty(orderInfoMasterDtoResponse.getTaxRateMaster())) {
            masterDto.setTaxRateMaster(toTaxRateMasterMap(orderInfoMasterDtoResponse.getTaxRateMaster()));
        }
        if (MapUtils.isNotEmpty(orderInfoMasterDtoResponse.getDeliveryMethodMaster())) {
            masterDto.setDeliveryMethodMaster(
                            toDeliveryMethodMasterMap(orderInfoMasterDtoResponse.getDeliveryMethodMaster()));
        }

        return masterDto;
    }

    /**
     * カート商品Dtoリストに変換
     *
     * @param cartGoodsDtoList カート商品Dtoリクエストリスト
     * @return カート商品Dtoリスト
     */
    private List<CartGoodsDtoRequest> toCartGoodsDtoOrderList(List<CartGoodsDto> cartGoodsDtoList) {

        if (CollectionUtils.isEmpty(cartGoodsDtoList)) {
            return new ArrayList<>();
        }

        List<CartGoodsDtoRequest> CartGoodsDtoRequestList = new ArrayList<>();

        cartGoodsDtoList.forEach(cartGoodsDto -> {
            CartGoodsDtoRequest cartGoodsDtoRequest = new CartGoodsDtoRequest();

            cartGoodsDtoRequest.setCartGoodsEntity(toCartGoodsEntityOrderRequest(cartGoodsDto.getCartGoodsEntity()));
            cartGoodsDtoRequest.setGoodsDetailsDto(toGoodsDetailsDtoOrderRequest(cartGoodsDto.getGoodsDetailsDto()));
            cartGoodsDtoRequest.setGoodsPriceSubtotal(cartGoodsDto.getGoodsPriceSubtotal());
            cartGoodsDtoRequest.setGoodsPriceInTaxSubtotal(cartGoodsDto.getGoodsPriceInTaxSubtotal());

            CartGoodsDtoRequestList.add(cartGoodsDtoRequest);
        });

        return CartGoodsDtoRequestList;
    }

    /**
     * カート商品に変換
     *
     * @param cartGoodsEntity カート商品エンティティリクエスト
     * @return カート商品
     */
    private CartGoodsEntityRequest toCartGoodsEntityOrderRequest(CartGoodsEntity cartGoodsEntity) {

        if (ObjectUtils.isEmpty(cartGoodsEntity)) {
            return null;
        }

        CartGoodsEntityRequest cartGoodsEntityRequest = new CartGoodsEntityRequest();

        cartGoodsEntityRequest.setCartSeq(cartGoodsEntity.getCartSeq());
        cartGoodsEntityRequest.setAccessUid(cartGoodsEntity.getAccessUid());
        cartGoodsEntityRequest.setMemberInfoSeq(cartGoodsEntity.getMemberInfoSeq());
        cartGoodsEntityRequest.setGoodsSeq(cartGoodsEntity.getGoodsSeq());
        cartGoodsEntityRequest.setCartGoodsCount(cartGoodsEntity.getCartGoodsCount());
        cartGoodsEntityRequest.setRegistTime(conversionUtility.toTimeStamp(cartGoodsEntity.getRegistTime()));
        cartGoodsEntityRequest.setUpdateTime(conversionUtility.toTimeStamp(cartGoodsEntity.getUpdateTime()));
        // 2023-renew No14 from here
        cartGoodsEntityRequest.setReserveFlag(cartGoodsEntity.getReserveFlag().getValue());
        cartGoodsEntityRequest.setReserveDeliveryDate(
                        conversionUtility.toTimeStamp(cartGoodsEntity.getReserveDeliveryDate()));
        // 2023-renew No14 to here

        return cartGoodsEntityRequest;
    }

    /**
     * 商品詳細Dtoに変換
     *
     * @param goodsDetailsDto 商品詳細Dtoクラスリクエスト
     * @return 商品詳細Dto
     */
    private GoodsDetailsDtoRequest toGoodsDetailsDtoOrderRequest(GoodsDetailsDto goodsDetailsDto) {

        if (ObjectUtils.isEmpty(goodsDetailsDto)) {
            return null;
        }

        GoodsDetailsDtoRequest goodsDetailsDtoRequest = new GoodsDetailsDtoRequest();

        goodsDetailsDtoRequest.setGoodsSeq(goodsDetailsDto.getGoodsSeq());
        goodsDetailsDtoRequest.setGoodsGroupSeq(goodsDetailsDto.getGoodsGroupSeq());
        goodsDetailsDtoRequest.setVersionNo(goodsDetailsDto.getVersionNo());
        goodsDetailsDtoRequest.setRegistTime(conversionUtility.toTimeStamp(goodsDetailsDto.getRegistTime()));
        goodsDetailsDtoRequest.setUpdateTime(conversionUtility.toTimeStamp(goodsDetailsDto.getUpdateTime()));
        goodsDetailsDtoRequest.setGoodsCode(goodsDetailsDto.getGoodsCode());
        goodsDetailsDtoRequest.setGoodsGroupMaxPrice(goodsDetailsDto.getGoodsGroupMaxPrice());
        goodsDetailsDtoRequest.setGoodsGroupMinPrice(goodsDetailsDto.getGoodsGroupMinPrice());
        goodsDetailsDtoRequest.setPreDiscountMinPrice(goodsDetailsDto.getPreDiscountMinPrice());
        goodsDetailsDtoRequest.setPreDiscountMaxPrice(goodsDetailsDto.getPreDiscountMaxPrice());
        goodsDetailsDtoRequest.setTaxRate(goodsDetailsDto.getTaxRate());
        goodsDetailsDtoRequest.setGoodsPriceInTax(goodsDetailsDto.getGoodsPriceInTax());
        goodsDetailsDtoRequest.setGoodsPrice(goodsDetailsDto.getGoodsPrice());
        goodsDetailsDtoRequest.setDeliveryType(goodsDetailsDto.getDeliveryType());
        goodsDetailsDtoRequest.setSaleStartTime(conversionUtility.toTimeStamp(goodsDetailsDto.getSaleStartTimePC()));
        goodsDetailsDtoRequest.setSaleEndTime(conversionUtility.toTimeStamp(goodsDetailsDto.getSaleEndTimePC()));
        goodsDetailsDtoRequest.setPurchasedMax(goodsDetailsDto.getPurchasedMax());
        goodsDetailsDtoRequest.setOrderDisplay(goodsDetailsDto.getOrderDisplay());
        goodsDetailsDtoRequest.setUnitValue1(goodsDetailsDto.getUnitValue1());
        goodsDetailsDtoRequest.setUnitValue2(goodsDetailsDto.getUnitValue2());
        goodsDetailsDtoRequest.setPreDiscountPrice(goodsDetailsDto.getPreDiscountPrice());
        goodsDetailsDtoRequest.setPreDisCountPriceInTax(goodsDetailsDto.getPreDisCountPriceInTax());
        goodsDetailsDtoRequest.setJanCode(goodsDetailsDto.getJanCode());
        goodsDetailsDtoRequest.setCatalogCode(goodsDetailsDto.getCatalogCode());
        goodsDetailsDtoRequest.setSalesPossibleStock(goodsDetailsDto.getSalesPossibleStock());
        goodsDetailsDtoRequest.setRealStock(goodsDetailsDto.getRealStock());
        goodsDetailsDtoRequest.setOrderReserveStock(goodsDetailsDto.getOrderReserveStock());
        goodsDetailsDtoRequest.setRemainderFewStock(goodsDetailsDto.getRemainderFewStock());
        goodsDetailsDtoRequest.setOrderPointStock(goodsDetailsDto.getOrderPointStock());
        goodsDetailsDtoRequest.setSafetyStock(goodsDetailsDto.getSafetyStock());
        goodsDetailsDtoRequest.setGoodsGroupCode(goodsDetailsDto.getGoodsGroupCode());
        goodsDetailsDtoRequest.setWhatsnewDate(conversionUtility.toTimeStamp(goodsDetailsDto.getWhatsnewDate()));
        goodsDetailsDtoRequest.setOpenStartTime(conversionUtility.toTimeStamp(goodsDetailsDto.getOpenStartTimePC()));
        goodsDetailsDtoRequest.setOpenEndTime(conversionUtility.toTimeStamp(goodsDetailsDto.getOpenEndTimePC()));
        goodsDetailsDtoRequest.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
        goodsDetailsDtoRequest.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
        goodsDetailsDtoRequest.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
        goodsDetailsDtoRequest.setGoodsPreDiscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());
        goodsDetailsDtoRequest.setGoodsGroupImageEntityList(
                        toGoodsGroupImageEntityRequestOrderList(goodsDetailsDto.getGoodsGroupImageEntityList()));
        goodsDetailsDtoRequest.setMetaDescription(goodsDetailsDto.getMetaDescription());
        goodsDetailsDtoRequest.setGoodsNote1(goodsDetailsDto.getGoodsNote1());
        goodsDetailsDtoRequest.setGoodsNote2(goodsDetailsDto.getGoodsNote2());
        goodsDetailsDtoRequest.setGoodsNote3(goodsDetailsDto.getGoodsNote3());
        goodsDetailsDtoRequest.setGoodsNote4(goodsDetailsDto.getGoodsNote4());
        goodsDetailsDtoRequest.setGoodsNote5(goodsDetailsDto.getGoodsNote5());
        goodsDetailsDtoRequest.setGoodsNote6(goodsDetailsDto.getGoodsNote6());
        goodsDetailsDtoRequest.setGoodsNote7(goodsDetailsDto.getGoodsNote7());
        goodsDetailsDtoRequest.setGoodsNote8(goodsDetailsDto.getGoodsNote8());
        goodsDetailsDtoRequest.setGoodsNote9(goodsDetailsDto.getGoodsNote9());
        goodsDetailsDtoRequest.setGoodsNote10(goodsDetailsDto.getGoodsNote10());
        goodsDetailsDtoRequest.setOrderSetting1(goodsDetailsDto.getOrderSetting1());
        goodsDetailsDtoRequest.setOrderSetting2(goodsDetailsDto.getOrderSetting2());
        goodsDetailsDtoRequest.setOrderSetting3(goodsDetailsDto.getOrderSetting3());
        goodsDetailsDtoRequest.setOrderSetting4(goodsDetailsDto.getOrderSetting4());
        goodsDetailsDtoRequest.setOrderSetting5(goodsDetailsDto.getOrderSetting5());
        goodsDetailsDtoRequest.setOrderSetting6(goodsDetailsDto.getOrderSetting6());
        goodsDetailsDtoRequest.setOrderSetting7(goodsDetailsDto.getOrderSetting7());
        goodsDetailsDtoRequest.setOrderSetting8(goodsDetailsDto.getOrderSetting8());
        goodsDetailsDtoRequest.setOrderSetting9(goodsDetailsDto.getOrderSetting9());
        goodsDetailsDtoRequest.setOrderSetting10(goodsDetailsDto.getOrderSetting10());
        goodsDetailsDtoRequest.setUnitImage(toUnitImageOrder(goodsDetailsDto.getUnitImage()));
        goodsDetailsDtoRequest.setGoodsOptionDisplayName(goodsDetailsDto.getGoodsOptionDisplayName());
        goodsDetailsDtoRequest.setCoolSendFrom(goodsDetailsDto.getCoolSendFrom());
        goodsDetailsDtoRequest.setCoolSendTo(goodsDetailsDto.getCoolSendTo());
        goodsDetailsDtoRequest.setTax(goodsDetailsDto.getTax());
        goodsDetailsDtoRequest.setUnit(goodsDetailsDto.getUnit());
        goodsDetailsDtoRequest.setGoodsManagementCode(goodsDetailsDto.getGoodsManagementCode());
        goodsDetailsDtoRequest.setGoodsDivisionCode(goodsDetailsDto.getGoodsDivisionCode());
        goodsDetailsDtoRequest.setGoodsCategory1(goodsDetailsDto.getGoodsCategory1());
        goodsDetailsDtoRequest.setGoodsCategory2(goodsDetailsDto.getGoodsCategory2());
        goodsDetailsDtoRequest.setGoodsCategory3(goodsDetailsDto.getGoodsCategory3());
        goodsDetailsDtoRequest.setSaleYesNo(goodsDetailsDto.getSaleYesNo());
        goodsDetailsDtoRequest.setSaleNgMessage(goodsDetailsDto.getSaleNgMessage());
        goodsDetailsDtoRequest.setDeliveryYesNo(goodsDetailsDto.getDeliveryYesNo());

        if (goodsDetailsDto.getSaleStatusPC() != null) {
            goodsDetailsDtoRequest.setSaleStatus(goodsDetailsDto.getSaleStatusPC().getValue());
        }
        if (goodsDetailsDto.getStockStatusPc() != null) {
            goodsDetailsDtoRequest.setStockStatus(goodsDetailsDto.getStockStatusPc().getValue());
        }
        if (goodsDetailsDto.getGoodsTaxType() != null) {
            goodsDetailsDtoRequest.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType().getValue());
        }
        if (goodsDetailsDto.getAlcoholFlag() != null) {
            goodsDetailsDtoRequest.setAlcoholFlag(goodsDetailsDto.getAlcoholFlag().getValue());
        }
        if (goodsDetailsDto.getUnitManagementFlag() != null) {
            goodsDetailsDtoRequest.setUnitManagementFlag(goodsDetailsDto.getUnitManagementFlag().getValue());
        }
        if (goodsDetailsDto.getStockManagementFlag() != null) {
            goodsDetailsDtoRequest.setStockManagementFlag(goodsDetailsDto.getStockManagementFlag().getValue());
        }
        if (goodsDetailsDto.getIndividualDeliveryType() != null) {
            goodsDetailsDtoRequest.setIndividualDeliveryType(goodsDetailsDto.getIndividualDeliveryType().getValue());
        }
        if (goodsDetailsDto.getFreeDeliveryFlag() != null) {
            goodsDetailsDtoRequest.setFreeDeliveryFlag(goodsDetailsDto.getFreeDeliveryFlag().getValue());
        }
        if (goodsDetailsDto.getGoodsOpenStatusPC() != null) {
            goodsDetailsDtoRequest.setGoodsOpenStatus(goodsDetailsDto.getGoodsOpenStatusPC().getValue());
        }
        if (goodsDetailsDto.getSnsLinkFlag() != null) {
            goodsDetailsDtoRequest.setSnsLinkFlag(goodsDetailsDto.getSnsLinkFlag().getValue());
        }
        if (goodsDetailsDto.getGoodsClassType() != null) {
            goodsDetailsDtoRequest.setGoodsClassType(goodsDetailsDto.getGoodsClassType().getValue());
        }
        if (goodsDetailsDto.getDentalMonopolySalesFlg() != null) {
            goodsDetailsDtoRequest.setDentalMonopolySalesFlg(goodsDetailsDto.getDentalMonopolySalesFlg().getValue());
        }
        if (goodsDetailsDto.getSaleIconFlag() != null) {
            goodsDetailsDtoRequest.setSaleIconFlag(goodsDetailsDto.getSaleIconFlag().getValue());
        }
        if (goodsDetailsDto.getReserveIconFlag() != null) {
            goodsDetailsDtoRequest.setReserveIconFlag(goodsDetailsDto.getReserveIconFlag().getValue());
        }

        if (goodsDetailsDto.getNewIconFlag() != null) {
            goodsDetailsDtoRequest.setNewIconFlag(goodsDetailsDto.getNewIconFlag().getValue());
        }
        if (goodsDetailsDto.getLandSendFlag() != null) {
            goodsDetailsDtoRequest.setLandSendFlag(goodsDetailsDto.getLandSendFlag().getValue());
        }
        if (goodsDetailsDto.getCoolSendFlag() != null) {
            goodsDetailsDtoRequest.setCoolSendFlag(goodsDetailsDto.getCoolSendFlag().getValue());
        }
        if (goodsDetailsDto.getReserveFlag() != null) {
            goodsDetailsDtoRequest.setReserveFlag(goodsDetailsDto.getReserveFlag().getValue());
        }
        if (goodsDetailsDto.getPriceMarkDispFlag() != null) {
            goodsDetailsDtoRequest.setPriceMarkDispFlag(goodsDetailsDto.getPriceMarkDispFlag().getValue());
        }
        if (goodsDetailsDto.getSalePriceMarkDispFlag() != null) {
            goodsDetailsDtoRequest.setSalePriceMarkDispFlag(goodsDetailsDto.getSalePriceMarkDispFlag().getValue());
        }
        if (goodsDetailsDto.getSalePriceIntegrityFlag() != null) {
            goodsDetailsDtoRequest.setSalePriceIntegrityFlag(goodsDetailsDto.getSalePriceIntegrityFlag().getValue());
        }
        if (goodsDetailsDto.getEmotionPriceType() != null) {
            goodsDetailsDtoRequest.setEmotionPriceType(goodsDetailsDto.getEmotionPriceType().getValue());
        }

        return goodsDetailsDtoRequest;
    }

    /**
     * 商品グループ画像リストに変換
     *
     * @param goodsGroupImageEntityList 商品グループ画像リクエストリスト
     * @return 商品グループ画像リスト
     */
    private List<GoodsGroupImageEntityRequest> toGoodsGroupImageEntityRequestOrderList(List<GoodsGroupImageEntity> goodsGroupImageEntityList) {

        if (CollectionUtils.isEmpty(goodsGroupImageEntityList)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntityRequest> goodsGroupImageEntityRequestList = new ArrayList<>();

        goodsGroupImageEntityList.forEach(goodsGroupImageEntity -> {
            GoodsGroupImageEntityRequest goodsGroupImageEntityRequest = new GoodsGroupImageEntityRequest();

            goodsGroupImageEntityRequest.setGoodsGroupSeq(goodsGroupImageEntity.getGoodsGroupSeq());
            goodsGroupImageEntityRequest.setImageTypeVersionNo(goodsGroupImageEntity.getImageTypeVersionNo());
            goodsGroupImageEntityRequest.setImageFileName(goodsGroupImageEntity.getImageFileName());
            goodsGroupImageEntityRequest.setRegistTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntity.getRegistTime()));
            goodsGroupImageEntityRequest.setUpdateTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntity.getUpdateTime()));

            goodsGroupImageEntityRequestList.add(goodsGroupImageEntityRequest);
        });

        return goodsGroupImageEntityRequestList;
    }

    /**
     * 商品画像に変換
     *
     * @param goodsImageEntity 商品グループ画像リクエスト
     * @return 商品画像
     */
    private GoodsImageEntityRequest toUnitImageOrder(GoodsImageEntity goodsImageEntity) {

        if (ObjectUtils.isEmpty(goodsImageEntity)) {
            return null;
        }

        GoodsImageEntityRequest goodsImageEntityRequest = new GoodsImageEntityRequest();

        goodsImageEntityRequest.setGoodsGroupSeq(goodsImageEntity.getGoodsGroupSeq());
        goodsImageEntityRequest.setGoodsSeq(goodsImageEntity.getGoodsSeq());
        goodsImageEntityRequest.setImageFileName(goodsImageEntity.getImageFileName());
        if (goodsImageEntity.getDisplayFlag() != null) {
            goodsImageEntityRequest.setDisplayFlag(goodsImageEntity.getDisplayFlag().getValue());
        }
        goodsImageEntityRequest.setRegistTime(conversionUtility.toTimeStamp(goodsImageEntity.getRegistTime()));
        goodsImageEntityRequest.setUpdateTime(conversionUtility.toTimeStamp(goodsImageEntity.getUpdateTime()));
        goodsImageEntityRequest.setTmpFilePath(goodsImageEntity.getTmpFilePath());

        return goodsImageEntityRequest;
    }

    /**
     * 割引適用結果取得 詳細情報Mapに変換
     *
     * @param discountsResponseDetailDtoMap 割引適用結果MAP
     * @return 割引適用結果取得 詳細情報Map
     */
    private Map<String, WebApiGetDiscountsResponseDetailDtoRequest> toDiscountsResponseDetailMapOrder(Map<String, WebApiGetDiscountsResponseDetailDto> discountsResponseDetailDtoMap) {

        if (MapUtils.isEmpty(discountsResponseDetailDtoMap)) {
            return new HashMap<>();
        }

        Map<String, WebApiGetDiscountsResponseDetailDtoRequest> discountsResponseDetailMapRequest = new HashMap<>();

        for (Map.Entry<String, WebApiGetDiscountsResponseDetailDto> entry : discountsResponseDetailDtoMap.entrySet()) {

            WebApiGetDiscountsResponseDetailDto webApiGetDiscountsResponseDetailDto = entry.getValue();
            WebApiGetDiscountsResponseDetailDtoRequest webApiGetDiscountsResponseDetailDtoRequest =
                            new WebApiGetDiscountsResponseDetailDtoRequest();

            webApiGetDiscountsResponseDetailDtoRequest.setGoodsCode(webApiGetDiscountsResponseDetailDto.getGoodsCode());
            webApiGetDiscountsResponseDetailDtoRequest.setSaleType(webApiGetDiscountsResponseDetailDto.getSaleType());
            webApiGetDiscountsResponseDetailDtoRequest.setSaleGroupCode(
                            webApiGetDiscountsResponseDetailDto.getSaleGroupCode());
            webApiGetDiscountsResponseDetailDtoRequest.setSalePrice(webApiGetDiscountsResponseDetailDto.getSalePrice());
            webApiGetDiscountsResponseDetailDtoRequest.setQuantity(webApiGetDiscountsResponseDetailDto.getQuantity());
            webApiGetDiscountsResponseDetailDtoRequest.setSaleCode(webApiGetDiscountsResponseDetailDto.getSaleCode());
            webApiGetDiscountsResponseDetailDtoRequest.setNote(webApiGetDiscountsResponseDetailDto.getNote());
            webApiGetDiscountsResponseDetailDtoRequest.setHints(webApiGetDiscountsResponseDetailDto.getHints());
            webApiGetDiscountsResponseDetailDtoRequest.setOrderDisplay(
                            webApiGetDiscountsResponseDetailDto.getOrderDisplay());

            discountsResponseDetailMapRequest.put(entry.getKey(), webApiGetDiscountsResponseDetailDtoRequest);
        }

        return discountsResponseDetailMapRequest;
    }

    /**
     * 消費税率取得 詳細情報Mapに変換
     *
     * @param consumptionTaxRateResponseDetailDtoMap 割引適用結果MAP
     * @return 消費税率取得 詳細情報Map
     */
    private Map<String, WebApiGetConsumptionTaxRateResponseDetailDtoRequest> toConsumptionTaxRateMapOrder(Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> consumptionTaxRateResponseDetailDtoMap) {

        if (MapUtils.isEmpty(consumptionTaxRateResponseDetailDtoMap)) {
            return new HashMap<>();
        }

        Map<String, WebApiGetConsumptionTaxRateResponseDetailDtoRequest> consumptionTaxRateMap = new HashMap<>();

        for (Map.Entry<String, WebApiGetConsumptionTaxRateResponseDetailDto> entry : consumptionTaxRateResponseDetailDtoMap.entrySet()) {

            WebApiGetConsumptionTaxRateResponseDetailDto webApiGetConsumptionTaxRateResponseDetailDto =
                            entry.getValue();
            WebApiGetConsumptionTaxRateResponseDetailDtoRequest webApiGetConsumptionTaxRateResponseDetailDtoRequest =
                            new WebApiGetConsumptionTaxRateResponseDetailDtoRequest();

            webApiGetConsumptionTaxRateResponseDetailDtoRequest.setGoodsCode(
                            webApiGetConsumptionTaxRateResponseDetailDto.getGoodsCode());
            webApiGetConsumptionTaxRateResponseDetailDtoRequest.setTaxRate(
                            webApiGetConsumptionTaxRateResponseDetailDto.getTaxRate());

            consumptionTaxRateMap.put(entry.getKey(), webApiGetConsumptionTaxRateResponseDetailDtoRequest);
        }

        return consumptionTaxRateMap;
    }

    /**
     * 商品マスタマップレスポンスに変換
     *
     * @param goodsDetailsDtoResponseMap 商品マスタマップ
     * @return 商品マスタマップレスポンス
     */
    private Map<Integer, GoodsDetailsDto> toGoodsMasterMap(Map<String, GoodsDetailsDtoResponse> goodsDetailsDtoResponseMap) {

        if (MapUtils.isEmpty(goodsDetailsDtoResponseMap)) {
            return new HashMap<>();
        }

        Map<Integer, GoodsDetailsDto> goodsMaster = new HashMap<>();

        for (Map.Entry<String, GoodsDetailsDtoResponse> entry : goodsDetailsDtoResponseMap.entrySet()) {

            GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();
            GoodsDetailsDtoResponse goodsDetailsDtoResponse = entry.getValue();

            goodsDetailsDto.setGoodsSeq(goodsDetailsDtoResponse.getGoodsSeq());
            goodsDetailsDto.setGoodsGroupSeq(goodsDetailsDtoResponse.getGoodsGroupSeq());
            goodsDetailsDto.setVersionNo(goodsDetailsDtoResponse.getVersionNo());
            goodsDetailsDto.setRegistTime(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getRegistTime()));
            goodsDetailsDto.setUpdateTime(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getUpdateTime()));
            goodsDetailsDto.setGoodsCode(goodsDetailsDtoResponse.getGoodsCode());
            goodsDetailsDto.setGoodsGroupMaxPrice(goodsDetailsDtoResponse.getGoodsGroupMaxPrice());
            goodsDetailsDto.setGoodsGroupMinPrice(goodsDetailsDtoResponse.getGoodsGroupMinPrice());
            goodsDetailsDto.setPreDiscountMinPrice(goodsDetailsDtoResponse.getPreDiscountMinPrice());
            goodsDetailsDto.setPreDiscountMaxPrice(goodsDetailsDtoResponse.getPreDiscountMaxPrice());
            goodsDetailsDto.setTaxRate(goodsDetailsDtoResponse.getTaxRate());
            goodsDetailsDto.setGoodsPriceInTax(goodsDetailsDtoResponse.getGoodsPriceInTax());
            goodsDetailsDto.setGoodsPrice(goodsDetailsDtoResponse.getGoodsPrice());
            goodsDetailsDto.setDeliveryType(goodsDetailsDtoResponse.getDeliveryType());
            goodsDetailsDto.setSaleStartTimePC(
                            conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getSaleStartTime()));
            goodsDetailsDto.setSaleEndTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getSaleEndTime()));
            goodsDetailsDto.setPurchasedMax(goodsDetailsDtoResponse.getPurchasedMax());
            goodsDetailsDto.setOrderDisplay(goodsDetailsDtoResponse.getOrderDisplay());
            goodsDetailsDto.setUnitValue1(goodsDetailsDtoResponse.getUnitValue1());
            goodsDetailsDto.setUnitValue2(goodsDetailsDtoResponse.getUnitValue2());
            goodsDetailsDto.setPreDiscountPrice(goodsDetailsDtoResponse.getPreDiscountPrice());
            goodsDetailsDto.setPreDisCountPriceInTax(goodsDetailsDtoResponse.getPreDisCountPriceInTax());
            goodsDetailsDto.setJanCode(goodsDetailsDtoResponse.getJanCode());
            goodsDetailsDto.setCatalogCode(goodsDetailsDtoResponse.getCatalogCode());
            goodsDetailsDto.setSalesPossibleStock(goodsDetailsDtoResponse.getSalesPossibleStock());
            goodsDetailsDto.setRealStock(goodsDetailsDtoResponse.getRealStock());
            goodsDetailsDto.setOrderReserveStock(goodsDetailsDtoResponse.getOrderReserveStock());
            goodsDetailsDto.setRemainderFewStock(goodsDetailsDtoResponse.getRemainderFewStock());
            goodsDetailsDto.setOrderPointStock(goodsDetailsDtoResponse.getOrderPointStock());
            goodsDetailsDto.setSafetyStock(goodsDetailsDtoResponse.getSafetyStock());
            goodsDetailsDto.setGoodsGroupCode(goodsDetailsDtoResponse.getGoodsGroupCode());
            goodsDetailsDto.setWhatsnewDate(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getWhatsnewDate()));
            goodsDetailsDto.setOpenStartTimePC(
                            conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getOpenStartTime()));
            goodsDetailsDto.setOpenEndTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getOpenEndTime()));
            goodsDetailsDto.setGoodsGroupName(goodsDetailsDtoResponse.getGoodsGroupName());
            goodsDetailsDto.setUnitTitle1(goodsDetailsDtoResponse.getUnitTitle1());
            goodsDetailsDto.setUnitTitle2(goodsDetailsDtoResponse.getUnitTitle2());
            goodsDetailsDto.setGoodsPreDiscountPrice(goodsDetailsDtoResponse.getGoodsPreDiscountPrice());
            goodsDetailsDto.setGoodsGroupImageEntityList(
                            toGoodsGroupImageEntityList(goodsDetailsDtoResponse.getGoodsGroupImageEntityList()));
            goodsDetailsDto.setMetaDescription(goodsDetailsDtoResponse.getMetaDescription());
            goodsDetailsDto.setGoodsNote1(goodsDetailsDtoResponse.getGoodsNote1());
            goodsDetailsDto.setGoodsNote2(goodsDetailsDtoResponse.getGoodsNote2());
            goodsDetailsDto.setGoodsNote3(goodsDetailsDtoResponse.getGoodsNote3());
            goodsDetailsDto.setGoodsNote4(goodsDetailsDtoResponse.getGoodsNote4());
            goodsDetailsDto.setGoodsNote5(goodsDetailsDtoResponse.getGoodsNote5());
            goodsDetailsDto.setGoodsNote6(goodsDetailsDtoResponse.getGoodsNote6());
            goodsDetailsDto.setGoodsNote7(goodsDetailsDtoResponse.getGoodsNote7());
            goodsDetailsDto.setGoodsNote8(goodsDetailsDtoResponse.getGoodsNote8());
            goodsDetailsDto.setGoodsNote9(goodsDetailsDtoResponse.getGoodsNote9());
            goodsDetailsDto.setGoodsNote10(goodsDetailsDtoResponse.getGoodsNote10());
            goodsDetailsDto.setOrderSetting1(goodsDetailsDtoResponse.getOrderSetting1());
            goodsDetailsDto.setOrderSetting2(goodsDetailsDtoResponse.getOrderSetting2());
            goodsDetailsDto.setOrderSetting3(goodsDetailsDtoResponse.getOrderSetting3());
            goodsDetailsDto.setOrderSetting4(goodsDetailsDtoResponse.getOrderSetting4());
            goodsDetailsDto.setOrderSetting5(goodsDetailsDtoResponse.getOrderSetting5());
            goodsDetailsDto.setOrderSetting6(goodsDetailsDtoResponse.getOrderSetting6());
            goodsDetailsDto.setOrderSetting7(goodsDetailsDtoResponse.getOrderSetting7());
            goodsDetailsDto.setOrderSetting8(goodsDetailsDtoResponse.getOrderSetting8());
            goodsDetailsDto.setOrderSetting9(goodsDetailsDtoResponse.getOrderSetting9());
            goodsDetailsDto.setOrderSetting10(goodsDetailsDtoResponse.getOrderSetting10());
            goodsDetailsDto.setUnitImage(toGoodsImageEntityResponse(goodsDetailsDtoResponse.getUnitImage()));
            goodsDetailsDto.setGoodsOptionDisplayName(goodsDetailsDtoResponse.getGoodsOptionDisplayName());
            goodsDetailsDto.setCoolSendFrom(goodsDetailsDtoResponse.getCoolSendFrom());
            goodsDetailsDto.setCoolSendTo(goodsDetailsDtoResponse.getCoolSendTo());
            goodsDetailsDto.setTax(goodsDetailsDtoResponse.getTax());
            goodsDetailsDto.setUnit(goodsDetailsDtoResponse.getUnit());
            goodsDetailsDto.setGoodsManagementCode(goodsDetailsDtoResponse.getGoodsManagementCode());
            goodsDetailsDto.setGoodsDivisionCode(goodsDetailsDtoResponse.getGoodsDivisionCode());
            goodsDetailsDto.setGoodsCategory1(goodsDetailsDtoResponse.getGoodsCategory1());
            goodsDetailsDto.setGoodsCategory2(goodsDetailsDtoResponse.getGoodsCategory2());
            goodsDetailsDto.setGoodsCategory3(goodsDetailsDtoResponse.getGoodsCategory3());
            goodsDetailsDto.setSaleYesNo(goodsDetailsDtoResponse.getSaleYesNo());
            goodsDetailsDto.setSaleNgMessage(goodsDetailsDtoResponse.getSaleNgMessage());
            goodsDetailsDto.setDeliveryYesNo(goodsDetailsDtoResponse.getDeliveryYesNo());
            goodsDetailsDto.setGoodsTaxType(goodsDetailsDtoResponse.getGoodsTaxType() != null ?
                                                            EnumTypeUtil.getEnumFromValue(HTypeGoodsTaxType.class,
                                                                                          goodsDetailsDtoResponse.getGoodsTaxType()
                                                                                         ) :
                                                            null);
            goodsDetailsDto.setAlcoholFlag(goodsDetailsDtoResponse.getAlcoholFlag() != null ?
                                                           EnumTypeUtil.getEnumFromValue(HTypeAlcoholFlag.class,
                                                                                         goodsDetailsDtoResponse.getAlcoholFlag()
                                                                                        ) :
                                                           null);
            goodsDetailsDto.setSaleStatusPC(goodsDetailsDtoResponse.getSaleStatus() != null ?
                                                            EnumTypeUtil.getEnumFromValue(HTypeGoodsSaleStatus.class,
                                                                                          goodsDetailsDtoResponse.getSaleStatus()
                                                                                         ) :
                                                            null);
            goodsDetailsDto.setUnitManagementFlag(goodsDetailsDtoResponse.getUnitManagementFlag() != null ?
                                                                  EnumTypeUtil.getEnumFromValue(
                                                                                  HTypeUnitManagementFlag.class,
                                                                                  goodsDetailsDtoResponse.getUnitManagementFlag()
                                                                                               ) :
                                                                  null);
            goodsDetailsDto.setStockManagementFlag(goodsDetailsDtoResponse.getStockManagementFlag() != null ?
                                                                   EnumTypeUtil.getEnumFromValue(
                                                                                   HTypeStockManagementFlag.class,
                                                                                   goodsDetailsDtoResponse.getStockManagementFlag()
                                                                                                ) :
                                                                   null);
            goodsDetailsDto.setIndividualDeliveryType(goodsDetailsDtoResponse.getIndividualDeliveryType() != null ?
                                                                      EnumTypeUtil.getEnumFromValue(
                                                                                      HTypeIndividualDeliveryType.class,
                                                                                      goodsDetailsDtoResponse.getIndividualDeliveryType()
                                                                                                   ) :
                                                                      null);
            goodsDetailsDto.setFreeDeliveryFlag(goodsDetailsDtoResponse.getFreeDeliveryFlag() != null ?
                                                                EnumTypeUtil.getEnumFromValue(
                                                                                HTypeFreeDeliveryFlag.class,
                                                                                goodsDetailsDtoResponse.getFreeDeliveryFlag()
                                                                                             ) :
                                                                null);
            goodsDetailsDto.setGoodsOpenStatusPC(goodsDetailsDtoResponse.getGoodsOpenStatus() != null ?
                                                                 EnumTypeUtil.getEnumFromValue(
                                                                                 HTypeOpenDeleteStatus.class,
                                                                                 goodsDetailsDtoResponse.getGoodsOpenStatus()
                                                                                              ) :
                                                                 null);
            goodsDetailsDto.setSnsLinkFlag(goodsDetailsDtoResponse.getSnsLinkFlag() != null ?
                                                           EnumTypeUtil.getEnumFromValue(HTypeSnsLinkFlag.class,
                                                                                         goodsDetailsDtoResponse.getSnsLinkFlag()
                                                                                        ) :
                                                           null);
            goodsDetailsDto.setStockStatusPc(goodsDetailsDtoResponse.getStockStatus() != null ?
                                                             EnumTypeUtil.getEnumFromValue(HTypeStockStatusType.class,
                                                                                           goodsDetailsDtoResponse.getStockStatus()
                                                                                          ) :
                                                             null);
            goodsDetailsDto.setGoodsClassType(goodsDetailsDtoResponse.getGoodsClassType() != null ?
                                                              EnumTypeUtil.getEnumFromValue(HTypeGoodsClassType.class,
                                                                                            goodsDetailsDtoResponse.getGoodsClassType()
                                                                                           ) :
                                                              null);
            goodsDetailsDto.setDentalMonopolySalesFlg(goodsDetailsDtoResponse.getDentalMonopolySalesFlg() != null ?
                                                                      EnumTypeUtil.getEnumFromValue(
                                                                                      HTypeDentalMonopolySalesFlg.class,
                                                                                      goodsDetailsDtoResponse.getDentalMonopolySalesFlg()
                                                                                                   ) :
                                                                      null);
            goodsDetailsDto.setSaleIconFlag(goodsDetailsDtoResponse.getSaleIconFlag() != null ?
                                                            EnumTypeUtil.getEnumFromValue(HTypeSaleIconFlag.class,
                                                                                          goodsDetailsDtoResponse.getSaleIconFlag()
                                                                                         ) :
                                                            null);
            goodsDetailsDto.setReserveIconFlag(goodsDetailsDtoResponse.getReserveIconFlag() != null ?
                                                               EnumTypeUtil.getEnumFromValue(HTypeReserveIconFlag.class,
                                                                                             goodsDetailsDtoResponse.getReserveIconFlag()
                                                                                            ) :
                                                               null);
            goodsDetailsDto.setNewIconFlag(goodsDetailsDtoResponse.getNewIconFlag() != null ?
                                                           EnumTypeUtil.getEnumFromValue(HTypeNewIconFlag.class,
                                                                                         goodsDetailsDtoResponse.getNewIconFlag()
                                                                                        ) :
                                                           null);
            goodsDetailsDto.setLandSendFlag(goodsDetailsDtoResponse.getLandSendFlag() != null ?
                                                            EnumTypeUtil.getEnumFromValue(HTypeLandSendFlag.class,
                                                                                          goodsDetailsDtoResponse.getLandSendFlag()
                                                                                         ) :
                                                            null);
            goodsDetailsDto.setCoolSendFlag(goodsDetailsDtoResponse.getCoolSendFlag() != null ?
                                                            EnumTypeUtil.getEnumFromValue(HTypeCoolSendFlag.class,
                                                                                          goodsDetailsDtoResponse.getCoolSendFlag()
                                                                                         ) :
                                                            null);
            goodsDetailsDto.setReserveFlag(goodsDetailsDtoResponse.getReserveFlag() != null ?
                                                           EnumTypeUtil.getEnumFromValue(HTypeReserveFlag.class,
                                                                                         goodsDetailsDtoResponse.getReserveFlag()
                                                                                        ) :
                                                           null);
            goodsDetailsDto.setPriceMarkDispFlag(goodsDetailsDtoResponse.getPriceMarkDispFlag() != null ?
                                                                 EnumTypeUtil.getEnumFromValue(
                                                                                 HTypePriceMarkDispFlag.class,
                                                                                 goodsDetailsDtoResponse.getPriceMarkDispFlag()
                                                                                              ) :
                                                                 null);
            goodsDetailsDto.setSalePriceMarkDispFlag(goodsDetailsDtoResponse.getSalePriceMarkDispFlag() != null ?
                                                                     EnumTypeUtil.getEnumFromValue(
                                                                                     HTypeSalePriceMarkDispFlag.class,
                                                                                     goodsDetailsDtoResponse.getSalePriceMarkDispFlag()
                                                                                                  ) :
                                                                     null);
            goodsDetailsDto.setSalePriceIntegrityFlag(goodsDetailsDtoResponse.getSalePriceIntegrityFlag() != null ?
                                                                      EnumTypeUtil.getEnumFromValue(
                                                                                      HTypeSalePriceIntegrityFlag.class,
                                                                                      goodsDetailsDtoResponse.getSalePriceIntegrityFlag()
                                                                                                   ) :
                                                                      null);
            goodsDetailsDto.setEmotionPriceType(goodsDetailsDtoResponse.getEmotionPriceType() != null ?
                                                                EnumTypeUtil.getEnumFromValue(
                                                                                HTypeEmotionPriceType.class,
                                                                                goodsDetailsDtoResponse.getEmotionPriceType()
                                                                                             ) :
                                                                null);

            goodsMaster.put(Integer.parseInt(entry.getKey()), goodsDetailsDto);
        }

        return goodsMaster;
    }

    /**
     * 商品グループ画像レスポンスリストに変換
     *
     * @param goodsGroupImageEntityResponseList 商品グループ画像リスト
     * @return 商品グループ画像レスポンスリスト
     */
    private List<GoodsGroupImageEntity> toGoodsGroupImageEntityList(List<GoodsGroupImageEntityResponse> goodsGroupImageEntityResponseList) {

        if (CollectionUtils.isEmpty(goodsGroupImageEntityResponseList)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntity> goodsGroupImageEntityList = new ArrayList<>();

        goodsGroupImageEntityResponseList.forEach(goodsGroupImageEntityResponse -> {
            GoodsGroupImageEntity goodsGroupImageEntity = new GoodsGroupImageEntity();

            goodsGroupImageEntity.setGoodsGroupSeq(goodsGroupImageEntityResponse.getGoodsGroupSeq());
            goodsGroupImageEntity.setImageTypeVersionNo(goodsGroupImageEntityResponse.getImageTypeVersionNo());
            goodsGroupImageEntity.setImageFileName(goodsGroupImageEntityResponse.getImageFileName());
            goodsGroupImageEntity.setRegistTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntityResponse.getRegistTime()));
            goodsGroupImageEntity.setUpdateTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntityResponse.getUpdateTime()));

            goodsGroupImageEntityList.add(goodsGroupImageEntity);
        });

        return goodsGroupImageEntityList;
    }

    /**
     * 商品グループ画像レスポンスに変換
     *
     * @param response 商品画像クラス
     * @return 商品グループ画像レスポンス
     */
    private GoodsImageEntity toGoodsImageEntityResponse(GoodsImageEntityResponse response) {

        if (ObjectUtils.isEmpty(response)) {
            return null;
        }

        GoodsImageEntity goodsImageEntity = new GoodsImageEntity();

        goodsImageEntity.setGoodsGroupSeq(response.getGoodsGroupSeq());
        goodsImageEntity.setGoodsSeq(response.getGoodsSeq());
        goodsImageEntity.setImageFileName(response.getImageFileName());
        goodsImageEntity.setDisplayFlag(response.getDisplayFlag() != null ?
                                                        EnumTypeUtil.getEnumFromValue(HTypeDisplayStatus.class,
                                                                                      response.getDisplayFlag()
                                                                                     ) :
                                                        null);
        goodsImageEntity.setTmpFilePath(response.getTmpFilePath());
        goodsImageEntity.setRegistTime(conversionUtility.toTimeStamp(response.getRegistTime()));

        return goodsImageEntity;
    }

    /**
     * 消費税率マスタマップレスポンスに変換
     *
     * @param taxRateEntityResponseMap 税率マスタマップ
     * @return 消費税率マスタマップレスポンス
     */
    private Map<HTypeTaxRateType, TaxRateEntity> toTaxRateMasterMap(Map<String, TaxRateEntityResponse> taxRateEntityResponseMap) {

        if (MapUtils.isEmpty(taxRateEntityResponseMap)) {
            return new HashMap<>();
        }

        Map<HTypeTaxRateType, TaxRateEntity> taxRateEntityMap = new HashMap<>();

        for (Map.Entry<String, TaxRateEntityResponse> entry : taxRateEntityResponseMap.entrySet()) {

            TaxRateEntityResponse taxRateEntityResponse = entry.getValue();
            TaxRateEntity taxRateEntity = new TaxRateEntity();

            taxRateEntity.setTaxSeq(taxRateEntityResponse.getTaxSeq());
            taxRateEntity.setRate(taxRateEntityResponse.getRate());
            taxRateEntity.setRateType(taxRateEntityResponse.getRateType() != null ?
                                                      EnumTypeUtil.getEnumFromValue(HTypeTaxRateType.class,
                                                                                    taxRateEntityResponse.getRateType()
                                                                                   ) :
                                                      null);
            taxRateEntity.setOrderDisplay(taxRateEntityResponse.getOrderDisplay());
            taxRateEntity.setRegistTime(conversionUtility.toTimeStamp(taxRateEntityResponse.getRegistTime()));
            taxRateEntity.setUpdateTime(conversionUtility.toTimeStamp(taxRateEntityResponse.getUpdateTime()));

            taxRateEntityMap.put(EnumTypeUtil.getEnumFromValue(HTypeTaxRateType.class, entry.getKey()), taxRateEntity);
        }

        return taxRateEntityMap;
    }

    /**
     * 配送マスタマップレスポンスに変換
     *
     * @param deliveryMethodDetailsDtoResponseMap 配送マスタマップ
     * @return 配送マスタマップ
     */
    private Map<Integer, DeliveryMethodDetailsDto> toDeliveryMethodMasterMap(Map<String, DeliveryMethodDetailsDtoResponse> deliveryMethodDetailsDtoResponseMap) {

        if (MapUtils.isEmpty(deliveryMethodDetailsDtoResponseMap)) {
            return new HashMap<>();
        }

        Map<Integer, DeliveryMethodDetailsDto> deliveryMethodDetailsDtoMap = new HashMap<>();

        for (Map.Entry<String, DeliveryMethodDetailsDtoResponse> entry : deliveryMethodDetailsDtoResponseMap.entrySet()) {

            DeliveryMethodDetailsDtoResponse response = entry.getValue();
            DeliveryMethodDetailsDto deliveryMethodDetailsDto = new DeliveryMethodDetailsDto();

            deliveryMethodDetailsDto.setDeliveryMethodEntity(
                            toDeliveryMethodEntity(response.getDeliveryMethodEntity()));
            deliveryMethodDetailsDto.setDeliveryMethodTypeCarriageEntityList(
                            toDeliveryMethodTypeCarriageEntityList(response.getDeliveryMethodTypeCarriageEntityList()));
            deliveryMethodDetailsDto.setDeliverySpecialChargeAreaCount(
                            response.getDeliverySpecialChargeAreaCount() == null ?
                                            0 :
                                            response.getDeliverySpecialChargeAreaCount());
            deliveryMethodDetailsDto.setDeliveryImpossibleAreaCount(response.getDeliveryImpossibleAreaCount() == null ?
                                                                                    0 :
                                                                                    response.getDeliveryImpossibleAreaCount());
            deliveryMethodDetailsDto.setDeliveryImpossibleAreaResultDtoList(
                            toDeliveryImpossibleAreaResultDtoList(response.getDeliveryImpossibleAreaResultDtoList()));
            deliveryMethodDetailsDto.setDeliverySpecialChargeAreaResultDtoList(
                            toDeliverySpecialChargeAreaResultDtoListResponse(
                                            response.getDeliverySpecialChargeAreaResultDtoList()));

            deliveryMethodDetailsDtoMap.put(Integer.parseInt(entry.getKey()), deliveryMethodDetailsDto);
        }

        return deliveryMethodDetailsDtoMap;

    }

    /**
     * 配送方法Entityレスポンスに変換
     *
     * @param deliveryMethodEntityResponse 配送方法エンティティ
     * @return 配送方法Entityレスポンス
     */
    private DeliveryMethodEntity toDeliveryMethodEntity(DeliveryMethodEntityResponse deliveryMethodEntityResponse) {

        if (ObjectUtils.isEmpty(deliveryMethodEntityResponse)) {
            return null;
        }

        DeliveryMethodEntity deliveryMethodEntity = new DeliveryMethodEntity();

        deliveryMethodEntity.setDeliveryMethodSeq(deliveryMethodEntityResponse.getDeliveryMethodSeq());
        deliveryMethodEntity.setDeliveryMethodName(deliveryMethodEntityResponse.getDeliveryMethodName());
        deliveryMethodEntity.setDeliveryMethodDisplayNamePC(
                        deliveryMethodEntityResponse.getDeliveryMethodDisplayName());
        deliveryMethodEntity.setDeliveryNotePC(deliveryMethodEntityResponse.getDeliveryNote());
        deliveryMethodEntity.setEqualsCarriage(deliveryMethodEntityResponse.getEqualsCarriage());
        deliveryMethodEntity.setLargeAmountDiscountPrice(deliveryMethodEntityResponse.getLargeAmountDiscountPrice());
        deliveryMethodEntity.setLargeAmountDiscountCarriage(
                        deliveryMethodEntityResponse.getLargeAmountDiscountCarriage());
        deliveryMethodEntity.setDeliveryLeadTime(deliveryMethodEntityResponse.getDeliveryLeadTime() == null ?
                                                                 0 :
                                                                 deliveryMethodEntityResponse.getDeliveryLeadTime());
        deliveryMethodEntity.setDeliveryChaseURL(deliveryMethodEntityResponse.getDeliveryChaseURL());
        deliveryMethodEntity.setDeliveryChaseURLDisplayPeriod(
                        deliveryMethodEntityResponse.getDeliveryChaseURLDisplayPeriod());
        deliveryMethodEntity.setPossibleSelectDays(deliveryMethodEntityResponse.getPossibleSelectDays() == null ?
                                                                   0 :
                                                                   deliveryMethodEntityResponse.getPossibleSelectDays());
        deliveryMethodEntity.setReceiverTimeZone1(deliveryMethodEntityResponse.getReceiverTimeZone1());
        deliveryMethodEntity.setReceiverTimeZone2(deliveryMethodEntityResponse.getReceiverTimeZone2());
        deliveryMethodEntity.setReceiverTimeZone3(deliveryMethodEntityResponse.getReceiverTimeZone3());
        deliveryMethodEntity.setReceiverTimeZone4(deliveryMethodEntityResponse.getReceiverTimeZone4());
        deliveryMethodEntity.setReceiverTimeZone5(deliveryMethodEntityResponse.getReceiverTimeZone5());
        deliveryMethodEntity.setReceiverTimeZone6(deliveryMethodEntityResponse.getReceiverTimeZone6());
        deliveryMethodEntity.setReceiverTimeZone7(deliveryMethodEntityResponse.getReceiverTimeZone7());
        deliveryMethodEntity.setReceiverTimeZone8(deliveryMethodEntityResponse.getReceiverTimeZone8());
        deliveryMethodEntity.setReceiverTimeZone9(deliveryMethodEntityResponse.getReceiverTimeZone9());
        deliveryMethodEntity.setReceiverTimeZone10(deliveryMethodEntityResponse.getReceiverTimeZone10());
        deliveryMethodEntity.setOrderDisplay(deliveryMethodEntityResponse.getOrderDisplay());
        deliveryMethodEntity.setRegistTime(conversionUtility.toTimeStamp(deliveryMethodEntityResponse.getRegistTime()));
        deliveryMethodEntity.setUpdateTime(conversionUtility.toTimeStamp(deliveryMethodEntityResponse.getUpdateTime()));

        deliveryMethodEntity.setOpenStatusPC(deliveryMethodEntityResponse.getOpenStatus() != null ?
                                                             EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                                           deliveryMethodEntityResponse.getOpenStatus()
                                                                                          ) :
                                                             null);
        deliveryMethodEntity.setDeliveryMethodType(deliveryMethodEntityResponse.getDeliveryMethodType() != null ?
                                                                   EnumTypeUtil.getEnumFromValue(
                                                                                   HTypeDeliveryMethodType.class,
                                                                                   deliveryMethodEntityResponse.getDeliveryMethodType()
                                                                                                ) :
                                                                   null);
        deliveryMethodEntity.setShortfallDisplayFlag(deliveryMethodEntityResponse.getShortfallDisplayFlag() != null ?
                                                                     EnumTypeUtil.getEnumFromValue(
                                                                                     HTypeShortfallDisplayFlag.class,
                                                                                     deliveryMethodEntityResponse.getShortfallDisplayFlag()
                                                                                                  ) :
                                                                     null);

        return deliveryMethodEntity;
    }

    /**
     * 配送区分別送料クラスリストに変換
     *
     * @param deliveryMethodTypeCarriageEntityResponseList 配送区分別送料リスト
     * @return 配送区分別送料クラスリスト
     */
    private List<DeliveryMethodTypeCarriageEntity> toDeliveryMethodTypeCarriageEntityList(List<DeliveryMethodTypeCarriageEntityResponse> deliveryMethodTypeCarriageEntityResponseList) {

        if (CollectionUtils.isEmpty(deliveryMethodTypeCarriageEntityResponseList)) {
            return new ArrayList<>();
        }

        List<DeliveryMethodTypeCarriageEntity> deliveryMethodTypeCarriageEntityList = new ArrayList<>();

        deliveryMethodTypeCarriageEntityResponseList.forEach(deliveryMethodTypeCarriageEntityResponse -> {
            DeliveryMethodTypeCarriageEntity deliveryMethodTypeCarriageEntity = new DeliveryMethodTypeCarriageEntity();

            deliveryMethodTypeCarriageEntity.setDeliveryMethodSeq(
                            deliveryMethodTypeCarriageEntityResponse.getDeliveryMethodSeq());
            deliveryMethodTypeCarriageEntity.setPrefectureType(
                            deliveryMethodTypeCarriageEntityResponse.getPrefectureType() != null ?
                                            EnumTypeUtil.getEnumFromValue(
                                                            HTypePrefectureType.class,
                                                            deliveryMethodTypeCarriageEntityResponse.getPrefectureType()
                                                                         ) :
                                            null);
            deliveryMethodTypeCarriageEntity.setMaxPrice(deliveryMethodTypeCarriageEntityResponse.getMaxPrice());
            deliveryMethodTypeCarriageEntity.setCarriage(deliveryMethodTypeCarriageEntityResponse.getCarriage());
            deliveryMethodTypeCarriageEntity.setRegistTime(
                            conversionUtility.toTimeStamp(deliveryMethodTypeCarriageEntityResponse.getRegistTime()));

            deliveryMethodTypeCarriageEntityList.add(deliveryMethodTypeCarriageEntity);
        });

        return deliveryMethodTypeCarriageEntityList;
    }

    /**
     * 配送不可能エリア詳細Dtoクラスレスポンスリストに変換
     *
     * @param deliveryImpossibleAreaResultDtoResponseList 配送不可能エリア詳細Dtoリスト
     * @return 配送不可能エリア詳細Dtoクラスレスポンスリスト
     */
    private List<DeliveryImpossibleAreaResultDto> toDeliveryImpossibleAreaResultDtoList(List<DeliveryImpossibleAreaResultDtoResponse> deliveryImpossibleAreaResultDtoResponseList) {

        if (CollectionUtils.isEmpty(deliveryImpossibleAreaResultDtoResponseList)) {
            return new ArrayList<>();
        }

        List<DeliveryImpossibleAreaResultDto> deliveryImpossibleAreaResultDtoList = new ArrayList<>();

        deliveryImpossibleAreaResultDtoResponseList.forEach(deliveryImpossibleAreaResultDtoResponse -> {

            DeliveryImpossibleAreaResultDto deliveryImpossibleAreaResultDto = new DeliveryImpossibleAreaResultDto();

            deliveryImpossibleAreaResultDto.setDeliveryMethodSeq(
                            deliveryImpossibleAreaResultDtoResponse.getDeliveryMethodSeq());
            deliveryImpossibleAreaResultDto.setZipcode(deliveryImpossibleAreaResultDtoResponse.getZipcode());
            deliveryImpossibleAreaResultDto.setPrefecture(deliveryImpossibleAreaResultDtoResponse.getPrefecture());
            deliveryImpossibleAreaResultDto.setCity(deliveryImpossibleAreaResultDtoResponse.getCity());
            deliveryImpossibleAreaResultDto.setTown(deliveryImpossibleAreaResultDtoResponse.getTown());
            deliveryImpossibleAreaResultDto.setNumbers(deliveryImpossibleAreaResultDtoResponse.getNumbers());
            deliveryImpossibleAreaResultDto.setAddressList(deliveryImpossibleAreaResultDtoResponse.getAddressList());

            deliveryImpossibleAreaResultDtoList.add(deliveryImpossibleAreaResultDto);
        });

        return deliveryImpossibleAreaResultDtoList;
    }

    /**
     * 配送特別料金エリア詳細Dtoクラスリストに変換
     *
     * @param deliverySpecialChargeAreaResultDtoResponseList 配送特別料金エリア詳細Dtoレスポンスリスト
     * @return 配送特別料金エリア詳細Dtoクラスリスト
     */
    private List<DeliverySpecialChargeAreaResultDto> toDeliverySpecialChargeAreaResultDtoListResponse(List<DeliverySpecialChargeAreaResultDtoResponse> deliverySpecialChargeAreaResultDtoResponseList) {

        if (CollectionUtils.isEmpty(deliverySpecialChargeAreaResultDtoResponseList)) {
            return new ArrayList<>();
        }

        List<DeliverySpecialChargeAreaResultDto> deliverySpecialChargeAreaResultDtoList = new ArrayList<>();

        deliverySpecialChargeAreaResultDtoResponseList.forEach(deliverySpecialChargeAreaResultDtoResponse -> {
            DeliverySpecialChargeAreaResultDto deliverySpecialChargeAreaResultDto =
                            new DeliverySpecialChargeAreaResultDto();

            deliverySpecialChargeAreaResultDto.setDeliveryMethodSeq(
                            deliverySpecialChargeAreaResultDtoResponse.getDeliveryMethodSeq());
            deliverySpecialChargeAreaResultDto.setZipcode(deliverySpecialChargeAreaResultDtoResponse.getZipcode());
            deliverySpecialChargeAreaResultDto.setCarriage(deliverySpecialChargeAreaResultDtoResponse.getCarriage());
            deliverySpecialChargeAreaResultDto.setPrefecture(
                            deliverySpecialChargeAreaResultDtoResponse.getPrefecture());
            deliverySpecialChargeAreaResultDto.setCity(deliverySpecialChargeAreaResultDtoResponse.getCity());
            deliverySpecialChargeAreaResultDto.setTown(deliverySpecialChargeAreaResultDtoResponse.getTown());
            deliverySpecialChargeAreaResultDto.setNumbers(deliverySpecialChargeAreaResultDtoResponse.getNumbers());
            deliverySpecialChargeAreaResultDto.setAddressList(
                            deliverySpecialChargeAreaResultDtoResponse.getAddressList());

            deliverySpecialChargeAreaResultDtoList.add(deliverySpecialChargeAreaResultDto);
        });

        return deliverySpecialChargeAreaResultDtoList;
    }

    /**
     * カート商品チェックリクエストに変換
     *
     * @param cartDto カートDTO
     * @param isLogin ログインしているかどうか
     * @param memberInfoEntity 会員エンティティ
     * @param memberInfoSeq 会員SEQ
     * @return
     */
    public CartGoodsCheckRequest toCartGoodsCheckRequest(CartDto cartDto, Boolean isLogin,
                                                         // 2023-renew No14 from here
                                                         MemberInfoEntity memberInfoEntity,
                                                         // 2023-renew No14 to here
                                                         Integer memberInfoSeq) {

        CartGoodsCheckRequest cartGoodsCheckRequest = new CartGoodsCheckRequest();

        cartGoodsCheckRequest.setCartDto(toCartDtoRequest(cartDto));
        cartGoodsCheckRequest.setIsLogin(isLogin);

        // 2023-renew No14 from here
        HTypeBusinessType businessType = memberInfoEntity.getBusinessType();
        HTypeConfDocumentType confDocumentType = memberInfoEntity.getConfDocumentType();
        // 2023-renew No14 to here
        if (businessType != null) {
            cartGoodsCheckRequest.setBusinessType(businessType.getValue());
        }
        if (confDocumentType != null) {
            cartGoodsCheckRequest.setConfDocumentType(confDocumentType.getValue());
        }
        cartGoodsCheckRequest.setMemberInfoSeq(memberInfoSeq);
        // 2023-renew No14 from here
        cartGoodsCheckRequest.setCustomerNo(memberInfoEntity.getCustomerNo());
        cartGoodsCheckRequest.setZipcode(memberInfoEntity.getMemberInfoZipCode());
        // 2023-renew No14 to here

        return cartGoodsCheckRequest;
    }

    /**
     * カートDtoリクエストに変換
     *
     * @param cartDto カートDtoクラス
     * @return カートDtoリクエスト
     */
    private jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartDtoRequest toCartDtoRequest(CartDto cartDto) {
        jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartDtoRequest cartDtoRequest =
                        new jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartDtoRequest();

        cartDtoRequest.setGoodsTotalCount(cartDto.getGoodsTotalCount());
        cartDtoRequest.setGoodsTotalPrice(cartDto.getGoodsTotalPrice());
        cartDtoRequest.setGoodsTotalPriceInTax(cartDto.getGoodsTotalPriceInTax());
        cartDtoRequest.setCartGoodsDtoList(toCartGoodsDtoList(cartDto.getCartGoodsDtoList()));
        if (cartDto.getSettlementMethodType() != null) {
            cartDtoRequest.setSettlementMethodType(cartDto.getSettlementMethodType().getValue());
        }
        cartDtoRequest.setDiscountsResponseDetailMap(
                        toDiscountsResponseDetailMap(cartDto.getDiscountsResponseDetailMap()));
        cartDtoRequest.setConsumptionTaxRateMap(toConsumptionTaxRateMap(cartDto.getConsumptionTaxRateMap()));
        // 2023-renew No14 from here
        cartDtoRequest.setQuantityDiscountsResponseDetailMap(
                        toWebApiGetQuantityDiscountResultResponseDetailDtoRequestMap(
                                        cartDto.getQuantityDiscountsResponseDetailMap()));
        cartDtoRequest.setReserveMap(toWebApiGetReserveResponseDetailDtoRequestMap(cartDto.getReserveMap()));
        cartDtoRequest.setSaleCheckMap(toWebApiGetSaleCheckDetailResponseMap(cartDto.getSaleCheckMap()));
        // 2023-renew No14 to here

        return cartDtoRequest;
    }

    // 2023-renew No14 from here

    /**
     * 数量割引適用結果取得リクエストMAPに変換
     *
     * @param quantityDiscountsResponseDetailMap 数量割引適用結果MAP
     * @return 数量割引適用結果取得リクエストMAP
     */
    private Map<String, WebApiGetQuantityDiscountResultResponseDetailDtoRequest> toWebApiGetQuantityDiscountResultResponseDetailDtoRequestMap(
                    Map<String, WebApiGetQuantityDiscountResultResponseDetailDto> quantityDiscountsResponseDetailMap) {

        if (MapUtils.isEmpty(quantityDiscountsResponseDetailMap)) {
            return null;
        }

        Map<String, WebApiGetQuantityDiscountResultResponseDetailDtoRequest> requestMap = new HashMap<>();

        quantityDiscountsResponseDetailMap.forEach((key, value) -> {
            if (ObjectUtils.isNotEmpty(value)) {
                WebApiGetQuantityDiscountResultResponseDetailDtoRequest request =
                                new WebApiGetQuantityDiscountResultResponseDetailDtoRequest();

                request.setGoodsCode(value.getGoodsCode());
                request.setSaleType(value.getSaleType());
                request.setSaleGroupCode(value.getSaleGroupCode());
                request.setSalePrice(value.getSalePrice());
                request.setQuantity(value.getQuantity());
                request.setSaleCode(value.getSaleCode());
                request.setNote(value.getNote());
                request.setHints(value.getHints());
                request.setPrice(value.getPrice());

                requestMap.put(key, request);
            }
        });

        return requestMap;
    }

    /**
     * 取りおき情報取得 詳細情報リクエストに変換
     *
     * @param reserveMap 取りおき情報MAP
     * @return 取りおき情報 詳細情報リクエスト
     */
    private Map<String, WebApiGetReserveResponseDetailDtoRequest> toWebApiGetReserveResponseDetailDtoRequestMap(Map<String, WebApiGetReserveResponseDetailDto> reserveMap) {

        if (MapUtils.isEmpty(reserveMap)) {
            return null;
        }

        Map<String, WebApiGetReserveResponseDetailDtoRequest> requestMap = new HashMap<>();

        reserveMap.forEach((key, value) -> {
            if (ObjectUtils.isNotEmpty(value)) {
                WebApiGetReserveResponseDetailDtoRequest request = new WebApiGetReserveResponseDetailDtoRequest();

                request.setGoodsCode(value.getGoodsCode());
                request.setReserveFlag(value.getReserveFlag());
                request.setDiscountFlag(value.getDiscountFlag());
                request.setPossibleReserveFromDay(value.getPossibleReserveFromDay());
                request.setPossibleReserveToDay(value.getPossibleReserveToDay());

                requestMap.put(key, request);
            }
        });

        return requestMap;
    }

    /**
     * 販売可否判定 詳細情報リクエストに変換
     *
     * @param saleCheckMap 販売可否判定MAP
     * @return 販売可否判定 詳細情報リクエスト
     */
    private Map<String, WebApiGetSaleCheckDetailResponse> toWebApiGetSaleCheckDetailResponseMap(Map<String, WebApiGetSaleCheckResponseDetailDto> saleCheckMap) {

        if (MapUtils.isEmpty(saleCheckMap)) {
            return null;
        }

        Map<String, WebApiGetSaleCheckDetailResponse> requestMap = new HashMap<>();

        saleCheckMap.forEach((key, value) -> {
            if (ObjectUtils.isNotEmpty(value)) {
                WebApiGetSaleCheckDetailResponse request = new WebApiGetSaleCheckDetailResponse();

                request.setGoodsCode(value.getGoodsCode());
                request.setGoodsSaleYesNo(value.getGoodsSaleYesNo());

                requestMap.put(key, request);
            }
        });

        return requestMap;
    }

    // 2023-renew No14 to here

    /**
     * カート商品Dtoリストに変換
     *
     * @param cartGoodsDtoList カート商品Dtoリクエストリスト
     * @return カート商品Dtoリスト
     */
    private List<jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsDtoRequest> toCartGoodsDtoList(List<CartGoodsDto> cartGoodsDtoList) {

        if (CollectionUtils.isEmpty(cartGoodsDtoList)) {
            return new ArrayList<>();
        }

        List<jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsDtoRequest> CartGoodsDtoRequestList =
                        new ArrayList<>();

        cartGoodsDtoList.forEach(cartGoodsDto -> {
            jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsDtoRequest cartGoodsDtoRequest =
                            new jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsDtoRequest();

            cartGoodsDtoRequest.setCartGoodsEntity(toCartGoodsEntityRequest(cartGoodsDto.getCartGoodsEntity()));
            cartGoodsDtoRequest.setGoodsDetailsDto(toGoodsDetailsDtoRequest(cartGoodsDto.getGoodsDetailsDto()));
            cartGoodsDtoRequest.setGoodsPriceSubtotal(cartGoodsDto.getGoodsPriceSubtotal());
            cartGoodsDtoRequest.setGoodsPriceInTaxSubtotal(cartGoodsDto.getGoodsPriceInTaxSubtotal());

            CartGoodsDtoRequestList.add(cartGoodsDtoRequest);
        });

        return CartGoodsDtoRequestList;
    }

    /**
     * カート商品に変換
     *
     * @param cartGoodsEntity カート商品エンティティリクエスト
     * @return カート商品
     */
    private jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsEntityRequest toCartGoodsEntityRequest(
                    CartGoodsEntity cartGoodsEntity) {

        if (ObjectUtils.isEmpty(cartGoodsEntity)) {
            return null;
        }

        jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsEntityRequest cartGoodsEntityRequest =
                        new jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsEntityRequest();

        cartGoodsEntityRequest.setCartSeq(cartGoodsEntity.getCartSeq());
        cartGoodsEntityRequest.setAccessUid(cartGoodsEntity.getAccessUid());
        cartGoodsEntityRequest.setMemberInfoSeq(cartGoodsEntity.getMemberInfoSeq());
        cartGoodsEntityRequest.setGoodsSeq(cartGoodsEntity.getGoodsSeq());
        cartGoodsEntityRequest.setCartGoodsCount(cartGoodsEntity.getCartGoodsCount());
        cartGoodsEntityRequest.setRegistTime(conversionUtility.toTimeStamp(cartGoodsEntity.getRegistTime()));
        cartGoodsEntityRequest.setUpdateTime(conversionUtility.toTimeStamp(cartGoodsEntity.getUpdateTime()));
        // 2023-renew No14 from here
        cartGoodsEntityRequest.setReserveFlag(cartGoodsEntity.getReserveFlag().getValue());
        cartGoodsEntityRequest.setReserveDeliveryDate(
                        conversionUtility.toTimeStamp(cartGoodsEntity.getReserveDeliveryDate()));
        // 2023-renew No14 to here

        return cartGoodsEntityRequest;
    }

    /**
     * 商品詳細Dtoに変換
     *
     * @param goodsDetailsDto 商品詳細Dtoクラスリクエスト
     * @return 商品詳細Dto
     */
    private jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsDetailsDtoRequest toGoodsDetailsDtoRequest(
                    GoodsDetailsDto goodsDetailsDto) {

        if (ObjectUtils.isEmpty(goodsDetailsDto)) {
            return null;
        }

        jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsDetailsDtoRequest goodsDetailsDtoRequest =
                        new jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsDetailsDtoRequest();

        goodsDetailsDtoRequest.setGoodsSeq(goodsDetailsDto.getGoodsSeq());
        goodsDetailsDtoRequest.setGoodsGroupSeq(goodsDetailsDto.getGoodsGroupSeq());
        goodsDetailsDtoRequest.setVersionNo(goodsDetailsDto.getVersionNo());
        goodsDetailsDtoRequest.setRegistTime(conversionUtility.toTimeStamp(goodsDetailsDto.getRegistTime()));
        goodsDetailsDtoRequest.setUpdateTime(conversionUtility.toTimeStamp(goodsDetailsDto.getUpdateTime()));
        goodsDetailsDtoRequest.setGoodsCode(goodsDetailsDto.getGoodsCode());
        goodsDetailsDtoRequest.setGoodsGroupMaxPrice(goodsDetailsDto.getGoodsGroupMaxPrice());
        goodsDetailsDtoRequest.setGoodsGroupMinPrice(goodsDetailsDto.getGoodsGroupMinPrice());
        goodsDetailsDtoRequest.setPreDiscountMinPrice(goodsDetailsDto.getPreDiscountMinPrice());
        goodsDetailsDtoRequest.setPreDiscountMaxPrice(goodsDetailsDto.getPreDiscountMaxPrice());
        goodsDetailsDtoRequest.setTaxRate(goodsDetailsDto.getTaxRate());
        goodsDetailsDtoRequest.setGoodsPriceInTax(goodsDetailsDto.getGoodsPriceInTax());
        goodsDetailsDtoRequest.setGoodsPrice(goodsDetailsDto.getGoodsPrice());
        goodsDetailsDtoRequest.setDeliveryType(goodsDetailsDto.getDeliveryType());
        goodsDetailsDtoRequest.setSaleStartTime(conversionUtility.toTimeStamp(goodsDetailsDto.getSaleStartTimePC()));
        goodsDetailsDtoRequest.setSaleEndTime(conversionUtility.toTimeStamp(goodsDetailsDto.getSaleEndTimePC()));
        goodsDetailsDtoRequest.setPurchasedMax(goodsDetailsDto.getPurchasedMax());
        goodsDetailsDtoRequest.setOrderDisplay(goodsDetailsDto.getOrderDisplay());
        goodsDetailsDtoRequest.setUnitValue1(goodsDetailsDto.getUnitValue1());
        goodsDetailsDtoRequest.setUnitValue2(goodsDetailsDto.getUnitValue2());
        goodsDetailsDtoRequest.setPreDiscountPrice(goodsDetailsDto.getPreDiscountPrice());
        goodsDetailsDtoRequest.setPreDisCountPriceInTax(goodsDetailsDto.getPreDisCountPriceInTax());
        goodsDetailsDtoRequest.setJanCode(goodsDetailsDto.getJanCode());
        goodsDetailsDtoRequest.setCatalogCode(goodsDetailsDto.getCatalogCode());
        goodsDetailsDtoRequest.setSalesPossibleStock(goodsDetailsDto.getSalesPossibleStock());
        goodsDetailsDtoRequest.setRealStock(goodsDetailsDto.getRealStock());
        goodsDetailsDtoRequest.setOrderReserveStock(goodsDetailsDto.getOrderReserveStock());
        goodsDetailsDtoRequest.setRemainderFewStock(goodsDetailsDto.getRemainderFewStock());
        goodsDetailsDtoRequest.setOrderPointStock(goodsDetailsDto.getOrderPointStock());
        goodsDetailsDtoRequest.setSafetyStock(goodsDetailsDto.getSafetyStock());
        goodsDetailsDtoRequest.setGoodsGroupCode(goodsDetailsDto.getGoodsGroupCode());
        goodsDetailsDtoRequest.setWhatsnewDate(conversionUtility.toTimeStamp(goodsDetailsDto.getWhatsnewDate()));
        goodsDetailsDtoRequest.setOpenStartTime(conversionUtility.toTimeStamp(goodsDetailsDto.getOpenStartTimePC()));
        goodsDetailsDtoRequest.setOpenEndTime(conversionUtility.toTimeStamp(goodsDetailsDto.getOpenEndTimePC()));
        goodsDetailsDtoRequest.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
        goodsDetailsDtoRequest.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
        goodsDetailsDtoRequest.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
        goodsDetailsDtoRequest.setGoodsPreDiscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());
        goodsDetailsDtoRequest.setGoodsGroupImageEntityList(
                        toGoodsGroupImageEntityRequestList(goodsDetailsDto.getGoodsGroupImageEntityList()));
        goodsDetailsDtoRequest.setMetaDescription(goodsDetailsDto.getMetaDescription());
        goodsDetailsDtoRequest.setGoodsNote1(goodsDetailsDto.getGoodsNote1());
        goodsDetailsDtoRequest.setGoodsNote2(goodsDetailsDto.getGoodsNote2());
        goodsDetailsDtoRequest.setGoodsNote3(goodsDetailsDto.getGoodsNote3());
        goodsDetailsDtoRequest.setGoodsNote4(goodsDetailsDto.getGoodsNote4());
        goodsDetailsDtoRequest.setGoodsNote5(goodsDetailsDto.getGoodsNote5());
        goodsDetailsDtoRequest.setGoodsNote6(goodsDetailsDto.getGoodsNote6());
        goodsDetailsDtoRequest.setGoodsNote7(goodsDetailsDto.getGoodsNote7());
        goodsDetailsDtoRequest.setGoodsNote8(goodsDetailsDto.getGoodsNote8());
        goodsDetailsDtoRequest.setGoodsNote9(goodsDetailsDto.getGoodsNote9());
        goodsDetailsDtoRequest.setGoodsNote10(goodsDetailsDto.getGoodsNote10());
        goodsDetailsDtoRequest.setOrderSetting1(goodsDetailsDto.getOrderSetting1());
        goodsDetailsDtoRequest.setOrderSetting2(goodsDetailsDto.getOrderSetting2());
        goodsDetailsDtoRequest.setOrderSetting3(goodsDetailsDto.getOrderSetting3());
        goodsDetailsDtoRequest.setOrderSetting4(goodsDetailsDto.getOrderSetting4());
        goodsDetailsDtoRequest.setOrderSetting5(goodsDetailsDto.getOrderSetting5());
        goodsDetailsDtoRequest.setOrderSetting6(goodsDetailsDto.getOrderSetting6());
        goodsDetailsDtoRequest.setOrderSetting7(goodsDetailsDto.getOrderSetting7());
        goodsDetailsDtoRequest.setOrderSetting8(goodsDetailsDto.getOrderSetting8());
        goodsDetailsDtoRequest.setOrderSetting9(goodsDetailsDto.getOrderSetting9());
        goodsDetailsDtoRequest.setOrderSetting10(goodsDetailsDto.getOrderSetting10());
        goodsDetailsDtoRequest.setUnitImage(toUnitImage(goodsDetailsDto.getUnitImage()));
        goodsDetailsDtoRequest.setGoodsOptionDisplayName(goodsDetailsDto.getGoodsOptionDisplayName());
        goodsDetailsDtoRequest.setCoolSendFrom(goodsDetailsDto.getCoolSendFrom());
        goodsDetailsDtoRequest.setCoolSendTo(goodsDetailsDto.getCoolSendTo());
        goodsDetailsDtoRequest.setTax(goodsDetailsDto.getTax());
        goodsDetailsDtoRequest.setUnit(goodsDetailsDto.getUnit());
        goodsDetailsDtoRequest.setGoodsManagementCode(goodsDetailsDto.getGoodsManagementCode());
        goodsDetailsDtoRequest.setGoodsDivisionCode(goodsDetailsDto.getGoodsDivisionCode());
        goodsDetailsDtoRequest.setGoodsCategory1(goodsDetailsDto.getGoodsCategory1());
        goodsDetailsDtoRequest.setGoodsCategory2(goodsDetailsDto.getGoodsCategory2());
        goodsDetailsDtoRequest.setGoodsCategory3(goodsDetailsDto.getGoodsCategory3());
        goodsDetailsDtoRequest.setSaleYesNo(goodsDetailsDto.getSaleYesNo());
        goodsDetailsDtoRequest.setSaleNgMessage(goodsDetailsDto.getSaleNgMessage());
        goodsDetailsDtoRequest.setDeliveryYesNo(goodsDetailsDto.getDeliveryYesNo());

        if (goodsDetailsDto.getSaleStatusPC() != null) {
            goodsDetailsDtoRequest.setSaleStatus(goodsDetailsDto.getSaleStatusPC().getValue());
        }
        if (goodsDetailsDto.getStockStatusPc() != null) {
            goodsDetailsDtoRequest.setStockStatus(goodsDetailsDto.getStockStatusPc().getValue());
        }
        if (goodsDetailsDto.getGoodsTaxType() != null) {
            goodsDetailsDtoRequest.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType().getValue());
        }
        if (goodsDetailsDto.getAlcoholFlag() != null) {
            goodsDetailsDtoRequest.setAlcoholFlag(goodsDetailsDto.getAlcoholFlag().getValue());
        }
        if (goodsDetailsDto.getUnitManagementFlag() != null) {
            goodsDetailsDtoRequest.setUnitManagementFlag(goodsDetailsDto.getUnitManagementFlag().getValue());
        }
        if (goodsDetailsDto.getStockManagementFlag() != null) {
            goodsDetailsDtoRequest.setStockManagementFlag(goodsDetailsDto.getStockManagementFlag().getValue());
        }
        if (goodsDetailsDto.getIndividualDeliveryType() != null) {
            goodsDetailsDtoRequest.setIndividualDeliveryType(goodsDetailsDto.getIndividualDeliveryType().getValue());
        }
        if (goodsDetailsDto.getFreeDeliveryFlag() != null) {
            goodsDetailsDtoRequest.setFreeDeliveryFlag(goodsDetailsDto.getFreeDeliveryFlag().getValue());
        }
        if (goodsDetailsDto.getGoodsOpenStatusPC() != null) {
            goodsDetailsDtoRequest.setGoodsOpenStatus(goodsDetailsDto.getGoodsOpenStatusPC().getValue());
        }
        if (goodsDetailsDto.getSnsLinkFlag() != null) {
            goodsDetailsDtoRequest.setSnsLinkFlag(goodsDetailsDto.getSnsLinkFlag().getValue());
        }
        if (goodsDetailsDto.getGoodsClassType() != null) {
            goodsDetailsDtoRequest.setGoodsClassType(goodsDetailsDto.getGoodsClassType().getValue());
        }
        if (goodsDetailsDto.getDentalMonopolySalesFlg() != null) {
            goodsDetailsDtoRequest.setDentalMonopolySalesFlg(goodsDetailsDto.getDentalMonopolySalesFlg().getValue());
        }
        if (goodsDetailsDto.getSaleIconFlag() != null) {
            goodsDetailsDtoRequest.setSaleIconFlag(goodsDetailsDto.getSaleIconFlag().getValue());
        }
        if (goodsDetailsDto.getReserveIconFlag() != null) {
            goodsDetailsDtoRequest.setReserveIconFlag(goodsDetailsDto.getReserveIconFlag().getValue());
        }
        if (goodsDetailsDto.getNewIconFlag() != null) {
            goodsDetailsDtoRequest.setNewIconFlag(goodsDetailsDto.getNewIconFlag().getValue());
        }
        if (goodsDetailsDto.getLandSendFlag() != null) {
            goodsDetailsDtoRequest.setLandSendFlag(goodsDetailsDto.getLandSendFlag().getValue());
        }
        if (goodsDetailsDto.getCoolSendFlag() != null) {
            goodsDetailsDtoRequest.setCoolSendFlag(goodsDetailsDto.getCoolSendFlag().getValue());
        }
        if (goodsDetailsDto.getReserveFlag() != null) {
            goodsDetailsDtoRequest.setReserveFlag(goodsDetailsDto.getReserveFlag().getValue());
        }
        if (goodsDetailsDto.getPriceMarkDispFlag() != null) {
            goodsDetailsDtoRequest.setPriceMarkDispFlag(goodsDetailsDto.getPriceMarkDispFlag().getValue());
        }
        if (goodsDetailsDto.getSalePriceMarkDispFlag() != null) {
            goodsDetailsDtoRequest.setSalePriceMarkDispFlag(goodsDetailsDto.getSalePriceMarkDispFlag().getValue());
        }
        if (goodsDetailsDto.getSalePriceIntegrityFlag() != null) {
            goodsDetailsDtoRequest.setSalePriceIntegrityFlag(goodsDetailsDto.getSalePriceIntegrityFlag().getValue());
        }
        if (goodsDetailsDto.getEmotionPriceType() != null) {
            goodsDetailsDtoRequest.setEmotionPriceType(goodsDetailsDto.getEmotionPriceType().getValue());
        }

        return goodsDetailsDtoRequest;
    }

    /**
     * 商品画像に変換
     *
     * @param goodsImageEntity 商品グループ画像リクエスト
     * @return 商品画像
     */
    private jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsImageEntityRequest toUnitImage(GoodsImageEntity goodsImageEntity) {

        if (ObjectUtils.isEmpty(goodsImageEntity)) {
            return null;
        }

        jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsImageEntityRequest goodsImageEntityRequest =
                        new jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsImageEntityRequest();

        goodsImageEntityRequest.setGoodsGroupSeq(goodsImageEntity.getGoodsGroupSeq());
        goodsImageEntityRequest.setGoodsSeq(goodsImageEntity.getGoodsSeq());
        goodsImageEntityRequest.setImageFileName(goodsImageEntity.getImageFileName());
        if (goodsImageEntity.getDisplayFlag() != null) {
            goodsImageEntityRequest.setDisplayFlag(goodsImageEntity.getDisplayFlag().getValue());
        }
        goodsImageEntityRequest.setRegistTime(conversionUtility.toTimeStamp(goodsImageEntity.getRegistTime()));
        goodsImageEntityRequest.setUpdateTime(conversionUtility.toTimeStamp(goodsImageEntity.getUpdateTime()));
        goodsImageEntityRequest.setTmpFilePath(goodsImageEntity.getTmpFilePath());

        return goodsImageEntityRequest;
    }

    /**
     * 商品グループ画像リストに変換
     *
     * @param goodsGroupImageEntityList 商品グループ画像リクエストリスト
     * @return 商品グループ画像リスト
     */
    private List<jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsGroupImageEntityRequest> toGoodsGroupImageEntityRequestList(
                    List<GoodsGroupImageEntity> goodsGroupImageEntityList) {

        if (CollectionUtils.isEmpty(goodsGroupImageEntityList)) {
            return new ArrayList<>();
        }

        List<jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsGroupImageEntityRequest>
                        goodsGroupImageEntityRequestList = new ArrayList<>();

        goodsGroupImageEntityList.forEach(goodsGroupImageEntity -> {
            jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsGroupImageEntityRequest goodsGroupImageEntityRequest =
                            new jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsGroupImageEntityRequest();

            goodsGroupImageEntityRequest.setGoodsGroupSeq(goodsGroupImageEntity.getGoodsGroupSeq());
            goodsGroupImageEntityRequest.setImageTypeVersionNo(goodsGroupImageEntity.getImageTypeVersionNo());
            goodsGroupImageEntityRequest.setImageFileName(goodsGroupImageEntity.getImageFileName());
            goodsGroupImageEntityRequest.setRegistTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntity.getRegistTime()));
            goodsGroupImageEntityRequest.setUpdateTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntity.getUpdateTime()));

            goodsGroupImageEntityRequestList.add(goodsGroupImageEntityRequest);
        });

        return goodsGroupImageEntityRequestList;
    }

    /**
     * 割引適用結果取得 詳細情報Mapに変換
     *
     * @param discountsResponseDetailDtoMap 割引適用結果MAP
     * @return 割引適用結果取得 詳細情報Map
     */
    private Map<String, jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetDiscountsResponseDetailDtoRequest> toDiscountsResponseDetailMap(
                    Map<String, WebApiGetDiscountsResponseDetailDto> discountsResponseDetailDtoMap) {

        if (MapUtils.isEmpty(discountsResponseDetailDtoMap)) {
            return new HashMap<>();
        }

        Map<String, jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetDiscountsResponseDetailDtoRequest>
                        discountsResponseDetailMapRequest = new HashMap<>();

        for (Map.Entry<String, WebApiGetDiscountsResponseDetailDto> entry : discountsResponseDetailDtoMap.entrySet()) {

            WebApiGetDiscountsResponseDetailDto webApiGetDiscountsResponseDetailDto = entry.getValue();
            jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetDiscountsResponseDetailDtoRequest
                            webApiGetDiscountsResponseDetailDtoRequest =
                            new jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetDiscountsResponseDetailDtoRequest();

            webApiGetDiscountsResponseDetailDtoRequest.setGoodsCode(webApiGetDiscountsResponseDetailDto.getGoodsCode());
            webApiGetDiscountsResponseDetailDtoRequest.setSaleType(webApiGetDiscountsResponseDetailDto.getSaleType());
            webApiGetDiscountsResponseDetailDtoRequest.setSaleGroupCode(
                            webApiGetDiscountsResponseDetailDto.getSaleGroupCode());
            webApiGetDiscountsResponseDetailDtoRequest.setSalePrice(webApiGetDiscountsResponseDetailDto.getSalePrice());
            webApiGetDiscountsResponseDetailDtoRequest.setQuantity(webApiGetDiscountsResponseDetailDto.getQuantity());
            webApiGetDiscountsResponseDetailDtoRequest.setSaleCode(webApiGetDiscountsResponseDetailDto.getSaleCode());
            webApiGetDiscountsResponseDetailDtoRequest.setNote(webApiGetDiscountsResponseDetailDto.getNote());
            webApiGetDiscountsResponseDetailDtoRequest.setHints(webApiGetDiscountsResponseDetailDto.getHints());
            webApiGetDiscountsResponseDetailDtoRequest.setOrderDisplay(
                            webApiGetDiscountsResponseDetailDto.getOrderDisplay());

            discountsResponseDetailMapRequest.put(entry.getKey(), webApiGetDiscountsResponseDetailDtoRequest);
        }

        return discountsResponseDetailMapRequest;
    }

    /**
     * 消費税率取得 詳細情報Mapに変換
     *
     * @param consumptionTaxRateResponseDetailDtoMap 割引適用結果MAP
     * @return 消費税率取得 詳細情報Map
     */
    private Map<String, jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetConsumptionTaxRateResponseDetailDtoRequest> toConsumptionTaxRateMap(
                    Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> consumptionTaxRateResponseDetailDtoMap) {

        if (MapUtils.isEmpty(consumptionTaxRateResponseDetailDtoMap)) {
            return new HashMap<>();
        }

        Map<String, jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetConsumptionTaxRateResponseDetailDtoRequest>
                        consumptionTaxRateMap = new HashMap<>();

        for (Map.Entry<String, WebApiGetConsumptionTaxRateResponseDetailDto> entry : consumptionTaxRateResponseDetailDtoMap.entrySet()) {

            WebApiGetConsumptionTaxRateResponseDetailDto webApiGetConsumptionTaxRateResponseDetailDto =
                            entry.getValue();
            jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetConsumptionTaxRateResponseDetailDtoRequest
                            webApiGetConsumptionTaxRateResponseDetailDtoRequest =
                            new jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetConsumptionTaxRateResponseDetailDtoRequest();

            webApiGetConsumptionTaxRateResponseDetailDtoRequest.setGoodsCode(
                            webApiGetConsumptionTaxRateResponseDetailDto.getGoodsCode());
            webApiGetConsumptionTaxRateResponseDetailDtoRequest.setTaxRate(
                            webApiGetConsumptionTaxRateResponseDetailDto.getTaxRate());

            consumptionTaxRateMap.put(entry.getKey(), webApiGetConsumptionTaxRateResponseDetailDtoRequest);
        }

        return consumptionTaxRateMap;
    }

    /**
     * チェックメッセージDtoMapに変換
     *
     * @param cartGoodsCheckMapResponse カート商品チェックMapレスポンス
     * @return チェックメッセージDtoMap
     */
    public Map<Integer, List<CheckMessageDto>> toCheckMessageDtoListMap(CartGoodsCheckMapResponse cartGoodsCheckMapResponse) {

        if (ObjectUtils.isEmpty(cartGoodsCheckMapResponse) || MapUtils.isEmpty(
                        cartGoodsCheckMapResponse.getMessages())) {
            return null;
        }

        Map<Integer, List<CheckMessageDto>> checkMessageMap = new HashMap<>();

        cartGoodsCheckMapResponse.getMessages()
                                 .forEach((key, value) -> checkMessageMap.put(conversionUtility.toInteger(key),
                                                                              toCheckMessageDtoList(value)
                                                                             ));

        return checkMessageMap;
    }

    /**
     * チェックメッセージDtoListレスポンスに変換
     *
     * @param checkMessageDtoResponseList エラーリスト
     * @return チェックメッセージDtoListレスポンス
     */
    public List<CheckMessageDto> toCheckMessageDtoList(List<jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CheckMessageDtoResponse> checkMessageDtoResponseList) {
        if (CollectionUtils.isEmpty(checkMessageDtoResponseList)) {
            return new ArrayList<>();
        }
        List<CheckMessageDto> checkMessageDtoList = new ArrayList<>();
        for (jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CheckMessageDtoResponse checkMessageDtoResponse : checkMessageDtoResponseList) {
            CheckMessageDto checkMessageDto = new CheckMessageDto();
            checkMessageDto.setMessageId(checkMessageDtoResponse.getMessageId());
            checkMessageDto.setMessage(checkMessageDtoResponse.getMessage());
            if (ObjectUtils.isNotEmpty(checkMessageDtoResponse.getArgs())) {
                checkMessageDto.setArgs(checkMessageDtoResponse.getArgs().toArray());
            }
            checkMessageDto.setError(checkMessageDtoResponse.getError() != null && checkMessageDtoResponse.getError());
            checkMessageDto.setOrderConsecutiveNo(checkMessageDtoResponse.getOrderConsecutiveNo());

            checkMessageDtoList.add(checkMessageDto);
        }

        return checkMessageDtoList;
    }

    /**
     * 請求金額算出リクエストに変換
     *
     * @param receiveOrderDto 受注Dtoクラス
     * @return 請求金額算出リクエスト
     */
    public BillPriceCalculateRequest toBillPriceCalculateRequest(ReceiveOrderDto receiveOrderDto) {
        BillPriceCalculateRequest billPriceCalculateRequest = new BillPriceCalculateRequest();

        billPriceCalculateRequest.setReceiveOrderDto(toReceiveOrderDtoJson(receiveOrderDto));

        return billPriceCalculateRequest;
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

        // PDR Migrate Customization from here
        ReceiveOrderDto receiveOrderDto = conversionUtility.toObject(receiveOrderDtoJson, ReceiveOrderDto.class);

        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();
        if (ObjectUtils.isEmpty(orderDeliveryDto)) {
            return receiveOrderDto;
        }

        List<OrderGoodsEntity> orderGoodsEntityList = orderDeliveryDto.getOrderGoodsEntityList();
        if (CollectionUtils.isEmpty(orderGoodsEntityList)) {
            return receiveOrderDto;
        }

        List<OrderReserveDeliveryDto> orderReserveDeliveryDtoList = orderDeliveryDto.getOrderReserveDeliveryDtoList();
        if (CollectionUtils.isEmpty(orderReserveDeliveryDtoList)) {
            return receiveOrderDto;
        }

        return receiveOrderDto;
        // PDR Migrate Customization to here
    }

    /**
     * 受注Dtoクラスに変換
     *
     * @param receiveOrderDtoJsonList 受注DtoクラスJSONリスト
     * @return 受注Dtoクラス
     */
    public List<ReceiveOrderDto> toReceiveOrderDtoList(List<String> receiveOrderDtoJsonList) {

        if (CollectionUtils.isEmpty(receiveOrderDtoJsonList)) {
            return new ArrayList<>();
        }

        List<ReceiveOrderDto> receiveOrderDtoList = new ArrayList<>();
        receiveOrderDtoJsonList.forEach(receiveOrderDto -> {
            receiveOrderDtoList.add(toReceiveOrderDto(receiveOrderDto));
        });

        return receiveOrderDtoList;
    }

    /**
     * 決済方法選択可能リスト取得リクエストに変換
     *
     * @param receiveOrderDto 受注Dtoクラス
     * @param allowCreditFlag クレジット決済許可フラグ
     * @return 決済方法選択可能リスト取得リクエスト
     */
    public SettlementMethodSelectListGetRequest toSettlementMethodSelectListGetRequest(ReceiveOrderDto receiveOrderDto,
                                                                                       Boolean allowCreditFlag) {
        SettlementMethodSelectListGetRequest settlementMethodSelectListGetRequest =
                        new SettlementMethodSelectListGetRequest();
        settlementMethodSelectListGetRequest.setReceiveOrderDto(toReceiveOrderDtoJson(receiveOrderDto));
        settlementMethodSelectListGetRequest.setAllowCreditFlag(allowCreditFlag);
        return settlementMethodSelectListGetRequest;
    }

    /**
     * 決済Dtoリストレスポンスリストに変換
     *
     * @param settlementDtoResponseList 決済DTOクラススリスト
     * @return 決済Dtoリストレスポンスリスト
     */
    public List<SettlementDto> toSettlementDtoResponseList(List<SettlementDtoResponse> settlementDtoResponseList) {

        if (CollectionUtils.isEmpty(settlementDtoResponseList)) {
            return new ArrayList<>();
        }

        List<SettlementDto> settlementDtoList = new ArrayList<>();

        for (SettlementDtoResponse dto : settlementDtoResponseList) {
            SettlementDto settlementDto = new SettlementDto();
            settlementDto.setSettlementDetailsDto(toSettlementDetailsDto(dto.getSettlementDetailsDto()));
            settlementDto.setConvenienceEntityList(toConvenienceEntityListResponse(dto.getConvenienceEntityList()));
            settlementDto.setCardBrandEntityList(toCardBrandResponseList(dto.getCardBrandEntityList()));
            settlementDto.setCharge(dto.getCharge());
            settlementDto.setSelectClass(dto.getSelectClass() != null && dto.getSelectClass());

            settlementDtoList.add(settlementDto);
        }

        return settlementDtoList;
    }

    /**
     * 決済詳細DTOレスポンスに変換
     *
     * @param settlementDetailsDtoResponse 決済詳細DTOクラス
     * @return 決済詳細DTOレスポンス
     */
    private SettlementDetailsDto toSettlementDetailsDto(SettlementDetailsDtoResponse settlementDetailsDtoResponse) {

        if (ObjectUtils.isEmpty(settlementDetailsDtoResponse)) {
            return null;
        }

        SettlementDetailsDto settlementDetailsDto = new SettlementDetailsDto();

        settlementDetailsDto.setSettlementMethodSeq(settlementDetailsDtoResponse.getSettlementMethodSeq());
        settlementDetailsDto.setSettlementMethodName(settlementDetailsDtoResponse.getSettlementMethodName());
        settlementDetailsDto.setSettlementMethodDisplayNamePC(
                        settlementDetailsDtoResponse.getSettlementMethodDisplayName());
        settlementDetailsDto.setOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                           settlementDetailsDtoResponse.getOpenStatus()
                                                                          ));
        settlementDetailsDto.setSettlementNotePC(settlementDetailsDtoResponse.getSettlementNote());
        settlementDetailsDto.setSettlementMethodType(EnumTypeUtil.getEnumFromValue(HTypeSettlementMethodType.class,
                                                                                   settlementDetailsDtoResponse.getSettlementMethodType()
                                                                                  ));
        settlementDetailsDto.setSettlementMethodCommissionType(
                        EnumTypeUtil.getEnumFromValue(HTypeSettlementMethodCommissionType.class,
                                                      settlementDetailsDtoResponse.getSettlementMethodCommissionType()
                                                     ));
        settlementDetailsDto.setBillType(
                        EnumTypeUtil.getEnumFromValue(HTypeBillType.class, settlementDetailsDtoResponse.getBillType()));
        settlementDetailsDto.setDeliveryMethodSeq(settlementDetailsDtoResponse.getDeliveryMethodSeq());
        settlementDetailsDto.setEqualsCommission(settlementDetailsDtoResponse.getEqualsCommission());
        settlementDetailsDto.setSettlementMethodPriceCommissionFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeSettlementMethodPriceCommissionFlag.class,
                                                      settlementDetailsDtoResponse.getSettlementMethodPriceCommissionFlag()
                                                     ));
        settlementDetailsDto.setLargeAmountDiscountPrice(settlementDetailsDtoResponse.getLargeAmountDiscountPrice());
        settlementDetailsDto.setLargeAmountDiscountCommission(
                        settlementDetailsDtoResponse.getLargeAmountDiscountCommission());
        settlementDetailsDto.setOrderDisplay(settlementDetailsDtoResponse.getOrderDisplay());
        settlementDetailsDto.setMaxPurchasedPrice(settlementDetailsDtoResponse.getMaxPurchasedPrice());
        settlementDetailsDto.setPaymentTimeLimitDayCount(settlementDetailsDtoResponse.getPaymentTimeLimitDayCount());
        settlementDetailsDto.setMinPurchasedPrice(settlementDetailsDtoResponse.getMinPurchasedPrice());
        settlementDetailsDto.setCancelTimeLimitDayCount(settlementDetailsDtoResponse.getCancelTimeLimitDayCount());
        settlementDetailsDto.setSettlementMailRequired(EnumTypeUtil.getEnumFromValue(HTypeMailRequired.class,
                                                                                     settlementDetailsDtoResponse.getSettlementMailRequired()
                                                                                    ));
        settlementDetailsDto.setEnableCardNoHolding(EnumTypeUtil.getEnumFromValue(HTypeEffectiveFlag.class,
                                                                                  settlementDetailsDtoResponse.getEnableCardNoHolding()
                                                                                 ));
        settlementDetailsDto.setEnableSecurityCode(EnumTypeUtil.getEnumFromValue(HTypeEffectiveFlag.class,
                                                                                 settlementDetailsDtoResponse.getEnableSecurityCode()
                                                                                ));
        settlementDetailsDto.setEnable3dSecure(EnumTypeUtil.getEnumFromValue(HTypeEffectiveFlag.class,
                                                                             settlementDetailsDtoResponse.getEnable3dSecure()
                                                                            ));
        settlementDetailsDto.setEnableInstallment(EnumTypeUtil.getEnumFromValue(HTypeEffectiveFlag.class,
                                                                                settlementDetailsDtoResponse.getEnableInstallment()
                                                                               ));
        settlementDetailsDto.setEnableBonusSinglePayment(EnumTypeUtil.getEnumFromValue(HTypeEffectiveFlag.class,
                                                                                       settlementDetailsDtoResponse.getEnableBonusSinglePayment()
                                                                                      ));
        settlementDetailsDto.setEnableBonusInstallment(EnumTypeUtil.getEnumFromValue(HTypeEffectiveFlag.class,
                                                                                     settlementDetailsDtoResponse.getEnableBonusInstallment()
                                                                                    ));
        settlementDetailsDto.setEnableRevolving(EnumTypeUtil.getEnumFromValue(HTypeEffectiveFlag.class,
                                                                              settlementDetailsDtoResponse.getEnableRevolving()
                                                                             ));
        settlementDetailsDto.setMaxPrice(settlementDetailsDtoResponse.getMaxPrice());
        settlementDetailsDto.setCommission(settlementDetailsDtoResponse.getCommission());

        return settlementDetailsDto;
    }

    /**
     * コンビニEntityListレスポンスに変換
     *
     * @param convenienceResponseList コンビニEntityList
     * @return コンビニEntityListレスポンス
     */
    public List<ConvenienceEntity> toConvenienceEntityListResponse(List<ConvenienceResponse> convenienceResponseList) {

        if (CollectionUtils.isEmpty(convenienceResponseList)) {
            return new ArrayList<>();
        }

        List<ConvenienceEntity> convenienceEntityList = new ArrayList<>();
        convenienceResponseList.forEach(ConvenienceResponse -> {
            ConvenienceEntity convenienceEntity = new ConvenienceEntity();

            convenienceEntity.setConveniSeq(ConvenienceResponse.getConveniSeq());
            convenienceEntity.setPayCode(ConvenienceResponse.getPayCode());
            convenienceEntity.setConveniCode(ConvenienceResponse.getConveniCode());
            convenienceEntity.setPayName(ConvenienceResponse.getPayName());
            convenienceEntity.setConveniName(ConvenienceResponse.getConveniName());
            convenienceEntity.setRegistTime(conversionUtility.toTimeStamp(ConvenienceResponse.getRegistTime()));
            convenienceEntity.setUpdateTime(conversionUtility.toTimeStamp(ConvenienceResponse.getUpdateTime()));
            convenienceEntity.setDisplayOrder(ConvenienceResponse.getDisplayOrder());

            if (ConvenienceResponse.getUseFlag() != null) {
                convenienceEntity.setUseFlag(
                                EnumTypeUtil.getEnumFromValue(HTypeUseConveni.class, ConvenienceResponse.getUseFlag()));
            }

            convenienceEntityList.add(convenienceEntity);
        });

        return convenienceEntityList;
    }

    /**
     * クレジットカードブレスポンスリストに変換
     *
     * @param cardBrandResponseList クレジットカードブランドリスト
     * @return クレジットカードブレスポンスリスト
     */
    private List<CardBrandEntity> toCardBrandResponseList(List<CardBrandResponse> cardBrandResponseList) {

        if (CollectionUtils.isEmpty(cardBrandResponseList)) {
            return new ArrayList<>();
        }

        List<CardBrandEntity> brandEntityArrayList = new ArrayList<>();

        for (CardBrandResponse cardBrandResponse : cardBrandResponseList) {
            CardBrandEntity cardBrandEntity = new CardBrandEntity();
            cardBrandEntity.setCardBrandSeq(cardBrandResponse.getCardBrandSeq());
            cardBrandEntity.setCardBrandCode(cardBrandResponse.getCardBrandCode());
            cardBrandEntity.setCardBrandName(cardBrandResponse.getCardBrandName());
            cardBrandEntity.setCardBrandDisplayPc(cardBrandResponse.getCardBrandDisplay());
            cardBrandEntity.setOrderDisplay(cardBrandResponse.getOrderDisplay());
            cardBrandEntity.setInstallment(cardBrandResponse.getInstallment());
            cardBrandEntity.setBounusSingle(cardBrandResponse.getBounusSingle());
            cardBrandEntity.setBounusInstallment(cardBrandResponse.getBounusInstallment());
            cardBrandEntity.setRevolving(cardBrandResponse.getRevolving());
            cardBrandEntity.setInstallmentCounts(cardBrandResponse.getInstallmentCounts());
            cardBrandEntity.setFrontDisplayFlag(EnumTypeUtil.getEnumFromValue(HTypeFrontDisplayFlag.class,
                                                                              cardBrandResponse.getFrontDisplayFlag()
                                                                             ));

            brandEntityArrayList.add(cardBrandEntity);
        }

        return brandEntityArrayList;
    }

    /**
     * 会員クラスに変換
     *
     * @param memberInfoEntityResponse 会員Entityレスポンス
     * @return 会員クラス
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public MemberInfoEntity toMemberInfoEntity(MemberInfoEntityResponse memberInfoEntityResponse) {

        if (memberInfoEntityResponse == null) {
            return null;
        }

        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        memberInfoEntity.setMemberInfoSeq(memberInfoEntityResponse.getMemberInfoSeq());
        memberInfoEntity.setMemberInfoStatus(memberInfoEntityResponse.getMemberInfoStatus() != null ?
                                                             EnumTypeUtil.getEnumFromValue(HTypeMemberInfoStatus.class,
                                                                                           memberInfoEntityResponse.getMemberInfoStatus()
                                                                                          ) :
                                                             null);
        memberInfoEntity.setMemberInfoUniqueId(memberInfoEntityResponse.getMemberInfoUniqueId());
        memberInfoEntity.setMemberInfoId(memberInfoEntityResponse.getMemberInfoId());
        memberInfoEntity.setMemberInfoPassword(memberInfoEntityResponse.getMemberInfoPassword());
        memberInfoEntity.setMemberInfoLastName(memberInfoEntityResponse.getMemberInfoLastName());
        memberInfoEntity.setMemberInfoFirstName(memberInfoEntityResponse.getMemberInfoFirstName());
        memberInfoEntity.setMemberInfoLastKana(memberInfoEntityResponse.getMemberInfoLastKana());
        memberInfoEntity.setMemberInfoFirstKana(memberInfoEntityResponse.getMemberInfoFirstKana());
        memberInfoEntity.setMemberInfoSex(memberInfoEntityResponse.getMemberInfoSex() != null ?
                                                          EnumTypeUtil.getEnumFromValue(HTypeSexUnnecessaryAnswer.class,
                                                                                        memberInfoEntityResponse.getMemberInfoSex()
                                                                                       ) :
                                                          null);
        memberInfoEntity.setMemberInfoBirthday(
                        conversionUtility.toTimeStamp(memberInfoEntityResponse.getMemberInfoBirthday()));
        memberInfoEntity.setMemberInfoTel(memberInfoEntityResponse.getMemberInfoTel());
        memberInfoEntity.setMemberInfoContactTel(memberInfoEntityResponse.getMemberInfoContactTel());
        memberInfoEntity.setMemberInfoZipCode(memberInfoEntityResponse.getMemberInfoZipCode());
        memberInfoEntity.setMemberInfoPrefecture(memberInfoEntityResponse.getMemberInfoPrefecture());
        memberInfoEntity.setPrefectureType(memberInfoEntityResponse.getPrefectureType() != null ?
                                                           EnumTypeUtil.getEnumFromValue(HTypePrefectureType.class,
                                                                                         memberInfoEntityResponse.getPrefectureType()
                                                                                        ) :
                                                           null);
        memberInfoEntity.setMemberInfoAddress1(memberInfoEntityResponse.getMemberInfoAddress1());
        memberInfoEntity.setMemberInfoAddress2(memberInfoEntityResponse.getMemberInfoAddress2());
        memberInfoEntity.setMemberInfoAddress3(memberInfoEntityResponse.getMemberInfoAddress3());
        memberInfoEntity.setMemberInfoAddress4(memberInfoEntityResponse.getMemberInfoAddress4());
        memberInfoEntity.setMemberInfoAddress5(memberInfoEntityResponse.getMemberInfoAddress5());
        memberInfoEntity.setMemberInfoMail(memberInfoEntityResponse.getMemberInfoMail());
        memberInfoEntity.setShopSeq(1001);
        memberInfoEntity.setAccessUid(memberInfoEntityResponse.getAccessUid());
        memberInfoEntity.setVersionNo(memberInfoEntityResponse.getVersionNo());
        memberInfoEntity.setAdmissionYmd(memberInfoEntityResponse.getAdmissionYmd());
        memberInfoEntity.setSecessionYmd(memberInfoEntityResponse.getSecessionYmd());
        memberInfoEntity.setMemo(memberInfoEntityResponse.getMemo());
        memberInfoEntity.setMemberInfoFax(memberInfoEntityResponse.getMemberInfoFax());
        memberInfoEntity.setLastLoginTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getLastLoginTime()));
        memberInfoEntity.setLastLoginUserAgent(memberInfoEntityResponse.getLastLoginUserAgent());
        memberInfoEntity.setPaymentMemberId(memberInfoEntityResponse.getPaymentMemberId());
        memberInfoEntity.setPaymentCardRegistType(memberInfoEntityResponse.getPaymentCardRegistType() != null ?
                                                                  EnumTypeUtil.getEnumFromValue(
                                                                                  HTypeCardRegistType.class,
                                                                                  memberInfoEntityResponse.getPaymentCardRegistType()
                                                                                               ) :
                                                                  null);
        memberInfoEntity.setPasswordNeedChangeFlag(memberInfoEntityResponse.getPasswordNeedChangeFlag() != null ?
                                                                   EnumTypeUtil.getEnumFromValue(
                                                                                   HTypePasswordNeedChangeFlag.class,
                                                                                   memberInfoEntityResponse.getPasswordNeedChangeFlag()
                                                                                                ) :
                                                                   null);
        memberInfoEntity.setLastLoginDeviceType(memberInfoEntityResponse.getLastLoginDeviceType() != null ?
                                                                EnumTypeUtil.getEnumFromValue(HTypeDeviceType.class,
                                                                                              memberInfoEntityResponse.getLastLoginDeviceType()
                                                                                             ) :
                                                                null);
        memberInfoEntity.setLoginFailureCount(memberInfoEntityResponse.getLoginFailureCount());
        memberInfoEntity.setAccountLockTime(
                        conversionUtility.toTimeStamp(memberInfoEntityResponse.getAccountLockTime()));
        memberInfoEntity.setRegistTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getRegistTime()));
        memberInfoEntity.setUpdateTime(conversionUtility.toTimeStamp(memberInfoEntityResponse.getUpdateTime()));
        memberInfoEntity.setCustomerNo(memberInfoEntityResponse.getCustomerNo());
        memberInfoEntity.setRepresentativeName(memberInfoEntityResponse.getRepresentativeName());
        memberInfoEntity.setBusinessType(memberInfoEntityResponse.getBusinessType() != null ?
                                                         EnumTypeUtil.getEnumFromValue(HTypeBusinessType.class,
                                                                                       memberInfoEntityResponse.getBusinessType()
                                                                                      ) :
                                                         null);
        memberInfoEntity.setSendFaxPermitFlag(memberInfoEntityResponse.getSendFaxPermitFlag() != null ?
                                                              EnumTypeUtil.getEnumFromValue(
                                                                              HTypeSendFaxPermitFlag.class,
                                                                              memberInfoEntityResponse.getSendFaxPermitFlag()
                                                                                           ) :
                                                              null);
        memberInfoEntity.setSendDirectMailFlag(memberInfoEntityResponse.getSendDirectMailFlag() != null ?
                                                               EnumTypeUtil.getEnumFromValue(
                                                                               HTypeSendDirectMailFlag.class,
                                                                               memberInfoEntityResponse.getSendDirectMailFlag()
                                                                                            ) :
                                                               null);
        memberInfoEntity.setNonConsultationDay(memberInfoEntityResponse.getNonConsultationDay());
        memberInfoEntity.setApproveStatus(memberInfoEntityResponse.getApproveStatus() != null ?
                                                          EnumTypeUtil.getEnumFromValue(HTypeApproveStatus.class,
                                                                                        memberInfoEntityResponse.getApproveStatus()
                                                                                       ) :
                                                          null);
        memberInfoEntity.setCreditPaymentUseFlag(memberInfoEntityResponse.getCreditPaymentUseFlag() != null ?
                                                                 EnumTypeUtil.getEnumFromValue(
                                                                                 HTypeCreditPaymentUseFlag.class,
                                                                                 memberInfoEntityResponse.getCreditPaymentUseFlag()
                                                                                              ) :
                                                                 null);
        memberInfoEntity.setTransferPaymentUseFlag(memberInfoEntityResponse.getTransferPaymentUseFlag() != null ?
                                                                   EnumTypeUtil.getEnumFromValue(
                                                                                   HTypeTransferPaymentUseFlag.class,
                                                                                   memberInfoEntityResponse.getTransferPaymentUseFlag()
                                                                                                ) :
                                                                   null);
        memberInfoEntity.setCashDeliveryUseFlag(memberInfoEntityResponse.getCashDeliveryUseFlag() != null ?
                                                                EnumTypeUtil.getEnumFromValue(
                                                                                HTypeCashDeliveryUseFlag.class,
                                                                                memberInfoEntityResponse.getCashDeliveryUseFlag()
                                                                                             ) :
                                                                null);
        memberInfoEntity.setDirectDebitUseFlag(memberInfoEntityResponse.getDirectDebitUseFlag() != null ?
                                                               EnumTypeUtil.getEnumFromValue(
                                                                               HTypeDirectDebitUseFlag.class,
                                                                               memberInfoEntityResponse.getDirectDebitUseFlag()
                                                                                            ) :
                                                               null);
        memberInfoEntity.setMonthlyPayUseFlag(memberInfoEntityResponse.getMonthlyPayUseFlag() != null ?
                                                              EnumTypeUtil.getEnumFromValue(
                                                                              HTypeMonthlyPayUseFlag.class,
                                                                              memberInfoEntityResponse.getMonthlyPayUseFlag()
                                                                                           ) :
                                                              null);
        memberInfoEntity.setMemberListType(memberInfoEntityResponse.getMemberListType() != null ?
                                                           EnumTypeUtil.getEnumFromValue(HTypeMemberListType.class,
                                                                                         memberInfoEntityResponse.getMemberListType()
                                                                                        ) :
                                                           null);
        memberInfoEntity.setOnlineRegistFlag(memberInfoEntityResponse.getOnlineRegistFlag() != null ?
                                                             EnumTypeUtil.getEnumFromValue(HTypeOnlineRegistFlag.class,
                                                                                           memberInfoEntityResponse.getOnlineRegistFlag()
                                                                                          ) :
                                                             null);
        memberInfoEntity.setConfDocumentType(memberInfoEntityResponse.getConfDocumentType() != null ?
                                                             EnumTypeUtil.getEnumFromValue(HTypeConfDocumentType.class,
                                                                                           memberInfoEntityResponse.getConfDocumentType()
                                                                                          ) :
                                                             null);
        memberInfoEntity.setMedicalTreatmentFlag(memberInfoEntityResponse.getMedicalTreatmentFlag());
        memberInfoEntity.setMedicalTreatmentMemo(memberInfoEntityResponse.getMedicalTreatmentMemo());
        memberInfoEntity.setMetalPermitFlag(memberInfoEntityResponse.getMetalPermitFlag() != null ?
                                                            EnumTypeUtil.getEnumFromValue(HTypeMetalPermitFlag.class,
                                                                                          memberInfoEntityResponse.getMetalPermitFlag()
                                                                                         ) :
                                                            null);
        // 2023-renew No79 from here
        memberInfoEntity.setOrderCompletePermitFlag(memberInfoEntityResponse.getOrderCompletePermitFlag() != null ?
                                                                    EnumTypeUtil.getEnumFromValue(
                                                                                    HTypeOrderCompletePermitFlag.class,
                                                                                    memberInfoEntityResponse.getOrderCompletePermitFlag()
                                                                                                 ) :
                                                                    null);
        memberInfoEntity.setDeliveryCompletePermitFlag(
                        memberInfoEntityResponse.getDeliveryCompletePermitFlag() != null ?
                                        EnumTypeUtil.getEnumFromValue(HTypeDeliveryCompletePermitFlag.class,
                                                                      memberInfoEntityResponse.getDeliveryCompletePermitFlag()
                                                                     ) :
                                        null);
        // 2023-renew No79 to here
        memberInfoEntity.setAccountingType(memberInfoEntityResponse.getAccountingType() != null ?
                                                           EnumTypeUtil.getEnumFromValue(HTypeAccountingType.class,
                                                                                         memberInfoEntityResponse.getAccountingType()
                                                                                        ) :
                                                           null);
        memberInfoEntity.setOnlineLoginAdvisability(memberInfoEntityResponse.getOnlineLoginAdvisability() != null ?
                                                                    EnumTypeUtil.getEnumFromValue(
                                                                                    HTypeOnlineLoginAdvisability.class,
                                                                                    memberInfoEntityResponse.getOnlineLoginAdvisability()
                                                                                                 ) :
                                                                    null);
        memberInfoEntity.setSendMailPermitFlag(memberInfoEntityResponse.getSendMailPermitFlag() != null ?
                                                               EnumTypeUtil.getEnumFromValue(
                                                                               HTypeSendMailPermitFlag.class,
                                                                               memberInfoEntityResponse.getSendMailPermitFlag()
                                                                                            ) :
                                                               null);
        memberInfoEntity.setSendMailStartTime(
                        conversionUtility.toTimeStamp(memberInfoEntityResponse.getSendMailStartTime()));
        memberInfoEntity.setSendMailStopTime(
                        conversionUtility.toTimeStamp(memberInfoEntityResponse.getSendMailStopTime()));
        memberInfoEntity.setNoAntiSocialFlag(memberInfoEntityResponse.getNoAntiSocialFlag() != null ?
                                                             EnumTypeUtil.getEnumFromValue(HTypeNoAntiSocialFlag.class,
                                                                                           memberInfoEntityResponse.getNoAntiSocialFlag()
                                                                                          ) :
                                                             null);

        return memberInfoEntity;
    }

    /**
     * 配送情報取得リクエストに変換
     *
     * @param orderGoodsEntityList 受注商品リスト
     * @param reqDto 配送情報取得
     * @param checkMessageDtoList チェックメッセージDtoリスト
     * @return 配送情報取得リクエスト
     */
    public OrderDeliveryInformationRequest toOrderDeliveryInformationRequest(List<OrderGoodsEntity> orderGoodsEntityList,
                                                                             WebApiGetDeliveryInformationRequestDto reqDto,
                                                                             List<CheckMessageDto> checkMessageDtoList) {
        OrderDeliveryInformationRequest orderDeliveryInformationRequest = new OrderDeliveryInformationRequest();

        orderDeliveryInformationRequest.setOrderGoodsEntityList(toOrderGoodsEntityList(orderGoodsEntityList));
        orderDeliveryInformationRequest.setReqDto(toReqDto(reqDto));
        orderDeliveryInformationRequest.setCheckMessageDtoList(toCheckMessageDtoRequestList(checkMessageDtoList));

        return orderDeliveryInformationRequest;
    }

    /**
     * 受注商品クラスリストに変換
     *
     * @param orderGoodsEntityList 受注商品リクエストリスト
     * @return 受注商品クラスリスト
     */
    public List<OrderGoodsRequest> toOrderGoodsEntityList(List<OrderGoodsEntity> orderGoodsEntityList) {

        if (CollectionUtil.isEmpty(orderGoodsEntityList)) {
            return new ArrayList<>();
        }

        List<OrderGoodsRequest> goodsRequestArrayList = new ArrayList<>();

        orderGoodsEntityList.forEach(orderGoodsEntity -> {

            OrderGoodsRequest orderGoodsRequest = new OrderGoodsRequest();

            orderGoodsRequest.setOrderSeq(orderGoodsEntity.getOrderSeq());
            orderGoodsRequest.setOrderGoodsVersionNo(orderGoodsEntity.getOrderGoodsVersionNo());
            orderGoodsRequest.setOrderConsecutiveNo(orderGoodsEntity.getOrderConsecutiveNo());
            orderGoodsRequest.setGoodsSeq(orderGoodsEntity.getGoodsSeq());
            orderGoodsRequest.setOrderDisplay(orderGoodsEntity.getOrderDisplay());
            orderGoodsRequest.setProcessTime(conversionUtility.toTimeStamp(orderGoodsEntity.getProcessTime()));
            orderGoodsRequest.setGoodsGroupCode(orderGoodsEntity.getGoodsGroupCode());
            orderGoodsRequest.setGoodsCode(orderGoodsEntity.getGoodsCode());
            orderGoodsRequest.setJanCode(orderGoodsEntity.getJanCode());
            orderGoodsRequest.setGoodsGroupName(orderGoodsEntity.getGoodsGroupName());
            orderGoodsRequest.setTaxRate(orderGoodsEntity.getTaxRate());
            orderGoodsRequest.setGoodsPrice(orderGoodsEntity.getGoodsPrice());
            orderGoodsRequest.setPreGoodsCount(orderGoodsEntity.getPreGoodsCount());
            orderGoodsRequest.setGoodsCount(orderGoodsEntity.getGoodsCount());
            orderGoodsRequest.setUnitValue1(orderGoodsEntity.getUnitValue1());
            orderGoodsRequest.setUnitValue2(orderGoodsEntity.getUnitValue2());
            orderGoodsRequest.setDeliveryType(orderGoodsEntity.getDeliveryType());
            orderGoodsRequest.setOrderSetting1(orderGoodsEntity.getOrderSetting1());
            orderGoodsRequest.setOrderSetting2(orderGoodsEntity.getOrderSetting2());
            orderGoodsRequest.setOrderSetting3(orderGoodsEntity.getOrderSetting3());
            orderGoodsRequest.setOrderSetting4(orderGoodsEntity.getOrderSetting4());
            orderGoodsRequest.setOrderSetting5(orderGoodsEntity.getOrderSetting5());
            orderGoodsRequest.setOrderSetting6(orderGoodsEntity.getOrderSetting6());
            orderGoodsRequest.setOrderSetting7(orderGoodsEntity.getOrderSetting7());
            orderGoodsRequest.setOrderSetting8(orderGoodsEntity.getOrderSetting8());
            orderGoodsRequest.setOrderSetting9(orderGoodsEntity.getOrderSetting9());
            orderGoodsRequest.setOrderSetting10(orderGoodsEntity.getOrderSetting10());
            orderGoodsRequest.setRegistTime(conversionUtility.toTimeStamp(orderGoodsEntity.getRegistTime()));
            orderGoodsRequest.setUpdateTime(conversionUtility.toTimeStamp(orderGoodsEntity.getUpdateTime()));
            orderGoodsRequest.setGroupCode(orderGoodsEntity.getGroupCode());
            orderGoodsRequest.setSaleCode(orderGoodsEntity.getSaleCode());
            orderGoodsRequest.setNote(orderGoodsEntity.getNote());
            orderGoodsRequest.setHints(orderGoodsEntity.getHints());
            orderGoodsRequest.setPrice(orderGoodsEntity.getPrice());
            orderGoodsRequest.setBundleFlag(orderGoodsEntity.isBundleFlag());
            orderGoodsRequest.setOrderSerial(orderGoodsEntity.getOrderSerial());
            orderGoodsRequest.setUnitDiscountPrice(orderGoodsEntity.getUnitDiscountPrice());
            orderGoodsRequest.setStockDate(conversionUtility.toTimeStamp(orderGoodsEntity.getStockDate()));
            orderGoodsRequest.setDeliveryYesNo(orderGoodsEntity.getDeliveryYesNo());
            orderGoodsRequest.setDipendingOnReceiptOrderCode(orderGoodsEntity.getDipendingOnReceiptOrderCode());

            if (orderGoodsEntity.getTotalingType() != null) {
                orderGoodsRequest.setTotalingType(orderGoodsEntity.getTotalingType().getValue());
            }
            if (orderGoodsEntity.getSalesFlag() != null) {
                orderGoodsRequest.setSalesFlag(orderGoodsEntity.getSalesFlag().getValue());
            }
            if (orderGoodsEntity.getGoodsTaxType() != null) {
                orderGoodsRequest.setGoodsTaxType(orderGoodsEntity.getGoodsTaxType().getValue());
            }
            if (orderGoodsEntity.getFreeDeliveryFlag() != null) {
                orderGoodsRequest.setFreeDeliveryFlag(orderGoodsEntity.getFreeDeliveryFlag().getValue());
            }
            if (orderGoodsEntity.getIndividualDeliveryType() != null) {
                orderGoodsRequest.setIndividualDeliveryType(orderGoodsEntity.getIndividualDeliveryType().getValue());
            }
            if (orderGoodsEntity.getDeliverySlipFlag() != null) {
                orderGoodsRequest.setDeliverySlipFlag(orderGoodsEntity.getDeliverySlipFlag().getValue());
            }
            if (orderGoodsEntity.getDiscountsType() != null) {
                orderGoodsRequest.setDiscountsType(orderGoodsEntity.getDiscountsType().getValue());
            }
            if (orderGoodsEntity.getStockManagementFlag() != null) {
                orderGoodsRequest.setStockManagementFlag(orderGoodsEntity.getStockManagementFlag().getValue());
            }

            goodsRequestArrayList.add(orderGoodsRequest);
        });

        return goodsRequestArrayList;
    }

    /**
     * 配送情報取得に変換
     *
     * @param webApiGetDeliveryInformationRequestDtoRequest API連携リクエストDTOリクエスト
     * @return 配送情報取得
     */
    public WebApiGetDeliveryInformationRequestDtoRequest toReqDto(WebApiGetDeliveryInformationRequestDto webApiGetDeliveryInformationRequestDtoRequest) {

        if (ObjectUtils.isEmpty(webApiGetDeliveryInformationRequestDtoRequest)) {
            return null;
        }

        WebApiGetDeliveryInformationRequestDtoRequest reqDto = new WebApiGetDeliveryInformationRequestDtoRequest();

        reqDto.setOrderCustomerNo(webApiGetDeliveryInformationRequestDtoRequest.getOrderCustomerNo());
        reqDto.setDeliveryCustomerNo(webApiGetDeliveryInformationRequestDtoRequest.getDeliveryCustomerNo());
        reqDto.setDeliveryZipcode(webApiGetDeliveryInformationRequestDtoRequest.getDeliveryZipcode());

        return reqDto;
    }

    /**
     * チェックメッセージDtoリストに変換
     *
     * @param messageDtoList チェックメッセージDtoリストリクエスト
     * @return チェックメッセージDtoリスト
     */
    public List<CheckMessageDtoRequest> toCheckMessageDtoRequestList(List<CheckMessageDto> messageDtoList) {

        if (CollectionUtil.isEmpty(messageDtoList)) {
            return new ArrayList<>();
        }

        List<CheckMessageDtoRequest> checkMessageDtoList = new ArrayList<>();

        messageDtoList.forEach(checkMessageDto -> {
            CheckMessageDtoRequest checkMessageDtoRequest = new CheckMessageDtoRequest();

            checkMessageDtoRequest.setMessageId(checkMessageDto.getMessageId());
            checkMessageDtoRequest.setArgs(Arrays.asList(checkMessageDto.getArgs()));
            checkMessageDtoRequest.setMessage(checkMessageDto.getMessage());
            checkMessageDtoRequest.setOrderConsecutiveNo(checkMessageDto.getOrderConsecutiveNo());
            checkMessageDtoRequest.setError(checkMessageDto.isError());

            checkMessageDtoList.add(checkMessageDtoRequest);
        });

        return checkMessageDtoList;
    }

    /**
     * 配送情報取得レスポンスに変換
     *
     * @param orderDeliveryInformationDtoResponse 配送情報取得 詳細情報
     * @return 配送情報取得レスポンス
     */
    public WebApiGetDeliveryInformationResponseDetailDto toWebApiGetDeliveryInformationResponseDetailDto(
                    OrderDeliveryInformationDtoResponse orderDeliveryInformationDtoResponse) {

        if (ObjectUtils.isEmpty(orderDeliveryInformationDtoResponse)) {
            return null;
        }

        WebApiGetDeliveryInformationResponseDetailDto webApiGetDeliveryInformationResponseDetailDto =
                        new WebApiGetDeliveryInformationResponseDetailDto();

        webApiGetDeliveryInformationResponseDetailDto.setDeliveryAssignFlag(
                        orderDeliveryInformationDtoResponse.getDeliveryAssignFlag());
        webApiGetDeliveryInformationResponseDetailDto.setDeliveryCompanyCode(
                        orderDeliveryInformationDtoResponse.getDeliveryCompanyCode());
        webApiGetDeliveryInformationResponseDetailDto.setDeliveryFlag(
                        orderDeliveryInformationDtoResponse.getDeliveryFlag());
        webApiGetDeliveryInformationResponseDetailDto.setNodeliveryGoodsCode(
                        orderDeliveryInformationDtoResponse.getNodeliveryGoodsCode());
        // 2023-renew No14 from here
        webApiGetDeliveryInformationResponseDetailDto.setComEarlyReceiveDate(
                        conversionUtility.toTimeStamp(orderDeliveryInformationDtoResponse.getComEarlyReceiveDate()));
        // 2023-renew No14 to here
        if (CollectionUtil.isNotEmpty(orderDeliveryInformationDtoResponse.getDateInfo())) {
            webApiGetDeliveryInformationResponseDetailDto.setDateInfo(
                            toWebApiGetDeliveryInformationResponseDetailDateInfoDtoList(
                                            orderDeliveryInformationDtoResponse.getDateInfo()));
        }

        return webApiGetDeliveryInformationResponseDetailDto;
    }

    /**
     * 配送情報取得 詳細情報-日付情報リストレスポンスに変換
     *
     * @param webApiGetDeliveryInformationResponseDetailDateInfoDtoResponseList WEB-API連携取得結果DTOクラス - 配送情報取得 詳細情報-日付情報
     * @return 配送情報取得 詳細情報-日付情報リストレスポンス
     */
    private List<WebApiGetDeliveryInformationResponseDetailDateInfoDto> toWebApiGetDeliveryInformationResponseDetailDateInfoDtoList(
                    List<WebApiGetDeliveryInformationResponseDetailDateInfoDtoResponse> webApiGetDeliveryInformationResponseDetailDateInfoDtoResponseList) {

        if (CollectionUtil.isEmpty(webApiGetDeliveryInformationResponseDetailDateInfoDtoResponseList)) {
            return new ArrayList<>();
        }
        List<WebApiGetDeliveryInformationResponseDetailDateInfoDto>
                        WebApiGetDeliveryInformationResponseDetailDateInfoDtoList = new ArrayList<>();

        webApiGetDeliveryInformationResponseDetailDateInfoDtoResponseList.forEach(
                        webApiGetDeliveryInformationResponseDetailDateInfoDtoResponse -> {
                            WebApiGetDeliveryInformationResponseDetailDateInfoDto
                                            webApiGetDeliveryInformationResponseDetailDateInfoDto =
                                            new WebApiGetDeliveryInformationResponseDetailDateInfoDto();

                            webApiGetDeliveryInformationResponseDetailDateInfoDto.setReceiveDate(
                                            conversionUtility.toTimeStamp(
                                                            webApiGetDeliveryInformationResponseDetailDateInfoDtoResponse.getReceiveDate()));
                            webApiGetDeliveryInformationResponseDetailDateInfoDto.setDispTimeZoneCode(
                                            webApiGetDeliveryInformationResponseDetailDateInfoDtoResponse.getDispTimeZoneCode());

                            WebApiGetDeliveryInformationResponseDetailDateInfoDtoList.add(
                                            webApiGetDeliveryInformationResponseDetailDateInfoDto);
                        });

        return WebApiGetDeliveryInformationResponseDetailDateInfoDtoList;
    }

    /**
     * お届け先参照レスポンスに変換
     *
     * @param webApiGetDestinationResponse WEB-API連携取得結果DTO基底クラス
     * @return お届け先参照レスポンス
     */
    public WebApiGetDestinationResponseDto toAbstractWebApiResponseDto(WebApiGetDestinationResponse webApiGetDestinationResponse) {
        if (webApiGetDestinationResponse == null) {
            return null;
        }

        WebApiGetDestinationResponseDto webApiGetDestinationResponseDto = new WebApiGetDestinationResponseDto();

        webApiGetDestinationResponseDto.setInfo(
                        toWebApiGetDestinationResponseDetailDtoList(webApiGetDestinationResponse.getInfo()));
        webApiGetDestinationResponseDto.setResult(
                        toAbstractWebApiResponseResultDto(webApiGetDestinationResponse.getResult()));

        return webApiGetDestinationResponseDto;
    }

    /**
     * お届け先参照詳細情報クラスリストに変換
     *
     * @param webApiGetDestinationResponseDetailDtoResponseList お届け先参照詳細情報レスポンスリスト
     * @return お届け先参照詳細情報クラスリスト
     */
    public List<WebApiGetDestinationResponseDetailDto> toWebApiGetDestinationResponseDetailDtoList(List<WebApiGetDestinationResponseDetailDtoResponse> webApiGetDestinationResponseDetailDtoResponseList) {

        if (CollectionUtils.isEmpty(webApiGetDestinationResponseDetailDtoResponseList)) {
            return new ArrayList<>();
        }

        List<WebApiGetDestinationResponseDetailDto> webApiGetDestinationResponseDetailDtoList = new ArrayList<>();

        for (WebApiGetDestinationResponseDetailDtoResponse webApiGetDestinationResponseDetailDtoResponse : webApiGetDestinationResponseDetailDtoResponseList) {
            WebApiGetDestinationResponseDetailDto webApiGetDestinationResponseDetailDto =
                            new WebApiGetDestinationResponseDetailDto();

            webApiGetDestinationResponseDetailDto.setReceiveCustomerNo(
                            webApiGetDestinationResponseDetailDtoResponse.getReceiveCustomerNo());
            webApiGetDestinationResponseDetailDto.setOfficeName(
                            webApiGetDestinationResponseDetailDtoResponse.getOfficeName());
            webApiGetDestinationResponseDetailDto.setOfficeKana(
                            webApiGetDestinationResponseDetailDtoResponse.getOfficeKana());
            webApiGetDestinationResponseDetailDto.setRepresentative(
                            webApiGetDestinationResponseDetailDtoResponse.getRepresentative());
            webApiGetDestinationResponseDetailDto.setBusinessType(
                            webApiGetDestinationResponseDetailDtoResponse.getBusinessType());
            webApiGetDestinationResponseDetailDto.setTel(webApiGetDestinationResponseDetailDtoResponse.getTel());
            webApiGetDestinationResponseDetailDto.setZipCode(
                            webApiGetDestinationResponseDetailDtoResponse.getZipCode());
            webApiGetDestinationResponseDetailDto.setCity(webApiGetDestinationResponseDetailDtoResponse.getCity());
            webApiGetDestinationResponseDetailDto.setAddress(
                            webApiGetDestinationResponseDetailDtoResponse.getAddress());
            webApiGetDestinationResponseDetailDto.setBuilding(
                            webApiGetDestinationResponseDetailDtoResponse.getBuilding());
            webApiGetDestinationResponseDetailDto.setOther(webApiGetDestinationResponseDetailDtoResponse.getOther());
            webApiGetDestinationResponseDetailDto.setApprovalFlag(
                            webApiGetDestinationResponseDetailDtoResponse.getApprovalFlag());

            webApiGetDestinationResponseDetailDtoList.add(webApiGetDestinationResponseDetailDto);
        }

        return webApiGetDestinationResponseDetailDtoList;
    }

    /**
     * WEB-API連携取得結果DTO共通情報クラスに変換
     *
     * @param abstractWebApiResponseResultDtoResponse API連携取得結果DTO共通情報レスポンス
     * @return WEB-API連携取得結果DTO共通情報クラス
     */
    public AbstractWebApiResponseResultDto toAbstractWebApiResponseResultDto(AbstractWebApiResponseResultDtoResponse abstractWebApiResponseResultDtoResponse) {

        if (ObjectUtils.isEmpty(abstractWebApiResponseResultDtoResponse)) {
            return null;
        }

        AbstractWebApiResponseResultDto abstractWebApiResponseResultDto = new AbstractWebApiResponseResultDto();

        abstractWebApiResponseResultDto.setMessage(abstractWebApiResponseResultDtoResponse.getMessage());
        abstractWebApiResponseResultDto.setStatus(abstractWebApiResponseResultDtoResponse.getStatus());

        return abstractWebApiResponseResultDto;
    }

    /**
     * 商品在庫情報リクエストに変換
     *
     * @param goodsCodeList 商品コードリスト
     * @param quantityList  数量リスト
     * @param customerNo    顧客番号
     * @return 商品在庫情報リクエスト
     */
    public OrderGetStockRequest toOrderGetStockRequest(List<String> goodsCodeList,
                                                       List<String> quantityList,
                                                       Integer customerNo) {
        OrderGetStockRequest orderGetStockRequest = new OrderGetStockRequest();
        orderGetStockRequest.setGoodsCodeList(goodsCodeList);
        orderGetStockRequest.setQuantityList(quantityList);
        orderGetStockRequest.setCustomerNo(customerNo);
        return orderGetStockRequest;
    }

    /**
     * 商品在庫情報Mapレスポンスに変換
     *
     * @param orderGetStockMapResponse 在庫数取得 詳細情報 Map
     * @return 商品在庫情報Mapレスポンス
     */
    public Map<String, WebApiGetStockResponseDetailDto> toWebApiGetStockResponseDetailDtoMap(OrderGetStockMapResponse orderGetStockMapResponse) {

        Map<String, WebApiGetStockResponseDetailDto> webApiGetStockResponseDetailDtoMap = new HashMap<>();

        if (MapUtils.isNotEmpty(orderGetStockMapResponse.getStockMap())) {
            for (Map.Entry<String, WebApiGetStockResponseDetailDtoResponse> entry : orderGetStockMapResponse.getStockMap()
                                                                                                            .entrySet()) {
                webApiGetStockResponseDetailDtoMap.put(
                                entry.getKey(), toWebApiGetStockResponseDetailDto(entry.getValue()));
            }
        }

        return webApiGetStockResponseDetailDtoMap;
    }

    /**
     * 在庫数取得 詳細情報クラスに変換
     *
     * @param webApiGetStockResponseDetailDtoResponse 商品在庫数取得 詳細情報 レスポンス
     * @return 在庫数取得 詳細情報クラス
     */
    public WebApiGetStockResponseDetailDto toWebApiGetStockResponseDetailDto(WebApiGetStockResponseDetailDtoResponse webApiGetStockResponseDetailDtoResponse) {

        if (ObjectUtils.isEmpty(webApiGetStockResponseDetailDtoResponse)) {
            return null;
        }

        WebApiGetStockResponseDetailDto webApiGetStockResponseDetailDto = new WebApiGetStockResponseDetailDto();

        webApiGetStockResponseDetailDto.setGoodsCode(webApiGetStockResponseDetailDtoResponse.getGoodsCode());
        webApiGetStockResponseDetailDto.setStockQuantity(webApiGetStockResponseDetailDtoResponse.getStockQuantity());
        webApiGetStockResponseDetailDto.setSaleYesNo(webApiGetStockResponseDetailDtoResponse.getSaleYesNo());
        webApiGetStockResponseDetailDto.setSaleNgMessage(webApiGetStockResponseDetailDtoResponse.getSaleNgMessage());
        webApiGetStockResponseDetailDto.setStockDate(
                        conversionUtility.toTimeStamp(webApiGetStockResponseDetailDtoResponse.getStockDate()));
        webApiGetStockResponseDetailDto.setDeliveryYesNo(webApiGetStockResponseDetailDtoResponse.getDeliveryYesNo());

        return webApiGetStockResponseDetailDto;

    }

    /**
     * 注文可能チェックリクエストに変換
     *
     * @param receiveOrderDto 受注Dtoクラス
     * @param commonInfo      共通情報
     * @return 注文可能チェックリクエスト
     */
    public OrderCheckRequest toOrderCheckRequest(ReceiveOrderDto receiveOrderDto, CommonInfo commonInfo) {
        OrderCheckRequest orderCheckRequest = new OrderCheckRequest();

        orderCheckRequest.setReceiveOrderDto(toReceiveOrderDtoJson(receiveOrderDto));
        orderCheckRequest.setMemberInfoId(commonInfo.getCommonInfoUser().getMemberInfoId());
        orderCheckRequest.setMemberInfoSeq(commonInfo.getCommonInfoUser().getMemberInfoSeq());
        orderCheckRequest.setAdminId(commonInfo.getCommonInfoAdministrator().getAdministratorId());

        return orderCheckRequest;
    }

    /**
     * 注文メッセージDtoクラスに変換
     *
     * @param orderMessageDtoResponse 注文メッセージDtoレスポンス
     * @return 注文メッセージDtoクラス
     */
    public OrderMessageDto toOrderMessageDto(OrderMessageDtoResponse orderMessageDtoResponse) {

        if (ObjectUtils.isEmpty(orderMessageDtoResponse)) {
            return null;
        }

        OrderMessageDto orderMessageDto = new OrderMessageDto();

        orderMessageDto.setOrderMessageList(toCheckMessageDtoListOrder(orderMessageDtoResponse.getOrderMessageList()));
        orderMessageDto.setOrderGoodsMessageMap(
                        toOrderGoodsMessageMap(orderMessageDtoResponse.getOrderGoodsMessageMap()));
        orderMessageDto.setOrderGoodsMessageMapMap(
                        toOrderGoodsMessageMapMap(orderMessageDtoResponse.getOrderGoodsMessageMapMap()));

        return orderMessageDto;
    }

    /**
     * 受注商品詳細メッセージマップレスポンスに変換
     * Key=注文連番, 子マップKey=商品SEQ
     *
     * @param CheckMessageDtoResponseListMapMap 受注商品詳細メッセージマップ：Key=注文連番, 子マップKey=商品SEQ
     * @return 受注商品詳細メッセージマップレスポンス：Key=注文連番, 子マップKey=商品SEQ
     */
    private Map<Integer, Map<Integer, List<CheckMessageDto>>> toOrderGoodsMessageMapMap(Map<String, Map<String, List<CheckMessageDtoResponse>>> CheckMessageDtoResponseListMapMap) {

        if (MapUtils.isEmpty(CheckMessageDtoResponseListMapMap)) {
            return new HashMap<>();
        }

        Map<Integer, Map<Integer, List<CheckMessageDto>>> checkMessageDtoListMapMap = new HashMap<>();

        for (Map.Entry<String, Map<String, List<CheckMessageDtoResponse>>> entry : CheckMessageDtoResponseListMapMap.entrySet()) {
            Integer newKey = Integer.parseInt(entry.getKey());
            Map<Integer, List<CheckMessageDto>> newValue = toOrderGoodsMessageMap(entry.getValue());
            checkMessageDtoListMapMap.put(newKey, newValue);
        }

        return checkMessageDtoListMapMap;
    }

    /**
     * 受注商品詳細メッセージマップレスポンスに変換
     * Key=注文連番, 子マップKey=商品SEQ
     *
     * @param CheckMessageDtoResponseListMap 受注商品詳細メッセージマップ：Key=注文連番, 子マップKey=商品SEQ
     * @return 受注商品詳細メッセージマップレスポンス：Key=注文連番, 子マップKey=商品SEQ
     */
    private Map<Integer, List<CheckMessageDto>> toOrderGoodsMessageMap(Map<String, List<CheckMessageDtoResponse>> CheckMessageDtoResponseListMap) {

        if (MapUtils.isEmpty(CheckMessageDtoResponseListMap)) {
            return new HashMap<>();
        }

        Map<Integer, List<CheckMessageDto>> map = new HashMap<>();

        for (Map.Entry<String, List<CheckMessageDtoResponse>> entry : CheckMessageDtoResponseListMap.entrySet()) {
            Integer newKey = Integer.parseInt(entry.getKey());
            List<CheckMessageDto> newValue = toCheckMessageDtoListOrder(entry.getValue());
            map.put(newKey, newValue);
        }

        return map;
    }

    /**
     * チェックメッセージDtoクラスレスポンスリストに変換
     *
     * @param checkMessageDtoResponseList チェックメッセージDtoクラスリスト
     * @return チェックメッセージDtoクラスレスポンスリスト
     */
    public List<CheckMessageDto> toCheckMessageDtoListOrder(List<CheckMessageDtoResponse> checkMessageDtoResponseList) {

        if (CollectionUtils.isEmpty(checkMessageDtoResponseList)) {
            return new ArrayList<>();
        }

        List<CheckMessageDto> messageDtoArrayList = new ArrayList<>();

        for (CheckMessageDtoResponse checkMessageDtoResponse : checkMessageDtoResponseList) {
            CheckMessageDto checkMessageDto = new CheckMessageDto();

            checkMessageDto.setMessageId(checkMessageDtoResponse.getMessageId());
            if (CollectionUtil.isNotEmpty(checkMessageDtoResponse.getArgs())) {
                checkMessageDto.setArgs(checkMessageDtoResponse.getArgs().toArray());
            }
            checkMessageDto.setMessage(checkMessageDtoResponse.getMessage());
            checkMessageDto.setOrderConsecutiveNo(checkMessageDtoResponse.getOrderConsecutiveNo());
            checkMessageDto.setError(checkMessageDtoResponse.getError() != null && checkMessageDtoResponse.getError());

            messageDtoArrayList.add(checkMessageDto);
        }

        return messageDtoArrayList;
    }

    /**
     * 配送方法選択可能リスト取得リクエストに変換
     *
     * @param receiveOrderDto 受注Dtoクラス
     * @param available       利用可能、不可能どちらかを指定。null..全部 / true..利用可能のみ / false..利用不可能のみ
     * @return 配送方法選択可能リスト取得リクエスト
     */
    public DeliveryMethodSelectListGetRequest toDeliveryMethodSelectListGetRequest(ReceiveOrderDto receiveOrderDto,
                                                                                   Boolean available) {
        DeliveryMethodSelectListGetRequest deliveryMethodSelectListGetRequest =
                        new DeliveryMethodSelectListGetRequest();

        deliveryMethodSelectListGetRequest.setReceiveOrderDto(toReceiveOrderDtoJson(receiveOrderDto));
        deliveryMethodSelectListGetRequest.setAvailable(available);

        return deliveryMethodSelectListGetRequest;
    }

    /**
     * 配送Dtoリストレスポンスに変換
     *
     * @param deliveryDtoListResponse 配送DTOリスト
     * @return 配送Dtoリストレスポンス
     */
    public List<DeliveryDto> toDeliveryDtoList(DeliveryDtoListResponse deliveryDtoListResponse) {

        if (ObjectUtils.isEmpty(deliveryDtoListResponse) || CollectionUtil.isEmpty(
                        deliveryDtoListResponse.getDeliveryDtoList())) {
            return new ArrayList<>();
        }

        List<DeliveryDto> deliveryDtoList = new ArrayList<>();

        deliveryDtoListResponse.getDeliveryDtoList().forEach(deliveryDtoResponse -> {
            DeliveryDto deliveryDto = new DeliveryDto();

            deliveryDto.setDeliveryDetailsDto(toDeliveryDetailsDto(deliveryDtoResponse.getDeliveryDetailsDto()));
            deliveryDto.setCarriage(deliveryDtoResponse.getCarriage());
            deliveryDto.setSelectClass(
                            deliveryDtoResponse.getSelectClass() != null && deliveryDtoResponse.getSelectClass());
            deliveryDto.setReceiverDateDto(toReceiverDateDto(deliveryDtoResponse.getReceiverDateDto()));
            deliveryDto.setSpecialChargeAreaFlag(deliveryDtoResponse.getSpecialChargeAreaFlag() != null
                                                 && deliveryDtoResponse.getSpecialChargeAreaFlag());

            deliveryDtoList.add(deliveryDto);
        });

        return deliveryDtoList;
    }

    /**
     * 配送方法詳細DTOレスポンスに変換
     *
     * @param deliveryDetailsDtoResponse 配送方法詳細DTO
     * @return 配送方法詳細DTOレスポンス
     */
    private DeliveryDetailsDto toDeliveryDetailsDto(DeliveryDetailsDtoResponse deliveryDetailsDtoResponse) {

        if (ObjectUtils.isEmpty(deliveryDetailsDtoResponse)) {
            return null;
        }

        DeliveryDetailsDto deliveryDetailsDto = new DeliveryDetailsDto();

        deliveryDetailsDto.setDeliveryMethodSeq(deliveryDetailsDtoResponse.getDeliveryMethodSeq());
        deliveryDetailsDto.setDeliveryMethodName(deliveryDetailsDtoResponse.getDeliveryMethodName());
        deliveryDetailsDto.setDeliveryMethodDisplayNamePC(deliveryDetailsDtoResponse.getDeliveryMethodDisplayName());
        deliveryDetailsDto.setDeliveryNotePC(deliveryDetailsDtoResponse.getDeliveryNote());
        deliveryDetailsDto.setEqualsCarriage(deliveryDetailsDtoResponse.getEqualsCarriage());
        deliveryDetailsDto.setLargeAmountDiscountPrice(deliveryDetailsDtoResponse.getLargeAmountDiscountPrice());
        deliveryDetailsDto.setLargeAmountDiscountCarriage(deliveryDetailsDtoResponse.getLargeAmountDiscountCarriage());
        deliveryDetailsDto.setDeliveryLeadTime(deliveryDetailsDtoResponse.getDeliveryLeadTime() == null ?
                                                               0 :
                                                               deliveryDetailsDtoResponse.getDeliveryLeadTime());
        deliveryDetailsDto.setPossibleSelectDays(deliveryDetailsDtoResponse.getPossibleSelectDays() == null ?
                                                                 0 :
                                                                 deliveryDetailsDtoResponse.getPossibleSelectDays());
        deliveryDetailsDto.setReceiverTimeZone1(deliveryDetailsDtoResponse.getReceiverTimeZone1());
        deliveryDetailsDto.setReceiverTimeZone2(deliveryDetailsDtoResponse.getReceiverTimeZone2());
        deliveryDetailsDto.setReceiverTimeZone3(deliveryDetailsDtoResponse.getReceiverTimeZone3());
        deliveryDetailsDto.setReceiverTimeZone4(deliveryDetailsDtoResponse.getReceiverTimeZone4());
        deliveryDetailsDto.setReceiverTimeZone5(deliveryDetailsDtoResponse.getReceiverTimeZone5());
        deliveryDetailsDto.setReceiverTimeZone6(deliveryDetailsDtoResponse.getReceiverTimeZone6());
        deliveryDetailsDto.setReceiverTimeZone7(deliveryDetailsDtoResponse.getReceiverTimeZone7());
        deliveryDetailsDto.setReceiverTimeZone8(deliveryDetailsDtoResponse.getReceiverTimeZone8());
        deliveryDetailsDto.setReceiverTimeZone9(deliveryDetailsDtoResponse.getReceiverTimeZone9());
        deliveryDetailsDto.setReceiverTimeZone10(deliveryDetailsDtoResponse.getReceiverTimeZone10());
        deliveryDetailsDto.setOrderDisplay(deliveryDetailsDtoResponse.getOrderDisplay());
        deliveryDetailsDto.setMaxPrice(deliveryDetailsDtoResponse.getMaxPrice());
        deliveryDetailsDto.setCarriage(deliveryDetailsDtoResponse.getCarriage());

        deliveryDetailsDto.setOpenStatusPC(deliveryDetailsDtoResponse.getOpenStatus() != null ?
                                                           EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                                         deliveryDetailsDtoResponse.getOpenStatus()
                                                                                        ) :
                                                           null);
        deliveryDetailsDto.setDeliveryMethodType(deliveryDetailsDtoResponse.getDeliveryMethodType() != null ?
                                                                 EnumTypeUtil.getEnumFromValue(
                                                                                 HTypeDeliveryMethodType.class,
                                                                                 deliveryDetailsDtoResponse.getDeliveryMethodType()
                                                                                              ) :
                                                                 null);
        deliveryDetailsDto.setShortfallDisplayFlag(deliveryDetailsDtoResponse.getShortfallDisplayFlag() != null ?
                                                                   EnumTypeUtil.getEnumFromValue(
                                                                                   HTypeShortfallDisplayFlag.class,
                                                                                   deliveryDetailsDtoResponse.getShortfallDisplayFlag()
                                                                                                ) :
                                                                   null);
        deliveryDetailsDto.setPrefectureType(deliveryDetailsDtoResponse.getPrefectureType() != null ?
                                                             EnumTypeUtil.getEnumFromValue(HTypePrefectureType.class,
                                                                                           deliveryDetailsDtoResponse.getPrefectureType()
                                                                                          ) :
                                                             null);

        return deliveryDetailsDto;
    }

    /**
     * お届け希望日Dtoレスポンスに変換
     *
     * @param receiverDateDtoResponse お届け希望日Dto
     * @return お届け希望日Dtoレスポンス
     */
    private ReceiverDateDto toReceiverDateDto(ReceiverDateDtoResponse receiverDateDtoResponse) {

        if (ObjectUtils.isEmpty(receiverDateDtoResponse)) {
            return null;
        }

        ReceiverDateDto receiverDateDto = new ReceiverDateDto();

        receiverDateDto.setDateMap(receiverDateDtoResponse.getDateMap());
        receiverDateDto.setShortestDeliveryDateToRegist(
                        conversionUtility.toTimeStamp(receiverDateDtoResponse.getShortestDeliveryDateToRegist()));
        receiverDateDto.setReceiverDateDesignationFlag(
                        receiverDateDtoResponse.getReceiverDateDesignationFlag() != null ?
                                        EnumTypeUtil.getEnumFromValue(HTypeReceiverDateDesignationFlag.class,
                                                                      receiverDateDtoResponse.getReceiverDateDesignationFlag()
                                                                     ) :
                                        null);

        return receiverDateDto;
    }

    /**
     * 配送方法詳細Dtoレスポンスに変換
     *
     * @param deliveryMethodDetailsDtoResponseMap 配送方法詳細Dtoクラス
     * @return 配送方法詳細Dtoレスポンス
     */
    public Map<Integer, DeliveryMethodDetailsDto> toDeliveryMethodDetailsDtoMapResponse(Map<String, jp.co.hankyuhanshin.itec.hitmall.api.shop.param.DeliveryMethodDetailsDtoResponse> deliveryMethodDetailsDtoResponseMap) {
        if (deliveryMethodDetailsDtoResponseMap == null) {
            return new HashMap<>();
        }

        Map<Integer, DeliveryMethodDetailsDto> map = new HashMap<>();

        for (Map.Entry<String, jp.co.hankyuhanshin.itec.hitmall.api.shop.param.DeliveryMethodDetailsDtoResponse> entry : deliveryMethodDetailsDtoResponseMap.entrySet()) {
            Integer newKey = Integer.parseInt(entry.getKey());
            DeliveryMethodDetailsDto newValue = toDeliveryMethodDetailsDto(entry.getValue());
            map.put(newKey, newValue);
        }

        return map;
    }

    /**
     * 配送方法詳細Dtoレスポンスに変換
     *
     * @param deliveryMethodDetailsDtoResponse 配送方法詳細Dtoクラス
     * @return 配送方法詳細Dtoレスポンス
     */
    private DeliveryMethodDetailsDto toDeliveryMethodDetailsDto(jp.co.hankyuhanshin.itec.hitmall.api.shop.param.DeliveryMethodDetailsDtoResponse deliveryMethodDetailsDtoResponse) {
        if (deliveryMethodDetailsDtoResponse == null) {
            return null;
        }

        DeliveryMethodDetailsDto deliveryMethodDetailsDto = new DeliveryMethodDetailsDto();
        deliveryMethodDetailsDto.setDeliveryMethodEntity(
                        toDeliveryMethodEntityShop(deliveryMethodDetailsDtoResponse.getDeliveryMethodEntity()));
        deliveryMethodDetailsDto.setDeliveryMethodTypeCarriageEntityList(toDeliveryMethodTypeCarriageEntityListShop(
                        deliveryMethodDetailsDtoResponse.getDeliveryMethodTypeCarriageEntityList()));
        deliveryMethodDetailsDto.setDeliverySpecialChargeAreaCount(
                        deliveryMethodDetailsDtoResponse.getDeliverySpecialChargeAreaCount() == null ?
                                        0 :
                                        deliveryMethodDetailsDtoResponse.getDeliverySpecialChargeAreaCount());
        deliveryMethodDetailsDto.setDeliveryImpossibleAreaCount(
                        deliveryMethodDetailsDtoResponse.getDeliveryImpossibleAreaCount() == null ?
                                        0 :
                                        deliveryMethodDetailsDtoResponse.getDeliveryImpossibleAreaCount());
        deliveryMethodDetailsDto.setDeliveryImpossibleAreaResultDtoList(toDeliveryImpossibleAreaResultDtoListShop(
                        deliveryMethodDetailsDtoResponse.getDeliveryImpossibleAreaResultDtoList()));
        deliveryMethodDetailsDto.setDeliverySpecialChargeAreaResultDtoList(
                        toDeliverySpecialChargeAreaResultDtoListResponseShop(
                                        deliveryMethodDetailsDtoResponse.getDeliverySpecialChargeAreaResultDtoList()));
        return deliveryMethodDetailsDto;
    }

    /**
     * 配送方法クラスに変換
     *
     * @param deliveryMethodEntityResponse 配送方法エンティティレスポンス
     * @return 配送方法クラス
     */
    private DeliveryMethodEntity toDeliveryMethodEntityShop(jp.co.hankyuhanshin.itec.hitmall.api.shop.param.DeliveryMethodEntityResponse deliveryMethodEntityResponse) {

        if (ObjectUtils.isEmpty(deliveryMethodEntityResponse)) {
            return null;
        }

        DeliveryMethodEntity deliveryMethodEntity = new DeliveryMethodEntity();

        deliveryMethodEntity.setDeliveryMethodSeq(deliveryMethodEntityResponse.getDeliveryMethodSeq());
        deliveryMethodEntity.setDeliveryMethodName(deliveryMethodEntityResponse.getDeliveryMethodName());
        deliveryMethodEntity.setDeliveryMethodDisplayNamePC(
                        deliveryMethodEntityResponse.getDeliveryMethodDisplayName());
        deliveryMethodEntity.setDeliveryNotePC(deliveryMethodEntityResponse.getDeliveryNote());
        deliveryMethodEntity.setEqualsCarriage(deliveryMethodEntityResponse.getEqualsCarriage());
        deliveryMethodEntity.setLargeAmountDiscountPrice(deliveryMethodEntityResponse.getLargeAmountDiscountPrice());
        deliveryMethodEntity.setLargeAmountDiscountCarriage(
                        deliveryMethodEntityResponse.getLargeAmountDiscountCarriage());
        deliveryMethodEntity.setDeliveryLeadTime(deliveryMethodEntityResponse.getDeliveryLeadTime() == null ?
                                                                 0 :
                                                                 deliveryMethodEntityResponse.getDeliveryLeadTime());
        deliveryMethodEntity.setDeliveryChaseURL(deliveryMethodEntityResponse.getDeliveryChaseURL());
        deliveryMethodEntity.setDeliveryChaseURLDisplayPeriod(
                        deliveryMethodEntityResponse.getDeliveryChaseURLDisplayPeriod());
        deliveryMethodEntity.setPossibleSelectDays(deliveryMethodEntityResponse.getPossibleSelectDays() == null ?
                                                                   0 :
                                                                   deliveryMethodEntityResponse.getPossibleSelectDays());
        deliveryMethodEntity.setReceiverTimeZone1(deliveryMethodEntityResponse.getReceiverTimeZone1());
        deliveryMethodEntity.setReceiverTimeZone2(deliveryMethodEntityResponse.getReceiverTimeZone2());
        deliveryMethodEntity.setReceiverTimeZone3(deliveryMethodEntityResponse.getReceiverTimeZone3());
        deliveryMethodEntity.setReceiverTimeZone4(deliveryMethodEntityResponse.getReceiverTimeZone4());
        deliveryMethodEntity.setReceiverTimeZone5(deliveryMethodEntityResponse.getReceiverTimeZone5());
        deliveryMethodEntity.setReceiverTimeZone6(deliveryMethodEntityResponse.getReceiverTimeZone6());
        deliveryMethodEntity.setReceiverTimeZone7(deliveryMethodEntityResponse.getReceiverTimeZone7());
        deliveryMethodEntity.setReceiverTimeZone8(deliveryMethodEntityResponse.getReceiverTimeZone8());
        deliveryMethodEntity.setReceiverTimeZone9(deliveryMethodEntityResponse.getReceiverTimeZone9());
        deliveryMethodEntity.setReceiverTimeZone10(deliveryMethodEntityResponse.getReceiverTimeZone10());
        deliveryMethodEntity.setOrderDisplay(deliveryMethodEntityResponse.getOrderDisplay());
        deliveryMethodEntity.setRegistTime(conversionUtility.toTimeStamp(deliveryMethodEntityResponse.getRegistTime()));
        deliveryMethodEntity.setUpdateTime(conversionUtility.toTimeStamp(deliveryMethodEntityResponse.getUpdateTime()));

        deliveryMethodEntity.setOpenStatusPC(deliveryMethodEntityResponse.getOpenStatus() != null ?
                                                             EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                                           deliveryMethodEntityResponse.getOpenStatus()
                                                                                          ) :
                                                             null);
        deliveryMethodEntity.setDeliveryMethodType(deliveryMethodEntityResponse.getDeliveryMethodType() != null ?
                                                                   EnumTypeUtil.getEnumFromValue(
                                                                                   HTypeDeliveryMethodType.class,
                                                                                   deliveryMethodEntityResponse.getDeliveryMethodType()
                                                                                                ) :
                                                                   null);
        deliveryMethodEntity.setShortfallDisplayFlag(deliveryMethodEntityResponse.getShortfallDisplayFlag() != null ?
                                                                     EnumTypeUtil.getEnumFromValue(
                                                                                     HTypeShortfallDisplayFlag.class,
                                                                                     deliveryMethodEntityResponse.getShortfallDisplayFlag()
                                                                                                  ) :
                                                                     null);

        return deliveryMethodEntity;
    }

    /**
     * 配送区分別送料クラスリストに変換
     *
     * @param deliveryMethodTypeCarriageEntityResponseList 配送区分別送料リスト
     * @return 配送区分別送料クラスリスト
     */
    private List<DeliveryMethodTypeCarriageEntity> toDeliveryMethodTypeCarriageEntityListShop(List<jp.co.hankyuhanshin.itec.hitmall.api.shop.param.DeliveryMethodTypeCarriageEntityResponse> deliveryMethodTypeCarriageEntityResponseList) {

        if (CollectionUtils.isEmpty(deliveryMethodTypeCarriageEntityResponseList)) {
            return new ArrayList<>();
        }

        List<DeliveryMethodTypeCarriageEntity> deliveryMethodTypeCarriageEntityList = new ArrayList<>();

        deliveryMethodTypeCarriageEntityResponseList.forEach(deliveryMethodTypeCarriageEntityResponse -> {
            DeliveryMethodTypeCarriageEntity deliveryMethodTypeCarriageEntity = new DeliveryMethodTypeCarriageEntity();

            deliveryMethodTypeCarriageEntity.setDeliveryMethodSeq(
                            deliveryMethodTypeCarriageEntityResponse.getDeliveryMethodSeq());
            deliveryMethodTypeCarriageEntity.setPrefectureType(
                            deliveryMethodTypeCarriageEntityResponse.getPrefectureType() != null ?
                                            EnumTypeUtil.getEnumFromValue(
                                                            HTypePrefectureType.class,
                                                            deliveryMethodTypeCarriageEntityResponse.getPrefectureType()
                                                                         ) :
                                            null);
            deliveryMethodTypeCarriageEntity.setMaxPrice(deliveryMethodTypeCarriageEntityResponse.getMaxPrice());
            deliveryMethodTypeCarriageEntity.setCarriage(deliveryMethodTypeCarriageEntityResponse.getCarriage());
            deliveryMethodTypeCarriageEntity.setRegistTime(
                            conversionUtility.toTimeStamp(deliveryMethodTypeCarriageEntityResponse.getRegistTime()));

            deliveryMethodTypeCarriageEntityList.add(deliveryMethodTypeCarriageEntity);
        });

        return deliveryMethodTypeCarriageEntityList;
    }

    /**
     * 配送不可能エリア詳細Dtoクラスレスポンスリストに変換
     *
     * @param deliveryImpossibleAreaResultDtoResponseList 配送不可能エリア詳細Dtoリスト
     * @return 配送不可能エリア詳細Dtoクラスレスポンスリスト
     */
    private List<DeliveryImpossibleAreaResultDto> toDeliveryImpossibleAreaResultDtoListShop(List<jp.co.hankyuhanshin.itec.hitmall.api.shop.param.DeliveryImpossibleAreaResultDtoResponse> deliveryImpossibleAreaResultDtoResponseList) {

        if (CollectionUtils.isEmpty(deliveryImpossibleAreaResultDtoResponseList)) {
            return new ArrayList<>();
        }

        List<DeliveryImpossibleAreaResultDto> deliveryImpossibleAreaResultDtoList = new ArrayList<>();

        deliveryImpossibleAreaResultDtoResponseList.forEach(deliveryImpossibleAreaResultDtoResponse -> {

            DeliveryImpossibleAreaResultDto deliveryImpossibleAreaResultDto = new DeliveryImpossibleAreaResultDto();

            deliveryImpossibleAreaResultDto.setDeliveryMethodSeq(
                            deliveryImpossibleAreaResultDtoResponse.getDeliveryMethodSeq());
            deliveryImpossibleAreaResultDto.setZipcode(deliveryImpossibleAreaResultDtoResponse.getZipcode());
            deliveryImpossibleAreaResultDto.setPrefecture(deliveryImpossibleAreaResultDtoResponse.getPrefecture());
            deliveryImpossibleAreaResultDto.setCity(deliveryImpossibleAreaResultDtoResponse.getCity());
            deliveryImpossibleAreaResultDto.setTown(deliveryImpossibleAreaResultDtoResponse.getTown());
            deliveryImpossibleAreaResultDto.setNumbers(deliveryImpossibleAreaResultDtoResponse.getNumbers());
            deliveryImpossibleAreaResultDto.setAddressList(deliveryImpossibleAreaResultDtoResponse.getAddressList());

            deliveryImpossibleAreaResultDtoList.add(deliveryImpossibleAreaResultDto);
        });

        return deliveryImpossibleAreaResultDtoList;
    }

    /**
     * 配送特別料金エリア詳細Dtoクラスリストに変換
     *
     * @param deliverySpecialChargeAreaResultDtoResponseList 配送特別料金エリア詳細Dtoレスポンスリスト
     * @return 配送特別料金エリア詳細Dtoクラスリスト
     */
    private List<DeliverySpecialChargeAreaResultDto> toDeliverySpecialChargeAreaResultDtoListResponseShop(List<jp.co.hankyuhanshin.itec.hitmall.api.shop.param.DeliverySpecialChargeAreaResultDtoResponse> deliverySpecialChargeAreaResultDtoResponseList) {

        if (CollectionUtils.isEmpty(deliverySpecialChargeAreaResultDtoResponseList)) {
            return new ArrayList<>();
        }

        List<DeliverySpecialChargeAreaResultDto> deliverySpecialChargeAreaResultDtoList = new ArrayList<>();

        deliverySpecialChargeAreaResultDtoResponseList.forEach(deliverySpecialChargeAreaResultDtoResponse -> {
            DeliverySpecialChargeAreaResultDto deliverySpecialChargeAreaResultDto =
                            new DeliverySpecialChargeAreaResultDto();

            deliverySpecialChargeAreaResultDto.setDeliveryMethodSeq(
                            deliverySpecialChargeAreaResultDtoResponse.getDeliveryMethodSeq());
            deliverySpecialChargeAreaResultDto.setZipcode(deliverySpecialChargeAreaResultDtoResponse.getZipcode());
            deliverySpecialChargeAreaResultDto.setCarriage(deliverySpecialChargeAreaResultDtoResponse.getCarriage());
            deliverySpecialChargeAreaResultDto.setPrefecture(
                            deliverySpecialChargeAreaResultDtoResponse.getPrefecture());
            deliverySpecialChargeAreaResultDto.setCity(deliverySpecialChargeAreaResultDtoResponse.getCity());
            deliverySpecialChargeAreaResultDto.setTown(deliverySpecialChargeAreaResultDtoResponse.getTown());
            deliverySpecialChargeAreaResultDto.setNumbers(deliverySpecialChargeAreaResultDtoResponse.getNumbers());
            deliverySpecialChargeAreaResultDto.setAddressList(
                            deliverySpecialChargeAreaResultDtoResponse.getAddressList());

            deliverySpecialChargeAreaResultDtoList.add(deliverySpecialChargeAreaResultDto);
        });

        return deliverySpecialChargeAreaResultDtoList;
    }

    /**
     * 注文履歴（出荷済）取得結果DTOクラスに変換
     *
     * @param webApiGetPreShipmentOrderHistoryAggregateResponse WEB-API連携注文履歴（過去1年）取得レスポンス
     * @return 注文履歴（出荷済）取得結果DTOクラス
     */
    public WebApiGetPreShipmentOrderHistoryResponseDto toWebApiGetPreShipmentOrderHistoryResponseDto(
                    WebApiGetPreShipmentOrderHistoryAggregateResponse webApiGetPreShipmentOrderHistoryAggregateResponse) {

        if (ObjectUtils.isEmpty(webApiGetPreShipmentOrderHistoryAggregateResponse)) {
            return null;
        }

        WebApiGetPreShipmentOrderHistoryResponseDto webApiGetPreShipmentOrderHistoryResponseDto =
                        new WebApiGetPreShipmentOrderHistoryResponseDto();

        webApiGetPreShipmentOrderHistoryResponseDto.setInfo(toWebApiGetPreShipmentOrderHistoryResponseDetailDtoList(
                        webApiGetPreShipmentOrderHistoryAggregateResponse.getInfo()));
        webApiGetPreShipmentOrderHistoryResponseDto.setResult(toAbstractWebApiResponseResultDto(
                        webApiGetPreShipmentOrderHistoryAggregateResponse.getResult()));

        return webApiGetPreShipmentOrderHistoryResponseDto;
    }

    /**
     * 注文履歴（出荷済）取得 詳細情報クラスリストに変換
     *
     * @param info WEB-API連携取得結果DTOレスポンス
     * @return 注文履歴（出荷済）取得 詳細情報リスト
     */
    private List<WebApiGetPreShipmentOrderHistoryResponseDetailDto> toWebApiGetPreShipmentOrderHistoryResponseDetailDtoList(
                    List<WebApiGetPreShipmentOrderHistoryResponseDetailDtoResponse> info) {
        if (CollectionUtils.isEmpty(info)) {
            return new ArrayList<>();
        }

        List<WebApiGetPreShipmentOrderHistoryResponseDetailDto> webApiGetPreShipmentOrderHistoryResponseDetailDtoList =
                        new ArrayList<>();

        for (WebApiGetPreShipmentOrderHistoryResponseDetailDtoResponse response : info) {
            WebApiGetPreShipmentOrderHistoryResponseDetailDto webApiGetPreShipmentOrderHistoryResponseDetailDto =
                            new WebApiGetPreShipmentOrderHistoryResponseDetailDto();

            webApiGetPreShipmentOrderHistoryResponseDetailDto.setOrderNo(response.getOrderNo());
            webApiGetPreShipmentOrderHistoryResponseDetailDto.setReceptionTypeName(response.getReceptionTypeName());
            webApiGetPreShipmentOrderHistoryResponseDetailDto.setOrderDate(
                            conversionUtility.toTimeStamp(response.getOrderDate()));
            webApiGetPreShipmentOrderHistoryResponseDetailDto.setReceiveOfficeName(response.getReceiveOfficeName());
            webApiGetPreShipmentOrderHistoryResponseDetailDto.setReceiveZipcode(response.getReceiveZipcode());
            webApiGetPreShipmentOrderHistoryResponseDetailDto.setReceiveAddress1(response.getReceiveAddress1());
            webApiGetPreShipmentOrderHistoryResponseDetailDto.setReceiveAddress2(response.getReceiveAddress2());
            webApiGetPreShipmentOrderHistoryResponseDetailDto.setReceiveAddress3(response.getReceiveAddress3());
            webApiGetPreShipmentOrderHistoryResponseDetailDto.setReceiveAddress4(response.getReceiveAddress4());
            webApiGetPreShipmentOrderHistoryResponseDetailDto.setReceiveAddress5(response.getReceiveAddress5());
            webApiGetPreShipmentOrderHistoryResponseDetailDto.setReceiveDate(
                            conversionUtility.toTimeStamp(response.getReceiveDate()));
            webApiGetPreShipmentOrderHistoryResponseDetailDto.setPaymentType(response.getPaymentType());
            webApiGetPreShipmentOrderHistoryResponseDetailDto.setPaymentTotalPrice(response.getPaymentTotalPrice());
            webApiGetPreShipmentOrderHistoryResponseDetailDto.setGoodsList(
                            toWebApiOrderHistoryResponseGoodsInfoDtoList(response.getGoodsList()));
            webApiGetPreShipmentOrderHistoryResponseDetailDto.setGoodsDetailsMap(
                            toGoodsDetailsDtoMap(response.getGoodsDetailsMap()));

            webApiGetPreShipmentOrderHistoryResponseDetailDtoList.add(
                            webApiGetPreShipmentOrderHistoryResponseDetailDto);

        }

        return webApiGetPreShipmentOrderHistoryResponseDetailDtoList;
    }

    /**
     * 注文履歴取得 共通商品情報リストに変換
     *
     * @param webApiOrderHistoryResponseGoodsInfoDtoResponseList WEB-API連携取得結果DTOクラスレスポンスリスト
     * @return 注文履歴取得 共通商品情報リスト
     */
    public List<WebApiOrderHistoryResponseGoodsInfoDto> toWebApiOrderHistoryResponseGoodsInfoDtoList(List<WebApiOrderHistoryResponseGoodsInfoDtoResponse> webApiOrderHistoryResponseGoodsInfoDtoResponseList) {

        if (CollectionUtils.isEmpty(webApiOrderHistoryResponseGoodsInfoDtoResponseList)) {
            return new ArrayList<>();
        }

        List<WebApiOrderHistoryResponseGoodsInfoDto> webApiOrderHistoryResponseGoodsInfoDtoList = new ArrayList<>();

        for (WebApiOrderHistoryResponseGoodsInfoDtoResponse response : webApiOrderHistoryResponseGoodsInfoDtoResponseList) {
            WebApiOrderHistoryResponseGoodsInfoDto webApiOrderHistoryResponseGoodsInfoDto =
                            new WebApiOrderHistoryResponseGoodsInfoDto();
            webApiOrderHistoryResponseGoodsInfoDto.setGoodsCode(response.getGoodsCode());
            webApiOrderHistoryResponseGoodsInfoDto.setGoodsName(response.getGoodsName());
            webApiOrderHistoryResponseGoodsInfoDto.setGoodsCount(response.getGoodsCount());
            webApiOrderHistoryResponseGoodsInfoDto.setUnitName(response.getUnitName());
            webApiOrderHistoryResponseGoodsInfoDto.setDiscountFlag(response.getDiscountFlag());

            webApiOrderHistoryResponseGoodsInfoDtoList.add(webApiOrderHistoryResponseGoodsInfoDto);
        }

        return webApiOrderHistoryResponseGoodsInfoDtoList;
    }

    /**
     * 商品詳細情報Mapに変換
     *
     * @param goodsDetailsMap 商品詳細DtoクラスレスポンスMap
     * @return 商品詳細情報Map
     */
    public Map<String, GoodsDetailsDto> toGoodsDetailsDtoMap(Map<String, jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.GoodsDetailsDtoResponse> goodsDetailsMap) {

        Map<String, GoodsDetailsDto> goodsDetailsDtoMap = new HashMap<>();

        if (MapUtils.isNotEmpty(goodsDetailsMap)) {
            for (Map.Entry<String, jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.GoodsDetailsDtoResponse> entry : goodsDetailsMap.entrySet()) {
                goodsDetailsDtoMap.put(entry.getKey(), toGoodsDetailsDto(entry.getValue()));
            }
        }

        return goodsDetailsDtoMap;
    }

    /**
     * 商品詳細Dtoクラスレスポンスに変換
     *
     * @param goodsDetailsDtoResponse 商品詳細Dtoクラス
     * @return 商品詳細Dtoクラスレスポンス
     */
    private GoodsDetailsDto toGoodsDetailsDto(jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.GoodsDetailsDtoResponse goodsDetailsDtoResponse) {
        if (goodsDetailsDtoResponse == null) {
            return null;
        }

        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();

        goodsDetailsDto.setGoodsSeq(goodsDetailsDtoResponse.getGoodsSeq());
        goodsDetailsDto.setGoodsGroupSeq(goodsDetailsDtoResponse.getGoodsGroupSeq());
        goodsDetailsDto.setVersionNo(goodsDetailsDtoResponse.getVersionNo());
        goodsDetailsDto.setRegistTime(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getRegistTime()));
        goodsDetailsDto.setUpdateTime(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getUpdateTime()));
        goodsDetailsDto.setGoodsCode(goodsDetailsDtoResponse.getGoodsCode());
        goodsDetailsDto.setGoodsGroupMaxPrice(goodsDetailsDtoResponse.getGoodsGroupMaxPrice());
        goodsDetailsDto.setGoodsGroupMinPrice(goodsDetailsDtoResponse.getGoodsGroupMinPrice());
        goodsDetailsDto.setPreDiscountMinPrice(goodsDetailsDtoResponse.getPreDiscountMinPrice());
        goodsDetailsDto.setPreDiscountMaxPrice(goodsDetailsDtoResponse.getPreDiscountMaxPrice());
        goodsDetailsDto.setGoodsTaxType(EnumTypeUtil.getEnumFromValue(HTypeGoodsTaxType.class,
                                                                      goodsDetailsDtoResponse.getGoodsTaxType()
                                                                     ));
        goodsDetailsDto.setTaxRate(goodsDetailsDtoResponse.getTaxRate());
        goodsDetailsDto.setAlcoholFlag(EnumTypeUtil.getEnumFromValue(HTypeAlcoholFlag.class,
                                                                     goodsDetailsDtoResponse.getAlcoholFlag()
                                                                    ));
        goodsDetailsDto.setGoodsPriceInTax(goodsDetailsDtoResponse.getGoodsPriceInTax());
        goodsDetailsDto.setGoodsPrice(goodsDetailsDtoResponse.getGoodsPrice());
        goodsDetailsDto.setDeliveryType(goodsDetailsDtoResponse.getDeliveryType());
        goodsDetailsDto.setSaleStatusPC(EnumTypeUtil.getEnumFromValue(HTypeGoodsSaleStatus.class,
                                                                      goodsDetailsDtoResponse.getSaleStatus()
                                                                     ));
        goodsDetailsDto.setSaleStartTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getSaleStartTime()));
        goodsDetailsDto.setSaleEndTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getSaleEndTime()));
        goodsDetailsDto.setUnitManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class,
                                                                            goodsDetailsDtoResponse.getUnitManagementFlag()
                                                                           ));
        goodsDetailsDto.setStockManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeStockManagementFlag.class,
                                                                             goodsDetailsDtoResponse.getStockManagementFlag()
                                                                            ));
        goodsDetailsDto.setIndividualDeliveryType(EnumTypeUtil.getEnumFromValue(HTypeIndividualDeliveryType.class,
                                                                                goodsDetailsDtoResponse.getIndividualDeliveryType()
                                                                               ));
        goodsDetailsDto.setPurchasedMax(goodsDetailsDtoResponse.getPurchasedMax());
        goodsDetailsDto.setFreeDeliveryFlag(EnumTypeUtil.getEnumFromValue(HTypeFreeDeliveryFlag.class,
                                                                          goodsDetailsDtoResponse.getFreeDeliveryFlag()
                                                                         ));
        goodsDetailsDto.setOrderDisplay(goodsDetailsDtoResponse.getOrderDisplay());
        goodsDetailsDto.setUnitValue1(goodsDetailsDtoResponse.getUnitValue1());
        goodsDetailsDto.setUnitValue2(goodsDetailsDtoResponse.getUnitValue2());
        goodsDetailsDto.setPreDiscountPrice(goodsDetailsDtoResponse.getPreDiscountPrice());
        goodsDetailsDto.setPreDisCountPriceInTax(goodsDetailsDtoResponse.getPreDisCountPriceInTax());
        goodsDetailsDto.setJanCode(goodsDetailsDtoResponse.getJanCode());
        goodsDetailsDto.setCatalogCode(goodsDetailsDtoResponse.getCatalogCode());
        goodsDetailsDto.setSalesPossibleStock(goodsDetailsDtoResponse.getSalesPossibleStock());
        goodsDetailsDto.setRealStock(goodsDetailsDtoResponse.getRealStock());
        goodsDetailsDto.setOrderReserveStock(goodsDetailsDtoResponse.getOrderReserveStock());
        goodsDetailsDto.setRemainderFewStock(goodsDetailsDtoResponse.getRemainderFewStock());
        goodsDetailsDto.setOrderPointStock(goodsDetailsDtoResponse.getOrderPointStock());
        goodsDetailsDto.setSafetyStock(goodsDetailsDtoResponse.getSafetyStock());
        goodsDetailsDto.setGoodsGroupCode(goodsDetailsDtoResponse.getGoodsGroupCode());
        goodsDetailsDto.setWhatsnewDate(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getWhatsnewDate()));
        goodsDetailsDto.setGoodsOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                           goodsDetailsDtoResponse.getGoodsOpenStatus()
                                                                          ));
        goodsDetailsDto.setOpenStartTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getOpenStartTime()));
        goodsDetailsDto.setOpenEndTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getOpenEndTime()));
        goodsDetailsDto.setGoodsGroupName(goodsDetailsDtoResponse.getGoodsGroupName());
        goodsDetailsDto.setUnitTitle1(goodsDetailsDtoResponse.getUnitTitle1());
        goodsDetailsDto.setUnitTitle2(goodsDetailsDtoResponse.getUnitTitle2());
        goodsDetailsDto.setGoodsPreDiscountPrice(goodsDetailsDtoResponse.getGoodsPreDiscountPrice());
        goodsDetailsDto.setGoodsGroupImageEntityList(
                        toGoodsGroupImageEntity(goodsDetailsDtoResponse.getGoodsGroupImageEntityList()));
        goodsDetailsDto.setSnsLinkFlag(EnumTypeUtil.getEnumFromValue(HTypeSnsLinkFlag.class,
                                                                     goodsDetailsDtoResponse.getSnsLinkFlag()
                                                                    ));
        goodsDetailsDto.setMetaDescription(goodsDetailsDtoResponse.getMetaDescription());
        goodsDetailsDto.setStockStatusPc(EnumTypeUtil.getEnumFromValue(HTypeStockStatusType.class,
                                                                       goodsDetailsDtoResponse.getStockStatus()
                                                                      ));
        goodsDetailsDto.setGoodsNote1(goodsDetailsDtoResponse.getGoodsNote1());
        goodsDetailsDto.setGoodsNote2(goodsDetailsDtoResponse.getGoodsNote2());
        goodsDetailsDto.setGoodsNote3(goodsDetailsDtoResponse.getGoodsNote3());
        goodsDetailsDto.setGoodsNote4(goodsDetailsDtoResponse.getGoodsNote4());
        goodsDetailsDto.setGoodsNote5(goodsDetailsDtoResponse.getGoodsNote5());
        goodsDetailsDto.setGoodsNote6(goodsDetailsDtoResponse.getGoodsNote6());
        goodsDetailsDto.setGoodsNote7(goodsDetailsDtoResponse.getGoodsNote7());
        goodsDetailsDto.setGoodsNote8(goodsDetailsDtoResponse.getGoodsNote8());
        goodsDetailsDto.setGoodsNote9(goodsDetailsDtoResponse.getGoodsNote9());
        goodsDetailsDto.setGoodsNote10(goodsDetailsDtoResponse.getGoodsNote10());
        goodsDetailsDto.setOrderSetting1(goodsDetailsDtoResponse.getOrderSetting1());
        goodsDetailsDto.setOrderSetting2(goodsDetailsDtoResponse.getOrderSetting2());
        goodsDetailsDto.setOrderSetting3(goodsDetailsDtoResponse.getOrderSetting3());
        goodsDetailsDto.setOrderSetting4(goodsDetailsDtoResponse.getOrderSetting4());
        goodsDetailsDto.setOrderSetting5(goodsDetailsDtoResponse.getOrderSetting5());
        goodsDetailsDto.setOrderSetting6(goodsDetailsDtoResponse.getOrderSetting6());
        goodsDetailsDto.setOrderSetting7(goodsDetailsDtoResponse.getOrderSetting7());
        goodsDetailsDto.setOrderSetting8(goodsDetailsDtoResponse.getOrderSetting8());
        goodsDetailsDto.setOrderSetting9(goodsDetailsDtoResponse.getOrderSetting9());
        goodsDetailsDto.setOrderSetting10(goodsDetailsDtoResponse.getOrderSetting10());
        goodsDetailsDto.setUnitImage(toGoodsImageEntityResponse(goodsDetailsDtoResponse.getUnitImage()));
        goodsDetailsDto.setGoodsOptionDisplayName(goodsDetailsDtoResponse.getGoodsOptionDisplayName());
        goodsDetailsDto.setGoodsClassType(EnumTypeUtil.getEnumFromValue(HTypeGoodsClassType.class,
                                                                        goodsDetailsDtoResponse.getGoodsClassType()
                                                                       ));
        goodsDetailsDto.setDentalMonopolySalesFlg(EnumTypeUtil.getEnumFromValue(HTypeDentalMonopolySalesFlg.class,
                                                                                goodsDetailsDtoResponse.getDentalMonopolySalesFlg()
                                                                               ));
        goodsDetailsDto.setSaleIconFlag(EnumTypeUtil.getEnumFromValue(HTypeSaleIconFlag.class,
                                                                      goodsDetailsDtoResponse.getSaleIconFlag()
                                                                     ));
        goodsDetailsDto.setReserveIconFlag(EnumTypeUtil.getEnumFromValue(HTypeReserveIconFlag.class,
                                                                         goodsDetailsDtoResponse.getReserveIconFlag()
                                                                        ));
        goodsDetailsDto.setNewIconFlag(EnumTypeUtil.getEnumFromValue(HTypeNewIconFlag.class,
                                                                     goodsDetailsDtoResponse.getNewIconFlag()
                                                                    ));
        goodsDetailsDto.setLandSendFlag(EnumTypeUtil.getEnumFromValue(HTypeLandSendFlag.class,
                                                                      goodsDetailsDtoResponse.getLandSendFlag()
                                                                     ));
        goodsDetailsDto.setCoolSendFlag(EnumTypeUtil.getEnumFromValue(HTypeCoolSendFlag.class,
                                                                      goodsDetailsDtoResponse.getCoolSendFlag()
                                                                     ));
        goodsDetailsDto.setCoolSendFrom(goodsDetailsDtoResponse.getCoolSendFrom());
        goodsDetailsDto.setCoolSendTo(goodsDetailsDtoResponse.getCoolSendTo());
        goodsDetailsDto.setTax(goodsDetailsDtoResponse.getTax());
        goodsDetailsDto.setUnit(goodsDetailsDtoResponse.getUnit());
        goodsDetailsDto.setGoodsManagementCode(goodsDetailsDtoResponse.getGoodsManagementCode());
        goodsDetailsDto.setGoodsDivisionCode(goodsDetailsDtoResponse.getGoodsDivisionCode());
        goodsDetailsDto.setGoodsCategory1(goodsDetailsDtoResponse.getGoodsCategory1());
        goodsDetailsDto.setGoodsCategory2(goodsDetailsDtoResponse.getGoodsCategory2());
        goodsDetailsDto.setGoodsCategory3(goodsDetailsDtoResponse.getGoodsCategory3());
        goodsDetailsDto.setReserveFlag(EnumTypeUtil.getEnumFromValue(HTypeReserveFlag.class,
                                                                     goodsDetailsDtoResponse.getReserveFlag()
                                                                    ));
        goodsDetailsDto.setPriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypePriceMarkDispFlag.class,
                                                                           goodsDetailsDtoResponse.getPriceMarkDispFlag()
                                                                          ));
        goodsDetailsDto.setSalePriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceMarkDispFlag.class,
                                                                               goodsDetailsDtoResponse.getSalePriceMarkDispFlag()
                                                                              ));
        goodsDetailsDto.setSalePriceIntegrityFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceIntegrityFlag.class,
                                                                                goodsDetailsDtoResponse.getSalePriceIntegrityFlag()
                                                                               ));
        goodsDetailsDto.setSaleYesNo(goodsDetailsDtoResponse.getSaleYesNo());
        goodsDetailsDto.setSaleNgMessage(goodsDetailsDtoResponse.getSaleNgMessage());
        goodsDetailsDto.setDeliveryYesNo(goodsDetailsDtoResponse.getDeliveryYesNo());
        goodsDetailsDto.setEmotionPriceType(EnumTypeUtil.getEnumFromValue(HTypeEmotionPriceType.class,
                                                                          goodsDetailsDtoResponse.getEmotionPriceType()
                                                                         ));

        return goodsDetailsDto;
    }

    /**
     * 商品グループ画像レスポンスに変換
     *
     * @param response 商品画像クラス
     * @return 商品グループ画像レスポンス
     */
    private GoodsImageEntity toGoodsImageEntityResponse(jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.GoodsImageEntityResponse response) {

        if (ObjectUtils.isEmpty(response)) {
            return null;
        }

        GoodsImageEntity goodsImageEntity = new GoodsImageEntity();

        goodsImageEntity.setGoodsGroupSeq(response.getGoodsGroupSeq());
        goodsImageEntity.setGoodsSeq(response.getGoodsSeq());
        goodsImageEntity.setImageFileName(response.getImageFileName());
        goodsImageEntity.setDisplayFlag(response.getDisplayFlag() != null ?
                                                        EnumTypeUtil.getEnumFromValue(HTypeDisplayStatus.class,
                                                                                      response.getDisplayFlag()
                                                                                     ) :
                                                        null);
        goodsImageEntity.setTmpFilePath(response.getTmpFilePath());
        goodsImageEntity.setRegistTime(conversionUtility.toTimeStamp(response.getRegistTime()));

        return goodsImageEntity;
    }

    /**
     * 商品グループ画像レスポンスに変換
     *
     * @param goodsGroupImageEntityResponseList 商品グループ画像クラス
     * @return 商品グループ画像レスポンス
     */
    private List<GoodsGroupImageEntity> toGoodsGroupImageEntity(List<jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.GoodsGroupImageEntityResponse> goodsGroupImageEntityResponseList) {
        if (CollectionUtils.isEmpty(goodsGroupImageEntityResponseList)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntity> goodsGroupImageEntityList = new ArrayList<>();

        for (jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.GoodsGroupImageEntityResponse goodsGroupImageEntityResponse : goodsGroupImageEntityResponseList) {
            GoodsGroupImageEntity goodsGroupImageEntity = new GoodsGroupImageEntity();
            goodsGroupImageEntity.setGoodsGroupSeq(goodsGroupImageEntityResponse.getGoodsGroupSeq());
            goodsGroupImageEntity.setRegistTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntityResponse.getRegistTime()));
            goodsGroupImageEntity.setUpdateTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntityResponse.getUpdateTime()));
            goodsGroupImageEntity.setImageFileName(goodsGroupImageEntityResponse.getImageFileName());
            goodsGroupImageEntity.setImageTypeVersionNo(goodsGroupImageEntityResponse.getImageTypeVersionNo());
            goodsGroupImageEntityList.add(goodsGroupImageEntity);
        }

        return goodsGroupImageEntityList;
    }

    /**
     * 受注配送DTOリクエストに変換
     *
     * @param orderDeliveryDto 受注配送Dtoクラス
     * @return 受注配送DTOリクエスト
     */
    public OrderDeliveryDtoRequest toOrderDeliveryDtoRequest(OrderDeliveryDto orderDeliveryDto) {

        if (ObjectUtils.isEmpty(orderDeliveryDto)) {
            return null;
        }

        OrderDeliveryDtoRequest orderDeliveryDtoRequest = new OrderDeliveryDtoRequest();

        orderDeliveryDtoRequest.setAddType(orderDeliveryDto.getAddType());
        orderDeliveryDtoRequest.setTmpOrderGoodsEntityList(
                        toOrderGoodsEntityList(orderDeliveryDto.getTmpOrderGoodsEntityList()));
        orderDeliveryDtoRequest.setOrderGoodsEntityList(
                        toOrderGoodsEntityList(orderDeliveryDto.getOrderGoodsEntityList()));
        orderDeliveryDtoRequest.setOrderDeliveryEntity(
                        toOrderDeliveryRequest(orderDeliveryDto.getOrderDeliveryEntity()));
        orderDeliveryDtoRequest.setDeliveryMethodEntity(
                        toDeliveryMethodRequest(orderDeliveryDto.getDeliveryMethodEntity()));
        orderDeliveryDtoRequest.setReceiverRegistFlg(orderDeliveryDto.isReceiverRegistFlg());
        orderDeliveryDtoRequest.setDeliveryDtoList(toDeliveryDtoList(orderDeliveryDto.getDeliveryDtoList()));
        orderDeliveryDtoRequest.setOriginalCarriage(orderDeliveryDto.getOriginalCarriage());
        orderDeliveryDtoRequest.setFirstSelectedOrderGoodsEntityList(
                        toOrderGoodsEntityList(orderDeliveryDto.getFirstSelectedOrderGoodsEntityList()));
        orderDeliveryDtoRequest.setDeliveryInformationDetailDto(toWebApiGetDeliveryInformationResponseDetailDto(
                        orderDeliveryDto.getDeliveryInformationDetailDto()));
        orderDeliveryDtoRequest.setOrderDeliveryNowDtoList(
                        toOrderDeliveryNowDtoList(orderDeliveryDto.getOrderDeliveryNowDtoList()));
        orderDeliveryDtoRequest.setOrderReserveDeliveryDtoList(
                        toOrderReserveDeliveryDtoRequestList(orderDeliveryDto.getOrderReserveDeliveryDtoList()));
        orderDeliveryDtoRequest.setOrderDependingOnReceiptGoodsDtoList(toOrderDependingOnReceiptGoodsDtoList(
                        orderDeliveryDto.getOrderDependingOnReceiptGoodsDtoList()));
        orderDeliveryDtoRequest.setCustomerNo(orderDeliveryDto.getCustomerNo());
        orderDeliveryDtoRequest.setAddressBookSeq(orderDeliveryDto.getAddressBookSeq());
        orderDeliveryDtoRequest.setDeliveryDate(orderDeliveryDto.getDeliveryDate());
        orderDeliveryDtoRequest.setDiscountPrice(orderDeliveryDto.getDiscountPrice());
        orderDeliveryDtoRequest.setTaxPrice(orderDeliveryDto.getTaxPrice());
        orderDeliveryDtoRequest.setTotalTax(orderDeliveryDto.getTotalTax());
        orderDeliveryDtoRequest.setCityCd(orderDeliveryDto.getCityCd());

        if (orderDeliveryDto.getBusinessType() != null) {
            orderDeliveryDtoRequest.setBusinessType(orderDeliveryDto.getBusinessType().getValue());
        }
        if (orderDeliveryDto.getConfDocumentType() != null) {
            orderDeliveryDtoRequest.setConfDocumentType(orderDeliveryDto.getConfDocumentType().getValue());
        }
        if (orderDeliveryDto.getRequisitionType() != null) {
            orderDeliveryDtoRequest.setRequisitionType(orderDeliveryDto.getRequisitionType().getValue());
        }

        return orderDeliveryDtoRequest;
    }

    /**
     * 配送方法リクエストに変換
     *
     * @param deliveryMethodEntity 配送方法エンティティ
     * @return 配送方法リクエスト
     */
    private DeliveryMethodRequest toDeliveryMethodRequest(DeliveryMethodEntity deliveryMethodEntity) {

        if (ObjectUtils.isEmpty(deliveryMethodEntity)) {
            return null;
        }

        DeliveryMethodRequest deliveryMethodRequest = new DeliveryMethodRequest();

        deliveryMethodRequest.setDeliveryMethodSeq(deliveryMethodEntity.getDeliveryMethodSeq());
        deliveryMethodRequest.setDeliveryMethodName(deliveryMethodEntity.getDeliveryMethodName());
        deliveryMethodRequest.setDeliveryMethodDisplayName(deliveryMethodEntity.getDeliveryMethodDisplayNamePC());
        deliveryMethodRequest.setDeliveryNote(deliveryMethodEntity.getDeliveryNotePC());
        deliveryMethodRequest.setEqualsCarriage(deliveryMethodEntity.getEqualsCarriage());
        deliveryMethodRequest.setLargeAmountDiscountPrice(deliveryMethodEntity.getLargeAmountDiscountPrice());
        deliveryMethodRequest.setLargeAmountDiscountCarriage(deliveryMethodEntity.getLargeAmountDiscountCarriage());
        deliveryMethodRequest.setDeliveryLeadTime(deliveryMethodEntity.getDeliveryLeadTime());
        deliveryMethodRequest.setDeliveryChaseURL(deliveryMethodEntity.getDeliveryChaseURL());
        deliveryMethodRequest.setDeliveryChaseURLDisplayPeriod(deliveryMethodEntity.getDeliveryChaseURLDisplayPeriod());
        deliveryMethodRequest.setPossibleSelectDays(deliveryMethodEntity.getPossibleSelectDays());
        deliveryMethodRequest.setReceiverTimeZone1(deliveryMethodEntity.getReceiverTimeZone1());
        deliveryMethodRequest.setReceiverTimeZone2(deliveryMethodEntity.getReceiverTimeZone2());
        deliveryMethodRequest.setReceiverTimeZone3(deliveryMethodEntity.getReceiverTimeZone3());
        deliveryMethodRequest.setReceiverTimeZone4(deliveryMethodEntity.getReceiverTimeZone4());
        deliveryMethodRequest.setReceiverTimeZone5(deliveryMethodEntity.getReceiverTimeZone5());
        deliveryMethodRequest.setReceiverTimeZone6(deliveryMethodEntity.getReceiverTimeZone6());
        deliveryMethodRequest.setReceiverTimeZone7(deliveryMethodEntity.getReceiverTimeZone7());
        deliveryMethodRequest.setReceiverTimeZone8(deliveryMethodEntity.getReceiverTimeZone8());
        deliveryMethodRequest.setReceiverTimeZone9(deliveryMethodEntity.getReceiverTimeZone9());
        deliveryMethodRequest.setReceiverTimeZone10(deliveryMethodEntity.getReceiverTimeZone10());
        deliveryMethodRequest.setOrderDisplay(deliveryMethodEntity.getOrderDisplay());
        deliveryMethodRequest.setRegistTime(conversionUtility.toTimeStamp(deliveryMethodEntity.getRegistTime()));
        deliveryMethodRequest.setUpdateTime(conversionUtility.toTimeStamp(deliveryMethodEntity.getUpdateTime()));

        if (deliveryMethodEntity.getOpenStatusPC() != null) {
            deliveryMethodRequest.setOpenStatus(deliveryMethodEntity.getOpenStatusPC().getValue());
        }
        if (deliveryMethodEntity.getDeliveryMethodType() != null) {
            deliveryMethodRequest.setDeliveryMethodType(deliveryMethodEntity.getDeliveryMethodType().getValue());
        }
        if (deliveryMethodEntity.getShortfallDisplayFlag() != null) {
            deliveryMethodRequest.setShortfallDisplayFlag(deliveryMethodEntity.getShortfallDisplayFlag().getValue());
        }

        return deliveryMethodRequest;
    }

    /**
     * 受注配送リクエストに変換
     *
     * @param orderDeliveryEntity 受注配送クラス
     * @return 受注配送リクエスト
     */
    private OrderDeliveryRequest toOrderDeliveryRequest(OrderDeliveryEntity orderDeliveryEntity) {

        if (ObjectUtils.isEmpty(orderDeliveryEntity)) {
            return null;
        }

        OrderDeliveryRequest orderDeliveryRequest = new OrderDeliveryRequest();

        orderDeliveryRequest.setOrderSeq(orderDeliveryEntity.getOrderSeq());
        orderDeliveryRequest.setOrderDeliveryVersionNo(orderDeliveryEntity.getOrderDeliveryVersionNo());
        orderDeliveryRequest.setOrderConsecutiveNo(orderDeliveryEntity.getOrderConsecutiveNo());
        orderDeliveryRequest.setPlanDate(conversionUtility.toTimeStamp(orderDeliveryEntity.getPlanDate()));
        orderDeliveryRequest.setShipmentDate(conversionUtility.toTimeStamp(orderDeliveryEntity.getShipmentDate()));
        orderDeliveryRequest.setDeliveryCode(orderDeliveryEntity.getDeliveryCode());
        orderDeliveryRequest.setDeliveryMethodSeq(orderDeliveryEntity.getDeliveryMethodSeq());
        orderDeliveryRequest.setReceiverLastName(orderDeliveryEntity.getReceiverLastName());
        orderDeliveryRequest.setReceiverFirstName(orderDeliveryEntity.getReceiverFirstName());
        orderDeliveryRequest.setReceiverLastKana(orderDeliveryEntity.getReceiverLastKana());
        orderDeliveryRequest.setReceiverFirstKana(orderDeliveryEntity.getReceiverFirstKana());
        orderDeliveryRequest.setReceiverTel(orderDeliveryEntity.getReceiverTel());
        orderDeliveryRequest.setReceiverZipCode(orderDeliveryEntity.getReceiverZipCode());
        orderDeliveryRequest.setReceiverPrefecture(orderDeliveryEntity.getReceiverPrefecture());
        orderDeliveryRequest.setReceiverAddress1(orderDeliveryEntity.getReceiverAddress1());
        orderDeliveryRequest.setReceiverAddress2(orderDeliveryEntity.getReceiverAddress2());
        orderDeliveryRequest.setReceiverAddress3(orderDeliveryEntity.getReceiverAddress3());
        orderDeliveryRequest.setReceiverDate(conversionUtility.toTimeStamp(orderDeliveryEntity.getReceiverDate()));
        orderDeliveryRequest.setReceiverTimeZone(orderDeliveryEntity.getReceiverTimeZone());
        orderDeliveryRequest.setDeliveryNote(orderDeliveryEntity.getDeliveryNote());
        orderDeliveryRequest.setOthersNote(orderDeliveryEntity.getOthersNote());
        orderDeliveryRequest.setMessage(orderDeliveryEntity.getMessage());
        orderDeliveryRequest.setCarriage(orderDeliveryEntity.getCarriage());
        orderDeliveryRequest.setRegistTime(conversionUtility.toTimeStamp(orderDeliveryEntity.getRegistTime()));
        orderDeliveryRequest.setUpdateTime(conversionUtility.toTimeStamp(orderDeliveryEntity.getUpdateTime()));
        orderDeliveryRequest.setShortestDeliveryDateToRegist(
                        conversionUtility.toTimeStamp(orderDeliveryEntity.getShortestDeliveryDateToRegist()));
        orderDeliveryRequest.setDeliveryDay(orderDeliveryEntity.getDeliveryDay());
        orderDeliveryRequest.setReceiverAddress4(orderDeliveryEntity.getReceiverAddress4());

        if (orderDeliveryEntity.getShipmentStatus() != null) {
            orderDeliveryRequest.setShipmentStatus(orderDeliveryEntity.getShipmentStatus().getValue());
        }
        if (orderDeliveryEntity.getReservationDeliveryFlag() != null) {
            orderDeliveryRequest.setReservationDeliveryFlag(
                            orderDeliveryEntity.getReservationDeliveryFlag().getValue());
        }
        if (orderDeliveryEntity.getInvoiceAttachmentFlag() != null) {
            orderDeliveryRequest.setInvoiceAttachmentFlag(orderDeliveryEntity.getInvoiceAttachmentFlag().getValue());
        }
        if (orderDeliveryEntity.getReceiverDateDesignationFlag() != null) {
            orderDeliveryRequest.setReceiverDateDesignationFlag(
                            orderDeliveryEntity.getReceiverDateDesignationFlag().getValue());
        }
        if (orderDeliveryEntity.getDeliveryCycle() != null) {
            orderDeliveryRequest.setDeliveryCycle(orderDeliveryEntity.getDeliveryCycle().getValue());
        }

        return orderDeliveryRequest;
    }

    /**
     * 配送DTOリクエストに変換
     *
     * @param deliveryDtoList 配送DTOクラスリスト
     * @return 配送DTOリクエスト
     */
    private List<DeliveryDtoRequest> toDeliveryDtoList(List<DeliveryDto> deliveryDtoList) {

        if (CollectionUtil.isEmpty(deliveryDtoList)) {
            return new ArrayList<>();
        }

        List<DeliveryDtoRequest> deliveryDtoRequestList = new ArrayList<>();

        deliveryDtoList.forEach(deliveryDto -> {
            DeliveryDtoRequest deliveryDtoRequest = new DeliveryDtoRequest();

            deliveryDtoRequest.setDeliveryDetailsDto(toDeliveryDetailsDtoRequest(deliveryDto.getDeliveryDetailsDto()));
            deliveryDtoRequest.setCarriage(deliveryDto.getCarriage());
            deliveryDtoRequest.setSelectClass(deliveryDto.isSelectClass());
            deliveryDtoRequest.setReceiverDateDto(toReceiverDateDtoRequest(deliveryDto.getReceiverDateDto()));
            deliveryDtoRequest.setSpecialChargeAreaFlag(deliveryDto.isSpecialChargeAreaFlag());

            deliveryDtoRequestList.add(deliveryDtoRequest);
        });

        return deliveryDtoRequestList;
    }

    /**
     * 配送方法詳細DTOリクエストに変換
     *
     * @param deliveryDetailsDto 配送方法詳細DTO
     * @return 配送方法詳細DTOリクエスト
     */
    private DeliveryDetailsDtoRequest toDeliveryDetailsDtoRequest(DeliveryDetailsDto deliveryDetailsDto) {

        if (ObjectUtils.isEmpty(deliveryDetailsDto)) {
            return null;
        }

        DeliveryDetailsDtoRequest deliveryDetailsDtoRequest = new DeliveryDetailsDtoRequest();

        deliveryDetailsDtoRequest.setDeliveryMethodSeq(deliveryDetailsDto.getDeliveryMethodSeq());
        deliveryDetailsDtoRequest.setDeliveryMethodName(deliveryDetailsDto.getDeliveryMethodName());
        deliveryDetailsDtoRequest.setDeliveryMethodDisplayName(deliveryDetailsDto.getDeliveryMethodDisplayNamePC());
        deliveryDetailsDtoRequest.setDeliveryNote(deliveryDetailsDto.getDeliveryNotePC());
        deliveryDetailsDtoRequest.setEqualsCarriage(deliveryDetailsDto.getEqualsCarriage());
        deliveryDetailsDtoRequest.setLargeAmountDiscountPrice(deliveryDetailsDto.getLargeAmountDiscountPrice());
        deliveryDetailsDtoRequest.setLargeAmountDiscountCarriage(deliveryDetailsDto.getLargeAmountDiscountCarriage());
        deliveryDetailsDtoRequest.setDeliveryLeadTime(deliveryDetailsDto.getDeliveryLeadTime());
        deliveryDetailsDtoRequest.setPossibleSelectDays(deliveryDetailsDto.getPossibleSelectDays());
        deliveryDetailsDtoRequest.setReceiverTimeZone1(deliveryDetailsDto.getReceiverTimeZone1());
        deliveryDetailsDtoRequest.setReceiverTimeZone2(deliveryDetailsDto.getReceiverTimeZone2());
        deliveryDetailsDtoRequest.setReceiverTimeZone3(deliveryDetailsDto.getReceiverTimeZone3());
        deliveryDetailsDtoRequest.setReceiverTimeZone4(deliveryDetailsDto.getReceiverTimeZone4());
        deliveryDetailsDtoRequest.setReceiverTimeZone5(deliveryDetailsDto.getReceiverTimeZone5());
        deliveryDetailsDtoRequest.setReceiverTimeZone6(deliveryDetailsDto.getReceiverTimeZone6());
        deliveryDetailsDtoRequest.setReceiverTimeZone7(deliveryDetailsDto.getReceiverTimeZone7());
        deliveryDetailsDtoRequest.setReceiverTimeZone8(deliveryDetailsDto.getReceiverTimeZone8());
        deliveryDetailsDtoRequest.setReceiverTimeZone9(deliveryDetailsDto.getReceiverTimeZone9());
        deliveryDetailsDtoRequest.setReceiverTimeZone10(deliveryDetailsDto.getReceiverTimeZone10());
        deliveryDetailsDtoRequest.setOrderDisplay(deliveryDetailsDto.getOrderDisplay());
        deliveryDetailsDtoRequest.setMaxPrice(deliveryDetailsDto.getMaxPrice());
        deliveryDetailsDtoRequest.setCarriage(deliveryDetailsDto.getCarriage());

        if (deliveryDetailsDto.getOpenStatusPC() != null) {
            deliveryDetailsDtoRequest.setOpenStatus(deliveryDetailsDto.getOpenStatusPC().getValue());
        }
        if (deliveryDetailsDto.getDeliveryMethodType() != null) {
            deliveryDetailsDtoRequest.setDeliveryMethodType(deliveryDetailsDto.getDeliveryMethodType().getValue());
        }
        if (deliveryDetailsDto.getShortfallDisplayFlag() != null) {
            deliveryDetailsDtoRequest.setShortfallDisplayFlag(deliveryDetailsDto.getShortfallDisplayFlag().getValue());
        }
        if (deliveryDetailsDto.getPrefectureType() != null) {
            deliveryDetailsDtoRequest.setPrefectureType(deliveryDetailsDto.getPrefectureType().getValue());
        }

        return deliveryDetailsDtoRequest;
    }

    /**
     * お届け希望日Dtoリクエストに変換
     *
     * @param receiverDateDto お届け希望日Dto
     * @return お届け希望日Dtoリクエスト
     */
    private ReceiverDateDtoRequest toReceiverDateDtoRequest(ReceiverDateDto receiverDateDto) {

        if (ObjectUtils.isEmpty(receiverDateDto)) {
            return null;
        }

        ReceiverDateDtoRequest receiverDateDtoRequest = new ReceiverDateDtoRequest();

        receiverDateDtoRequest.setDateMap(receiverDateDto.getDateMap());
        receiverDateDtoRequest.setShortestDeliveryDateToRegist(
                        conversionUtility.toTimeStamp(receiverDateDto.getShortestDeliveryDateToRegist()));
        if (receiverDateDto.getReceiverDateDesignationFlag() != null) {
            receiverDateDtoRequest.setReceiverDateDesignationFlag(
                            receiverDateDto.getReceiverDateDesignationFlag().getValue());
        }

        return receiverDateDtoRequest;
    }

    /**
     * 配送情報取得Dtoリクエストに変換
     *
     * @param webApiGetDeliveryInformationResponseDetailDto 配送情報取得 詳細情報
     * @return 配送情報取得Dtoリクエスト
     */
    public WebApiGetDeliveryInformationResponseDetailDtoRequest toWebApiGetDeliveryInformationResponseDetailDto(
                    WebApiGetDeliveryInformationResponseDetailDto webApiGetDeliveryInformationResponseDetailDto) {

        if (ObjectUtils.isEmpty(webApiGetDeliveryInformationResponseDetailDto)) {
            return null;
        }

        WebApiGetDeliveryInformationResponseDetailDtoRequest webApiGetDeliveryInformationResponseDetailDtoRequest =
                        new WebApiGetDeliveryInformationResponseDetailDtoRequest();

        webApiGetDeliveryInformationResponseDetailDtoRequest.setDeliveryAssignFlag(
                        webApiGetDeliveryInformationResponseDetailDto.getDeliveryAssignFlag());
        webApiGetDeliveryInformationResponseDetailDtoRequest.setDeliveryCompanyCode(
                        webApiGetDeliveryInformationResponseDetailDto.getDeliveryCompanyCode());
        webApiGetDeliveryInformationResponseDetailDtoRequest.setDeliveryFlag(
                        webApiGetDeliveryInformationResponseDetailDto.getDeliveryFlag());
        webApiGetDeliveryInformationResponseDetailDtoRequest.setNodeliveryGoodsCode(
                        webApiGetDeliveryInformationResponseDetailDto.getNodeliveryGoodsCode());
        if (CollectionUtil.isNotEmpty(webApiGetDeliveryInformationResponseDetailDto.getDateInfo())) {
            webApiGetDeliveryInformationResponseDetailDtoRequest.setDateInfo(
                            toWebApiGetDeliveryInformationResponseDetailDateInfoDtoRequest(
                                            webApiGetDeliveryInformationResponseDetailDto.getDateInfo()));
        }

        return webApiGetDeliveryInformationResponseDetailDtoRequest;
    }

    /**
     * 配送情報取得 詳細情報-日付情報リストレスポンスに変換
     *
     * @param webApiGetDeliveryInformationResponseDetailDateInfoDtoList WEB-API連携取得結果DTOクラス - 配送情報取得 詳細情報-日付情報
     * @return 配送情報取得 詳細情報-日付情報リストレスポンス
     */
    private List<WebApiGetDeliveryInformationResponseDetailDateInfoDtoRequest> toWebApiGetDeliveryInformationResponseDetailDateInfoDtoRequest(
                    List<WebApiGetDeliveryInformationResponseDetailDateInfoDto> webApiGetDeliveryInformationResponseDetailDateInfoDtoList) {

        if (CollectionUtil.isEmpty(webApiGetDeliveryInformationResponseDetailDateInfoDtoList)) {
            return new ArrayList<>();
        }
        List<WebApiGetDeliveryInformationResponseDetailDateInfoDtoRequest>
                        webApiGetDeliveryInformationResponseDetailDateInfoDtoRequestList = new ArrayList<>();

        webApiGetDeliveryInformationResponseDetailDateInfoDtoList.forEach(
                        webApiGetDeliveryInformationResponseDetailDateInfoDtoResponse -> {
                            WebApiGetDeliveryInformationResponseDetailDateInfoDtoRequest
                                            webApiGetDeliveryInformationResponseDetailDateInfoDto =
                                            new WebApiGetDeliveryInformationResponseDetailDateInfoDtoRequest();

                            webApiGetDeliveryInformationResponseDetailDateInfoDto.setReceiveDate(
                                            conversionUtility.toTimeStamp(
                                                            webApiGetDeliveryInformationResponseDetailDateInfoDtoResponse.getReceiveDate()));
                            webApiGetDeliveryInformationResponseDetailDateInfoDto.setDispTimeZoneCode(
                                            webApiGetDeliveryInformationResponseDetailDateInfoDtoResponse.getDispTimeZoneCode());

                            webApiGetDeliveryInformationResponseDetailDateInfoDtoRequestList.add(
                                            webApiGetDeliveryInformationResponseDetailDateInfoDto);
                        });

        return webApiGetDeliveryInformationResponseDetailDateInfoDtoRequestList;
    }

    /**
     * 今すぐお届け分DTOリクエストリストに変換
     *
     * @param orderDeliveryNowDtoList 今すぐお届け分DTOリスト
     * @return 今すぐお届け分DTOリクエストリスト
     */
    private List<OrderDeliveryNowDtoRequest> toOrderDeliveryNowDtoList(List<OrderDeliveryNowDto> orderDeliveryNowDtoList) {

        if (CollectionUtil.isEmpty(orderDeliveryNowDtoList)) {
            return new ArrayList<>();
        }

        List<OrderDeliveryNowDtoRequest> orderDeliveryNowDtoRequestList = new ArrayList<>();

        orderDeliveryNowDtoList.forEach(orderDeliveryNowDto -> {

            OrderDeliveryNowDtoRequest orderDeliveryNowDtoRequest = new OrderDeliveryNowDtoRequest();

            orderDeliveryNowDtoRequest.setOrderGoodsEntity(
                            toOrderGoodsRequest(orderDeliveryNowDto.getOrderGoodsEntity()));

            orderDeliveryNowDtoRequestList.add(orderDeliveryNowDtoRequest);
        });

        return orderDeliveryNowDtoRequestList;
    }

    /**
     * 受注商品リクエストに変換
     *
     * @param orderGoodsEntity 受注商品
     * @return 受注商品リクエスト
     */
    private OrderGoodsRequest toOrderGoodsRequest(OrderGoodsEntity orderGoodsEntity) {

        if (ObjectUtils.isEmpty(orderGoodsEntity)) {
            return null;
        }

        OrderGoodsRequest orderGoodsRequest = new OrderGoodsRequest();

        orderGoodsRequest.setOrderSeq(orderGoodsEntity.getOrderSeq());
        orderGoodsRequest.setOrderGoodsVersionNo(orderGoodsEntity.getOrderGoodsVersionNo());
        orderGoodsRequest.setOrderConsecutiveNo(orderGoodsEntity.getOrderConsecutiveNo());
        orderGoodsRequest.setGoodsSeq(orderGoodsEntity.getGoodsSeq());
        orderGoodsRequest.setOrderDisplay(orderGoodsEntity.getOrderDisplay());
        orderGoodsRequest.setProcessTime(conversionUtility.toTimeStamp(orderGoodsEntity.getProcessTime()));
        orderGoodsRequest.setGoodsGroupCode(orderGoodsEntity.getGoodsGroupCode());
        orderGoodsRequest.setGoodsCode(orderGoodsEntity.getGoodsCode());
        orderGoodsRequest.setJanCode(orderGoodsEntity.getJanCode());
        orderGoodsRequest.setGoodsGroupName(orderGoodsEntity.getGoodsGroupName());
        orderGoodsRequest.setTaxRate(orderGoodsEntity.getTaxRate());
        orderGoodsRequest.setGoodsPrice(orderGoodsEntity.getGoodsPrice());
        orderGoodsRequest.setPreGoodsCount(orderGoodsEntity.getPreGoodsCount());
        orderGoodsRequest.setGoodsCount(orderGoodsEntity.getGoodsCount());
        orderGoodsRequest.setUnitValue1(orderGoodsEntity.getUnitValue1());
        orderGoodsRequest.setUnitValue2(orderGoodsEntity.getUnitValue2());
        orderGoodsRequest.setDeliveryType(orderGoodsEntity.getDeliveryType());
        orderGoodsRequest.setOrderSetting1(orderGoodsEntity.getOrderSetting1());
        orderGoodsRequest.setOrderSetting2(orderGoodsEntity.getOrderSetting2());
        orderGoodsRequest.setOrderSetting3(orderGoodsEntity.getOrderSetting3());
        orderGoodsRequest.setOrderSetting4(orderGoodsEntity.getOrderSetting4());
        orderGoodsRequest.setOrderSetting5(orderGoodsEntity.getOrderSetting5());
        orderGoodsRequest.setOrderSetting6(orderGoodsEntity.getOrderSetting6());
        orderGoodsRequest.setOrderSetting7(orderGoodsEntity.getOrderSetting7());
        orderGoodsRequest.setOrderSetting8(orderGoodsEntity.getOrderSetting8());
        orderGoodsRequest.setOrderSetting9(orderGoodsEntity.getOrderSetting9());
        orderGoodsRequest.setOrderSetting10(orderGoodsEntity.getOrderSetting10());
        orderGoodsRequest.setRegistTime(conversionUtility.toTimeStamp(orderGoodsEntity.getRegistTime()));
        orderGoodsRequest.setUpdateTime(conversionUtility.toTimeStamp(orderGoodsEntity.getUpdateTime()));
        orderGoodsRequest.setGroupCode(orderGoodsEntity.getGroupCode());
        orderGoodsRequest.setSaleCode(orderGoodsEntity.getSaleCode());
        orderGoodsRequest.setNote(orderGoodsEntity.getNote());
        orderGoodsRequest.setHints(orderGoodsEntity.getHints());
        orderGoodsRequest.setPrice(orderGoodsEntity.getPrice());
        orderGoodsRequest.setBundleFlag(orderGoodsEntity.isBundleFlag());
        orderGoodsRequest.setOrderSerial(orderGoodsEntity.getOrderSerial());
        orderGoodsRequest.setUnitDiscountPrice(orderGoodsEntity.getUnitDiscountPrice());
        orderGoodsRequest.setStockDate(conversionUtility.toTimeStamp(orderGoodsEntity.getStockDate()));
        orderGoodsRequest.setDeliveryYesNo(orderGoodsEntity.getDeliveryYesNo());
        orderGoodsRequest.setDipendingOnReceiptOrderCode(orderGoodsEntity.getDipendingOnReceiptOrderCode());

        if (orderGoodsEntity.getTotalingType() != null) {
            orderGoodsRequest.setTotalingType(orderGoodsEntity.getTotalingType().getValue());
        }
        if (orderGoodsEntity.getSalesFlag() != null) {
            orderGoodsRequest.setSalesFlag(orderGoodsEntity.getSalesFlag().getValue());
        }
        if (orderGoodsEntity.getGoodsTaxType() != null) {
            orderGoodsRequest.setGoodsTaxType(orderGoodsEntity.getGoodsTaxType().getValue());
        }
        if (orderGoodsEntity.getFreeDeliveryFlag() != null) {
            orderGoodsRequest.setFreeDeliveryFlag(orderGoodsEntity.getFreeDeliveryFlag().getValue());
        }
        if (orderGoodsEntity.getIndividualDeliveryType() != null) {
            orderGoodsRequest.setIndividualDeliveryType(orderGoodsEntity.getIndividualDeliveryType().getValue());
        }
        if (orderGoodsEntity.getDeliverySlipFlag() != null) {
            orderGoodsRequest.setDeliverySlipFlag(orderGoodsEntity.getDeliverySlipFlag().getValue());
        }
        if (orderGoodsEntity.getDiscountsType() != null) {
            orderGoodsRequest.setDiscountsType(orderGoodsEntity.getDiscountsType().getValue());
        }
        if (orderGoodsEntity.getStockManagementFlag() != null) {
            orderGoodsRequest.setStockManagementFlag(orderGoodsEntity.getStockManagementFlag().getValue());
        }

        return orderGoodsRequest;
    }

    /**
     * 取りおき情報DTOリクエストリストに変換
     *
     * @param orderReserveDeliveryDtoList 取りおき情報DTOリスト
     * @return 取りおき情報DTOリクエストリスト
     */
    private List<OrderReserveDeliveryDtoRequest> toOrderReserveDeliveryDtoRequestList(List<OrderReserveDeliveryDto> orderReserveDeliveryDtoList) {

        if (CollectionUtil.isEmpty(orderReserveDeliveryDtoList)) {
            return new ArrayList<>();
        }

        List<OrderReserveDeliveryDtoRequest> orderReserveDeliveryDtoRequestList = new ArrayList<>();

        orderReserveDeliveryDtoList.forEach(orderReserveDeliveryDto -> {
            OrderReserveDeliveryDtoRequest orderReserveDeliveryDtoRequest = new OrderReserveDeliveryDtoRequest();
            orderReserveDeliveryDtoRequest.setOrderGoodsEntity(
                            toOrderGoodsEntity(orderReserveDeliveryDto.getOrderGoodsEntity()));
        });

        return orderReserveDeliveryDtoRequestList;
    }

    /**
     * 受注商品リクエストに変換
     *
     * @param orderGoodsEntity 受注商品
     * @return 受注商品リクエスト
     */
    private OrderGoodsRequest toOrderGoodsEntity(OrderGoodsEntity orderGoodsEntity) {

        if (ObjectUtils.isEmpty(orderGoodsEntity)) {
            return null;
        }

        OrderGoodsRequest orderGoodsRequest = new OrderGoodsRequest();

        orderGoodsRequest.setOrderSeq(orderGoodsEntity.getOrderSeq());
        orderGoodsRequest.setOrderGoodsVersionNo(orderGoodsEntity.getOrderGoodsVersionNo());
        orderGoodsRequest.setOrderConsecutiveNo(orderGoodsEntity.getOrderConsecutiveNo());
        orderGoodsRequest.setGoodsSeq(orderGoodsEntity.getGoodsSeq());
        orderGoodsRequest.setOrderDisplay(orderGoodsEntity.getOrderDisplay());
        orderGoodsRequest.setProcessTime(conversionUtility.toTimeStamp(orderGoodsEntity.getProcessTime()));
        orderGoodsRequest.setGoodsGroupCode(orderGoodsEntity.getGoodsGroupCode());
        orderGoodsRequest.setGoodsCode(orderGoodsEntity.getGoodsCode());
        orderGoodsRequest.setJanCode(orderGoodsEntity.getJanCode());
        orderGoodsRequest.setGoodsGroupName(orderGoodsEntity.getGoodsGroupName());
        orderGoodsRequest.setTaxRate(orderGoodsEntity.getTaxRate());
        orderGoodsRequest.setGoodsPrice(orderGoodsEntity.getGoodsPrice());
        orderGoodsRequest.setPreGoodsCount(orderGoodsEntity.getPreGoodsCount());
        orderGoodsRequest.setGoodsCount(orderGoodsEntity.getGoodsCount());
        orderGoodsRequest.setUnitValue1(orderGoodsEntity.getUnitValue1());
        orderGoodsRequest.setUnitValue2(orderGoodsEntity.getUnitValue2());
        orderGoodsRequest.setDeliveryType(orderGoodsEntity.getDeliveryType());
        orderGoodsRequest.setOrderSetting1(orderGoodsEntity.getOrderSetting1());
        orderGoodsRequest.setOrderSetting2(orderGoodsEntity.getOrderSetting2());
        orderGoodsRequest.setOrderSetting3(orderGoodsEntity.getOrderSetting3());
        orderGoodsRequest.setOrderSetting4(orderGoodsEntity.getOrderSetting4());
        orderGoodsRequest.setOrderSetting5(orderGoodsEntity.getOrderSetting5());
        orderGoodsRequest.setOrderSetting6(orderGoodsEntity.getOrderSetting6());
        orderGoodsRequest.setOrderSetting7(orderGoodsEntity.getOrderSetting7());
        orderGoodsRequest.setOrderSetting8(orderGoodsEntity.getOrderSetting8());
        orderGoodsRequest.setOrderSetting9(orderGoodsEntity.getOrderSetting9());
        orderGoodsRequest.setOrderSetting10(orderGoodsEntity.getOrderSetting10());
        orderGoodsRequest.setRegistTime(conversionUtility.toTimeStamp(orderGoodsEntity.getRegistTime()));
        orderGoodsRequest.setUpdateTime(conversionUtility.toTimeStamp(orderGoodsEntity.getUpdateTime()));
        orderGoodsRequest.setGroupCode(orderGoodsEntity.getGroupCode());
        orderGoodsRequest.setSaleCode(orderGoodsEntity.getSaleCode());
        orderGoodsRequest.setNote(orderGoodsEntity.getNote());
        orderGoodsRequest.setHints(orderGoodsEntity.getHints());
        orderGoodsRequest.setPrice(orderGoodsEntity.getPrice());
        orderGoodsRequest.setBundleFlag(orderGoodsEntity.isBundleFlag());
        orderGoodsRequest.setOrderSerial(orderGoodsEntity.getOrderSerial());
        orderGoodsRequest.setUnitDiscountPrice(orderGoodsEntity.getUnitDiscountPrice());
        orderGoodsRequest.setStockDate(conversionUtility.toTimeStamp(orderGoodsEntity.getStockDate()));
        orderGoodsRequest.setDeliveryYesNo(orderGoodsEntity.getDeliveryYesNo());
        orderGoodsRequest.setDipendingOnReceiptOrderCode(orderGoodsEntity.getDipendingOnReceiptOrderCode());

        if (orderGoodsEntity.getTotalingType() != null) {
            orderGoodsRequest.setTotalingType(orderGoodsEntity.getTotalingType().getValue());
        }
        if (orderGoodsEntity.getSalesFlag() != null) {
            orderGoodsRequest.setSalesFlag(orderGoodsEntity.getSalesFlag().getValue());
        }
        if (orderGoodsEntity.getGoodsTaxType() != null) {
            orderGoodsRequest.setGoodsTaxType(orderGoodsEntity.getGoodsTaxType().getValue());
        }
        if (orderGoodsEntity.getFreeDeliveryFlag() != null) {
            orderGoodsRequest.setFreeDeliveryFlag(orderGoodsEntity.getFreeDeliveryFlag().getValue());
        }
        if (orderGoodsEntity.getIndividualDeliveryType() != null) {
            orderGoodsRequest.setIndividualDeliveryType(orderGoodsEntity.getIndividualDeliveryType().getValue());
        }
        if (orderGoodsEntity.getDeliverySlipFlag() != null) {
            orderGoodsRequest.setDeliverySlipFlag(orderGoodsEntity.getDeliverySlipFlag().getValue());
        }
        if (orderGoodsEntity.getDiscountsType() != null) {
            orderGoodsRequest.setDiscountsType(orderGoodsEntity.getDiscountsType().getValue());
        }
        if (orderGoodsEntity.getStockManagementFlag() != null) {
            orderGoodsRequest.setStockManagementFlag(orderGoodsEntity.getStockManagementFlag().getValue());
        }

        return orderGoodsRequest;
    }

    /**
     * 入荷次第お届け分DTOリクエストに変換
     *
     * @param orderDependingOnReceiptGoodsDtoList 入荷次第お届け分DTOクラスリスト
     * @return 入荷次第お届け分DTOリクエスト
     */
    private List<OrderDependingOnReceiptGoodsDtoRequest> toOrderDependingOnReceiptGoodsDtoList(List<OrderDependingOnReceiptGoodsDto> orderDependingOnReceiptGoodsDtoList) {

        if (CollectionUtil.isEmpty(orderDependingOnReceiptGoodsDtoList)) {
            return new ArrayList<>();
        }

        List<OrderDependingOnReceiptGoodsDtoRequest> orderDependingOnReceiptGoodsDtoRequestList = new ArrayList<>();

        orderDependingOnReceiptGoodsDtoList.forEach(orderDependingOnReceiptGoodsDto -> {
            OrderDependingOnReceiptGoodsDtoRequest orderDependingOnReceiptGoodsDtoRequest =
                            new OrderDependingOnReceiptGoodsDtoRequest();

            orderDependingOnReceiptGoodsDtoRequest.setOrderGoodsEntity(
                            toOrderGoodsEntity(orderDependingOnReceiptGoodsDto.getOrderGoodsEntity()));
            orderDependingOnReceiptGoodsDtoRequest.setStockDate(
                            conversionUtility.toTimeStamp(orderDependingOnReceiptGoodsDto.getStockDate()));
            if (orderDependingOnReceiptGoodsDto.getStockManagementFlag() != null) {
                orderDependingOnReceiptGoodsDtoRequest.setStockManagementFlag(
                                orderDependingOnReceiptGoodsDto.getStockManagementFlag().getValue());
            }

            orderDependingOnReceiptGoodsDtoRequestList.add(orderDependingOnReceiptGoodsDtoRequest);
        });

        return orderDependingOnReceiptGoodsDtoRequestList;
    }

    /**
     * 取りおきMapレスポンスに変換
     *
     * @param orderGetReserveMapResponse 取りおき情報Map
     * @return 取りおきMapレスポンス
     */
    public Map<String, WebApiGetReserveResponseDetailDto> toWebApiGetReserveResponseDetailDtoMap(
                    OrderGetReserveMapResponse orderGetReserveMapResponse) {

        Map<String, WebApiGetReserveResponseDetailDto> webApiGetReserveResponseDetailDtoMap = new HashMap<>();

        if (MapUtils.isNotEmpty(orderGetReserveMapResponse.getReserveDtoMap())) {
            for (Map.Entry<String, WebApiGetReserveResponseDetailDtoResponse> entry : orderGetReserveMapResponse.getReserveDtoMap()
                                                                                                                .entrySet()) {

                WebApiGetReserveResponseDetailDtoResponse webApiGetReserveResponseDetailDtoResponse = entry.getValue();
                WebApiGetReserveResponseDetailDto webApiGetReserveResponseDetailDto =
                                new WebApiGetReserveResponseDetailDto();

                webApiGetReserveResponseDetailDto.setGoodsCode(
                                webApiGetReserveResponseDetailDtoResponse.getGoodsCode());
                webApiGetReserveResponseDetailDto.setReserveFlag(
                                webApiGetReserveResponseDetailDtoResponse.getReserveFlag());
                webApiGetReserveResponseDetailDto.setDiscountFlag(
                                webApiGetReserveResponseDetailDtoResponse.getDiscountFlag());
                // 2023-renew No14 from here
                webApiGetReserveResponseDetailDto.setPossibleReserveFromDay(conversionUtility.toTimeStamp(
                                webApiGetReserveResponseDetailDtoResponse.getPossibleReserveFromDay()));
                webApiGetReserveResponseDetailDto.setPossibleReserveToDay(conversionUtility.toTimeStamp(
                                webApiGetReserveResponseDetailDtoResponse.getPossibleReserveToDay()));
                // 2023-renew No14 to here

                webApiGetReserveResponseDetailDtoMap.put(entry.getKey(), webApiGetReserveResponseDetailDto);
            }
        }
        return webApiGetReserveResponseDetailDtoMap;
    }

    /**
     * 商品詳細DtoListリストに変換
     *
     * @param goodsDetailsDtoResponseList 商品詳細Dtoクラスレスポンス
     * @return 商品詳細Dtoリスト
     */
    public List<GoodsDetailsDto> toListGoodsDetailsDtoResponse(List<jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsDtoResponse> goodsDetailsDtoResponseList)
                    throws InvocationTargetException, IllegalAccessException {

        if (goodsDetailsDtoResponseList == null) {
            return new ArrayList<>();
        }

        List<GoodsDetailsDto> goodsDetailsDtoList = new ArrayList<>();
        for (jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsDtoResponse goodsDetailsDtoResponse : goodsDetailsDtoResponseList) {
            GoodsDetailsDto detailsDtoResponse = this.toGoodsDetailsDtoGoods(goodsDetailsDtoResponse);
            goodsDetailsDtoList.add(detailsDtoResponse);
        }
        return goodsDetailsDtoList;
    }

    /**
     * 商品詳細Dtoに変換
     *
     * @param goodsDetailsDtoResponse 商品詳細Dtoクラスレスポンス
     * @return 商品詳細Dto
     */
    public GoodsDetailsDto toGoodsDetailsDtoGoods(jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsDtoResponse goodsDetailsDtoResponse) {

        if (ObjectUtils.isEmpty(goodsDetailsDtoResponse)) {
            return null;
        }

        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();

        goodsDetailsDto.setGoodsSeq(goodsDetailsDtoResponse.getGoodsSeq());
        goodsDetailsDto.setGoodsGroupSeq(goodsDetailsDtoResponse.getGoodsGroupSeq());
        goodsDetailsDto.setShopSeq(1001);
        goodsDetailsDto.setVersionNo(goodsDetailsDtoResponse.getVersionNo());
        goodsDetailsDto.setRegistTime(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getRegistTime()));
        goodsDetailsDto.setUpdateTime(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getUpdateTime()));
        goodsDetailsDto.setGoodsCode(goodsDetailsDtoResponse.getGoodsCode());
        goodsDetailsDto.setGoodsGroupMaxPrice(goodsDetailsDtoResponse.getGoodsGroupMaxPrice());
        goodsDetailsDto.setGoodsGroupMinPrice(goodsDetailsDtoResponse.getGoodsGroupMinPrice());
        goodsDetailsDto.setPreDiscountMinPrice(goodsDetailsDtoResponse.getPreDiscountMinPrice());
        goodsDetailsDto.setPreDiscountMaxPrice(goodsDetailsDtoResponse.getPreDiscountMaxPrice());
        goodsDetailsDto.setTaxRate(goodsDetailsDtoResponse.getTaxRate());
        goodsDetailsDto.setGoodsPriceInTax(goodsDetailsDtoResponse.getGoodsPriceInTax());
        goodsDetailsDto.setGoodsPrice(goodsDetailsDtoResponse.getGoodsPrice());
        goodsDetailsDto.setDeliveryType(goodsDetailsDtoResponse.getDeliveryType());
        goodsDetailsDto.setSaleStartTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getSaleStartTime()));
        goodsDetailsDto.setSaleEndTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getSaleEndTime()));
        goodsDetailsDto.setPurchasedMax(goodsDetailsDtoResponse.getPurchasedMax());
        goodsDetailsDto.setOrderDisplay(goodsDetailsDtoResponse.getOrderDisplay());
        goodsDetailsDto.setUnitValue1(goodsDetailsDtoResponse.getUnitValue1());
        goodsDetailsDto.setUnitValue2(goodsDetailsDtoResponse.getUnitValue2());
        goodsDetailsDto.setPreDiscountPrice(goodsDetailsDtoResponse.getPreDiscountPrice());
        goodsDetailsDto.setPreDisCountPriceInTax(goodsDetailsDtoResponse.getPreDisCountPriceInTax());
        goodsDetailsDto.setJanCode(goodsDetailsDtoResponse.getJanCode());
        goodsDetailsDto.setCatalogCode(goodsDetailsDtoResponse.getCatalogCode());
        goodsDetailsDto.setSalesPossibleStock(goodsDetailsDtoResponse.getSalesPossibleStock());
        goodsDetailsDto.setRealStock(goodsDetailsDtoResponse.getRealStock());
        goodsDetailsDto.setOrderReserveStock(goodsDetailsDtoResponse.getOrderReserveStock());
        goodsDetailsDto.setRemainderFewStock(goodsDetailsDtoResponse.getRemainderFewStock());
        goodsDetailsDto.setOrderPointStock(goodsDetailsDtoResponse.getOrderPointStock());
        goodsDetailsDto.setSafetyStock(goodsDetailsDtoResponse.getSafetyStock());
        goodsDetailsDto.setGoodsGroupCode(goodsDetailsDtoResponse.getGoodsGroupCode());
        goodsDetailsDto.setWhatsnewDate(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getWhatsnewDate()));
        goodsDetailsDto.setOpenStartTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getOpenStartTime()));
        goodsDetailsDto.setOpenEndTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getOpenEndTime()));
        goodsDetailsDto.setGoodsGroupName(goodsDetailsDtoResponse.getGoodsGroupName());
        goodsDetailsDto.setUnitTitle1(goodsDetailsDtoResponse.getUnitTitle1());
        goodsDetailsDto.setUnitTitle2(goodsDetailsDtoResponse.getUnitTitle2());
        goodsDetailsDto.setGoodsPreDiscountPrice(goodsDetailsDtoResponse.getGoodsPreDiscountPrice());
        goodsDetailsDto.setMetaDescription(goodsDetailsDtoResponse.getMetaDescription());
        goodsDetailsDto.setGoodsNote1(goodsDetailsDtoResponse.getGoodsNote1());
        goodsDetailsDto.setGoodsNote2(goodsDetailsDtoResponse.getGoodsNote2());
        goodsDetailsDto.setGoodsNote3(goodsDetailsDtoResponse.getGoodsNote3());
        goodsDetailsDto.setGoodsNote4(goodsDetailsDtoResponse.getGoodsNote4());
        goodsDetailsDto.setGoodsNote5(goodsDetailsDtoResponse.getGoodsNote5());
        goodsDetailsDto.setGoodsNote6(goodsDetailsDtoResponse.getGoodsNote6());
        goodsDetailsDto.setGoodsNote7(goodsDetailsDtoResponse.getGoodsNote7());
        goodsDetailsDto.setGoodsNote8(goodsDetailsDtoResponse.getGoodsNote8());
        goodsDetailsDto.setGoodsNote9(goodsDetailsDtoResponse.getGoodsNote9());
        goodsDetailsDto.setGoodsNote10(goodsDetailsDtoResponse.getGoodsNote10());
        goodsDetailsDto.setOrderSetting1(goodsDetailsDtoResponse.getOrderSetting1());
        goodsDetailsDto.setOrderSetting2(goodsDetailsDtoResponse.getOrderSetting2());
        goodsDetailsDto.setOrderSetting3(goodsDetailsDtoResponse.getOrderSetting3());
        goodsDetailsDto.setOrderSetting4(goodsDetailsDtoResponse.getOrderSetting4());
        goodsDetailsDto.setOrderSetting5(goodsDetailsDtoResponse.getOrderSetting5());
        goodsDetailsDto.setOrderSetting6(goodsDetailsDtoResponse.getOrderSetting6());
        goodsDetailsDto.setOrderSetting7(goodsDetailsDtoResponse.getOrderSetting7());
        goodsDetailsDto.setOrderSetting8(goodsDetailsDtoResponse.getOrderSetting8());
        goodsDetailsDto.setOrderSetting9(goodsDetailsDtoResponse.getOrderSetting9());
        goodsDetailsDto.setOrderSetting10(goodsDetailsDtoResponse.getOrderSetting10());
        goodsDetailsDto.setGoodsOptionDisplayName(goodsDetailsDtoResponse.getGoodsOptionDisplayName());
        goodsDetailsDto.setCoolSendFrom(goodsDetailsDtoResponse.getCoolSendFrom());
        goodsDetailsDto.setCoolSendTo(goodsDetailsDtoResponse.getCoolSendTo());
        goodsDetailsDto.setTax(goodsDetailsDtoResponse.getTax());
        goodsDetailsDto.setUnit(goodsDetailsDtoResponse.getUnit());
        goodsDetailsDto.setGoodsManagementCode(goodsDetailsDtoResponse.getGoodsManagementCode());
        goodsDetailsDto.setGoodsDivisionCode(goodsDetailsDtoResponse.getGoodsDivisionCode());
        goodsDetailsDto.setGoodsCategory1(goodsDetailsDtoResponse.getGoodsCategory1());
        goodsDetailsDto.setGoodsCategory2(goodsDetailsDtoResponse.getGoodsCategory2());
        goodsDetailsDto.setGoodsCategory3(goodsDetailsDtoResponse.getGoodsCategory3());
        goodsDetailsDto.setSaleYesNo(goodsDetailsDtoResponse.getSaleYesNo());
        goodsDetailsDto.setSaleNgMessage(goodsDetailsDtoResponse.getSaleNgMessage());
        goodsDetailsDto.setDeliveryYesNo(goodsDetailsDtoResponse.getDeliveryYesNo());

        goodsDetailsDto.setGoodsTaxType(EnumTypeUtil.getEnumFromValue(HTypeGoodsTaxType.class,
                                                                      goodsDetailsDtoResponse.getGoodsTaxType()
                                                                     ));
        goodsDetailsDto.setAlcoholFlag(EnumTypeUtil.getEnumFromValue(HTypeAlcoholFlag.class,
                                                                     goodsDetailsDtoResponse.getAlcoholFlag()
                                                                    ));
        goodsDetailsDto.setSaleStatusPC(EnumTypeUtil.getEnumFromValue(HTypeGoodsSaleStatus.class,
                                                                      goodsDetailsDtoResponse.getSaleStatus()
                                                                     ));
        goodsDetailsDto.setUnitManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class,
                                                                            goodsDetailsDtoResponse.getUnitManagementFlag()
                                                                           ));
        goodsDetailsDto.setStockManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeStockManagementFlag.class,
                                                                             goodsDetailsDtoResponse.getStockManagementFlag()
                                                                            ));
        goodsDetailsDto.setIndividualDeliveryType(EnumTypeUtil.getEnumFromValue(HTypeIndividualDeliveryType.class,
                                                                                goodsDetailsDtoResponse.getIndividualDeliveryType()
                                                                               ));
        goodsDetailsDto.setFreeDeliveryFlag(EnumTypeUtil.getEnumFromValue(HTypeFreeDeliveryFlag.class,
                                                                          goodsDetailsDtoResponse.getFreeDeliveryFlag()
                                                                         ));
        goodsDetailsDto.setGoodsOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                           goodsDetailsDtoResponse.getGoodsOpenStatus()
                                                                          ));
        goodsDetailsDto.setSnsLinkFlag(EnumTypeUtil.getEnumFromValue(HTypeSnsLinkFlag.class,
                                                                     goodsDetailsDtoResponse.getSnsLinkFlag()
                                                                    ));
        goodsDetailsDto.setStockStatusPc(EnumTypeUtil.getEnumFromValue(HTypeStockStatusType.class,
                                                                       goodsDetailsDtoResponse.getStockStatus()
                                                                      ));
        goodsDetailsDto.setGoodsClassType(EnumTypeUtil.getEnumFromValue(HTypeGoodsClassType.class,
                                                                        goodsDetailsDtoResponse.getGoodsClassType()
                                                                       ));
        goodsDetailsDto.setDentalMonopolySalesFlg(EnumTypeUtil.getEnumFromValue(HTypeDentalMonopolySalesFlg.class,
                                                                                goodsDetailsDtoResponse.getDentalMonopolySalesFlg()
                                                                               ));
        goodsDetailsDto.setSaleIconFlag(EnumTypeUtil.getEnumFromValue(HTypeSaleIconFlag.class,
                                                                      goodsDetailsDtoResponse.getSaleIconFlag()
                                                                     ));
        goodsDetailsDto.setReserveIconFlag(EnumTypeUtil.getEnumFromValue(HTypeReserveIconFlag.class,
                                                                         goodsDetailsDtoResponse.getReserveIconFlag()
                                                                        ));
        goodsDetailsDto.setNewIconFlag(EnumTypeUtil.getEnumFromValue(HTypeNewIconFlag.class,
                                                                     goodsDetailsDtoResponse.getNewIconFlag()
                                                                    ));
        goodsDetailsDto.setLandSendFlag(EnumTypeUtil.getEnumFromValue(HTypeLandSendFlag.class,
                                                                      goodsDetailsDtoResponse.getLandSendFlag()
                                                                     ));
        goodsDetailsDto.setCoolSendFlag(EnumTypeUtil.getEnumFromValue(HTypeCoolSendFlag.class,
                                                                      goodsDetailsDtoResponse.getCoolSendFlag()
                                                                     ));
        goodsDetailsDto.setReserveFlag(EnumTypeUtil.getEnumFromValue(HTypeReserveFlag.class,
                                                                     goodsDetailsDtoResponse.getReserveFlag()
                                                                    ));
        goodsDetailsDto.setPriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypePriceMarkDispFlag.class,
                                                                           goodsDetailsDtoResponse.getPriceMarkDispFlag()
                                                                          ));
        goodsDetailsDto.setSalePriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceMarkDispFlag.class,
                                                                               goodsDetailsDtoResponse.getSalePriceMarkDispFlag()
                                                                              ));
        goodsDetailsDto.setSalePriceIntegrityFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceIntegrityFlag.class,
                                                                                goodsDetailsDtoResponse.getSalePriceIntegrityFlag()
                                                                               ));
        goodsDetailsDto.setEmotionPriceType(EnumTypeUtil.getEnumFromValue(HTypeEmotionPriceType.class,
                                                                          goodsDetailsDtoResponse.getEmotionPriceType()
                                                                         ));

        goodsDetailsDto.setGoodsGroupImageEntityList(
                        toGoodsGroupImageEntityListCart(goodsDetailsDtoResponse.getGoodsGroupImageEntityList()));
        goodsDetailsDto.setUnitImage(toGoodsImageEntity(goodsDetailsDtoResponse.getUnitImage()));

        return goodsDetailsDto;
    }

    /**
     * 商品グループ画像リストに変換
     *
     * @param goodsGroupImageEntityResponseList 商品グループ画像リストレスポンス
     * @return 商品グループ画像リスト
     */
    private List<GoodsGroupImageEntity> toGoodsGroupImageEntityListCart(List<jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupImageEntityResponse> goodsGroupImageEntityResponseList) {

        if (CollectionUtil.isEmpty(goodsGroupImageEntityResponseList)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntity> goodsGroupImageEntityList = new ArrayList<>();

        goodsGroupImageEntityResponseList.forEach(goodsGroupImageEntityResponse -> {
            GoodsGroupImageEntity goodsGroupImageEntity = new GoodsGroupImageEntity();

            goodsGroupImageEntity.setGoodsGroupSeq(goodsGroupImageEntityResponse.getGoodsGroupSeq());
            goodsGroupImageEntity.setImageTypeVersionNo(goodsGroupImageEntityResponse.getImageTypeVersionNo());
            goodsGroupImageEntity.setImageFileName(goodsGroupImageEntityResponse.getImageFileName());
            goodsGroupImageEntity.setRegistTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntityResponse.getRegistTime()));
            goodsGroupImageEntity.setUpdateTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntityResponse.getUpdateTime()));

            goodsGroupImageEntityList.add(goodsGroupImageEntity);
        });

        return goodsGroupImageEntityList;
    }

    /**
     * 商品画像に変換
     *
     * @param goodsImageEntityResponse 商品グループ画像レスポンス
     * @return 商品画像
     */
    public GoodsImageEntity toGoodsImageEntity(jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsImageEntityResponse goodsImageEntityResponse) {
        if (ObjectUtils.isEmpty(goodsImageEntityResponse)) {
            return null;
        }
        GoodsImageEntity imageEntity = new GoodsImageEntity();

        imageEntity.setGoodsGroupSeq(goodsImageEntityResponse.getGoodsGroupSeq());
        imageEntity.setGoodsSeq(goodsImageEntityResponse.getGoodsSeq());
        imageEntity.setImageFileName(goodsImageEntityResponse.getImageFileName());
        imageEntity.setDisplayFlag(EnumTypeUtil.getEnumFromValue(HTypeDisplayStatus.class,
                                                                 goodsImageEntityResponse.getDisplayFlag()
                                                                ));
        imageEntity.setRegistTime(conversionUtility.toTimeStamp(goodsImageEntityResponse.getRegistTime()));
        imageEntity.setUpdateTime(conversionUtility.toTimeStamp(goodsImageEntityResponse.getUpdateTime()));
        imageEntity.setTmpFilePath(goodsImageEntityResponse.getTmpFilePath());

        return imageEntity;
    }

    /**
     * 割引適用結果取得リクエストに変換
     *
     * @param reqDto 割引適用結果取得
     * @return 割引適用結果取得リクエスト
     */
    public WebApiGetDiscountsResultRequest toWebApiGetDiscountsResultRequest(WebApiGetDiscountsRequestDto reqDto) {

        if (ObjectUtils.isEmpty(reqDto)) {
            return null;
        }

        WebApiGetDiscountsResultRequest webApiGetDiscountsResultRequest = new WebApiGetDiscountsResultRequest();
        webApiGetDiscountsResultRequest.setGoodsCode(reqDto.getGoodsCode());
        webApiGetDiscountsResultRequest.setCustomerNo(reqDto.getCustomerNo());
        webApiGetDiscountsResultRequest.setQuantity(reqDto.getQuantity());
        webApiGetDiscountsResultRequest.setOrderDisplay(reqDto.getOrderDisplay());

        return webApiGetDiscountsResultRequest;
    }

    /**
     * 割引適用結果取得結果DTOクラスに変換
     *
     * @param webApiGetDiscountsResultResponse WEB-API連携割引適用結果取得レスポンス
     * @return 割引適用結果取得結果DTOクラス
     */
    public WebApiGetDiscountsResponseDto toWebApiGetDiscountsResponseDto(WebApiGetDiscountsResultResponse webApiGetDiscountsResultResponse) {

        if (ObjectUtils.isEmpty(webApiGetDiscountsResultResponse)) {
            return null;
        }

        WebApiGetDiscountsResponseDto webApiGetDiscountsResponseDto = new WebApiGetDiscountsResponseDto();

        webApiGetDiscountsResponseDto.setInfo(
                        toWebApiGetDiscountsResponseDetailDtoList(webApiGetDiscountsResultResponse.getInfo()));
        webApiGetDiscountsResponseDto.setResult(
                        toAbstractWebApiResponseResultDto(webApiGetDiscountsResultResponse.getResult()));

        return webApiGetDiscountsResponseDto;
    }

    /**
     * 割引適用結果取得 詳細情報DTOクラスリスト
     *
     * @param info 割引適用結果取得 詳細情報レスポンス
     * @return 割引適用結果取得 詳細情報DTOクラスリスト
     */
    public List<WebApiGetDiscountsResponseDetailDto> toWebApiGetDiscountsResponseDetailDtoList(List<WebApiGetDiscountsResponseDetailDtoResponse> info) {

        if (CollectionUtil.isEmpty(info)) {
            return new ArrayList<>();
        }

        List<WebApiGetDiscountsResponseDetailDto> webApiGetDiscountsResponseDetailDtoList = new ArrayList<>();

        for (WebApiGetDiscountsResponseDetailDtoResponse webApiGetDiscountsResponseDetailDtoResponse : info) {
            WebApiGetDiscountsResponseDetailDto webApiGetDiscountsResponseDetailDto =
                            new WebApiGetDiscountsResponseDetailDto();

            webApiGetDiscountsResponseDetailDto.setGoodsCode(
                            webApiGetDiscountsResponseDetailDtoResponse.getGoodsCode());
            webApiGetDiscountsResponseDetailDto.setSaleType(webApiGetDiscountsResponseDetailDtoResponse.getSaleType());
            webApiGetDiscountsResponseDetailDto.setSaleGroupCode(
                            webApiGetDiscountsResponseDetailDtoResponse.getSaleGroupCode());
            webApiGetDiscountsResponseDetailDto.setSalePrice(
                            webApiGetDiscountsResponseDetailDtoResponse.getSalePrice());
            webApiGetDiscountsResponseDetailDto.setQuantity(webApiGetDiscountsResponseDetailDtoResponse.getQuantity());
            webApiGetDiscountsResponseDetailDto.setSaleCode(webApiGetDiscountsResponseDetailDtoResponse.getSaleCode());
            webApiGetDiscountsResponseDetailDto.setNote(webApiGetDiscountsResponseDetailDtoResponse.getNote());
            webApiGetDiscountsResponseDetailDto.setHints(webApiGetDiscountsResponseDetailDtoResponse.getHints());
            webApiGetDiscountsResponseDetailDto.setOrderDisplay(
                            webApiGetDiscountsResponseDetailDtoResponse.getOrderDisplay());

            webApiGetDiscountsResponseDetailDtoList.add(webApiGetDiscountsResponseDetailDto);
        }

        return webApiGetDiscountsResponseDetailDtoList;
    }

    /**
     * 消費税率取得 詳細情報MAPに変換
     *
     * @param consumptionTaxRateResponse WEB-API連携 消費税率レスポンス
     * @return 消費税率取得 詳細情報MAP
     */
    public Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> toWebApiGetConsumptionTaxRateResponseDetailDtoMap(
                    ConsumptionTaxRateResponse consumptionTaxRateResponse) {

        Map<String, WebApiGetConsumptionTaxRateResponseDetailDto>
                        stringWebApiGetConsumptionTaxRateResponseDetailDtoMap = new HashMap<>();
        if (MapUtils.isNotEmpty(consumptionTaxRateResponse.getTaxRateMap())) {
            for (Map.Entry<String, WebApiGetConsumptionTaxRateResponseDetailDtoResponse> entry : consumptionTaxRateResponse.getTaxRateMap()
                                                                                                                           .entrySet()) {
                WebApiGetConsumptionTaxRateResponseDetailDto webApiGetConsumptionTaxRateResponseDetailDto =
                                new WebApiGetConsumptionTaxRateResponseDetailDto();
                webApiGetConsumptionTaxRateResponseDetailDto.setGoodsCode(entry.getValue().getGoodsCode());
                webApiGetConsumptionTaxRateResponseDetailDto.setTaxRate(entry.getValue().getTaxRate());

                stringWebApiGetConsumptionTaxRateResponseDetailDtoMap.put(
                                entry.getKey(), webApiGetConsumptionTaxRateResponseDetailDto);
            }
        }

        return stringWebApiGetConsumptionTaxRateResponseDetailDtoMap;
    }

    /**
     * 数量割引取得結果連携リクエストに変換
     *
     * @param receiveOrderDto  受注DTO
     * @param taxRateMap       消費税率MAP
     * @param checkMessageList エラーメッセージ用List
     * @return 数量割引取得結果連携リクエスト
     */
    public OrderQuantityDiscountRequest toOrderQuantityDiscountRequest(ReceiveOrderDto receiveOrderDto,
                                                                       Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> taxRateMap,
                                                                       List<CheckMessageDto> checkMessageList) {
        OrderQuantityDiscountRequest orderQuantityDiscountRequest = new OrderQuantityDiscountRequest();

        if (ObjectUtils.isNotEmpty(receiveOrderDto)) {
            orderQuantityDiscountRequest.setReceiveOrderDto(toReceiveOrderDtoJson(receiveOrderDto));
        }

        if (MapUtils.isNotEmpty(taxRateMap)) {
            orderQuantityDiscountRequest.setTaxRateMap(
                            toWebApiGetConsumptionTaxRateResponseDetailDtoRequestMap(taxRateMap));
        }

        if (CollectionUtil.isNotEmpty(checkMessageList)) {
            orderQuantityDiscountRequest.setCheckMessageDtoList(toCheckMessageDtoRequestList(checkMessageList));
        }

        return orderQuantityDiscountRequest;
    }

    /**
     * 消費税率MAPに変換
     *
     * @param taxRateMap WEB-API連携取得結果DTOクラスMAP
     * @return 消費税率MAP
     */
    public Map<String, WebApiGetConsumptionTaxRateResponseDetailDtoRequest> toWebApiGetConsumptionTaxRateResponseDetailDtoRequestMap(
                    Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> taxRateMap) {
        Map<String, WebApiGetConsumptionTaxRateResponseDetailDtoRequest>
                        webApiGetConsumptionTaxRateResponseDetailDtoRequestMap = new HashMap<>();

        if (MapUtils.isNotEmpty(taxRateMap)) {
            for (Map.Entry<String, WebApiGetConsumptionTaxRateResponseDetailDto> entry : taxRateMap.entrySet()) {
                WebApiGetConsumptionTaxRateResponseDetailDtoRequest
                                webApiGetConsumptionTaxRateResponseDetailDtoRequest =
                                new WebApiGetConsumptionTaxRateResponseDetailDtoRequest();
                webApiGetConsumptionTaxRateResponseDetailDtoRequest.setGoodsCode(entry.getValue().getGoodsCode());
                webApiGetConsumptionTaxRateResponseDetailDtoRequest.setTaxRate(entry.getValue().getTaxRate());

                webApiGetConsumptionTaxRateResponseDetailDtoRequestMap.put(
                                entry.getKey(), webApiGetConsumptionTaxRateResponseDetailDtoRequest);
            }
        }

        return webApiGetConsumptionTaxRateResponseDetailDtoRequestMap;
    }

    /**
     * プロモーション連携リクエストに変換
     *
     * @param receiveOrderDtoList 受注DTOリスト
     * @param checkMessageList    エラーメッセージ用List
     * @return プロモーション連携リクエスト
     */
    public OrderPromotionInformationRequest toOrderPromotionInformationRequest(List<ReceiveOrderDto> receiveOrderDtoList,
                                                                               List<CheckMessageDto> checkMessageList) {
        OrderPromotionInformationRequest orderPromotionInformationRequest = new OrderPromotionInformationRequest();

        if (CollectionUtil.isNotEmpty(receiveOrderDtoList)) {
            orderPromotionInformationRequest.setReceiveOrderDtoList(toReceiveOrderDtoJsonList(receiveOrderDtoList));
        }
        if (CollectionUtil.isNotEmpty(checkMessageList)) {
            orderPromotionInformationRequest.setCheckMessageDtoList(toCheckMessageDtoRequestList(checkMessageList));
        }

        return orderPromotionInformationRequest;
    }

    /**
     * 決済方法クラスに変換
     *
     * @param settlementMethodResponse 決済方法レスポンス
     * @return 決済方法クラス
     * @throws Exception
     */
    public SettlementMethodEntity toSettlementMethodEntity(SettlementMethodResponse settlementMethodResponse)
                    throws Exception {

        if (ObjectUtils.isEmpty(settlementMethodResponse)) {
            return null;
        }

        SettlementMethodEntity settlementMethodEntity = new SettlementMethodEntity();
        settlementMethodEntity.setSettlementMethodSeq(settlementMethodResponse.getSettlementMethodSeq());
        settlementMethodEntity.setSettlementMethodName(settlementMethodResponse.getSettlementMethodName());
        settlementMethodEntity.setSettlementMethodDisplayNamePC(
                        settlementMethodResponse.getSettlementMethodDisplayName());
        settlementMethodEntity.setOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                             settlementMethodResponse.getOpenStatus()
                                                                            ));
        settlementMethodEntity.setSettlementNotePC(settlementMethodResponse.getSettlementNote());
        settlementMethodEntity.setSettlementMethodType(settlementMethodResponse.getSettlementMethodType() != null ?
                                                                       EnumTypeUtil.getEnumFromValue(
                                                                                       HTypeSettlementMethodType.class,
                                                                                       settlementMethodResponse.getSettlementMethodType()
                                                                                                    ) :
                                                                       null);
        settlementMethodEntity.setSettlementMethodCommissionType(
                        settlementMethodResponse.getSettlementMethodCommissionType() != null ?
                                        EnumTypeUtil.getEnumFromValue(
                                                        HTypeSettlementMethodCommissionType.class,
                                                        settlementMethodResponse.getSettlementMethodCommissionType()
                                                                     ) :
                                        null);
        settlementMethodEntity.setBillType(settlementMethodResponse.getBillType() != null ?
                                                           EnumTypeUtil.getEnumFromValue(HTypeBillType.class,
                                                                                         settlementMethodResponse.getBillType()
                                                                                        ) :
                                                           null);
        settlementMethodEntity.setDeliveryMethodSeq(settlementMethodResponse.getDeliveryMethodSeq());
        settlementMethodEntity.setEqualsCommission(settlementMethodResponse.getEqualsCommission());
        settlementMethodEntity.setSettlementMethodPriceCommissionFlag(
                        settlementMethodResponse.getSettlementMethodPriceCommissionFlag() != null ?
                                        EnumTypeUtil.getEnumFromValue(
                                                        HTypeSettlementMethodPriceCommissionFlag.class,
                                                        settlementMethodResponse.getSettlementMethodPriceCommissionFlag()
                                                                     ) :
                                        null);
        settlementMethodEntity.setLargeAmountDiscountPrice(settlementMethodResponse.getLargeAmountDiscountPrice());
        settlementMethodEntity.setLargeAmountDiscountCommission(
                        settlementMethodResponse.getLargeAmountDiscountCommission());
        settlementMethodEntity.setOrderDisplay(settlementMethodResponse.getOrderDisplay());
        settlementMethodEntity.setMaxPurchasedPrice(settlementMethodResponse.getMaxPurchasedPrice());
        settlementMethodEntity.setPaymentTimeLimitDayCount(settlementMethodResponse.getPaymentTimeLimitDayCount());
        settlementMethodEntity.setMinPurchasedPrice(settlementMethodResponse.getMinPurchasedPrice());
        settlementMethodEntity.setCancelTimeLimitDayCount(settlementMethodResponse.getCancelTimeLimitDayCount());
        settlementMethodEntity.setSettlementMailRequired(settlementMethodResponse.getSettlementMailRequired() != null ?
                                                                         EnumTypeUtil.getEnumFromValue(
                                                                                         HTypeMailRequired.class,
                                                                                         settlementMethodResponse.getSettlementMailRequired()
                                                                                                      ) :
                                                                         null);
        settlementMethodEntity.setEnableCardNoHolding(settlementMethodResponse.getEnableCardNoHolding() != null ?
                                                                      EnumTypeUtil.getEnumFromValue(
                                                                                      HTypeEffectiveFlag.class,
                                                                                      settlementMethodResponse.getEnableCardNoHolding()
                                                                                                   ) :
                                                                      null);
        settlementMethodEntity.setEnableSecurityCode(settlementMethodResponse.getEnableSecurityCode() != null ?
                                                                     EnumTypeUtil.getEnumFromValue(
                                                                                     HTypeEffectiveFlag.class,
                                                                                     settlementMethodResponse.getEnableSecurityCode()
                                                                                                  ) :
                                                                     null);
        settlementMethodEntity.setEnable3dSecure(settlementMethodResponse.getEnable3dSecure() != null ?
                                                                 EnumTypeUtil.getEnumFromValue(HTypeEffectiveFlag.class,
                                                                                               settlementMethodResponse.getEnable3dSecure()
                                                                                              ) :
                                                                 null);
        settlementMethodEntity.setEnableInstallment(settlementMethodResponse.getEnableInstallment() != null ?
                                                                    EnumTypeUtil.getEnumFromValue(
                                                                                    HTypeEffectiveFlag.class,
                                                                                    settlementMethodResponse.getEnableInstallment()
                                                                                                 ) :
                                                                    null);
        settlementMethodEntity.setEnableBonusSinglePayment(
                        settlementMethodResponse.getEnableBonusSinglePayment() != null ?
                                        EnumTypeUtil.getEnumFromValue(HTypeEffectiveFlag.class,
                                                                      settlementMethodResponse.getEnableBonusSinglePayment()
                                                                     ) :
                                        null);
        settlementMethodEntity.setEnableBonusInstallment(settlementMethodResponse.getEnableBonusInstallment() != null ?
                                                                         EnumTypeUtil.getEnumFromValue(
                                                                                         HTypeEffectiveFlag.class,
                                                                                         settlementMethodResponse.getEnableBonusInstallment()
                                                                                                      ) :
                                                                         null);
        settlementMethodEntity.setEnableRevolving(settlementMethodResponse.getEnableRevolving() != null ?
                                                                  EnumTypeUtil.getEnumFromValue(
                                                                                  HTypeEffectiveFlag.class,
                                                                                  settlementMethodResponse.getEnableRevolving()
                                                                                               ) :
                                                                  null);
        settlementMethodEntity.setRegistTime(conversionUtility.toTimeStamp(settlementMethodResponse.getRegistTime()));
        settlementMethodEntity.setUpdateTime(conversionUtility.toTimeStamp(settlementMethodResponse.getUpdateTime()));

        return settlementMethodEntity;
    }

    /**
     * 決済通信結果返却用Dtoに変換
     *
     * @param cardInfoResponse カード情報取得レスポンス
     * @return 決済通信結果返却用Dto
     */
    public ComResultDto toComResultDto(CardInfoResponse cardInfoResponse) {

        if (ObjectUtils.isEmpty(cardInfoResponse)) {
            return null;
        }

        ComResultDto comResultDto = new ComResultDto();

        comResultDto.setResultStatus(cardInfoResponse.getResultStatus());
        comResultDto.setResponseCode(cardInfoResponse.getResponseCode());
        comResultDto.setResponseDetail(cardInfoResponse.getResponseDetail());
        comResultDto.setMessageList(toCheckMessageDtoListMemberInfo(cardInfoResponse.getMessageList()));
        comResultDto.setResultMap(cardInfoResponse.getResultMap());
        comResultDto.setResultMapList(cardInfoResponse.getResultMapList());

        return comResultDto;
    }

    /**
     * チェックメッセージDtoListレスポンスに変換
     *
     * @param checkMessageDtoResponseList エラーリスト
     * @return チェックメッセージDtoListレスポンス
     */
    public List<CheckMessageDto> toCheckMessageDtoListMemberInfo(List<jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CheckMessageDtoResponse> checkMessageDtoResponseList) {
        if (CollectionUtils.isEmpty(checkMessageDtoResponseList)) {
            return new ArrayList<>();
        }
        List<CheckMessageDto> checkMessageDtoList = new ArrayList<>();
        for (jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CheckMessageDtoResponse checkMessageDtoResponse : checkMessageDtoResponseList) {
            CheckMessageDto checkMessageDto = new CheckMessageDto();
            checkMessageDto.setMessageId(checkMessageDtoResponse.getMessageId());
            checkMessageDto.setMessage(checkMessageDtoResponse.getMessage());
            if (ObjectUtils.isNotEmpty(checkMessageDtoResponse.getArgs())) {
                checkMessageDto.setArgs(checkMessageDtoResponse.getArgs().toArray());
            }
            checkMessageDto.setError(checkMessageDtoResponse.getError() != null && checkMessageDtoResponse.getError());
            checkMessageDto.setOrderConsecutiveNo(checkMessageDtoResponse.getOrderConsecutiveNo());

            checkMessageDtoList.add(checkMessageDto);
        }

        return checkMessageDtoList;
    }

    // 2023-renew No14 from here

    /**
     * 前回支払方法取得リクエストに変換
     * @param customerNo 顧客番号
     * @param paymentModel お支払い方法選択画面Model
     * @return 前回支払方法取得リクエスト
     */
    public OrderBeforePaymentRequest toOrderBeforePaymentRequest(Integer customerNo, PaymentModel paymentModel) {

        OrderBeforePaymentRequest orderBeforePaymentRequest = new OrderBeforePaymentRequest();

        // 顧客番号
        orderBeforePaymentRequest.setCustomerNo(customerNo);

        // 決済タイプリスト（画面選択可能な決済のみを設定）
        orderBeforePaymentRequest.setSettlementMethodTypeList(new ArrayList<>());
        for (PaymentModelItem paymentModelItem : paymentModel.getPaymentModelItems()) {
            if (paymentModelItem.isUsabilityFlag()) {
                orderBeforePaymentRequest.getSettlementMethodTypeList()
                                         .add(paymentModelItem.getSettlementMethodType().getValue());
            }
        }

        return orderBeforePaymentRequest;
    }

    /**
     * WEB-API連携出荷予定日取得リクエストに変換
     * @param requestDto
     * @return WEB-API連携出荷予定日取得リクエスト
     */
    public WebApiGetShipmentDateRequest toWebApiGetShipmentDateRequest(WebApiGetShipmentDateRequestDto requestDto) {

        if (ObjectUtils.isEmpty(requestDto)) {
            return null;
        }

        WebApiGetShipmentDateRequest webApiGetShipmentDateRequest = new WebApiGetShipmentDateRequest();
        webApiGetShipmentDateRequest.setOrderCustomerNo(requestDto.getOrderCustomerNo());
        webApiGetShipmentDateRequest.setDeliveryCustomerNo(requestDto.getDeliveryCustomerNo());
        webApiGetShipmentDateRequest.setDeliveryZipcode(requestDto.getDeliveryZipcode());
        webApiGetShipmentDateRequest.setDeliveryCompanyCode(requestDto.getDeliveryCompanyCode());
        webApiGetShipmentDateRequest.setInfo(
                        toWebApiGetShipmentDateRequestDetailDtoRequestDtoList(requestDto.getInfo()));

        return webApiGetShipmentDateRequest;
    }

    /**
     * WEB-API連携出荷予定日取得リクエストに変換
     * @param requestDtoList
     * @return WEB-API連携出荷予定日取得リクエスト
     */
    public List<WebApiGetShipmentDateRequestDetailDtoRequest> toWebApiGetShipmentDateRequestDetailDtoRequestDtoList(List<WebApiGetShipmentDateRequestDetailDto> requestDtoList) {

        if (CollectionUtils.isEmpty(requestDtoList)) {
            return new ArrayList<>();
        }

        List<WebApiGetShipmentDateRequestDetailDtoRequest> infoList = new ArrayList<>();

        requestDtoList.forEach(dto -> {
            WebApiGetShipmentDateRequestDetailDtoRequest req = new WebApiGetShipmentDateRequestDetailDtoRequest();
            req.setGoodsCode(dto.getGoodsCode());
            req.setDeliveryDesignatedDay(dto.getDeliveryDesignatedDay());
            req.setDeliveryDesignatedTimeCode(dto.getDeliveryDesignatedTimeCode());
            infoList.add(req);
        });

        return infoList;
    }

    /**
     * WEB-API連携レスポンスDTO
     * 出荷予定日取得
     * @param response
     * @return 出荷予定日取得
     */
    public WebApiGetShipmentDateResponseDto toWebApiGetShipmentDateResponseDetailDto(WebApiGetShipmentDateResponse response) {

        if (ObjectUtils.isEmpty(response)) {
            return null;
        }

        WebApiGetShipmentDateResponseDto responseDto = new WebApiGetShipmentDateResponseDto();

        responseDto.setResult(toAbstractWebApiResponseResultDto(response.getResult()));
        responseDto.setInfo(toWebApiGetShipmentDateResponseDetailDtoList(response.getInfo()));

        return responseDto;
    }

    /**
     * 出荷予定日取得(詳細情報)リストに変換
     *
     * @param webApiGetShipmentDateResponseDetailDtoResponseList 出荷予定日取得 詳細情報
     * @return 出荷予定日取得(詳細情報)リスト
     */
    public List<WebApiGetShipmentDateResponseDetailDto> toWebApiGetShipmentDateResponseDetailDtoList(List<WebApiGetShipmentDateResponseDetailDtoResponse> webApiGetShipmentDateResponseDetailDtoResponseList) {

        if (CollectionUtil.isEmpty(webApiGetShipmentDateResponseDetailDtoResponseList)) {
            return new ArrayList<>();
        }

        List<WebApiGetShipmentDateResponseDetailDto> infoList = new ArrayList<>();

        webApiGetShipmentDateResponseDetailDtoResponseList.forEach(dto -> {
            WebApiGetShipmentDateResponseDetailDto res = new WebApiGetShipmentDateResponseDetailDto();
            res.setGoodsCode(dto.getGoodsCode());
            res.setDeliveryDesignatedDay(conversionUtility.toTimeStamp(dto.getDeliveryDesignatedDay()));
            res.setShipmentDate(conversionUtility.toTimeStamp(dto.getShipmentDate()));
            infoList.add(res);
        });

        return infoList;
    }

    // 2023-renew No14 to here

}
