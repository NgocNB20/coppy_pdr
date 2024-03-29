/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCNumber;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVNumber;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleCheckType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common.CategoryItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common.GoodsGroupItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common.GoodsIconItem;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 商品詳細画面 Model
 * // PDR Migrate Customization from here
 * <pre>
 * 商品数量 最大数 9999→999に変更
 * </pre>
 * // PDR Migrate Customization to here
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Data
public class GoodsIndexModel extends AbstractModel {

    /**
     * 商品が存在しない
     */
    public static final String MSGCD_GOODS_NOT_FOUND_ERROR = "AGX000201";

    // 2023-renew No11 from here
    /**
     * 商品情報が取得できない（リンク押下時）
     */
    public static final String MSGCD_GOODS_NOT_FOUND_CLICK_ERROR = "PDR-2023RENEW-11-001-";
    // 2023-renew No11 to here\

    /**
     * カートからの遷移
     */
    private boolean fromCart;

    /**
     * カテゴリID
     */
    @HVSpecialCharacter(allowPunctuation = true)
    private String cid;
    /**
     * カテゴリ名
     */
    private String categoryName;
    /**
     * タイトル画像PC（カテゴリ）
     */
    private String categoryImagePC;
    /**
     * カテゴリパスリスト（パンくず情報）
     */
    private List<CategoryItem> categoryPassItems;

    /**
     * 商品数量
     */
    @NotEmpty
    @HVNumber
    // PDR Migrate Customization from here
    @Range(min = 1, max = 999)
    // PDR Migrate Customization to here
    @HCNumber
    private String gcnt;

    // パンくず情報（商品一覧画面から引き継いできた。）
    /**
     * 商品グループSEQ
     */
    private Integer goodsGroupSeq;
    /**
     * 商品グループコード
     */
    @HVSpecialCharacter(allowPunctuation = true)
    private String ggcd;
    /**
     * 商品名
     */
    private String goodsGroupName;

    /**
     * 商品説明01
     */
    private String goodsNote1;
    /**
     * 商品説明02
     */
    private String goodsNote2;
    /**
     * 商品説明03
     */
    private String goodsNote3;
    /**
     * 商品説明04
     */
    private String goodsNote4;
    /**
     * 商品説明05
     */
    private String goodsNote5;
    /**
     * 商品説明06
     */
    private String goodsNote6;
    /**
     * 商品説明07
     */
    private String goodsNote7;
    /**
     * 商品説明08
     */
    private String goodsNote8;
    /**
     * 商品説明09
     */
    private String goodsNote9;
    /**
     * 商品説明10
     */
    private String goodsNote10;
    //2023-renew No0 from here
    /**
     * 商品説明14
     */
    private String goodsNote14;
    /**
     * 商品説明15
     */
    private String goodsNote15;
    /**
     * 商品説明16
     */
    private String goodsNote16;
    /**
     * 商品説明17
     */
    private String goodsNote17;
    /**
     * 商品説明18
     */
    private String goodsNote18;
    /**
     * 商品説明19
     */
    private String goodsNote19;
    /**
     * 商品説明20
     */
    private String goodsNote20;
    //2023-renew No11 from here
    /**
     * 商品説明21
     */
    private String goodsNote21;
    //2023-renew No11 to here
    /**
     * 商品説明22
     */
    private String goodsNote22;
    //2023-renew No0 to here

    /**
     * 受注連携設定01
     */
    private String orderSetting1;
    /**
     * 受注連携設定02
     */
    private String orderSetting2;
    /**
     * 受注連携設定03
     */
    private String orderSetting3;
    /**
     * 受注連携設定04
     */
    private String orderSetting4;
    /**
     * 受注連携設定05
     */
    private String orderSetting5;
    /**
     * 受注連携設定06
     */
    private String orderSetting6;
    /**
     * 受注連携設定07
     */
    private String orderSetting7;
    /**
     * 受注連携設定08
     */
    private String orderSetting8;
    /**
     * 受注連携設定09
     */
    private String orderSetting9;
    /**
     * 受注連携設定10
     */
    private String orderSetting10;

    /**
     * 規格管理フラグ
     */
    private String unitManagementFlag;
    /**
     * 規格タイトル１（在庫表示用）
     */
    private String unitTitle1;
    /**
     * 規格タイトル１（在庫表示用）
     */
    private String unitTitle2;

    /**
     * 値引きコメント
     */
    private String goodsPreDiscountPrice;

    /**
     * meta-description
     */
    private String metaDescription;
    /**
     * meta-keyword
     */
    private String metaKeyword;
    /**
     * エラー表示用規格表示名１
     */
    @HVSpecialCharacter(allowPunctuation = true)
    private String errorUnitTitle1;
    /**
     * エラー表示用規格表示名２
     */
    @HVSpecialCharacter(allowPunctuation = true)
    private String errorUnitTitle2;
    /**
     * 納期
     */
    private String deliveryType;

    /**
     * 商品インフォメーションアイコン
     */
    private List<GoodsIconItem> informationIconItems;
    /**
     * 商品在庫一覧
     */
    private List<GoodsStockItem> goodsStockItems;
    /**
     * あしあと情報リスト
     */
    private List<GoodsGroupItem> footPrintGoodsItems;
    /**
     * お気に入り情報リスト
     */
    // 2023-renew AddNo5 from here
    private List<GoodsStockItem> favoriteGoodsItems;
    // 2023-renew AddNo5 to here
    /**
     * 関連商品情報リスト
     */
    private List<GoodsGroupItem> relatedGoodsItems;

    /**
     * 商品グループ在庫表示Pc
     */
    private String stockStatusPc;
    /**
     * 商品グループ在庫表示フラグ
     */
    private boolean stockStatusDisplay;

    /**
     * 販売状態=販売の商品ありフラグ
     */
    private boolean existsSaleStatusGoods;

    /**
     * 商品画像アイテム
     */
    private List<String> goodsImageItems;

    /**
     * 商品画像の物が全て
     */
    private List<String> goodsImageAllItems;

    /**
     * 製品標準部品画像項目
     */
    private List<GoodsUnitImageItem> goodsUnitImageItems;

    /**
     * 商品選択プルダウン１リスト
     */
    // private List<Map<String, String>> unitSelect1Items;
    private Map<String, String> unitSelect1Items;
    /**
     * 商品選択プルダウン２リスト
     */
    // private List<Map<String, String>> unitSelect2Items;
    private Map<String, String> unitSelect2Items;

    // 選択値
    /**
     * 選択商品コード
     */
    @HVSpecialCharacter(allowCharacters = {'-'})
    private String gcd;

    /**
     * 規格値１（選択プルダウン用 ※Ajaxで値を変更しているので、「@HVItems」は使用不可）
     */
    @NotEmpty
    @HVSpecialCharacter(allowPunctuation = true)
    private String unitSelect1;

    /**
     * 規格値２（選択プルダウン用 ※Ajaxで値を変更しているので、「@HVItems」は使用不可）
     */
    @NotEmpty
    @HVSpecialCharacter(allowPunctuation = true)
    private String unitSelect2;

    /**
     * 規格1
     */
    private String redirectU1Lbl;

    /**
     * お気に入り商品リスト(":"区切り)
     */
    @HVSpecialCharacter(allowPunctuation = true)
    private String favoriteGoodsCodeList;

    /**
     * SNS連携のボタンを表示するか？
     */
    private boolean snsLinkDisplay;

    /**
     * Facebookを利用するか？
     */
    private boolean useFacebook;

    /**
     * Twitterを利用するか？
     */
    private boolean useTwitter;
    /**
     * Twitter アカウント名
     */
    private String twitterVia;

    /**
     * Lineを利用するか？
     */
    private boolean useLine;

    /**
     * 選択商品コード PATH
     */
    private boolean isGcdPath;

    // 2023-renew No11 from here
    /**
     * index of 在庫商品Item selected
     */
    private Integer indexStock;
    // 2023-renew No11 to here

    /**
     * カテゴリ画像存在チェック
     *
     * @return true:カテゴリ画像が存在する
     */
    public boolean isViewCategoryImagePC() {
        return StringUtils.isNotEmpty(categoryImagePC);
    }

    /**
     * 商品存在チェック
     *
     * @return true:商品が存在しない
     */
    public boolean isNoGoods() {
        return this.goodsGroupSeq == null;
    }

    /**
     * 商品アイコン存在チェック
     *
     * @return true:商品アイコンあり
     */
    public boolean isInformationIconView() {
        boolean flg = true;
        if (CollectionUtil.isEmpty(informationIconItems)) {
            if (!isNewDate() && !stockStatusDisplay) {
                // 新着でない、かつ、商品グループ在庫表示をしない場合
                flg = false;
            }
        }
        return flg;
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
        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        return whatsnewDate.compareTo(dateUtility.getCurrentDate()) >= 0;
    }

    /**
     * 在庫表示チェック
     *
     * @return true:表示する
     */
    public boolean isViewStock() {
        return CollectionUtil.isNotEmpty(goodsStockItems);
    }

    /**
     * 在庫チェック（全商品total)
     * StockFew「残りわずか」
     *
     * @return true：残りわずか
     */
    public boolean isStockFewDisp() {
        return HTypeStockStatusType.STOCK_FEW.getValue().equals(stockStatusPc);
    }

    /**
     * 商品説明01がセットされてるか確認する<br/>
     *
     * @return true;セットされている。
     */
    public boolean isGoodsNote1() {
        return StringUtils.isNotEmpty(goodsNote1);
    }

    /**
     * 商品説明02がセットされてるか確認する<br/>
     *
     * @return true;セットされている。
     */
    public boolean isGoodsNote2() {
        return StringUtils.isNotEmpty(goodsNote2);
    }

    /**
     * 商品説明03がセットされてるか確認する<br/>
     *
     * @return true;セットされている。
     */
    public boolean isGoodsNote3() {
        return StringUtils.isNotEmpty(goodsNote3);
    }

    /**
     * 商品説明04がセットされてるか確認する<br/>
     *
     * @return true;セットされている。
     */
    public boolean isGoodsNote4() {
        return StringUtils.isNotEmpty(goodsNote4);
    }

    /**
     * 商品説明05がセットされてるか確認する<br/>
     *
     * @return true;セットされている。
     */
    public boolean isGoodsNote5() {
        return StringUtils.isNotEmpty(goodsNote5);
    }

    /**
     * 商品説明06がセットされてるか確認する<br/>
     *
     * @return true;セットされている。
     */
    public boolean isGoodsNote6() {
        return StringUtils.isNotEmpty(goodsNote6);
    }

    /**
     * 商品説明07がセットされてるか確認する<br/>
     *
     * @return true;セットされている。
     */
    public boolean isGoodsNote7() {
        return StringUtils.isNotEmpty(goodsNote7);
    }

    /**
     * 商品説明08がセットされてるか確認する<br/>
     *
     * @return true;セットされている。
     */
    public boolean isGoodsNote8() {
        return StringUtils.isNotEmpty(goodsNote8);
    }

    /**
     * 商品説明09がセットされてるか確認する<br/>
     *
     * @return true;セットされている。
     */
    public boolean isGoodsNote9() {
        return StringUtils.isNotEmpty(goodsNote9);
    }

    /**
     * 商品説明10がセットされてるか確認する<br/>
     *
     * @return true;セットされている。
     */
    public boolean isGoodsNote10() {
        return StringUtils.isNotEmpty(goodsNote10);
    }

    /**
     * 非販売の判定<br/>
     * <pre>
     * 販売可能チェック、及び、在庫チェックを実行し、非販売であるかの判定を行う。
     * 「販売可能でない」、且つ、「在庫切れでない」場合は「true」、そうでない場合は「false」とする。
     * </pre>
     *
     * @return true:カテゴリ内商品一覧は空でない / false:空である
     */
    public boolean isNoSale() {
        return HTypeStockStatusType.NO_SALE.getValue().equals(stockStatusPc);
    }

    /**
     * 納期
     *
     * @return true:納期あり
     */
    public boolean isDeliveryTypeDisplay() {
        return StringUtils.isNotEmpty(deliveryType);
    }

    /**
     * 在庫チェック（全商品total)
     * StockPossibleSalesDisp「在庫あり」
     *
     * @return true：在庫あり
     */
    public boolean isStockPossibleSalesDisp() {
        return HTypeStockStatusType.STOCK_POSSIBLE_SALES.getValue().equals(stockStatusPc);
    }

    /**
     * 在庫チェック（全商品total）
     * NoStoc「在庫切れ」
     *
     * @return true:在庫切れ
     */
    public boolean isNoStockDisp() {
        return HTypeStockStatusType.STOCK_NOSTOCK.getValue().equals(stockStatusPc);
    }

    /**
     * 在庫チェック（全商品total）
     * SellOut 「販売期間終了」
     *
     * @return true:販売期間終了
     */
    public boolean isSellOutDisp() {
        // 2023-renew No11 from here
        return sellOutIconFlag;
        // 2023-renew No11 to here
    }

    /**
     * 在庫チェック（全商品total）
     * BeforeSale 「販売前」
     *
     * @return true:販売前
     */
    public boolean isBeforeSaleDisp() {
        return HTypeStockStatusType.BEFORE_SALE.getValue().equals(stockStatusPc);
    }

    /**
     * 在庫チェック（全商品total）
     * NoSale「非販売」
     *
     * @return true:非販売
     */
    public boolean isNoSaleDisp() {
        return HTypeStockStatusType.NO_SALE.getValue().equals(stockStatusPc);
    }

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
        return StringUtils.isNotEmpty(unitTitle2);
    }

    /**
     * お気に入り情報表示チェック<br/>
     * true:表示<br/>
     *
     * @return true:表示
     */
    public boolean isViewFavoriteGoods() {
        if (favoriteGoodsItems == null) {
            return false;
        }
        return true;
    }

    public boolean isGoodsUnitImageDisplay() {
        return CollectionUtils.isNotEmpty(goodsUnitImageItems);
    }

    /**
     * 商品単価(税抜)
     */
    private BigDecimal goodsPrice;

    /**
     * 値引前単価(税抜)
     */
    private BigDecimal preDiscountPrice;

    /**
     * 値引前単価(税込)
     */
    private BigDecimal preDiscountPriceInTax;

    /**
     * 商品表示単価価格帯フラグ
     */
    private boolean goodsDisplayPriceRange;

    /**
     * 商品表示値引き前単価価格帯フラグ
     */
    private boolean goodsDisplayPreDiscountPriceRange;

    /**
     * 新着日付
     */
    private Timestamp whatsnewDate;

    // 2023-renew No5 from here
    /**
     * セール終了日
     */
    private Timestamp saleEndDay;
    // 2023-renew No5 to here

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
     * 商品表示値引き前単価の表示可否
     *
     * @return 表示可はtrue、それ以外はfalse
     */
    public boolean isDisplayPreDiscountPrice() {
        return this.preDiscountPrice != null;
    }

    // PDR Migrate Customization from here
    /** 商品管理番号または商品コードがURLパラメータにない場合のエラー */
    public static final String MSGCD_GOODS_CODE_GET_ERROR = "PDR-0047-002-A-";

    /** 閲覧不可商品が含まれていた場合のエラー */
    public static final String MSGCD_GOODS_NOT_VIEW_ERROR = "PDR-0047-003-A-";

    /** 在庫表示：在庫あり */
    public static final String DISP_IN_STOCK = "○";

    /** 在庫表示：在庫わずか */
    public static final String DISP_LITTLE_STOCK = "△";

    /** 在庫表示：在庫なし */
    public static final String DISP_OUT_OF_STOCK = "×";

    // 2023-renew No11 from here
    /** 在庫表示：入荷次第お届け */
    public static final String DISP_DEPENDING_ON_RECEIPT_STOCK = "入荷次第お届け";
    // 2023-renew No11 to here

    /**
     * ログイン判定<br/>
     *
     * @return true=ログイン済、false=未ログイン
     */
    public boolean isMemberLogin() {
        // 共通情報Helper取得
        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
        return commonInfoUtility.isLogin(getCommonInfo());
    }

    /** メーカー（商品詳細１携帯） */
    private String goodsNote11;

    /** 洗濯アイコン（商品詳細２携帯） */
    private String goodsNote12;

    /** 商品詳細アイコン（商品詳細３携帯） */
    private String goodsNote13;

    private boolean groupSalePriceIntegrityFlag;

    private boolean saleIconFlag;

    /** CUSTOMERSALEアイコンフラグ */

    private boolean customerSaleIconFlag;

    private boolean reserveIconFlag;

    private boolean newIconFlag;

    /** すべての在庫状況 */
    private List<GoodsStockItem> goodsStockInfoItems;

    /** 全ての在庫状況インデックス */
    private int goodsStockInfoIndex;

    private String goodsCode;

    private String patternTitle1;

    private String patternTitle2;

    private String price;

    private String level;

    private String salePrice;

    private String customerSalePrice;

    private String stock;

    private int goodsCodeRowSpan;

    /** セール価格が存在するかどうか */
    private boolean existSalePrice;

    /** 規格１が存在するかどうか */
    private boolean pattern1Use;

    /** 規格２が存在するかどうか */
    private boolean pattern2Use;

    /** セール価格整合性フラグ */
    private boolean salePriceIntegrityFlag;

    /** セール閾値 */
    private String saleLevel;

    /** 顧客セール閾値 */
    private String customerSaleLevel;

    private boolean priceMarkDispFlag;

    private boolean salePriceMarkDispFlag;

    /** 商品コードごとの規格画像コード（JS用） */
    private String unitImageCodeForJs;

    /** 商品画像リスト（JS用） */
    private List<String> goodsUnitImageForJs;

    private List<String> goodsGroupImageForJs;

    // 2023-renew No92 from here
    /** アウトレットアイコンフラグ */
    private boolean outletIconFlag;
    // 2023-renew No92 to here

    // 2023-renew No11 from here
    /** 販売終了しましたアイコンフラグ */
    private boolean sellOutIconFlag = false;
    // 2023-renew No11 to here

    // 2023-renew No2 from here

    /** 販売可否判定結果 */
    private String goodsSaleCheck;

    /**
     * 販売可否判定結果<br/>
     *
     * @return true:販売可 / false:販売不可
     */
    public boolean isSalable() {
        return HTypeSaleCheckType.YES.getValue().equals(goodsSaleCheck);
    }
    // 2023-renew No2 to here

    /**
     * セール価格<br/>
     *
     * @return true:セール価格あり / false:セール価格なし
     */
    // 2023-renew AddNo5 from here
    public boolean isSale() {
        if (goodsSalePriceLow != null || goodsSalePriceHigh != null) {
            return true;
        }
        return false;
    }
    // 2023-renew AddNo5 to here

    /**
     * 同一商品コード内で先頭行かどうか<br/>
     *
     * @return true:先頭行 / false:先頭行ではない
     */
    public boolean isHeader1() {
        return isHeader();
    }

    /**
     * 同一商品コード内で先頭行かどうか<br/>
     *
     * @return true:先頭行 / false:先頭行ではない
     */
    public boolean isHeader2() {
        return isHeader();
    }

    /**
     * 同一商品コード内で先頭行かどうか<br/>
     *
     * @return true:先頭行 / false:先頭行ではない
     */
    public boolean isHeader3() {
        return isHeader();
    }

    /**
     * 同一商品コード内で先頭行かどうか<br/>
     *
     * @return true:先頭行 / false:先頭行ではない
     */
    public boolean isHeader4() {
        if (salePrice != null && !salePrice.isEmpty() || customerSalePrice != null && !customerSalePrice.isEmpty()) {
            return isHeader();
        }
        return true;

    }

    /**
     * 同一商品コード内で先頭行かどうか<br/>
     *
     * @return true:先頭行 / false:先頭行ではない
     */
    public boolean isHeader5() {
        return isHeader();
    }

    /**
     * 同一商品コード内で先頭行かどうか<br/>
     *
     * @return true:先頭行 / false:先頭行ではない
     */
    public boolean isHeader6() {
        return isHeader();
    }

    /**
     * 同一商品コード内で先頭行かどうか<br/>
     *
     * @return true:先頭行 / false:先頭行ではない
     */
    public boolean isHeader7() {
        return isHeader();
    }

    /**
     * 同一商品コード内で先頭行かどうか<br/>
     *
     * @return true:先頭行 / false:先頭行ではない
     */
    public boolean isHeader8() {
        return isHeader();
    }

    /**
     * 同一商品コード内で先頭行かどうか<br/>
     *
     * @return true:先頭行 / false:先頭行ではない
     */
    protected boolean isHeader() {
        // 最初の行なら<td>を表示する
        if (goodsStockInfoIndex == 0) {
            return true;
        }
        return !goodsCode.equals(((GoodsStockItem) goodsStockInfoItems.get(goodsStockInfoIndex - 1)).getGoodsCode());
    }

    /**
     * ROWSPAN値を返却する<br/>
     *
     * @return ROWSPAN数
     */
    public int getHeader1Rowspan() {
        return goodsCodeRowSpan;
    }

    /**
     * ROWSPAN値を返却する<br/>
     *
     * @return ROWSPAN数
     */
    public int getHeader2Rowspan() {
        return goodsCodeRowSpan;
    }

    /**
     * ROWSPAN値を返却する<br/>
     *
     * @return ROWSPAN数
     */
    public int getHeader3Rowspan() {
        return goodsCodeRowSpan;
    }

    /**
     * ROWSPAN値を返却する<br/>
     *
     * @return ROWSPAN数
     */
    public int getHeader4Rowspan() {
        if (salePrice != null && !salePrice.isEmpty() || customerSalePrice != null && !customerSalePrice.isEmpty()) {

            return goodsCodeRowSpan;
        }
        return 1;
    }

    /**
     * ROWSPAN値を返却する<br/>
     *
     * @return ROWSPAN数
     */
    public int getHeader5Rowspan() {
        return goodsCodeRowSpan;
    }

    /**
     * ROWSPAN値を返却する<br/>
     *
     * @return ROWSPAN数
     */
    public int getHeader6Rowspan() {
        return goodsCodeRowSpan;
    }

    /**
     * ROWSPAN値を返却する<br/>
     *
     * @return ROWSPAN数
     */
    public int getHeader7Rowspan() {
        return goodsCodeRowSpan;
    }

    /**
     * ROWSPAN値を返却する<br/>
     *
     * @return ROWSPAN数
     */
    public int getHeader8Rowspan() {
        return goodsCodeRowSpan;
    }

    /**
     * CLASS値を返却する<br/>
     *
     * @return CLASS値
     */
    public String getGoodsStockColumnStyleClass() {
        return "stItem st-" + String.valueOf(goodsStockInfoIndex + 1);
    }

    /**
     * 販売可能商品区分が価格非表示（ログイン前）かどうか
     *
     * @return true=価格非表示（ログイン前）、false=価格非表示（ログイン前）
     */
    public boolean isPriceHideGoods() {
        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
        return !commonInfoUtility.isLogin(getCommonInfo());
    }

    /**
     * 商品選択プルダウン１リストが１件かどうか
     *
     * @return true:１件
     */
    public boolean isUnitSelect1ItemsOne() {
        if (unitSelect1Items != null && unitSelect1Items.size() == 1) {
            return true;
        }
        return false;
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
     * 顧客SALEアイコン画像のパスを取得する<br/>
     *
     * @return 顧客セールアイコン画像のパス
     */
    public String getCustomerSaleIconSrc() {
        return PropertiesUtil.getSystemPropertiesValue("images.icon.path.customerSale");
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

    /**
     * セール価格がセットされているか確認する
     *
     * @return true セットされている
     */
    public boolean isConfirmQuantitySalePrice() {

        if (salePrice != null && !salePrice.isEmpty()) {
            return true;
        }
        if (customerSalePrice != null && !customerSalePrice.isEmpty()) {
            return false;
        }
        return false;

    }

    /**
     * 顧客セール価格がセットされているか確認する
     *
     * @return true セットされている
     */
    public boolean isConfirmSalePrice() {

        if (customerSalePrice != null && !customerSalePrice.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 関連商品が存在するか<br/>
     *
     * @return true:存在する / false:存在しない
     */
    public boolean isExistRelatedGoodsItems() {
        if (relatedGoodsItems == null || relatedGoodsItems.size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 商品説明11がセットされてるか確認する
     *
     * @return true;セットされている。
     */
    public boolean isGoodsNote11() {
        return StringUtils.isNotEmpty(goodsNote11);
    }

    /**
     * 商品説明12がセットされてるか確認する
     *
     * @return true;セットされている。
     */
    public boolean isGoodsNote12() {
        return StringUtils.isNotEmpty(goodsNote12);
    }

    /**
     * 商品説明13がセットされてるか確認する
     *
     * @return true;セットされている。
     */
    public boolean isGoodsNote13() {
        return StringUtils.isNotEmpty(goodsNote13);
    }
    //2023-renew No11 from here

    /**
     * 商品説明21がセットされてるか確認する
     *
     * @return true;セットされている。
     */
    public boolean isGoodsNote21() {
        return StringUtils.isNotEmpty(goodsNote21);
    }

    public boolean isDisplaySaleEndDay() {
        if (ObjectUtils.isNotEmpty(saleEndDay)) {
            return true;
        }
        return false;
    }
    //2023-renew No11 to here

    // 2023-renew No92 from here

    /**
     * アウトレットアイコン画像のパスを取得する<br/>
     *
     * @return アウトレットアイコンのパス
     */
    public String getOutletIconSrc() {
        return PropertiesUtil.getSystemPropertiesValue("images.icon.path.outlet");
    }
    // 2023-renew No92 to here

    // 2023-renew AddNo5 from here

    /**
     * セール価格<br/>
     *
     * @return true:セール価格あり / false:セール価格なし
     */
    public boolean isStockSale() {
        if (goodsSalePriceLow != null || goodsSalePriceHigh != null) {
            return true;
        }
        return false;
    }

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

    //2023-renew No0 from here

    /**
     * 商品説明22がセットされてるか確認する
     *
     * @return true;セットされている。
     */
    public boolean isGoodsNote22() {
        return StringUtils.isNotEmpty(goodsNote22);
    }
    //2023-renew No0 to here

    // PDR Migrate Customization to here

    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品リスト
     */
    private List<GoodsGroupItem> togetherBuyGoodsItems;

    /**
     * よく一緒に購入される商品情報の存在チェック<br/>
     *
     * @return true:存在する / false:存在しない
     */
    public boolean isExistTogetherBuyGoodsItems() {
        if (togetherBuyGoodsItems == null || togetherBuyGoodsItems.size() == 0) {
            return false;
        }
        return true;
    }
    // 2023-renew No21 to here

    // 2023-renew No65 from here
    /**
     * 商品グループ入荷お知らせ一覧コード
     */
    private String gcdStock;
    // 2023-renew No65 to here
}
