package jp.co.hankyuhanshin.itec.ukapi.pdr.response.feedgoods;

public class GetUkUniSearchGoodsHeaderFallback {

    /** フォールバック種類 */
    private String type;
    /** フォールバック後のキーワード */
    private String keyword;

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
