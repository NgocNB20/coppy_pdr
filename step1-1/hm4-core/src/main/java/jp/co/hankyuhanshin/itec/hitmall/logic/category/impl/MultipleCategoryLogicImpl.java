/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalableGoodsType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipleCategory.ajax.MultipleCategoryGoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.category.MultipleCategoryLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsDisplayPriceLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.OpenGoodsGroupSearchService;
import jp.co.hankyuhanshin.itec.hitmall.thymeleaf.ImageConverterViewUtil;
import jp.co.hankyuhanshin.itec.hitmall.thymeleaf.PreConverterViewUtil;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.web.PageInfo;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.NumberUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * To fetch special category list
 *
 * @author Shalaka kale
 */

@Component
public class MultipleCategoryLogicImpl extends AbstractShopLogic implements MultipleCategoryLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MultipleCategoryLogicImpl.class);

    /**
     * 公開展示商品情報リスト取得サービスクラス
     */
    private final OpenGoodsGroupSearchService openGoodsGroupSearchService;

    /**
     * 表示形式：サムネイル表示 キー
     */
    public static final String VIEW_TYPE_THUMBNAIL_KEY = "thumbs";

    /**
     * 表示形式：リスト表示 キー
     */
    public static final String VIEW_TYPE_LIST_KEY = "list";

    /**
     * 数値関連ヘルパー
     */
    private final NumberUtility numberUtility;

    /**
     * 商品系Helper取得
     */
    private final GoodsUtility goodsUtility;

    /**
     * 税込計算Utility
     */
    private final CalculatePriceUtility calculatePriceUtility;

    /**
     * 商品表示単価を生成するイ
     */
    private final GoodsDisplayPriceLogic goodsDisplayPriceLogic;

    @Autowired
    public MultipleCategoryLogicImpl(OpenGoodsGroupSearchService openGoodsGroupSearchService,
                                     NumberUtility numberUtility,
                                     GoodsUtility goodsUtility,
                                     CalculatePriceUtility calculatePriceUtility,
                                     GoodsDisplayPriceLogic goodsDisplayPriceLogic) {
        this.openGoodsGroupSearchService = openGoodsGroupSearchService;
        this.numberUtility = numberUtility;
        this.goodsUtility = goodsUtility;
        this.calculatePriceUtility = calculatePriceUtility;
        this.goodsDisplayPriceLogic = goodsDisplayPriceLogic;
    }

    /**
     * To fetch special category list
     *
     * @param categoryString comma separated categoryId
     * @param seqString      comma separated category sequences
     * @param limitString    comma separated category limit
     * @param priceFrom      priceFrom
     * @param priceTo        priceTo
     * @param stock          stock
     * @param viewType       viewType
     * @return Map as categoryId as key and list of GoodsGroupDto as values
     */
    @Override
    public Map<String, List<MultipleCategoryGoodsDetailsDto>> getCategoryMap(String categoryString,
                                                                             String seqString,
                                                                             String limitString,
                                                                             String priceFrom,
                                                                             String priceTo,
                                                                             String stock,
                                                                             String viewType,
                                                                             Boolean isLogin,
                                                                             String businessType,
                                                                             String confDocumentType) {
        Map<String, List<MultipleCategoryGoodsDetailsDto>> resultMap = new LinkedHashMap<>();

        if (StringUtil.isEmpty(categoryString)) {
            return resultMap;
        }

        String[] categoryArray = categoryString.split(",");

        int index = 0;
        for (String cc : categoryArray) {
            if (StringUtil.isEmpty(cc)) {
                index++;
                continue;
            }
            String seqStr = getString(seqString, index);
            String limitStr = getString(limitString, index);
            String priceFromStr = getString(priceFrom, index);
            String priceToStr = getString(priceTo, index);
            String stockStr = getString(stock, index);
            String viewTypeStr = getString(viewType, index);

            // 引数の作成
            String order = null;
            boolean asc = false;
            if (MultipleCategoryLogic.SORT_TYPE_GOODSPRICE_KEY.equals(seqStr)) {
                // 価格順
                order = "goodsGroupMinPrice";
                asc = true;
            } else if (MultipleCategoryLogic.SORT_TYPE_SALECOUNT_KEY.equals(seqStr)) {
                // 売上個数順
                order = "popularityCount";
                asc = false;
            } else if (MultipleCategoryLogic.SORT_TYPE_REGISTDATE_KEY.equals(seqStr)) {
                // PDR Migrate Customization from here
                // 新着順
                order = "newlyGoodsItems";
                // PDR Migrate Customization to here

                asc = false;
            } else {
                // 標準
                order = "normal";
                asc = false;
            }

            int limit = 0;
            try {
                limit = Integer.parseInt(limitStr);
            } catch (NumberFormatException e) {
                // 変換失敗はログだけ吐いて無視
                LOGGER.error("例外処理が発生しました", e);
            }
            List<GoodsGroupDto> specialDtoList =
                            getCategoryGoodsList(cc, limit, order, asc, priceFromStr, priceToStr, stockStr);

            if (specialDtoList.isEmpty()) {
                continue;
            }
            // Set page items
            List<MultipleCategoryGoodsDetailsDto> pageItemList =
                            this.makeIndexPageItemList(specialDtoList, viewTypeStr, isLogin, businessType,
                                                       confDocumentType
                                                      );
            resultMap.put(cc, pageItemList);
            index++;
        }

        return resultMap;
    }

    /**
     * This method will accept String array and index, and returns the value from the array at the given index<br/>
     * It also handles ArrayIndexOutOfBoundsException and return blank string<br/>
     *
     * @param valueToConvert string array
     * @param index          index to get the value
     * @return value at given index from given array
     */
    protected String getString(String valueToConvert, int index) {
        String value = StringUtils.EMPTY;
        if (StringUtils.isEmpty(valueToConvert)) {
            return valueToConvert;
        }
        String[] stringArray = valueToConvert.split(",");
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
     * 指定したカテゴリに属する商品グループ情報を取得する。<br/>
     *
     * @param categoryId   カテゴリID
     * @param limit        limit
     * @param order        order
     * @param asc          asc
     * @param priceFromStr priceFrom
     * @param priceToStr   priceTo
     * @param stockStr     stock
     * @return 商品グループ情報
     */
    public List<GoodsGroupDto> getCategoryGoodsList(String categoryId,
                                                    int limit,
                                                    String order,
                                                    boolean asc,
                                                    String priceFromStr,
                                                    String priceToStr,
                                                    String stockStr) {

        GoodsGroupSearchForDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(GoodsGroupSearchForDaoConditionDto.class);

        conditionDto.setCategoryId(categoryId);
        if (StringUtil.isNotEmpty(priceFromStr) && numberUtility.isNumber(priceFromStr)) {
            conditionDto.setMinPrice(new BigDecimal(priceFromStr));
        }
        if (StringUtil.isNotEmpty(priceToStr) && numberUtility.isNumber(priceToStr)) {
            conditionDto.setMaxPrice(new BigDecimal(priceToStr));
        }

        conditionDto.setOpenStatus(HTypeOpenDeleteStatus.OPEN);

        PageInfo pageInfo = ApplicationContextUtility.getBean(PageInfo.class);
        pageInfo.setLimit(limit);
        pageInfo.setOrder(order, asc);
        pageInfo.setupSelectOptions();
        conditionDto.setPageInfo(pageInfo);

        if (StringUtil.isNotEmpty(stockStr) && Boolean.valueOf(stockStr)) {
            // 在庫ありの指定がある場合は、在庫状況が「在庫あり」「残りわずか」「予約受付中」のものが対象
            conditionDto.setStcockExistStatus(new String[] {HTypeStockStatusType.STOCK_POSSIBLE_SALES.getValue(),
                            HTypeStockStatusType.STOCK_FEW.getValue()});
        }
        return openGoodsGroupSearchService.execute(conditionDto);
    }

    /**
     * DTO一覧をitemクラスの一覧に変換する<br/>
     *
     * @param goodsGroupDtoList 商品グループ一覧情報DTO
     * @param viewType          viewType
     * @return カテゴリページアイテム情報一覧
     */
    protected List<MultipleCategoryGoodsDetailsDto> makeIndexPageItemList(List<GoodsGroupDto> goodsGroupDtoList,
                                                                          String viewType,
                                                                          Boolean isLogin,
                                                                          String businessType,
                                                                          String confDocumentType) {

        List<MultipleCategoryGoodsDetailsDto> itemsList = new ArrayList<>();
        PreConverterViewUtil pre = new PreConverterViewUtil();

        for (GoodsGroupDto goodsGroupDto : goodsGroupDtoList) {

            GoodsGroupEntity goodsGroupEntity = goodsGroupDto.getGoodsGroupEntity();
            MultipleCategoryGoodsDetailsDto multipleCategoryGoodsDetails =
                            ApplicationContextUtility.getBean(MultipleCategoryGoodsDetailsDto.class);
            multipleCategoryGoodsDetails.setGoodsGroupSeq(goodsGroupEntity.getGoodsGroupSeq());
            multipleCategoryGoodsDetails.setGgcd(goodsGroupEntity.getGoodsGroupCode());
            multipleCategoryGoodsDetails.setGoodsGroupName(goodsGroupEntity.getGoodsGroupName());
            multipleCategoryGoodsDetails.setGoodsTaxType(goodsGroupEntity.getGoodsTaxType());
            multipleCategoryGoodsDetails.setTaxRate(goodsGroupEntity.getTaxRate());

            BigDecimal taxRate = multipleCategoryGoodsDetails.getTaxRate();

            // 通常価格 - 税込計算
            BigDecimal goodsGroupMinPrice = goodsGroupEntity.getGoodsGroupMinPricePc();
            multipleCategoryGoodsDetails.setGoodsPrice(goodsGroupMinPrice);
            multipleCategoryGoodsDetails.setGoodsPriceInTax(
                            calculatePriceUtility.getTaxIncludedPrice(multipleCategoryGoodsDetails.getGoodsPrice(),
                                                                      taxRate
                                                                     ));

            BigDecimal goodsGroupMaxPrice = goodsGroupEntity.getGoodsGroupMaxPricePc();

            multipleCategoryGoodsDetails.setGoodsDisplayPriceRange(
                            goodsUtility.isGoodsDisplayPriceRange(goodsGroupMinPrice, goodsGroupMaxPrice));

            multipleCategoryGoodsDetails.setPreDiscountPrice(goodsGroupEntity.getPreDiscountMinPrice());
            multipleCategoryGoodsDetails.setPreDisCountPriceInTax(
                            calculatePriceUtility.getTaxIncludedPrice(goodsGroupEntity.getPreDiscountMinPrice(),
                                                                      taxRate
                                                                     ));
            multipleCategoryGoodsDetails.setGoodsPreDiscount(
                            multipleCategoryGoodsDetails.getPreDiscountPrice() != null);
            multipleCategoryGoodsDetails.setGoodsPreDiscountPrice(goodsGroupEntity.getGoodsPreDiscountPrice());

            multipleCategoryGoodsDetails.setGoodsDisplayPreDiscountPriceRange(
                            goodsUtility.isGoodsDisplayPriceRange(goodsGroupEntity.getPreDiscountMinPrice(),
                                                                  goodsGroupEntity.getPreDiscountMaxPrice()
                                                                 ));

            // 商品消費税種別
            multipleCategoryGoodsDetails.setGoodsTaxType(goodsGroupEntity.getGoodsTaxType());

            // 新着画像の表示期間を取得
            Timestamp whatsnewDate = goodsUtility.getRealWhatsNewDate(goodsGroupEntity.getWhatsnewDate());
            multipleCategoryGoodsDetails.setWhatsnewDate(whatsnewDate);

            String goodsImage = null;
            if (goodsGroupDto.getGoodsGroupImageEntityList() != null) {
                // 画像ファイルを取得
                goodsImage = goodsUtility.getImageFileName(goodsGroupDto);
                multipleCategoryGoodsDetails.setGoodsImageItem(getGoodsImageItem(goodsGroupDto));
            }
            multipleCategoryGoodsDetails.setGoodsGroupImageThumbnail(goodsUtility.getGoodsImagePath(goodsImage));

            // アイコン情報の取得
            List<MultipleCategoryGoodsDetailsDto> goodsIconList = new ArrayList<>();
            if (goodsGroupDto.getGoodsInformationIconDetailsDtoList() != null) {
                for (GoodsInformationIconDetailsDto goodsInformationIconDetailsDto : goodsGroupDto.getGoodsInformationIconDetailsDtoList()) {
                    MultipleCategoryGoodsDetailsDto iconPageItem = new MultipleCategoryGoodsDetailsDto();
                    iconPageItem.setIconName(goodsInformationIconDetailsDto.getIconName());
                    iconPageItem.setIconColorCode(goodsInformationIconDetailsDto.getColorCode());

                    goodsIconList.add(iconPageItem);
                }
            }
            multipleCategoryGoodsDetails.setGoodsIconItems(goodsIconList);

            multipleCategoryGoodsDetails.setNewDate(isNewDate(whatsnewDate));
            multipleCategoryGoodsDetails.setGoodsGroupImage(
                            isGoodsGroupImage(multipleCategoryGoodsDetails.getGoodsGroupImageThumbnail()));

            String stockStatusPc = EnumTypeUtil.getValue(goodsGroupDto.getBatchUpdateStockStatus().getStockStatusPc());
            multipleCategoryGoodsDetails.setStockStatusPc(stockStatusPc);
            multipleCategoryGoodsDetails.setStockStatusDisplay(false);

            // 商品グループ在庫の表示判定
            if (goodsUtility.isGoodsGroupStock(goodsGroupDto.getBatchUpdateStockStatus().getStockStatusPc())) {
                multipleCategoryGoodsDetails.setStockStatusDisplay(true);
            }
            multipleCategoryGoodsDetails.setStockPossibleSalesIconDisp(isStockPossibleSalesIconDisp(stockStatusPc));
            multipleCategoryGoodsDetails.setStockFewIconDisp(isStockFewIconDisp(stockStatusPc));
            multipleCategoryGoodsDetails.setStockSoldOutIconDisp(isStockSoldOutIconDisp(stockStatusPc));
            multipleCategoryGoodsDetails.setStockBeforeSaleIconDisp(isStockBeforeSaleIconDisp(stockStatusPc));
            multipleCategoryGoodsDetails.setStockNoStockIconDisp(isStockNoStockIconDisp(stockStatusPc));
            multipleCategoryGoodsDetails.setStockNoSaleDisp(isStockNoSaleDisp(stockStatusPc));

            GoodsGroupDisplayEntity goodsGroupDisplayEntity = goodsGroupDto.getGoodsGroupDisplayEntity();
            multipleCategoryGoodsDetails.setGoodsNote1(pre.convert(goodsGroupDisplayEntity.getGoodsNote1(), false));

            // PDR Migrate Customization from here
            // 販売可能商品区分
            // 2023-renew No2 from here

            // 2023-renew No2 to here

            // 商品表示単価、商品表示値引き前単価を設定
            Map<GoodsDisplayPriceLogic.Key, Object> prices = goodsDisplayPriceLogic.create(goodsGroupDto, true);

            multipleCategoryGoodsDetails.setGoodsDisplayPrice(
                            (String) prices.get(GoodsDisplayPriceLogic.Key.DISPLAY_PRICE));
            multipleCategoryGoodsDetails.setGoodsDisplayPreDiscountPrice(
                            (String) prices.get(GoodsDisplayPriceLogic.Key.DISPLAY_PREDISCOUNT_PRICE));

            multipleCategoryGoodsDetails.setSale(this.isSale(goodsGroupEntity.getPreDiscountMinPrice()));
            // PDR Migrate Customization to here

            // PDR Migrate Customization from here
            // シリーズセール価格整合性フラグ
            multipleCategoryGoodsDetails.setGroupSalePriceIntegrityFlag(HTypeSalePriceIntegrityFlag.MATCH.equals(
                            goodsGroupEntity.getGroupSalePriceIntegrityFlag()) ? true : false);
            // SALEアイコンフラグ
            multipleCategoryGoodsDetails.setSaleIconFlag(
                            HTypeSaleIconFlag.ON.equals(goodsGroupDisplayEntity.getSaleIconFlag()) ? true : false);
            // お取りおきアイコンフラグ
            multipleCategoryGoodsDetails.setReserveIconFlag(
                            HTypeReserveIconFlag.ON.equals(goodsGroupDisplayEntity.getReserveIconFlag()) ?
                                            true :
                                            false);
            // NEWアイコンフラグ
            multipleCategoryGoodsDetails.setNewIconFlag(
                            HTypeNewIconFlag.ON.equals(goodsGroupDisplayEntity.getNewIconFlag()) ? true : false);

            // 2023-renew AddNo5 from here
            multipleCategoryGoodsDetails.setGoodsGroupMaxPricePc(
                            goodsGroupEntity.getGoodsGroupMaxPricePc());
            multipleCategoryGoodsDetails.setGoodsGroupMinPricePc(
                            goodsGroupEntity.getGoodsGroupMinPricePc());
            multipleCategoryGoodsDetails.setGoodsGroupMaxPriceMb(
                            goodsGroupEntity.getGoodsGroupMaxPriceMb());
            multipleCategoryGoodsDetails.setGoodsGroupMinPriceMb(
                            goodsGroupEntity.getGoodsGroupMinPriceMb());
            // 2023-renew AddNo5 to here
            // PDR Migrate Customization to here

            itemsList.add(multipleCategoryGoodsDetails);
        }
        return itemsList;
    }

    /**
     * TOP表示用画像を取得
     * ①：Entityから画像リストを取得
     * ②：thymeleafユーティリティオブジェクトで、TOP表示用画像ファイル取得<br/>
     *
     * @param goodsGroupDto 商品グループDTO
     * @param customParams  案件用引数
     * @return TOP表示用画像ファイル
     */
    protected String getGoodsImageItem(GoodsGroupDto goodsGroupDto, Object... customParams) {

        List<String> imageList = new ArrayList<String>();
        for (GoodsGroupImageEntity goodsGroupImageEntity : goodsGroupDto.getGoodsGroupImageEntityList()) {
            imageList.add(goodsGroupImageEntity.getImageFileName());
        }

        // 画面表示用のthymeleafユーティリティオブジェクト
        ImageConverterViewUtil util = new ImageConverterViewUtil();
        // 画像リスト、画像番号、画像種別を渡す
        // thymeleafユーティリティオブジェクトからは、指定された画像を返却
        return util.convert(imageList, 0, null);
    }

    /**
     * 新着日付が現在の時刻を過ぎていないか判断
     *
     * @param whatsnewDate whatsnewDate
     * @return true:新着日付、false:新着日付を過ぎている
     */
    public boolean isNewDate(Timestamp whatsnewDate) {
        if (whatsnewDate == null) {
            return false;
        }
        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        return whatsnewDate.compareTo(dateUtility.getCurrentDate()) >= 0;
    }

    /**
     * サムネイル画像有無チェック<br/>
     *
     * @param goodsGroupImageThumbnail goodsGroupImageThumbnail
     * @return 画像あり
     */
    public boolean isGoodsGroupImage(String goodsGroupImageThumbnail) {
        return StringUtil.isNotEmpty(goodsGroupImageThumbnail);
    }

    /**
     * 在庫状態:在庫あり
     * （アイコン表示用）
     *
     * @param stockStatusPc stockStatusPc
     * @return true：在庫あり
     */
    public boolean isStockPossibleSalesIconDisp(String stockStatusPc) {
        return HTypeStockStatusType.STOCK_POSSIBLE_SALES.getValue().equals(stockStatusPc);
    }

    /**
     * 在庫状態:残りわずか
     * （アイコン表示用）
     *
     * @param stockStatusPc stockStatusPc
     * @return true：残りわずか
     */
    public boolean isStockFewIconDisp(String stockStatusPc) {
        return HTypeStockStatusType.STOCK_FEW.getValue().equals(stockStatusPc);
    }

    /**
     * 在庫状態:販売期間終了
     * （アイコン表示用）
     *
     * @param stockStatusPc stockStatusPc
     * @return true：販売期間終了
     */
    public boolean isStockSoldOutIconDisp(String stockStatusPc) {
        return HTypeStockStatusType.SOLDOUT.getValue().equals(stockStatusPc);
    }

    /**
     * 在庫状態:販売前
     * （アイコン表示用）
     *
     * @param stockStatusPc stockStatusPc
     * @return true：販売前
     */
    public boolean isStockBeforeSaleIconDisp(String stockStatusPc) {
        return HTypeStockStatusType.BEFORE_SALE.getValue().equals(stockStatusPc);
    }

    /**
     * 在庫状態:在庫なし
     * （アイコン表示用）
     *
     * @param stockStatusPc stockStatusPc
     * @return true：在庫なし
     */
    public boolean isStockNoStockIconDisp(String stockStatusPc) {
        return HTypeStockStatusType.STOCK_NOSTOCK.getValue().equals(stockStatusPc);
    }

    /**
     * 在庫状態:非販売
     * （アイコン表示用）
     *
     * @param stockStatusPc stockStatusPc
     * @return true：非販売
     */
    public boolean isStockNoSaleDisp(String stockStatusPc) {
        return HTypeStockStatusType.NO_SALE.getValue().equals(stockStatusPc);
    }
    // PDR Migrate Customization from here

    /**
     * セール価格（グループ）<br/>
     *
     * @return true:セール価格あり / false:セール価格なし
     */
    public boolean isSale(BigDecimal goodsPreDiscountPrice) {
        if (goodsPreDiscountPrice != null) {
            return true;
        }
        return false;
    }
    // PDR Migrate Customization to here
}
