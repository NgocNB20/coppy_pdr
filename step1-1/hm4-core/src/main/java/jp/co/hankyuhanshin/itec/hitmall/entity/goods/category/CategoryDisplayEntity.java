/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.goods.category;

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
 * カテゴリ表示クラス
 *
 * @author EntityGenerator
 */
@Entity
@Table(name = "CategoryDisplay")
@Data
@Component
@Scope("prototype")
public class CategoryDisplayEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * カテゴリSEQ (FK)（必須）
     */
    @Column(name = "categorySeq")
    @Id
    private Integer categorySeq;

    /**
     * カテゴリ表示名PC
     */
    @Column(name = "categoryNamePC")
    private String categoryNamePC;

    /**
     * カテゴリ表示名携帯
     */
    @Column(name = "categoryNameMB")
    private String categoryNameMB;

    /**
     * カテゴリについての説明文PC
     */
    @Column(name = "categoryNotePC")
    private String categoryNotePC;

    /**
     * カテゴリについての説明文携帯
     */
    @Column(name = "categoryNoteMB")
    private String categoryNoteMB;

    /**
     * フリーテキストPC
     */
    @Column(name = "freeTextPC")
    private String freeTextPC;

    /**
     * フリーテキストスマートフォン
     */
    @Column(name = "freeTextSP")
    private String freeTextSP;

    /**
     * フリーテキスト携帯
     */
    @Column(name = "freeTextMB")
    private String freeTextMB;

    /**
     * meta-description
     */
    @Column(name = "metaDescription")
    private String metaDescription;

    /**
     * meta-keyword
     */
    @Column(name = "metaKeyword")
    private String metaKeyword;

    /**
     * カテゴリ画像PC
     */
    @Column(name = "categoryImagePC")
    private String categoryImagePC;

    /**
     * カテゴリ画像スマートフォン
     */
    @Column(name = "categoryImageSP")
    private String categoryImageSP;

    /**
     * カテゴリ画像携帯
     */
    @Column(name = "categoryImageMB")
    private String categoryImageMB;

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
