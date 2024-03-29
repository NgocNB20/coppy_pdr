/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.shop;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAutoApprovalFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.SequenceGenerator;
import org.seasar.doma.Table;
import org.seasar.doma.Version;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * ショップクラス
 */
@Entity
@Table(name = "Shop")
@Data
@Component
@Scope("prototype")
public class ShopEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ショップSEQ（必須）
     */
    @Column(name = "shopSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "shopSeq")
    private Integer shopSeq;

    /**
     * ショップID（必須）
     */
    @Column(name = "shopId")
    private String shopId;

    /**
     * ショップ名pc（必須）
     */
    @Column(name = "shopNamePC")
    private String shopNamePC;

    /**
     * ショップ名携帯（必須）
     */
    @Column(name = "shopNameMB")
    private String shopNameMB;

    /**
     * ショップurl-pc
     */
    @Column(name = "urlPC")
    private String urlPC;

    /**
     * ショップurl-携帯
     */
    @Column(name = "urlMB")
    private String urlMB;

    /**
     * ショップ公開状態PC
     */
    @Column(name = "shopOpenStatusPC")
    private HTypeOpenStatus shopOpenStatusPC;

    /**
     * ショップ公開状態携帯
     */
    @Column(name = "shopOpenStatusMB")
    private HTypeOpenStatus shopOpenStatusMB;

    /**
     * ショップ公開開始日時pc
     */
    @Column(name = "shopOpenStartTimePC")
    private Timestamp shopOpenStartTimePC;

    /**
     * ショップ公開終了日時pc
     */
    @Column(name = "shopOpenEndTimePC")
    private Timestamp shopOpenEndTimePC;

    /**
     * ショップ公開開始日時携帯
     */
    @Column(name = "shopOpenStartTimeMB")
    private Timestamp shopOpenStartTimeMB;

    /**
     * ショップ公開終了日時携帯
     */
    @Column(name = "shopOpenEndTimeMB")
    private Timestamp shopOpenEndTimeMB;

    /**
     * meta-description
     */
    @Column(name = "metaDescription")
    private String metaDescription;

    /**
     * meta-keyword
     */
    @Column(name = "metaKeyword")
    private String metaKeyword;

    /**
     * ポイント付与基準金額
     */
    @Column(name = "pointAddBasePriceDefault")
    private BigDecimal pointAddBasePriceDefault;

    /**
     * ポイント付与率
     */
    @Column(name = "pointAddRateDefault")
    private BigDecimal pointAddRateDefault;

    /**
     * ポイント適用開始日時1
     */
    @Column(name = "pointStartTime1")
    private Timestamp pointStartTime1;

    /**
     * ポイント適用終了日時1
     */
    @Column(name = "pointEndTime1")
    private Timestamp pointEndTime1;

    /**
     * ポイント付与基準金額1
     */
    @Column(name = "pointAddBasePrice1")
    private BigDecimal pointAddBasePrice1;

    /**
     * ポイント付与率1
     */
    @Column(name = "pointAddRate1")
    private BigDecimal pointAddRate1;

    /**
     * ポイント適用開始日時2
     */
    @Column(name = "pointStartTime2")
    private Timestamp pointStartTime2;

    /**
     * ポイント適用終了日時2
     */
    @Column(name = "pointEndTime2")
    private Timestamp pointEndTime2;

    /**
     * ポイント付与基準金額2
     */
    @Column(name = "pointAddBasePrice2")
    private BigDecimal pointAddBasePrice2;

    /**
     * ポイント付与率2
     */
    @Column(name = "pointAddRate2")
    private BigDecimal pointAddRate2;

    /**
     * 会員ランク判定日
     */
    @Column(name = "memberRankSetDate")
    private Timestamp memberRankSetDate;

    /**
     * 会員ランク累計開始日時
     */
    @Column(name = "memberRankPurchaseStartTime")
    private Timestamp memberRankPurchaseStartTime;

    /**
     * 更新カウンタ
     */
    @Version
    @Column(name = "versionNo")
    private Integer versionNo;

    /**
     * 登録日時（必須）
     */
    @Column(name = "registTime")
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    @Column(name = "updateTime")
    private Timestamp updateTime;

    /**
     * 自動承認フラグ
     */
    @Column(name = "autoApprovalFlag")
    private HTypeAutoApprovalFlag autoApprovalFlag;

    /**
     * レビューデフォルトポイント付与数
     */
    @Column(name = "reviewDefaultPoint")
    private BigDecimal reviewDefaultPoint;

    // 2023-renew No60 from here
    /**
     * クレジットカードエラー回数上限
     */
    @Column(name = "creditErrorCount")
    private Integer creditErrorCount;
    // 2023-renew No60 to here

}
