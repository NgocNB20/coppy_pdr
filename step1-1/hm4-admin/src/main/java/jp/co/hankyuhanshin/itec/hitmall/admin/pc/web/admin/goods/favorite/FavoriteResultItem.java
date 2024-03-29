/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.favorite;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * お気に入り商品検索結果画面情報<br/>
 *
 * @author takashima
 */
@Data
@Component
@Scope("prototype")
public class FavoriteResultItem {
    /**
     * シリアルバージョンID<br/>
     */
    private static final long serialVersionUID = 1L;

    /************************************
     ** 検索結果項目
     ************************************/
    /**
     * resultGoodsCheck<br/>
     */
    private boolean resultFavoriteCheck;

    /**
     * resultGoodsSeq<br/>
     */
    private Integer resultGoodsSeq;

    /**
     * resultMemberInfoSeq<br/>
     */
    private Integer resultMemberInfoSeq;

    /**
     * No<br/>
     */
    private Integer resultNo;

    /**
     * goodsGroupCode<br/>
     */
    private String goodsGroupCode;

    /**
     * resultGoodsCode<br/>
     */
    private String resultGoodsCode;

    /**
     * resultCustomerNo<br/>
     */
    private String resultCustomerNo;

    /**
     * resultSaleStatus<br/>
     */
    private String resultSaleStatus;

    /**
     * resultSaleCd<br/>
     */
    private String resultSaleCd;

    /**
     * resultSaleTo<br/>
     */
    private Timestamp resultSaleTo;

}
