/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCNumber;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetCouponListResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart.validation.group.DoAddCouponGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import org.apache.commons.collections.MapUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * PDR#013 09_データ連携（受注データ）<br/>
 * ショッピングカートModel
 *
 * @author kaneda
 */
@Data
@Component
@Scope("prototype")
public class CartModel extends AbstractModel {

    /**
     * カート投入エラー
     */
    public static final String MSGCD_CART_ADD_ERROR = "ACX000101";

    /**
     * カートエラー
     */
    public static final String MSGCD_CART_ERROR = "ACX000102";

    /**
     * 個別配送商品エラー
     */
    public static final String MSGCD_INDIVIDUAL_DELIVERY_ERROR = "ACX000103";

    /**
     * ページ再表示 のメッセージ
     */
    public static final String MSGCD_PAGE_RELOAD = "ACX000104";

    /**
     * カート投入_公開エラー
     */
    public static final String MSGCD_CART_OPEN_ERROR = "ACX000106";

    /**
     * カート投入_販売エラー
     */
    public static final String MSGCD_CART_SALES_ERROR = "ACX000107";

    /**
     * カート投入_在庫切れエラー
     */
    public static final String MSGCD_CART_STOCK_ERROR = "ACX000108";

    /**
     * カート投入_在庫不足
     */
    public static final String MSGCD_CART_LESS_STOCK_ERROR = "ACX000109";

    // PDR Migrate Customization from here
    /**
     * 商品合計金額超過チェックメッセージ<br/>
     * <code>MSGCD_CART_GOODS_MAX_OVER</code>
     */
    public static final String MSGCD_CART_TOTAL_PRICE_MAX_OVER = "PDR-0004-003-A-";

    /**
     * 送料無料基準注文金額
     */
    public static final String FREESHIPPING_STANDARD_AMOUNT = "freeshipping.standard.amount";

    // PDR Migrate Customization to here

    // 2023-renew No24 from here
    /**
     * クーポンを「利用しない」場合のvalue
     */
    public static final String DEFAULT_UNSELECTED_COUPON_VALUE = "-1";
    // 2023-renew No24 to here

    // 2023-renew No11 from here
    /** 在庫表示：在庫あり */
    public static final String DISP_IN_STOCK = "○";

    /** 在庫表示：在庫わずか */
    public static final String DISP_LITTLE_STOCK = "△";

    /** 在庫表示：在庫なし */
    public static final String DISP_OUT_OF_STOCK = "×";
    // 2023-renew No11 to here

    // 2023-renew No3-taglog from here
    /**
     * クイックオーダーからカートインされた商品情報を保持する。 ※UK連携に利用
     */
    public Map<String, String> catalogCartInMap;

    /**
     * UK連携値
     */
    public String catalogCartInString;
    // 2023-renew No3-taglog to here

    /* 各フィールド */

    /**
     * 商品合計数量
     */
    private BigDecimal goodsCountTotal;

    /**
     * 商品合計金額(税抜き)
     */
    private BigDecimal priceTotal;

    /**
     * 商品合計金額(税込み)
     */
    private BigDecimal priceTotalInTax;

    /* 各種Items */

    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品リスト
     */
    private List<CartModelItem> togetherBuyGoodsItems;

    /**
     * よく一緒に購入される商品リストのインデックス
     */
    private int togetherBuyGoodsLimit;
    // 2023-renew No21 to here

    /**
     * お気に入り情報リスト
     */
    private List<CartModelItem> favoriteGoodsItems;

    /**
     * お気に入り情報リストのインデックス
     */
    private int favoriteGoodsIndex;

    /**
     * お気に入り情報リスト表示最大件数
     */
    private int favoriteGoodsLimit;

    /**
     * お気に入り商品リスト(":"区切り)
     */
    private String favoriteGoodsCodeList;

    /**
     * あしあと情報リスト
     */
    private List<CartModelItem> footPrintGoodsItems;

    /**
     * あしあと情報リストのインデックス
     */
    private int footPrintGoodsIndex;

    /**
     * あしあと情報リスト表示最大件数
     */
    private int footPrintGoodsLimit;

    /**
     * 関連商品情報リストのインデックス
     */
    private int relatedGoodsIndex;

    /**
     * 関連商品情報リスト表示最大件数
     */
    private int relatedGoodsLimit;

    /**
     * カート一覧情報（今すぐお届け）
     */
    @Valid
    private List<CartModelItem> cartGoodsItems;

    /**
     * カート一覧情報（今すぐお届け）のインデックス（削除用）
     */
    private int cartGoodsIndex;

    // 2023-renew No14 from here
    /**
     * カート一覧情報（セールde予約）
     */
    @Valid
    private Map<String, List<CartModelItem>> cartGoodsReserveItemMap;

    /**
     * カート一覧情報（セールde予約）のインデックス代わりの商品コード（削除用）
     */
    private String cartGoodsReserveGoodsCode;

    /**
     * 今すぐお届け／セールde予約内の同一商品チェック用リスト（確認ダイアログ用）
     */
    private List<CartModelItem> duplicationGoodsItems;
    // 2023-renew No14 to here

    /**
     * カートチェック<br/>
     *
     * @return true:カート内が空ではない
     */
    public boolean isCartIn() {
        // 2023-renew No14 from here
        return isCartGoodsIn() || isCartGoodsReserveIn();
        // 2023-renew No14 to here
    }

    // 2023-renew No14 from here

    /**
     * 今すぐお届け商品チェック<br/>
     *
     * @return true:今すぐお届け商品が空ではない
     */
    public boolean isCartGoodsIn() {
        return CollectionUtil.getSize(cartGoodsItems) > 0;
    }

    /**
     * セールde予約チェック<br/>
     *
     * @return true:セールde予約商品が空ではない
     */
    public boolean isCartGoodsReserveIn() {
        return MapUtils.isNotEmpty(cartGoodsReserveItemMap);
    }

    // 2023-renew No14 to here

    /**
     * お気に入り情報表示チェック<br/>
     *
     * @return true:表示
     */
    public boolean isViewFavoriteGoods() {
        return CollectionUtil.getSize(favoriteGoodsItems) > 0;
    }

    // PDR Migrate Customization from here

    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品表示チェック<br/>
     *
     * @return true:表示
     */
    public boolean isViewTogetherBuyGoods() {
        return CollectionUtil.getSize(togetherBuyGoodsItems) > 0;
    }
    // 2023-renew No21 to here

    /**
     * 商品金額(税抜き)
     */
    @HCNumber
    private BigDecimal goodsTotalPriceNoTax;

    /**
     * 消費税額
     */
    @HCNumber
    private BigDecimal goodsTotalTax;

    /**
     * ecコンシェル連携用 商品合計金額
     */
    private Integer eccCartTotalPrice;

    /**
     * 送料無料まで○円
     */
    @HCNumber
    private BigDecimal freeShippingDifference;

    /**
     * 送料無料の基準金額
     */
    @HCNumber
    private BigDecimal standardAmount;

    // 2023-renew No24 from here
    /**
     * クーポンコード（入力）
     */
    @NotEmpty(message = "{PDR-0436-013-A-E}", groups = DoAddCouponGroup.class)
    @Pattern(regexp = "[0-9a-zA-Z]+", message = "{PDR-0436-012-A-E}", groups = DoAddCouponGroup.class)
    @Length(max = 10, message = "{PDR-0436-003-A-E}", groups = DoAddCouponGroup.class)
    private String couponCodeAdd;

    /**
     * クーポンコード（選択中）
     */
    private String couponCode;

    /**
     * 取得済みクーポン一覧
     */
    public List<WebApiGetCouponListResponseDetailDto> couponList;
    // 2023-renew No24 to here

    /**
     * ログイン会員ID（cookie用）
     */
    private String loginUserId;

    /**
     * その他の送料割引キャンペーン無しの時の表示判定<br/>
     * フリーエリアキー「cart_promotion」
     * ※true その他の送料割引キャンペーンなし、false その他の送料割引キャンペーンあり
     */
    private boolean freeAreCampaign;

    /**
     * 商品合計金額が0円か1円以上かを判定<br/>
     * 送料無料基準注文金額と合計金額の差額を計算
     *
     * @return true 合計金額が0円、false 合計金額が1円以上
     */
    public boolean isShippingTotalPrice() {
        standardAmount = new BigDecimal(PropertiesUtil.getSystemPropertiesValue(FREESHIPPING_STANDARD_AMOUNT));
        // 送料無料基準注文金額と合計金額の差額を計算
        freeShippingDifference = standardAmount.subtract(goodsTotalPriceNoTax);
        return BigDecimal.ZERO.compareTo(goodsTotalPriceNoTax) == 0;
    }

    /**
     * カート内合計金額が「送料無料基準注文金額」より高いか安いかを判定<br/>
     *
     * @return true 「送料無料基準注文金額」以上、false 「送料無料基準注文金額」未満
     */
    public boolean isFreeShippingStandardAmount() {
        return freeShippingDifference.compareTo(standardAmount) >= 0
               || BigDecimal.ZERO.compareTo(freeShippingDifference) >= 0;
    }

    /**
     * SALEアイコン画像のパスを取得する<br/>
     *
     * @return SALEアイコン画像のパス
     */
    public String getNewIconSrc() {
        return PropertiesUtil.getSystemPropertiesValue("images.icon.path.new");
    }

    /**
     * 新着アイコン画像のパスを取得する<br/>
     *
     * @return 新着アイコン画像のパス
     */
    public String getSaleIconSrc() {
        return PropertiesUtil.getSystemPropertiesValue("images.icon.path.sale");
    }

    /**
     * 取りおき可能アイコンのパスを取得する<br/>
     *
     * @return 取りおき可能アイコンのパス
     */
    public String getReserveIconSrc() {
        return PropertiesUtil.getSystemPropertiesValue("images.icon.path.reserve");
    }

    // 2023-renew No92 from here

    /**
     * アウトレットアイコンフラグアイコン画像のパスを取得する<br/>
     *
     * @return アウトレットアイコンフラグアイコン画像のパス
     */
    public String getOutletIconSrc() {
        return PropertiesUtil.getSystemPropertiesValue("images.icon.path.outlet");
    }
    // 2023-renew No92 to here

    /**
     * ログインチェック<br/>
     *
     * @return true:ログイン中
     */
    public boolean isLogin() {
        // 共通情報Helper取得
        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
        return commonInfoUtility.isLogin(getCommonInfo());
    }
    // PDR Migrate Customization to here

}
