/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.dto.multipayment;

import com.gmo_pg.g_pay.client.input.SaveMemberInput;
import org.springframework.stereotype.Component;

/**
 * HitMall用に項目追加された XxxInput DTO
 *
 * @author kanayama
 */
@Component
public class HmSaveMemberInput extends SaveMemberInput implements HmPaymentClientInput {

    /**
     * シリアル
     */
    private static final long serialVersionUID = 1L;

    /**
     * OrderSeq
     */
    protected Integer orderSeq;

    /**
     * OrderVersionNo
     */
    protected Integer orderVersionNo;

    @Override
    public Integer getOrderSeq() {
        return this.orderSeq;
    }

    @Override
    public Integer getOrderVersionNo() {
        return this.orderVersionNo;
    }

    @Override
    public void setOrderSeq(Integer orderSeq) {
        this.orderSeq = orderSeq;
    }

    @Override
    public void setOrderVersionNo(Integer orderVersionNo) {
        this.orderVersionNo = orderVersionNo;
    }
}
