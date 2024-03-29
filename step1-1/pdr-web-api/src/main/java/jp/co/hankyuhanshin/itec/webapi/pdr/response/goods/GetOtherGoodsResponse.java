package jp.co.hankyuhanshin.itec.webapi.pdr.response.goods;

/**
 * PDR#15 WEB-API連携 後継品代替品情報取得のリクエストモデル<br/>
 *
 * @author k-katoh
 */
public class GetOtherGoodsResponse {

    /** 商品コード */
    private String goodsCode;

    /** 後継品商品コード */
    private String otherGoodsCode;

    /**
     * @return the goodsCode
     */
    public String getGoodsCode() {
        return goodsCode;
    }

    /**
     * @param goodsCode the goodsCode to set
     */
    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    /**
     * @return the otherGoodsCode
     */
    public String getOtherGoodsCode() {
        return otherGoodsCode;
    }

    /**
     * @param otherGoodsCode the otherGoodsCode to set
     */
    public void setOtherGoodsCode(String otherGoodsCode) {
        this.otherGoodsCode = otherGoodsCode;
    }
}
