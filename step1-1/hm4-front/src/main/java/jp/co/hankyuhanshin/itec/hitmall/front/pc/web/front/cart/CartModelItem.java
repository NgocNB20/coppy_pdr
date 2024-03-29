/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCNumber;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVNumber;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalableGoodsType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleCheckType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleControlType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart.validation.group.CartGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart.validation.group.DoAddCouponGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * PDR#013 09_データ連携（受注データ）<br/>
 * カートModel カート商品Item
 *
 * @author kaneda
 */
@Data
@Component
@Scope("prototype")
public class CartModelItem implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * カート商品No
     */
    private Integer cartNo;

    /**
     * カートSEQ
     */
    private Integer cartSeq;

    /**
     * 商品SEQ
     */
    private Integer goodsSeq;

    /**
     * 商品コード
     */
    private String gcd;

    /**
     * 商品名
     */
    private String goodsGroupName;

    /**
     * 規格タイトル1
     */
    private String unitTitle1;

    /**
     * 規格値1
     */
    private String unitValue1;

    /**
     * 規格タイトル2
     */
    private String unitTitle2;

    /**
     * 規格値2
     */
    private String unitValue2;

    /**
     * 商品画像アイテム
     */
    private List<String> goodsImageItems;

    /**
     * 商品単価(税抜き)
     */
    @HCNumber
    private BigDecimal goodsPrice;

    /**
     * 商品単価(税込み)
     */
    @HCNumber
    private BigDecimal goodsPriceInTax;

    /**
     * 商品数量
     */
    @NotEmpty(groups = {CartGroup.class, DoAddCouponGroup.class})
    @HVNumber(groups = {CartGroup.class, DoAddCouponGroup.class})
    // PDR Migrate Customization from here
    @Range(min = 1, max = 999, groups = {CartGroup.class, DoAddCouponGroup.class})
    // PDR Migrate Customization to here
    @HCNumber
    private String gcnt;

    /**
     * 商品金額（税抜き）
     */
    private BigDecimal goodsTotalPrice;

    /**
     * 商品金額（税込み）
     */
    private BigDecimal goodsTotalPriceInTax;

    /**
     * 値引き前単価＝値引前単価（税抜き）<br/>
     */
    private BigDecimal preDiscountPrice;

    /**
     * 値引き前単価＝値引前単価（税込み）<br/>
     */
    private BigDecimal preDisCountPriceInTax;

    /**
     * 値引きコメント
     */
    private String goodsPreDiscountPrice;

    /**
     * 商品表示単価価格帯フラグ
     */
    private boolean goodsDisplayPriceRange;

    /**
     * 商品表示値引き前単価価格帯フラグ
     */
    private boolean goodsDisplayPreDiscountPriceRange;

    // HTMLへの追加のみで表示できるよう保持
    /**
     * 商品納期
     */
    private String deliveryType;

    /**
     * 商品お知らせ情報
     */
    private String goodsInformation;

    /**
     * お気に入りボタン表示フラグ
     */
    private boolean favoriteButtonView;

    /**
     * 商品グループSEQ
     */
    private Integer goodsGroupSeq;

    /**
     * 商品グループコード
     */
    private String ggcd;

    /**
     * 商品グループ画像
     */
    private String goodsGroupImageFileName;

    /**
     * 新着日付
     */
    private Timestamp whatsnewDate;

    /**
     * 商品アイコンリスト
     */
    private List<CartModelItem> goodsIconItems;

    /**
     * 商品アイコン名
     */
    private String iconName;

    /**
     * 商品アイコンカラーコード
     */
    private String iconColorCode;

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

    /**
     * 販売状態
     */
    private HTypeGoodsSaleStatus saleStatus;

    /**
     * 販売開始日時
     */
    private Timestamp saleStartTime;

    /**
     * 販売終了日時
     */
    private Timestamp saleEndTime;

    /**
     * 商品消費税種別
     */
    private HTypeGoodsTaxType goodsTaxType;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 酒類フラグ
     */
    private HTypeAlcoholFlag alcoholFlag;

    /**
     * 一覧用在庫状況表示
     */
    private String listStockStatusPc;

    /**
     * 個別配送フラグ
     */
    private String individualDeliveryType;

    // PDR Migrate Customization from here
    /**
     * 陸送商品
     */
    private String landSendFlagDisp;

    /**
     * クール便商品
     */
    private String coolSendFlagDisp;

    /**
     * SALEアイコンフラグ
     */
    private boolean saleIconFlag;

    /**
     * 販売可能商品区分
     */
    private HTypeSalableGoodsType salableGoodsType;

    /**
     * シリーズセール価格整合性フラグ
     */
    private boolean groupSalePriceIntegrityFlag;

    /**
     * お取りおきアイコンフラグ
     */
    private boolean reserveIconFlag;

    /**
     * NEWアイコンフラグ
     */
    private boolean newIconFlag;

    // 2023-renew No92 from here
    /** アウトレットアイコンフラグ */
    private boolean outletIconFlag;
    // 2023-renew No92 to here

    /**
     * セール価格整合性フラグ
     */
    private boolean salePriceIntegrityFlag;

    /**
     * 振替前心意気商品コード
     */
    private String beforeTransferEmotionGoodsCode;

    /**
     * 心意気商品フラグ
     */
    private boolean emotionPriceGoods;

    /**
     * 商品詳細ページ遷移先用の商品コード（心意気商品の場合に振替前の商品に切り替えるため）
     */
    private String gcdForHref;

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

    // 2023-renew No11 from here
    /** 在庫 */
    private String stock;

    /** 販売制御区分 */
    private HTypeSaleControlType saleControl;

    /** 販売可否判定 */
    private Integer saleCheck;
    // 2023-renew No11 to here
    // PDR Migrate Customization to here

    /**
     * お気に入り表示判定<br/>
     * お気に入り登録済みなら非表示<br/>
     * true：表示、false：非表示
     */
    private boolean favoriteView = true;

    // 2023-renew No14 from here
    /**
     * 数量割引フラグ
     */
    private boolean quantityDiscount;

    /**
     * セールde予約可能かどうか（今すぐお届け用）
     */
    private boolean reserveFlag;

    /**
     * セールde予約変更可能かどうか（セールde予約用）
     */
    private boolean reserveEditFlag;

    /**
     * お届け希望日（セールde予約用）
     */
    private Timestamp reserveDeliveryDate;

    /**
     * 今すぐお届け数量（確認ダイアログ用）
     */
    private int countQuantityDeliveryNow;

    /**
     * セールde予約数量（確認ダイアログ用）
     */
    private int countQuantityReserve;
    // 2023-renew No14 to here

    /**
     * カートに入れるボタンの表示判定
     * <br/>
     * 以下の場合のみカートに入れるボタンを表示する<br/>
     * ・公開状態：公開<br/>
     * ・販売状態：販売<br/>
     * ・在庫状況：在庫なし以外<br/>
     * <p>
     * 以下今回用に追加<br/>
     * ・販売可能商品区分：購入可能(ログイン後)<br/>
     *
     * @return true=表示する、false=表示しない
     */
    public boolean isViewCartIn() {
        // PDR Migrate Customization from here
        return isGoodsOpen() && isGoodsSales() && !isNoStockIconDisp() && isSaleOKGoods();
        // PDR Migrate Customization to here
    }

    /**
     * 個別配送商品フラグ
     * 個別配送商品の場合、カート商品備考欄に「個別配送商品」と表示する<br/>
     *
     * @return true..個別配送商品 / false..個別配送商品でない
     */
    public boolean isIndividualDeliveryTypeView() {
        return StringUtils.isNotEmpty(individualDeliveryType);
    }

    /**
     * 納期が設定されているか確認<br/>
     * true:設定されている<br/>
     *
     * @return true:納期が設定されている
     */
    public boolean isViewDeliveryType() {
        return StringUtils.isNotEmpty(deliveryType);
    }

    /**
     * 商品グループ画像をセット（サムネイル画像）
     *
     * @return goodsName
     */
    public String getGoodsGroupImageThumbnailAlt() {
        String name = null;
        if (StringUtils.isNotEmpty(goodsGroupName)) {
            name = goodsGroupName;
        }
        return name;
    }

    /**
     * 在庫状態:在庫なし
     * （一覧アイコン表示用）
     *
     * @return true：在庫なし
     */
    public boolean isNoStockIconDisp() {
        return HTypeStockStatusType.STOCK_NOSTOCK.getValue().equals(listStockStatusPc);
    }

    /**
     * 在庫状態:在庫あり
     * （一覧アイコン表示用）
     *
     * @return true：販売期間終了
     */
    public boolean isSellOutIconDisp() {
        return HTypeStockStatusType.SOLDOUT.getValue().equals(listStockStatusPc);
    }

    /**
     * 在庫状態:販売前
     * （一覧アイコン表示用）
     *
     * @return true：販売前
     */
    public boolean isBeforeSaleIconDisp() {
        return HTypeStockStatusType.BEFORE_SALE.getValue().equals(listStockStatusPc);
    }

    /**
     * 在庫状態:在庫あり
     * （一覧アイコン表示用）
     *
     * @return true：在庫あり
     */
    public boolean isStockPossibleSalesIconDisp() {
        return HTypeStockStatusType.STOCK_POSSIBLE_SALES.getValue().equals(listStockStatusPc);
    }

    /**
     * 在庫状態:残りわずか
     * （一覧アイコン表示用）
     *
     * @return true：残りわずか
     */
    public boolean isStockFewIconDisp() {
        return HTypeStockStatusType.STOCK_FEW.getValue().equals(listStockStatusPc);
    }

    /**
     * 在庫状態:非販売
     * （一覧アイコン表示用）
     *
     * @return true：非販売
     */
    public boolean isNoSaleIconDisp() {
        return HTypeStockStatusType.NO_SALE.getValue().equals(listStockStatusPc);
    }

    /**
     * 在庫状態:公開前
     * （一覧アイコン表示用）
     *
     * @return true：公開前
     */
    public boolean isStockBeforeOpenIconDisp() {
        return HTypeStockStatusType.BEFORE_OPEN.getValue().equals(listStockStatusPc);
    }

    /**
     * 在庫状態:公開終了
     * （一覧アイコン表示用）
     *
     * @return true：公開終了
     */
    public boolean isStockOpenEndIconDisp() {
        return HTypeStockStatusType.OPEN_END.getValue().equals(listStockStatusPc);
    }

    /**
     * 在庫状態:非公開
     * （一覧アイコン表示用）
     *
     * @return true：非公開
     */
    public boolean isStockNoOpenIconDisp() {
        return HTypeStockStatusType.NO_OPEN.getValue().equals(listStockStatusPc);
    }

    /**
     * 在庫状態:予約受付前
     * （一覧アイコン表示用）
     *
     * @return true：予約受付前
     */
    public boolean isStockBeforeReservationDisp() {
        return HTypeStockStatusType.BEFORE_RESERVATIONS.getValue().equals(listStockStatusPc);
    }

    /**
     * 在庫状態:予約受付中
     * （一覧アイコン表示用）
     *
     * @return true：予約受付中
     */
    public boolean isStockOnReservationDisp() {
        return HTypeStockStatusType.ON_RESERVATIONS.getValue().equals(listStockStatusPc);
    }

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

    /**
     * 商品販売判定<br/>
     *
     * @return true=公開、false=公開でない
     */
    public boolean isGoodsSales() {
        // 商品系Helper取得
        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);
        return goodsUtility.isGoodsSales(saleStatus, saleStartTime, saleEndTime);
    }

    /**
     * 酒類判定
     *
     * @return 酒類
     */
    public boolean isDispAlcohol() {
        return HTypeAlcoholFlag.ALCOHOL.equals(alcoholFlag);
    }

    /**
     * 規格1の有無判定<br/>
     *
     * @return true=有、false=無
     */
    public boolean isUnit1() {
        return StringUtils.isNotEmpty(unitTitle1);
    }

    /**
     * 規格2の有無判定<br/>
     *
     * @return true=有、false=無
     */
    public boolean isUnit2() {
        return StringUtils.isNotEmpty(unitTitle2);
    }

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
     * 「値引き前単価＝値引前単価」フィールドに値が存在するかどうかを判定する
     *
     * @return TRUE：値が存在する、FALSE：値が存在しない
     */
    public boolean isPreDiscountPrice() {
        return preDiscountPrice != null;
    }

    // PDR Migrate Customization from here

    /**
     * 陸送商品表示フラグ<br/>
     * 陸送商品の場合、備考欄に「陸送商品」と表示<br/>
     *
     * @return true=表示する、false=表示しない
     */
    public boolean isLandSendFlag() {
        return StringUtil.isNotEmpty(landSendFlagDisp);
    }

    /**
     * クール便表示フラグ<br/>
     * クール便の場合、備考欄に「クール便」と表示<br/>
     *
     * @return true=表示する、false=表示しない
     */
    public boolean isCoolSendFlag() {
        return StringUtil.isNotEmpty(coolSendFlagDisp);
    }

    /**
     * 販売可能商品区分が購入可能（ログイン後）かどうか
     *
     * @return true=購入可能（ログイン後）、false=購入可能（ログイン後）かどうか
     */
    public boolean isSaleOKGoods() {
        return HTypeSalableGoodsType.SALEON.equals(salableGoodsType);
    }

    /**
     * 販売可能商品区分が購入不可（ログイン後）かどうか
     *
     * @return true=購入不可（ログイン後）、false=購入不可（ログイン後）でない
     */
    public boolean isSaleNGGoods() {
        return HTypeSalableGoodsType.SALEOFF.equals(salableGoodsType);
    }

    /**
     * 販売可能商品区分が閲覧不可（ログイン後）かどうか
     *
     * @return true=閲覧不可（ログイン後）、false=閲覧不可（ログイン後）でない
     */
    public boolean isNoDispGoods() {
        return HTypeSalableGoodsType.NOT_VIEW_LOGIN.equals(salableGoodsType);
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
     * カート投入されている商品の商品詳細リンクを表示するか<br/>
     * <pre>
     * 心意気商品の場合は振替前商品の詳細ページを表示
     * </pre>
     *
     * @return true=表示する、false=表示しない
     */
    public boolean isDispGoodsLink() {

        // 商品公開判定
        if (!isGoodsOpen()) {
            // 非公開の場合
            if (emotionPriceGoods && !StringUtil.isEmpty(beforeTransferEmotionGoodsCode)) {
                // 心意気商品 かつ 振替前商品コードが存在する場合は表示
                return true;
            }
            return false;
        }

        return true;
    }

    /**
     * お気に入りボタン表示フラグ<br/>
     *
     * @return true=表示する、false=表示しない
     */
    public boolean isDispFavoriteButton() {
        return favoriteButtonView && !emotionPriceGoods;
    }

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

    // 2023-renew No11 from here
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
    // 2023-renew No11 to here
    // PDR Migrate Customization to here
}
