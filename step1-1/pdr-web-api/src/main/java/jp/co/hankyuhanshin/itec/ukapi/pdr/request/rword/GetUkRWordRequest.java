package jp.co.hankyuhanshin.itec.ukapi.pdr.request.rword;

/**
 * UK-API連携 関連ワードAPIのリクエストモデル<br/>
 * @author tt32117
 */
public class GetUkRWordRequest {

    /** 検索キーワード */
    private String kw;
    /** 検索結果数 */
    private Integer rows;

    /**
     * @return the kw
     */
    public String getKw() {
        return kw;
    }

    /**
     * @param kw the kw to set
     */
    public void setKw(String kw) {
        this.kw = kw;
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
