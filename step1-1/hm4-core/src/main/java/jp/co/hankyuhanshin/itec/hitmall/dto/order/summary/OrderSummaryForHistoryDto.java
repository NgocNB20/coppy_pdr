/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.order.summary;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCancelFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeWaitingFlag;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 注文履歴用注文サマリDto<br/>
 *
 * @author DtoGenerator
 */
@Entity
@Data
@Component
@Scope("prototype")
public class OrderSummaryForHistoryDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 定期受注コード
     */
    private String periodicOrderCode;

    /**
     * 受注SEQ
     */
    private Integer orderSeq;

    /**
     * 受注履歴連番
     */
    private Integer orderVersionNo;

    /**
     * 受注コード
     */
    private String orderCode;

    /**
     * 受注日時
     */
    private Timestamp orderTime;

    /**
     * キャンセルフラグ
     */
    private HTypeCancelFlag cancelFlag;

    /**
     * 保留中フラグ
     */
    private HTypeWaitingFlag waitingFlag;

    /**
     * 受注状態
     */
    private HTypeOrderStatus orderStatus;

    /**
     * 商品金額合計
     */
    private BigDecimal goodsPriceTotal;

    /**
     * 受注金額
     */
    private BigDecimal orderPrice;

    /**
     * 入金累計
     */
    private BigDecimal receiptPriceTotal;

    /**
     * 会員SEQ
     */
    private Integer memberInfoSeq;

    /**
     * 定期受注SEQ
     */
    private Integer periodicOrderSeq;
}
