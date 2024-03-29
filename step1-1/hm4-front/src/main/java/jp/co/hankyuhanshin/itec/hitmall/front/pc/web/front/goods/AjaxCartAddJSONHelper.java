/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods;

import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsAddRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CheckMessageDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsGroupImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetConsumptionTaxRateResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetDiscountsResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
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
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.ajax.CartResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.ajax.CartResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetConsumptionTaxRateResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDiscountsResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * カート追加（Ajax）Helperクラス
 *
 * @author kaneda
 */
@Component
public class AjaxCartAddJSONHelper {

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
    public AjaxCartAddJSONHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * カート追加結果レスポンス作成
     *
     * @param validatorErrorList バリデータエラーメッセージ
     * @param errorList          エラーメッセージ
     * @param cartDto            最新のカート情報
     * @param okSession          セッション情報がただしいか true:正しい / false:不正
     * @return カート追加結果
     */
    public CartResponseDto toCartIFResponse(List<CheckMessageDto> validatorErrorList,
                                            List<CheckMessageDto> errorList,
                                            CartDto cartDto,
                                            boolean okSession) {

        // カート追加結果の格納
        CartResultDto cartResultDto = ApplicationContextUtility.getBean(CartResultDto.class);
        cartResultDto.setOkSession(okSession);
        if (cartDto != null) {
            for (CartGoodsDto cartGoodsDto : cartDto.getCartGoodsDtoList()) {
                cartGoodsDto.getGoodsDetailsDto()
                            .setGoodsGroupName(StringEscapeUtils.escapeHtml4(
                                            cartGoodsDto.getGoodsDetailsDto().getGoodsGroupName()));
            }
        }
        if (okSession && errorList.isEmpty() && validatorErrorList.isEmpty()) {
            // カート投入成功
            cartResultDto.setResult(true);
            cartResultDto.setValidatorErrorList(null);
            cartResultDto.setErrorList(null);
            cartResultDto.setCartInfo(cartDto);
        } else {
            // カート投入失敗
            cartResultDto.setResult(false);
            cartResultDto.setValidatorErrorList(validatorErrorList);
            cartResultDto.setErrorList(errorList);
            cartResultDto.setCartInfo(cartDto);
        }

        CartResponseDto cartResponseDto = ApplicationContextUtility.getBean(CartResponseDto.class);
        cartResponseDto.setCart(cartResultDto);

        return cartResponseDto;

    }

    /**
     * カート商品追加リクエストに変換
     *
     * @param goodsCode     商品コード
     * @param count         商品数量
     * @param accessUid     端末識別情報
     * @param siteType      サイト区分
     * @param memberInfoEntity 会員エンティティ
     * @return カート商品追加リクエスト
     */
    public CartGoodsAddRequest toCartGoodsAddRequest(String goodsCode,
                                                     BigDecimal count,
                                                     String accessUid,
                                                     String siteType,
                                                     MemberInfoEntity memberInfoEntity) {

        CartGoodsAddRequest cartGoodsAddRequest = new CartGoodsAddRequest();

        cartGoodsAddRequest.setGoodsCode(goodsCode);
        cartGoodsAddRequest.setCount(count);
        cartGoodsAddRequest.setAccessUid(accessUid);
        cartGoodsAddRequest.setSiteType(siteType);
        // 2023-renew No14 from here
        cartGoodsAddRequest.setMemberInfoSeq(memberInfoEntity.getMemberInfoSeq());
        cartGoodsAddRequest.setCustomerNo(memberInfoEntity.getCustomerNo());
        // 2023-renew No14 to here

        return cartGoodsAddRequest;
    }

    /**
     * チェックメッセージDtoリストに変換
     *
     * @param checkMessageDtoResponseList チェックメッセージDtoリストクラスレスポンス
     * @return チェックメッセージDtoリスト
     */
    public List<CheckMessageDto> toCheckMessageDtoList(List<CheckMessageDtoResponse> checkMessageDtoResponseList) {

        if (CollectionUtil.isEmpty(checkMessageDtoResponseList)) {
            return new ArrayList<>();
        }

        List<CheckMessageDto> checkMessageDtoList = new ArrayList<>();

        checkMessageDtoResponseList.forEach(checkMessageDtoResponse -> {

            CheckMessageDto checkMessageDto = new CheckMessageDto();

            checkMessageDto.setMessageId(checkMessageDtoResponse.getMessageId());
            if (CollectionUtil.isNotEmpty(checkMessageDtoResponse.getArgs())) {
                checkMessageDto.setArgs(checkMessageDtoResponse.getArgs().toArray());
            }
            checkMessageDto.setMessage(checkMessageDtoResponse.getMessage());
            checkMessageDto.setOrderConsecutiveNo(checkMessageDtoResponse.getOrderConsecutiveNo());
            checkMessageDto.setError(Boolean.TRUE.equals(checkMessageDtoResponse.getError()));

            checkMessageDtoList.add(checkMessageDto);
        });

        return checkMessageDtoList;
    }

    /**
     * カート情報取得リクエストに変換
     *
     * @param accessUid     端末識別情報
     * @param memberInfoSeq 会員SEQ
     * @param siteType      サイト区分
     * @param orderField    ソート項目
     * @param orderAsc      ソート条件
     * @return カート情報取得リクエスト
     */
    public CartGoodsGetRequest toCartGoodsGetRequest(String accessUid,
                                                     Integer memberInfoSeq,
                                                     String siteType,
                                                     String orderField,
                                                     Boolean orderAsc) {

        CartGoodsGetRequest cartGoodsGetRequest = new CartGoodsGetRequest();

        cartGoodsGetRequest.setAccessUid(accessUid);
        cartGoodsGetRequest.setMemberInfoSeq(memberInfoSeq);
        cartGoodsGetRequest.setSiteType(siteType);
        cartGoodsGetRequest.setOrderField(orderField);
        cartGoodsGetRequest.setOrderAsc(orderAsc);
        // 2023-renew No14 from here
        cartGoodsGetRequest.setReserveFlag(HTypeReserveDeliveryFlag.OFF.getValue());
        // 2023-renew No14 to here

        return cartGoodsGetRequest;
    }

    /**
     * カートDtoに変換
     *
     * @param cartDtoResponse カートDtoクラスレスポンス
     * @return カートDto
     */
    public CartDto toCartDto(CartDtoResponse cartDtoResponse) {

        if (ObjectUtils.isEmpty(cartDtoResponse)) {
            return null;
        }

        CartDto cartDto = new CartDto();

        cartDto.setGoodsTotalCount(cartDtoResponse.getGoodsTotalCount());
        cartDto.setGoodsTotalPrice(cartDtoResponse.getGoodsTotalPrice());
        cartDto.setGoodsTotalPriceInTax(cartDtoResponse.getGoodsTotalPriceInTax());
        cartDto.setBeforeTransferEmotionGoodsCodeMap(cartDtoResponse.getBeforeTransferEmotionGoodsCodeMap());
        cartDto.setTotalPriceTax(cartDtoResponse.getTotalPriceTax());
        cartDto.setSettlementMethodType(EnumTypeUtil.getEnumFromValue(HTypeSettlementMethodType.class,
                                                                      cartDtoResponse.getSettlementMethodType()
                                                                     ));

        cartDto.setCartGoodsDtoList(toCartGoodsDtoList(cartDtoResponse.getCartGoodsDtoListResponse()));
        cartDto.setDiscountsResponseDetailMap(
                        toDiscountsResponseDetailMap(cartDtoResponse.getDiscountsResponseDetailMap()));
        cartDto.setConsumptionTaxRateMap(toConsumptionTaxRateMap(cartDtoResponse.getConsumptionTaxRateMap()));

        return cartDto;
    }

    /**
     * カート商品Dtoリストに変換
     *
     * @param cartGoodsDtoResponseList カート商品Dtoリストレスポンス
     * @return カート商品Dtoリスト
     */
    private List<CartGoodsDto> toCartGoodsDtoList(List<CartGoodsDtoResponse> cartGoodsDtoResponseList) {

        if (CollectionUtil.isEmpty(cartGoodsDtoResponseList)) {
            return new ArrayList<>();
        }

        List<CartGoodsDto> cartGoodsDtoList = new ArrayList<>();

        cartGoodsDtoResponseList.forEach(cartGoodsDtoResponse -> {
            CartGoodsDto cartGoodsDto = new CartGoodsDto();

            cartGoodsDto.setCartGoodsEntity(toCartGoodsEntity(cartGoodsDtoResponse.getCartGoodsEntityResponse()));
            cartGoodsDto.setGoodsDetailsDto(toGoodsDetailsDto(cartGoodsDtoResponse.getGoodsDetailsDtoResponse()));
            cartGoodsDto.setGoodsPriceSubtotal(cartGoodsDtoResponse.getGoodsPriceSubtotal());
            cartGoodsDto.setGoodsPriceInTaxSubtotal(cartGoodsDtoResponse.getGoodsPriceInTaxSubtotal());

            cartGoodsDtoList.add(cartGoodsDto);
        });

        return cartGoodsDtoList;
    }

    /**
     * カート商品に変換
     *
     * @param cartGoodsEntityResponse カート商品エンティティレスポンス
     * @return カート商品
     */
    private CartGoodsEntity toCartGoodsEntity(CartGoodsEntityResponse cartGoodsEntityResponse) {

        if (ObjectUtils.isEmpty(cartGoodsEntityResponse)) {
            return null;
        }

        CartGoodsEntity cartGoodsEntity = new CartGoodsEntity();

        cartGoodsEntity.setCartSeq(cartGoodsEntityResponse.getCartSeq());
        cartGoodsEntity.setAccessUid(cartGoodsEntityResponse.getAccessUid());
        cartGoodsEntity.setMemberInfoSeq(cartGoodsEntityResponse.getMemberInfoSeq());
        cartGoodsEntity.setGoodsSeq(cartGoodsEntityResponse.getGoodsSeq());
        cartGoodsEntity.setCartGoodsCount(cartGoodsEntityResponse.getCartGoodsCount());
        cartGoodsEntity.setRegistTime(conversionUtility.toTimeStamp(cartGoodsEntityResponse.getRegistTime()));
        cartGoodsEntity.setUpdateTime(conversionUtility.toTimeStamp(cartGoodsEntityResponse.getUpdateTime()));
        cartGoodsEntity.setShopSeq(1001);
        // 2023-renew No14 from here
        cartGoodsEntity.setReserveFlag(EnumTypeUtil.getEnumFromValue(HTypeReserveDeliveryFlag.class,
                                                                     cartGoodsEntityResponse.getReserveFlag()
                                                                    ));
        cartGoodsEntity.setReserveDeliveryDate(
                        conversionUtility.toTimeStamp(cartGoodsEntityResponse.getReserveDeliveryDate()));
        // 2023-renew No14 to here
        return cartGoodsEntity;
    }

    /**
     * 商品詳細Dtoに変換
     *
     * @param goodsDetailsDtoResponse 商品詳細Dtoクラスレスポンス
     * @return 商品詳細Dto
     */
    private GoodsDetailsDto toGoodsDetailsDto(GoodsDetailsDtoResponse goodsDetailsDtoResponse) {

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
                        toGoodsGroupImageEntityList(goodsDetailsDtoResponse.getGoodsGroupImageEntityList()));
        goodsDetailsDto.setUnitImage(toGoodsImageEntity(goodsDetailsDtoResponse.getUnitImage()));

        return goodsDetailsDto;
    }

    /**
     * 商品画像に変換
     *
     * @param goodsImageEntityResponse 商品グループ画像レスポンス
     * @return 商品画像
     */
    public GoodsImageEntity toGoodsImageEntity(GoodsImageEntityResponse goodsImageEntityResponse) {
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
     * 商品グループ画像リストに変換
     *
     * @param goodsGroupImageEntityResponseList 商品グループ画像リストレスポンス
     * @return 商品グループ画像リスト
     */
    private List<GoodsGroupImageEntity> toGoodsGroupImageEntityList(List<GoodsGroupImageEntityResponse> goodsGroupImageEntityResponseList) {

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
     * 割引適用結果MAPに変換
     *
     * @param discountsResponseDetailMapResponse WEB-API連携取得結果DTOMapクラスレスポンス
     * @return 割引適用結果MAP
     */
    private Map<String, WebApiGetDiscountsResponseDetailDto> toDiscountsResponseDetailMap(Map<String, WebApiGetDiscountsResponseDetailDtoResponse> discountsResponseDetailMapResponse) {

        if (MapUtils.isEmpty(discountsResponseDetailMapResponse)) {
            return new HashMap<>();
        }

        Map<String, WebApiGetDiscountsResponseDetailDto> discountsResponseDetailMap = new HashMap<>();

        for (Map.Entry<String, WebApiGetDiscountsResponseDetailDtoResponse> entry : discountsResponseDetailMapResponse.entrySet()) {

            WebApiGetDiscountsResponseDetailDtoResponse webApiGetDiscountsResponseDetailDtoResponse = entry.getValue();
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

            discountsResponseDetailMap.put(entry.getKey(), webApiGetDiscountsResponseDetailDto);
        }

        return discountsResponseDetailMap;
    }

    /**
     * 消費税率MAPに変換
     *
     * @param consumptionTaxRateMapResponse WEB-API連携取得結果DTOMapレスポンス
     * @return 消費税率MAP
     */
    private Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> toConsumptionTaxRateMap(Map<String, WebApiGetConsumptionTaxRateResponseDetailDtoResponse> consumptionTaxRateMapResponse) {

        if (MapUtils.isEmpty(consumptionTaxRateMapResponse)) {
            return new HashMap<>();
        }

        Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> consumptionTaxRateMap = new HashMap<>();

        for (Map.Entry<String, WebApiGetConsumptionTaxRateResponseDetailDtoResponse> entry : consumptionTaxRateMapResponse.entrySet()) {

            WebApiGetConsumptionTaxRateResponseDetailDtoResponse webApiGetConsumptionTaxRateResponseDetailDtoResponse =
                            entry.getValue();
            WebApiGetConsumptionTaxRateResponseDetailDto webApiGetConsumptionTaxRateResponseDetailDto =
                            new WebApiGetConsumptionTaxRateResponseDetailDto();

            webApiGetConsumptionTaxRateResponseDetailDto.setGoodsCode(
                            webApiGetConsumptionTaxRateResponseDetailDtoResponse.getGoodsCode());
            webApiGetConsumptionTaxRateResponseDetailDto.setTaxRate(
                            webApiGetConsumptionTaxRateResponseDetailDtoResponse.getTaxRate());

            consumptionTaxRateMap.put(entry.getKey(), webApiGetConsumptionTaxRateResponseDetailDto);
        }

        return consumptionTaxRateMap;
    }

}
