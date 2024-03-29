package jp.co.hankyuhanshin.itec.cuenoteapi.pdr.request;

/**
 * Cuenote-API連携 メール文書セット複製のリクエストモデル<br/>
 * @author st75001
 */
public class GetCuenoteApiMailSetRequest {

    /** メールテンプレートのメール文書セットＩＤ（必須） */
    private String original_mail_id;
    /** メール文書セット名（必須） */
    private String title;
    /** 備考 */
    private String comment;
    /** 使用目的 */
    private String purpose;

    /**
     * @return the original_mail_id
     */
    public String getOriginal_mail_id() {
        return original_mail_id;
    }

    /**
     * @param original_mail_id the original_mail_id to set
     */
    public void setOriginal_mail_id(String original_mail_id) {
        this.original_mail_id = original_mail_id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the purpose
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * @param purpose the purpose to set
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

}
