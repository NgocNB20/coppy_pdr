// 2023-renew No11 from here
package jp.co.hankyuhanshin.itec.webapi.pdr.response.goods;

/**
 * PDR#15 WEB-API連携 販売可否判定APIのレスポンスモデル<br/>
 *
 * @author k-katoh
 */
public class GetSaleCheckResponse {
    /** 商品コード */
    private String goodsCode;

    /** 販売可否判定結果 */
    private Integer goodsSaleYesNo;

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
     * @return the goodsSaleYesNo
     */
    public Integer getGoodsSaleYesNo() {
        return goodsSaleYesNo;
    }

    /**
     * @param goodsSaleYesNo the goodsCode to set
     */
    public void setGoodsSaleYesNo(Integer goodsSaleYesNo) {
        this.goodsSaleYesNo = goodsSaleYesNo;
    }
}
// 2023-renew No11 to here
