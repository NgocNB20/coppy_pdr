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
 * 入荷お知らせ商品詳細画面情報<br/>
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class ReStockDetailsResultItem implements Serializable {
    /**
     * シリアルバージョンID<br/>
     */
    private static final long serialVersionUID = 1L;

    /************************************
     ** 検索結果項目
     ************************************/

    /**
     * resultKey<br/>
     */
    private String resultKey;

    /**
     * resultReStockCheck<br/>
     */
    private boolean resultReStockCheck;

    /**
     * No<br/>
     */
    private Integer resultNo;

    /**
     * memberInfoSeq<br/>
     */
    private Integer memberInfoSeq;

    /**
     * customerNo<br/>
     */
    private Integer customerNo;

    /**
     * memberInfoLastName<br/>
     */
    private String memberInfoLastName;

    /**
     * memberInfoId<br/>
     */
    private String memberInfoId;

    /**
     * registTime<br/>
     */
    private Timestamp registTime;

    /**
     * resultDeliveryId<br/>
     */
    private String deliveryStatus;

    /**
     * versionNo<br/>
     */
    private Integer versionNo;

}
