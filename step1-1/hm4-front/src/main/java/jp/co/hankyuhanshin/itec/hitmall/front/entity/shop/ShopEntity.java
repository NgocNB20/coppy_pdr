/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.shop;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAutoApprovalFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenStatus;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * ショップクラス
 */
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
    private Integer shopSeq;

    /**
     * ショップID（必須）
     */
    private String shopId;

    /**
     * ショップ名pc（必須）
     */
    private String shopNamePC;

    /**
     * ショップ名携帯（必須）
     */
    private String shopNameMB;

    /**
     * ショップurl-pc
     */
    private String urlPC;

    /**
     * ショップurl-携帯
     */
    private String urlMB;

    /**
     * ショップ公開状態PC
     */
    private HTypeOpenStatus shopOpenStatusPC;

    /**
     * ショップ公開状態携帯
     */
    private HTypeOpenStatus shopOpenStatusMB;

    /**
     * ショップ公開開始日時pc
     */
    private Timestamp shopOpenStartTimePC;

    /**
     * ショップ公開終了日時pc
     */
    private Timestamp shopOpenEndTimePC;

    /**
     * ショップ公開開始日時携帯
     */
    private Timestamp shopOpenStartTimeMB;

    /**
     * ショップ公開終了日時携帯
     */
    private Timestamp shopOpenEndTimeMB;

    /**
     * meta-description
     */
    private String metaDescription;

    /**
     * meta-keyword
     */
    private String metaKeyword;

    /**
     * ポイント付与基準金額
     */
    private BigDecimal pointAddBasePriceDefault;

    /**
     * ポイント付与率
     */
    private BigDecimal pointAddRateDefault;

    /**
     * ポイント適用開始日時1
     */
    private Timestamp pointStartTime1;

    /**
     * ポイント適用終了日時1
     */
    private Timestamp pointEndTime1;

    /**
     * ポイント付与基準金額1
     */
    private BigDecimal pointAddBasePrice1;

    /**
     * ポイント付与率1
     */
    private BigDecimal pointAddRate1;

    /**
     * ポイント適用開始日時2
     */
    private Timestamp pointStartTime2;

    /**
     * ポイント適用終了日時2
     */
    private Timestamp pointEndTime2;

    /**
     * ポイント付与基準金額2
     */
    private BigDecimal pointAddBasePrice2;

    /**
     * ポイント付与率2
     */
    private BigDecimal pointAddRate2;

    /**
     * 会員ランク判定日
     */
    private Timestamp memberRankSetDate;

    /**
     * 会員ランク累計開始日時
     */
    private Timestamp memberRankPurchaseStartTime;

    /**
     * 更新カウンタ
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
     * 自動承認フラグ
     */
    private HTypeAutoApprovalFlag autoApprovalFlag;

    /**
     * レビューデフォルトポイント付与数
     */
    private BigDecimal reviewDefaultPoint;

    // 2023-renew No60 from here
    /**
     * クレジットカードエラー回数上限
     */
    private Integer creditErrorCount;
    // 2023-renew No60 to here
}
