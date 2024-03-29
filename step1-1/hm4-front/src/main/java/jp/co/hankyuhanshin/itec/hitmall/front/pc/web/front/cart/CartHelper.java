/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart;

import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartDtoCorrectionScreenUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsAddRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsChangeRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsDeleteRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsForTakeOverDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartScreenRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CheckMessageDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetConsumptionTaxRateResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetDiscountsResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetQuantityDiscountResultResponseDetailDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetQuantityDiscountResultResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetReserveResponseDetailDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetReserveResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetSaleCheckDetailResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryGoodsEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupDisplayEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.StockDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.StockStatusDisplayEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CartDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CartGoodsDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CartGoodsEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteForCartCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteGoodsStockStatusGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsDetailsDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsGroupImageEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsGroupImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsImageEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.PageInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.WebApiGetConsumptionTaxRateResponseDetailDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.WebApiGetDiscountsResponseDetailDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.AccessRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.FreeAreaEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.AbstractWebApiResponseResultDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAccessType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeConfDocumentType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCoolSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDentalMonopolySalesFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDiscountsType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDisplayStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeFreeAreaOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsClassType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGroupPriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGroupSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeLandSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOutletIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleControlType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartGoodsForTakeOverDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.stock.StockDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.favorite.FavoriteDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.favorite.FavoriteSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.AbstractWebApiResponseResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetSaleCheckResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetConsumptionTaxRateResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDiscountsResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetQuantityDiscountResultResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.category.CategoryGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.stock.StockStatusDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.favorite.FavoriteEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * PDR#013 09_データ連携（受注データ）
 * PDR#007 04_数量割引サービス
 * <p>
 * カートHelper
 *
 * @author kaneda
 */
@Component
public class CartHelper {

    /**
     * GoodsUtility
     */
    private final GoodsUtility goodsUtility;

    /**
     * ConversionUtility
     */
    private final ConversionUtility conversionUtility;

    /**
     * 計算ユーティリティ
     */
    private final CalculatePriceUtility calculatePriceUtility;

    // 2023-renew No14 from here
    /**
     * CommonInfoUtility
     */
    private final CommonInfoUtility commonInfoUtility;
    // 2023-renew No14 to here

    /**
     * コンストラクタ
     */
    @Autowired
    public CartHelper(GoodsUtility goodsUtility,
                      ConversionUtility conversionUtility,
                      CalculatePriceUtility calculatePriceUtility,
                      CommonInfoUtility commonInfoUtility) {
        this.goodsUtility = goodsUtility;
        this.conversionUtility = conversionUtility;
        this.calculatePriceUtility = calculatePriceUtility;
        // 2023-renew No14 from here
        this.commonInfoUtility = commonInfoUtility;
        // 2023-renew No14 to here
    }

    /**
     * 画面表示・再表示
     *
     * <pre>
     * 陸送、クール便商品の場合 備考欄にその旨のメッセージを表示する処理を追加
     * 商品単価、消費税に関する処理を追加
     * </pre>
     *
     * @param cartDto   カート情報DTO
     * @param cartModel カートModel
     */
    public void toPageForLoad(CartDto cartDto, CartModel cartModel) {

        List<CartModelItem> cartGoodsItems = new ArrayList<>();
        // 2023-renew No14 from here
        Map<String, List<CartModelItem>> cartGoodsReserveItemMap = new LinkedHashMap<>(); // 順序保持（LinkedHashMap）
        // 2023-renew No14 to here
        // 2023-renew No3-taglog from here
        StringBuilder sb = new StringBuilder();
        // 2023-renew No3-taglog to here

        int cartNo = 1;
        for (CartGoodsDto cartGoodsDto : cartDto.getCartGoodsDtoList()) {

            CartModelItem cartModelItem = ApplicationContextUtility.getBean(CartModelItem.class);

            CartGoodsEntity cartGoodsEntity = cartGoodsDto.getCartGoodsEntity();
            GoodsDetailsDto goodsDetailsDto = cartGoodsDto.getGoodsDetailsDto();
            BigDecimal goodsCount = conversionUtility.toBigDecimal(cartGoodsEntity.getCartGoodsCount());
            // PDR Migrate Customization from here
            cartModelItem.setGcd(
                            goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(goodsDetailsDto.getGoodsCode()));
            cartModelItem.setGoodsGroupName(
                            goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(goodsDetailsDto));
            // PDR Migrate Customization to here
            cartModelItem.setGoodsPrice(goodsDetailsDto.getGoodsPrice());
            cartModelItem.setGoodsPriceInTax(calculatePriceUtility.getTaxIncludedPrice(goodsDetailsDto.getGoodsPrice(),
                                                                                       goodsDetailsDto.getTaxRate()
                                                                                      ));
            cartModelItem.setPreDiscountPrice(goodsDetailsDto.getPreDiscountPrice());
            cartModelItem.setPreDisCountPriceInTax(goodsDetailsDto.getPreDisCountPriceInTax());
            cartModelItem.setGoodsPreDiscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());
            cartModelItem.setGoodsSeq(goodsDetailsDto.getGoodsSeq());
            cartModelItem.setDeliveryType(goodsDetailsDto.getDeliveryType());
            cartModelItem.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType());
            cartModelItem.setTaxRate(goodsDetailsDto.getTaxRate());
            cartModelItem.setAlcoholFlag(goodsDetailsDto.getAlcoholFlag());
            cartModelItem.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
            cartModelItem.setUnitValue1(goodsDetailsDto.getUnitValue1());
            cartModelItem.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
            cartModelItem.setUnitValue2(goodsDetailsDto.getUnitValue2());
            cartModelItem.setGoodsOpenStatus(goodsDetailsDto.getGoodsOpenStatusPC());
            cartModelItem.setOpenStartTime(goodsDetailsDto.getOpenStartTimePC());
            cartModelItem.setOpenEndTime(goodsDetailsDto.getOpenEndTimePC());

            // 商品画像リストを取り出す。
            List<String> goodsImageList = new ArrayList<>();
            if (goodsDetailsDto.getUnitImage() != null) {
                String cartItemImageFileName = goodsDetailsDto.getUnitImage().getImageFileName();
                goodsImageList.add(goodsUtility.getGoodsImagePath(cartItemImageFileName));
            } else {
                if (goodsDetailsDto.getGoodsGroupImageEntityList() != null) {
                    for (GoodsGroupImageEntity goodsGroupImageEntity : goodsDetailsDto.getGoodsGroupImageEntityList()) {
                        goodsImageList.add(goodsUtility.getGoodsImagePath(goodsGroupImageEntity.getImageFileName()));
                    }
                }
            }

            cartModelItem.setGoodsImageItems(goodsImageList);

            cartModelItem.setGcnt(cartGoodsEntity.getCartGoodsCount().toString());
            cartModelItem.setCartSeq(cartGoodsEntity.getCartSeq());
            cartModelItem.setGoodsTotalPrice(cartModelItem.getGoodsPrice().multiply(goodsCount));
            cartModelItem.setGoodsTotalPriceInTax(cartModelItem.getGoodsPriceInTax().multiply(goodsCount));

            // 個別配送商品設定
            if (HTypeIndividualDeliveryType.ON.equals(goodsDetailsDto.getIndividualDeliveryType())) {
                cartModelItem.setIndividualDeliveryType(goodsDetailsDto.getIndividualDeliveryType().getLabel());
            }

            // アイコン設定
            cartModelItem.setGoodsIconItems(createGoodsIconList(cartGoodsDto.getGoodsInformationIconDetailsDtoList()));

            // 新着画像の表示期間を取得
            Timestamp whatsnewDate =
                            goodsUtility.getRealWhatsNewDate(cartGoodsDto.getGoodsDetailsDto().getWhatsnewDate());
            cartModelItem.setWhatsnewDate(whatsnewDate);

            // PDR Migrate Customization from here
            // 陸送便のチェック
            if (HTypeLandSendFlag.ON.equals(goodsDetailsDto.getLandSendFlag())) {
                cartModelItem.setLandSendFlagDisp(HTypeLandSendFlag.ON.getLabel());
            }

            // クール便のチェック
            if (HTypeCoolSendFlag.ON.equals(goodsDetailsDto.getCoolSendFlag())) {
                // クール便適用期間内かチェック
                if (goodsUtility.checkCoolSend(goodsDetailsDto.getCoolSendFrom(), goodsDetailsDto.getCoolSendTo())) {
                    cartModelItem.setCoolSendFlagDisp(HTypeCoolSendFlag.ON.getLabel());
                }
            }

            // 2023-renew No14 from here
            // 数量割引適用結果情報を取得
            WebApiGetQuantityDiscountResultResponseDetailDto discountsResponseDetailDto =
                            cartDto.getQuantityDiscountsResponseDetailMap().get(goodsDetailsDto.getGoodsCode());
            // 2023-renew No14 to here

            // セールアイコンフラグ
            cartModelItem.setSaleIconFlag(
                            !HTypeDiscountsType.SALEOFF.getValue().equals(discountsResponseDetailDto.getSaleType()));

            // 振替前心意気商品コード 設定
            if (HTypeDiscountsType.SALEON_EMOTION_PRICE.getValue().equals(discountsResponseDetailDto.getSaleType())) {
                cartModelItem.setBeforeTransferEmotionGoodsCode(
                                cartDto.getBeforeTransferEmotionGoodsCodeMap().get(goodsDetailsDto.getGoodsCode()));
            }

            // 心意気商品フラグ
            cartModelItem.setEmotionPriceGoods(HTypeDiscountsType.SALEON_EMOTION_PRICE.getValue()
                                                                                      .equals(discountsResponseDetailDto.getSaleType()));

            // 2023-renew No14 from here
            // 数量割引フラグ
            cartModelItem.setQuantityDiscount(
                            StringUtil.isNotEmpty(discountsResponseDetailDto.getSaleGroupCode()) && !"0".equals(
                                            discountsResponseDetailDto.getSaleGroupCode()));
            // 2023-renew No14 to here

            // 商品詳細ページ遷移先用の商品コード
            if (!StringUtil.isEmpty(cartModelItem.getBeforeTransferEmotionGoodsCode())) {
                cartModelItem.setGcdForHref(cartModelItem.getBeforeTransferEmotionGoodsCode());
            } else {
                cartModelItem.setGcdForHref(cartModelItem.getGcd());
            }
            // PDR Migrate Customization to here

            // カート行番号設定
            cartModelItem.setCartNo(cartNo++);

            // 2023-renew No14 from here
            // 今すぐお届け商品の場合
            if (HTypeReserveDeliveryFlag.OFF.equals(cartGoodsEntity.getReserveFlag())) {
                // セールde予約可能かどうか
                cartModelItem.setReserveFlag(HTypeReserveDeliveryFlag.ON.getValue()
                                                                        .equals(cartDto.getReserveMap()
                                                                                       .get(goodsDetailsDto.getGoodsCode())
                                                                                       .getReserveFlag()));
                // カート商品リストに追加
                cartGoodsItems.add(cartModelItem);
            }
            // セールde予約商品の場合
            else {
                // お届け希望日（セールde予約用）
                cartModelItem.setReserveDeliveryDate(cartGoodsEntity.getReserveDeliveryDate());
                // カート商品マップに追加
                cartGoodsReserveItemMap.computeIfAbsent(goodsDetailsDto.getGoodsCode(), k -> new ArrayList<>())
                                       .add(cartModelItem);
            }
            // 2023-renew No14 to here

            // 2023-renew No3-taglog from here
            if (cartModel.getCatalogCartInMap().containsKey(goodsDetailsDto.getGoodsCode())) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(goodsDetailsDto.getGoodsGroupCode())
                  .append(":")
                  .append(cartModel.getCatalogCartInMap().get(goodsDetailsDto.getGoodsCode()))
                  .append(":")
                  .append(goodsDetailsDto.getGoodsPrice());
            }
            // 2023-renew No3-taglog to here
        }

        // カート一覧情報（今すぐお届け）
        cartModel.setCartGoodsItems(cartGoodsItems);

        // 2023-renew No14 from here
        // カート一覧情報（今すぐお届け）※お届け希望日でソートしてから設定
        cartGoodsReserveItemMap.forEach((k, v) -> cartGoodsReserveItemMap.put(k, cartGoodsReserveItemMap.get(k)
                                                                                                        .stream()
                                                                                                        .sorted(Comparator.comparing(
                                                                                                                        CartModelItem::getReserveDeliveryDate))
                                                                                                        .collect(Collectors.toList())));
        cartModel.setCartGoodsReserveItemMap(cartGoodsReserveItemMap);
        // 2023-renew No14 to here

        cartModel.setGoodsCountTotal(cartDto.getGoodsTotalCount());
        cartModel.setPriceTotal(cartDto.getGoodsTotalPrice());
        cartModel.setPriceTotalInTax(cartDto.getGoodsTotalPriceInTax());

        // PDR Migrate Customization from here
        // 商品合計金額(税抜)
        cartModel.setGoodsTotalPriceNoTax(cartDto.getGoodsTotalPrice());
        // 商品合計消費税額
        cartModel.setGoodsTotalTax(cartDto.getTotalPriceTax());
        // ecコンシェル連携用 カート合計金額
        if (cartModel.getPriceTotal() != null) {
            cartModel.setEccCartTotalPrice(cartModel.getPriceTotal().intValue());
        }
        // PDR Migrate Customization to here

        // 2023-renew No3-taglog from here
        cartModel.setCatalogCartInString(sb.toString());
        // 2023-renew No3-taglog to here

        // 2023-renew No14 from here
        // 今すぐお届け／セールde予約内の同一商品チェック用リスト（確認ダイアログ用）を作成
        setDuplicationGoodsItems(cartModel);
        // 2023-renew No14 to here
    }

    /**
     * 画面表示・再表示(お気に入り情報)
     *
     * @param favoriteDtoList お気に入りDTOリスト
     * @param cartModel       カートModel
     */
    public void toPageForLoadFavorite(List<FavoriteDto> favoriteDtoList, CartModel cartModel) {

        List<CartModelItem> goodsItems = new ArrayList<>();

        for (FavoriteDto favoriteDto : favoriteDtoList) {
            // お気に入り商品情報取得
            GoodsDetailsDto goodsDetailsDto = favoriteDto.getGoodsDetailsDto();

            // Modelアイテムクラスにセット
            CartModelItem cartModelItem = ApplicationContextUtility.getBean(CartModelItem.class);
            if (goodsDetailsDto != null) {
                cartModelItem.setGoodsGroupSeq(goodsDetailsDto.getGoodsGroupSeq());
                cartModelItem.setGgcd(goodsDetailsDto.getGoodsGroupCode());
                cartModelItem.setGoodsSeq(goodsDetailsDto.getGoodsSeq());
                cartModelItem.setGcd(goodsDetailsDto.getGoodsCode());
                cartModelItem.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
                cartModelItem.setUnitValue1(goodsDetailsDto.getUnitValue1());
                cartModelItem.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
                cartModelItem.setUnitValue2(goodsDetailsDto.getUnitValue2());
                cartModelItem.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
                cartModelItem.setDeliveryType(goodsDetailsDto.getDeliveryType());
                cartModelItem.setGcnt("1");// カートに入れるボタンのために必要

                // PDR Migrate Customization from here
                // 価格
                cartModelItem.setGoodsPrice(goodsDetailsDto.getGoodsPrice());
                // セール価格
                cartModelItem.setPreDiscountPrice(goodsDetailsDto.getPreDiscountPrice());
                // 価格帯フラグ
                cartModelItem.setGoodsDisplayPriceRange(
                                HTypePriceMarkDispFlag.ON.equals(goodsDetailsDto.getPriceMarkDispFlag()));
                // セール価格帯フラグ
                cartModelItem.setGoodsDisplayPreDiscountPriceRange(
                                HTypeSalePriceMarkDispFlag.ON.equals(goodsDetailsDto.getSalePriceMarkDispFlag()));
                // PDR Migrate Customization to here

                // 新着画像の表示期間を取得
                Timestamp whatsnewDate = goodsUtility.getRealWhatsNewDate(goodsDetailsDto.getWhatsnewDate());
                cartModelItem.setWhatsnewDate(whatsnewDate);
                cartModelItem.setGoodsOpenStatus(goodsDetailsDto.getGoodsOpenStatusPC());
                cartModelItem.setOpenStartTime(goodsDetailsDto.getOpenStartTimePC());
                cartModelItem.setOpenEndTime(goodsDetailsDto.getOpenEndTimePC());
                cartModelItem.setSaleStatus(goodsDetailsDto.getSaleStatusPC());
                cartModelItem.setSaleStartTime(goodsDetailsDto.getSaleStartTimePC());
                cartModelItem.setSaleEndTime(goodsDetailsDto.getSaleEndTimePC());

                // 商品画像リストを取り出す。
                List<String> goodsImageList = new ArrayList<>();
                if (goodsDetailsDto.getUnitImage() != null) {
                    String cartItemImageFileName = goodsDetailsDto.getUnitImage().getImageFileName();
                    goodsImageList.add(goodsUtility.getGoodsImagePath(cartItemImageFileName));
                } else {
                    if (goodsDetailsDto.getGoodsGroupImageEntityList() != null) {
                        for (GoodsGroupImageEntity goodsGroupImageEntity : goodsDetailsDto.getGoodsGroupImageEntityList()) {
                            goodsImageList.add(
                                            goodsUtility.getGoodsImagePath(goodsGroupImageEntity.getImageFileName()));
                        }
                    }
                }

                cartModelItem.setGoodsImageItems(goodsImageList);

                // PDR Migrate Customization from here
                // SALEアイコンフラグ
                cartModelItem.setSaleIconFlag(HTypeSaleIconFlag.ON.equals(goodsDetailsDto.getSaleIconFlag()));
                // 取りおきアイコンフラグ
                cartModelItem.setReserveIconFlag(HTypeReserveIconFlag.ON.equals(goodsDetailsDto.getReserveIconFlag()));
                // NEWアイコンフラグ
                cartModelItem.setNewIconFlag(HTypeNewIconFlag.ON.equals(goodsDetailsDto.getNewIconFlag()));
                // 2023-renew No92 from here
                // アウトレットアイコンフラグ
                cartModelItem.setOutletIconFlag(HTypeOutletIconFlag.ON.equals(goodsDetailsDto.getOutletIconFlag()));
                // 2023-renew No92 to here
                // セール価格整合性フラグ
                cartModelItem.setSalePriceIntegrityFlag(
                                HTypeSalePriceIntegrityFlag.MATCH.equals(goodsDetailsDto.getSalePriceIntegrityFlag()));
                // PDR Migrate Customization to here

                cartModelItem.setGoodsPreDiscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());

                // 2023-renew AddNo5 from here
                cartModelItem.setGoodsPriceHigh(goodsDetailsDto.getGoodsPriceInTaxHight());
                cartModelItem.setGoodsPriceLow(goodsDetailsDto.getGoodsPriceInTaxLow());
                cartModelItem.setGoodsSalePriceHigh(goodsDetailsDto.getPreDiscountPriceHight());
                cartModelItem.setGoodsSalePriceLow(goodsDetailsDto.getPreDiscountPriceLow());
                // 2023-renew AddNo5 to here
            }

            // 在庫状況表示
            cartModelItem.setListStockStatusPc(favoriteDto.getStockStatus());

            // アイコン情報の取得
            cartModelItem.setGoodsIconItems(createGoodsIconList(favoriteDto.getGoodsInformationIconDetailsDtoList()));

            goodsItems.add(cartModelItem);
        }

        cartModel.setFavoriteGoodsItems(goodsItems);
        cartModel.setFavoriteGoodsCodeList(goodsItems.stream()
                                                     .map(CartModelItem::getGcd)
                                                     .collect(Collectors.joining(",")));
    }

    /**
     * 画面表示・再表示(あしあと情報)
     *
     * @param goodsGroupDtoList 商品グループDTOリスト
     * @param cartModel         カートModel
     */
    public void toPageForLoadFootprint(List<GoodsGroupDto> goodsGroupDtoList, CartModel cartModel) {
        cartModel.setFootPrintGoodsItems(getGoodsItemsForGoodsListDto(goodsGroupDtoList, cartModel));
    }

    // 2023-renew No21 from here

    /**
     * 画面表示・再表示(よく一緒に購入される商品情報)
     *
     * @param goodsGroupDtoList 商品グループDTOリスト
     * @param cartModel         カートModel
     */
    public void toPageForLoadTogetherBuy(List<GoodsGroupDto> goodsGroupDtoList, CartModel cartModel) {
        cartModel.setTogetherBuyGoodsItems(getGoodsItemsForGoodsListDto(goodsGroupDtoList, cartModel));
    }
    // 2023-renew No21 to here

    /**
     * 画面表示・再表示(備考欄等)
     *
     * @param checkMessageDtoListMap チェックメッセージDTOリストマップ
     * @param cartModel              カートModel
     */
    public void toPageForLoad(Map<Integer, List<CheckMessageDto>> checkMessageDtoListMap, CartModel cartModel) {
        // カート一覧情報（今すぐお届け）をループ
        for (CartModelItem cartModelItem : cartModel.getCartGoodsItems()) {
            boolean viewFlg = true;
            List<CheckMessageDto> checkMessageDtoList = checkMessageDtoListMap.get(cartModelItem.getCartSeq());
            if (CollectionUtil.isNotEmpty(checkMessageDtoList)) {
                StringBuilder s = new StringBuilder();
                for (CheckMessageDto checkMessageDto : checkMessageDtoList) {
                    // 備考欄には出力しないメッセージは飛ばす
                    if (!isOutputGoodsMemoField(checkMessageDto)) {
                        continue;
                    }
                    s.append(checkMessageDto.getMessage());
                    s.append("<br />");

                    // 公開状態、販売状態、在庫きれ状況に応じて、お気に入りに追加ボタンの表示を制御する。
                    if (CartController.MSGCD_OPEN_STATUS_HIKOUKAI.equals(checkMessageDto.getMessageId())
                        || CartController.MSGCD_OPEN_END.equals(checkMessageDto.getMessageId())
                        || CartController.MSGCD_SALE_STATUS_HIHANBAI.equals(checkMessageDto.getMessageId())
                        || CartController.MSGCD_SALE_BEFORE.equals(checkMessageDto.getMessageId())
                        || CartController.MSGCD_SALE_END.equals(checkMessageDto.getMessageId())) {
                        viewFlg = false;
                    }

                }
                cartModelItem.setGoodsInformation(s.toString());
            }
            cartModelItem.setFavoriteButtonView(viewFlg);
        }

        // 2023-renew No14 from here
        // カート一覧情報（セールde予約）をループ
        for (Map.Entry<String, List<CartModelItem>> cartModelItemList : cartModel.getCartGoodsReserveItemMap()
                                                                                 .entrySet()) {
            Set<String> checkSet = new HashSet<>();
            StringBuilder s = new StringBuilder();
            boolean viewFlg = true;
            boolean reserveEditFlag = true;

            // 同一商品の1件目を取得（セールde予約は同一商品は表結合されて表示されるので、1件目に値を格納する）
            CartModelItem cartModelItemFirst = cartModelItemList.getValue().get(0);

            // 同一商品内のCartModelItemをループ
            for (CartModelItem cartModelItem : cartModelItemList.getValue()) {
                List<CheckMessageDto> checkMessageDtoList = checkMessageDtoListMap.get(cartModelItem.getCartSeq());
                if (CollectionUtil.isNotEmpty(checkMessageDtoList)) {
                    for (CheckMessageDto checkMessageDto : checkMessageDtoList) {
                        // 備考欄には出力しないメッセージは飛ばす
                        if (!isOutputGoodsMemoField(checkMessageDto)) {
                            continue;
                        }

                        // セット済のメッセージと同じものはセットしない
                        if (!checkSet.contains(checkMessageDto.getMessage())) {
                            s.append(checkMessageDto.getMessage());
                            s.append("<br />");
                            checkSet.add(checkMessageDto.getMessage());
                        }

                        // 公開状態、販売状態、在庫きれ状況に応じて、お気に入りに追加ボタンの表示を制御する。
                        if (CartController.MSGCD_OPEN_STATUS_HIKOUKAI.equals(checkMessageDto.getMessageId())
                            || CartController.MSGCD_OPEN_END.equals(checkMessageDto.getMessageId())
                            || CartController.MSGCD_SALE_STATUS_HIHANBAI.equals(checkMessageDto.getMessageId())
                            || CartController.MSGCD_SALE_BEFORE.equals(checkMessageDto.getMessageId())
                            || CartController.MSGCD_SALE_END.equals(checkMessageDto.getMessageId())) {
                            viewFlg = false;
                        }

                        // 取りおき可否チェックの結果に応じて、セールde予約変更ボタンの表示を制御する。
                        if (CartController.MSGCD_NOT_RESERVE.equals(checkMessageDto.getMessageId())) {
                            reserveEditFlag = false;
                        }
                    }

                }
            }

            // 同一商品の1件目に全てセット
            cartModelItemFirst.setGoodsInformation(s.toString());
            cartModelItemFirst.setFavoriteButtonView(viewFlg);
            cartModelItemFirst.setReserveEditFlag(reserveEditFlag);
        }
        // 2023-renew No14 to here
    }

    /**
     * メッセージを備考欄に出力するメッセージか判定します。
     *
     * @param checkMessageDto メッセージDto
     * @return true:出力する false:出力しない
     */
    protected boolean isOutputGoodsMemoField(CheckMessageDto checkMessageDto) {
        switch (checkMessageDto.getMessageId()) {
            case CartController.MSGCD_INDIVIDUAL_DELIVERY:
            case CartController.MSGCD_ALCOHOL_FLAG:
            case CartController.MSGCD_ALCOHOL_CHECK_ERROR:
                // PDR Migrate Customization from here
            case CartController.MSGCD_CART_TOTAL_PRICE_MAX_OVER:
                // PDR Migrate Customization to here
                return false;
            default:
                return true;
        }
    }

    /**
     * 再計算用のカート情報DTO作成
     *
     * @param cartModel カートModel
     * @return カート情報DTO
     */
    public CartDto toCartDtoForCalculate(CartModel cartModel) {

        CartDto cartDto = ApplicationContextUtility.getBean(CartDto.class);
        List<CartGoodsDto> cartGoodsDtoList = new ArrayList<>();

        for (CartModelItem cartModelItem : cartModel.getCartGoodsItems()) {

            // ブラウザバックなどで値がクリアされている場合、無視する
            // ※ブラウザバック直後などでは、itemsがクリアされ再計算用のボタンを押下したitemしか取得できない
            if (cartModelItem.getCartSeq() == null) {
                continue;
            }

            CartGoodsDto cartGoodsDto = ApplicationContextUtility.getBean(CartGoodsDto.class);
            GoodsDetailsDto goodsDetailsDto = ApplicationContextUtility.getBean(GoodsDetailsDto.class);
            CartGoodsEntity cartGoodsEntity = ApplicationContextUtility.getBean(CartGoodsEntity.class);

            BigDecimal cartGoodsCount = conversionUtility.toBigDecimal(cartModelItem.getGcnt());
            cartGoodsEntity.setCartGoodsCount(cartGoodsCount);
            cartGoodsEntity.setCartSeq(cartModelItem.getCartSeq());
            cartGoodsDto.setGoodsDetailsDto(goodsDetailsDto);
            cartGoodsDto.setCartGoodsEntity(cartGoodsEntity);
            cartGoodsDtoList.add(cartGoodsDto);
        }

        cartDto.setCartGoodsDtoList(cartGoodsDtoList);
        return cartDto;
    }

    /**
     * 商品情報一覧ページアイテムリストの作成
     *
     * @param goodsGroupDtoList 商品グループ情報DTO一覧
     * @param cartModel         カートモデル
     * @return 商品情報一覧ページアイテムリスト
     */
    protected List<CartModelItem> getGoodsItemsForGoodsListDto(List<GoodsGroupDto> goodsGroupDtoList,
                                                               CartModel cartModel) {

        List<CartModelItem> goodsItems = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(goodsGroupDtoList)) {
            for (GoodsGroupDto goodsGroupDto : goodsGroupDtoList) {
                CartModelItem cartModelItem = ApplicationContextUtility.getBean(CartModelItem.class);

                GoodsGroupEntity goodsGroupEntity = goodsGroupDto.getGoodsGroupEntity();
                if (goodsGroupDto.getGoodsGroupImageEntityList() != null) {
                    // 商品画像リストを取り出す。
                    List<String> goodsImageList = new ArrayList<>();
                    for (GoodsGroupImageEntity goodsGroupImageEntity : goodsGroupDto.getGoodsGroupImageEntityList()) {
                        goodsImageList.add(goodsUtility.getGoodsImagePath(goodsGroupImageEntity.getImageFileName()));
                    }
                    cartModelItem.setGoodsImageItems(goodsImageList);
                }

                if (goodsGroupEntity != null) {
                    cartModelItem.setGoodsGroupSeq(goodsGroupEntity.getGoodsGroupSeq());
                    cartModelItem.setGgcd(goodsGroupEntity.getGoodsGroupCode());
                    cartModelItem.setGoodsGroupName(goodsGroupEntity.getGoodsGroupName());
                    cartModelItem.setGoodsOpenStatus(goodsGroupEntity.getGoodsOpenStatusPC());
                    cartModelItem.setOpenStartTime(goodsGroupEntity.getOpenStartTimePC());
                    cartModelItem.setOpenEndTime(goodsGroupEntity.getOpenEndTimePC());

                    // PDR Migrate Customization from here
                    // 価格
                    cartModelItem.setGoodsPrice(goodsGroupEntity.getGoodsGroupMinPricePc());
                    // セール価格
                    cartModelItem.setPreDiscountPrice(goodsGroupEntity.getPreDiscountMinPrice());
                    // 価格帯フラグ
                    cartModelItem.setGoodsDisplayPriceRange(
                                    goodsUtility.isGoodsDisplayPriceRange(goodsGroupEntity.getGoodsGroupMinPricePc(),
                                                                          goodsGroupEntity.getGoodsGroupMaxPricePc()
                                                                         ));
                    // セール価格帯フラグ
                    cartModelItem.setGoodsDisplayPreDiscountPriceRange(
                                    goodsUtility.isGoodsDisplayPriceRange(goodsGroupEntity.getPreDiscountMinPrice(),
                                                                          goodsGroupEntity.getPreDiscountMaxPrice()
                                                                         ));
                    // PDR Migrate Customization to here

                    // 新着画像の表示期間を取得
                    Timestamp whatsnewDate = goodsUtility.getRealWhatsNewDate(
                                    goodsGroupDto.getGoodsGroupEntity().getWhatsnewDate());
                    cartModelItem.setWhatsnewDate(whatsnewDate);
                    cartModelItem.setGoodsPreDiscountPrice(goodsGroupEntity.getGoodsPreDiscountPrice());

                    // 2023-renew AddNo5 from here
                    cartModelItem.setGoodsPriceHigh(goodsGroupEntity.getGoodsGroupMaxPricePc());
                    cartModelItem.setGoodsPriceLow(goodsGroupEntity.getGoodsGroupMinPricePc());
                    cartModelItem.setGoodsSalePriceHigh(goodsGroupEntity.getGoodsGroupMaxPriceMb());
                    cartModelItem.setGoodsSalePriceLow(goodsGroupEntity.getGoodsGroupMinPriceMb());
                    // 2023-renew AddNo5 to here
                }

                // 在庫状況表示
                StockStatusDisplayEntity stockStatusDisplayEntity = goodsGroupDto.getBatchUpdateStockStatus();
                if (stockStatusDisplayEntity != null && stockStatusDisplayEntity.getStockStatusPc() != null) {
                    cartModelItem.setListStockStatusPc(stockStatusDisplayEntity.getStockStatusPc().getValue());
                }

                // アイコン情報の取得
                cartModelItem.setGoodsIconItems(
                                createGoodsIconList(goodsGroupDto.getGoodsInformationIconDetailsDtoList()));

                // PDR Migrate Customization from here
                GoodsGroupDisplayEntity goodsGroupDisplayEntity = goodsGroupDto.getGoodsGroupDisplayEntity();
                // シリーズセール価格整合性フラグ
                cartModelItem.setGroupSalePriceIntegrityFlag(HTypeSalePriceIntegrityFlag.MATCH.equals(
                                goodsGroupEntity.getGroupSalePriceIntegrityFlag()));
                // SALEアイコンフラグ
                cartModelItem.setSaleIconFlag(HTypeSaleIconFlag.ON.equals(goodsGroupDisplayEntity.getSaleIconFlag()));
                // お取りおきアイコンフラグ
                cartModelItem.setReserveIconFlag(
                                HTypeReserveIconFlag.ON.equals(goodsGroupDisplayEntity.getReserveIconFlag()));
                // NEWアイコンフラグ
                cartModelItem.setNewIconFlag(HTypeNewIconFlag.ON.equals(goodsGroupDisplayEntity.getNewIconFlag()));
                // PDR Migrate Customization to here

                // 2023-renew No92 from here
                cartModelItem.setOutletIconFlag(
                                HTypeOutletIconFlag.ON.equals(goodsGroupDisplayEntity.getOutletIconFlag()));
                // 2023-renew No92 to here

                goodsItems.add(cartModelItem);
            }
        }
        return goodsItems;
    }

    /**
     * お気に入り商品検索条件Dtoの作成
     *
     * @param cartModel カートモデル
     * @return お気に入り検索条件Dto
     */
    public FavoriteSearchForDaoConditionDto toFavoriteConditionDtoForSearchFavoriteList(CartModel cartModel) {
        // お気に入り検索条件Dtoの作成 公開状態＝指定なし
        FavoriteSearchForDaoConditionDto favoriteConditionDto =
                        ApplicationContextUtility.getBean(FavoriteSearchForDaoConditionDto.class);

        favoriteConditionDto.setMemberInfoSeq(cartModel.getCommonInfo().getCommonInfoUser().getMemberInfoSeq());

        return favoriteConditionDto;
    }

    /**
     * アイコン情報を設定
     *
     * @param goodsInformationIconDetailsDtoList 登録されているアイコン情報
     * @return 画面表示用に作成したアイコンリスト
     */
    protected List<CartModelItem> createGoodsIconList(List<GoodsInformationIconDetailsDto> goodsInformationIconDetailsDtoList) {
        List<CartModelItem> goodsIconList = new ArrayList<>();
        if (goodsInformationIconDetailsDtoList != null) {
            for (GoodsInformationIconDetailsDto goodsInformationIconDetailsDto : goodsInformationIconDetailsDtoList) {
                CartModelItem iconPageItem = ApplicationContextUtility.getBean(CartModelItem.class);
                iconPageItem.setIconName(goodsInformationIconDetailsDto.getIconName());
                iconPageItem.setIconColorCode(goodsInformationIconDetailsDto.getColorCode());

                goodsIconList.add(iconPageItem);

            }
        }
        return goodsIconList;
    }

    /**
     * お気に入り表示設定
     *
     * @param favoriteList お気に入りリスト
     * @param cartModel    カートModel
     */
    protected void setFavoriteView(List<FavoriteEntity> favoriteList, CartModel cartModel) {
        if (cartModel.getFavoriteGoodsItems() != null) {
            for (CartModelItem indexPageItem : cartModel.getCartGoodsItems()) {
                for (FavoriteEntity favoriteEntity : favoriteList) {
                    if (indexPageItem.getGoodsSeq().equals(favoriteEntity.getGoodsSeq())) {
                        indexPageItem.setFavoriteView(false);
                    }
                }
            }
        }
    }

    // 2023-renew No14 from here

    /**
     * 今すぐお届け／セールde予約内の同一商品チェック用リスト（確認ダイアログ用）を作成
     *
     * @param cartModel カートModel
     */
    protected void setDuplicationGoodsItems(CartModel cartModel) {

        // 同一商品チェック用リストを初期化
        cartModel.setDuplicationGoodsItems(new ArrayList<>());

        if (!cartModel.isCartGoodsIn() || !cartModel.isCartGoodsReserveIn()) {
            // 今すぐお届け／セールde予約のどちらか片方でも空の場合、処理しない
            return;
        }

        // 以降の処理は、今すぐお届け／セールde予約の両者が存在する場合のみ行う

        // 今すぐお届け分を商品コード（kp除去）単位で数量集計
        // ※通常価格と心意気価格で分かれている明細も集約
        Map<String, Integer> deliveryNowGcntMap = new LinkedHashMap<>();
        cartModel.getCartGoodsItems().forEach(item -> {
            Integer gcnt = conversionUtility.toInteger((item.getGcnt()));
            if (deliveryNowGcntMap.containsKey(item.getGcd())) {
                gcnt += deliveryNowGcntMap.get(item.getGcd());
            }
            deliveryNowGcntMap.put(item.getGcd(), gcnt);
        });

        // セールde予約分を商品コード単位で数量集計
        // ※上記の今すぐお届け分に含まれているもののみ
        Map<String, Integer> reserveGcntMap = new LinkedHashMap<>();
        cartModel.getCartGoodsReserveItemMap().forEach((k, v) -> {
            if (deliveryNowGcntMap.containsKey(k)) {
                reserveGcntMap.put(k, v.stream().mapToInt(o -> Integer.parseInt(o.getGcnt())).sum());
            }
        });

        // 同一商品チェック用リストを生成
        for (Map.Entry<String, Integer> entry : reserveGcntMap.entrySet()) {

            // 基本情報はセールde予約分の1件目からコピー
            // ※表示する商品名のために、心意気商品を含まない方をベースにする
            CartModelItem duplicationGoodsItem = ApplicationContextUtility.getBean(CartModelItem.class);
            try {
                BeanUtils.copyProperties(duplicationGoodsItem,
                                         cartModel.getCartGoodsReserveItemMap().get(entry.getKey()).get(0)
                                        );
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            // 今すぐお届け数量（確認ダイアログ用）
            duplicationGoodsItem.setCountQuantityDeliveryNow(deliveryNowGcntMap.get(entry.getKey()));
            // セールde予約数量（確認ダイアログ用）
            duplicationGoodsItem.setCountQuantityReserve(entry.getValue());

            // 同一商品チェック用リストに追加
            cartModel.getDuplicationGoodsItems().add(duplicationGoodsItem);
        }
    }

    // 2023-renew No14 to here

    /**
     * カート一括登録リクエストに変換
     *
     * @param commonInfo 共通情報
     * @param cartGoodsForTakeOverDtoList カート一括登録用引継DTOリスト
     * @return カート一括登録リクエスト
     */
    public CartScreenRegistRequest toCartScreenRegistRequest(CommonInfo commonInfo,
                                                             List<CartGoodsForTakeOverDto> cartGoodsForTakeOverDtoList) {

        if (CollectionUtils.isEmpty(cartGoodsForTakeOverDtoList)) {
            return null;
        }

        CartScreenRegistRequest cartScreenRegistRequest = new CartScreenRegistRequest();
        List<CartGoodsForTakeOverDtoRequest> registCartGoodsList = new ArrayList<>();

        for (CartGoodsForTakeOverDto cartGoodsForTakeOverDto : cartGoodsForTakeOverDtoList) {
            CartGoodsForTakeOverDtoRequest cartGoodsForTakeOverDtoRequest = new CartGoodsForTakeOverDtoRequest();
            cartGoodsForTakeOverDtoRequest.setGoodsGroupSeq(cartGoodsForTakeOverDto.getGoodsGroupSeq());
            cartGoodsForTakeOverDtoRequest.setGoodsSeq(cartGoodsForTakeOverDto.getGoodsSeq());
            cartGoodsForTakeOverDtoRequest.setGoodsCode(cartGoodsForTakeOverDto.getGoodsCode());
            cartGoodsForTakeOverDtoRequest.setGoodsCount(cartGoodsForTakeOverDto.getGoodsCount());
            // 2023-renew No14 from here
            cartGoodsForTakeOverDtoRequest.setReserveDeliveryDate(cartGoodsForTakeOverDto.getReserveDeliveryDate());
            cartGoodsForTakeOverDtoRequest.setReserveFlag(cartGoodsForTakeOverDto.getReserveFlag().getValue());
            // 2023-renew No14 to here
            registCartGoodsList.add(cartGoodsForTakeOverDtoRequest);
        }

        cartScreenRegistRequest.setRegistCartGoodsList(registCartGoodsList);
        cartScreenRegistRequest.setAccessUid(commonInfo.getCommonInfoBase().getAccessUid());
        cartScreenRegistRequest.setMemberInfoSeq(commonInfo.getCommonInfoUser().getMemberInfoSeq());
        cartScreenRegistRequest.setSiteType(HTypeSiteType.FRONT_PC.getValue());

        // 2023-renew No14 from here
        MemberInfoEntity memberInfoEntity = commonInfoUtility.getMemberInfoEntity(commonInfo);
        cartScreenRegistRequest.setCustomerNo(memberInfoEntity.getCustomerNo());
        cartScreenRegistRequest.setZipcode(memberInfoEntity.getMemberInfoZipCode());
        // 2023-renew No14 to here

        return cartScreenRegistRequest;
    }

    /**
     * アクセス情報登録リクエストに変換
     *
     * @param accessType    アクセス区分
     * @param goodsGroupSeq 商品グループSEQ
     * @param accessCount   アクセス数
     * @param accessUid     端末識別番号
     * @param campaignCode  キャンペーンコード
     * @return アクセス情報登録リクエスト
     */
    public AccessRegistRequest toAccessRegistRequest(HTypeAccessType accessType,
                                                     Integer goodsGroupSeq,
                                                     Integer accessCount,
                                                     String accessUid,
                                                     String campaignCode) {

        AccessRegistRequest accessRegistRequest = new AccessRegistRequest();
        if (accessType != null) {
            accessRegistRequest.setAccessType(accessType.getValue());
        }
        accessRegistRequest.setGoodsGroupSeq(goodsGroupSeq);
        accessRegistRequest.setAccessCount(accessCount);
        accessRegistRequest.setAccessUid(accessUid);
        accessRegistRequest.setCampainCode(campaignCode);

        return accessRegistRequest;
    }

    /**
     * カート商品追加リクエストに変換
     *
     * @param goodsCode     商品コード
     * @param goodsCount    商品数量
     * @param accessUid     端末識別番号
     * @param siteType      サイト種別
     * @param memberInfoEntity 会員エンティティ
     * @return カート商品追加リクエスト
     */
    public CartGoodsAddRequest toCartGoodsAddRequest(String goodsCode,
                                                     BigDecimal goodsCount,
                                                     String accessUid,
                                                     HTypeSiteType siteType,
                                                     MemberInfoEntity memberInfoEntity) {

        CartGoodsAddRequest cartGoodsAddRequest = new CartGoodsAddRequest();
        cartGoodsAddRequest.setGoodsCode(goodsCode);
        cartGoodsAddRequest.setCount(goodsCount);
        cartGoodsAddRequest.setAccessUid(accessUid);
        if (siteType != null) {
            cartGoodsAddRequest.setSiteType(siteType.getValue());
        }
        // 2023-renew No14 from here
        cartGoodsAddRequest.setMemberInfoSeq(memberInfoEntity.getMemberInfoSeq());
        cartGoodsAddRequest.setCustomerNo(memberInfoEntity.getCustomerNo());
        // 2023-renew No14 to here

        return cartGoodsAddRequest;
    }

    /**
     * お気に入り情報リスト取得リクエストに変換
     *
     * @param conditionDto お気に入りDao用検索条件Dtoクラス
     * @return お気に入り情報リスト取得リクエスト
     */
    public FavoriteListGetRequest toFavoriteListGetRequest(FavoriteSearchForDaoConditionDto conditionDto) {

        if (conditionDto == null) {
            return null;
        }

        FavoriteListGetRequest favoriteListGetRequest = new FavoriteListGetRequest();
        favoriteListGetRequest.setMemberInfoSeq(conditionDto.getMemberInfoSeq());
        favoriteListGetRequest.setExclusionGoodsSeqList(conditionDto.getExclusionGoodsSeqList());
        favoriteListGetRequest.setSiteType(HTypeSiteType.FRONT_PC.getValue());
        if (conditionDto.getGoodsOpenStatus() != null) {
            favoriteListGetRequest.setGoodsOpenStatus(conditionDto.getGoodsOpenStatus().getValue());
        }
        if (conditionDto.getMemberRank() != null) {
            favoriteListGetRequest.setMemberRank(conditionDto.getMemberRank().getValue());
        }

        return favoriteListGetRequest;
    }

    /**
     * ページ情報リクエストに変換
     *
     * @param limit      画面最大表示件数
     * @param orderField ソート項目
     * @param orderAsc   昇順/降順フラグ
     * @return ページ情報リクエスト
     */
    public PageInfoRequest toPageInfoRequest(int limit, String orderField, boolean orderAsc) {

        PageInfoRequest pageInfoRequest = new PageInfoRequest();
        pageInfoRequest.setLimit(limit);
        pageInfoRequest.setOrderBy(orderField);
        pageInfoRequest.setSort(orderAsc);

        return pageInfoRequest;
    }

    /**
     * お気に入りDtoクラスリストに変換
     *
     * @param favoriteDtoListResponse 商品グループDtoListレスポンス
     * @return お気に入りDtoクラスリスト
     */
    public List<FavoriteDto> toFavoriteDtoList(FavoriteDtoListResponse favoriteDtoListResponse) {

        if (favoriteDtoListResponse == null || CollectionUtil.isEmpty(
                        favoriteDtoListResponse.getFavoriteDtoListResponse())) {
            return new ArrayList<>();
        }

        List<FavoriteDto> favoriteDtoList = new ArrayList<>();

        for (FavoriteDtoResponse favoriteDtoResponse : favoriteDtoListResponse.getFavoriteDtoListResponse()) {
            FavoriteDto favoriteDto = new FavoriteDto();

            favoriteDto.setFavoriteEntity(toFavoriteEntity(favoriteDtoResponse.getFavoriteEntityResponse()));
            favoriteDto.setGoodsDetailsDto(toGoodsDetailsDtoMember(favoriteDtoResponse.getGoodsDetailsDtoResponse()));
            favoriteDto.setGoodsGroupImageEntityList(toGoodsGroupImageEntityListMember(
                            favoriteDtoResponse.getGoodsGroupImageEntityListResponse()));
            favoriteDto.setStockStatus(favoriteDtoResponse.getStockStatus());

            favoriteDtoList.add(favoriteDto);
        }

        return favoriteDtoList;
    }

    /**
     * お気に入りエンティティに変換
     *
     * @param favoriteEntityResponse お気に入りクラスレスポンス
     * @return お気に入りエンティティ
     */
    public FavoriteEntity toFavoriteEntity(FavoriteEntityResponse favoriteEntityResponse) {

        if (favoriteEntityResponse == null) {
            return null;
        }

        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.setMemberInfoSeq(favoriteEntityResponse.getMemberInfoSeq());
        favoriteEntity.setGoodsSeq(favoriteEntityResponse.getGoodsSeq());
        favoriteEntity.setRegistTime(conversionUtility.toTimeStamp(favoriteEntityResponse.getRegistTime()));
        favoriteEntity.setUpdateTime(conversionUtility.toTimeStamp(favoriteEntityResponse.getUpdateTime()));

        return favoriteEntity;
    }

    /**
     * 商品詳細DTOに変換
     *
     * @param goodsDetailsDtoResponse 商品詳細Dtoクラスレスポンス
     * @return 商品詳細DTO
     */
    public GoodsDetailsDto toGoodsDetailsDtoMember(GoodsDetailsDtoResponse goodsDetailsDtoResponse) {

        if (goodsDetailsDtoResponse == null) {
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
                        toGoodsGroupImageEntityListMember(goodsDetailsDtoResponse.getGoodsGroupImageEntityList()));
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
        goodsDetailsDto.setUnitImage(toGoodsImageEntity(goodsDetailsDtoResponse.getUnitImage()));
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
        // 2023-renew No92 from here
        goodsDetailsDto.setOutletIconFlag(EnumTypeUtil.getEnumFromValue(HTypeOutletIconFlag.class,
                                                                        goodsDetailsDtoResponse.getOutletIconFlag()
                                                                       ));
        // 2023-renew No92 to here
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
        // 2023-renew AddNo5 from here
        goodsDetailsDto.setGoodsPriceInTaxHight(goodsDetailsDtoResponse.getGoodsPriceInTaxHight());
        goodsDetailsDto.setGoodsPriceInTaxLow(goodsDetailsDtoResponse.getGoodsPriceInTaxLow());
        goodsDetailsDto.setPreDiscountPriceHight(goodsDetailsDtoResponse.getPreDiscountPriceHight());
        goodsDetailsDto.setPreDiscountPriceLow(goodsDetailsDtoResponse.getPreDiscountPriceLow());
        // 2023-renew AddNo5 to here
        return goodsDetailsDto;
    }

    /**
     * 商品グループ画像リストに変換
     *
     * @param goodsGroupImageEntityResponseList 商品グループ画像リストレスポンス
     * @return 商品グループ画像リスト
     */
    public List<GoodsGroupImageEntity> toGoodsGroupImageEntityListMember(List<GoodsGroupImageEntityResponse> goodsGroupImageEntityResponseList) {

        if (CollectionUtil.isEmpty(goodsGroupImageEntityResponseList)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntity> goodsGroupImageEntityList = new ArrayList<>();

        for (GoodsGroupImageEntityResponse goodsGroupImageEntityResponse : goodsGroupImageEntityResponseList) {
            GoodsGroupImageEntity goodsGroupImageEntity = new GoodsGroupImageEntity();
            goodsGroupImageEntity.setGoodsGroupSeq(goodsGroupImageEntityResponse.getGoodsGroupSeq());
            goodsGroupImageEntity.setImageTypeVersionNo(goodsGroupImageEntityResponse.getImageTypeVersionNo());
            goodsGroupImageEntity.setImageFileName(goodsGroupImageEntityResponse.getImageFileName());
            goodsGroupImageEntity.setRegistTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntityResponse.getRegistTime()));
            goodsGroupImageEntity.setUpdateTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntityResponse.getUpdateTime()));

            goodsGroupImageEntityList.add(goodsGroupImageEntity);
        }

        return goodsGroupImageEntityList;
    }

    /**
     * 商品規格画像登録リストに変換
     *
     * @param goodsImageEntityResponse 商品グループ画像レスポンス
     * @return 商品規格画像登録リスト
     */
    public GoodsImageEntity toGoodsImageEntity(GoodsImageEntityResponse goodsImageEntityResponse) {

        if (goodsImageEntityResponse == null) {
            return null;
        }

        GoodsImageEntity goodsImageEntity = new GoodsImageEntity();
        goodsImageEntity.setGoodsGroupSeq(goodsImageEntityResponse.getGoodsGroupSeq());
        goodsImageEntity.setGoodsSeq(goodsImageEntityResponse.getGoodsSeq());
        goodsImageEntity.setImageFileName(goodsImageEntityResponse.getImageFileName());
        goodsImageEntity.setDisplayFlag(EnumTypeUtil.getEnumFromValue(HTypeDisplayStatus.class,
                                                                      goodsImageEntityResponse.getDisplayFlag()
                                                                     ));
        goodsImageEntity.setRegistTime(conversionUtility.toTimeStamp(goodsImageEntityResponse.getRegistTime()));
        goodsImageEntity.setUpdateTime(conversionUtility.toTimeStamp(goodsImageEntityResponse.getUpdateTime()));
        goodsImageEntity.setTmpFilePath(goodsImageEntityResponse.getTmpFilePath());

        return goodsImageEntity;
    }

    /**
     * 商品グループ一覧情報DTOに変換
     *
     * @param goodsGroupDtoListResponse 商品グループDtoListレスポンス
     * @return 商品グループ一覧情報DTO
     */
    public List<GoodsGroupDto> toGoodsGroupDtoList(GoodsGroupDtoListResponse goodsGroupDtoListResponse) {

        if (goodsGroupDtoListResponse == null || CollectionUtil.isEmpty(
                        goodsGroupDtoListResponse.getGoodsGroupDtoListResponse())) {
            return null;
        }

        List<GoodsGroupDto> footprintGoodsGroupDtoList = new ArrayList<>();

        for (GoodsGroupDtoResponse goodsGroupDtoResponse : goodsGroupDtoListResponse.getGoodsGroupDtoListResponse()) {
            GoodsGroupDto goodsGroupDto = new GoodsGroupDto();

            goodsGroupDto.setGoodsGroupEntity(toGoodsGroupEntity(goodsGroupDtoResponse.getGoodsGroupEntity()));
            goodsGroupDto.setBatchUpdateStockStatus(
                            toStockStatusDisplayEntity(goodsGroupDtoResponse.getBatchUpdateStockStatus()));
            goodsGroupDto.setRealTimeStockStatus(
                            toStockStatusDisplayEntity(goodsGroupDtoResponse.getRealTimeStockStatus()));
            goodsGroupDto.setGoodsGroupDisplayEntity(
                            toGoodsGroupDisplayEntity(goodsGroupDtoResponse.getGoodsGroupDisplayEntity()));
            goodsGroupDto.setGoodsGroupImageEntityList(
                            toGoodsGroupImageEntityListGoods(goodsGroupDtoResponse.getGoodsGroupImageEntityList()));
            goodsGroupDto.setGoodsDtoList(toGoodsDtoList(goodsGroupDtoResponse.getGoodsDtoList()));
            goodsGroupDto.setCategoryGoodsEntityList(
                            toCategoryGoodsEntityList(goodsGroupDtoResponse.getCategoryGoodsEntityList()));
            goodsGroupDto.setTaxRate(goodsGroupDtoResponse.getTaxRate());

            footprintGoodsGroupDtoList.add(goodsGroupDto);
        }

        return footprintGoodsGroupDtoList;
    }

    /**
     * 商品グループエンティティに変換
     *
     * @param goodsGroupEntityResponse 商品グループレスポンス
     * @return 商品グループエンティティ
     */
    public GoodsGroupEntity toGoodsGroupEntity(GoodsGroupEntityResponse goodsGroupEntityResponse) {

        if (goodsGroupEntityResponse == null) {
            return null;
        }

        GoodsGroupEntity goodsGroupEntity = new GoodsGroupEntity();
        goodsGroupEntity.setGoodsGroupSeq(goodsGroupEntityResponse.getGoodsGroupSeq());
        goodsGroupEntity.setGoodsGroupCode(goodsGroupEntityResponse.getGoodsGroupCode());
        goodsGroupEntity.setGoodsGroupName(goodsGroupEntityResponse.getGoodsGroupName());
        goodsGroupEntity.setWhatsnewDate(conversionUtility.toTimeStamp(goodsGroupEntityResponse.getWhatsnewDate()));
        goodsGroupEntity.setGoodsOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                            goodsGroupEntityResponse.getGoodsOpenStatus()
                                                                           ));
        goodsGroupEntity.setOpenStartTimePC(conversionUtility.toTimeStamp(goodsGroupEntityResponse.getOpenStartTime()));
        goodsGroupEntity.setOpenEndTimePC(conversionUtility.toTimeStamp(goodsGroupEntityResponse.getOpenEndTime()));
        goodsGroupEntity.setGoodsPreDiscountPrice(goodsGroupEntityResponse.getGoodsPreDiscountPrice());
        goodsGroupEntity.setGoodsTaxType(EnumTypeUtil.getEnumFromValue(HTypeGoodsTaxType.class,
                                                                       goodsGroupEntityResponse.getGoodsTaxType()
                                                                      ));
        goodsGroupEntity.setTaxRate(goodsGroupEntityResponse.getTaxRate());
        goodsGroupEntity.setAlcoholFlag(EnumTypeUtil.getEnumFromValue(HTypeAlcoholFlag.class,
                                                                      goodsGroupEntityResponse.getAlcoholFlag()
                                                                     ));
        // 2023-renew AddNo5 from here
        goodsGroupEntity.setGoodsGroupMaxPricePc(goodsGroupEntityResponse.getGoodsGroupMaxPricePc());
        goodsGroupEntity.setGoodsGroupMinPricePc(goodsGroupEntityResponse.getGoodsGroupMinPricePc());
        goodsGroupEntity.setGoodsGroupMaxPriceMb(goodsGroupEntityResponse.getGoodsGroupMaxPriceMb());
        goodsGroupEntity.setGoodsGroupMinPriceMb(goodsGroupEntityResponse.getGoodsGroupMinPriceMb());
        // 2023-renew AddNo5 to here
        goodsGroupEntity.setSnsLinkFlag(EnumTypeUtil.getEnumFromValue(HTypeSnsLinkFlag.class,
                                                                      goodsGroupEntityResponse.getSnsLinkFlag()
                                                                     ));
        goodsGroupEntity.setShopSeq(1001);
        goodsGroupEntity.setVersionNo(goodsGroupEntityResponse.getVersionNo());
        goodsGroupEntity.setRegistTime(conversionUtility.toTimeStamp(goodsGroupEntityResponse.getRegistTime()));
        goodsGroupEntity.setUpdateTime(conversionUtility.toTimeStamp(goodsGroupEntityResponse.getUpdateTime()));
        goodsGroupEntity.setPreDiscountMaxPrice(goodsGroupEntityResponse.getPreDiscountMaxPrice());
        goodsGroupEntity.setPreDiscountMinPrice(goodsGroupEntityResponse.getPreDiscountMinPrice());
        goodsGroupEntity.setGoodsClassType(EnumTypeUtil.getEnumFromValue(HTypeGoodsClassType.class,
                                                                         goodsGroupEntityResponse.getGoodsClassType()
                                                                        ));
        goodsGroupEntity.setDentalMonopolySalesFlg(EnumTypeUtil.getEnumFromValue(HTypeDentalMonopolySalesFlg.class,
                                                                                 goodsGroupEntityResponse.getDentalMonopolySalesFlg()
                                                                                ));
        goodsGroupEntity.setCatalogDisplayOrder(goodsGroupEntityResponse.getCatalogDisplayOrder());
        goodsGroupEntity.setGroupPrice(goodsGroupEntityResponse.getGroupPrice());
        goodsGroupEntity.setGroupSalePrice(goodsGroupEntityResponse.getGroupSalePrice());
        goodsGroupEntity.setGroupPriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypeGroupPriceMarkDispFlag.class,
                                                                                 goodsGroupEntityResponse.getSnsLinkFlag()
                                                                                ));
        goodsGroupEntity.setGroupSalePriceMarkDispFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeGroupSalePriceMarkDispFlag.class,
                                                      goodsGroupEntityResponse.getGroupSalePriceMarkDispFlag()
                                                     ));
        goodsGroupEntity.setGroupSalePriceIntegrityFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceIntegrityFlag.class,
                                                                                      goodsGroupEntityResponse.getGroupSalePriceIntegrityFlag()
                                                                                     ));
        return goodsGroupEntity;
    }

    /**
     * 商品グループ在庫表示クラスに変換
     *
     * @param stockStatusDisplayEntityResponse 商品グループ在庫表示レスポンス<br/>こちらは非同期更新処理の完了にかかわらず設定されるため、最新データでないことに注意
     * @return 商品グループ在庫表示クラス
     */
    public StockStatusDisplayEntity toStockStatusDisplayEntity(StockStatusDisplayEntityResponse stockStatusDisplayEntityResponse) {

        if (stockStatusDisplayEntityResponse == null) {
            return null;
        }

        StockStatusDisplayEntity stockStatusDisplayEntity = new StockStatusDisplayEntity();

        stockStatusDisplayEntity.setGoodsGroupSeq(stockStatusDisplayEntityResponse.getGoodsGroupSeq());
        stockStatusDisplayEntity.setStockStatusPc(EnumTypeUtil.getEnumFromValue(HTypeStockStatusType.class,
                                                                                stockStatusDisplayEntityResponse.getStockStatus()
                                                                               ));
        stockStatusDisplayEntity.setRegistTime(
                        conversionUtility.toTimeStamp(stockStatusDisplayEntityResponse.getRegistTime()));
        stockStatusDisplayEntity.setUpdateTime(
                        conversionUtility.toTimeStamp(stockStatusDisplayEntityResponse.getUpdateTime()));

        return stockStatusDisplayEntity;
    }

    /**
     * 商品グループ表示クラスに変換
     *
     * @param goodsGroupDisplayEntityResponse 商品グループ表示レスポンス
     * @return 商品グループ表示クラス
     */
    public GoodsGroupDisplayEntity toGoodsGroupDisplayEntity(GoodsGroupDisplayEntityResponse goodsGroupDisplayEntityResponse) {

        if (goodsGroupDisplayEntityResponse == null) {
            return null;
        }

        GoodsGroupDisplayEntity goodsGroupDisplayEntity = new GoodsGroupDisplayEntity();

        goodsGroupDisplayEntity.setGoodsGroupSeq(goodsGroupDisplayEntityResponse.getGoodsGroupSeq());
        goodsGroupDisplayEntity.setInformationIconPC(goodsGroupDisplayEntityResponse.getInformationIcon());
        goodsGroupDisplayEntity.setSearchKeyword(goodsGroupDisplayEntityResponse.getSearchKeyword());
        goodsGroupDisplayEntity.setSearchKeywordEm(goodsGroupDisplayEntityResponse.getSearchKeywordEm());
        goodsGroupDisplayEntity.setUnitManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class,
                                                                                    goodsGroupDisplayEntityResponse.getUnitManagementFlag()
                                                                                   ));
        goodsGroupDisplayEntity.setUnitTitle1(goodsGroupDisplayEntityResponse.getUnitTitle1());
        goodsGroupDisplayEntity.setUnitTitle2(goodsGroupDisplayEntityResponse.getUnitTitle2());
        goodsGroupDisplayEntity.setMetaDescription(goodsGroupDisplayEntityResponse.getMetaDescription());
        goodsGroupDisplayEntity.setMetaKeyword(goodsGroupDisplayEntityResponse.getMetaKeyword());
        goodsGroupDisplayEntity.setDeliveryType(goodsGroupDisplayEntityResponse.getDeliveryType());
        goodsGroupDisplayEntity.setGoodsNote1(goodsGroupDisplayEntityResponse.getGoodsNote1());
        goodsGroupDisplayEntity.setGoodsNote2(goodsGroupDisplayEntityResponse.getGoodsNote2());
        goodsGroupDisplayEntity.setGoodsNote3(goodsGroupDisplayEntityResponse.getGoodsNote3());
        goodsGroupDisplayEntity.setGoodsNote4(goodsGroupDisplayEntityResponse.getGoodsNote4());
        goodsGroupDisplayEntity.setGoodsNote5(goodsGroupDisplayEntityResponse.getGoodsNote5());
        goodsGroupDisplayEntity.setGoodsNote6(goodsGroupDisplayEntityResponse.getGoodsNote6());
        goodsGroupDisplayEntity.setGoodsNote7(goodsGroupDisplayEntityResponse.getGoodsNote7());
        goodsGroupDisplayEntity.setGoodsNote8(goodsGroupDisplayEntityResponse.getGoodsNote8());
        goodsGroupDisplayEntity.setGoodsNote9(goodsGroupDisplayEntityResponse.getGoodsNote9());
        goodsGroupDisplayEntity.setGoodsNote10(goodsGroupDisplayEntityResponse.getGoodsNote10());
        goodsGroupDisplayEntity.setGoodsNote11(goodsGroupDisplayEntityResponse.getGoodsNote11());
        goodsGroupDisplayEntity.setGoodsNote12(goodsGroupDisplayEntityResponse.getGoodsNote12());
        goodsGroupDisplayEntity.setGoodsNote13(goodsGroupDisplayEntityResponse.getGoodsNote13());
        goodsGroupDisplayEntity.setGoodsNote14(goodsGroupDisplayEntityResponse.getGoodsNote14());
        goodsGroupDisplayEntity.setGoodsNote15(goodsGroupDisplayEntityResponse.getGoodsNote15());
        goodsGroupDisplayEntity.setGoodsNote16(goodsGroupDisplayEntityResponse.getGoodsNote16());
        goodsGroupDisplayEntity.setGoodsNote17(goodsGroupDisplayEntityResponse.getGoodsNote17());
        goodsGroupDisplayEntity.setGoodsNote18(goodsGroupDisplayEntityResponse.getGoodsNote18());
        goodsGroupDisplayEntity.setGoodsNote19(goodsGroupDisplayEntityResponse.getGoodsNote19());
        goodsGroupDisplayEntity.setGoodsNote20(goodsGroupDisplayEntityResponse.getGoodsNote20());
        goodsGroupDisplayEntity.setOrderSetting1(goodsGroupDisplayEntityResponse.getOrderSetting1());
        goodsGroupDisplayEntity.setOrderSetting2(goodsGroupDisplayEntityResponse.getOrderSetting2());
        goodsGroupDisplayEntity.setOrderSetting3(goodsGroupDisplayEntityResponse.getOrderSetting3());
        goodsGroupDisplayEntity.setOrderSetting4(goodsGroupDisplayEntityResponse.getOrderSetting4());
        goodsGroupDisplayEntity.setOrderSetting5(goodsGroupDisplayEntityResponse.getOrderSetting5());
        goodsGroupDisplayEntity.setOrderSetting6(goodsGroupDisplayEntityResponse.getOrderSetting6());
        goodsGroupDisplayEntity.setOrderSetting7(goodsGroupDisplayEntityResponse.getOrderSetting7());
        goodsGroupDisplayEntity.setOrderSetting8(goodsGroupDisplayEntityResponse.getOrderSetting8());
        goodsGroupDisplayEntity.setOrderSetting9(goodsGroupDisplayEntityResponse.getOrderSetting9());
        goodsGroupDisplayEntity.setOrderSetting10(goodsGroupDisplayEntityResponse.getOrderSetting10());
        goodsGroupDisplayEntity.setRegistTime(
                        conversionUtility.toTimeStamp(goodsGroupDisplayEntityResponse.getRegistTime()));
        goodsGroupDisplayEntity.setUpdateTime(
                        conversionUtility.toTimeStamp(goodsGroupDisplayEntityResponse.getUpdateTime()));
        goodsGroupDisplayEntity.setSaleIconFlag(EnumTypeUtil.getEnumFromValue(HTypeSaleIconFlag.class,
                                                                              goodsGroupDisplayEntityResponse.getSaleIconFlag()
                                                                             ));
        goodsGroupDisplayEntity.setReserveIconFlag(EnumTypeUtil.getEnumFromValue(HTypeReserveIconFlag.class,
                                                                                 goodsGroupDisplayEntityResponse.getReserveIconFlag()
                                                                                ));
        goodsGroupDisplayEntity.setNewIconFlag(EnumTypeUtil.getEnumFromValue(HTypeNewIconFlag.class,
                                                                             goodsGroupDisplayEntityResponse.getNewIconFlag()
                                                                            ));
        // 2023-renew No92 from here
        goodsGroupDisplayEntity.setOutletIconFlag(EnumTypeUtil.getEnumFromValue(HTypeOutletIconFlag.class,
                                                                                goodsGroupDisplayEntityResponse.getOutletIconFlag()
                                                                               ));
        // 2023-renew No92 to here
        return goodsGroupDisplayEntity;
    }

    /**
     * 商品グループ画像クラスリストに変換
     *
     * @param goodsGroupImageEntityResponseList 商品グループDtoListレスポンス
     * @return 商品グループ画像クラスリスト
     */
    public List<GoodsGroupImageEntity> toGoodsGroupImageEntityListGoods(List<jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupImageEntityResponse> goodsGroupImageEntityResponseList) {

        if (CollectionUtil.isEmpty(goodsGroupImageEntityResponseList)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntity> goodsGroupImageEntityList = new ArrayList<>();

        for (jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupImageEntityResponse goodsGroupImageEntityResponse : goodsGroupImageEntityResponseList) {
            GoodsGroupImageEntity goodsGroupImageEntity = new GoodsGroupImageEntity();
            goodsGroupImageEntity.setGoodsGroupSeq(goodsGroupImageEntityResponse.getGoodsGroupSeq());
            goodsGroupImageEntity.setImageTypeVersionNo(goodsGroupImageEntityResponse.getImageTypeVersionNo());
            goodsGroupImageEntity.setImageFileName(goodsGroupImageEntityResponse.getImageFileName());
            goodsGroupImageEntity.setRegistTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntityResponse.getRegistTime()));
            goodsGroupImageEntity.setUpdateTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntityResponse.getUpdateTime()));

            goodsGroupImageEntityList.add(goodsGroupImageEntity);
        }

        return goodsGroupImageEntityList;
    }

    /**
     * 商品DTOリストに変換
     *
     * @param goodsDtoResponseList 商品DTOリストレスポンス
     * @return 商品DTOリスト
     */
    public List<GoodsDto> toGoodsDtoList(List<GoodsDtoResponse> goodsDtoResponseList) {

        if (CollectionUtil.isEmpty(goodsDtoResponseList)) {
            return new ArrayList<>();
        }

        List<GoodsDto> goodsDtoList = new ArrayList<>();

        for (GoodsDtoResponse goodsDtoResponse : goodsDtoResponseList) {
            GoodsDto goodsDto = new GoodsDto();
            goodsDto.setGoodsEntity(toGoodsEntity(goodsDtoResponse.getGoodsEntity()));
            goodsDto.setStockDto(toStockDto(goodsDtoResponse.getStockDto()));
            goodsDto.setDeleteFlg(goodsDtoResponse.getDeleteFlg());
            goodsDto.setStockStatusPc(EnumTypeUtil.getEnumFromValue(HTypeStockStatusType.class,
                                                                    goodsDtoResponse.getStockStatus()
                                                                   ));
            goodsDto.setUnitImage(toGoodsImageEntity(goodsDtoResponse.getUnitImage()));

            goodsDtoList.add(goodsDto);
        }

        return goodsDtoList;
    }

    /**
     * 商品規格画像登録リストに変換
     *
     * @param goodsImageEntityResponse 商品グループ画像レスポンス
     * @return 商品規格画像登録リスト
     */
    public GoodsImageEntity toGoodsImageEntity(jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsImageEntityResponse goodsImageEntityResponse) {

        if (goodsImageEntityResponse == null) {
            return null;
        }

        GoodsImageEntity goodsImageEntity = new GoodsImageEntity();
        goodsImageEntity.setGoodsGroupSeq(goodsImageEntityResponse.getGoodsGroupSeq());
        goodsImageEntity.setGoodsSeq(goodsImageEntityResponse.getGoodsSeq());
        goodsImageEntity.setImageFileName(goodsImageEntityResponse.getImageFileName());
        goodsImageEntity.setDisplayFlag(EnumTypeUtil.getEnumFromValue(HTypeDisplayStatus.class,
                                                                      goodsImageEntityResponse.getDisplayFlag()
                                                                     ));
        goodsImageEntity.setRegistTime(conversionUtility.toTimeStamp(goodsImageEntityResponse.getRegistTime()));
        goodsImageEntity.setUpdateTime(conversionUtility.toTimeStamp(goodsImageEntityResponse.getUpdateTime()));
        goodsImageEntity.setTmpFilePath(goodsImageEntityResponse.getTmpFilePath());

        return goodsImageEntity;
    }

    /**
     * 商品クラスに変換
     *
     * @param goodsEntityResponse 商品レスポンス
     * @return 商品クラス
     */
    public GoodsEntity toGoodsEntity(GoodsEntityResponse goodsEntityResponse) {

        if (goodsEntityResponse == null) {
            return null;
        }

        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGoodsSeq(goodsEntityResponse.getGoodsSeq());
        goodsEntity.setGoodsGroupSeq(goodsEntityResponse.getGoodsGroupSeq());
        goodsEntity.setGoodsCode(goodsEntityResponse.getGoodsCode());
        goodsEntity.setJanCode(goodsEntityResponse.getJanCode());
        goodsEntity.setCatalogCode(goodsEntityResponse.getCatalogCode());
        goodsEntity.setSaleStatusPC(
                        EnumTypeUtil.getEnumFromValue(HTypeGoodsSaleStatus.class, goodsEntityResponse.getSaleStatus()));
        goodsEntity.setSaleStartTimePC(conversionUtility.toTimeStamp(goodsEntityResponse.getSaleStartTime()));
        goodsEntity.setSaleEndTimePC(conversionUtility.toTimeStamp(goodsEntityResponse.getSaleEndTime()));
        goodsEntity.setGoodsPrice(goodsEntityResponse.getGoodsPrice());
        goodsEntity.setPreDiscountPrice(goodsEntityResponse.getPreDiscountPrice());
        goodsEntity.setIndividualDeliveryType(EnumTypeUtil.getEnumFromValue(HTypeIndividualDeliveryType.class,
                                                                            goodsEntityResponse.getIndividualDeliveryType()
                                                                           ));
        goodsEntity.setFreeDeliveryFlag(EnumTypeUtil.getEnumFromValue(HTypeFreeDeliveryFlag.class,
                                                                      goodsEntityResponse.getFreeDeliveryFlag()
                                                                     ));
        goodsEntity.setUnitManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class,
                                                                        goodsEntityResponse.getUnitManagementFlag()
                                                                       ));
        goodsEntity.setUnitValue1(goodsEntityResponse.getUnitValue1());
        goodsEntity.setUnitValue2(goodsEntityResponse.getUnitValue2());
        goodsEntity.setStockManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeStockManagementFlag.class,
                                                                         goodsEntityResponse.getStockManagementFlag()
                                                                        ));
        goodsEntity.setPurchasedMax(goodsEntityResponse.getPurchasedMax());
        goodsEntity.setOrderDisplay(goodsEntityResponse.getOrderDisplay());
        goodsEntity.setShopSeq(1001);
        goodsEntity.setVersionNo(goodsEntityResponse.getVersionNo());
        goodsEntity.setRegistTime(conversionUtility.toTimeStamp(goodsEntityResponse.getRegistTime()));
        goodsEntity.setUpdateTime(conversionUtility.toTimeStamp(goodsEntityResponse.getUpdateTime()));
        goodsEntity.setReserveFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeReserveFlag.class, goodsEntityResponse.getReserveFlag()));
        goodsEntity.setUnit(goodsEntityResponse.getUnit());
        goodsEntity.setPriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypePriceMarkDispFlag.class,
                                                                       goodsEntityResponse.getPriceMarkDispFlag()
                                                                      ));
        goodsEntity.setSalePriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceMarkDispFlag.class,
                                                                           goodsEntityResponse.getSalePriceMarkDispFlag()
                                                                          ));
        goodsEntity.setGoodsManagementCode(goodsEntityResponse.getGoodsManagementCode());
        goodsEntity.setGoodsDivisionCode(goodsEntityResponse.getGoodsDivisionCode());
        goodsEntity.setGoodsCategory1(goodsEntityResponse.getGoodsCategory1());
        goodsEntity.setGoodsCategory2(goodsEntityResponse.getGoodsCategory2());
        goodsEntity.setGoodsCategory3(goodsEntityResponse.getGoodsCategory3());
        goodsEntity.setLandSendFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeLandSendFlag.class, goodsEntityResponse.getLandSendFlag()));
        goodsEntity.setCoolSendFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeCoolSendFlag.class, goodsEntityResponse.getCoolSendFlag()));
        goodsEntity.setCoolSendFrom(goodsEntityResponse.getCoolSendFrom());
        goodsEntity.setCoolSendTo(goodsEntityResponse.getCoolSendTo());
        goodsEntity.setSalePriceIntegrityFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceIntegrityFlag.class,
                                                                            goodsEntityResponse.getSalePriceIntegrityFlag()
                                                                           ));
        goodsEntity.setEmotionPriceType(EnumTypeUtil.getEnumFromValue(HTypeEmotionPriceType.class,
                                                                      goodsEntityResponse.getEmotionPriceType()
                                                                     ));

        return goodsEntity;
    }

    /**
     * 在庫Dtoクラスに変換
     *
     * @param stockDtoResponse 在庫Dtoレスポンス
     * @return 在庫Dtoクラス
     */
    public StockDto toStockDto(StockDtoResponse stockDtoResponse) {

        if (stockDtoResponse == null) {
            return null;
        }

        StockDto stockDto = new StockDto();
        stockDto.setGoodsSeq(stockDtoResponse.getGoodsSeq());
        stockDto.setShopSeq(1001);
        stockDto.setSalesPossibleStock(stockDtoResponse.getSalesPossibleStock());
        stockDto.setRealStock(stockDtoResponse.getRealStock());
        stockDto.setOrderReserveStock(stockDtoResponse.getOrderReserveStock());
        stockDto.setRemainderFewStock(stockDtoResponse.getRemainderFewStock());
        stockDto.setSupplementCount(stockDtoResponse.getSupplementCount());
        stockDto.setOrderPointStock(stockDtoResponse.getOrderPointStock());
        stockDto.setSafetyStock(stockDtoResponse.getSafetyStock());
        stockDto.setRegistTime(conversionUtility.toTimeStamp(stockDtoResponse.getRegistTime()));
        stockDto.setUpdateTime(conversionUtility.toTimeStamp(stockDtoResponse.getUpdateTime()));

        return stockDto;
    }

    /**
     * カテゴリ登録商品エンティティリストに変換
     *
     * @param categoryGoodsEntityResponseList カテゴリ登録商品エンティティリストレスポンス
     * @return カテゴリ登録商品エンティティリスト
     */
    public List<CategoryGoodsEntity> toCategoryGoodsEntityList(List<CategoryGoodsEntityResponse> categoryGoodsEntityResponseList) {

        if (CollectionUtil.isEmpty(categoryGoodsEntityResponseList)) {
            return new ArrayList<>();
        }

        List<CategoryGoodsEntity> categoryGoodsEntityList = new ArrayList<>();

        for (CategoryGoodsEntityResponse categoryGoodsEntityResponse : categoryGoodsEntityResponseList) {
            CategoryGoodsEntity categoryGoodsEntity = new CategoryGoodsEntity();
            categoryGoodsEntity.setCategorySeq(categoryGoodsEntityResponse.getCategorySeq());
            categoryGoodsEntity.setGoodsGroupSeq(categoryGoodsEntityResponse.getGoodsGroupSeq());
            categoryGoodsEntity.setOrderDisplay(categoryGoodsEntityResponse.getOrderDisplay());
            categoryGoodsEntity.setRegistTime(
                            conversionUtility.toTimeStamp(categoryGoodsEntityResponse.getRegistTime()));
            categoryGoodsEntity.setUpdateTime(
                            conversionUtility.toTimeStamp(categoryGoodsEntityResponse.getUpdateTime()));

            categoryGoodsEntityList.add(categoryGoodsEntity);
        }

        return categoryGoodsEntityList;
    }

    /**
     * お気に入り商品在庫状況取得リクエストに変換
     *
     * @param favoriteDtoList お気に入りDTOリスト
     * @return お気に入り商品在庫状況取得リクエスト
     */
    public FavoriteGoodsStockStatusGetRequest toFavoriteGoodsStockStatusGetRequest(List<FavoriteDto> favoriteDtoList) {

        if (CollectionUtil.isEmpty(favoriteDtoList)) {
            return null;
        }

        FavoriteGoodsStockStatusGetRequest favoriteGoodsStockStatusGetRequest =
                        new FavoriteGoodsStockStatusGetRequest();
        List<FavoriteDtoRequest> favoriteDtoListRequest = new ArrayList<>();

        for (FavoriteDto favoriteDto : favoriteDtoList) {
            FavoriteDtoRequest favoriteDtoRequest = new FavoriteDtoRequest();

            favoriteDtoRequest.setFavoriteEntityRequest(toFavoriteEntityRequest(favoriteDto.getFavoriteEntity()));
            favoriteDtoRequest.setGoodsDetailsDtoRequest(
                            toGoodsDetailsDtoRequestMember(favoriteDto.getGoodsDetailsDto()));
            favoriteDtoRequest.setGoodsGroupImageEntityListRequest(
                            toGoodsGroupImageEntityListRequest(favoriteDto.getGoodsGroupImageEntityList()));
            favoriteDtoRequest.setStockStatus(favoriteDto.getStockStatus());

            favoriteDtoListRequest.add(favoriteDtoRequest);
        }

        favoriteGoodsStockStatusGetRequest.setFavoriteDtoListRequest(favoriteDtoListRequest);

        return favoriteGoodsStockStatusGetRequest;
    }

    /**
     * お気に入りクラスリクエストに変換
     *
     * @param favoriteEntity お気に入りクラス
     * @return お気に入りクラスリクエスト
     */
    public FavoriteEntityRequest toFavoriteEntityRequest(FavoriteEntity favoriteEntity) {

        if (favoriteEntity == null) {
            return null;
        }

        FavoriteEntityRequest favoriteEntityRequest = new FavoriteEntityRequest();
        favoriteEntityRequest.setMemberInfoSeq(favoriteEntity.getMemberInfoSeq());
        favoriteEntityRequest.setGoodsSeq(favoriteEntity.getGoodsSeq());
        favoriteEntityRequest.setRegistTime(conversionUtility.toTimeStamp(favoriteEntity.getRegistTime()));
        favoriteEntityRequest.setUpdateTime(conversionUtility.toTimeStamp(favoriteEntity.getUpdateTime()));

        return favoriteEntityRequest;
    }

    /**
     * 商品詳細Dtoクラスリクエストに変換
     *
     * @param goodsDetailsDto 商品詳細Dtoクラス
     * @return 商品詳細Dtoクラスリクエスト
     */
    public GoodsDetailsDtoRequest toGoodsDetailsDtoRequestMember(GoodsDetailsDto goodsDetailsDto) {

        if (goodsDetailsDto == null) {
            return null;
        }

        GoodsDetailsDtoRequest goodsDetailsDtoRequest = new GoodsDetailsDtoRequest();
        goodsDetailsDtoRequest.setGoodsSeq(goodsDetailsDto.getGoodsSeq());
        goodsDetailsDtoRequest.setGoodsGroupSeq(goodsDetailsDto.getGoodsGroupSeq());
        goodsDetailsDtoRequest.setVersionNo(goodsDetailsDto.getVersionNo());
        goodsDetailsDtoRequest.setRegistTime(goodsDetailsDto.getRegistTime());
        goodsDetailsDtoRequest.setUpdateTime(goodsDetailsDto.getUpdateTime());
        goodsDetailsDtoRequest.setGoodsCode(goodsDetailsDto.getGoodsCode());
        goodsDetailsDtoRequest.setGoodsGroupMaxPrice(goodsDetailsDto.getGoodsGroupMaxPrice());
        goodsDetailsDtoRequest.setGoodsGroupMinPrice(goodsDetailsDto.getGoodsGroupMinPrice());
        goodsDetailsDtoRequest.setPreDiscountMinPrice(goodsDetailsDto.getPreDiscountMinPrice());
        goodsDetailsDtoRequest.setPreDiscountMaxPrice(goodsDetailsDto.getPreDiscountMaxPrice());
        if (goodsDetailsDto.getGoodsTaxType() != null) {
            goodsDetailsDtoRequest.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType().getValue());
        }
        goodsDetailsDtoRequest.setTaxRate(goodsDetailsDto.getTaxRate());
        if (goodsDetailsDto.getAlcoholFlag() != null) {
            goodsDetailsDtoRequest.setAlcoholFlag(goodsDetailsDto.getAlcoholFlag().getValue());
        }
        goodsDetailsDtoRequest.setGoodsPriceInTax(goodsDetailsDto.getGoodsPriceInTax());
        goodsDetailsDtoRequest.setGoodsPrice(goodsDetailsDto.getGoodsPrice());
        goodsDetailsDtoRequest.setDeliveryType(goodsDetailsDto.getDeliveryType());
        if (goodsDetailsDto.getSaleStatusPC() != null) {
            goodsDetailsDtoRequest.setSaleStatus(goodsDetailsDto.getSaleStatusPC().getValue());
        }
        goodsDetailsDtoRequest.setSaleStartTime(goodsDetailsDto.getSaleStartTimePC());
        goodsDetailsDtoRequest.setSaleEndTime(goodsDetailsDto.getSaleEndTimePC());
        if (goodsDetailsDto.getUnitManagementFlag() != null) {
            goodsDetailsDtoRequest.setUnitManagementFlag(goodsDetailsDto.getUnitManagementFlag().getValue());
        }
        if (goodsDetailsDto.getStockManagementFlag() != null) {
            goodsDetailsDtoRequest.setStockManagementFlag(goodsDetailsDto.getStockManagementFlag().getValue());
        }
        if (goodsDetailsDto.getIndividualDeliveryType() != null) {
            goodsDetailsDtoRequest.setIndividualDeliveryType(goodsDetailsDto.getIndividualDeliveryType().getValue());
        }
        goodsDetailsDtoRequest.setPurchasedMax(goodsDetailsDto.getPurchasedMax());
        if (goodsDetailsDto.getFreeDeliveryFlag() != null) {
            goodsDetailsDtoRequest.setFreeDeliveryFlag(goodsDetailsDto.getFreeDeliveryFlag().getValue());
        }
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
        goodsDetailsDtoRequest.setWhatsnewDate(goodsDetailsDto.getWhatsnewDate());
        if (goodsDetailsDto.getGoodsOpenStatusPC() != null) {
            goodsDetailsDtoRequest.setGoodsOpenStatus(goodsDetailsDto.getGoodsOpenStatusPC().getValue());
        }
        goodsDetailsDtoRequest.setOpenStartTime(goodsDetailsDto.getOpenStartTimePC());
        goodsDetailsDtoRequest.setOpenEndTime(goodsDetailsDto.getOpenEndTimePC());
        goodsDetailsDtoRequest.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
        goodsDetailsDtoRequest.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
        goodsDetailsDtoRequest.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
        goodsDetailsDtoRequest.setGoodsPreDiscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());
        goodsDetailsDtoRequest.setGoodsGroupImageEntityList(
                        toGoodsGroupImageEntityRequestMember(goodsDetailsDto.getGoodsGroupImageEntityList()));
        if (goodsDetailsDto.getSnsLinkFlag() != null) {
            goodsDetailsDtoRequest.setSnsLinkFlag(goodsDetailsDto.getSnsLinkFlag().getValue());
        }
        goodsDetailsDtoRequest.setMetaDescription(goodsDetailsDto.getMetaDescription());
        if (goodsDetailsDto.getStockStatusPc() != null) {
            goodsDetailsDtoRequest.setStockStatus(goodsDetailsDto.getStockStatusPc().getValue());
        }
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
        goodsDetailsDtoRequest.setUnitImage(toGoodsImageEntityRequestMember(goodsDetailsDto.getUnitImage()));
        goodsDetailsDtoRequest.setGoodsOptionDisplayName(goodsDetailsDto.getGoodsOptionDisplayName());
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
        // 2023-renew No92 from here
        if (goodsDetailsDto.getOutletIconFlag() != null) {
            goodsDetailsDtoRequest.setOutletIconFlag(goodsDetailsDto.getOutletIconFlag().getValue());
        }
        // 2023-renew No92 to here
        if (goodsDetailsDto.getLandSendFlag() != null) {
            goodsDetailsDtoRequest.setLandSendFlag(goodsDetailsDto.getLandSendFlag().getValue());
        }
        if (goodsDetailsDto.getCoolSendFlag() != null) {
            goodsDetailsDtoRequest.setCoolSendFlag(goodsDetailsDto.getCoolSendFlag().getValue());
        }
        goodsDetailsDtoRequest.setCoolSendFrom(goodsDetailsDto.getCoolSendFrom());
        goodsDetailsDtoRequest.setCoolSendTo(goodsDetailsDto.getCoolSendTo());
        goodsDetailsDtoRequest.setTax(goodsDetailsDto.getTax());
        goodsDetailsDtoRequest.setUnit(goodsDetailsDto.getUnit());
        goodsDetailsDtoRequest.setGoodsManagementCode(goodsDetailsDto.getGoodsManagementCode());
        goodsDetailsDtoRequest.setGoodsDivisionCode(goodsDetailsDto.getGoodsDivisionCode());
        goodsDetailsDtoRequest.setGoodsCategory1(goodsDetailsDto.getGoodsCategory1());
        goodsDetailsDtoRequest.setGoodsCategory2(goodsDetailsDto.getGoodsCategory2());
        goodsDetailsDtoRequest.setGoodsCategory3(goodsDetailsDto.getGoodsCategory3());
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
        goodsDetailsDtoRequest.setSaleYesNo(goodsDetailsDto.getSaleYesNo());
        goodsDetailsDtoRequest.setSaleNgMessage(goodsDetailsDto.getSaleNgMessage());
        goodsDetailsDtoRequest.setDeliveryYesNo(goodsDetailsDto.getDeliveryYesNo());
        if (goodsDetailsDto.getEmotionPriceType() != null) {
            goodsDetailsDtoRequest.setEmotionPriceType(goodsDetailsDto.getEmotionPriceType().getValue());
        }

        // 2023-renew AddNo5 from here
        goodsDetailsDtoRequest.setGoodsPriceInTaxHight(goodsDetailsDto.getGoodsPriceInTaxHight());
        goodsDetailsDtoRequest.setGoodsPriceInTaxLow(goodsDetailsDto.getGoodsPriceInTaxLow());
        goodsDetailsDtoRequest.setPreDiscountPriceHight(goodsDetailsDto.getPreDiscountPriceHight());
        goodsDetailsDtoRequest.setPreDiscountPriceLow(goodsDetailsDto.getPreDiscountPriceLow());
        // 2023-renew AddNo5 to here
        return goodsDetailsDtoRequest;
    }

    /**
     * 商品グループ画像エンティティリストリクエストに変換
     *
     * @param goodsGroupImageEntityList お気に入りDtoクラスリスト
     * @return 商品グループ画像エンティティリストリクエスト
     */
    public List<GoodsGroupImageEntityRequest> toGoodsGroupImageEntityRequestMember(List<GoodsGroupImageEntity> goodsGroupImageEntityList) {

        if (CollectionUtil.isEmpty(goodsGroupImageEntityList)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntityRequest> goodsGroupImageEntityRequestList = new ArrayList<>();

        for (GoodsGroupImageEntity goodsGroupImageEntity : goodsGroupImageEntityList) {
            GoodsGroupImageEntityRequest goodsGroupImageEntityRequest = new GoodsGroupImageEntityRequest();
            goodsGroupImageEntityRequest.setGoodsGroupSeq(goodsGroupImageEntity.getGoodsGroupSeq());
            goodsGroupImageEntityRequest.setImageTypeVersionNo(goodsGroupImageEntity.getImageTypeVersionNo());
            goodsGroupImageEntityRequest.setImageFileName(goodsGroupImageEntity.getImageFileName());
            goodsGroupImageEntityRequest.setRegistTime(goodsGroupImageEntity.getRegistTime());
            goodsGroupImageEntityRequest.setUpdateTime(goodsGroupImageEntity.getUpdateTime());

            goodsGroupImageEntityRequestList.add(goodsGroupImageEntityRequest);
        }

        return goodsGroupImageEntityRequestList;
    }

    /**
     * 商品グループ画像リクエストに変換
     *
     * @param goodsImageEntity 商品規格画像登録リスト
     * @return 商品グループ画像リクエスト
     */
    public GoodsImageEntityRequest toGoodsImageEntityRequestMember(GoodsImageEntity goodsImageEntity) {

        if (goodsImageEntity == null) {
            return null;
        }

        GoodsImageEntityRequest goodsImageEntityRequest = new GoodsImageEntityRequest();
        goodsImageEntityRequest.setGoodsGroupSeq(goodsImageEntity.getGoodsGroupSeq());
        goodsImageEntityRequest.setGoodsSeq(goodsImageEntity.getGoodsSeq());
        goodsImageEntityRequest.setImageFileName(goodsImageEntity.getImageFileName());
        if (goodsImageEntity.getDisplayFlag() != null) {
            goodsImageEntityRequest.setDisplayFlag(goodsImageEntity.getDisplayFlag().getValue());
        }
        goodsImageEntityRequest.setRegistTime(goodsImageEntity.getRegistTime());
        goodsImageEntityRequest.setUpdateTime(goodsImageEntity.getUpdateTime());
        goodsImageEntityRequest.setTmpFilePath(goodsImageEntity.getTmpFilePath());

        return goodsImageEntityRequest;
    }

    /**
     * 商品グループ画像エンティティリストリクエストに変換
     *
     * @param goodsGroupImageEntityList 商品グループ画像エンティティリスト
     * @return 商品グループ画像エンティティリストリクエスト
     */
    public List<GoodsGroupImageEntityRequest> toGoodsGroupImageEntityListRequest(List<GoodsGroupImageEntity> goodsGroupImageEntityList) {

        if (CollectionUtil.isEmpty(goodsGroupImageEntityList)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntityRequest> goodsGroupImageEntityListRequest = new ArrayList<>();

        for (GoodsGroupImageEntity goodsGroupImageEntity : goodsGroupImageEntityList) {
            GoodsGroupImageEntityRequest goodsGroupImageEntityRequest = new GoodsGroupImageEntityRequest();
            goodsGroupImageEntityRequest.setGoodsGroupSeq(goodsGroupImageEntity.getGoodsGroupSeq());
            goodsGroupImageEntityRequest.setImageTypeVersionNo(goodsGroupImageEntity.getImageTypeVersionNo());
            goodsGroupImageEntityRequest.setImageFileName(goodsGroupImageEntity.getImageFileName());
            goodsGroupImageEntityRequest.setRegistTime(goodsGroupImageEntity.getRegistTime());
            goodsGroupImageEntityRequest.setUpdateTime(goodsGroupImageEntity.getUpdateTime());

            goodsGroupImageEntityListRequest.add(goodsGroupImageEntityRequest);
        }

        return goodsGroupImageEntityListRequest;
    }

    /**
     * カートチェック用お気に入り情報リスト取得リクエストに変換
     *
     * @param memberInfoSeq 会員SEQ
     * @param cartDto       カートDtoクラス
     * @return カートチェック用お気に入り情報リスト取得リクエスト
     */
    public FavoriteForCartCheckRequest toFavoriteForCartCheckRequest(Integer memberInfoSeq, CartDto cartDto) {

        if (memberInfoSeq == null || memberInfoSeq == 0 || cartDto == null) {
            return null;
        }

        FavoriteForCartCheckRequest favoriteForCartCheckRequest = new FavoriteForCartCheckRequest();
        favoriteForCartCheckRequest.setMemberInfoSeq(memberInfoSeq);
        favoriteForCartCheckRequest.setCartDto(toCartDtoRequestMember(cartDto));

        return favoriteForCartCheckRequest;
    }

    /**
     * カートDtoリクエストに変換
     *
     * @param cartDto カートDtoクラス
     * @return カートDtoリクエスト
     */
    public CartDtoRequest toCartDtoRequestMember(CartDto cartDto) {

        if (cartDto == null) {
            return null;
        }

        CartDtoRequest cartDtoRequest = new CartDtoRequest();
        cartDtoRequest.setGoodsTotalCount(cartDto.getGoodsTotalCount());
        cartDtoRequest.setGoodsTotalPrice(cartDto.getGoodsTotalPrice());
        cartDtoRequest.setGoodsTotalPriceInTax(cartDto.getGoodsTotalPriceInTax());
        cartDtoRequest.setBeforeTransferEmotionGoodsCodeMap(cartDto.getBeforeTransferEmotionGoodsCodeMap());
        cartDtoRequest.setTotalPriceTax(cartDto.getTotalPriceTax());

        cartDtoRequest.setCartGoodsDtoList(toCartGoodsDtoListMember(cartDto.getCartGoodsDtoList()));
        if (cartDto.getSettlementMethodType() != null) {
            cartDtoRequest.setSettlementMethodType(cartDto.getSettlementMethodType().getValue());
        }
        cartDtoRequest.setDiscountsResponseDetailMap(
                        toDiscountsResponseDetailMapMember(cartDto.getDiscountsResponseDetailMap()));
        cartDtoRequest.setConsumptionTaxRateMap(toConsumptionTaxRateMapMember(cartDto.getConsumptionTaxRateMap()));

        return cartDtoRequest;
    }

    /**
     * カート商品Dtoリクエストに変換
     *
     * @param cartGoodsDtoList カート商品Dtoリスト
     * @return カート商品Dtoリクエスト
     */
    private List<CartGoodsDtoRequest> toCartGoodsDtoListMember(List<CartGoodsDto> cartGoodsDtoList) {

        if (CollectionUtil.isEmpty(cartGoodsDtoList)) {
            return new ArrayList<>();
        }

        List<CartGoodsDtoRequest> cartGoodsDtoRequestList = new ArrayList<>();

        cartGoodsDtoList.forEach(cartGoodsDto -> {
            CartGoodsDtoRequest cartGoodsDtoRequest = new CartGoodsDtoRequest();

            cartGoodsDtoRequest.setCartGoodsEntity(toCartGoodsEntityRequestMember(cartGoodsDto.getCartGoodsEntity()));
            cartGoodsDtoRequest.setGoodsDetailsDto(toGoodsDetailsDtoRequestMember(cartGoodsDto.getGoodsDetailsDto()));
            cartGoodsDtoRequest.setGoodsPriceSubtotal(cartGoodsDto.getGoodsPriceSubtotal());
            cartGoodsDtoRequest.setGoodsPriceInTaxSubtotal(cartGoodsDto.getGoodsPriceInTaxSubtotal());

            cartGoodsDtoRequestList.add(cartGoodsDtoRequest);
        });

        return cartGoodsDtoRequestList;
    }

    /**
     * カート商品エンティティリクエストに変換
     *
     * @param cartGoodsEntity カート商品クラス
     * @return カート商品エンティティリクエスト
     */
    public CartGoodsEntityRequest toCartGoodsEntityRequestMember(CartGoodsEntity cartGoodsEntity) {
        if (ObjectUtils.isEmpty(cartGoodsEntity)) {
            return null;
        }

        CartGoodsEntityRequest cartGoodsEntityRequest = new CartGoodsEntityRequest();
        cartGoodsEntityRequest.setCartSeq(cartGoodsEntity.getCartSeq());
        cartGoodsEntityRequest.setGoodsSeq(cartGoodsEntity.getGoodsSeq());
        cartGoodsEntityRequest.setCartGoodsCount(cartGoodsEntity.getCartGoodsCount());
        cartGoodsEntityRequest.setMemberInfoSeq(cartGoodsEntity.getMemberInfoSeq());
        cartGoodsEntityRequest.setAccessUid(cartGoodsEntity.getAccessUid());
        cartGoodsEntityRequest.setRegistTime(cartGoodsEntity.getRegistTime());
        cartGoodsEntityRequest.setUpdateTime(cartGoodsEntity.getUpdateTime());
        // 2023-renew No14 from here
        cartGoodsEntityRequest.setReserveFlag(cartGoodsEntity.getReserveFlag().getValue());
        cartGoodsEntityRequest.setReserveDeliveryDate(
                        conversionUtility.toTimeStamp(cartGoodsEntity.getReserveDeliveryDate()));
        // 2023-renew No14 to here

        return cartGoodsEntityRequest;
    }

    /**
     * 割引適用結果MAPに変換
     *
     * @param discountsResponseDetailMap 割引適用結果MAP
     * @return 割引適用結果MAP
     */
    private Map<String, WebApiGetDiscountsResponseDetailDtoRequest> toDiscountsResponseDetailMapMember(Map<String, WebApiGetDiscountsResponseDetailDto> discountsResponseDetailMap) {

        if (MapUtils.isEmpty(discountsResponseDetailMap)) {
            return new HashMap<>();
        }

        Map<String, WebApiGetDiscountsResponseDetailDtoRequest> discountsResponseDetailDtoRequestMap = new HashMap<>();

        for (Map.Entry<String, WebApiGetDiscountsResponseDetailDto> entry : discountsResponseDetailMap.entrySet()) {

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

            discountsResponseDetailDtoRequestMap.put(entry.getKey(), webApiGetDiscountsResponseDetailDtoRequest);
        }

        return discountsResponseDetailDtoRequestMap;
    }

    /**
     * 消費税率MAPに変換
     *
     * @param consumptionTaxRateMap 消費税率MAP
     * @return 消費税率MAP
     */
    private Map<String, WebApiGetConsumptionTaxRateResponseDetailDtoRequest> toConsumptionTaxRateMapMember(Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> consumptionTaxRateMap) {

        if (MapUtils.isEmpty(consumptionTaxRateMap)) {
            return new HashMap<>();
        }

        Map<String, WebApiGetConsumptionTaxRateResponseDetailDtoRequest> consumptionTaxRateMapRequest = new HashMap<>();

        for (Map.Entry<String, WebApiGetConsumptionTaxRateResponseDetailDto> entry : consumptionTaxRateMap.entrySet()) {

            WebApiGetConsumptionTaxRateResponseDetailDto webApiGetConsumptionTaxRateResponseDetailDto =
                            entry.getValue();
            WebApiGetConsumptionTaxRateResponseDetailDtoRequest webApiGetConsumptionTaxRateResponseDetailDtoRequest =
                            new WebApiGetConsumptionTaxRateResponseDetailDtoRequest();

            webApiGetConsumptionTaxRateResponseDetailDtoRequest.setGoodsCode(
                            webApiGetConsumptionTaxRateResponseDetailDto.getGoodsCode());
            webApiGetConsumptionTaxRateResponseDetailDtoRequest.setTaxRate(
                            webApiGetConsumptionTaxRateResponseDetailDto.getTaxRate());

            consumptionTaxRateMapRequest.put(entry.getKey(), webApiGetConsumptionTaxRateResponseDetailDtoRequest);
        }

        return consumptionTaxRateMapRequest;
    }

    /**
     * お気に入りリストに変換
     *
     * @param favoriteEntityResponseList お気に入りリストレスポンス
     * @return お気に入りリスト
     */
    public List<FavoriteEntity> toFavoriteEntityList(List<FavoriteEntityResponse> favoriteEntityResponseList) {

        if (CollectionUtil.isEmpty(favoriteEntityResponseList)) {
            return new ArrayList<>();
        }

        List<FavoriteEntity> favoriteEntityList = new ArrayList<>();

        favoriteEntityResponseList.forEach(favoriteEntityResponse -> {

            FavoriteEntity favoriteEntity = new FavoriteEntity();

            favoriteEntity.setMemberInfoSeq(favoriteEntityResponse.getMemberInfoSeq());
            favoriteEntity.setGoodsSeq(favoriteEntityResponse.getGoodsSeq());
            favoriteEntity.setRegistTime(conversionUtility.toTimeStamp(favoriteEntityResponse.getRegistTime()));
            favoriteEntity.setUpdateTime(conversionUtility.toTimeStamp(favoriteEntityResponse.getUpdateTime()));

            favoriteEntityList.add(favoriteEntity);
        });

        return favoriteEntityList;
    }

    /**
     * カート商品チェックリクエストに変換
     *
     * @param commonInfo 共通情報
     * @param cartDto    カートDtoクラス
     * @return カート商品チェックリクエスト
     */
    public CartGoodsCheckRequest toCartGoodsCheckRequest(CommonInfo commonInfo, CartDto cartDto) {

        if (cartDto == null) {
            return null;
        }

        CartGoodsCheckRequest cartGoodsCheckRequest = new CartGoodsCheckRequest();
        cartGoodsCheckRequest.setCartDto(toCartDtoRequest(cartDto));

        // 非ログイン状態⇒1、ログイン状態⇒2　
        if (!commonInfoUtility.isLogin(commonInfo)) {
            cartGoodsCheckRequest.setIsLogin(false);
            cartGoodsCheckRequest.setBusinessType(null);
            cartGoodsCheckRequest.setConfDocumentType(null);
        } else {
            cartGoodsCheckRequest.setIsLogin(true);

            MemberInfoEntity memberInfoEntity = commonInfoUtility.getMemberInfoEntity(commonInfo);
            HTypeBusinessType businessType = memberInfoEntity.getBusinessType();
            HTypeConfDocumentType confDocumentType = memberInfoEntity.getConfDocumentType();

            if (businessType != null) {
                cartGoodsCheckRequest.setBusinessType(businessType.getValue());
            }
            if (confDocumentType != null) {
                cartGoodsCheckRequest.setConfDocumentType(confDocumentType.getValue());
            }

            // 2023-renew No14 from here
            cartGoodsCheckRequest.setCustomerNo(memberInfoEntity.getCustomerNo());
            cartGoodsCheckRequest.setZipcode(memberInfoEntity.getMemberInfoZipCode());
            // 2023-renew No14 to here
        }

        cartGoodsCheckRequest.setMemberInfoSeq(commonInfo.getCommonInfoUser().getMemberInfoSeq());

        return cartGoodsCheckRequest;
    }

    /**
     * カートDtoリクエストに変換
     *
     * @param cartDto カートDtoクラス
     * @return カートDtoリクエスト
     */
    public jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartDtoRequest toCartDtoRequest(CartDto cartDto) {

        if (cartDto == null) {
            return null;
        }

        jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartDtoRequest cartDtoRequest =
                        new jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartDtoRequest();
        cartDtoRequest.setGoodsTotalCount(cartDto.getGoodsTotalCount());
        cartDtoRequest.setGoodsTotalPrice(cartDto.getGoodsTotalPrice());
        cartDtoRequest.setGoodsTotalPriceInTax(cartDto.getGoodsTotalPriceInTax());
        cartDtoRequest.setBeforeTransferEmotionGoodsCodeMap(cartDto.getBeforeTransferEmotionGoodsCodeMap());
        cartDtoRequest.setTotalPriceTax(cartDto.getTotalPriceTax());

        cartDtoRequest.setCartGoodsDtoList(toCartGoodsDtoListRequest(cartDto.getCartGoodsDtoList()));
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

    /**
     * 商品詳細Dtoクラスリクエストに変換
     *
     * @param cartGoodsDtoList カート商品Dtoリスト
     * @return 商品詳細Dtoクラスリクエスト
     */
    private List<jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsDtoRequest> toCartGoodsDtoListRequest(List<CartGoodsDto> cartGoodsDtoList) {

        if (CollectionUtil.isEmpty(cartGoodsDtoList)) {
            return new ArrayList<>();
        }

        List<jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsDtoRequest> cartGoodsDtoRequestList =
                        new ArrayList<>();

        cartGoodsDtoList.forEach(cartGoodsDto -> {
            jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsDtoRequest cartGoodsDtoRequest =
                            new jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsDtoRequest();

            cartGoodsDtoRequest.setCartGoodsEntity(toCartGoodsEntityRequest(cartGoodsDto.getCartGoodsEntity()));
            cartGoodsDtoRequest.setGoodsDetailsDto(toGoodsDetailsDtoRequest(cartGoodsDto.getGoodsDetailsDto()));
            cartGoodsDtoRequest.setGoodsPriceSubtotal(cartGoodsDto.getGoodsPriceSubtotal());
            cartGoodsDtoRequest.setGoodsPriceInTaxSubtotal(cartGoodsDto.getGoodsPriceInTaxSubtotal());

            cartGoodsDtoRequestList.add(cartGoodsDtoRequest);
        });

        return cartGoodsDtoRequestList;
    }

    /**
     * カート商品エンティティリクエストに変換
     *
     * @param cartGoodsEntity カート商品クラス
     * @return カート商品エンティティリクエスト
     */
    public jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsEntityRequest toCartGoodsEntityRequest(
                    CartGoodsEntity cartGoodsEntity) {
        if (ObjectUtils.isEmpty(cartGoodsEntity)) {
            return null;
        }

        jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsEntityRequest cartGoodsEntityRequest =
                        new jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsEntityRequest();
        cartGoodsEntityRequest.setCartSeq(cartGoodsEntity.getCartSeq());
        cartGoodsEntityRequest.setGoodsSeq(cartGoodsEntity.getGoodsSeq());
        cartGoodsEntityRequest.setCartGoodsCount(cartGoodsEntity.getCartGoodsCount());
        cartGoodsEntityRequest.setMemberInfoSeq(cartGoodsEntity.getMemberInfoSeq());
        cartGoodsEntityRequest.setAccessUid(cartGoodsEntity.getAccessUid());
        cartGoodsEntityRequest.setRegistTime(cartGoodsEntity.getRegistTime());
        cartGoodsEntityRequest.setUpdateTime(cartGoodsEntity.getUpdateTime());
        // 2023-renew No14 from here
        cartGoodsEntityRequest.setReserveFlag(cartGoodsEntity.getReserveFlag().getValue());
        cartGoodsEntityRequest.setReserveDeliveryDate(
                        conversionUtility.toTimeStamp(cartGoodsEntity.getReserveDeliveryDate()));
        // 2023-renew No14 to here

        return cartGoodsEntityRequest;
    }

    /**
     * 商品詳細Dtoクラスリクエストに変換
     *
     * @param goodsDetailsDto 商品詳細Dtoクラス
     * @return 商品詳細Dtoクラスリクエスト
     */
    public jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsDetailsDtoRequest toGoodsDetailsDtoRequest(
                    GoodsDetailsDto goodsDetailsDto) {

        if (goodsDetailsDto == null) {
            return null;
        }

        jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsDetailsDtoRequest goodsDetailsDtoRequest =
                        new jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsDetailsDtoRequest();
        goodsDetailsDtoRequest.setGoodsSeq(goodsDetailsDto.getGoodsSeq());
        goodsDetailsDtoRequest.setGoodsGroupSeq(goodsDetailsDto.getGoodsGroupSeq());
        goodsDetailsDtoRequest.setVersionNo(goodsDetailsDto.getVersionNo());
        goodsDetailsDtoRequest.setRegistTime(goodsDetailsDto.getRegistTime());
        goodsDetailsDtoRequest.setUpdateTime(goodsDetailsDto.getUpdateTime());
        goodsDetailsDtoRequest.setGoodsCode(goodsDetailsDto.getGoodsCode());
        goodsDetailsDtoRequest.setGoodsGroupMaxPrice(goodsDetailsDto.getGoodsGroupMaxPrice());
        goodsDetailsDtoRequest.setGoodsGroupMinPrice(goodsDetailsDto.getGoodsGroupMinPrice());
        goodsDetailsDtoRequest.setPreDiscountMinPrice(goodsDetailsDto.getPreDiscountMinPrice());
        goodsDetailsDtoRequest.setPreDiscountMaxPrice(goodsDetailsDto.getPreDiscountMaxPrice());
        if (goodsDetailsDto.getGoodsTaxType() != null) {
            goodsDetailsDtoRequest.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType().getValue());
        }
        goodsDetailsDtoRequest.setTaxRate(goodsDetailsDto.getTaxRate());
        if (goodsDetailsDto.getAlcoholFlag() != null) {
            goodsDetailsDtoRequest.setAlcoholFlag(goodsDetailsDto.getAlcoholFlag().getValue());
        }
        goodsDetailsDtoRequest.setGoodsPriceInTax(goodsDetailsDto.getGoodsPriceInTax());
        goodsDetailsDtoRequest.setGoodsPrice(goodsDetailsDto.getGoodsPrice());
        goodsDetailsDtoRequest.setDeliveryType(goodsDetailsDto.getDeliveryType());
        if (goodsDetailsDto.getSaleStatusPC() != null) {
            goodsDetailsDtoRequest.setSaleStatus(goodsDetailsDto.getSaleStatusPC().getValue());
        }
        goodsDetailsDtoRequest.setSaleStartTime(goodsDetailsDto.getSaleStartTimePC());
        goodsDetailsDtoRequest.setSaleEndTime(goodsDetailsDto.getSaleEndTimePC());
        if (goodsDetailsDto.getUnitManagementFlag() != null) {
            goodsDetailsDtoRequest.setUnitManagementFlag(goodsDetailsDto.getUnitManagementFlag().getValue());
        }
        if (goodsDetailsDto.getStockManagementFlag() != null) {
            goodsDetailsDtoRequest.setStockManagementFlag(goodsDetailsDto.getStockManagementFlag().getValue());
        }
        if (goodsDetailsDto.getIndividualDeliveryType() != null) {
            goodsDetailsDtoRequest.setIndividualDeliveryType(goodsDetailsDto.getIndividualDeliveryType().getValue());
        }
        goodsDetailsDtoRequest.setPurchasedMax(goodsDetailsDto.getPurchasedMax());
        if (goodsDetailsDto.getFreeDeliveryFlag() != null) {
            goodsDetailsDtoRequest.setFreeDeliveryFlag(goodsDetailsDto.getFreeDeliveryFlag().getValue());
        }
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
        goodsDetailsDtoRequest.setWhatsnewDate(goodsDetailsDto.getWhatsnewDate());
        if (goodsDetailsDto.getGoodsOpenStatusPC() != null) {
            goodsDetailsDtoRequest.setGoodsOpenStatus(goodsDetailsDto.getGoodsOpenStatusPC().getValue());
        }
        goodsDetailsDtoRequest.setOpenStartTime(goodsDetailsDto.getOpenStartTimePC());
        goodsDetailsDtoRequest.setOpenEndTime(goodsDetailsDto.getOpenEndTimePC());
        goodsDetailsDtoRequest.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
        goodsDetailsDtoRequest.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
        goodsDetailsDtoRequest.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
        goodsDetailsDtoRequest.setGoodsPreDiscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());
        goodsDetailsDtoRequest.setGoodsGroupImageEntityList(
                        toGoodsGroupImageEntityRequest(goodsDetailsDto.getGoodsGroupImageEntityList()));
        if (goodsDetailsDto.getSnsLinkFlag() != null) {
            goodsDetailsDtoRequest.setSnsLinkFlag(goodsDetailsDto.getSnsLinkFlag().getValue());
        }
        goodsDetailsDtoRequest.setMetaDescription(goodsDetailsDto.getMetaDescription());
        if (goodsDetailsDto.getStockStatusPc() != null) {
            goodsDetailsDtoRequest.setStockStatus(goodsDetailsDto.getStockStatusPc().getValue());
        }
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
        goodsDetailsDtoRequest.setUnitImage(toGoodsImageEntityRequest(goodsDetailsDto.getUnitImage()));
        goodsDetailsDtoRequest.setGoodsOptionDisplayName(goodsDetailsDto.getGoodsOptionDisplayName());
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
        goodsDetailsDtoRequest.setCoolSendFrom(goodsDetailsDto.getCoolSendFrom());
        goodsDetailsDtoRequest.setCoolSendTo(goodsDetailsDto.getCoolSendTo());
        goodsDetailsDtoRequest.setTax(goodsDetailsDto.getTax());
        goodsDetailsDtoRequest.setUnit(goodsDetailsDto.getUnit());
        goodsDetailsDtoRequest.setGoodsManagementCode(goodsDetailsDto.getGoodsManagementCode());
        goodsDetailsDtoRequest.setGoodsDivisionCode(goodsDetailsDto.getGoodsDivisionCode());
        goodsDetailsDtoRequest.setGoodsCategory1(goodsDetailsDto.getGoodsCategory1());
        goodsDetailsDtoRequest.setGoodsCategory2(goodsDetailsDto.getGoodsCategory2());
        goodsDetailsDtoRequest.setGoodsCategory3(goodsDetailsDto.getGoodsCategory3());
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
        goodsDetailsDtoRequest.setSaleYesNo(goodsDetailsDto.getSaleYesNo());
        goodsDetailsDtoRequest.setSaleNgMessage(goodsDetailsDto.getSaleNgMessage());
        goodsDetailsDtoRequest.setDeliveryYesNo(goodsDetailsDto.getDeliveryYesNo());
        if (goodsDetailsDto.getEmotionPriceType() != null) {
            goodsDetailsDtoRequest.setEmotionPriceType(goodsDetailsDto.getEmotionPriceType().getValue());
        }

        return goodsDetailsDtoRequest;
    }

    /**
     * 商品グループ画像エンティティリストリクエストに変換
     *
     * @param goodsGroupImageEntityList お気に入りDtoクラスリスト
     * @return 商品グループ画像エンティティリストリクエスト
     */
    public List<jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsGroupImageEntityRequest> toGoodsGroupImageEntityRequest(
                    List<GoodsGroupImageEntity> goodsGroupImageEntityList) {

        if (CollectionUtil.isEmpty(goodsGroupImageEntityList)) {
            return new ArrayList<>();
        }

        List<jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsGroupImageEntityRequest>
                        goodsGroupImageEntityRequestList = new ArrayList<>();

        for (GoodsGroupImageEntity goodsGroupImageEntity : goodsGroupImageEntityList) {
            jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsGroupImageEntityRequest goodsGroupImageEntityRequest =
                            new jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsGroupImageEntityRequest();
            goodsGroupImageEntityRequest.setGoodsGroupSeq(goodsGroupImageEntity.getGoodsGroupSeq());
            goodsGroupImageEntityRequest.setImageTypeVersionNo(goodsGroupImageEntity.getImageTypeVersionNo());
            goodsGroupImageEntityRequest.setImageFileName(goodsGroupImageEntity.getImageFileName());
            goodsGroupImageEntityRequest.setRegistTime(goodsGroupImageEntity.getRegistTime());
            goodsGroupImageEntityRequest.setUpdateTime(goodsGroupImageEntity.getUpdateTime());

            goodsGroupImageEntityRequestList.add(goodsGroupImageEntityRequest);
        }

        return goodsGroupImageEntityRequestList;
    }

    /**
     * 商品グループ画像リクエストに変換
     *
     * @param goodsImageEntity 商品規格画像登録リスト
     * @return 商品グループ画像リクエスト
     */
    public jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsImageEntityRequest toGoodsImageEntityRequest(
                    GoodsImageEntity goodsImageEntity) {

        if (goodsImageEntity == null) {
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
        goodsImageEntityRequest.setRegistTime(goodsImageEntity.getRegistTime());
        goodsImageEntityRequest.setUpdateTime(goodsImageEntity.getUpdateTime());
        goodsImageEntityRequest.setTmpFilePath(goodsImageEntity.getTmpFilePath());

        return goodsImageEntityRequest;
    }

    /**
     * 割引適用結果MAPに変換
     *
     * @param discountsResponseDetailMap 割引適用結果MAP
     * @return 割引適用結果MAP
     */
    private Map<String, jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetDiscountsResponseDetailDtoRequest> toDiscountsResponseDetailMap(
                    Map<String, WebApiGetDiscountsResponseDetailDto> discountsResponseDetailMap) {

        if (MapUtils.isEmpty(discountsResponseDetailMap)) {
            return new HashMap<>();
        }

        Map<String, jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetDiscountsResponseDetailDtoRequest>
                        discountsResponseDetailDtoRequestMap = new HashMap<>();

        for (Map.Entry<String, WebApiGetDiscountsResponseDetailDto> entry : discountsResponseDetailMap.entrySet()) {

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

            discountsResponseDetailDtoRequestMap.put(entry.getKey(), webApiGetDiscountsResponseDetailDtoRequest);
        }

        return discountsResponseDetailDtoRequestMap;
    }

    /**
     * 消費税率MAPに変換
     *
     * @param consumptionTaxRateMap 消費税率MAP
     * @return 消費税率MAP
     */
    private Map<String, jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetConsumptionTaxRateResponseDetailDtoRequest> toConsumptionTaxRateMap(
                    Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> consumptionTaxRateMap) {

        if (MapUtils.isEmpty(consumptionTaxRateMap)) {
            return new HashMap<>();
        }

        Map<String, jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetConsumptionTaxRateResponseDetailDtoRequest>
                        consumptionTaxRateMapRequest = new HashMap<>();

        for (Map.Entry<String, WebApiGetConsumptionTaxRateResponseDetailDto> entry : consumptionTaxRateMap.entrySet()) {

            WebApiGetConsumptionTaxRateResponseDetailDto webApiGetConsumptionTaxRateResponseDetailDto =
                            entry.getValue();
            jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetConsumptionTaxRateResponseDetailDtoRequest
                            webApiGetConsumptionTaxRateResponseDetailDtoRequest =
                            new jp.co.hankyuhanshin.itec.hitmall.api.cart.param.WebApiGetConsumptionTaxRateResponseDetailDtoRequest();

            webApiGetConsumptionTaxRateResponseDetailDtoRequest.setGoodsCode(
                            webApiGetConsumptionTaxRateResponseDetailDto.getGoodsCode());
            webApiGetConsumptionTaxRateResponseDetailDtoRequest.setTaxRate(
                            webApiGetConsumptionTaxRateResponseDetailDto.getTaxRate());

            consumptionTaxRateMapRequest.put(entry.getKey(), webApiGetConsumptionTaxRateResponseDetailDtoRequest);
        }

        return consumptionTaxRateMapRequest;
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
            return new HashMap<>();
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
     * 取りおき情報MAPリクエストに変換
     *
     * @param reserveMap 取りおき情報MAP
     * @return 取りおき情報MAPリクエスト
     */
    private Map<String, WebApiGetReserveResponseDetailDtoRequest> toWebApiGetReserveResponseDetailDtoRequestMap(Map<String, WebApiGetReserveResponseDetailDto> reserveMap) {

        if (MapUtils.isEmpty(reserveMap)) {
            return new HashMap<>();
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
     * 販売可否判定MAPリクエストに変換
     *
     * @param saleCheckMap 販売可否判定MAP
     * @return 販売可否判定MAPリクエスト
     */
    private Map<String, WebApiGetSaleCheckDetailResponse> toWebApiGetSaleCheckDetailResponseMap(Map<String, WebApiGetSaleCheckResponseDetailDto> saleCheckMap) {

        if (MapUtils.isEmpty(saleCheckMap)) {
            return new HashMap<>();
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
     * チェックメッセージDTOリストマップに変換
     *
     * @param messages チェックメッセージDtoクラスレスポンス
     * @return チェックメッセージDTOリストマップ
     */
    public Map<Integer, List<CheckMessageDto>> toCheckMessageDtoListMap(Map<String, List<CheckMessageDtoResponse>> messages) {

        if (MapUtils.isEmpty(messages)) {
            return new HashMap<>();
        }

        Map<Integer, List<CheckMessageDto>> checkMessageDtoListMap = new HashMap<>();

        for (Map.Entry<String, List<CheckMessageDtoResponse>> entry : messages.entrySet()) {

            List<CheckMessageDtoResponse> checkMessageDtoResponseList = entry.getValue();

            List<CheckMessageDto> checkMessageDtoList = new ArrayList<>();

            if (CollectionUtil.isNotEmpty(checkMessageDtoResponseList)) {
                for (CheckMessageDtoResponse checkMessageDtoResponse : checkMessageDtoResponseList) {
                    CheckMessageDto checkMessageDto = new CheckMessageDto();
                    checkMessageDto.setMessageId(checkMessageDtoResponse.getMessageId());
                    checkMessageDto.setArgs(!CollectionUtil.isEmpty(checkMessageDtoResponse.getArgs()) ?
                                                            checkMessageDtoResponse.getArgs().toArray() :
                                                            null);
                    checkMessageDto.setMessage(checkMessageDtoResponse.getMessage());
                    checkMessageDto.setOrderConsecutiveNo(checkMessageDtoResponse.getOrderConsecutiveNo());
                    checkMessageDto.setError(Boolean.TRUE.equals(checkMessageDtoResponse.getError()));
                    checkMessageDtoList.add(checkMessageDto);
                }
            }
            checkMessageDtoListMap.put(conversionUtility.toInteger(entry.getKey()), checkMessageDtoList);
        }

        return checkMessageDtoListMap;
    }

    /**
     * カート情報変更リクエストに変換
     *
     * @param memberInfoSeq    会員SEQ
     * @param accessUid        端末識別番号
     * @param cartGoodsDtoList カート商品Dtoリスト
     * @return カート情報変更リクエスト
     */
    public CartGoodsChangeRequest toCartGoodsChangeRequest(Integer memberInfoSeq,
                                                           String accessUid,
                                                           List<CartGoodsDto> cartGoodsDtoList) {

        CartGoodsChangeRequest cartGoodsChangeRequest = new CartGoodsChangeRequest();
        cartGoodsChangeRequest.setMemberInfoSeq(memberInfoSeq);
        cartGoodsChangeRequest.setAccessUid(accessUid);
        cartGoodsChangeRequest.setCartGoodsDtoList(toCartGoodsDtoListRequest(cartGoodsDtoList));

        return cartGoodsChangeRequest;
    }

    /**
     * カート商品削除リクエストに変換
     *
     * @param memberInfoSeq 会員SEQ
     * @param accessUid     端末識別番号
     * @param cartSeqList   カート商品情報SEQリスト
     * @return カート商品削除リクエスト
     */
    public CartGoodsDeleteRequest toCartGoodsDeleteRequest(Integer memberInfoSeq,
                                                           String accessUid,
                                                           List<Integer> cartSeqList) {

        CartGoodsDeleteRequest cartGoodsDeleteRequest = new CartGoodsDeleteRequest();
        cartGoodsDeleteRequest.setMemberInfoSeq(memberInfoSeq);
        cartGoodsDeleteRequest.setAccessUid(accessUid);
        cartGoodsDeleteRequest.setCartGoodsSeqList(cartSeqList);

        return cartGoodsDeleteRequest;
    }

    /**
     * フリーエリアクラスに変換
     *
     * @param freeAreaEntityResponse フリーエリアEntityレスポンス
     * @return フリーエリアクラス
     */
    public FreeAreaEntity toFreeAreaEntity(FreeAreaEntityResponse freeAreaEntityResponse) {

        if (freeAreaEntityResponse == null) {
            return null;
        }

        FreeAreaEntity freeAreaEntity = new FreeAreaEntity();
        freeAreaEntity.setFreeAreaSeq(freeAreaEntityResponse.getFreeAreaSeq());
        freeAreaEntity.setShopSeq(1001);
        freeAreaEntity.setFreeAreaKey(freeAreaEntityResponse.getFreeAreaKey());
        freeAreaEntity.setOpenStartTime(conversionUtility.toTimeStamp(freeAreaEntityResponse.getOpenStartTime()));
        freeAreaEntity.setFreeAreaTitle(freeAreaEntityResponse.getFreeAreaTitle());
        freeAreaEntity.setFreeAreaBodyPc(freeAreaEntityResponse.getFreeAreaBody());
        freeAreaEntity.setTargetGoods(freeAreaEntityResponse.getFreeAreaBody());
        freeAreaEntity.setRegistTime(conversionUtility.toTimeStamp(freeAreaEntityResponse.getRegistTime()));
        freeAreaEntity.setUpdateTime(conversionUtility.toTimeStamp(freeAreaEntityResponse.getUpdateTime()));
        freeAreaEntity.setFreeAreaOpenStatus(EnumTypeUtil.getEnumFromValue(HTypeFreeAreaOpenStatus.class,
                                                                           freeAreaEntityResponse.getFreeAreaOpenStatus()
                                                                          ));
        freeAreaEntity.setSiteMapFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeSiteMapFlag.class, freeAreaEntityResponse.getSiteMapFlag()));

        return freeAreaEntity;
    }

    /**
     * カート情報取得リクエストに変換
     *
     * @param accessUid     端末識別情報
     * @param memberInfoSeq 会員SEQ
     * @param siteType      サイト区分
     * @param orderField    ソート項目
     * @param orderAsc      ソート条件
     * @param goodsSeq      商品シーケンス（任意）
     * @param reserveFlag   取りおきフラグ（任意）
     * @return カート情報取得リクエスト
     */
    public CartGoodsGetRequest toCartGoodsGetRequest(String accessUid,
                                                     Integer memberInfoSeq,
                                                     String siteType,
                                                     String orderField,
                                                     Boolean orderAsc,
                                                     Integer goodsSeq,
                                                     HTypeReserveDeliveryFlag reserveFlag) {

        CartGoodsGetRequest cartGoodsGetRequest = new CartGoodsGetRequest();

        cartGoodsGetRequest.setAccessUid(accessUid);
        cartGoodsGetRequest.setMemberInfoSeq(memberInfoSeq);
        cartGoodsGetRequest.setSiteType(siteType);
        cartGoodsGetRequest.setOrderField(orderField);
        cartGoodsGetRequest.setOrderAsc(orderAsc);
        // 2023-renew No14 from here
        cartGoodsGetRequest.setGoodsSeq(goodsSeq);
        cartGoodsGetRequest.setReserveFlag(reserveFlag.getValue());
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
                        toDiscountsResponseDetailMapResponse(cartDtoResponse.getDiscountsResponseDetailMap()));
        cartDto.setConsumptionTaxRateMap(toConsumptionTaxRateMapResponse(cartDtoResponse.getConsumptionTaxRateMap()));
        // 2023-renew No14 from here
        cartDto.setQuantityDiscountsResponseDetailMap(toWebApiGetQuantityDiscountResultResponseDetailDtoMap(
                        cartDtoResponse.getQuantityDiscountsResponseDetailMap()));
        cartDto.setReserveMap(toWebApiGetReserveResponseDetailDtoMap(cartDtoResponse.getReserveMap()));
        cartDto.setSaleCheckMap(toWebApiGetSaleCheckResponseDetailDtoMap(cartDtoResponse.getSaleCheckMap()));
        // 2023-renew No14 to here
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
     * 商品詳細DTOに変換
     *
     * @param goodsDetailsDtoResponse 商品詳細Dtoクラスレスポンス
     * @return 商品詳細DTO
     */
    public GoodsDetailsDto toGoodsDetailsDto(jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsDetailsDtoResponse goodsDetailsDtoResponse) {

        if (goodsDetailsDtoResponse == null) {
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
                        toGoodsGroupImageEntityList(goodsDetailsDtoResponse.getGoodsGroupImageEntityList()));
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
        goodsDetailsDto.setUnitImage(toGoodsImageEntity(goodsDetailsDtoResponse.getUnitImage()));
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
     * 商品グループ画像クラスリストに変換
     *
     * @param goodsGroupImageEntityResponseList 商品グループDtoListレスポンス
     * @return 商品グループ画像クラスリスト
     */
    public List<GoodsGroupImageEntity> toGoodsGroupImageEntityList(List<jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsGroupImageEntityResponse> goodsGroupImageEntityResponseList) {

        if (CollectionUtil.isEmpty(goodsGroupImageEntityResponseList)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntity> goodsGroupImageEntityList = new ArrayList<>();

        for (jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsGroupImageEntityResponse goodsGroupImageEntityResponse : goodsGroupImageEntityResponseList) {
            GoodsGroupImageEntity goodsGroupImageEntity = new GoodsGroupImageEntity();
            goodsGroupImageEntity.setGoodsGroupSeq(goodsGroupImageEntityResponse.getGoodsGroupSeq());
            goodsGroupImageEntity.setImageTypeVersionNo(goodsGroupImageEntityResponse.getImageTypeVersionNo());
            goodsGroupImageEntity.setImageFileName(goodsGroupImageEntityResponse.getImageFileName());
            goodsGroupImageEntity.setRegistTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntityResponse.getRegistTime()));
            goodsGroupImageEntity.setUpdateTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntityResponse.getUpdateTime()));

            goodsGroupImageEntityList.add(goodsGroupImageEntity);
        }

        return goodsGroupImageEntityList;
    }

    /**
     * 商品規格画像登録リストに変換
     *
     * @param goodsImageEntityResponse 商品グループ画像レスポンス
     * @return 商品規格画像登録リスト
     */
    public GoodsImageEntity toGoodsImageEntity(jp.co.hankyuhanshin.itec.hitmall.api.cart.param.GoodsImageEntityResponse goodsImageEntityResponse) {

        if (goodsImageEntityResponse == null) {
            return null;
        }

        GoodsImageEntity goodsImageEntity = new GoodsImageEntity();
        goodsImageEntity.setGoodsGroupSeq(goodsImageEntityResponse.getGoodsGroupSeq());
        goodsImageEntity.setGoodsSeq(goodsImageEntityResponse.getGoodsSeq());
        goodsImageEntity.setImageFileName(goodsImageEntityResponse.getImageFileName());
        goodsImageEntity.setDisplayFlag(EnumTypeUtil.getEnumFromValue(HTypeDisplayStatus.class,
                                                                      goodsImageEntityResponse.getDisplayFlag()
                                                                     ));
        goodsImageEntity.setRegistTime(conversionUtility.toTimeStamp(goodsImageEntityResponse.getRegistTime()));
        goodsImageEntity.setUpdateTime(conversionUtility.toTimeStamp(goodsImageEntityResponse.getUpdateTime()));
        goodsImageEntity.setTmpFilePath(goodsImageEntityResponse.getTmpFilePath());

        return goodsImageEntity;
    }

    /**
     * 割引適用結果MAPに変換
     *
     * @param discountsResponseDetailMapResponse WEB-API連携取得結果DTOMapクラスレスポンス
     * @return 割引適用結果MAP
     */
    private Map<String, WebApiGetDiscountsResponseDetailDto> toDiscountsResponseDetailMapResponse(Map<String, WebApiGetDiscountsResponseDetailDtoResponse> discountsResponseDetailMapResponse) {

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
    private Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> toConsumptionTaxRateMapResponse(Map<String, WebApiGetConsumptionTaxRateResponseDetailDtoResponse> consumptionTaxRateMapResponse) {

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

    // 2023-renew No14 from here

    /**
     * 数量割引適用結果MAPに変換
     *
     * @param quantityDiscountsResponseDetailMap 数量割引適用結果取得レスポンスMAP
     * @return 数量割引適用結果MAP
     */
    private Map<String, WebApiGetQuantityDiscountResultResponseDetailDto> toWebApiGetQuantityDiscountResultResponseDetailDtoMap(
                    Map<String, WebApiGetQuantityDiscountResultResponseDetailDtoResponse> quantityDiscountsResponseDetailMap) {

        if (MapUtils.isEmpty(quantityDiscountsResponseDetailMap)) {
            return new HashMap<>();
        }

        Map<String, WebApiGetQuantityDiscountResultResponseDetailDto> quantityDiscountResultResponseDetailDtoMap =
                        new HashMap<>();

        quantityDiscountsResponseDetailMap.forEach((key, value) -> {
            if (ObjectUtils.isNotEmpty(value)) {
                WebApiGetQuantityDiscountResultResponseDetailDto webApiGetQuantityDiscountResultResponseDetailDto =
                                new WebApiGetQuantityDiscountResultResponseDetailDto();

                webApiGetQuantityDiscountResultResponseDetailDto.setGoodsCode(value.getGoodsCode());
                webApiGetQuantityDiscountResultResponseDetailDto.setSaleType(value.getSaleType());
                webApiGetQuantityDiscountResultResponseDetailDto.setSaleGroupCode(value.getSaleGroupCode());
                webApiGetQuantityDiscountResultResponseDetailDto.setSalePrice(value.getSalePrice());
                webApiGetQuantityDiscountResultResponseDetailDto.setQuantity(value.getQuantity());
                webApiGetQuantityDiscountResultResponseDetailDto.setSaleCode(value.getSaleCode());
                webApiGetQuantityDiscountResultResponseDetailDto.setNote(value.getNote());
                webApiGetQuantityDiscountResultResponseDetailDto.setHints(value.getHints());
                webApiGetQuantityDiscountResultResponseDetailDto.setPrice(value.getPrice());

                quantityDiscountResultResponseDetailDtoMap.put(key, webApiGetQuantityDiscountResultResponseDetailDto);
            }
        });

        return quantityDiscountResultResponseDetailDtoMap;
    }

    /**
     * 取りおき情報取得 詳細情報MAPに変換
     *
     * @param reserveMap 取りおきMapレスポンス
     * @return 取りおき情報取得 詳細情報MAP
     */
    private Map<String, WebApiGetReserveResponseDetailDto> toWebApiGetReserveResponseDetailDtoMap(Map<String, WebApiGetReserveResponseDetailDtoResponse> reserveMap) {

        if (MapUtils.isEmpty(reserveMap)) {
            return new HashMap<>();
        }

        Map<String, WebApiGetReserveResponseDetailDto> reserveResponseDetailDtoMap = new HashMap<>();

        reserveMap.forEach((key, value) -> {
            if (ObjectUtils.isNotEmpty(value)) {
                WebApiGetReserveResponseDetailDto detailDto = new WebApiGetReserveResponseDetailDto();

                detailDto.setGoodsCode(value.getGoodsCode());
                detailDto.setReserveFlag(value.getReserveFlag());
                detailDto.setDiscountFlag(value.getDiscountFlag());
                detailDto.setPossibleReserveFromDay(conversionUtility.toTimeStamp(value.getPossibleReserveFromDay()));
                detailDto.setPossibleReserveToDay(conversionUtility.toTimeStamp(value.getPossibleReserveToDay()));

                reserveResponseDetailDtoMap.put(key, detailDto);
            }
        });

        return reserveResponseDetailDtoMap;
    }

    /**
     * 販売可否判定 詳細情報MAPに変換
     *
     * @param saleCheckMap 販売可否判定Mapレスポンス
     * @return 販売可否判定 詳細情報MAP
     */
    private Map<String, WebApiGetSaleCheckResponseDetailDto> toWebApiGetSaleCheckResponseDetailDtoMap(Map<String, WebApiGetSaleCheckDetailResponse> saleCheckMap) {

        if (MapUtils.isEmpty(saleCheckMap)) {
            return new HashMap<>();
        }

        Map<String, WebApiGetSaleCheckResponseDetailDto> saleCheckResponseDetailDtoMap = new HashMap<>();

        saleCheckMap.forEach((key, value) -> {
            if (ObjectUtils.isNotEmpty(value)) {
                WebApiGetSaleCheckResponseDetailDto detailDto = new WebApiGetSaleCheckResponseDetailDto();

                detailDto.setGoodsCode(value.getGoodsCode());
                detailDto.setGoodsSaleYesNo(value.getGoodsSaleYesNo());

                saleCheckResponseDetailDtoMap.put(key, detailDto);
            }
        });

        return saleCheckResponseDetailDtoMap;
    }

    // 2023-renew No14 to here

    /**
     * カート再構築リクエストに変換
     *
     * @param accessUid     端末識別情報
     * @param memberInfoSeq 会員SEQ
     * @param siteType      サイト区分
     * @param orderField    ソート項目
     * @param orderAsc      ソート条件
     * @param customerNo    顧客番号
     * @param zipcode       郵便番号
     * @return カート再構築リクエスト
     */
    public CartDtoCorrectionScreenUpdateRequest toCartDtoCorrectionScreenUpdateRequest(String accessUid,
                                                                                       Integer memberInfoSeq,
                                                                                       String siteType,
                                                                                       String orderField,
                                                                                       Boolean orderAsc,
                                                                                       Integer customerNo,
                                                                                       String zipcode) {

        CartDtoCorrectionScreenUpdateRequest cartDtoCorrectionScreenUpdateRequest =
                        new CartDtoCorrectionScreenUpdateRequest();

        cartDtoCorrectionScreenUpdateRequest.setAccessUid(accessUid);
        cartDtoCorrectionScreenUpdateRequest.setMemberInfoSeq(memberInfoSeq);
        cartDtoCorrectionScreenUpdateRequest.setSiteType(siteType);
        cartDtoCorrectionScreenUpdateRequest.setOrderField(orderField);
        cartDtoCorrectionScreenUpdateRequest.setOrderAsc(orderAsc);
        cartDtoCorrectionScreenUpdateRequest.setCustomerNo(customerNo);
        // 2023-renew No14 from here
        cartDtoCorrectionScreenUpdateRequest.setZipcode(zipcode);
        // 2023-renew No14 to here

        return cartDtoCorrectionScreenUpdateRequest;
    }

    // 2023-renew No11 from here

    /**
     * WEB-API連携取得結果DTOに変換
     *
     * @param webApiGetQuantityDiscountResponse 数量割引情報取得レスポンス
     * @return WEB-API連携取得結果DTO
     */
    public WebApiGetQuantityDiscountResponseDto toWebApiGetQuantityDiscountResponseDto(WebApiGetQuantityDiscountResponse webApiGetQuantityDiscountResponse) {

        if (ObjectUtils.isEmpty(webApiGetQuantityDiscountResponse)) {
            return null;
        }

        WebApiGetQuantityDiscountResponseDto webApiGetQuantityDiscountResponseDto =
                        new WebApiGetQuantityDiscountResponseDto();

        webApiGetQuantityDiscountResponseDto.setInfo(
                        toWebApiGetQuantityDiscountResponseDetailDtoList(webApiGetQuantityDiscountResponse.getInfo()));
        webApiGetQuantityDiscountResponseDto.setResult(
                        toAbstractWebApiResponseResultDto(webApiGetQuantityDiscountResponse.getResult()));

        return webApiGetQuantityDiscountResponseDto;
    }

    /**
     * WEB-API連携取得結果DTO 数量割引情報取得 詳細情報リストに変換
     *
     * @param webApiGetQuantityDiscountResponseDetailDtoResponseList 数量割引情報取得 詳細情報リストレスポンス
     * @return WEB-API連携取得結果DTO 数量割引情報取得 詳細情報リスト
     */
    private List<WebApiGetQuantityDiscountResponseDetailDto> toWebApiGetQuantityDiscountResponseDetailDtoList(List<WebApiGetQuantityDiscountResponseDetailDtoResponse> webApiGetQuantityDiscountResponseDetailDtoResponseList) {

        if (CollectionUtil.isEmpty(webApiGetQuantityDiscountResponseDetailDtoResponseList)) {
            return new ArrayList<>();
        }

        List<WebApiGetQuantityDiscountResponseDetailDto> webApiGetQuantityDiscountResponseDetailDtoList =
                        new ArrayList<>();

        webApiGetQuantityDiscountResponseDetailDtoResponseList.forEach(webApiResponse -> {
            WebApiGetQuantityDiscountResponseDetailDto webApiGetQuantityDiscountResponseDetailDto =
                            new WebApiGetQuantityDiscountResponseDetailDto();

            webApiGetQuantityDiscountResponseDetailDto.setGoodsCode(webApiResponse.getGoodsCode());
            webApiGetQuantityDiscountResponseDetailDtoList.add(webApiGetQuantityDiscountResponseDetailDto);
        });

        return webApiGetQuantityDiscountResponseDetailDtoList;
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
     * WEB-API連携取得結果DTOに変換
     *
     * @param webApiGetStockResponse 商品在庫数取得レスポンス
     * @return WEB-API連携取得結果DTO
     */
    public WebApiGetStockResponseDto toWebApiGetStockResponseDto(WebApiGetStockResponse webApiGetStockResponse) {

        if (ObjectUtils.isEmpty(webApiGetStockResponse)) {
            return null;
        }

        WebApiGetStockResponseDto webApiGetStockResponseDto = new WebApiGetStockResponseDto();

        webApiGetStockResponseDto.setInfo(toWebApiGetStockResponseDetailDtoList(webApiGetStockResponse.getInfo()));
        webApiGetStockResponseDto.setResult(toAbstractWebApiResponseResultDto(webApiGetStockResponse.getResult()));

        return webApiGetStockResponseDto;
    }

    /**
     * WEB-API連携取得結果DTOリストに変換
     *
     * @param webApiGetStockResponseDetailDtoResponseList API連携取得結果DTOリストレスポンス
     * @return WEB-API連携取得結果DTOリスト
     */
    private List<WebApiGetStockResponseDetailDto> toWebApiGetStockResponseDetailDtoList(List<WebApiGetStockResponseDetailDtoResponse> webApiGetStockResponseDetailDtoResponseList) {

        if (CollectionUtil.isEmpty(webApiGetStockResponseDetailDtoResponseList)) {
            return new ArrayList<>();
        }

        List<WebApiGetStockResponseDetailDto> webApiGetStockResponseDetailDtoList = new ArrayList<>();

        webApiGetStockResponseDetailDtoResponseList.forEach(webApiGetStockResponseDetailDtoResponse -> {
            WebApiGetStockResponseDetailDto webApiGetStockResponseDetailDto = new WebApiGetStockResponseDetailDto();

            webApiGetStockResponseDetailDto.setGoodsCode(webApiGetStockResponseDetailDtoResponse.getGoodsCode());
            webApiGetStockResponseDetailDto.setStockQuantity(
                            webApiGetStockResponseDetailDtoResponse.getStockQuantity());
            webApiGetStockResponseDetailDto.setSaleYesNo(webApiGetStockResponseDetailDtoResponse.getSaleYesNo());
            webApiGetStockResponseDetailDto.setSaleNgMessage(
                            webApiGetStockResponseDetailDtoResponse.getSaleNgMessage());
            webApiGetStockResponseDetailDto.setStockDate(
                            conversionUtility.toTimeStamp(webApiGetStockResponseDetailDtoResponse.getStockDate()));
            webApiGetStockResponseDetailDto.setDeliveryYesNo(
                            webApiGetStockResponseDetailDtoResponse.getDeliveryYesNo());
            webApiGetStockResponseDetailDto.setSaleControl(EnumTypeUtil.getEnumFromValue(HTypeSaleControlType.class,
                                                                                         webApiGetStockResponseDetailDtoResponse.getSaleControl()
                                                                                        ));

            webApiGetStockResponseDetailDtoList.add(webApiGetStockResponseDetailDto);
        });

        return webApiGetStockResponseDetailDtoList;
    }

    // 2023-renew No11 to here

}
