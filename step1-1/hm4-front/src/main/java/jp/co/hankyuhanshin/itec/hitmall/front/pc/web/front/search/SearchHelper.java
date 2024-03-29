/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.search;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryDisplayEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryGoodsEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryTreeNodeResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupDisplayEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.StockDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.StockStatusDisplayEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.AccessSearchKeywordRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkRWordResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkRWordResponseInfoItemDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchContentsResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchContentsResponseInfoDocsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchGoodsResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchGoodsResponseInfoDocsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.BeanUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCategoryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCoolSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDentalMonopolySalesFlg;
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
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.category.CategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.stock.StockDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.access.AccessSearchKeywordEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.category.CategoryDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.category.CategoryGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.stock.StockStatusDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common.GoodsGroupItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common.GoodsIconItem;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.PageInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品検索画面 Helper
 * // PDR Migrate Customization from here
 * PDR#4,10 商品検索画面画面DXO<br/>
 *
 * @author kato
 * // PDR Migrate Customization to here
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Component
public class SearchHelper {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchHelper.class);

    /**
     * CalculatePriceUtility
     */
    private final CalculatePriceUtility calculatePriceUtility;

    /**
     * 変換ユーティリティ
     */
    private final ConversionUtility conversionUtility;

    /**
     * 商品系Helper
     */
    private final GoodsUtility goodsUtility;

    /**
     * 検索キーワードの最大文字数（超過部分は切り捨てます）
     */
    protected static final int KEYWORD_MAX_LENGTH = 255;

    /**
     * コンストラクタ
     *
     * @param calculatePriceUtility 金額計算のUtilityクラス
     * @param conversionUtility     変換ユーティリティクラス
     * @param goodsUtility          商品系Helper
     */
    @Autowired
    public SearchHelper(CalculatePriceUtility calculatePriceUtility,
                        ConversionUtility conversionUtility,
                        GoodsUtility goodsUtility) {
        this.calculatePriceUtility = calculatePriceUtility;
        this.conversionUtility = conversionUtility;
        this.goodsUtility = goodsUtility;
    }

    /**
     * 検索条件の設定
     *
     * @param searchModel 商品検索Model
     * @param fromView    遷移元画面
     */
    public void toPageForLoad(SearchModel searchModel, String fromView) {
        // クリア除外対象フィールドリスト
        List<String> clearExcludedFieldsList = new ArrayList<>();

        // **************************************************************************
        // 検索条件Modelをクリアする
        // ⇒fromView（遷移元パラメータ）によって、以下３パターン処理する
        //  【１】自画面（検索画面）からの遷移（fromView=search）
        //     ⇒削除のため遷移なし
        //
        //  【２】共通ヘッダからの検索（fromView=header）
        //     ⇒検索キーワードだけ残し、他項目をクリアする
        //
        //  【３】上記以外（fromView=その他）
        //     ⇒Getパラメータのみ活かし、他項目クリアする
        // **************************************************************************
        // 【１】自画面（検索画面）からの遷移
        if (StringUtils.equals(SearchModel.FROM_VIEW_SEARCH_KEY, fromView)) {
            // 何もせず処理を抜ける
            // ★Modelクリアを行わない★
            return;
        }

        // 【２】共通ヘッダからの検索
        // 【３】上記以外
        // リクエストパラメータ項目をクリア除外対象項目に登録
        HttpServletRequest request =
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            clearExcludedFieldsList.add(paramNames.nextElement());
        }

        // Modelクリアを実行
        BeanUtility beanUtility = ApplicationContextUtility.getBean(BeanUtility.class);
        beanUtility.clearBean(SearchModel.class, searchModel, clearExcludedFieldsList.toArray(new String[] {}));

        // 2023-renew No36-1, No61,67,95 from here
        // なぜかclearBeanでクリアされないためここでnullにする
        searchModel.setFallbackKeyword(null);
        searchModel.setContentsFallbackKeyword(null);
        // 2023-renew No36-1, No61,67,95 to here
        // keyword<--->q の調整
        if (StringUtils.isNotEmpty(searchModel.getKeyword())) {
            // keywordがパラメータに含まれている場合は、keywordをencodeした文字列をqに入れる
            try {
                trimKeyword(searchModel, searchModel.getKeyword());
                String encodedKeyword = URLEncoder.encode(searchModel.getKeyword(), "UTF-8");
                searchModel.setQ(encodedKeyword);
            } catch (UnsupportedEncodingException e) {
                LOGGER.warn("文字エンコーディングサポート例外：", e);
            }

        } else if (StringUtils.isNotEmpty(searchModel.getQ())) {
            // qがパラメータに含まれている場合は、qをdecodeencodeした文字列をkewwordに入れる
            try {
                String decodedKeyword = URLDecoder.decode(searchModel.getQ(), "UTF-8");
                trimKeyword(searchModel, decodedKeyword);
                // trimしたkeywordから再びQを設定する
                String encodedKeyword = URLEncoder.encode(searchModel.getKeyword(), "UTF-8");
                searchModel.setQ(encodedKeyword);
            } catch (UnsupportedEncodingException e) {
                LOGGER.warn("文字エンコーディングサポート例外：", e);
            }
        }
    }

    public void trimKeyword(SearchModel model, String keyword) {
        // 先頭と末尾のtrim
        keyword = keyword.replaceAll("^[\\s　]*", "").replaceAll("[\\s　]*$", "");
        // 256文字以降を切り捨て
        if (keyword.length() > KEYWORD_MAX_LENGTH) {
            keyword = keyword.substring(0, KEYWORD_MAX_LENGTH);
        }
        model.setKeyword(keyword);
    }

    /**
     * 画面表示・再表示<br/>
     *
     * @param response          ユニサーチ（商品）レスポンス
     * @param searchModel       商品検索Model
     */
    public void toPageForLoad(UkApiGetUkUniSearchGoodsResponse response, SearchModel searchModel) {

        // 2023-renew No3-taglog from here
        searchModel.setReqIdOfGoods(response.getResponseHeader().getReqID());
        // 2023-renew No3-taglog to here

        if (response.getResponse() == null || response.getResponse().getDocs() == null) {
            return;
        }
        // 商品グループリスト設定
        List<GoodsGroupItem> itemsList = setGoodsGroupListItems(response.getResponse().getDocs(), searchModel);

        // 商品グループサムネイルリスト設定
        // レスポンシブデザインのため利用なし
        setGoodsGroupThumbnailItemsItems(searchModel, itemsList);

        // リスト
        searchModel.setGoodsGroupListItems(itemsList);
        // 2023-renew No36-1, No61,67,95 from here
        // フォールバック後のキーワードを設定
        if (response.getResponseHeader().getFallback() != null) {
            String fallback = response.getResponseHeader().getFallback().getKeyword();
            if (!StringUtils.isEmpty(fallback)) {
                searchModel.setFallbackKeyword(fallback);
                searchModel.setTrimKeyword(searchModel.getKeyword().replaceAll(fallback + "[\\s|　]+", ""));
            }
        }
        // 2023-renew No36-1, No61,67,95 to here
    }

    /**
     * 商品グループリスト設定<br/>
     *
     * @param docsResponse      商品リストレスポンス
     * @param searchModel       商品検索Model
     * @param customParams      案件用引数
     * @return 商品グループリスト
     */
    protected List<GoodsGroupItem> setGoodsGroupListItems(List<UkApiGetUkUniSearchGoodsResponseInfoDocsDtoResponse> docsResponse,
                                                          SearchModel searchModel,
                                                          Object... customParams) {

        // 商品系Helper取得
        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);

        List<GoodsGroupItem> itemsList = new ArrayList<>();
        StringBuilder searchResult = new StringBuilder();
        // 2023-renew No36-1, No61,67,95 from here
        for (UkApiGetUkUniSearchGoodsResponseInfoDocsDtoResponse goodsGroupDto : docsResponse) {
            GoodsGroupItem listPageItem = ApplicationContextUtility.getBean(GoodsGroupItem.class);
            listPageItem.setGgcd(goodsGroupDto.getGroupId());
            if (searchResult.length() > 0) {
                searchResult.append(",");
            }
            searchResult.append(goodsGroupDto.getGroupId());
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

            // 値引後最大価格
            listPageItem.setSaleMaxPrice(saleMaxPrice);
            // 値引後最小価格
            listPageItem.setSaleMinPrice(saleMinPrice);
            // 値引きコメント
            listPageItem.setGoodsPreDiscountPrice(goodsGroupDto.getSaleComment());

            // 値下げ前価格 - 価格帯チェック
            listPageItem.setGoodsDisplayPreDiscountPriceRange(
                            goodsUtility.isGoodsDisplayPriceRange(minPrice, maxPrice));

            // 画像の取得
            List<String> goodsImageList = new ArrayList<>();
            if (goodsGroupDto.getGoodsImageName() != null) {
                goodsImageList.add(goodsUtility.getGoodsImagePath(goodsGroupDto.getGoodsImageName()));
            }
            listPageItem.setGoodsImageItems(goodsImageList);

            // 商品状態はユニサーチから取得して判定
            listPageItem.setItemStatus(String.valueOf(goodsGroupDto.getItemStatus()));

            // シリーズセール価格整合性フラグ
            listPageItem.setGroupSalePriceIntegrityFlag(
                            saleMaxPrice.compareTo(maxPrice) < 0 || saleMinPrice.compareTo(minPrice) < 0);
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

            itemsList.add(listPageItem);
            // 商品グループリスト設定
        }
        // 2023-renew No36-1, No61,67,95 to here
        searchModel.setGoodsGroupListItems(itemsList);
        searchModel.setUkGoodsSearchResult(searchResult.toString());

        return itemsList;
    }

    // 2023-renew No36-1, No61,67,95 from here

    /**
     * 画面表示・再表示<br/>
     *
     * @param response          ユニサーチ（商品）レスポンス
     * @param searchModel       商品検索Model
     */
    public void toPageForLoad(UkApiGetUkRWordResponse response, SearchModel searchModel) {
        if (response.getResponse() == null || response.getResponse().getRelatedWord() == null
            || response.getResponse().getRelatedWord().getDocs() == null
            || response.getResponse().getRelatedWord().getDocs().getItem() == null) {
            return;
        }

        List<String> rwordList = new ArrayList<>();
        for (UkApiGetUkRWordResponseInfoItemDtoResponse rword : response.getResponse()
                                                                        .getRelatedWord()
                                                                        .getDocs()
                                                                        .getItem()) {
            rwordList.add(rword.getWord());
        }
        searchModel.setRelatedWordList(rwordList);
    }
    // 2023-renew No36-1, No61,67,95 to here

    /**
     * 商品グループ情報設定<br/>
     *
     * @param goodsUtility   商品系Helper
     * @param goodsGroupDto  商品グループDTO
     * @param goodsGroupItem 商品検索画面ページアイテム
     * @param searchModel    商品検索画面ページ
     * @param customParams   案件用引数
     */
    protected void setGoodsGroupData(GoodsUtility goodsUtility,
                                     GoodsGroupDto goodsGroupDto,
                                     GoodsGroupItem goodsGroupItem,
                                     SearchModel searchModel,
                                     Object... customParams) {
        GoodsGroupEntity goodsGroupEntity = goodsGroupDto.getGoodsGroupEntity();
        goodsGroupItem.setGoodsGroupSeq(goodsGroupEntity.getGoodsGroupSeq());
        goodsGroupItem.setGgcd(goodsGroupEntity.getGoodsGroupCode());
        goodsGroupItem.setGoodsGroupName(goodsGroupEntity.getGoodsGroupName());
        goodsGroupItem.setGoodsPreDiscountPrice(goodsGroupEntity.getGoodsPreDiscountPrice());

        GoodsGroupDisplayEntity goodsGroupDisplayEntity = goodsGroupDto.getGoodsGroupDisplayEntity();
        goodsGroupItem.setGoodsNote1(goodsGroupDisplayEntity.getGoodsNote1());

        // 税率
        BigDecimal taxRate = goodsGroupEntity.getTaxRate();
        goodsGroupItem.setTaxRate(taxRate);

        // 通常価格 - 税込計算
        BigDecimal goodsPrice = goodsGroupEntity.getGoodsGroupMinPricePc();
        goodsGroupItem.setGoodsPrice(goodsPrice);

        // 値引き前価格設定
        BigDecimal preDiscountMinPrice = goodsGroupEntity.getPreDiscountMinPrice();

        if (preDiscountMinPrice != null) {
            goodsGroupItem.setPreDiscountPrice(preDiscountMinPrice);
            goodsGroupItem.setPreDiscountPriceInTax(
                            calculatePriceUtility.getTaxIncludedPrice(preDiscountMinPrice, taxRate));
        }
        // 通常価格 - 価格帯チェック
        BigDecimal goodsGroupMaxPricePc = goodsGroupEntity.getGoodsGroupMaxPricePc();

        goodsGroupItem.setGoodsDisplayPriceRange(
                        goodsUtility.isGoodsDisplayPriceRange(goodsPrice, goodsGroupMaxPricePc));
        goodsGroupItem.setGoodsDisplayPreDiscountPriceRange(goodsUtility.isGoodsDisplayPriceRange(preDiscountMinPrice,
                                                                                                  goodsGroupEntity.getPreDiscountMaxPrice()
                                                                                                 ));

        // PDR Migrate Customization from here
        // 販売可能商品区分
        // 2023-renew No2 from here

        // 2023-renew No2 to here
        // PDR Migrate Customization to here

        // PDR Migrate Customization from here
        // シリーズセール価格整合性フラグ
        goodsGroupItem.setGroupSalePriceIntegrityFlag(
                        HTypeSalePriceIntegrityFlag.MATCH.equals(goodsGroupEntity.getGroupSalePriceIntegrityFlag()) ?
                                        true :
                                        false);
        // SALEアイコンフラグ
        goodsGroupItem.setSaleIconFlag(
                        HTypeSaleIconFlag.ON.equals(goodsGroupDisplayEntity.getSaleIconFlag()) ? true : false);
        // お取りおきアイコンフラグ
        goodsGroupItem.setReserveIconFlag(
                        HTypeReserveIconFlag.ON.equals(goodsGroupDisplayEntity.getReserveIconFlag()) ? true : false);
        // NEWアイコンフラグ
        goodsGroupItem.setNewIconFlag(
                        HTypeNewIconFlag.ON.equals(goodsGroupDisplayEntity.getNewIconFlag()) ? true : false);

        // 商品表示単
        if (HTypeGroupPriceMarkDispFlag.ON.equals(goodsGroupEntity.getGroupPriceMarkDispFlag())) {
            goodsGroupItem.setPriceMarkDispFlag(true);
        }
        // 商品表示値引き後単価PC
        if (HTypeGroupSalePriceMarkDispFlag.ON.equals(goodsGroupEntity.getGroupSalePriceMarkDispFlag())) {
            goodsGroupItem.setSalePriceMarkDispFlag(true);
        }
        // 公開状態
        goodsGroupItem.setGoodsOpenStatus(goodsGroupDto.getGoodsGroupEntity().getGoodsOpenStatusPC());

        // PDR Migrate Customization to here
    }

    /**
     * 商品画像情報設定<br/>
     *
     * @param goodsUtility   商品系Helper
     * @param goodsGroupDto  商品グループDTO
     * @param goodsGroupItem 商品検索画面ページアイテム
     * @param customParams   案件用引数
     */
    protected void setGoodsGroupImage(GoodsUtility goodsUtility,
                                      GoodsGroupDto goodsGroupDto,
                                      GoodsGroupItem goodsGroupItem,
                                      Object... customParams) {
        // 画像の取得
        List<String> goodsImageList = new ArrayList<>();
        if (goodsGroupDto.getGoodsGroupImageEntityList() != null) {
            for (GoodsGroupImageEntity goodsGroupImageEntity : goodsGroupDto.getGoodsGroupImageEntityList()) {
                goodsImageList.add(goodsUtility.getGoodsImagePath(goodsGroupImageEntity.getImageFileName()));
            }
        }
        goodsGroupItem.setGoodsImageItems(goodsImageList);
    }

    /**
     * アイコン情報設定<br/>
     *
     * @param goodsUtility   商品系Helper
     * @param goodsGroupDto  商品グループDTO
     * @param goodsGroupItem 商品検索画面ページアイテム
     * @param customParams   案件用引数
     */
    protected void setGoodsIconItems(GoodsUtility goodsUtility,
                                     GoodsGroupDto goodsGroupDto,
                                     GoodsGroupItem goodsGroupItem,
                                     Object... customParams) {
        List<GoodsIconItem> goodsIconList = new ArrayList<>();
        if (goodsGroupDto.getGoodsInformationIconDetailsDtoList() != null) {
            setGoodsIconList(goodsUtility, goodsGroupDto, goodsIconList);
        }
        // アイコン情報の設定
        goodsGroupItem.setGoodsIconItems(goodsIconList);
    }

    /**
     * 商品アイコンリスト設定<br/>
     *
     * @param goodsUtility  商品系Helper
     * @param goodsGroupDto 商品グループDTO
     * @param goodsIconList 商品アイコンリスト
     * @param customParams  案件用引数
     */
    protected void setGoodsIconList(GoodsUtility goodsUtility,
                                    GoodsGroupDto goodsGroupDto,
                                    List<GoodsIconItem> goodsIconList,
                                    Object... customParams) {
        for (GoodsInformationIconDetailsDto goodsInformationIconDetailsDto : goodsGroupDto.getGoodsInformationIconDetailsDtoList()) {
            GoodsIconItem iconPageItem = ApplicationContextUtility.getBean(GoodsIconItem.class);
            iconPageItem.setIconName(goodsInformationIconDetailsDto.getIconName());
            iconPageItem.setIconColorCode(goodsInformationIconDetailsDto.getColorCode());
            goodsIconList.add(iconPageItem);
        }
    }

    /**
     * 商品グループサムネイルリスト設定<br/>
     *
     * @param searchModel  商品検索Model
     * @param itemsList    商品グループリスト
     * @param customParams 案件用引数
     */
    protected void setGoodsGroupThumbnailItemsItems(SearchModel searchModel,
                                                    List<GoodsGroupItem> itemsList,
                                                    Object... customParams) {

        // 縦リスト
        List<List<GoodsGroupItem>> listPageItemsItems = new ArrayList<>();
        // 横リスト
        List<GoodsGroupItem> listPageItems = new ArrayList<>();

        int col = searchModel.getCol();

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
        searchModel.setGoodsGroupThumbnailItemsItems(listPageItemsItems);
    }

    /**
     * 画面表示・再表示<br/>
     *
     * @param response          ユニサーチ（コンテンツ）レスポンス
     * @param searchModel       商品検索Model
     */
    public void toPageForLoad(UkApiGetUkUniSearchContentsResponse response, SearchModel searchModel) {
        // 2023-renew No3-taglog from here
        searchModel.setReqIdOfContents(response.getResponseHeader().getReqID());
        // 2023-renew No3-taglog to here

        if (response.getResponse() == null || response.getResponse().getDocs() == null) {
            return;
        }
        searchModel.setContentsSearchTotalCount(response.getResponse().getNumFound());
        // コンテンツグループリスト設定
        List<ContentsItem> itemsList = setContentsListItems(response.getResponse().getDocs(), searchModel);

        // リスト
        searchModel.setContentsListItems(itemsList);
        // フォールバック後のキーワードを設定
        if (response.getResponseHeader().getFallback() != null) {
            String fallback = response.getResponseHeader().getFallback().getKeyword();
            if (!StringUtils.isEmpty(fallback)) {
                searchModel.setContentsFallbackKeyword(fallback);
                searchModel.setContentsTrimKeyword(searchModel.getKeyword().replaceAll(fallback + "[\\s|　]+", ""));
            }
        }
    }

    /**
     * 商品グループリスト設定<br/>
     *
     * @param docsResponse      商品リストレスポンス
     * @param searchModel       商品検索Model
     * @param customParams      案件用引数
     * @return 商品グループリスト
     */
    protected List<ContentsItem> setContentsListItems(List<UkApiGetUkUniSearchContentsResponseInfoDocsDtoResponse> docsResponse,
                                                      SearchModel searchModel,
                                                      Object... customParams) {

        List<ContentsItem> itemsList = new ArrayList<>();
        StringBuilder contentsSearch = new StringBuilder();
        // 2023-renew No36-1, No61,67,95 from here
        for (UkApiGetUkUniSearchContentsResponseInfoDocsDtoResponse contentsDto : docsResponse) {
            ContentsItem listPageItem = ApplicationContextUtility.getBean(ContentsItem.class);
            if (contentsSearch.length() > 0) {
                contentsSearch.append(",");
            }
            contentsSearch.append(contentsDto.getContentId());
            listPageItem.setContentName(contentsDto.getContentName());
            listPageItem.setTransitionUrl(contentsDto.getTransitionUrl());
            if (!StringUtils.isEmpty(contentsDto.getContentImageUrl())) {
                listPageItem.setContentImageUrl(PropertiesUtil.getSystemPropertiesValue("images.path.contents") + "/"
                                                + contentsDto.getContentImageUrl());
            }
            itemsList.add(listPageItem);
            // 商品グループリスト設定
        }
        // 2023-renew No36-1, No61,67,95 to here
        searchModel.setContentsListItems(itemsList);
        searchModel.setUkContentsSearchResult(contentsSearch.toString());
        return itemsList;
    }

    /**
     * 画面表示・再表示<br/>
     * カテゴリ情報をページクラスにセット<br />
     *
     * @param categoryDto カテゴリDTO
     * @param searchModel 商品検索Model
     */
    public void toPageForLoad(CategoryDto categoryDto, SearchModel searchModel) {

        // 検索条件プルダウンに、項目をセット
        // 何階層目まで表示するかは、プロパティファイルにて設定
        List<Map<String, String>> categoryList = new ArrayList<>();

        if (categoryDto.getCategoryDtoList() != null) {
            categoryList = makeList(categoryDto, categoryList, searchModel);
        }

        searchModel.setCondCidItems(categoryList);
    }

    /**
     * 子カテゴリを再帰的に呼び、カテゴリ情報リストを作成<br/>
     *
     * @param categoryDto  カテゴリDTO
     * @param categoryList カテゴリ情報リスト
     * @param searchModel  商品検索Model
     * @return カテゴリ情報リスト
     */
    protected List<Map<String, String>> makeList(CategoryDto categoryDto,
                                                 List<Map<String, String>> categoryList,
                                                 SearchModel searchModel) {

        for (int i = 0; i < categoryDto.getCategoryDtoList().size(); i++) {
            CategoryDto childCategoryDto = categoryDto.getCategoryDtoList().get(i);

            if (childCategoryDto.getCategoryEntity().getCategoryType().equals(HTypeCategoryType.NORMAL)) {

                Map<String, String> map = new HashMap<>();

                // カテゴリーレベルごとの、プレフィックスラベルを取得

                map.put("label", childCategoryDto.getCategoryDisplayEntity().getCategoryNamePC());
                map.put("value", childCategoryDto.getCategoryEntity().getCategoryId());
                map.put("level", String.valueOf(childCategoryDto.getCategoryEntity().getCategoryLevel()));
                categoryList.add(map);

                if (childCategoryDto.getCategoryDtoList() != null) {
                    makeList(childCategoryDto, categoryList, searchModel);
                }
            }
        }

        return categoryList;
    }

    /**
     * カテゴリEntityに変換
     *
     * @param categoryEntityResponse カテゴリEntityレスポンス
     * @return カテゴリEntity
     */
    public CategoryEntity toCategoryEntity(CategoryEntityResponse categoryEntityResponse) {
        if (ObjectUtils.isEmpty(categoryEntityResponse)) {
            return null;
        }
        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setCategorySeq(categoryEntityResponse.getCategorySeq());
        categoryEntity.setCategoryId(categoryEntityResponse.getCategoryId());
        categoryEntity.setCategoryName(categoryEntityResponse.getCategoryName());
        categoryEntity.setCategoryOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class,
                                                                             categoryEntityResponse.getCategoryOpenStatus()
                                                                            ));
        categoryEntity.setCategoryOpenStartTimePC(
                        conversionUtility.toTimeStamp(categoryEntityResponse.getCategoryOpenStartTime()));
        categoryEntity.setCategoryOpenEndTimePC(
                        conversionUtility.toTimeStamp(categoryEntityResponse.getCategoryOpenEndTime()));
        categoryEntity.setCategoryType(EnumTypeUtil.getEnumFromValue(HTypeCategoryType.class,
                                                                     categoryEntityResponse.getCategoryType()
                                                                    ));
        categoryEntity.setCategorySeqPath(categoryEntityResponse.getCategorySeqPath());
        categoryEntity.setOrderDisplay(categoryEntityResponse.getOrderDisplay());
        categoryEntity.setRootCategorySeq(categoryEntityResponse.getRootCategorySeq());
        categoryEntity.setCategoryLevel(categoryEntityResponse.getCategoryLevel());
        categoryEntity.setCategoryPath(categoryEntityResponse.getCategoryPath());
        categoryEntity.setManualDisplayFlag(EnumTypeUtil.getEnumFromValue(HTypeManualDisplayFlag.class,
                                                                          categoryEntityResponse.getManualDisplayFlag()
                                                                         ));
        categoryEntity.setVersionNo(categoryEntityResponse.getVersionNo());
        categoryEntity.setRegistTime(conversionUtility.toTimeStamp(categoryEntityResponse.getRegistTime()));
        categoryEntity.setUpdateTime(conversionUtility.toTimeStamp(categoryEntityResponse.getUpdateTime()));
        categoryEntity.setSiteMapFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeSiteMapFlag.class, categoryEntityResponse.getSiteMapFlag()));
        categoryEntity.setOpenGoodsCountPC(categoryEntityResponse.getOpenGoodsCount());

        return categoryEntity;
    }

    /**
     * カテゴリ木構造取得リクエストに変換
     *
     * @param entity アクセス検索キーワードクラス
     * @return カテゴリ木構造取得リクエスト
     */
    public AccessSearchKeywordRegistRequest toAccessSearchKeywordRequest(AccessSearchKeywordEntity entity) {

        AccessSearchKeywordRegistRequest registRequest = new AccessSearchKeywordRegistRequest();
        registRequest.setAccessTime(entity.getAccessTime());
        registRequest.setSearchkeyword(entity.getSearchkeyword());
        registRequest.setRegistTime(entity.getRegistTime());
        registRequest.setPageId(entity.getPageId());
        registRequest.setSearchCategorySeq(entity.getSearchCategorySeq());
        registRequest.setSearchPriceFrom(entity.getSearchPriceFrom());
        registRequest.setSearchPriceTo(entity.getSearchPriceTo());
        registRequest.setSearchResultCount(entity.getSearchResultCount());
        registRequest.setAccessUid(entity.getAccessUid());
        registRequest.setUpdateTime(entity.getUpdateTime());
        return registRequest;
    }

    /**
     * 商品グループDtoクラススに変換
     *
     * @param goodsGroupDtoListResponse 商品覧レスポンス
     * @return 商品グループ一覧情報DTO
     */
    protected List<GoodsGroupDto> toGoodsGroupDtoList(GoodsGroupDtoListResponse goodsGroupDtoListResponse) {
        // 処理前は存在しないためnullを返す
        if (goodsGroupDtoListResponse == null) {
            return null;
        }

        List<GoodsGroupDto> goodsGroupDtoList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(goodsGroupDtoListResponse.getGoodsGroupDtoListResponse())) {
            for (GoodsGroupDtoResponse i : goodsGroupDtoListResponse.getGoodsGroupDtoListResponse()) {
                GoodsGroupDto goodsGroupDto = new GoodsGroupDto();
                goodsGroupDto.setTaxRate(i.getTaxRate());
                if (i.getGoodsGroupDisplayEntity() != null) {
                    goodsGroupDto.setGoodsGroupDisplayEntity(toGoodsGroupDisplayEntity(i.getGoodsGroupDisplayEntity()));
                }
                goodsGroupDto.setGoodsGroupEntity(toGoodsGroupEntity(i.getGoodsGroupEntity()));
                if (CollectionUtil.isNotEmpty(i.getGoodsDtoList())) {
                    goodsGroupDto.setGoodsDtoList(toGoodsDtoList(i.getGoodsDtoList()));
                }
                if (CollectionUtil.isNotEmpty(i.getGoodsGroupImageEntityList())) {
                    goodsGroupDto.setGoodsGroupImageEntityList(
                                    toGoodsGroupImageEntityList(i.getGoodsGroupImageEntityList()));
                }
                if (i.getBatchUpdateStockStatus() != null) {
                    goodsGroupDto.setBatchUpdateStockStatus(toStockStatusDisplayEntity(i.getBatchUpdateStockStatus()));
                }
                if (CollectionUtil.isNotEmpty(i.getCategoryGoodsEntityList())) {
                    goodsGroupDto.setCategoryGoodsEntityList(toCategoryGoodsEntityList(i.getCategoryGoodsEntityList()));
                }
                if (i.getRealTimeStockStatus() != null) {
                    goodsGroupDto.setRealTimeStockStatus(toStockStatusDisplayEntity(i.getRealTimeStockStatus()));
                }

                goodsGroupDtoList.add(goodsGroupDto);
            }
        }
        return goodsGroupDtoList;
    }

    /**
     * [商品グループ表示レスポンス]を[商品グループ表示クラス]に変換する
     *
     * @param goodsGroupDisplayResponse 商品グループ表示レスポンス
     * @return GoodsGroupDisplayEntity 商品グループ表示クラス
     */
    public GoodsGroupDisplayEntity toGoodsGroupDisplayEntity(GoodsGroupDisplayEntityResponse goodsGroupDisplayResponse) {

        if (ObjectUtils.isEmpty(goodsGroupDisplayResponse)) {
            return null;
        }

        GoodsGroupDisplayEntity goodsGroupDisplayEntity = new GoodsGroupDisplayEntity();

        goodsGroupDisplayEntity.setGoodsGroupSeq(goodsGroupDisplayResponse.getGoodsGroupSeq());
        goodsGroupDisplayEntity.setInformationIconPC(goodsGroupDisplayResponse.getInformationIcon());
        goodsGroupDisplayEntity.setSearchKeyword(goodsGroupDisplayResponse.getSearchKeyword());
        goodsGroupDisplayEntity.setSearchKeywordEm(goodsGroupDisplayResponse.getSearchKeywordEm());
        if (goodsGroupDisplayResponse.getUnitManagementFlag() != null) {
            goodsGroupDisplayEntity.setUnitManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class,
                                                                                        goodsGroupDisplayResponse.getUnitManagementFlag()
                                                                                       ));
        }
        goodsGroupDisplayEntity.setUnitTitle1(goodsGroupDisplayResponse.getUnitTitle1());
        goodsGroupDisplayEntity.setUnitTitle2(goodsGroupDisplayResponse.getUnitTitle2());
        goodsGroupDisplayEntity.setMetaDescription(goodsGroupDisplayResponse.getMetaDescription());
        goodsGroupDisplayEntity.setMetaKeyword(goodsGroupDisplayResponse.getMetaKeyword());
        goodsGroupDisplayEntity.setDeliveryType(goodsGroupDisplayResponse.getDeliveryType());
        goodsGroupDisplayEntity.setGoodsNote1(goodsGroupDisplayResponse.getGoodsNote1());
        goodsGroupDisplayEntity.setGoodsNote2(goodsGroupDisplayResponse.getGoodsNote2());
        goodsGroupDisplayEntity.setGoodsNote3(goodsGroupDisplayResponse.getGoodsNote3());
        goodsGroupDisplayEntity.setGoodsNote4(goodsGroupDisplayResponse.getGoodsNote4());
        goodsGroupDisplayEntity.setGoodsNote5(goodsGroupDisplayResponse.getGoodsNote5());
        goodsGroupDisplayEntity.setGoodsNote6(goodsGroupDisplayResponse.getGoodsNote6());
        goodsGroupDisplayEntity.setGoodsNote7(goodsGroupDisplayResponse.getGoodsNote7());
        goodsGroupDisplayEntity.setGoodsNote8(goodsGroupDisplayResponse.getGoodsNote8());
        goodsGroupDisplayEntity.setGoodsNote9(goodsGroupDisplayResponse.getGoodsNote9());
        goodsGroupDisplayEntity.setGoodsNote10(goodsGroupDisplayResponse.getGoodsNote10());
        goodsGroupDisplayEntity.setGoodsNote11(goodsGroupDisplayResponse.getGoodsNote11());
        goodsGroupDisplayEntity.setGoodsNote12(goodsGroupDisplayResponse.getGoodsNote12());
        goodsGroupDisplayEntity.setGoodsNote13(goodsGroupDisplayResponse.getGoodsNote13());
        goodsGroupDisplayEntity.setGoodsNote14(goodsGroupDisplayResponse.getGoodsNote14());
        goodsGroupDisplayEntity.setGoodsNote15(goodsGroupDisplayResponse.getGoodsNote15());
        goodsGroupDisplayEntity.setGoodsNote16(goodsGroupDisplayResponse.getGoodsNote16());
        goodsGroupDisplayEntity.setGoodsNote17(goodsGroupDisplayResponse.getGoodsNote17());
        goodsGroupDisplayEntity.setGoodsNote18(goodsGroupDisplayResponse.getGoodsNote18());
        goodsGroupDisplayEntity.setGoodsNote19(goodsGroupDisplayResponse.getGoodsNote19());
        goodsGroupDisplayEntity.setGoodsNote20(goodsGroupDisplayResponse.getGoodsNote20());
        goodsGroupDisplayEntity.setOrderSetting1(goodsGroupDisplayResponse.getOrderSetting1());
        goodsGroupDisplayEntity.setOrderSetting2(goodsGroupDisplayResponse.getOrderSetting2());
        goodsGroupDisplayEntity.setOrderSetting3(goodsGroupDisplayResponse.getOrderSetting3());
        goodsGroupDisplayEntity.setOrderSetting4(goodsGroupDisplayResponse.getOrderSetting4());
        goodsGroupDisplayEntity.setOrderSetting5(goodsGroupDisplayResponse.getOrderSetting5());
        goodsGroupDisplayEntity.setOrderSetting6(goodsGroupDisplayResponse.getOrderSetting6());
        goodsGroupDisplayEntity.setOrderSetting7(goodsGroupDisplayResponse.getOrderSetting7());
        goodsGroupDisplayEntity.setOrderSetting8(goodsGroupDisplayResponse.getOrderSetting8());
        goodsGroupDisplayEntity.setOrderSetting9(goodsGroupDisplayResponse.getOrderSetting9());
        goodsGroupDisplayEntity.setOrderSetting10(goodsGroupDisplayResponse.getOrderSetting10());
        goodsGroupDisplayEntity.setRegistTime(conversionUtility.toTimeStamp(goodsGroupDisplayResponse.getRegistTime()));
        goodsGroupDisplayEntity.setUpdateTime(conversionUtility.toTimeStamp(goodsGroupDisplayResponse.getUpdateTime()));
        goodsGroupDisplayEntity.setSaleIconFlag(EnumTypeUtil.getEnumFromValue(HTypeSaleIconFlag.class,
                                                                              goodsGroupDisplayResponse.getSaleIconFlag()
                                                                             ));
        goodsGroupDisplayEntity.setNewIconFlag(EnumTypeUtil.getEnumFromValue(HTypeNewIconFlag.class,
                                                                             goodsGroupDisplayResponse.getNewIconFlag()
                                                                            ));
        goodsGroupDisplayEntity.setReserveIconFlag(EnumTypeUtil.getEnumFromValue(HTypeReserveIconFlag.class,
                                                                                 goodsGroupDisplayResponse.getReserveIconFlag()
                                                                                ));

        return goodsGroupDisplayEntity;
    }

    /**
     * 商品グループクラススに変換
     *
     * @param goodsGroupSubResponse 商品詳細レスポンスクラス
     * @return 商品グループクラス
     */
    public GoodsGroupEntity toGoodsGroupEntity(GoodsGroupEntityResponse goodsGroupSubResponse) {
        // 処理前は存在しないためnullを返す
        if (goodsGroupSubResponse == null) {
            return null;
        }

        GoodsGroupEntity goodsGroupEntity = new GoodsGroupEntity();
        if (goodsGroupSubResponse.getGoodsClassType() != null) {
            goodsGroupEntity.setGoodsClassType(EnumTypeUtil.getEnumFromValue(HTypeGoodsClassType.class,
                                                                             goodsGroupSubResponse.getGoodsClassType()
                                                                            ));
        }
        goodsGroupEntity.setGoodsGroupSeq(goodsGroupSubResponse.getGoodsGroupSeq());
        goodsGroupEntity.setGoodsGroupCode(goodsGroupSubResponse.getGoodsGroupCode());
        goodsGroupEntity.setGoodsGroupName(goodsGroupSubResponse.getGoodsGroupName());
        goodsGroupEntity.setWhatsnewDate(conversionUtility.toTimeStamp(goodsGroupSubResponse.getWhatsnewDate()));
        if (goodsGroupSubResponse.getGoodsOpenStatus() != null) {
            goodsGroupEntity.setGoodsOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                                goodsGroupSubResponse.getGoodsOpenStatus()
                                                                               ));
        }
        goodsGroupEntity.setOpenStartTimePC(conversionUtility.toTimeStamp(goodsGroupSubResponse.getOpenStartTime()));
        goodsGroupEntity.setOpenEndTimePC(conversionUtility.toTimeStamp(goodsGroupSubResponse.getOpenEndTime()));
        if (goodsGroupSubResponse.getGoodsTaxType() != null) {
            goodsGroupEntity.setGoodsTaxType(EnumTypeUtil.getEnumFromValue(HTypeGoodsTaxType.class,
                                                                           goodsGroupSubResponse.getGoodsTaxType()
                                                                          ));
        }
        goodsGroupEntity.setTaxRate(goodsGroupSubResponse.getTaxRate());
        if (goodsGroupEntity.getAlcoholFlag() != null) {
            goodsGroupEntity.setAlcoholFlag(EnumTypeUtil.getEnumFromValue(HTypeAlcoholFlag.class,
                                                                          goodsGroupSubResponse.getAlcoholFlag()
                                                                         ));
        }
        if (goodsGroupEntity.getSnsLinkFlag() != null) {
            goodsGroupEntity.setSnsLinkFlag(EnumTypeUtil.getEnumFromValue(HTypeSnsLinkFlag.class,
                                                                          goodsGroupSubResponse.getSnsLinkFlag()
                                                                         ));
        }

        goodsGroupEntity.setVersionNo(goodsGroupSubResponse.getVersionNo());
        goodsGroupEntity.setRegistTime(conversionUtility.toTimeStamp(goodsGroupSubResponse.getRegistTime()));
        goodsGroupEntity.setUpdateTime(conversionUtility.toTimeStamp(goodsGroupSubResponse.getUpdateTime()));
        if (goodsGroupSubResponse.getDentalMonopolySalesFlg() != null) {
            goodsGroupEntity.setDentalMonopolySalesFlg(EnumTypeUtil.getEnumFromValue(HTypeDentalMonopolySalesFlg.class,
                                                                                     goodsGroupSubResponse.getDentalMonopolySalesFlg()
                                                                                    ));
        }
        goodsGroupEntity.setGroupPrice(goodsGroupSubResponse.getGroupPrice());
        goodsGroupEntity.setGoodsPreDiscountPrice(goodsGroupSubResponse.getGoodsPreDiscountPrice());
        if (goodsGroupSubResponse.getGroupSalePriceIntegrityFlag() != null) {
            goodsGroupEntity.setGroupSalePriceIntegrityFlag(
                            EnumTypeUtil.getEnumFromValue(HTypeSalePriceIntegrityFlag.class,
                                                          goodsGroupSubResponse.getGroupSalePriceIntegrityFlag()
                                                         ));
        }
        goodsGroupEntity.setGroupSalePrice(goodsGroupSubResponse.getGroupSalePrice());
        if (goodsGroupSubResponse.getGroupPriceMarkDispFlag() != null) {
            goodsGroupEntity.setGroupPriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypeGroupPriceMarkDispFlag.class,
                                                                                     goodsGroupSubResponse.getGroupPriceMarkDispFlag()
                                                                                    ));
        }
        if (goodsGroupSubResponse.getGroupSalePriceMarkDispFlag() != null) {
            goodsGroupEntity.setGroupSalePriceMarkDispFlag(
                            EnumTypeUtil.getEnumFromValue(HTypeGroupSalePriceMarkDispFlag.class,
                                                          goodsGroupSubResponse.getGroupSalePriceMarkDispFlag()
                                                         ));
        }

        goodsGroupEntity.setPreDiscountMaxPrice(goodsGroupSubResponse.getPreDiscountMaxPrice());
        goodsGroupEntity.setPreDiscountMinPrice(goodsGroupSubResponse.getPreDiscountMinPrice());
        goodsGroupEntity.setGoodsGroupMinPricePc(goodsGroupSubResponse.getGoodsGroupMinPrice());
        goodsGroupEntity.setCatalogDisplayOrder(goodsGroupSubResponse.getCatalogDisplayOrder());

        return goodsGroupEntity;
    }

    /**
     * 商品DTOリストクラスリストに変換
     *
     * @param goodsResponseList 商品レスポンスクラスリスト
     * @return 商品DTOリスト
     */
    public List<GoodsDto> toGoodsDtoList(List<GoodsDtoResponse> goodsResponseList) {

        if (CollectionUtil.isEmpty(goodsResponseList)) {
            return new ArrayList<>();
        }

        List<GoodsDto> goodsDtoList = new ArrayList<>();

        goodsResponseList.forEach(item -> {

            GoodsDto goodsDto = new GoodsDto();
            if (item.getGoodsEntity() != null) {
                GoodsEntity goodsEntity = toGoodsEntity(item.getGoodsEntity());
                goodsDto.setGoodsEntity(goodsEntity);
                goodsDtoList.add(goodsDto);
            }
            if (item.getStockDto() != null) {
                StockDto stockDto = toStockDto(item.getStockDto());
                goodsDto.setStockDto(stockDto);
            }
            goodsDto.setDeleteFlg(item.getDeleteFlg());

            if (item.getStockStatus() != null) {
                goodsDto.setStockStatusPc(
                                EnumTypeUtil.getEnumFromValue(HTypeStockStatusType.class, item.getStockStatus()));
            }

            goodsDtoList.add(goodsDto);
        });

        return goodsDtoList;
    }

    /**
     * 商品クラススに変換
     *
     * @param goodsSubResponse 商品クラス
     * @return 商品クラス
     */
    public GoodsEntity toGoodsEntity(GoodsEntityResponse goodsSubResponse) {

        if (ObjectUtils.isEmpty(goodsSubResponse)) {
            return null;
        }

        GoodsEntity goodsEntity = new GoodsEntity();

        goodsEntity.setGoodsSeq(goodsSubResponse.getGoodsSeq());
        goodsEntity.setGoodsGroupSeq(goodsSubResponse.getGoodsGroupSeq());
        goodsEntity.setGoodsCode(goodsSubResponse.getGoodsCode());
        goodsEntity.setJanCode(goodsSubResponse.getJanCode());
        if (goodsSubResponse.getSaleStatus() != null) {
            goodsEntity.setSaleStatusPC(EnumTypeUtil.getEnumFromValue(HTypeGoodsSaleStatus.class,
                                                                      goodsSubResponse.getSaleStatus()
                                                                     ));
        }
        goodsEntity.setSaleStartTimePC(conversionUtility.toTimeStamp(goodsSubResponse.getSaleStartTime()));
        goodsEntity.setSaleEndTimePC(conversionUtility.toTimeStamp(goodsSubResponse.getSaleEndTime()));

        if (goodsSubResponse.getIndividualDeliveryType() != null) {
            goodsEntity.setIndividualDeliveryType(EnumTypeUtil.getEnumFromValue(HTypeIndividualDeliveryType.class,
                                                                                goodsSubResponse.getIndividualDeliveryType()
                                                                               ));
        }
        if (goodsSubResponse.getFreeDeliveryFlag() != null) {
            goodsEntity.setFreeDeliveryFlag(EnumTypeUtil.getEnumFromValue(HTypeFreeDeliveryFlag.class,
                                                                          goodsSubResponse.getFreeDeliveryFlag()
                                                                         ));
        }
        if (goodsSubResponse.getUnitManagementFlag() != null) {
            goodsEntity.setUnitManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class,
                                                                            goodsSubResponse.getUnitManagementFlag()
                                                                           ));
        }
        goodsEntity.setUnitValue1(goodsSubResponse.getUnitValue1());
        goodsEntity.setUnitValue2(goodsSubResponse.getUnitValue2());
        if (goodsSubResponse.getStockManagementFlag() != null) {
            goodsEntity.setStockManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeStockManagementFlag.class,
                                                                             goodsSubResponse.getStockManagementFlag()
                                                                            ));
        }
        goodsEntity.setPurchasedMax(goodsSubResponse.getPurchasedMax());
        goodsEntity.setOrderDisplay(goodsSubResponse.getOrderDisplay());
        goodsEntity.setVersionNo(goodsSubResponse.getVersionNo());
        goodsEntity.setRegistTime(conversionUtility.toTimeStamp(goodsSubResponse.getRegistTime()));
        goodsEntity.setUpdateTime(conversionUtility.toTimeStamp(goodsSubResponse.getUpdateTime()));
        goodsEntity.setCatalogCode(goodsSubResponse.getCatalogCode());
        goodsEntity.setGoodsPrice(goodsSubResponse.getGoodsPrice());
        goodsEntity.setPreDiscountPrice(goodsSubResponse.getPreDiscountPrice());
        if (goodsSubResponse.getReserveFlag() != null) {
            goodsEntity.setReserveFlag(
                            EnumTypeUtil.getEnumFromValue(HTypeReserveFlag.class, goodsSubResponse.getReserveFlag()));
        }
        goodsEntity.setUnit(goodsSubResponse.getUnit());
        if (goodsSubResponse.getPriceMarkDispFlag() != null) {
            goodsEntity.setPriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypePriceMarkDispFlag.class,
                                                                           goodsSubResponse.getPriceMarkDispFlag()
                                                                          ));
        }
        if (goodsSubResponse.getSalePriceMarkDispFlag() != null) {
            goodsEntity.setSalePriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceMarkDispFlag.class,
                                                                               goodsSubResponse.getSalePriceMarkDispFlag()
                                                                              ));
        }
        goodsEntity.setGoodsManagementCode(goodsSubResponse.getGoodsManagementCode());
        goodsEntity.setGoodsDivisionCode(goodsSubResponse.getGoodsDivisionCode());
        goodsEntity.setGoodsCategory1(goodsSubResponse.getGoodsCategory1());
        goodsEntity.setGoodsCategory2(goodsSubResponse.getGoodsCategory2());
        goodsEntity.setGoodsCategory3(goodsSubResponse.getGoodsCategory3());
        if (goodsSubResponse.getLandSendFlag() != null) {
            goodsEntity.setLandSendFlag(
                            EnumTypeUtil.getEnumFromValue(HTypeLandSendFlag.class, goodsSubResponse.getLandSendFlag()));
        }
        if (goodsSubResponse.getCoolSendFlag() != null) {
            goodsEntity.setCoolSendFlag(
                            EnumTypeUtil.getEnumFromValue(HTypeCoolSendFlag.class, goodsSubResponse.getCoolSendFlag()));
        }
        goodsEntity.setCoolSendFrom(goodsSubResponse.getCoolSendFrom());
        goodsEntity.setCoolSendTo(goodsSubResponse.getCoolSendTo());
        if (goodsSubResponse.getSalePriceIntegrityFlag() != null) {
            goodsEntity.setSalePriceIntegrityFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceIntegrityFlag.class,
                                                                                goodsSubResponse.getSalePriceIntegrityFlag()
                                                                               ));
        }
        if (goodsSubResponse.getEmotionPriceType() != null) {
            goodsEntity.setEmotionPriceType(EnumTypeUtil.getEnumFromValue(HTypeEmotionPriceType.class,
                                                                          goodsSubResponse.getEmotionPriceType()
                                                                         ));
        }

        return goodsEntity;
    }

    /**
     * 在庫Dtoクラススに変換
     *
     * @param stockResponse 商品グループ検索条件
     * @return 在庫DTO
     */
    public StockDto toStockDto(StockDtoResponse stockResponse) {

        if (ObjectUtils.isEmpty(stockResponse)) {
            return null;
        }

        StockDto stockDto = new StockDto();

        stockDto.setGoodsSeq(stockResponse.getGoodsSeq());
        stockDto.setShopSeq(1001);
        stockDto.setSalesPossibleStock(stockResponse.getSalesPossibleStock());
        stockDto.setRealStock(stockResponse.getRealStock());
        stockDto.setOrderReserveStock(stockResponse.getOrderReserveStock());
        stockDto.setRemainderFewStock(stockResponse.getRemainderFewStock());
        stockDto.setSupplementCount(stockResponse.getSupplementCount());
        stockDto.setOrderPointStock(stockResponse.getOrderPointStock());
        stockDto.setSafetyStock(stockResponse.getSafetyStock());
        stockDto.setRegistTime(conversionUtility.toTimeStamp(stockResponse.getRegistTime()));
        stockDto.setUpdateTime(conversionUtility.toTimeStamp(stockResponse.getUpdateTime()));

        return stockDto;
    }

    /**
     * 商品グループ画像クラスリストに変換
     *
     * @param goodsGroupImageResponseList 商品グループ画像クラスリスト
     * @return 商品グループ画像クラスリスト
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
     * 商品グループ在庫表示クラススに変換
     *
     * @param stockStatusDisplayResponse 商品グループ在庫表示クラス
     * @return 在庫状態表示
     */
    public StockStatusDisplayEntity toStockStatusDisplayEntity(StockStatusDisplayEntityResponse stockStatusDisplayResponse) {

        if (ObjectUtils.isEmpty(stockStatusDisplayResponse)) {
            return null;
        }

        StockStatusDisplayEntity stockStatusDisplayEntity = new StockStatusDisplayEntity();

        stockStatusDisplayEntity.setGoodsGroupSeq(stockStatusDisplayResponse.getGoodsGroupSeq());
        if (stockStatusDisplayResponse.getStockStatus() != null) {
            stockStatusDisplayEntity.setStockStatusPc(EnumTypeUtil.getEnumFromValue(HTypeStockStatusType.class,
                                                                                    stockStatusDisplayResponse.getStockStatus()
                                                                                   ));
        }
        stockStatusDisplayEntity.setRegistTime(
                        conversionUtility.toTimeStamp(stockStatusDisplayResponse.getRegistTime()));
        stockStatusDisplayEntity.setUpdateTime(
                        conversionUtility.toTimeStamp(stockStatusDisplayResponse.getUpdateTime()));

        return stockStatusDisplayEntity;
    }

    /**
     * カテゴリ登録商品クラスリストに変換
     *
     * @param categoryGoodsResponseList 商品レスポンスクラスリスト
     * @return カテゴリ登録商品クラスリスト
     */
    public List<CategoryGoodsEntity> toCategoryGoodsEntityList(List<CategoryGoodsEntityResponse> categoryGoodsResponseList) {

        if (CollectionUtil.isEmpty(categoryGoodsResponseList)) {
            return new ArrayList<>();
        }

        List<CategoryGoodsEntity> categoryGoodsEntityList = new ArrayList<>();

        categoryGoodsResponseList.forEach(categoryGoodsResponse -> {
            CategoryGoodsEntity categoryGoodsEntity = new CategoryGoodsEntity();

            categoryGoodsEntity.setCategorySeq(categoryGoodsResponse.getCategorySeq());
            categoryGoodsEntity.setGoodsGroupSeq(categoryGoodsResponse.getGoodsGroupSeq());
            categoryGoodsEntity.setOrderDisplay(categoryGoodsResponse.getOrderDisplay());
            categoryGoodsEntity.setRegistTime(conversionUtility.toTimeStamp(categoryGoodsResponse.getRegistTime()));
            categoryGoodsEntity.setUpdateTime(conversionUtility.toTimeStamp(categoryGoodsResponse.getUpdateTime()));

            categoryGoodsEntityList.add(categoryGoodsEntity);
        });

        return categoryGoodsEntityList;
    }

    public CategoryDto toCategoryDto(CategoryTreeNodeResponse categoryTreeNodeResponse) {
        if (categoryTreeNodeResponse == null) {
            return null;
        }

        // Convert CategoryEntity
        CategoryEntityResponse categoryEntityResponse = categoryTreeNodeResponse.getCategoryEntity();
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategorySeq(categoryEntityResponse.getCategorySeq());
        categoryEntity.setShopSeq(1001);
        categoryEntity.setCategoryId(categoryEntityResponse.getCategoryId());
        categoryEntity.setCategoryName(categoryEntityResponse.getCategoryName());
        categoryEntity.setCategoryOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class,
                                                                             categoryEntityResponse.getCategoryOpenStatus()
                                                                            ));
        categoryEntity.setCategoryOpenStartTimePC(
                        conversionUtility.toTimeStamp(categoryEntityResponse.getCategoryOpenStartTime()));
        categoryEntity.setCategoryOpenEndTimePC(
                        conversionUtility.toTimeStamp(categoryEntityResponse.getCategoryOpenEndTime()));
        categoryEntity.setCategoryType(EnumTypeUtil.getEnumFromValue(HTypeCategoryType.class,
                                                                     categoryEntityResponse.getCategoryType()
                                                                    ));
        categoryEntity.setCategorySeqPath(categoryEntityResponse.getCategorySeqPath());
        categoryEntity.setOrderDisplay(categoryEntityResponse.getOrderDisplay());
        categoryEntity.setRootCategorySeq(categoryEntityResponse.getRootCategorySeq());
        categoryEntity.setCategoryLevel(categoryEntityResponse.getCategoryLevel());
        categoryEntity.setCategoryPath(categoryEntityResponse.getCategoryPath());
        categoryEntity.setManualDisplayFlag(EnumTypeUtil.getEnumFromValue(HTypeManualDisplayFlag.class,
                                                                          categoryEntityResponse.getManualDisplayFlag()
                                                                         ));
        categoryEntity.setVersionNo(categoryEntityResponse.getVersionNo());
        categoryEntity.setRegistTime(conversionUtility.toTimeStamp(categoryEntityResponse.getRegistTime()));
        categoryEntity.setUpdateTime(conversionUtility.toTimeStamp(categoryEntityResponse.getUpdateTime()));
        categoryEntity.setSiteMapFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeSiteMapFlag.class, categoryEntityResponse.getSiteMapFlag()));
        categoryEntity.setOpenGoodsCountPC(categoryEntityResponse.getOpenGoodsCount());

        // Convert CategoryDisplayEntity
        CategoryDisplayEntityResponse categoryDisplayEntityResponse =
                        categoryTreeNodeResponse.getCategoryDisplayEntity();

        CategoryDisplayEntity categoryDisplayEntity = new CategoryDisplayEntity();
        categoryDisplayEntity.setCategorySeq(categoryDisplayEntityResponse.getCategorySeq());
        categoryDisplayEntity.setCategoryNamePC(categoryDisplayEntityResponse.getCategoryName());
        categoryDisplayEntity.setCategoryNotePC(categoryDisplayEntityResponse.getCategoryNote());
        categoryDisplayEntity.setFreeTextPC(categoryDisplayEntityResponse.getFreeText());
        categoryDisplayEntity.setMetaDescription(categoryDisplayEntityResponse.getMetaDescription());
        categoryDisplayEntity.setMetaKeyword(categoryDisplayEntityResponse.getMetaKeyword());
        categoryDisplayEntity.setCategoryImagePC(categoryDisplayEntityResponse.getCategoryImage());
        categoryDisplayEntity.setRegistTime(
                        conversionUtility.toTimeStamp(categoryDisplayEntityResponse.getRegistTime()));
        categoryDisplayEntity.setUpdateTime(
                        conversionUtility.toTimeStamp(categoryDisplayEntityResponse.getUpdateTime()));

        // Convert CategoryDtoList
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        List<CategoryTreeNodeResponse> categoryTreeNodeResponseList = categoryTreeNodeResponse.getCategoryDtoList();

        if (categoryTreeNodeResponseList != null) {
            for (CategoryTreeNodeResponse treeNodeResponse : categoryTreeNodeResponseList) {
                CategoryDto categoryDto = toCategoryDto(treeNodeResponse);
                categoryDtoList.add(categoryDto);
            }
        }

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryEntity(categoryEntity);
        categoryDto.setCategoryDisplayEntity(categoryDisplayEntity);
        categoryDto.setCategoryDtoList(categoryDtoList);

        return categoryDto;
    }

    // 2023-renew No36-1, No61,67,95 from here

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
