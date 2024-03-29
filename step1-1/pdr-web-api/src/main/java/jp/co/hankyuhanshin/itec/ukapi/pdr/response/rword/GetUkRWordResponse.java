package jp.co.hankyuhanshin.itec.ukapi.pdr.response.rword;

import jp.co.hankyuhanshin.itec.ukapi.pdr.response.UkResponseInfo;

public class GetUkRWordResponse {

    /** 関連ワード */
    private String word;

    /**
     * @return the word
     */
    public String getWord() {
        return word;
    }

    /**
     * @param word the word to set
     */
    public void setWord(String word) {
        this.word = word;
    }
}
