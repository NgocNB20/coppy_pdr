/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.shop.top;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsRankingType;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 商品ランキングクラス
 *
 * @author EntityGenerator
 * @version $Revision: 1.0 $
 */
@Entity
@Table(name = "GoodsRanking")
@Data
@Component
@Scope("prototype")
public class GoodsRankingEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ショップSEQ（必須）
     */
    @Column(name = "shopSeq")
    @Id
    private Integer shopSeq;

    /**
     * 商品ランキング種別（必須）
     */
    @Column(name = "goodsRankingType")
    @Id
    private HTypeGoodsRankingType goodsRankingType = HTypeGoodsRankingType.CLICK_COUNT;

    /**
     * 商品グループSEQ（必須）
     */
    @Column(name = "goodsGroupSeq")
    @Id
    private Integer goodsGroupSeq;

    /**
     * 集計値（必須）
     */
    @Column(name = "totalValue")
    private BigDecimal totalValue = new BigDecimal(0);

    /**
     * 集計対象名
     */
    @Column(name = "totalTargetName")
    private String totalTargetName;

    /**
     * 登録日時（必須）
     */
    @Column(name = "registTime")
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    @Column(name = "updateTime")
    private Timestamp updateTime;

}
