/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.dto.ukapi;

import jp.co.hankyuhanshin.itec.hitmall.annotation.csv.CsvColumn;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsClassType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOutletIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleIconFlag;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 商品フィードTsvDto
 */
@Entity
@Data
@Component
@Scope("prototype")
public class UkGoodsFeedTsvDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品グループSEQ（商品表示名更新用に利用）
     */
    private Integer goodsGroupSeq;

    /**
     * 商品グループコード
     */
    @CsvColumn(order = 10, columnLabel = "group_id")
    @NotEmpty
    private String goodsGroupCode;

    /**
     * NEWアイコンフラグ
     */
    @CsvColumn(order = 20, columnLabel = "new_flg", enumOutputType = "value")
    @NotEmpty
    private HTypeNewIconFlag newIconFlag;

    /**
     * SALEアイコンフラグ
     */
    @CsvColumn(order = 30, columnLabel = "sale_flg", enumOutputType = "value")
    @NotEmpty
    private HTypeSaleIconFlag saleIconFlag;

    /**
     * セールde予約アイコンフラグ
     */
    @CsvColumn(order = 40, columnLabel = "reserve_flg", enumOutputType = "value")
    @NotEmpty
    private HTypeReserveIconFlag reserveIconFlag;

    /**
     * アウトレットアイコンフラグ
     */
    @CsvColumn(order = 50, columnLabel = "outlet_flg", enumOutputType = "value")
    @NotEmpty
    private HTypeOutletIconFlag outletIconFlag;

    /**
     * 商品名
     */
    @CsvColumn(order = 60, columnLabel = "item_name")
    @NotEmpty
    private String goodsGroupName;

    /**
     * セールコメント
     */
    @CsvColumn(order = 70, columnLabel = "sale_comment")
    @NotEmpty
    private String goodsPreDiscountPrice;

    /**
     * 値引前最小価格
     */
    @CsvColumn(order = 80, columnLabel = "min_price")
    @NotEmpty
    private String goodsGroupMinPricePc;

    /**
     * 値引前最大価格
     */
    @CsvColumn(order = 90, columnLabel = "max_price")
    @NotEmpty
    private String goodsGroupMaxPricePc;

    /**
     * 値引後最小価格
     */
    @CsvColumn(order = 100, columnLabel = "sale_min_price")
    @NotEmpty
    private String goodsGroupMinPriceMb;

    /**
     * 値引後最大価格
     */
    @CsvColumn(order = 110, columnLabel = "sale_max_price")
    @NotEmpty
    private String goodsGroupMaxPriceMb;

    /**
     * カテゴリSEQリスト（カテゴリIDリスト作成に利用）
     */
    private String categorySeqList;

    /**
     * カテゴリIDリスト
     */
    @CsvColumn(order = 120, columnLabel = "category_id_list")
    @NotEmpty
    private String categoryIdList;

    /**
     * カテゴリ名リスト
     */
    @CsvColumn(order = 130, columnLabel = "category_name_list")
    @NotEmpty
    private String categoryNameList;

    /**
     * カテゴリ順リスト
     */
    @CsvColumn(order = 140, columnLabel = "category_order_list")
    @NotEmpty
    private String categoryOrderList;

    /**
     * 商品状態
     */
    @CsvColumn(order = 150, columnLabel = "item_status")
    @NotEmpty
    private String itemStatus;

    /**
     * 心意気対象フラグ
     */
    @CsvColumn(order = 160, columnLabel = "spirit_flg")
    @NotEmpty
    private String spiritFlag;

    /**
     * 薬品区分
     */
    private HTypeGoodsClassType goodsClassType;

    /**
     * 医薬品フラグ
     */
    @CsvColumn(order = 170, columnLabel = "drug_flg")
    @NotEmpty
    private String drugFlag;

    /**
     * 商品概要1（商品概要説明設定用）
     */
    private String goodsNote1;

    /**
     * 商品概要1_公開開始日時（商品概要説明設定用）
     */
    private Timestamp goodsNote1OpenStartTime;

    /**
     * 商品概要2（商品概要説明設定用）
     */
    private String goodsNote1Sub;

    /**
     * 商品概要2_公開開始日時（商品概要説明設定用）
     */
    private Timestamp goodsNote1SubOpenStartTime;

    /**
     * 商品概要説明
     */
    @CsvColumn(order = 180, columnLabel = "item_overview")
    @NotEmpty
    private String itemOverview;

    /**
     * 新着日付（tmp）
     */
    private Timestamp tmpNewDate;

    /**
     * 新着日付（連携用）
     */
    @CsvColumn(order = 190, columnLabel = "new_date")
    @NotEmpty
    private String newDate;

    /**
     * カタログ番号
     */
    @CsvColumn(order = 200, columnLabel = "catalog_id")
    @NotEmpty
    private String catalogCode;

    /**
     * 検索キーワード（tmp）
     */
    private String searchKeyword;

    /**
     * 検索キーワード（連携用）
     */
    @CsvColumn(order = 210, columnLabel = "search_keyword")
    @NotEmpty
    private String ukSearchKeyword;

    /**
     * 商品番号
     */
    @CsvColumn(order = 220, columnLabel = "item_id")
    @NotEmpty
    private String goodsCode;

    /**
     * 規格名1
     */
    @CsvColumn(order = 230, columnLabel = "unit_name1")
    @NotEmpty
    private String unitTitle1;

    /**
     * 規格名2
     */
    @CsvColumn(order = 240, columnLabel = "unit_name2")
    @NotEmpty
    private String unitTitle2;

    /**
     * 規格値1
     */
    @CsvColumn(order = 250, columnLabel = "unit_value1")
    @NotEmpty
    private String unitValue1;

    /**
     * 規格値2
     */
    @CsvColumn(order = 260, columnLabel = "unit_value2")
    @NotEmpty
    private String unitValue2;

    /**
     * 値引前商品価格
     */
    @CsvColumn(order = 270, columnLabel = "price")
    @NotEmpty
    private String goodsPriceInTaxLow;

    /**
     * 値引後商品価格
     */
    @CsvColumn(order = 280, columnLabel = "sale_price")
    @NotEmpty
    private String preDiscountPriceLow;

}
