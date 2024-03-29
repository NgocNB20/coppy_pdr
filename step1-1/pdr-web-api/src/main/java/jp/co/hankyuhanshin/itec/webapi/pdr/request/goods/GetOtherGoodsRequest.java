package jp.co.hankyuhanshin.itec.webapi.pdr.request.goods;

/**
 * PDR#15 WEB-API連携 後継品代替品情報取得のリクエストモデル<br/>
 *
 * @author k-katoh
 */
public class GetOtherGoodsRequest {

    /** 商品コード */
    private String goodsCode;

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
}
