/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common;

import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalableGoodsType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 商品グループItem
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Data
@Component
@Scope("prototype")
public class GoodsGroupItem {

    // 2023-renew No36-1, No61,67,95 from here
    /** UK商品状態（在庫なし） */
    public static final String UK_NOSTOCK_STATUS = "10";

    /** UK商品状態（販売終了） */
    public static final String UK_NOSALE_STATUS = "20";
    // 2023-renew No36-1, No61,67,95 to here
    /**
     * カテゴリID
     */
    private String cid;

    /**
     * 商品グループSEQ
     */
    private Integer goodsGroupSeq;

    /**
     * 商品グループコード
     */
    private String ggcd;

    /**
     * 商品グループ名
     */
    private String goodsGroupName;

    /**
     * 新着日付
     */
    private Timestamp whatsnewDate;

    /**
     * 商品画像アイテム
     */
    private List<String> goodsImageItems;

    /**
     * 商品表示単価(税抜)
     */
    private BigDecimal goodsPrice;

    /**
     * 値引き前単価最安値
     */
    private BigDecimal preDiscountPrice;

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
    // 2023-renew AddNo5 to here

    /**
     * 値引前単価（税込）
     */
    private BigDecimal preDiscountPriceInTax;

    /**
     * 値引きコメント
     */
    private String goodsPreDiscountPrice;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 商品表示単価価格帯フラグ
     */
    private boolean goodsDisplayPriceRange;

    /**
     * 商品表示値引き前単価価格帯フラグ
     */
    private boolean goodsDisplayPreDiscountPriceRange;

    /**
     * 商品消費税種別（必須）
     */
    private HTypeGoodsTaxType goodsTaxType = HTypeGoodsTaxType.OUT_TAX;

    // アイコン
    /**
     * 商品アイコンリスト
     */
    private List<GoodsIconItem> goodsIconItems;

    /**
     * 商品グループ在庫表示Pc
     */
    private String stockStatusPc;

    /**
     * 商品グループ在庫表示フラグ
     */
    private boolean stockStatusDisplay;

    /**
     * 商品説明1
     */
    private String goodsNote1;

    // あしあと商品のみで使用 >>>>>>>>>>>>>>>>>>>>>>>>>
    /**
     * 公開状態
     */
    private HTypeOpenDeleteStatus goodsOpenStatus;

    /**
     * 公開開始日時
     */
    private Timestamp openStartTime;

    /**
     * 公開終了日時
     */
    private Timestamp openEndTime;

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    // PDR Migrate Customization from here
    /** 販売可能商品区分 */
    private HTypeSalableGoodsType salableGoodsType;

    /** シリーズセール価格整合性フラグ */
    private boolean groupSalePriceIntegrityFlag;

    /** SALEアイコンフラグ */
    private boolean saleIconFlag;

    /** お取りおきアイコンフラグ */
    private boolean reserveIconFlag;

    // 2023-renew No36-1, No61,67,95 from here
    /** アウトレットアイコンフラグ */
    private boolean outletIconFlag;
    // 2023-renew No36-1, No61,67,95 to here
    /** NEWアイコンフラグ */
    private boolean newIconFlag;

    /** シリーズ価格記号表示フラグ */
    private boolean priceMarkDispFlag;

    /** シリーズセール価格記号表示フラグ */
    private boolean salePriceMarkDispFlag;

    // 2023-renew No36-1, No61,67,95 from here
    /** 商品状態 */
    private String itemStatus;

    /**
     * 値引前最大価格(税抜)
     */
    private BigDecimal maxPrice;

    /**
     * 値引後最大価格(税抜)
     */
    private BigDecimal minPrice;

    /**
     * 値引後最大価格(税抜)
     */
    private BigDecimal saleMaxPrice;

    /**
     * 値引後最大価格(税抜)
     */
    private BigDecimal saleMinPrice;
    // 2023-renew No36-1, No61,67,95 to here

    /**
     * 販売可能商品区分が価格非表示（ログイン前）かどうか
     *
     * @return true=価格非表示（ログイン前）、false=価格非表示（ログイン前）
     */
    public boolean isPriceHideGoods() {
        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
        return !commonInfoUtility.isLogin(ApplicationContextUtility.getBean(CommonInfo.class));
    }
    //PDR Migrate Customization to here

    /**
     * 新着日付が現在の時刻を過ぎていないか判断
     *
     * @return true:新着日付、false:新着日付を過ぎている
     */
    public boolean isNewDate() {
        if (whatsnewDate == null) {
            return false;
        }
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        return whatsnewDate.compareTo(dateUtility.getCurrentDate()) >= 0;
    }

    /**
     * 在庫状態:非販売
     * （アイコン表示用）
     *
     * @return true：非販売
     */
    public boolean isStockNoSaleIconDisp() {
        return HTypeStockStatusType.NO_SALE.getValue().equals(stockStatusPc);
    }

    /**
     * 在庫状態:販売期間終了
     * （アイコン表示用）
     *
     * @return true：販売期間終了
     */
    public boolean isStockSoldOutIconDisp() {
        return HTypeStockStatusType.SOLDOUT.getValue().equals(stockStatusPc);
    }

    /**
     * 在庫状態:販売前
     * （アイコン表示用）
     *
     * @return true：販売前
     */
    public boolean isStockBeforeSaleIconDisp() {
        return HTypeStockStatusType.BEFORE_SALE.getValue().equals(stockStatusPc);
    }

    /**
     * 在庫状態:在庫なし
     * （アイコン表示用）
     *
     * @return true：在庫なし
     */
    public boolean isStockNoStockIconDisp() {
        return HTypeStockStatusType.STOCK_NOSTOCK.getValue().equals(stockStatusPc);
    }

    /**
     * 在庫状態:残りわずか
     * （アイコン表示用）
     *
     * @return true：残りわずか
     */
    public boolean isStockFewIconDisp() {
        return HTypeStockStatusType.STOCK_FEW.getValue().equals(stockStatusPc);
    }

    /**
     * 在庫状態:在庫あり
     * （アイコン表示用）
     *
     * @return true：在庫あり
     */
    public boolean isStockPossibleSalesIconDisp() {
        return HTypeStockStatusType.STOCK_POSSIBLE_SALES.getValue().equals(stockStatusPc);
    }

    // あしあと商品のみで使用 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * 商品公開判定<br/>
     *
     * @return true=公開、false=公開でない
     */
    public boolean isGoodsOpen() {
        // 商品系Helper取得
        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);
        return goodsUtility.isGoodsOpen(goodsOpenStatus, openStartTime, openEndTime);
    }
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 商品表示値引き前単価の表示可否
     *
     * @return 表示可はtrue、それ以外はfalse
     */
    public boolean isDisplayPreDiscountPrice() {
        // 2023-renew AddNo5 from here
        return goodsSalePriceLow != null || goodsSalePriceHigh != null;
        // 2023-renew AddNo5 to here
    }

    //  PDR Customzation from here

    /**
     * セール価格<br/>
     *
     * @return true:セール価格あり / false:セール価格なし
     */
    public boolean isSaleUni() {
        return saleIconFlag;
    }

    /**
     * セール価格<br/>
     *
     * @return true:セール価格あり / false:セール価格なし
     */
    public boolean isSale() {
        // 2023-renew AddNo5 from here
        if ((goodsSalePriceLow != null && goodsSalePriceLow.compareTo(BigDecimal.ZERO) != 0)
            || (goodsSalePriceHigh != null && goodsSalePriceHigh.compareTo(BigDecimal.ZERO) != 0)) {
            return true;
        }
        // 2023-renew AddNo5 to here
        return false;
    }

    /**
     * 商品情報表示判定<br/>
     * <br/>
     * 以下の場合は商品情報を非表示にする。<br/>
     * ・販売可能商品区分が閲覧不可(ログイン前)の場合<br/>
     *
     * @return true=表示する、false=表示しない
     */
    public boolean isNoViewGoodsNoLogin() {

        if (HTypeSalableGoodsType.NOT_VIEW.equals(salableGoodsType)) {
            return true;
        }
        return false;
    }

    /**
     * 商品情報表示判定<br/>
     * <br/>
     * 以下の場合は商品情報を非表示にする。<br/>
     * ・販売可能商品区分が閲覧不可(ログイン後)の場合<br/>
     *
     * @return true=表示する、false=表示しない
     */
    public boolean isNoViewGoodsLogin() {

        if (HTypeSalableGoodsType.NOT_VIEW_LOGIN.equals(salableGoodsType)) {
            return true;
        }
        return false;
    }

    /**
     * 商品情報表示判定<br/>
     * <br/>
     * 以下の場合は商品価格情報を非表示にする。<br/>
     * ・販売可能商品区分が価格非表示(ログイン前)の場合<br/>
     *
     * @return true=表示する、false=表示しない
     */
    public boolean isPriceHide() {
        if (HTypeSalableGoodsType.PRICE_HIDE.equals(salableGoodsType)) {
            return true;
        }
        return false;
    }

    // 2023-renew No36-1, No61,67,95 from here

    /**
     * ユニサーチの返却値より在庫切れか判定する
     */
    public boolean isNoStockUni() {
        return UK_NOSTOCK_STATUS.equals(itemStatus);
    }

    /**
     * ユニサーチの返却値より公開終了か判定する
     */
    public boolean isNoSaleUni() {
        return UK_NOSALE_STATUS.equals(itemStatus);
    }
    // 2023-renew No36-1, No61,67,95 to here

    // 2023-renew AddNo5 from here
    /**
     * 最低価格が最高価格と等しい
     */
    public boolean isSamePrice() {
        if (this.goodsPriceLow == null && this.goodsPriceHigh == null) {
            return true;
        } else if (this.goodsPriceLow == null || this.goodsPriceHigh == null) {
            return false;
        } else {
            return this.goodsPriceLow.compareTo(this.goodsPriceHigh) == 0;
        }
    }

    /**
     * 最低セール価格が最高セール価格と等しい
     */
    public boolean isSameSalePrice() {
        if (this.goodsSalePriceLow == null && this.goodsSalePriceHigh == null) {
            return true;
        } else if (this.goodsSalePriceLow == null || this.goodsSalePriceHigh == null) {
            return false;
        } else {
            return this.goodsSalePriceLow.compareTo(this.goodsSalePriceHigh) == 0;
        }
    }
    // 2023-renew AddNo5 to here
    //  PDR Customzation to here
}
