/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeleteFlag;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.SequenceGenerator;
import org.seasar.doma.Table;
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
@Entity
@Table(name = "Campaign")
@Data
@Component
@Scope("prototype")
public class CampaignEntity implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** キャンペーンSEQ（必須） */
    @Column(name = "campaignSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "campaignSeq")
    private Integer campaignSeq;

    /** ショップSEQ (FK)（必須） */
    @Column(name = "shopSeq")
    private Integer shopSeq;

    /** キャンペーンコード */
    @Column(name = "campaignCode")
    private String campaignCode;

    /** キャンペーン名（必須） */
    @Column(name = "campaignName")
    private String campaignName;

    /** キャンペーン開始日 */
    @Column(name = "campaignStartDate")
    private Timestamp campaignStartDate;

    /** キャンペーン終了日 */
    @Column(name = "campaignEndDate")
    private Timestamp campaignEndDate;

    /** リダイレクト先url */
    @Column(name = "redirectUrl")
    private String redirectUrl;

    /** 備考 */
    @Column(name = "note")
    private String note;

    /** キャンペーンコスト */
    @Column(name = "campaignCost")
    private BigDecimal campaignCost = new BigDecimal(0);

    /** 削除フラグ（必須） */
    @Column(name = "deleteFlag")
    private HTypeDeleteFlag deleteFlag = HTypeDeleteFlag.OFF;

    /** 登録日時（必須） */
    @Column(name = "registTime")
    private Timestamp registTime;

    /** 更新日時（必須） */
    @Column(name = "updateTime")
    private Timestamp updateTime;

}
