/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.totaling;

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
 * RFM分析検索条件クラス
 *
 * @author EntityGenerator
 * @version $Revision: 1.0 $
 */
@Entity
@Table(name = "RfmAnalysisSearchCondition")
@Data
@Component
@Scope("prototype")
public class RfmAnalysisSearchConditionEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ショップSEQ（必須）
     */
    @Column(name = "shopSeq")
    @Id
    private Integer shopSeq;

    /**
     * r1From
     */
    @Column(name = "r1From")
    private BigDecimal r1From;

    /**
     * r1To
     */
    @Column(name = "r1To")
    private BigDecimal r1To;

    /**
     * r2From
     */
    @Column(name = "r2From")
    private BigDecimal r2From;

    /**
     * r2To
     */
    @Column(name = "r2To")
    private BigDecimal r2To;

    /**
     * r3From
     */
    @Column(name = "r3From")
    private BigDecimal r3From;

    /**
     * r3To
     */
    @Column(name = "r3To")
    private BigDecimal r3To;

    /**
     * r4From
     */
    @Column(name = "r4From")
    private BigDecimal r4From;

    /**
     * r4To
     */
    @Column(name = "r4To")
    private BigDecimal r4To;

    /**
     * r5From
     */
    @Column(name = "r5From")
    private BigDecimal r5From;

    /**
     * r5To
     */
    @Column(name = "r5To")
    private BigDecimal r5To;

    /**
     * f1From
     */
    @Column(name = "f1From")
    private BigDecimal f1From;

    /**
     * f1To
     */
    @Column(name = "f1To")
    private BigDecimal f1To;

    /**
     * f2From
     */
    @Column(name = "f2From")
    private BigDecimal f2From;

    /**
     * f2To
     */
    @Column(name = "f2To")
    private BigDecimal f2To;

    /**
     * f3From
     */
    @Column(name = "f3From")
    private BigDecimal f3From;

    /**
     * f3To
     */
    @Column(name = "f3To")
    private BigDecimal f3To;

    /**
     * f4From
     */
    @Column(name = "f4From")
    private BigDecimal f4From;

    /**
     * f4To
     */
    @Column(name = "f4To")
    private BigDecimal f4To;

    /**
     * f5From
     */
    @Column(name = "f5From")
    private BigDecimal f5From;

    /**
     * f5To
     */
    @Column(name = "f5To")
    private BigDecimal f5To;

    /**
     * m1From
     */
    @Column(name = "m1From")
    private BigDecimal m1From;

    /**
     * m1To
     */
    @Column(name = "m1To")
    private BigDecimal m1To;

    /**
     * m2From
     */
    @Column(name = "m2From")
    private BigDecimal m2From;

    /**
     * m2To
     */
    @Column(name = "m2To")
    private BigDecimal m2To;

    /**
     * m3From
     */
    @Column(name = "m3From")
    private BigDecimal m3From;

    /**
     * m3To
     */
    @Column(name = "m3To")
    private BigDecimal m3To;

    /**
     * m4From
     */
    @Column(name = "m4From")
    private BigDecimal m4From;

    /**
     * m4To
     */
    @Column(name = "m4To")
    private BigDecimal m4To;

    /**
     * m5From
     */
    @Column(name = "m5From")
    private BigDecimal m5From;

    /**
     * m5To
     */
    @Column(name = "m5To")
    private BigDecimal m5To;

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

}
