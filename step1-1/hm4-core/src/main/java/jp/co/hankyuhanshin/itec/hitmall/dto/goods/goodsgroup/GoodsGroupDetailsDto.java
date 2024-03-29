/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDentalMonopolySalesFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsClassType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGroupPriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGroupSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOutletIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 商品グループDtoクラス
 *
 * @author DtoGenerator
 * @version $Revision: 1.0 $
 */
@Entity
@Data
@Component
@Scope("prototype")
public class GoodsGroupDetailsDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品グループSEQ
     */
    private Integer goodsGroupSeq;

    /**
     * 商品グループコード
     */
    private String goodsGroupCode;

    /**
     * 商品グループ最高値PC
     */
    private BigDecimal goodsGroupMaxPricePc;

    /**
     * 商品グループ最安値PC
     */
    private BigDecimal goodsGroupMinPricePc;

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
     * ショップSEQ
     */
    private Integer shopSeq;

    /**
     * 商品グループ名
     */
    private String goodsGroupName;

    /**
     * 値引き前単価最高値
     */
    private BigDecimal preDiscountMaxPrice;

    /**
     * 値引き前単価最安値
     */
    private BigDecimal preDiscountMinPrice;

    /**
     * 商品消費税区分
     */
    private HTypeGoodsTaxType goodsTaxType;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 更新カウンタ
     */
    private Integer versionNo;

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
     * 商品説明１
     */
    private String goodsNote11;

    /**
     * 商品説明２
     */
    private String goodsNote12;

    /**
     * 商品説明３
     */
    private String goodsNote13;

    /**
     * 商品説明４
     */
    private String goodsNote14;

    /**
     * 商品説明５
     */
    private String goodsNote15;

    /**
     * 商品説明６
     */
    private String goodsNote16;

    /**
     * 商品説明７
     */
    private String goodsNote17;

    /**
     * 商品説明８
     */
    private String goodsNote18;

    /**
     * 商品説明９
     */
    private String goodsNote19;

    /**
     * 商品説明１０
     */
    private String goodsNote20;

    // 2023-renew No11 from here
    /**
     * 商品説明２１
     */
    private String goodsNote21;
    // 2023-renew No11 to here

    // 2023-renew No0 from here
    /**
     * 商品説明２２
     */
    private String goodsNote22;
    // 2023-renew No0 to here

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
     * インフォメーションアイコンPC
     */
    private String informationIconPC;

    /**
     * 商品検索キーワード
     */
    private String searchKeyword;

    /**
     * 商品検索キーワード全角
     */
    private String searchKeywordEm;

    /**
     * 規格管理フラグ
     */
    private HTypeUnitManagementFlag unitManagementFlag;

    /**
     * 規格タイトル１
     */
    private String unitTitle1;

    /**
     * 規格タイトル２
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
     * 商品納期
     */
    private String deliverytype;

    /**
     * 在庫状態PC
     */
    private HTypeStockStatusType stockstatusPC;

    // PDR Migrate Customization from here
    //    /**
    //     * PDR#4,10 商品グループDtoクラス
    //     *
    //     */
    /** 商品区分 ⇒ 薬品区分 */
    private HTypeGoodsClassType goodsClassType;

    /** 歯科専売可否フラグ */
    private HTypeDentalMonopolySalesFlg dentalMonopolySalesFlg;

    /** シリーズ価格 */
    private BigDecimal groupPrice;

    /** シリーズセール価格 */
    private BigDecimal groupSalePrice;

    /** シリーズ価格記号表示フラグ */
    private HTypeGroupPriceMarkDispFlag groupPriceMarkDispFlag;

    /** シリーズセール価格記号表示フラグ */
    private HTypeGroupSalePriceMarkDispFlag groupSalePriceMarkDispFlag;

    /** シリーズセール価格整合性フラグ */
    private HTypeSalePriceIntegrityFlag groupSalePriceIntegrityFlag;

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

    // 2023-renew AddNo5 from here
    /**
     * 商品グループ最高値携帯
     */
    private BigDecimal goodsGroupMaxPriceMb;

    /**
     * 商品グループ最安値携帯
     */
    private BigDecimal goodsGroupMinPriceMb;
    // 2023-renew AddNo5 to here
    // PDR Migrate Customization to here
}
