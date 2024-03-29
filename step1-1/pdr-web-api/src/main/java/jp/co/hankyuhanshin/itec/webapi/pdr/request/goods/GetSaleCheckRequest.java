// 2023-renew No11 from here
package jp.co.hankyuhanshin.itec.webapi.pdr.request.goods;

/**
 * PDR#15 WEB-API連携 販売可否判定のリクエストモデル<br/>
 *
 * @author k-katoh
 */
public class GetSaleCheckRequest {

    /** 商品コード */
    private String goodsCode;

    /** 顧客番号 */
    private String customerNo;

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
     * @return the customerNo
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * @param customerNo the customerNo to set
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
}
// 2023-renew No11 to here
