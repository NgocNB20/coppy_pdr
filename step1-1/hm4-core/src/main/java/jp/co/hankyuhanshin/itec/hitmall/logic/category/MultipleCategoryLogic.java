/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.category;

import jp.co.hankyuhanshin.itec.hitmall.dto.multipleCategory.ajax.MultipleCategoryGoodsDetailsDto;

import java.util.List;
import java.util.Map;

/**
 * To fetch special category list
 *
 * @author Shalaka kale
 */
public interface MultipleCategoryLogic {

    /**
     * 並び順：標準 キー
     */
    public static final String SORT_TYPE_NORMAL_KEY = "normal";
    /**
     * 並び順：新着順 キー
     */
    public static final String SORT_TYPE_REGISTDATE_KEY = "new";
    /**
     * 並び順：価格順 キー
     */
    public static final String SORT_TYPE_GOODSPRICE_KEY = "price";
    /**
     * 並び順：売れ筋順 キー
     */
    public static final String SORT_TYPE_SALECOUNT_KEY = "salableness";

    /**
     * To fetch special category list
     *
     * @param category  comma separated categoryId
     * @param seq       comma separated category sequences
     * @param limit     comma separated category limit
     * @param priceFrom priceFrom
     * @param priceTo   priceTo
     * @param stock     stock
     * @param viewType  viewType
     * @return Map as categoryId as key and list of GoodsGroupDto as values
     */
    Map<String, List<MultipleCategoryGoodsDetailsDto>> getCategoryMap(String category,
                                                                      String seq,
                                                                      String limit,
                                                                      String priceFrom,
                                                                      String priceTo,
                                                                      String stock,
                                                                      String viewType,
                                                                      Boolean isLogin,
                                                                      String businessType,
                                                                      String confDocumentType);
}
