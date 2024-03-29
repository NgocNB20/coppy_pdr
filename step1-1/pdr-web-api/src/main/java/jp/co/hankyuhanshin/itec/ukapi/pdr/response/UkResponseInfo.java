package jp.co.hankyuhanshin.itec.ukapi.pdr.response;

import jp.co.hankyuhanshin.itec.ukapi.pdr.response.rword.GetUkRWordInfoWord;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestCampaign;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestCategory;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestHistory;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestKeyword;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestModel;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestProduct;
import net.arnx.jsonic.JSONHint;

import java.util.List;

/**
 * UK-API連携 レスポンスモデル<br/>
 * @author tt32117
 */
public class UkResponseInfo {

    /** numFound */
    @JSONHint(ordinal = 10, name = "numFound")
    private int numFound;

    /** 関連ワードDto */
    @JSONHint(ordinal = 20, name = "relatedWord")
    private GetUkRWordInfoWord relatedWord;

    /** 検索結果ページ数 */
    @JSONHint(ordinal = 30, name = "page")
    private String page;

    /** 検索結果ドキュメント */
    @JSONHint(ordinal = 40, name = "docs")
    private List<Object> docs;

    /** keyword */
    @JSONHint(ordinal = 50, name = "keyword")
    GetUkUniSuggestKeyword keyword;

    /** category */
    @JSONHint(ordinal = 60, name = "category")
    GetUkUniSuggestCategory category;

    /** campaign */
    @JSONHint(ordinal = 65, name = "campaign")
    GetUkUniSuggestCampaign campaign;

    /** modelnumber */
    @JSONHint(ordinal = 70, name = "modelnumber")
    GetUkUniSuggestModel modelnumber;

    /** productname */
    @JSONHint(ordinal = 80, name = "productname")
    GetUkUniSuggestProduct productname;

    /** goods_history */
    @JSONHint(ordinal = 90, name = "history")
    GetUkUniSuggestHistory history;

    /**
     * @return the numFound
     */
    public int getNumFound() {
        return numFound;
    }

    /**
     * @param numFound the numFound to set
     */
    public void setNumFound(int numFound) {
        this.numFound = numFound;
    }

    /**
     * @return the relatedWord
     */
    public GetUkRWordInfoWord getRelatedWord() {
        return relatedWord;
    }

    /**
     * @param relatedWord the relatedWord to set
     */
    public void setRelatedWord(GetUkRWordInfoWord relatedWord) {
        this.relatedWord = relatedWord;
    }

    /**
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * @return the docs
     */
    public List<Object> getDocs() {
        return docs;
    }

    /**
     * @param docs the docs to set
     */
    public void setDocs(List<Object> docs) {
        this.docs = docs;
    }

    /**
     * @return the keyword
     */
    public GetUkUniSuggestKeyword getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(GetUkUniSuggestKeyword keyword) {
        this.keyword = keyword;
    }

    /**
     * @return the category
     */
    public GetUkUniSuggestCategory getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(GetUkUniSuggestCategory category) {
        this.category = category;
    }

    /**
     * @return the campaign
     */
    public GetUkUniSuggestCampaign getCampaign() {
        return campaign;
    }

    /**
     * @param campaign the campaign to set
     */
    public void setCampaign(GetUkUniSuggestCampaign campaign) {
        this.campaign = campaign;
    }

    /**
     * @return the modelnumber
     */
    public GetUkUniSuggestModel getModelnumber() {
        return modelnumber;
    }

    /**
     * @param modelnumber the modelnumber to set
     */
    public void setModelNumber(GetUkUniSuggestModel modelnumber) {
        this.modelnumber = modelnumber;
    }

    /**
     * @return the productName
     */
    public GetUkUniSuggestProduct getProductname() {
        return productname;
    }

    /**
     * @param productname the productname to set
     */
    public void setProductName(GetUkUniSuggestProduct productname) {
        this.productname = productname;
    }

    /**
     * @return the history
     */
    public GetUkUniSuggestHistory getHistory() {
        return history;
    }

    /**
     * @param history the history to set
     */
    public void setHistory(GetUkUniSuggestHistory history) {
        this.history = history;
    }
}
