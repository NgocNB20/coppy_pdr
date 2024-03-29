/**
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods;

import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDiscountsType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleCheckType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleControlType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common.GoodsItem;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 在庫商品Item
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 *
 */
@Data
@Component
@Scope("prototype")
@Primary
public class GoodsStockItem extends GoodsItem {
    /** 在庫管理フラグ*/
    private HTypeStockManagementFlag stockManagementFlag;

    // HTMLへの追加のみで表示できるよう保持
    /** 在庫状態文字列 */
    private String stockTextType;

    // HTMLへの追加のみで表示できるよう保持
    /** 商品個別配送種別 */
    private HTypeIndividualDeliveryType individualDeliveryType;

    /** 規格管理フラグ */
    private String unitManagementFlag;

    /** 販売可能在庫数 */
    private BigDecimal salesPossibleStock;

    // 2023-renew No11 from here
    /** 販売制御区分 */
    private HTypeSaleControlType saleControl;

    /** 在庫数 */
    private BigDecimal stockQuantity;

    /** 取りおき可否 */
    private String reserveFlag;

    /** 販売可否判定 */
    private Integer saleCheck;

    /** 割引適用区分 */
    private HTypeDiscountsType saleType;

    /** 心意気価格 */
    private String saleEmotionPrice;

    // 2023-renew No11 to here

    // 2023-renew No92 from here
    /** 価格(最低) */
    private BigDecimal goodsPriceInTaxLow;

    /** 価格（最高） */
    private BigDecimal goodsPriceInTaxHight;

    /** セール価格（最低） */
    private BigDecimal preDiscountPriceLow;

    /** セール価格（最高） */
    private BigDecimal preDiscountPriceHight;

    /** 後継品代替品情報リスト */
    private GoodsStockItem otherGoodsItem;

    /** アウトレットアイコンフラグ */
    private boolean outletIconFlag;
    // 2023-renew No92 to here

    // 2023-renew No2 from here
    /**
     * 販売可否判定結果<br/>
     *
     * @return true:販売不可 / false:販売可
     */
    public boolean isNoGoodsStockItemSaleCheck() {
        return saleCheck == null || saleCheck != 1;
    }
    // 2023-renew No2 to here

    /**
     * 規格管理チェック
     *
     * @return true:規格管理する
     */
    public boolean isUnitManage() {
        return HTypeUnitManagementFlag.ON.getValue().equals(unitManagementFlag);
    }

    /**
     * 商品規格２の存在チェック
     *
     * @return true:あり
     */
    public boolean isUseUnit2() {
        return StringUtils.isNotEmpty(getUnitTitle2());
    }

    /**
     * 在庫状態：在庫あり
     *
     * @return true.在庫あり
     */
    public boolean isStockPossibleSales() {
        return HTypeStockStatusType.STOCK_POSSIBLE_SALES.getValue().equals(stockTextType);
    }

    /**
     * 在庫状態：残小
     *
     * @return true.残小
     */
    public boolean isStockFew() {
        return HTypeStockStatusType.STOCK_FEW.getValue().equals(stockTextType);
    }

    /**
     * 在庫状態：在庫切れ
     *
     * @return true.在庫切れ
     */
    public boolean isStockNoStock() {
        return HTypeStockStatusType.STOCK_NOSTOCK.getValue().equals(stockTextType);
    }

    /**
     * 値引き前単価の有無を検査する
     * @return 登録有りの場合はtrue、それ以外はfalse
     */
    public boolean isDisplayPreDiscountPrice() {
        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);
        return goodsUtility.isDisplayPreDiscountPrice(preDiscountPrice);
    }

    // 2023-renew No11 from here
    /**
     * お気に入りボタンを表示する
     *
     * @return 販売制御状態=null および 商品販売判定公開: true、それ以外は: false
     */
    public boolean isPossibleAddFavorite() {
        if (Objects.isNull(saleControl)) {
            return false;
        }
        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);
        String goodsIconDisplay = goodsUtility.getGoodsIconDisplay(this);

        return this.isGoodsSales()
               && StringUtils.isBlank(goodsIconDisplay);
    }

    /**
     * セールde予約ボタンを表示
     *
     * @return 取りおき可否 = 1：取りおき可はtrue、それ以外はfalse
     */
    public boolean isDisplaySaleDeReserve() {
        if (!Objects.isNull(reserveFlag) && HTypeReserveFlag.ON.getValue().equals(reserveFlag)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * カートインボタンを表示
     *
     * @return true=表示する、false=表示しない
     */
    public boolean isDisplayAddCart() {
        if (Objects.isNull(saleControl) || Objects.isNull(saleCheck)) {
            return false;
        }
        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);
        String goodsIconDisplay = goodsUtility.getGoodsIconDisplay(this);

        return this.isGoodsSales()
               && StringUtils.isBlank(goodsIconDisplay)
               && HTypeSaleCheckType.YES.getValue().equals(saleCheck.toString());
    }

    /**
     * 販売不可状態のアイコンを表示
     *
     * @return true=表示する、false=表示しない
     */
    public String iconDisplayGoodsUnit() {
        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);
        String goodsIconDisplay = goodsUtility.getGoodsIconDisplay(this);

        if (!StringUtils.isBlank(goodsIconDisplay)) {
            return goodsIconDisplay;
        }

        return null;
    }

    /**
     * 入荷お知らせ設定する
     *
     * @return true=表示する、false=表示しない
     */
    public boolean isDisplayArrivalNotification() {
        if (Objects.isNull(saleControl) || Objects.isNull(stockQuantity) || Objects.isNull(getSaleStatus())) {
            return false;
        }
        if (!HTypeSaleControlType.NOBACKORDER.equals(saleControl)
            && !HTypeSaleControlType.LIMITEDSTOCKNORMAL.equals(saleControl)) {
            return false;
        }
        return ((HTypeGoodsSaleStatus.SALE.equals(getSaleStatus()) || HTypeGoodsSaleStatus.NO_SALE.equals(getSaleStatus()))
                && stockQuantity.compareTo(BigDecimal.ZERO) == 0)
               || (HTypeGoodsSaleStatus.NO_SALE.equals(getSaleStatus()) && stockQuantity.compareTo(BigDecimal.ZERO) == 1);
    }


    /**
     * 規格単位の価格情報を表示
     *
     * @return true=表示する、false=表示しない
     */
    public boolean isDisplayDiscountFlag() {
        if (Objects.isNull(saleEmotionPrice) || !HTypeDiscountsType.SALEON_EMOTION_PRICE.equals(saleType)) {
            return false;
        }
        return true;
    }

    /**
     * カートインボタンを表示
     *
     * @return true=表示する、false=表示しない
     */
    public boolean isValidSaleEndDay() {
        if (Objects.isNull(super.getSaleEndDay()) || Objects.isNull(saleControl) || Objects.isNull(saleCheck)) {
            return false;
        }

        return this.isGoodsSales()
               && !HTypeSaleControlType.DISCONTINUED.equals(saleControl)
               && HTypeSaleCheckType.YES.getValue().equals(saleCheck.toString());
    }
    // 2023-renew No11 to here

    // 2023-renew AddNo5 from here
    /**
     * セール価格<br/>
     *
     * @return true:セール価格あり / false:セール価格なし
     */
    public boolean isSale() {
        if ((preDiscountPriceLow != null && preDiscountPriceLow.compareTo(BigDecimal.ZERO) != 0)
            || (preDiscountPriceHight != null && preDiscountPriceHight.compareTo(BigDecimal.ZERO) != 0)) {
            return true;
        }
        return false;
    }

    /**
     * 最低価格が最高価格と等しい
     */
    public boolean isSamePrice() {
        if (this.goodsPriceInTaxLow == null && this.goodsPriceInTaxHight == null) {
            return true;
        } else if (this.goodsPriceInTaxLow == null || this.goodsPriceInTaxHight == null) {
            return false;
        } else {
            return this.goodsPriceInTaxLow.compareTo(this.goodsPriceInTaxHight) == 0;
        }
    }

    /**
     * 最低セール価格が最高セール価格と等しい
     */
    public boolean isSameSalePrice() {
        if (this.preDiscountPriceLow == null && this.preDiscountPriceHight == null) {
            return true;
        } else if (this.preDiscountPriceLow == null || this.preDiscountPriceHight == null) {
            return false;
        } else {
            return this.preDiscountPriceLow.compareTo(this.preDiscountPriceHight) == 0;
        }
    }
    // 2023-renew AddNo5 to here

    // 2023-renew No10 from here
    /**
     * Display price when アイコン表示 is not 販売終了しました、取扱中止
     *
     * @return
     */
    public boolean isDisplayPrice() {
        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);
        String goodsIconDisplay = goodsUtility.getGoodsIconDisplay(this);

        if (!StringUtils.isBlank(goodsIconDisplay) && (goodsIconDisplay.equals(GoodsUtility.ICON_SERVICE_STOP_TEXT) ||
                                                       goodsIconDisplay.equals(GoodsUtility.ICON_DISCONTINUED_TEXT))) {
            return false;
        }

        return true;
    }
    // 2023-renew No10 to here

    // 2023-renew No65 from here
    /**
     * 在庫数表示<br/>
     *
     * @return true=表示する、false=表示しない
     */
    public boolean isHasStockQuantity() {
        return !Objects.isNull(stockQuantity) && stockQuantity.compareTo(BigDecimal.ZERO) != 0;
    }
    // 2023-renew No65 to here
}
