/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCoolSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeLandSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceMarkDispFlag;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 商品管理：商品詳細ページ 商品規格アイテム<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class GoodsDetailsUnitItem implements Serializable {

    /**
     * シリアルバージョンID<br/>
     */
    private static final long serialVersionUID = 1L;

    /************************************
     ** 商品規格項目
     ************************************/
    /**
     * No<br/>
     */
    private Integer unitDspNo;

    /**
     * 商品SEQ<br/>
     */
    private Integer goodsSeq;

    /**
     * 商品コード<br/>
     */
    private String goodsCode;

    /**
     * 単価＝商品単価（税抜）<br/>
     * <pre>※画面非表示項目（最低価格・最高価格を表示）</pre>
     */
    private String goodsPrice;

    // 2023-renew addNo5 from here
    /**
     * 価格(最低)
     */
    private BigDecimal goodsPriceInTaxLow;

    /**
     * 価格（最高）
     */
    private BigDecimal goodsPriceInTaxHight;
    // 2023-renew addNo5 to here

    /**
     * JANコード<br/>
     */
    private String janCode;

    /**
     * Jカタログコード<br/>
     */
    private String catalogCode;

    /**
     * 規格値１<br/>
     */
    private String unitValue1;

    /**
     * 規格値２<br/>
     */
    private String unitValue2;

    /**
     * 販売状態PC<br/>
     */
    private String goodsSaleStatusPC;

    /**
     * 販売開始年月日PC<br/>
     */
    private String saleStartDatePC;

    /**
     * 販売開始時刻PC<br/>
     */
    private String saleStartTimePC;

    /**
     * 販売開始日時PC<br/>
     */
    private Timestamp saleStartDateTimePC;

    /**
     * 販売終了年月日PC<br/>
     */
    private String saleEndDatePC;

    /**
     * 販売終了時刻PC<br/>
     */
    private String saleEndTimePC;

    /**
     * 販売終了日時PC<br/>
     */
    private Timestamp saleEndDateTimePC;

    /**
     * 最大購入可能数<br/>
     */
    private BigDecimal purchasedMax;

    /**
     * 値引き前単価＝値引前単価（税込み）<br/>
     * <pre>※画面非表示項目（セール価格（最低）・セール価格（最高）を表示）</pre>
     */
    private BigDecimal preDiscountPrice;

    // 2023-renew addNo5 from here
    /**
     * セール価格（最低）
     */
    private BigDecimal preDiscountPriceLow;

    /**
     * セール価格（最高）
     */
    private BigDecimal preDiscountPriceHight;
    // 2023-renew addNo5 to here

    // PDR Migrate Customization from here
    /**
     * 保留フラグ
     */
    private HTypeReserveFlag reserveFlag;

    /**
     * 管理商品コード
     */
    private String goodsManagementCode;

    /**
     * 商品分類コード
     */
    private Integer goodsDivisionCode;

    /**
     * カテゴリー1
     */
    private Integer goodsCategory1;

    /**
     * カテゴリー2
     */
    private Integer goodsCategory2;

    /**
     * カテゴリー3
     */
    private Integer goodsCategory3;

    /**
     * 陸送商品フラグ
     */
    private HTypeLandSendFlag landSendFlag;

    /**
     * クール便フラグ
     */
    private HTypeCoolSendFlag coolSendFlag;

    /**
     * クール便適用期間From<br/>
     * 値の中にyyyyがなく閏年の判定ができないため厳密な日付チェックを行っていない<br/>
     * MMが01～12、ddが01～31であるかをチェックしている<br/>
     */
    private String coolSendFrom;

    /**
     * クール便適用期間To<br/>
     * 値の中にyyyyがなく閏年の判定ができないため厳密な日付チェックを行っていない<br/>
     * MMが01～12、ddが01～31であるかをチェックしている<br/>
     */
    private String coolSendTo;

    /**
     * 単位
     */
    private String unit;

    /**
     * 価格記号表示フラグ
     */
    private HTypePriceMarkDispFlag priceMarkDispFlag;

    /**
     * セール価格記号表示フラグ
     */
    private HTypeSalePriceMarkDispFlag salePriceMarkDispFlag;

    /**
     * 心意気価格保持区分
     */
    private HTypeEmotionPriceType emotionPriceType;

    /**
     * クール便適用期間を表示するか否か<br/>
     *
     * @return true：クール便適用期間を表示
     */
    public boolean isCoolSendAreaDisplay() {
        if (this.coolSendFrom != null) {
            return true;
        }
        return false;
    }
    // PDR Migrate Customization to here

    /**
     * @return saleDateTimeExistPC
     */
    public boolean isSaleDateTimePCExist() {
        if (this.saleStartDateTimePC != null || this.saleEndDateTimePC != null) {
            return true;
        }
        return false;
    }

    /**
     * 値引き前単価＝値引前単価（税込み）有無判定<br/>
     *
     * @return true=値引き前単価がある場合
     */
    public boolean isPreDiscountPriceFlg() {
        return preDiscountPrice != null;
    }

}
