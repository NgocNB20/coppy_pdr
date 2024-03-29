package jp.co.hankyuhanshin.itec.ukapi.pdr.response.feedcontents;

public class GetUkUniSearchContentsResponse {

    /** コンテンツID */
    private String content_id;

    /** コンテンツ名 */
    private String content_name;

    /** 遷移先URL */
    private String transition_url;

    /** コンテンツ画像URL */
    private String content_image_url;

    /**
     * @return the content_id
     */
    public String getContent_id() {
        return content_id;
    }

    /**
     * @param content_id the content_id to set
     */
    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    /**
     * @return the content_name
     */
    public String getContent_name() {
        return content_name;
    }

    /**
     * @param content_name the content_name to set
     */
    public void setContent_name(String content_name) {
        this.content_name = content_name;
    }

    /**
     * @return the transition_url
     */
    public String getTransition_url() {
        return transition_url;
    }

    /**
     * @param transition_url the transition_url to set
     */
    public void setTransition_url(String transition_url) {
        this.transition_url = transition_url;
    }

    /**
     * @return the content_image_url
     */
    public String getContent_image_url() {
        return content_image_url;
    }

    /**
     * @param content_image_url the content_image_url to set
     */
    public void setContent_image_url(String content_image_url) {
        this.content_image_url = content_image_url;
    }
}
