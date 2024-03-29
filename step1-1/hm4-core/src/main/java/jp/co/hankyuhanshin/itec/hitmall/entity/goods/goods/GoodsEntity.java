/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCoolSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeLandSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleControlType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitImageFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 商品クラス
 * <pre>
 * 画面入力を行う必須項目を追加した場合はGoodsRegistLogicImpl#getGoodsEntityByOverwritedRequiredItem
 * </pre>
 *
 * @author EntityGenerator
 * @version $Revision: 1.0 $
 */
@Entity
@Table(name = "Goods")
@Data
@Component
@Scope("prototype")
public class GoodsEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品SEQ（必須）
     */
    @Column(name = "goodsSeq")
    @Id
    private Integer goodsSeq;

    /**
     * 商品グループSEQ (FK)（必須）
     */
    @Column(name = "goodsGroupSeq")
    private Integer goodsGroupSeq;

    /**
     * 商品コード（必須）
     */
    @Column(name = "goodsCode")
    private String goodsCode;

    /**
     * JANコード
     */
    @Column(name = "janCode")
    private String janCode;

    /**
     * カタログコード
     */
    @Column(name = "catalogCode")
    private String catalogCode;

    /**
     * 販売状態PC（必須）
     */
    @Column(name = "saleStatusPC")
    private HTypeGoodsSaleStatus saleStatusPC = HTypeGoodsSaleStatus.NO_SALE;

    /**
     * 販売開始日時PC
     */
    @Column(name = "saleStartTimePC")
    private Timestamp saleStartTimePC;

    /**
     * 販売終了日時PC
     */
    @Column(name = "saleEndTimePC")
    private Timestamp saleEndTimePC;

    /**
     * 商品単価（必須）
     */
    @Column(name = "goodsPrice")
    private BigDecimal goodsPrice = BigDecimal.ZERO;

    /**
     * 値引前単価
     */
    @Column(name = "preDiscountPrice")
    private BigDecimal preDiscountPrice;

    /**
     * 商品個別配送種別（必須）
     */
    @Column(name = "individualDeliveryType")
    private HTypeIndividualDeliveryType individualDeliveryType = HTypeIndividualDeliveryType.OFF;

    /**
     * 無料配送フラグ（必須）
     */
    @Column(name = "freeDeliveryFlag")
    private HTypeFreeDeliveryFlag freeDeliveryFlag = HTypeFreeDeliveryFlag.OFF;

    /**
     * 規格管理フラグ（必須）
     */
    @Column(name = "unitManagementFlag")
    private HTypeUnitManagementFlag unitManagementFlag = HTypeUnitManagementFlag.OFF;

    /**
     * 規格値１
     */
    @Column(name = "unitValue1")
    private String unitValue1;

    /**
     * 規格値２
     */
    @Column(name = "unitValue2")
    private String unitValue2;

    /**
     * 在庫管理フラグ（必須）
     */
    @Column(name = "stockManagementFlag")
    private HTypeStockManagementFlag stockManagementFlag = HTypeStockManagementFlag.OFF;

    /**
     * 商品最大購入可能数（必須）
     */
    @Column(name = "purchasedMax")
    private BigDecimal purchasedMax = BigDecimal.ZERO;

    /**
     * 表示順
     */
    @Column(name = "orderDisplay")
    private Integer orderDisplay;

    /**
     * ショップSEQ（必須）
     */
    @Column(name = "shopSeq")
    private Integer shopSeq;

    /**
     * 更新カウンタ（必須）
     */
    // 商品CSV用に排他チェックしない @Version
    @Column(name = "versionNo")
    private Integer versionNo = 0;

    /**
     * 登録日時（必須）
     */
    @Column(name = "registTime", updatable = false)
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    @Column(name = "updateTime")
    private Timestamp updateTime;

    // PDR Migrate Customization from here
    //    /**
    //     * PDR#10 07_データ連携（商品情報）新規情報を商品情報に追加<br/>
    //     * <pre>
    //     * ・保留フラグを追加
    //     * ・単位を追加
    //     * ・価格記号表示フラグを追加
    //     * ・セール価格記号表示フラグを追加
    //     * ・管理商品コードを追加
    //     * ・商品分類コードを追加
    //     * ・カテゴリー1を追加
    //     * ・カテゴリー2を追加
    //     * ・カテゴリー3を追加
    //     * ・陸送商品フラグを追加
    //     * ・クール便フラグを追加
    //     * ・クール便適用期間Fromを追加
    //     * ・クール便適用期間Toを追加
    //     * ・セール価格整合性フラグを追加
    //     * </pre>
    //     *
    //     */
    /**
     * 保留フラグ<br/>
     */
    @Column(name = "reserveFlag")
    private HTypeReserveFlag reserveFlag;

    /**
     * 単位<br/>
     */
    @Column(name = "unit")
    private String unit;

    /**
     * 価格記号表示フラグ<br/>
     */
    @Column(name = "priceMarkDispFlag")
    private HTypePriceMarkDispFlag priceMarkDispFlag;

    /**
     * セール価格記号表示フラグ<br/>
     */
    @Column(name = "salePriceMarkDispFlag")
    private HTypeSalePriceMarkDispFlag salePriceMarkDispFlag;

    /**
     * 管理商品コード<br/>
     */
    @Column(name = "goodsManagementCode")
    private String goodsManagementCode;

    /**
     * 商品分類コード<br/>
     */
    @Column(name = "goodsDivisionCode")
    private Integer goodsDivisionCode;

    /**
     * カテゴリー1<br/>
     */
    @Column(name = "goodsCategory1")
    private Integer goodsCategory1;

    /**
     * カテゴリー2<br/>
     */
    @Column(name = "goodsCategory2")
    private Integer goodsCategory2;

    /**
     * カテゴリー3<br/>
     */
    @Column(name = "goodsCategory3")
    private Integer goodsCategory3;

    /**
     * 陸送商品フラグ<br/>
     */
    @Column(name = "landSendFlag")
    private HTypeLandSendFlag landSendFlag;

    /**
     * クール便フラグ<br/>
     */
    @Column(name = "coolSendFlag")
    private HTypeCoolSendFlag coolSendFlag;

    /**
     * クール便適用期間From<br/>
     */
    @Column(name = "coolSendFrom")
    private String coolSendFrom;

    /**
     * クール便適用期間To<br/>
     */
    @Column(name = "coolSendTo")
    private String coolSendTo;

    /**
     * セール価格整合性フラグ<br/>
     */
    @Column(name = "salePriceIntegrityFlag")
    private HTypeSalePriceIntegrityFlag salePriceIntegrityFlag;

    /**
     * 心意気価格保持区分<br/>
     */
    @Column(name = "emotionPriceType")
    private HTypeEmotionPriceType emotionPriceType;

    // 2023-renew No92 from here
    /**
     * 価格(最低)
     */
    @Column(name = "goodsPriceInTaxLow")
    private BigDecimal goodsPriceInTaxLow = BigDecimal.ZERO;

    /**
     * 価格（最高）
     */
    @Column(name = "goodsPriceInTaxHight")
    private BigDecimal goodsPriceInTaxHight = BigDecimal.ZERO;

    /**
     * セール価格（最低）
     */
    @Column(name = "preDiscountPriceLow")
    private BigDecimal preDiscountPriceLow = BigDecimal.ZERO;

    /**
     * セール価格（最高）
     */
    @Column(name = "preDiscountPriceHight")
    private BigDecimal preDiscountPriceHight = BigDecimal.ZERO;

    /**
     * 販売制御区分
     */
    @Column(name = "saleControl")
    private HTypeSaleControlType saleControl;
    // 2023-renew No92 to here
    // PDR Migrate Customization to here

    // 2023-renew No76 from here
    /**
     * 規格画像有無
     */
    @Column(name = "unitImageFlag")
    private HTypeUnitImageFlag unitImageFlag;
    // 2023-renew No76 to here
}
