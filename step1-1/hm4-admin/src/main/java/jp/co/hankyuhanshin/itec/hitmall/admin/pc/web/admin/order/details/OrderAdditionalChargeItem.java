/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 受注追加料金アイテムクラス<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class OrderAdditionalChargeItem implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受注SEQ
     */
    private Integer orderSeq;

    /**
     * 受注追加料金連番
     */
    private Integer orderAdditionalChargeVersionNo;

    /**
     * 表示順
     */
    private Integer orderDisplay;

    /**
     * 受注履歴連番
     */
    private Integer orderVersionNo;

    /**
     * 追加明細項目名
     */
    private String additionalDetailsName;

    /**
     * 追加明細金額
     */
    private BigDecimal additionalDetailsPrice = BigDecimal.ZERO;

    /**
     * 登録日時
     */
    private Timestamp registTime;

    /**
     * 更新日時
     */
    private Timestamp updateTime;
}
