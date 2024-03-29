/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front;

import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalableGoodsType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * トップ画面 ModelItem
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Data
@Component
@Scope("prototype")
public class IndexModelItem implements Serializable {

    /**
     * serialVersionUID<br/>
     */
    private static final long serialVersionUID = 1L;
    // PDR Migrate Customization from here
    // 商品
    // 規格単位の商品情報
    /**
     * 選択商品コード
     */
    private String gcd;

    /**
     * 商品グループSEQ
     */
    private Integer goodsGroupSeq;

    /**
     * 商品グループコード
     */
    private String ggcd;

    /**
     * 販売可能商品区分
     */
    private HTypeSalableGoodsType salableGoodsType;

    /**
     * 値引きコメント
     */
    private String goodsGroupPreDiscountPrice;

    /**
     * 商品名
     */
    private String goodsGroupName;

    /**
     * 商品グループサムネイル画像
     */
    private String goodsGroupImageThumbnail;

    /**
     * 規格タイトル1
     */
    private String unitTitle1;

    /**
     * 商品規格１
     */
    private String unitValue1;

    /**
     * 規格タイトル2
     */
    private String unitTitle2;

    /**
     * 商品規格２
     */
    private String unitValue2;

    /**
     * 元金額
     */
    private BigDecimal preDiscountPrice;

    /**
     * 税込み金額
     */
    private BigDecimal goodsPriceInTax;

    /**
     * 商品SEQ
     */
    private Integer goodsSeq;

    /**
     * 画面表示用価格
     */
    private String dispGoodsPriceInTax;

    /**
     * 画面表示用割引後価格
     */
    private String dispPreDiscountPrice;

    /**
     * 在庫表示
     */
    private String listStockStatusPc;

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
     * 値引きコメント
     */
    private String goodsPrediscountPrice;

    /**
     * 画面表示用価格
     */
    private String dispPrice;

    /**
     * 画面表示用セール価格
     */
    private String dispSalePrice;

    /**
     * セール価格整合性フラグ
     */
    private boolean salePriceIntegrityFlag;

    /**
     * 在庫状況表示
     */
    private String stockStatusPc;

    /**
     * 新着日付
     */
    private Timestamp whatsnewDate;
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

    // 2023-renew No92 from here
    /** アウトレットアイコンフラグ */
    private boolean outletIconFlag;
    // 2023-renew No92 to here
    // PDR Migrate Customization to here
    // ニュース
    /**
     * ニュースSEQ
     */
    private String nseq;
    /**
     * ニュースタイトルPC用
     */
    private String newsTitlePC;
    /**
     * ニュースリンク先URLPC用
     */
    private String newsUrlPC;
    /**
     * ニュース本文PC用
     */
    private String newsBodyPC;
    /**
     * ニュース詳細PC用
     */
    private String newsNotePC;
    /**
     * ニュース日付
     */
    private Timestamp newsTime;

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
     * ニュースリンクPCチェック(外部リンク)<br/>
     *
     * @return true:リンクあり
     */
    public boolean isNewsLinkPC() {
        return StringUtils.isNotEmpty(newsUrlPC);
    }

    /**
     * ニュースリンクPCチェック(ニュース詳細画面)<br/>
     *
     * @return true:リンクあり
     */
    public boolean isNewsDetailsLinkPC() {
        return StringUtils.isNotEmpty(newsNotePC);
    }

    /**
     * ニュース本文PC存在チェック<br/>
     *
     * @return true:本文あり
     */
    public boolean isNewsBodyPCExists() {
        return StringUtils.isNotEmpty(newsBodyPC);
    }
    // PDR Migrate Customization from here

    /**
     * セール価格（商品）<br/>
     *
     * @return true:セール価格あり / false:セール価格なし
     */
    public boolean isSaleItem() {
        // 2023-renew AddNo5 from here
        if ((goodsSalePriceLow != null && goodsSalePriceLow.compareTo(BigDecimal.ZERO) != 0)
            || (goodsSalePriceHigh != null && goodsSalePriceHigh.compareTo(BigDecimal.ZERO) != 0)) {
            return true;
        }
        // 2023-renew AddNo5 to here
        return false;
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
    public boolean isBeforeOpenIconDisp() {
        return HTypeStockStatusType.BEFORE_OPEN.getValue().equals(listStockStatusPc);
    }

    /**
     * 在庫状態:公開終了
     * （一覧アイコン表示用）
     *
     * @return true：公開終了
     */
    public boolean isOpenEndIconDisp() {
        return HTypeStockStatusType.OPEN_END.getValue().equals(listStockStatusPc);
    }

    /**
     * 在庫状態:非公開
     * （一覧アイコン表示用）
     *
     * @return true：非公開
     */
    public boolean isNoOpenIconDisp() {
        return HTypeStockStatusType.NO_OPEN.getValue().equals(listStockStatusPc);
    }
    // 2023-renew No2 from here
    /**
     * 販売可能商品区分が価格非表示（ログイン前）かどうか
     *
     * @return true=価格非表示（ログイン前）、false=価格非表示（ログイン前）
     */
    public boolean isPriceHideGoods() {
        // 共通情報Helper取得
        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
        return !commonInfoUtility.isLogin(ApplicationContextUtility.getBean(CommonInfo.class));
    }
    // 2023-renew No2 to here

    /**
     * カートに入れるボタンの表示判定<br/>
     * <br/>
     * 以下の場合のみカートに入れるボタンを表示する<br/>
     * ・公開状態：公開<br/>
     * ・販売状態：販売<br/>
     * ・オプション：管理しない<br/>
     * ・在庫状態：在庫なし以外<br/>
     * ・商品種別：セット対象専用以外<br/>
     * <p>
     * 以下今回用に追加<br/>
     * ・販売可能商品区分：購入可能(ログイン後)<br/>
     *
     * @return true=表示する、false=表示しない
     */
    public boolean isViewCartIn() {
        // 2023-renew No2 from here
        return isGoodsOpen() && isGoodsSales() && !isStockNoStock();
        // 2023-renew No2 to here
    }

    /**
     * 商品公開判定<br/>
     *
     * @return true=公開、false=公開でない　または　会員ランク不一致
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
     * 在庫状態:在庫なし
     *
     * @return true：在庫なし
     */
    public boolean isStockNoStock() {
        return HTypeStockStatusType.STOCK_NOSTOCK.getValue().equals(stockStatusPc);
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
    // PDR Migrate Customization to here

}
