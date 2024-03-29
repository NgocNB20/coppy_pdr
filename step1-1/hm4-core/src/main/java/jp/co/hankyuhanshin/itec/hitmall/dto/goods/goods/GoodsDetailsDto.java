/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCoolSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDentalMonopolySalesFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsClassType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeLandSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOutletIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import lombok.Data;
import org.seasar.doma.Entity;
import org.seasar.doma.Transient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品詳細Dtoクラス
 *
 * @author DtoGenerator
 */
@Entity
@Data
@Component
@Scope("prototype")
public class GoodsDetailsDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品SEQ
     */
    private Integer goodsSeq;

    /**
     * 商品グループSEQ
     */
    private Integer goodsGroupSeq;

    /**
     * ショップSEQ
     */
    private Integer shopSeq;

    /**
     * 更新カウンタ（必須）
     */
    private Integer versionNo;

    /**
     * 登録日時（必須）
     */
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    private Timestamp updateTime;

    /**
     * 商品コード
     */
    private String goodsCode;

    /**
     * 商品グループ最高値
     */
    private BigDecimal goodsGroupMaxPrice;

    /**
     * 商品グループ最安値
     */
    private BigDecimal goodsGroupMinPrice;

    /**
     * 値引き前単価最安値
     */
    private BigDecimal preDiscountMinPrice;

    /**
     * 値引き前単価最高値
     */
    private BigDecimal preDiscountMaxPrice;

    /**
     * 商品消費税区分
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
     * 商品単価（税込）
     */
    private BigDecimal goodsPriceInTax;

    /**
     * 商品単価（税抜）
     */
    private BigDecimal goodsPrice;

    /**
     * 商品納期
     */
    private String deliveryType;

    /**
     * 販売状態PC
     */
    private HTypeGoodsSaleStatus saleStatusPC;

    /**
     * 販売開始日時PC
     */
    private Timestamp saleStartTimePC;

    /**
     * 販売終了日時PC
     */
    private Timestamp saleEndTimePC;

    /**
     * 規格管理
     */
    private HTypeUnitManagementFlag unitManagementFlag;

    /**
     * 在庫管理
     */
    private HTypeStockManagementFlag stockManagementFlag;

    /**
     * 商品個別配送
     */
    private HTypeIndividualDeliveryType individualDeliveryType;

    /**
     * 商品最大購入可能数
     */
    private BigDecimal purchasedMax;

    /**
     * 無料配送フラグ
     */
    private HTypeFreeDeliveryFlag freeDeliveryFlag;

    /**
     * 表示順
     */
    private Integer orderDisplay;

    /**
     * 規格値１
     */
    private String unitValue1;

    /**
     * 規格値２
     */
    private String unitValue2;

    /**
     * 値引き前単価＝値引前単価（税抜き）<br/>
     */
    private BigDecimal preDiscountPrice;

    /**
     * 値引き前単価＝値引前単価（税込み）<br/>
     */
    private BigDecimal preDisCountPriceInTax;

    /**
     * JANコード
     */
    private String janCode;

    /**
     * カタログコード
     */
    private String catalogCode;

    /**
     * 販売可能在庫数
     */
    private BigDecimal salesPossibleStock;

    /**
     * 実在庫数
     */
    private BigDecimal realStock;

    /**
     * 注文確保在庫数
     */
    private BigDecimal orderReserveStock;

    /**
     * 残少表示在庫数
     */
    private BigDecimal remainderFewStock;

    /**
     * 発注点在庫数
     */
    private BigDecimal orderPointStock;

    /**
     * 安全在庫数
     */
    private BigDecimal safetyStock;

    /**
     * 商品グループコード
     */
    private String goodsGroupCode;

    /**
     * 新着日付
     */
    private Timestamp whatsnewDate;

    /**
     * 商品公開状態PC
     */
    private HTypeOpenDeleteStatus goodsOpenStatusPC;

    /**
     * 商品公開開始日時PC
     */
    private Timestamp openStartTimePC;

    /**
     * 商品公開終了日時PC
     */
    private Timestamp openEndTimePC;

    /**
     * 商品グループ名
     */
    private String goodsGroupName;

    /**
     * 規格タイトル1
     */
    private String unitTitle1;

    /**
     * 規格タイトル2
     */
    private String unitTitle2;

    /** 値引きコメント */
    private String goodsPreDiscountPrice;

    /**
     * 商品グループ画像エンティティリスト
     */
    @Transient
    private List<GoodsGroupImageEntity> goodsGroupImageEntityList = new ArrayList<>();

    /**
     * SNS連携フラグ
     */
    private HTypeSnsLinkFlag snsLinkFlag;

    /**
     * meta-description
     */
    private String metaDescription;

    /**
     * 在庫状態PC
     */
    private HTypeStockStatusType stockStatusPc;

    /**
     * 商品アイコンリスト
     */
    @Transient
    private List<GoodsInformationIconDetailsDto> goodsIconList = new ArrayList<>();

    /**
     * 商品説明１
     */
    private String goodsNote1;

    /**
     * 商品説明２
     */
    private String goodsNote2;

    /**
     * 商品説明３
     */
    private String goodsNote3;

    /**
     * 商品説明４
     */
    private String goodsNote4;

    /**
     * 商品説明５
     */
    private String goodsNote5;

    /**
     * 商品説明６
     */
    private String goodsNote6;

    /**
     * 商品説明７
     */
    private String goodsNote7;

    /**
     * 商品説明８
     */
    private String goodsNote8;

    /**
     * 商品説明９
     */
    private String goodsNote9;

    /**
     * 商品説明１０
     */
    private String goodsNote10;

    /**
     * 受注連携設定１
     */
    private String orderSetting1;

    /**
     * 受注連携設定２
     */
    private String orderSetting2;

    /**
     * 受注連携設定３
     */
    private String orderSetting3;

    /**
     * 受注連携設定４
     */
    private String orderSetting4;

    /**
     * 受注連携設定５
     */
    private String orderSetting5;

    /**
     * 受注連携設定６
     */
    private String orderSetting6;

    /**
     * 受注連携設定７
     */
    private String orderSetting7;

    /**
     * 受注連携設定８
     */
    private String orderSetting8;

    /**
     * 受注連携設定９
     */
    private String orderSetting9;

    /**
     * 受注連携設定１０
     */
    private String orderSetting10;

    /**
     * 商品規格画像登録リスト<br/>
     */
    @Transient
    private GoodsImageEntity unitImage;

    // PDR Migrate Customization from here
    //    /**
    //     * PDR#004 01_販売可能商品の制御<br/>
    //     *
    //     * <pre>
    //     * 商品詳細Dtoクラス
    //     * 商品区分を追加
    //     * SALEアイコンフラグを追加
    //     * お取りおきアイコンフラグを追加
    //     * NEWアイコンフラグを追加
    //     * 陸送商品フラグを追加
    //     * クール便フラグを追加
    //     * クール便適用期間FROMを追加
    //     * クール便適用期間TOを追加
    //     * 消費税率を追加
    //     * 消費税を追加
    //     * 単位を追加
    //     * </pre>
    //     * @author satoh
    //     *
    //     */
    //    /**
    //     * 商品種別
    //     */
    //    private HTypeGoodsType goodsType;

    /**
     *
     */
    private String goodsOptionDisplayName;

    /** 商品区分 */
    private HTypeGoodsClassType goodsClassType;

    /** 歯科専売可否フラグ */
    private HTypeDentalMonopolySalesFlg dentalMonopolySalesFlg;

    /** SALEアイコンフラグ */
    private HTypeSaleIconFlag saleIconFlag;

    /** お取りおきアイコンフラグ */
    private HTypeReserveIconFlag reserveIconFlag;

    /** NEWアイコンフラグ */
    private HTypeNewIconFlag newIconFlag;

    // 2023-renew No92 from here
    /** アウトレットアイコンフラグ */
    private HTypeOutletIconFlag outletIconFlag;
    // 2023-renew No92 to here

    /** 陸送商品フラグ */
    private HTypeLandSendFlag landSendFlag;

    /** クール便フラグ */
    private HTypeCoolSendFlag coolSendFlag;

    /** クール便適用期間FROM */
    private String coolSendFrom;

    /** クール便適用期間TO */
    private String coolSendTo;

    /** 消費税 */
    private BigDecimal tax;

    /** 単位 */
    private String unit;

    /** 管理商品コード */
    private String goodsManagementCode;

    /** 商品分類コード */
    private String goodsDivisionCode;

    /** カテゴリー1 */
    private String goodsCategory1;

    /** カテゴリー2 */
    private String goodsCategory2;

    /** カテゴリー3 */
    private String goodsCategory3;

    /** 保留フラグ */
    private HTypeReserveFlag reserveFlag;

    /** 価格記号表示フラグ */
    private HTypePriceMarkDispFlag priceMarkDispFlag;

    /** セール価格記号表示フラグ */
    private HTypeSalePriceMarkDispFlag salePriceMarkDispFlag;

    /** セール価格整合性フラグ */
    private HTypeSalePriceIntegrityFlag salePriceIntegrityFlag;

    /** 販売可否判定結果 */
    private Integer saleYesNo;

    /** 販売不可メッセージ */
    private String saleNgMessage;

    /** 入荷次第お届け可否 */
    private String deliveryYesNo;

    /** 心意気価格保持区分 */
    private HTypeEmotionPriceType emotionPriceType;

    // 2023-renew AddNo5 from here
    /** 価格(最低) */
    private BigDecimal goodsPriceInTaxLow;

    /** 価格（最高） */
    private BigDecimal goodsPriceInTaxHight;

    /** セール価格（最低） */
    private BigDecimal preDiscountPriceLow;

    /** セール価格（最高） */
    private BigDecimal preDiscountPriceHight;
    // 2023-renew AddNo5 to here
    // PDR Migrate Customization to here
}
