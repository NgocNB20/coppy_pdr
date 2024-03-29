/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.web;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

/**
 * ページ情報クラス
 *
 * @author Pham Quang Dieu
 */
@Getter
@Component
@Scope("prototype")
public class PageInfo implements Serializable {

    // ----------------------------
    // 定数
    // ----------------------------
    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * デフォルトページ番号表示数
     */
    private static final int DEFAULT_PAGE_LINK_COUNT = 10;

    /**
     * デフォルト最大表示件数
     */
    public static final int DEFAULT_LIMIT = 10;

    /**
     * デフォルトページ番号
     */
    public static final int DEFAULT_PNUM = 1;

    /**
     * 属性名 キー
     */
    public static final String ATTRIBUTE_NAME_KEY = "pageInfo";

    /**
     * 表示形式：サムネイル表示 キー
     */
    public static final String VIEW_TYPE_THUMBNAIL_KEY = "thumbs";
    /**
     * 表示形式：リスト表示 キー
     */
    public static final String VIEW_TYPE_LIST_KEY = "list";
    /**
     * 並び順タイプ：標準 キー
     */
    public static final String SORT_TYPE_NORMAL_KEY = "normal";
    /**
     * 並び順タイプ：新着順 キー
     */
    public static final String SORT_TYPE_REGISTDATE_KEY = "new";
    /**
     * 並び順タイプ：価格順 キー
     */
    public static final String SORT_TYPE_GOODSPRICE_KEY = "price";
    /**
     * 並び順タイプ：売れ筋順 キー
     */
    public static final String SORT_TYPE_SALECOUNT_KEY = "salableness";
    /**
     * 昇降順：昇順 キー
     */
    public static final String ASC_TYPE_TRUE_KEY = "true";
    /**
     * 昇降順：降順 キー
     */
    public static final String ASC_TYPE_FALSE_KEY = "false";
    // 2023-renew No36-1, No61,67,95 from here
    /**
     * UK並び順タイプ：おすすめ順 キー
     */
    public static final String UK_SORT_TYPE_RECOMMEND_KEY = "";
    /**
     * UK並び順タイプ：カタログID順 キー
     */
    public static final String UK_SORT_TYPE_CATALOGID_KEY = "catalog_id asc";
    /**
     * UK並び順タイプ：新着順（ユニサーチ） キー
     */
    public static final String UK_SORT_TYPE_NEWDATE_KEY = "new_date desc";
    /**
     * UK並び順タイプ：価格（安い順） キー
     */
    public static final String UK_SORT_TYPE_SALEMINPRICE_KEY = "sale_min_price asc";
    /**
     * UK並び順タイプ：価格（高い順） キー
     */
    public static final String UK_SORT_TYPE_SALEMAXPRICE_KEY = "sale_max_price desc";
    // 2023-renew No36-1, No61,67,95 to here

    // ----------------------------
    // 内部処理で設定されるフィールド
    // ----------------------------
    /**
     * 前ページを表示するか ※pager.htmlで使用
     */
    private boolean isDisplayPrev;

    /**
     * 次ページを表示するか ※pager.htmlで使用
     */
    private boolean isDisplayNext;

    /**
     * 開始ページ番号 ※pager.htmlで使用
     */
    private Integer startPageNo;

    /**
     * 終了ページ番号 ※pager.htmlで使用
     */
    private Integer endPageNo;

    // ----------------------------
    // レスポンスから設定されるフィールド
    // ----------------------------
    /**
     * 現在のページ番号 ※pager.htmlで使用
     */
    private Integer pnum;

    /**
     * 表示最大件数
     */
    private Integer limit;

    /**
     * 次のページ番号
     **/
    private Integer nextPage;

    /**
     * 前のページ番号
     **/
    private Integer prevPage;

    /**
     * 全件数 ※pager.htmlで使用
     **/
    private Integer total;

    /**
     * ページ数 ※pager.htmlで使用
     */
    private Integer totalPages;

    /**
     * 並替項目
     */
    private String orderField;

    /**
     * 並替昇順フラグ
     */
    private boolean orderAsc;

    /**
     * サムネイルかリストか
     *
     * @see PageInfo#
     * @see PageInfo#VIEW_TYPE_LIST_KEY
     */
    private String vtype;

    /**
     * 並替項目マップ
     * <p>
     * 　KEY:stype
     * 　VALUE:orderField
     * のマップ
     * <p>
     * ※並替項目について、フロント商品検索などでGETパラメータ指定されることがあるが、
     * 　できるだけ短い文字列でパラメータ指定したいという要件がある
     * 　（逆に言うと、orderFieldで指定される文字列はSQLを意識しているため、文字数が少々長い。。）
     * これを解消するために、本Mapをもたせ
     * SQL実行前、画面ページャ取得前のタイミングでKEY⇔VALUEの変換を行う
     */
    private Map<String, String> stypeMap;

    // 2023-renew No36-1, No61,67,95 from here
    /**
     * UK並び順
     */
    private String sort;
    // 2023-renew No36-1, No61,67,95 to here

    /**
     * ソートタイプを取得
     * ※ページャHTMLから、${pageInfo.stype}で該当項目を取得できるようにするための対処
     * orderFieldに設定する文字列はDB項目名であるため、文字数が長い
     * ⇒ソート用のGetパラメータからは省略文字列を使いたいので、このstypeを追加する
     *
     * @return ソートタイプ（ソート条件）
     */
    public String getStype() {
        // stypeMapをループ
        if (stypeMap != null) {
            for (String stype : stypeMap.keySet()) {
                String orderField = stypeMap.get(stype);

                // SQLで使われたorderFieldと一致するか判定
                if (StringUtils.equals(orderField, this.orderField)) {
                    return stype;
                }
            }
        }
        // Mapに無ければnull返却
        return null;
    }

    /**
     * ページャ表示用にPageInfoのセットアップを行う
     */
    public void setupViewPager() {

        this.isDisplayPrev = false;
        this.isDisplayNext = false;

        // 集計件数を取得
        // ページ番号が1より大きく、前ページ番号が存在すれば
        if (this.pnum > 1 && this.prevPage != null) {
            // 前ページを表示
            this.isDisplayPrev = true;
        }

        // ページ数が1より大きく、ページ番号と一致しない場合
        if (this.totalPages > 1 && !this.pnum.equals(this.totalPages)) {
            // 次ページを表示
            this.isDisplayNext = true;
        }

        // 開始、終了ページの番号を取得する
        int[] startEndPageNumberArray =
                        getStartEndPageNumberArray(pnum, this.total, this.limit, PageInfo.DEFAULT_PAGE_LINK_COUNT,
                                                   totalPages
                                                  );
        this.startPageNo = startEndPageNumberArray[0];
        this.endPageNo = startEndPageNumberArray[1];
    }

    /**
     * ページャ表示用にPageInfoのセットアップを行う
     *
     * @param pageLinkCount ページ番号表示数
     */
    public void setupViewPager(int pageLinkCount) {

        this.isDisplayPrev = false;
        this.isDisplayNext = false;

        // 集計件数を取得
        // ページ番号が1より大きく、前ページ番号が存在すれば
        if (this.pnum > 1 && this.prevPage != null) {
            // 前ページを表示
            this.isDisplayPrev = true;
        }

        // ページ数が1より大きく、ページ番号と一致しない場合
        if (this.totalPages > 1 && !this.pnum.equals(this.totalPages)) {
            // 次ページを表示
            this.isDisplayNext = true;
        }

        // 開始、終了ページの番号を取得する
        int[] startEndPageNumberArray =
                        getStartEndPageNumberArray(pnum, this.total, this.limit, pageLinkCount, totalPages);
        this.startPageNo = startEndPageNumberArray[0];
        this.endPageNo = startEndPageNumberArray[1];
    }

    /**
     * 開始、終了ページの番号を返すメソッド
     *
     * @param pnum          現在のページ番号
     * @param total         集計件数
     * @param limit         最大表示件数
     * @param pageLinkCount ページ番号表示数
     * @param lastPageNo    最終ページ番号
     * @return 開始、終了ページ番号の配列
     */
    private int[] getStartEndPageNumberArray(int pnum, int total, int limit, int pageLinkCount, int lastPageNo) {

        // 初期ページ番号
        int firstPageNo = 1;
        // ページ番号表示数の半分
        int pageLinkCountHalf = pageLinkCount / 2;
        // 前ページ表示数
        int prevPageCount = pnum - pageLinkCountHalf;
        // 次ページ表示数
        int nextPageCount = pnum + pageLinkCountHalf;
        // 偶数の場合 次ページ表示数=次ページ表示数-1 をする
        if (pageLinkCount % 2 == 0) {
            nextPageCount = nextPageCount - 1;
        }

        // 開始ページ番号
        int startPageNo = 0;
        // 終了ページ番号
        int endPageNo = 0;

        // 最終ページ番号が、ページ表示件数以下の場合
        if (lastPageNo <= pageLinkCount) {
            startPageNo = firstPageNo;
            endPageNo = lastPageNo;
            // 最終ページ番号が、ページ表示件数より多い場合
        } else {
            if (prevPageCount <= firstPageNo) {
                startPageNo = firstPageNo;
                endPageNo = pageLinkCount;
            } else {
                if (nextPageCount > lastPageNo) {
                    startPageNo = lastPageNo - pageLinkCount + 1;
                    endPageNo = lastPageNo;
                } else {
                    startPageNo = prevPageCount;
                    endPageNo = nextPageCount;
                }
            }
        }

        int startEndPageNumberArray[] = {startPageNo, endPageNo};
        return startEndPageNumberArray;
    }

    // ----------------------------
    // Setter系メソッド
    // ----------------------------

    /**
     * 現在のページ番号の設定
     *
     * @param pnum 現在のページ番号
     */
    public void setPnum(String pnum) {
        if (pnum != null) {
            this.pnum = Integer.parseInt(pnum);
        }
    }

    /**
     * 現在のページ番号の設定
     *
     * @param pnum 現在のページ番号
     */
    public void setPnum(Integer pnum) {
        this.pnum = pnum;
    }

    /**
     * 最大表示件数の設定
     *
     * @param limit 最大表示件数
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * 次のページ番号
     *
     * @param nextPage 次のページ番号
     */
    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    /**
     * 前のページ番号
     *
     * @param prevPage 前のページ番号
     */
    public void setPrevPage(Integer prevPage) {
        this.prevPage = prevPage;
    }

    /**
     * 全件数
     *
     * @param total 全件数
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * ページ数
     *
     * @param totalPages ページ数
     */
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * 並替条件マップの設定
     *
     * @param stypeMap 並替条件マップ
     */
    public void setStypeMap(Map<String, String> stypeMap) {
        this.stypeMap = stypeMap;
    }

    /**
     * 並替条件の設定
     *
     * @param field フィールド
     * @param asc   昇順フラグ
     */
    public void setOrder(String field, boolean asc) {
        this.orderField = field;
        this.orderAsc = asc;
    }

    /**
     * 並替項目の設定
     *
     * @param orderField 並替項目
     */
    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    /**
     * 並替昇順フラグの設定
     *
     * @param orderAsc 並替昇順フラグ
     */
    public void setOrderAsc(boolean orderAsc) {
        this.orderAsc = orderAsc;
    }

    /**
     * 画像表示形式の設定
     *
     * @param vtype 画像表示形式
     */
    public void setVtype(String vtype) {
        this.vtype = vtype;
    }

    // 2023-renew No36-1, No61,67,95 from here

    /**
     * UK並び順の設定<br/>
     *
     * @param sort UK並び順
     */
    public void setSort(String sort) {
        this.sort = sort;
    }
    // 2023-renew No36-1, No61,67,95 to here

    /**
     * 昇順降順フラグを取得
     * ※ページャHTMLから、${pageInfo.asc}で該当項目を取得できるようにするための対処
     *
     * @return
     */
    public boolean isAsc() {
        return this.orderAsc;
    }

    /**
     * サムネイル表示か判定
     *
     * @return true..サムネイル表示
     */
    public boolean isViewTypeThumbnail() {
        return VIEW_TYPE_THUMBNAIL_KEY.equals(this.vtype);
    }

    /**
     * リスト表示か判定
     *
     * @return true..リスト表示
     */
    public boolean isViewTypeList() {
        return VIEW_TYPE_LIST_KEY.equals(this.vtype);
    }

    /**
     * 並び順タイプが標準順か判定
     *
     * @return true..標準順
     */
    public boolean isSortTypeNormal() {
        return SORT_TYPE_NORMAL_KEY.equals(getStype());
    }

    /**
     * 並び順タイプが新着順か判定
     *
     * @return true..新着順
     */
    public boolean isSortTypeNew() {
        return SORT_TYPE_REGISTDATE_KEY.equals(getStype());
    }

    /**
     * 並び順タイプが価格順か判定
     *
     * @return true..価格順
     */
    public boolean isSortTypePrice() {
        return SORT_TYPE_GOODSPRICE_KEY.equals(getStype());
    }

    /**
     * 並び順タイプが売れ筋順か判定
     *
     * @return true..売れ筋順
     */
    public boolean isSortTypeSalableness() {
        return SORT_TYPE_SALECOUNT_KEY.equals(getStype());
    }

    // 2023-renew No36-1, No61,67,95 from here

    /**
     * 並び順タイプがUKおすすめ順か判定
     *
     * @return true..おすすめ順
     */
    public boolean isUkSortTypeRecommend() {
        return UK_SORT_TYPE_RECOMMEND_KEY.equals(getSort());
    }

    /**
     * 並び順タイプがUKカタログ掲載順か判定
     *
     * @return true..カタログ掲載順
     */
    public boolean isUkSortTypeCatalogId() {
        return UK_SORT_TYPE_CATALOGID_KEY.equals(getSort());
    }

    /**
     * 並び順タイプがUK新着順か判定
     *
     * @return true..新着順
     */
    public boolean isUkSortTypeNewDate() {
        return UK_SORT_TYPE_NEWDATE_KEY.equals(getSort());
    }

    /**
     * 並び順タイプがUK価格（安い順）か判定
     *
     * @return true..価格（安い順）
     */
    public boolean isUkSortTypeSaleMinPrice() {
        return UK_SORT_TYPE_SALEMINPRICE_KEY.equals(getSort());
    }

    /**
     * 並び順タイプがUk価格（高い順）か判定
     *
     * @return true..価格（高い順）
     */
    public boolean isUkSortTypeSaleMaxPrice() {
        return UK_SORT_TYPE_SALEMAXPRICE_KEY.equals(getSort());
    }
    // 2023-renew No36-1, No61,67,95 to here
}
