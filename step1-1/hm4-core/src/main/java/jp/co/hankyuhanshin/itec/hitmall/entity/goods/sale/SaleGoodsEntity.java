/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.goods.sale;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFavoriteSaleStatus;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * セール商品クラス
 *
 * @author EntityGenerator
 * @version $Revision: 1.0 $
 */
@Entity
@Table(name = "SaleGoods")
@Data
@Component
@Scope("prototype")
public class SaleGoodsEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品SEQ（必須）
     */
    @Column(name = "goodsSeq")
    @Id
    private Integer goodsSeq;

    /**
     * セール状態（必須）
     */
    @Column(name = "saleStatus")
    private HTypeFavoriteSaleStatus saleStatus;

    /**
     * 前回セールコード
     */
    @Column(name = "preSaleCd")
    private String preSaleCd;

    /**
     * セールコード
     */
    @Column(name = "saleCd")
    private String saleCd;

    /**
     * セール期間From
     */
    @Column(name = "saleFrom")
    private Timestamp saleFrom;

    /**
     * セール期間To
     */
    @Column(name = "saleTo")
    private Timestamp saleTo;

    /**
     * 登録日時（必須）
     */
    @Column(name = "registTime", updatable = false)
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    @Column(name = "updateTime")
    private Timestamp updateTime;

}