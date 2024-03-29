/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.dto.ajax;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalableGoodsType;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Multiple category goods data
 *
 * @author Shalaka kale
 */
@Data
@Component
@Scope("prototype")
public class MultipleCategoryGoodsDetailsDto implements Serializable {

    /**
     * serialVersionUID<br/>
     */
    private static final long serialVersionUID = 1L;
    /**
     * 商品表示値引き前単価
     */
    public String goodsDisplayPreDiscountPrice;
    /**
     * 値引き前単価
     */
    public boolean isGoodsPreDiscount;
    /**
     * 商品表示単価価格帯フラグ
     */
    public boolean isGoodsDisplayPriceRange;
    /**
     * 商品表示値引き前単価価格帯フラグ
     */
    public boolean isGoodsDisplayPreDiscountPriceRange;
    /**
     * 商品グループSEQ
     */
    private Integer goodsGroupSeq;
    // PDR Migrate Customization from here
    /**
     * 商品グループコード
     */
    private String ggcd;
    /**
     * 商品グループコード
     */
    private String href;
    /**
     * 商品名
     */
    private String goodsGroupName;
    /**
     * 商品グループサムネイル画像
     */
    private String goodsGroupImageThumbnail;
    // PDR Migrate Customization to here
    /**
     * 販売可能商品区分
     */
    private HTypeSalableGoodsType salableGoodsType;
    /**
     * 商品表示単価
     */
    private String goodsDisplayPrice;
    /**
     * 値引きコメント
     */
    private String goodsPreDiscountPrice;
    /**
     * 商品画像アイテム
     */
    private String goodsImageItem;
    /**
     * 商品単価
     */
    private BigDecimal goodsPrice;
    /**
     * 商品単価(税込)
     */
    private BigDecimal goodsPriceInTax;
    /**
     * 値引き前単価＝値引前単価（税抜き）<br/>
     */
    private BigDecimal preDiscountPrice;
    /**
     * 値引き前単価＝値引前単価（税込み）<br/>
     */
    private BigDecimal preDisCountPriceInTax;
    /**
     * 商品説明1
     */
    private String goodsNote1;
    /**
     * 税率
     */
    private BigDecimal taxRate;

    // アイコン
    /**
     * 商品消費税種別（必須）
     */
    private HTypeGoodsTaxType goodsTaxType = HTypeGoodsTaxType.OUT_TAX;
    /**
     * 新着日付
     */
    private Timestamp whatsnewDate;
    /**
     * 在庫状況表示
     */
    private String stockStatusPc;
    /**
     * 商品アイコンリスト
     */
    private List<MultipleCategoryGoodsDetailsDto> goodsIconItems;
    /**
     * 商品アイコン名
     */
    private String iconName;
    /**
     * 商品アイコンカラーコード
     */
    private String iconColorCode;
    /**
     * isNewDate
     */
    private boolean isNewDate;
    /**
     * isStockStatusDisplay
     */
    private boolean isStockStatusDisplay;
    /**
     * isStockNoSaleDisp
     */
    private boolean isStockNoSaleDisp;
    /**
     * isStockSoldOutIconDisp
     */
    private boolean isStockSoldOutIconDisp;
    /**
     * isStockBeforeSaleIconDisp
     */
    private boolean isStockBeforeSaleIconDisp;
    /**
     * isStockNoStockIconDisp
     */
    private boolean isStockNoStockIconDisp;
    /**
     * isStockFewIconDisp
     */
    private boolean isStockFewIconDisp;
    /**
     * isStockPossibleSalesIconDisp
     */
    private boolean isStockPossibleSalesIconDisp;
    /**
     * isGoodsGroupImage
     */
    private boolean isGoodsGroupImage;

    // PDR Migrate Customization from here

    /**
     * 画面表示用シリーズセール価格
     */
    private boolean groupSalePriceIntegrityFlag;

    /**
     * 販売可能商品区分が閲覧不可（ログイン前）かどうか
     *
     * @return true=閲覧不可（ログイン前）、false=閲覧不可（ログイン前）でない
     */
    private boolean necessaryLoginGoods;

    /**
     * 販売可能商品区分が価格非表示（ログイン前）かどうか
     *
     * @return true=価格非表示（ログイン前）、false=価格非表示（ログイン前）
     */
    private boolean priceHideGoods;

    /**
     * 販売可能商品区分が閲覧不可（ログイン後）かどうか
     *
     * @return true=閲覧不可（ログイン後）、false=閲覧不可（ログイン後）でない
     */
    private boolean noDispGoods;

    /**
     * 画面表示用シリーズセール価格
     */
    private boolean saleIconFlag;

    /**
     * お取りおきアイコンフラグ
     */
    private boolean reserveIconFlag;

    /**
     * NEWアイコンフラグ
     */
    private boolean newIconFlag;

    /**
     * セール価格（グループ）<br/>
     */
    private boolean sale;

    // 2023-renew AddNo5 from here
    /**
     * 通常価格(最低)
     */
    private BigDecimal goodsPriceLow;

    /**
     * 通常価格(最高)
     */
    private BigDecimal goodsPriceHigh;

    /**
     * セール価格(最低)
     */
    private BigDecimal goodsSalePriceLow;

    /**
     * セール価格(最高)
     */
    private BigDecimal goodsSalePriceHigh;

    /**
     * 画面表示用通常価格(最低)
     */
    private String dispGoodsPriceLow;

    /**
     * 画面表示用通常価格(最高)
     */
    private String dispGoodsPriceHigh;

    /**
     * 画面表示用セール価格(最低)
     */
    private String dispGoodsSalePriceLow;

    /**
     * 画面表示用セール価格(最高)
     */
    private String dispGoodsSalePriceHigh;

    /**
     * 最低価格が最高価格と等しい
     */
    public boolean isSamePrice;

    /**
     * 最低セール価格が最高セール価格と等しい
     */
    public boolean isSameSalePrice;

    /**
     * セール価格（商品）<br/>
     *
     * @return true:セール価格あり / false:セール価格なし
     */
    public boolean isSaleItem;
    // 2023-renew AddNo5 to here
    // PDR Migrate Customization to here

}
