/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.order.additional;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 受注追加料金クラス
 *
 * @author EntityGenerator
 */
@Data
@Component
@Scope("prototype")
public class OrderAdditionalChargeEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受注SEQ（必須）
     */
    private Integer orderSeq;

    /**
     * 受注追加料金連番（必須）
     */
    private Integer orderAdditionalChargeVersionNo;

    /**
     * 表示順（必須）
     */
    private Integer orderDisplay;

    /**
     * 追加明細項目名（必須）
     */
    private String additionalDetailsName;

    /**
     * 追加明細金額（必須）
     */
    private BigDecimal additionalDetailsPrice = BigDecimal.ZERO;

    /**
     * 登録日時（必須）
     */
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    private Timestamp updateTime;
}
