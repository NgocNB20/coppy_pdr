/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.hitmall.dto.ukapi;

import lombok.Data;
import net.arnx.jsonic.JSONHint;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * UK-API連携 ユニサーチ（商品）responseDocsDto
 * @author tk32120
 */
@Data
@Component
@Scope("prototype")
public class UkApiUniSearchGoodsResponseDocsDto implements Serializable {
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 商品グループコード */
    @JSONHint(ordinal = 10, name = "group_id")
    private String groupId;

    /** NEWアイコンフラグ */
    @JSONHint(ordinal = 20, name = "new_flg")
    private Integer newFlg;

    /** SALEアイコンフラグ */
    @JSONHint(ordinal = 30, name = "sale_flg")
    private Integer saleFlg;

    /** セールde予約アイコンフラグ */
    @JSONHint(ordinal = 40, name = "reserve_flg")
    private Integer reserveFlg;

    /** アウトレットアイコンフラグ */
    @JSONHint(ordinal = 50, name = "outlet_flg")
    private Integer outletFlg;

    /** 商品名 */
    @JSONHint(ordinal = 60, name = "item_name")
    private String itemName;

    /** セールコメント */
    @JSONHint(ordinal = 70, name = "sale_comment")
    private String saleComment;

    /** 値引前最小価格 */
    @JSONHint(ordinal = 80, name = "min_price")
    private Integer minPrice;

    /** 値引前最大価格 */
    @JSONHint(ordinal = 90, name = "max_price")
    private Integer maxPrice;

    /** 値引後最小価格 */
    @JSONHint(ordinal = 100, name = "sale_min_price")
    private Integer saleMinPrice;

    /** 値引後最大価格 */
    @JSONHint(ordinal = 110, name = "sale_max_price")
    private Integer saleMaxPrice;

    /** カテゴリIDリスト */
    @JSONHint(ordinal = 120, name = "category_id_list")
    private List<String> categoryIdList;

    /** 商品状態 */
    @JSONHint(ordinal = 130, name = "item_status")
    private Integer itemStatus;

    /** 医薬品フラグ */
    @JSONHint(ordinal = 140, name = "drug_flg")
    private Integer drugFlg;

    /** 商品概要説明 */
    @JSONHint(ordinal = 150, name = "item_overview")
    private String itemOverview;

    /** 新着日付 */
    @JSONHint(ordinal = 160, name = "new_date")
    private Timestamp newDate;

    /** カタログID */
    @JSONHint(ordinal = 170, name = "catalog_id")
    private String catalogId;

}
