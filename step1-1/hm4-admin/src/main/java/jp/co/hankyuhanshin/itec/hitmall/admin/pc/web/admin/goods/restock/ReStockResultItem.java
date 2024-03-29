/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.restock;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 入荷お知らせ商品検索結果画面情報<br/>
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class ReStockResultItem implements Serializable {
    /**
     * シリアルバージョンID<br/>
     */
    private static final long serialVersionUID = 1L;

    /***********************************
     検索結果項目
     */

    /**
     * resultReStockCheck<br/>
     */
    private boolean resultReStockCheck;

    /**
     * resultKey<br/>
     */
    private String resultKey;

    /**
     * No<br/>
     */
    private Integer resultNo;

    /**
     * resultGoodsSeq<br/>
     */
    private Integer resultGoodsSeq;

    /**
     * resultGoodsGroupCode<br/>
     */
    private String resultGoodsGroupCode;

    /**
     * resultGoodsCode<br/>
     */
    private String resultGoodsCode;

    /**
     * resultRegistMemberCount<br/>
     */
    private Integer resultRegistMemberCount;

    /**
     * resultReStockStatus<br/>
     */
    private String resultReStockStatus;

    /**
     * resultDeliveryId<br/>
     */
    private String resultDeliveryId;

    /**
     * resultDeliveryStatus<br/>
     */
    private String resultDeliveryStatus;

    /**
     * resultReStockTime<br/>
     */
    private Timestamp resultReStockTime;
}
