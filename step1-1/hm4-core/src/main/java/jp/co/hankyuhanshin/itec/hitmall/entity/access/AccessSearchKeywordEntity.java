/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.access;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
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
 * アクセス検索キーワードクラス
 *
 * @author EntityGenerator
 * @version $Revision: 1.2 $
 */
@Entity
@Table(name = "AccessSearchKeyword")
@Data
@Component
@Scope("prototype")
public class AccessSearchKeywordEntity implements Serializable {

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
     * アクセス日時（必須）
     */
    @Column(name = "accessTime")
    @Id
    private Timestamp accessTime;

    /**
     * 端末識別情報（必須）
     */
    @Column(name = "accessUid")
    @Id
    private String accessUid;

    /**
     * サイト種別（必須）
     */
    @Column(name = "siteType")
    @Id
    private HTypeSiteType siteType = HTypeSiteType.FRONT_PC;

    /**
     * 検索キー（必須）
     */
    @Column(name = "searchkeyword")
    @Id
    private String searchkeyword;

    /**
     * 検索結果件数（必須）
     */
    @Column(name = "searchResultCount")
    @Id
    private Integer searchResultCount = 0;

    /**
     * 検索カテゴリSEQ
     */
    @Column(name = "searchCategorySeq")
    private Integer searchCategorySeq = 0;

    /**
     * 検索価格from
     */
    @Column(name = "searchPriceFrom")
    private BigDecimal searchPriceFrom;

    /**
     * 検索価格to
     */
    @Column(name = "searchPriceTo")
    private BigDecimal searchPriceTo;

    /**
     * 画面ID（必須）
     */
    @Column(name = "pageId")
    private String pageId;

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
