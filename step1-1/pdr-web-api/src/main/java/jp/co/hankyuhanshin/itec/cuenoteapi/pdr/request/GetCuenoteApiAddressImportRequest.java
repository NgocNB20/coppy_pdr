package jp.co.hankyuhanshin.itec.cuenoteapi.pdr.request;

/**
 * Cuenote-API連携 アドレス帳インポートAPIリクエストモデル<br/>
 * @author st75001
 */
public class GetCuenoteApiAddressImportRequest {

    /** メールアドレス */
    private String email;
    /** 事業所名 */
    private String office_name;
    /** 商品情報 */
    private String goods_info;

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the office_name
     */
    public String getOffice_name() {
        return office_name;
    }

    /**
     * @param office_name the office_name to set
     */
    public void setOffice_name(String office_name) {
        this.office_name = office_name;
    }

    /**
     * @return the goods_info
     */
    public String getGoods_info() {
        return goods_info;
    }

    /**
     * @param goods_info the goods_info to set
     */
    public void setGoods_info(String goods_info) {
        this.goods_info = goods_info;
    }

}
