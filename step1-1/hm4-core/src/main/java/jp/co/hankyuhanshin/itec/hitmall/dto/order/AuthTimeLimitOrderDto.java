/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.order;

import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * オーソリ期限切れ間近受注Dto
 *
 * @author DtoGenerator
 */
@Entity
@Data
@Component
@Scope("prototype")
public class AuthTimeLimitOrderDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受注番号
     */
    private String orderCode;

    /**
     * オーソリ期限日（決済日付＋オーソリ保持期間）
     */
    private Timestamp authoryLimitDate;
}
