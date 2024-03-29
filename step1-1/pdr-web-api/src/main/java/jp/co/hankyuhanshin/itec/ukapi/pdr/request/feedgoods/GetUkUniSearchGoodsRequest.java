package jp.co.hankyuhanshin.itec.ukapi.pdr.request.feedgoods;

import java.sql.Array;

/**
 * UK-API連携 ユニサーチ（商品API）のリクエストモデル<br/>
 * @author tk32120
 */

public class GetUkUniSearchGoodsRequest {

    /** カテゴリID */
    private String category;
    /** 検索キーワード */
    private Array kw;
    /** 検索結果ページ数 */
    private Integer page;
    /** 検索結果数 */
    private Integer rows;
    /** ソート条件 */
    private String sort;

    /**
     * @return the kw
     */
    public Array getKw() {
        return kw;
    }

    /**
     * @param kw the kw to set
     */
    public void setKw(Array kw) {
        this.kw = kw;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the fq_category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return the sort
     */
    public String getSort() {
        return sort;
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * @return the rows
     */
    public Integer getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
