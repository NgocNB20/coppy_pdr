// 2023-renew No65 from here
// 2023-renew No71 from here
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsDetailsDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsGroupImageEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsImageEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoAnnounceUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.StockDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.AbstractWebApiResponseResultDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
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
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOutletIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleAnnounceWatchFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleControlType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockAnnounceWatchFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTopSaleAnnounceFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTopStockAnnounceFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.stock.StockDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.favorite.FavoriteDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.favorite.FavoriteSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.restockannounce.RestockAnnounceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.restockannounce.RestockAnnounceSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.AbstractWebApiResponseResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.favorite.FavoriteEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.restockannounce.RestockAnnounceEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsStockItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common.GoodsIconItem;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.PageInfoHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * メンバーコントローラーの共通ヘルパー
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Component
@RequiredArgsConstructor
public class MemberHelper {
    // 2023-renew No22 from here
    /**
     * 日付時刻の形式を設定する
     */
    private static final String FORMAT_DATE_TIME = "yyyyMMddHHmmssSSS";
    // 2023-renew No22 to here

    /**
     * 商品系ヘルパークラス
     */
    private final GoodsUtility goodsUtility;

    /**
     * 変換ユーティリティ
     */
    private final ConversionUtility conversionUtility;

    /**
     * 金額計算のUtilityクラス
     */
    private final CalculatePriceUtility calculatePriceUtility;

    /**
     * 入荷お知らせDtoリストに変換
     *
     * @param restockAnnounceDtoResponseList 入荷お知らせ情報DtoListレスポンス
     * @return 入荷お知らせDtoリスト
     */
    public List<RestockAnnounceDto> toRestockAnnounceDtoList(List<RestockAnnounceDtoResponse> restockAnnounceDtoResponseList) {

        if (CollectionUtil.isEmpty(restockAnnounceDtoResponseList)) {
            return new ArrayList<>();
        }

        List<RestockAnnounceDto> restockAnnounceDtoList = new ArrayList<>();

        restockAnnounceDtoResponseList.forEach(restockAnnounceDtoResponse -> {
            RestockAnnounceDto restockAnnounceDto = new RestockAnnounceDto();

            restockAnnounceDto.setRestockAnnounceEntity(
                            toRestockAnnounceEntity(restockAnnounceDtoResponse.getRestockAnnounceEntityResponse()));
            restockAnnounceDto.setGoodsDetailsDto(
                            toGoodsDetailsDto(restockAnnounceDtoResponse.getGoodsDetailsDtoResponse()));
            restockAnnounceDto.setGoodsGroupImageEntityList(toRestockAnnounceGoodsGroupImageEntityList(
                            restockAnnounceDtoResponse.getGoodsGroupImageEntityListResponse()));
            restockAnnounceDto.setStockStatus(restockAnnounceDtoResponse.getStockStatus());

            restockAnnounceDtoList.add(restockAnnounceDto);
        });

        return restockAnnounceDtoList;
    }

    /**
     * 商品グループ画像リストに変換
     *
     * @param goodsGroupImageEntityResponseList 商品グループ画像リストレスポンス
     * @return 商品グループ画像リスト
     */
    private List<GoodsGroupImageEntity> toRestockAnnounceGoodsGroupImageEntityList(List<jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsGroupImageEntityResponse> goodsGroupImageEntityResponseList) {

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
                        toFavoriteGoodsGroupImageEntityList(goodsDetailsDtoResponse.getGoodsGroupImageEntityList()));
        goodsDetailsDto.setUnitImage(toGoodsImageEntityResponse(goodsDetailsDtoResponse.getUnitImage()));

        // 2023-renew AddNo5 from here
        goodsDetailsDto.setGoodsPriceInTaxLow(goodsDetailsDtoResponse.getGoodsPriceInTaxLow());
        goodsDetailsDto.setGoodsPriceInTaxHight(goodsDetailsDtoResponse.getGoodsPriceInTaxHight());
        goodsDetailsDto.setPreDiscountPriceLow(goodsDetailsDtoResponse.getPreDiscountPriceLow());
        goodsDetailsDto.setPreDiscountPriceHight(goodsDetailsDtoResponse.getPreDiscountPriceHight());
        // 2023-renew AddNo5 to here
        return goodsDetailsDto;
    }

    /**
     * 商品画像に変換
     *
     * @param goodsImageEntityResponse 商品グループ画像レスポンス
     * @return 商品画像
     */
    private GoodsImageEntity toGoodsImageEntityResponse(jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsImageEntityResponse goodsImageEntityResponse) {

        if (ObjectUtils.isEmpty(goodsImageEntityResponse)) {
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
     * 入荷お知らせエンティティに変換
     *
     * @param restockAnnounceEntityResponse 入荷お知らせクラスレスポンス
     * @return 入荷お知らせエンティティ
     */
    public RestockAnnounceEntity toRestockAnnounceEntity(RestockAnnounceEntityResponse restockAnnounceEntityResponse) {

        if (restockAnnounceEntityResponse == null) {
            return null;
        }

        RestockAnnounceEntity restockAnnounceEntity = new RestockAnnounceEntity();
        restockAnnounceEntity.setMemberInfoSeq(restockAnnounceEntityResponse.getMemberInfoSeq());
        restockAnnounceEntity.setGoodsSeq(restockAnnounceEntityResponse.getGoodsSeq());
        restockAnnounceEntity.setRegistTime(
                        conversionUtility.toTimeStamp(restockAnnounceEntityResponse.getRegistTime()));
        restockAnnounceEntity.setUpdateTime(
                        conversionUtility.toTimeStamp(restockAnnounceEntityResponse.getUpdateTime()));

        return restockAnnounceEntity;
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
     * 画面表示・再表示(入荷お知らせ情報)
     *
     * @param restockAnnounceDtoList 入荷お知らせDTOリスト
     * @param memberModel 商品詳細画面 Model
     * @return 在庫商品Itemリスト
     */
    public List<GoodsStockItem> toPageForLoadRestockAnnounce(List<RestockAnnounceDto> restockAnnounceDtoList,
                                                             MemberModel memberModel,
                                                             List<WebApiGetStockResponseDetailDto> webApiGetStockResponseDetailDtos) {

        List<GoodsStockItem> goodsItems = new ArrayList<>();
        for (RestockAnnounceDto restockAnnounceDto : restockAnnounceDtoList) {
            // 入荷お知らせ商品情報取得
            GoodsDetailsDto goodsDetailsDto = restockAnnounceDto.getGoodsDetailsDto();

            // ページアイテムクラスにセット
            GoodsStockItem goodsItem = ApplicationContextUtility.getBean(GoodsStockItem.class);
            WebApiGetStockResponseDetailDto webApiDto = webApiGetStockResponseDetailDtos.stream()
                                                                                        .filter(dto -> dto.getGoodsCode()
                                                                                                          .equals(goodsDetailsDto.getGoodsCode()))
                                                                                        .findFirst()
                                                                                        .orElse(null);

            if (webApiDto != null) {
                goodsItem.setSaleControl(webApiDto.getSaleControl());
            }

            if (goodsDetailsDto != null) {
                goodsItem.setGoodsGroupSeq(goodsDetailsDto.getGoodsGroupSeq());
                goodsItem.setGcd(goodsDetailsDto.getGoodsCode());
                goodsItem.setGoodsCode(goodsDetailsDto.getGoodsCode());
                goodsItem.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
                goodsItem.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
                goodsItem.setUnitValue1(goodsDetailsDto.getUnitValue1());
                goodsItem.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
                goodsItem.setUnitValue2(goodsDetailsDto.getUnitValue2());
                goodsItem.setGoodsPreDiscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());
                goodsItem.setPreDiscountPrice(goodsDetailsDto.getPreDiscountPrice());
                goodsItem.setGoodsPrice(goodsDetailsDto.getGoodsPrice());
                goodsItem.setGoodsPriceInTaxLow(goodsDetailsDto.getGoodsPriceInTaxLow());
                goodsItem.setGoodsPriceInTaxHight(goodsDetailsDto.getGoodsPriceInTaxHight());
                goodsItem.setPreDiscountPriceLow(goodsDetailsDto.getPreDiscountPriceLow());
                goodsItem.setPreDiscountPriceHight(goodsDetailsDto.getPreDiscountPriceHight());

                // 税率、税種別の変換
                goodsItem.setTaxRate(goodsDetailsDto.getTaxRate());
                goodsItem.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType());

                // 税込価格の計算
                goodsItem.setPreDiscountPriceInTax(
                                calculatePriceUtility.getTaxIncludedPrice(goodsItem.getPreDiscountPrice(),
                                                                          goodsItem.getTaxRate()
                                                                         ));

                // 新着画像の表示期間を取得
                Timestamp whatsnewDate = goodsUtility.getRealWhatsNewDate(goodsDetailsDto.getWhatsnewDate());
                goodsItem.setWhatsnewDate(whatsnewDate);

                goodsItem.setGoodsOpenStatus(goodsDetailsDto.getGoodsOpenStatusPC());
                goodsItem.setOpenStartTime(goodsDetailsDto.getOpenStartTimePC());
                goodsItem.setOpenEndTime(goodsDetailsDto.getOpenEndTimePC());

                goodsItem.setSaleStatus(goodsDetailsDto.getSaleStatusPC());
                goodsItem.setSaleStartTime(goodsDetailsDto.getSaleStartTimePC());
                goodsItem.setSaleEndTime(goodsDetailsDto.getSaleEndTimePC());

                goodsItem.setGoodsSeq(goodsDetailsDto.getGoodsSeq());

                // 商品画像リストを取り出す。
                List<String> goodsImageList = new ArrayList<>();
                String goodsItemImageFileName = null;
                if (goodsDetailsDto.getUnitImage() != null) {
                    goodsItemImageFileName = goodsDetailsDto.getUnitImage().getImageFileName();
                    goodsImageList.add(goodsUtility.getGoodsImagePath(goodsItemImageFileName));
                } else {
                    if (goodsDetailsDto.getGoodsGroupImageEntityList() != null) {
                        for (GoodsGroupImageEntity goodsGroupImageEntity : goodsDetailsDto.getGoodsGroupImageEntityList()) {
                            goodsImageList.add(
                                            goodsUtility.getGoodsImagePath(goodsGroupImageEntity.getImageFileName()));
                        }
                    }
                }

                goodsItem.setGoodsImageItems(goodsImageList);

                // PDR Migrate Customization from here
                // SALEアイコンフラグ
                goodsItem.setSaleIconFlag(HTypeSaleIconFlag.ON.equals(goodsDetailsDto.getSaleIconFlag()));
                // 取りおきアイコンフラグ
                goodsItem.setReserveIconFlag(HTypeReserveIconFlag.ON.equals(goodsDetailsDto.getReserveIconFlag()));
                // NEWアイコンフラグ
                goodsItem.setNewIconFlag(HTypeNewIconFlag.ON.equals(goodsDetailsDto.getNewIconFlag()));
                // アウトレットアイコンフラグ
                goodsItem.setOutletIconFlag(HTypeOutletIconFlag.ON.equals(goodsDetailsDto.getOutletIconFlag()));

                // 商品表示単
                if (HTypePriceMarkDispFlag.ON.equals(goodsDetailsDto.getPriceMarkDispFlag())) {
                    goodsItem.setPriceMarkDispFlag(true);
                }
                // 商品表示値引き後単価PC
                if (HTypeSalePriceMarkDispFlag.ON.equals(goodsDetailsDto.getSalePriceMarkDispFlag())) {
                    goodsItem.setSalePriceMarkDispFlag(true);
                }

                // セール価格整合性フラグ
                goodsItem.setSalePriceIntegrityFlag(
                                HTypeSalePriceIntegrityFlag.MATCH.equals(goodsDetailsDto.getSalePriceIntegrityFlag()));
                // PDR Migrate Customization to here
            }

            // 在庫状況表示
            goodsItem.setStockStatusPc(restockAnnounceDto.getStockStatus());
            // アイコン情報の取得
            goodsItem.setGoodsIconItems(
                            createGoodsIconList(restockAnnounceDto.getGoodsInformationIconDetailsDtoList()));

            goodsItems.add(goodsItem);

        }

        return goodsItems;
    }

    /**
     * アイコン情報を設定
     *
     * @param goodsInformationIconDetailsDtoList 登録されているアイコン情報
     * @return 画面表示用に作成したアイコンリスト
     */
    protected List<GoodsIconItem> createGoodsIconList(List<GoodsInformationIconDetailsDto> goodsInformationIconDetailsDtoList) {
        List<GoodsIconItem> goodsIconList = new ArrayList<>();
        if (goodsInformationIconDetailsDtoList != null) {
            for (GoodsInformationIconDetailsDto goodsInformationIconDetailsDto : goodsInformationIconDetailsDtoList) {
                GoodsIconItem goodsIconItem = ApplicationContextUtility.getBean(GoodsIconItem.class);
                goodsIconItem.setIconName(goodsInformationIconDetailsDto.getIconName());
                goodsIconItem.setIconColorCode(goodsInformationIconDetailsDto.getColorCode());

                goodsIconList.add(goodsIconItem);
            }
        }
        return goodsIconList;
    }

    /**
     * 入荷お知らせ情報リスト取得リクエストに変換
     *
     * @param conditionDto 入荷お知らせDao用検索条件Dtoクラス
     * @return 入荷お知らせ情報リスト取得リクエスト
     */
    public RestockAnnounceListGetRequest toRestockAnnounceListGetRequest(RestockAnnounceSearchForDaoConditionDto conditionDto) {

        if (conditionDto == null) {
            return null;
        }

        RestockAnnounceListGetRequest restockAnnounceListGetRequest = new RestockAnnounceListGetRequest();
        restockAnnounceListGetRequest.setMemberInfoSeq(conditionDto.getMemberInfoSeq());
        restockAnnounceListGetRequest.setExclusionGoodsSeqList(conditionDto.getExclusionGoodsSeqList());
        restockAnnounceListGetRequest.setSiteType(HTypeSiteType.FRONT_PC.getValue());
        if (conditionDto.getGoodsOpenStatus() != null) {
            restockAnnounceListGetRequest.setGoodsOpenStatus(conditionDto.getGoodsOpenStatus().getValue());
        }

        restockAnnounceListGetRequest.setRestockstatus(conditionDto.getRestockstatus());

        return restockAnnounceListGetRequest;
    }
    // 2023-renew No71 from here

    /**
     * 作成リクエスト更新アナウンス
     * @param isTopSale トップセールを更新
     * @param isTopStock 上位株を更新
     * */
    public MemberInfoAnnounceUpdateRequest createRequestUpdateAnnounce(Boolean isTopSale, Boolean isTopStock) {

        CommonInfo commonInfo = ApplicationContextUtility.getBean(CommonInfo.class);
        MemberInfoAnnounceUpdateRequest request = new MemberInfoAnnounceUpdateRequest();
        request.setMemberInfoSeq(commonInfo.getCommonInfoUser().getMemberInfoSeq());

        if (HTypeTopSaleAnnounceFlg.ON.equals(commonInfo.getCommonInfoBase().getTopSaleAnnounceFlg()) && isTopSale) {
            request.setSaleAnnounceWatchFlg(HTypeSaleAnnounceWatchFlg.READ.getValue());
        }
        if (HTypeTopStockAnnounceFlg.ON.equals(commonInfo.getCommonInfoBase().getTopStockAnnounceFlg()) && isTopStock) {
            request.setStockAnnounceWatchFlg(HTypeStockAnnounceWatchFlg.READ.getValue());
        }
        return request;
    }

    /**
     * セッションへ既読フラグを更新
     * */
    public void updateCommonInfoAnnounceStatus(Boolean isTopSale, Boolean isTopStock) {
        CommonInfo commonInfo = ApplicationContextUtility.getBean(CommonInfo.class);
        if (HTypeTopSaleAnnounceFlg.ON.equals(commonInfo.getCommonInfoBase().getTopSaleAnnounceFlg()) && isTopSale) {
            commonInfo.getCommonInfoBase().setSaleAnnounceWatchFlg(HTypeSaleAnnounceWatchFlg.READ);
        }
        if (HTypeTopStockAnnounceFlg.ON.equals(commonInfo.getCommonInfoBase().getTopStockAnnounceFlg()) && isTopStock) {
            commonInfo.getCommonInfoBase().setStockAnnounceWatchFlg(HTypeStockAnnounceWatchFlg.READ);
        }
    }

    // 2023-renew No22 from here

    /**
     * ファイルの名前を変更
     *
     * @param fileName ファイル名
     * @return ファイル名が変更されました
     */
    public String renameFile(String fileName) {
        if (fileName == null)
            return "";
        String[] parts = fileName.split("\\.");
        // 拡張子があるかどうかを確認する
        String extension = parts[parts.length - 1];

        int dotIndex = fileName.lastIndexOf('.');
        String name = fileName.substring(0, dotIndex);
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);
        String date = dateFormat.format(new Date());

        return name + "_" + date + "." + extension;
    }
    // 2023-renew No22 to here

    // 2023-renew No71 to here

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
            // 2023-renew No11 from here
            webApiGetStockResponseDetailDto.setSaleControl(EnumTypeUtil.getEnumFromValue(HTypeSaleControlType.class,
                                                                                         webApiGetStockResponseDetailDtoResponse.getSaleControl()
                                                                                        ));
            // 2023-renew No11 to here

            webApiGetStockResponseDetailDtoList.add(webApiGetStockResponseDetailDto);
        });

        return webApiGetStockResponseDetailDtoList;
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
     * お気に入り商品検索条件Dtoの作成
     *
     * @param memberModel    Model
     * @param favoriteGoodsLimit お気に入り商品件数
     * @return お気に入り検索条件Dto
     */
    public FavoriteSearchForDaoConditionDto toFavoriteConditionDtoForSearchFavoriteList(int favoriteGoodsLimit,
                                                                                        MemberModel memberModel) {
        // お気に入り検索条件Dtoの作成 公開状態＝指定なし
        FavoriteSearchForDaoConditionDto favoriteConditionDto =
                        ApplicationContextUtility.getBean(FavoriteSearchForDaoConditionDto.class);
        favoriteConditionDto.setMemberInfoSeq(memberModel.getCommonInfo().getCommonInfoUser().getMemberInfoSeq());
        // PageInfoHelper取得
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        // ページングセットアップ
        favoriteConditionDto =
                        pageInfoHelper.setupPageInfoForSkipCount(favoriteConditionDto, favoriteGoodsLimit, "updateTime",
                                                                 false
                                                                );
        return favoriteConditionDto;
    }

    /**
     * お気に入り情報リスト取得リクエストに変換
     *
     * @param favoriteConditionDto お気に入りDao用検索条件Dto
     * @return お気に入り情報リスト取得リクエスト
     */
    public FavoriteListGetRequest toFavoriteListGetRequest(FavoriteSearchForDaoConditionDto favoriteConditionDto) {

        if (ObjectUtils.isEmpty(favoriteConditionDto)) {
            return null;
        }

        FavoriteListGetRequest favoriteListGetRequest = new FavoriteListGetRequest();

        favoriteListGetRequest.setMemberInfoSeq(favoriteConditionDto.getMemberInfoSeq());
        favoriteListGetRequest.setExclusionGoodsSeqList(favoriteConditionDto.getExclusionGoodsSeqList());
        favoriteListGetRequest.setSiteType(HTypeSiteType.FRONT_PC.getValue());
        if (favoriteConditionDto.getGoodsOpenStatus() != null) {
            favoriteListGetRequest.setGoodsOpenStatus(favoriteConditionDto.getGoodsOpenStatus().getValue());
        }
        if (favoriteConditionDto.getMemberRank() != null) {
            favoriteListGetRequest.setMemberRank(favoriteConditionDto.getMemberRank().getValue());
        }

        return favoriteListGetRequest;
    }

    /**
     * お気に入りDtoリストに変換
     *
     * @param favoriteDtoResponseList お気に入り情報DtoListレスポンス
     * @return お気に入りDtoリスト
     */
    public List<FavoriteDto> toFavoriteDtoList(List<FavoriteDtoResponse> favoriteDtoResponseList) {

        if (CollectionUtil.isEmpty(favoriteDtoResponseList)) {
            return new ArrayList<>();
        }

        List<FavoriteDto> favoriteDtoList = new ArrayList<>();

        favoriteDtoResponseList.forEach(favoriteDtoResponse -> {
            FavoriteDto favoriteDto = new FavoriteDto();

            favoriteDto.setFavoriteEntity(toFavoriteEntity(favoriteDtoResponse.getFavoriteEntityResponse()));
            favoriteDto.setGoodsDetailsDto(toGoodsDetailsDto(favoriteDtoResponse.getGoodsDetailsDtoResponse()));
            favoriteDto.setGoodsGroupImageEntityList(toFavoriteGoodsGroupImageEntityList(
                            favoriteDtoResponse.getGoodsGroupImageEntityListResponse()));
            favoriteDto.setStockStatus(favoriteDtoResponse.getStockStatus());

            favoriteDtoList.add(favoriteDto);
        });

        return favoriteDtoList;
    }

    /**
     * お気に入りに変換
     *
     * @param favoriteEntityResponse お気に入りクラスレスポンス
     * @return お気に入り
     */
    private FavoriteEntity toFavoriteEntity(FavoriteEntityResponse favoriteEntityResponse) {

        if (ObjectUtils.isEmpty(favoriteEntityResponse)) {
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
     * 商品グループ画像リストに変換
     *
     * @param goodsGroupImageEntityResponseList 商品グループ画像リストレスポンス
     * @return 商品グループ画像リスト
     */
    private List<GoodsGroupImageEntity> toFavoriteGoodsGroupImageEntityList(List<jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsGroupImageEntityResponse> goodsGroupImageEntityResponseList) {

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
     * 画面表示・再表示(お気に入り情報)
     *
     * @param favoriteDtoList お気に入りDTOリスト
     * @param memberModel 商品詳細画面 Model
     */
    public void toPageForLoadFavorite(List<FavoriteDto> favoriteDtoList, MemberModel memberModel) {
        // 2023-renew AddNo5 from here
        List<GoodsStockItem> goodsItems = new ArrayList<>();
        // 2023-renew AddNo5 to here
        for (FavoriteDto favoriteDto : favoriteDtoList) {
            // お気に入り商品情報取得
            GoodsDetailsDto goodsDetailsDto = favoriteDto.getGoodsDetailsDto();

            // ページアイテムクラスにセット
            // 2023-renew AddNo5 from here
            GoodsStockItem goodsItem = ApplicationContextUtility.getBean(GoodsStockItem.class);
            // 2023-renew AddNo5 to here
            if (goodsDetailsDto != null) {
                goodsItem.setGoodsGroupSeq(goodsDetailsDto.getGoodsGroupSeq());
                goodsItem.setGcd(goodsDetailsDto.getGoodsCode());
                // 2023-renew No11 from here
                goodsItem.setGoodsCode(goodsDetailsDto.getGoodsCode());
                // 2023-renew No11 to here
                goodsItem.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
                goodsItem.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
                goodsItem.setUnitValue1(goodsDetailsDto.getUnitValue1());
                goodsItem.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
                goodsItem.setUnitValue2(goodsDetailsDto.getUnitValue2());
                goodsItem.setGoodsPreDiscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());
                goodsItem.setPreDiscountPrice(goodsDetailsDto.getPreDiscountPrice());
                goodsItem.setGoodsPrice(goodsDetailsDto.getGoodsPrice());

                // 税率、税種別の変換
                goodsItem.setTaxRate(goodsDetailsDto.getTaxRate());
                goodsItem.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType());

                // 税込価格の計算
                goodsItem.setPreDiscountPriceInTax(
                                calculatePriceUtility.getTaxIncludedPrice(goodsItem.getPreDiscountPrice(),
                                                                          goodsItem.getTaxRate()
                                                                         ));

                // 新着画像の表示期間を取得
                Timestamp whatsnewDate = goodsUtility.getRealWhatsNewDate(goodsDetailsDto.getWhatsnewDate());
                goodsItem.setWhatsnewDate(whatsnewDate);

                goodsItem.setGoodsOpenStatus(goodsDetailsDto.getGoodsOpenStatusPC());
                goodsItem.setOpenStartTime(goodsDetailsDto.getOpenStartTimePC());
                goodsItem.setOpenEndTime(goodsDetailsDto.getOpenEndTimePC());

                goodsItem.setSaleStatus(goodsDetailsDto.getSaleStatusPC());
                goodsItem.setSaleStartTime(goodsDetailsDto.getSaleStartTimePC());
                goodsItem.setSaleEndTime(goodsDetailsDto.getSaleEndTimePC());

                goodsItem.setGoodsSeq(goodsDetailsDto.getGoodsSeq());

                // 商品画像リストを取り出す。
                List<String> goodsImageList = new ArrayList<>();
                String goodsItemImageFileName = null;
                if (goodsDetailsDto.getUnitImage() != null) {
                    goodsItemImageFileName = goodsDetailsDto.getUnitImage().getImageFileName();
                    goodsImageList.add(goodsUtility.getGoodsImagePath(goodsItemImageFileName));
                } else {
                    if (goodsDetailsDto.getGoodsGroupImageEntityList() != null) {
                        for (GoodsGroupImageEntity goodsGroupImageEntity : goodsDetailsDto.getGoodsGroupImageEntityList()) {
                            goodsImageList.add(
                                            goodsUtility.getGoodsImagePath(goodsGroupImageEntity.getImageFileName()));
                        }
                    }
                }

                goodsItem.setGoodsImageItems(goodsImageList);

                // PDR Migrate Customization from here
                // SALEアイコンフラグ
                goodsItem.setSaleIconFlag(
                                HTypeSaleIconFlag.ON.equals(goodsDetailsDto.getSaleIconFlag()) ? true : false);
                // 取りおきアイコンフラグ
                goodsItem.setReserveIconFlag(
                                HTypeReserveIconFlag.ON.equals(goodsDetailsDto.getReserveIconFlag()) ? true : false);
                // NEWアイコンフラグ
                goodsItem.setNewIconFlag(HTypeNewIconFlag.ON.equals(goodsDetailsDto.getNewIconFlag()) ? true : false);
                // 2023-renew No92 from here
                // アウトレットアイコンフラグ
                goodsItem.setOutletIconFlag(
                                HTypeOutletIconFlag.ON.equals(goodsDetailsDto.getOutletIconFlag()) ? true : false);
                // 2023-renew No92 to here
                // 商品表示単
                if (HTypePriceMarkDispFlag.ON.equals(goodsDetailsDto.getPriceMarkDispFlag())) {
                    goodsItem.setPriceMarkDispFlag(true);
                }
                // 商品表示値引き後単価PC
                if (HTypeSalePriceMarkDispFlag.ON.equals(goodsDetailsDto.getSalePriceMarkDispFlag())) {
                    goodsItem.setSalePriceMarkDispFlag(true);
                }

                // セール価格整合性フラグ
                goodsItem.setSalePriceIntegrityFlag(
                                HTypeSalePriceIntegrityFlag.MATCH.equals(goodsDetailsDto.getSalePriceIntegrityFlag()) ?
                                                true :
                                                false);

                // 2023-renew AddNo5 from here
                goodsItem.setGoodsPriceInTaxLow(goodsDetailsDto.getGoodsPriceInTaxLow());
                goodsItem.setGoodsPriceInTaxHight(goodsDetailsDto.getGoodsPriceInTaxHight());
                goodsItem.setPreDiscountPriceLow(goodsDetailsDto.getPreDiscountPriceLow());
                goodsItem.setPreDiscountPriceHight(goodsDetailsDto.getPreDiscountPriceHight());
                // 2023-renew AddNo5 to here
                // PDR Migrate Customization to here
            }

            // 在庫状況表示
            goodsItem.setStockStatusPc(favoriteDto.getStockStatus());
            // アイコン情報の取得
            goodsItem.setGoodsIconItems(createGoodsIconList(favoriteDto.getGoodsInformationIconDetailsDtoList()));

            goodsItems.add(goodsItem);

        }

        memberModel.setFavoriteAnnounceGoodsItems(goodsItems);
    }

    /**
     * お気に入りDtoリストリクエストに変換
     *
     * @param favoriteDtoList お気に入りDtoリスト
     * @return お気に入りDtoリストリクエスト
     */
    public List<FavoriteDtoRequest> toFavoriteDtoListRequest(List<FavoriteDto> favoriteDtoList) {

        if (CollectionUtil.isEmpty(favoriteDtoList)) {
            return new ArrayList<>();
        }

        List<FavoriteDtoRequest> favoriteDtoRequestList = new ArrayList<>();

        favoriteDtoList.forEach(favoriteDto -> {

            FavoriteDtoRequest favoriteDtoRequest = new FavoriteDtoRequest();

            favoriteDtoRequest.setFavoriteEntityRequest(toFavoriteEntityRequest(favoriteDto.getFavoriteEntity()));
            favoriteDtoRequest.setGoodsDetailsDtoRequest(toGoodsDetailsDtoRequest(favoriteDto.getGoodsDetailsDto()));
            favoriteDtoRequest.setGoodsGroupImageEntityListRequest(
                            toGoodsGroupImageEntityListRequest(favoriteDto.getGoodsGroupImageEntityList()));
            favoriteDtoRequest.setStockStatus(favoriteDto.getStockStatus());

            favoriteDtoRequestList.add(favoriteDtoRequest);
        });

        return favoriteDtoRequestList;
    }

    /**
     * お気に入りクラスリクエストに変換
     *
     * @param favoriteEntity お気に入り
     * @return お気に入りクラスリクエスト
     */
    private FavoriteEntityRequest toFavoriteEntityRequest(FavoriteEntity favoriteEntity) {

        if (ObjectUtils.isEmpty(favoriteEntity)) {
            return null;
        }

        FavoriteEntityRequest favoriteEntityRequest = new FavoriteEntityRequest();

        favoriteEntityRequest.setMemberInfoSeq(favoriteEntity.getMemberInfoSeq());
        favoriteEntityRequest.setGoodsSeq(favoriteEntity.getGoodsSeq());
        favoriteEntityRequest.setRegistTime(favoriteEntity.getRegistTime());
        favoriteEntityRequest.setUpdateTime(favoriteEntity.getUpdateTime());

        return favoriteEntityRequest;
    }

    /**
     * 商品詳細Dtoクラスリクエストに変換
     *
     * @param goodsDetailsDto 商品詳細Dto
     * @return 商品詳細Dtoクラスリクエスト
     */
    private GoodsDetailsDtoRequest toGoodsDetailsDtoRequest(GoodsDetailsDto goodsDetailsDto) {

        if (ObjectUtils.isEmpty(goodsDetailsDto)) {
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
        goodsDetailsDtoRequest.setTaxRate(goodsDetailsDto.getTaxRate());
        goodsDetailsDtoRequest.setGoodsPriceInTax(goodsDetailsDto.getGoodsPriceInTax());
        goodsDetailsDtoRequest.setGoodsPrice(goodsDetailsDto.getGoodsPrice());
        goodsDetailsDtoRequest.setDeliveryType(goodsDetailsDto.getDeliveryType());
        goodsDetailsDtoRequest.setSaleStartTime(goodsDetailsDto.getSaleStartTimePC());
        goodsDetailsDtoRequest.setSaleEndTime(goodsDetailsDto.getSaleEndTimePC());
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
        goodsDetailsDtoRequest.setWhatsnewDate(goodsDetailsDto.getWhatsnewDate());
        goodsDetailsDtoRequest.setOpenStartTime(goodsDetailsDto.getOpenStartTimePC());
        goodsDetailsDtoRequest.setOpenEndTime(goodsDetailsDto.getOpenEndTimePC());
        goodsDetailsDtoRequest.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
        goodsDetailsDtoRequest.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
        goodsDetailsDtoRequest.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
        goodsDetailsDtoRequest.setGoodsPreDiscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());
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

        if (goodsDetailsDto.getGoodsTaxType() != null) {
            goodsDetailsDtoRequest.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType().getValue());
        }
        if (goodsDetailsDto.getAlcoholFlag() != null) {
            goodsDetailsDtoRequest.setAlcoholFlag(goodsDetailsDto.getAlcoholFlag().getValue());
        }
        if (goodsDetailsDto.getSaleStatusPC() != null) {
            goodsDetailsDtoRequest.setSaleStatus(goodsDetailsDto.getSaleStatusPC().getValue());
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
        if (goodsDetailsDto.getStockStatusPc() != null) {
            goodsDetailsDtoRequest.setStockStatus(goodsDetailsDto.getStockStatusPc().getValue());
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
        // 2023-renew AddNo5 from here
        goodsDetailsDtoRequest.setGoodsPriceInTaxLow(goodsDetailsDto.getGoodsPriceInTaxLow());
        goodsDetailsDtoRequest.setGoodsPriceInTaxHight(goodsDetailsDto.getGoodsPriceInTaxHight());
        goodsDetailsDtoRequest.setPreDiscountPriceLow(goodsDetailsDto.getPreDiscountPriceLow());
        goodsDetailsDtoRequest.setPreDiscountPriceHight(goodsDetailsDto.getPreDiscountPriceHight());
        // 2023-renew AddNo5 to here

        goodsDetailsDtoRequest.setGoodsGroupImageEntityList(
                        toGoodsGroupImageEntityListRequest(goodsDetailsDto.getGoodsGroupImageEntityList()));
        goodsDetailsDtoRequest.setUnitImage(toGoodsImageEntityRequest(goodsDetailsDto.getUnitImage()));

        return goodsDetailsDtoRequest;
    }

    /**
     * 商品グループ画像リストリクエストに変換
     *
     * @param goodsGroupImageEntityList 商品グループ画像リスト
     * @return 商品グループ画像リストリクエスト
     */
    private List<GoodsGroupImageEntityRequest> toGoodsGroupImageEntityListRequest(List<GoodsGroupImageEntity> goodsGroupImageEntityList) {

        if (CollectionUtil.isEmpty(goodsGroupImageEntityList)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntityRequest> goodsGroupImageEntityRequestList = new ArrayList<>();

        goodsGroupImageEntityList.forEach(goodsGroupImageEntity -> {
            GoodsGroupImageEntityRequest goodsGroupImageEntityRequest = new GoodsGroupImageEntityRequest();

            goodsGroupImageEntityRequest.setGoodsGroupSeq(goodsGroupImageEntity.getGoodsGroupSeq());
            goodsGroupImageEntityRequest.setImageTypeVersionNo(goodsGroupImageEntity.getImageTypeVersionNo());
            goodsGroupImageEntityRequest.setImageFileName(goodsGroupImageEntity.getImageFileName());
            goodsGroupImageEntityRequest.setRegistTime(goodsGroupImageEntity.getRegistTime());
            goodsGroupImageEntityRequest.setUpdateTime(goodsGroupImageEntity.getUpdateTime());

            goodsGroupImageEntityRequestList.add(goodsGroupImageEntityRequest);
        });

        return goodsGroupImageEntityRequestList;
    }

    /**
     * 商品クラスリクエストに変換
     *
     * @param goodsEntity 商品
     * @return 商品クラスリクエスト
     */
    private GoodsEntityRequest toGoodsEntityRequest(GoodsEntity goodsEntity) {

        if (ObjectUtils.isEmpty(goodsEntity)) {
            return null;
        }

        GoodsEntityRequest goodsEntityRequest = new GoodsEntityRequest();

        goodsEntityRequest.setGoodsSeq(goodsEntity.getGoodsSeq());
        goodsEntityRequest.setGoodsGroupSeq(goodsEntity.getGoodsGroupSeq());
        goodsEntityRequest.setGoodsCode(goodsEntity.getGoodsCode());
        goodsEntityRequest.setJanCode(goodsEntity.getJanCode());
        goodsEntityRequest.setCatalogCode(goodsEntity.getCatalogCode());
        goodsEntityRequest.setSaleStartTime(goodsEntity.getSaleStartTimePC());
        goodsEntityRequest.setSaleEndTime(goodsEntity.getSaleEndTimePC());
        goodsEntityRequest.setGoodsPrice(goodsEntity.getGoodsPrice());
        goodsEntityRequest.setPreDiscountPrice(goodsEntity.getPreDiscountPrice());
        goodsEntityRequest.setUnitValue1(goodsEntity.getUnitValue1());
        goodsEntityRequest.setUnitValue2(goodsEntity.getUnitValue2());
        goodsEntityRequest.setPurchasedMax(goodsEntity.getPurchasedMax());
        goodsEntityRequest.setOrderDisplay(goodsEntity.getOrderDisplay());
        goodsEntityRequest.setVersionNo(goodsEntity.getVersionNo());
        goodsEntityRequest.setRegistTime(goodsEntity.getRegistTime());
        goodsEntityRequest.setUpdateTime(goodsEntity.getUpdateTime());
        goodsEntityRequest.setUnit(goodsEntity.getUnit());
        goodsEntityRequest.setGoodsManagementCode(goodsEntity.getGoodsManagementCode());
        goodsEntityRequest.setGoodsDivisionCode(goodsEntity.getGoodsDivisionCode());
        goodsEntityRequest.setGoodsCategory1(goodsEntity.getGoodsCategory1());
        goodsEntityRequest.setGoodsCategory2(goodsEntity.getGoodsCategory2());
        goodsEntityRequest.setGoodsCategory3(goodsEntity.getGoodsCategory3());
        goodsEntityRequest.setCoolSendFrom(goodsEntity.getCoolSendFrom());
        goodsEntityRequest.setCoolSendTo(goodsEntity.getCoolSendTo());

        if (goodsEntity.getSaleStatusPC() != null) {
            goodsEntityRequest.setSaleStatus(goodsEntity.getSaleStatusPC().getValue());
        }
        if (goodsEntity.getIndividualDeliveryType() != null) {
            goodsEntityRequest.setIndividualDeliveryType(goodsEntity.getIndividualDeliveryType().getValue());
        }
        if (goodsEntity.getFreeDeliveryFlag() != null) {
            goodsEntityRequest.setFreeDeliveryFlag(goodsEntity.getFreeDeliveryFlag().getValue());
        }
        if (goodsEntity.getUnitManagementFlag() != null) {
            goodsEntityRequest.setUnitManagementFlag(goodsEntity.getUnitManagementFlag().getValue());
        }
        if (goodsEntity.getStockManagementFlag() != null) {
            goodsEntityRequest.setStockManagementFlag(goodsEntity.getStockManagementFlag().getValue());
        }
        if (goodsEntity.getReserveFlag() != null) {
            goodsEntityRequest.setReserveFlag(goodsEntity.getReserveFlag().getValue());
        }
        if (goodsEntity.getPriceMarkDispFlag() != null) {
            goodsEntityRequest.setPriceMarkDispFlag(goodsEntity.getPriceMarkDispFlag().getValue());
        }
        if (goodsEntity.getSalePriceMarkDispFlag() != null) {
            goodsEntityRequest.setSalePriceMarkDispFlag(goodsEntity.getSalePriceMarkDispFlag().getValue());
        }
        if (goodsEntity.getLandSendFlag() != null) {
            goodsEntityRequest.setLandSendFlag(goodsEntity.getLandSendFlag().getValue());
        }
        if (goodsEntity.getCoolSendFlag() != null) {
            goodsEntityRequest.setCoolSendFlag(goodsEntity.getCoolSendFlag().getValue());
        }
        if (goodsEntity.getSalePriceIntegrityFlag() != null) {
            goodsEntityRequest.setSalePriceIntegrityFlag(goodsEntity.getSalePriceIntegrityFlag().getValue());
        }
        if (goodsEntity.getEmotionPriceType() != null) {
            goodsEntityRequest.setEmotionPriceType(goodsEntity.getEmotionPriceType().getValue());
        }

        return goodsEntityRequest;
    }

    /**
     * 在庫Dtoクラスリクエストに変換
     *
     * @param stockDto 在庫Dto
     * @return 在庫Dtoクラスリクエスト
     */
    private StockDtoRequest toStockDtoRequest(StockDto stockDto) {

        if (ObjectUtils.isEmpty(stockDto)) {
            return null;
        }

        StockDtoRequest stockDtoRequest = new StockDtoRequest();

        stockDtoRequest.setGoodsSeq(stockDto.getGoodsSeq());
        stockDtoRequest.setSalesPossibleStock(stockDto.getSalesPossibleStock());
        stockDtoRequest.setRealStock(stockDto.getRealStock());
        stockDtoRequest.setOrderReserveStock(stockDto.getOrderReserveStock());
        stockDtoRequest.setRemainderFewStock(stockDto.getRemainderFewStock());
        stockDtoRequest.setSupplementCount(stockDto.getSupplementCount());
        stockDtoRequest.setOrderPointStock(stockDto.getOrderPointStock());
        stockDtoRequest.setSafetyStock(stockDto.getSafetyStock());
        stockDtoRequest.setRegistTime(stockDto.getRegistTime());
        stockDtoRequest.setUpdateTime(stockDto.getUpdateTime());

        return stockDtoRequest;
    }

    /**
     * 商品グループ画像リクエストに変換
     *
     * @param goodsImageEntity 商品画像
     * @return 商品グループ画像リクエスト
     */
    private GoodsImageEntityRequest toGoodsImageEntityRequest(GoodsImageEntity goodsImageEntity) {

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
        goodsImageEntityRequest.setTmpFilePath(goodsImageEntity.getTmpFilePath());
        goodsImageEntityRequest.setRegistTime(goodsImageEntity.getRegistTime());
        goodsImageEntityRequest.setUpdateTime(goodsImageEntity.getUpdateTime());

        return goodsImageEntityRequest;
    }
}
// 2023-renew No65 to here
