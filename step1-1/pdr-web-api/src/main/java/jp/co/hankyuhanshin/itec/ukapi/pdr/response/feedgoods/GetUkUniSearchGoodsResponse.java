package jp.co.hankyuhanshin.itec.ukapi.pdr.response.feedgoods;

import java.sql.Timestamp;
import java.util.List;

public class GetUkUniSearchGoodsResponse {

    /** 商品グループコード*/
    private String group_id;
    /** NEWアイコンフラグ */
    private int new_flg;
    /** SALEアイコンフラグ */
    private int sale_flg;
    /** セールde予約アイコンフラグ */
    private int reserve_flg;
    /** アウトレットアイコンフラグ */
    private int outlet_flg;
    /** 商品名 */
    private String item_name;
    /** セールコメント */
    private String sale_comment;
    /** 値引前最小価格 */
    private int min_price;
    /** 値引前最大価格 */
    private int max_price;
    /** 値引後最小価格 */
    private int sale_min_price;
    /** 値引後最大価格 */
    private int sale_max_price;
    /** カテゴリIDリスト */
    private List<String> category_id_list;
    /** 商品状態 */
    private int item_status;
    /** 医薬品フラグ */
    private int drug_flg;
    /** 商品概要説明 */
    private String item_overview;
    /** 新着日付 */
    private Timestamp new_date;
    /** カタログ番号 */
    private int catalog_id;

    /**
     * @return the group_id
     */
    public String getGroup_id() {
        return group_id;
    }

    /**
     * @param group_id the group_id to set
     */
    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    /**
     * @return the new_flag
     */
    public int getNew_flg() {
        return new_flg;
    }

    /**
     * @param new_flg the new_flag to set
     */
    public void setNew_flg(int new_flg) {
        this.new_flg = new_flg;
    }

    /**
     * @return the sale_flag
     */
    public int getSale_flg() {
        return sale_flg;
    }

    /**
     * @param sale_flg the sale_flag to set
     */
    public void setSale_flg(int sale_flg) {
        this.sale_flg = sale_flg;
    }

    /**
     * @return the reserve_flag
     */
    public int getReserve_flg() {
        return reserve_flg;
    }

    /**
     * @param reserve_flg the reserve_flag to set
     */
    public void setReserve_flg(int reserve_flg) {
        this.reserve_flg = reserve_flg;
    }

    /**
     * @return the outlet_flag
     */
    public int getOutlet_flg() {
        return outlet_flg;
    }

    /**
     * @param outlet_flg the outlet_flag to set
     */
    public void setOutlet_flg(int outlet_flg) {
        this.outlet_flg = outlet_flg;
    }

    /**
     * @return the item_name
     */
    public String getItem_name() {
        return item_name;
    }

    /**
     * @param item_name the item_name to set
     */
    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    /**
     * @return the sale_comment
     */
    public String getSale_comment() {
        return sale_comment;
    }

    /**
     * @param sale_comment the sale_comment to set
     */
    public void setSale_comment(String sale_comment) {
        this.sale_comment = sale_comment;
    }

    /**
     * @return the min_price
     */
    public int getMin_price() {
        return min_price;
    }

    /**
     * @param min_price the min_price to set
     */
    public void setMin_price(int min_price) {
        this.min_price = min_price;
    }

    /**
     * @return the max_price
     */
    public int getMax_price() {
        return max_price;
    }

    /**
     * @param max_price the max_price to set
     */
    public void setMax_price(int max_price) {
        this.max_price = max_price;
    }

    /**
     * @return the sale_min_price
     */
    public int getSale_min_price() {
        return sale_min_price;
    }

    /**
     * @param sale_min_price the sale_min_price to set
     */
    public void setSale_min_price(int sale_min_price) {
        this.sale_min_price = sale_min_price;
    }

    /**
     * @return the sale_max_price
     */
    public int getSale_max_price() {
        return sale_max_price;
    }

    /**
     * @param sale_max_price the sale_max_price to set
     */
    public void setSale_max_price(int sale_max_price) {
        this.sale_max_price = sale_max_price;
    }

    /**
     * @return the category_id_list
     */
    public List<String> getCategory_id_list() {
        return category_id_list;
    }

    /**
     * @param category_id_list the category_id_list to set
     */
    public void setCategory_id_list(List<String> category_id_list) {
        this.category_id_list = category_id_list;
    }

    /**
     * @return the item_status
     */
    public int getItem_status() {
        return item_status;
    }

    /**
     * @param item_status the item_status to set
     */
    public void setItem_status(int item_status) {
        this.item_status = item_status;
    }

    /**
     * @return the drug_flag
     */
    public int getDrug_flg() {
        return drug_flg;
    }

    /**
     * @param drug_flg the drug_flag to set
     */
    public void setDrug_flg(int drug_flg) {
        this.drug_flg = drug_flg;
    }

    /**
     * @return the item_overview
     */
    public String getItem_overview() {
        return item_overview;
    }

    /**
     * @param item_overview the item_overview to set
     */
    public void setItem_overview(String item_overview) {
        this.item_overview = item_overview;
    }

    /**
     * @return the new_date
     */
    public Timestamp getNew_date() {
        return new_date;
    }

    /**
     * @param new_date the new_date to set
     */
    public void setNew_date(Timestamp new_date) {
        this.new_date = new_date;
    }

    /**
     * @return the catalog_id
     */
    public int getCatalog_id() {
        return catalog_id;
    }

    /**
     * @param catalog_id the catalog_id to set
     */
    public void setCatalog_id(int catalog_id) {
        this.catalog_id = catalog_id;
    }
}
