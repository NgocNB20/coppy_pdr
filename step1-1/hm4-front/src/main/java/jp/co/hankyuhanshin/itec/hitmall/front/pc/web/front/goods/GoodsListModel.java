package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeManualDisplayFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalableGoodsType;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common.CategoryItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common.GoodsGroupItem;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.front.web.PageInfo;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 商品一覧画面 Model
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Data
public class GoodsListModel extends AbstractModel {

    /**
     * カテゴリIDが指定されていない<br/>
     * <code>MSGCD_CATEGORY_NOT_FOUND_ERROR</code>
     */
    public static final String MSGCD_CATEGORY_NOT_SELECT_ERROR = "AGX000101";

    /**
     * カテゴリが取得できない<br/>
     * <code>MSGCD_CATEGORY_NOT_FOUND_ERROR</code>
     */
    public static final String MSGCD_CATEGORY_NOT_FOUND_ERROR = "AGX000102";

    // 2023-renew No36-1, No61,67,95 from here
    /** 表示形式・並び順を変更して遷移した場合のパラメータ */
    public static final String FROM_VIEW_CHANGE_VIEW_KEY = "changeView";
    // 2023-renew No36-1, No61,67,95 to here

    /**
     * 商品一覧画面：１ページ当たりの商品表示件数
     */
    public static final int DEFAULT_GOODS_LIMIT = 50;

    /**
     * カテゴリID
     */
    private String cid;

    /**
     * カテゴリ画像PC
     */
    private String categoryImagePC;

    /**
     * meta-description
     */
    private String metaDescription;

    /**
     * meta-kewyord
     */
    private String metaKeyword;

    /**
     * カテゴリ表示名PC
     */
    private String categoryName;

    /**
     * 手動表示フラグ
     */
    private HTypeManualDisplayFlag manualDisplayFlag;

    /**
     * フリーテキストPC
     */
    private String freeTextPC;

    /**
     * カテゴリパスリスト（パンくず情報）
     */
    private List<CategoryItem> categoryPassItems;

    /**
     * 商品グループ（リスト）
     */
    private List<GoodsGroupItem> goodsGroupListItems;

    /**
     * サムネイル用ループリスト
     */
    private List<List<GoodsGroupItem>> goodsGroupThumbnailItemsItems;

    // PDR Migrate Customization from here

    private HTypeSalableGoodsType salableGoodsType;

    private boolean groupSalePriceIntegrityFlag;

    /**
     * SALEアイコンフラグ
     */
    private boolean saleIconFlag;

    /**
     * セールde予約アイコンフラグ
     */
    private boolean reserveIconFlag;

    /**
     * NEWアイコンフラグ
     */
    private boolean newIconFlag;

    // 2023-renew No36-1, No61,67,95 from here
    /**
     * アウトレットアイコンフラグ
     */
    private boolean outletIconFlag;
    // 2023-renew No36-1, No61,67,95 to here

    // 2023-renew No3-taglog from here

    /**
     * 商品検索結果（UK連携用）
     */
    private String ukGoodsSearchResult;

    /**
     * UK連携用カテゴリー
     */
    private String fqCategory;

    /**
     * ユニサーチ・リクエストID（商品用）
     */
    private String reqIdOfGoods;
    // 2023-renew No3-taglog to here

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

    /**
     * ダイナミックプロパティ：content属性<br/>
     * ※検索ロボットの制御切り替え用<br/>
     *
     * @return true:noindexを設定 / false:index,followを設定
     */
    public String getMetaRobotsContent() {
        return this.isNoGoods() ? "noindex" : "index,follow";
    }

    /**
     * ページネーションURL（prev）
     */
    private String pagePrevUrl;

    /**
     * ページネーションURL（next）
     */
    private String pageNextUrl;

    /**
     * ページネーション（prev） 表示可否<br/>
     *
     * @return true..表示する
     */
    public boolean isPaginationPrev() {
        if (StringUtils.isNotEmpty(pagePrevUrl)) {
            return true;
        }
        return false;
    }

    /**
     * ページネーション（next） 表示可否<br/>
     *
     * @return true..表示する
     */
    public boolean isPaginationNext() {
        if (StringUtils.isNotEmpty(pageNextUrl)) {
            return true;
        }
        return false;
    }

    /**
     * 前ページURL取得<br/>
     *
     * @return 前ページURL
     */
    public String getGoRelPrevHref() {
        return pagePrevUrl;
    }

    /**
     * 次ページURL取得<br/>
     *
     * @return 次ページURL
     */
    public String getGoRelNextHref() {
        return pageNextUrl;
    }

    /**
     * 検索結果０件チェック
     *
     * @return true:結果０件
     */
    // PDR Migrate Customization to here

    /**
     * 検索結果０件チェック
     *
     * @return true:結果０件
     */
    public boolean isNoGoods() {
        return CollectionUtil.isEmpty(goodsGroupListItems);
    }

    /**
     * 手動並び順指定可能フラグ<br/>
     *
     * @return true:手動並び順指定可能
     */
    public boolean isManualDisplay() {
        return HTypeManualDisplayFlag.ON.equals(manualDisplayFlag);
    }

    /**
     * カテゴリ画像（PC）存在チェック<br/>
     *
     * @return true:存在する
     */
    public boolean isViewCategoryImagePC() {
        return StringUtils.isNotEmpty(categoryImagePC);
    }

    /**
     * 総件数
     */
    private int totalCount;

    /**
     * ページ番号
     */
    private String pnum;

    /**
     * UKソート順
     */
    private String sort;

    /**
     * 並び順タイプがUKおすすめ順か判定
     *
     * @return true..おすすめ順
     */
    public boolean isUkSortTypeRecommend() {
        return PageInfo.UK_SORT_TYPE_RECOMMEND_KEY.equals(getSort()) || getSort() == null;
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

}
