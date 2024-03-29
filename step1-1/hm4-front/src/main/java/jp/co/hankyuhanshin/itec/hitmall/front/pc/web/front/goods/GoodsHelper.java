package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryGoodsEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupDisplayEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.StockDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.StockStatusDisplayEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsDetailsDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsGroupImageEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsImageEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.StockDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.AbstractWebApiResponseResultDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDiscountsResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDiscountsResultResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponseCustomerSalePriceDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponsePriceDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponseSalePriceDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetReserveResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetReserveResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCategoryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCoolSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDentalMonopolySalesFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDisplayStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsClassType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGroupPriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGroupSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeLandSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeManualDisplayFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOutletIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleControlType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.stock.StockDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.favorite.FavoriteDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.favorite.FavoriteSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.restockannounce.RestockAnnounceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.AbstractWebApiResponseResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseCustomerSalePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponsePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseSalePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetReserveResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDiscountsResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDiscountsResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.category.CategoryGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.stock.StockStatusDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.favorite.FavoriteEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.restockannounce.RestockAnnounceEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品Helper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class GoodsHelper {

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
    public GoodsHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * 商品グループDtoに変換
     *
     * @param goodsGroupDtoResponse 商品詳細レスポンス
     * @return 商品グループDto
     */
    public GoodsGroupDto toGoodsGroupDto(GoodsGroupDtoResponse goodsGroupDtoResponse) {

        if (ObjectUtils.isEmpty(goodsGroupDtoResponse)) {
            return null;
        }

        GoodsGroupDto goodsGroupDto = new GoodsGroupDto();

        goodsGroupDto.setGoodsGroupEntity(toGoodsGroupEntity(goodsGroupDtoResponse.getGoodsGroupEntity()));
        goodsGroupDto.setBatchUpdateStockStatus(
                        toStockStatusDisplayEntity(goodsGroupDtoResponse.getBatchUpdateStockStatus()));
        goodsGroupDto.setRealTimeStockStatus(
                        toStockStatusDisplayEntity(goodsGroupDtoResponse.getRealTimeStockStatus()));
        goodsGroupDto.setGoodsGroupDisplayEntity(
                        toGoodsGroupDisplayEntity(goodsGroupDtoResponse.getGoodsGroupDisplayEntity()));
        goodsGroupDto.setGoodsGroupImageEntityList(
                        toGoodsGroupImageEntityList(goodsGroupDtoResponse.getGoodsGroupImageEntityList()));
        goodsGroupDto.setGoodsDtoList(toGoodsDtoList(goodsGroupDtoResponse.getGoodsDtoList()));
        goodsGroupDto.setCategoryGoodsEntityList(
                        toCategoryGoodsEntityList(goodsGroupDtoResponse.getCategoryGoodsEntityList()));
        goodsGroupDto.setTaxRate(goodsGroupDtoResponse.getTaxRate());

        return goodsGroupDto;
    }

    /**
     * 商品グループDtoリストに変換
     *
     * @param goodsGroupDtoListResponse 商品詳細リストレスポンス
     * @return 商品グループDtoリスト
     */
    public List<GoodsGroupDto> toGoodsGroupDtoList(List<GoodsGroupDtoResponse> goodsGroupDtoListResponse) {

        if (ObjectUtils.isEmpty(goodsGroupDtoListResponse) || CollectionUtil.isEmpty(goodsGroupDtoListResponse)) {
            return new ArrayList<>();
        }
        List<GoodsGroupDto> goodsGroupDtoList = new ArrayList<>();

        goodsGroupDtoListResponse.forEach(goodsGroupDtoResponse -> {
            GoodsGroupDto goodsGroupDto = toGoodsGroupDto(goodsGroupDtoResponse);

            goodsGroupDtoList.add(goodsGroupDto);
        });

        return goodsGroupDtoList;
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
     * 商品Dtoリストリクエストに変換
     *
     * @param goodsDtoList 商品Dtoリスト
     * @return 商品Dtoリストリクエスト
     */
    public List<GoodsDtoRequest> toGoodsDtoListRequest(List<GoodsDto> goodsDtoList) {

        if (CollectionUtil.isEmpty(goodsDtoList)) {
            return new ArrayList<>();
        }

        List<GoodsDtoRequest> goodsDtoRequestList = new ArrayList<>();

        goodsDtoList.forEach(goodsDto -> {

            GoodsDtoRequest goodsDtoRequest = new GoodsDtoRequest();

            goodsDtoRequest.setGoodsEntity(toGoodsEntityRequest(goodsDto.getGoodsEntity()));
            goodsDtoRequest.setStockDto(toStockDtoRequest(goodsDto.getStockDto()));
            goodsDtoRequest.setDeleteFlg(goodsDto.getDeleteFlg());
            if (goodsDto.getStockStatusPc() != null) {
                goodsDtoRequest.setStockStatus(goodsDto.getStockStatusPc().getValue());
            }
            goodsDtoRequest.setUnitImage(toGoodsImageEntityRequest(goodsDto.getUnitImage()));

            goodsDtoRequestList.add(goodsDtoRequest);
        });

        return goodsDtoRequestList;
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
     * カテゴリ詳細Dtoに変換
     *
     * @param categoryDetailsDtoResponse カテゴリ詳細Dtoレスポンス
     * @return カテゴリ詳細Dto
     */
    public CategoryDetailsDto toCategoryDetailsDto(CategoryDetailsDtoResponse categoryDetailsDtoResponse) {

        if (ObjectUtils.isEmpty(categoryDetailsDtoResponse)) {
            return null;
        }

        CategoryDetailsDto categoryDetailsDto = new CategoryDetailsDto();

        categoryDetailsDto.setCategoryId(categoryDetailsDtoResponse.getCategoryId());
        categoryDetailsDto.setCategoryNamePC(categoryDetailsDtoResponse.getCategoryName());
        categoryDetailsDto.setCategoryNotePC(categoryDetailsDtoResponse.getCategoryNote());
        categoryDetailsDto.setFreeTextPC(categoryDetailsDtoResponse.getFreeText());
        categoryDetailsDto.setMetaDescription(categoryDetailsDtoResponse.getMetaDescription());
        categoryDetailsDto.setMetaKeyword(categoryDetailsDtoResponse.getMetaKeyword());
        categoryDetailsDto.setCategoryImagePC(categoryDetailsDtoResponse.getCategoryImage());
        categoryDetailsDto.setCategorySeq(categoryDetailsDtoResponse.getCategorySeq());
        categoryDetailsDto.setShopSeq(1001);
        categoryDetailsDto.setCategoryName(categoryDetailsDtoResponse.getCategoryName());
        categoryDetailsDto.setCategoryOpenStartTimePC(
                        conversionUtility.toTimeStamp(categoryDetailsDtoResponse.getCategoryOpenStartTime()));
        categoryDetailsDto.setCategoryOpenEndTimePC(
                        conversionUtility.toTimeStamp(categoryDetailsDtoResponse.getCategoryOpenEndTime()));
        categoryDetailsDto.setCategorySeqPath(categoryDetailsDtoResponse.getCategorySeqPath());
        categoryDetailsDto.setOrderDisplay(categoryDetailsDtoResponse.getOrderDisplay());
        categoryDetailsDto.setRootCategorySeq(categoryDetailsDtoResponse.getRootCategorySeq());
        categoryDetailsDto.setCategoryLevel(categoryDetailsDtoResponse.getCategoryLevel());
        categoryDetailsDto.setCategoryPath(categoryDetailsDtoResponse.getCategoryPath());
        categoryDetailsDto.setVersionNo(categoryDetailsDtoResponse.getVersionNo());
        categoryDetailsDto.setRegistTime(conversionUtility.toTimeStamp(categoryDetailsDtoResponse.getRegistTime()));
        categoryDetailsDto.setUpdateTime(conversionUtility.toTimeStamp(categoryDetailsDtoResponse.getUpdateTime()));
        categoryDetailsDto.setDisplayRegistTime(
                        conversionUtility.toTimeStamp(categoryDetailsDtoResponse.getDisplayRegistTime()));
        categoryDetailsDto.setDisplayUpdateTime(
                        conversionUtility.toTimeStamp(categoryDetailsDtoResponse.getDisplayUpdateTime()));

        categoryDetailsDto.setCategoryOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class,
                                                                                 categoryDetailsDtoResponse.getCategoryOpenStatus()
                                                                                ));
        categoryDetailsDto.setCategoryType(EnumTypeUtil.getEnumFromValue(HTypeCategoryType.class,
                                                                         categoryDetailsDtoResponse.getCategoryType()
                                                                        ));
        categoryDetailsDto.setManualDisplayFlag(EnumTypeUtil.getEnumFromValue(HTypeManualDisplayFlag.class,
                                                                              categoryDetailsDtoResponse.getManualDisplayFlag()
                                                                             ));
        categoryDetailsDto.setSiteMapFlag(EnumTypeUtil.getEnumFromValue(HTypeSiteMapFlag.class,
                                                                        categoryDetailsDtoResponse.getSiteMapFlag()
                                                                       ));

        return categoryDetailsDto;
    }

    /**
     * カテゴリ詳細Dtoリストに変換
     *
     * @param categoryDetailsDtoResponseList カテゴリ詳細Dtoリストレスポンス
     * @return カテゴリ詳細Dtoリスト
     */
    public List<CategoryDetailsDto> toCategoryDetailsDtoList(List<CategoryDetailsDtoResponse> categoryDetailsDtoResponseList) {

        if (CollectionUtil.isEmpty(categoryDetailsDtoResponseList)) {
            return new ArrayList<>();
        }

        List<CategoryDetailsDto> categoryDetailsDtoList = new ArrayList<>();

        categoryDetailsDtoResponseList.forEach(categoryDetailsDtoResponse -> {
            CategoryDetailsDto categoryDetailsDto = toCategoryDetailsDto(categoryDetailsDtoResponse);

            categoryDetailsDtoList.add(categoryDetailsDto);
        });

        return categoryDetailsDtoList;
    }

    /**
     * 商品グループに変換
     *
     * @param goodsGroupEntityResponse 商品グループレスポンス
     * @return 商品グループ
     */
    private GoodsGroupEntity toGoodsGroupEntity(GoodsGroupEntityResponse goodsGroupEntityResponse) {

        if (ObjectUtils.isEmpty(goodsGroupEntityResponse)) {
            return null;
        }

        GoodsGroupEntity goodsGroupEntity = new GoodsGroupEntity();

        goodsGroupEntity.setGoodsGroupSeq(goodsGroupEntityResponse.getGoodsGroupSeq());
        goodsGroupEntity.setGoodsGroupCode(goodsGroupEntityResponse.getGoodsGroupCode());
        goodsGroupEntity.setGoodsGroupName(goodsGroupEntityResponse.getGoodsGroupName());
        goodsGroupEntity.setWhatsnewDate(conversionUtility.toTimeStamp(goodsGroupEntityResponse.getWhatsnewDate()));
        goodsGroupEntity.setOpenStartTimePC(conversionUtility.toTimeStamp(goodsGroupEntityResponse.getOpenStartTime()));
        goodsGroupEntity.setOpenEndTimePC(conversionUtility.toTimeStamp(goodsGroupEntityResponse.getOpenEndTime()));
        goodsGroupEntity.setGoodsPreDiscountPrice(goodsGroupEntityResponse.getGoodsPreDiscountPrice());
        goodsGroupEntity.setTaxRate(goodsGroupEntityResponse.getTaxRate());
        goodsGroupEntity.setGoodsGroupMaxPricePc(goodsGroupEntityResponse.getGoodsGroupMaxPrice());
        goodsGroupEntity.setGoodsGroupMinPricePc(goodsGroupEntityResponse.getGoodsGroupMinPrice());
        goodsGroupEntity.setShopSeq(1001);
        goodsGroupEntity.setVersionNo(goodsGroupEntityResponse.getVersionNo());
        goodsGroupEntity.setRegistTime(conversionUtility.toTimeStamp(goodsGroupEntityResponse.getRegistTime()));
        goodsGroupEntity.setUpdateTime(conversionUtility.toTimeStamp(goodsGroupEntityResponse.getUpdateTime()));
        goodsGroupEntity.setPreDiscountMaxPrice(goodsGroupEntityResponse.getPreDiscountMaxPrice());
        goodsGroupEntity.setPreDiscountMinPrice(goodsGroupEntityResponse.getPreDiscountMinPrice());
        goodsGroupEntity.setCatalogDisplayOrder(goodsGroupEntityResponse.getCatalogDisplayOrder());
        goodsGroupEntity.setGroupPrice(goodsGroupEntityResponse.getGroupPrice());
        goodsGroupEntity.setGroupSalePrice(goodsGroupEntityResponse.getGroupSalePrice());

        goodsGroupEntity.setGoodsOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                            goodsGroupEntityResponse.getGoodsOpenStatus()
                                                                           ));
        goodsGroupEntity.setGoodsTaxType(EnumTypeUtil.getEnumFromValue(HTypeGoodsTaxType.class,
                                                                       goodsGroupEntityResponse.getGoodsTaxType()
                                                                      ));
        goodsGroupEntity.setAlcoholFlag(EnumTypeUtil.getEnumFromValue(HTypeAlcoholFlag.class,
                                                                      goodsGroupEntityResponse.getAlcoholFlag()
                                                                     ));
        goodsGroupEntity.setSnsLinkFlag(EnumTypeUtil.getEnumFromValue(HTypeSnsLinkFlag.class,
                                                                      goodsGroupEntityResponse.getSnsLinkFlag()
                                                                     ));
        goodsGroupEntity.setGoodsClassType(EnumTypeUtil.getEnumFromValue(HTypeGoodsClassType.class,
                                                                         goodsGroupEntityResponse.getGoodsClassType()
                                                                        ));
        goodsGroupEntity.setDentalMonopolySalesFlg(EnumTypeUtil.getEnumFromValue(HTypeDentalMonopolySalesFlg.class,
                                                                                 goodsGroupEntityResponse.getDentalMonopolySalesFlg()
                                                                                ));
        goodsGroupEntity.setGroupPriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypeGroupPriceMarkDispFlag.class,
                                                                                 goodsGroupEntityResponse.getGroupPriceMarkDispFlag()
                                                                                ));
        goodsGroupEntity.setGroupSalePriceMarkDispFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeGroupSalePriceMarkDispFlag.class,
                                                      goodsGroupEntityResponse.getGroupSalePriceMarkDispFlag()
                                                     ));
        goodsGroupEntity.setGroupSalePriceIntegrityFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceIntegrityFlag.class,
                                                                                      goodsGroupEntityResponse.getGroupSalePriceIntegrityFlag()
                                                                                     ));
        // 2023-renew AddNo5 from here
        goodsGroupEntity.setGoodsGroupMaxPriceMb(goodsGroupEntityResponse.getGoodsGroupMaxPriceMb());
        goodsGroupEntity.setGoodsGroupMinPriceMb(goodsGroupEntityResponse.getGoodsGroupMinPriceMb());
        // 2023-renew AddNo5 to here

        return goodsGroupEntity;
    }

    /**
     * 商品グループ在庫表示に変換
     *
     * @param stockStatusDisplayEntityResponse 商品グループ在庫表示レスポンス<btestr/>こちらは非同期更新処理の完了にかかわらず設定されるため、最新データでないことに注意
     * @return 商品グループ在庫表示
     */
    private StockStatusDisplayEntity toStockStatusDisplayEntity(StockStatusDisplayEntityResponse stockStatusDisplayEntityResponse) {

        if (ObjectUtils.isEmpty(stockStatusDisplayEntityResponse)) {
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
     * 商品グループ表示に変換
     *
     * @param goodsGroupDisplayEntityResponse 商品グループ表示レスポンス
     * @return 商品グループ表示
     */
    private GoodsGroupDisplayEntity toGoodsGroupDisplayEntity(GoodsGroupDisplayEntityResponse goodsGroupDisplayEntityResponse) {

        if (ObjectUtils.isEmpty(goodsGroupDisplayEntityResponse)) {
            return null;
        }

        GoodsGroupDisplayEntity goodsGroupDisplayEntity = new GoodsGroupDisplayEntity();

        goodsGroupDisplayEntity.setGoodsGroupSeq(goodsGroupDisplayEntityResponse.getGoodsGroupSeq());
        goodsGroupDisplayEntity.setInformationIconPC(goodsGroupDisplayEntityResponse.getInformationIcon());
        goodsGroupDisplayEntity.setSearchKeyword(goodsGroupDisplayEntityResponse.getSearchKeyword());
        goodsGroupDisplayEntity.setSearchKeywordEm(goodsGroupDisplayEntityResponse.getSearchKeywordEm());
        goodsGroupDisplayEntity.setUnitTitle1(goodsGroupDisplayEntityResponse.getUnitTitle1());
        goodsGroupDisplayEntity.setUnitTitle2(goodsGroupDisplayEntityResponse.getUnitTitle2());
        goodsGroupDisplayEntity.setMetaDescription(goodsGroupDisplayEntityResponse.getMetaDescription());
        goodsGroupDisplayEntity.setMetaKeyword(goodsGroupDisplayEntityResponse.getMetaKeyword());
        goodsGroupDisplayEntity.setDeliveryType(goodsGroupDisplayEntityResponse.getDeliveryType());
        goodsGroupDisplayEntity.setGoodsNote1(goodsGroupDisplayEntityResponse.getGoodsNote1());
        //2023-renew No64 from here
        goodsGroupDisplayEntity.setGoodsNote1Sub(goodsGroupDisplayEntityResponse.getGoodsNote1Sub());
        goodsGroupDisplayEntity.setGoodsNote1OpenStartTime(
                        conversionUtility.toTimeStamp(goodsGroupDisplayEntityResponse.getGoodsNote1OpenStartTime()));
        goodsGroupDisplayEntity.setGoodsNote1SubOpenStartTime(
                        conversionUtility.toTimeStamp(goodsGroupDisplayEntityResponse.getGoodsNote1SubOpenStartTime()));
        goodsGroupDisplayEntity.setGoodsNote2(goodsGroupDisplayEntityResponse.getGoodsNote2());
        goodsGroupDisplayEntity.setGoodsNote2Sub(goodsGroupDisplayEntityResponse.getGoodsNote2Sub());
        goodsGroupDisplayEntity.setGoodsNote2OpenStartTime(
                        conversionUtility.toTimeStamp(goodsGroupDisplayEntityResponse.getGoodsNote2OpenStartTime()));
        goodsGroupDisplayEntity.setGoodsNote2SubOpenStartTime(
                        conversionUtility.toTimeStamp(goodsGroupDisplayEntityResponse.getGoodsNote2SubOpenStartTime()));
        //2023-renew No64 to here
        goodsGroupDisplayEntity.setGoodsNote3(goodsGroupDisplayEntityResponse.getGoodsNote3());
        goodsGroupDisplayEntity.setGoodsNote4(goodsGroupDisplayEntityResponse.getGoodsNote4());
        //2023-renew No64 from here
        goodsGroupDisplayEntity.setGoodsNote4Sub(goodsGroupDisplayEntityResponse.getGoodsNote4Sub());
        goodsGroupDisplayEntity.setGoodsNote4OpenStartTime(
                        conversionUtility.toTimeStamp(goodsGroupDisplayEntityResponse.getGoodsNote4OpenStartTime()));
        goodsGroupDisplayEntity.setGoodsNote4SubOpenStartTime(
                        conversionUtility.toTimeStamp(goodsGroupDisplayEntityResponse.getGoodsNote4SubOpenStartTime()));
        //2023-renew No64 to here
        goodsGroupDisplayEntity.setGoodsNote5(goodsGroupDisplayEntityResponse.getGoodsNote5());
        goodsGroupDisplayEntity.setGoodsNote6(goodsGroupDisplayEntityResponse.getGoodsNote6());
        goodsGroupDisplayEntity.setGoodsNote7(goodsGroupDisplayEntityResponse.getGoodsNote7());
        goodsGroupDisplayEntity.setGoodsNote8(goodsGroupDisplayEntityResponse.getGoodsNote8());
        goodsGroupDisplayEntity.setGoodsNote9(goodsGroupDisplayEntityResponse.getGoodsNote9());
        goodsGroupDisplayEntity.setGoodsNote10(goodsGroupDisplayEntityResponse.getGoodsNote10());
        //2023-renew No64 from here
        goodsGroupDisplayEntity.setGoodsNote10Sub(goodsGroupDisplayEntityResponse.getGoodsNote10Sub());
        goodsGroupDisplayEntity.setGoodsNote10OpenStartTime(
                        conversionUtility.toTimeStamp(goodsGroupDisplayEntityResponse.getGoodsNote10OpenStartTime()));
        goodsGroupDisplayEntity.setGoodsNote10SubOpenStartTime(conversionUtility.toTimeStamp(
                        goodsGroupDisplayEntityResponse.getGoodsNote10SubOpenStartTime()));
        //2023-renew No64 to here
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
        // 2023-renew No11 from here
        goodsGroupDisplayEntity.setGoodsNote21(goodsGroupDisplayEntityResponse.getGoodsNote21());
        // 2023-renew No11 to here
        // 2023-renew No0 from here
        goodsGroupDisplayEntity.setGoodsNote22(goodsGroupDisplayEntityResponse.getGoodsNote22());
        // 2023-renew No0 to here
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

        goodsGroupDisplayEntity.setUnitManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class,
                                                                                    goodsGroupDisplayEntityResponse.getUnitManagementFlag()
                                                                                   ));
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
     * 商品Dtoリストに変換
     *
     * @param goodsDtoResponseList 商品Dtoレスポンスリスト
     * @return 商品Dtoリスト
     */
    private List<GoodsDto> toGoodsDtoList(List<GoodsDtoResponse> goodsDtoResponseList) {

        if (CollectionUtil.isEmpty(goodsDtoResponseList)) {
            return new ArrayList<>();
        }

        List<GoodsDto> goodsDtoList = new ArrayList<>();

        goodsDtoResponseList.forEach(goodsDtoResponse -> {
            GoodsDto goodsDto = new GoodsDto();

            goodsDto.setGoodsEntity(toGoodsEntity(goodsDtoResponse.getGoodsEntity()));
            goodsDto.setStockDto(toStockDto(goodsDtoResponse.getStockDto()));
            goodsDto.setDeleteFlg(goodsDtoResponse.getDeleteFlg());
            goodsDto.setStockStatusPc(EnumTypeUtil.getEnumFromValue(HTypeStockStatusType.class,
                                                                    goodsDtoResponse.getStockStatus()
                                                                   ));
            goodsDto.setUnitImage(toUnitImage(goodsDtoResponse.getUnitImage()));

            goodsDtoList.add(goodsDto);
        });

        return goodsDtoList;
    }

    /**
     * 商品に変換
     *
     * @param goodsEntityResponse 商品レスポンス
     * @return 商品
     */
    private GoodsEntity toGoodsEntity(GoodsEntityResponse goodsEntityResponse) {

        if (ObjectUtils.isEmpty(goodsEntityResponse)) {
            return null;
        }

        GoodsEntity goodsEntity = new GoodsEntity();

        goodsEntity.setGoodsSeq(goodsEntityResponse.getGoodsSeq());
        goodsEntity.setGoodsGroupSeq(goodsEntityResponse.getGoodsGroupSeq());
        goodsEntity.setGoodsCode(goodsEntityResponse.getGoodsCode());
        goodsEntity.setJanCode(goodsEntityResponse.getJanCode());
        goodsEntity.setCatalogCode(goodsEntityResponse.getCatalogCode());
        goodsEntity.setSaleStartTimePC(conversionUtility.toTimeStamp(goodsEntityResponse.getSaleStartTime()));
        goodsEntity.setSaleEndTimePC(conversionUtility.toTimeStamp(goodsEntityResponse.getSaleEndTime()));
        goodsEntity.setGoodsPrice(goodsEntityResponse.getGoodsPrice());
        goodsEntity.setPreDiscountPrice(goodsEntityResponse.getPreDiscountPrice());
        goodsEntity.setUnitValue1(goodsEntityResponse.getUnitValue1());
        goodsEntity.setUnitValue2(goodsEntityResponse.getUnitValue2());
        goodsEntity.setPurchasedMax(goodsEntityResponse.getPurchasedMax());
        goodsEntity.setOrderDisplay(goodsEntityResponse.getOrderDisplay());
        goodsEntity.setShopSeq(1001);
        goodsEntity.setVersionNo(goodsEntityResponse.getVersionNo());
        goodsEntity.setRegistTime(conversionUtility.toTimeStamp(goodsEntityResponse.getRegistTime()));
        goodsEntity.setUpdateTime(conversionUtility.toTimeStamp(goodsEntityResponse.getUpdateTime()));
        goodsEntity.setUnit(goodsEntityResponse.getUnit());
        goodsEntity.setGoodsManagementCode(goodsEntityResponse.getGoodsManagementCode());
        goodsEntity.setGoodsDivisionCode(goodsEntityResponse.getGoodsDivisionCode());
        goodsEntity.setGoodsCategory1(goodsEntityResponse.getGoodsCategory1());
        goodsEntity.setGoodsCategory2(goodsEntityResponse.getGoodsCategory2());
        goodsEntity.setGoodsCategory3(goodsEntityResponse.getGoodsCategory3());
        goodsEntity.setCoolSendFrom(goodsEntityResponse.getCoolSendFrom());
        goodsEntity.setCoolSendTo(goodsEntityResponse.getCoolSendTo());

        goodsEntity.setSaleStatusPC(
                        EnumTypeUtil.getEnumFromValue(HTypeGoodsSaleStatus.class, goodsEntityResponse.getSaleStatus()));
        goodsEntity.setIndividualDeliveryType(EnumTypeUtil.getEnumFromValue(HTypeIndividualDeliveryType.class,
                                                                            goodsEntityResponse.getIndividualDeliveryType()
                                                                           ));
        goodsEntity.setFreeDeliveryFlag(EnumTypeUtil.getEnumFromValue(HTypeFreeDeliveryFlag.class,
                                                                      goodsEntityResponse.getFreeDeliveryFlag()
                                                                     ));
        goodsEntity.setUnitManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class,
                                                                        goodsEntityResponse.getUnitManagementFlag()
                                                                       ));
        goodsEntity.setStockManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeStockManagementFlag.class,
                                                                         goodsEntityResponse.getStockManagementFlag()
                                                                        ));
        goodsEntity.setReserveFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeReserveFlag.class, goodsEntityResponse.getReserveFlag()));
        goodsEntity.setPriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypePriceMarkDispFlag.class,
                                                                       goodsEntityResponse.getPriceMarkDispFlag()
                                                                      ));
        goodsEntity.setSalePriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceMarkDispFlag.class,
                                                                           goodsEntityResponse.getSalePriceMarkDispFlag()
                                                                          ));
        goodsEntity.setLandSendFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeLandSendFlag.class, goodsEntityResponse.getLandSendFlag()));
        goodsEntity.setCoolSendFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeCoolSendFlag.class, goodsEntityResponse.getCoolSendFlag()));
        goodsEntity.setSalePriceIntegrityFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceIntegrityFlag.class,
                                                                            goodsEntityResponse.getSalePriceIntegrityFlag()
                                                                           ));
        goodsEntity.setEmotionPriceType(EnumTypeUtil.getEnumFromValue(HTypeEmotionPriceType.class,
                                                                      goodsEntityResponse.getEmotionPriceType()
                                                                     ));

        return goodsEntity;
    }

    /**
     * 在庫Dtoに変換
     *
     * @param stockDtoResponse 在庫Dtoレスポンス
     * @return 在庫Dto
     */
    private StockDto toStockDto(StockDtoResponse stockDtoResponse) {

        if (ObjectUtils.isEmpty(stockDtoResponse)) {
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
     * 商品画像に変換
     *
     * @param goodsImageEntityResponse 商品グループ画像レスポンス
     * @return 商品画像
     */
    private GoodsImageEntity toUnitImage(GoodsImageEntityResponse goodsImageEntityResponse) {

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
     * カテゴリ登録商品リストに変換
     *
     * @param categoryGoodsEntityResponseList カテゴリ登録商品リストレスポンス
     * @return カテゴリ登録商品リスト
     */
    private List<CategoryGoodsEntity> toCategoryGoodsEntityList(List<CategoryGoodsEntityResponse> categoryGoodsEntityResponseList) {

        if (CollectionUtil.isEmpty(categoryGoodsEntityResponseList)) {
            return new ArrayList<>();
        }

        List<CategoryGoodsEntity> categoryGoodsEntityList = new ArrayList<>();

        categoryGoodsEntityResponseList.forEach(categoryGoodsEntityResponse -> {
            CategoryGoodsEntity categoryGoodsEntity = new CategoryGoodsEntity();

            categoryGoodsEntity.setCategorySeq(categoryGoodsEntityResponse.getCategorySeq());
            categoryGoodsEntity.setGoodsGroupSeq(categoryGoodsEntityResponse.getGoodsGroupSeq());
            categoryGoodsEntity.setOrderDisplay(categoryGoodsEntityResponse.getOrderDisplay());
            categoryGoodsEntity.setRegistTime(
                            conversionUtility.toTimeStamp(categoryGoodsEntityResponse.getRegistTime()));
            categoryGoodsEntity.setUpdateTime(
                            conversionUtility.toTimeStamp(categoryGoodsEntityResponse.getUpdateTime()));

            categoryGoodsEntityList.add(categoryGoodsEntity);
        });

        return categoryGoodsEntityList;
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
            webApiGetQuantityDiscountResponseDetailDto.setPriceList(
                            toWebApiGetQuantityDiscountResponsePriceDtoList(webApiResponse.getPriceList()));
            webApiGetQuantityDiscountResponseDetailDto.setSalePriceList(
                            toWebApiGetQuantityDiscountResponseSalePriceDtoList(webApiResponse.getSalePriceList()));
            webApiGetQuantityDiscountResponseDetailDto.setCustomerSalePriceList(
                            toWebApiGetQuantityDiscountResponseCustomerSalePriceDtoList(
                                            webApiResponse.getCustomerSalePriceList()));
            webApiGetQuantityDiscountResponseDetailDto.setSaleFlag(webApiResponse.getSaleFlag());
            webApiGetQuantityDiscountResponseDetailDto.setMarketPriceFlag(webApiResponse.getMarketPriceFlag());
            webApiGetQuantityDiscountResponseDetailDto.setCustomerSaleFlag(webApiResponse.getCustomerSaleFlag());
            // 2023-renew No5 from here
            webApiGetQuantityDiscountResponseDetailDto.setSaleEndDay(
                            conversionUtility.toTimeStamp(webApiResponse.getSaleEndDay()));
            // 2023-renew No5 to here

            webApiGetQuantityDiscountResponseDetailDtoList.add(webApiGetQuantityDiscountResponseDetailDto);
        });

        return webApiGetQuantityDiscountResponseDetailDtoList;
    }

    /**
     * WEB-API連携取得結果DTOリストに変換
     *
     * @param webApiGetQuantityDiscountResponsePriceDtoResponseList 数量割引情報取得 価格情報リストレスポンス
     * @return 数量割引情報取得 価格情報リスト
     */
    private List<WebApiGetQuantityDiscountResponsePriceDto> toWebApiGetQuantityDiscountResponsePriceDtoList(List<WebApiGetQuantityDiscountResponsePriceDtoResponse> webApiGetQuantityDiscountResponsePriceDtoResponseList) {

        if (CollectionUtil.isEmpty(webApiGetQuantityDiscountResponsePriceDtoResponseList)) {
            return new ArrayList<>();
        }

        List<WebApiGetQuantityDiscountResponsePriceDto> webApiGetQuantityDiscountResponsePriceDtoList =
                        new ArrayList<>();

        webApiGetQuantityDiscountResponsePriceDtoResponseList.forEach(webApiResponse -> {
            WebApiGetQuantityDiscountResponsePriceDto webApiGetQuantityDiscountResponsePriceDto =
                            new WebApiGetQuantityDiscountResponsePriceDto();

            webApiGetQuantityDiscountResponsePriceDto.setPrice(webApiResponse.getPrice());
            webApiGetQuantityDiscountResponsePriceDto.setLevel(webApiResponse.getLevel());

            webApiGetQuantityDiscountResponsePriceDtoList.add(webApiGetQuantityDiscountResponsePriceDto);
        });

        return webApiGetQuantityDiscountResponsePriceDtoList;

    }

    /**
     * WEB-API連携取得結果DTOリストに変換
     *
     * @param webApiGetQuantityDiscountResponseSalePriceDtoResponseList 数量割引情報取得 割引価格情報レスポンス
     * @return 数量割引情報取得 割引価格情報リスト
     */
    private List<WebApiGetQuantityDiscountResponseSalePriceDto> toWebApiGetQuantityDiscountResponseSalePriceDtoList(List<WebApiGetQuantityDiscountResponseSalePriceDtoResponse> webApiGetQuantityDiscountResponseSalePriceDtoResponseList) {

        if (CollectionUtil.isEmpty(webApiGetQuantityDiscountResponseSalePriceDtoResponseList)) {
            return new ArrayList<>();
        }

        List<WebApiGetQuantityDiscountResponseSalePriceDto> webApiGetQuantityDiscountResponseSalePriceDtoList =
                        new ArrayList<>();

        webApiGetQuantityDiscountResponseSalePriceDtoResponseList.forEach(webApiResponse -> {
            WebApiGetQuantityDiscountResponseSalePriceDto webApiGetQuantityDiscountResponseSalePriceDto =
                            new WebApiGetQuantityDiscountResponseSalePriceDto();

            webApiGetQuantityDiscountResponseSalePriceDto.setSaleLevel(webApiResponse.getSaleLevel());
            webApiGetQuantityDiscountResponseSalePriceDto.setSalePrice(webApiResponse.getSalePrice());

            webApiGetQuantityDiscountResponseSalePriceDtoList.add(webApiGetQuantityDiscountResponseSalePriceDto);
        });

        return webApiGetQuantityDiscountResponseSalePriceDtoList;

    }

    /**
     * WEB-API連携取得結果DTOリストに変換
     *
     * @param webApiGetQuantityDiscountResponseCustomerSalePriceDtoResponseList 数量割引情報取得 顧客セール価格情報リストレスポンス
     * @return 数量割引情報取得 顧客セール価格情報リスト
     */
    private List<WebApiGetQuantityDiscountResponseCustomerSalePriceDto> toWebApiGetQuantityDiscountResponseCustomerSalePriceDtoList(
                    List<WebApiGetQuantityDiscountResponseCustomerSalePriceDtoResponse> webApiGetQuantityDiscountResponseCustomerSalePriceDtoResponseList) {

        if (CollectionUtil.isEmpty(webApiGetQuantityDiscountResponseCustomerSalePriceDtoResponseList)) {
            return new ArrayList<>();
        }

        List<WebApiGetQuantityDiscountResponseCustomerSalePriceDto>
                        webApiGetQuantityDiscountResponseCustomerSalePriceDtoList = new ArrayList<>();

        webApiGetQuantityDiscountResponseCustomerSalePriceDtoResponseList.forEach(webApiResponse -> {
            WebApiGetQuantityDiscountResponseCustomerSalePriceDto
                            webApiGetQuantityDiscountResponseCustomerSalePriceDto =
                            new WebApiGetQuantityDiscountResponseCustomerSalePriceDto();

            webApiGetQuantityDiscountResponseCustomerSalePriceDto.setCustomerSaleLevel(
                            webApiResponse.getCustomerSaleLevel());
            webApiGetQuantityDiscountResponseCustomerSalePriceDto.setCustomerSalePrice(
                            webApiResponse.getCustomerSalePrice());

            webApiGetQuantityDiscountResponseCustomerSalePriceDtoList.add(
                            webApiGetQuantityDiscountResponseCustomerSalePriceDto);
        });

        return webApiGetQuantityDiscountResponseCustomerSalePriceDtoList;

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

    // 2023-renew No11 from here

    /**
     * WEB-API連携取得結果DTOクラスに変換
     *
     * @param webApiGetDiscountsResultResponse WEB-API連携割引適用結果取得レスポンス
     * @return WEB-API連携取得結果DTOクラス
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
     * WEB-API連携取得結果DTOクラスリストに変換
     *
     * @param info 割引適用結果取得 詳細情報レスポンス
     * @return WEB-API連携取得結果DTOクラスリスト
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
    // 2023-renew No11 to here
    // 2023-renew No65 from here

    /**
     * 入荷お知らせりに変換
     *
     * @param restockAnnounceEntityResponse 入荷お知らせりクラスレスポンス
     * @return 入荷お知らせ
     */
    private RestockAnnounceEntity toRestockAnnounceEntity(RestockAnnounceEntityResponse restockAnnounceEntityResponse) {

        if (ObjectUtils.isEmpty(restockAnnounceEntityResponse)) {
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
     * 入荷お知らせ入りDtoリストに変換
     *
     * @param restockAnnounceDtoResponseList 入荷お知らせDtoListレスポンス
     * @return 入荷お知ら入りDtoリスト
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
            restockAnnounceDto.setGoodsGroupImageEntityList(toFavoriteGoodsGroupImageEntityList(
                            restockAnnounceDtoResponse.getGoodsGroupImageEntityListResponse()));
            restockAnnounceDto.setStockStatus(restockAnnounceDtoResponse.getStockStatus());

            restockAnnounceDtoList.add(restockAnnounceDto);
        });

        return restockAnnounceDtoList;
    }
    // 2023-renew No65 to here

    // 2023-renew No11 from here

    /**
     * 取りおき情報取得APIのレスポンスをHM用のDTOクラスに変換
     *
     * @param webApiGetReserveResponse 取りおき情報取得Web-APIレスポンス
     * @return 取りおき情報取得
     */
    public WebApiGetReserveResponseDto toWebApiGetReserveResponseDto(WebApiGetReserveResponse webApiGetReserveResponse) {

        if (ObjectUtils.isEmpty(webApiGetReserveResponse)) {
            return null;
        }

        WebApiGetReserveResponseDto webApiGetReserveResponseDto = new WebApiGetReserveResponseDto();
        webApiGetReserveResponseDto.setResult(toAbstractWebApiResponseResultDto(webApiGetReserveResponse.getResult()));
        webApiGetReserveResponseDto.setInfo(
                        toWebApiGetReserveResponseDetailDtoList(webApiGetReserveResponse.getInfo()));

        return webApiGetReserveResponseDto;
    }

    /**
     * 取りおき情報取得(詳細情報)リストに変換
     *
     * @param webApiGetReserveResponseDetailDtoResponseList 取りおき情報取得 詳細情報
     * @return 取りおき情報取得(詳細情報)リスト
     */
    private List<WebApiGetReserveResponseDetailDto> toWebApiGetReserveResponseDetailDtoList(List<WebApiGetReserveResponseDetailDtoResponse> webApiGetReserveResponseDetailDtoResponseList) {

        if (CollectionUtil.isEmpty(webApiGetReserveResponseDetailDtoResponseList)) {
            return new ArrayList<>();
        }

        List<WebApiGetReserveResponseDetailDto> webApiGetReserveResponseDetailDtoList = new ArrayList<>();

        webApiGetReserveResponseDetailDtoResponseList.forEach(item -> {
            WebApiGetReserveResponseDetailDto webApiGetReserveResponseDetailDto =
                            new WebApiGetReserveResponseDetailDto();
            webApiGetReserveResponseDetailDto.setGoodsCode(item.getGoodsCode());
            webApiGetReserveResponseDetailDto.setReserveFlag(item.getReserveFlag());
            webApiGetReserveResponseDetailDto.setDiscountFlag(item.getDiscountFlag());
            webApiGetReserveResponseDetailDto.setPossibleReserveFromDay(
                            conversionUtility.toTimeStamp(item.getPossibleReserveFromDay()));
            webApiGetReserveResponseDetailDto.setPossibleReserveToDay(
                            conversionUtility.toTimeStamp(item.getPossibleReserveToDay()));
            webApiGetReserveResponseDetailDtoList.add(webApiGetReserveResponseDetailDto);
        });

        return webApiGetReserveResponseDetailDtoList;
    }
    // 2023-renew No11 to here
}
