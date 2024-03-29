/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.campaign;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeleteFlag;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * キャンペーンクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@Data
@Component
@Scope("prototype")
public class CampaignEntity implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** キャンペーンSEQ（必須） */
    private Integer campaignSeq;

    /** ショップSEQ (FK)（必須） */
    private Integer shopSeq;

    /** キャンペーンコード */
    private String campaignCode;

    /** キャンペーン名（必須） */
    private String campaignName;

    /** キャンペーン開始日 */
    private Timestamp campaignStartDate;

    /** キャンペーン終了日 */
    private Timestamp campaignEndDate;

    /** リダイレクト先url */
    private String redirectUrl;

    /** 備考 */
    private String note;

    /** キャンペーンコスト */
    private BigDecimal campaignCost = new BigDecimal(0);

    /** 削除フラグ（必須） */
    private HTypeDeleteFlag deleteFlag = HTypeDeleteFlag.OFF;

    /** 登録日時（必須） */
    private Timestamp registTime;

    /** 更新日時（必須） */
    private Timestamp updateTime;

}
