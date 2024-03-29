/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.NewsEntityListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.NewsEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.AbstractWebApiResponseResultDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPurchasedCommodityInformationResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPurchasedCommodityInformationResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponseCustomerSalePriceDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponsePriceDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponseSalePriceDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockResponseDetailDtoResponse;
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
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGroupPriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeLandSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOutletIconFlag;
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
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.AbstractWebApiResponseResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetPurchasedCommodityInformationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetPurchasedCommodityInformationResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.news.NewsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * トップ画面 Helper
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Component
public class IndexHelper {

    private final GoodsUtility goodsUtility;

    private final ConversionUtility conversionUtility;

    @Autowired
    public IndexHelper(GoodsUtility goodsUtility, ConversionUtility conversionUtility) {
        this.goodsUtility = goodsUtility;
        this.conversionUtility = conversionUtility;
    }

    /**
     * Modelへの変換処理
     *
     * @param newsEntityList ニュースエンティティリスト
     * @param indexModel     トップ画面Model
     */
    public void toPageForLoadNews(List<NewsEntity> newsEntityList, IndexModel indexModel) {
        List<IndexModelItem> itemsList = new ArrayList<>();
        for (NewsEntity newsEntity : newsEntityList) {

            IndexModelItem indexModelItem = ApplicationContextUtility.getBean(IndexModelItem.class);

            indexModelItem.setNseq(newsEntity.getNewsSeq().toString());
            indexModelItem.setNewsTime(newsEntity.getNewsTime());

            indexModelItem.setNewsTitlePC(newsEntity.getTitlePC());
            indexModelItem.setNewsUrlPC(newsEntity.getNewsUrlPC());
            indexModelItem.setNewsBodyPC(newsEntity.getNewsBodyPC());
            indexModelItem.setNewsNotePC(newsEntity.getNewsNotePC());

            itemsList.add(indexModelItem);
        }
        indexModel.newsItems = itemsList;
    }
    // PDR Migrate Customization from here

    /**
     * 画面表示・再表示(購入した商品)<br/>
     *
     * @param puchasedDtoList お気に入りDTOリスト
     * @param indexModel      ページクラス
     */
    public void toPageForLoadPuchased(List<GoodsDetailsDto> puchasedDtoList, IndexModel indexModel) {

        List<IndexModelItem> goodsItems = new ArrayList<>();
        NumberFormat formatter = NumberFormat.getNumberInstance();

        for (GoodsDetailsDto goodsDetailsDto : puchasedDtoList) {

            if (goodsDetailsDto == null || !goodsUtility.isGoodsOpen(goodsDetailsDto.getGoodsOpenStatusPC(),
                                                                     goodsDetailsDto.getOpenStartTimePC(),
                                                                     goodsDetailsDto.getOpenEndTimePC()
                                                                    )) {
                continue;
            }
            // ページアイテムクラスにセット
            IndexModelItem indexModelItem = new IndexModelItem();
            indexModelItem.setGoodsGroupSeq(goodsDetailsDto.getGoodsGroupSeq());
            indexModelItem.setGgcd(goodsDetailsDto.getGoodsGroupCode());
            indexModelItem.setGcd(goodsDetailsDto.getGoodsCode());
            indexModelItem.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
            indexModelItem.setGoodsPriceInTax(goodsDetailsDto.getGoodsPriceInTax());
            indexModelItem.setGoodsGroupPreDiscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());
            indexModelItem.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
            indexModelItem.setUnitValue1(goodsDetailsDto.getUnitValue1());
            indexModelItem.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
            indexModelItem.setUnitValue2(goodsDetailsDto.getUnitValue2());
            indexModelItem.setPreDiscountPrice(goodsDetailsDto.getPreDiscountPrice());

            // 販売可能商品区分
            // 2023-renew No2 from here

            // 2023-renew No2 to here

            indexModelItem.setDispGoodsPriceInTax(goodsDetailsDto.getGoodsPriceInTax() == null ?
                                                                  null :
                                                                  formatter.format(goodsDetailsDto.getGoodsPriceInTax())
                                                                  + "円");
            indexModelItem.setDispPreDiscountPrice(goodsDetailsDto.getPreDiscountPrice() == null ?
                                                                   null :
                                                                   formatter.format(
                                                                                   goodsDetailsDto.getPreDiscountPrice())
                                                                   + "円");

            // 新着画像の表示期間を取得
            Timestamp whatsnewDate = goodsUtility.getRealWhatsNewDate(goodsDetailsDto.getWhatsnewDate());
            indexModelItem.setWhatsnewDate(whatsnewDate);

            indexModelItem.setGoodsOpenStatus(goodsDetailsDto.getGoodsOpenStatusPC());
            indexModelItem.setOpenStartTime(goodsDetailsDto.getOpenStartTimePC());
            indexModelItem.setOpenEndTime(goodsDetailsDto.getOpenEndTimePC());

            indexModelItem.setSaleStatus(goodsDetailsDto.getSaleStatusPC());
            indexModelItem.setSaleStartTime(goodsDetailsDto.getSaleStartTimePC());
            indexModelItem.setSaleEndTime(goodsDetailsDto.getSaleEndTimePC());

            indexModelItem.setGoodsSeq(goodsDetailsDto.getGoodsSeq());

            // 商品画像名(PCサムネイル)を取得
            String imageFileName = null;
            if (goodsDetailsDto.getGoodsGroupImageEntityList() != null) {
                imageFileName = goodsUtility.getImageFileName(goodsDetailsDto);

            }
            // 商品画像パスを設定する
            indexModelItem.setGoodsGroupImageThumbnail(goodsUtility.getGoodsImagePath(imageFileName));

            // 在庫状況表示
            if (goodsDetailsDto.getStockStatusPc() != null) {
                indexModelItem.setListStockStatusPc(goodsDetailsDto.getStockStatusPc().getValue());
            }
            // SALEアイコンフラグ
            indexModelItem.setSaleIconFlag(
                            HTypeSaleIconFlag.ON.equals(goodsDetailsDto.getSaleIconFlag()) ? true : false);
            // お取りおきアイコンフラグ
            indexModelItem.setReserveIconFlag(
                            HTypeReserveIconFlag.ON.equals(goodsDetailsDto.getReserveIconFlag()) ? true : false);
            // NEWアイコンフラグ
            indexModelItem.setNewIconFlag(HTypeNewIconFlag.ON.equals(goodsDetailsDto.getNewIconFlag()) ? true : false);

            // 2023-renew No92 from here
            // アウトレットアイコンフラグ
            indexModelItem.setOutletIconFlag(HTypeOutletIconFlag.ON.equals(goodsDetailsDto.getOutletIconFlag()) ? true : false);
            // 2023-renew No92 to here
            // 画面表示用価格
            indexModelItem.setDispPrice(goodsDetailsDto.getGoodsPriceInTax() == null ?
                                                        null :
                                                        formatter.format(goodsDetailsDto.getGoodsPriceInTax()) + "円");
            if (HTypeGroupPriceMarkDispFlag.ON.equals(goodsDetailsDto.getPriceMarkDispFlag())) {
                indexModelItem.setDispPrice(indexModelItem.getDispPrice() + "～");
            }

            // 画面表示用セール価格
            indexModelItem.setDispSalePrice(goodsDetailsDto.getPreDiscountPrice() == null ?
                                                            null :
                                                            formatter.format(goodsDetailsDto.getPreDiscountPrice())
                                                            + "円");
            if (HTypeGroupPriceMarkDispFlag.ON.equals(goodsDetailsDto.getSalePriceMarkDispFlag())) {
                indexModelItem.setDispSalePrice(indexModelItem.getDispSalePrice() + "～");
            }

            // セール価格整合性フラグ
            indexModelItem.setSalePriceIntegrityFlag(
                            HTypeSalePriceIntegrityFlag.MATCH.equals(goodsDetailsDto.getSalePriceIntegrityFlag()) ?
                                            true :
                                            false);

            indexModelItem.setGoodsPrediscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());

            // 2023-renew AddNo5 from here
            indexModelItem.setGoodsPriceHigh(goodsDetailsDto.getGoodsPriceInTaxHight());
            indexModelItem.setGoodsPriceLow(goodsDetailsDto.getGoodsPriceInTaxLow());
            indexModelItem.setGoodsSalePriceHigh(goodsDetailsDto.getPreDiscountPriceHight());
            indexModelItem.setGoodsSalePriceLow(goodsDetailsDto.getPreDiscountPriceLow());
            // 2023-renew AddNo5 to here

            goodsItems.add(indexModelItem);

        }

        indexModel.setPuchasedGoodsItems(goodsItems);
    }

    /**
     * ニュースクラスに変換
     *
     * @param response ニュースEntityListレスポンス
     * @return ニュースクラス
     */
    public List<NewsEntity> toNewsEntityList(NewsEntityListResponse response) {
        if (ObjectUtils.isEmpty(response) || CollectionUtils.isEmpty(response.getNewsEntityListResponse())) {
            return new ArrayList<>();
        }
        List<NewsEntity> newsEntityList = new ArrayList<>();
        for (NewsEntityResponse entityResponse : response.getNewsEntityListResponse()) {
            NewsEntity newsEntity = new NewsEntity();
            newsEntity.setNewsSeq(entityResponse.getNewsSeq());
            newsEntity.setTitlePC(entityResponse.getTitle());
            newsEntity.setNewsBodyPC(entityResponse.getNewsBody());
            newsEntity.setNewsUrlPC(entityResponse.getNewsUrl());
            newsEntity.setNewsOpenStatusPC(
                            EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class, entityResponse.getNewsOpenStatus()));
            newsEntity.setNewsOpenStartTimePC(conversionUtility.toTimeStamp(entityResponse.getNewsOpenStartTime()));
            newsEntity.setNewsOpenEndTimePC(conversionUtility.toTimeStamp(entityResponse.getNewsOpenEndTime()));
            newsEntity.setNewsTime(conversionUtility.toTimeStamp(entityResponse.getNewsTime()));
            newsEntity.setRegistTime(conversionUtility.toTimeStamp(entityResponse.getRegistTime()));
            newsEntity.setUpdateTime(conversionUtility.toTimeStamp(entityResponse.getUpdateTime()));
            newsEntity.setNewsNotePC(entityResponse.getNewsNote());

            newsEntityList.add(newsEntity);
        }

        return newsEntityList;
    }

    /**
     * 商品詳細Dtoに変換
     *
     * @param goodsDetailsDtoResponse 商品詳細Dtoクラスレスポンス
     * @return 商品詳細Dto
     */
    public GoodsDetailsDto toGoodsDetailsDto(GoodsDetailsDtoResponse goodsDetailsDtoResponse) {
        if (ObjectUtils.isEmpty(goodsDetailsDtoResponse)) {
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
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getGoodsTaxType())) {
            goodsDetailsDto.setGoodsTaxType(EnumTypeUtil.getEnumFromValue(HTypeGoodsTaxType.class,
                                                                          goodsDetailsDtoResponse.getGoodsTaxType()
                                                                         ));
        }
        goodsDetailsDto.setTaxRate(goodsDetailsDtoResponse.getTaxRate());
        if (goodsDetailsDtoResponse.getAlcoholFlag() != null) {
            goodsDetailsDto.setAlcoholFlag(EnumTypeUtil.getEnumFromValue(HTypeAlcoholFlag.class,
                                                                         goodsDetailsDtoResponse.getAlcoholFlag()
                                                                        ));
        }
        goodsDetailsDto.setGoodsPriceInTax(goodsDetailsDtoResponse.getGoodsPriceInTax());
        goodsDetailsDto.setGoodsPrice(goodsDetailsDtoResponse.getGoodsPrice());
        goodsDetailsDto.setDeliveryType(goodsDetailsDtoResponse.getDeliveryType());
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getSaleStatus())) {
            goodsDetailsDto.setSaleStatusPC(EnumTypeUtil.getEnumFromValue(HTypeGoodsSaleStatus.class,
                                                                          goodsDetailsDtoResponse.getSaleStatus()
                                                                         ));
        }
        goodsDetailsDto.setSaleStartTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getSaleStartTime()));
        goodsDetailsDto.setSaleEndTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getSaleEndTime()));
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getUnitManagementFlag())) {
            goodsDetailsDto.setUnitManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class,
                                                                                goodsDetailsDtoResponse.getUnitManagementFlag()
                                                                               ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getStockManagementFlag())) {
            goodsDetailsDto.setStockManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeStockManagementFlag.class,
                                                                                 goodsDetailsDtoResponse.getStockManagementFlag()
                                                                                ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getIndividualDeliveryType())) {
            goodsDetailsDto.setIndividualDeliveryType(EnumTypeUtil.getEnumFromValue(HTypeIndividualDeliveryType.class,
                                                                                    goodsDetailsDtoResponse.getIndividualDeliveryType()
                                                                                   ));
        }
        goodsDetailsDto.setPurchasedMax(goodsDetailsDtoResponse.getPurchasedMax());
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getFreeDeliveryFlag())) {
            goodsDetailsDto.setFreeDeliveryFlag(EnumTypeUtil.getEnumFromValue(HTypeFreeDeliveryFlag.class,
                                                                              goodsDetailsDtoResponse.getFreeDeliveryFlag()
                                                                             ));
        }
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
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getGoodsOpenStatus())) {
            goodsDetailsDto.setGoodsOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                               goodsDetailsDtoResponse.getGoodsOpenStatus()
                                                                              ));
        }
        goodsDetailsDto.setOpenStartTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getOpenStartTime()));
        goodsDetailsDto.setOpenEndTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoResponse.getOpenEndTime()));
        goodsDetailsDto.setGoodsGroupName(goodsDetailsDtoResponse.getGoodsGroupName());
        goodsDetailsDto.setUnitTitle1(goodsDetailsDtoResponse.getUnitTitle1());
        goodsDetailsDto.setUnitTitle2(goodsDetailsDtoResponse.getUnitTitle2());
        goodsDetailsDto.setGoodsPreDiscountPrice(goodsDetailsDtoResponse.getGoodsPreDiscountPrice());
        goodsDetailsDto.setGoodsGroupImageEntityList(
                        this.toGoodsGroupImageEntityList(goodsDetailsDtoResponse.getGoodsGroupImageEntityList()));
        goodsDetailsDto.setSnsLinkFlag(EnumTypeUtil.getEnumFromValue(HTypeSnsLinkFlag.class,
                                                                     goodsDetailsDtoResponse.getSnsLinkFlag()
                                                                    ));
        goodsDetailsDto.setMetaDescription(goodsDetailsDtoResponse.getMetaDescription());
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getStockStatus())) {
            goodsDetailsDto.setStockStatusPc(EnumTypeUtil.getEnumFromValue(HTypeStockStatusType.class,
                                                                           goodsDetailsDtoResponse.getStockStatus()
                                                                          ));
        }
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
        goodsDetailsDto.setUnitImage(this.toGoodsImageEntity(goodsDetailsDtoResponse.getUnitImage()));
        goodsDetailsDto.setGoodsOptionDisplayName(goodsDetailsDtoResponse.getGoodsOptionDisplayName());
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getGoodsClassType())) {
            goodsDetailsDto.setGoodsClassType(EnumTypeUtil.getEnumFromValue(HTypeGoodsClassType.class,
                                                                            goodsDetailsDtoResponse.getGoodsClassType()
                                                                           ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getDentalMonopolySalesFlg())) {
            goodsDetailsDto.setDentalMonopolySalesFlg(EnumTypeUtil.getEnumFromValue(HTypeDentalMonopolySalesFlg.class,
                                                                                    goodsDetailsDtoResponse.getDentalMonopolySalesFlg()
                                                                                   ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getSaleIconFlag())) {
            goodsDetailsDto.setSaleIconFlag(EnumTypeUtil.getEnumFromValue(HTypeSaleIconFlag.class,
                                                                          goodsDetailsDtoResponse.getSaleIconFlag()
                                                                         ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getReserveIconFlag())) {
            goodsDetailsDto.setReserveIconFlag(EnumTypeUtil.getEnumFromValue(HTypeReserveIconFlag.class,
                                                                             goodsDetailsDtoResponse.getReserveIconFlag()
                                                                            ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getNewIconFlag())) {
            goodsDetailsDto.setNewIconFlag(EnumTypeUtil.getEnumFromValue(HTypeNewIconFlag.class,
                                                                         goodsDetailsDtoResponse.getNewIconFlag()
                                                                        ));
        }
        // 2023-renew No92 from here
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getOutletIconFlag())) {
            goodsDetailsDto.setOutletIconFlag(EnumTypeUtil.getEnumFromValue(HTypeOutletIconFlag.class,
                                                                         goodsDetailsDtoResponse.getOutletIconFlag()
                                                                        ));
        }
        // 2023-renew No92 to here
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getLandSendFlag())) {
            goodsDetailsDto.setLandSendFlag(EnumTypeUtil.getEnumFromValue(HTypeLandSendFlag.class,
                                                                          goodsDetailsDtoResponse.getLandSendFlag()
                                                                         ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getCoolSendFlag())) {

            goodsDetailsDto.setCoolSendFlag(EnumTypeUtil.getEnumFromValue(HTypeCoolSendFlag.class,
                                                                          goodsDetailsDtoResponse.getCoolSendFlag()
                                                                         ));
        }
        goodsDetailsDto.setCoolSendFrom(goodsDetailsDtoResponse.getCoolSendFrom());
        goodsDetailsDto.setCoolSendTo(goodsDetailsDtoResponse.getCoolSendTo());
        goodsDetailsDto.setTax(goodsDetailsDtoResponse.getTax());
        goodsDetailsDto.setUnit(goodsDetailsDtoResponse.getUnit());
        goodsDetailsDto.setGoodsManagementCode(goodsDetailsDtoResponse.getGoodsManagementCode());
        goodsDetailsDto.setGoodsDivisionCode(goodsDetailsDtoResponse.getGoodsDivisionCode());
        goodsDetailsDto.setGoodsCategory1(goodsDetailsDtoResponse.getGoodsCategory1());
        goodsDetailsDto.setGoodsCategory2(goodsDetailsDtoResponse.getGoodsCategory2());
        goodsDetailsDto.setGoodsCategory3(goodsDetailsDtoResponse.getGoodsCategory3());
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getReserveFlag())) {
            goodsDetailsDto.setReserveFlag(EnumTypeUtil.getEnumFromValue(HTypeReserveFlag.class,
                                                                         goodsDetailsDtoResponse.getReserveFlag()
                                                                        ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getPriceMarkDispFlag())) {
            goodsDetailsDto.setPriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypePriceMarkDispFlag.class,
                                                                               goodsDetailsDtoResponse.getPriceMarkDispFlag()
                                                                              ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getSalePriceMarkDispFlag())) {
            goodsDetailsDto.setSalePriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceMarkDispFlag.class,
                                                                                   goodsDetailsDtoResponse.getSalePriceMarkDispFlag()
                                                                                  ));
        }
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getSalePriceIntegrityFlag())) {
            goodsDetailsDto.setSalePriceIntegrityFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceIntegrityFlag.class,
                                                                                    goodsDetailsDtoResponse.getSalePriceIntegrityFlag()
                                                                                   ));
        }
        goodsDetailsDto.setSaleYesNo(goodsDetailsDtoResponse.getSaleYesNo());
        goodsDetailsDto.setSaleNgMessage(goodsDetailsDtoResponse.getSaleNgMessage());
        goodsDetailsDto.setDeliveryYesNo(goodsDetailsDtoResponse.getDeliveryYesNo());
        if (StringUtils.isNotEmpty(goodsDetailsDtoResponse.getEmotionPriceType())) {
            goodsDetailsDto.setEmotionPriceType(EnumTypeUtil.getEnumFromValue(HTypeEmotionPriceType.class,
                                                                              goodsDetailsDtoResponse.getEmotionPriceType()
                                                                             ));
        }

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
     * @param goodsGroupImageEntityRsponsetList 商品グループ画像リクエストリスト
     * @return 商品グループ画像リスト
     */
    public List<GoodsGroupImageEntity> toGoodsGroupImageEntityList(List<GoodsGroupImageEntityResponse> goodsGroupImageEntityRsponsetList) {
        if (CollectionUtils.isEmpty(goodsGroupImageEntityRsponsetList)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntity> imageEntityList = new ArrayList<>();

        for (GoodsGroupImageEntityResponse goodsGroupImage : goodsGroupImageEntityRsponsetList) {
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
     * 商品画像に変換
     *
     * @param goodsImageEntityResponse 商品グループ画像リクエスト
     * @return 商品画像
     */
    public GoodsImageEntity toGoodsImageEntity(GoodsImageEntityResponse goodsImageEntityResponse) {
        if (ObjectUtils.isEmpty(goodsImageEntityResponse)) {
            return null;
        }
        GoodsImageEntity imageEntity = new GoodsImageEntity();

        imageEntity.setGoodsSeq(goodsImageEntityResponse.getGoodsSeq());
        imageEntity.setGoodsGroupSeq(goodsImageEntityResponse.getGoodsGroupSeq());
        imageEntity.setImageFileName(goodsImageEntityResponse.getImageFileName());
        imageEntity.setDisplayFlag(EnumTypeUtil.getEnumFromValue(HTypeDisplayStatus.class,
                                                                 goodsImageEntityResponse.getDisplayFlag()
                                                                ));
        imageEntity.setTmpFilePath(goodsImageEntityResponse.getTmpFilePath());
        imageEntity.setRegistTime(conversionUtility.toTimeStamp(goodsImageEntityResponse.getRegistTime()));
        imageEntity.setUpdateTime(conversionUtility.toTimeStamp(goodsImageEntityResponse.getUpdateTime()));

        return imageEntity;
    }

    /**
     * 商品詳細マップに変換
     *
     * @param goodsDetailsDtoResponseMap 商品詳細DtoMAPレスポンス
     * @return 商品詳細マップ
     */
    public Map<String, GoodsDetailsDto> toGoodsDetailsDtoMap(Map<String, GoodsDetailsDtoResponse> goodsDetailsDtoResponseMap) {
        Map<String, GoodsDetailsDto> goodsDetailsDtoMap = new HashMap<>();
        if (MapUtils.isNotEmpty(goodsDetailsDtoResponseMap)) {
            goodsDetailsDtoResponseMap.forEach((key, value) -> {
                if (ObjectUtils.isNotEmpty(value)) {
                    GoodsDetailsDto goodsDetailsDto = this.toGoodsDetailsDto(value);

                    goodsDetailsDtoMap.put(key, goodsDetailsDto);
                }
            });
        }
        return goodsDetailsDtoMap;
    }

    /**
     * WEB-API連携取得結果DTOに変換
     *
     * @param getPurchasedCommodityInformationResponse WEB-API連携 購入済み商品情報レスポンス
     * @return WEB-API連携取得結果DTO
     */
    public WebApiGetPurchasedCommodityInformationResponseDto toPurchasedCommodityInformationDto(
                    WebApiGetPurchasedCommodityInformationResponse getPurchasedCommodityInformationResponse) {
        if (ObjectUtils.isEmpty(getPurchasedCommodityInformationResponse)) {
            return null;
        }

        WebApiGetPurchasedCommodityInformationResponseDto getPurchasedCommodityInformationResponseDto =
                        new WebApiGetPurchasedCommodityInformationResponseDto();
        List<WebApiGetPurchasedCommodityInformationResponseDetailDto> infoList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(getPurchasedCommodityInformationResponse.getInfo())) {
            for (WebApiGetPurchasedCommodityInformationResponseDetailDtoResponse dtoResponse : getPurchasedCommodityInformationResponse
                            .getInfo()) {
                WebApiGetPurchasedCommodityInformationResponseDetailDto dto =
                                new WebApiGetPurchasedCommodityInformationResponseDetailDto();
                dto.setGoodsCode(dtoResponse.getGoodsCode());

                infoList.add(dto);
            }
        }

        AbstractWebApiResponseResultDto abstractWebApiResponseResultDto = new AbstractWebApiResponseResultDto();
        abstractWebApiResponseResultDto.setMessage(getPurchasedCommodityInformationResponse.getResult().getMessage());
        abstractWebApiResponseResultDto.setStatus(getPurchasedCommodityInformationResponse.getResult().getStatus());

        getPurchasedCommodityInformationResponseDto.setInfo(infoList);
        getPurchasedCommodityInformationResponseDto.setResult(abstractWebApiResponseResultDto);

        return getPurchasedCommodityInformationResponseDto;
    }

    // PDR Migrate Customization to here
}
