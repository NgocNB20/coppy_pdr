/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart;

import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsForTakeOverDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsImageEntityResponse;
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
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartGoodsForTakeOverDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一括注文画面 Helper
 *
 * @author pham-q7
 */
@Component
// PDR Migrate Customization from here
public class CatalogHelper {

    // PDR Migrate Customization from here
    /**
     * 商品系Helper取得
     */
    private final GoodsUtility goodsUtility;
    // PDR Migrate Customization to here

    /**
     * 変換Utility
     */
    private final ConversionUtility conversionUtility;

    /**
     * コンストラクタ
     *
     * @param goodsUtility      商品系ヘルパークラス
     * @param conversionUtility
     */
    @Autowired
    public CatalogHelper(GoodsUtility goodsUtility, ConversionUtility conversionUtility) {
        this.goodsUtility = goodsUtility;
        this.conversionUtility = conversionUtility;
    }

    /**
     * 初期表示設定
     * 初期表示情報をページクラスにセット
     *
     * @param catalogModel 一括注文Model
     * @param limit        最大表示件数
     */
    public void toPageForLoad(CatalogModel catalogModel, int limit) {
        // 一括注文画面ページListをセット
        List<CatalogModelItem> catalogItemList = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            CatalogModelItem catalogItem = ApplicationContextUtility.getBean(CatalogModelItem.class);
            catalogItem.setCatalogIndex(i);
            catalogItemList.add(catalogItem);
        }

        catalogModel.setCatalogItems(catalogItemList);
    }

    /**
     * 商品詳細情報設定
     * ページクラスに商品詳細情報を設定する
     *
     * @param catalogModel    一括注文Model
     * @param catalogItem     一括注文画面アイテム
     * @param goodsDetailsDto 商品詳細Dto
     */
    public void toPageForGoodsDetails(CatalogModel catalogModel,
                                      CatalogModelItem catalogItem,
                                      GoodsDetailsDto goodsDetailsDto) {
        // PDR Migrate Customization from here
        // 販売可能商品区分判定（商品が空のときはnull）
        // 2023-renew No2 from here
        // 商品グループSEQ
        catalogItem.setGoodsSeq(goodsDetailsDto.getGoodsSeq());
        catalogItem.setGoodsGroupSeq(goodsDetailsDto.getGoodsGroupSeq());
        // 商品名・記号
        catalogItem.setNamePc(goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(goodsDetailsDto));
        // 規格
        catalogItem.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
        catalogItem.setUnitValue1(goodsDetailsDto.getUnitValue1());
        catalogItem.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
        catalogItem.setUnitValue2(goodsDetailsDto.getUnitValue2());
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
        catalogItem.setGoodsImageItems(goodsImageList);
        // 2023-renew No2 to here
        // PDR Migrate Customization to here
    }

    /**
     * 商品詳細Dtoに変換
     *
     * @param response 商品詳細Dtoクラスレスポンス
     * @return 商品詳細Dto
     */
    public GoodsDetailsDto toGoodsDetailsDto(GoodsDetailsDtoResponse response) {
        if (response == null) {
            return null;
        }

        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();
        goodsDetailsDto.setGoodsSeq(response.getGoodsSeq());
        goodsDetailsDto.setGoodsGroupSeq(response.getGoodsGroupSeq());
        goodsDetailsDto.setShopSeq(1001);
        goodsDetailsDto.setVersionNo(response.getVersionNo());
        goodsDetailsDto.setRegistTime(conversionUtility.toTimeStamp(response.getRegistTime()));
        goodsDetailsDto.setUpdateTime(conversionUtility.toTimeStamp(response.getUpdateTime()));
        goodsDetailsDto.setGoodsCode(response.getGoodsCode());
        goodsDetailsDto.setGoodsGroupMaxPrice(response.getGoodsGroupMaxPrice());
        goodsDetailsDto.setGoodsGroupMinPrice(response.getGoodsGroupMinPrice());
        goodsDetailsDto.setPreDiscountMinPrice(response.getPreDiscountMinPrice());
        goodsDetailsDto.setPreDiscountMaxPrice(response.getPreDiscountMaxPrice());
        if (response.getGoodsTaxType() != null) {
            goodsDetailsDto.setGoodsTaxType(
                            EnumTypeUtil.getEnumFromValue(HTypeGoodsTaxType.class, response.getGoodsTaxType()));
        }
        goodsDetailsDto.setTaxRate(response.getTaxRate());
        if (response.getAlcoholFlag() != null) {
            goodsDetailsDto.setAlcoholFlag(
                            EnumTypeUtil.getEnumFromValue(HTypeAlcoholFlag.class, response.getAlcoholFlag()));
        }
        goodsDetailsDto.setGoodsPriceInTax(response.getGoodsPriceInTax());
        goodsDetailsDto.setGoodsPrice(response.getGoodsPrice());
        goodsDetailsDto.setDeliveryType(response.getDeliveryType());
        if (response.getSaleStatus() != null) {
            goodsDetailsDto.setSaleStatusPC(
                            EnumTypeUtil.getEnumFromValue(HTypeGoodsSaleStatus.class, response.getSaleStatus()));
        }
        goodsDetailsDto.setSaleStartTimePC(conversionUtility.toTimeStamp(response.getSaleStartTime()));
        goodsDetailsDto.setSaleEndTimePC(conversionUtility.toTimeStamp(response.getSaleEndTime()));
        if (response.getUnitManagementFlag() != null) {
            goodsDetailsDto.setUnitManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class,
                                                                                response.getUnitManagementFlag()
                                                                               ));
        }
        if (response.getStockManagementFlag() != null) {
            goodsDetailsDto.setStockManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeStockManagementFlag.class,
                                                                                 response.getStockManagementFlag()
                                                                                ));
        }
        if (response.getIndividualDeliveryType() != null) {
            goodsDetailsDto.setIndividualDeliveryType(EnumTypeUtil.getEnumFromValue(HTypeIndividualDeliveryType.class,
                                                                                    response.getIndividualDeliveryType()
                                                                                   ));
        }
        goodsDetailsDto.setPurchasedMax(response.getPurchasedMax());
        if (response.getFreeDeliveryFlag() != null) {
            goodsDetailsDto.setFreeDeliveryFlag(
                            EnumTypeUtil.getEnumFromValue(HTypeFreeDeliveryFlag.class, response.getFreeDeliveryFlag()));
        }
        goodsDetailsDto.setOrderDisplay(response.getOrderDisplay());
        goodsDetailsDto.setUnitValue1(response.getUnitValue1());
        goodsDetailsDto.setUnitValue2(response.getUnitValue2());
        goodsDetailsDto.setPreDiscountPrice(response.getPreDiscountPrice());
        goodsDetailsDto.setPreDisCountPriceInTax(response.getPreDisCountPriceInTax());
        goodsDetailsDto.setJanCode(response.getJanCode());
        goodsDetailsDto.setCatalogCode(response.getCatalogCode());
        goodsDetailsDto.setSalesPossibleStock(response.getSalesPossibleStock());
        goodsDetailsDto.setRealStock(response.getRealStock());
        goodsDetailsDto.setOrderReserveStock(response.getOrderReserveStock());
        goodsDetailsDto.setRemainderFewStock(response.getRemainderFewStock());
        goodsDetailsDto.setOrderPointStock(response.getOrderPointStock());
        goodsDetailsDto.setSafetyStock(response.getSafetyStock());
        goodsDetailsDto.setGoodsGroupCode(response.getGoodsGroupCode());
        goodsDetailsDto.setWhatsnewDate(conversionUtility.toTimeStamp(response.getWhatsnewDate()));
        if (response.getGoodsOpenStatus() != null) {
            goodsDetailsDto.setGoodsOpenStatusPC(
                            EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class, response.getGoodsOpenStatus()));
        }
        goodsDetailsDto.setOpenStartTimePC(conversionUtility.toTimeStamp(response.getOpenStartTime()));
        goodsDetailsDto.setOpenEndTimePC(conversionUtility.toTimeStamp(response.getOpenEndTime()));
        goodsDetailsDto.setGoodsGroupName(response.getGoodsGroupName());
        goodsDetailsDto.setUnitTitle1(response.getUnitTitle1());
        goodsDetailsDto.setUnitTitle2(response.getUnitTitle2());
        goodsDetailsDto.setGoodsPreDiscountPrice(response.getGoodsPreDiscountPrice());
        goodsDetailsDto.setGoodsGroupImageEntityList(
                        toGoodsGroupImageEntityList(response.getGoodsGroupImageEntityList()));
        if (response.getSnsLinkFlag() != null) {
            goodsDetailsDto.setSnsLinkFlag(
                            EnumTypeUtil.getEnumFromValue(HTypeSnsLinkFlag.class, response.getSnsLinkFlag()));
        }
        goodsDetailsDto.setMetaDescription(response.getMetaDescription());
        if (response.getStockStatus() != null) {
            goodsDetailsDto.setStockStatusPc(
                            EnumTypeUtil.getEnumFromValue(HTypeStockStatusType.class, response.getStockStatus()));
        }
        goodsDetailsDto.setGoodsNote1(response.getGoodsNote1());
        goodsDetailsDto.setGoodsNote2(response.getGoodsNote2());
        goodsDetailsDto.setGoodsNote3(response.getGoodsNote3());
        goodsDetailsDto.setGoodsNote4(response.getGoodsNote4());
        goodsDetailsDto.setGoodsNote5(response.getGoodsNote5());
        goodsDetailsDto.setGoodsNote6(response.getGoodsNote6());
        goodsDetailsDto.setGoodsNote7(response.getGoodsNote7());
        goodsDetailsDto.setGoodsNote8(response.getGoodsNote8());
        goodsDetailsDto.setGoodsNote9(response.getGoodsNote9());
        goodsDetailsDto.setGoodsNote10(response.getGoodsNote10());
        goodsDetailsDto.setOrderSetting1(response.getOrderSetting1());
        goodsDetailsDto.setOrderSetting2(response.getOrderSetting2());
        goodsDetailsDto.setOrderSetting3(response.getOrderSetting3());
        goodsDetailsDto.setOrderSetting4(response.getOrderSetting4());
        goodsDetailsDto.setOrderSetting5(response.getOrderSetting5());
        goodsDetailsDto.setOrderSetting6(response.getOrderSetting6());
        goodsDetailsDto.setOrderSetting7(response.getOrderSetting7());
        goodsDetailsDto.setOrderSetting8(response.getOrderSetting8());
        goodsDetailsDto.setOrderSetting9(response.getOrderSetting9());
        goodsDetailsDto.setOrderSetting10(response.getOrderSetting10());
        goodsDetailsDto.setUnitImage(toGoodsImageEntity(response.getUnitImage()));
        goodsDetailsDto.setGoodsOptionDisplayName(response.getGoodsOptionDisplayName());
        if (response.getGoodsClassType() != null) {
            goodsDetailsDto.setGoodsClassType(
                            EnumTypeUtil.getEnumFromValue(HTypeGoodsClassType.class, response.getGoodsClassType()));
        }
        if (response.getDentalMonopolySalesFlg() != null) {
            goodsDetailsDto.setDentalMonopolySalesFlg(EnumTypeUtil.getEnumFromValue(HTypeDentalMonopolySalesFlg.class,
                                                                                    response.getDentalMonopolySalesFlg()
                                                                                   ));
        }
        if (response.getSaleIconFlag() != null) {
            goodsDetailsDto.setSaleIconFlag(
                            EnumTypeUtil.getEnumFromValue(HTypeSaleIconFlag.class, response.getSaleIconFlag()));
        }
        if (response.getReserveIconFlag() != null) {
            goodsDetailsDto.setReserveIconFlag(
                            EnumTypeUtil.getEnumFromValue(HTypeReserveIconFlag.class, response.getReserveIconFlag()));
        }
        if (response.getNewIconFlag() != null) {
            goodsDetailsDto.setNewIconFlag(
                            EnumTypeUtil.getEnumFromValue(HTypeNewIconFlag.class, response.getNewIconFlag()));
        }
        if (response.getLandSendFlag() != null) {
            goodsDetailsDto.setLandSendFlag(
                            EnumTypeUtil.getEnumFromValue(HTypeLandSendFlag.class, response.getLandSendFlag()));
        }
        if (response.getCoolSendFlag() != null) {
            goodsDetailsDto.setCoolSendFlag(
                            EnumTypeUtil.getEnumFromValue(HTypeCoolSendFlag.class, response.getCoolSendFlag()));
        }
        goodsDetailsDto.setCoolSendFrom(response.getCoolSendFrom());
        goodsDetailsDto.setCoolSendTo(response.getCoolSendTo());
        goodsDetailsDto.setTax(response.getTax());
        goodsDetailsDto.setUnit(response.getUnit());
        goodsDetailsDto.setGoodsManagementCode(response.getGoodsManagementCode());
        goodsDetailsDto.setGoodsDivisionCode(response.getGoodsDivisionCode());
        goodsDetailsDto.setGoodsCategory1(response.getGoodsCategory1());
        goodsDetailsDto.setGoodsCategory2(response.getGoodsCategory2());
        goodsDetailsDto.setGoodsCategory3(response.getGoodsCategory3());
        if (response.getReserveFlag() != null) {
            goodsDetailsDto.setReserveFlag(
                            EnumTypeUtil.getEnumFromValue(HTypeReserveFlag.class, response.getReserveFlag()));
        }
        if (response.getPriceMarkDispFlag() != null) {
            goodsDetailsDto.setPriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypePriceMarkDispFlag.class,
                                                                               response.getPriceMarkDispFlag()
                                                                              ));
        }
        if (response.getSalePriceMarkDispFlag() != null) {
            goodsDetailsDto.setSalePriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceMarkDispFlag.class,
                                                                                   response.getSalePriceMarkDispFlag()
                                                                                  ));
        }
        if (response.getSalePriceIntegrityFlag() != null) {
            goodsDetailsDto.setSalePriceIntegrityFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceIntegrityFlag.class,
                                                                                    response.getSalePriceIntegrityFlag()
                                                                                   ));
        }
        goodsDetailsDto.setSaleYesNo(response.getSaleYesNo());
        goodsDetailsDto.setSaleNgMessage(response.getSaleNgMessage());
        goodsDetailsDto.setDeliveryYesNo(response.getDeliveryYesNo());
        if (response.getEmotionPriceType() != null) {
            goodsDetailsDto.setEmotionPriceType(
                            EnumTypeUtil.getEnumFromValue(HTypeEmotionPriceType.class, response.getEmotionPriceType()));
        }

        return goodsDetailsDto;
    }

    /**
     * 商品グループ画像エンティティに変換
     *
     * @param goodsGroupImageResponseList 商品グループ画像レスポンスリスト
     * @return 商品グループ画像エンティティ
     */
    public List<GoodsGroupImageEntity> toGoodsGroupImageEntityList(List<GoodsGroupImageEntityResponse> goodsGroupImageResponseList) {
        if (CollectionUtil.isEmpty(goodsGroupImageResponseList)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntity> goodsGroupImageEntityList = new ArrayList<>();
        goodsGroupImageResponseList.forEach(item -> {
            GoodsGroupImageEntity goodsGroupImageEntity = new GoodsGroupImageEntity();
            goodsGroupImageEntity.setGoodsGroupSeq(item.getGoodsGroupSeq());
            goodsGroupImageEntity.setImageTypeVersionNo(item.getImageTypeVersionNo());
            goodsGroupImageEntity.setImageFileName(item.getImageFileName());
            goodsGroupImageEntity.setRegistTime(conversionUtility.toTimeStamp(item.getRegistTime()));
            goodsGroupImageEntity.setUpdateTime(conversionUtility.toTimeStamp(item.getUpdateTime()));

            goodsGroupImageEntityList.add(goodsGroupImageEntity);
        });

        return goodsGroupImageEntityList;
    }

    /**
     * 商品画像に変換
     *
     * @param response 商品グループ画像レスポンス
     * @return 商品画像
     */
    public GoodsImageEntity toGoodsImageEntity(GoodsImageEntityResponse response) {
        if (response == null) {
            return null;
        }

        GoodsImageEntity goodsImageEntity = new GoodsImageEntity();
        goodsImageEntity.setGoodsGroupSeq(response.getGoodsGroupSeq());
        goodsImageEntity.setGoodsSeq(response.getGoodsSeq());
        goodsImageEntity.setImageFileName(response.getImageFileName());
        if (response.getDisplayFlag() != null) {
            goodsImageEntity.setDisplayFlag(
                            EnumTypeUtil.getEnumFromValue(HTypeDisplayStatus.class, response.getDisplayFlag()));
        }
        goodsImageEntity.setRegistTime(conversionUtility.toTimeStamp(response.getRegistTime()));
        goodsImageEntity.setUpdateTime(conversionUtility.toTimeStamp(response.getUpdateTime()));
        goodsImageEntity.setTmpFilePath(response.getTmpFilePath());

        return goodsImageEntity;
    }

    /**
     * カート一括登録用引継DTOリクエストリストに変換
     *
     * @param cartGoodsForTakeOverDtoList カート一括登録用引継DTOリスト
     * @return カート一括登録用引継DTOリクエストリスト
     */
    public List<CartGoodsForTakeOverDtoRequest> toCartGoodsForTakeOverDtoRequestList(List<CartGoodsForTakeOverDto> cartGoodsForTakeOverDtoList) {
        if (CollectionUtil.isEmpty(cartGoodsForTakeOverDtoList)) {
            return new ArrayList<>();
        }

        List<CartGoodsForTakeOverDtoRequest> requestList = new ArrayList<>();
        cartGoodsForTakeOverDtoList.forEach(item -> {
            CartGoodsForTakeOverDtoRequest request = new CartGoodsForTakeOverDtoRequest();
            request.setGoodsGroupSeq(item.getGoodsGroupSeq());
            request.setGoodsSeq(item.getGoodsSeq());
            request.setGoodsCode(item.getGoodsCode());
            request.setGoodsCount(item.getGoodsCount());

            requestList.add(request);
        });

        return requestList;
    }

    /**
     * 商品詳細情報MAPに変換
     *
     * @param dtoResponseMap 商品詳細情報MAP取得レスポンス
     * @return 商品詳細情報MAP
     */
    public Map<Integer, GoodsDetailsDto> toGoodsDetailsDtoMap(Map<String, GoodsDetailsDtoResponse> dtoResponseMap) {
        if (MapUtils.isEmpty(dtoResponseMap)) {
            return new HashMap<>();
        }

        Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap = new HashMap<>();
        dtoResponseMap.keySet().forEach(key -> {
            GoodsDetailsDtoResponse response = dtoResponseMap.get(key);
            GoodsDetailsDto goodsDetailsDto = toGoodsDetailsDto(response);

            goodsDetailsDtoMap.put(conversionUtility.toInteger(key), goodsDetailsDto);
        });

        return goodsDetailsDtoMap;
    }

}
// PDR Migrate Customization to here
