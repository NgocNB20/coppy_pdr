/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCategoryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeManualDisplayFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * カテゴリクラス
 *
 * @author EntityGenerator
 * @version $Revision: 1.0 $
 */
@Entity
@Table(name = "Category")
@Data
@Component
@Scope("prototype")
public class CategoryEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * カテゴリSEQ（必須）
     */
    @Column(name = "categorySeq")
    @Id
    private Integer categorySeq;

    /**
     * ショップSEQ（必須）
     */
    @Column(name = "shopSeq")
    private Integer shopSeq;

    /**
     * カテゴリID（必須）
     */
    @Column(name = "categoryId")
    private String categoryId;

    /**
     * カテゴリ名（必須）
     */
    @Column(name = "categoryName")
    private String categoryName;

    /**
     * カテゴリ公開状態PC（必須）
     */
    @Column(name = "categoryOpenStatusPC")
    private HTypeOpenStatus categoryOpenStatusPC = HTypeOpenStatus.NO_OPEN;

    /**
     * カテゴリ公開状態携帯（必須）
     */
    @Column(name = "categoryOpenStatusMB")
    private HTypeOpenStatus categoryOpenStatusMB = HTypeOpenStatus.NO_OPEN;

    /**
     * カテゴリ公開開始日時PC
     */
    @Column(name = "categoryOpenStartTimePC")
    private Timestamp categoryOpenStartTimePC;

    /**
     * カテゴリ公開終了日時PC
     */
    @Column(name = "categoryOpenEndTimePC")
    private Timestamp categoryOpenEndTimePC;

    /**
     * カテゴリ公開開始日時携帯
     */
    @Column(name = "categoryOpenStartTimeMB")
    private Timestamp categoryOpenStartTimeMB;

    /**
     * カテゴリ公開終了日時携帯
     */
    @Column(name = "categoryOpenEndTimeMB")
    private Timestamp categoryOpenEndTimeMB;

    /**
     * カテゴリ種別（必須）
     */
    @Column(name = "categoryType")
    private HTypeCategoryType categoryType = HTypeCategoryType.NORMAL;

    /**
     * カテゴリSEQパス（必須）
     */
    @Column(name = "categorySeqPath")
    private String categorySeqPath;

    /**
     * 表示順
     */
    @Column(name = "orderDisplay")
    private Integer orderDisplay;

    /**
     * ルートカテゴリSEQ（必須）
     */
    @Column(name = "rootCategorySeq")
    private Integer rootCategorySeq;

    /**
     * カテゴリ階層（必須）
     */
    @Column(name = "categoryLevel")
    private Integer categoryLevel;

    /**
     * カテゴリパス（必須）
     */
    @Column(name = "categoryPath")
    private String categoryPath;

    /**
     * 手動表示フラグ（必須）
     */
    @Column(name = "manualDisplayFlag")
    private HTypeManualDisplayFlag manualDisplayFlag = HTypeManualDisplayFlag.OFF;

    /**
     * 更新カウンタ（必須）
     */
    @Version
    @Column(name = "versionNo")
    private Integer versionNo = 0;

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

    /**
     * サイトマップ出力フラグ（必須）
     */
    @Column(name = "siteMapFlag")
    private HTypeSiteMapFlag siteMapFlag = HTypeSiteMapFlag.OFF;

    // テーブル項目外追加フィールド

    /**
     * PC公開商品点数
     */
    @Column(insertable = false, updatable = false)
    private Integer openGoodsCountPC;

    /**
     * MB公開商品点数
     */
    @Column(insertable = false, updatable = false)
    private Integer openGoodsCountMB;
}
