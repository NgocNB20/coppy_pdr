/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.shop.sitemap;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOutputFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUrlType;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.SequenceGenerator;
import org.seasar.doma.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * サイトマップ
 */
@Entity
@Table(name = "SiteMap")
@Data
@Component
@Scope("prototype")
public class SiteMapEntity implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * サイトマップSEQ
     */
    @Column(name = "siteMapSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "siteMapSeq")
    private Integer siteMapSeq;

    /**
     * サイトマップ名
     */
    @Column(name = "siteMapName")
    private String siteMapName;

    /**
     * サイト種別
     */
    @Column(name = "siteType")
    private HTypeSiteType siteType;

    /**
     * 出力ファイル名
     */
    @Column(name = "outputFileName")
    private String outputFileName;

    /**
     * 出力対象フラグ
     */
    @Column(name = "outputFlag")
    private HTypeOutputFlag outputFlag;

    /**
     * URLタイプ
     */
    @Column(name = "urlType")
    private HTypeUrlType urlType;

    /**
     * loc
     */
    @Column(name = "loc")
    private String loc;

    /**
     * changefreq
     */
    @Column(name = "changefreq")
    private String changefreq;

    /**
     * priority
     */
    @Column(name = "priority")
    private BigDecimal priority;

    /**
     * 備考
     */
    @Column(name = "note")
    private String note;

    /**
     * 登録日時
     */
    @Column(name = "registTime")
    private Timestamp registTime;

    /**
     * 更新日時
     */
    @Column(name = "updateTime")
    private Timestamp updateTime;

}
