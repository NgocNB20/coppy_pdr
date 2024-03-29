/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.goods;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 シリーズ商品価格情報取得APIのレスポンスモデル<br/>
 *
 * @author k-katoh
 */
public class GetSeriesPriceResponse {

    /** 商品グループ管理番号 */
    private String goodsGroupCode;

    // 2023-renew AddNo5 from here
    // 2023-renew AddNo5 to here

    /** シリーズセールコメント */
    private String seriesSaleComment;

    /** NEWアイコンフラグ */
    private String newIconFlag;

    /** お取りおきアイコンフラグ */
    private String reserveIconFlag;

    /** SALEアイコンフラグ */
    private String saleIconFlag;

    // 2023-renew AddNo5 from here
    // 2023-renew AddNo5 to here

    // 2023-renew No92 from here
    /** アウトレットアイコンフラグ */
    private String outletIconFlag;
    // 2023-renew No92 to here

    /**
     * @return the goodsGroupManagementNo
     */
    public String getGoodsGroupCode() {
        return goodsGroupCode;
    }

    /**
     * @param goodsGroupCode the goodsGroupCode to set
     */
    public void setGoodsGroupCode(String goodsGroupCode) {
        this.goodsGroupCode = goodsGroupCode;
    }

    // 2023-renew AddNo5 from here
    // 2023-renew AddNo5 to here

    /**
     * @return the seriesSaleComment
     */
    public String getSeriesSaleComment() {
        return seriesSaleComment;
    }

    /**
     * @param seriesSaleComment the seriesSaleComment to set
     */
    public void setSeriesSaleComment(String seriesSaleComment) {
        this.seriesSaleComment = seriesSaleComment;
    }

    /**
     * @return the newIconFlag
     */
    public String getNewIconFlag() {
        return newIconFlag;
    }

    /**
     * @param newIconFlag the newIconFlag to set
     */
    public void setNewIconFlag(String newIconFlag) {
        this.newIconFlag = newIconFlag;
    }

    /**
     * @return the reserveIconFlag
     */
    public String getReserveIconFlag() {
        return reserveIconFlag;
    }

    /**
     * @param reserveIconFlag the reserveIconFlag to set
     */
    public void setReserveIconFlag(String reserveIconFlag) {
        this.reserveIconFlag = reserveIconFlag;
    }

    /**
     * @return the saleIconFlag
     */
    public String getSaleIconFlag() {
        return saleIconFlag;
    }

    /**
     * @param saleIconFlag the saleIconFlag to set
     */
    public void setSaleIconFlag(String saleIconFlag) {
        this.saleIconFlag = saleIconFlag;
    }

    // 2023-renew AddNo5 from here
    // 2023-renew AddNo5 to here

    // 2023-renew No92 from here

    /**
     * @return the outletIconFlag
     */
    public String getOutletIconFlag() {
        return outletIconFlag;
    }

    /**
     * @param outletIconFlag the saleControl to set
     */
    public void setOutletIconFlag(String outletIconFlag) {
        this.outletIconFlag = outletIconFlag;
    }
    // 2023-renew No92 to here
}
