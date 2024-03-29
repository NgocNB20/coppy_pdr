/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.order;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 入金登録Dtoクラス
 *
 * @author DtoGenerator
 * @version $Revision: 1.2 $
 */
@Data
@Component
@Scope("prototype")
public class PaymentRegistDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受注番号
     */
    private String orderCode;

    /**
     * 受注履歴連番
     */
    private Integer orderVersionNo;

    /**
     * 入金金額
     */
    private BigDecimal receiptPrice;

    /**
     * 入金日時
     */
    private Timestamp receiptTime;

}
