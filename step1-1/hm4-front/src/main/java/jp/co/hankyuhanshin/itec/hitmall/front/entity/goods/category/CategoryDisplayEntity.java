/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.category;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * カテゴリ表示クラス
 *
 * @author EntityGenerator
 */
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
    private Integer categorySeq;

    /**
     * カテゴリ表示名PC
     */
    private String categoryNamePC;

    /**
     * カテゴリ表示名携帯
     */
    private String categoryNameMB;

    /**
     * カテゴリについての説明文PC
     */
    private String categoryNotePC;

    /**
     * カテゴリについての説明文携帯
     */
    private String categoryNoteMB;

    /**
     * フリーテキストPC
     */
    private String freeTextPC;

    /**
     * フリーテキストスマートフォン
     */
    private String freeTextSP;

    /**
     * フリーテキスト携帯
     */
    private String freeTextMB;

    /**
     * meta-description
     */
    private String metaDescription;

    /**
     * meta-keyword
     */
    private String metaKeyword;

    /**
     * カテゴリ画像PC
     */
    private String categoryImagePC;

    /**
     * カテゴリ画像スマートフォン
     */
    private String categoryImageSP;

    /**
     * カテゴリ画像携帯
     */
    private String categoryImageMB;

    /**
     * 登録日時（必須）
     */
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    private Timestamp updateTime;
}
