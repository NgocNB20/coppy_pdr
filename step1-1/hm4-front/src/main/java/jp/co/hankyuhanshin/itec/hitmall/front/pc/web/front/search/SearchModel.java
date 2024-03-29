/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.search;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCNumber;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVNumber;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common.GoodsGroupItem;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.front.web.PageInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;

import java.util.List;
import java.util.Map;

/**
 * 商品検索画面 Model
 * // PDR Migrate Customization from here
 * PDR#4,10 カテゴリ画面のページクラス<br/>
 *
 * @author kato
 * // PDR Migrate Customization to here
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchModel extends AbstractModel {

    /**
     * 共通ヘッダからの検索
     */
    public static final String FROM_VIEW_HEADER_KEY = "header";

    /**
     * 検索画面からの検索
     */
    public static final String FROM_VIEW_SEARCH_KEY = "search";

    // PDR Migrate Customization from here
    /** 表示形式：サムネイル表示 キー */
    public static final String VIEW_TYPE_THUMBNAIL_KEY = "thumbs";

    /** 表示形式：リスト表示 キー */
    public static final String VIEW_TYPE_LIST_KEY = "list";

    /** 並び順：新着順 キー */
    public static final String SORT_TYPE_REGISTDATE_KEY = "new";
    /** 並び順：価格順 キー */
    public static final String SORT_TYPE_GOODSPRICE_KEY = "price";
    /** 並び順：売れ筋順 キー */
    public static final String SORT_TYPE_SALECOUNT_KEY = "salableness";

    /** 遷移元判定用文字列が格納される */
    private String fromView;
    // PDR Migrate Customization to here

    /**
     * 価格帯From
     */
    @HCNumber
    @HVNumber
    @Range(min = 0, max = 99999999)
    private String ll;

    /**
     * 価格帯To
     */
    @HCNumber
    @HVNumber
    @Range(min = 0, max = 99999999)
    private String ul;

    /**
     * キーワード
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE)
    @HVSpecialCharacter(allowPunctuation = true)
    private String keyword;

    /**
     * カテゴリID
     */
    @HVSpecialCharacter(allowPunctuation = true)
    private String condCid;

    /**
     * エンコード後キーワード
     */
    @HVSpecialCharacter(allowPunctuation = true)
    private String q;

    /**
     * 商品グループリスト
     */
    private List<GoodsGroupItem> goodsGroupListItems;

    /**
     * サムネイル用ループリスト
     */
    private List<List<GoodsGroupItem>> goodsGroupThumbnailItemsItems;

    /**
     * 総件数
     */
    private int totalCount;

    /**
     * カテゴリID(ヘッダで選択した情報)
     */
    private String headerSelectcategory;

    /**
     * カテゴリプルダウン
     */
    private List<Map<String, String>> condCidItems;

    /**
     * 在庫ありフラグ
     */
    private boolean st;

    /**
     * ページ番号
     */
    private String pnum;

    /**
     * 表示形式
     */
    private String vtype;

    /**
     * ソート形式
     */
    private String stype;

    /**
     * 昇順/降順フラグ
     */
    // PDR Migrate Customization from here
    private boolean asc = true;
    // PDR Migrate Customization to here

    /**
     * リスト最大表示件数
     */
    private int listLimit;

    /**
     * サムネイル最大表示件数
     */
    private int thumbnailLimit;

    /**
     * サムネイル表示：改行位置
     */
    private int col;

    // 2023-renew No36-1, No61,67,95 from here
    /**
     * UKソート順
     */
    private String sort;

    /**
     * 商品検索のフォールバック後キーワード
     */
    private String fallbackKeyword;

    /**
     * 商品検索のフォールバックで削除されたキーワード
     */
    private String trimKeyword;

    /**
     * コンテンツリスト
     */
    private List<ContentsItem> contentsListItems;

    /**
     * コンテンツ検索のフォールバック後キーワード
     */
    private String contentsFallbackKeyword;

    /**
     * コンテンツ検索のフォールバックで削除されたキーワード
     */
    private String contentsTrimKeyword;

    /**
     * 関連ワードリスト
     */
    private List<String> relatedWordList;
    // 2023-renew No36-1, No61,67,95 to here
    // 2023-renew No3-taglog from here
    /**
     * 商品検索結果（UK連携用）
     */
    private String ukGoodsSearchResult;

    /**
     * コンテンツ検索結果（UK連携用）
     */
    private String ukContentsSearchResult;

    /**
     * ユニサーチ・リクエストID（商品用）
     */
    private String reqIdOfGoods;

    /**
     * ユニサーチ・リクエストID（コンテンツ用）
     */
    private String reqIdOfContents;
    // 2023-renew No3-taglog to here

    /**
     * コンテンツ検索の総件数
     */
    private int contentsSearchTotalCount;

    // PDR Migrate Customization from here
    /**
     * @see jp.co.hankyuhanshin.itec.hitmall.front.pc.pdr.web.front.search.IndexPdrPageItem#saleIconFlag
     */
    private boolean saleIconFlag;

    /**
     * @see jp.co.hankyuhanshin.itec.hitmall.front.pc.pdr.web.front.search.IndexPdrPageItem#reserveIconFlag
     */
    private boolean reserveIconFlag;

    /**
     * @see jp.co.hankyuhanshin.itec.hitmall.front.pc.pdr.web.front.search.IndexPdrPageItem#newIconFlag
     */
    private boolean newIconFlag;

    /**
     * SALEアイコン画像のパスを取得する<br/>
     *
     * @return SALEアイコン画像のパス
     */
    public String getNewIconSrc() {
        return PropertiesUtil.getSystemPropertiesValue("images.icon.path.new");
    }

    /**
     * 新着アイコン画像のパスを取得する<br/>
     *
     * @return 新着アイコン画像のパス
     */
    public String getSaleIconSrc() {
        return PropertiesUtil.getSystemPropertiesValue("images.icon.path.sale");
    }

    /**
     * 取りおき可能アイコンのパスを取得する<br/>
     *
     * @return 取りおき可能アイコンのパス
     */
    public String getReserveIconSrc() {
        return PropertiesUtil.getSystemPropertiesValue("images.icon.path.reserve");
    }
    // PDR Migrate Customization to here

    /**
     * 商品検索結果０件チェック
     *
     * @return true:結果０件
     */
    public boolean isNoGoods() {
        return CollectionUtil.isEmpty(goodsGroupListItems);
    }

    // PDR Migrate Customization from here

    /**
     * 表示形式チェック（サムネイル表示）
     *
     * @return true:サムネイル表示
     */
    public boolean isViewTypeThumbnail() {
        return this.vtype == null || this.vtype.equals(VIEW_TYPE_THUMBNAIL_KEY);
    }

    /**
     * 表示形式チェック（リスト表示）
     *
     * @return true:リスト表示
     */
    public boolean isViewTypeList() {
        return this.vtype != null && this.vtype.equals(VIEW_TYPE_LIST_KEY);
    }

    // PDR Migrate Customization to here

    // 2023-renew No36-1, No61,67,95 from here

    /**
     * 検索キーワードチェック
     *
     * @return ture:検索キーワードあり
     */
    public boolean isKeyword() {
        return !StringUtils.isEmpty(this.keyword);
    }

    /**
     * バックフォワードチェック（商品）
     *
     * @return true:バックフォワードあり
     */
    public boolean isFallbackKeyword() {
        return !StringUtils.isEmpty(this.fallbackKeyword);
    }
    // 2023-renew No36-1, No61,67,95 from here

    /**
     * コンテンツ検索結果０件チェック
     *
     * @return true:結果０件
     */
    public boolean isNoContents() {
        return CollectionUtil.isEmpty(contentsListItems);
    }

    /**
     * バックフォワードチェック（コンテンツ）
     *
     * @return true:バックフォワードあり
     */
    public boolean isContentsFallbackKeyword() {
        return !StringUtils.isEmpty(this.contentsFallbackKeyword);
    }

    /**
     * 並び順タイプがUKおすすめ順か判定
     *
     * @return true..おすすめ順
     */
    public boolean isUkSortTypeRecommend() {
        return PageInfo.UK_SORT_TYPE_RECOMMEND_KEY.equals(getSort());
    }

    /**
     * 並び順タイプがUKカタログ掲載順か判定
     *
     * @return true..カタログ掲載順
     */
    public boolean isUkSortTypeCatalogId() {
        return PageInfo.UK_SORT_TYPE_CATALOGID_KEY.equals(getSort());
    }

    /**
     * 並び順タイプがUK新着順か判定
     *
     * @return true..新着順
     */
    public boolean isUkSortTypeNewDate() {
        return PageInfo.UK_SORT_TYPE_NEWDATE_KEY.equals(getSort());
    }
    // 2023-renew No36-1, No61,67,95 to here
}
