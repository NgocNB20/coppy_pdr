package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchGoodsRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchGoodsResponseInfoDocsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoUser;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCategoryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeManualDisplayFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOutletIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common.CategoryItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common.GoodsGroupItem;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CategoryUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.PageInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.BooleanUtils.toBoolean;

/**
 * 商品一覧画面 Helper
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Component
public class GoodsListHelper {

    /**
     * カテゴリーヘルパークラス
     */
    private final CategoryUtility categoryUtility;

    /**
     * 金額計算のUtilityクラス
     */
    private final CalculatePriceUtility calculatePriceUtility;

    /**
     * 商品系Helper
     */
    private final GoodsUtility goodsUtility;

    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    /**
     * 共通情報ヘルパークラス
     */
    private final CommonInfoUtility commonInfoUtility;

    /**
     * コンストラクタ
     *
     * @param categoryUtility       カテゴリーヘルパークラス
     * @param calculatePriceUtility 金額計算のUtilityクラス
     * @param goodsUtility          商品系ヘルパークラス
     * @param conversionUtility     変換ユーティリティクラス
     * @param commonInfoUtility     共通情報
     */
    @Autowired
    public GoodsListHelper(CategoryUtility categoryUtility,
                           CalculatePriceUtility calculatePriceUtility,
                           GoodsUtility goodsUtility,
                           ConversionUtility conversionUtility,
                           CommonInfoUtility commonInfoUtility) {
        this.categoryUtility = categoryUtility;
        this.calculatePriceUtility = calculatePriceUtility;
        this.goodsUtility = goodsUtility;
        this.conversionUtility = conversionUtility;
        this.commonInfoUtility = commonInfoUtility;
    }

    /**
     * 画面表示・再表示
     * カテゴリ情報をページクラスにセット<br />
     *
     * @param categoryDetailsDto カテゴリ詳細DTO
     * @param goodsListModel     商品一覧画面 Model
     */
    public void toPageForLoad(CategoryDetailsDto categoryDetailsDto, GoodsListModel goodsListModel) {

        // カテゴリが取得できない場合は、終了
        goodsListModel.setCid(null);
        if (categoryDetailsDto == null) {
            return;
        }

        // 表示カテゴリ情報をページクラスにセット
        goodsListModel.setCid(categoryDetailsDto.getCategoryId());
        goodsListModel.setCategoryImagePC(null);

        if (categoryDetailsDto.getCategoryImagePC() != null) {
            goodsListModel.setCategoryImagePC(
                            categoryUtility.getCategoryImagePath(categoryDetailsDto.getCategoryImagePC()));
        }
        goodsListModel.setMetaDescription(categoryDetailsDto.getMetaDescription());
        goodsListModel.setMetaKeyword(categoryDetailsDto.getMetaKeyword());

        goodsListModel.setManualDisplayFlag(categoryDetailsDto.getManualDisplayFlag());

        goodsListModel.setFreeTextPC(categoryDetailsDto.getFreeTextPC());

        // ブラウザタイトル用に利用
        goodsListModel.setCategoryName(categoryDetailsDto.getCategoryNamePC());
    }

    /**
     * 画面表示・再表示
     * パンくず情報をページクラスにセット<br />
     *
     * @param openCategoryPassList 公開カテゴリパスリスト
     * @param goodsListModel       商品一覧画面 Model
     */
    public void toPageForLoadForTopicPath(List<CategoryDetailsDto> openCategoryPassList,
                                          GoodsListModel goodsListModel) {

        List<CategoryItem> itemsList = new ArrayList<>();
        for (int i = 0; i < openCategoryPassList.size(); i++) {
            CategoryDetailsDto categoryDetailsDto = openCategoryPassList.get(i);

            if (categoryDetailsDto.getCategoryLevel().intValue() > 0) {// トップは省く
                CategoryItem categoryItem = ApplicationContextUtility.getBean(CategoryItem.class);
                categoryItem.setCid(categoryDetailsDto.getCategoryId());
                categoryItem.setCategoryName(categoryDetailsDto.getCategoryNamePC());

                itemsList.add(categoryItem);
            }
        }

        goodsListModel.setCategoryPassItems(itemsList);
    }

    /**
     * 画面表示・再表示
     * 現在のカテゴリに属する商品一覧情報をページクラスに設定
     *
     * @param goodsGroupDtoList 商品グループリストDTO
     * @param col               サムネイル横表示商品数
     * @param goodsListModel    商品一覧画面 Model
     */
    public void toPageForLoadCurrentCategoryGoods(List<UkApiGetUkUniSearchGoodsResponseInfoDocsDtoResponse> goodsGroupDtoList,
                                                  int col,
                                                  GoodsListModel goodsListModel) {

        if (goodsGroupDtoList == null) {
            return;
        }
        List<GoodsGroupItem> itemsList = makeListPageItemList(goodsGroupDtoList, goodsListModel);

        // リスト
        goodsListModel.setGoodsGroupListItems(itemsList);

        // 縦リスト
        List<List<GoodsGroupItem>> listPageItemsItems = new ArrayList<>();

        // 横リスト
        List<GoodsGroupItem> listPageItems = new ArrayList<>();

        for (int i = 0; i < itemsList.size(); i++) {
            // 横表示毎にリストを作成
            if (i % col == 0) {
                listPageItems = new ArrayList<>();
            }

            // リストに追加
            listPageItems.add(itemsList.get(i));

            // 次のインデックスが横表示 or ラストインデックスの場合 縦リストに追加
            if ((i + 1) % col == 0 || i == (itemsList.size() - 1)) {
                listPageItemsItems.add(listPageItems);
            }

        }

        // サムネイルループリストにセット
        goodsListModel.setGoodsGroupThumbnailItemsItems(listPageItemsItems);
    }

    // 2023-renew No36-1, No61,67,95 from here

    /**
     * DTO一覧をitemクラスの一覧に変換する
     *
     * @param goodsGroupDtoList 商品グループ一覧情報DTO
     * @param goodsListModel    商品一覧画面 Model
     * @return カテゴリページアイテム情報一覧
     */
    protected List<GoodsGroupItem> makeListPageItemList(List<UkApiGetUkUniSearchGoodsResponseInfoDocsDtoResponse> goodsGroupDtoList,
                                                        GoodsListModel goodsListModel) {

        List<GoodsGroupItem> itemsList = new ArrayList<>();
        String currentCategoryId = goodsListModel.getCid();
        StringBuilder searchResult = new StringBuilder();
        for (UkApiGetUkUniSearchGoodsResponseInfoDocsDtoResponse goodsGroupDto : goodsGroupDtoList) {

            GoodsGroupItem listPageItem = ApplicationContextUtility.getBean(GoodsGroupItem.class);
            if (searchResult.length() > 0) {
                searchResult.append(",");
            }
            searchResult.append(goodsGroupDto.getGroupId());
            listPageItem.setGgcd(goodsGroupDto.getGroupId());
            listPageItem.setGoodsGroupName(goodsGroupDto.getItemName());

            // 商品概要説明
            listPageItem.setGoodsNote1(goodsGroupDto.getItemOverview());

            // 値引前価格
            BigDecimal maxPrice = new BigDecimal(goodsGroupDto.getMaxPrice());
            BigDecimal minPrice = new BigDecimal(goodsGroupDto.getMinPrice());
            listPageItem.setMaxPrice(maxPrice);
            listPageItem.setMinPrice(minPrice);

            BigDecimal saleMaxPrice = new BigDecimal(goodsGroupDto.getSaleMaxPrice());
            BigDecimal saleMinPrice = new BigDecimal(goodsGroupDto.getSaleMinPrice());

            if (toBoolean(goodsGroupDto.getSaleFlg())) {
                // 値引後最大価格
                listPageItem.setSaleMaxPrice(saleMaxPrice);
                // 値引後最小価格
                listPageItem.setSaleMinPrice(saleMinPrice);
                // 値引きコメント
                listPageItem.setGoodsPreDiscountPrice(goodsGroupDto.getSaleComment());

                // 価格帯チェック
                listPageItem.setGoodsDisplayPreDiscountPriceRange(
                                goodsUtility.isGoodsDisplayPriceRange(minPrice, maxPrice));
            }

            // カテゴリーID
            if (currentCategoryId != null && !"".equals(currentCategoryId)) {
                listPageItem.setCid(currentCategoryId);
            }

            // 画像の取得
            List<String> goodsImageList = new ArrayList<>();
            if (goodsGroupDto.getGoodsImageName() != null) {
                goodsImageList.add(goodsUtility.getGoodsImagePath(goodsGroupDto.getGoodsImageName()));
            }
            listPageItem.setGoodsImageItems(goodsImageList);

            // 商品状態はユニサーチから取得して判定
            listPageItem.setItemStatus(String.valueOf(goodsGroupDto.getItemStatus()));

            // PDR Migrate Customization from here
            // PDR Migrate Customization to here

            // PDR Migrate Customization from here
            // シリーズセール価格整合性フラグ
            listPageItem.setGroupSalePriceIntegrityFlag(
                            saleMaxPrice.compareTo(maxPrice) <= 0 || saleMinPrice.compareTo(minPrice) <= 0);
            // SALEアイコンフラグ
            listPageItem.setSaleIconFlag(
                            HTypeSaleIconFlag.ON.getValue().equals(String.valueOf(goodsGroupDto.getSaleFlg())));
            // お取りおきアイコンフラグ
            listPageItem.setReserveIconFlag(
                            HTypeReserveIconFlag.ON.getValue().equals(String.valueOf(goodsGroupDto.getReserveFlg())));
            // NEWアイコンフラグ
            listPageItem.setNewIconFlag(
                            HTypeNewIconFlag.ON.getValue().equals(String.valueOf(goodsGroupDto.getNewFlg())));
            // アウトレットアイコンフラグ
            listPageItem.setOutletIconFlag(
                            HTypeOutletIconFlag.ON.getValue().equals(String.valueOf(goodsGroupDto.getOutletFlg())));

            // シリーズ価格記号表示フラグ
            // 複数規格の場合に設定
            if (maxPrice.compareTo(minPrice) > 0) {
                listPageItem.setPriceMarkDispFlag(true);
            }
            // シリーズセール価格記号表示フラグ
            // 複数規格の場合に設定
            if (saleMaxPrice.compareTo(saleMinPrice) > 0) {
                listPageItem.setSalePriceMarkDispFlag(true);
            }
            // PDR Migrate Customization to here
            itemsList.add(listPageItem);
        }
        goodsListModel.setUkGoodsSearchResult(searchResult.toString());

        return itemsList;
    }
    // 2023-renew No36-1, No61,67,95 to here

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

    // 2023-renew No36-1, No61,67,95 from here

    /**
     * ユニサーチ（商品）リクエストDtoを設定
     *
     * @param model 商品一覧Model
     * @param cid   カテゴリーID
     * @param pnum  ページ数
     * @param sort  並び順（ユニサーチ）
     * @return カテゴリ詳細Dto
     */
    public UkApiGetUkUniSearchGoodsRequest setRequest(GoodsListModel model,
                                                      String cid,
                                                      String pnum,
                                                      String sort,
                                                      String stype,
                                                      boolean asc,
                                                      String fromView) {
        UkApiGetUkUniSearchGoodsRequest req = new UkApiGetUkUniSearchGoodsRequest();
        req.setCategory(cid);
        req.setKw(null);
        req.setPage(Integer.parseInt(pnum));
        req.setRows(model.DEFAULT_GOODS_LIMIT);
        String sortType = sort;
        /* UK並び順がnullの場合、stype&ascの値でUK並び順を決定する */
        if (sortType == null && !model.FROM_VIEW_CHANGE_VIEW_KEY.equals(fromView)) {
            sortType = getSortType(stype, asc);
        }
        req.setSortType(sortType);
        // ユーザーIDをセット
        CommonInfoUser user = model.getCommonInfo().getCommonInfoUser();
        String cryptUser = "";
        if (commonInfoUtility.isLogin(model.getCommonInfo())) {
            cryptUser = user.getCryptUserId();
        }
        req.setUser(cryptUser);

        /* タグログ用にmodellにセットする */
        model.setSort(sortType);
        model.setPnum(pnum);

        return req;
    }

    /**
     * stype&ascからsortTypeを決定する
     *
     * @param stype ソートタイプ
     * @param asc   昇順降順
     * @return 　　　UK並び順
     */
    public String getSortType(String stype, boolean asc) {
        if (PageInfo.SORT_TYPE_REGISTDATE_KEY.equals(stype) && asc) {
            return PageInfo.UK_SORT_TYPE_CATALOGID_KEY;
        } else if (PageInfo.SORT_TYPE_GOODSPRICE_KEY.equals(stype) && asc) {
            return PageInfo.UK_SORT_TYPE_SALEMINPRICE_KEY;
        } else if (PageInfo.SORT_TYPE_GOODSPRICE_KEY.equals(stype) && !asc) {
            return PageInfo.UK_SORT_TYPE_SALEMAXPRICE_KEY;
        } else if (PageInfo.SORT_TYPE_SALECOUNT_KEY.equals(stype)) {
            return PageInfo.UK_SORT_TYPE_RECOMMEND_KEY;
        }
        return PageInfo.UK_SORT_TYPE_RECOMMEND_KEY;
    }
    // 2023-renew No36-1, No61,67,95 to here
}
