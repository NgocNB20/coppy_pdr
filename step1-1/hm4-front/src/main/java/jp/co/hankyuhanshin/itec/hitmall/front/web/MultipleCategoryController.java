/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.web;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.CategoryApi;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.GoodsApi;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.MultipleCategoryGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.MultipleCategoryGoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.MultipleCategoryMapGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.MultipleCategoryMapResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoUser;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalableGoodsType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.ajax.MultipleCategoryGoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 複数カテゴリコントローラー
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@RestController
@RequestMapping("/")
public class MultipleCategoryController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MultipleCategoryController.class);

    /**
     * カテゴリーAPI
     */
    private final CategoryApi categoryApi;

    /**
     * 商品API
     */
    private final GoodsApi goodsApi;

    /**
     * 変換Utility
     */
    private final ConversionUtility conversionUtility;

    /**
     * 共通情報ヘルパークラス
     */
    private final CommonInfoUtility commonInfoUtility;

    @Autowired
    public MultipleCategoryController(CategoryApi categoryApi,
                                      GoodsApi goodsApi,
                                      ConversionUtility conversionUtility,
                                      CommonInfoUtility commonInfoUtility) {
        this.categoryApi = categoryApi;
        this.goodsApi = goodsApi;
        this.conversionUtility = conversionUtility;
        this.commonInfoUtility = commonInfoUtility;
    }

    /**
     * 複数カテゴリを取得する
     *
     * @param categoryId カテゴリコード
     * @param seq        ソート条件 normal=標準  new=新着順  price=価格順  salableness=売れ筋順
     * @param limit      取得件数
     * @param priceFrom  価格from
     * @param priceTo    価格to
     * @param stock      在庫指定 有りのみ=true 指定なし=false
     * @param viewType   取得画像 リスト=list サムネイル=thumbnail
     * @return MultipleCategoryDataインスタンス
     */
    @GetMapping("/getMultipleCategoryData")
    @ResponseBody
    public MultipleCategoryData getMultipleCategoryData(
                    @RequestParam(name = "categoryId", required = false) String categoryId,
                    @RequestParam(name = "seq", required = false) String seq,
                    @RequestParam(name = "limit", required = false) String limit,
                    @RequestParam(name = "priceFrom", required = false) String priceFrom,
                    @RequestParam(name = "priceTo", required = false) String priceTo,
                    @RequestParam(name = "stock", required = false) String stock,
                    @RequestParam(name = "viewType", required = false) String viewType)
                    throws InvocationTargetException, IllegalAccessException {

        MultipleCategoryData multipleCategoryData = new MultipleCategoryData();
        // Get multiple category map using categoryId, seq and limit with
        // categoryId as key and
        // goods group dto list as value
        MultipleCategoryMapGetRequest multipleCategoryMapGetRequest = new MultipleCategoryMapGetRequest();
        multipleCategoryMapGetRequest.setCategory(categoryId);
        multipleCategoryMapGetRequest.setSeq(seq);
        multipleCategoryMapGetRequest.setLimit(limit);
        multipleCategoryMapGetRequest.setPriceFrom(priceFrom);
        multipleCategoryMapGetRequest.setPriceTo(priceTo);
        multipleCategoryMapGetRequest.setStock(stock);
        multipleCategoryMapGetRequest.setViewType(viewType);

        if (commonInfoUtility.isLogin(getCommonInfo())) {
            multipleCategoryMapGetRequest.isLogin(true);
            CommonInfoUser commonInfoUser = getCommonInfo().getCommonInfoUser();

            if (Boolean.TRUE.equals(commonInfoUser.isAdminLoginAsMember())) {
                multipleCategoryMapGetRequest.setBusinessType(commonInfoUtility.getAdminUserDetailsFromSpringSecurity()
                                                                               .getMemberInfoEntity()
                                                                               .getBusinessType()
                                                                               .getValue());
                multipleCategoryMapGetRequest.setConfDocumentType(
                                commonInfoUtility.getAdminUserDetailsFromSpringSecurity()
                                                 .getMemberInfoEntity()
                                                 .getConfDocumentType()
                                                 .getValue());
            } else {
                multipleCategoryMapGetRequest.setBusinessType(commonInfoUtility.getFrontUserDetailsFromSpringSecurity()
                                                                               .getMemberInfoEntity()
                                                                               .getBusinessType()
                                                                               .getValue());
                multipleCategoryMapGetRequest.setConfDocumentType(
                                commonInfoUtility.getFrontUserDetailsFromSpringSecurity()
                                                 .getMemberInfoEntity()
                                                 .getConfDocumentType()
                                                 .getValue());
            }
        } else {
            multipleCategoryMapGetRequest.isLogin(false);
            multipleCategoryMapGetRequest.setBusinessType(null);
            multipleCategoryMapGetRequest.setConfDocumentType(null);
        }
        MultipleCategoryMapResponse multipleCategoryMapResponse = null;
        try {
            multipleCategoryMapResponse = categoryApi.getMultipleCategoryMap(multipleCategoryMapGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        assert multipleCategoryMapResponse != null;
        Map<String, List<MultipleCategoryGoodsDetailsDtoResponse>> multipleCategoryMap =
                        multipleCategoryMapResponse.getMultipleCategoryMap();

        if (CollectionUtils.isEmpty(multipleCategoryMap)) {
            return multipleCategoryData;
        }
        // Get categoryDetailsDtoList using list of category id
        List<String> categoryIdList = new ArrayList<>(multipleCategoryMap.keySet());

        MultipleCategoryGetRequest multipleCategoryGetRequest = new MultipleCategoryGetRequest();
        multipleCategoryGetRequest.setCategoryIdList(categoryIdList);

        List<CategoryDetailsDtoResponse> categoryDetailsDtoResponseList = null;
        try {
            categoryDetailsDtoResponseList = goodsApi.getCategoryDetailsList(multipleCategoryGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        multipleCategoryData.multipleCategoryMap = toMultipleCategoryDtoMap(multipleCategoryMap);

        toDataForLoadMultipleCategory(categoryDetailsDtoResponseList, categoryId, seq, multipleCategoryData);

        return multipleCategoryData;
    }

    /**
     * Method to set MultipleCategor data
     *
     * @param categoryDetailsDtoList categoryDetailsDtoList instance
     * @param categoryString         categoryString
     * @param seqString              seqString
     * @param multipleCategoryData   multipleCategoryData instance
     */
    protected void toDataForLoadMultipleCategory(List<CategoryDetailsDtoResponse> categoryDetailsDtoList,
                                                 String categoryString,
                                                 String seqString,
                                                 MultipleCategoryData multipleCategoryData) {
        List<CategoryData> categoryItemsList = new ArrayList<>();

        Map<String, String> seqMap = new LinkedHashMap<>();
        if (StringUtils.isNotEmpty(categoryString) && StringUtils.isNotEmpty(seqString)) {
            String[] categoryArray = categoryString.split(",");
            String[] seqArray = seqString.split(",");

            int index = 0;
            for (String cc : categoryArray) {
                if (StringUtils.isEmpty(cc)) {
                    index++;
                    continue;
                }
                String seqStr = getStringAtIndex(seqArray, index);
                seqMap.put(cc, seqStr);
                index++;
            }
        }

        for (CategoryDetailsDtoResponse categoryDetailsDto : categoryDetailsDtoList) {
            String categoryId = categoryDetailsDto.getCategoryId();

            CategoryData categoryItems = new CategoryData();
            categoryItems.cid = categoryId;
            categoryItems.categoryName = categoryDetailsDto.getCategoryName();
            categoryItems.stype = seqMap.get(categoryId);
            categoryItemsList.add(categoryItems);
        }

        multipleCategoryData.categoryItems = categoryItemsList;
    }

    /**
     * This method will accept String array and index, and returns the value from the array at the given index<br/>
     * It also handles ArrayIndexOutOfBoundsException and return blank string<br/>
     *
     * @param stringArray string array
     * @param index       index to get the value
     * @return value at given index from given array
     */
    protected String getStringAtIndex(String[] stringArray, int index) {
        String value = StringUtils.EMPTY;
        try {
            if (StringUtils.isNotEmpty(stringArray[index])) {
                value = stringArray[index];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // 取得失敗はログだけ吐いて無視
            LOGGER.error("例外処理が発生しました", e);
        }
        return value;
    }

    /**
     * To multiple category dto map map.
     *
     * @param multipleCategoryResponse the multiple category response
     * @return the map
     */
    private Map<String, List<MultipleCategoryGoodsDetailsDto>> toMultipleCategoryDtoMap(Map<String, List<MultipleCategoryGoodsDetailsDtoResponse>> multipleCategoryResponse) {
        Map<String, List<MultipleCategoryGoodsDetailsDto>> multipleCategoryDtoMap = new HashMap<>();

        multipleCategoryResponse.forEach((k, v) -> multipleCategoryDtoMap.put(k, toMultipleCategoryList(v)));

        return multipleCategoryDtoMap;
    }

    /**
     * To multiple category list list.
     *
     * @param multipleCategoryResponse the multiple category response
     * @return the list
     */
    private List<MultipleCategoryGoodsDetailsDto> toMultipleCategoryList(List<MultipleCategoryGoodsDetailsDtoResponse> multipleCategoryResponse) {
        if (multipleCategoryResponse == null) {
            return new ArrayList<>();
        }

        List<MultipleCategoryGoodsDetailsDto> multipleCategoryGoodsDetailsDtoList = new ArrayList<>();

        for (MultipleCategoryGoodsDetailsDtoResponse multipleCategoryGoodsDetailsDtoResponse : multipleCategoryResponse) {
            MultipleCategoryGoodsDetailsDto categoryGoodsDetailsDto =
                            toMultipleCategoryGoodsDetailsDto(multipleCategoryGoodsDetailsDtoResponse);
            multipleCategoryGoodsDetailsDtoList.add(categoryGoodsDetailsDto);
        }

        return multipleCategoryGoodsDetailsDtoList;
    }

    /**
     * To multiple category goods details dto multiple category goods details dto.
     *
     * @param multipleCategoryResponse the multiple category response
     * @return the multiple category goods details dto
     */
    private MultipleCategoryGoodsDetailsDto toMultipleCategoryGoodsDetailsDto(MultipleCategoryGoodsDetailsDtoResponse multipleCategoryResponse) {
        if (multipleCategoryResponse == null) {
            return null;
        }

        MultipleCategoryGoodsDetailsDto categoryGoodsDetailsDto = new MultipleCategoryGoodsDetailsDto();
        categoryGoodsDetailsDto.setGoodsDisplayPreDiscountPrice(
                        multipleCategoryResponse.getGoodsDisplayPreDiscountPrice());
        categoryGoodsDetailsDto.setGoodsPreDiscount(
                        Boolean.TRUE.equals(multipleCategoryResponse.getIsGoodsPreDiscount()));
        categoryGoodsDetailsDto.setGoodsDisplayPriceRange(
                        Boolean.TRUE.equals(multipleCategoryResponse.getIsGoodsDisplayPriceRange()));
        categoryGoodsDetailsDto.setGoodsDisplayPreDiscountPriceRange(
                        Boolean.TRUE.equals(multipleCategoryResponse.getIsGoodsDisplayPreDiscountPriceRange()));
        categoryGoodsDetailsDto.setGoodsGroupSeq(multipleCategoryResponse.getGoodsGroupSeq());
        categoryGoodsDetailsDto.setGgcd(multipleCategoryResponse.getGgcd());
        categoryGoodsDetailsDto.setHref(multipleCategoryResponse.getHref());
        categoryGoodsDetailsDto.setGoodsGroupName(multipleCategoryResponse.getGoodsGroupName());
        categoryGoodsDetailsDto.setGoodsGroupImageThumbnail(multipleCategoryResponse.getGoodsGroupImageThumbnail());
        categoryGoodsDetailsDto.setGoodsDisplayPrice(multipleCategoryResponse.getGoodsDisplayPrice());
        categoryGoodsDetailsDto.setGoodsPreDiscountPrice(multipleCategoryResponse.getGoodsPreDiscountPrice());
        categoryGoodsDetailsDto.setGoodsImageItem(multipleCategoryResponse.getGoodsImageItem());
        categoryGoodsDetailsDto.setGoodsPrice(multipleCategoryResponse.getGoodsPrice());
        categoryGoodsDetailsDto.setGoodsPriceInTax(multipleCategoryResponse.getGoodsPriceInTax());
        categoryGoodsDetailsDto.setPreDiscountPrice(multipleCategoryResponse.getPreDiscountPrice());
        categoryGoodsDetailsDto.setPreDisCountPriceInTax(multipleCategoryResponse.getPreDisCountPriceInTax());
        categoryGoodsDetailsDto.setGoodsNote1(multipleCategoryResponse.getGoodsNote1());
        categoryGoodsDetailsDto.setTaxRate(multipleCategoryResponse.getTaxRate());
        categoryGoodsDetailsDto.setGoodsTaxType(EnumTypeUtil.getEnumFromValue(HTypeGoodsTaxType.class,
                                                                              multipleCategoryResponse.getGoodsTaxType()
                                                                             ));
        categoryGoodsDetailsDto.setWhatsnewDate(
                        conversionUtility.toTimeStamp(multipleCategoryResponse.getWhatsnewDate()));
        categoryGoodsDetailsDto.setStockStatusPc(multipleCategoryResponse.getStockStatus());
        if (multipleCategoryResponse.getGoodsIconItems() != null) {
            categoryGoodsDetailsDto.setGoodsIconItems(
                            toMultipleCategoryList(multipleCategoryResponse.getGoodsIconItems()));
        }
        categoryGoodsDetailsDto.setIconName(multipleCategoryResponse.getIconName());
        categoryGoodsDetailsDto.setIconColorCode(multipleCategoryResponse.getIconColorCode());
        categoryGoodsDetailsDto.setNewDate(Boolean.TRUE.equals(multipleCategoryResponse.getIsNewDate()));
        categoryGoodsDetailsDto.setStockStatusDisplay(
                        Boolean.TRUE.equals(multipleCategoryResponse.getIsStockStatusDisplay()));
        categoryGoodsDetailsDto.setStockNoSaleDisp(
                        Boolean.TRUE.equals(multipleCategoryResponse.getIsStockNoSaleDisp()));
        categoryGoodsDetailsDto.setStockSoldOutIconDisp(
                        Boolean.TRUE.equals(multipleCategoryResponse.getIsStockSoldOutIconDisp()));
        categoryGoodsDetailsDto.setStockBeforeSaleIconDisp(
                        Boolean.TRUE.equals(multipleCategoryResponse.getIsStockBeforeSaleIconDisp()));
        categoryGoodsDetailsDto.setStockNoStockIconDisp(
                        Boolean.TRUE.equals(multipleCategoryResponse.getIsStockNoStockIconDisp()));
        categoryGoodsDetailsDto.setStockFewIconDisp(
                        Boolean.TRUE.equals(multipleCategoryResponse.getIsStockFewIconDisp()));
        categoryGoodsDetailsDto.setStockPossibleSalesIconDisp(
                        Boolean.TRUE.equals(multipleCategoryResponse.getIsStockPossibleSalesIconDisp()));
        categoryGoodsDetailsDto.setGoodsGroupImage(
                        Boolean.TRUE.equals(multipleCategoryResponse.getIsGoodsGroupImage()));
        categoryGoodsDetailsDto.setGroupSalePriceIntegrityFlag(
                        Boolean.TRUE.equals(multipleCategoryResponse.getGroupSalePriceIntegrityFlag()));
        // 2023-renew No2 from here
        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
        categoryGoodsDetailsDto.setPriceHideGoods(!commonInfoUtility.isLogin(getCommonInfo()));
        // 2023-renew No2 to here
        categoryGoodsDetailsDto.setSaleIconFlag(Boolean.TRUE.equals(multipleCategoryResponse.getSaleIconFlag()));
        categoryGoodsDetailsDto.setReserveIconFlag(Boolean.TRUE.equals(multipleCategoryResponse.getReserveIconFlag()));
        categoryGoodsDetailsDto.setNewIconFlag(Boolean.TRUE.equals(multipleCategoryResponse.getNewIconFlag()));
        categoryGoodsDetailsDto.setSale(Boolean.TRUE.equals(multipleCategoryResponse.getSale()));

        // 2023-renew AddNo5 from here
        categoryGoodsDetailsDto.setGoodsPriceHigh(multipleCategoryResponse.getGoodsGroupMaxPricePc());
        categoryGoodsDetailsDto.setGoodsPriceLow(multipleCategoryResponse.getGoodsGroupMinPricePc());
        categoryGoodsDetailsDto.setGoodsSalePriceHigh(multipleCategoryResponse.getGoodsGroupMaxPriceMb());
        categoryGoodsDetailsDto.setGoodsSalePriceLow(multipleCategoryResponse.getGoodsGroupMinPriceMb());

        NumberFormat formatter = NumberFormat.getNumberInstance();
        categoryGoodsDetailsDto.setDispGoodsPriceHigh(multipleCategoryResponse.getGoodsGroupMaxPricePc() != null
                                                             ? formatter.format(multipleCategoryResponse.getGoodsGroupMaxPricePc()) + "円"
                                                             : null);
        categoryGoodsDetailsDto.setDispGoodsPriceLow(multipleCategoryResponse.getGoodsGroupMinPricePc() != null
                                                            ? formatter.format(multipleCategoryResponse.getGoodsGroupMinPricePc()) + "円"
                                                            : null);
        categoryGoodsDetailsDto.setDispGoodsSalePriceHigh(multipleCategoryResponse.getGoodsGroupMaxPriceMb() != null
                                                                 ? formatter.format(multipleCategoryResponse.getGoodsGroupMaxPriceMb()) + "円"
                                                                 : null);
        categoryGoodsDetailsDto.setDispGoodsSalePriceLow(multipleCategoryResponse.getGoodsGroupMinPriceMb() != null
                                                                ? formatter.format(multipleCategoryResponse.getGoodsGroupMinPriceMb()) + "円"
                                                                : null);
        categoryGoodsDetailsDto.setSamePrice(isSamePrice(categoryGoodsDetailsDto));
        categoryGoodsDetailsDto.setSameSalePrice(isSameSalePrice(categoryGoodsDetailsDto));
        categoryGoodsDetailsDto.setSaleItem(isSaleItem(categoryGoodsDetailsDto));
        // 2023-renew AddNo5 to here
        return categoryGoodsDetailsDto;
    }

    /**
     * 最低価格が最高価格と等しいことをチェックする
     *
     * @param categoryGoodsDetailsDto
     * @return
     */
    private boolean isSamePrice(MultipleCategoryGoodsDetailsDto categoryGoodsDetailsDto) {
        if (categoryGoodsDetailsDto.getGoodsPriceLow() == null && categoryGoodsDetailsDto.getGoodsPriceHigh() == null) {
            return true;
        } else if (categoryGoodsDetailsDto.getGoodsPriceLow() == null || categoryGoodsDetailsDto.getGoodsPriceHigh() == null) {
            return false;
        } else {
            return categoryGoodsDetailsDto.getGoodsPriceLow().compareTo(categoryGoodsDetailsDto.getGoodsPriceHigh()) == 0;
        }
    }

    /**
     * 最低セール価格が最高セール価格と等しいことをチェックする
     *
     * @param categoryGoodsDetailsDto
     * @return
     */
    private boolean isSameSalePrice(MultipleCategoryGoodsDetailsDto categoryGoodsDetailsDto) {
        if (categoryGoodsDetailsDto.getGoodsSalePriceLow() == null && categoryGoodsDetailsDto.getGoodsSalePriceHigh() == null) {
            return true;
        } else if (categoryGoodsDetailsDto.getGoodsSalePriceLow() == null || categoryGoodsDetailsDto.getGoodsSalePriceHigh() == null) {
            return false;
        } else {
            return categoryGoodsDetailsDto.getGoodsSalePriceLow().compareTo(categoryGoodsDetailsDto.getGoodsSalePriceHigh()) == 0;
        }
    }

    private boolean isSaleItem(MultipleCategoryGoodsDetailsDto categoryGoodsDetailsDto) {
        // 2023-renew AddNo5 from here
        if ((categoryGoodsDetailsDto.getGoodsSalePriceLow() != null && categoryGoodsDetailsDto.getGoodsSalePriceLow().compareTo(BigDecimal.ZERO) != 0)
            || (categoryGoodsDetailsDto.getGoodsSalePriceHigh() != null && categoryGoodsDetailsDto.getGoodsSalePriceHigh().compareTo(BigDecimal.ZERO) != 0)) {
            return true;
        }
        // 2023-renew AddNo5 to here
        return false;
    }

    /**
     * CategoryData
     */
    class CategoryData implements Serializable {
        /**
         * serialVersionUID<br/>
         */
        private static final long serialVersionUID = 1L;

        /**
         * categoryId
         */
        public String cid;

        /**
         * category name
         */
        public String categoryName;

        /**
         * SORT_TYPE
         */
        public String stype;

    }

    /**
     * Multiple category data
     */
    class MultipleCategoryData implements Serializable {
        /**
         * serialVersionUID<br/>
         */
        private static final long serialVersionUID = 1L;

        /**
         * CategoryData
         */
        public List<CategoryData> categoryItems;

        /**
         * Multiple category items
         */
        public Map<String, List<MultipleCategoryGoodsDetailsDto>> multipleCategoryMap;

    }

}
