/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDentalMonopolySalesFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeExcludeFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsClassType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGroupPriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGroupSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSnsLinkFlag;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 商品グループクラス
 *
 * @author EntityGenerator
 * @version $Revision: 1.0 $
 */
@Entity
@Table(name = "GoodsGroup")
@Data
@Component
@Scope("prototype")
public class GoodsGroupEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品グループSEQ（必須）
     */
    @Column(name = "goodsGroupSeq")
    @Id
    private Integer goodsGroupSeq;

    /**
     * 商品グループコード（必須）
     */
    @Column(name = "goodsGroupCode")
    private String goodsGroupCode;

    // 2023-renew No64 from here
    /**
     * 商品グループ名（管理用）
     */
    @Column(name = "goodsGroupNameAdmin")
    private String goodsGroupNameAdmin;
    // 2023-renew No64 to here

    /**
     * 商品グループ名
     */
    @Column(name = "goodsGroupName")
    private String goodsGroupName;

    //2023-renew No64 from here
    /**
     * 商品名1
     */
    @Column(name = "goodsGroupName1")
    private String goodsGroupName1;

    /**
     * 商品名1_公開開始日時
     */
    @Column(name = "goodsGroupName1OpenStartTime")
    private Timestamp goodsGroupName1OpenStartTime;

    /**
     * 商品名2
     */
    @Column(name = "goodsGroupName2")
    private String goodsGroupName2;

    /**
     * 商品名2_公開開始日時
     */
    @Column(name = "goodsGroupName2OpenStartTime")
    private Timestamp goodsGroupName2OpenStartTime;
    //2023-renew No64 to here

    /**
     * 新着日付（必須）
     */
    @Column(name = "whatsnewDate")
    private Timestamp whatsnewDate;

    /**
     * 商品公開状態PC（必須）
     */
    @Column(name = "goodsOpenStatusPC")
    private HTypeOpenDeleteStatus goodsOpenStatusPC = HTypeOpenDeleteStatus.NO_OPEN;

    /**
     * 商品公開開始日時PC
     */
    @Column(name = "openStartTimePC")
    private Timestamp openStartTimePC;

    /**
     * 商品公開終了日時PC
     */
    @Column(name = "openEndTimePC")
    private Timestamp openEndTimePC;

    /**
     * 値引きコメント
     */
    @Column(name = "goodsPreDiscountPrice")
    private String goodsPreDiscountPrice;

    /**
     * 商品消費税種別（必須）
     */
    @Column(name = "goodsTaxType")
    private HTypeGoodsTaxType goodsTaxType = HTypeGoodsTaxType.OUT_TAX;

    /**
     * 税率（必須）
     */
    @Column(name = "taxRate")
    private BigDecimal taxRate;

    /**
     * 酒類フラグ
     */
    @Column(name = "alcoholFlag")
    private HTypeAlcoholFlag alcoholFlag = HTypeAlcoholFlag.NOT_ALCOHOL;

    /**
     * 商品グループ最高値PC（必須）
     */
    @Column(name = "goodsGroupMaxPricePc")
    private BigDecimal goodsGroupMaxPricePc = BigDecimal.ZERO;

    /**
     * 商品グループ最安値PC（必須）
     */
    @Column(name = "goodsGroupMinPricePc")
    private BigDecimal goodsGroupMinPricePc = BigDecimal.ZERO;

    /**
     * SNS連携フラグ
     */
    @Column(name = "snsLinkFlag")
    private HTypeSnsLinkFlag snsLinkFlag = HTypeSnsLinkFlag.OFF;

    /**
     * ショップSEQ（必須）
     */
    @Column(name = "shopSeq")
    private Integer shopSeq;

    /**
     * 更新カウンタ（必須）
     */
    @Version
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

    // テーブル項目外追加フィールド
    /**
     * 値引き前単価最高値
     */
    @Column(insertable = false, updatable = false)
    private BigDecimal preDiscountMaxPrice;

    /**
     * 値引き前単価最安値
     */
    @Column(insertable = false, updatable = false)
    private BigDecimal preDiscountMinPrice;

    // PDR Migrate Customization from here
    //    /**
    //     * PDR#10 07_データ連携（商品情報）新規情報を商品グループ情報に追加<br/>
    //     * <pre>
    //     * ・商品区分を追加
    //     * ・カタログ表示順を追加
    //     * ・シリーズ価格を追加
    //     * ・シリーズセール価格を追加
    //     * ・シリーズ価格記号表示フラグを追加
    //     * ・シリーズセール価格記号表示フラグを追加
    //     * ・シリーズセール価格整合性フラグを追加
    //     * </pre>
    //     *
    //     */
    /**
     * 商品区分 ⇒ 薬品区分<br/>
     */
    @Column(name = "goodsClassType")
    private HTypeGoodsClassType goodsClassType;

    /**
     * 歯科専売可否フラグ<br/>
     */
    @Column(name = "dentalMonopolySalesFlg")
    private HTypeDentalMonopolySalesFlg dentalMonopolySalesFlg;

    /**
     * カタログ表示順<br/>
     */
    @Column(name = "catalogDisplayOrder")
    private Integer catalogDisplayOrder;

    /**
     * シリーズ価格<br/>
     */
    @Column(name = "groupPrice")
    private BigDecimal groupPrice;

    /**
     * シリーズセール価格<br/>
     */
    @Column(name = "groupSalePrice")
    private BigDecimal groupSalePrice;

    /**
     * シリーズ価格記号表示フラグ<br/>
     */
    @Column(name = "groupPriceMarkDispFlag")
    private HTypeGroupPriceMarkDispFlag groupPriceMarkDispFlag;

    /**
     * シリーズセール価格記号表示フラグ<br/>
     */
    @Column(name = "groupSalePriceMarkDispFlag")
    private HTypeGroupSalePriceMarkDispFlag groupSalePriceMarkDispFlag;

    /**
     * シリーズセール価格整合性フラグ
     */
    @Column(name = "groupSalePriceIntegrityFlag")
    private HTypeSalePriceIntegrityFlag groupSalePriceIntegrityFlag;
    // PDR Migrate Customization to here

    // 2023-renew AddNo5 from here
    /**
     * 商品グループ最高値携帯
     */
    @Column(name = "goodsGroupMaxPriceMb")
    private BigDecimal goodsGroupMaxPriceMb = BigDecimal.ZERO;

    /**
     * 商品グループ最高値携帯
     */
    @Column(name = "goodsGroupMinPriceMb")
    private BigDecimal goodsGroupMinPriceMb = BigDecimal.ZERO;
    // 2023-renew AddNo5 to here

    // 2023-renew No21 from here
    /**
     * 除外フラグ
     */
    @Column(name = "excludingFlag")
    private HTypeExcludeFlag excludingFlag;
    // 2023-renew No21 to here
}
