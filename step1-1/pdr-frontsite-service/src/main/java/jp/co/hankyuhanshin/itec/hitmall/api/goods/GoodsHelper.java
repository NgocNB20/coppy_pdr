package jp.co.hankyuhanshin.itec.hitmall.api.goods;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryDisplayEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryGoodsEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategorySearchForDaoConditionDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryTreeNodeGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryTreeNodeResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsFootPrintRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupDisplayEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsUnitDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.MultipleCategoryGoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.MultipleCategoryMapResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.OpenGoodsGroupDetailsListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.OpenRelatedGoodsListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.PageInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.SaleGoodsListDetailGetResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.StockDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.StockStatusDisplayEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberRank;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsUnitDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsRelationSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.salegoods.SaleGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipleCategory.ajax.MultipleCategoryGoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.footprint.FootprintEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.stock.StockStatusDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.web.PageInfo;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品Helperクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class GoodsHelper {

    /**
     * カテゴリ情報Dao用検索条件Dtoに変換
     *
     * @param categoryTreeNodeGetRequest カテゴリ木構造取得リクエスト
     * @return カテゴリ情報Dao用検索条件Dto
     */
    public CategorySearchForDaoConditionDto toCategorySearchForDaoConditionDto(CategoryTreeNodeGetRequest categoryTreeNodeGetRequest) {
        if (ObjectUtils.isEmpty(categoryTreeNodeGetRequest)) {
            return null;
        }

        CategorySearchForDaoConditionDto conditionDto = new CategorySearchForDaoConditionDto();

        conditionDto.setCategoryId(categoryTreeNodeGetRequest.getCategoryId());
        conditionDto.setCategorySeqList(categoryTreeNodeGetRequest.getCategorySeqList());
        conditionDto.setMaxHierarchical(categoryTreeNodeGetRequest.getMaxHierarchical());
        conditionDto.setSiteType(
                        EnumTypeUtil.getEnumFromValue(HTypeSiteType.class, categoryTreeNodeGetRequest.getSiteType()));
        conditionDto.setOpenStatus(EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class,
                                                                 categoryTreeNodeGetRequest.getOpenStatus()
                                                                ));
        conditionDto.setNotInCategoryIdList(categoryTreeNodeGetRequest.getNotInCategoryIdList());
        conditionDto.setOrderField(categoryTreeNodeGetRequest.getOrderField());
        conditionDto.setOrderAsc(categoryTreeNodeGetRequest.getOrderAsc());

        return conditionDto;
    }

    /**
     * カテゴリ詳細Dtoレスポンスに変換
     *
     * @param categoryDetailsDto カテゴリ詳細Dto
     * @return カテゴリ詳細Dtoレスポンス
     */
    public CategoryDetailsDtoResponse toCategoryDetailsDtoResponse(CategoryDetailsDto categoryDetailsDto) {
        if (categoryDetailsDto == null) {
            return null;
        }
        CategoryDetailsDtoResponse response = new CategoryDetailsDtoResponse();

        response.setCategoryId(categoryDetailsDto.getCategoryId());
        response.setCategoryNote(categoryDetailsDto.getCategoryNotePC());
        response.setFreeText(categoryDetailsDto.getFreeTextPC());
        response.setMetaDescription(categoryDetailsDto.getMetaDescription());
        response.setMetaKeyword(categoryDetailsDto.getMetaKeyword());
        response.setCategoryImage(categoryDetailsDto.getCategoryImagePC());
        response.setCategorySeq(categoryDetailsDto.getCategorySeq());
        response.setCategoryName(categoryDetailsDto.getCategoryNamePC());
        if (categoryDetailsDto.getCategoryOpenStatusPC() != null) {
            response.setCategoryOpenStatus(categoryDetailsDto.getCategoryOpenStatusPC().getValue());
        }
        response.setCategoryOpenStartTime(categoryDetailsDto.getCategoryOpenStartTimePC());
        response.setCategoryOpenEndTime(categoryDetailsDto.getCategoryOpenEndTimePC());
        if (categoryDetailsDto.getCategoryOpenStatusPC() != null) {
            response.setCategoryOpenStatus(categoryDetailsDto.getCategoryOpenStatusPC().getValue());
        }
        if (categoryDetailsDto.getCategoryType() != null) {
            response.setCategoryType(categoryDetailsDto.getCategoryType().getValue());
        }
        response.setCategorySeqPath(categoryDetailsDto.getCategorySeqPath());
        response.setOrderDisplay(categoryDetailsDto.getOrderDisplay());
        response.setRootCategorySeq(categoryDetailsDto.getRootCategorySeq());
        response.setCategoryLevel(categoryDetailsDto.getCategoryLevel());
        response.setCategoryPath(categoryDetailsDto.getCategoryPath());
        if (categoryDetailsDto.getManualDisplayFlag() != null) {
            response.setManualDisplayFlag(categoryDetailsDto.getManualDisplayFlag().getValue());
        }

        response.setVersionNo(categoryDetailsDto.getVersionNo());
        response.setRegistTime(categoryDetailsDto.getRegistTime());
        response.setUpdateTime(categoryDetailsDto.getUpdateTime());
        response.setDisplayRegistTime(categoryDetailsDto.getDisplayRegistTime());
        response.setDisplayUpdateTime(categoryDetailsDto.getDisplayUpdateTime());
        if (categoryDetailsDto.getSiteMapFlag() != null) {
            response.setSiteMapFlag(categoryDetailsDto.getSiteMapFlag().getValue());
        }

        return response;
    }

    /**
     * カテゴリEntityレスポンスに変換
     *
     * @param categoryEntity カテゴリEntity
     * @return カテゴリEntityレスポンス
     */
    public CategoryEntityResponse toCategoryEntityResponse(CategoryEntity categoryEntity) {
        if (categoryEntity == null) {
            return null;
        }
        CategoryEntityResponse response = new CategoryEntityResponse();

        response.setCategorySeq(categoryEntity.getCategorySeq());
        response.setCategoryId(categoryEntity.getCategoryId());
        response.setCategoryName(categoryEntity.getCategoryName());
        if (categoryEntity.getCategoryOpenStatusPC() != null) {
            response.setCategoryOpenStatus(categoryEntity.getCategoryOpenStatusPC().getValue());
        }
        response.setCategoryOpenStartTime(categoryEntity.getCategoryOpenStartTimePC());
        response.setCategoryOpenEndTime(categoryEntity.getCategoryOpenEndTimePC());
        if (categoryEntity.getCategoryType() != null) {
            response.setCategoryType(categoryEntity.getCategoryType().getValue());
        }
        response.setCategorySeqPath(categoryEntity.getCategorySeqPath());
        response.setOrderDisplay(categoryEntity.getOrderDisplay());
        response.setRootCategorySeq(categoryEntity.getRootCategorySeq());
        response.setCategoryLevel(categoryEntity.getCategoryLevel());
        response.setCategoryPath(categoryEntity.getCategoryPath());
        if (categoryEntity.getManualDisplayFlag() != null) {
            response.setManualDisplayFlag(categoryEntity.getManualDisplayFlag().getValue());
        }
        response.setVersionNo(categoryEntity.getVersionNo());
        response.setRegistTime(categoryEntity.getRegistTime());
        response.setUpdateTime(categoryEntity.getUpdateTime());
        if (categoryEntity.getSiteMapFlag() != null) {
            response.setSiteMapFlag(categoryEntity.getSiteMapFlag().getValue());
        }
        response.setOpenGoodsCount(categoryEntity.getOpenGoodsCountPC());

        return response;
    }

    /**
     * カテゴリ表示レスポンスに変換
     *
     * @param categoryDisplayEntity カテゴリ表示クラス
     * @return カテゴリ表示レスポンス
     */
    public CategoryDisplayEntityResponse toCategoryDisplayEntityResponse(CategoryDisplayEntity categoryDisplayEntity) {

        if (categoryDisplayEntity == null) {
            return null;
        }
        CategoryDisplayEntityResponse response = new CategoryDisplayEntityResponse();

        response.setCategorySeq(categoryDisplayEntity.getCategorySeq());
        response.setCategoryName(categoryDisplayEntity.getCategoryNamePC());
        response.setCategoryNote(categoryDisplayEntity.getCategoryNotePC());
        response.setFreeText(categoryDisplayEntity.getFreeTextPC());
        response.setMetaDescription(categoryDisplayEntity.getMetaDescription());
        response.setMetaKeyword(categoryDisplayEntity.getMetaKeyword());
        response.setCategoryImage(categoryDisplayEntity.getCategoryImagePC());
        response.setRegistTime(categoryDisplayEntity.getRegistTime());
        response.setUpdateTime(categoryDisplayEntity.getUpdateTime());

        return response;
    }

    /**
     * カテゴリ木構造レスポンスリストに変換
     *
     * @param categoryDtoList カテゴリDTOリスト
     * @return カテゴリ木構造リストレスポンス
     */
    public List<CategoryTreeNodeResponse> toCategoryTreeNodeResponseList(List<CategoryDto> categoryDtoList) {

        if (CollectionUtil.isEmpty(categoryDtoList)) {
            return new ArrayList<>();
        }

        List<CategoryTreeNodeResponse> categoryTreeNodeResponseList = new ArrayList<>();

        categoryDtoList.forEach(categoryDto -> {
            CategoryTreeNodeResponse categoryTreeNodeResponse = new CategoryTreeNodeResponse();

            categoryTreeNodeResponse.setCategoryEntity(toCategoryEntityResponse(categoryDto.getCategoryEntity()));
            categoryTreeNodeResponse.setCategoryDisplayEntity(
                            toCategoryDisplayEntityResponse(categoryDto.getCategoryDisplayEntity()));
            categoryTreeNodeResponse.setCategoryDtoList(
                            toCategoryTreeNodeResponseList(categoryDto.getCategoryDtoList()));

            categoryTreeNodeResponseList.add(categoryTreeNodeResponse);
        });

        return categoryTreeNodeResponseList;
    }

    /**
     * カテゴリ情報DtoListレスポンスに変換
     *
     * @param categoryDetailsDtoList カテゴリ情報DtoList
     * @return カテゴリ情報DtoListレスポンス
     */
    public List<CategoryDetailsDtoResponse> toListCategoryDetailsDtoResponse(List<CategoryDetailsDto> categoryDetailsDtoList) {
        if (categoryDetailsDtoList == null) {
            return new ArrayList<>();
        }
        List<CategoryDetailsDtoResponse> responses = new ArrayList<>();
        for (int i = 0; i < categoryDetailsDtoList.size(); i++) {
            CategoryDetailsDtoResponse categoryDetailsDtoResponse =
                            this.toCategoryDetailsDtoResponse(categoryDetailsDtoList.get(i));
            responses.add(categoryDetailsDtoResponse);
        }
        return responses;
    }

    /**
     * 商品詳細DtoListレスポンスに変換
     *
     * @param goodsDetailsList 商品詳細DtoList
     * @return 商品詳細DtoListレスポンス
     */
    public List<GoodsDetailsDtoResponse> toListGoodsDetailsDtoResponse(List<GoodsDetailsDto> goodsDetailsList)
                    throws InvocationTargetException, IllegalAccessException {
        if (goodsDetailsList == null) {
            return new ArrayList<>();
        }
        List<GoodsDetailsDtoResponse> responses = new ArrayList<>();
        for (int i = 0; i < goodsDetailsList.size(); i++) {
            GoodsDetailsDtoResponse goodsDetailsDtoResponse = this.toGoodsDetailsDtoResponse(goodsDetailsList.get(i));
            responses.add(goodsDetailsDtoResponse);
        }
        return responses;
    }

    /**
     * 商品詳細Dtoレスポンスに変換
     *
     * @param goodsDetailsDto 商品詳細Dto
     * @return 商品詳細Dtoレスポンス
     */
    public GoodsDetailsDtoResponse toGoodsDetailsDtoResponse(GoodsDetailsDto goodsDetailsDto)
                    throws InvocationTargetException, IllegalAccessException {
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
        response.setTaxRate(goodsDetailsDto.getTaxRate());
        response.setGoodsPriceInTax(goodsDetailsDto.getGoodsPriceInTax());
        response.setGoodsPrice(goodsDetailsDto.getGoodsPrice());
        response.setDeliveryType(goodsDetailsDto.getDeliveryType());
        response.setSaleStartTime(goodsDetailsDto.getSaleStartTimePC());
        response.setSaleEndTime(goodsDetailsDto.getSaleEndTimePC());
        response.setPurchasedMax(goodsDetailsDto.getPurchasedMax());
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
        response.setOpenStartTime(goodsDetailsDto.getOpenStartTimePC());
        response.setOpenEndTime(goodsDetailsDto.getOpenEndTimePC());
        response.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
        response.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
        response.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
        response.setGoodsPreDiscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());
        response.setMetaDescription(goodsDetailsDto.getMetaDescription());
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
        response.setGoodsOptionDisplayName(goodsDetailsDto.getGoodsOptionDisplayName());
        response.setCoolSendFrom(goodsDetailsDto.getCoolSendFrom());
        response.setCoolSendTo(goodsDetailsDto.getCoolSendTo());
        response.setTax(goodsDetailsDto.getTax());
        response.setUnit(goodsDetailsDto.getUnit());
        response.setGoodsManagementCode(goodsDetailsDto.getGoodsManagementCode());
        response.setGoodsDivisionCode(goodsDetailsDto.getGoodsDivisionCode());
        response.setGoodsCategory1(goodsDetailsDto.getGoodsCategory1());
        response.setGoodsCategory2(goodsDetailsDto.getGoodsCategory2());
        response.setGoodsCategory3(goodsDetailsDto.getGoodsCategory3());
        response.setSaleYesNo(goodsDetailsDto.getSaleYesNo());
        response.setSaleNgMessage(goodsDetailsDto.getSaleNgMessage());
        response.setDeliveryYesNo(goodsDetailsDto.getDeliveryYesNo());
        // 2023-renew AddNo5 from here
        response.setGoodsPriceInTaxHight(goodsDetailsDto.getGoodsPriceInTaxHight());
        response.setGoodsPriceInTaxLow(goodsDetailsDto.getGoodsPriceInTaxLow());
        response.setPreDiscountPriceHight(goodsDetailsDto.getPreDiscountPriceHight());
        response.setPreDiscountPriceLow(goodsDetailsDto.getPreDiscountPriceLow());
        // 2023-renew AddNo5 to here

        if (goodsDetailsDto.getGoodsTaxType() != null) {
            response.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType().getValue());
        }
        if (goodsDetailsDto.getAlcoholFlag() != null) {
            response.setAlcoholFlag(goodsDetailsDto.getAlcoholFlag().getValue());
        }
        if (goodsDetailsDto.getSaleStatusPC() != null) {
            response.setSaleStatus(goodsDetailsDto.getSaleStatusPC().getValue());
        }
        if (goodsDetailsDto.getUnitManagementFlag() != null) {
            response.setUnitManagementFlag(goodsDetailsDto.getUnitManagementFlag().getValue());
        }
        if (goodsDetailsDto.getStockManagementFlag() != null) {
            response.setStockManagementFlag(goodsDetailsDto.getStockManagementFlag().getValue());
        }
        if (goodsDetailsDto.getIndividualDeliveryType() != null) {
            response.setIndividualDeliveryType(goodsDetailsDto.getIndividualDeliveryType().getValue());
        }
        if (goodsDetailsDto.getFreeDeliveryFlag() != null) {
            response.setFreeDeliveryFlag(goodsDetailsDto.getFreeDeliveryFlag().getValue());
        }
        if (goodsDetailsDto.getGoodsOpenStatusPC() != null) {
            response.setGoodsOpenStatus(goodsDetailsDto.getGoodsOpenStatusPC().getValue());
        }
        if (goodsDetailsDto.getSnsLinkFlag() != null) {
            response.setSnsLinkFlag(goodsDetailsDto.getSnsLinkFlag().getValue());
        }
        if (goodsDetailsDto.getStockStatusPc() != null) {
            response.setStockStatus(goodsDetailsDto.getStockStatusPc().getValue());
        }
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
        // 2023-renew No92 from here
        if (goodsDetailsDto.getOutletIconFlag() != null) {
            response.setOutletIconFlag(goodsDetailsDto.getOutletIconFlag().getValue());
        }
        // 2023-renew No92 to here
        if (goodsDetailsDto.getLandSendFlag() != null) {
            response.setLandSendFlag(goodsDetailsDto.getLandSendFlag().getValue());
        }
        if (goodsDetailsDto.getCoolSendFlag() != null) {
            response.setCoolSendFlag(goodsDetailsDto.getCoolSendFlag().getValue());
        }
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
        if (goodsDetailsDto.getEmotionPriceType() != null) {
            response.setEmotionPriceType(goodsDetailsDto.getEmotionPriceType().getValue());
        }

        if (goodsDetailsDto.getGoodsGroupImageEntityList() != null) {
            //商品グループ画像エンティティリスト
            List<GoodsGroupImageEntityResponse> goodsGroupImageEntityResponseList = new ArrayList<>();
            for (int i = 0; i < goodsDetailsDto.getGoodsGroupImageEntityList().size(); i++) {
                GoodsGroupImageEntityResponse goodsGroupImageEntityResponse = new GoodsGroupImageEntityResponse();
                GoodsGroupImageEntity goodsGroupImageEntity = goodsDetailsDto.getGoodsGroupImageEntityList().get(i);

                goodsGroupImageEntityResponse.setGoodsGroupSeq(goodsGroupImageEntity.getGoodsGroupSeq());
                goodsGroupImageEntityResponse.setImageTypeVersionNo(goodsGroupImageEntity.getImageTypeVersionNo());
                goodsGroupImageEntityResponse.setImageFileName(goodsGroupImageEntity.getImageFileName());
                goodsGroupImageEntityResponse.setRegistTime(goodsGroupImageEntity.getRegistTime());
                goodsGroupImageEntityResponse.setUpdateTime(goodsGroupImageEntity.getUpdateTime());

                goodsGroupImageEntityResponseList.add(goodsGroupImageEntityResponse);
            }
            response.setGoodsGroupImageEntityList(goodsGroupImageEntityResponseList);
        }

        if (goodsDetailsDto.getUnitImage() != null) {
            GoodsImageEntityResponse goodsImageEntityResponse = new GoodsImageEntityResponse();
            GoodsImageEntity goodsImageEntity = goodsDetailsDto.getUnitImage();

            goodsImageEntityResponse.setGoodsGroupSeq(goodsImageEntity.getGoodsGroupSeq());
            goodsImageEntityResponse.setGoodsSeq(goodsImageEntity.getGoodsSeq());
            goodsImageEntityResponse.setImageFileName(goodsImageEntity.getImageFileName());
            goodsImageEntityResponse.setDisplayFlag(goodsImageEntity.getDisplayFlag() != null ?
                                                                    goodsImageEntity.getDisplayFlag().getValue() :
                                                                    null);
            goodsImageEntityResponse.setTmpFilePath(goodsImageEntity.getTmpFilePath());
            goodsImageEntityResponse.setRegistTime(goodsImageEntity.getRegistTime());
            goodsImageEntityResponse.setUpdateTime(goodsImageEntity.getUpdateTime());

            if (goodsDetailsDto.getUnitImage().getDisplayFlag() != null) {
                goodsImageEntityResponse.setDisplayFlag(goodsDetailsDto.getUnitImage().getDisplayFlag().getValue());
            }
            response.setUnitImage(goodsImageEntityResponse);
        }

        return response;
    }

    /**
     * 商品詳細DtoMAP取得レスポンスに変換
     *
     * @param goodsDetailsDtoMap 商品詳細DtoMAP取得
     * @return 商品詳細DtoMAP取得レスポンス
     */
    public Map<String, GoodsDetailsDtoResponse> toGoodsDetailsDtoResponseMap(Map<String, GoodsDetailsDto> goodsDetailsDtoMap)
                    throws InvocationTargetException, IllegalAccessException {
        Map<String, GoodsDetailsDtoResponse> response = new HashMap<>();

        for (String key : goodsDetailsDtoMap.keySet()) {
            GoodsDetailsDtoResponse goodsDetailsDtoResponse = toGoodsDetailsDtoResponse(goodsDetailsDtoMap.get(key));
            response.put(key, goodsDetailsDtoResponse);
        }

        return response;
    }

    /**
     * マルチカテゴリ一覧レスポンスに変換
     *
     * @param multipleCategoryMap Map as categoryId as key and list of MultipleCategoryGoodsDetailsDto as values
     * @return マルチカテゴリ一覧レスポンス
     */
    public MultipleCategoryMapResponse toMultipleCategoryMapResponse(Map<String, List<MultipleCategoryGoodsDetailsDto>> multipleCategoryMap) {

        if (multipleCategoryMap == null) {
            return null;
        }

        Map<String, List<MultipleCategoryGoodsDetailsDtoResponse>> multipleCategoryList = new HashMap<>();
        multipleCategoryMap.keySet().forEach(key -> {
            List<MultipleCategoryGoodsDetailsDtoResponse> multipleCategoryGoodsDetailsResponses = new ArrayList<>();
            multipleCategoryMap.get(key).forEach(multipleCategoryGoodsDetailsDto -> {
                MultipleCategoryGoodsDetailsDtoResponse response = new MultipleCategoryGoodsDetailsDtoResponse();

                response.setGoodsGroupSeq(multipleCategoryGoodsDetailsDto.getGoodsGroupSeq());
                response.setGgcd(multipleCategoryGoodsDetailsDto.getGgcd());
                response.setHref(multipleCategoryGoodsDetailsDto.getHref());
                response.setGoodsGroupName(multipleCategoryGoodsDetailsDto.getGoodsGroupName());
                response.setGoodsGroupImageThumbnail(multipleCategoryGoodsDetailsDto.getGoodsGroupImageThumbnail());
                response.setGoodsDisplayPrice(multipleCategoryGoodsDetailsDto.getGoodsDisplayPrice());
                response.setGoodsPreDiscountPrice(multipleCategoryGoodsDetailsDto.getGoodsPreDiscountPrice());
                response.setPreDiscountPrice(multipleCategoryGoodsDetailsDto.getPreDiscountPrice());
                response.setGoodsImageItem(multipleCategoryGoodsDetailsDto.getGoodsImageItem());
                response.setGoodsPrice(multipleCategoryGoodsDetailsDto.getGoodsPrice());
                response.setGoodsPriceInTax(multipleCategoryGoodsDetailsDto.getGoodsPriceInTax());
                response.setPreDiscountPrice(multipleCategoryGoodsDetailsDto.getPreDiscountPrice());
                response.setPreDisCountPriceInTax(multipleCategoryGoodsDetailsDto.getPreDisCountPriceInTax());
                response.setGoodsNote1(multipleCategoryGoodsDetailsDto.getGoodsNote1());
                response.setTaxRate(multipleCategoryGoodsDetailsDto.getTaxRate());
                if (multipleCategoryGoodsDetailsDto.getGoodsTaxType() != null) {
                    response.setGoodsTaxType(multipleCategoryGoodsDetailsDto.getGoodsTaxType().getValue());
                }
                response.setWhatsnewDate(multipleCategoryGoodsDetailsDto.getWhatsnewDate());
                response.setStockStatus(multipleCategoryGoodsDetailsDto.getStockStatusPc());
                response.setGoodsIconItems(toListMultipleCategoryGoodsDetailsDtoResponse(
                                multipleCategoryGoodsDetailsDto.getGoodsIconItems()));
                response.setIconName(multipleCategoryGoodsDetailsDto.getIconName());
                response.setIconColorCode(multipleCategoryGoodsDetailsDto.getIconColorCode());
                response.setIsNewDate(multipleCategoryGoodsDetailsDto.isNewDate());
                response.setIsGoodsPreDiscount(multipleCategoryGoodsDetailsDto.isGoodsPreDiscount());
                response.setIsGoodsDisplayPriceRange(multipleCategoryGoodsDetailsDto.isGoodsDisplayPriceRange());
                response.setIsGoodsDisplayPreDiscountPriceRange(
                                multipleCategoryGoodsDetailsDto.isGoodsDisplayPreDiscountPriceRange());
                response.setIsStockStatusDisplay(multipleCategoryGoodsDetailsDto.isStockStatusDisplay());
                response.setIsStockNoSaleDisp(multipleCategoryGoodsDetailsDto.isStockNoSaleDisp());
                response.setIsStockSoldOutIconDisp(multipleCategoryGoodsDetailsDto.isStockSoldOutIconDisp());
                response.setIsStockBeforeSaleIconDisp(multipleCategoryGoodsDetailsDto.isStockBeforeSaleIconDisp());
                response.setIsStockNoStockIconDisp(multipleCategoryGoodsDetailsDto.isStockNoStockIconDisp());
                response.setIsStockFewIconDisp(multipleCategoryGoodsDetailsDto.isStockFewIconDisp());
                response.setIsStockPossibleSalesIconDisp(
                                multipleCategoryGoodsDetailsDto.isStockPossibleSalesIconDisp());
                response.setIsGoodsGroupImage(multipleCategoryGoodsDetailsDto.isGoodsGroupImage());
                response.setGroupSalePriceIntegrityFlag(
                                multipleCategoryGoodsDetailsDto.isGroupSalePriceIntegrityFlag());
                response.setNecessaryLoginGoods(multipleCategoryGoodsDetailsDto.isNecessaryLoginGoods());
                response.setPriceHideGoods(multipleCategoryGoodsDetailsDto.isPriceHideGoods());
                response.setNoDispGoods(multipleCategoryGoodsDetailsDto.isNoDispGoods());
                response.setSaleIconFlag(multipleCategoryGoodsDetailsDto.isSaleIconFlag());
                response.setReserveIconFlag(multipleCategoryGoodsDetailsDto.isReserveIconFlag());
                response.setNewIconFlag(multipleCategoryGoodsDetailsDto.isNewIconFlag());
                response.setSale(multipleCategoryGoodsDetailsDto.isSale());

                // 2023-renew AddNo5 from here
                response.setGoodsGroupMaxPricePc(multipleCategoryGoodsDetailsDto.getGoodsGroupMaxPricePc());
                response.setGoodsGroupMinPricePc(multipleCategoryGoodsDetailsDto.getGoodsGroupMinPricePc());
                response.setGoodsGroupMaxPriceMb(multipleCategoryGoodsDetailsDto.getGoodsGroupMaxPriceMb());
                response.setGoodsGroupMinPriceMb(multipleCategoryGoodsDetailsDto.getGoodsGroupMinPriceMb());
                // 2023-renew AddNo5 to here

                multipleCategoryGoodsDetailsResponses.add(response);
            });
            multipleCategoryList.put(key, multipleCategoryGoodsDetailsResponses);
        });

        MultipleCategoryMapResponse multipleCategoryMapResponse = new MultipleCategoryMapResponse();
        multipleCategoryMapResponse.setMultipleCategoryMap(multipleCategoryList);
        return multipleCategoryMapResponse;
    }

    /**
     * To list multiple category goods details response list.
     *
     * @param goodsIconItems 商品アイコンリスト
     * @return マルチカテゴリ一覧レスポンスリスト
     */
    public List<MultipleCategoryGoodsDetailsDtoResponse> toListMultipleCategoryGoodsDetailsDtoResponse(List<MultipleCategoryGoodsDetailsDto> goodsIconItems) {
        if (goodsIconItems == null) {
            return new ArrayList<>();
        }

        List<MultipleCategoryGoodsDetailsDtoResponse> multipleCategoryGoodsDetailsDtoResponses = new ArrayList<>();
        for (MultipleCategoryGoodsDetailsDto i : goodsIconItems) {
            MultipleCategoryGoodsDetailsDtoResponse multipleCategoryGoodsDetailsDtoResponse =
                            new MultipleCategoryGoodsDetailsDtoResponse();
            multipleCategoryGoodsDetailsDtoResponse.setIconName(i.getIconName());
            multipleCategoryGoodsDetailsDtoResponse.setIconColorCode(i.getIconColorCode());
            multipleCategoryGoodsDetailsDtoResponses.add(multipleCategoryGoodsDetailsDtoResponse);
        }

        return multipleCategoryGoodsDetailsDtoResponses;
    }

    /**
     * 商品詳細レスポンスに変換
     *
     * @param footprintGoodsGroupDtoList 商品グループDTO一覧
     * @return 商品詳細レスポンス
     */
    public List<GoodsGroupDtoResponse> toListGoodsGroupDtoResponse(List<GoodsGroupDto> footprintGoodsGroupDtoList) {
        if (footprintGoodsGroupDtoList == null) {
            return new ArrayList<>();
        }

        List<GoodsGroupDtoResponse> goodsGroupDtoResponses = new ArrayList<>();
        footprintGoodsGroupDtoList.forEach(goodsGroupDto -> {
            GoodsGroupDtoResponse goodsGroupDtoResponse = toGoodsGroupDtoResponse(goodsGroupDto);
            goodsGroupDtoResponses.add(goodsGroupDtoResponse);
        });
        return goodsGroupDtoResponses;
    }

    /**
     * 商品グループレスポンスに変換
     *
     * @param goodsGroupEntity 商品グループクラス
     * @return 商品グループレスポンス
     */
    public GoodsGroupEntityResponse toGoodsGroupEntityResponse(GoodsGroupEntity goodsGroupEntity) {
        if (goodsGroupEntity == null) {
            return null;
        }

        GoodsGroupEntityResponse goodsGroupEntityResponse = new GoodsGroupEntityResponse();

        goodsGroupEntityResponse.setGoodsGroupSeq(goodsGroupEntity.getGoodsGroupSeq());
        goodsGroupEntityResponse.setGoodsGroupCode(goodsGroupEntity.getGoodsGroupCode());
        goodsGroupEntityResponse.setGoodsGroupName(goodsGroupEntity.getGoodsGroupName());
        goodsGroupEntityResponse.setWhatsnewDate(goodsGroupEntity.getWhatsnewDate());

        if (goodsGroupEntity.getGoodsOpenStatusPC() != null) {
            goodsGroupEntityResponse.setGoodsOpenStatus(goodsGroupEntity.getGoodsOpenStatusPC().getValue());
        }
        goodsGroupEntityResponse.setOpenStartTime(goodsGroupEntity.getOpenStartTimePC());
        goodsGroupEntityResponse.setOpenEndTime(goodsGroupEntity.getOpenEndTimePC());
        goodsGroupEntityResponse.setGoodsPreDiscountPrice(goodsGroupEntity.getGoodsPreDiscountPrice());
        if (goodsGroupEntity.getGoodsTaxType() != null) {
            goodsGroupEntityResponse.setGoodsTaxType(goodsGroupEntity.getGoodsTaxType().getValue());
        }
        goodsGroupEntityResponse.setTaxRate(goodsGroupEntity.getTaxRate());
        if (goodsGroupEntity.getAlcoholFlag() != null) {
            goodsGroupEntityResponse.setAlcoholFlag(goodsGroupEntity.getAlcoholFlag().getValue());
        }
        goodsGroupEntityResponse.setGoodsGroupMaxPrice(goodsGroupEntity.getGoodsGroupMaxPricePc());
        goodsGroupEntityResponse.setGoodsGroupMinPrice(goodsGroupEntity.getGoodsGroupMinPricePc());
        goodsGroupEntityResponse.setTaxRate(goodsGroupEntity.getTaxRate());
        if (goodsGroupEntity.getSnsLinkFlag() != null) {
            goodsGroupEntityResponse.setSnsLinkFlag(goodsGroupEntity.getSnsLinkFlag().getValue());
        }
        goodsGroupEntityResponse.setVersionNo(goodsGroupEntity.getVersionNo());
        goodsGroupEntityResponse.setRegistTime(goodsGroupEntity.getRegistTime());
        goodsGroupEntityResponse.setUpdateTime(goodsGroupEntity.getUpdateTime());
        goodsGroupEntityResponse.setPreDiscountMaxPrice(goodsGroupEntity.getPreDiscountMaxPrice());
        goodsGroupEntityResponse.setPreDiscountMinPrice(goodsGroupEntity.getPreDiscountMinPrice());
        if (goodsGroupEntity.getGoodsClassType() != null) {
            goodsGroupEntityResponse.setGoodsClassType(goodsGroupEntity.getGoodsClassType().getValue());
        }
        if (goodsGroupEntity.getDentalMonopolySalesFlg() != null) {
            goodsGroupEntityResponse.setDentalMonopolySalesFlg(goodsGroupEntity.getDentalMonopolySalesFlg().getValue());
        }
        goodsGroupEntityResponse.setCatalogDisplayOrder(goodsGroupEntity.getCatalogDisplayOrder());
        goodsGroupEntityResponse.setGroupPrice(goodsGroupEntity.getGroupPrice());
        goodsGroupEntityResponse.setGroupSalePrice(goodsGroupEntity.getGroupSalePrice());
        if (goodsGroupEntity.getGroupPriceMarkDispFlag() != null) {
            goodsGroupEntityResponse.setGroupPriceMarkDispFlag(goodsGroupEntity.getGroupPriceMarkDispFlag().getValue());
        }
        if (goodsGroupEntity.getGroupSalePriceMarkDispFlag() != null) {
            goodsGroupEntityResponse.setGroupSalePriceMarkDispFlag(
                            goodsGroupEntity.getGroupSalePriceMarkDispFlag().getValue());
        }
        if (goodsGroupEntity.getGroupSalePriceIntegrityFlag() != null) {
            goodsGroupEntityResponse.setGroupSalePriceIntegrityFlag(
                            goodsGroupEntity.getGroupSalePriceIntegrityFlag().getValue());
        }
        // 2023-renew AddNo5 from here
        goodsGroupEntityResponse.setGoodsGroupMaxPricePc(goodsGroupEntity.getGoodsGroupMaxPricePc());
        goodsGroupEntityResponse.setGoodsGroupMinPricePc(goodsGroupEntity.getGoodsGroupMinPricePc());
        goodsGroupEntityResponse.setGoodsGroupMaxPriceMb(goodsGroupEntity.getGoodsGroupMaxPriceMb());
        goodsGroupEntityResponse.setGoodsGroupMinPriceMb(goodsGroupEntity.getGoodsGroupMinPriceMb());
        // 2023-renew AddNo5 to here
        return goodsGroupEntityResponse;
    }

    /**
     * 在庫状態表示用レスポンスに変換
     *
     * @param entity 商品グループ在庫表示クラス
     * @return 在庫状態表示用レスポンス
     */
    public StockStatusDisplayEntityResponse toStockStatusDisplayEntityResponse(StockStatusDisplayEntity entity) {

        // 商品グループ在庫表示更新バッチの処理前は存在しないためnullを返す
        if (entity == null) {
            return null;
        }

        StockStatusDisplayEntityResponse stockStatusDisplayResponse = new StockStatusDisplayEntityResponse();

        stockStatusDisplayResponse.setGoodsGroupSeq(entity.getGoodsGroupSeq());
        if (entity.getStockStatusPc() != null) {
            stockStatusDisplayResponse.setStockStatus(entity.getStockStatusPc().getValue());
        }
        stockStatusDisplayResponse.setRegistTime(entity.getRegistTime());
        stockStatusDisplayResponse.setUpdateTime(entity.getUpdateTime());

        return stockStatusDisplayResponse;
    }

    /**
     * 商品グループ表示レスポンスに変換
     *
     * @param goodsGroupDisplayEntity 商品グループ表示クラス
     * @return 商品グループ表示レスポンス
     */
    public GoodsGroupDisplayEntityResponse toGoodsGroupDisplayEntityResponse(GoodsGroupDisplayEntity goodsGroupDisplayEntity) {
        if (goodsGroupDisplayEntity == null) {
            return null;
        }

        GoodsGroupDisplayEntityResponse goodsGroupDisplayResponse = new GoodsGroupDisplayEntityResponse();

        goodsGroupDisplayResponse.setGoodsGroupSeq(goodsGroupDisplayEntity.getGoodsGroupSeq());
        goodsGroupDisplayResponse.setInformationIcon(goodsGroupDisplayEntity.getInformationIconPC());
        goodsGroupDisplayResponse.setSearchKeyword(goodsGroupDisplayEntity.getSearchKeyword());
        goodsGroupDisplayResponse.setSearchKeywordEm(goodsGroupDisplayEntity.getSearchKeywordEm());
        if (goodsGroupDisplayEntity.getUnitManagementFlag() != null) {
            goodsGroupDisplayResponse.setUnitManagementFlag(goodsGroupDisplayEntity.getUnitManagementFlag().getValue());
        }

        goodsGroupDisplayResponse.setUnitTitle1(goodsGroupDisplayEntity.getUnitTitle1());
        goodsGroupDisplayResponse.setUnitTitle2(goodsGroupDisplayEntity.getUnitTitle2());
        goodsGroupDisplayResponse.setMetaDescription(goodsGroupDisplayEntity.getMetaDescription());
        goodsGroupDisplayResponse.setMetaKeyword(goodsGroupDisplayEntity.getMetaKeyword());
        goodsGroupDisplayResponse.setDeliveryType(goodsGroupDisplayEntity.getDeliveryType());
        goodsGroupDisplayResponse.setGoodsNote1(goodsGroupDisplayEntity.getGoodsNote1());
        //2023-renew No64 from here
        goodsGroupDisplayResponse.setGoodsNote1Sub(goodsGroupDisplayEntity.getGoodsNote1Sub());
        goodsGroupDisplayResponse.setGoodsNote1OpenStartTime(goodsGroupDisplayEntity.getGoodsNote1OpenStartTime());
        goodsGroupDisplayResponse.setGoodsNote1SubOpenStartTime(goodsGroupDisplayEntity.getGoodsNote1SubOpenStartTime());
        goodsGroupDisplayResponse.setGoodsNote2(goodsGroupDisplayEntity.getGoodsNote2());
        goodsGroupDisplayResponse.setGoodsNote2Sub(goodsGroupDisplayEntity.getGoodsNote2Sub());
        goodsGroupDisplayResponse.setGoodsNote2OpenStartTime(goodsGroupDisplayEntity.getGoodsNote2OpenStartTime());
        goodsGroupDisplayResponse.setGoodsNote2SubOpenStartTime(goodsGroupDisplayEntity.getGoodsNote2SubOpenStartTime());
        goodsGroupDisplayResponse.setGoodsNote3(goodsGroupDisplayEntity.getGoodsNote3());
        goodsGroupDisplayResponse.setGoodsNote4(goodsGroupDisplayEntity.getGoodsNote4());
        goodsGroupDisplayResponse.setGoodsNote4Sub(goodsGroupDisplayEntity.getGoodsNote4Sub());
        goodsGroupDisplayResponse.setGoodsNote4OpenStartTime(goodsGroupDisplayEntity.getGoodsNote4OpenStartTime());
        goodsGroupDisplayResponse.setGoodsNote4SubOpenStartTime(goodsGroupDisplayEntity.getGoodsNote4SubOpenStartTime());
        goodsGroupDisplayResponse.setGoodsNote5(goodsGroupDisplayEntity.getGoodsNote5());
        goodsGroupDisplayResponse.setGoodsNote6(goodsGroupDisplayEntity.getGoodsNote6());
        goodsGroupDisplayResponse.setGoodsNote7(goodsGroupDisplayEntity.getGoodsNote7());
        goodsGroupDisplayResponse.setGoodsNote8(goodsGroupDisplayEntity.getGoodsNote8());
        goodsGroupDisplayResponse.setGoodsNote9(goodsGroupDisplayEntity.getGoodsNote9());
        goodsGroupDisplayResponse.setGoodsNote10(goodsGroupDisplayEntity.getGoodsNote10());
        goodsGroupDisplayResponse.setGoodsNote10Sub(goodsGroupDisplayEntity.getGoodsNote10Sub());
        goodsGroupDisplayResponse.setGoodsNote10OpenStartTime(goodsGroupDisplayEntity.getGoodsNote10OpenStartTime());
        goodsGroupDisplayResponse.setGoodsNote10SubOpenStartTime(goodsGroupDisplayEntity.getGoodsNote10SubOpenStartTime());
        //2023-renew No64 to here
        goodsGroupDisplayResponse.setGoodsNote11(goodsGroupDisplayEntity.getGoodsNote11());
        goodsGroupDisplayResponse.setGoodsNote12(goodsGroupDisplayEntity.getGoodsNote12());
        goodsGroupDisplayResponse.setGoodsNote13(goodsGroupDisplayEntity.getGoodsNote13());
        goodsGroupDisplayResponse.setGoodsNote14(goodsGroupDisplayEntity.getGoodsNote14());
        goodsGroupDisplayResponse.setGoodsNote15(goodsGroupDisplayEntity.getGoodsNote15());
        goodsGroupDisplayResponse.setGoodsNote16(goodsGroupDisplayEntity.getGoodsNote16());
        goodsGroupDisplayResponse.setGoodsNote17(goodsGroupDisplayEntity.getGoodsNote17());
        goodsGroupDisplayResponse.setGoodsNote18(goodsGroupDisplayEntity.getGoodsNote18());
        goodsGroupDisplayResponse.setGoodsNote19(goodsGroupDisplayEntity.getGoodsNote19());
        goodsGroupDisplayResponse.setGoodsNote20(goodsGroupDisplayEntity.getGoodsNote20());
        // 2023-renew No11 from here
        goodsGroupDisplayResponse.setGoodsNote21(goodsGroupDisplayEntity.getGoodsNote21());
        // 2023-renew No11 to here
        // 2023-renew No0 from here
        goodsGroupDisplayResponse.setGoodsNote22(goodsGroupDisplayEntity.getGoodsNote22());
        // 2023-renew No0 to here
        goodsGroupDisplayResponse.setOrderSetting1(goodsGroupDisplayEntity.getOrderSetting1());
        goodsGroupDisplayResponse.setOrderSetting2(goodsGroupDisplayEntity.getOrderSetting2());
        goodsGroupDisplayResponse.setOrderSetting3(goodsGroupDisplayEntity.getOrderSetting3());
        goodsGroupDisplayResponse.setOrderSetting4(goodsGroupDisplayEntity.getOrderSetting4());
        goodsGroupDisplayResponse.setOrderSetting5(goodsGroupDisplayEntity.getOrderSetting5());
        goodsGroupDisplayResponse.setOrderSetting6(goodsGroupDisplayEntity.getOrderSetting6());
        goodsGroupDisplayResponse.setOrderSetting7(goodsGroupDisplayEntity.getOrderSetting7());
        goodsGroupDisplayResponse.setOrderSetting8(goodsGroupDisplayEntity.getOrderSetting8());
        goodsGroupDisplayResponse.setOrderSetting9(goodsGroupDisplayEntity.getOrderSetting9());
        goodsGroupDisplayResponse.setOrderSetting10(goodsGroupDisplayEntity.getOrderSetting10());
        goodsGroupDisplayResponse.setRegistTime(goodsGroupDisplayEntity.getRegistTime());
        goodsGroupDisplayResponse.setUpdateTime(goodsGroupDisplayEntity.getUpdateTime());
        if (goodsGroupDisplayEntity.getSaleIconFlag() != null) {
            goodsGroupDisplayResponse.setSaleIconFlag(goodsGroupDisplayEntity.getSaleIconFlag().getValue());
        }
        if (goodsGroupDisplayEntity.getReserveIconFlag() != null) {
            goodsGroupDisplayResponse.setReserveIconFlag(goodsGroupDisplayEntity.getReserveIconFlag().getValue());
        }
        if (goodsGroupDisplayEntity.getNewIconFlag() != null) {
            goodsGroupDisplayResponse.setNewIconFlag(goodsGroupDisplayEntity.getNewIconFlag().getValue());
        }
        // 2023-renew No92 from here
        if (goodsGroupDisplayEntity.getOutletIconFlag() != null) {
            goodsGroupDisplayResponse.setOutletIconFlag(goodsGroupDisplayEntity.getOutletIconFlag().getValue());
        }
        // 2023-renew No92 to here

        return goodsGroupDisplayResponse;
    }

    /**
     * 商品グループ画像レスポンスに変換
     *
     * @param goodsGroupImageEntityList　商品グループ画像クラス
     * @return 商品グループ画像レスポンス
     */
    public List<GoodsGroupImageEntityResponse> toListGoodsGroupImageEntityResponse(List<GoodsGroupImageEntity> goodsGroupImageEntityList) {
        if (goodsGroupImageEntityList == null) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntityResponse> goodsGroupImageEntityResponses = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(goodsGroupImageEntityList)) {
            goodsGroupImageEntityList.forEach(item -> {
                GoodsGroupImageEntityResponse goodsGroupImageEntityResponse = new GoodsGroupImageEntityResponse();

                goodsGroupImageEntityResponse.setGoodsGroupSeq(item.getGoodsGroupSeq());
                goodsGroupImageEntityResponse.setImageTypeVersionNo(item.getImageTypeVersionNo());
                goodsGroupImageEntityResponse.setImageFileName(item.getImageFileName());
                goodsGroupImageEntityResponse.setRegistTime(item.getRegistTime());
                goodsGroupImageEntityResponse.setUpdateTime(item.getUpdateTime());

                goodsGroupImageEntityResponses.add(goodsGroupImageEntityResponse);
            });
        }

        return goodsGroupImageEntityResponses;
    }

    /**
     * 商品Dtoレスポンスに変換
     *
     * @param goodsDtoList 商品Dtoクラス
     * @return 商品Dtoレスポンスリスト
     */
    public List<GoodsDtoResponse> toListGoodsDtoResponse(List<GoodsDto> goodsDtoList) {
        if (goodsDtoList == null) {
            return new ArrayList<>();
        }

        List<GoodsDtoResponse> goodsResponseList = new ArrayList<>();
        goodsDtoList.forEach(item -> {
            GoodsDtoResponse goodsResponse = new GoodsDtoResponse();
            if (item.getGoodsEntity() != null) {
                GoodsEntityResponse goodsEntityResponse = toGoodsEntityResponse(item.getGoodsEntity());
                goodsResponse.setGoodsEntity(goodsEntityResponse);
            }

            if (item.getStockDto() != null) {
                StockDtoResponse stockResponse = toStockDtoResponse(item.getStockDto());
                goodsResponse.setStockDto(stockResponse);
            }
            goodsResponse.setDeleteFlg(item.getDeleteFlg());
            if (item.getStockStatusPc() != null) {
                goodsResponse.setStockStatus(item.getStockStatusPc().getValue());
            }
            if (item.getUnitImage() != null) {
                GoodsImageEntityResponse goodsImageEntity = toGoodsImageEntityResponse(item.getUnitImage());
                goodsResponse.setUnitImage(goodsImageEntity);
            }

            goodsResponseList.add(goodsResponse);
        });

        return goodsResponseList;
    }

    /**
     * レスポンスに変換
     *
     * @param goodsEntity 商品クラス
     * @return 商品クラス
     */
    public GoodsEntityResponse toGoodsEntityResponse(GoodsEntity goodsEntity) {
        if (goodsEntity == null) {
            return null;
        }

        GoodsEntityResponse response = new GoodsEntityResponse();
        response.setGoodsSeq(goodsEntity.getGoodsSeq());
        response.setGoodsGroupSeq(goodsEntity.getGoodsGroupSeq());
        response.setGoodsCode(goodsEntity.getGoodsCode());
        response.setJanCode(goodsEntity.getJanCode());
        response.setCatalogCode(goodsEntity.getCatalogCode());
        if (goodsEntity.getSaleStatusPC() != null) {
            response.setSaleStatus(goodsEntity.getSaleStatusPC().getValue());
        }
        response.setSaleStartTime(goodsEntity.getSaleStartTimePC());
        response.setSaleEndTime(goodsEntity.getSaleEndTimePC());
        response.setGoodsPrice(goodsEntity.getGoodsPrice());
        response.setPreDiscountPrice(goodsEntity.getPreDiscountPrice());
        if (goodsEntity.getIndividualDeliveryType() != null) {
            response.setIndividualDeliveryType(goodsEntity.getIndividualDeliveryType().getValue());
        }
        if (goodsEntity.getFreeDeliveryFlag() != null) {
            response.setFreeDeliveryFlag(goodsEntity.getFreeDeliveryFlag().getValue());
        }
        if (goodsEntity.getUnitManagementFlag() != null) {
            response.setUnitManagementFlag(goodsEntity.getUnitManagementFlag().getValue());
        }
        response.setUnitValue1(goodsEntity.getUnitValue1());
        response.setUnitValue2(goodsEntity.getUnitValue2());
        if (goodsEntity.getStockManagementFlag() != null) {
            response.setStockManagementFlag(goodsEntity.getStockManagementFlag().getValue());
        }
        response.setPurchasedMax(goodsEntity.getPurchasedMax());
        response.setOrderDisplay(goodsEntity.getOrderDisplay());
        response.setVersionNo(goodsEntity.getVersionNo());
        response.setRegistTime(goodsEntity.getRegistTime());
        response.setUpdateTime(goodsEntity.getUpdateTime());
        if (goodsEntity.getReserveFlag() != null) {
            response.setReserveFlag(goodsEntity.getReserveFlag().getValue());
        }
        response.setUnit(goodsEntity.getUnit());
        if (goodsEntity.getPriceMarkDispFlag() != null) {
            response.setPriceMarkDispFlag(goodsEntity.getPriceMarkDispFlag().getValue());
        }
        if (goodsEntity.getSalePriceMarkDispFlag() != null) {
            response.setSalePriceMarkDispFlag(goodsEntity.getSalePriceMarkDispFlag().getValue());
        }
        response.setGoodsManagementCode(goodsEntity.getGoodsManagementCode());
        response.setGoodsDivisionCode(goodsEntity.getGoodsDivisionCode());
        response.setGoodsCategory1(goodsEntity.getGoodsCategory1());
        response.setGoodsCategory2(goodsEntity.getGoodsCategory2());
        response.setGoodsCategory3(goodsEntity.getGoodsCategory3());
        if (goodsEntity.getLandSendFlag() != null) {
            response.setLandSendFlag(goodsEntity.getLandSendFlag().getValue());
        }
        if (goodsEntity.getCoolSendFlag() != null) {
            response.setCoolSendFlag(goodsEntity.getCoolSendFlag().getValue());
        }
        response.setCoolSendFrom(goodsEntity.getCoolSendFrom());
        response.setCoolSendTo(goodsEntity.getCoolSendTo());
        if (goodsEntity.getSalePriceIntegrityFlag() != null) {
            response.setSalePriceIntegrityFlag(goodsEntity.getSalePriceIntegrityFlag().getValue());
        }
        if (goodsEntity.getEmotionPriceType() != null) {
            response.setEmotionPriceType(goodsEntity.getEmotionPriceType().getValue());
        }
        // 2023-renew No92 from here
        response.setGoodsPriceInTaxLow(goodsEntity.getGoodsPriceInTaxLow());
        response.setGoodsPriceInTaxHight(goodsEntity.getGoodsPriceInTaxHight());
        response.setPreDiscountPriceLow(goodsEntity.getPreDiscountPriceLow());
        response.setPreDiscountPriceHight(goodsEntity.getPreDiscountPriceHight());
        // 2023-renew No92 to here
        return response;
    }

    /**
     * 在庫Dtoレスポンスに変換
     *
     * @param stockDto 在庫Dto
     * @return 在庫Dtoレスポンス
     */
    public StockDtoResponse toStockDtoResponse(StockDto stockDto) {
        if (stockDto == null) {
            return null;
        }

        StockDtoResponse stockResponse = new StockDtoResponse();
        stockResponse.setGoodsSeq(stockDto.getGoodsSeq());
        stockResponse.setSalesPossibleStock(stockDto.getSalesPossibleStock());
        stockResponse.setRealStock(stockDto.getRealStock());
        stockResponse.setOrderReserveStock(stockDto.getOrderReserveStock());
        stockResponse.setRemainderFewStock(stockDto.getRemainderFewStock());
        stockResponse.setSupplementCount(stockDto.getSupplementCount());
        stockResponse.setOrderPointStock(stockDto.getOrderPointStock());
        stockResponse.setSafetyStock(stockDto.getSafetyStock());
        stockResponse.setRegistTime(stockDto.getRegistTime());
        stockResponse.setUpdateTime(stockDto.getUpdateTime());

        return stockResponse;
    }

    /**
     * カテゴリ登録商品レスポンスリストに変換
     *
     * @param categoryGoodsEntityList カテゴリ登録商品リスト
     * @return カテゴリ登録商品レスポンスリスト
     */
    public List<CategoryGoodsEntityResponse> toListCategoryGoodsEntityResponse(List<CategoryGoodsEntity> categoryGoodsEntityList) {
        if (categoryGoodsEntityList == null) {
            return new ArrayList<>();
        }

        List<CategoryGoodsEntityResponse> categoryGoodsResponseList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(categoryGoodsEntityList)) {
            categoryGoodsEntityList.forEach(categoryGoodsEntity -> {
                CategoryGoodsEntityResponse categoryGoodsResponse = new CategoryGoodsEntityResponse();

                categoryGoodsResponse.setCategorySeq(categoryGoodsEntity.getCategorySeq());
                categoryGoodsResponse.setGoodsGroupSeq(categoryGoodsEntity.getGoodsGroupSeq());
                categoryGoodsResponse.setOrderDisplay(categoryGoodsEntity.getOrderDisplay());
                categoryGoodsResponse.setRegistTime(categoryGoodsEntity.getRegistTime());
                categoryGoodsResponse.setUpdateTime(categoryGoodsEntity.getUpdateTime());

                categoryGoodsResponseList.add(categoryGoodsResponse);
            });
        }
        return categoryGoodsResponseList;
    }

    /**
     * 商品グループ画像レスポンスに変換
     *
     * @param goodsImageEntity 商品画像
     * @return 商品グループ画像レスポンス
     */
    public GoodsImageEntityResponse toGoodsImageEntityResponse(GoodsImageEntity goodsImageEntity) {
        if (goodsImageEntity == null) {
            return null;
        }

        GoodsImageEntityResponse goodsImageEntityResponse = new GoodsImageEntityResponse();
        goodsImageEntityResponse.setGoodsGroupSeq(goodsImageEntity.getGoodsGroupSeq());
        goodsImageEntityResponse.setGoodsSeq(goodsImageEntity.getGoodsSeq());
        goodsImageEntityResponse.setImageFileName(goodsImageEntity.getImageFileName());
        if (goodsImageEntity.getDisplayFlag() != null) {
            goodsImageEntityResponse.setDisplayFlag(goodsImageEntity.getDisplayFlag().getValue());
        }
        goodsImageEntityResponse.setRegistTime(goodsImageEntity.getRegistTime());
        goodsImageEntityResponse.setUpdateTime(goodsImageEntity.getUpdateTime());
        goodsImageEntityResponse.setTmpFilePath(goodsImageEntity.getTmpFilePath());

        return goodsImageEntityResponse;
    }

    /**
     * 商品規格値DTOクラスに変換
     *
     * @param goodsUnit2DtoList 規格値2リスト取得処理
     * @return 商品規格値DTOクラス
     */
    public List<GoodsUnitDtoResponse> toListGoodsUnitDtoResponse(List<GoodsUnitDto> goodsUnit2DtoList) {
        if (goodsUnit2DtoList == null) {
            return new ArrayList<>();
        }

        List<GoodsUnitDtoResponse> goodsUnitDtoResponses = new ArrayList<>();
        goodsUnit2DtoList.forEach(goodsUnitDto -> {
            GoodsUnitDtoResponse goodsUnitDtoResponse = new GoodsUnitDtoResponse();
            goodsUnitDtoResponse.setGoodsCode(goodsUnitDto.getGoodsCode());
            if (goodsUnitDto.getSaleStatusPC() != null) {
                goodsUnitDtoResponse.setSaleStatus(goodsUnitDto.getSaleStatusPC().getValue());
            }
            goodsUnitDtoResponse.setSaleStartTime(goodsUnitDto.getSaleStartTimePC());
            goodsUnitDtoResponse.setSaleEndTime(goodsUnitDto.getSaleEndTimePC());
            if (goodsUnitDto.getStockManagementFlag() != null) {
                goodsUnitDtoResponse.setStockManagementFlag(goodsUnitDto.getStockManagementFlag().getValue());
            }
            goodsUnitDtoResponse.setUnitValue1(goodsUnitDto.getUnitValue1());
            goodsUnitDtoResponse.setUnitValue2(goodsUnitDto.getUnitValue2());
            goodsUnitDtoResponse.setSalesPossibleStock(goodsUnitDto.getSalesPossibleStock());
            goodsUnitDtoResponse.setRemainderFewStock(goodsUnitDto.getRemainderFewStock());

            goodsUnitDtoResponses.add(goodsUnitDtoResponse);
        });
        return goodsUnitDtoResponses;
    }

    /**
     * 商品詳細レスポンスに変換
     *
     * @param goodsGroupDto 商品グループDto
     * @return 商品詳細レスポンス
     */
    public GoodsGroupDtoResponse toGoodsGroupDtoResponse(GoodsGroupDto goodsGroupDto) {
        if (goodsGroupDto == null) {
            return null;
        }

        GoodsGroupDtoResponse goodsGroupDtoResponse = new GoodsGroupDtoResponse();
        goodsGroupDtoResponse.setGoodsGroupEntity(toGoodsGroupEntityResponse(goodsGroupDto.getGoodsGroupEntity()));
        goodsGroupDtoResponse.setBatchUpdateStockStatus(
                        toStockStatusDisplayEntityResponse(goodsGroupDto.getBatchUpdateStockStatus()));
        goodsGroupDtoResponse.setRealTimeStockStatus(
                        toStockStatusDisplayEntityResponse(goodsGroupDto.getRealTimeStockStatus()));
        goodsGroupDtoResponse.setGoodsGroupDisplayEntity(
                        toGoodsGroupDisplayEntityResponse(goodsGroupDto.getGoodsGroupDisplayEntity()));
        goodsGroupDtoResponse.setGoodsGroupImageEntityList(
                        toListGoodsGroupImageEntityResponse(goodsGroupDto.getGoodsGroupImageEntityList()));
        goodsGroupDtoResponse.setGoodsDtoList(toListGoodsDtoResponse(goodsGroupDto.getGoodsDtoList()));
        goodsGroupDtoResponse.setCategoryGoodsEntityList(
                        toListCategoryGoodsEntityResponse(goodsGroupDto.getCategoryGoodsEntityList()));
        goodsGroupDtoResponse.setTaxRate(goodsGroupDto.getTaxRate());
        // 2023-renew No21 from here
        if (goodsGroupDto.getGoodsGroupEntity() != null && goodsGroupDto.getGoodsGroupEntity().getExcludingFlag() != null)  {
            goodsGroupDtoResponse.setExcludingFlag(goodsGroupDto.getGoodsGroupEntity().getExcludingFlag().getValue());
        }
        // 2023-renew No21 to here

        return goodsGroupDtoResponse;
    }

    /**
     * 商品グループDao用検索条件Dtoに変換
     *
     * @param request 公開商品グループ情報検索リクエスト
     * @return 商品グループDao用検索条件Dto
     */
    public GoodsGroupSearchForDaoConditionDto toGoodsGroupSearchForDaoConditionDto(OpenGoodsGroupDetailsListGetRequest request) {
        if (request == null) {
            return null;
        }

        GoodsGroupSearchForDaoConditionDto searchConditionDto = new GoodsGroupSearchForDaoConditionDto();
        searchConditionDto.setCategoryId(request.getCategoryId());
        searchConditionDto.setMinPrice(request.getMinPrice());
        searchConditionDto.setMaxPrice(request.getMaxPrice());
        searchConditionDto.setOpenStatus(
                        EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class, request.getOpenStatus()));
        searchConditionDto.setKeywordLikeCondition1(request.getKeywordLikeCondition1());
        searchConditionDto.setKeywordLikeCondition2(request.getKeywordLikeCondition2());
        searchConditionDto.setKeywordLikeCondition3(request.getKeywordLikeCondition3());
        searchConditionDto.setKeywordLikeCondition4(request.getKeywordLikeCondition4());
        searchConditionDto.setKeywordLikeCondition5(request.getKeywordLikeCondition5());
        searchConditionDto.setKeywordLikeCondition6(request.getKeywordLikeCondition6());
        searchConditionDto.setKeywordLikeCondition7(request.getKeywordLikeCondition7());
        searchConditionDto.setKeywordLikeCondition8(request.getKeywordLikeCondition8());
        searchConditionDto.setKeywordLikeCondition9(request.getKeywordLikeCondition9());
        searchConditionDto.setKeywordLikeCondition10(request.getKeywordLikeCondition10());

        return searchConditionDto;
    }

    /**
     * 関連商品Dao用検索条件Dtoに変換
     *
     * @param request 公開あしあと商品情報取得リクエスト
     * @return 関連商品Dao用検索条件Dto
     */
    public GoodsRelationSearchForDaoConditionDto toGoodsRelationSearchForDaoConditionDto(OpenRelatedGoodsListGetRequest request) {
        if (request == null) {
            return null;
        }

        GoodsRelationSearchForDaoConditionDto conditionDto = new GoodsRelationSearchForDaoConditionDto();
        conditionDto.setGoodsGroupSeq(request.getGoodsGroupSeq());
        conditionDto.setSiteType(EnumTypeUtil.getEnumFromValue(HTypeSiteType.class, request.getOpenStatus()));
        conditionDto.setOpenStatus(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class, request.getOpenStatus()));
        conditionDto.setMemberRank(EnumTypeUtil.getEnumFromValue(HTypeMemberRank.class, request.getMemberRank()));

        return conditionDto;
    }

    /**
     * ページ情報に変換
     *
     * @param pageInfoRequest ページ情報リクエスト
     * @return ページ情報
     */
    public PageInfo toPageInfo(PageInfoRequest pageInfoRequest) {
        if (pageInfoRequest == null) {
            return null;
        }

        PageInfo pageInfo = new PageInfo();
        pageInfo.setLimit(pageInfoRequest.getLimit());
        pageInfo.setOrderField(pageInfoRequest.getOrderBy());
        pageInfo.setOrderAsc(pageInfoRequest.getSort());
        pageInfo.setPage(pageInfoRequest.getPage() != null ? pageInfoRequest.getPage() : 0);
        return pageInfo;
    }

    /**
     * あしあと商品に変換
     *
     * @param request あしあと商品情報登録リクエスト
     * @return あしあと商品
     */
    public FootprintEntity toFootprintEntity(GoodsFootPrintRegistRequest request) {
        if (request == null) {
            return null;
        }

        FootprintEntity footprint = new FootprintEntity();
        footprint.setAccessUid(request.getAccessUid());
        footprint.setGoodsGroupSeq(request.getGoodsGroupSeq());
        footprint.setShopSeq(1001);

        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
        footprint.setRegistTime(conversionUtility.toTimeStamp(request.getRegistTime()));
        footprint.setUpdateTime(conversionUtility.toTimeStamp(request.getUpdateTime()));

        return footprint;
    }

    /**
     * カテゴリ情報Dao用検索条件Dtoに変換
     *
     * @param request カテゴリ検索条件Dtoリクエスト
     * @return カテゴリ情報Dao用検索条件Dto
     */
    public CategorySearchForDaoConditionDto toCategorySearchForDaoConditionDto(CategorySearchForDaoConditionDtoRequest request) {
        if (ObjectUtils.isEmpty(request)) {
            return null;
        }

        CategorySearchForDaoConditionDto conditionDto = new CategorySearchForDaoConditionDto();
        conditionDto.setShopSeq(1001);
        conditionDto.setCategoryId(request.getCategoryId());
        conditionDto.setCategorySeqList(request.getCategorySeqList());
        conditionDto.setMaxHierarchical(request.getMaxHierarchical());
        conditionDto.setSiteType(HTypeSiteType.FRONT_PC);
        conditionDto.setOpenStatus(EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class, request.getOpenStatus()));
        conditionDto.setNotInCategoryIdList(request.getNotInCategoryIdList());
        conditionDto.setOrderField(request.getOrderField());
        conditionDto.setOrderAsc(request.getOrderAsc());

        return conditionDto;
    }

    public Map<String, GoodsDetailsDtoResponse> toMapStringGoodsDetailsDtoResponse(Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap)
                    throws InvocationTargetException, IllegalAccessException {
        if (MapUtils.isEmpty(goodsDetailsDtoMap)) {
            return new HashMap<>();
        }

        Map<String, GoodsDetailsDtoResponse> responseMap = new HashMap<>();
        for (Integer key : goodsDetailsDtoMap.keySet()) {
            GoodsDetailsDtoResponse goodsDetailsDtoResponse = toGoodsDetailsDtoResponse(goodsDetailsDtoMap.get(key));
            responseMap.put(key.toString(), goodsDetailsDtoResponse);
        }

        return responseMap;
    }

    // 2023-renew No65 from here
    /**
     * セール商品詳細情報レスポンスセール商品Dtoに変換
     *
     * @param saleGoodsDtos
     * @return セール商品詳細情報レスリスト
     */
    public List<SaleGoodsListDetailGetResponse> toSaleGoodsListResponse(List<SaleGoodsDto> saleGoodsDtos) {
        if (CollectionUtil.isEmpty(saleGoodsDtos)) {
            return new ArrayList<>();
        }
        return saleGoodsDtos.stream().map(saleGoodsDto -> {
            SaleGoodsListDetailGetResponse responseDetail = new SaleGoodsListDetailGetResponse();
            responseDetail.setGoodsSeq(saleGoodsDto.getGoodsSeq());
            responseDetail.setSaleCd(saleGoodsDto.getSaleCd());
            responseDetail.setPreSaleCd(saleGoodsDto.getPreSaleCd());
            responseDetail.setSaleFrom(saleGoodsDto.getSaleFrom());
            responseDetail.setSaleTo(saleGoodsDto.getSaleTo());
            responseDetail.setSaleStatus(saleGoodsDto.getSaleStatus().getValue());
            return responseDetail;
        }).collect(Collectors.toList());
    }
    // 2023-renew No65 to here
}
