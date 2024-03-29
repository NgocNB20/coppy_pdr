/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDentalMonopolySalesFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsClassType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGroupPriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGroupSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSnsLinkFlag;
import lombok.Data;
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
    private Integer goodsGroupSeq;

    /**
     * 商品グループコード（必須）
     */
    private String goodsGroupCode;

    // 2023-renew No64 from here
    /**
     * 商品グループ名（管理用）
     */
    private String goodsGroupNameAdmin;
    // 2023-renew No64 to here

    /**
     * 商品グループ名
     */
    private String goodsGroupName;

    /**
     * 新着日付（必須）
     */
    private Timestamp whatsnewDate;

    /**
     * 商品公開状態PC（必須）
     */
    private HTypeOpenDeleteStatus goodsOpenStatusPC = HTypeOpenDeleteStatus.NO_OPEN;

    /**
     * 商品公開開始日時PC
     */
    private Timestamp openStartTimePC;

    /**
     * 商品公開終了日時PC
     */
    private Timestamp openEndTimePC;

    /**
     * 値引きコメント
     */
    private String goodsPreDiscountPrice;

    /**
     * 商品消費税種別（必須）
     */
    private HTypeGoodsTaxType goodsTaxType = HTypeGoodsTaxType.OUT_TAX;

    /**
     * 税率（必須）
     */
    private BigDecimal taxRate;

    /**
     * 酒類フラグ
     */
    private HTypeAlcoholFlag alcoholFlag = HTypeAlcoholFlag.NOT_ALCOHOL;

    /**
     * 商品グループ最高値PC（必須）
     */
    private BigDecimal goodsGroupMaxPricePc = BigDecimal.ZERO;

    /**
     * 商品グループ最安値PC（必須）
     */
    private BigDecimal goodsGroupMinPricePc = BigDecimal.ZERO;

    /**
     * SNS連携フラグ
     */
    private HTypeSnsLinkFlag snsLinkFlag = HTypeSnsLinkFlag.OFF;

    /**
     * ショップSEQ（必須）
     */
    private Integer shopSeq;

    /**
     * 更新カウンタ（必須）
     */
    private Integer versionNo = 0;

    /**
     * 登録日時（必須）
     */
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    private Timestamp updateTime;

    // テーブル項目外追加フィールド
    /**
     * 値引き前単価最高値
     */
    private BigDecimal preDiscountMaxPrice;

    /**
     * 値引き前単価最安値
     */
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
    private HTypeGoodsClassType goodsClassType;

    /**
     * 歯科専売可否フラグ<br/>
     */
    private HTypeDentalMonopolySalesFlg dentalMonopolySalesFlg;

    /**
     * カタログ表示順<br/>
     */
    private Integer catalogDisplayOrder;

    /**
     * シリーズ価格<br/>
     */
    private BigDecimal groupPrice;

    /**
     * シリーズセール価格<br/>
     */
    private BigDecimal groupSalePrice;

    /**
     * シリーズ価格記号表示フラグ<br/>
     */
    private HTypeGroupPriceMarkDispFlag groupPriceMarkDispFlag;

    /**
     * シリーズセール価格記号表示フラグ<br/>
     */
    private HTypeGroupSalePriceMarkDispFlag groupSalePriceMarkDispFlag;

    /**
     * シリーズセール価格整合性フラグ
     */
    private HTypeSalePriceIntegrityFlag groupSalePriceIntegrityFlag;

    // 2023-renew AddNo5 from here
    /**
     * 商品グループ最高値携帯
     */
    private BigDecimal goodsGroupMaxPriceMb = BigDecimal.ZERO;

    /**
     * 商品グループ最安値携帯
     */
    private BigDecimal goodsGroupMinPriceMb = BigDecimal.ZERO;
    // 2023-renew AddNo5 to here
    // PDR Migrate Customization to here
}
